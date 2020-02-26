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

package org.springframework.beans.factory;

import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;

/**
 * Exception thrown when a BeanFactory encounters an invalid bean definition:
 * e.g. in case of incomplete or contradictory bean metadata.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 */
/**
 * 当BeanFactory遇到无效的Bean定义时引发的异常：如果bean元数据不完整或自相矛盾。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @author  Rob Harrop
 */
@SuppressWarnings("serial")
public class BeanDefinitionStoreException extends FatalBeanException {

	@Nullable
	private final String resourceDescription;

	@Nullable
	private final String beanName;


	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param msg the detail message (used as exception message as-is)
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  msg详细消息（按原样用作异常消息）
	 */
	public BeanDefinitionStoreException(String msg) {
		super(msg);
		this.resourceDescription = null;
		this.beanName = null;
	}

	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param msg the detail message (used as exception message as-is)
	 * @param cause the root cause (may be {@code null})
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  msg详细消息（按原样用作异常消息）
	 * @param 引起根本原因（可能为{@code  null}）
	 */
	public BeanDefinitionStoreException(String msg, @Nullable Throwable cause) {
		super(msg, cause);
		this.resourceDescription = null;
		this.beanName = null;
	}

	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param resourceDescription description of the resource that the bean definition came from
	 * @param msg the detail message (used as exception message as-is)
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  resource Bean定义来自
	 * @param  msg的资源的描述msg详细消息（按原样用作异常消息）
	 */
	public BeanDefinitionStoreException(@Nullable String resourceDescription, String msg) {
		super(msg);
		this.resourceDescription = resourceDescription;
		this.beanName = null;
	}

	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param resourceDescription description of the resource that the bean definition came from
	 * @param msg the detail message (used as exception message as-is)
	 * @param cause the root cause (may be {@code null})
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  resource Bean定义来自的资源的描述
	 * @param  msg详细消息（按原样用作异常消息）
	 * @param 引起根本原因（可能为{@code  null} ）
	 */
	public BeanDefinitionStoreException(@Nullable String resourceDescription, String msg, @Nullable Throwable cause) {
		super(msg, cause);
		this.resourceDescription = resourceDescription;
		this.beanName = null;
	}

	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param resourceDescription description of the resource that the bean definition came from
	 * @param beanName the name of the bean
	 * @param msg the detail message (appended to an introductory message that indicates
	 * the resource and the name of the bean)
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  resource Bean定义来自的资源的描述
	 * @param  beanName Bean的名称
	 * @param  msg详细消息（附加在介绍性消息中，用于指示资源和Bean的名称）
	 */
	public BeanDefinitionStoreException(@Nullable String resourceDescription, String beanName, String msg) {
		this(resourceDescription, beanName, msg, null);
	}

	/**
	 * Create a new BeanDefinitionStoreException.
	 * @param resourceDescription description of the resource that the bean definition came from
	 * @param beanName the name of the bean
	 * @param msg the detail message (appended to an introductory message that indicates
	 * the resource and the name of the bean)
	 * @param cause the root cause (may be {@code null})
	 */
	/**
	 * 创建一个新的BeanDefinitionStoreException。 
	 *  
	 * @param  resource Bean定义来自的资源的描述
	 * @param  beanName Bean的名称
	 * @param  msg详细消息（附加在介绍性消息中，用于指示资源和Bean的名称） 
	 * @param 原因的根本原因（可能是{@code  null}）
	 */
	public BeanDefinitionStoreException(
			@Nullable String resourceDescription, String beanName, String msg, @Nullable Throwable cause) {

		super("Invalid bean definition with name '" + beanName + "' defined in " + resourceDescription + ": " + msg,
				cause);
		this.resourceDescription = resourceDescription;
		this.beanName = beanName;
	}


	/**
	 * Return the description of the resource that the bean definition came from, if available.
	 */
	/**
	 * 返回bean定义来自的资源的描述（如果有）。 
	 * 
	 */
	@Nullable
	public String getResourceDescription() {
		return this.resourceDescription;
	}

	/**
	 * Return the name of the bean, if available.
	 */
	/**
	 * 返回bean的名称（如果有）。 
	 * 
	 */
	@Nullable
	public String getBeanName() {
		return this.beanName;
	}

}
