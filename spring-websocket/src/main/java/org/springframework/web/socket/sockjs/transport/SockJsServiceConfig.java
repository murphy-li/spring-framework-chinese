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

package org.springframework.web.socket.sockjs.transport;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.sockjs.SockJsService;
import org.springframework.web.socket.sockjs.frame.SockJsMessageCodec;

/**
 * Provides transport handling code with access to the {@link SockJsService} configuration
 * options they need to have access to. Mainly for internal use.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 为传输处理代码提供对他们需要访问的{@link  SockJsService}配置选项的访问权限。 
 * 主要供内部使用。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public interface SockJsServiceConfig {

	/**
	 * A scheduler instance to use for scheduling heart-beat messages.
	 */
	/**
	 * 用于调度心跳消息的调度程序实例。 
	 * 
	 */
	TaskScheduler getTaskScheduler();

	/**
	 * Streaming transports save responses on the client side and don't free
	 * memory used by delivered messages. Such transports need to recycle the
	 * connection once in a while. This property sets a minimum number of bytes
	 * that can be send over a single HTTP streaming request before it will be
	 * closed. After that client will open a new request. Setting this value to
	 * one effectively disables streaming and will make streaming transports to
	 * behave like polling transports.
	 * <p>The default value is 128K (i.e. 128 * 1024).
	 */
	/**
	 * 流传输将响应保存在客户端，并且不会释放已传递邮件所使用的内存。 
	 * 这样的传输需要不时地回收连接。 
	 * 此属性设置在关闭HTTP之前可以通过单个HTTP流请求发送的最小字节数。 
	 * 之后，客户端将打开一个新请求。 
	 * 将此值设置为1会有效禁用流传输，并使流传输的行为类似于轮询传输。 
	 *  <p>默认值为128K（即1281024）。 
	 * 
	 */
	int getStreamBytesLimit();

	/**
	 * The amount of time in milliseconds when the server has not sent any
	 * messages and after which the server should send a heartbeat frame to the
	 * client in order to keep the connection from breaking.
	 * <p>The default value is 25,000 (25 seconds).
	 */
	/**
	 * 服务器未发送任何消息的时间（以毫秒为单位），在此时间之后，服务器应向客户端发送心跳帧，以防止连接断开。 
	 *  <p>默认值为25,000（25秒）。 
	 * 
	 */
	long getHeartbeatTime();

	/**
	 * The number of server-to-client messages that a session can cache while waiting for
	 * the next HTTP polling request from the client. All HTTP transports use this
	 * property since even streaming transports recycle HTTP requests periodically.
	 * <p>The amount of time between HTTP requests should be relatively brief and will not
	 * exceed the allows disconnect delay (see
	 * {@link org.springframework.web.socket.sockjs.support.AbstractSockJsService#setDisconnectDelay(long)},
	 * 5 seconds by default.
	 * <p>The default size is 100.
	 */
	/**
	 * 会话在等待来自客户端的下一个HTTP轮询请求时可以缓存的服务器到客户端消息的数量。 
	 * 所有HTTP传输都使用此属性，因为即使流传输也定期回收HTTP请求。 
	 *  <p> HTTP请求之间的时间间隔应该相对较短，并且不会超过允许的断开延迟（请参阅{@link  org.springframework.web.socket.sockjs.support.AbstractSockJsService＃setDisconnectDelay（long）}，5 <p>默认大小为100。 
	 * 
	 */
	int getHttpMessageCacheSize();

	/**
	 * The codec to use for encoding and decoding SockJS messages.
	 * @throws IllegalStateException if no {@link SockJsMessageCodec} is available
	 */
	/**
	 * 用于编码和解码SockJS消息的编解码器。 
	 *  
	 * @throws  IllegalStateException如果没有可用的{@link  SockJsMessageCodec}
	 */
	SockJsMessageCodec getMessageCodec();

}
