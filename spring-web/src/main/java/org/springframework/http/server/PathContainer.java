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

package org.springframework.http.server;

import java.util.List;

import org.springframework.util.MultiValueMap;

/**
 * Structured representation of a URI path parsed via {@link #parsePath(String)}
 * into a sequence of {@link Separator} and {@link PathSegment} elements.
 *
 * <p>Each {@link PathSegment} exposes its content in decoded form and with path
 * parameters removed. This makes it safe to match one path segment at a time
 * without the risk of decoded reserved characters altering the structure of
 * the path.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 通过{@link  #parsePath（String）}解析为一系列{@link 分隔符}和{@link  PathSegment}元素的URI路径的结构化表示。 
 *  <p>每个{@link  PathSegment}均以解码形式公开其内容，并删除路径参数。 
 * 这样可以安全地一次匹配一个路径段，而没有解码的保留字符改变路径结构的风险。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public interface PathContainer {

	/**
	 * The original path from which this instance was parsed.
	 */
	/**
	 * 解析此实例的原始路径。 
	 * 
	 */
	String value();

	/**
	 * The contained path elements, either {@link Separator} or {@link PathSegment}.
	 */
	/**
	 * 包含的路径元素，{<@link>分隔符}或{@link  PathSegment}。 
	 * 
	 */
	List<Element> elements();

	/**
	 * Extract a sub-path from the given offset into the elements list.
	 * @param index the start element index (inclusive)
	 * @return the sub-path
	 */
	/**
	 * 从给定的偏移量中提取一个子路径到元素列表中。 
	 *  
	 * @param 索引起始元素索引（含）
	 * @return 子路径
	 */
	default PathContainer subPath(int index) {
		return subPath(index, elements().size());
	}

	/**
	 * Extract a sub-path from the given start offset into the element list
	 * (inclusive) and to the end offset (exclusive).
	 * @param startIndex the start element index (inclusive)
	 * @param endIndex the end element index (exclusive)
	 * @return the sub-path
	 */
	/**
	 * 从给定的起始偏移量提取一个子路径到元素列表（包括）和结束偏移量（不包括）。 
	 *  
	 * @param  startIndex起始元素索引（含）
	 * @param  endIndex结束元素索引（不含）
	 * @return 子路径
	 */
	default PathContainer subPath(int startIndex, int endIndex) {
		return DefaultPathContainer.subPath(this, startIndex, endIndex);
	}


	/**
	 * Parse the path value into a sequence of {@code "/"} {@link Separator Separator}
	 * and {@link PathSegment PathSegment} elements.
	 * @param path the encoded, raw path value to parse
	 * @return the parsed path
	 */
	/**
	 * 将路径值解析为{{@code>"/"} {@link 分隔符}和{@link  PathSegment PathSegment}元素的序列。 
	 *  
	 * @param 路径解析的原始编码路径值
	 * @return 解析的路径
	 */
	static PathContainer parsePath(String path) {
		return DefaultPathContainer.createFromUrlPath(path, Options.HTTP_PATH);
	}

	/**
	 * Parse the path value into a sequence of {@link Separator Separator} and
	 * {@link PathSegment PathSegment} elements.
	 * @param path the encoded, raw path value to parse
	 * @param options to customize parsing
	 * @return the parsed path
	 * @since 5.2
	 */
	/**
	 * 将路径值解析为{@link 分隔符}和{@link  PathSegment PathSegment}元素的序列。 
	 *  
	 * @param 路径用于解析的已编码原始路径值
	 * @param 选项以自定义解析
	 * @return 解析路径@5.2起
	 */
	static PathContainer parsePath(String path, Options options) {
		return DefaultPathContainer.createFromUrlPath(path, options);
	}


	/**
	 * A path element, either separator or path segment.
	 */
	/**
	 * 路径元素，分隔符或路径段。 
	 * 
	 */
	interface Element {

		/**
		 * The unmodified, original value of this element.
		 */
		/**
		 * 此元素的未经修改的原始值。 
		 * 
		 */
		String value();
	}


	/**
	 * Path separator element.
	 */
	/**
	 * 路径分隔符元素。 
	 * 
	 */
	interface Separator extends Element {
	}


	/**
	 * Path segment element.
	 */
	/**
	 * 路径段元素。 
	 * 
	 */
	interface PathSegment extends Element {

		/**
		 * Return the path segment value, decoded and sanitized, for path matching.
		 */
		/**
		 * 返回经过解码和清理的路径段值，以进行路径匹配。 
		 * 
		 */
		String valueToMatch();

		/**
		 * Expose {@link #valueToMatch()} as a character array.
		 */
		/**
		 * 将{@link  #valueToMatch（）}公开为字符数组。 
		 * 
		 */
		char[] valueToMatchAsChars();

		/**
		 * Path parameters associated with this path segment.
		 */
		/**
		 * 与此路径段关联的路径参数。 
		 * 
		 */
		MultiValueMap<String, String> parameters();
	}


	/**
	 * Options to customize parsing based on the type of input path.
	 * @since 5.2
	 */
	/**
	 * 用于根据输入路径的类型来自定义分析的选项。 
	 *  @5.2起
	 */
	class Options {

		/**
		 * Options for HTTP URL paths:
		 * <p>Separator '/' with URL decoding and parsing of path params.
		 */
		/**
		 * HTTP URL路径的选项：<p>带有URL解码和路径参数解析的分隔符'/'。 
		 * 
		 */
		public final static Options HTTP_PATH = Options.create('/', true);

		/**
		 * Options for a message route:
		 * <p>Separator '.' without URL decoding nor parsing of params. Escape
		 * sequences for the separator char in segment values are still decoded.
		 */
		/**
		 * 消息路由的选项：<p>分隔符'。 
		 * 无需URL解码或参数解析。 
		 * 段值中分隔符char的转义序列仍被解码。 
		 * 
		 */
		public final static Options MESSAGE_ROUTE = Options.create('.', false);

		private final char separator;

		private final boolean decodeAndParseSegments;

		private Options(char separator, boolean decodeAndParseSegments) {
			this.separator = separator;
			this.decodeAndParseSegments = decodeAndParseSegments;
		}

		public char separator() {
			return this.separator;
		}

		public boolean shouldDecodeAndParseSegments() {
			return this.decodeAndParseSegments;
		}

		/**
		 * Create an {@link Options} instance with the given settings.
		 * @param separator the separator for parsing the path into segments;
		 * currently this must be slash or dot.
		 * @param decodeAndParseSegments whether to URL decode path segment
		 * values and parse path parameters. If set to false, only escape
		 * sequences for the separator char are decoded.
		 */
		/**
		 * 使用给定的设置创建一个{@link  Options}实例。 
		 *  
		 * @param 分隔符分隔符，用于将路径解析为段； 
		 * 当前这必须是斜线或点。 
		 *  
		 * @param  encodeAndParseSegments是否要URL解码路径段值和解析路径参数。 
		 * 如果设置为false，则仅解码分隔符char的转义序列。 
		 * 
		 */
		public static Options create(char separator, boolean decodeAndParseSegments) {
			return new Options(separator, decodeAndParseSegments);
		}
	}

}
