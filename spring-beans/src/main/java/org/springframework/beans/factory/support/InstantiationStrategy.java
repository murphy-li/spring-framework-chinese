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

package org.springframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.Nullable;

/**
 * Interface responsible for creating instances corresponding to a root bean definition.
 *
 * <p>This is pulled out into a strategy as various approaches are possible,
 * including using CGLIB to create subclasses on the fly to support Method Injection.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 1.1
 */
/**
 * 负责创建与根bean定义相对应的实例的接口。 
 *  <p>由于有多种可能的方法，因此将其引入策略中，包括使用CGLIB动态创建子类以支持方法注入。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @始于1.1
 */
public interface InstantiationStrategy {

	/**
	 * Return an instance of the bean with the given name in this factory.
	 * @param bd the bean definition
	 * @param beanName the name of the bean when it is created in this context.
	 * The name can be {@code null} if we are autowiring a bean which doesn't
	 * belong to the factory.
	 * @param owner the owning BeanFactory
	 * @return a bean instance for this bean definition
	 * @throws BeansException if the instantiation attempt failed
	 */
	/**
	 * 在此工厂中以给定名称返回Bean的实例。 
	 *  
	 * @param  bd bean定义
	 * @param  beanName在此上下文中创建时的bean名称。 
	 * 如果我们正在自动装配不属于工厂的bean，则名称可以为{@code  null}。 
	 *  
	 * @param 拥有拥有的BeanFactory的所有者
	 * @return 此bean定义的bean实例
	 * @throws  BeansException如果实例化尝试失败
	 */
	Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner)
			throws BeansException;

	/**
	 * Return an instance of the bean with the given name in this factory,
	 * creating it via the given constructor.
	 * @param bd the bean definition
	 * @param beanName the name of the bean when it is created in this context.
	 * The name can be {@code null} if we are autowiring a bean which doesn't
	 * belong to the factory.
	 * @param owner the owning BeanFactory
	 * @param ctor the constructor to use
	 * @param args the constructor arguments to apply
	 * @return a bean instance for this bean definition
	 * @throws BeansException if the instantiation attempt failed
	 */
	/**
	 * 返回该工厂中具有给定名称的Bean实例，通过给定的构造函数创建它。 
	 *  
	 * @param  bd bean定义
	 * @param  beanName在此上下文中创建时的bean名称。 
	 * 如果我们正在自动装配不属于工厂的bean，则名称可以为{@code  null}。 
	 *  
	 * @param 拥有所有者的BeanFactory 
	 * @param 构造函数使用
	 * @param  args构造函数参数为该Bean定义应用
	 * @return 一个bean实例
	 * @throws  BeansException如果实例化尝试失败
	 */
	Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner,
			Constructor<?> ctor, Object... args) throws BeansException;

	/**
	 * Return an instance of the bean with the given name in this factory,
	 * creating it via the given factory method.
	 * @param bd the bean definition
	 * @param beanName the name of the bean when it is created in this context.
	 * The name can be {@code null} if we are autowiring a bean which doesn't
	 * belong to the factory.
	 * @param owner the owning BeanFactory
	 * @param factoryBean the factory bean instance to call the factory method on,
	 * or {@code null} in case of a static factory method
	 * @param factoryMethod the factory method to use
	 * @param args the factory method arguments to apply
	 * @return a bean instance for this bean definition
	 * @throws BeansException if the instantiation attempt failed
	 */
	/**
	 * 在此工厂中返回具有给定名称的Bean实例，并通过给定的factory方法创建它。 
	 *  
	 * @param  bd bean定义
	 * @param  beanName在此上下文中创建时的bean名称。 
	 * 如果我们正在自动装配不属于工厂的bean，则名称可以为{@code  null}。 
	 *  
	 * @param 拥有所有者的BeanFactory 
	 * @param  factoryBean调用工厂方法的工厂bean实例，如果是静态工厂方法，则为{@code  null} 
	 * @param  factoryMethod要使用的工厂方法
	 * @param  args工厂方法参数，以在实例化尝试失败的情况下为该bean定义
	 * @throws  BeansException应用
	 * @return 一个bean实例
	 */
	Object instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner,
			@Nullable Object factoryBean, Method factoryMethod, Object... args)
			throws BeansException;

}
