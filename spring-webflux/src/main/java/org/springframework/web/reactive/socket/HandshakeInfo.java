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

package org.springframework.web.reactive.socket;

import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Simple container of information related to the handshake request that started
 * the {@link WebSocketSession} session.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 * @see WebSocketSession#getHandshakeInfo()
 */
/**
 * 简单的信息容器，与开始{@link  WebSocketSession}会话的握手请求有关。 
 *  @author  Rossen Stoyanchev @从5.0起
 * @see  WebSocketSession＃getHandshakeInfo（）
 */
public class HandshakeInfo {

	private final URI uri;

	private final Mono<Principal> principalMono;

	private final HttpHeaders headers;

	@Nullable
	private final String protocol;

	@Nullable
	private final InetSocketAddress remoteAddress;

	private final Map<String, Object> attributes;

	@Nullable
	private final String logPrefix;


	/**
	 * Constructor with basic information about the handshake.
	 * @param uri the endpoint URL
	 * @param headers request headers for server or response headers or client
	 * @param principal the principal for the session
	 * @param protocol the negotiated sub-protocol (may be {@code null})
	 */
	/**
	 * 具有有关握手的基本信息的构造方法。 
	 *  
	 * @param  uri端点URL 
	 * @param 头，服务器或响应头的请求头或客户端
	 * @param 主体，会话
	 * @param 协议的主体，协商的子协议（可以是{@code 空值}）
	 */
	public HandshakeInfo(URI uri, HttpHeaders headers, Mono<Principal> principal, @Nullable String protocol) {
		this(uri, headers, principal, protocol, null, Collections.emptyMap(), null);
	}

	/**
	 * Constructor targetting server-side use with extra information about the
	 * handshake, the remote address, and a pre-existing log prefix for
	 * correlation.
	 * @param uri the endpoint URL
	 * @param headers request headers for server or response headers or client
	 * @param principal the principal for the session
	 * @param protocol the negotiated sub-protocol (may be {@code null})
	 * @param remoteAddress the remote address where the handshake came from
	 * @param attributes initial attributes to use for the WebSocket session
	 * @param logPrefix log prefix used during the handshake for correlating log
	 * messages, if any.
	 * @since 5.1
	 */
	/**
	 * 构造函数以服务器端为目标，并带有有关握手，远程地址和相关性的预先存在的日志前缀的额外信息。 
	 *  
	 * @param  uri端点URL 
	 * @param 头，服务器或响应头的请求头或客户端
	 * @param 主体，会话
	 * @param 协议的主体，协商的子协议（可以是{@code  null}）
	 * @param  remoteAddress握手来自的远程地址
	 * @param 属性初始属性，用于WebSocket会话
	 * @param  logPrefix日志前缀，在握手过程中用于关联日志消息（如果有）。 
	 *  @5.1起
	 */
	public HandshakeInfo(URI uri, HttpHeaders headers, Mono<Principal> principal,
			@Nullable String protocol, @Nullable InetSocketAddress remoteAddress,
			Map<String, Object> attributes, @Nullable String logPrefix) {

		Assert.notNull(uri, "URI is required");
		Assert.notNull(headers, "HttpHeaders are required");
		Assert.notNull(principal, "Principal is required");
		Assert.notNull(attributes, "'attributes' is required");

		this.uri = uri;
		this.headers = headers;
		this.principalMono = principal;
		this.protocol = protocol;
		this.remoteAddress = remoteAddress;
		this.attributes = attributes;
		this.logPrefix = logPrefix;
	}


	/**
	 * Return the URL for the WebSocket endpoint.
	 */
	/**
	 * 返回WebSocket端点的URL。 
	 * 
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Return the handshake HTTP headers. Those are the request headers for a
	 * server session and the response headers for a client session.
	 */
	/**
	 * 返回握手HTTP标头。 
	 * 这些是服务器会话的请求标头和客户端会话的响应标头。 
	 * 
	 */
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	/**
	 * Return the principal associated with the handshake HTTP request.
	 */
	/**
	 * 返回与握手HTTP请求关联的主体。 
	 * 
	 */
	public Mono<Principal> getPrincipal() {
		return this.principalMono;
	}

	/**
	 * The sub-protocol negotiated at handshake time, or {@code null} if none.
	 * @see <a href="https://tools.ietf.org/html/rfc6455#section-1.9">
	 * https://tools.ietf.org/html/rfc6455#section-1.9</a>
	 */
	/**
	 * 子协议在握手时协商，如果没有，则协商{@code  null}。 
	 *  
	 * @see  <a href="https://tools.ietf.org/html/rfc6455#section-1.9"> https://tools.ietf.org/html/rfc6455#section-1.9 </a>
	 */
	@Nullable
	public String getSubProtocol() {
		return this.protocol;
	}

	/**
	 * For a server-side session this is the remote address where the handshake
	 * request came from.
	 * @since 5.1
	 */
	/**
	 * 对于服务器端会话，这是握手请求来自的远程地址。 
	 *  @5.1起
	 */
	@Nullable
	public InetSocketAddress getRemoteAddress() {
		return this.remoteAddress;
	}

	/**
	 * Attributes extracted from the handshake request to be added to the
	 * WebSocket session.
	 * @since 5.1
	 */
	/**
	 * 从握手请求中提取的属性将添加到WebSocket会话中。 
	 *  @5.1起
	 */
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	/**
	 * A log prefix used in the handshake to correlate log messages, if any.
	 * @return a log prefix, or {@code null} if not specified
	 * @since 5.1
	 */
	/**
	 * 握手中用于关联日志消息（如果有）的日志前缀。 
	 *  
	 * @return 日志前缀，如果未指定，则为{@code  null} @从5.1开始
	 */
	@Nullable
	public String getLogPrefix() {
		return this.logPrefix;
	}


	@Override
	public String toString() {
		return "HandshakeInfo[uri=" + this.uri + ", headers=" + this.headers + "]";
	}

}
