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

package org.springframework.http;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

/**
 * Extension of {@link HttpEntity} that adds a {@link HttpStatus} status code.
 * Used in {@code RestTemplate} as well {@code @Controller} methods.
 *
 * <p>In {@code RestTemplate}, this class is returned by
 * {@link org.springframework.web.client.RestTemplate#getForEntity getForEntity()} and
 * {@link org.springframework.web.client.RestTemplate#exchange exchange()}:
 * <pre class="code">
 * ResponseEntity&lt;String&gt; entity = template.getForEntity("https://example.com", String.class);
 * String body = entity.getBody();
 * MediaType contentType = entity.getHeaders().getContentType();
 * HttpStatus statusCode = entity.getStatusCode();
 * </pre>
 *
 * <p>Can also be used in Spring MVC, as the return value from a @Controller method:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public ResponseEntity&lt;String&gt; handle() {
 *   URI location = ...;
 *   HttpHeaders responseHeaders = new HttpHeaders();
 *   responseHeaders.setLocation(location);
 *   responseHeaders.set("MyResponseHeader", "MyValue");
 *   return new ResponseEntity&lt;String&gt;("Hello World", responseHeaders, HttpStatus.CREATED);
 * }
 * </pre>
 *
 * Or, by using a builder accessible via static methods:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public ResponseEntity&lt;String&gt; handle() {
 *   URI location = ...;
 *   return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
 * }
 * </pre>
 *
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @since 3.0.2
 * @param <T> the body type
 * @see #getStatusCode()
 */
/**
 * {@link  HttpEntity}的扩展，添加了一个{@link  HttpStatus}状态代码。 
 * 在{@code  RestTemplate}和{@code  @Controller}方法中使用。 
 *  <p>在{@code  RestTemplate}中，此类由{@link  org.springframework.web.client.RestTemplate＃getForEntity getForEntity（）}和{@link  org.springframework.web.client返回.RestTemplate＃exchange exchange（）}：<pre class ="code"> ResponseEntity <String>实体= template.getForEntity（"https://example.com"，String.class）;字符串正文= entity.getBody（）; MediaType contentType = entity.getHeaders（）。 
 * getContentType（）; HttpStatus statusCode = entity.getStatusCode（）; </ pre> <p>也可以在Spring MVC中使用，作为@Controller方法的返回值：<pre class ="code"> @RequestMapping（"/ handle"）public ResponseEntity <String> handle（）{ URI位置= ...; HttpHeaders responseHeaders =新的HttpHeaders（）; responseHeaders.setLocation（location）; responseHeaders.set（"MyResponseHeader"，"MyValue"）;返回新的ResponseEntity <String>（"Hello World"，responseHeaders，HttpStatus.CREATED）; } </ pre>或者，通过使用可通过静态方法访问的构建器：<pre class ="code"> @RequestMapping（"/ handle"）public ResponseEntity <String> handle（）{URI location = ...; return ResponseEntity.created（location）.header（"MyResponseHeader"，"MyValue"）。 
 * body（"Hello World"）; } </ pre> @author  Arjen Poutsma @author  Brian Clozel @since 3.0.2 
 * @param  <T>主体类型
 * @see  #getStatusCode（）
 */
public class ResponseEntity<T> extends HttpEntity<T> {

	private final Object status;


	/**
	 * Create a new {@code ResponseEntity} with the given status code, and no body nor headers.
	 * @param status the status code
	 */
	/**
	 * 使用给定的状态码创建新的{@code  ResponseEntity}，没有正文或标题。 
	 *  
	 * @param 状态状态代码
	 */
	public ResponseEntity(HttpStatus status) {
		this(null, null, status);
	}

	/**
	 * Create a new {@code ResponseEntity} with the given body and status code, and no headers.
	 * @param body the entity body
	 * @param status the status code
	 */
	/**
	 * 用给定的正文和状态代码创建新的{@code  ResponseEntity}，并且不包含标题。 
	 *  
	 * @param 实体实体body 
	 * @param 状态状态代码
	 */
	public ResponseEntity(@Nullable T body, HttpStatus status) {
		this(body, null, status);
	}

