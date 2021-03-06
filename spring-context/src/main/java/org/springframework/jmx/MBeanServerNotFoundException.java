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

package org.springframework.jmx;

/**
 * Exception thrown when we cannot locate an instance of an {@code MBeanServer},
 * or when more than one instance is found.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.2
 * @see org.springframework.jmx.support.JmxUtils#locateMBeanServer
 */
/**
 * 当我们找不到{@code  MBeanServer}的实例时，或者发现多个实例时，抛出该异常。 
 *  @author  Rob Harrop @author  Juergen Hoeller @始于1.2 
 * @see  org.springframework.jmx.support.JmxUtils＃locateMBeanServer
 */
@SuppressWarnings("serial")
public class MBeanServerNotFoundException extends JmxException {

	/**
	 * Create a new {@code MBeanServerNotFoundException} with the
	 * supplied error message.
	 * @param msg the error message
	 */
	/**
	 * 使用提供的错误消息创建一个新的{@code  MBeanServerNotFoundException}。 
	 *  
	 * @param  msg错误消息
	 */
	public MBeanServerNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Create a new {@code MBeanServerNotFoundException} with the
	 * specified error message and root cause.
	 * @param msg the error message
	 * @param cause the root cause
	 */
	/**
	 * 使用指定的错误消息和根本原因创建一个新的{@code  MBeanServerNotFoundException}。 
	 *  
	 * @param  msg错误消息
	 * @param 引起根本原因
	 */
	public MBeanServerNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
