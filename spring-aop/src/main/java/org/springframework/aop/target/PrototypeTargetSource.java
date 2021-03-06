/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.target;

import org.springframework.beans.BeansException;

/**
 * {@link org.springframework.aop.TargetSource} implementation that
 * creates a new instance of the target bean for each request,
 * destroying each instance on release (after each request).
 *
 * <p>Obtains bean instances from its containing
 * {@link org.springframework.beans.factory.BeanFactory}.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #setBeanFactory
 * @see #setTargetBeanName
 */
/**
 * {@link  org.springframework.aop.TargetSource}实现为每个请求创建目标Bean的新实例，并在发行时（在每个请求之后）销毁每个实例。 
 *  <p>从其包含的{@link  org.springframework.beans.factory.BeanFactory}获取bean实例。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller 
 * @see  #setBeanFactory 
 * @see  #setTargetBeanName
 */
@SuppressWarnings("serial")
public class PrototypeTargetSource extends AbstractPrototypeBasedTargetSource {

	/**
	 * Obtain a new prototype instance for every call.
	 * @see #newPrototypeInstance()
	 */
	/**
	 * 为每个调用获取一个新的原型实例。 
	 *  
	 * @see  #newPrototypeInstance（）
	 */
	@Override
	public Object getTarget() throws BeansException {
		return newPrototypeInstance();
	}

	/**
	 * Destroy the given independent instance.
	 * @see #destroyPrototypeInstance
	 */
	/**
	 * 销毁给定的独立实例。 
	 *  
	 * @see  #destroyPrototypeInstance
	 */
	@Override
	public void releaseTarget(Object target) {
		destroyPrototypeInstance(target);
	}

	@Override
	public String toString() {
		return "PrototypeTargetSource for target bean with name '" + getTargetBeanName() + "'";
	}

}
