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

package org.springframework.web.socket.adapter;

import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketSession;

/**
 * A {@link WebSocketSession} that exposes the underlying, native WebSocketSession
 * through a getter.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 一个{@link  WebSocketSession}，它通过获取器公开基础的本机WebSocketSession。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public interface NativeWebSocketSession extends WebSocketSession {

	/**
	 * Return the underlying native WebSocketSession.
	 */
	/**
	 * 返回基础的本地WebSocketSession。 
	 * 
	 */
	Object getNativeSession();

	/**
	 * Return the underlying native WebSocketSession, if available.
	 * @param requiredType the required type of the session
	 * @return the native session of the required type,
	 * or {@code null} if not available
	 */
	/**
	 * 返回基础本机WebSocketSession（如果有）。 
	 *  
	 * @param  requiredType会话的必需类型
	 * @return 所需类型的本机会话，如果不可用，则为{@code  null}
	 */
	@Nullable
	<T> T getNativeSession(@Nullable Class<T> requiredType);

}
