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
package org.springframework.web.context.request;

/**
 * Extends {@code WebRequestInterceptor} with a callback method invoked during
 * asynchronous request handling.
 *
 * <p>When a handler starts asynchronous request handling, the DispatcherServlet
 * exits without invoking {@code postHandle} and {@code afterCompletion}, as it
 * normally does, since the results of request handling (e.g. ModelAndView) are
 * not available in the current thread and handling is not yet complete.
 * In such scenarios, the {@link #afterConcurrentHandlingStarted(WebRequest)}
 * method is invoked instead allowing implementations to perform tasks such as
 * cleaning up thread bound attributes.
 *
 * <p>When asynchronous handling completes, the request is dispatched to the
 * container for further processing. At this stage the DispatcherServlet invokes
 * {@code preHandle}, {@code postHandle} and {@code afterCompletion} as usual.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 *
 * @see org.springframework.web.context.request.async.WebAsyncManager
 */
/**
 * 使用在异步请求处理期间调用的回调方法扩展{@code  WebRequestInterceptor}。 
 *  <p>当处理程序开始异步请求处理时，因为不存在请求处理的结果（例如ModelAndView），所以像通常一样，DispatcherServlet退出而没有调用{@code  postHandle}和{@code  afterCompletion}在当前线程中，并且处理尚未完成。 
 * 在这种情况下，将调用{@link  #afterConcurrentHandlingStarted（WebRequest）}方法，而不是允许实现执行诸如清除线程绑定属性之类的任务。 
 *  <p>异步处理完成后，请求将分派到容器中进行进一步处理。 
 * 在此阶段，DispatcherServlet照常调用{@code  preHandle}，{<@code> postHandle}和{@code  afterCompletion}。 
 *  @author  Rossen Stoyanchev @从3.2起
 * @see  org.springframework.web.context.request.async.WebAsyncManager
 */
public interface AsyncWebRequestInterceptor extends WebRequestInterceptor{

	/**
	 * Called instead of {@code postHandle} and {@code afterCompletion}, when the
	 * handler started handling the request concurrently.
	 *
	 * @param request the current request
	 */
	/**
	 * 当处理程序开始并发处理请求时，调用而不是{@code  postHandle}和{@code  afterCompletion}。 
	 *  
	 * @param 请求当前请求
	 */
	void afterConcurrentHandlingStarted(WebRequest request);

}
