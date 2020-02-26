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

package org.springframework.core.task;

import java.util.concurrent.Callable;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * Extension of the {@link AsyncTaskExecutor} interface, adding the capability to submit
 * tasks for {@link ListenableFuture ListenableFutures}.
 *
 * @author Arjen Poutsma
 * @since 4.0
 * @see ListenableFuture
 */
/**
 * {@link  AsyncTaskExecutor}接口的扩展，增加了为{@link  ListenableFuture ListenableFutures}提交任务的功能。 
 *  @author  Arjen Poutsma @从4.0开始
 * @see  ListenableFuture
 */
public interface AsyncListenableTaskExecutor extends AsyncTaskExecutor {

	/**
	 * Submit a {@code Runnable} task for execution, receiving a {@code ListenableFuture}
	 * representing that task. The Future will return a {@code null} result upon completion.
	 * @param task the {@code Runnable} to execute (never {@code null})
	 * @return a {@code ListenableFuture} representing pending completion of the task
	 * @throws TaskRejectedException if the given task was not accepted
	 */
	/**
	 * 提交要执行的{@code  Runnable}任务，并收到代表该任务的{@code  ListenableFuture}。 
	 * 完成后，Future将返回{@code  null}结果。 
	 *  {
	 * @param>任务{@code  Runnable}的执行（决不执行{@code  null}）
	 * @return 一个{@code  ListenableFuture}，代表任务的未完成完成
	 * @throws  TaskRejectedException给定的任务不被接受
	 */
	ListenableFuture<?> submitListenable(Runnable task);

	/**
	 * Submit a {@code Callable} task for execution, receiving a {@code ListenableFuture}
	 * representing that task. The Future will return the Callable's result upon
	 * completion.
	 * @param task the {@code Callable} to execute (never {@code null})
	 * @return a {@code ListenableFuture} representing pending completion of the task
	 * @throws TaskRejectedException if the given task was not accepted
	 */
	/**
	 * 提交要执行的{@code  Callable}任务，并接收代表该任务的{@code  ListenableFuture}。 
	 * 期货将在完成后返回可赎回债券的结果。 
	 *  
	 * @param 任务{{@@code>可调用}来执行（从不执行{@code  null}）
	 * @return 一个{@code  ListenableFuture}，表示任务待完成<< throws> TaskRejectedException，如果给定的任务不被接受
	 */
	<T> ListenableFuture<T> submitListenable(Callable<T> task);

}
