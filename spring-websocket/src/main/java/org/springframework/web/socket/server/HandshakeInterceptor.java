/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.socket.server;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;

/**
 * Interceptor for WebSocket handshake requests. Can be used to inspect the
 * handshake request and response as well as to pass attributes to the target
 * {@link WebSocketHandler}.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 * @see org.springframework.web.socket.server.support.WebSocketHttpRequestHandler
 * @see org.springframework.web.socket.sockjs.transport.handler.DefaultSockJsService
 */
/**
 * WebSocket握手请求的拦截器。 
 * 可用于检查握手请求和响应，以及将属性传递给目标{@link  WebSocketHandler}。 
 *  @author  Rossen Stoyanchev @从4.0开始
 * @see  org.springframework.web.socket.server.support.WebSocketHttpRequestHandler 
 * @see  org.springframework.web.socket.sockjs.transport.handler.DefaultSockJsService
 */
public interface HandshakeInterceptor {

	/**
	 * Invoked before the handshake is processed.
	 * @param request the current request
	 * @param response the current response
	 * @param wsHandler the target WebSocket handler
	 * @param attributes attributes from the HTTP handshake to associate with the WebSocket
	 * session; the provided attributes are copied, the original map is not used.
	 * @return whether to proceed with the handshake ({@code true}) or abort ({@code false})
	 */
	/**
	 * 在处理握手之前调用。 
	 *  
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @param  wsHandler目标WebSocket处理程序
	 * @param 通过HTTP握手的属性与WebSocket会话关联； 
	 * 提供的属性被复制，不使用原始地图。 
	 *  
	 * @return 是继续进行握手（{@code  true}）还是中止（{@code  false}）
	 */
	boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception;

	/**
	 * Invoked after the handshake is done. The response status and headers indicate
	 * the results of the handshake, i.e. whether it was successful or not.
	 * @param request the current request
	 * @param response the current response
	 * @param wsHandler the target WebSocket handler
	 * @param exception an exception raised during the handshake, or {@code null} if none
	 */
	/**
	 * 握手完成后调用。 
	 * 响应状态和标头指示握手的结果，即是否成功。 
	 *  
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @param  wsHandler目标WebSocket处理程序
	 * @param 异常握手过程中引发的异常； 
	 * 如果没有，则为{@code  null}
	 */
	void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, @Nullable Exception exception);

}
