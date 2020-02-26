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

package org.springframework.messaging.handler.invocation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.core.MethodParameter;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Support for {@link CompletableFuture} (and as of 4.3.7 also {@link CompletionStage})
 * as a return value type.
 *
 * @author Sebastien Deleuze
 * @author Juergen Hoeller
 * @since 4.2
 */
/**
 * 支持{@link  CompletableFuture}（从4.3.7版本开始还支持{@link  CompletionStage}）作为返回值类型。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Juergen Hoeller @4.2起
 */
public class CompletableFutureReturnValueHandler extends AbstractAsyncReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return CompletionStage.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	@SuppressWarnings("unchecked")
	public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
		return new CompletableToListenableFutureAdapter<>((CompletionStage<Object>) returnValue);
	}

}
