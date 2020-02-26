/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.framework.autoproxy;

import org.springframework.aop.TargetSource;
import org.springframework.lang.Nullable;

/**
 * Implementations can create special target sources, such as pooling target
 * sources, for particular beans. For example, they may base their choice
 * on attributes, such as a pooling attribute, on the target class.
 *
 * <p>AbstractAutoProxyCreator can support a number of TargetSourceCreators,
 * which will be applied in order.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
/**
 * 实现可以创建特殊的目标源，例如为特定的bean合并目标源。 
 * 例如，他们可以根据目标类的属性（例如池属性）进行选择。 
 *  <p> AbstractAutoProxyCreator可以支持许多TargetSourceCreators，这些将按顺序应用。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller
 */
@FunctionalInterface
public interface TargetSourceCreator {

	/**
	 * Create a special TargetSource for the given bean, if any.
	 * @param beanClass the class of the bean to create a TargetSource for
	 * @param beanName the name of the bean
	 * @return a special TargetSource or {@code null} if this TargetSourceCreator isn't
	 * interested in the particular bean
	 */
	/**
	 * 如果给定bean，则创建一个特殊的TargetSource。 
	 *  @param beanClass为@param beanName创建TargetSource的bean的类bean的名称
	 * @return 特殊的TargetSource或{@code  null}（如果此TargetSourceCreator对特定bean不感兴趣）
	 */
	@Nullable
	TargetSource getTargetSource(Class<?> beanClass, String beanName);

}
