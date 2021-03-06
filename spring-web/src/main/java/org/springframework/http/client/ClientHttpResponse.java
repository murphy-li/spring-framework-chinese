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

package org.springframework.http.client;

import java.io.Closeable;
import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;

/**
 * Represents a client-side HTTP response.
 *
 * <p>Obtained via an invocation of {@link ClientHttpRequest#execute()}.
 *
 * <p>A {@code ClientHttpResponse} must be {@linkplain #close() closed},
 * typically in a {@code finally} block.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
/**
 * 表示客户端HTTP响应。 
 *  <p>通过调用{@link  ClientHttpRequest＃execute（）}获得。 
 *  <p> {{@@code> ClientHttpResponse}必须为{@link  plain #close（）关闭}，通常在{@code  finally}块中。 
 *  @author  Arjen Poutsma @从3.0开始
 */
public interface ClientHttpResponse extends HttpInputMessage, Closeable {

	/**
	 * Get the HTTP status code as an {@link HttpStatus} enum value.
	 * <p>For status codes not supported by {@code HttpStatus}, use
	 * {@link #getRawStatusCode()} instead.
	 * @return the HTTP status as an HttpStatus enum value (never {@code null})
	 * @throws IOException in case of I/O errors
	 * @throws IllegalArgumentException in case of an unknown HTTP status code
	 * @since #getRawStatusCode()
	 * @see HttpStatus#valueOf(int)
	 */
	/**
	 * 获取HTTP状态代码作为{@link  HttpStatus}枚举值。 
	 *  <p>对于{@code  HttpStatus}不支持的状态代码，请改用{@link  #getRawStatusCode（）}。 
	 *  
	 * @return 作为HttpStatus枚举值的HTTP状态（从不{<@@code> null}）
	 * @throws 发生I / O错误时的IOException 
	 * @throws  IllegalArgumentException如果存在未知的HTTP状态代码，则@since＃ getRawStatusCode（）
	 * @see  HttpStatus＃valueOf（int）
	 */
	HttpStatus getStatusCode() throws IOException;

	/**
	 * Get the HTTP status code (potentially non-standard and not
	 * resolvable through the {@link HttpStatus} enum) as an integer.
	 * @return the HTTP status as an integer value
	 * @throws IOException in case of I/O errors
	 * @since 3.1.1
	 * @see #getStatusCode()
	 * @see HttpStatus#resolve(int)
	 */
	/**
	 * 以整数形式获取HTTP状态代码（可能是非标准的，并且无法通过{@link  HttpStatus}枚举解析）。 
	 *  
	 * @return  HTTP状态为整数值
	 * @throws  IOException，如果发生I / O错误@since 3.1.1 
	 * @see  #getStatusCode（）
	 * @see  HttpStatus＃resolve（int）
	 */
	int getRawStatusCode() throws IOException;

	/**
	 * Get the HTTP status text of the response.
	 * @return the HTTP status text
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 获取响应的HTTP状态文本。 
	 *  
	 * @return  HTTP状态文本
	 * @throws  IOException（如果发生I / O错误）
	 */
	String getStatusText() throws IOException;

	/**
	 * Close this response, freeing any resources created.
	 */
	/**
	 * 关闭此响应，释放创建的所有资源。 
	 * 
	 */
	@Override
	void close();

}
