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

package org.springframework.http.client;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Represents a client-side asynchronous HTTP request. Created via an
 * implementation of the {@link AsyncClientHttpRequestFactory}.
 *
 * <p>A {@code AsyncHttpRequest} can be {@linkplain #executeAsync() executed},
 * getting a future {@link ClientHttpResponse} which can be read from.
 *
 * @author Arjen Poutsma
 * @since 4.0
 * @see AsyncClientHttpRequestFactory#createAsyncRequest
 * @deprecated as of Spring 5.0, in favor of {@link org.springframework.web.reactive.function.client.ClientRequest}
 */
/**
 * 表示客户端异步HTTP请求。 
 * 通过{@link  AsyncClientHttpRequestFactory}的实现创建。 
 *  <p>可以{{@link> plain #executeAsync（）执行} {{@code> AsyncHttpRequest}}，以获得将来的{@link  ClientHttpResponse}可以读取。 
 *  @author  Arjen Poutsma @从4.0起
 */
@Deprecated
public interface AsyncClientHttpRequest extends HttpRequest, HttpOutputMessage {

	/**
	 * Execute this request asynchronously, resulting in a Future handle.
	 * {@link ClientHttpResponse} that can be read.
	 * @return the future response result of the execution
	 * @throws java.io.IOException in case of I/O errors
	 */
	/**
	 * 异步执行此请求，从而生成Future句柄。 
	 * 可以读取的{@link  ClientHttpResponse}。 
	 *  
	 * @return 执行I / O错误时执行
	 * @throws  java.io.IOException的将来响应结果
	 */
	ListenableFuture<ClientHttpResponse> executeAsync() throws IOException;

}
