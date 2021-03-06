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

package org.springframework.http.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

/**
 * A specialization of {@link HttpMessageConverter} that can convert an HTTP request
 * into a target object of a specified generic type and a source object of a specified
 * generic type into an HTTP response.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 3.2
 * @param <T> the converted object type
 * @see org.springframework.core.ParameterizedTypeReference
 */
/**
 * {@link  HttpMessageConverter}的特殊化，可以将HTTP请求转换为指定的通用类型的目标对象，并将指定的通用类型的源对象转换为HTTP响应。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）自3.2起
 * @param  <T>转换后的对象类型
 * @see  org.springframework.core.ParameterizedTypeReference
 */
public interface GenericHttpMessageConverter<T> extends HttpMessageConverter<T> {

	/**
	 * Indicates whether the given type can be read by this converter.
	 * This method should perform the same checks than
	 * {@link HttpMessageConverter#canRead(Class, MediaType)} with additional ones
	 * related to the generic type.
	 * @param type the (potentially generic) type to test for readability
	 * @param contextClass a context class for the target type, for example a class
	 * in which the target type appears in a method signature (can be {@code null})
	 * @param mediaType the media type to read, can be {@code null} if not specified.
	 * Typically the value of a {@code Content-Type} header.
	 * @return {@code true} if readable; {@code false} otherwise
	 */
	/**
	 * 指示此转换器是否可以读取给定类型。 
	 * 此方法应该执行与{@link  HttpMessageConverter＃canRead（Class，MediaType）}相同的检查，并执行与通用类型相关的其他检查。 
	 *  
	 * @param 类型（可能是通用的）类型以测试可读性
	 * @param  contextClass目标类型的上下文类，例如，目标类型出现在方法签名中的类（可以是{@code  null}）
	 * @param  mediaType要读取的媒体类型，如果未指定，则可以为{@code  null}。 
	 * 通常是{@code  Content-Type}标头的值。 
	 *  
	 * @return  {@code  true}（如果可读）； 
	 *  {@code  false}否则
	 */
	boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType);

	/**
	 * Read an object of the given type form the given input message, and returns it.
	 * @param type the (potentially generic) type of object to return. This type must have
	 * previously been passed to the {@link #canRead canRead} method of this interface,
	 * which must have returned {@code true}.
	 * @param contextClass a context class for the target type, for example a class
	 * in which the target type appears in a method signature (can be {@code null})
	 * @param inputMessage the HTTP input message to read from
	 * @return the converted object
	 * @throws IOException in case of I/O errors
	 * @throws HttpMessageNotReadableException in case of conversion errors
	 */
	/**
	 * 从给定的输入消息中读取给定类型的对象，并将其返回。 
	 *  
	 * @param 键入要返回的对象（可能是通用的）类型。 
	 * 此类型必须事先已传递到此接口的{@link  #canRead canRead}方法中，该方法必须返回{@code  true}。 
	 *  
	 * @param  contextClass目标类型的上下文类，例如，目标类型出现在方法签名中的类（可以为{@code  null}）
	 * @param  inputMessage从中读取的HTTP输入消息
	 * @return 转换后的对象
	 * @throws  IOException（如果发生I / O错误）
	 * @throws  HttpMessageNotReadableException（如果发生转换错误）
	 */
	T read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException;

	/**
	 * Indicates whether the given class can be written by this converter.
	 * <p>This method should perform the same checks than
	 * {@link HttpMessageConverter#canWrite(Class, MediaType)} with additional ones
	 * related to the generic type.
	 * @param type the (potentially generic) type to test for writability
	 * (can be {@code null} if not specified)
	 * @param clazz the source object class to test for writability
	 * @param mediaType the media type to write (can be {@code null} if not specified);
	 * typically the value of an {@code Accept} header.
	 * @return {@code true} if writable; {@code false} otherwise
	 * @since 4.2
	 */
	/**
	 * 指示给定的类是否可以由此转换器编写。 
	 *  <p>此方法应该执行与{@link  HttpMessageConverter＃canWrite（Class，MediaType）}相同的检查，并执行与通用类型相关的其他检查。 
	 *  
	 * @param 键入要测试可写性的（可能是通用的）类型（如果未指定，可以为{@code  null}）。 
	 * @
	 * @param>弄乱要测试可写性的源对象类。 
	 * 编写（如果未指定，可以为{@code  null}）； 
	 * 通常是{@code  Accept}头的值。 
	 *  
	 * @return  {@code  true}（如果可写）； 
	 *  {@code  false}否则@4.2起
	 */
	boolean canWrite(@Nullable Type type, Class<?> clazz, @Nullable MediaType mediaType);

	/**
	 * Write an given object to the given output message.
	 * @param t the object to write to the output message. The type of this object must
	 * have previously been passed to the {@link #canWrite canWrite} method of this
	 * interface, which must have returned {@code true}.
	 * @param type the (potentially generic) type of object to write. This type must have
	 * previously been passed to the {@link #canWrite canWrite} method of this interface,
	 * which must have returned {@code true}. Can be {@code null} if not specified.
	 * @param contentType the content type to use when writing. May be {@code null} to
	 * indicate that the default content type of the converter must be used. If not
	 * {@code null}, this media type must have previously been passed to the
	 * {@link #canWrite canWrite} method of this interface, which must have returned
	 * {@code true}.
	 * @param outputMessage the message to write to
	 * @throws IOException in case of I/O errors
	 * @throws HttpMessageNotWritableException in case of conversion errors
	 * @since 4.2
	 */
	/**
	 * 将给定对象写入给定输出消息。 
	 *  
	 * @param  t写入输出消息的对象。 
	 * 该对象的类型必须事先已传递到此接口的{@link  #canWrite canWrite}方法中，该方法必须返回{@code  true}。 
	 *  
	 * @param 键入要写入的对象（可能是通用的）类型。 
	 * 此类型必须事先已传递到此接口的{@link  #canWrite canWrite}方法中，该方法必须返回{@code  true}。 
	 * 如果未指定，则可以为{@code  null}。 
	 *  
	 * @param  contentType写入时要使用的内容类型。 
	 * 可能为{@code  null}表示必须使用转换器的默认内容类型。 
	 * 如果不是{@code  null}，则此媒体类型必须事先已传递到此接口的{@link  #canWrite canWrite}方法中，该方法必须返回{@code  true}。 
	 *  
	 * @param  outputMessage在发生I / O错误的情况下将消息写入
	 * @throws  IOException 
	 * @throws 在发生转换错误的情况下HttpMessageNotWritableException @从4.2开始
	 */
	void write(T t, @Nullable Type type, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException;

}
