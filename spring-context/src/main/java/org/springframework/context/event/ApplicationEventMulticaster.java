/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * Interface to be implemented by objects that can manage a number of
 * {@link ApplicationListener} objects and publish events to them.
 *
 * <p>An {@link org.springframework.context.ApplicationEventPublisher}, typically
 * a Spring {@link org.springframework.context.ApplicationContext}, can use an
 * {@code ApplicationEventMulticaster} as a delegate for actually publishing events.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @see ApplicationListener
 */
/**
 * 由可以管理多个{@link  ApplicationListener}对象并向其发布事件的对象实现的接口。 
 *  <p> {<@link> org.springframework.context.ApplicationEventPublisher}（通常是Spring {@link  org.springframework.context.ApplicationContext}）可以使用{@code  ApplicationEventMulticaster}作为实际的委托发布事件。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Stephane Nicoll 
 * @see  ApplicationListener
 */
public interface ApplicationEventMulticaster {

	/**
	 * Add a listener to be notified of all events.
	 * @param listener the listener to add
	 */
	/**
	 * 添加一个侦听器以通知所有事件。 
	 *  
	 * @param 侦听器要添加的侦听器
	 */
	void addApplicationListener(ApplicationListener<?> listener);

	/**
	 * Add a listener bean to be notified of all events.
	 * @param listenerBeanName the name of the listener bean to add
	 */
	/**
	 * 添加一个侦听器bean，以通知所有事件。 
	 *  
	 * @param  listenerBeanName要添加的侦听器bean的名称
	 */
	void addApplicationListenerBean(String listenerBeanName);

	/**
	 * Remove a listener from the notification list.
	 * @param listener the listener to remove
	 */
	/**
	 * 从通知列表中删除一个侦听器。 
	 *  
	 * @param 侦听器要删除的侦听器
	 */
	void removeApplicationListener(ApplicationListener<?> listener);

	/**
	 * Remove a listener bean from the notification list.
	 * @param listenerBeanName the name of the listener bean to remove
	 */
	/**
	 * 从通知列表中删除一个侦听器bean。 
	 *  
	 * @param  listenerBeanName要删除的侦听器bean的名称
	 */
	void removeApplicationListenerBean(String listenerBeanName);

	/**
	 * Remove all listeners registered with this multicaster.
	 * <p>After a remove call, the multicaster will perform no action
	 * on event notification until new listeners are registered.
	 */
	/**
	 * 删除在此多播器上注册的所有侦听器。 
	 *  <p>在删除呼叫之后，多播器将不对事件通知执行任何操作，直到注册新的侦听器为止。 
	 * 
	 */
	void removeAllListeners();

	/**
	 * Multicast the given application event to appropriate listeners.
	 * <p>Consider using {@link #multicastEvent(ApplicationEvent, ResolvableType)}
	 * if possible as it provides better support for generics-based events.
	 * @param event the event to multicast
	 */
	/**
	 * 将给定的应用程序事件多播到适当的侦听器。 
	 *  <p>请考虑使用{@link  #multicastEvent（ApplicationEvent，ResolvableType）}，因为它为基于泛型的事件提供了更好的支持。 
	 *  
	 * @param 事件事件要多播
	 */
	void multicastEvent(ApplicationEvent event);

	/**
	 * Multicast the given application event to appropriate listeners.
	 * <p>If the {@code eventType} is {@code null}, a default type is built
	 * based on the {@code event} instance.
	 * @param event the event to multicast
	 * @param eventType the type of event (can be {@code null})
	 * @since 4.2
	 */
	/**
	 * 将给定的应用程序事件多播到适当的侦听器。 
	 *  <p>如果{@code  eventType}为{@code  null}，则会基于{@code  event}实例构建默认类型。 
	 *  
	 * @param 事件要多播的事件
	 * @param  eventType事件类型（可以为{@code  null}）@4.2起
	 */
	void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType);

}
