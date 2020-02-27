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

package org.springframework.web.reactive.function.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * Exceptions that contain actual HTTP response data.
 *
 * @author Arjen Poutsma
 * @since 5.0
 */
/**
 * 包含实际HTTP响应数据的异常。 
 *  @author  Arjen Poutsma @从5.0开始
 */
public class WebClientResponseException extends WebClientException {

	private static final long serialVersionUID = 4127543205414951611L;


	private final int statusCode;

	private final String statusText;

	private final byte[] responseBody;

	private final HttpHeaders headers;

	private final Charset responseCharset;

	@Nullable
	private final HttpRequest request;


	/**
	 * Constructor with response data only, and a default message.
	 * @since 5.1
	 */
	/**
	 * 仅具有响应数据和默认消息的构造方法。 
	 *  @5.1起
	 */
	public WebClientResponseException(int statusCode, String statusText,
			@Nullable HttpHeaders headers, @Nullable byte[] body, @Nullable Charset charset) {

		this(statusCode, statusText, headers, body, charset, null);
	}

	/**
	 * Constructor with response data only, and a default message.
	 * @since 5.1.4
	 */
	/**
	 * 仅具有响应数据和默认消息的构造方法。 
	 *  @从5.1.4开始
	 */
	public WebClientResponseException(int status, String reasonPhrase,
			@Nullable HttpHeaders headers, @Nullable byte[] body, @Nullable Charset charset,
			@Nullable HttpRequest request) {

		this(initMessage(status, reasonPhrase, request), status, reasonPhrase, headers, body, charset, request);
	}

	private static String initMessage(int status, String reasonPhrase, @Nullable HttpRequest request) {
		return status + " " + reasonPhrase +
				(request != null ? " from " + request.getMethodValue() + " " + request.getURI() : "");
	}

	/**
	 * Constructor with a prepared message.
	 */
	/**
	 * 具有准备好的消息的构造函数。 
	 * 
	 */
	public WebClientResponseException(String message, int statusCode, String statusText,
			@Nullable HttpHeaders headers, @Nullable byte[] responseBody, @Nullable Charset charset) {
		this(message, statusCode, statusText, headers, responseBody, charset, null);
	}

	/**
	 * Constructor with a prepared message.
	 * @since 5.1.4
	 */
	/**
	 * 具有准备好的消息的构造函数。 
	 *  @从5.1.4开始
	 */
	public WebClientResponseException(String message, int statusCode, String statusText,
			@Nullable HttpHeaders headers, @Nullable byte[] responseBody, @Nullable Charset charset,
			@Nullable HttpRequest request) {

		super(message);

		this.statusCode = statusCode;
		this.statusText = statusText;
		this.headers = (headers != null ? headers : HttpHeaders.EMPTY);
		this.responseBody = (responseBody != null ? responseBody : new byte[0]);
		this.responseCharset = (charset != null ? charset : StandardCharsets.ISO_8859_1);
		this.request = request;
	}


	/**
	 * Return the HTTP status code value.
	 * @throws IllegalArgumentException in case of an unknown HTTP status code
	 */
	/**
	 * 返回HTTP状态代码值。 
	 *  
	 * @throws  IllegalArgumentException，如果HTTP状态代码未知
	 */
	public HttpStatus getStatusCode() {
		return HttpStatus.valueOf(this.statusCode);
	}

	/**
	 * Return the raw HTTP status code value.
	 */
	/**
	 * 返回原始HTTP状态代码值。 
	 * 
	 */
	public int getRawStatusCode() {
		return this.statusCode;
	}

	/**
	 * Return the HTTP status text.
	 */
	/**
	 * 返回HTTP状态文本。 
	 * 
	 */
	public String getStatusText() {
		return this.statusText;
	}

