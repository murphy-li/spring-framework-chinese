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

package org.springframework.context.annotation;

import java.util.Set;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.lang.Nullable;

/**
 * Parser for the &lt;context:annotation-config/&gt; element.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Christian Dupuis
 * @since 2.5
 * @see AnnotationConfigUtils
 */
/**
 * <context：annotation-config />元素的解析器。 
 *  @author  Mark Fisher @author  Juergen Hoeller @author  Christian Dupuis @从2.5开始
 * @see  AnnotationConfigUtils
 */
public class AnnotationConfigBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	@Nullable
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);

		// Obtain bean definitions for all relevant BeanPostProcessors.
		Set<BeanDefinitionHolder> processorDefinitions =
				AnnotationConfigUtils.registerAnnotationConfigProcessors(parserContext.getRegistry(), source);

		// Register component for the surrounding <context:annotation-config> element.
		CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
		parserContext.pushContainingComponent(compDefinition);

		// Nest the concrete beans in the surrounding component.
		for (BeanDefinitionHolder processorDefinition : processorDefinitions) {
			parserContext.registerComponent(new BeanComponentDefinition(processorDefinition));
		}

		// Finally register the composite component.
		parserContext.popAndRegisterContainingComponent();

		return null;
	}

}
