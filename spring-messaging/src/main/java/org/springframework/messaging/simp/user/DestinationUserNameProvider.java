/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */
package org.springframework.messaging.simp.user;

/**
 * A {@link java.security.Principal} can also implement this contract when
 * {@link java.security.Principal#getName() getName()} isn't globally unique
 * and therefore not suited for use with "user" destinations.
 *
 * @author Rossen Stoyanchev
 * @since 4.0.1
 * @see org.springframework.messaging.simp.user.UserDestinationResolver
 */
/**
 * 当{@link  java.security.Principal＃getName（）getName（）}不是全局唯一的，因此不适合与"用户"一起使用时，{<@link> java.security.Principal}也可以实现此合同。 
 *  "目的地。 
 *  @author  Rossen Stoyanchev @4.0.1起
 * @see  org.springframework.messaging.simp.user.UserDestinationResolver
 */
public interface DestinationUserNameProvider {

	/**
	 * Return a globally unique user name for use with "user" destinations.
	 */
	/**
	 * 返回全局唯一的用户名，以用于"用户"目标。 
	 * 
	 */
	String getDestinationUserName();

}
