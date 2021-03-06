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
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * Entity-specific subtype of {@link ServerResponse} that exposes entity data.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 5.0
 * @param <T> the entity type
 */
/**
 * {@link  ServerResponse}的特定于实体的子类型，用于公开实体数据。 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @从5.0起
 * @param  <T>实体类型
 */
public interface EntityResponse<T> extends ServerResponse {

	/**
	 * Return the entity that makes up this response.
	 */
	/**
	 * 返回组成此响应的实体。 
	 * 
	 */
	T entity();

	/**
	 * Return the {@code BodyInserter} that writes the entity to the output stream.
	 */
	/**
	 * 返回将实体写入输出流的{@code  BodyInserter}。 
	 * 
	 */
	BodyInserter<T, ? super ServerHttpResponse> inserter();


	// Static builder methods

	/**
	 * Create a builder with the given object.
	 * @param body the object that represents the body of the response
	 * @param <T> the type of the body
	 * @return the created builder
	 */
	/**
	 * 使用给定的对象创建一个生成器。 
	 *  
	 * @param 主体代表响应主体的对象
	 * @param  <T>主体的类型
	 * @return 创建的构建器
	 */
	static <T> Builder<T> fromObject(T body) {
		return new DefaultEntityResponseBuilder<>(body, BodyInserters.fromValue(body));
	}

	/**
	 * Create a builder with the given producer.
	 * @param producer the producer that represents the body of the response
	 * @param elementClass the class of elements contained in the publisher
	 * @return the created builder
	 * @since 5.2
	 */
	/**
	 * 使用给定的生产者创建一个构建器。 
	 *  
	 * @param 生产者代表响应主体的生产者
	 * @param  elementClass发布者中包含的元素类
	 * @return 创建的生成器@since 5.2
	 */
	static <T> Builder<T> fromProducer(T producer, Class<?> elementClass) {
		return new DefaultEntityResponseBuilder<>(producer,
				BodyInserters.fromProducer(producer, elementClass));
	}

	/**
	 * Create a builder with the given producer.
	 * @param producer the producer that represents the body of the response
	 * @param typeReference the type of elements contained in the producer
	 * @return the created builder
	 * @since 5.2
	 */
	/**
	 * 使用给定的生产者创建一个构建器。 
	 *  
	 * @param 生产者代表响应主体的生产者
	 * @param  typeReference生产者中包含的元素类型
	 * @return 创建的生成器@since 5.2
	 */
	static <T> Builder<T> fromProducer(T producer, ParameterizedTypeReference<?> typeReference) {
		return new DefaultEntityResponseBuilder<>(producer,
				BodyInserters.fromProducer(producer, typeReference));
	}

	/**
	 * Create a builder with the given publisher.
	 * @param publisher the publisher that represents the body of the response
	 * @param elementClass the class of elements contained in the publisher
	 * @param <T> the type of the elements contained in the publisher
	 * @param <P> the type of the {@code Publisher}
	 * @return the created builder
	 */
	/**
	 * 使用给定的发布者创建一个构建器。 
	 *  
	 * @param 发布者代表响应主体的发布者
	 * @param  elementClass发布者中包含的元素的类
	 * @param  <T>发布者中包含的元素的类型
	 * @param  <P> {@code 发布者} 
	 * @return 创建的构建器的类型
	 */
	static <T, P extends Publisher<T>> Builder<P> fromPublisher(P publisher, Class<T> elementClass) {
		return new DefaultEntityResponseBuilder<>(publisher,
				BodyInserters.fromPublisher(publisher, elementClass));
	}

	/**
	 * Create a builder with the given publisher.
	 * @param publisher the publisher that represents the body of the response
	 * @param typeReference the type of elements contained in the publisher
	 * @param <T> the type of the elements contained in the publisher
	 * @param <P> the type of the {@code Publisher}
	 * @return the created builder
	 */
	/**
	 * 使用给定的发布者创建一个构建器。 
	 *  
	 * @param 发布者代表响应主体的发布者
	 * @param  typeReference发布者中包含的元素类型
	 * @param  <T>发布者中包含的元素类型
	 * @param  <P> {@code 发布者} 
	 * @return 创建的构建器的类型
	 */
	static <T, P extends Publisher<T>> Builder<P> fromPublisher(P publisher,
			ParameterizedTypeReference<T> typeReference) {

		return new DefaultEntityResponseBuilder<>(publisher,
				BodyInserters.fromPublisher(publisher, typeReference));
	}


