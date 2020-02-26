/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.socket.sockjs.transport;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.Lifecycle;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HandshakeInterceptorChain;
import org.springframework.web.socket.sockjs.SockJsException;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;
import org.springframework.web.socket.sockjs.frame.SockJsMessageCodec;
import org.springframework.web.socket.sockjs.support.AbstractSockJsService;

/**
 * A basic implementation of {@link org.springframework.web.socket.sockjs.SockJsService}
 * with support for SPI-based transport handling and session management.
 *
 * <p>Based on the {@link TransportHandler} SPI. {@code TransportHandlers} may
 * additionally implement the {@link SockJsSessionFactory} and {@link HandshakeHandler} interfaces.
 *
 * <p>See the {@link AbstractSockJsService} base class for important details on request mapping.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @since 4.0
 */
/**
 * {@link  org.springframework.web.socket.sockjs.SockJsService}的基本实现，支持基于SPI的传输处理和会话管理。 
 *  <p>基于{@link  TransportHandler} SPI。 
 *  {@code  TransportHandlers}可以另外实现{@link  SockJsSessionFactory}和{@link  HandshakeHandler}接口。 
 *  <p>有关请求映射的重要详细信息，请参见{@link  AbstractSockJsService}基类。 
 *  @author  Rossen Stoyanchev @author  Juergen Hoeller @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）自4.0起
 */
public class TransportHandlingSockJsService extends AbstractSockJsService implements SockJsServiceConfig, Lifecycle {

	private static final boolean jackson2Present = ClassUtils.isPresent(
			"com.fasterxml.jackson.databind.ObjectMapper", TransportHandlingSockJsService.class.getClassLoader());


	private final Map<TransportType, TransportHandler> handlers = new EnumMap<>(TransportType.class);

	@Nullable
	private SockJsMessageCodec messageCodec;

	private final List<HandshakeInterceptor> interceptors = new ArrayList<>();

	private final Map<String, SockJsSession> sessions = new ConcurrentHashMap<>();

	@Nullable
	private ScheduledFuture<?> sessionCleanupTask;

	private volatile boolean running;


	/**
	 * Create a TransportHandlingSockJsService with given {@link TransportHandler handler} types.
	 * @param scheduler a task scheduler for heart-beat messages and removing timed-out sessions;
	 * the provided TaskScheduler should be declared as a Spring bean to ensure it gets
	 * initialized at start-up and shuts down when the application stops
	 * @param handlers one or more {@link TransportHandler} implementations to use
	 */
	/**
	 * 使用给定的{@link  TransportHandler handler}类型创建一个TransportHandlingSockJsService。 
	 *  
	 * @param 计划程序一个任务计划程序，用于心跳消息并删除超时的会话； 
	 * 提供的TaskScheduler应该声明为Spring bean，以确保其在启动时初始化并在应用程序停止
	 * @param 处理程序使用一个或多个{@link  TransportHandler}实现时关闭并关闭
	 */
	public TransportHandlingSockJsService(TaskScheduler scheduler, TransportHandler... handlers) {
		this(scheduler, Arrays.asList(handlers));
	}

	/**
	 * Create a TransportHandlingSockJsService with given {@link TransportHandler handler} types.
	 * @param scheduler a task scheduler for heart-beat messages and removing timed-out sessions;
	 * the provided TaskScheduler should be declared as a Spring bean to ensure it gets
	 * initialized at start-up and shuts down when the application stops
	 * @param handlers one or more {@link TransportHandler} implementations to use
	 */
	/**
	 * 使用给定的{@link  TransportHandler handler}类型创建一个TransportHandlingSockJsService。 
	 *  
	 * @param 计划程序一个任务计划程序，用于心跳消息并删除超时的会话； 
	 * 提供的TaskScheduler应该声明为Spring bean，以确保其在启动时初始化并在应用程序停止
	 * @param 处理程序使用一个或多个{@link  TransportHandler}实现时关闭并关闭
	 */
	public TransportHandlingSockJsService(TaskScheduler scheduler, Collection<TransportHandler> handlers) {
		super(scheduler);

		if (CollectionUtils.isEmpty(handlers)) {
			logger.warn("No transport handlers specified for TransportHandlingSockJsService");
		}
		else {
			for (TransportHandler handler : handlers) {
				handler.initialize(this);
				this.handlers.put(handler.getTransportType(), handler);
			}
		}

		if (jackson2Present) {
			this.messageCodec = new Jackson2SockJsMessageCodec();
		}
	}


