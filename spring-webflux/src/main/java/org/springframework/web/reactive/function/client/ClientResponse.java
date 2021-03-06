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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractor;

/**
 * Represents an HTTP response, as returned by {@link WebClient} and also
 * {@link ExchangeFunction}. Provides access to the response status and
 * headers, and also methods to consume the response body.
 *
 * <p><strong>NOTE:</strong> When using a {@link ClientResponse}
 * through the {@code WebClient}
 * {@link WebClient.RequestHeadersSpec#exchange() exchange()} method,
 * you have to make sure that the body is consumed or released by using
 * one of the following methods:
 * <ul>
 * <li>{@link #body(BodyExtractor)}</li>
 * <li>{@link #bodyToMono(Class)} or
 *     {@link #bodyToMono(ParameterizedTypeReference)}</li>
 * <li>{@link #bodyToFlux(Class)} or
 *     {@link #bodyToFlux(ParameterizedTypeReference)}</li>
 * <li>{@link #toEntity(Class)} or
 *     {@link #toEntity(ParameterizedTypeReference)}</li>
 * <li>{@link #toEntityList(Class)} or
 *     {@link #toEntityList(ParameterizedTypeReference)}</li>
*  <li>{@link #toBodilessEntity()}</li>
 * <li>{@link #releaseBody()}</li>
 * </ul>
 * You can also use {@code bodyToMono(Void.class)} if no response content is
 * expected. However keep in mind the connection will be closed, instead of
 * being placed back in the pool, if any content does arrive. This is in
 * contrast to {@link #releaseBody()} which does consume the full body and
 * releases any content received.
 *
 * @author Brian Clozel
 * @author Arjen Poutsma
 * @since 5.0
 */
/**
 * 表示HTTP响应，由{@link  WebClient}和{@link  ExchangeFunction}返回。 
 * 提供对响应状态和标头以及使用响应主体的方法的访问。 
 *  <p> <strong>注意：</ strong>：通过{@code  WebClient} {@link  WebClient.RequestHeadersSpec＃exchange（）exchange（）}方法使用{@link  ClientResponse}时，您必须使用以下方法之一确保身体被消耗或释放：<ul> <li> {<@link> #body（BodyExtractor）} </ li> <li> {<@link> #bodyToMono （Class）}或{@link  #bodyToMono（ParameterizedTypeReference）} </ li> <li> {<@link> #bodyToFlux（Class）}或{@link  #bodyToFlux（ParameterizedTypeReference）} </ li> <li> {<@link> #toEntity（Class）}或{@link  #toEntity（ParameterizedTypeReference）} </ li> <li> {<@link> #toEntityList（Class）}或{@link  #toEntityList（ParameterizedTypeReference）} </ li> <li> {<@link> #toBodilessEntity（）} </ li> <li> {<@link> #releaseBody（）} </ li> </ ul>您可以如果不需要响应内容，也可以使用{@code  bodyToMono（Void.class）}。 
 * 但是请记住，如果有任何内容到达，连接将被关闭，而不是放回池中。 
 * 这与{@link  #releaseBody（）}相反，它确实消耗了整个主体并释放了收到的所有内容。 
 *  @author  Brian Clozel @author  Arjen Poutsma @从5.0开始
 */
public interface ClientResponse {

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
	HttpStatus statusCode();

	/**
	 * Return the (potentially non-standard) status code of this response.
	 * @return the HTTP status as an integer value
	 * @since 5.1
	 * @see #statusCode()
	 * @see HttpStatus#resolve(int)
	 */
	/**
	 * 返回此响应的（可能是非标准的）状态代码。 
	 *  
	 * @return  HTTP状态为整数值@自5.1起
	 * @see  #statusCode（）
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
	Headers headers();

	/**
	 * Return cookies of this response.
	 */
	/**
	 * 返回此响应的cookie。 
	 * 
	 */
	MultiValueMap<String, ResponseCookie> cookies();

	/**
	 * Return the strategies used to convert the body of this response.
	 */
	/**
	 * 返回用于转换此响应主体的策略。 
	 * 
	 */
	ExchangeStrategies strategies();

