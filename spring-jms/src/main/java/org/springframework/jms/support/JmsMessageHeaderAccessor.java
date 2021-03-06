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

package org.springframework.jms.support;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;

/**
 * A {@link org.springframework.messaging.support.MessageHeaderAccessor}
 * implementation giving access to JMS-specific headers.
 *
 * @author Stephane Nicoll
 * @since 4.1
 */
/**
 * 一个{@link  org.springframework.messaging.support.MessageHeaderAccessor}实现，可以访问特定于JMS的标头。 
 *  @author 史蒂芬·尼科尔@since 4.1
 */
public class JmsMessageHeaderAccessor extends NativeMessageHeaderAccessor {

	protected JmsMessageHeaderAccessor(Map<String, List<String>> nativeHeaders) {
		super(nativeHeaders);
	}

	protected JmsMessageHeaderAccessor(Message<?> message) {
		super(message);
	}


	/**
	 * Return the {@link JmsHeaders#CORRELATION_ID correlationId}.
	 * @see JmsHeaders#CORRELATION_ID
	 */
	/**
	 * 返回{@link  JmsHeaders＃CORRELATION_ID relatedId}。 
	 *  
	 * @see  JmsHeaders＃CORRELATION_ID
	 */
	@Nullable
	public String getCorrelationId() {
		return (String) getHeader(JmsHeaders.CORRELATION_ID);
	}

	/**
	 * Return the {@link JmsHeaders#DESTINATION destination}.
	 * @see JmsHeaders#DESTINATION
	 */
	/**
	 * 返回{@link  JmsHeaders＃DESTINATION目标}。 
	 *  
	 * @see  JmsHeaders＃DESTINATION
	 */
	@Nullable
	public Destination getDestination() {
		return (Destination) getHeader(JmsHeaders.DESTINATION);
	}

	/**
	 * Return the {@link JmsHeaders#DELIVERY_MODE delivery mode}.
	 * @see JmsHeaders#DELIVERY_MODE
	 */
	/**
	 * 返回{@link  JmsHeaders＃DELIVERY_MODE传递模式}。 
	 *  
	 * @see  JmsHeaders＃DELIVERY_MODE
	 */
	@Nullable
	public Integer getDeliveryMode() {
		return (Integer) getHeader(JmsHeaders.DELIVERY_MODE);
	}

	/**
	 * Return the message {@link JmsHeaders#EXPIRATION expiration}.
	 * @see JmsHeaders#EXPIRATION
	 */
	/**
	 * 返回消息{@link  JmsHeaders＃EXPIRATION到期}。 
	 *  
	 * @see  JmsHeaders＃EXPIRATION
	 */
	@Nullable
	public Long getExpiration() {
		return (Long) getHeader(JmsHeaders.EXPIRATION);
	}

	/**
	 * Return the {@link JmsHeaders#MESSAGE_ID message id}.
	 * @see JmsHeaders#MESSAGE_ID
	 */
	/**
	 * 返回{@link  JmsHeaders＃MESSAGE_ID消息ID}。 
	 *  
	 * @see  JmsHeaders＃MESSAGE_ID
	 */
	@Nullable
	public String getMessageId() {
		return (String) getHeader(JmsHeaders.MESSAGE_ID);
	}

	/**
	 * Return the {@link JmsHeaders#PRIORITY priority}.
	 * @see JmsHeaders#PRIORITY
	 */
	/**
	 * 返回{@link  JmsHeaders＃PRIORITY优先级}。 
	 *  
	 * @see  JmsHeaders＃PRIORITY
	 */
	@Nullable
	public Integer getPriority() {
		return (Integer) getHeader(JmsHeaders.PRIORITY);
	}

	/**
	 * Return the {@link JmsHeaders#REPLY_TO reply to}.
	 * @see JmsHeaders#REPLY_TO
	 */
	/**
	 * 返回{@link  JmsHeaders＃REPLY_TO答复}。 
	 *  
	 * @see  JmsHeaders＃REPLY_TO
	 */
	@Nullable
	public Destination getReplyTo() {
		return (Destination) getHeader(JmsHeaders.REPLY_TO);
	}

	/**
	 * Return the {@link JmsHeaders#REDELIVERED redelivered} flag.
	 * @see JmsHeaders#REDELIVERED
	 */
	/**
	 * 返回{@link  JmsHeaders＃REDELIVERED redelivered}标志。 
	 *  
	 * @see  JmsHeaders＃REDELIVERED
	 */
	@Nullable
	public Boolean getRedelivered() {
		return (Boolean) getHeader(JmsHeaders.REDELIVERED);
	}

	/**
	 * Return the {@link JmsHeaders#TYPE type}.
	 * @see JmsHeaders#TYPE
	 */
	/**
	 * 返回{@link  JmsHeaders＃TYPE type}。 
	 *  
	 * @see  JmsHeaders＃TYPE
	 */
	@Nullable
	public String getType() {
		return (String) getHeader(JmsHeaders.TYPE);
	}

	/**
	 * Return the {@link JmsHeaders#TIMESTAMP timestamp}.
	 * @see JmsHeaders#TIMESTAMP
	 */
	/**
	 * 返回{@link  JmsHeaders＃TIMESTAMP时间戳记}。 
	 *  
	 * @see  JmsHeaders＃TIMESTAMP
	 */
	@Override
	@Nullable
	public Long getTimestamp() {
		return (Long) getHeader(JmsHeaders.TIMESTAMP);
	}


	// Static factory method

	/**
	 * Create a {@link JmsMessageHeaderAccessor} from the headers of an existing message.
	 */
	/**
	 * 从现有消息的标题中创建一个{@link  JmsMessageHeaderAccessor}。 
	 * 
	 */
	public static JmsMessageHeaderAccessor wrap(Message<?> message) {
		return new JmsMessageHeaderAccessor(message);
	}

}
