/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.test.util;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.Assert;

/**
 * {@code AopTestUtils} is a collection of AOP-related utility methods for
 * use in unit and integration testing scenarios.
 *
 * <p>For Spring's core AOP utilities, see
 * {@link org.springframework.aop.support.AopUtils AopUtils} and
 * {@link org.springframework.aop.framework.AopProxyUtils AopProxyUtils}.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 4.2
 * @see org.springframework.aop.support.AopUtils
 * @see org.springframework.aop.framework.AopProxyUtils
 * @see ReflectionTestUtils
 */
/**
 * {@code  AopTestUtils}是AOP相关实用程序方法的集合，可用于单元和集成测试方案。 
 *  <p>有关Spring的核心AOP实用程序，请参见{@link  org.springframework.aop.support.AopUtils AopUtils}和{@link  org.springframework.aop.framework.AopProxyUtils AopProxyUtils}。 
 *  @author  Sam Brannen @author  Juergen Hoeller @since 4.2起
 * @see  org.springframework.aop.support.AopUtils 
 * @see  org.springframework.aop.framework.AopProxyUtils 
 * @see  ReflectionTestUtils
 */
public abstract class AopTestUtils {

	/**
	 * Get the <em>target</em> object of the supplied {@code candidate} object.
	 * <p>If the supplied {@code candidate} is a Spring
	 * {@linkplain AopUtils#isAopProxy proxy}, the target of the proxy will
	 * be returned; otherwise, the {@code candidate} will be returned
	 * <em>as is</em>.
	 * @param candidate the instance to check (potentially a Spring AOP proxy;
	 * never {@code null})
	 * @return the target object or the {@code candidate} (never {@code null})
	 * @throws IllegalStateException if an error occurs while unwrapping a proxy
	 * @see Advised#getTargetSource()
	 * @see #getUltimateTargetObject
	 */
	/**
	 * 获取提供的{@code 候选者}对象的<em> target </ em>对象。 
	 *  <p>如果提供的{@code 候选者}是Spring {@link  plain AopUtils＃isAopProxy代理}，则将返回代理的目标； 
	 * 否则，将按原样返回{@code 候选者}。 
	 *  
	 * @param 候选对象要检查的实例（可能是Spring AOP代理； 
	 * 决不{@code  null}）
	 * @return 目标对象或{@code 候选者}（决不{@code  null} ）
	 * @throws  IllegalStateException如果展开代理时发生错误
	 * @see  Advised＃getTargetSource（）
	 * @see  #getUltimateTargetObject
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getTargetObject(Object candidate) {
		Assert.notNull(candidate, "Candidate must not be null");
		try {
			if (AopUtils.isAopProxy(candidate) && candidate instanceof Advised) {
				Object target = ((Advised) candidate).getTargetSource().getTarget();
				if (target != null) {
					return (T) target;
				}
			}
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Failed to unwrap proxied object", ex);
		}
		return (T) candidate;
	}

	/**
	 * Get the ultimate <em>target</em> object of the supplied {@code candidate}
	 * object, unwrapping not only a top-level proxy but also any number of
	 * nested proxies.
	 * <p>If the supplied {@code candidate} is a Spring
	 * {@linkplain AopUtils#isAopProxy proxy}, the ultimate target of all
	 * nested proxies will be returned; otherwise, the {@code candidate}
	 * will be returned <em>as is</em>.
	 * @param candidate the instance to check (potentially a Spring AOP proxy;
	 * never {@code null})
	 * @return the target object or the {@code candidate} (never {@code null})
	 * @throws IllegalStateException if an error occurs while unwrapping a proxy
	 * @see Advised#getTargetSource()
	 * @see org.springframework.aop.framework.AopProxyUtils#ultimateTargetClass
	 */
	/**
	 * 获取提供的{@code 候选对象}对象的最终<em> target </ em>对象，不仅展开顶级代理，而且展开任意数量的嵌套代理。 
	 *  <p>如果提供的{@code 候选者}是Spring {@link  plain AopUtils＃isAopProxy代理}，则将返回所有嵌套代理的最终目标； 
	 * 否则，将按原样返回{@code 候选者}。 
	 *  
	 * @param 候选对象要检查的实例（可能是Spring AOP代理； 
	 * 绝不{@code  null}）
	 * @return 目标对象或{@code 候选者}（绝不{@code  null} ）
	 * @throws  IllegalStateException如果解包代理时发生错误
	 * @see  Advised＃getTargetSource（）
	 * @see  org.springframework.aop.framework.AopProxyUtils＃ultimateTargetClass
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getUltimateTargetObject(Object candidate) {
		Assert.notNull(candidate, "Candidate must not be null");
		try {
			if (AopUtils.isAopProxy(candidate) && candidate instanceof Advised) {
				Object target = ((Advised) candidate).getTargetSource().getTarget();
				if (target != null) {
					return (T) getUltimateTargetObject(target);
				}
			}
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Failed to unwrap proxied object", ex);
		}
		return (T) candidate;
	}

}
