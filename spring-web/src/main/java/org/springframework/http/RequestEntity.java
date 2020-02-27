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

import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.function.Consumer;

import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

/**
 * Extension of {@link HttpEntity} that adds a {@linkplain HttpMethod method} and
 * {@linkplain URI uri}. Used in {@code RestTemplate} and {@code @Controller} methods.
 *
 * <p>In {@code RestTemplate}, this class is used as parameter in
 * {@link org.springframework.web.client.RestTemplate#exchange(RequestEntity, Class) exchange()}:
 * <pre class="code">
 * MyRequest body = ...
 * RequestEntity&lt;MyRequest&gt; request = RequestEntity
 *     .post(new URI(&quot;https://example.com/bar&quot;))
 *     .accept(MediaType.APPLICATION_JSON)
 *     .body(body);
 * ResponseEntity&lt;MyResponse&gt; response = template.exchange(request, MyResponse.class);
 * </pre>
 *
 * <p>If you would like to provide a URI template with variables, consider using
 * {@link org.springframework.web.util.DefaultUriBuilderFactory DefaultUriBuilderFactory}:
 * <pre class="code">
 * // Create shared factory
 * UriBuilderFactory factory = new DefaultUriBuilderFactory();
 *
 * // Use factory to create URL from template
 * URI uri = factory.uriString(&quot;https://example.com/{foo}&quot;).build(&quot;bar&quot;);
 * RequestEntity&lt;MyRequest&gt; request = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON).body(body);
 * </pre>
 *
 * <p>Can also be used in Spring MVC, as a parameter in a @Controller method:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public void handle(RequestEntity&lt;String&gt; request) {
 *   HttpMethod method = request.getMethod();
 *   URI url = request.getUrl();
 *   String body = request.getBody();
 * }
 * </pre>
 *
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @since 4.1
 * @param <T> the body type
 * @see #getMethod()
 * @see #getUrl()
 */
/**
 * {@link  HttpEntity}的扩展，添加了一个{@link  plain HttpMethod方法}和{@link  plain URI uri}。 
 * 在{@code  RestTemplate}和{@code  @Controller}方法中使用。 
 *  <p>在{@code  RestTemplate}中，此类用作{@link  org.springframework.web.client.RestTemplate＃exchange（RequestEntity，Class）exchange（）}中的参数：<pre class ="代码"> MyRequest正文= ... RequestEntity <MyRequest>请求= RequestEntity .post（new URI（"https://example.com/bar"））.accept（MediaType.APPLICATION_JSON）.body（body）; ResponseEntity <MyResponse> response = template.exchange（request，MyResponse.class）; </ pre> <p>如果您想为URI模板提供变量，请考虑使用{@link  org.springframework.web.util.DefaultUriBuilderFactory DefaultUriBuilderFactory}：<pre class ="code"> //创建共享factory UriBuilderFactory factory =新的DefaultUriBuilderFactory（）; //使用factory从模板URI创建URL uri = factory.uriString（"https://example.com/{foo}"）.build（"bar"）; RequestEntity <MyRequest>请求= RequestEntity.post（uri）.accept（MediaType.APPLICATION_JSON）.body（body）; </ pre> <p>也可以在Spring MVC中用作@Controller方法中的参数：<pre class ="code"> @RequestMapping（"/ handle"）公共无效句柄（RequestEntity <String>请求） {HttpMethod method = request.getMethod（）; URI url = request.getUrl（）;字符串体= request.getBody（）; } </ pre> @author  Arjen Poutsma @author 塞巴斯蒂安·德勒兹@since 4.1 
 * @param  <T>身体类型
 * @see  #getMethod（）
 * @see  #getUrl（）
 */
public class RequestEntity<T> extends HttpEntity<T> {

	@Nullable
	private final HttpMethod method;

	private final URI url;

	@Nullable
	private final Type type;


	/**
	 * Constructor with method and URL but without body nor headers.
	 * @param method the method
	 * @param url the URL
	 */
	/**
	 * 具有方法和URL的构造函数，但没有正文或标头。 
	 *  
	 * @param 方法方法
	 * @param  URL URL
	 */
	public RequestEntity(HttpMethod method, URI url) {
		this(null, null, method, url, null);
	}