	/**
	 * Return the registered handlers per transport type.
	 */
	/**
	 * 返回每种运输类型的注册处理程序。 
	 * 
	 */
	public Map<TransportType, TransportHandler> getTransportHandlers() {
		return Collections.unmodifiableMap(this.handlers);
	}

	/**
	 * The codec to use for encoding and decoding SockJS messages.
	 */
	/**
	 * 用于编码和解码SockJS消息的编解码器。 
	 * 
	 */
	public void setMessageCodec(SockJsMessageCodec messageCodec) {
		this.messageCodec = messageCodec;
	}

	@Override
	public SockJsMessageCodec getMessageCodec() {
		Assert.state(this.messageCodec != null, "A SockJsMessageCodec is required but not available: " +
				"Add Jackson to the classpath, or configure a custom SockJsMessageCodec.");
		return this.messageCodec;
	}

	/**
	 * Configure one or more WebSocket handshake request interceptors.
	 */
	/**
	 * 配置一个或多个WebSocket握手请求拦截器。 
	 * 
	 */
	public void setHandshakeInterceptors(@Nullable List<HandshakeInterceptor> interceptors) {
		this.interceptors.clear();
		if (interceptors != null) {
			this.interceptors.addAll(interceptors);
		}
	}

	/**
	 * Return the configured WebSocket handshake request interceptors.
	 */
	/**
	 * 返回配置的WebSocket握手请求拦截器。 
	 * 
	 */
	public List<HandshakeInterceptor> getHandshakeInterceptors() {
		return this.interceptors;
	}


	@Override
	public void start() {
		if (!isRunning()) {
			this.running = true;
			for (TransportHandler handler : this.handlers.values()) {
				if (handler instanceof Lifecycle) {
					((Lifecycle) handler).start();
				}
			}
		}
	}

	@Override
	public void stop() {
		if (isRunning()) {
			this.running = false;
			for (TransportHandler handler : this.handlers.values()) {
				if (handler instanceof Lifecycle) {
					((Lifecycle) handler).stop();
				}
			}
		}
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}


	@Override
	protected void handleRawWebSocketRequest(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler handler) throws IOException {

		TransportHandler transportHandler = this.handlers.get(TransportType.WEBSOCKET);
		if (!(transportHandler instanceof HandshakeHandler)) {
			logger.error("No handler configured for raw WebSocket messages");
			response.setStatusCode(HttpStatus.NOT_FOUND);
			return;
		}

		HandshakeInterceptorChain chain = new HandshakeInterceptorChain(this.interceptors, handler);
		HandshakeFailureException failure = null;

		try {
			Map<String, Object> attributes = new HashMap<>();
			if (!chain.applyBeforeHandshake(request, response, attributes)) {
				return;
			}
			((HandshakeHandler) transportHandler).doHandshake(request, response, handler, attributes);
			chain.applyAfterHandshake(request, response, null);
		}
		catch (HandshakeFailureException ex) {
			failure = ex;
		}
		catch (Exception ex) {
			failure = new HandshakeFailureException("Uncaught failure for request " + request.getURI(), ex);
		}
		finally {
			if (failure != null) {
				chain.applyAfterHandshake(request, response, failure);
				throw failure;
			}
		}
	}

	@Override
	protected void handleTransportRequest(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler handler, String sessionId, String transport) throws SockJsException {

		TransportType transportType = TransportType.fromValue(transport);
		if (transportType == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Unknown transport type for " + request.getURI());
			}
			response.setStatusCode(HttpStatus.NOT_FOUND);
			return;
		}

