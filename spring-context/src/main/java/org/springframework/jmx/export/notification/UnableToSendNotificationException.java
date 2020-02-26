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

package org.springframework.jmx.export.notification;

import org.springframework.jmx.JmxException;

/**
 * Thrown when a JMX {@link javax.management.Notification} is unable to be sent.
 *
 * <p>The root cause of just why a particular notification could not be sent
 * will <i>typically</i> be available via the {@link #getCause()} property.
 *
 * @author Rob Harrop
 * @since 2.0
 * @see NotificationPublisher
 */
/**
 * 无法发送JMX {@link  javax.management.Notification}时抛出。 
 *  <p>为什么无法发送特定通知的根本原因通常是通过{@link  #getCause（）}属性提供的。 
 *  @author 罗布·哈罗普（Rob Harrop）@从2.0开始
 * @see  NotificationPublisher
 */
@SuppressWarnings("serial")
public class UnableToSendNotificationException extends JmxException {

	/**
	 * Create a new instance of the {@link UnableToSendNotificationException}
	 * class with the specified error message.
	 * @param msg the detail message
	 */
	/**
	 * 使用指定的错误消息创建{@link  UnableToSendNotificationException}类的新实例。 
	 *  
	 * @param  msg详细信息
	 */
	public UnableToSendNotificationException(String msg) {
		super(msg);
	}

	/**
	 * Create a new instance of the {@link UnableToSendNotificationException}
	 * with the specified error message and root cause.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	/**
	 * 使用指定的错误消息和根本原因创建{@link  UnableToSendNotificationException}的新实例。 
	 *  
	 * @param  msg详细消息
	 * @param 引起根本原因
	 */
	public UnableToSendNotificationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
