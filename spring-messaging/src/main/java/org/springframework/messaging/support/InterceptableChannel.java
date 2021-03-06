/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.messaging.support;

import java.util.List;

/**
 * A {@link org.springframework.messaging.MessageChannel MessageChannel} that
 * maintains a list {@link org.springframework.messaging.support.ChannelInterceptor
 * ChannelInterceptors} and allows interception of message sending.
 *
 * @author Rossen Stoyanchev
 * @since 4.1
 */
/**
 * 一个{@link  org.springframework.messaging.MessageChannel MessageChannel}，它维护一个列表{@link  org.springframework.messaging.support.ChannelInterceptor ChannelInterceptors}并允许截取消息。 
 *  @author  Rossen Stoyanchev @从4.1开始
 */
public interface InterceptableChannel {

	/**
	 * Set the list of channel interceptors clearing any existing interceptors.
	 */
	/**
	 * 设置通道拦截器列表，清除所有现有的拦截器。 
	 * 
	 */
	void setInterceptors(List<ChannelInterceptor> interceptors);

	/**
	 * Add a channel interceptor to the end of the list.
	 */
	/**
	 * 将通道拦截器添加到列表的末尾。 
	 * 
	 */
	void addInterceptor(ChannelInterceptor interceptor);

	/**
	 * Add a channel interceptor at the specified index.
	 */
	/**
	 * 在指定的索引处添加通道拦截器。 
	 * 
	 */
	void addInterceptor(int index, ChannelInterceptor interceptor);

	/**
	 * Return the list of configured interceptors.
	 */
	/**
	 * 返回已配置的拦截器列表。 
	 * 
	 */
	List<ChannelInterceptor> getInterceptors();

	/**
	 * Remove the given interceptor.
	 */
	/**
	 * 删除给定的拦截器。 
	 * 
	 */
	boolean removeInterceptor(ChannelInterceptor interceptor);

	/**
	 * Remove the interceptor at the given index.
	 */
	/**
	 * 删除给定索引处的拦截器。 
	 * 
	 */
	ChannelInterceptor removeInterceptor(int index);

}