	/**
	 * Return the HTTP response headers.
	 */
	/**
	 * 返回HTTP响应头。 
	 * 
	 */
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	/**
	 * Return the response body as a byte array.
	 */
	/**
	 * 以字节数组形式返回响应主体。 
	 * 
	 */
	public byte[] getResponseBodyAsByteArray() {
		return this.responseBody;
	}

	/**
	 * Return the response body as a string.
	 */
	/**
	 * 以字符串形式返回响应主体。 
	 * 
	 */
	public String getResponseBodyAsString() {
		return new String(this.responseBody, this.responseCharset);
	}

	/**
	 * Return the corresponding request.
	 * @since 5.1.4
	 */
	/**
	 * 返回相应的请求。 
	 *  @从5.1.4开始
	 */
	@Nullable
	public HttpRequest getRequest() {
		return this.request;
	}

	/**
	 * Create {@code WebClientResponseException} or an HTTP status specific subclass.
	 * @since 5.1
	 */
	/**
	 * 创建{@code  WebClientResponseException}或HTTP状态特定的子类。 
	 *  @5.1起
	 */
	public static WebClientResponseException create(
			int statusCode, String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset) {

		return create(statusCode, statusText, headers, body, charset, null);
	}

	/**
	 * Create {@code WebClientResponseException} or an HTTP status specific subclass.
	 * @since 5.1.4
	 */
	/**
	 * 创建{@code  WebClientResponseException}或HTTP状态特定的子类。 
	 *  @从5.1.4开始
	 */
	public static WebClientResponseException create(
			int statusCode, String statusText, HttpHeaders headers, byte[] body,
			@Nullable Charset charset, @Nullable HttpRequest request) {

		HttpStatus httpStatus = HttpStatus.resolve(statusCode);
		if (httpStatus != null) {
			switch (httpStatus) {
				case BAD_REQUEST:
					return new WebClientResponseException.BadRequest(statusText, headers, body, charset, request);
				case UNAUTHORIZED:
					return new WebClientResponseException.Unauthorized(statusText, headers, body, charset, request);
				case FORBIDDEN:
					return new WebClientResponseException.Forbidden(statusText, headers, body, charset, request);
				case NOT_FOUND:
					return new WebClientResponseException.NotFound(statusText, headers, body, charset, request);
				case METHOD_NOT_ALLOWED:
					return new WebClientResponseException.MethodNotAllowed(statusText, headers, body, charset, request);
				case NOT_ACCEPTABLE:
					return new WebClientResponseException.NotAcceptable(statusText, headers, body, charset, request);
				case CONFLICT:
					return new WebClientResponseException.Conflict(statusText, headers, body, charset, request);
				case GONE:
					return new WebClientResponseException.Gone(statusText, headers, body, charset, request);
				case UNSUPPORTED_MEDIA_TYPE:
					return new WebClientResponseException.UnsupportedMediaType(statusText, headers, body, charset, request);
				case TOO_MANY_REQUESTS:
					return new WebClientResponseException.TooManyRequests(statusText, headers, body, charset, request);
				case UNPROCESSABLE_ENTITY:
					return new WebClientResponseException.UnprocessableEntity(statusText, headers, body, charset, request);
				case INTERNAL_SERVER_ERROR:
					return new WebClientResponseException.InternalServerError(statusText, headers, body, charset, request);
				case NOT_IMPLEMENTED:
					return new WebClientResponseException.NotImplemented(statusText, headers, body, charset, request);
				case BAD_GATEWAY:
					return new WebClientResponseException.BadGateway(statusText, headers, body, charset, request);
				case SERVICE_UNAVAILABLE:
					return new WebClientResponseException.ServiceUnavailable(statusText, headers, body, charset, request);
				case GATEWAY_TIMEOUT:
					return new WebClientResponseException.GatewayTimeout(statusText, headers, body, charset, request);
			}
		}
		return new WebClientResponseException(statusCode, statusText, headers, body, charset, request);
	}



	// Subclasses for specific, client-side, HTTP status codes

