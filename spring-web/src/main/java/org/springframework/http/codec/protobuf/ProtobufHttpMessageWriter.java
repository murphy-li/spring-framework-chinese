/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.http.codec.protobuf;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.ResolvableType;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.codec.Encoder;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.HttpMessageEncoder;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * {@code HttpMessageWriter} that can write a protobuf {@link Message} and adds
 * {@code X-Protobuf-Schema}, {@code X-Protobuf-Message} headers and a
 * {@code delimited=true} parameter is added to the content type if a flux is serialized.
 *
 * <p>For {@code HttpMessageReader}, just use
 * {@code new DecoderHttpMessageReader(new ProtobufDecoder())}.
 *
 * @author Sébastien Deleuze
 * @since 5.1
 * @see ProtobufEncoder
 */
/**
 * {@code  HttpMessageWriter}可以编写protobuf {@link  Message}并添加{@code  X-Protobuf-Schema}，{<@code> X-Protobuf-Message}标头和{<@如果对序列号进行了序列化，则将code> delimited = true}参数添加到内容类型。 
 *  <p>对于{@code  HttpMessageReader}，只需使用{@code  new DecoderHttpMessageReader（new ProtobufDecoder（））}。 
 *  @author SébastienDeleuze @5.1起
 * @see  ProtobufEncoder
 */
public class ProtobufHttpMessageWriter extends EncoderHttpMessageWriter<Message> {

	private static final String X_PROTOBUF_SCHEMA_HEADER = "X-Protobuf-Schema";

	private static final String X_PROTOBUF_MESSAGE_HEADER = "X-Protobuf-Message";

	private static final ConcurrentMap<Class<?>, Method> methodCache = new ConcurrentReferenceHashMap<>();


	/**
	 * Create a new {@code ProtobufHttpMessageWriter} with a default {@link ProtobufEncoder}.
	 */
	/**
	 * 使用默认的{@link  ProtobufEncoder}创建一个新的{@code  ProtobufHttpMessageWriter}。 
	 * 
	 */
	public ProtobufHttpMessageWriter() {
		super(new ProtobufEncoder());
	}

	/**
	 * Create a new {@code ProtobufHttpMessageWriter} with the given encoder.
	 * @param encoder the Protobuf message encoder to use
	 */
	/**
	 * 使用给定的编码器创建一个新的{@code  ProtobufHttpMessageWriter}。 
	 *  
	 * @param 编码器要使用的Protobuf消息编码器
	 */
	public ProtobufHttpMessageWriter(Encoder<Message> encoder) {
		super(encoder);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Mono<Void> write(Publisher<? extends Message> inputStream, ResolvableType elementType,
			@Nullable MediaType mediaType, ReactiveHttpOutputMessage message, Map<String, Object> hints) {

		try {
			Message.Builder builder = getMessageBuilder(elementType.toClass());
			Descriptors.Descriptor descriptor = builder.getDescriptorForType();
			message.getHeaders().add(X_PROTOBUF_SCHEMA_HEADER, descriptor.getFile().getName());
			message.getHeaders().add(X_PROTOBUF_MESSAGE_HEADER, descriptor.getFullName());
			if (inputStream instanceof Flux) {
				if (mediaType == null) {
					message.getHeaders().setContentType(((HttpMessageEncoder<?>)getEncoder()).getStreamingMediaTypes().get(0));
				}
				else if (!ProtobufEncoder.DELIMITED_VALUE.equals(mediaType.getParameters().get(ProtobufEncoder.DELIMITED_KEY))) {
					Map<String, String> parameters = new HashMap<>(mediaType.getParameters());
					parameters.put(ProtobufEncoder.DELIMITED_KEY, ProtobufEncoder.DELIMITED_VALUE);
					message.getHeaders().setContentType(new MediaType(mediaType.getType(), mediaType.getSubtype(), parameters));
				}
			}
			return super.write(inputStream, elementType, mediaType, message, hints);
		}
		catch (Exception ex) {
			return Mono.error(new DecodingException("Could not read Protobuf message: " + ex.getMessage(), ex));
		}
	}

	/**
	 * Create a new {@code Message.Builder} instance for the given class.
	 * <p>This method uses a ConcurrentHashMap for caching method lookups.
	 */
	/**
	 * 为给定的类创建一个新的{@code  Message.Builder}实例。 
	 *  <p>此方法使用ConcurrentHashMap进行缓存方法查找。 
	 * 
	 */
	private static Message.Builder getMessageBuilder(Class<?> clazz) throws Exception {
		Method method = methodCache.get(clazz);
		if (method == null) {
			method = clazz.getMethod("newBuilder");
			methodCache.put(clazz, method);
		}
		return (Message.Builder) method.invoke(clazz);
	}

}
