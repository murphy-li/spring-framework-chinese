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

package org.springframework.web.server.session;

import reactor.core.publisher.Mono;

import org.springframework.web.server.WebSession;

/**
 * Strategy for {@link WebSession} persistence.
 *
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 5.0
 */
/**
 * {@link  WebSession}持久性策略。 
 *  @author  Rossen Stoyanchev @author  Rob Winch @从5.0开始
 */
public interface WebSessionStore {

	/**
	 * Create a new WebSession.
	 * <p>Note that this does nothing more than create a new instance.
	 * The session can later be started explicitly via {@link WebSession#start()}
	 * or implicitly by adding attributes -- and then persisted via
	 * {@link WebSession#save()}.
	 * @return the created session instance
	 */
	/**
	 * 创建一个新的WebSession。 
	 *  <p>请注意，这无非是创建一个新实例。 
	 * 以后可以通过{@link  WebSession＃start（）}显式启动会话，也可以通过添加属性隐式启动会话，然后通过{@link  WebSession＃save（）}持久化会话。 
	 *  
	 * @return 创建的会话实例
	 */
	Mono<WebSession> createWebSession();

	/**
	 * Return the WebSession for the given id.
	 * <p><strong>Note:</strong> This method should perform an expiration check,
	 * and if it has expired remove the session and return empty. This method
	 * should also update the lastAccessTime of retrieved sessions.
	 * @param sessionId the session to load
	 * @return the session, or an empty {@code Mono} .
	 */
	/**
	 * 返回给定ID的WebSession。 
	 *  <p> <strong>注意</ strong>：该方法应执行到期检查，如果该方法已到期，请删除该会话并返回空值。 
	 * 此方法还应该更新检索到的会话的lastAccessTime。 
	 *  
	 * @param  sessionId要加载的会话
	 * @return 会话，或者为空的{@code  Mono}。 
	 * 
	 */
	Mono<WebSession> retrieveSession(String sessionId);

	/**
	 * Remove the WebSession for the specified id.
	 * @param sessionId the id of the session to remove
	 * @return a completion notification (success or error)
	 */
	/**
	 * 删除指定ID的WebSession。 
	 *  
	 * @param  sessionId要删除
	 * @return 完成通知的会话的ID（成功或错误）
	 */
	Mono<Void> removeSession(String sessionId);

	/**
	 * Update the last accessed timestamp to "now".
	 * @param webSession the session to update
	 * @return the session with the updated last access time
	 */
	/**
	 * 将上次访问的时间戳更新为"现在"。 
	 *  
	 * @param  webSession要更新的会话
	 * @return 具有更新的上次访问时间的会话
	 */
	Mono<WebSession> updateLastAccessTime(WebSession webSession);

}
