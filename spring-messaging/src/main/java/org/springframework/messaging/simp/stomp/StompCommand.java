/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.messaging.simp.stomp;

import org.springframework.messaging.simp.SimpMessageType;

/**
 * Represents a STOMP command.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 4.0
 */
/**
 * 代表STOMP命令。 
 *  @author  Rossen Stoyanchev @author  Juergen Hoeller @始于4.0
 */
public enum StompCommand {

	// client
	STOMP(SimpMessageType.CONNECT),
	CONNECT(SimpMessageType.CONNECT),
	DISCONNECT(SimpMessageType.DISCONNECT),
	SUBSCRIBE(SimpMessageType.SUBSCRIBE, true, true, false),
	UNSUBSCRIBE(SimpMessageType.UNSUBSCRIBE, false, true, false),
	SEND(SimpMessageType.MESSAGE, true, false, true),
	ACK(SimpMessageType.OTHER),
	NACK(SimpMessageType.OTHER),
	BEGIN(SimpMessageType.OTHER),
	COMMIT(SimpMessageType.OTHER),
	ABORT(SimpMessageType.OTHER),

	// server
	CONNECTED(SimpMessageType.OTHER),
	RECEIPT(SimpMessageType.OTHER),
	MESSAGE(SimpMessageType.MESSAGE, true, true, true),
	ERROR(SimpMessageType.OTHER, false, false, true);


	private final SimpMessageType messageType;

	private final boolean destination;

	private final boolean subscriptionId;

	private final boolean body;


	StompCommand(SimpMessageType messageType) {
		this(messageType, false, false, false);
	}

	StompCommand(SimpMessageType messageType, boolean destination, boolean subscriptionId, boolean body) {
		this.messageType = messageType;
		this.destination = destination;
		this.subscriptionId = subscriptionId;
		this.body = body;
	}


	public SimpMessageType getMessageType() {
		return this.messageType;
	}

	public boolean requiresDestination() {
		return this.destination;
	}

	public boolean requiresSubscriptionId() {
		return this.subscriptionId;
	}

	public boolean requiresContentLength() {
		return this.body;
	}

	public boolean isBodyAllowed() {
		return this.body;
	}

}
