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

package org.springframework.messaging.handler.invocation;

import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.MonoToListenableFutureAdapter;

/**
 * Support for single-value reactive types (like {@code Mono} or {@code Single})
 * as a return value type.
 *
 * @author Sebastien Deleuze
 * @since 5.1
 */
/**
 * 支持将单值反应类型（例如{@code  Mono}或{<@@code> Single}）作为返回值类型。 
 *  @author 塞巴斯蒂安·德勒兹@5.1起
 */
public class ReactiveReturnValueHandler extends AbstractAsyncReturnValueHandler {

	private final ReactiveAdapterRegistry adapterRegistry;


	public ReactiveReturnValueHandler() {
		this(ReactiveAdapterRegistry.getSharedInstance());
	}

	public ReactiveReturnValueHandler(ReactiveAdapterRegistry adapterRegistry) {
		this.adapterRegistry = adapterRegistry;
	}


	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return (this.adapterRegistry.getAdapter(returnType.getParameterType()) != null);
	}

	@Override
	public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
		ReactiveAdapter adapter = this.adapterRegistry.getAdapter(returnType.getParameterType(), returnValue);
		return (adapter != null && !adapter.isMultiValue() && !adapter.isNoValue());
	}

	@Override
	public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
		ReactiveAdapter adapter = this.adapterRegistry.getAdapter(returnType.getParameterType(), returnValue);
		if (adapter != null) {
			return new MonoToListenableFutureAdapter<>(Mono.from(adapter.toPublisher(returnValue)));
		}
		return null;
	}

}
