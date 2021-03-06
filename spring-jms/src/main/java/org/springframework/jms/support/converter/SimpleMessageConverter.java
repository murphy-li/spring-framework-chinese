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

package org.springframework.jms.support.converter;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.util.ObjectUtils;

/**
 * A simple message converter which is able to handle TextMessages, BytesMessages,
 * MapMessages, and ObjectMessages. Used as default conversion strategy
 * by {@link org.springframework.jms.core.JmsTemplate}, for
 * {@code convertAndSend} and {@code receiveAndConvert} operations.
 *
 * <p>Converts a String to a {@link javax.jms.TextMessage}, a byte array to a
 * {@link javax.jms.BytesMessage}, a Map to a {@link javax.jms.MapMessage}, and
 * a Serializable object to a {@link javax.jms.ObjectMessage} (or vice versa).
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see org.springframework.jms.core.JmsTemplate#convertAndSend
 * @see org.springframework.jms.core.JmsTemplate#receiveAndConvert
 */
/**
 * 一个简单的消息转换器，能够处理TextMessages，BytesMessages，MapMessages和ObjectMessages。 
 *  {@link  org.springframework.jms.core.JmsTemplate}用作默认转换策略，用于{@code  convertAndSend}和{@code  receiveAndConvert}操作。 
 *  <p>将字符串转换为{@link  javax.jms.TextMessage}，将字节数组转换为{@link  javax.jms.BytesMessage}，将映射转换为{@link  javax.jms。 
 *  MapMessage}，以及指向{@link  javax.jms.ObjectMessage}的Serializable对象（反之亦然）。 
 *  @author  Juergen Hoeller @since 1.1起
 * @see  org.springframework.jms.core.JmsTemplate＃convertAndSend 
 * @see  org.springframework.jms.core.JmsTemplate＃receiveAndConvert
 */
public class SimpleMessageConverter implements MessageConverter {