	/**
	 * Defines a builder for {@code EntityResponse}.
	 *
	 * @param <T> a self reference to the builder type
	 */
	/**
	 * 为{@code  EntityResponse}定义一个构建器。 
	 *  
	 * @param  <T>对构建器类型的自引用
	 */
	interface Builder<T> {

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
		Builder<T> header(String headerName, String... headerValues);

		/**
		 * Copy the given headers into the entity's headers map.
		 * @param headers the existing HttpHeaders to copy from
		 * @return this builder
		 * @see HttpHeaders#add(String, String)
		 */
		/**
		 * 将给定的标题复制到实体的标题映射中。 
		 *  
		 * @param 标头要从此构建器
		 * @return 复制的现有HttpHeaders标头
		 * @see  HttpHeaders＃add（String，String）
		 */
		Builder<T> headers(HttpHeaders headers);

		/**
		 * Set the HTTP status.
		 * @param status the response status
		 * @return this builder
		 */
		/**
		 * 设置HTTP状态。 
		 *  
		 * @param 状态响应状态
		 * @return 此构建器
		 */
		Builder<T> status(HttpStatus status);

		/**
		 * Set the HTTP status.
		 * @param status the response status
		 * @return this builder
		 * @since 5.0.3
		 */
		/**
		 * 设置HTTP状态。 
		 *  
		 * @param 状态响应状态
		 * @return 此构建器@始于5.0.3
		 */
		Builder<T> status(int status);

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
		Builder<T> cookie(ResponseCookie cookie);

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
		Builder<T> cookies(Consumer<MultiValueMap<String, ResponseCookie>> cookiesConsumer);

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
		Builder<T> allow(HttpMethod... allowedMethods);

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
		Builder<T> allow(Set<HttpMethod> allowedMethods);

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
		Builder<T> eTag(String etag);

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
		Builder<T> lastModified(ZonedDateTime lastModified);

		/**
		 * Set the time the resource was last changed, as specified by the
		 * {@code Last-Modified} header.
		 * <p>The date should be specified as the number of milliseconds since
		 * January 1, 1970 GMT.
		 * @param lastModified the last modified date
		 * @return this builder
		 * @since 5.1.4
		 * @see HttpHeaders#setLastModified(long)
		 */
		/**
		 * 设置资源的最后更改时间，如{@code  Last-Modified}标头所指定。 
		 *  <p>日期应指定为格林尼治标准时间1970年1月1日以来的毫秒数。 
		 *  
		 * @param  lastModified的最后修改日期
		 * @return 此构建器@始于5.1.4 
		 * @see  HttpHeaders＃setLastModified（long）
		 */
		Builder<T> lastModified(Instant lastModified);

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
		Builder<T> location(URI location);

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
		Builder<T> cacheControl(CacheControl cacheControl);

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
		Builder<T> varyBy(String... requestHeaders);

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
		Builder<T> contentLength(long contentLength);

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
		Builder<T> contentType(MediaType contentType);

		/**
		 * Add a serialization hint like {@link Jackson2CodecSupport#JSON_VIEW_HINT} to
		 * customize how the body will be serialized.
		 * @param key the hint key
		 * @param value the hint value
		 */
		/**
		 * 添加诸如{@link  Jackson2CodecSupport＃JSON_VIEW_HINT}之类的序列化提示，以自定义主体的序列化方式。 
		 *  
		 * @param 键提示键
		 * @param 值提示值
		 */
		Builder<T> hint(String key, Object value);

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
		Builder<T> hints(Consumer<Map<String, Object>> hintsConsumer);

		/**
		 * Build the response.
		 * @return the built response
		 */
		/**
		 * 建立响应。 
		 *  
		 * @return 内置响应
		 */
		Mono<EntityResponse<T>> build();
	}

}
