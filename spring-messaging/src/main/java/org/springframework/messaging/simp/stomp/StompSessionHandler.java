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

package org.springframework.messaging.simp.stomp;

import org.springframework.lang.Nullable;

/**
 * A contract for client STOMP session lifecycle events including a callback
 * when the session is established and notifications of transport or message
 * handling failures.
 *
 * <p>This contract also extends {@link StompFrameHandler} in order to handle
 * STOMP ERROR frames received from the broker.
 *
 * <p>Implementations of this interface should consider extending
 * {@link StompSessionHandlerAdapter}.
 *
 * @author Rossen Stoyanchev
 * @since 4.2
 * @see StompSessionHandlerAdapter
 */
/**
 * 客户端STOMP会话生命周期事件的合同，包括建立会话时的回调以及传输或消息处理失败的通知。 
 *  <p>此合同还扩展了{@link  StompFrameHandler}，以处理从代理接收到的STOMP ERROR帧。 
 *  <p>此接口的实现应考虑扩展{@link  StompSessionHandlerAdapter}。 
 *  @author  Rossen Stoyanchev @从4.2开始
 * @see  StompSessionHandlerAdapter
 */
public interface StompSessionHandler extends StompFrameHandler {

	/**
	 * Invoked when the session is ready to use, i.e. after the underlying
	 * transport (TCP, WebSocket) is connected and a STOMP CONNECTED frame is
	 * received from the broker.
	 * @param session the client STOMP session
	 * @param connectedHeaders the STOMP CONNECTED frame headers
	 */
	/**
	 * 当会话准备好使用时，即在连接了基础传输（TCP，WebSocket）并从代理接收到STOMP CONNECTED帧之后调用。 
	 *  
	 * @param 会话客户端STOMP会话
	 * @param  connectedHeaders STOMP CONNECTED帧头
	 */
	void afterConnected(StompSession session, StompHeaders connectedHeaders);

	/**
	 * Handle any exception arising while processing a STOMP frame such as a
	 * failure to convert the payload or an unhandled exception in the
	 * application {@code StompFrameHandler}.
	 * @param session the client STOMP session
	 * @param command the STOMP command of the frame
	 * @param headers the headers
	 * @param payload the raw payload
	 * @param exception the exception
	 */
	/**
	 * 处理在处理STOMP帧时出现的任何异常，例如转换有效负载失败或应用程序{@code  StompFrameHandler}中的未处理异常。 
	 *  
	 * @param 会话客户端STOMP会话
	 * @param 命令帧的STOMP命令
	 * @param 标头标头
	 * @param 有效负载原始有效负载
	 * @param 异常
	 */
	void handleException(StompSession session, @Nullable StompCommand command,
			StompHeaders headers, byte[] payload, Throwable exception);

	/**
	 * Handle a low level transport error which could be an I/O error or a
	 * failure to encode or decode a STOMP message.
	 * <p>Note that
	 * {@link org.springframework.messaging.simp.stomp.ConnectionLostException
	 * ConnectionLostException} will be passed into this method when the
	 * connection is lost rather than closed normally via
	 * {@link StompSession#disconnect()}.
	 * @param session the client STOMP session
	 * @param exception the exception that occurred
	 */
	/**
	 * 处理低级传输错误，该错误可能是I / O错误或对STOMP消息进行编码或解码失败。 
	 *  <p>请注意，当连接丢失而不是通过{@link  StompSession＃disconnect（）}通常关闭时，{<@link> org.springframework.messaging.simp.stomp.ConnectionLostException ConnectionLostException}将传递到此方法中。 
	 *  
	 * @param 会话客户端STOMP会话
	 * @param 异常发生的异常
	 */
	void handleTransportError(StompSession session, Throwable exception);

}
