/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.config;

import org.springframework.lang.Nullable;

/**
 * Interface that defines a registry for shared bean instances.
 * Can be implemented by {@link org.springframework.beans.factory.BeanFactory}
 * implementations in order to expose their singleton management facility
 * in a uniform manner.
 *
 * <p>The {@link ConfigurableBeanFactory} interface extends this interface.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see ConfigurableBeanFactory
 * @see org.springframework.beans.factory.support.DefaultSingletonBeanRegistry
 * @see org.springframework.beans.factory.support.AbstractBeanFactory
 */
/**
 * 为共享bean实例定义注册表的接口。 
 * 可以由{@link  org.springframework.beans.factory.BeanFactory}实现实现，以便以统一的方式公开其单例管理工具。 
 *  <p> {<@link> ConfigurableBeanFactory}接口扩展了此接口。 
 *  @author  Juergen Hoeller @since 2.0起
 * @see  ConfigurableBeanFactory 
 * @see  org.springframework.beans.factory.support.DefaultSingletonBeanRegistry 
 * @see  org.springframework.beans.factory.support.AbstractBeanFactory
 */
public interface SingletonBeanRegistry {

	/**
	 * Register the given existing object as singleton in the bean registry,
	 * under the given bean name.
	 * <p>The given instance is supposed to be fully initialized; the registry
	 * will not perform any initialization callbacks (in particular, it won't
	 * call InitializingBean's {@code afterPropertiesSet} method).
	 * The given instance will not receive any destruction callbacks
	 * (like DisposableBean's {@code destroy} method) either.
	 * <p>When running within a full BeanFactory: <b>Register a bean definition
	 * instead of an existing instance if your bean is supposed to receive
	 * initialization and/or destruction callbacks.</b>
	 * <p>Typically invoked during registry configuration, but can also be used
	 * for runtime registration of singletons. As a consequence, a registry
	 * implementation should synchronize singleton access; it will have to do
	 * this anyway if it supports a BeanFactory's lazy initialization of singletons.
	 * @param beanName the name of the bean
	 * @param singletonObject the existing singleton object
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 * @see org.springframework.beans.factory.DisposableBean#destroy
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition
	 */
	/**
	 * 在给定的bean名称下，在bean注册表中将给定的现有对象注册为单例。 
	 *  <p>该给定实例应该被完全初始化； 
	 * 该注册表将不执行任何初始化回调（特别是，它不会调用InitializingBean的{@code  afterPropertiesSet}方法）。 
	 * 给定的实例也不会接收任何销毁回调（例如DisposableBean的{@code  destroy}方法）。 
	 *  <p>在完整的BeanFactory中运行时：<b>如果您的bean应该接收初始化和/或销毁回调，请注册一个bean定义而不是现有实例。 
	 * </ b> <p>通常在注册表配置期间调用，但是也可以用于单例的运行时注册。 
	 * 因此，注册表实现应同步单例访问。 
	 * 如果它支持BeanFactory的单例延迟初始化，则无论如何都必须这样做。 
	 *  
	 * @param  beanName Bean的名称
	 * @param  singletonObject 现有的单例对象
	 * @see  org.springframework.beans.factory.InitializingBean＃afterPropertiesSet 
	 * @see  org.springframework.beans.factory.DisposableBean＃destroy 
	 * @see  org.springframework.beans.factory.support.BeanDefinitionRegistry＃registerBeanDefinition
	 */
	void registerSingleton(String beanName, Object singletonObject);

	/**
	 * Return the (raw) singleton object registered under the given name.
	 * <p>Only checks already instantiated singletons; does not return an Object
	 * for singleton bean definitions which have not been instantiated yet.
	 * <p>The main purpose of this method is to access manually registered singletons
	 * (see {@link #registerSingleton}). Can also be used to access a singleton
	 * defined by a bean definition that already been created, in a raw fashion.
	 * <p><b>NOTE:</b> This lookup method is not aware of FactoryBean prefixes or aliases.
	 * You need to resolve the canonical bean name first before obtaining the singleton instance.
	 * @param beanName the name of the bean to look for
	 * @return the registered singleton object, or {@code null} if none found
	 * @see ConfigurableListableBeanFactory#getBeanDefinition
	 */
	/**
	 * 返回以给定名称注册的（原始）单例对象。 
	 *  <p>仅检查已实例化的单例； 
	 * 对于尚未实例化的单例 bean definitions，不返回Object。
	 *  <p>此方法的主要目的是访问手动注册的单例（请参阅{@link  #registerSingleton}）。 
	 * 也可以用于以原始方式访问由已创建的bean定义定义的单例。 
	 *  <p> <b>注意：</ b>此查找方法无法识别FactoryBean前缀或别名。 
	 * 在获取单例实例之前，您需要首先解析规范的bean名称。 
	 *  
	 * @param  beanName要查找的豆的名称
	 * @return 注册的单例对象； 
	 * 如果找不到，则为{@code  null} 
	 * @see  ConfigurableListableBeanFactory＃getBeanDefinition
	 */
	@Nullable
	Object getSingleton(String beanName);

