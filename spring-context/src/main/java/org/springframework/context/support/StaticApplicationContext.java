/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.context.support;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;

/**
 * {@link org.springframework.context.ApplicationContext} implementation
 * which supports programmatic registration of beans and messages,
 * rather than reading bean definitions from external configuration sources.
 * Mainly useful for testing.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #registerSingleton
 * @see #registerPrototype
 * @see #registerBeanDefinition
 * @see #refresh
 */
/**
 * {@link  org.springframework.context.ApplicationContext}实现，它支持以编程方式注册bean和消息，而不是从外部配置源中读取bean定义。 
 * 主要用于测试。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller 
 * @see  #registerSingleton 
 * @see  #registerPrototype 
 * @see  #registerBeanDefinition 
 * @see  #refresh
 */
public class StaticApplicationContext extends GenericApplicationContext {

	private final StaticMessageSource staticMessageSource;


	/**
	 * Create a new StaticApplicationContext.
	 * @see #registerSingleton
	 * @see #registerPrototype
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 创建一个新的StaticApplicationContext。 
	 *  
	 * @see  #registerSingleton 
	 * @see  #registerPrototype 
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public StaticApplicationContext() throws BeansException {
		this(null);
	}

	/**
	 * Create a new StaticApplicationContext with the given parent.
	 * @see #registerSingleton
	 * @see #registerPrototype
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 使用给定的父级创建一个新的StaticApplicationContext。 
	 *  
	 * @see  #registerSingleton 
	 * @see  #registerPrototype 
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public StaticApplicationContext(@Nullable ApplicationContext parent) throws BeansException {
		super(parent);

		// Initialize and register a StaticMessageSource.
		this.staticMessageSource = new StaticMessageSource();
		getBeanFactory().registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.staticMessageSource);
	}


	/**
	 * Overridden to turn it into a no-op, to be more lenient towards test cases.
	 */
	/**
	 * 重写以将其变为无操作，以更宽容地对待测试用例。 
	 * 
	 */
	@Override
	protected void assertBeanFactoryActive() {
	}

	/**
	 * Return the internal StaticMessageSource used by this context.
	 * Can be used to register messages on it.
	 * @see #addMessage
	 */
	/**
	 * 返回此上下文使用的内部StaticMessageSource。 
	 * 可用于在其上注册消息。 
	 *  
	 * @see  #addMessage
	 */
	public final StaticMessageSource getStaticMessageSource() {
		return this.staticMessageSource;
	}

	/**
	 * Register a singleton bean with the underlying bean factory.
	 * <p>For more advanced needs, register with the underlying BeanFactory directly.
	 * @see #getDefaultListableBeanFactory
	 */
	/**
	 * 向基础bean工厂注册一个singleton bean。 
	 *  <p>对于更高级的需求，请直接向基础BeanFactory注册。 
	 *  
	 * @see  #getDefaultListableBeanFactory
	 */
	public void registerSingleton(String name, Class<?> clazz) throws BeansException {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(clazz);
		getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
	}

	/**
	 * Register a singleton bean with the underlying bean factory.
	 * <p>For more advanced needs, register with the underlying BeanFactory directly.
	 * @see #getDefaultListableBeanFactory
	 */
	/**
	 * 向基础bean工厂注册一个singleton bean。 
	 *  <p>对于更高级的需求，请直接向基础BeanFactory注册。 
	 *  
	 * @see  #getDefaultListableBeanFactory
	 */
	public void registerSingleton(String name, Class<?> clazz, MutablePropertyValues pvs) throws BeansException {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(clazz);
		bd.setPropertyValues(pvs);
		getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
	}

	/**
	 * Register a prototype bean with the underlying bean factory.
	 * <p>For more advanced needs, register with the underlying BeanFactory directly.
	 * @see #getDefaultListableBeanFactory
	 */
	/**
	 * 向基础bean工厂注册原型bean。 
	 *  <p>对于更高级的需求，请直接向基础BeanFactory注册。 
	 *  
	 * @see  #getDefaultListableBeanFactory
	 */
	public void registerPrototype(String name, Class<?> clazz) throws BeansException {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		bd.setBeanClass(clazz);
		getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
	}

	/**
	 * Register a prototype bean with the underlying bean factory.
	 * <p>For more advanced needs, register with the underlying BeanFactory directly.
	 * @see #getDefaultListableBeanFactory
	 */
	/**
	 * 向基础bean工厂注册原型bean。 
	 *  <p>对于更高级的需求，请直接向基础BeanFactory注册。 
	 *  
	 * @see  #getDefaultListableBeanFactory
	 */
	public void registerPrototype(String name, Class<?> clazz, MutablePropertyValues pvs) throws BeansException {
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		bd.setBeanClass(clazz);
		bd.setPropertyValues(pvs);
		getDefaultListableBeanFactory().registerBeanDefinition(name, bd);
	}

	/**
	 * Associate the given message with the given code.
	 * @param code lookup code
	 * @param locale locale message should be found within
	 * @param defaultMessage message associated with this lookup code
	 * @see #getStaticMessageSource
	 */
	/**
	 * 将给定的消息与给定的代码相关联。 
	 *  
	 * @param 代码查找代码
	 * @param 区域设置区域消息应在与此查找代码关联的
	 * @param  defaultMessage消息中
	 * @see  #getStaticMessageSource
	 */
	public void addMessage(String code, Locale locale, String defaultMessage) {
		getStaticMessageSource().addMessage(code, locale, defaultMessage);
	}

}
