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

package org.springframework.messaging.simp.user;

import java.util.Set;

import org.springframework.lang.Nullable;

/**
 * Represents a connected user.
 *
 * @author Rossen Stoyanchev
 * @since 4.2
 */
/**
 * 表示一个已连接的用户。 
 *  @author  Rossen Stoyanchev @从4.2开始
 */
public interface SimpUser {

	/**
	 * The unique user name.
	 */
	/**
	 * 唯一的用户名。 
	 * 
	 */
	String getName();

	/**
	 * Whether the user has any sessions.
	 */
	/**
	 * 用户是否有任何会话。 
	 * 
	 */
	boolean hasSessions();

	/**
	 * Look up the session for the given id.
	 * @param sessionId the session id
	 * @return the matching session, or {@code null} if none found
	 */
	/**
	 * 在会话中查找给定的ID。 
	 *  
	 * @param  sessionId会话ID 
	 * @return 匹配的会话； 
	 * 如果找不到，则{@code  null}
	 */
	@Nullable
	SimpSession getSession(String sessionId);

	/**
	 * Return the sessions for the user.
	 * The returned set is a copy and will never be modified.
	 * @return a set of session ids, or an empty set if none
	 */
	/**
	 * 返回用户的会话。 
	 * 返回的集是副本，将永远不会被修改。 
	 *  
	 * @return 一组会话ID，如果没有则为空
	 */
	Set<SimpSession> getSessions();

}
