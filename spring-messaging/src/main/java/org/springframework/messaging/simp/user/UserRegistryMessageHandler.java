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

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

/**
 * {@code MessageHandler} that handles user registry broadcasts from other
 * application servers and periodically broadcasts the content of the local
 * user registry.
 *
 * <p>The aggregated information is maintained in a {@link MultiServerUserRegistry}.
 *
 * @author Rossen Stoyanchev
 * @since 4.2
 */
/**
 * {@code  MessageHandler}处理来自其他应用程序服务器的用户注册表广播，并定期广播本地用户注册表的内容。 
 *  <p>聚合的信息保存在{@link  MultiServerUserRegistry}中。 
 *  @author  Rossen Stoyanchev @从4.2开始
 */
public class UserRegistryMessageHandler implements MessageHandler, ApplicationListener<BrokerAvailabilityEvent> {

	private final MultiServerUserRegistry userRegistry;

	private final SimpMessagingTemplate brokerTemplate;

	private final String broadcastDestination;

	private final TaskScheduler scheduler;

	private final UserRegistryTask schedulerTask = new UserRegistryTask();

	@Nullable
	private volatile ScheduledFuture<?> scheduledFuture;

	private long registryExpirationPeriod = TimeUnit.SECONDS.toMillis(20);


	/**
	 * Constructor.
	 * @param userRegistry the registry with local and remote user registry information
	 * @param brokerTemplate template for broadcasting local registry information
	 * @param broadcastDestination the destination to broadcast to
	 * @param scheduler the task scheduler to use
	 */
	/**
	 * 构造函数。 
	 *  
	 * @param  userRegistry使用本地和远程用户注册表信息注册注册表
	 * @param  brokerTemplate模板，用于广播本地注册表信息
	 * @param  broadcastDestination要向任务调度程序使用的目标@
	 * @param>调度程序广播的目标
	 */
	public UserRegistryMessageHandler(MultiServerUserRegistry userRegistry,
			SimpMessagingTemplate brokerTemplate, String broadcastDestination, TaskScheduler scheduler) {

		Assert.notNull(userRegistry, "'userRegistry' is required");
		Assert.notNull(brokerTemplate, "'brokerTemplate' is required");
		Assert.hasText(broadcastDestination, "'broadcastDestination' is required");
		Assert.notNull(scheduler, "'scheduler' is required");

		this.userRegistry = userRegistry;
		this.brokerTemplate = brokerTemplate;
		this.broadcastDestination = broadcastDestination;
		this.scheduler = scheduler;
	}


	/**
	 * Return the configured destination for broadcasting UserRegistry information.
	 */
	/**
	 * 返回配置的目的地，以广播UserRegistry信息。 
	 * 
	 */
	public String getBroadcastDestination() {
		return this.broadcastDestination;
	}

	/**
	 * Configure the amount of time (in milliseconds) before a remote user
	 * registry snapshot is considered expired.
	 * <p>By default this is set to 20 seconds (value of 20000).
	 * @param milliseconds the expiration period in milliseconds
	 */
	/**
	 * 配置远程用户注册表快照被视为过期之前的时间（以毫秒为单位）。 
	 *  <p>默认情况下，它设置为20秒（值20000）。 
	 *  
	 * @param 毫秒有效期限（以毫秒为单位）
	 */
	@SuppressWarnings("unused")
	public void setRegistryExpirationPeriod(long milliseconds) {
		this.registryExpirationPeriod = milliseconds;
	}

	/**
	 * Return the configured registry expiration period.
	 */
	/**
	 * 返回配置的注册表有效期限。 
	 * 
	 */
	public long getRegistryExpirationPeriod() {
		return this.registryExpirationPeriod;
	}


	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		if (event.isBrokerAvailable()) {
			long delay = getRegistryExpirationPeriod() / 2;
			this.scheduledFuture = this.scheduler.scheduleWithFixedDelay(this.schedulerTask, delay);
		}
		else {
			ScheduledFuture<?> future = this.scheduledFuture;
			if (future != null ){
				future.cancel(true);
				this.scheduledFuture = null;
			}
		}
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		MessageConverter converter = this.brokerTemplate.getMessageConverter();
		this.userRegistry.addRemoteRegistryDto(message, converter, getRegistryExpirationPeriod());
	}


	private class UserRegistryTask implements Runnable {

		@Override
		public void run() {
			try {
				SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
				accessor.setHeader(SimpMessageHeaderAccessor.IGNORE_ERROR, true);
				accessor.setLeaveMutable(true);
				Object payload = userRegistry.getLocalRegistryDto();
				brokerTemplate.convertAndSend(getBroadcastDestination(), payload, accessor.getMessageHeaders());
			}
			finally {
				userRegistry.purgeExpiredRegistries();
			}
		}
	}

}
