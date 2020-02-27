/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.server.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebHandler;

/**
 * WebHandler decorator that invokes one or more {@link WebExceptionHandler WebExceptionHandlers}
 * after the delegate {@link WebHandler}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * WebHandler装饰器，在委托{@link  WebHandler}之后调用一个或多个{@link  WebExceptionHandler WebExceptionHandlers}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class ExceptionHandlingWebHandler extends WebHandlerDecorator {

	private final List<WebExceptionHandler> exceptionHandlers;


	/**
	 * Create an {@code ExceptionHandlingWebHandler} for the given delegate.
	 * @param delegate the WebHandler delegate
	 * @param handlers the WebExceptionHandlers to apply
	 */
	/**
	 * 为给定的委托创建一个{@code  ExceptionHandlingWebHandler}。 
	 *  
	 * @param 委托WebHandler委托
	 * @param 处理器要应用的WebExceptionHandlers
	 */
	public ExceptionHandlingWebHandler(WebHandler delegate, List<WebExceptionHandler> handlers) {
		super(delegate);
		List<WebExceptionHandler> handlersToUse = new ArrayList<>();
		handlersToUse.add(new CheckpointInsertingHandler());
		handlersToUse.addAll(handlers);
		this.exceptionHandlers = Collections.unmodifiableList(handlersToUse);
	}


	/**
	 * Return a read-only list of the configured exception handlers.
	 */
	/**
	 * 返回配置的异常处理程序的只读列表。 
	 * 
	 */
	public List<WebExceptionHandler> getExceptionHandlers() {
		return this.exceptionHandlers;
	}


	@Override
	public Mono<Void> handle(ServerWebExchange exchange) {
		Mono<Void> completion;
		try {
			completion = super.handle(exchange);
		}
		catch (Throwable ex) {
			completion = Mono.error(ex);
		}

		for (WebExceptionHandler handler : this.exceptionHandlers) {
			completion = completion.onErrorResume(ex -> handler.handle(exchange, ex));
		}
		return completion;
	}


	/**
	 * WebExceptionHandler to insert a checkpoint with current URL information.
	 * Must be the first in order to ensure we catch the error signal before
	 * the exception is handled and e.g. turned into an error response.
	 * @since 5.2
 	 */
	/**
	 * WebExceptionHandler插入带有当前URL信息的检查点。 
	 * 必须是第一个，以确保我们在处理异常之前（例如变成了错误响应。 
	 *  @5.2起
	 */
	private static class CheckpointInsertingHandler implements WebExceptionHandler {

		@Override
		public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
			ServerHttpRequest request = exchange.getRequest();
			String rawQuery = request.getURI().getRawQuery();
			String query = StringUtils.hasText(rawQuery) ? "?" + rawQuery : "";
			HttpMethod httpMethod = request.getMethod();
			String description = "HTTP " + httpMethod + " \"" + request.getPath() + query + "\"";
			return Mono.<Void>error(ex).checkpoint(description + " [ExceptionHandlingWebHandler]");
		}
	}

}
