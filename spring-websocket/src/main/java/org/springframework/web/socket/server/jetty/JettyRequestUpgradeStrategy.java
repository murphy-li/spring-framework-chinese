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

package org.springframework.web.socket.server.jetty;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.api.extensions.ExtensionConfig;
import org.eclipse.jetty.websocket.server.HandshakeRFC6455;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;

import org.springframework.context.Lifecycle;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.adapter.jetty.JettyWebSocketHandlerAdapter;
import org.springframework.web.socket.adapter.jetty.JettyWebSocketSession;
import org.springframework.web.socket.adapter.jetty.WebSocketToJettyExtensionConfigAdapter;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.RequestUpgradeStrategy;

/**
 * A {@link RequestUpgradeStrategy} for use with Jetty 9.4. Based on Jetty's
 * internal {@code org.eclipse.jetty.websocket.server.WebSocketHandler} class.
 *
 * @author Phillip Webb
 * @author Rossen Stoyanchev
 * @author Brian Clozel
 * @author Juergen Hoeller
 * @since 4.0
 */
/**
 * 与Jetty 9.4一起使用的{@link  RequestUpgradeStrategy}。 
 * 基于Jetty的内部{@code  org.eclipse.jetty.websocket.server.WebSocketHandler}类。 
 *  @author  Phillip Webb @author  Rossen Stoyanchev @author  Brian Clozel @author  Juergen Hoeller @始于4.0
 */
public class JettyRequestUpgradeStrategy implements RequestUpgradeStrategy, ServletContextAware, Lifecycle {

	private static final ThreadLocal<WebSocketHandlerContainer> containerHolder =
			new NamedThreadLocal<>("WebSocketHandlerContainer");


	private WebSocketPolicy policy;

	private WebSocketServerFactory factory;

	private ServletContext servletContext;

	private volatile boolean running = false;

	private volatile List<WebSocketExtension> supportedExtensions;


	/**
	 * Default constructor that creates {@link WebSocketServerFactory} through
	 * its default constructor thus using a default {@link WebSocketPolicy}.
	 */
	/**
	 * 通过其默认构造函数创建{@link  WebSocketServerFactory}的默认构造函数，从而使用默认的{@link  WebSocketPolicy}。 
	 * 
	 */
	public JettyRequestUpgradeStrategy() {
		this.policy = WebSocketPolicy.newServerPolicy();
	}

	/**
	 * A constructor accepting a {@link WebSocketPolicy} to be used when
	 * creating the {@link WebSocketServerFactory} instance.
	 * @param policy the policy to use
	 * @since 4.3.5
	 */
	/**
	 * 创建{@link  WebSocketServerFactory}实例时使用的{{@link> WebSocketPolicy}构造函数。 
	 *  
	 * @param  policy自4.3.5起使用的策略
	 */
	public JettyRequestUpgradeStrategy(WebSocketPolicy policy) {
		Assert.notNull(policy, "WebSocketPolicy must not be null");
		this.policy = policy;
	}

	/**
	 * A constructor accepting a {@link WebSocketServerFactory}.
	 * @param factory the pre-configured factory to use
	 */
	/**
	 * 接受{@link  WebSocketServerFactory}的构造函数。 
	 *  
	 * @param 工厂要使用的预配置工厂
	 */
	public JettyRequestUpgradeStrategy(WebSocketServerFactory factory) {
		Assert.notNull(factory, "WebSocketServerFactory must not be null");
		this.factory = factory;
	}


	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void start() {
		if (!isRunning()) {
			this.running = true;
			try {
				if (this.factory == null) {
					this.factory = new WebSocketServerFactory(this.servletContext, this.policy);
				}
				this.factory.setCreator((request, response) -> {
					WebSocketHandlerContainer container = containerHolder.get();
					Assert.state(container != null, "Expected WebSocketHandlerContainer");
					response.setAcceptedSubProtocol(container.getSelectedProtocol());
					response.setExtensions(container.getExtensionConfigs());
					return container.getHandler();
				});
				this.factory.start();
			}
			catch (Throwable ex) {
				throw new IllegalStateException("Unable to start Jetty WebSocketServerFactory", ex);
			}
		}
	}

