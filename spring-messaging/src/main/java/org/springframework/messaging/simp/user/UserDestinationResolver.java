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

package org.springframework.messaging.simp.user;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

/**
 * A strategy for resolving a "user" destination by translating it to one or more
 * actual destinations one per active user session. When sending a message to a
 * user destination, the destination must contain the user name so it may be
 * extracted and used to look up the user sessions. When subscribing to a user
 * destination, the destination does not have to contain the user's own name.
 * We simply use the current session.
 *
 * <p>See implementation classes and the documentation for example destinations.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 *
 * @see org.springframework.messaging.simp.user.DefaultUserDestinationResolver
 * @see UserDestinationMessageHandler
 */
/**
 * 一种策略，通过在每个活动用户会话中将一个"用户"目标转换为一个或多个实际目标来解决。 
 * 在向用户目标发送消息时，目标必须包含用户名，以便可以提取它并将其用于查找用户会话。 
 * 订阅用户目的地时，目的地不必包含用户自己的名称。 
 * 我们只使用当前会话。 
 *  <p>有关示例目标，请参见实现类和文档。 
 *  @author  Rossen Stoyanchev @从4.0开始
 * @see  org.springframework.messaging.simp.user.DefaultUserDestinationResolver 
 * @see  UserDestinationMessageHandler
 */
@FunctionalInterface
public interface UserDestinationResolver {

	/**
	 * Resolve the given message with a user destination to one or more messages
	 * with actual destinations, one for each active user session.
	 * @param message the message to try to resolve
	 * @return 0 or more target messages (one for each active session), or
	 * {@code null} if the source message does not contain a user destination.
	 */
	/**
	 * 将具有用户目标的给定消息解析为一个或多个具有实际目标的消息，每个活动用户会话一个。 
	 *  
	 * @param 向消息发送消息，以尝试解决
	 * @return  0个或更多目标消息（每个活动会话一个），如果源消息不包含用户目的地，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	UserDestinationResult resolveDestination(Message<?> message);

}
