/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.test.context.support;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.util.Assert;

/**
 * Utility methods for working with
 * {@link ApplicationContextInitializer ApplicationContextInitializers}.
 *
 * <p>Although {@code ApplicationContextInitializerUtils} was first introduced
 * in Spring Framework 4.1, the initial implementations of methods in this class
 * were based on the existing code base in {@code ContextLoaderUtils}.
 *
 * @author Sam Brannen
 * @since 4.1
 * @see ContextConfiguration#initializers
 */
/**
 * 使用{@link  ApplicationContextInitializer ApplicationContextInitializers}的实用方法。 
 *  <p>尽管{@code  ApplicationContextInitializerUtils}是在Spring Framework 4.1中首次引入的，但此类中的方法的初始实现是基于{@code  ContextLoaderUtils}中的现有代码库。 
 *  @author  Sam Brannen @从4.1开始
 * @see  ContextConfiguration＃initializers
 */
abstract class ApplicationContextInitializerUtils {

	private static final Log logger = LogFactory.getLog(ApplicationContextInitializerUtils.class);


	/**
	 * Resolve the set of merged {@code ApplicationContextInitializer} classes for the
	 * supplied list of {@code ContextConfigurationAttributes}.
	 * <p>Note that the {@link ContextConfiguration#inheritInitializers inheritInitializers}
	 * flag of {@link ContextConfiguration @ContextConfiguration} will be taken into
	 * consideration. Specifically, if the {@code inheritInitializers} flag is set to
	 * {@code true} for a given level in the class hierarchy represented by the provided
	 * configuration attributes, context initializer classes defined at the given level
	 * will be merged with those defined in higher levels of the class hierarchy.
	 * @param configAttributesList the list of configuration attributes to process; must
	 * not be {@code null} or <em>empty</em>; must be ordered <em>bottom-up</em>
	 * (i.e., as if we were traversing up the class hierarchy)
	 * @return the set of merged context initializer classes, including those from
	 * superclasses if appropriate (never {@code null})
	 * @since 3.2
	 */
	/**
	 * 为提供的{@code  ContextConfigurationAttributes}列表解析合并的{@code  ApplicationContextInitializer}类的集合。 
	 *  <p>请注意，将考虑{@link  ContextConfiguration @ContextConfiguration}的{@link  ContextConfiguration＃inheritInitializersInheritInitializers}标志。 
	 * 具体来说，如果对于由提供的配置属性表示的类层次结构中的给定级别，将{@code  InheritInitializers}标志设置为{@code  true}，则在给定级别定义的上下文初始化器类将与那些合并。 
	 * 在更高层次的类层次结构中定义。 
	 *  
	 * @param  configAttributes列出要处理的配置属性列表； 
	 * 不得为{@code  null}或<em> empty </ em>； 
	 * 必须顺序排列<em>自下而上</ em>（即，就像我们遍历类层次结构一样）
	 * @return 合并的上下文初始化器类的集合，包括那些来自超类的类（如果合适的话，切勿使用{<@@code > null}），自3.2起
	 */
	static Set<Class<? extends ApplicationContextInitializer<?>>> resolveInitializerClasses(
			List<ContextConfigurationAttributes> configAttributesList) {

		Assert.notEmpty(configAttributesList, "ContextConfigurationAttributes List must not be empty");
		Set<Class<? extends ApplicationContextInitializer<?>>> initializerClasses = new LinkedHashSet<>();

		for (ContextConfigurationAttributes configAttributes : configAttributesList) {
			if (logger.isTraceEnabled()) {
				logger.trace("Processing context initializers for configuration attributes " + configAttributes);
			}
			Collections.addAll(initializerClasses, configAttributes.getInitializers());
			if (!configAttributes.isInheritInitializers()) {
				break;
			}
		}

		return initializerClasses;
	}

}
