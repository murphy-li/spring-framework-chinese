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

package org.springframework.scheduling.config;

/**
 * Specialization of {@link IntervalTask} for fixed-rate semantics.
 *
 * @author Juergen Hoeller
 * @since 5.0.2
 * @see org.springframework.scheduling.annotation.Scheduled#fixedRate()
 * @see ScheduledTaskRegistrar#addFixedRateTask(IntervalTask)
 */
/**
 * {@link  IntervalTask​​}专门用于固定速率语义。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@从5.0.2起
 * @see  org.springframework.scheduling.annotation.Scheduled＃fixedRate（）
 * @see  ScheduledTaskRegistrar＃addFixedRateTask（IntervalTask​​）
 */
public class FixedRateTask extends IntervalTask {

	/**
	 * Create a new {@code FixedRateTask}.
	 * @param runnable the underlying task to execute
	 * @param interval how often in milliseconds the task should be executed
	 * @param initialDelay the initial delay before first execution of the task
	 */
	/**
	 * 创建一个新的{@code  FixedRateTask}。 
	 *  
	 * @param 可运行基础任务以执行
	 * @param 间隔，应执行任务的频率（以毫秒为单位）
	 * @param  initialDelay首次执行任务之前的初始延迟
	 */
	public FixedRateTask(Runnable runnable, long interval, long initialDelay) {
		super(runnable, interval, initialDelay);
	}

}
