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

package org.springframework.http.codec.protobuf;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.protobuf.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageEncoder;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;

/**
 * An {@code Encoder} that writes {@link com.google.protobuf.Message}s
 * using <a href="https://developers.google.com/protocol-buffers/">Google Protocol Buffers</a>.
 *
 * <p>Flux are serialized using
 * <a href="https://developers.google.com/protocol-buffers/docs/techniques?hl=en#streaming">delimited Protobuf messages</a>
 * with the size of each message specified before the message itself. Single values are
 * serialized using regular Protobuf message format (without the size prepended before the message).
 *
 * <p>To generate {@code Message} Java classes, you need to install the {@code protoc} binary.
 *
 * <p>This encoder requires Protobuf 3 or higher, and supports
 * {@code "application/x-protobuf"} and {@code "application/octet-stream"} with the official
 * {@code "com.google.protobuf:protobuf-java"} library.
 *
 * @author Sébastien Deleuze
 * @since 5.1
 * @see ProtobufDecoder
 */
/**
 * 一个{@code 编码器}，它使用<a href="https://developers.google.com/protocol-buffers/"> Google协议缓冲区<< @link> com.google.protobuf.Message}编写< / a>。 
 *  <p>使用<a href="https://developers.google.com/protocol-buffers/docs/techniques?hl=zh_CN#streaming">带分隔符的Protobuf消息</a>来对流量进行序列化</a>在消息本身之前指定。 
 * 使用常规的Protobuf消息格式（没有消息前的大小）对单个值进行序列化。 
 *  <p>要生成{@code  Message} Java类，您需要安装{@code  protoc}二进制文件。 
 *  <p>此编码器需要Protobuf 3或更高版本，并支持{@code "application / x-protobuf"}和{@code "application / octet-stream"}，并带有官方{@code "com .google.protobuf：protobuf-java"}库。 
 *  @author SébastienDeleuze @5.1起
 * @see  ProtobufDecoder
 */
public class ProtobufEncoder extends ProtobufCodecSupport implements HttpMessageEncoder<Message> {

	private static final List<MediaType> streamingMediaTypes = MIME_TYPES
			.stream()
			.map(mimeType -> new MediaType(mimeType.getType(), mimeType.getSubtype(),
					Collections.singletonMap(DELIMITED_KEY, DELIMITED_VALUE)))
			.collect(Collectors.toList());


	@Override
	public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
		return Message.class.isAssignableFrom(elementType.toClass()) && supportsMimeType(mimeType);
	}

	@Override
	public Flux<DataBuffer> encode(Publisher<? extends Message> inputStream, DataBufferFactory bufferFactory,
			ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		return Flux.from(inputStream).map(message ->
				encodeValue(message, bufferFactory, !(inputStream instanceof Mono)));
	}

	@Override
	public DataBuffer encodeValue(Message message, DataBufferFactory bufferFactory,
			ResolvableType valueType, @Nullable MimeType mimeType, @Nullable Map<String, Object> hints) {

		return encodeValue(message, bufferFactory, false);
	}

	private DataBuffer encodeValue(Message message, DataBufferFactory bufferFactory, boolean delimited) {

		DataBuffer buffer = bufferFactory.allocateBuffer();
		boolean release = true;
		try {
			if (delimited) {
				message.writeDelimitedTo(buffer.asOutputStream());
			}
			else {
				message.writeTo(buffer.asOutputStream());
			}
			release = false;
			return buffer;
		}
		catch (IOException ex) {
			throw new IllegalStateException("Unexpected I/O error while writing to data buffer", ex);
		}
		finally {
			if (release) {
				DataBufferUtils.release(buffer);
			}
		}
	}

	@Override
	public List<MediaType> getStreamingMediaTypes() {
		return streamingMediaTypes;
	}

	@Override
	public List<MimeType> getEncodableMimeTypes() {
		return getMimeTypes();
	}

}
