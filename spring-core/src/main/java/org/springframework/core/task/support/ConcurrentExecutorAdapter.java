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

package org.springframework.core.task.support;

import java.util.concurrent.Executor;

import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;

/**
 * Adapter that exposes the {@link java.util.concurrent.Executor} interface
 * for any Spring {@link org.springframework.core.task.TaskExecutor}.
 *
 * <p>This is less useful as of Spring 3.0, since TaskExecutor itself
 * extends the Executor interface. The adapter is only relevant for
 * <em>hiding</em> the TaskExecutor nature of a given object now,
 * solely exposing the standard Executor interface to a client.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see java.util.concurrent.Executor
 * @see org.springframework.core.task.TaskExecutor
 */
/**
 * 公开任何Spring {@link  org.springframework.core.task.TaskExecutor}的{@link  java.util.concurrent.Executor}接口的适配器。 
 *  <p>自Spring 3.0起，此功能不再有用，因为TaskExecutor本身扩展了Executor接口。 
 * 现在，适配器仅与<em>隐藏</ em>给定对象的TaskExecutor性质有关，仅将标准Executor接口公开给客户端。 
 *  @author  Juergen Hoeller @since 2.5起
 * @see  java.util.concurrent.Executor 
 * @see  org.springframework.core.task.TaskExecutor
 */
public class ConcurrentExecutorAdapter implements Executor {

	private final TaskExecutor taskExecutor;


	/**
	 * Create a new ConcurrentExecutorAdapter for the given Spring TaskExecutor.
	 * @param taskExecutor the Spring TaskExecutor to wrap
	 */
	/**
	 * 为给定的Spring TaskExecutor创建一个新的ConcurrentExecutorAdapter。 
	 *  
	 * @param  taskExecutor要包装的Spring TaskExecutor
	 */
	public ConcurrentExecutorAdapter(TaskExecutor taskExecutor) {
		Assert.notNull(taskExecutor, "TaskExecutor must not be null");
		this.taskExecutor = taskExecutor;
	}


	@Override
	public void execute(Runnable command) {
		this.taskExecutor.execute(command);
	}

}
