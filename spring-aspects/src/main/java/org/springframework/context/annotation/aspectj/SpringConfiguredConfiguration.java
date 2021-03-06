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

package org.springframework.context.annotation.aspectj;

import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * {@code @Configuration} class that registers an {@code AnnotationBeanConfigurerAspect}
 * capable of performing dependency injection services for non-Spring managed objects
 * annotated with @{@link org.springframework.beans.factory.annotation.Configurable
 * Configurable}.
 *
 * <p>This configuration class is automatically imported when using the
 * {@link EnableSpringConfigured @EnableSpringConfigured} annotation. See
 * {@code @EnableSpringConfigured}'s javadoc for complete usage details.
 *
 * @author Chris Beams
 * @since 3.1
 * @see EnableSpringConfigured
 */
/**
 * {@code  @Configuration}类注册了一个{@code  AnnotationBeanConfigurerAspect}类，该类能够为使用@{<@link> org.springframework.beans.factory.annotation.Configurable注释的非Spring托管对象执行依赖项注入服务。 
 * 可配置}。 
 *  <p>使用{@link  EnableSpringConfigured @EnableSpringConfigured}注释时，将自动导入此配置类。 
 * 有关完整用法的详细信息，请参见{@code  @EnableSpringConfigured}的javadoc。 
 *  @author 克里斯·比姆斯（Chris Beams）自3.1起
 * @see  EnableSpringConfigured
 */
@Configuration
public class SpringConfiguredConfiguration {

	/**
	 * The bean name used for the configurer aspect.
	 */
	/**
	 * 用于配置器方面的Bean名称。 
	 * 
	 */
	public static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME =
			"org.springframework.context.config.internalBeanConfigurerAspect";

	@Bean(name = BEAN_CONFIGURER_ASPECT_BEAN_NAME)
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AnnotationBeanConfigurerAspect beanConfigurerAspect() {
		return AnnotationBeanConfigurerAspect.aspectOf();
	}

}
