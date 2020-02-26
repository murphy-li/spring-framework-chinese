/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.scheduling.commonj;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import commonj.timers.Timer;
import commonj.timers.TimerManager;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.Lifecycle;
import org.springframework.lang.Nullable;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that retrieves a
 * CommonJ {@link commonj.timers.TimerManager} and exposes it for bean references.
 *
 * <p><b>This is the central convenience class for setting up a
 * CommonJ TimerManager in a Spring context.</b>
 *
 * <p>Allows for registration of ScheduledTimerListeners. This is the main
 * purpose of this class; the TimerManager itself could also be fetched
 * from JNDI via {@link org.springframework.jndi.JndiObjectFactoryBean}.
 * In scenarios that just require static registration of tasks at startup,
 * there is no need to access the TimerManager itself in application code.
 *
 * <p>Note that the TimerManager uses a TimerListener instance that is
 * shared between repeated executions, in contrast to Quartz which
 * instantiates a new Job for each execution.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see ScheduledTimerListener
 * @see commonj.timers.TimerManager
 * @see commonj.timers.TimerListener
 * @deprecated as of 5.1, in favor of EE 7's
 * {@link org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler}
 */
/**
 * {@link  org.springframework.beans.factory.FactoryBean}检索CommonJ {@link  commonj.timers.TimerManager}并将其公开以供bean参考。 
 *  <p> <b>这是在Spring上下文中设置CommonJ TimerManager的中央便利类。 
 * </ b> <p>允许注册ScheduledTimerListeners。 
 * 这是本课程的主要目的； 
 * 也可以通过{@link  org.springframework.jndi.JndiObjectFactoryBean}从JNDI获取TimerManager本身。 
 * 在只需要在启动时静态注册任务的情况下，无需在应用程序代码中访问TimerManager本身。 
 *  <p>请注意，与Quartz相比，TimerManager使用在重复执行之间共享的TimerListener实例，该实例为每次执行实例化一个新Job。 
 *  @author  Juergen Hoeller @since 2.0起
 * @see  ScheduledTimerListener 
 * @see  commonj.timers.TimerManager 
 * @see  commonj.timers.TimerListener @从5.1开始不推荐使用，而推荐使用EE 7的{@link  org。 
 *  springframework.scheduling.concurrent.DefaultManagedTaskScheduler}
 */
@Deprecated
public class TimerManagerFactoryBean extends TimerManagerAccessor
		implements FactoryBean<TimerManager>, InitializingBean, DisposableBean, Lifecycle {

	@Nullable
	private ScheduledTimerListener[] scheduledTimerListeners;

	private final List<Timer> timers = new LinkedList<>();


	/**
	 * Register a list of ScheduledTimerListener objects with the TimerManager
	 * that this FactoryBean creates. Depending on each ScheduledTimerListener's settings,
	 * it will be registered via one of TimerManager's schedule methods.
	 * @see commonj.timers.TimerManager#schedule(commonj.timers.TimerListener, long)
	 * @see commonj.timers.TimerManager#schedule(commonj.timers.TimerListener, long, long)
	 * @see commonj.timers.TimerManager#scheduleAtFixedRate(commonj.timers.TimerListener, long, long)
	 */
	/**
	 * 使用此FactoryBean创建的TimerManager注册ScheduledTimerListener对象的列表。 
	 * 根据每个ScheduledTimerListener的设置，它将通过TimerManager的调度方法之一进行注册。 
	 *  
	 * @see  commonj.timers.TimerManager＃schedule（commonj.timers.TimerListener，长）
	 * @see  commonj.timers.TimerManager＃schedule（commonj.timers.TimerListener，long，long）
	 * @see  commonj.timers。 
	 *  TimerManager＃scheduleAtFixedRate（commonj.timers.TimerListener，长，长）
	 */
	public void setScheduledTimerListeners(ScheduledTimerListener[] scheduledTimerListeners) {
		this.scheduledTimerListeners = scheduledTimerListeners;
	}


	//---------------------------------------------------------------------
	// Implementation of InitializingBean interface
	//---------------------------------------------------------------------

	@Override
	public void afterPropertiesSet() throws NamingException {
		super.afterPropertiesSet();

		if (this.scheduledTimerListeners != null) {
			TimerManager timerManager = obtainTimerManager();
			for (ScheduledTimerListener scheduledTask : this.scheduledTimerListeners) {
				Timer timer;
				if (scheduledTask.isOneTimeTask()) {
					timer = timerManager.schedule(scheduledTask.getTimerListener(), scheduledTask.getDelay());
				}
				else {
					if (scheduledTask.isFixedRate()) {
						timer = timerManager.scheduleAtFixedRate(
								scheduledTask.getTimerListener(), scheduledTask.getDelay(), scheduledTask.getPeriod());
					}
					else {
						timer = timerManager.schedule(
								scheduledTask.getTimerListener(), scheduledTask.getDelay(), scheduledTask.getPeriod());
					}
				}
				this.timers.add(timer);
			}
		}
	}


	//---------------------------------------------------------------------
	// Implementation of FactoryBean interface
	//---------------------------------------------------------------------

	@Override
	@Nullable
	public TimerManager getObject() {
		return getTimerManager();
	}

	@Override
	public Class<? extends TimerManager> getObjectType() {
		TimerManager timerManager = getTimerManager();
		return (timerManager != null ? timerManager.getClass() : TimerManager.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	//---------------------------------------------------------------------
	// Implementation of DisposableBean interface
	//---------------------------------------------------------------------

	/**
	 * Cancels all statically registered Timers on shutdown,
	 * and stops the underlying TimerManager (if not shared).
	 * @see commonj.timers.Timer#cancel()
	 * @see commonj.timers.TimerManager#stop()
	 */
	/**
	 * 关机时取消所有静态注册的Timer，并停止基础TimerManager（如果未共享）。 
	 *  
	 * @see  commonj.timers.Timer＃cancel（）
	 * @see  commonj.timers.TimerManager＃stop（）
	 */
	@Override
	public void destroy() {
		// Cancel all registered timers.
		for (Timer timer : this.timers) {
			try {
				timer.cancel();
			}
			catch (Throwable ex) {
				logger.debug("Could not cancel CommonJ Timer", ex);
			}
		}
		this.timers.clear();

		// Stop the TimerManager itself.
		super.destroy();
	}

}
