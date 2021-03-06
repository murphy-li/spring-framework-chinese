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

package org.springframework.messaging.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * Operations for receiving messages from a destination.
 *
 * @author Mark Fisher
 * @author Rossen Stoyanchev
 * @since 4.0
 * @param <D> the type of destination
 * @see GenericMessagingTemplate
 */
/**
 * 从目的地接收消息的操作。 
 *  @author  Mark Fisher @author  Rossen Stoyanchev @从4.0起
 * @param  <D>目标类型
 * @see  GenericMessagingTemplate
 */
public interface MessageReceivingOperations<D> {

	/**
	 * Receive a message from a default destination.
	 * @return the received message, possibly {@code null} if the message could not
	 * be received, for example due to a timeout
	 */
	/**
	 * 接收来自默认目的地的消息。 
	 *  
	 * @return 接收到的消息，如果由于超时等原因无法接收到消息，则可能为{@code  null}
	 */
	@Nullable
	Message<?> receive() throws MessagingException;

	/**
	 * Receive a message from the given destination.
	 * @param destination the target destination
	 * @return the received message, possibly {@code null} if the message could not
	 * be received, for example due to a timeout
	 */
	/**
	 * 接收来自给定目的地的消息。 
	 *  
	 * @param 目的地目标目的地
	 * @return 接收到的消息，如果由于超时等原因而无法接收到消息，则可能为{@code  null}
	 */
	@Nullable
	Message<?> receive(D destination) throws MessagingException;

	/**
	 * Receive a message from a default destination and convert its payload to the
	 * specified target class.
	 * @param targetClass the target class to convert the payload to
	 * @return the converted payload of the reply message, possibly {@code null} if
	 * the message could not be received, for example due to a timeout
	 */
	/**
	 * 从默认目标接收消息，并将其有效负载转换为指定的目标类。 
	 *  
	 * @param  targetClass目标类，用于将有效载荷转换为
	 * @return 转换后的回复消息的有效载荷，如果由于超时等原因无法接收到消息，则可能为{@code  null}
	 */
	@Nullable
	<T> T receiveAndConvert(Class<T> targetClass) throws MessagingException;

	/**
	 * Receive a message from the given destination and convert its payload to the
	 * specified target class.
	 * @param destination the target destination
	 * @param targetClass the target class to convert the payload to
	 * @return the converted payload of the reply message, possibly {@code null} if
	 * the message could not be received, for example due to a timeout
	 */
	/**
	 * 接收来自给定目标的消息，并将其有效负载转换为指定的目标类。 
	 *  
	 * @param 目的地目标目的地
	 * @param  targetClass将有效负载转换为
	 * @return 回复消息的已转换有效负载的目标类，如果无法接收到该消息，则可能为{@code  null}由于超时的示例
	 */
	@Nullable
	<T> T receiveAndConvert(D destination, Class<T> targetClass) throws MessagingException;

}
