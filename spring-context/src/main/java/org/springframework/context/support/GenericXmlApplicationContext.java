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

package org.springframework.context.support;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Convenient application context with built-in XML support.
 * This is a flexible alternative to {@link ClassPathXmlApplicationContext}
 * and {@link FileSystemXmlApplicationContext}, to be configured via setters,
 * with an eventual {@link #refresh()} call activating the context.
 *
 * <p>In case of multiple configuration files, bean definitions in later files
 * will override those defined in earlier files. This can be leveraged to
 * intentionally override certain bean definitions via an extra configuration
 * file appended to the list.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 3.0
 * @see #load
 * @see XmlBeanDefinitionReader
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext
 */
/**
 * 带有内置XML支持的便捷应用程序上下文。 
 * 这是{@link  ClassPathXmlApplicationContext}和{@link  FileSystemXmlApplicationContext}的灵活替代方法，可以通过设置器进行配置，并最终通过{@link  #refresh（）}调用来激活上下文。 
 *  <p>如果有多个配置文件，则更高版本文件中的Bean定义将覆盖先前文件中定义的Bean。 
 * 可以利用此属性通过附加到列表的额外配置文件来有意覆盖某些Bean定义。 
 *  @author  Juergen Hoeller @author 克里斯·比姆斯（Chris Beams）@since 3.0起// 
 * @see> #load 
 * @see  XmlBeanDefinitionReader 
 * @see 
 */
public class GenericXmlApplicationContext extends GenericApplicationContext {

	private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


	/**
	 * Create a new GenericXmlApplicationContext that needs to be
	 * {@link #load loaded} and then manually {@link #refresh refreshed}.
	 */
	/**
	 * 创建一个新的GenericXmlApplicationContext，它需要{@link  #load加载}，然后手动{@link  #refresh refreshed}。 
	 * 
	 */
	public GenericXmlApplicationContext() {
	}

	/**
	 * Create a new GenericXmlApplicationContext, loading bean definitions
	 * from the given resources and automatically refreshing the context.
	 * @param resources the resources to load from
	 */
	/**
	 * 创建一个新的GenericXmlApplicationContext，从给定资源加载bean定义并自动刷新上下文。 
	 *  
	 * @param 资源从中加载资源
	 */
	public GenericXmlApplicationContext(Resource... resources) {
		load(resources);
		refresh();
	}

	/**
	 * Create a new GenericXmlApplicationContext, loading bean definitions
	 * from the given resource locations and automatically refreshing the context.
	 * @param resourceLocations the resources to load from
	 */
	/**
	 * 创建一个新的GenericXmlApplicationContext，从给定的资源位置加载bean定义并自动刷新上下文。 
	 *  
	 * @param  resourceLocations从中加载资源
	 */
	public GenericXmlApplicationContext(String... resourceLocations) {
		load(resourceLocations);
		refresh();
	}

	/**
	 * Create a new GenericXmlApplicationContext, loading bean definitions
	 * from the given resource locations and automatically refreshing the context.
	 * @param relativeClass class whose package will be used as a prefix when
	 * loading each specified resource name
	 * @param resourceNames relatively-qualified names of resources to load
	 */
	/**
	 * 创建一个新的GenericXmlApplicationContext，从给定的资源位置加载bean定义并自动刷新上下文。 
	 *  
	 * @param  relativeClass类，在加载每个指定的资源名称时其包将用作前缀
	 * @param  resourceNames要加载的相对合格的资源名称
	 */
	public GenericXmlApplicationContext(Class<?> relativeClass, String... resourceNames) {
		load(relativeClass, resourceNames);
		refresh();
	}


	/**
	 * Exposes the underlying {@link XmlBeanDefinitionReader} for additional
	 * configuration facilities and {@code loadBeanDefinition} variations.
	 */
	/**
	 * 公开基础的{@link  XmlBeanDefinitionReader}，以获取其他配置工具和{@code  loadBeanDefinition}变体。 
	 * 
	 */
	public final XmlBeanDefinitionReader getReader() {
		return this.reader;
	}

	/**
	 * Set whether to use XML validation. Default is {@code true}.
	 */
	/**
	 * 设置是否使用XML验证。 
	 * 默认值为{@code  true}。 
	 * 
	 */
	public void setValidating(boolean validating) {
		this.reader.setValidating(validating);
	}

	/**
	 * Delegates the given environment to underlying {@link XmlBeanDefinitionReader}.
	 * Should be called before any call to {@code #load}.
	 */
	/**
	 * 将给定环境委托给基础{@link  XmlBeanDefinitionReader}。 
	 * 应该在调用{@code  #load}之前调用。 
	 * 
	 */
	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(getEnvironment());
	}


	//---------------------------------------------------------------------
	// Convenient methods for loading XML bean definition files
	//---------------------------------------------------------------------

	/**
	 * Load bean definitions from the given XML resources.
	 * @param resources one or more resources to load from
	 */
	/**
	 * 从给定的XML资源加载bean定义。 
	 *  
	 * @param 资源一个或多个要加载的资源
	 */
	public void load(Resource... resources) {
		this.reader.loadBeanDefinitions(resources);
	}

	/**
	 * Load bean definitions from the given XML resources.
	 * @param resourceLocations one or more resource locations to load from
	 */
	/**
	 * 从给定的XML资源加载bean定义。 
	 *  
	 * @param  resourceLocations一个或多个要从中加载的资源位置
	 */
	public void load(String... resourceLocations) {
		this.reader.loadBeanDefinitions(resourceLocations);
	}

	/**
	 * Load bean definitions from the given XML resources.
	 * @param relativeClass class whose package will be used as a prefix when
	 * loading each specified resource name
	 * @param resourceNames relatively-qualified names of resources to load
	 */
	/**
	 * 从给定的XML资源加载bean定义。 
	 *  
	 * @param  relativeClass类，在加载每个指定的资源名称时其包将用作前缀
	 * @param  resourceNames要加载的相对合格的资源名称
	 */
	public void load(Class<?> relativeClass, String... resourceNames) {
		Resource[] resources = new Resource[resourceNames.length];
		for (int i = 0; i < resourceNames.length; i++) {
			resources[i] = new ClassPathResource(resourceNames[i], relativeClass);
		}
		this.load(resources);
	}

}