		TransportHandler transportHandler = this.handlers.get(transportType);
		if (transportHandler == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("No TransportHandler for " + request.getURI());
			}
			response.setStatusCode(HttpStatus.NOT_FOUND);
			return;
		}

		SockJsException failure = null;
		HandshakeInterceptorChain chain = new HandshakeInterceptorChain(this.interceptors, handler);

		try {
			HttpMethod supportedMethod = transportType.getHttpMethod();
			if (supportedMethod != request.getMethod()) {
				if (request.getMethod() == HttpMethod.OPTIONS && transportType.supportsCors()) {
					if (checkOrigin(request, response, HttpMethod.OPTIONS, supportedMethod)) {
						response.setStatusCode(HttpStatus.NO_CONTENT);
						addCacheHeaders(response);
					}
				}
				else if (transportType.supportsCors()) {
					sendMethodNotAllowed(response, supportedMethod, HttpMethod.OPTIONS);
				}
				else {
					sendMethodNotAllowed(response, supportedMethod);
				}
				return;
			}

			SockJsSession session = this.sessions.get(sessionId);
			if (session == null) {
				if (transportHandler instanceof SockJsSessionFactory) {
					Map<String, Object> attributes = new HashMap<>();
					if (!chain.applyBeforeHandshake(request, response, attributes)) {
						return;
					}
					SockJsSessionFactory sessionFactory = (SockJsSessionFactory) transportHandler;
					session = createSockJsSession(sessionId, sessionFactory, handler, attributes);
				}
				else {
					response.setStatusCode(HttpStatus.NOT_FOUND);
					if (logger.isDebugEnabled()) {
						logger.debug("Session not found, sessionId=" + sessionId +
								". The session may have been closed " +
								"(e.g. missed heart-beat) while a message was coming in.");
					}
					return;
				}
			}
			else {
				Principal principal = session.getPrincipal();
				if (principal != null && !principal.equals(request.getPrincipal())) {
					logger.debug("The user for the session does not match the user for the request.");
					response.setStatusCode(HttpStatus.NOT_FOUND);
					return;
				}
				if (!transportHandler.checkSessionType(session)) {
					logger.debug("Session type does not match the transport type for the request.");
					response.setStatusCode(HttpStatus.NOT_FOUND);
					return;
				}
			}

			if (transportType.sendsNoCacheInstruction()) {
				addNoCacheHeaders(response);
			}
			if (transportType.supportsCors() && !checkOrigin(request, response)) {
				return;
			}

			transportHandler.handleRequest(request, response, handler, session);
			chain.applyAfterHandshake(request, response, null);
		}
		catch (SockJsException ex) {
			failure = ex;
		}
		catch (Exception ex) {
			failure = new SockJsException("Uncaught failure for request " + request.getURI(), sessionId, ex);
		}
		finally {
			if (failure != null) {
				chain.applyAfterHandshake(request, response, failure);
				throw failure;
			}
		}
	}

	@Override
	protected boolean validateRequest(String serverId, String sessionId, String transport) {
		if (!super.validateRequest(serverId, sessionId, transport)) {
			return false;
		}

		if (!this.allowedOrigins.contains("*")) {
			TransportType transportType = TransportType.fromValue(transport);
			if (transportType == null || !transportType.supportsOrigin()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Origin check enabled but transport '" + transport + "' does not support it.");
				}
				return false;
			}
		}

		return true;
	}

	private SockJsSession createSockJsSession(String sessionId, SockJsSessionFactory sessionFactory,
			WebSocketHandler handler, Map<String, Object> attributes) {

		SockJsSession session = this.sessions.get(sessionId);
		if (session != null) {
			return session;
		}
		if (this.sessionCleanupTask == null) {
			scheduleSessionTask();
		}
		session = sessionFactory.createSession(sessionId, handler, attributes);
		this.sessions.put(sessionId, session);
		return session;
	}

	private void scheduleSessionTask() {
		synchronized (this.sessions) {
			if (this.sessionCleanupTask != null) {
				return;
			}
			this.sessionCleanupTask = getTaskScheduler().scheduleAtFixedRate(() -> {
				List<String> removedIds = new ArrayList<>();
				for (SockJsSession session : this.sessions.values()) {
					try {
						if (session.getTimeSinceLastActive() > getDisconnectDelay()) {
							this.sessions.remove(session.getId());
							removedIds.add(session.getId());
							session.close();
						}
					}
					catch (Throwable ex) {
						// Could be part of normal workflow (e.g. browser tab closed)
						logger.debug("Failed to close " + session, ex);
					}
				}
				if (logger.isDebugEnabled() && !removedIds.isEmpty()) {
					logger.debug("Closed " + removedIds.size() + " sessions: " + removedIds);
				}
			}, getDisconnectDelay());
		}
	}

}