	/**
	 * Create a new {@code HttpEntity} with the given headers and status code, and no body.
	 * @param headers the entity headers
	 * @param status the status code
	 */
	/**
	 * 创建一个新的{@code  HttpEntity}，它具有给定的标题和状态码，并且没有正文。 
	 *  
	 * @param 标头，实体标头
	 * @param 状态，状态码
	 */
	public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
		this(null, headers, status);
	}

	/**
	 * Create a new {@code HttpEntity} with the given body, headers, and status code.
	 * @param body the entity body
	 * @param headers the entity headers
	 * @param status the status code
	 */
	/**
	 * 使用给定的正文，标头和状态代码创建一个新的{@code  HttpEntity}。 
	 *  
	 * @param 实体实体主体
	 * @param 标头实体标头
	 * @param 状态状态代码
	 */
	public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers);
		Assert.notNull(status, "HttpStatus must not be null");
		this.status = status;
	}

	/**
	 * Create a new {@code HttpEntity} with the given body, headers, and status code.
	 * Just used behind the nested builder API.
	 * @param body the entity body
	 * @param headers the entity headers
	 * @param status the status code (as {@code HttpStatus} or as {@code Integer} value)
	 */
	/**
	 * 使用给定的正文，标头和状态代码创建一个新的{@code  HttpEntity}。 
	 * 只是在嵌套的构建器API之后使用。 
	 *  
	 * @param 实体实体主体
	 * @param 标头实体标头
	 * @param 状态状态代码（作为{@code  HttpStatus}或{@code  Integer}值）
	 */
	private ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Object status) {
		super(body, headers);
		Assert.notNull(status, "HttpStatus must not be null");
		this.status = status;
	}


	/**
	 * Return the HTTP status code of the response.
	 * @return the HTTP status as an HttpStatus enum entry
	 */
	/**
	 * 返回响应的HTTP状态代码。 
	 *  
	 * @return  HTTP状态作为HttpStatus枚举条目
	 */
	public HttpStatus getStatusCode() {
		if (this.status instanceof HttpStatus) {
			return (HttpStatus) this.status;
		}
		else {
			return HttpStatus.valueOf((Integer) this.status);
		}
	}

	/**
	 * Return the HTTP status code of the response.
	 * @return the HTTP status as an int value
	 * @since 4.3
	 */
	/**
	 * 返回响应的HTTP状态代码。 
	 *  
	 * @return  HTTP状态作为int值@自4.3起
	 */
	public int getStatusCodeValue() {
		if (this.status instanceof HttpStatus) {
			return ((HttpStatus) this.status).value();
		}
		else {
			return (Integer) this.status;
		}
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!super.equals(other)) {
			return false;
		}
		ResponseEntity<?> otherEntity = (ResponseEntity<?>) other;
		return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
	}

	@Override
	public int hashCode() {
		return (29 * super.hashCode() + ObjectUtils.nullSafeHashCode(this.status));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("<");
		builder.append(this.status.toString());
		if (this.status instanceof HttpStatus) {
			builder.append(' ');
			builder.append(((HttpStatus) this.status).getReasonPhrase());
		}
		builder.append(',');
		T body = getBody();
		HttpHeaders headers = getHeaders();
		if (body != null) {
			builder.append(body);
			builder.append(',');
		}
		builder.append(headers);
		builder.append('>');
		return builder.toString();
	}


	// Static builder methods

	/**
	 * Create a builder with the given status.
	 * @param status the response status
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建具有给定状态的构建器。 
	 *  
	 * @param 状态响应状态
	 * @return 创建的构建器@since 4.1
	 */
	public static BodyBuilder status(HttpStatus status) {
		Assert.notNull(status, "HttpStatus must not be null");
		return new DefaultBuilder(status);
	}

	/**
	 * Create a builder with the given status.
	 * @param status the response status
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建具有给定状态的构建器。 
	 *  
	 * @param 状态响应状态
	 * @return 创建的构建器@since 4.1
	 */
	public static BodyBuilder status(int status) {
		return new DefaultBuilder(status);
	}

	/**
	 * A shortcut for creating a {@code ResponseEntity} with the given body
	 * and the {@linkplain HttpStatus#OK OK} status, or an empty body and a
	 * {@linkplain HttpStatus#NOT_FOUND NOT FOUND} status in case of an
	 * {@linkplain Optional#empty()} parameter.
	 * @return the created {@code ResponseEntity}
	 * @since 5.1
	 */
	/**
	 * 创建具有给定正文和{@link  plain HttpStatus＃OK OK}状态的{@code  ResponseEntity}的快捷方式，或者创建一个空正文和{@link  plain HttpStatus＃NOT_FOUND NOT FOUND}状态的快捷方式如果使用{@link  plain Optional＃empty（）}参数。 
	 *  
	 * @return 创建的{@code  ResponseEntity} @从5.1开始
	 */
	public static <T> ResponseEntity<T> of(Optional<T> body) {
		Assert.notNull(body, "Body must not be null");
		return body.map(ResponseEntity::ok).orElse(notFound().build());
	}

	/**
	 * Create a builder with the status set to {@linkplain HttpStatus#OK OK}.
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态设置为{@link  plain HttpStatus＃OK OK}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1
	 */
	public static BodyBuilder ok() {
		return status(HttpStatus.OK);
	}

	/**
	 * A shortcut for creating a {@code ResponseEntity} with the given body and
	 * the status set to {@linkplain HttpStatus#OK OK}.
	 * @return the created {@code ResponseEntity}
	 * @since 4.1
	 */
	/**
	 * 创建具有给定主体并将状态设置为{@link  plain HttpStatus＃OK OK}的{@code  ResponseEntity}的快捷方式。 
	 *  
	 * @return 创建的{@code  ResponseEntity} @从4.1开始
	 */
	public static <T> ResponseEntity<T> ok(T body) {
		BodyBuilder builder = ok();
		return builder.body(body);
	}

	/**
	 * Create a new builder with a {@linkplain HttpStatus#CREATED CREATED} status
	 * and a location header set to the given URI.
	 * @param location the location URI
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃CREATED CREATED}且位置标头设置为给定URI的新构建器。 
	 *  
	 * @param  location位置URI 
	 * @return 创建的构建器@since 4.1
	 */
	public static BodyBuilder created(URI location) {
		BodyBuilder builder = status(HttpStatus.CREATED);
		return builder.location(location);
	}

	/**
	 * Create a builder with an {@linkplain HttpStatus#ACCEPTED ACCEPTED} status.
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃ACCEPTED ACCEPTED}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1
	 */
	public static BodyBuilder accepted() {
		return status(HttpStatus.ACCEPTED);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#NO_CONTENT NO_CONTENT} status.
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃NO_CONTENT NO_CONTENT}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1
	 */
	public static HeadersBuilder<?> noContent() {
		return status(HttpStatus.NO_CONTENT);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#BAD_REQUEST BAD_REQUEST} status.
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃BAD_REQUEST BAD_REQUEST}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1
	 */
	public static BodyBuilder badRequest() {
		return status(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#NOT_FOUND NOT_FOUND} status.
	 * @return the created builder
	 * @since 4.1
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃NOT_FOUND NOT_FOUND}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1
	 */
	public static HeadersBuilder<?> notFound() {
		return status(HttpStatus.NOT_FOUND);
	}

	/**
	 * Create a builder with an
	 * {@linkplain HttpStatus#UNPROCESSABLE_ENTITY UNPROCESSABLE_ENTITY} status.
	 * @return the created builder
	 * @since 4.1.3
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃UNPROCESSABLE_ENTITY UNPROCESSABLE_ENTITY}的构建器。 
	 *  
	 * @return 创建的生成器@始于4.1.3
	 */
	public static BodyBuilder unprocessableEntity() {
		return status(HttpStatus.UNPROCESSABLE_ENTITY);
	}


	/**
	 * Defines a builder that adds headers to the response entity.
	 * @since 4.1
	 * @param <B> the builder subclass
	 */
	/**
	 * 定义一个将标题添加到响应实体的构建器。 
	 *  @since 4.1 
	 * @param  <B>构建器子类
	 */
	public interface HeadersBuilder<B extends HeadersBuilder<B>> {

		/**
		 * Add the given, single header value under the given name.
		 * @param headerName the header name
		 * @param headerValues the header value(s)
		 * @return this builder
		 * @see HttpHeaders#add(String, String)
		 */
		/**
		 * 在给定名称下添加给定的单个标头值。 
		 *  
		 * @param  headerName标头名称
		 * @param  headerValues标头值
		 * @return 此构建器
		 * @see  HttpHeaders＃add（String，String）
		 */
		B header(String headerName, String... headerValues);

		/**
		 * Copy the given headers into the entity's headers map.
		 * @param headers the existing HttpHeaders to copy from
		 * @return this builder
		 * @since 4.1.2
		 * @see HttpHeaders#add(String, String)
		 */
		/**
		 * 将给定的标题复制到实体的标题映射中。 
		 *  
		 * @param 标头要从此构建器
		 * @return 复制的现有HttpHeaders，从4.1.2开始
		 * @see  HttpHeaders＃add（String，String）
		 */
		B headers(@Nullable HttpHeaders headers);

		/**
		 * Manipulate this entity's headers with the given consumer. The
		 * headers provided to the consumer are "live", so that the consumer can be used to
		 * {@linkplain HttpHeaders#set(String, String) overwrite} existing header values,
		 * {@linkplain HttpHeaders#remove(Object) remove} values, or use any of the other
		 * {@link HttpHeaders} methods.
		 * @param headersConsumer a function that consumes the {@code HttpHeaders}
		 * @return this builder
		 * @since 5.2
		 */
		/**
		 * 使用给定的使用者操作此实体的标头。 
		 * 提供给使用者的标头是"活动的"，因此使用者可以用来{@link 纯HttpHeaders＃set（String，String）覆盖}现有标头值{@link 纯HttpHeaders＃remove（Object ）删除}值，或使用其他任何{@link  HttpHeaders}方法。 
		 *  
		 * @param  headers消费一个消耗{@code  HttpHeaders}的函数
		 * @return 此生成器@since 5.2
		 */
		B headers(Consumer<HttpHeaders> headersConsumer);

		/**
		 * Set the set of allowed {@link HttpMethod HTTP methods}, as specified
		 * by the {@code Allow} header.
		 * @param allowedMethods the allowed methods
		 * @return this builder
		 * @see HttpHeaders#setAllow(Set)
		 */
		/**
		 * 设置由{@code  Allow}标头指定的一组允许的{@link  HttpMethod HTTP方法}。 
		 *  
		 * @param  allowedMethods允许的方法
		 * @return 此构建器
		 * @see  HttpHeaders＃setAllow（Set）
		 */
		B allow(HttpMethod... allowedMethods);

		/**
		 * Set the entity tag of the body, as specified by the {@code ETag} header.
		 * @param etag the new entity tag
		 * @return this builder
		 * @see HttpHeaders#setETag(String)
		 */
		/**
		 * 设置正文的实体标签，如{@code  ETag}标头所指定。 
		 *  
		 * @param 标记新的实体标签
		 * @return 此构建器
		 * @see  HttpHeaders＃setETag（String）
		 */
		B eTag(String etag);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @since 5.1.4
		 * @see HttpHeaders#setLastModified(ZonedDateTime)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  
		 * @param  lastModified的最后修改日期
		 * @return 此构建器@始于5.1.4 
		 * @see  HttpHeaders＃setLastModified（ZonedDateTime）
		 */
		B lastModified(ZonedDateTime lastModified);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @since 5.1.4
		 * @see HttpHeaders#setLastModified(Instant)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  
		 * @param  lastModified的最后修改日期
		 * @return 此构建器@始于5.1.4 
		 * @see  HttpHeaders＃setLastModified（Instant）
		 */
		B lastModified(Instant lastModified);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * <p>The date should be specified as the number of milliseconds since
		 * January 1, 1970 GMT.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @see HttpHeaders#setLastModified(long)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  <p>日期应指定为格林尼治标准时间1970年1月1日以来的毫秒数。 
		 *  
		 * @param  lastModified最后修改日期
		 * @return 此构建器
		 * @see  HttpHeaders＃setLastModified（long）
		 */
		B lastModified(long lastModified);

		/**
		 * Set the location of a resource, as specified by the {@code Location} header.
		 * @param location the location
		 * @return this builder
		 * @see HttpHeaders#setLocation(URI)
		 */
		/**
		 * 设置资源的位置，如{@code  Location}标头所指定。 
		 *  
		 * @param  location位置
		 * @return 此构建器
		 * @see  HttpHeaders＃setLocation（URI）
		 */
		B location(URI location);

		/**
		 * Set the caching directives for the resource, as specified by the HTTP 1.1
		 * {@code Cache-Control} header.
		 * <p>A {@code CacheControl} instance can be built like
		 * {@code CacheControl.maxAge(3600).cachePublic().noTransform()}.
		 * @param cacheControl a builder for cache-related HTTP response headers
		 * @return this builder
		 * @since 4.2
		 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">RFC-7234 Section 5.2</a>
		 */
		/**
		 * 设置资源的高速缓存指令，如HTTP 1.1 {@code  Cache-Control}标头所指定。 
		 *  <p>可以像{@code  CacheControl.maxAge（3600）.cachePublic（）。 
		 * noTransform（）}一样构建{@code  CacheControl}实例。 
		 *  
		 * @param  cacheControl用于与缓存相关的HTTP响应标头的构建器
		 * @return 此构建器@始于4.2 
		 * @see  <a href ="https://tools.ietf.org/html/rfc7234#section-5.2"> RFC-7234第5.2节</a>
		 */
		B cacheControl(CacheControl cacheControl);

		/**
		 * Configure one or more request header names (e.g. "Accept-Language") to
		 * add to the "Vary" response header to inform clients that the response is
		 * subject to content negotiation and variances based on the value of the
		 * given request headers. The configured request header names are added only
		 * if not already present in the response "Vary" header.
		 * @param requestHeaders request header names
		 * @since 4.3
		 */
		/**
		 * 配置一个或多个请求标头名称（例如"Accept-Language"）以添加到"Vary"响应标头中，以告知客户端响应取决于内容协商和基于给定请求标头值的变化。 
		 * 仅当响应"Vary"标头中不存在已配置的请求标头名称时，才添加该名称。 
		 *  
		 * @param  requestHeaders请求标头名称@4.3起
		 */
		B varyBy(String... requestHeaders);

		/**
		 * Build the response entity with no body.
		 * @return the response entity
		 * @see BodyBuilder#body(Object)
		 */
		/**
		 * 建立没有实体的响应实体。 
		 *  
		 * @return 响应实体
		 * @see  BodyBuilder＃body（Object）
		 */
		<T> ResponseEntity<T> build();
	}


	/**
	 * Defines a builder that adds a body to the response entity.
	 * @since 4.1
	 */
	/**
	 * 定义一个将主体添加到响应实体的构建器。 
	 *  @始于4.1
	 */
	public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

		/**
		 * Set the length of the body in bytes, as specified by the
		 * {@code Content-Length} header.
		 * @param contentLength the content length
		 * @return this builder
		 * @see HttpHeaders#setContentLength(long)
		 */
		/**
		 * 按照{@code  Content-Length}标头指定的内容，以字节为单位设置正文的长度。 
		 *  
		 * @param  contentLength内容长度
		 * @return 此构建器
		 * @see  HttpHeaders＃setContentLength（long）
		 */
		BodyBuilder contentLength(long contentLength);

		/**
		 * Set the {@linkplain MediaType media type} of the body, as specified by the
		 * {@code Content-Type} header.
		 * @param contentType the content type
		 * @return this builder
		 * @see HttpHeaders#setContentType(MediaType)
		 */
		/**
		 * 设置主体的{@link  plain MediaType媒体类型}，如{@code  Content-Type}标头所指定。 
		 *  
		 * @param  contentType内容类型
		 * @return 此构建器
		 * @see  HttpHeaders＃setContentType（MediaType）
		 */
		BodyBuilder contentType(MediaType contentType);

		/**
		 * Set the body of the response entity and returns it.
		 * @param <T> the type of the body
		 * @param body the body of the response entity
		 * @return the built response entity
		 */
		/**
		 * 设置响应实体的主体并返回它。 
		 *  
		 * @param  <T>主体的类型
		 * @param 主体响应实体的主体
		 * @return 构建的响应实体
		 */
		<T> ResponseEntity<T> body(@Nullable T body);
	}


	private static class DefaultBuilder implements BodyBuilder {

		private final Object statusCode;

		private final HttpHeaders headers = new HttpHeaders();

		public DefaultBuilder(Object statusCode) {
			this.statusCode = statusCode;
		}

		@Override
		public BodyBuilder header(String headerName, String... headerValues) {
			for (String headerValue : headerValues) {
				this.headers.add(headerName, headerValue);
			}
			return this;
		}

		@Override
		public BodyBuilder headers(@Nullable HttpHeaders headers) {
			if (headers != null) {
				this.headers.putAll(headers);
			}
			return this;
		}

		@Override
		public BodyBuilder headers(Consumer<HttpHeaders> headersConsumer) {
			headersConsumer.accept(this.headers);
			return this;
		}

		@Override
		public BodyBuilder allow(HttpMethod... allowedMethods) {
			this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
			return this;
		}

		@Override
		public BodyBuilder contentLength(long contentLength) {
			this.headers.setContentLength(contentLength);
			return this;
		}

		@Override
		public BodyBuilder contentType(MediaType contentType) {
			this.headers.setContentType(contentType);
			return this;
		}

		@Override
		public BodyBuilder eTag(String etag) {
			if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
				etag = "\"" + etag;
			}
			if (!etag.endsWith("\"")) {
				etag = etag + "\"";
			}
			this.headers.setETag(etag);
			return this;
		}

		@Override
		public BodyBuilder lastModified(ZonedDateTime date) {
			this.headers.setLastModified(date);
			return this;
		}

		@Override
		public BodyBuilder lastModified(Instant date) {
			this.headers.setLastModified(date);
			return this;
		}

		@Override
		public BodyBuilder lastModified(long date) {
			this.headers.setLastModified(date);
			return this;
		}

		@Override
		public BodyBuilder location(URI location) {
			this.headers.setLocation(location);
			return this;
		}

		@Override
		public BodyBuilder cacheControl(CacheControl cacheControl) {
			this.headers.setCacheControl(cacheControl);
			return this;
		}

		@Override
		public BodyBuilder varyBy(String... requestHeaders) {
			this.headers.setVary(Arrays.asList(requestHeaders));
			return this;
		}

		@Override
		public <T> ResponseEntity<T> build() {
			return body(null);
		}

		@Override
		public <T> ResponseEntity<T> body(@Nullable T body) {
			return new ResponseEntity<>(body, this.headers, this.statusCode);
		}
	}

}
