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

package org.springframework.http.codec;

import java.util.List;
import java.util.Map;

import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Encoder;
import org.springframework.core.codec.Hints;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;

/**
 * Extension of {@code Encoder} exposing extra methods relevant in the context
 * of HTTP request or response body encoding.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 * @param <T> the type of elements in the input stream
 */
/**
 * {@code  Encoder}的扩展公开了在HTTP请求或响应主体编码的上下文中相关的其他方法。 
 *  @author  Rossen Stoyanchev @从5.0开始
 * @param  <T>输入流中元素的类型
 */
public interface HttpMessageEncoder<T> extends Encoder<T> {

	/**
	 * Return "streaming" media types for which flushing should be performed
	 * automatically vs at the end of the input stream.
	 */
	/**
	 * 返回应自动执行冲洗的"流"媒体类型，而不是在输入流的末尾。 
	 * 
	 */
	List<MediaType> getStreamingMediaTypes();

	/**
	 * Get decoding hints based on the server request or annotations on the
	 * target controller method parameter.
	 * @param actualType the actual source type to encode, possibly a reactive
	 * wrapper and sourced from {@link org.springframework.core.MethodParameter},
	 * i.e. providing access to method annotations.
	 * @param elementType the element type within {@code Flux/Mono} that we're
	 * trying to encode.
	 * @param request the current request
	 * @param response the current response
	 * @return a Map with hints, possibly empty
	 */
	/**
	 * 根据服务器请求或目标控制器方法参数上的注释获取解码提示。 
	 *  
	 * @param  actualType要编码的实际源类型，可能是反应性包装，并从{@link  org.springframework.core.MethodParameter}派生，即提供对方法注释的访问。 
	 *  
	 * @param  elementType我们尝试编码的{@code  Flux / Mono}中的元素类型。 
	 *  
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @return 带有提示的Map，可能为空
	 */
	default Map<String, Object> getEncodeHints(ResolvableType actualType, ResolvableType elementType,
			@Nullable MediaType mediaType, ServerHttpRequest request, ServerHttpResponse response) {

		return Hints.none();
	}

}
