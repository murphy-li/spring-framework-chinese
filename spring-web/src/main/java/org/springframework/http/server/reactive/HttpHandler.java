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

package org.springframework.http.server.reactive;

import reactor.core.publisher.Mono;

/**
 * Lowest level contract for reactive HTTP request handling that serves as a
 * common denominator across different runtimes.
 *
 * <p>Higher-level, but still generic, building blocks for applications such as
 * {@code WebFilter}, {@code WebSession}, {@code ServerWebExchange}, and others
 * are available in the {@code org.springframework.web.server} package.
 *
 * <p>Application level programming models such as annotated controllers and
 * functional handlers are available in the {@code spring-webflux} module.
 *
 * <p>Typically an {@link HttpHandler} represents an entire application with
 * higher-level programming models bridged via
 * {@link org.springframework.web.server.adapter.WebHttpHandlerBuilder}.
 * Multiple applications at unique context paths can be plugged in with the
 * help of the {@link ContextPathCompositeHandler}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 5.0
 * @see ContextPathCompositeHandler
 */
/**
 * 响应式HTTP请求处理的最低级别合同，在不同的运行时中充当共同点。 
 *  <p> {{@@}中提供了诸如{@code  WebFilter}，{<@code> WebSession}，{<@code> ServerWebExchange}之类的应用程序的更高级但仍通用的构建块。 
 * 代码> org.springframework.web.server}包。 
 *  <p>应用级编程模型，例如带注释的控制器和功能处理程序，可在{@code  spring-webflux}模块中获得。 
 *  <p>通常，一个{@link  HttpHandler}代表整个应用程序，该应用程序具有通过{@link  org.springframework.web.server.adapter.WebHttpHandlerBuilder}桥接的更高级别的编程模型。 
 * 可以借助{@link  ContextPathCompositeHandler}插入唯一上下文路径中的多个应用程序。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @从5.0起
 * @see  ContextPathCompositeHandler
 */
public interface HttpHandler {

	/**
	 * Handle the given request and write to the response.
	 * @param request current request
	 * @param response current response
	 * @return indicates completion of request handling
	 */
	/**
	 * 处理给定的请求并写入响应。 
	 *  
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @return 表示请求处理已完成
	 */
	Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response);

}