	@Override
	public void stop() {
		if (isRunning()) {
			this.running = false;
			if (this.factory != null) {
				try {
					this.factory.stop();
				}
				catch (Throwable ex) {
					throw new IllegalStateException("Unable to stop Jetty WebSocketServerFactory", ex);
				}
			}
		}
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}


	@Override
	public String[] getSupportedVersions() {
		return new String[] { String.valueOf(HandshakeRFC6455.VERSION) };
	}

	@Override
	public List<WebSocketExtension> getSupportedExtensions(ServerHttpRequest request) {
		if (this.supportedExtensions == null) {
			this.supportedExtensions = buildWebSocketExtensions();
		}
		return this.supportedExtensions;
	}

	private List<WebSocketExtension> buildWebSocketExtensions() {
		Set<String> names = getExtensionNames();
		List<WebSocketExtension> result = new ArrayList<>(names.size());
		for (String name : names) {
			result.add(new WebSocketExtension(name));
		}
		return result;
	}

	@SuppressWarnings({"unchecked", "deprecation"})
	private Set<String> getExtensionNames() {
		try {
			return this.factory.getAvailableExtensionNames();
		}
		catch (IncompatibleClassChangeError ex) {
			// Fallback for versions prior to 9.4.21:
			// 9.4.20.v20190813: ExtensionFactory (abstract class -> interface)
			// 9.4.21.v20190926: ExtensionFactory (interface -> abstract class) + deprecated
			Class<?> clazz = org.eclipse.jetty.websocket.api.extensions.ExtensionFactory.class;
			Method method = ClassUtils.getMethod(clazz, "getExtensionNames");
			return (Set<String>) ReflectionUtils.invokeMethod(method, this.factory.getExtensionFactory());
		}
	}

	@Override
	public void upgrade(ServerHttpRequest request, ServerHttpResponse response,
			String selectedProtocol, List<WebSocketExtension> selectedExtensions, Principal user,
			WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {

		Assert.isInstanceOf(ServletServerHttpRequest.class, request, "ServletServerHttpRequest required");
		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

		Assert.isInstanceOf(ServletServerHttpResponse.class, response, "ServletServerHttpResponse required");
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

		Assert.isTrue(this.factory.isUpgradeRequest(servletRequest, servletResponse), "Not a WebSocket handshake");

		JettyWebSocketSession session = new JettyWebSocketSession(attributes, user);
		JettyWebSocketHandlerAdapter handlerAdapter = new JettyWebSocketHandlerAdapter(wsHandler, session);

		WebSocketHandlerContainer container =
				new WebSocketHandlerContainer(handlerAdapter, selectedProtocol, selectedExtensions);

		try {
			containerHolder.set(container);
			this.factory.acceptWebSocket(servletRequest, servletResponse);
		}
		catch (IOException ex) {
			throw new HandshakeFailureException(
					"Response update failed during upgrade to WebSocket: " + request.getURI(), ex);
		}
		finally {
			containerHolder.remove();
		}
	}


	private static class WebSocketHandlerContainer {

		private final JettyWebSocketHandlerAdapter handler;

		private final String selectedProtocol;

		private final List<ExtensionConfig> extensionConfigs;

		public WebSocketHandlerContainer(
				JettyWebSocketHandlerAdapter handler, String protocol, List<WebSocketExtension> extensions) {

			this.handler = handler;
			this.selectedProtocol = protocol;
			if (CollectionUtils.isEmpty(extensions)) {
				this.extensionConfigs = new ArrayList<>(0);
			}
			else {
				this.extensionConfigs = new ArrayList<>(extensions.size());
				for (WebSocketExtension extension : extensions) {
					this.extensionConfigs.add(new WebSocketToJettyExtensionConfigAdapter(extension));
				}
			}
		}

		public JettyWebSocketHandlerAdapter getHandler() {
			return this.handler;
		}

		public String getSelectedProtocol() {
			return this.selectedProtocol;
		}

		public List<ExtensionConfig> getExtensionConfigs() {
			return this.extensionConfigs;
		}
	}

}
