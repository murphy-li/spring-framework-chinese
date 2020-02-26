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

package org.springframework.web.socket.adapter.standard;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;

/**
 * Base class that can be used to implement a standard {@link javax.websocket.Encoder}
 * and/or {@link javax.websocket.Decoder}. It provides encode and decode method
 * implementations that delegate to a Spring {@link ConversionService}.
 *
 * <p>By default, this class looks up a {@link ConversionService} registered in the
 * {@link #getApplicationContext() active ApplicationContext} under
 * the name {@code 'webSocketConversionService'}. This works fine for both client
 * and server endpoints, in a Servlet container environment. If not running in a
 * Servlet container, subclasses will need to override the
 * {@link #getConversionService()} method to provide an alternative lookup strategy.
 *
 * <p>Subclasses can extend this class and should also implement one or
 * both of {@link javax.websocket.Encoder} and {@link javax.websocket.Decoder}.
 * For convenience {@link ConvertingEncoderDecoderSupport.BinaryEncoder},
 * {@link ConvertingEncoderDecoderSupport.BinaryDecoder},
 * {@link ConvertingEncoderDecoderSupport.TextEncoder} and
 * {@link ConvertingEncoderDecoderSupport.TextDecoder} subclasses are provided.
 *
 * <p>Since JSR-356 only allows Encoder/Decoder to be registered by type, instances
 * of this class are therefore managed by the WebSocket runtime, and do not need to
 * be registered as Spring Beans. They can, however, by injected with Spring-managed
 * dependencies via {@link Autowired @Autowire}.
 *
 * <p>Converters to convert between the {@link #getType() type} and {@code String} or
 * {@code ByteBuffer} should be registered.
 *
 * @author Phillip Webb
 * @since 4.0
 * @param <T> the type being converted to (for Encoder) or from (for Decoder)
 * @param <M> the WebSocket message type ({@link String} or {@link ByteBuffer})
 * @see ConvertingEncoderDecoderSupport.BinaryEncoder
 * @see ConvertingEncoderDecoderSupport.BinaryDecoder
 * @see ConvertingEncoderDecoderSupport.TextEncoder
 * @see ConvertingEncoderDecoderSupport.TextDecoder
 */
/**
 * 可用于实现标准{@link  javax.websocket.Encoder}和/或{@link  javax.websocket.Decoder}的基类。 
 * 它提供了编码和解码方法的实现，这些实现委派给Spring {@link  ConversionService}。 
 *  <p>默认情况下，此类会在名称为{@code 'webSocketConversionService'}的{@link  #getApplicationContext（）活动ApplicationContext}中查找注册的{@link  ConversionService}。 
 * 在Servlet容器环境中，这对于客户端和服务器端点都可以正常工作。 
 * 如果未在Servlet容器中运行，则子类将需要重写{@link  #getConversionService（）}方法以提供替代的查找策略。 
 *  <p>子类可以扩展此类，并且还应该实现{@link  javax.websocket.Encoder}和{@link  javax.websocket.Decoder}中的一个或两个。 
 * 为了方便起见，提供了{@link  ConvertingEncoderDecoderSupport.BinaryEncoder}，{<@link> ConvertingEncoderDecoderSupport.BinaryDecoder}，{<@link> ConvertingEncoderDecoderSupport.TextEncoder}和{@link  ConvertingEncoderDecoderSupport.TextDecoder}子类。 
 *  <p>由于JSR-356仅允许按类型注册编码器/解码器，因此此类的实例由WebSocket运行时管理，而不必注册为Spring Bean。 
 * 但是，可以通过{@link  Autowired @Autowire}注入Spring管理的依赖项。 
 *  <p>应注册在{@link  #getType（）类型}和{@code 字符串}或{@code  ByteBuffer}之间进行转换的转换器。 
 *  @author  Phillip Webb @从4.0开始
 * @param  <T>转换为（对于编码器）或从（对于解码器）（@
 * @param>）<M> WebSocket消息类型（{@link 字符串}或{@link  ByteBuffer}）
 * @see  ConvertingEncoderDecoderSupport.BinaryEncoder 
 * @see  ConvertingEncoderDecoderSupport.BinaryDecoder 
 * @see  ConvertingEncoderDecoderSupport.TextEncoder 
 * @see  ConvertingEncoderDecoderSupport.TextDecoder
 */
public abstract class ConvertingEncoderDecoderSupport<T, M> {

	private static final String CONVERSION_SERVICE_BEAN_NAME = "webSocketConversionService";


