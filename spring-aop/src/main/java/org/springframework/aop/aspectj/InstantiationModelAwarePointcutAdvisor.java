/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2006 the original author or authors.
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
 * 版权所有2002-2006的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.aspectj;

import org.springframework.aop.PointcutAdvisor;

/**
 * Interface to be implemented by Spring AOP Advisors wrapping AspectJ
 * aspects that may have a lazy initialization strategy. For example,
 * a perThis instantiation model would mean lazy initialization of the advice.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 由Spring AOP Advisors实现的接口，其中包装了可能具有惰性初始化策略的AspectJ方面。 
 * 例如，perThis实例化模型将意味着建议的延迟初始化。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @始于2.0
 */
public interface InstantiationModelAwarePointcutAdvisor extends PointcutAdvisor {

	/**
	 * Return whether this advisor is lazily initializing its underlying advice.
	 */
	/**
	 * 返回此顾问是否正在延迟初始化其基础建议。 
	 * 
	 */
	boolean isLazy();

	/**
	 * Return whether this advisor has already instantiated its advice.
	 */
	/**
	 * 返回此顾问是否已实例化其建议。 
	 * 
	 */
	boolean isAdviceInstantiated();

}
