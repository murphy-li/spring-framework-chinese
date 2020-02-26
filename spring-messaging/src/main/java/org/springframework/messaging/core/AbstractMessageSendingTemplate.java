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

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.util.Assert;

/**
 * Abstract base class for implementations of {@link MessageSendingOperations}.
 *
 * @author Mark Fisher
 * @author Rossen Stoyanchev
 * @author Stephane Nicoll
 * @since 4.0
 * @param <D> the destination type
 */
/**
 * {@link  MessageSendingOperations}的实现的抽象基类。 
 *  @author  Mark Fisher @author  Rossen Stoyanchev @author  Stephane Nicoll @始于4.0 
 * @param  <D>目标类型
 */
public abstract class AbstractMessageSendingTemplate<D> implements MessageSendingOperations<D> {

	/**
	 * Name of the header that can be set to provide further information
	 * (e.g. a {@code MethodParameter} instance) about the origin of the
	 * payload, to be taken into account as a conversion hint.
	 * @since 4.2
	 */
	/**
	 * 可以设置标头的名称，以提供有关有效负载来源的更多信息（例如{@code  MethodParameter}实例），并将其作为转换提示。 
	 *  @4.2起
	 */
	public static final String CONVERSION_HINT_HEADER = "conversionHint";


	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private D defaultDestination;

	private MessageConverter converter = new SimpleMessageConverter();


	/**
	 * Configure the default destination to use in send methods that don't have
	 * a destination argument. If a default destination is not configured, send methods
	 * without a destination argument will raise an exception if invoked.
	 */
	/**
	 * 配置默认目标以在没有目标参数的发送方法中使用。 
	 * 如果未配置默认目标，则没有目标参数的send方法将在调用时引发异常。 
	 * 
	 */
	public void setDefaultDestination(@Nullable D defaultDestination) {
		this.defaultDestination = defaultDestination;
	}

	/**
	 * Return the configured default destination.
	 */
	/**
	 * 返回配置的默认目标。 
	 * 
	 */
	@Nullable
	public D getDefaultDestination() {
		return this.defaultDestination;
	}

	/**
	 * Set the {@link MessageConverter} to use in {@code convertAndSend} methods.
	 * <p>By default, {@link SimpleMessageConverter} is used.
	 * @param messageConverter the message converter to use
	 */
	/**
	 * 设置{@link  MessageConverter}以在{@code  convertAndSend}方法中使用。 
	 *  <p>默认情况下，使用{@link  SimpleMessageConverter}。 
	 *  
	 * @param  messageConverter要使用的消息转换器
	 */
	public void setMessageConverter(MessageConverter messageConverter) {
		Assert.notNull(messageConverter, "MessageConverter must not be null");
		this.converter = messageConverter;
	}

	/**
	 * Return the configured {@link MessageConverter}.
	 */
	/**
	 * 返回已配置的{@link  MessageConverter}。 
	 * 
	 */
	public MessageConverter getMessageConverter() {
		return this.converter;
	}


	@Override
	public void send(Message<?> message) {
		send(getRequiredDefaultDestination(), message);
	}

	protected final D getRequiredDefaultDestination() {
		Assert.state(this.defaultDestination != null, "No 'defaultDestination' configured");
		return this.defaultDestination;
	}

	@Override
	public void send(D destination, Message<?> message) {
		doSend(destination, message);
	}

	protected abstract void doSend(D destination, Message<?> message);


	@Override
	public void convertAndSend(Object payload) throws MessagingException {
		convertAndSend(payload, null);
	}

	@Override
	public void convertAndSend(D destination, Object payload) throws MessagingException {
		convertAndSend(destination, payload, (Map<String, Object>) null);
	}

	@Override
	public void convertAndSend(D destination, Object payload, @Nullable Map<String, Object> headers)
			throws MessagingException {

		convertAndSend(destination, payload, headers, null);
	}

	@Override
	public void convertAndSend(Object payload, @Nullable MessagePostProcessor postProcessor)
			throws MessagingException {

		convertAndSend(getRequiredDefaultDestination(), payload, postProcessor);
	}

	@Override
	public void convertAndSend(D destination, Object payload, @Nullable MessagePostProcessor postProcessor)
			throws MessagingException {

		convertAndSend(destination, payload, null, postProcessor);
	}

	@Override
	public void convertAndSend(D destination, Object payload, @Nullable Map<String, Object> headers,
			@Nullable MessagePostProcessor postProcessor) throws MessagingException {

		Message<?> message = doConvert(payload, headers, postProcessor);
		send(destination, message);
	}

	/**
	 * Convert the given Object to serialized form, possibly using a
	 * {@link MessageConverter}, wrap it as a message with the given
	 * headers and apply the given post processor.
	 * @param payload the Object to use as payload
	 * @param headers headers for the message to send
	 * @param postProcessor the post processor to apply to the message
	 * @return the converted message
	 */
	/**
	 * 可以使用{@link  MessageConverter}将给定的Object转换为序列化形式，将其包装为带有给定的标头的消息，并应用给定的后处理器。 
	 *  
	 * @param 负载用作负载的对象
	 * @param 头标头，用于发送消息的消息头。 
	 * 
	 */
	protected Message<?> doConvert(Object payload, @Nullable Map<String, Object> headers,
			@Nullable MessagePostProcessor postProcessor) {

		MessageHeaders messageHeaders = null;
		Object conversionHint = (headers != null ? headers.get(CONVERSION_HINT_HEADER) : null);

		Map<String, Object> headersToUse = processHeadersToSend(headers);
		if (headersToUse != null) {
			if (headersToUse instanceof MessageHeaders) {
				messageHeaders = (MessageHeaders) headersToUse;
			}
			else {
				messageHeaders = new MessageHeaders(headersToUse);
			}
		}

		MessageConverter converter = getMessageConverter();
		Message<?> message = (converter instanceof SmartMessageConverter ?
				((SmartMessageConverter) converter).toMessage(payload, messageHeaders, conversionHint) :
				converter.toMessage(payload, messageHeaders));
		if (message == null) {
			String payloadType = payload.getClass().getName();
			Object contentType = (messageHeaders != null ? messageHeaders.get(MessageHeaders.CONTENT_TYPE) : null);
			throw new MessageConversionException("Unable to convert payload with type='" + payloadType +
					"', contentType='" + contentType + "', converter=[" + getMessageConverter() + "]");
		}
		if (postProcessor != null) {
			message = postProcessor.postProcessMessage(message);
		}
		return message;
	}

	/**
	 * Provides access to the map of input headers before a send operation.
	 * Subclasses can modify the headers and then return the same or a different map.
	 * <p>This default implementation in this class returns the input map.
	 * @param headers the headers to send (or {@code null} if none)
	 * @return the actual headers to send (or {@code null} if none)
	 */
	/**
	 * 在发送操作之前，提供对输入标题映射的访问。 
	 * 子类可以修改标题，然后返回相同或不同的映射。 
	 *  <p>此类中的默认实现返回输入映射。 
	 *  
	 * @param 标头要发送的标头（如果没有则为{@code  null}）
	 * @return 要发送的实际标头（如果没有则为{@code  null}）
	 */
	@Nullable
	protected Map<String, Object> processHeadersToSend(@Nullable Map<String, Object> headers) {
		return headers;
	}

}
