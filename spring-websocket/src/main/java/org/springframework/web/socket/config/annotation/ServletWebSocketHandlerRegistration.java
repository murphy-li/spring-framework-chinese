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

package org.springframework.web.socket.config.annotation;

import java.util.Arrays;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;
import org.springframework.web.socket.sockjs.SockJsService;
import org.springframework.web.socket.sockjs.support.SockJsHttpRequestHandler;

/**
 * A helper class for configuring {@link WebSocketHandler} request handling
 * including SockJS fallback options.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 用于配置{@link  WebSocketHandler}请求处理的帮助程序类，包括SockJS后备选项。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public class ServletWebSocketHandlerRegistration
		extends AbstractWebSocketHandlerRegistration<MultiValueMap<HttpRequestHandler, String>> {


	@Override
	protected MultiValueMap<HttpRequestHandler, String> createMappings() {
		return new LinkedMultiValueMap<>();
	}

	@Override
	protected void addSockJsServiceMapping(MultiValueMap<HttpRequestHandler, String> mappings,
			SockJsService sockJsService, WebSocketHandler handler, String pathPattern) {

		SockJsHttpRequestHandler httpHandler = new SockJsHttpRequestHandler(sockJsService, handler);
		mappings.add(httpHandler, pathPattern);
	}

	@Override
	protected void addWebSocketHandlerMapping(MultiValueMap<HttpRequestHandler, String> mappings,
			WebSocketHandler webSocketHandler, HandshakeHandler handshakeHandler,
			HandshakeInterceptor[] interceptors, String path) {

		WebSocketHttpRequestHandler httpHandler =
				new WebSocketHttpRequestHandler(webSocketHandler, handshakeHandler);

		if (!ObjectUtils.isEmpty(interceptors)) {
			httpHandler.setHandshakeInterceptors(Arrays.asList(interceptors));
		}
		mappings.add(httpHandler, path);
	}

}
