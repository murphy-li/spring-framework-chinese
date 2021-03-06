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

package org.springframework.web.reactive.function;

import java.util.Collections;
import java.util.List;

import org.springframework.core.NestedRuntimeException;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

/**
 * Exception thrown to indicate that a {@code Content-Type} is not supported.
 *
 * @author Arjen Poutsma
 * @since 5.0
 */
/**
 * 抛出异常，指示不支持{@code  Content-Type}。 
 *  @author  Arjen Poutsma @从5.0开始
 */
@SuppressWarnings("serial")
public class UnsupportedMediaTypeException extends NestedRuntimeException {

	@Nullable
	private final MediaType contentType;

	private final List<MediaType> supportedMediaTypes;

	@Nullable
	private final ResolvableType bodyType;


	/**
	 * Constructor for when the specified Content-Type is invalid.
	 */
	/**
	 * 指定的Content-Type无效时的构造方法。 
	 * 
	 */
	public UnsupportedMediaTypeException(String reason) {
		super(reason);
		this.contentType = null;
		this.supportedMediaTypes = Collections.emptyList();
		this.bodyType = null;
	}

	/**
	 * Constructor for when the Content-Type can be parsed but is not supported.
	 */
	/**
	 * 何时可以解析但不支持Content-Type的构造方法。 
	 * 
	 */
	public UnsupportedMediaTypeException(@Nullable MediaType contentType, List<MediaType> supportedTypes) {
		this(contentType, supportedTypes, null);
	}

	/**
	 * Constructor for when trying to encode from or decode to a specific Java type.
	 * @since 5.1
	 */
	/**
	 * 尝试从特定Java类型编码或解码为特定Java类型的构造方法。 
	 *  @5.1起
	 */
	public UnsupportedMediaTypeException(@Nullable MediaType contentType, List<MediaType> supportedTypes,
			@Nullable ResolvableType bodyType) {

		super(initReason(contentType, bodyType));
		this.contentType = contentType;
		this.supportedMediaTypes = Collections.unmodifiableList(supportedTypes);
		this.bodyType = bodyType;
	}

	private static String initReason(@Nullable MediaType contentType, @Nullable ResolvableType bodyType) {
		return "Content type '" + (contentType != null ? contentType : "") + "' not supported" +
				(bodyType != null ? " for bodyType=" + bodyType.toString() : "");
	}


	/**
	 * Return the request Content-Type header if it was parsed successfully,
	 * or {@code null} otherwise.
	 */
	/**
	 * 如果请求的Content-Type标头已成功解析，则返回； 
	 * 否则，返回{@code  null}。 
	 * 
	 */
	@Nullable
	public MediaType getContentType() {
		return this.contentType;
	}

	/**
	 * Return the list of supported content types in cases when the Content-Type
	 * header is parsed but not supported, or an empty list otherwise.
	 */
	/**
	 * 在解析但不支持Content-Type标头的情况下，返回受支持的内容类型的列表，否则返回空列表。 
	 * 
	 */
	public List<MediaType> getSupportedMediaTypes() {
		return this.supportedMediaTypes;
	}

	/**
	 * Return the body type in the context of which this exception was generated.
	 * This is applicable when the exception was raised as a result trying to
	 * encode from or decode to a specific Java type.
	 * @return the body type, or {@code null} if not available
	 * @since 5.1
	 */
	/**
	 * 返回在生成此异常的上下文中的主体类型。 
	 * 当由于尝试从特定Java类型进行编码或解码而引发异常时，此方法适用。 
	 *  
	 * @return 正文类型，或者{@code  null}（如果自5.1起不可用）
	 */
	@Nullable
	public ResolvableType getBodyType() {
		return this.bodyType;
	}

}