	/**
	 * {@link WebClientResponseException} for status HTTP 400 Bad Request.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 400错误请求。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class BadRequest extends WebClientResponseException {

		BadRequest(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.BAD_REQUEST.value(), statusText, headers, body, charset, request);
		}

	}

	/**
	 * {@link WebClientResponseException} for status HTTP 401 Unauthorized.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}的状态为HTTP 401未经授权。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class Unauthorized extends WebClientResponseException {

		Unauthorized(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.UNAUTHORIZED.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 403 Forbidden.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}的状态为HTTP 403 Forbidden。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class Forbidden extends WebClientResponseException {

		Forbidden(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.FORBIDDEN.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 404 Not Found.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 404找不到。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class NotFound extends WebClientResponseException {

		NotFound(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.NOT_FOUND.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 405 Method Not Allowed.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 405，不允许使用方法。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class MethodNotAllowed extends WebClientResponseException {

		MethodNotAllowed(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.METHOD_NOT_ALLOWED.value(), statusText, headers, body, charset,
					request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 406 Not Acceptable.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}的状态为HTTP 406不可接受。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class NotAcceptable extends WebClientResponseException {

		NotAcceptable(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.NOT_ACCEPTABLE.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 409 Conflict.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 409冲突。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class Conflict extends WebClientResponseException {

		Conflict(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.CONFLICT.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 410 Gone.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 410已消失。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class Gone extends WebClientResponseException {

		Gone(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.GONE.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 415 Unsupported Media Type.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}的状态为HTTP 415不支持的媒体类型。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class UnsupportedMediaType extends WebClientResponseException {

		UnsupportedMediaType(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {

			super(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), statusText, headers, body, charset,
					request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 422 Unprocessable Entity.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 422不可处理实体。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class UnprocessableEntity extends WebClientResponseException {

		UnprocessableEntity(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.UNPROCESSABLE_ENTITY.value(), statusText, headers, body, charset,
					request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 429 Too Many Requests.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 429请求太多。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class TooManyRequests extends WebClientResponseException {

		TooManyRequests(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.TOO_MANY_REQUESTS.value(), statusText, headers, body, charset,
					request);
		}
	}



	// Subclasses for specific, server-side, HTTP status codes

	/**
	 * {@link WebClientResponseException} for status HTTP 500 Internal Server Error.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 500内部服务器错误。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class InternalServerError extends WebClientResponseException {

		InternalServerError(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.INTERNAL_SERVER_ERROR.value(), statusText, headers, body, charset,
					request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 501 Not Implemented.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 501未实现。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class NotImplemented extends WebClientResponseException {

		NotImplemented(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.NOT_IMPLEMENTED.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP HTTP 502 Bad Gateway.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}用于状态HTTP HTTP 502错误的网关。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class BadGateway extends WebClientResponseException {

		BadGateway(String statusText, HttpHeaders headers, byte[] body, @Nullable Charset charset,
				@Nullable HttpRequest request) {
			super(HttpStatus.BAD_GATEWAY.value(), statusText, headers, body, charset, request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 503 Service Unavailable.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 503服务不可用。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class ServiceUnavailable extends WebClientResponseException {

		ServiceUnavailable(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.SERVICE_UNAVAILABLE.value(), statusText, headers, body, charset,
					request);
		}
	}

	/**
	 * {@link WebClientResponseException} for status HTTP 504 Gateway Timeout.
	 * @since 5.1
	 */
	/**
	 * {@link  WebClientResponseException}，状态为HTTP 504网关超时。 
	 *  @5.1起
	 */
	@SuppressWarnings("serial")
	public static class GatewayTimeout extends WebClientResponseException {

		GatewayTimeout(String statusText, HttpHeaders headers, byte[] body,
				@Nullable Charset charset, @Nullable HttpRequest request) {
			super(HttpStatus.GATEWAY_TIMEOUT.value(), statusText, headers, body, charset,
					request);
		}
	}

}
