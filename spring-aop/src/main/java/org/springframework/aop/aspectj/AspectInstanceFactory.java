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

package org.springframework.aop.aspectj;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * Interface implemented to provide an instance of an AspectJ aspect.
 * Decouples from Spring's bean factory.
 *
 * <p>Extends the {@link org.springframework.core.Ordered} interface
 * to express an order value for the underlying aspect in a chain.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.beans.factory.BeanFactory#getBean
 */
/**
 * 实现以提供AspectJ方面的实例的接口。 
 * 从Spring的bean工厂解耦。 
 *  <p>扩展{@link  org.springframework.core.Ordered}接口，以表示链中基础方面的订单值。 
 *  @author  Rod Johnson @author  Juergen Hoeller @始于2.0 
 * @see  org.springframework.beans.factory.BeanFactory＃getBean
 */
public interface AspectInstanceFactory extends Ordered {

	/**
	 * Create an instance of this factory's aspect.
	 * @return the aspect instance (never {@code null})
	 */
	/**
	 * 创建该工厂方面的实例。 
	 *  
	 * @return 方面实例（永远{<@@code> null}）
	 */
	Object getAspectInstance();

	/**
	 * Expose the aspect class loader that this factory uses.
	 * @return the aspect class loader (or {@code null} for the bootstrap loader)
	 * @see org.springframework.util.ClassUtils#getDefaultClassLoader()
	 */
	/**
	 * 公开该工厂使用的方面类加载器。 
	 *  
	 * @return 方面类加载器（或引导加载器为{@code  null}）
	 * @see  org.springframework.util.ClassUtils＃getDefaultClassLoader（）
	 */
	@Nullable
	ClassLoader getAspectClassLoader();

}
