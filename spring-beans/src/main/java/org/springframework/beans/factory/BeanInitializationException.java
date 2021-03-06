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

package org.springframework.beans.factory;

import org.springframework.beans.FatalBeanException;

/**
 * Exception that a bean implementation is suggested to throw if its own
 * factory-aware initialization code fails. BeansExceptions thrown by
 * bean factory methods themselves should simply be propagated as-is.
 *
 * <p>Note that {@code afterPropertiesSet()} or a custom "init-method"
 * can throw any exception.
 *
 * @author Juergen Hoeller
 * @since 13.11.2003
 * @see BeanFactoryAware#setBeanFactory
 * @see InitializingBean#afterPropertiesSet
 */
/**
 * 如果bean实现自身的工厂感知初始化代码失败，则建议抛出该bean实现的异常。 
 *  Bean工厂方法本身抛出的BeansExceptions应该仅按原样传播。 
 *  <p>请注意，{<@code> afterPropertiesSet（）}或自定义的"初始化方法"可能会引发任何异常。 
 *  @author  Juergen Hoeller @2003年11月13日以来
 * @see  BeanFactoryAware＃setBeanFactory 
 * @see  InitializingBean＃afterPropertiesSet
 */
@SuppressWarnings("serial")
public class BeanInitializationException extends FatalBeanException {

	/**
	 * Create a new BeanInitializationException with the specified message.
	 * @param msg the detail message
	 */
	/**
	 * 使用指定的消息创建一个新的BeanInitializationException。 
	 *  
	 * @param  msg详细信息
	 */
	public BeanInitializationException(String msg) {
		super(msg);
	}

	/**
	 * Create a new BeanInitializationException with the specified message
	 * and root cause.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	/**
	 * 使用指定的消息和根本原因创建一个新的BeanInitializationException。 
	 *  
	 * @param  msg详细消息
	 * @param 引起根本原因
	 */
	public BeanInitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
