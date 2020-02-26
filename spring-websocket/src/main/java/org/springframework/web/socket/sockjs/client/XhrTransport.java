/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.socket.sockjs.client;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.TextMessage;

/**
 * A SockJS {@link Transport} that uses HTTP requests to simulate a WebSocket
 * interaction. The {@code connect} method of the base {@code Transport} interface
 * is used to receive messages from the server while the
 * {@link #executeSendRequest} method here is used to send messages.
 *
 * @author Rossen Stoyanchev
 * @since 4.1
 */
/**
 * 一个SockJS {@link  Transport}，它使用HTTP请求来模拟WebSocket交互。 
 * 基本{@code  Transport}接口的{@code  connect}方法用于从服务器接收消息，而此处的{@link  #executeSendRequest}方法用于发送消息。 
 *  @author  Rossen Stoyanchev @从4.1开始
 */
public interface XhrTransport extends Transport, InfoReceiver {

	/**
	 * An {@code XhrTransport} supports both the "xhr_streaming" and "xhr" SockJS
	 * server transports. From a client perspective there is no implementation
	 * difference.
	 * <p>By default an {@code XhrTransport} will be used with "xhr_streaming"
	 * first and then with "xhr", if the streaming fails to connect. In some
	 * cases it may be useful to suppress streaming so that only "xhr" is used.
	 */
	/**
	 * {@code  XhrTransport}支持SockJS服务器传输的"xhr_streaming"和"xhr"。 
	 * 从客户角度看，实现上没有区别。 
	 *  <p>默认情况下，如果流无法连接，则{{@@code> XhrTransport}将首先与"xhr_streaming"一起使用，然后与"xhr"一起使用。 
	 * 在某些情况下，抑制流传输可能非常有用，因此仅使用"xhr"。 
	 * 
	 */
	boolean isXhrStreamingDisabled();

	/**
	 * Execute a request to send the message to the server.
	 * <p>Note that as of 4.2 this method accepts a {@code headers} parameter.
	 * @param transportUrl the URL for sending messages.
	 * @param message the message to send
	 */
	/**
	 * 执行请求以将消息发送到服务器。 
	 *  <p>请注意，从4.2版本开始，此方法接受{@code  headers}参数。 
	 *  
	 * @param  transportUrl发送消息的URL。 
	 *  
	 * @param 消息要发送的消息
	 */
	void executeSendRequest(URI transportUrl, HttpHeaders headers, TextMessage message);

}
