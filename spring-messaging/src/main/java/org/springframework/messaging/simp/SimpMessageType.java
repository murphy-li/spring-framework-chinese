/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.messaging.simp;

/**
 * A generic representation of different kinds of messages found in simple messaging
 * protocols like STOMP.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 在诸如STOMP之类的简单消息传递协议中发现的各种消息的通用表示形式。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public enum SimpMessageType {

	CONNECT,

	CONNECT_ACK,

	MESSAGE,

	SUBSCRIBE,

	UNSUBSCRIBE,

	HEARTBEAT,

	DISCONNECT,

	DISCONNECT_ACK,

	OTHER

}
