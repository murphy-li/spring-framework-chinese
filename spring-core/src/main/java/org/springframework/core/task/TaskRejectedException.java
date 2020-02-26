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

package org.springframework.core.task;

import java.util.concurrent.RejectedExecutionException;

/**
 * Exception thrown when a {@link TaskExecutor} rejects to accept
 * a given task for execution.
 *
 * @author Juergen Hoeller
 * @since 2.0.1
 * @see TaskExecutor#execute(Runnable)
 * @see TaskTimeoutException
 */
/**
 * 当{@link  TaskExecutor}拒绝接受给定任务以执行时引发的异常。 
 *  @author  Juergen Hoeller @2.0.1起
 * @see  TaskExecutor＃execute（Runnable）
 * @see  TaskTimeoutException
 */
@SuppressWarnings("serial")
public class TaskRejectedException extends RejectedExecutionException {

	/**
	 * Create a new {@code TaskRejectedException}
	 * with the specified detail message and no root cause.
	 * @param msg the detail message
	 */
	/**
	 * 使用指定的详细消息创建新的{@code  TaskRejectedException}，没有根本原因。 
	 *  
	 * @param  msg详细信息
	 */
	public TaskRejectedException(String msg) {
		super(msg);
	}

	/**
	 * Create a new {@code TaskRejectedException}
	 * with the specified detail message and the given root cause.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using an underlying
	 * API such as the {@code java.util.concurrent} package)
	 * @see java.util.concurrent.RejectedExecutionException
	 */
	/**
	 * 使用指定的详细消息和给定的根本原因创建一个新的{@code  TaskRejectedException}。 
	 *  
	 * @param  msg详细消息
	 * @param 引起根本原因（通常是使用诸如{@code  java.util.concurrent}包之类的基础API）
	 * @see  java.util.concurrent.RejectedExecutionException
	 */
	public TaskRejectedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