	/**
	 * Constructor with method, URL and body but without headers.
	 * @param body the body
	 * @param method the method
	 * @param url the URL
	 */
	/**
	 * 具有方法，URL和正文但不包含标题的构造函数。 
	 *  
	 * @param 正文正文
	 * @param 方法方法
	 * @param  url网址
	 */
	public RequestEntity(@Nullable T body, HttpMethod method, URI url) {
		this(body, null, method, url, null);
	}

	/**
	 * Constructor with method, URL, body and type but without headers.
	 * @param body the body
	 * @param method the method
	 * @param url the URL
	 * @param type the type used for generic type resolution
	 * @since 4.3
	 */
	/**
	 * 具有方法，URL，正文和类型的构造函数，但不包含标题。 
	 *  
	 * @param 正文正文
	 * @param 方法方法
	 * @param  url网址
	 * @param 输入用于通用类型解析的类型，自4.3起
	 */
	public RequestEntity(@Nullable T body, HttpMethod method, URI url, Type type) {
		this(body, null, method, url, type);
	}

	/**
	 * Constructor with method, URL and headers but without body.
	 * @param headers the headers
	 * @param method the method
	 * @param url the URL
	 */
	/**
	 * 具有方法，URL和标题的构造函数，但没有主体。 
	 *  
	 * @param 标头标头
	 * @param 方法方法
	 * @param 对URL进行URL
	 */
	public RequestEntity(MultiValueMap<String, String> headers, HttpMethod method, URI url) {
		this(null, headers, method, url, null);
	}

