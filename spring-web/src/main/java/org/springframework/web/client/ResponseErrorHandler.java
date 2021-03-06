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

package org.springframework.web.client;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Strategy interface used by the {@link RestTemplate} to determine
 * whether a particular response has an error or not.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
/**
 * {@link  RestTemplate}用于确定特定响应是否有错误的策略接口。 
 *  @author  Arjen Poutsma @从3.0开始
 */
public interface ResponseErrorHandler {

	/**
	 * Indicate whether the given response has any errors.
	 * <p>Implementations will typically inspect the
	 * {@link ClientHttpResponse#getStatusCode() HttpStatus} of the response.
	 * @param response the response to inspect
	 * @return {@code true} if the response indicates an error; {@code false} otherwise
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 指示给定的响应是否有任何错误。 
	 *  <p>实施通常会检查响应的{@link  ClientHttpResponse＃getStatusCode（）HttpStatus}。 
	 *  
	 * @param 响应，如果响应指示错误，则检查
	 * @return  {@code  true}； 
	 *  {@code  false}否则为
	 * @throws  IOException，如果发生I / O错误
	 */
	boolean hasError(ClientHttpResponse response) throws IOException;

	/**
	 * Handle the error in the given response.
	 * <p>This method is only called when {@link #hasError(ClientHttpResponse)}
	 * has returned {@code true}.
	 * @param response the response with the error
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 处理给定响应中的错误。 
	 *  <p>仅当{@link  #hasError（ClientHttpResponse）}返回{@code  true}时才调用此方法。 
	 *  
	 * @param 在发生I / O错误的情况下以错误
	 * @throws  IOException响应响应
	 */
	void handleError(ClientHttpResponse response) throws IOException;

	/**
	 * Alternative to {@link #handleError(ClientHttpResponse)} with extra
	 * information providing access to the request URL and HTTP method.
	 * @param url the request URL
	 * @param method the HTTP method
	 * @param response the response with the error
	 * @throws IOException in case of I/O errors
	 * @since 5.0
	 */
	/**
	 * 替代{@link  #handleError（ClientHttpResponse）}的附加信息，可提供对请求URL和HTTP方法的访问。 
	 *  
	 * @param 为请求URL 
	 * @param 方法添加URL HTTP方法
	 * @param 在自5.0以来发生I / O错误的情况下以错误
	 * @throws  IOException响应响应
	 */
	default void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
		handleError(response);
	}

}