	/**
	 * Called to initialize the encoder/decoder.
	 * @see javax.websocket.Encoder#init(EndpointConfig)
	 * @see javax.websocket.Decoder#init(EndpointConfig)
	 */
	/**
	 * 调用以初始化编码器/解码器。 
	 *  
	 * @see  javax.websocket.Encoder＃init（EndpointConfig）
	 * @see  javax.websocket.Decoder＃init（EndpointConfig）
	 */
	public void init(EndpointConfig config) {
		ApplicationContext applicationContext = getApplicationContext();
		if (applicationContext != null && applicationContext instanceof ConfigurableApplicationContext) {
			ConfigurableListableBeanFactory beanFactory =
					((ConfigurableApplicationContext) applicationContext).getBeanFactory();
			beanFactory.autowireBean(this);
		}
	}

	/**
	 * Called to destroy the encoder/decoder.
	 * @see javax.websocket.Encoder#destroy()
	 * @see javax.websocket.Decoder#destroy()
	 */
	/**
	 * 调用以销毁编码器/解码器。 
	 *  
	 * @see  javax.websocket.Encoder＃destroy（）
	 * @see  javax.websocket.Decoder＃destroy（）
	 */
	public void destroy() {
	}

	/**
	 * Strategy method used to obtain the {@link ConversionService}. By default this
	 * method expects a bean named {@code 'webSocketConversionService'} in the
	 * {@link #getApplicationContext() active ApplicationContext}.
	 * @return the {@link ConversionService} (never null)
	 */
	/**
	 * 用于获取{@link  ConversionService}的策略方法。 
	 * 默认情况下，此方法在{@link  #getApplicationContext（）active ApplicationContext}中需要一个名为{@code 'webSocketConversionService'}的bean。 
	 *  
	 * @return  {@link  ConversionService}（绝不为null）
	 */
	protected ConversionService getConversionService() {
		ApplicationContext applicationContext = getApplicationContext();
		Assert.state(applicationContext != null, "Unable to locate the Spring ApplicationContext");
		try {
			return applicationContext.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class);
		}
		catch (BeansException ex) {
			throw new IllegalStateException("Unable to find ConversionService: please configure a '" +
					CONVERSION_SERVICE_BEAN_NAME + "' or override the getConversionService() method", ex);
		}
	}

	/**
	 * Returns the active {@link ApplicationContext}. Be default this method obtains
	 * the context via {@link ContextLoader#getCurrentWebApplicationContext()}, which
	 * finds the ApplicationContext loaded via {@link ContextLoader} typically in a
	 * Servlet container environment. When not running in a Servlet container and
	 * not using {@link ContextLoader}, this method should be overridden.
	 * @return the {@link ApplicationContext} or {@code null}
	 */
	/**
	 * 返回活动的{@link  ApplicationContext}。 
	 * 默认情况下，此方法通过{@link  ContextLoader＃getCurrentWebApplicationContext（）}获取上下文，该上下文查找通常通过Servlet容器环境通过{@link  ContextLoader}加载的ApplicationContext。 
	 * 当不在Servlet容器中运行且未使用{@link  ContextLoader}时，应重写此方法。 
	 *  
	 * @return  {@link  ApplicationContext}或{@code  null}
	 */
	@Nullable
	protected ApplicationContext getApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

	/**
	 * Returns the type being converted. By default the type is resolved using
	 * the generic arguments of the class.
	 */
	/**
	 * 返回要转换的类型。 
	 * 默认情况下，使用类的通用参数来解析类型。 
	 * 
	 */
	protected TypeDescriptor getType() {
		return TypeDescriptor.valueOf(resolveTypeArguments()[0]);
	}

	/**
	 * Returns the websocket message type. By default the type is resolved using
	 * the generic arguments of the class.
	 */
	/**
	 * 返回websocket消息类型。 
	 * 默认情况下，使用类的通用参数来解析类型。 
	 * 
	 */
	protected TypeDescriptor getMessageType() {
		return TypeDescriptor.valueOf(resolveTypeArguments()[1]);
	}

	private Class<?>[] resolveTypeArguments() {
		Class<?>[] resolved = GenericTypeResolver.resolveTypeArguments(getClass(), ConvertingEncoderDecoderSupport.class);
		if (resolved == null) {
			throw new IllegalStateException("ConvertingEncoderDecoderSupport's generic types T and M " +
					"need to be substituted in subclass: " + getClass());
		}
		return resolved;
	}

	/**
	 * Encode an object to a message.
	 * @see javax.websocket.Encoder.Text#encode(Object)
	 * @see javax.websocket.Encoder.Binary#encode(Object)
	 */
	/**
	 * 将对象编码为消息。 
	 *  
	 * @see  javax.websocket.Encoder.Text＃encode（对象）
	 * @see  javax.websocket.Encoder.Binary＃encode（Object）
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public M encode(T object) throws EncodeException {
		try {
			return (M) getConversionService().convert(object, getType(), getMessageType());
		}
		catch (ConversionException ex) {
			throw new EncodeException(object, "Unable to encode websocket message using ConversionService", ex);
		}
	}

	/**
	 * Determine if a given message can be decoded.
	 * @see #decode(Object)
	 * @see javax.websocket.Decoder.Text#willDecode(String)
	 * @see javax.websocket.Decoder.Binary#willDecode(ByteBuffer)
	 */
	/**
	 * 确定给定的消息是否可以解码。 
	 *  
	 * @see  #decode（Object）
	 * @see  javax.websocket.Decoder.Text＃willDecode（String）
	 * @see  javax.websocket.Decoder.Binary＃willDecode（ByteBuffer）
	 */
	public boolean willDecode(M bytes) {
		return getConversionService().canConvert(getType(), getMessageType());
	}

	/**
	 * Decode the a message into an object.
	 * @see javax.websocket.Decoder.Text#decode(String)
	 * @see javax.websocket.Decoder.Binary#decode(ByteBuffer)
	 */
	/**
	 * 将消息解码为对象。 
	 *  
	 * @see  javax.websocket.Decoder.Text＃decode（String）
	 * @see  javax.websocket.Decoder.Binary＃decode（ByteBuffer）
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public T decode(M message) throws DecodeException {
		try {
			return (T) getConversionService().convert(message, getMessageType(), getType());
		}
		catch (ConversionException ex) {
			if (message instanceof String) {
				throw new DecodeException((String) message,
						"Unable to decode websocket message using ConversionService", ex);
			}
			if (message instanceof ByteBuffer) {
				throw new DecodeException((ByteBuffer) message,
						"Unable to decode websocket message using ConversionService", ex);
			}
			throw ex;
		}
	}


	/**
	 * A binary {@link javax.websocket.Encoder.Binary javax.websocket.Encoder} that delegates
	 * to Spring's conversion service. See {@link ConvertingEncoderDecoderSupport} for details.
	 * @param <T> the type that this Encoder can convert to
	 */
	/**
	 * 二进制{@link  javax.websocket.Encoder.Binary javax.websocket.Encoder}委托给Spring的转换服务。 
	 * 有关详细信息，请参见{@link  ConvertingEncoderDecoderSupport}。 
	 *  
	 * @param  <T>此编码器可以转换为的类型
	 */
	public abstract static class BinaryEncoder<T> extends ConvertingEncoderDecoderSupport<T, ByteBuffer>
			implements Encoder.Binary<T> {
	}


	/**
	 * A binary {@link javax.websocket.Encoder.Binary javax.websocket.Encoder} that delegates
	 * to Spring's conversion service. See {@link ConvertingEncoderDecoderSupport} for details.
	 * @param <T> the type that this Decoder can convert from
	 */
	/**
	 * 二进制{@link  javax.websocket.Encoder.Binary javax.websocket.Encoder}委托给Spring的转换服务。 
	 * 有关详细信息，请参见{@link  ConvertingEncoderDecoderSupport}。 
	 *  
	 * @param  <T>此解码器可以转换的类型
	 */
	public abstract static class BinaryDecoder<T> extends ConvertingEncoderDecoderSupport<T, ByteBuffer>
			implements Decoder.Binary<T> {
	}


	/**
	 * A text {@link javax.websocket.Encoder.Text javax.websocket.Encoder} that delegates
	 * to Spring's conversion service. See {@link ConvertingEncoderDecoderSupport} for
	 * details.
	 * @param <T> the type that this Encoder can convert to
	 */
	/**
	 * 文本{@link  javax.websocket.Encoder.Text javax.websocket.Encoder}委托给Spring的转换服务。 
	 * 有关详细信息，请参见{@link  ConvertingEncoderDecoderSupport}。 
	 *  
	 * @param  <T>此编码器可以转换为的类型
	 */
	public abstract static class TextEncoder<T> extends ConvertingEncoderDecoderSupport<T, String>
			implements Encoder.Text<T> {
	}


	/**
	 * A Text {@link javax.websocket.Encoder.Text javax.websocket.Encoder} that delegates
	 * to Spring's conversion service. See {@link ConvertingEncoderDecoderSupport} for details.
	 * @param <T> the type that this Decoder can convert from
	 */
	/**
	 * 文本{@link  javax.websocket.Encoder.Text javax.websocket.Encoder}委托给Spring的转换服务。 
	 * 有关详细信息，请参见{@link  ConvertingEncoderDecoderSupport}。 
	 *  
	 * @param  <T>此解码器可以转换的类型
	 */
	public abstract static class TextDecoder<T> extends ConvertingEncoderDecoderSupport<T, String>
			implements Decoder.Text<T> {
	}

}