	/**
	 * Constructor with method, URL, headers and body.
	 * @param body the body
	 * @param headers the headers
	 * @param method the method
	 * @param url the URL
	 */
	/**
	 * 带有方法，URL，标题和正文的构造函数。 
	 *  
	 * @param 正文正文
	 * @param 标头标头
	 * @param 方法方法
	 * @param  url的URL
	 */
	public RequestEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers,
			@Nullable HttpMethod method, URI url) {

		this(body, headers, method, url, null);
	}

	/**
	 * Constructor with method, URL, headers, body and type.
	 * @param body the body
	 * @param headers the headers
	 * @param method the method
	 * @param url the URL
	 * @param type the type used for generic type resolution
	 * @since 4.3
	 */
	/**
	 * 带有方法，URL，标题，正文和类型的构造函数。 
	 *  
	 * @param 正文正文
	 * @param 标头标头
	 * @param 方法方法
	 * @param  url URL 
	 * @param 类型用于泛型类型解析的类型@4.3起
	 */
	public RequestEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers,
			@Nullable HttpMethod method, URI url, @Nullable Type type) {

		super(body, headers);
		this.method = method;
		this.url = url;
		this.type = type;
	}


	/**
	 * Return the HTTP method of the request.
	 * @return the HTTP method as an {@code HttpMethod} enum value
	 */
	/**
	 * 返回请求的HTTP方法。 
	 *  
	 * @return  HTTP方法作为{@code  HttpMethod}枚举值
	 */
	@Nullable
	public HttpMethod getMethod() {
		return this.method;
	}

	/**
	 * Return the URL of the request.
	 * @return the URL as a {@code URI}
	 */
	/**
	 * 返回请求的URL。 
	 *  
	 * @return  URL作为{<@@code> URI}
	 */
	public URI getUrl() {
		return this.url;
	}

	/**
	 * Return the type of the request's body.
	 * @return the request's body type, or {@code null} if not known
	 * @since 4.3
	 */
	/**
	 * 返回请求正文的类型。 
	 *  
	 * @return 请求的正文类型，如果不知道，则为{@code  null}（自4.3开始）
	 */
	@Nullable
	public Type getType() {
		if (this.type == null) {
			T body = getBody();
			if (body != null) {
				return body.getClass();
			}
		}
		return this.type;
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!super.equals(other)) {
			return false;
		}
		RequestEntity<?> otherEntity = (RequestEntity<?>) other;
		return (ObjectUtils.nullSafeEquals(getMethod(), otherEntity.getMethod()) &&
				ObjectUtils.nullSafeEquals(getUrl(), otherEntity.getUrl()));
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.method);
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.url);
		return hashCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("<");
		builder.append(getMethod());
		builder.append(' ');
		builder.append(getUrl());
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
	 * Create a builder with the given method and url.
	 * @param method the HTTP method (GET, POST, etc)
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的方法和URL创建一个构建器。 
	 *  
	 * @param 方法HTTP方法（GET，POST等）
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static BodyBuilder method(HttpMethod method, URI url) {
		return new DefaultBodyBuilder(method, url);
	}

	/**
	 * Create an HTTP GET builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP GET构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static HeadersBuilder<?> get(URI url) {
		return method(HttpMethod.GET, url);
	}

	/**
	 * Create an HTTP HEAD builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP HEAD构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static HeadersBuilder<?> head(URI url) {
		return method(HttpMethod.HEAD, url);
	}

	/**
	 * Create an HTTP POST builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP POST构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static BodyBuilder post(URI url) {
		return method(HttpMethod.POST, url);
	}

	/**
	 * Create an HTTP PUT builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP PUT构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static BodyBuilder put(URI url) {
		return method(HttpMethod.PUT, url);
	}

	/**
	 * Create an HTTP PATCH builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP PATCH构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static BodyBuilder patch(URI url) {
		return method(HttpMethod.PATCH, url);
	}

	/**
	 * Create an HTTP DELETE builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP DELETE构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static HeadersBuilder<?> delete(URI url) {
		return method(HttpMethod.DELETE, url);
	}

	/**
	 * Creates an HTTP OPTIONS builder with the given url.
	 * @param url the URL
	 * @return the created builder
	 */
	/**
	 * 使用给定的URL创建一个HTTP OPTIONS构建器。 
	 *  
	 * @param  URL URL 
	 * @return 创建的构建器
	 */
	public static HeadersBuilder<?> options(URI url) {
		return method(HttpMethod.OPTIONS, url);
	}


	/**
	 * Defines a builder that adds headers to the request entity.
	 * @param <B> the builder subclass
	 */
	/**
	 * 定义一个将标题添加到请求实体的构建器。 
	 *  
	 * @param  <B>构建器子类
	 */
	public interface HeadersBuilder<B extends HeadersBuilder<B>> {

		/**
		 * Add the given, single header value under the given name.
		 * @param headerName  the header name
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
		 * @since 5.2
		 * @see HttpHeaders#add(String, String)
		 */
		/**
		 * 将给定的标题复制到实体的标题映射中。 
		 *  
		 * @param 标头要从此构建器
		 * @return 复制的现有HttpHeaders @@5.2起
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
		 * Set the list of acceptable {@linkplain MediaType media types}, as
		 * specified by the {@code Accept} header.
		 * @param acceptableMediaTypes the acceptable media types
		 */
		/**
		 * 设置可接受的{@link  plain MediaType媒体类型}列表，如{@code  Accept}标头所指定。 
		 *  
		 * @param  acceptMediaMedias可接受的媒体类型
		 */
		B accept(MediaType... acceptableMediaTypes);

		/**
		 * Set the list of acceptable {@linkplain Charset charsets}, as specified
		 * by the {@code Accept-Charset} header.
		 * @param acceptableCharsets the acceptable charsets
		 */
		/**
		 * 按照{@code  Accept-Charset}标头指定的设置可接受的{@link  plain Charset字符集}的列表。 
		 *  
		 * @param  acceptableCharsets可接受的字符集
		 */
		B acceptCharset(Charset... acceptableCharsets);

		/**
		 * Set the value of the {@code If-Modified-Since} header.
		 * @param ifModifiedSince the new value of the header
		 * @since 5.1.4
		 */
		/**
		 * 设置{@code  If-Modified-Since}标头的值。 
		 *  
		 * @param  ifModifiedSince标头的新值@since 5.1.4起
		 */
		B ifModifiedSince(ZonedDateTime ifModifiedSince);

		/**
		 * Set the value of the {@code If-Modified-Since} header.
		 * @param ifModifiedSince the new value of the header
		 * @since 5.1.4
		 */
		/**
		 * 设置{@code  If-Modified-Since}标头的值。 
		 *  
		 * @param  ifModifiedSince标头的新值@since 5.1.4起
		 */
		B ifModifiedSince(Instant ifModifiedSince);

		/**
		 * Set the value of the {@code If-Modified-Since} header.
		 * <p>The date should be specified as the number of milliseconds since
		 * January 1, 1970 GMT.
		 * @param ifModifiedSince the new value of the header
		 */
		/**
		 * 设置{@code  If-Modified-Since}标头的值。 
		 *  <p>日期应指定为格林尼治标准时间1970年1月1日以来的毫秒数。 
		 *  
		 * @param  ifModifiedSince标头的新值
		 */
		B ifModifiedSince(long ifModifiedSince);

		/**
		 * Set the values of the {@code If-None-Match} header.
		 * @param ifNoneMatches the new value of the header
		 */
		/**
		 * 设置{@code  If-None-Match}标头的值。 
		 *  
		 * @param  ifNoneMatch标题的新值
		 */
		B ifNoneMatch(String... ifNoneMatches);

		/**
		 * Builds the request entity with no body.
		 * @return the request entity
		 * @see BodyBuilder#body(Object)
		 */
		/**
		 * 构建没有实体的请求实体。 
		 *  
		 * @return 请求实体
		 * @see  BodyBuilder＃body（Object）
		 */
		RequestEntity<Void> build();
	}


	/**
	 * Defines a builder that adds a body to the response entity.
	 */
	/**
	 * 定义一个将主体添加到响应实体的构建器。 
	 * 
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
		 * Set the {@linkplain MediaType media type} of the body, as specified
		 * by the {@code Content-Type} header.
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
		 * Set the body of the request entity and build the RequestEntity.
		 * @param <T> the type of the body
		 * @param body the body of the request entity
		 * @return the built request entity
		 */
		/**
		 * 设置请求实体的主体并构建RequestEntity。 
		 *  
		 * @param  <T>正文的类型
		 * @param 正文请求实体的正文
		 * @return 已构建的请求实体
		 */
		<T> RequestEntity<T> body(T body);

		/**
		 * Set the body and type of the request entity and build the RequestEntity.
		 * @param <T> the type of the body
		 * @param body the body of the request entity
		 * @param type the type of the body, useful for generic type resolution
		 * @return the built request entity
		 * @since 4.3
		 */
		/**
		 * 设置请求实体的主体和类型，并构建RequestEntity。 
		 *  
		 * @param  <T>主体的类型
		 * @param 主体请求实体的主体
		 * @param 类型主体的类型，对于通用类型解析
		 * @return 构建的请求实体@since 4.3很有用
		 */
		<T> RequestEntity<T> body(T body, Type type);
	}


	private static class DefaultBodyBuilder implements BodyBuilder {

		private final HttpMethod method;

		private final URI url;

		private final HttpHeaders headers = new HttpHeaders();

		public DefaultBodyBuilder(HttpMethod method, URI url) {
			this.method = method;
			this.url = url;
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
		public BodyBuilder accept(MediaType... acceptableMediaTypes) {
			this.headers.setAccept(Arrays.asList(acceptableMediaTypes));
			return this;
		}

		@Override
		public BodyBuilder acceptCharset(Charset... acceptableCharsets) {
			this.headers.setAcceptCharset(Arrays.asList(acceptableCharsets));
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
		public BodyBuilder ifModifiedSince(ZonedDateTime ifModifiedSince) {
			this.headers.setIfModifiedSince(ifModifiedSince);
			return this;
		}

		@Override
		public BodyBuilder ifModifiedSince(Instant ifModifiedSince) {
			this.headers.setIfModifiedSince(ifModifiedSince);
			return this;
		}

		@Override
		public BodyBuilder ifModifiedSince(long ifModifiedSince) {
			this.headers.setIfModifiedSince(ifModifiedSince);
			return this;
		}

		@Override
		public BodyBuilder ifNoneMatch(String... ifNoneMatches) {
			this.headers.setIfNoneMatch(Arrays.asList(ifNoneMatches));
			return this;
		}

		@Override
		public RequestEntity<Void> build() {
			return new RequestEntity<>(this.headers, this.method, this.url);
		}

		@Override
		public <T> RequestEntity<T> body(T body) {
			return new RequestEntity<>(body, this.headers, this.method, this.url);
		}

		@Override
		public <T> RequestEntity<T> body(T body, Type type) {
			return new RequestEntity<>(body, this.headers, this.method, this.url, type);
		}
	}

}
