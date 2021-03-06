/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.context.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * A simple thread-backed {@link Scope} implementation.
 *
 * <p><b>NOTE:</b> This thread scope is not registered by default in common contexts.
 * Instead, you need to explicitly assign it to a scope key in your setup, either through
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope}
 * or through a {@link org.springframework.beans.factory.config.CustomScopeConfigurer} bean.
 *
 * <p>{@code SimpleThreadScope} <em>does not clean up any objects</em> associated with it.
 * It is therefore typically preferable to use a request-bound scope implementation such
 * as {@code org.springframework.web.context.request.RequestScope} in web environments,
 * implementing the full lifecycle for scoped attributes (including reliable destruction).
 *
 * <p>For an implementation of a thread-based {@code Scope} with support for destruction
 * callbacks, refer to
 * <a href="https://www.springbyexample.org/examples/custom-thread-scope-module.html">Spring by Example</a>.
 *
 * <p>Thanks to Eugene Kuleshov for submitting the original prototype for a thread scope!
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 * @see org.springframework.web.context.request.RequestScope
 */
/**
 * 一个简单的线程支持的{@link  Scope}实现。 
 *  <p> <b>注意：</ b>在常见上下文中，默认情况下未注册此线程范围。 
 * 相反，您需要通过{@link  org.springframework.beans.factory.config.ConfigurableBeanFactory＃registerScope}或通过{@link  org.springframework.beans将其显式分配给设置中的作用域键.factory.config.CustomScopeConfigurer} bean。 
 *  <p> {<@@code> SimpleThreadScope} <em>不会清除与其关联的任何对象</ em>。 
 * 因此，通常最好在Web环境中使用请求绑定的范围实现，例如{@code  org.springframework.web.context.request.RequestScope}，以实现范围属性的完整生命周期（包括可靠的销毁）。 
 *  <p>有关支持销毁回调的基于线程的{@code  Scope}的实现，请参考<a href ="https://www.springbyexample.org/examples/custom-thread-scope-module .html">以实例为基础的</a>。 
 *  <p>感谢Eugene Kuleshov为线程范围提交了原始原型！ 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @从3.0起
 * @see  org.springframework.web.context.request.RequestScope
 */
public class SimpleThreadScope implements Scope {

	private static final Log logger = LogFactory.getLog(SimpleThreadScope.class);

	private final ThreadLocal<Map<String, Object>> threadScope =
			new NamedThreadLocal<Map<String, Object>>("SimpleThreadScope") {
				@Override
				protected Map<String, Object> initialValue() {
					return new HashMap<>();
				}
			};


	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> scope = this.threadScope.get();
		Object scopedObject = scope.get(name);
		if (scopedObject == null) {
			scopedObject = objectFactory.getObject();
			scope.put(name, scopedObject);
		}
		return scopedObject;
	}

	@Override
	@Nullable
	public Object remove(String name) {
		Map<String, Object> scope = this.threadScope.get();
		return scope.remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		logger.warn("SimpleThreadScope does not support destruction callbacks. " +
				"Consider using RequestScope in a web environment.");
	}

	@Override
	@Nullable
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return Thread.currentThread().getName();
	}

}
