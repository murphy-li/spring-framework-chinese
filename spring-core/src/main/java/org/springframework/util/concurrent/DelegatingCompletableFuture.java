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

package org.springframework.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.util.Assert;

/**
 * Extension of {@link CompletableFuture} which allows for cancelling
 * a delegate along with the {@link CompletableFuture} itself.
 *
 * @author Juergen Hoeller
 * @since 5.0
 * @param <T> the result type returned by this Future's {@code get} method
 */
/**
 * {@link  CompletableFuture}的扩展允许取消{@link  CompletableFuture}本身的委托。 
 *  @author  Juergen Hoeller @自5.0起
 * @param  <T>此Future的{@code  get}方法返回的结果类型
 */
class DelegatingCompletableFuture<T> extends CompletableFuture<T> {

	private final Future<T> delegate;


	public DelegatingCompletableFuture(Future<T> delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}


	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		boolean result = this.delegate.cancel(mayInterruptIfRunning);
		super.cancel(mayInterruptIfRunning);
		return result;
	}

}
