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

package org.springframework.web.reactive.function.server.support;

import java.lang.reflect.Method;

import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * {@code HandlerAdapter} implementation that supports {@link HandlerFunction HandlerFunctions}.
 *
 * @author Arjen Poutsma
 * @since 5.0
 */
/**
 * 支持{@link  HandlerFunction HandlerFunctions}的{@code  HandlerAdapter}实现。 
 *  @author  Arjen Poutsma @从5.0开始
 */
public class HandlerFunctionAdapter implements HandlerAdapter {

	private static final MethodParameter HANDLER_FUNCTION_RETURN_TYPE;

	static {
		try {
			Method method = HandlerFunction.class.getMethod("handle", ServerRequest.class);
			HANDLER_FUNCTION_RETURN_TYPE = new MethodParameter(method, -1);
		}
		catch (NoSuchMethodException ex) {
			throw new IllegalStateException(ex);
		}
	}


	@Override
	public boolean supports(Object handler) {
		return handler instanceof HandlerFunction;
	}

	@Override
	public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
		HandlerFunction<?> handlerFunction = (HandlerFunction<?>) handler;
		ServerRequest request = exchange.getRequiredAttribute(RouterFunctions.REQUEST_ATTRIBUTE);
		return handlerFunction.handle(request)
				.map(response -> new HandlerResult(handlerFunction, response, HANDLER_FUNCTION_RETURN_TYPE));
	}
}
