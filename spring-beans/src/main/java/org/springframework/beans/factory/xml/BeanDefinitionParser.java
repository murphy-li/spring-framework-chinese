/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2011 the original author or authors.
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
 * 版权所有2002-2011的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.xml;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.lang.Nullable;

/**
 * Interface used by the {@link DefaultBeanDefinitionDocumentReader} to handle custom,
 * top-level (directly under {@code <beans/>}) tags.
 *
 * <p>Implementations are free to turn the metadata in the custom tag into as many
 * {@link BeanDefinition BeanDefinitions} as required.
 *
 * <p>The parser locates a {@link BeanDefinitionParser} from the associated
 * {@link NamespaceHandler} for the namespace in which the custom tag resides.
 *
 * @author Rob Harrop
 * @since 2.0
 * @see NamespaceHandler
 * @see AbstractBeanDefinitionParser
 */
/**
 * {@link  DefaultBeanDefinitionDocumentReader}用于处理自定义顶级（直接在{@code  <beans />}下）标签的接口。 
 *  <p>实现可以随意将自定义标记中的元数据转换为所需的{{@link> BeanDefinition BeanDefinitions}个。 
 *  <p>解析器从关联的{@link  NamespaceHandler}中找到一个{@link  BeanDefinitionParser}，以查找自定义标签所在的名称空间。 
 *  @author  Rob Harrop @since 2.0起
 * @see  NamespaceHandler 
 * @see  AbstractBeanDefinitionParser
 */
public interface BeanDefinitionParser {

	/**
	 * Parse the specified {@link Element} and register the resulting
	 * {@link BeanDefinition BeanDefinition(s)} with the
	 * {@link org.springframework.beans.factory.xml.ParserContext#getRegistry() BeanDefinitionRegistry}
	 * embedded in the supplied {@link ParserContext}.
	 * <p>Implementations must return the primary {@link BeanDefinition} that results
	 * from the parse if they will ever be used in a nested fashion (for example as
	 * an inner tag in a {@code <property/>} tag). Implementations may return
	 * {@code null} if they will <strong>not</strong> be used in a nested fashion.
	 * @param element the element that is to be parsed into one or more {@link BeanDefinition BeanDefinitions}
	 * @param parserContext the object encapsulating the current state of the parsing process;
	 * provides access to a {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
	 * @return the primary {@link BeanDefinition}
	 */
	/**
	 * 解析指定的{@link 元素}并使用嵌入的{@link  org.springframework.beans.factory.xml.ParserContext＃getRegistry（）BeanDefinitionRegistry}注册生成的{@link  BeanDefinition BeanDefinition（s）}在提供的{@link  ParserContext}中。 
	 *  <p>实施必须以嵌套方式使用（例如，作为{@code  <property />}标记中的内部标记），因此必须返回解析产生的主{@link  BeanDefinition} ）。 
	 * 如果实现<strong>不</ strong>以嵌套方式使用，则实现可能返回{@code  null}。 
	 *  
	 * @param 元素将被解析为一个或多个{{@link> BeanDefinition BeanDefinitions}的元素。 
	 * 
	 * @param  parserContext该对象封装了解析过程的当前状态； 
	 * 提供对{@link  org.springframework.beans.factory.support.BeanDefinitionRegistry}的访问
	 * @return 主{@link  BeanDefinition}
	 */
	@Nullable
	BeanDefinition parse(Element element, ParserContext parserContext);

}
