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

package org.springframework.core.testfixture.codec;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.testfixture.io.buffer.AbstractLeakCheckingTests;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Abstract base class for {@link Decoder} unit tests. Subclasses need to implement
 * {@link #canDecode()}, {@link #decode()} and {@link #decodeToMono()}, possibly using the wide
 * variety of helper methods like {@link #testDecodeAll} or {@link #testDecodeToMonoAll}.
 *
 * @author Arjen Poutsma
 * @since 5.1.3
 */
/**
 * {@link  Decoder}单元测试的抽象基类。 
 * 子类需要实现{@link  #canDecode（）}，{<@link> #decode（）}和{@link  #decodeToMono（）}，可能使用多种辅助方法，例如{<@link > #testDecodeAll}或{@link  #testDecodeToMonoAll}。 
 *  @author  Arjen Poutsma @自5.1.3起
 */
public abstract class AbstractDecoderTests<D extends Decoder<?>> extends AbstractLeakCheckingTests {

	/**
	 * The decoder to test.
	 */
	/**
	 * 解码器进行测试。 
	 * 
	 */
	protected D decoder;

	/**
	 * Construct a new {@code AbstractDecoderTests} instance for the given decoder.
	 * @param decoder the decoder
	 */
	/**
	 * 为给定的解码器构造一个新的{@code  AbstractDecoderTests}实例。 
	 *  
	 * @param 解码器解码器
	 */
	protected AbstractDecoderTests(D decoder) {
		Assert.notNull(decoder, "Encoder must not be null");

		this.decoder = decoder;
	}


	/**
	 * Subclasses should implement this method to test {@link Decoder#canDecode}.
	 */
	/**
	 * 子类应实现此方法以测试{@link  Decoder＃canDecode}。 
	 * 
	 */
	@Test
	public abstract void canDecode() throws Exception;

	/**
	 * Subclasses should implement this method to test {@link Decoder#decode}, possibly using
	 * {@link #testDecodeAll} or other helper methods.
	 */
	/**
	 * 子类应该实现此方法以测试{@link  Decoder＃decode}，可能使用{@link  #testDecodeAll}或其他辅助方法。 
	 * 
	 */
	@Test
	public abstract void decode() throws Exception;

	/**
	 * Subclasses should implement this method to test {@link Decoder#decodeToMono}, possibly using
	 * {@link #testDecodeToMonoAll}.
	 */
	/**
	 * 子类应实现此方法以测试{@link  Decoder＃decodeToMono}，可能使用{@link  #testDecodeToMonoAll}。 
	 * 
	 */
	@Test
	public abstract void decodeToMono() throws Exception;

	// Flux

	/**
	 * Helper methods that tests for a variety of {@link Flux} decoding scenarios. This methods
	 * invokes:
	 * <ul>
	 *     <li>{@link #testDecode(Publisher, ResolvableType, Consumer, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeError(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeCancel(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeEmpty(ResolvableType, MimeType, Map)}</li>
	 * </ul>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputClass the desired output class
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param <T> the output type
	 */
	/**
	 * 测试各种{@link  Flux}解码方案的辅助方法。 
	 * 此方法调用：<ul> <li> {<@link> #testDecode（Publisher，ResolvableType，Consumer，MimeType，Map）} </ li> <li> {<@link> #testDecodeError（Publisher，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeCancel（发布商，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeEmpty（ResolvableType，MimeType，地图）} < / li> </ ul> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputClass所需的输出类
	 * @param  stepConsumer消费者使用{@link  plain StepVerifier verify}输出<
	 * @param> <T>输出类型
	 */
	protected <T> void testDecodeAll(Publisher<DataBuffer> input, Class<? extends T> outputClass,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer) {

		testDecodeAll(input, ResolvableType.forClass(outputClass), stepConsumer, null, null);
	}

	/**
	 * Helper methods that tests for a variety of {@link Flux} decoding scenarios. This methods
	 * invokes:
	 * <ul>
	 *     <li>{@link #testDecode(Publisher, ResolvableType, Consumer, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeError(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeCancel(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeEmpty(ResolvableType, MimeType, Map)}</li>
	 * </ul>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @param <T> the output type
	 */
	/**
	 * 测试各种{@link  Flux}解码方案的辅助方法。 
	 * 此方法调用：<ul> <li> {<@link> #testDecode（Publisher，ResolvableType，Consumer，MimeType，Map）} </ li> <li> {<@link> #testDecodeError（Publisher，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeCancel（发布商，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeEmpty（ResolvableType，MimeType，地图）} < / li> </ ul> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  stepConsumer消费者使用{@link  plain StepVerifier verify}输出<
	 * @param> mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @param  <T>输出类型
	 */
	protected <T> void testDecodeAll(Publisher<DataBuffer> input, ResolvableType outputType,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		testDecode(input, outputType, stepConsumer, mimeType, hints);
		testDecodeError(input, outputType, mimeType, hints);
		testDecodeCancel(input, outputType, mimeType, hints);
		testDecodeEmpty(outputType, mimeType, hints);
	}

	/**
	 * Test a standard {@link Decoder#decode decode} scenario. For example:
	 * <pre class="code">
	 * byte[] bytes1 = ...
	 * byte[] bytes2 = ...
	 *
	 * Flux&lt;DataBuffer&gt; input = Flux.concat(
	 *   dataBuffer(bytes1),
	 *   dataBuffer(bytes2));
	 *
	 * testDecodeAll(input, byte[].class, step -&gt; step
	 *   .consumeNextWith(expectBytes(bytes1))
	 *   .consumeNextWith(expectBytes(bytes2))
	 * 	 .verifyComplete());
	 * </pre>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputClass the desired output class
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param <T> the output type
	 */
	/**
	 * 测试标准的{@link  Decoder＃decode解码}方案。 
	 * 例如：<pre class ="code"> byte [] bytes1 = ... byte [] bytes2 = ... Flux <DataBuffer>输入= Flux.concat（dataBuffer（bytes1），dataBuffer（bytes2））; testDecodeAll（input，byte []。 
	 * class，step-> step .consumeNextWith（expectBytes（bytes1））.consumeNextWith（expectBytes（bytes2））.verifyComplete（））; </ pre> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputClass所需的输出类
	 * @param  step消费者使用{{@link> plain StepVerifier verify}输出
	 * @param  < T>输出类型
	 */
	protected <T> void testDecode(Publisher<DataBuffer> input, Class<? extends T> outputClass,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer) {

		testDecode(input, ResolvableType.forClass(outputClass), stepConsumer, null, null);
	}

	/**
	 * Test a standard {@link Decoder#decode decode} scenario. For example:
	 * <pre class="code">
	 * byte[] bytes1 = ...
	 * byte[] bytes2 = ...
	 *
	 * Flux&lt;DataBuffer&gt; input = Flux.concat(
	 *   dataBuffer(bytes1),
	 *   dataBuffer(bytes2));
	 *
	 * testDecodeAll(input, byte[].class, step -&gt; step
	 *   .consumeNextWith(expectBytes(bytes1))
	 *   .consumeNextWith(expectBytes(bytes2))
	 * 	 .verifyComplete());
	 * </pre>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @param <T> the output type
	 */
	/**
	 * 测试标准的{@link  Decoder＃decode解码}方案。 
	 * 例如：<pre class ="code"> byte [] bytes1 = ... byte [] bytes2 = ... Flux <DataBuffer>输入= Flux.concat（dataBuffer（bytes1），dataBuffer（bytes2））; testDecodeAll（input，byte []。 
	 * class，step-> step .consumeNextWith（expectBytes（bytes1））.consumeNextWith（expectBytes（bytes2））.verifyComplete（））; </ pre> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  step消费者使用{{@link> plain StepVerifier verify}输出
	 * @param  mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @param  <T>输出类型
	 */
	@SuppressWarnings("unchecked")
	protected <T> void testDecode(Publisher<DataBuffer> input, ResolvableType outputType,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		Flux<T> result = (Flux<T>) this.decoder.decode(input, outputType, mimeType, hints);
		StepVerifier.FirstStep<T> step = StepVerifier.create(result);
		stepConsumer.accept(step);
	}

	/**
	 * Test a {@link Decoder#decode decode} scenario where the input stream contains an error.
	 * This test method will feed the first element of the {@code input} stream to the decoder,
	 * followed by an {@link InputException}.
	 * The result is expected to contain one "normal" element, followed by the error.
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @see InputException
	 */
	/**
	 * 测试一个{@link  Decoder＃decode解码}方案，其中输入流包含一个错误。 
	 * 此测试方法会将{@code  input}流的第一个元素馈送到解码器，然后是{@link  InputException}。 
	 * 结果应包含一个"正常"元素，后跟错误。 
	 *  
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  mimeType mime类型用于解码。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @see  InputException
	 */
	protected void testDecodeError(Publisher<DataBuffer> input, ResolvableType outputType,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		Flux<DataBuffer> buffer = Mono.from(input).concatWith(Flux.error(new InputException()));
		assertThatExceptionOfType(InputException.class).isThrownBy(() ->
				this.decoder.decode(buffer, outputType, mimeType, hints).blockLast(Duration.ofSeconds(5)));
	}

	/**
	 * Test a {@link Decoder#decode decode} scenario where the input stream is canceled.
	 * This test method will feed the first element of the {@code input} stream to the decoder,
	 * followed by a cancel signal.
	 * The result is expected to contain one "normal" element.
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 */
	/**
	 * 测试一个{@link  Decoder＃decode解码}方案，其中输入流被取消。 
	 * 此测试方法会将{@code  input}流的第一个元素馈送到解码器，然后是取消信号。 
	 * 预期结果将包含一个"正常"元素。 
	 *  
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  mimeType mime类型用于解码。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 * 
	 */
	protected void testDecodeCancel(Publisher<DataBuffer> input, ResolvableType outputType,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		Flux<?> result = this.decoder.decode(input, outputType, mimeType, hints);
		StepVerifier.create(result).expectNextCount(1).thenCancel().verify();
	}

	/**
	 * Test a {@link Decoder#decode decode} scenario where the input stream is empty.
	 * The output is expected to be empty as well.
	 *
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 */
	/**
	 * 测试{@link  Decoder＃decode解码}方案，其中输入流为空。 
	 * 预计输出也将为空。 
	 *  
	 * @param  outputType所需的输出类型
	 * @param  mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 * 
	 */
	protected void testDecodeEmpty(ResolvableType outputType, @Nullable MimeType mimeType,
			@Nullable Map<String, Object> hints) {

		Flux<DataBuffer> input = Flux.empty();
		Flux<?> result = this.decoder.decode(input, outputType, mimeType, hints);
		StepVerifier.create(result).verifyComplete();
	}

	// Mono

	/**
	 * Helper methods that tests for a variety of {@link Mono} decoding scenarios. This methods
	 * invokes:
	 * <ul>
	 *     <li>{@link #testDecodeToMono(Publisher, ResolvableType, Consumer, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoError(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoCancel(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoEmpty(ResolvableType, MimeType, Map)}</li>
	 * </ul>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputClass the desired output class
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param <T> the output type
	 */
	/**
	 * 测试各种{@link  Mono}解码方案的辅助方法。 
	 * 此方法调用：<ul> <li> {<@link> #testDecodeToMono（Publisher，ResolvableType，Consumer，MimeType，Map）} </ li> <li> {<@link> #testDecodeToMonoError（Publisher，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeToMonoCancel（发布商，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeToMonoEmpty（ResolvableType，MimeType，地图）} < / li> </ ul> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputClass所需的输出类
	 * @param  stepConsumer消费者使用{@link  plain StepVerifier verify}输出<
	 * @param> <T>输出类型
	 */
	protected <T> void testDecodeToMonoAll(Publisher<DataBuffer> input,
			Class<? extends T> outputClass, Consumer<StepVerifier.FirstStep<T>> stepConsumer) {

		testDecodeToMonoAll(input, ResolvableType.forClass(outputClass), stepConsumer, null, null);
	}

	/**
	 * Helper methods that tests for a variety of {@link Mono} decoding scenarios. This methods
	 * invokes:
	 * <ul>
	 *     <li>{@link #testDecodeToMono(Publisher, ResolvableType, Consumer, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoError(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoCancel(Publisher, ResolvableType, MimeType, Map)}</li>
	 *     <li>{@link #testDecodeToMonoEmpty(ResolvableType, MimeType, Map)}</li>
	 * </ul>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @param <T> the output type
	 */
	/**
	 * 测试各种{@link  Mono}解码方案的辅助方法。 
	 * 此方法调用：<ul> <li> {<@link> #testDecodeToMono（Publisher，ResolvableType，Consumer，MimeType，Map）} </ li> <li> {<@link> #testDecodeToMonoError（Publisher，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeToMonoCancel（发布商，ResolvableType，MimeType，地图）} </ li> <li> {<@link> #testDecodeToMonoEmpty（ResolvableType，MimeType，地图）} < / li> </ ul> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  stepConsumer消费者使用{@link  plain StepVerifier verify}输出<
	 * @param> mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @param  <T>输出类型
	 */
	protected <T> void testDecodeToMonoAll(Publisher<DataBuffer> input, ResolvableType outputType,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		testDecodeToMono(input, outputType, stepConsumer, mimeType, hints);
		testDecodeToMonoError(input, outputType, mimeType, hints);
		testDecodeToMonoCancel(input, outputType, mimeType, hints);
		testDecodeToMonoEmpty(outputType, mimeType, hints);
	}

	/**
	 * Test a standard {@link Decoder#decodeToMono decode} scenario. For example:
	 * <pre class="code">
	 * byte[] bytes1 = ...
	 * byte[] bytes2 = ...
	 * byte[] allBytes = ... // bytes1 + bytes2
	 *
	 * Flux&lt;DataBuffer&gt; input = Flux.concat(
	 *   dataBuffer(bytes1),
	 *   dataBuffer(bytes2));
	 *
	 * testDecodeAll(input, byte[].class, step -&gt; step
	 *   .consumeNextWith(expectBytes(allBytes))
	 * 	 .verifyComplete());
	 * </pre>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputClass the desired output class
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param <T> the output type
	 */
	/**
	 * 测试标准的{@link  Decoder＃decodeToMono解码}方案。 
	 * 例如：<pre class ="code"> byte [] bytes1 = ... byte [] bytes2 = ... byte [] allBytes = ... //字节1 +字节2 Flux <DataBuffer>输入= Flux.concat（ dataBuffer（bytes1），dataBuffer（bytes2））; testDecodeAll（input，byte []。 
	 * class，step-> step .consumeNextWith（expectBytes（allBytes））.verifyComplete（））; </ pre> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputClass所需的输出类
	 * @param  step消费者使用{{@link> plain StepVerifier verify}输出
	 * @param  < T>输出类型
	 */
	protected <T> void testDecodeToMono(Publisher<DataBuffer> input,
			Class<? extends T> outputClass, Consumer<StepVerifier.FirstStep<T>> stepConsumer) {

		testDecodeToMono(input, ResolvableType.forClass(outputClass), stepConsumer, null, null);
	}

	/**
	 * Test a standard {@link Decoder#decodeToMono decode} scenario. For example:
	 * <pre class="code">
	 * byte[] bytes1 = ...
	 * byte[] bytes2 = ...
	 * byte[] allBytes = ... // bytes1 + bytes2
	 *
	 * Flux&lt;DataBuffer&gt; input = Flux.concat(
	 *   dataBuffer(bytes1),
	 *   dataBuffer(bytes2));
	 *
	 * testDecodeAll(input, byte[].class, step -&gt; step
	 *   .consumeNextWith(expectBytes(allBytes))
	 * 	 .verifyComplete());
	 * </pre>
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param stepConsumer a consumer to {@linkplain StepVerifier verify} the output
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @param <T> the output type
	 */
	/**
	 * 测试标准的{@link  Decoder＃decodeToMono解码}方案。 
	 * 例如：<pre class ="code"> byte [] bytes1 = ... byte [] bytes2 = ... byte [] allBytes = ... //字节1 +字节2 Flux <DataBuffer>输入= Flux.concat（ dataBuffer（bytes1），dataBuffer（bytes2））; testDecodeAll（input，byte []。 
	 * class，step-> step .consumeNextWith（expectBytes（allBytes））.verifyComplete（））; </ pre> 
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  step消费者使用{{@link> plain StepVerifier verify}输出
	 * @param  mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @param  <T>输出类型
	 */
	@SuppressWarnings("unchecked")
	protected <T> void testDecodeToMono(Publisher<DataBuffer> input, ResolvableType outputType,
			Consumer<StepVerifier.FirstStep<T>> stepConsumer,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		Mono<T> result = (Mono<T>) this.decoder.decodeToMono(input, outputType, mimeType, hints);
		StepVerifier.FirstStep<T> step = StepVerifier.create(result);
		stepConsumer.accept(step);
	}

	/**
	 * Test a {@link Decoder#decodeToMono decode} scenario where the input stream contains an error.
	 * This test method will feed the first element of the {@code input} stream to the decoder,
	 * followed by an {@link InputException}.
	 * The result is expected to contain the error.
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 * @see InputException
	 */
	/**
	 * 测试{@link  Decoder＃decodeToMono解码}方案，其中输入流包含错误。 
	 * 此测试方法会将{@code  input}流的第一个元素馈送到解码器，然后是{@link  InputException}。 
	 * 结果应包含错误。 
	 *  
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  mimeType mime类型用于解码。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 *  
	 * @see  InputException
	 */
	protected void testDecodeToMonoError(Publisher<DataBuffer> input, ResolvableType outputType,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		input = Mono.from(input).concatWith(Flux.error(new InputException()));
		Mono<?> result = this.decoder.decodeToMono(input, outputType, mimeType, hints);
		StepVerifier.create(result).expectError(InputException.class).verify();
	}

	/**
	 * Test a {@link Decoder#decodeToMono decode} scenario where the input stream is canceled.
	 * This test method will immediately cancel the output stream.
	 *
	 * @param input the input to be provided to the decoder
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 */
	/**
	 * 测试一个{@link  Decoder＃decodeToMono解码}方案，其中输入流被取消。 
	 * 此测试方法将立即取消输出流。 
	 *  
	 * @param 输入要提供给解码器的输入
	 * @param  outputType所需的输出类型
	 * @param  mimeType mime类型用于解码。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 * 
	 */
	protected void testDecodeToMonoCancel(Publisher<DataBuffer> input, ResolvableType outputType,
			@Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		Mono<?> result = this.decoder.decodeToMono(input, outputType, mimeType, hints);
		StepVerifier.create(result).thenCancel().verify();
	}

	/**
	 * Test a {@link Decoder#decodeToMono decode} scenario where the input stream is empty.
	 * The output is expected to be empty as well.
	 *
	 * @param outputType the desired output type
	 * @param mimeType the mime type to use for decoding. May be {@code null}.
	 * @param hints the hints used for decoding. May be {@code null}.
	 */
	/**
	 * 测试{@link  Decoder＃decodeToMono解码}方案，其中输入流为空。 
	 * 预计输出也将为空。 
	 *  
	 * @param  outputType所需的输出类型
	 * @param  mimeType用于解码的mime类型。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 提示用于解码的提示。 
	 * 可能为{@code  null}。 
	 * 
	 */
	protected void testDecodeToMonoEmpty(ResolvableType outputType, @Nullable MimeType mimeType,
			@Nullable Map<String, Object> hints) {

		Mono<?> result = this.decoder.decodeToMono(Flux.empty(), outputType, mimeType, hints);
		StepVerifier.create(result).verifyComplete();
	}

	/**
	 * Creates a deferred {@link DataBuffer} containing the given bytes.
	 * @param bytes the bytes that are to be stored in the buffer
	 * @return the deferred buffer
	 */
	/**
	 * 创建一个包含给定字节的延迟的{@link  DataBuffer}。 
	 *  
	 * @param 字节要存储在缓冲区中的字节
	 * @return 延迟缓冲区
	 */
	protected Mono<DataBuffer> dataBuffer(byte[] bytes) {
		return Mono.fromCallable(() -> {
			DataBuffer dataBuffer = this.bufferFactory.allocateBuffer(bytes.length);
			dataBuffer.write(bytes);
			return dataBuffer;
		});
	}

	/**
	 * Exception used in {@link #testDecodeError} and {@link #testDecodeToMonoError}
	 */
	/**
	 * {@link  #testDecodeError}和{@link  #testDecodeToMonoError}中使用的异常
	 */
	@SuppressWarnings("serial")
	public static class InputException extends RuntimeException {}

}
