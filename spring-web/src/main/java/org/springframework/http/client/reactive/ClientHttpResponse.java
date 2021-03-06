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

package org.springframework.http.client.reactive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;

/**
 * Represents a client-side reactive HTTP response.
 *
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @since 5.0
 */
/**
 * 表示客户端反应式HTTP响应。 
 *  @author  Arjen Poutsma @author 布莱恩·克洛泽尔​​（Brian Clozel）@5.0起
 */
public interface ClientHttpResponse extends ReactiveHttpInputMessage {

	/**
	 * Return the HTTP status code as an {@link HttpStatus} enum value.
	 * @return the HTTP status as an HttpStatus enum value (never {@code null})
	 * @throws IllegalArgumentException in case of an unknown HTTP status code
	 * @since #getRawStatusCode()
	 * @see HttpStatus#valueOf(int)
	 */
	/**
	 * 返回HTTP状态代码作为{@link  HttpStatus}枚举值。 
	 *  
	 * @return 作为HttpStatus枚举值的HTTP状态（决不{<@@code> null}）
	 * @throws  IllegalArgumentException，如果未知的HTTP状态代码@since #getRawStatusCode（）
	 * @see  HttpStatus＃valueOf（int ）
	 */
	HttpStatus getStatusCode();

	/**
	 * Return the HTTP status code (potentially non-standard and not
	 * resolvable through the {@link HttpStatus} enum) as an integer.
	 * @return the HTTP status as an integer value
	 * @since 5.0.6
	 * @see #getStatusCode()
	 * @see HttpStatus#resolve(int)
	 */
	/**
	 * 以整数形式返回HTTP状态代码（可能是非标准的，并且无法通过{@link  HttpStatus}枚举解析）。 
	 *  
	 * @return  HTTP状态为整数值@自5.0.6起
	 * @see  #getStatusCode（）
	 * @see  HttpStatus＃resolve（int）
	 */
	int getRawStatusCode();

	/**
	 * Return a read-only map of response cookies received from the server.
	 */
	/**
	 * 返回从服务器收到的响应cookie的只读映射。 
	 * 
	 */
	MultiValueMap<String, ResponseCookie> getCookies();

}
