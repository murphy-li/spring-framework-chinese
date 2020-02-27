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

package org.springframework.http.codec;

import java.util.List;
import java.util.Map;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;

/**
 * Strategy for encoding a stream of objects of type {@code <T>} and writing
 * the encoded stream of bytes to an {@link ReactiveHttpOutputMessage}.
 *
 * @author Rossen Stoyanchev
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @since 5.0
 * @param <T> the type of objects in the input stream
 */
/**
 * 编码类型为{@code  <T>}的对象流并将已编码的字节流写入{@link  ReactiveHttpOutputMessage}的策略。 
 *  @author  Rossen Stoyanchev @author  Arjen Poutsma @author  Sebastien Deleuze @since 5.0 
 * @param  <T>输入流中对象的类型
 */
public interface HttpMessageWriter<T> {

	/**
	 * Return the {@link MediaType}'s that this writer supports.
	 */
	/**
	 * 返回作者支持的{@link  MediaType}。 
	 * 
	 */
	List<MediaType> getWritableMediaTypes();

	/**
	 * Whether the given object type is supported by this writer.
	 * @param elementType the type of object to check
	 * @param mediaType the media type for the write (possibly {@code null})
	 * @return {@code true} if writable, {@code false} otherwise
	 */
	/**
	 * 给定的对象类型是否受此编写器支持。 
	 *  
	 * @param  elementType要检查的对象的类型
	 * @param  mediaType用于写入的介质类型（可能为{@code  null}）
	 * @return  {@code  true}（如果可写），{<@code > false}否则
	 */
	boolean canWrite(ResolvableType elementType, @Nullable MediaType mediaType);

	/**
	 * Write an given stream of object to the output message.
	 * @param inputStream the objects to write
	 * @param elementType the type of objects in the stream which must have been
	 * previously checked via {@link #canWrite(ResolvableType, MediaType)}
	 * @param mediaType the content type for the write (possibly {@code null} to
	 * indicate that the default content type of the writer must be used)
	 * @param message the message to write to
	 * @param hints additional information about how to encode and write
	 * @return indicates completion or error
	 */
	/**
	 * 将给定的对象流写入输出消息。 
	 *  
	 * @param  inputStream要写入的对象
	 * @param  elementType流中必须事先通过{@link  #canWrite（ResolvableType，MediaType）}检查过的对象的类型。 
	 * 
	 * @param  mediaType的内容类型写入（可能使用{@code  null}表示必须使用编写器的默认内容类型）
	 * @param 消息写入
	 * @param 的消息提示有关如何编码和写入<@的其他信息return>表示完成或错误
	 */
	Mono<Void> write(Publisher<? extends T> inputStream, ResolvableType elementType,
			@Nullable MediaType mediaType, ReactiveHttpOutputMessage message, Map<String, Object> hints);

	/**
	 * Server-side only alternative to
	 * {@link #write(Publisher, ResolvableType, MediaType, ReactiveHttpOutputMessage, Map)}
	 * with additional context available.
	 * @param actualType the actual return type of the method that returned the
	 * value; for annotated controllers, the {@link MethodParameter} can be
	 * accessed via {@link ResolvableType#getSource()}.
	 * @param elementType the type of Objects in the input stream
	 * @param mediaType the content type to use (possibly {@code null} indicating
	 * the default content type of the writer should be used)
	 * @param request the current request
	 * @param response the current response
	 * @return a {@link Mono} that indicates completion of writing or error
	 */
	/**
	 * 具有其他可用上下文的{@link  #write（Publisher，ResolvableType，MediaType，ReactiveHttpOutputMessage，Map）}的仅服务器端替代方法。 
	 *  
	 * @param  actualType返回值的方法的实际返回类型； 
	 * 对于带注释的控制器，可以通过{@link  ResolvableType＃getSource（）}访问{@link  MethodParameter}。 
	 *  
	 * @param  elementType输入流中对象的类型
	 * @param  mediaType要使用的内容类型（可能是{@code  null}，指示应使用编写器的默认内容类型）
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @return 一个{@link  Mono}，指示写入或错误完成
	 */
	default Mono<Void> write(Publisher<? extends T> inputStream, ResolvableType actualType,
			ResolvableType elementType, @Nullable MediaType mediaType, ServerHttpRequest request,
			ServerHttpResponse response, Map<String, Object> hints) {

		return write(inputStream, elementType, mediaType, response, hints);
	}

}
