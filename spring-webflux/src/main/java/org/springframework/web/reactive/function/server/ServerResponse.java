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

package org.springframework.web.reactive.function.server;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;

/**
 * Represents a typed server-side HTTP response, as returned
 * by a {@linkplain HandlerFunction handler function} or
 * {@linkplain HandlerFilterFunction filter function}.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * 表示由{@link  plain HandlerFunction处理程序函数}或{@link  plain HandlerFilterFunction过滤器函数}返回的类型化的服务器端HTTP响应。 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @author 塞巴斯蒂安·德勒兹@5.0
 */
public interface ServerResponse {

	/**
	 * Return the status code of this response.
	 * @return the status as an HttpStatus enum value
	 * @throws IllegalArgumentException in case of an unknown HTTP status code
	 * @see HttpStatus#valueOf(int)
	 */
	/**
	 * 返回此响应的状态码。 
	 *  
	 * @return 状态为HttpStatus枚举值
	 * @throws  IllegalArgumentException，如果HTTP状态代码未知
	 * @see  HttpStatus＃valueOf（int）
	 */
	HttpStatus statusCode();

	/**
	 * Return the (potentially non-standard) status code of this response.
	 * @return the status as an integer
	 * @since 5.2
	 * @see #statusCode()
	 * @see HttpStatus#resolve(int)
	 */
	/**
	 * 返回此响应的（可能是非标准的）状态代码。 
	 *  
	 * @return 状态为整数@5.2起@
	 * @see> #statusCode（）
	 * @see  HttpStatus＃resolve（int）
	 */
	int rawStatusCode();

	/**
	 * Return the headers of this response.
	 */
	/**
	 * 返回此响应的标头。 
	 * 
	 */
	HttpHeaders headers();

	/**
	 * Return the cookies of this response.
	 */
	/**
	 * 返回此响应的cookie。 
	 * 
	 */
	MultiValueMap<String, ResponseCookie> cookies();

	/**
	 * Write this response to the given web exchange.
	 * @param exchange the web exchange to write to
	 * @param context the context to use when writing
	 * @return {@code Mono<Void>} to indicate when writing is complete
	 */
	/**
	 * 将此响应写到给定的Web交换。 
	 *  
	 * @param 交换Web交换将
	 * @return  {@code  Mono <Void>}写入时使用的上下文写入
	 * @param 上下文，以指示写入完成
	 */
	Mono<Void> writeTo(ServerWebExchange exchange, Context context);


	// Static methods

	/**
	 * Create a builder with the status code and headers of the given response.
	 * @param other the response to copy the status and headers from
	 * @return the created builder
	 */
	/**
	 * 使用状态代码和给定响应的标题创建一个构建器。 
	 *  
	 * @param 其他从
	 * @return 创建的构建器复制状态和标题的响应
	 */
	static BodyBuilder from(ServerResponse other) {
		return new DefaultServerResponseBuilder(other);
	}

	/**
	 * Create a builder with the given HTTP status.
	 * @param status the response status
	 * @return the created builder
	 */
	/**
	 * 创建具有给定HTTP状态的构建器。 
	 *  
	 * @param 状态响应状态
	 * @return 创建的构建器
	 */
	static BodyBuilder status(HttpStatus status) {
		return new DefaultServerResponseBuilder(status);
	}

	/**
	 * Create a builder with the given HTTP status.
	 * @param status the response status
	 * @return the created builder
	 * @since 5.0.3
	 */
	/**
	 * 创建具有给定HTTP状态的构建器。 
	 *  
	 * @param 状态响应状态
	 * @return 创建的构建器@since 5.0.3
	 */
	static BodyBuilder status(int status) {
		return new DefaultServerResponseBuilder(status);
	}

