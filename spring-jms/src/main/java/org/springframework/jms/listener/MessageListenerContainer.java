/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jms.listener;

import org.springframework.context.SmartLifecycle;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;

/**
 * Internal abstraction used by the framework representing a message
 * listener container. Not meant to be implemented externally with
 * support for both JMS and JCA style containers.
 *
 * @author Stephane Nicoll
 * @since 4.1
 */
/**
 * 框架使用的内部抽象表示消息侦听器容器。 
 * 不打算在外部实现对JMS和JCA样式容器的支持。 
 *  @author 史蒂芬·尼科尔@since 4.1
 */
public interface MessageListenerContainer extends SmartLifecycle {

	/**
	 * Setup the message listener to use. Throws an {@link IllegalArgumentException}
	 * if that message listener type is not supported.
	 */
	/**
	 * 设置要使用的消息侦听器。 
	 * 如果不支持该消息侦听器类型，则抛出{@link  IllegalArgumentException}。 
	 * 
	 */
	void setupMessageListener(Object messageListener);

	/**
	 * Return the {@link MessageConverter} that can be used to
	 * convert {@link javax.jms.Message}, if any.
	 */
	/**
	 * 返回{@link  MessageConverter}（可用于转换{@link  javax.jms.Message}（如果有）。 
	 * 
	 */
	@Nullable
	MessageConverter getMessageConverter();

	/**
	 * Return the {@link DestinationResolver} to use to resolve
	 * destinations by names.
	 */
	/**
	 * 返回{@link  DestinationResolver}，用于按名称解析目标。 
	 * 
	 */
	@Nullable
	DestinationResolver getDestinationResolver();

	/**
	 * Return whether the Publish/Subscribe domain ({@link javax.jms.Topic Topics}) is used.
	 * Otherwise, the Point-to-Point domain ({@link javax.jms.Queue Queues}) is used.
	 */
	/**
	 * 返回是否使用发布/订阅域（{@link  javax.jms.Topic Topics}）。 
	 * 否则，将使用点对点域（{@link  javax.jms.Queue Queues}）。 
	 * 
	 */
	boolean isPubSubDomain();

	/**
	 * Return whether the reply destination uses Publish/Subscribe domain
	 * ({@link javax.jms.Topic Topics}). Otherwise, the Point-to-Point domain
	 * ({@link javax.jms.Queue Queues}) is used.
	 * <p>By default, the value is identical to {@link #isPubSubDomain()}.
	 */
	/**
	 * 返回回复目标是否使用发布/订阅域（{@link  javax.jms.Topic Topics}）。 
	 * 否则，将使用点对点域（{@link  javax.jms.Queue Queues}）。 
	 *  <p>默认情况下，该值与{@link  #isPubSubDomain（）}相同。 
	 * 
	 */
	boolean isReplyPubSubDomain();

	/**
	 * Return the {@link QosSettings} to use when sending a reply,
	 * or {@code null} if the broker's defaults should be used.
	 * @since 5.0
	 */
	/**
	 * 返回{@link  QosSettings}，以在发送回复时使用； 
	 * 如果应该使用代理的默认值，则返回{@code  null}。 
	 *  @5.0起
	 */
	@Nullable
	QosSettings getReplyQosSettings();

}
