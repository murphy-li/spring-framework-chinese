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

package org.springframework.messaging.simp.user;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;

import org.springframework.context.SmartLifecycle;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@code MessageHandler} with support for "user" destinations.
 *
 * <p>Listens for messages with "user" destinations, translates their destination
 * to actual target destinations unique to the active session(s) of a user, and
 * then sends the resolved messages to the broker channel to be delivered.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * {@code  MessageHandler}，支持"用户"目标。 
 *  <p>侦听具有"用户"目的地的消息，将其目的地转换为用户的活动会话所独有的实际目标目的地，然后将已解析的消息发送到要传递的代理通道。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public class UserDestinationMessageHandler implements MessageHandler, SmartLifecycle {

	private static final Log logger = SimpLogging.forLogName(UserDestinationMessageHandler.class);


	private final SubscribableChannel clientInboundChannel;

	private final SubscribableChannel brokerChannel;

	private final UserDestinationResolver destinationResolver;

	private final MessageSendingOperations<String> messagingTemplate;

	@Nullable
	private BroadcastHandler broadcastHandler;

	@Nullable
	private MessageHeaderInitializer headerInitializer;

	private volatile boolean running = false;

	private final Object lifecycleMonitor = new Object();


	/**
	 * Create an instance with the given client and broker channels subscribing
	 * to handle messages from each and then sending any resolved messages to the
	 * broker channel.
	 * @param clientInboundChannel messages received from clients.
	 * @param brokerChannel messages sent to the broker.
	 * @param resolver the resolver for "user" destinations.
	 */
	/**
	 * 使用给定的客户端和代理通道订阅一个实例来处理每个实例的消息，然后将所有已解析的消息发送到代理通道，以创建一个实例。 
	 * 从客户端收到的
	 * @param  clientInboundChannel消息。 
	 *  
	 * @param  brokerChannel发送到代理的消息。 
	 *  
	 * @param 解析器"用户"目标的解析器。 
	 * 
	 */
	public UserDestinationMessageHandler(SubscribableChannel clientInboundChannel,
			SubscribableChannel brokerChannel, UserDestinationResolver resolver) {

		Assert.notNull(clientInboundChannel, "'clientInChannel' must not be null");
		Assert.notNull(brokerChannel, "'brokerChannel' must not be null");
		Assert.notNull(resolver, "resolver must not be null");

		this.clientInboundChannel = clientInboundChannel;
		this.brokerChannel = brokerChannel;
		this.messagingTemplate = new SimpMessagingTemplate(brokerChannel);
		this.destinationResolver = resolver;
	}


	/**
	 * Return the configured {@link UserDestinationResolver}.
	 */
	/**
	 * 返回配置的{@link  UserDestinationResolver}。 
	 * 
	 */
	public UserDestinationResolver getUserDestinationResolver() {
		return this.destinationResolver;
	}

	/**
	 * Set a destination to broadcast messages to that remain unresolved because
	 * the user is not connected. In a multi-application server scenario this
	 * gives other application servers a chance to try.
	 * <p>By default this is not set.
	 * @param destination the target destination.
	 */
	/**
	 * 将目标设置为广播消息，直到由于用户未连接而仍未解决的消息为止。 
	 * 在多应用程序服务器方案中，这使其他应用程序服务器有机会尝试。 
	 *  <p>默认情况下未设置。 
	 *  
	 * @param 目的地目标目的地。 
	 * 
	 */
	public void setBroadcastDestination(@Nullable String destination) {
		this.broadcastHandler = (StringUtils.hasText(destination) ?
				new BroadcastHandler(this.messagingTemplate, destination) : null);
	}

	/**
	 * Return the configured destination for unresolved messages.
	 */
	/**
	 * 返回未解决消息的配置目标。 
	 * 
	 */
	@Nullable
	public String getBroadcastDestination() {
		return (this.broadcastHandler != null ? this.broadcastHandler.getBroadcastDestination() : null);
	}

	/**
	 * Return the messaging template used to send resolved messages to the
	 * broker channel.
	 */
	/**
	 * 返回用于将已解决的消息发送到代理通道的消息传递模板。 
	 * 
	 */
	public MessageSendingOperations<String> getBrokerMessagingTemplate() {
		return this.messagingTemplate;
	}

	/**
	 * Configure a custom {@link MessageHeaderInitializer} to initialize the
	 * headers of resolved target messages.
	 * <p>By default this is not set.
	 */
	/**
	 * 配置自定义{@link  MessageHeaderInitializer}以初始化已解析目标消息的标头。 
	 *  <p>默认情况下未设置。 
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
	public final void start() {
		synchronized (this.lifecycleMonitor) {
			this.clientInboundChannel.subscribe(this);
			this.brokerChannel.subscribe(this);
			this.running = true;
		}
	}

	@Override
	public final void stop() {
		synchronized (this.lifecycleMonitor) {
			this.running = false;
			this.clientInboundChannel.unsubscribe(this);
			this.brokerChannel.unsubscribe(this);
		}
	}

	@Override
	public final void stop(Runnable callback) {
		synchronized (this.lifecycleMonitor) {
			stop();
			callback.run();
		}
	}

	@Override
	public final boolean isRunning() {
		return this.running;
	}


	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		Message<?> messageToUse = message;
		if (this.broadcastHandler != null) {
			messageToUse = this.broadcastHandler.preHandle(message);
			if (messageToUse == null) {
				return;
			}
		}

		UserDestinationResult result = this.destinationResolver.resolveDestination(messageToUse);
		if (result == null) {
			return;
		}

		if (result.getTargetDestinations().isEmpty()) {
			if (logger.isTraceEnabled()) {
				logger.trace("No active sessions for user destination: " + result.getSourceDestination());
			}
			if (this.broadcastHandler != null) {
				this.broadcastHandler.handleUnresolved(messageToUse);
			}
			return;
		}

		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(messageToUse);
		initHeaders(accessor);
		accessor.setNativeHeader(SimpMessageHeaderAccessor.ORIGINAL_DESTINATION, result.getSubscribeDestination());
		accessor.setLeaveMutable(true);

		messageToUse = MessageBuilder.createMessage(messageToUse.getPayload(), accessor.getMessageHeaders());
		if (logger.isTraceEnabled()) {
			logger.trace("Translated " + result.getSourceDestination() + " -> " + result.getTargetDestinations());
		}
		for (String target : result.getTargetDestinations()) {
			this.messagingTemplate.send(target, messageToUse);
		}
	}

	private void initHeaders(SimpMessageHeaderAccessor headerAccessor) {
		if (getHeaderInitializer() != null) {
			getHeaderInitializer().initHeaders(headerAccessor);
		}
	}

	@Override
	public String toString() {
		return "UserDestinationMessageHandler[" + this.destinationResolver + "]";
	}


	/**
	 * A handler that broadcasts locally unresolved messages to the broker and
	 * also handles similar broadcasts received from the broker.
	 */
	/**
	 * 一个处理程序，它向代理广播本地未解决的消息，并且还处理从代理接收到的类似广播。 
	 * 
	 */
	private static class BroadcastHandler {

		private static final List<String> NO_COPY_LIST = Arrays.asList("subscription", "message-id");

		private final MessageSendingOperations<String> messagingTemplate;

		private final String broadcastDestination;

		public BroadcastHandler(MessageSendingOperations<String> template, String destination) {
			this.messagingTemplate = template;
			this.broadcastDestination = destination;
		}

		public String getBroadcastDestination() {
			return this.broadcastDestination;
		}

		@Nullable
		public Message<?> preHandle(Message<?> message) throws MessagingException {
			String destination = SimpMessageHeaderAccessor.getDestination(message.getHeaders());
			if (!getBroadcastDestination().equals(destination)) {
				return message;
			}
			SimpMessageHeaderAccessor accessor =
					SimpMessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
			Assert.state(accessor != null, "No SimpMessageHeaderAccessor");
			if (accessor.getSessionId() == null) {
				// Our own broadcast
				return null;
			}
			destination = accessor.getFirstNativeHeader(SimpMessageHeaderAccessor.ORIGINAL_DESTINATION);
			if (logger.isTraceEnabled()) {
				logger.trace("Checking unresolved user destination: " + destination);
			}
			SimpMessageHeaderAccessor newAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
			for (String name : accessor.toNativeHeaderMap().keySet()) {
				if (NO_COPY_LIST.contains(name)) {
					continue;
				}
				newAccessor.setNativeHeader(name, accessor.getFirstNativeHeader(name));
			}
			if (destination != null) {
				newAccessor.setDestination(destination);
			}
			newAccessor.setHeader(SimpMessageHeaderAccessor.IGNORE_ERROR, true); // ensure send doesn't block
			return MessageBuilder.createMessage(message.getPayload(), newAccessor.getMessageHeaders());
		}

		public void handleUnresolved(Message<?> message) {
			MessageHeaders headers = message.getHeaders();
			if (SimpMessageHeaderAccessor.getFirstNativeHeader(
					SimpMessageHeaderAccessor.ORIGINAL_DESTINATION, headers) != null) {
				// Re-broadcast
				return;
			}
			SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
			String destination = accessor.getDestination();
			accessor.setNativeHeader(SimpMessageHeaderAccessor.ORIGINAL_DESTINATION, destination);
			accessor.setLeaveMutable(true);
			message = MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
			if (logger.isTraceEnabled()) {
				logger.trace("Translated " + destination + " -> " + getBroadcastDestination());
			}
			this.messagingTemplate.send(getBroadcastDestination(), message);
		}
	}

}
