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

package org.springframework.messaging.simp.annotation.support;

import java.lang.annotation.Annotation;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.DestinationVariableMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.DestinationUserNameProvider;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.StringUtils;

/**
 * A {@link HandlerMethodReturnValueHandler} for sending to destinations specified in a
 * {@link SendTo} or {@link SendToUser} method-level annotations.
 *
 * <p>The value returned from the method is converted, and turned to a {@link Message} and
 * sent through the provided {@link MessageChannel}. The message is then enriched with the
 * session id of the input message as well as the destination from the annotation(s).
 * If multiple destinations are specified, a copy of the message is sent to each destination.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 4.0
 */
/**
 * 一个{@link  HandlerMethodReturnValueHandler}，用于发送到在{@link  SendTo}或{@link  SendToUser}方法级注释中指定的目的地。 
 *  <p>从方法返回的值将被转换，并转换为{@link  Message}并通过提供的{@link  MessageChannel}发送。 
 * 然后，使用输入消息的会话ID以及注释中的目的地来丰富消息。 
 * 如果指定了多个目的地，则将消息的副本发送到每个目的地。 
 *  @author  Rossen Stoyanchev @author 塞巴斯蒂安·德勒兹@始于4.0
 */
public class SendToMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	private final SimpMessageSendingOperations messagingTemplate;

	private final boolean annotationRequired;

	private String defaultDestinationPrefix = "/topic";

	private String defaultUserDestinationPrefix = "/queue";

	private PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}", null, false);

	@Nullable
	private MessageHeaderInitializer headerInitializer;


	public SendToMethodReturnValueHandler(SimpMessageSendingOperations messagingTemplate, boolean annotationRequired) {
		Assert.notNull(messagingTemplate, "'messagingTemplate' must not be null");
		this.messagingTemplate = messagingTemplate;
		this.annotationRequired = annotationRequired;
	}


	/**
	 * Configure a default prefix to add to message destinations in cases where a method
	 * is not annotated with {@link SendTo @SendTo} or does not specify any destinations
	 * through the annotation's value attribute.
	 * <p>By default, the prefix is set to "/topic".
	 */
	/**
	 * 如果未使用{@link  SendTo @SendTo}注释方法或未通过注释的value属性指定任何目标，请配置默认前缀以添加到消息目标。 
	 *  <p>默认情况下，前缀设置为"/ topic"。 
	 * 
	 */
	public void setDefaultDestinationPrefix(String defaultDestinationPrefix) {
		this.defaultDestinationPrefix = defaultDestinationPrefix;
	}

	/**
	 * Return the configured default destination prefix.
	 * @see #setDefaultDestinationPrefix(String)
	 */
	/**
	 * 返回配置的默认目标前缀。 
	 *  
	 * @see  #setDefaultDestinationPrefix（String）
	 */
	public String getDefaultDestinationPrefix() {
		return this.defaultDestinationPrefix;
	}

	/**
	 * Configure a default prefix to add to message destinations in cases where a
	 * method is annotated with {@link SendToUser @SendToUser} but does not specify
	 * any destinations through the annotation's value attribute.
	 * <p>By default, the prefix is set to "/queue".
	 */
	/**
	 * 在使用{@link  SendToUser @SendToUser}注释方法但未通过注释的value属性指定任何目标的情况下，配置默认前缀以添加到消息目标。 
	 *  <p>默认情况下，前缀设置为"/ queue"。 
	 * 
	 */
	public void setDefaultUserDestinationPrefix(String prefix) {
		this.defaultUserDestinationPrefix = prefix;
	}

	/**
	 * Return the configured default user destination prefix.
	 * @see #setDefaultUserDestinationPrefix(String)
	 */
	/**
	 * 返回配置的默认用户目标前缀。 
	 *  
	 * @see  #setDefaultUserDestinationPrefix（String）
	 */
	public String getDefaultUserDestinationPrefix() {
		return this.defaultUserDestinationPrefix;
	}

	/**
	 * Configure a {@link MessageHeaderInitializer} to apply to the headers of all
	 * messages sent to the client outbound channel.
	 * <p>By default this property is not set.
	 */
	/**
	 * 配置{@link  MessageHeaderInitializer}以应用于发送到客户端出站通道的所有消息的标头。 
	 *  <p>默认情况下未设置此属性。 
	 * 
	 */
	public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
		this.headerInitializer = headerInitializer;
	}

	/**
	 * Return the configured header initializer.
	 */
	/**
	 * 返回已配置的标头初始化程序。 
	 * 
	 */
	@Nullable
	public MessageHeaderInitializer getHeaderInitializer() {
		return this.headerInitializer;
	}


	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return (returnType.hasMethodAnnotation(SendTo.class) ||
				AnnotatedElementUtils.hasAnnotation(returnType.getDeclaringClass(), SendTo.class) ||
				returnType.hasMethodAnnotation(SendToUser.class) ||
				AnnotatedElementUtils.hasAnnotation(returnType.getDeclaringClass(), SendToUser.class) ||
				!this.annotationRequired);
	}

	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message)
			throws Exception {

		if (returnValue == null) {
			return;
		}

		MessageHeaders headers = message.getHeaders();
		String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
		DestinationHelper destinationHelper = getDestinationHelper(headers, returnType);

		SendToUser sendToUser = destinationHelper.getSendToUser();
		if (sendToUser != null) {
			boolean broadcast = sendToUser.broadcast();
			String user = getUserName(message, headers);
			if (user == null) {
				if (sessionId == null) {
					throw new MissingSessionUserException(message);
				}
				user = sessionId;
				broadcast = false;
			}
			String[] destinations = getTargetDestinations(sendToUser, message, this.defaultUserDestinationPrefix);
			for (String destination : destinations) {
				destination = destinationHelper.expandTemplateVars(destination);
				if (broadcast) {
					this.messagingTemplate.convertAndSendToUser(
							user, destination, returnValue, createHeaders(null, returnType));
				}
				else {
					this.messagingTemplate.convertAndSendToUser(
							user, destination, returnValue, createHeaders(sessionId, returnType));
				}
			}
		}

		SendTo sendTo = destinationHelper.getSendTo();
		if (sendTo != null || sendToUser == null) {
			String[] destinations = getTargetDestinations(sendTo, message, this.defaultDestinationPrefix);
			for (String destination : destinations) {
				destination = destinationHelper.expandTemplateVars(destination);
				this.messagingTemplate.convertAndSend(destination, returnValue, createHeaders(sessionId, returnType));
			}
		}
	}

	private DestinationHelper getDestinationHelper(MessageHeaders headers, MethodParameter returnType) {
		SendToUser m1 = AnnotatedElementUtils.findMergedAnnotation(returnType.getExecutable(), SendToUser.class);
		SendTo m2 = AnnotatedElementUtils.findMergedAnnotation(returnType.getExecutable(), SendTo.class);
		if ((m1 != null && !ObjectUtils.isEmpty(m1.value())) || (m2 != null && !ObjectUtils.isEmpty(m2.value()))) {
			return new DestinationHelper(headers, m1, m2);
		}

		SendToUser c1 = AnnotatedElementUtils.findMergedAnnotation(returnType.getDeclaringClass(), SendToUser.class);
		SendTo c2 = AnnotatedElementUtils.findMergedAnnotation(returnType.getDeclaringClass(), SendTo.class);
		if ((c1 != null && !ObjectUtils.isEmpty(c1.value())) || (c2 != null && !ObjectUtils.isEmpty(c2.value()))) {
			return new DestinationHelper(headers, c1, c2);
		}

		return (m1 != null || m2 != null ?
				new DestinationHelper(headers, m1, m2) : new DestinationHelper(headers, c1, c2));
	}

	@Nullable
	protected String getUserName(Message<?> message, MessageHeaders headers) {
		Principal principal = SimpMessageHeaderAccessor.getUser(headers);
		if (principal != null) {
			return (principal instanceof DestinationUserNameProvider ?
					((DestinationUserNameProvider) principal).getDestinationUserName() : principal.getName());
		}
		return null;
	}

	protected String[] getTargetDestinations(@Nullable Annotation annotation, Message<?> message, String defaultPrefix) {
		if (annotation != null) {
			String[] value = (String[]) AnnotationUtils.getValue(annotation);
			if (!ObjectUtils.isEmpty(value)) {
				return value;
			}
		}

		String name = DestinationPatternsMessageCondition.LOOKUP_DESTINATION_HEADER;
		String destination = (String) message.getHeaders().get(name);
		if (!StringUtils.hasText(destination)) {
			throw new IllegalStateException("No lookup destination header in " + message);
		}

		return (destination.startsWith("/") ?
				new String[] {defaultPrefix + destination} : new String[] {defaultPrefix + '/' + destination});
	}

	private MessageHeaders createHeaders(@Nullable String sessionId, MethodParameter returnType) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		if (getHeaderInitializer() != null) {
			getHeaderInitializer().initHeaders(headerAccessor);
		}
		if (sessionId != null) {
			headerAccessor.setSessionId(sessionId);
		}
		headerAccessor.setHeader(SimpMessagingTemplate.CONVERSION_HINT_HEADER, returnType);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}


	@Override
	public String toString() {
		return "SendToMethodReturnValueHandler [annotationRequired=" + this.annotationRequired + "]";
	}


	private class DestinationHelper {

		private final PlaceholderResolver placeholderResolver;

		@Nullable
		private final SendTo sendTo;

		@Nullable
		private final SendToUser sendToUser;


		public DestinationHelper(MessageHeaders headers, @Nullable SendToUser sendToUser, @Nullable SendTo sendTo) {
			Map<String, String> variables = getTemplateVariables(headers);
			this.placeholderResolver = variables::get;
			this.sendTo = sendTo;
			this.sendToUser = sendToUser;
		}

		@SuppressWarnings("unchecked")
		private Map<String, String> getTemplateVariables(MessageHeaders headers) {
			String name = DestinationVariableMethodArgumentResolver.DESTINATION_TEMPLATE_VARIABLES_HEADER;
			return (Map<String, String>) headers.getOrDefault(name, Collections.emptyMap());
		}

		@Nullable
		public SendTo getSendTo() {
			return this.sendTo;
		}

		@Nullable
		public SendToUser getSendToUser() {
			return this.sendToUser;
		}

		public String expandTemplateVars(String destination) {
			return placeholderHelper.replacePlaceholders(destination, this.placeholderResolver);
		}
	}

}
