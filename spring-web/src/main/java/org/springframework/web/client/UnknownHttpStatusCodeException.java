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

package org.springframework.web.client;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * Exception thrown when an unknown (or custom) HTTP status code is received.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 */
/**
 * 收到未知（或自定义）HTTP状态代码时引发的异常。 
 *  @author  Rossen Stoyanchev @从3.2开始
 */
public class UnknownHttpStatusCodeException extends RestClientResponseException {

	private static final long serialVersionUID = 7103980251635005491L;


	/**
	 * Construct a new instance of {@code HttpStatusCodeException} based on an
	 * {@link HttpStatus}, status text, and response body content.
	 * @param rawStatusCode the raw status code value
	 * @param statusText the status text
	 * @param responseHeaders the response headers (may be {@code null})
	 * @param responseBody the response body content (may be {@code null})
	 * @param responseCharset the response body charset (may be {@code null})
	 */
	/**
	 * 根据{@link  HttpStatus}，状态文本和响应正文内容，构造一个{@code  HttpStatusCodeException}的新实例。 
	 *  
	 * @param  rawStatusCode原始状态代码值
	 * @param  statusText状态文本
	 * @param  responseHeaders响应标头（可以为{@code  null}）
	 * @param  responseBody响应正文内容（可以为{ @code  null}）
	 * @param  responseCharset响应正文字符集（可以为{@code  null}）
	 */
	public UnknownHttpStatusCodeException(int rawStatusCode, String statusText, @Nullable HttpHeaders responseHeaders,
			@Nullable byte[] responseBody, @Nullable Charset responseCharset) {

		this("Unknown status code [" + rawStatusCode + "]" + " " + statusText,
				rawStatusCode, statusText, responseHeaders, responseBody, responseCharset);
	}

	/**
	 * Construct a new instance of {@code HttpStatusCodeException} based on an
	 * {@link HttpStatus}, status text, and response body content.
	 * @param rawStatusCode the raw status code value
	 * @param statusText the status text
	 * @param responseHeaders the response headers (may be {@code null})
	 * @param responseBody the response body content (may be {@code null})
	 * @param responseCharset the response body charset (may be {@code null})
	 * @since 5.2.2
	 */
	/**
	 * 根据{@link  HttpStatus}，状态文本和响应正文内容，构造一个{@code  HttpStatusCodeException}的新实例。 
	 *  
	 * @param  rawStatusCode原始状态代码值
	 * @param  statusText状态文本
	 * @param  responseHeaders响应标头（可以为{@code  null}）
	 * @param  responseBody响应正文内容（可以为{ @code  null}）
	 * @param  responseCharset响应正文字符集（可能为{@code  null}）@5.2.2起
	 */
	public UnknownHttpStatusCodeException(String message, int rawStatusCode, String statusText,
			@Nullable HttpHeaders responseHeaders, @Nullable byte[] responseBody, @Nullable Charset responseCharset) {

		super(message, rawStatusCode, statusText, responseHeaders, responseBody, responseCharset);
	}
}
