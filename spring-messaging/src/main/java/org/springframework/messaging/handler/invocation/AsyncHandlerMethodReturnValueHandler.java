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

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * An extension of {@link HandlerMethodReturnValueHandler} for handling async,
 * Future-like return value types that support success and error callbacks.
 * Essentially anything that can be adapted to a {@link ListenableFuture}.
 *
 * <p>Implementations should consider extending the convenient base class
 * {@link AbstractAsyncReturnValueHandler}.
 *
 * @author Rossen Stoyanchev
 * @since 4.2
 * @see AbstractAsyncReturnValueHandler
 */
/**
 * {@link  HandlerMethodReturnValueHandler}的扩展，用于处理支持成功和错误回调的异步，类似Future的返回值类型。 
 * 基本上可以适应{@link  ListenableFuture}的所有内容。 
 *  <p>实施应考虑扩展方便的基类{@link  AbstractAsyncReturnValueHandler}。 
 *  @author  Rossen Stoyanchev @从4.2起
 * @see  AbstractAsyncReturnValueHandler
 */
public interface AsyncHandlerMethodReturnValueHandler extends HandlerMethodReturnValueHandler {

	/**
	 * Whether the return value represents an asynchronous, Future-like type
	 * with success and error callbacks. If this method returns {@code true},
	 * then {@link #toListenableFuture} is invoked next. If it returns
	 * {@code false}, then {@link #handleReturnValue} is called.
	 * <p><strong>Note:</strong> this method will only be invoked after
	 * {@link #supportsReturnType(org.springframework.core.MethodParameter)}
	 * is called and it returns {@code true}.
	 * @param returnValue the value returned from the handler method
	 * @param returnType the type of the return value
	 * @return {@code true} if the return value type represents an async value
	 */
	/**
	 * 返回值是否表示具有成功和错误回调的异步，类似Future的类型。 
	 * 如果此方法返回{@code  true}，则接下来将调用{@link  #toListenableFuture}。 
	 * 如果返回{@code  false}，则会调用{@link  #handleReturnValue}。 
	 *  <p> <strong>注意</ strong>：只有在调用{@link  #supportsReturnType（org.springframework.core.MethodParameter）}并返回{@code  true}后，才会调用此方法。 
	 *  
	 * @param  returnValue从处理程序方法返回的值
	 * @param  returnType返回值的类型
	 * @return  {@code  true}，如果返回值类型表示异步值
	 */
	boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType);

	/**
	 * Adapt the asynchronous return value to a {@link ListenableFuture}.
	 * Implementations should consider returning an instance of
	 * {@link org.springframework.util.concurrent.SettableListenableFuture
	 * SettableListenableFuture}. Return value handling will then continue when
	 * the ListenableFuture is completed with either success or error.
	 * <p><strong>Note:</strong> this method will only be invoked after
	 * {@link #supportsReturnType(org.springframework.core.MethodParameter)}
	 * is called and it returns {@code true}.
	 * @param returnValue the value returned from the handler method
	 * @param returnType the type of the return value
	 * @return the resulting ListenableFuture, or {@code null} in which case
	 * no further handling will be performed
	 */
	/**
	 * 将异步返回值调整为{@link  ListenableFuture}。 
	 * 实现应考虑返回{@link  org.springframework.util.concurrent.SettableListenableFuture SettableListenableFuture}的实例。 
	 * 当ListenableFuture成功完成或发生错误时，返回值处理将继续。 
	 *  <p> <strong>注意</ strong>：只有在调用{@link  #supportsReturnType（org.springframework.core.MethodParameter）}并返回{@code  true}后，才会调用此方法。 
	 *  
	 * @param  returnValue从处理程序方法返回的值
	 * @param  returnType返回值的类型
	 * @return 所得的ListenableFuture或{@code  null}，在这种情况下将不执行进一步的处理
	 */
	@Nullable
	ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType);

}
