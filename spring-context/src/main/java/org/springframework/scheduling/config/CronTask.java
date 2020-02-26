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

import org.springframework.scheduling.support.CronTrigger;

/**
 * {@link TriggerTask} implementation defining a {@code Runnable} to be executed according
 * to a {@linkplain org.springframework.scheduling.support.CronSequenceGenerator standard
 * cron expression}.
 *
 * @author Chris Beams
 * @since 3.2
 * @see org.springframework.scheduling.annotation.Scheduled#cron()
 * @see ScheduledTaskRegistrar#addCronTask(CronTask)
 */
/**
 * {@link  TriggerTask}实现定义了根据{@link  plain org.springframework.scheduling.support.CronSequenceGenerator标准cron表达式}执行的{@code  Runnable}。 
 *  @author 克里斯·比姆斯（Chris Beams）自3.2起
 * @see  org.springframework.scheduling.annotation.Scheduled＃cron（）
 * @see  ScheduledTaskRegistrar＃addCronTask（CronTask）
 */
public class CronTask extends TriggerTask {

	private final String expression;


	/**
	 * Create a new {@code CronTask}.
	 * @param runnable the underlying task to execute
	 * @param expression the cron expression defining when the task should be executed
	 */
	/**
	 * 创建一个新的{@code  CronTask}。 
	 *  
	 * @param 可运行基础任务以执行
	 * @param 表达式cron表达式定义何时应执行任务
	 */
	public CronTask(Runnable runnable, String expression) {
		this(runnable, new CronTrigger(expression));
	}

	/**
	 * Create a new {@code CronTask}.
	 * @param runnable the underlying task to execute
	 * @param cronTrigger the cron trigger defining when the task should be executed
	 */
	/**
	 * 创建一个新的{@code  CronTask}。 
	 *  
	 * @param 可运行基础任务以执行
	 * @param  cron触发cron触发器，定义何时应执行任务
	 */
	public CronTask(Runnable runnable, CronTrigger cronTrigger) {
		super(runnable, cronTrigger);
		this.expression = cronTrigger.getExpression();
	}


	/**
	 * Return the cron expression defining when the task should be executed.
	 */
	/**
	 * 返回定义任务何时执行的cron表达式。 
	 * 
	 */
	public String getExpression() {
		return this.expression;
	}

}
