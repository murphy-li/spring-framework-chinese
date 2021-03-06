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

package org.springframework.web.context.request.async;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * Abstract adapter class for the {@link DeferredResultProcessingInterceptor}
 * interface for simplified implementation of individual methods.
 *
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 3.2
 * @deprecated as of 5.0 where DeferredResultProcessingInterceptor has default methods
 */
/**
 * {@link  DeferredResultProcessingInterceptor}接口的抽象适配器类，用于简化各个方法的实现。 
 *  @author  Rossen Stoyanchev @author  Rob Winch @自3.2起已弃用，其中DeferredResultProcessingInterceptor具有默认方法
 */
@Deprecated
public abstract class DeferredResultProcessingInterceptorAdapter implements DeferredResultProcessingInterceptor {

	/**
	 * This implementation is empty.
	 */
	/**
	 * 此实现为空。 
	 * 
	 */
	@Override
	public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> deferredResult)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	/**
	 * 此实现为空。 
	 * 
	 */
	@Override
	public <T> void preProcess(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	/**
	 * 此实现为空。 
	 * 
	 */
	@Override
	public <T> void postProcess(NativeWebRequest request, DeferredResult<T> deferredResult,
			Object concurrentResult) throws Exception {
	}

	/**
	 * This implementation returns {@code true} by default allowing other interceptors
	 * to be given a chance to handle the timeout.
	 */
	/**
	 * 默认情况下，此实现返回{@code  true}，使其他拦截器有机会处理超时。 
	 * 
	 */
	@Override
	public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
		return true;
	}

	/**
	 * This implementation returns {@code true} by default allowing other interceptors
	 * to be given a chance to handle the error.
	 */
	/**
	 * 默认情况下，此实现返回{@code  true}，使其他拦截器有机会处理该错误。 
	 * 
	 */
	@Override
	public <T> boolean handleError(NativeWebRequest request, DeferredResult<T> deferredResult, Throwable t)
			throws Exception {
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	/**
	 * 此实现为空。 
	 * 
	 */
	@Override
	public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
	}

}
