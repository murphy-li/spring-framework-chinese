/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.config;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

/**
 * {@link BeanDefinitionParser} responsible for parsing the
 * {@code <aop:spring-configured/>} tag.
 *
 * <p><b>NOTE:</b> This is essentially a duplicate of Spring 2.5's
 * {@link org.springframework.context.config.SpringConfiguredBeanDefinitionParser}
 * for the {@code <context:spring-configured/>} tag, mirrored here for compatibility with
 * Spring 2.0's {@code <aop:spring-configured/>} tag (avoiding a direct dependency on the
 * context package).
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * {@link  BeanDefinitionParser}负责解析{@code  <aop：spring-configured />}标签。 
 *  <p> <b>注意：</ b>这实质上是Spring 2.5的{@code  <context：spring-configured的{{@link> org.springframework.context.config.SpringConfiguredBeanDefinitionParser}}的副本。 
 *  />}标记，在此处镜像以与Spring 2.0的{@code  <aop：spring-configured />}标记兼容（避免直接依赖于上下文包）。 
 *  @author 罗布·哈罗普（Rob Harrop）@author  Juergen Hoeller @始于2.0
 */
class SpringConfiguredBeanDefinitionParser implements BeanDefinitionParser {

	/**
	 * The bean name of the internally managed bean configurer aspect.
	 */
	/**
	 * 内部管理的bean配置器方面的bean名称。 
	 * 
	 */
	public static final String BEAN_CONFIGURER_ASPECT_BEAN_NAME =
			"org.springframework.context.config.internalBeanConfigurerAspect";

	private static final String BEAN_CONFIGURER_ASPECT_CLASS_NAME =
			"org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect";


	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if (!parserContext.getRegistry().containsBeanDefinition(BEAN_CONFIGURER_ASPECT_BEAN_NAME)) {
			RootBeanDefinition def = new RootBeanDefinition();
			def.setBeanClassName(BEAN_CONFIGURER_ASPECT_CLASS_NAME);
			def.setFactoryMethodName("aspectOf");
			def.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			def.setSource(parserContext.extractSource(element));
			parserContext.registerBeanComponent(new BeanComponentDefinition(def, BEAN_CONFIGURER_ASPECT_BEAN_NAME));
		}
		return null;
	}

}
