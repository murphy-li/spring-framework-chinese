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

package org.springframework.web.context.request;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.Nullable;

/**
 * Abstract {@link Scope} implementation that reads from a particular scope
 * in the current thread-bound {@link RequestAttributes} object.
 *
 * <p>Subclasses simply need to implement {@link #getScope()} to instruct
 * this class which {@link RequestAttributes} scope to read attributes from.
 *
 * <p>Subclasses may wish to override the {@link #get} and {@link #remove}
 * methods to add synchronization around the call back into this super class.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 2.0
 */
/**
 * 从当前线程绑定的{@link  RequestAttributes}对象中的特定范围读取的抽象{@link  Scope}实现。 
 *  <p>子类仅需要实现{@link  #getScope（）}来指示此类从哪个{@link  RequestAttributes}范围读取属性。 
 *  <p>子类可能希望重写{@link  #get}和{@link  #remove}方法，以在回退到该超类的调用中添加同步。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @author  Rob Harrop @since 2.0
 */
public abstract class AbstractRequestAttributesScope implements Scope {

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		Object scopedObject = attributes.getAttribute(name, getScope());
		if (scopedObject == null) {
			scopedObject = objectFactory.getObject();
			attributes.setAttribute(name, scopedObject, getScope());
			// Retrieve object again, registering it for implicit session attribute updates.
			// As a bonus, we also allow for potential decoration at the getAttribute level.
			Object retrievedObject = attributes.getAttribute(name, getScope());
			if (retrievedObject != null) {
				// Only proceed with retrieved object if still present (the expected case).
				// If it disappeared concurrently, we return our locally created instance.
				scopedObject = retrievedObject;
			}
		}
		return scopedObject;
	}

	@Override
	@Nullable
	public Object remove(String name) {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		Object scopedObject = attributes.getAttribute(name, getScope());
		if (scopedObject != null) {
			attributes.removeAttribute(name, getScope());
			return scopedObject;
		}
		else {
			return null;
		}
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		attributes.registerDestructionCallback(name, callback, getScope());
	}

	@Override
	@Nullable
	public Object resolveContextualObject(String key) {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		return attributes.resolveReference(key);
	}


	/**
	 * Template method that determines the actual target scope.
	 * @return the target scope, in the form of an appropriate
	 * {@link RequestAttributes} constant
	 * @see RequestAttributes#SCOPE_REQUEST
	 * @see RequestAttributes#SCOPE_SESSION
	 */
	/**
	 * 确定实际目标范围的模板方法。 
	 *  
	 * @return 目标范围，以适当的{@link  RequestAttributes}常量的形式
	 * @see  RequestAttributes＃SCOPE_REQUEST 
	 * @see  RequestAttributes＃SCOPE_SESSION
	 */
	protected abstract int getScope();

}
