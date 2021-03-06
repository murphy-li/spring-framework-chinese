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

package org.springframework.web.testfixture.http.server.reactive;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

/**
 * Mock extension of {@link AbstractServerHttpResponse} for use in tests without
 * an actual server.
 *
 * <p>By default response content is consumed in full upon writing and cached
 * for subsequent access, however it is also possible to set a custom
 * {@link #setWriteHandler(Function) writeHandler}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * {@link  AbstractServerHttpResponse}的模拟扩展，用于没有实际服务器的测试。 
 *  <p>默认情况下，响应内容在写入时会被完全消耗，并缓存起来供以后访问，但是也可以设置自定义{@link  #setWriteHandler（Function）writeHandler}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class MockServerHttpResponse extends AbstractServerHttpResponse {

	private Flux<DataBuffer> body = Flux.error(new IllegalStateException(
			"No content was written nor was setComplete() called on this response."));

	private Function<Flux<DataBuffer>, Mono<Void>> writeHandler;


	public MockServerHttpResponse() {
		this(new DefaultDataBufferFactory());
	}

	public MockServerHttpResponse(DataBufferFactory dataBufferFactory) {
		super(dataBufferFactory);
		this.writeHandler = body -> {
			// Avoid .then() which causes data buffers to be released
			MonoProcessor<Void> completion = MonoProcessor.create();
			this.body = body.doOnComplete(completion::onComplete).doOnError(completion::onError).cache();
			this.body.subscribe();
			return completion;
		};
	}


	/**
	 * Configure a custom handler to consume the response body.
	 * <p>By default, response body content is consumed in full and cached for
	 * subsequent access in tests. Use this option to take control over how the
	 * response body is consumed.
	 * @param writeHandler the write handler to use returning {@code Mono<Void>}
	 * when the body has been "written" (i.e. consumed).
	 */
	/**
	 * 配置一个自定义处理程序以使用响应主体。 
	 *  <p>默认情况下，响应主体内容已被全部消耗，并被缓存以供后续测试访问。 
	 * 使用此选项可以控制如何使用响应主体。 
	 *  
	 * @param  writeHandler写入处理程序，以在主体被"写入"（即消耗）时使用返回的{@code  Mono <Void>}。 
	 * 
	 */
	public void setWriteHandler(Function<Flux<DataBuffer>, Mono<Void>> writeHandler) {
		Assert.notNull(writeHandler, "'writeHandler' is required");
		this.body = Flux.error(new IllegalStateException("Not available with custom write handler."));
		this.writeHandler = writeHandler;
	}

	@Override
	public <T> T getNativeResponse() {
		throw new IllegalStateException("This is a mock. No running server, no native response.");
	}


	@Override
	protected void applyStatusCode() {
	}

	@Override
	protected void applyHeaders() {
	}

	@Override
	protected void applyCookies() {
		for (List<ResponseCookie> cookies : getCookies().values()) {
			for (ResponseCookie cookie : cookies) {
				getHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
			}
		}
	}

	@Override
	protected Mono<Void> writeWithInternal(Publisher<? extends DataBuffer> body) {
		return this.writeHandler.apply(Flux.from(body));
	}

	@Override
	protected Mono<Void> writeAndFlushWithInternal(
			Publisher<? extends Publisher<? extends DataBuffer>> body) {

		return this.writeHandler.apply(Flux.from(body).concatMap(Flux::from));
	}

	@Override
	public Mono<Void> setComplete() {
		return doCommit(() -> Mono.defer(() -> this.writeHandler.apply(Flux.empty())));
	}

	/**
	 * Return the response body or an error stream if the body was not set.
	 */
	/**
	 * 返回响应正文或错误流（如果未设置响应正文）。 
	 * 
	 */
	public Flux<DataBuffer> getBody() {
		return this.body;
	}

	/**
	 * Aggregate response data and convert to a String using the "Content-Type"
	 * charset or "UTF-8" by default.
	 */
	/**
	 * 汇总响应数据，并默认使用"Content-Type"字符集或"UTF-8"将其转换为字符串。 
	 * 
	 */
	public Mono<String> getBodyAsString() {

		Charset charset = Optional.ofNullable(getHeaders().getContentType()).map(MimeType::getCharset)
				.orElse(StandardCharsets.UTF_8);

		return getBody()
				.reduce(bufferFactory().allocateBuffer(), (previous, current) -> {
					previous.write(current);
					DataBufferUtils.release(current);
					return previous;
				})
				.map(buffer -> bufferToString(buffer, charset));
	}

	private static String bufferToString(DataBuffer buffer, Charset charset) {
		Assert.notNull(charset, "'charset' must not be null");
		byte[] bytes = new byte[buffer.readableByteCount()];
		buffer.read(bytes);
		DataBufferUtils.release(buffer);
		return new String(bytes, charset);
	}

}