	/**
	 * Create a builder with the status set to {@linkplain HttpStatus#OK 200 OK}.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态设置为{@link  plain HttpStatus＃OK 200 OK}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static BodyBuilder ok() {
		return status(HttpStatus.OK);
	}

	/**
	 * Create a new builder with a {@linkplain HttpStatus#CREATED 201 Created} status
	 * and a location header set to the given URI.
	 * @param location the location URI
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃CREATED 201 Created}且位置标头设置为给定URI的新构建器。 
	 *  
	 * @param 位置位置URI 
	 * @return 创建的构建器
	 */
	static BodyBuilder created(URI location) {
		BodyBuilder builder = status(HttpStatus.CREATED);
		return builder.location(location);
	}

	/**
	 * Create a builder with an {@linkplain HttpStatus#ACCEPTED 202 Accepted} status.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃ACCEPTED 202 Accepted}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static BodyBuilder accepted() {
		return status(HttpStatus.ACCEPTED);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#NO_CONTENT 204 No Content} status.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃NO_CONTENT 204 No Content}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static HeadersBuilder<?> noContent() {
		return status(HttpStatus.NO_CONTENT);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#SEE_OTHER 303 See Other}
	 * status and a location header set to the given URI.
	 * @param location the location URI
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃SEE_OTHER 303 See Other}的构建器，并将位置标头设置为给定的URI。 
	 *  
	 * @param 位置位置URI 
	 * @return 创建的构建器
	 */
	static BodyBuilder seeOther(URI location) {
		BodyBuilder builder = status(HttpStatus.SEE_OTHER);
		return builder.location(location);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#TEMPORARY_REDIRECT 307 Temporary Redirect}
	 * status and a location header set to the given URI.
	 * @param location the location URI
	 * @return the created builder
	 */
	/**
	 * 创建一个{{@link> plain HttpStatus＃TEMPORARY_REDIRECT 307 Temporary Redirect}状态和位置标头设置为给定URI的构建器。 
	 *  
	 * @param 位置位置URI 
	 * @return 创建的构建器
	 */
	static BodyBuilder temporaryRedirect(URI location) {
		BodyBuilder builder = status(HttpStatus.TEMPORARY_REDIRECT);
		return builder.location(location);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#PERMANENT_REDIRECT 308 Permanent Redirect}
	 * status and a location header set to the given URI.
	 * @param location the location URI
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃PERMANENT_REDIRECT 308永久重定向}且位置标头设置为给定URI的构建器。 
	 *  
	 * @param 位置位置URI 
	 * @return 创建的构建器
	 */
	static BodyBuilder permanentRedirect(URI location) {
		BodyBuilder builder = status(HttpStatus.PERMANENT_REDIRECT);
		return builder.location(location);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#BAD_REQUEST 400 Bad Request} status.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃BAD_REQUEST 400 Bad Request}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static BodyBuilder badRequest() {
		return status(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Create a builder with a {@linkplain HttpStatus#NOT_FOUND 404 Not Found} status.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃NOT_FOUND 404 Not Found}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static HeadersBuilder<?> notFound() {
		return status(HttpStatus.NOT_FOUND);
	}

	/**
	 * Create a builder with an
	 * {@linkplain HttpStatus#UNPROCESSABLE_ENTITY 422 Unprocessable Entity} status.
	 * @return the created builder
	 */
	/**
	 * 创建一个状态为{@link  plain HttpStatus＃UNPROCESSABLE_ENTITY 422无法处理的实体}的构建器。 
	 *  
	 * @return 创建的生成器
	 */
	static BodyBuilder unprocessableEntity() {
		return status(HttpStatus.UNPROCESSABLE_ENTITY);
	}


	/**
	 * Defines a builder that adds headers to the response.
	 * @param <B> the builder subclass
	 */
	/**
	 * 定义一个将标题添加到响应的构建器。 
	 *  
	 * @param  <B>构建器子类
	 */
	interface HeadersBuilder<B extends HeadersBuilder<B>> {

		/**
		 * Add the given header value(s) under the given name.
		 * @param headerName   the header name
		 * @param headerValues the header value(s)
		 * @return this builder
		 * @see HttpHeaders#add(String, String)
		 */
		/**
		 * 在给定名称下添加给定标头值。 
		 *  
		 * @param  headerName标头名称
		 * @param  headerValues标头值
		 * @return 此构建器
		 * @see  HttpHeaders＃add（String，String）
		 */
		B header(String headerName, String... headerValues);

		/**
		 * Manipulate this response's headers with the given consumer. The
		 * headers provided to the consumer are "live", so that the consumer can be used to
		 * {@linkplain HttpHeaders#set(String, String) overwrite} existing header values,
		 * {@linkplain HttpHeaders#remove(Object) remove} values, or use any of the other
		 * {@link HttpHeaders} methods.
		 * @param headersConsumer a function that consumes the {@code HttpHeaders}
		 * @return this builder
		 */
		/**
		 * 与给定的使用者操作此响应的标头。 
		 * 提供给使用者的标头是"活动的"，因此使用者可以用来{@link 纯HttpHeaders＃set（String，String）覆盖}现有标头值{@link 纯HttpHeaders＃remove（Object ）删除}值，或使用其他任何{@link  HttpHeaders}方法。 
		 *  
		 * @param  headers消费一个使用{@code  HttpHeaders} 
		 * @return 此构建器的函数
		 */
		B headers(Consumer<HttpHeaders> headersConsumer);

		/**
		 * Add the given cookie to the response.
		 * @param cookie the cookie to add
		 * @return this builder
		 */
		/**
		 * 将给定的cookie添加到响应中。 
		 *  
		 * @param  cookie cookie以添加
		 * @return 此构建器
		 */
		B cookie(ResponseCookie cookie);

		/**
		 * Manipulate this response's cookies with the given consumer. The
		 * cookies provided to the consumer are "live", so that the consumer can be used to
		 * {@linkplain MultiValueMap#set(Object, Object) overwrite} existing cookies,
		 * {@linkplain MultiValueMap#remove(Object) remove} cookies, or use any of the other
		 * {@link MultiValueMap} methods.
		 * @param cookiesConsumer a function that consumes the cookies
		 * @return this builder
		 */
		/**
		 * 与给定的消费者操作此响应的cookie。 
		 * 提供给使用者的cookie是"活动的"，因此可以使用使用者{@link  plain MultiValueMap＃set（Object，Object）覆盖}现有的cookie，{<@link> plain MultiValueMap＃remove（Object）删除} cookie，或使用其他任何{@link  MultiValueMap}方法。 
		 *  
		 * @param  cookies消费一个消耗cookie的函数，该构建器
		 * @return 
		 */
		B cookies(Consumer<MultiValueMap<String, ResponseCookie>> cookiesConsumer);

		/**
		 * Set the set of allowed {@link HttpMethod HTTP methods}, as specified
		 * by the {@code Allow} header.
		 *
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
		B allow(Set<HttpMethod> allowedMethods);

		/**
		 * Set the entity tag of the body, as specified by the {@code ETag} header.
		 * @param eTag the new entity tag
		 * @return this builder
		 * @see HttpHeaders#setETag(String)
		 */
		/**
		 * 设置正文的实体标签，如{@code  ETag}标头所指定。 
		 *  
		 * @param  eTag新实体标签
		 * @return 此构建器
		 * @see  HttpHeaders＃setETag（String）
		 */
		B eTag(String eTag);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @see HttpHeaders#setLastModified(long)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  
		 * @param  lastModified最后修改日期
		 * @return 此构建器
		 * @see  HttpHeaders＃setLastModified（long）
		 */
		B lastModified(ZonedDateTime lastModified);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @since 5.1.4
		 * @see HttpHeaders#setLastModified(long)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  
		 * @param  lastModified的最后修改日期
		 * @return 此构建器@始于5.1.4 
		 * @see  HttpHeaders＃setLastModified（long）
		 */
		B lastModified(Instant lastModified);

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
		 * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">RFC-7234 Section 5.2</a>
		 */
		/**
		 * 设置资源的高速缓存指令，如HTTP 1.1 {@code  Cache-Control}标头所指定。 
		 *  <p>可以像{@code  CacheControl.maxAge（3600）.cachePublic（）。 
		 * noTransform（）}一样构建{@code  CacheControl}实例。 
		 *  
		 * @param  cacheControl用于与缓存相关的HTTP响应标头的构建器
		 * @return 此构建器
		 * @see  <a href="https://tools.ietf.org/html/rfc7234#section-5.2"> RFC- 7234第5.2节</a>
		 */
		B cacheControl(CacheControl cacheControl);

		/**
		 * Configure one or more request header names (e.g. "Accept-Language") to
		 * add to the "Vary" response header to inform clients that the response is
		 * subject to content negotiation and variances based on the value of the
		 * given request headers. The configured request header names are added only
		 * if not already present in the response "Vary" header.
		 * @param requestHeaders request header names
		 * @return this builder
		 */
		/**
		 * 配置一个或多个请求标头名称（例如"Accept-Language"）以添加到"Vary"响应标头中，以告知客户端响应取决于内容协商和基于给定请求标头值的变化。 
		 * 仅当响应"Vary"标头中不存在已配置的请求标头名称时，才添加该名称。 
		 *  
		 * @param  requestHeaders请求标头名称
		 * @return 此构建器
		 */
		B varyBy(String... requestHeaders);

		/**
		 * Build the response entity with no body.
		 */
		/**
		 * 建立没有实体的响应实体。 
		 * 
		 */
		Mono<ServerResponse> build();

		/**
		 * Build the response entity with no body.
		 * The response will be committed when the given {@code voidPublisher} completes.
		 * @param voidPublisher publisher publisher to indicate when the response should be committed
		 */
		/**
		 * 建立没有实体的响应实体。 
		 * 给定的{@code  voidPublisher}完成时，将提交响应。 
		 *  
		 * @param  voidPublisher发布者发布者指示何时应提交响应
		 */
		Mono<ServerResponse> build(Publisher<Void> voidPublisher);

		/**
		 * Build the response entity with a custom writer function.
		 * @param writeFunction the function used to write to the {@link ServerWebExchange}
		 */
		/**
		 * 使用自定义编写器功能构建响应实体。 
		 *  
		 * @param  writeFunction用于写入{@link  ServerWebExchange}的函数
		 */
		Mono<ServerResponse> build(BiFunction<ServerWebExchange, Context, Mono<Void>> writeFunction);
	}


	/**
	 * Defines a builder that adds a body to the response.
	 */
	/**
	 * 定义一个将主体添加到响应的构建器。 
	 * 
	 */
	interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

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
		 * Add a serialization hint like {@link Jackson2CodecSupport#JSON_VIEW_HINT}
		 * to customize how the body will be serialized.
		 * @param key the hint key
		 * @param value the hint value
		 */
		/**
		 * 添加诸如{@link  Jackson2CodecSupport＃JSON_VIEW_HINT}之类的序列化提示，以自定义主体的序列化方式。 
		 *  
		 * @param 键提示键
		 * @param 值提示值
		 */
		BodyBuilder hint(String key, Object value);

		/**
		 * Customize the serialization hints with the given consumer.
		 * @param hintsConsumer a function that consumes the hints
		 * @return this builder
		 * @since 5.1.6
		 */
		/**
		 * 使用给定的使用者自定义序列化提示。 
		 *  
		 * @param  hints消费使用提示的函数
		 * @return 此构建器@since 5.1.6
		 */
		BodyBuilder hints(Consumer<Map<String, Object>> hintsConsumer);

		/**
		 * Set the body of the response to the given {@code Object} and return it.
		 * This is a shortcut for using a {@link #body(BodyInserter)} with a
		 * {@linkplain BodyInserters#fromValue value inserter}.
		 * @param body the body of the response
		 * @return the built response
		 * @throws IllegalArgumentException if {@code body} is a
		 * {@link Publisher} or producer known to {@link ReactiveAdapterRegistry}
		 * @since 5.2
		 */
		/**
		 * 将响应的主体设置为给定的{@code  Object}并返回它。 
		 * 这是将{@link  #body（BodyInserter）}与{@link  plain BodyInserters＃fromValue值插入器}结合使用的快捷方式。 
		 *  
		 * @param 主体响应的主体
		 * @return 内置响应
		 * @throws  IllegalArgumentException如果{@code 主体}是{@link 发布者}或{@link  ReactiveAdapterRegistry}已知的生产者@5.2起
		 */
		Mono<ServerResponse> bodyValue(Object body);

		/**
		 * Set the body from the given {@code Publisher}. Shortcut for
		 * {@link #body(BodyInserter)} with a
		 * {@linkplain BodyInserters#fromPublisher Publisher inserter}.
		 * @param publisher the {@code Publisher} to write to the response
		 * @param elementClass the type of elements published
		 * @param <T> the type of the elements contained in the publisher
		 * @param <P> the type of the {@code Publisher}
		 * @return the built response
		 */
		/**
		 * 从给定的{@code  Publisher}设置正文。 
		 *  {@link  #body（BodyInserter）}的快捷方式，带有{@link  plain BodyInserters＃fromPublisher Publisher插入程序}。 
		 *  
		 * @param 发布者{{@@code> Publisher}向响应
		 * @param  elementClass写入已发布元素的类型
		 * @param  <T>发布者中包含的元素类型
		 * @param  <P > {@code  Publisher}的类型
		 * @return 构建的响应
		 */
		<T, P extends Publisher<T>> Mono<ServerResponse> body(P publisher, Class<T> elementClass);

		/**
		 * Variant of {@link #body(Publisher, Class)} that allows using any
		 * producer that can be resolved to {@link Publisher} via
		 * {@link ReactiveAdapterRegistry}.
		 * @param publisher the {@code Publisher} to use to write the response
		 * @param elementTypeRef the type of elements produced
		 * @param <T> the type of the elements contained in the publisher
		 * @param <P> the type of the {@code Publisher}
		 * @return the built response
		 */
		/**
		 * {@link  #body（Publisher，Class）}的变体，它允许使用可以通过{@link  ReactiveAdapterRegistry}解析为{@link  Publisher}的任何生产者。 
		 *  
		 * @param 发布者{{@@code> Publisher}用于写响应
		 * @param  elementTypeRef生成的元素类型
		 * @param  <T>发布者
		 * @param 中包含的元素类型< P> {@code  Publisher}的类型
		 * @return 构建的响应
		 */
		<T, P extends Publisher<T>> Mono<ServerResponse> body(P publisher,
				ParameterizedTypeReference<T> elementTypeRef);

		/**
		 * Variant of {@link #body(Publisher, Class)} that allows using any
		 * producer that can be resolved to {@link Publisher} via
		 * {@link ReactiveAdapterRegistry}.
		 * @param producer the producer to write to the request
		 * @param elementClass the type of elements produced
		 * @return the built response
		 * @since 5.2
		 */
		/**
		 * {@link  #body（Publisher，Class）}的变体，它允许使用可以通过{@link  ReactiveAdapterRegistry}解析为{@link  Publisher}的任何生产者。 
		 *  
		 * @param 生产者生产者将写入请求
		 * @param  elementClass产生的元素类型
		 * @return 内置响应@5.2起
		 */
		Mono<ServerResponse> body(Object producer, Class<?> elementClass);

		/**
		 * Variant of {@link #body(Publisher, ParameterizedTypeReference)} that
		 * allows using any producer that can be resolved to {@link Publisher}
		 * via {@link ReactiveAdapterRegistry}.
		 * @param producer the producer to write to the response
		 * @param elementTypeRef the type of elements produced
		 * @return the built response
		 * @since 5.2
		 */
		/**
		 * {@link  #body（Publisher，ParameterizedTypeReference）}的变体，它允许使用可以通过{@link  ReactiveAdapterRegistry}解析为{@link  Publisher}的任何生产者。 
		 *  
		 * @param 生产者生产者写入响应
		 * @param  elementTypeRef产生的元素类型
		 * @return 内置响应@since 5.2
		 */
		Mono<ServerResponse> body(Object producer, ParameterizedTypeReference<?> elementTypeRef);

		/**
		 * Set the body of the response to the given {@code BodyInserter} and return it.
		 * @param inserter the {@code BodyInserter} that writes to the response
		 * @return the built response
		 */
		/**
		 * 将响应的主体设置为给定的{@code  BodyInserter}并返回它。 
		 *  
		 * @param 插入程序{@@code> BodyInserter}，它写入响应
		 * @return 构建的响应
		 */
		Mono<ServerResponse> body(BodyInserter<?, ? super ServerHttpResponse> inserter);

		/**
		 * Set the response body to the given {@code Object} and return it.
		 * As of 5.2 this method delegates to {@link #bodyValue(Object)}.
		 * @deprecated as of Spring Framework 5.2 in favor of {@link #bodyValue(Object)}
		 */
		/**
		 * 将响应主体设置为给定的{@code  Object}并返回它。 
		 * 从5.2开始，此方法委托给{@link  #bodyValue（Object）}。 
		 * 自Spring Framework 5.2起已弃用@，推荐使用{@link  #bodyValue（Object）}
		 */
		@Deprecated
		Mono<ServerResponse> syncBody(Object body);

		/**
		 * Render the template with the given {@code name} using the given {@code modelAttributes}.
		 * The model attributes are mapped under a
		 * {@linkplain org.springframework.core.Conventions#getVariableName generated name}.
		 * <p><em>Note: Empty {@link Collection Collections} are not added to
		 * the model when using this method because we cannot correctly determine
		 * the true convention name.</em>
		 * @param name the name of the template to be rendered
		 * @param modelAttributes the modelAttributes used to render the template
		 * @return the built response
		 */
		/**
		 * 使用给定的{@code  modelAttributes}使用给定的{@code 名称}渲染模板。 
		 * 模型属性映射在{@link 纯org.springframework.core.Conventions＃getVariableName生成的名称}下。 
		 *  <p> <em>注意：使用此方法时，没有将空的{@link 集合集合}添加到模型中，因为我们无法正确确定真实的约定名称。 
		 * </ em> 
		 * @param 名称要呈现的模板
		 * @param  modelAttributes用于呈现模板的模型属性
		 * @return 构建的响应
		 */
		Mono<ServerResponse> render(String name, Object... modelAttributes);

		/**
		 * Render the template with the given {@code name} using the given {@code model}.
		 * @param name the name of the template to be rendered
		 * @param model the model used to render the template
		 * @return the built response
		 */
		/**
		 * 使用给定的{@code 模型}使用给定的{@code 名称}渲染模板。 
		 *  
		 * @param 命名要渲染的模板的名称
		 * @param 建模用于渲染模板的模型
		 * @return 构建的响应
		 */
		Mono<ServerResponse> render(String name, Map<String, ?> model);
	}


	/**
	 * Defines the context used during the {@link #writeTo(ServerWebExchange, Context)}.
	 */
	/**
	 * 定义在{@link  #writeTo（ServerWebExchange，Context）}中使用的上下文。 
	 * 
	 */
	interface Context {

		/**
		 * Return the {@link HttpMessageWriter HttpMessageWriters} to be used for response body conversion.
		 * @return the list of message writers
		 */
		/**
		 * 返回{@link  HttpMessageWriter HttpMessageWriters}以用于响应正文转换。 
		 *  
		 * @return 消息作者列表
		 */
		List<HttpMessageWriter<?>> messageWriters();

		/**
		 * Return the  {@link ViewResolver ViewResolvers} to be used for view name resolution.
		 * @return the list of view resolvers
		 */
		/**
		 * 返回{@link  ViewResolver ViewResolvers}用于视图名称解析。 
		 *  
		 * @return 视图解析器列表
		 */
		List<ViewResolver> viewResolvers();
	}

}