	/**
	 * Extract the body with the given {@code BodyExtractor}.
	 * @param extractor the {@code BodyExtractor} that reads from the response
	 * @param <T> the type of the body returned
	 * @return the extracted body
	 */
	/**
	 * 使用给定的{@code  BodyExtractor}提取主体。 
	 *  
	 * @param 提取器{@code  BodyExtractor}从响应中读取
	 * @param  <T>返回的主体类型
	 * @return 提取的主体
	 */
	<T> T body(BodyExtractor<T, ? super ClientHttpResponse> extractor);

	/**
	 * Extract the body to a {@code Mono}.
	 * @param elementClass the class of element in the {@code Mono}
	 * @param <T> the element type
	 * @return a mono containing the body of the given type {@code T}
	 */
	/**
	 * 将主体提取到{@code  Mono}。 
	 *  
	 * @param  elementClass {@code  Mono}中元素的类
	 * @param  <T>元素类型
	 * @return 一个包含给定类型{@code  T}的主体的mono
	 */
	<T> Mono<T> bodyToMono(Class<? extends T> elementClass);

	/**
	 * Extract the body to a {@code Mono}.
	 * @param elementTypeRef the type reference of element in the {@code Mono}
	 * @param <T> the element type
	 * @return a mono containing the body of the given type {@code T}
	 */
	/**
	 * 将主体提取到{@code  Mono}。 
	 *  
	 * @param  elementTypeRef {@code  Mono}中元素的类型引用
	 * @param  <T>元素类型
	 * @return 包含给定类型{@code  T}的主体的mono
	 */
	<T> Mono<T> bodyToMono(ParameterizedTypeReference<T> elementTypeRef);

	/**
	 * Extract the body to a {@code Flux}.
	 * @param elementClass the class of elements in the {@code Flux}
	 * @param <T> the element type
	 * @return a flux containing the body of the given type {@code T}
	 */
	/**
	 * 将主体提取到{@code  Flux}。 
	 *  
	 * @param  elementClass {{@code> Flux}中元素的类别
	 * @param  <T>元素类型
	 * @return 包含给定类型{@code  T}的主体的通量
	 */
	<T> Flux<T> bodyToFlux(Class<? extends T> elementClass);

	/**
	 * Extract the body to a {@code Flux}.
	 * @param elementTypeRef the type reference of elements in the {@code Flux}
	 * @param <T> the element type
	 * @return a flux containing the body of the given type {@code T}
	 */
	/**
	 * 将主体提取到{@code  Flux}。 
	 *  
	 * @param  elementTypeRef {@code  Flux}中元素的类型引用
	 * @param  <T>元素类型
	 * @return 包含给定类型{@code  T}的主体的通量
	 */
	<T> Flux<T> bodyToFlux(ParameterizedTypeReference<T> elementTypeRef);

	/**
	 * Releases the body of this response.
	 * @return a completion signal
	 * @since 5.2
	 * @see org.springframework.core.io.buffer.DataBufferUtils#release(DataBuffer)
	 */
	/**
	 * 释放此响应的主体。 
	 *  
	 * @return 完成信号@从5.2起
	 * @see  org.springframework.core.io.buffer.DataBufferUtils＃release（DataBuffer）
	 */
	Mono<Void> releaseBody();

	/**
	 * Return this response as a delayed {@code ResponseEntity}.
	 * @param bodyClass the expected response body type
	 * @param <T> response body type
	 * @return {@code Mono} with the {@code ResponseEntity}
	 */
	/**
	 * 返回此响应作为延迟的{@code  ResponseEntity}。 
	 *  
	 * @param  bodyClass预期的响应主体类型
	 * @param  <T>响应主体类型
	 * @return  {@code  Mono}与{@code  ResponseEntity}
	 */
	<T> Mono<ResponseEntity<T>> toEntity(Class<T> bodyClass);

	/**
	 * Return this response as a delayed {@code ResponseEntity}.
	 * @param bodyTypeReference a type reference describing the expected response body type
	 * @param <T> response body type
	 * @return {@code Mono} with the {@code ResponseEntity}
	 */
	/**
	 * 返回此响应作为延迟的{@code  ResponseEntity}。 
	 *  
	 * @param  bodyTypeReference类型参考，描述了预期的响应主体类型
	 * @param  <T>响应主体类型
	 * @return  {@code  Mono}与{@code  ResponseEntity}
	 */
	<T> Mono<ResponseEntity<T>> toEntity(ParameterizedTypeReference<T> bodyTypeReference);