	/**
	 * Check if this registry contains a singleton instance with the given name.
	 * <p>Only checks already instantiated singletons; does not return {@code true}
	 * for singleton bean definitions which have not been instantiated yet.
	 * <p>The main purpose of this method is to check manually registered singletons
	 * (see {@link #registerSingleton}). Can also be used to check whether a
	 * singleton defined by a bean definition has already been created.
	 * <p>To check whether a bean factory contains a bean definition with a given name,
	 * use ListableBeanFactory's {@code containsBeanDefinition}. Calling both
	 * {@code containsBeanDefinition} and {@code containsSingleton} answers
	 * whether a specific bean factory contains a local bean instance with the given name.
	 * <p>Use BeanFactory's {@code containsBean} for general checks whether the
	 * factory knows about a bean with a given name (whether manually registered singleton
	 * instance or created by bean definition), also checking ancestor factories.
	 * <p><b>NOTE:</b> This lookup method is not aware of FactoryBean prefixes or aliases.
	 * You need to resolve the canonical bean name first before checking the singleton status.
	 * @param beanName the name of the bean to look for
	 * @return if this bean factory contains a singleton instance with the given name
	 * @see #registerSingleton
	 * @see org.springframework.beans.factory.ListableBeanFactory#containsBeanDefinition
	 * @see org.springframework.beans.factory.BeanFactory#containsBean
	 */
	/**
	 * 检查此注册表是否包含具有给定名称的单例实例。 
	 *  <p>仅检查已实例化的单例； 
	 * 对于尚未实例化的单例bean定义，不返回{@code  true}。 
	 *  <p>此方法的主要目的是检查手动注册的单例（请参阅{@link  #registerSingleton}）。 
	 * 也可以用于检查是否已创建由bean定义定义的单例。 
	 *  <p>要检查bean工厂是否包含具有给定名称的bean定义，请使用ListableBeanFactory的{@code  containsBeanDefinition}。 
	 * 调用{@code  containsBeanDefinition}和{@code  containsSingleton}都会回答特定的bean工厂是否包含具有给定名称的本地bean实例。 
	 *  <p>使用BeanFactory的{@code  containsBean}进行常规检查，以检查工厂是否知道具有给定名称的bean（无论是手动注册的单例实例还是由bean定义创建的），还检查祖先工厂。 
	 *  <p> <b>注意：</ b>此查找方法无法识别FactoryBean前缀或别名。 
	 * 在检查单例状态之前，您需要首先解析规范的bean名称。 
	 *  
	 * @param beanName 如果此bean工厂包含具有给定名称的单例实例，则要查找的bean名称。
	 * 
	 * @see  #registerSingleton 
	 * @see  org.springframework.beans.factory.ListableBeanFactory＃containsBeanDefinition 
	 * @see  org.springframework.beans.factory.BeanFactory＃containsBean
	 */
	boolean containsSingleton(String beanName);

	/**
	 * Return the names of singleton beans registered in this registry.
	 * <p>Only checks already instantiated singletons; does not return names
	 * for singleton bean definitions which have not been instantiated yet.
	 * <p>The main purpose of this method is to check manually registered singletons
	 * (see {@link #registerSingleton}). Can also be used to check which singletons
	 * defined by a bean definition have already been created.
	 * @return the list of names as a String array (never {@code null})
	 * @see #registerSingleton
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinitionNames
	 * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionNames
	 */
	/**
	 * 返回在此注册表中注册的单例bean的名称。 
	 *  <p>仅检查已实例化的单例； 
	 * 不返回尚未实例化的单例bean定义的名称。 
	 *  <p>此方法的主要目的是检查手动注册的单例（请参阅{@link  #registerSingleton}）。 
	 * 也可以用来检查由bean定义定义的单例已经创建。 
	 *  
	 * @return 作为字符串数组的名称列表（从不{<@@code> null}）
	 * @see  #registerSingleton 
	 * @see  org.springframework.beans.factory.support.BeanDefinitionRegistry＃getBeanDefinitionNames 
	 * @see  org .springframework.beans.factory.ListableBeanFactory＃getBeanDefinitionNames
	 */
	String[] getSingletonNames();

	/**
	 * Return the number of singleton beans registered in this registry.
	 * <p>Only checks already instantiated singletons; does not count
	 * singleton bean definitions which have not been instantiated yet.
	 * <p>The main purpose of this method is to check manually registered singletons
	 * (see {@link #registerSingleton}). Can also be used to count the number of
	 * singletons defined by a bean definition that have already been created.
	 * @return the number of singleton beans
	 * @see #registerSingleton
	 * @see org.springframework.beans.factory.support.BeanDefinitionRegistry#getBeanDefinitionCount
	 * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionCount
	 */
	/**
	 * 返回在此注册表中注册的单例bean的数量。 
	 *  <p>仅检查已实例化的单例； 
	 * 不计算尚未实例化的单例bean定义。 
	 *  <p>此方法的主要目的是检查手动注册的单例（请参阅{@link  #registerSingleton}）。 
	 * 也可以用来计算已经由bean definition定义的单例的数量。
	 *  
	 * @return 单例bean的数量
	 * @see  #registerSingleton 
	 * @see  org.springframework.beans.factory.support.BeanDefinitionRegistry＃getBeanDefinitionCount 
	 * @see  org.springframework.beans.factory.ListableBeanFactory＃getBeanDefinitionCount
	 */
	int getSingletonCount();

	/**
	 * Return the singleton mutex used by this registry (for external collaborators).
	 * @return the mutex object (never {@code null})
	 * @since 4.2
	 */
	/**
	 * 返回此注册表使用的单例mutex（对于外部协作者）。
	 *  
	 * @return 互斥对象（永不返回{@code  null}）@自4.2起
	 */
	Object getSingletonMutex();

}
