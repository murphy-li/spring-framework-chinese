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

package org.springframework.beans.factory.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.Resource;

/**
 * Convenience extension of {@link DefaultListableBeanFactory} that reads bean definitions
 * from an XML document. Delegates to {@link XmlBeanDefinitionReader} underneath; effectively
 * equivalent to using an XmlBeanDefinitionReader with a DefaultListableBeanFactory.
 *
 * <p>The structure, element and attribute names of the required XML document
 * are hard-coded in this class. (Of course a transform could be run if necessary
 * to produce this format). "beans" doesn't need to be the root element of the XML
 * document: This class will parse all bean definition elements in the XML file.
 *
 * <p>This class registers each bean definition with the {@link DefaultListableBeanFactory}
 * superclass, and relies on the latter's implementation of the {@link BeanFactory} interface.
 * It supports singletons, prototypes, and references to either of these kinds of bean.
 * See {@code "spring-beans-3.x.xsd"} (or historically, {@code "spring-beans-2.0.dtd"}) for
 * details on options and configuration style.
 *
 * <p><b>For advanced needs, consider using a {@link DefaultListableBeanFactory} with
 * an {@link XmlBeanDefinitionReader}.</b> The latter allows for reading from multiple XML
 * resources and is highly configurable in its actual XML parsing behavior.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 15 April 2001
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see XmlBeanDefinitionReader
 * @deprecated as of Spring 3.1 in favor of {@link DefaultListableBeanFactory} and
 * {@link XmlBeanDefinitionReader}
 */
/**
 * {@link  DefaultListableBeanFactory}的便捷扩展，可从XML文档读取bean定义。 
 * 委托给下面的{@link  XmlBeanDefinitionReader}； 
 * 实际上等效于将XmlBeanDefinitionReader与DefaultListableBeanFactory一起使用。 
 *  <p>所需的XML文档的结构，元素和属性名称在此类中进行了硬编码。 
 *  （当然，如果需要产生此格式，可以运行转换）。 
 *  "beans"不必是XML文档的根元素：此类将解析XML文件中的所有bean定义元素。 
 *  <p>此类使用{@link  DefaultListableBeanFactory}超类注册每个bean定义，并依赖后者的{@link  BeanFactory}接口的实现。 
 * 它支持单例，原型和对这两种bean的引用。 
 * 有关选项和配置样式的详细信息，请参见{@code "spring-beans-3.x.xsd"}（或从历史上看，{<@code>"spring-beans-2.0.dtd"}）。 
 *  <p> <b>出于高级需求，请考虑将{@link  DefaultListableBeanFactory}与{@link  XmlBeanDefinitionReader}一起使用。 
 * </ b>后者允许从多个XML资源中读取，并且在实际中可以高度配置XML解析行为。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Chris Beams @自2001年4月15日以来
 * @see  org.springframework.beans.factory.support.DefaultListableBeanFactory 
 * @see  XmlBeanDefinitionReader @自Spring 3.1开始弃用{@link  DefaultListableBeanFactory}和{@link  XmlBeanDefinitionReader}的支持
 */
@Deprecated
@SuppressWarnings({"serial", "all"})
public class XmlBeanFactory extends DefaultListableBeanFactory {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


	/**
	 * Create a new XmlBeanFactory with the given resource,
	 * which must be parsable using DOM.
	 * @param resource the XML resource to load bean definitions from
	 * @throws BeansException in case of loading or parsing errors
	 */
	/**
	 * 使用给定资源创建一个新的XmlBeanFactory，该资源必须可以使用DOM进行解析。 
	 *  
	 * @param 资源XML资源，用于在加载或解析错误的情况下从
	 * @throws  BeansException加载Bean定义。 
	 * 
	 */
	public XmlBeanFactory(Resource resource) throws BeansException {
		this(resource, null);
	}

	/**
	 * Create a new XmlBeanFactory with the given input stream,
	 * which must be parsable using DOM.
	 * @param resource the XML resource to load bean definitions from
	 * @param parentBeanFactory parent bean factory
	 * @throws BeansException in case of loading or parsing errors
	 */
	/**
	 * 使用给定的输入流创建一个新的XmlBeanFactory，必须使用DOM对其进行解析。 
	 *  
	 * @param 资源XML资源，用于从
	 * @param  parentBeanFactory父bean工厂
	 * @throws  BeansException加载bean定义，以防加载或解析错误
	 */
	public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
		super(parentBeanFactory);
		this.reader.loadBeanDefinitions(resource);
	}

}