	/**
	 * Return this response as a delayed list of {@code ResponseEntity}s.
	 * @param elementClass the expected response body list element class
	 * @param <T> the type of elements in the list
	 * @return {@code Mono} with the list of {@code ResponseEntity}s
	 */
	/**
	 * 以{@code  ResponseEntity}的延迟列表形式返回此响应。 
	 *  
	 * @param  elementClass期望的响应主体列表元素类
	 * @param  <T>列表中元素的类型
	 * @return  {@code  Mono}和列表中的{@code  ResponseEntity} s
	 */
	<T> Mono<ResponseEntity<List<T>>> toEntityList(Class<T> elementClass);

	/**
	 * Return this response as a delayed list of {@code ResponseEntity}s.
	 * @param elementTypeRef the expected response body list element reference type
	 * @param <T> the type of elements in the list
	 * @return {@code Mono} with the list of {@code ResponseEntity}s
	 */
	/**
	 * 以{@code  ResponseEntity}的延迟列表形式返回此响应。 
	 *  
	 * @param  elementTypeRef期望的响应主体列表元素引用类型
	 * @param  <T>列表中的元素类型
	 * @return  {@code  Mono}和列表中的{@code  ResponseEntity} s
	 */
	<T> Mono<ResponseEntity<List<T>>> toEntityList(ParameterizedTypeReference<T> elementTypeRef);

	/**
	 * Return this response as a delayed {@code ResponseEntity} containing
	 * status and headers, but no body. Calling this method will
	 * {@linkplain #releaseBody() release} the body of the response.
	 * @return {@code Mono} with the bodiless {@code ResponseEntity}
	 * @since 5.2
	 */
	/**
	 * 以延迟的{@code  ResponseEntity}形式返回此响应，其中包含状态和标头，但没有正文。 
	 * 调用此方法将{@link  plain #releaseBody（）release}响应的正文。 
	 *  
	 * @return  {@code  Mono}与无身体的{@code  ResponseEntity} @从5.2开始
	 */
	Mono<ResponseEntity<Void>> toBodilessEntity();

	/**
	 * Creates a {@link WebClientResponseException} based on the status code,
	 * headers, and body of this response as well as the corresponding request.
	 * @return a {@code Mono} with a {@code WebClientResponseException} based on this response
	 * @since 5.2
	 */
	/**
	 * 根据此响应的状态码，标头和正文以及相应的请求，创建一个{@link  WebClientResponseException}。 
	 * 根据此响应，从
	 * @return 一个带有<< @code> WebClientResponseException}的{@code  Mono}
	 */
	Mono<WebClientResponseException> createException();

	/**
	 * Return a log message prefix to use to correlate messages for this exchange.
	 * The prefix is based on {@linkplain ClientRequest#logPrefix()}, which
	 * itself is based on the value of the {@link ClientRequest#LOG_ID_ATTRIBUTE
	 * LOG_ID_ATTRIBUTE} request attribute, further surrounded with "[" and "]".
	 * @return the log message prefix or an empty String if the
	 * {@link ClientRequest#LOG_ID_ATTRIBUTE LOG_ID_ATTRIBUTE} is not set.
	 * @since 5.2.3
	 */
	/**
	 * 返回日志消息前缀，以用于关联此交换的消息。 
	 * 前缀基于{@link  ClientRequest＃logPrefix（）}，前缀本身基于{@link  ClientRequest＃LOG_ID_ATTRIBUTE LOG_ID_ATTRIBUTE}请求属性的值，并用"["和"]"进一步包围。 
	 *  
	 * @return 日志消息前缀，如果未设置{@link  ClientRequest＃LOG_ID_ATTRIBUTE LOG_ID_ATTRIBUTE}，则为空字符串。 
	 *  @从5.2.3开始
	 */
	String logPrefix();


	// Static builder methods

