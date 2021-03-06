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

/**
 * Event raised when a new WebSocket client using a Simple Messaging Protocol
 * (e.g. STOMP) sends a subscription request.
 *
 * @author Rossen Stoyanchev
 * @since 4.0.3
 */
/**
 * 当使用简单消息协议（例如STOMP）的新WebSocket客户端发送订阅请求时引发的事件。 
 *  @author 罗森·斯托扬切夫（Rossen Stoyanchev）@4.0.3起
 */
@SuppressWarnings("serial")
public class SessionSubscribeEvent extends AbstractSubProtocolEvent {

	public SessionSubscribeEvent(Object source, Message<byte[]> message) {
		super(source, message);
	}

	public SessionSubscribeEvent(Object source, Message<byte[]> message, @Nullable Principal user) {
		super(source, message, user);
	}

}
