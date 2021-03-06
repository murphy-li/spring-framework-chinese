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

package org.springframework.http.client.reactive;

import java.net.URI;
import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpMethod;

/**
 * Abstraction over HTTP clients driving the underlying HTTP client to connect
 * to the origin server and provide all necessary infrastructure to send a
 * {@link ClientHttpRequest} and receive a {@link ClientHttpResponse}.
 *
 * @author Brian Clozel
 * @since 5.0
 */
/**
 * HTTP客户端上的抽象驱动底层HTTP客户端连接到原始服务器，并提供所有必要的基础结构来发送{@link  ClientHttpRequest}和接收{@link  ClientHttpResponse}。 
 *  @author 布赖恩·克洛泽尔​​（Brian Clozel）@从5.0开始
 */
public interface ClientHttpConnector {

	/**
	 * Connect to the origin server using the given {@code HttpMethod} and
	 * {@code URI} and apply the given {@code requestCallback} when the HTTP
	 * request of the underlying API can be initialized and written to.
	 * @param method the HTTP request method
	 * @param uri the HTTP request URI
	 * @param requestCallback a function that prepares and writes to the request,
	 * returning a publisher that signals when it's done writing.
	 * Implementations can return a {@code Mono<Void>} by calling
	 * {@link ClientHttpRequest#writeWith} or {@link ClientHttpRequest#setComplete}.
	 * @return publisher for the {@link ClientHttpResponse}
	 */
	/**
	 * 当可以初始化和写入基础API的HTTP请求时，使用给定的{@code  HttpMethod}和{@code  URI}连接到原始服务器，并应用给定的{@code  requestCallback}。 
	 *  
	 * @param 方法HTTP请求方法
	 * @param  uri HTTP请求URI 
	 * @param  requestCallback一个准备并写入请求的函数，返回发布者，该信号在完成写入时发出信号。 
	 * 实现可以通过调用{@link  ClientHttpRequest＃writeWith}或{@link  ClientHttpRequest＃setComplete}来返回{@code  Mono <Void>}。 
	 *  {@link  ClientHttpResponse}的
	 * @return 发布者
	 */
	Mono<ClientHttpResponse> connect(HttpMethod method, URI uri,
			Function<? super ClientHttpRequest, Mono<Void>> requestCallback);

}