	/**
	 * Create a builder with the status, headers, and cookies of the given response.
	 * @param other the response to copy the status, headers, and cookies from
	 * @return the created builder
	 */
	/**
	 * 创建具有给定响应的状态，标题和cookie的构建器。 
	 *  
	 * @param 其他响应，用于从
	 * @return 创建的构建器复制状态，标题和cookie
	 */
	static Builder from(ClientResponse other) {
		return new DefaultClientResponseBuilder(other);
	}

	/**
	 * Create a response builder with the given status code and using default strategies for
	 * reading the body.
	 * @param statusCode the status code
	 * @return the created builder
	 */
	/**
	 * 使用给定的状态码并使用默认策略来读取正文，创建响应构建器。 
	 *  
	 * @param  statusCode状态代码
	 * @return 创建的构建器
	 */
	static Builder create(HttpStatus statusCode) {
		return create(statusCode, ExchangeStrategies.withDefaults());
	}

	/**
	 * Create a response builder with the given status code and strategies for reading the body.
	 * @param statusCode the status code
	 * @param strategies the strategies
	 * @return the created builder
	 */
	/**
	 * 创建具有给定状态码和读取正文的策略的响应生成器。 
	 *  
	 * @param  statusCode状态代码
	 * @param 策略策略
	 * @return 创建的构建器
	 */
	static Builder create(HttpStatus statusCode, ExchangeStrategies strategies) {
		return new DefaultClientResponseBuilder(strategies).statusCode(statusCode);
	}

	/**
	 * Create a response builder with the given raw status code and strategies for reading the body.
	 * @param statusCode the status code
	 * @param strategies the strategies
	 * @return the created builder
	 * @since 5.1.9
	 */
	/**
	 * 创建具有给定的原始状态代码和读取正文的策略的响应生成器。 
	 *  
	 * @param  statusCode状态代码
	 * @param 策略策略
	 * @return 创建的生成器@since 5.1.9
	 */
	static Builder create(int statusCode, ExchangeStrategies strategies) {
		return new DefaultClientResponseBuilder(strategies).rawStatusCode(statusCode);
	}

	/**
	 * Create a response builder with the given status code and message body readers.
	 * @param statusCode the status code
	 * @param messageReaders the message readers
	 * @return the created builder
	 */
	/**
	 * 使用给定的状态码和消息正文阅读器创建响应构建器。 
	 *  
	 * @param  statusCode状态代码
	 * @param  messageReaders消息阅读器
	 * @return 创建的构建器
	 */
	static Builder create(HttpStatus statusCode, List<HttpMessageReader<?>> messageReaders) {
		return create(statusCode, new ExchangeStrategies() {
			@Override
			public List<HttpMessageReader<?>> messageReaders() {
				return messageReaders;
			}
			@Override
			public List<HttpMessageWriter<?>> messageWriters() {
				// not used in the response
				return Collections.emptyList();
			}
		});
	}


	/**
	 * Represents the headers of the HTTP response.
	 * @see ClientResponse#headers()
	 */
	/**
	 * 表示HTTP响应的标头。 
	 *  
	 * @see  ClientResponse＃headers（）
	 */
	interface Headers {

		/**
		 * Return the length of the body in bytes, as specified by the
		 * {@code Content-Length} header.
		 */
		/**
		 * 返回正文的长度（以字节为单位），如{@code  Content-Length}标头所指定。 
		 * 
		 */
		OptionalLong contentLength();

		/**
		 * Return the {@linkplain MediaType media type} of the body, as specified
		 * by the {@code Content-Type} header.
		 */
		/**
		 * 返回正文的{@link  plain MediaType媒体类型}，如{@code  Content-Type}标头所指定。 
		 * 
		 */
		Optional<MediaType> contentType();

		/**
		 * Return the header value(s), if any, for the header of the given name.
		 * <p>Return an empty list if no header values are found.
		 * @param headerName the header name
		 */
		/**
		 * 返回给定名称的标头的标头值（如果有）。 
		 *  <p>如果未找到标头值，则返回一个空列表。 
		 *  
		 * @param  headerName标题名称
		 */
		List<String> header(String headerName);

		/**
		 * Return the headers as a {@link HttpHeaders} instance.
		 */
		/**
		 * 以{@link  HttpHeaders}实例的形式返回标头。 
		 * 
		 */
		HttpHeaders asHttpHeaders();
	}