	/**
	 * This implementation creates a TextMessage for a String, a
	 * BytesMessage for a byte array, a MapMessage for a Map,
	 * and an ObjectMessage for a Serializable object.
	 * @see #createMessageForString
	 * @see #createMessageForByteArray
	 * @see #createMessageForMap
	 * @see #createMessageForSerializable
	 */
	/**
	 * 此实现为字符串创建TextMessage，为字节数组创建BytesMessage，为Map创建MapMessage，并为Serializable对象创建ObjectMessage。 
	 *  
	 * @see  #createMessageForString 
	 * @see  #createMessageForByteArray 
	 * @see  #createMessageForMap 
	 * @see  #createMessageForSerializable
	 */
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		if (object instanceof Message) {
			return (Message) object;
		}
		else if (object instanceof String) {
			return createMessageForString((String) object, session);
		}
		else if (object instanceof byte[]) {
			return createMessageForByteArray((byte[]) object, session);
		}
		else if (object instanceof Map) {
			return createMessageForMap((Map<? ,?>) object, session);
		}
		else if (object instanceof Serializable) {
			return createMessageForSerializable(((Serializable) object), session);
		}
		else {
			throw new MessageConversionException("Cannot convert object of type [" +
					ObjectUtils.nullSafeClassName(object) + "] to JMS message. Supported message " +
					"payloads are: String, byte array, Map<String,?>, Serializable object.");
		}
	}

	/**
	 * This implementation converts a TextMessage back to a String, a
	 * ByteMessage back to a byte array, a MapMessage back to a Map,
	 * and an ObjectMessage back to a Serializable object. Returns
	 * the plain Message object in case of an unknown message type.
	 * @see #extractStringFromMessage
	 * @see #extractByteArrayFromMessage
	 * @see #extractMapFromMessage
	 * @see #extractSerializableFromMessage
	 */
	/**
	 * 此实现将TextMessage转换回字符串，将ByteMessage转换回字节数组，将MapMessage转换回Map，将ObjectMessage转换回Serializable对象。 
	 * 如果消息类型未知，则返回普通的Message对象。 
	 *  
	 * @see  #extractStringFromMessage 
	 * @see  #extractByteArrayFromMessage 
	 * @see  #extractMapFromMessage 
	 * @see  #extractSerializableFromMessage
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		if (message instanceof TextMessage) {
			return extractStringFromMessage((TextMessage) message);
		}
		else if (message instanceof BytesMessage) {
			return extractByteArrayFromMessage((BytesMessage) message);
		}
		else if (message instanceof MapMessage) {
			return extractMapFromMessage((MapMessage) message);
		}
		else if (message instanceof ObjectMessage) {
			return extractSerializableFromMessage((ObjectMessage) message);
		}
		else {
			return message;
		}
	}


	/**
	 * Create a JMS TextMessage for the given String.
	 * @param text the String to convert
	 * @param session current JMS session
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @see javax.jms.Session#createTextMessage
	 */
	/**
	 * 为给定的字符串创建一个JMS TextMessage。 
	 *  
	 * @param 将要转换的字符串文本化为
	 * @param 会话当前的JMS会话
	 * @return 结果消息
	 * @throws  JMSException（如果由JMS方法抛出）
	 * @see  javax.jms.Session＃createTextMessage
	 */
	protected TextMessage createMessageForString(String text, Session session) throws JMSException {
		return session.createTextMessage(text);
	}

	/**
	 * Create a JMS BytesMessage for the given byte array.
	 * @param bytes the byte array to convert
	 * @param session current JMS session
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @see javax.jms.Session#createBytesMessage
	 */
	/**
	 * 为给定的字节数组创建一个JMS BytesMessage。 
	 *  
	 * @param 字节，用于转换的字节数组
	 * @param 会话当前JMS会话
	 * @return 结果消息
	 * @throws  JMSException（如果由JMS方法抛出）
	 * @see  javax.jms.Session＃createBytesMessage
	 */
	protected BytesMessage createMessageForByteArray(byte[] bytes, Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(bytes);
		return message;
	}

	/**
	 * Create a JMS MapMessage for the given Map.
	 * @param map the Map to convert
	 * @param session current JMS session
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @see javax.jms.Session#createMapMessage
	 */
	/**
	 * 为给定的Map创建一个JMS MapMessage。 
	 *  
	 * @param 映射该映射以转换
	 * @param 会话当前JMS会话
	 * @return 结果消息
	 * @throws  JMSException（如果由JMS方法抛出）
	 * @see  javax.jms.Session＃createMapMessage
	 */
	protected MapMessage createMessageForMap(Map<?, ?> map, Session session) throws JMSException {
		MapMessage message = session.createMapMessage();
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			if (!(key instanceof String)) {
				throw new MessageConversionException("Cannot convert non-String key of type [" +
						ObjectUtils.nullSafeClassName(key) + "] to JMS MapMessage entry");
			}
			message.setObject((String) key, entry.getValue());
		}
		return message;
	}

	/**
	 * Create a JMS ObjectMessage for the given Serializable object.
	 * @param object the Serializable object to convert
	 * @param session current JMS session
	 * @return the resulting message
	 * @throws JMSException if thrown by JMS methods
	 * @see javax.jms.Session#createObjectMessage
	 */
	/**
	 * 为给定的Serializable对象创建一个JMS ObjectMessage。 
	 *  
	 * @param 对象可序列化的对象，用于转换
	 * @param 会话当前JMS会话
	 * @return 结果消息
	 * @throws  JMSException（如果由JMS方法抛出）
	 * @see  javax.jms.Session＃createObjectMessage
	 */
	protected ObjectMessage createMessageForSerializable(Serializable object, Session session) throws JMSException {
		return session.createObjectMessage(object);
	}


	/**
	 * Extract a String from the given TextMessage.
	 * @param message the message to convert
	 * @return the resulting String
	 * @throws JMSException if thrown by JMS methods
	 */
	/**
	 * 从给定的TextMessage中提取一个字符串。 
	 *  
	 * @param 消息，消息将转换为
	 * @return 结果字符串
	 * @throws  JMSException（如果由JMS方法抛出）
	 */
	protected String extractStringFromMessage(TextMessage message) throws JMSException {
		return message.getText();
	}

	/**
	 * Extract a byte array from the given {@link BytesMessage}.
	 * @param message the message to convert
	 * @return the resulting byte array
	 * @throws JMSException if thrown by JMS methods
	 */
	/**
	 * 从给定的{@link  BytesMessage}中提取字节数组。 
	 *  
	 * @param 消息将转换为
	 * @return 结果字节数组的消息
	 * @throws  JMSException（如果由JMS方法抛出）
	 */
	protected byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {
		byte[] bytes = new byte[(int) message.getBodyLength()];
		message.readBytes(bytes);
		return bytes;
	}

	/**
	 * Extract a Map from the given {@link MapMessage}.
	 * @param message the message to convert
	 * @return the resulting Map
	 * @throws JMSException if thrown by JMS methods
	 */
	/**
	 * 从给定的{@link  MapMessage}中提取地图。 
	 *  
	 * @param 消息，消息将转换为
	 * @return 结果映射
	 * @throws  JMSException（如果由JMS方法抛出）
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> extractMapFromMessage(MapMessage message) throws JMSException {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> en = message.getMapNames();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			map.put(key, message.getObject(key));
		}
		return map;
	}

	/**
	 * Extract a Serializable object from the given {@link ObjectMessage}.
	 * @param message the message to convert
	 * @return the resulting Serializable object
	 * @throws JMSException if thrown by JMS methods
	 */
	/**
	 * 从给定的{@link  ObjectMessage}中提取可序列化的对象。 
	 *  
	 * @param 消息，消息将转换为
	 * @return 结果的可序列化对象
	 * @throws  JMSException（如果由JMS方法抛出）
	 */
	protected Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {
		return message.getObject();
	}

}
