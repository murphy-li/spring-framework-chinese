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

package org.springframework.beans;

import org.springframework.lang.Nullable;

/**
 * Exception thrown when referring to an invalid bean property.
 * Carries the offending bean class and property name.
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 */
/**
 * 当引用无效的bean属性时引发异常。 
 * 携带有问题的bean类和属性名。 
 *  @author  Juergen Hoeller @始于1.0.2
 */
@SuppressWarnings("serial")
public class InvalidPropertyException extends FatalBeanException {

	private final Class<?> beanClass;

	private final String propertyName;


	/**
	 * Create a new InvalidPropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property
	 * @param msg the detail message
	 */
	/**
	 * 创建一个新的InvalidPropertyException。 
	 *  
	 * @param  beanClass有问题的bean类
	 * @param 属性为有问题的属性
	 * @param  msg详细消息
	 */
	public InvalidPropertyException(Class<?> beanClass, String propertyName, String msg) {
		this(beanClass, propertyName, msg, null);
	}

	/**
	 * Create a new InvalidPropertyException.
	 * @param beanClass the offending bean class
	 * @param propertyName the offending property
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	/**
	 * 创建一个新的InvalidPropertyException。 
	 *  
	 * @param  beanClass有问题的bean类
	 * @param  propertyName有问题的属性
	 * @param  msg详细消息
	 * @param 引起根本原因
	 */
	public InvalidPropertyException(Class<?> beanClass, String propertyName, String msg, @Nullable Throwable cause) {
		super("Invalid property '" + propertyName + "' of bean class [" + beanClass.getName() + "]: " + msg, cause);
		this.beanClass = beanClass;
		this.propertyName = propertyName;
	}

	/**
	 * Return the offending bean class.
	 */
	/**
	 * 返回有问题的bean类。 
	 * 
	 */
	public Class<?> getBeanClass() {
		return this.beanClass;
	}

	/**
	 * Return the name of the offending property.
	 */
	/**
	 * 返回违规属性的名称。 
	 * 
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

}
