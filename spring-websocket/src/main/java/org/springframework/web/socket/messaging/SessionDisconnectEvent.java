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

package org.springframework.web.socket.messaging;

import java.security.Principal;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.web.socket.CloseStatus;

/**
 * Event raised when the session of a WebSocket client using a Simple Messaging
 * Protocol (e.g. STOMP) as the WebSocket sub-protocol is closed.
 *
 * <p>Note that this event may be raised more than once for a single session and
 * therefore event consumers should be idempotent and ignore a duplicate event.
 *
 * @author Rossen Stoyanchev
 * @since 4.0.3
 */
/**
 * 当使用简单消息协议（例如STOMP）作为WebSocket子协议的WebSocket客户端会话关闭时引发的事件。 
 *  <p>请注意，对于单个会话，可能会多次引发此事件，因此，事件使用者应该是幂等的，并忽略重复的事件。 
 *  @author 罗森·斯托扬切夫（Rossen Stoyanchev）@4.0.3起
 */
@SuppressWarnings("serial")
public class SessionDisconnectEvent extends AbstractSubProtocolEvent {

	private final String sessionId;

	private final CloseStatus status;


	/**
	 * Create a new SessionDisconnectEvent.
	 * @param source the component that published the event (never {@code null})
	 * @param message the message (never {@code null})
	 * @param sessionId the disconnect message
	 * @param closeStatus the status object
	 */
	/**
	 * 创建一个新的SessionDisconnectEvent。 
	 *  
	 * @param 提供发布事件的组件的源信息（从不{<@@code> null}）
	 * @param 向消息发送消息（从未{<@@code> null}）
	 * @param  sessionId断开消息
	 * @param  closeStatus状态对象
	 */
	public SessionDisconnectEvent(Object source, Message<byte[]> message, String sessionId,
			CloseStatus closeStatus) {

		this(source, message, sessionId, closeStatus, null);
	}

	/**
	 * Create a new SessionDisconnectEvent.
	 * @param source the component that published the event (never {@code null})
	 * @param message the message (never {@code null})
	 * @param sessionId the disconnect message
	 * @param closeStatus the status object
	 * @param user the current session user
	 */
	/**
	 * 创建一个新的SessionDisconnectEvent。 
	 *  
	 * @param 提供发布事件的组件的源信息（从不{<@@code> null}）
	 * @param 向消息发送消息（从未{<@@code> null}）
	 * @param  sessionId断开消息
	 * @param  closeStatus状态对象
	 * @param 用户当前会话用户
	 */
	public SessionDisconnectEvent(Object source, Message<byte[]> message, String sessionId,
			CloseStatus closeStatus, @Nullable Principal user) {

		super(source, message, user);
		Assert.notNull(sessionId, "Session id must not be null");
		this.sessionId = sessionId;
		this.status = closeStatus;
	}


	/**
	 * Return the session id.
	 */
	/**
	 * 返回会话ID。 
	 * 
	 */
	public String getSessionId() {
		return this.sessionId;
	}

	/**
	 * Return the status with which the session was closed.
	 */
	/**
	 * 返回关闭会话的状态。 
	 * 
	 */
	public CloseStatus getCloseStatus() {
		return this.status;
	}


	@Override
	public String toString() {
		return "SessionDisconnectEvent[sessionId=" + this.sessionId + ", " + this.status.toString() + "]";
	}

}
