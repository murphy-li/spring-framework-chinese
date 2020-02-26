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

package org.springframework.messaging.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.TypeMismatchException;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

/**
 * Implementation of {@link MessageConverter} that can read and write XML using Spring's
 * {@link Marshaller} and {@link Unmarshaller} abstractions.
 *
 * <p>This converter requires a {@code Marshaller} and {@code Unmarshaller} before it can
 * be used. These can be injected by the {@linkplain #MarshallingMessageConverter(Marshaller)
 * constructor} or {@linkplain #setMarshaller(Marshaller) bean properties}.
 *
 * @author Arjen Poutsma
 * @since 4.2
 */
/**
 * {@link  MessageConverter}的实现，可以使用Spring的{@link  Marshaller}和{@link  Unmarshaller}抽象来读写XML。 
 *  <p>此转换器需要使用{@code  Marshaller}和{<@@code> Unmarshaller}才能使用。 
 * 这些可以通过{@link  plain #MarshallingMessageConverter（Marshaller）构造函数}或{@link  plain #setMarshaller（Marshaller）bean属性}注入。 
 *  @author  Arjen Poutsma @从4.2开始
 */
public class MarshallingMessageConverter extends AbstractMessageConverter {

	@Nullable
	private Marshaller marshaller;

	@Nullable
	private Unmarshaller unmarshaller;


	/**
	 * Default construct allowing for {@link #setMarshaller(Marshaller)} and/or
	 * {@link #setUnmarshaller(Unmarshaller)} to be invoked separately.
	 */
	/**
	 * 默认构造允许分别调用{@link  #setMarshaller（Marshaller）}和/或{@link  #setUnmarshaller（Unmarshaller）}。 
	 * 
	 */
	public MarshallingMessageConverter() {
		this(new MimeType("application", "xml"), new MimeType("text", "xml"), new MimeType("application", "*+xml"));
	}

	/**
	 * Constructor with a given list of MIME types to support.
	 * @param supportedMimeTypes the MIME types
	 */
	/**
	 * 具有要支持的MIME类型的给定列表的构造方法。 
	 *  
	 * @param  supportedMimeTypes MIME类型
	 */
	public MarshallingMessageConverter(MimeType... supportedMimeTypes) {
		super(supportedMimeTypes);
	}

	/**
	 * Constructor with {@link Marshaller}. If the given {@link Marshaller} also
	 * implements {@link Unmarshaller}, it is also used for unmarshalling.
	 * <p>Note that all {@code Marshaller} implementations in Spring also implement
	 * {@code Unmarshaller} so that you can safely use this constructor.
	 * @param marshaller object used as marshaller and unmarshaller
	 */
	/**
	 * {@link  Marshaller}的构造函数。 
	 * 如果给定的{@link  Marshaller}也实现了{@link  Unmarshaller}，则它也用于解组。 
	 *  <p>请注意，Spring中的所有{@code  Marshaller}实现也都实现了{@code  Unmarshaller}，以便您可以安全地使用此构造函数。 
	 *  
	 * @param  marshaller对象用作marshaller和unmarshaller
	 */
	public MarshallingMessageConverter(Marshaller marshaller) {
		this();
		Assert.notNull(marshaller, "Marshaller must not be null");
		this.marshaller = marshaller;
		if (marshaller instanceof Unmarshaller) {
			this.unmarshaller = (Unmarshaller) marshaller;
		}
	}


	/**
	 * Set the {@link Marshaller} to be used by this message converter.
	 */
	/**
	 * 设置{@link  Marshaller}以供此消息转换器使用。 
	 * 
	 */
	public void setMarshaller(@Nullable Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * Return the configured Marshaller.
	 */
	/**
	 * 返回配置的Marshaller。 
	 * 
	 */
	@Nullable
	public Marshaller getMarshaller() {
		return this.marshaller;
	}

	/**
	 * Set the {@link Unmarshaller} to be used by this message converter.
	 */
	/**
	 * 设置{@link  Unmarshaller}以便此消息转换器使用。 
	 * 
	 */
	public void setUnmarshaller(@Nullable Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	/**
	 * Return the configured unmarshaller.
	 */
	/**
	 * 返回配置的解组器。 
	 * 
	 */
	@Nullable
	public Unmarshaller getUnmarshaller() {
		return this.unmarshaller;
	}


	@Override
	protected boolean canConvertFrom(Message<?> message, Class<?> targetClass) {
		return (supportsMimeType(message.getHeaders()) && this.unmarshaller != null &&
				this.unmarshaller.supports(targetClass));
	}

	@Override
	protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
		return (supportsMimeType(headers) && this.marshaller != null &&
				this.marshaller.supports(payload.getClass()));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canConvertFrom/canConvertTo instead
		throw new UnsupportedOperationException();
	}

	@Override
	@Nullable
	protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
		Assert.notNull(this.unmarshaller, "Property 'unmarshaller' is required");
		try {
			Source source = getSource(message.getPayload());
			Object result = this.unmarshaller.unmarshal(source);
			if (!targetClass.isInstance(result)) {
				throw new TypeMismatchException(result, targetClass);
			}
			return result;
		}
		catch (Exception ex) {
			throw new MessageConversionException(message, "Could not unmarshal XML: " + ex.getMessage(), ex);
		}
	}

	private Source getSource(Object payload) {
		if (payload instanceof byte[]) {
			return new StreamSource(new ByteArrayInputStream((byte[]) payload));
		}
		else {
			return new StreamSource(new StringReader((String) payload));
		}
	}

	@Override
	@Nullable
	protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers,
			@Nullable Object conversionHint) {

		Assert.notNull(this.marshaller, "Property 'marshaller' is required");
		try {
			if (byte[].class == getSerializedPayloadClass()) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Result result = new StreamResult(out);
				this.marshaller.marshal(payload, result);
				payload = out.toByteArray();
			}
			else {
				Writer writer = new StringWriter();
				Result result = new StreamResult(writer);
				this.marshaller.marshal(payload, result);
				payload = writer.toString();
			}
		}
		catch (Throwable ex) {
			throw new MessageConversionException("Could not marshal XML: " + ex.getMessage(), ex);
		}
		return payload;
	}

}