	/**
	 * Defines a builder for a response.
	 */
	/**
	 * 定义响应的构建器。 
	 * 
	 */
	interface Builder {

		/**
		 * Set the status code of the response.
		 * @param statusCode the new status code.
		 * @return this builder
		 */
		/**
		 * 设置响应的状态码。 
		 *  
		 * @param  statusCode新的状态代码。 
		 *  
		 * @return 此构建器
		 */
		Builder statusCode(HttpStatus statusCode);

		/**
		 * Set the raw status code of the response.
		 * @param statusCode the new status code.
		 * @return this builder
		 * @since 5.1.9
		 */
		/**
		 * 设置响应的原始状态代码。 
		 *  
		 * @param  statusCode新的状态代码。 
		 *  
		 * @return 此构建器@始于5.1.9
		 */
		Builder rawStatusCode(int statusCode);

		/**
		 * Add the given header value(s) under the given name.
		 * @param headerName  the header name
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
		Builder header(String headerName, String... headerValues);

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
		Builder headers(Consumer<HttpHeaders> headersConsumer);

		/**
		 * Add a cookie with the given name and value(s).
		 * @param name the cookie name
		 * @param values the cookie value(s)
		 * @return this builder
		 */
		/**
		 * 添加具有给定名称和值的Cookie。 
		 *  
		 * @param 命名cookie名称
		 * @param 评估cookie值
		 * @return 此构建器
		 */
		Builder cookie(String name, String... values);

		/**
		 * Manipulate this response's cookies with the given consumer. The
		 * map provided to the consumer is "live", so that the consumer can be used to
		 * {@linkplain MultiValueMap#set(Object, Object) overwrite} existing header values,
		 * {@linkplain MultiValueMap#remove(Object) remove} values, or use any of the other
		 * {@link MultiValueMap} methods.
		 * @param cookiesConsumer a function that consumes the cookies map
		 * @return this builder
		 */
		/**
		 * 与给定的消费者操作此响应的cookie。 
		 * 提供给使用者的映射是"实时的"，因此使用者可以用来{@link  plain MultiValueMap＃set（Object，Object）覆盖}现有的标头值，{<@link> plain MultiValueMap＃remove（Object ）删除}值，或使用其他任何{@link  MultiValueMap}方法。 
		 *  
		 * @param  cookies消费一个使用Cookies映射的函数
		 * @return 此构建器
		 */
		Builder cookies(Consumer<MultiValueMap<String, ResponseCookie>> cookiesConsumer);

		/**
		 * Set the body of the response. Calling this methods will
		 * {@linkplain org.springframework.core.io.buffer.DataBufferUtils#release(DataBuffer) release}
		 * the existing body of the builder.
		 * @param body the new body.
		 * @return this builder
		 */
		/**
		 * 设置响应的主体。 
		 * 调用此方法将{@link  plain org.springframework.core.io.buffer.DataBufferUtils＃release（DataBuffer）release}构建器的现有主体。 
		 *  
		 * @param 正文新的正文。 
		 *  
		 * @return 此构建器
		 */
		Builder body(Flux<DataBuffer> body);

		/**
		 * Set the body of the response to the UTF-8 encoded bytes of the given string.
		 * Calling this methods will
		 * {@linkplain org.springframework.core.io.buffer.DataBufferUtils#release(DataBuffer) release}
		 * the existing body of the builder.
		 * @param body the new body.
		 * @return this builder
		 */
		/**
		 * 将响应的主体设置为给定字符串的UTF-8编码字节。 
		 * 调用此方法将{@link  plain org.springframework.core.io.buffer.DataBufferUtils＃release（DataBuffer）release}构建器的现有主体。 
		 *  
		 * @param 正文新的正文。 
		 *  
		 * @return 此构建器
		 */
		Builder body(String body);

		/**
		 * Set the request associated with the response.
		 * @param request the request
		 * @return this builder
		 * @since 5.2
		 */
		/**
		 * 设置与响应关联的请求。 
		 *  
		 * @param 请求请求
		 * @return 此构建器@始于5.2
		 */
		Builder request(HttpRequest request);

		/**
		 * Build the response.
		 */
		/**
		 * 建立响应。 
		 * 
		 */
		ClientResponse build();
	}

}
