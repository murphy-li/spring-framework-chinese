/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.scheduling;

import java.util.Date;

import org.springframework.lang.Nullable;

/**
 * Common interface for trigger objects that determine the next execution time
 * of a task that they get associated with.
 *
 * @author Juergen Hoeller
 * @since 3.0
 * @see TaskScheduler#schedule(Runnable, Trigger)
 * @see org.springframework.scheduling.support.CronTrigger
 */
/**
 * 触发对象的公共接口，这些接口确定与它们关联的任务的下一次执行时间。 
 *  @author  Juergen Hoeller @since 3.0起
 * @see  TaskScheduler＃schedule（Runnable，Trigger）
 * @see  org.springframework.scheduling.support.CronTrigger
 */
public interface Trigger {

	/**
	 * Determine the next execution time according to the given trigger context.
	 * @param triggerContext context object encapsulating last execution times
	 * and last completion time
	 * @return the next execution time as defined by the trigger,
	 * or {@code null} if the trigger won't fire anymore
	 */
	/**
	 * 根据给定的触发上下文确定下一个执行时间。 
	 *  
	 * @param  triggerContext上下文对象，封装了触发器定义的上次执行时间和上次完成时间
	 * @return 下一个执行时间； 
	 * 如果触发器不再触发，则返回{@code  null}
	 */
	@Nullable
	Date nextExecutionTime(TriggerContext triggerContext);

}
