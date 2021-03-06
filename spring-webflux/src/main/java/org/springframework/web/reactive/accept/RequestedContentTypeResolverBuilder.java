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

package org.springframework.web.reactive.accept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

/**
 * Builder for a composite {@link RequestedContentTypeResolver} that delegates
 * to other resolvers each implementing a different strategy to determine the
 * requested content type -- e.g. Accept header, query parameter, or other.
 *
 * <p>Use builder methods to add resolvers in the desired order. For a given
 * request he first resolver to return a list that is not empty and does not
 * consist of just {@link MediaType#ALL}, will be used.
 *
 * <p>By default, if no resolvers are explicitly configured, the builder will
 * add {@link HeaderContentTypeResolver}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 合成{@link  RequestedContentTypeResolver}的构建器，该委派给其他解析器的委托，每个解析器都实施不同的策略来确定所请求的内容类型，例如接受标头，查询参数或其他。 
 *  <p>使用构建器方法以所需顺序添加解析器。 
 * 对于给定的请求，将使用他的第一个解析器返回不为空且不仅由{@link  MediaType＃ALL}组成的列表。 
 *  <p>默认情况下，如果未明确配置解析器，则构建器将添加{@link  HeaderContentTypeResolver}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class RequestedContentTypeResolverBuilder {

	private final List<Supplier<RequestedContentTypeResolver>> candidates = new ArrayList<>();


	/**
	 * Add a resolver to get the requested content type from a query parameter.
	 * By default the query parameter name is {@code "format"}.
	 */
	/**
	 * 添加解析器以从查询参数获取请求的内容类型。 
	 * 默认情况下，查询参数名称为{@code "format"}。 
	 * 
	 */
	public ParameterResolverConfigurer parameterResolver() {
		ParameterResolverConfigurer parameterBuilder = new ParameterResolverConfigurer();
		this.candidates.add(parameterBuilder::createResolver);
		return parameterBuilder;
	}

	/**
	 * Add resolver to get the requested content type from the
	 * {@literal "Accept"} header.
	 */
	/**
	 * 添加解析程序以从{@literal"Accept"}标头中获取请求的内容类型。 
	 * 
	 */
	public void headerResolver() {
		this.candidates.add(HeaderContentTypeResolver::new);
	}

	/**
	 * Add resolver that returns a fixed set of media types.
	 * @param mediaTypes the media types to use
	 */
	/**
	 * 添加返回固定媒体类型集的解析器。 
	 *  
	 * @param  mediaType键入要使用的媒体类型
	 */
	public void fixedResolver(MediaType... mediaTypes) {
		this.candidates.add(() -> new FixedContentTypeResolver(Arrays.asList(mediaTypes)));
	}

	/**
	 * Add a custom resolver.
	 * @param resolver the resolver to add
	 */
	/**
	 * 添加自定义解析器。 
	 *  
	 * @param 解析器要添加的解析器
	 */
	public void resolver(RequestedContentTypeResolver resolver) {
		this.candidates.add(() -> resolver);
	}

	/**
	 * Build a {@link RequestedContentTypeResolver} that delegates to the list
	 * of resolvers configured through this builder.
	 */
	/**
	 * 构建一个{@link  RequestedContentTypeResolver}，委派给通过此构建器配置的解析器列表。 
	 * 
	 */
	public RequestedContentTypeResolver build() {
		List<RequestedContentTypeResolver> resolvers = (!this.candidates.isEmpty() ?
				this.candidates.stream().map(Supplier::get).collect(Collectors.toList()) :
				Collections.singletonList(new HeaderContentTypeResolver()));

		return exchange -> {
			for (RequestedContentTypeResolver resolver : resolvers) {
				List<MediaType> mediaTypes = resolver.resolveMediaTypes(exchange);
				if (mediaTypes.equals(RequestedContentTypeResolver.MEDIA_TYPE_ALL_LIST)) {
					continue;
				}
				return mediaTypes;
			}
			return RequestedContentTypeResolver.MEDIA_TYPE_ALL_LIST;
		};
	}


	/**
	 * Helper to create and configure {@link ParameterContentTypeResolver}.
	 */
	/**
	 * 创建和配置{@link  ParameterContentTypeResolver}的助手。 
	 * 
	 */
	public static class ParameterResolverConfigurer {

		private final Map<String, MediaType> mediaTypes = new HashMap<>();

		@Nullable
		private String parameterName;

		/**
		 * Configure a mapping between a lookup key (extracted from a query
		 * parameter value) and a corresponding {@code MediaType}.
		 * @param key the lookup key
		 * @param mediaType the MediaType for that key
		 */
		/**
		 * 在查找关键字（从查询参数值中提取）和相应的{@code  MediaType}之间配置映射。 
		 *  
		 * @param 键查找键
		 * @param  mediaType该键的MediaType
		 */
		public ParameterResolverConfigurer mediaType(String key, MediaType mediaType) {
			this.mediaTypes.put(key, mediaType);
			return this;
		}

		/**
		 * Map-based variant of {@link #mediaType(String, MediaType)}.
		 * @param mediaTypes the mappings to copy
		 */
		/**
		 * {@link  #mediaType（String，MediaType）}的基于地图的变体。 
		 *  
		 * @param  mediaTypes要复制的映射
		 */
		public ParameterResolverConfigurer mediaType(Map<String, MediaType> mediaTypes) {
			this.mediaTypes.putAll(mediaTypes);
			return this;
		}

		/**
		 * Set the name of the parameter to use to determine requested media types.
		 * <p>By default this is set to {@literal "format"}.
		 */
		/**
		 * 设置参数名称以用于确定请求的媒体类型。 
		 *  <p>默认情况下，此设置为{@literal"format"}。 
		 * 
		 */
		public ParameterResolverConfigurer parameterName(String parameterName) {
			this.parameterName = parameterName;
			return this;
		}

		/**
		 * Private factory method to create the resolver.
		 */
		/**
		 * 创建解析器的私有工厂方法。 
		 * 
		 */
		private RequestedContentTypeResolver createResolver() {
			ParameterContentTypeResolver resolver = new ParameterContentTypeResolver(this.mediaTypes);
			if (this.parameterName != null) {
				resolver.setParameterName(this.parameterName);
			}
			return resolver;
		}
	}

}
