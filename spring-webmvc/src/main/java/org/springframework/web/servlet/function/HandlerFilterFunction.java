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

package org.springframework.web.servlet.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.util.Assert;

/**
 * Represents a function that filters a {@linkplain HandlerFunction handler function}.
 *
 * @author Arjen Poutsma
 * @since 5.2
 * @param <T> the type of the {@linkplain HandlerFunction handler function} to filter
 * @param <R> the type of the response of the function
 * @see RouterFunction#filter(HandlerFilterFunction)
 */
/**
 * 表示一个过滤{@link  plain HandlerFunction handler function}的函数。 
 *  @author  Arjen Poutsma @自5.2起
 * @param  <T> {@link  plain HandlerFunction handler function}的类型以过滤
 * @param  <R>函数响应的类型<
 * @see > RouterFunction＃filter（HandlerFilterFunction）
 */
@FunctionalInterface
public interface HandlerFilterFunction<T extends ServerResponse, R extends ServerResponse> {

	/**
	 * Apply this filter to the given handler function. The given
	 * {@linkplain HandlerFunction handler function} represents the next entity in the chain,
	 * and can be {@linkplain HandlerFunction#handle(ServerRequest) invoked} in order to
	 * proceed to this entity, or not invoked to block the chain.
	 * @param request the request
	 * @param next the next handler or filter function in the chain
	 * @return the filtered response
	 */
	/**
	 * 将此过滤器应用于给定的处理函数。 
	 * 给定的{@link  plain HandlerFunction handler function}表示链中的下一个实体，可以进行{@link  plain HandlerFunction＃handle（ServerRequest）调用}，以继续进行此实体，或不进行调用以阻塞连锁，链条。 
	 *  
	 * @param 请求请求
	 * @param 链中下一个处理程序或过滤器函数的下一个
	 * @return 过滤后的响应
	 */
	R filter(ServerRequest request, HandlerFunction<T> next) throws Exception;

	/**
	 * Return a composed filter function that first applies this filter, and then applies the
	 * {@code after} filter.
	 * @param after the filter to apply after this filter is applied
	 * @return a composed filter that first applies this function and then applies the
	 * {@code after} function
	 */
	/**
	 * 返回一个组合的过滤器函数，该函数首先应用此过滤器，然后再应用{@code  after}过滤器。 
	 *  
	 * @param 在应用此过滤器之后要应用的过滤器之后
	 * @return 一个组合过滤器，该组合过滤器首先应用此功能，然后应用{@code  after}函数
	 */
	default HandlerFilterFunction<T, R> andThen(HandlerFilterFunction<T, T> after) {
		Assert.notNull(after, "HandlerFilterFunction must not be null");
		return (request, next) -> {
			HandlerFunction<T> nextHandler = handlerRequest -> after.filter(handlerRequest, next);
			return filter(request, nextHandler);
		};
	}

	/**
	 * Apply this filter to the given handler function, resulting in a filtered handler function.
	 * @param handler the handler function to filter
	 * @return the filtered handler function
	 */
	/**
	 * 将此过滤器应用于给定的处理函数，从而生成一个过滤处理函数。 
	 *  
	 * @param 处理程序过滤程序的处理程序功能
	 * @return 
	 */
	default HandlerFunction<R> apply(HandlerFunction<T> handler) {
		Assert.notNull(handler, "HandlerFunction must not be null");
		return request -> this.filter(request, handler);
	}

	/**
	 * Adapt the given request processor function to a filter function that only operates
	 * on the {@code ServerRequest}.
	 * @param requestProcessor the request processor
	 * @return the filter adaptation of the request processor
	 */
	/**
	 * 使给定的请求处理器功能适应仅在{@code  ServerRequest}上运行的筛选器功能。 
	 *  
	 * @param  requestProcessor请求处理器
	 * @return 请求处理器的过滤器适配
	 */
	static <T extends ServerResponse> HandlerFilterFunction<T, T>
	ofRequestProcessor(Function<ServerRequest, ServerRequest> requestProcessor) {

		Assert.notNull(requestProcessor, "Function must not be null");
		return (request, next) -> next.handle(requestProcessor.apply(request));
	}

	/**
	 * Adapt the given response processor function to a filter function that only operates
	 * on the {@code ServerResponse}.
	 * @param responseProcessor the response processor
	 * @return the filter adaptation of the request processor
	 */
	/**
	 * 使给定的响应处理器功能适应仅在{@code  ServerResponse}上运行的筛选器功能。 
	 *  
	 * @param  responseProcessor响应处理器
	 * @return 请求处理器的过滤器适配
	 */
	static <T extends ServerResponse, R extends ServerResponse> HandlerFilterFunction<T, R>
	ofResponseProcessor(BiFunction<ServerRequest, T, R> responseProcessor) {

		Assert.notNull(responseProcessor, "Function must not be null");
		return (request, next) -> responseProcessor.apply(request, next.handle(request));
	}

	/**
	 * Adapt the given predicate and response provider function to a filter function that returns
	 * a {@code ServerResponse} on a given exception.
	 * @param predicate the predicate to match an exception
	 * @param errorHandler the response provider
	 * @return the filter adaption of the error handler
	 */
	/**
	 * 使给定的谓词和响应提供程序功能适应于在给定异常上返回{@code  ServerResponse}的筛选器功能。 
	 *  
	 * @param 谓词匹配异常
	 * @param  errorHandler响应提供者
	 * @return 错误处理程序的过滤器适应
	 */
	static <T extends ServerResponse> HandlerFilterFunction<T, T>
	ofErrorHandler(Predicate<Throwable> predicate, BiFunction<Throwable, ServerRequest, T> errorHandler) {

		Assert.notNull(predicate, "Predicate must not be null");
		Assert.notNull(errorHandler, "ErrorHandler must not be null");

		return (request, next) -> {
			try {
				T t = next.handle(request);
				if (t instanceof DefaultServerResponseBuilder.AbstractServerResponse) {
					((DefaultServerResponseBuilder.AbstractServerResponse) t)
							.addErrorHandler(predicate, errorHandler);
				}
				return t;
			}
			catch (Throwable throwable) {
				if (predicate.test(throwable)) {
					return errorHandler.apply(throwable, request);
				}
				else {
					throw throwable;
				}
			}
		};
	}

}
