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

package org.springframework.web.util;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * Utility methods for URI encoding and decoding based on RFC 3986.
 *
 * <p>There are two types of encode methods:
 * <ul>
 * <li>{@code "encodeXyz"} -- these encode a specific URI component (e.g. path,
 * query) by percent encoding illegal characters, which includes non-US-ASCII
 * characters, and also characters that are otherwise illegal within the given
 * URI component type, as defined in RFC 3986. The effect of this method, with
 * regards to encoding, is comparable to using the multi-argument constructor
 * of {@link URI}.
 * <li>{@code "encode"} and {@code "encodeUriVariables"} -- these can be used
 * to encode URI variable values by percent encoding all characters that are
 * either illegal, or have any reserved meaning, anywhere within a URI.
 * </ul>
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 3.0
 * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986</a>
 */
/**
 * 基于RFC 3986的URI编码和解码的实用方法。 
 * <p>有两种类型的编码方法：<ul> <li> {<@code>"encodeXyz"}-这些编码特定的URI组件（例如，路径，查询）的百分比，对非法字符（包括非US-ASCII字符）以及在给定URI组件类型内否则非法的字符进行编码，如RFC 3986所定义。 
 * 就编码而言，此方法的效果可比使用{@link  URI}的多参数构造函数。 
 *  <li> {<@code>"encode"}和{@code "encodeUriVariables"} –这些可以用于通过百分比编码在任何地方非法或具有任何保留含义的所有字符来编码URI变量值在URI中。 
 *  </ ul> @author  Arjen Poutsma @author  Juergen Hoeller @author  Rossen Stoyanchev @since 3.0 
 * @see  <a href ="https://www.ietf.org/rfc/rfc3986.txt"> RFC 3986 </a>
 */
public abstract class UriUtils {

	/**
	 * Encode the given URI scheme with the given encoding.
	 * @param scheme the scheme to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded scheme
	 */
	/**
	 * 使用给定的编码对给定的URI方案进行编码。 
	 *  
	 * @param 方案要编码的方案
	 * @param 编码字符编码以编码为
	 * @return 编码方案
	 */
	public static String encodeScheme(String scheme, String encoding) {
		return encode(scheme, encoding, HierarchicalUriComponents.Type.SCHEME);
	}

	/**
	 * Encode the given URI scheme with the given encoding.
	 * @param scheme the scheme to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded scheme
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI方案进行编码。 
	 *  
	 * @param 方案要编码的方案
	 * @param 将字符编码字符集编码为
	 * @return 编码方案@5.0起
	 */
	public static String encodeScheme(String scheme, Charset charset) {
		return encode(scheme, charset, HierarchicalUriComponents.Type.SCHEME);
	}

	/**
	 * Encode the given URI authority with the given encoding.
	 * @param authority the authority to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded authority
	 */
	/**
	 * 使用给定的编码对给定的URI授权进行编码。 
	 *  
	 * @param 权限要编码的权限
	 * @param 编码字符编码以
	 * @return 编码的权限
	 */
	public static String encodeAuthority(String authority, String encoding) {
		return encode(authority, encoding, HierarchicalUriComponents.Type.AUTHORITY);
	}

	/**
	 * Encode the given URI authority with the given encoding.
	 * @param authority the authority to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded authority
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI授权进行编码。 
	 *  
	 * @param 权限要编码的权限
	 * @param 字符集将字符编码编码为
	 * @return 编码的权限@5.0起
	 */
	public static String encodeAuthority(String authority, Charset charset) {
		return encode(authority, charset, HierarchicalUriComponents.Type.AUTHORITY);
	}

	/**
	 * Encode the given URI user info with the given encoding.
	 * @param userInfo the user info to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded user info
	 */
	/**
	 * 使用给定的编码对给定的URI用户信息进行编码。 
	 *  
	 * @param  userInfo要编码的用户信息
	 * @param 编码字符编码以
	 * @return 编码的用户信息
	 */
	public static String encodeUserInfo(String userInfo, String encoding) {
		return encode(userInfo, encoding, HierarchicalUriComponents.Type.USER_INFO);
	}

	/**
	 * Encode the given URI user info with the given encoding.
	 * @param userInfo the user info to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded user info
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI用户信息进行编码。 
	 *  
	 * @param  userInfo要编码的用户信息
	 * @param 将字符编码设置为要编码，以
	 * @return 编码的用户信息@5.0起
	 */
	public static String encodeUserInfo(String userInfo, Charset charset) {
		return encode(userInfo, charset, HierarchicalUriComponents.Type.USER_INFO);
	}

	/**
	 * Encode the given URI host with the given encoding.
	 * @param host the host to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded host
	 */
	/**
	 * 使用给定的编码对给定的URI主机进行编码。 
	 *  
	 * @param 托管要编码的主机
	 * @param 编码字符编码，以编码为
	 * @return 编码的主机
	 */
	public static String encodeHost(String host, String encoding) {
		return encode(host, encoding, HierarchicalUriComponents.Type.HOST_IPV4);
	}

	/**
	 * Encode the given URI host with the given encoding.
	 * @param host the host to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded host
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI主机进行编码。 
	 *  
	 * @param 托管要编码的主机
	 * @param 字符集将字符编码编码为
	 * @return 编码的主机@从5.0开始
	 */
	public static String encodeHost(String host, Charset charset) {
		return encode(host, charset, HierarchicalUriComponents.Type.HOST_IPV4);
	}

	/**
	 * Encode the given URI port with the given encoding.
	 * @param port the port to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded port
	 */
	/**
	 * 使用给定的编码对给定的URI端口进行编码。 
	 *  
	 * @param 端口要编码的端口
	 * @param 编码字符编码以
	 * @return 编码端口
	 */
	public static String encodePort(String port, String encoding) {
		return encode(port, encoding, HierarchicalUriComponents.Type.PORT);
	}

	/**
	 * Encode the given URI port with the given encoding.
	 * @param port the port to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded port
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI端口进行编码。 
	 *  
	 * @param 端口要编码的端口
	 * @param 字符集将字符编码编码为
	 * @return 编码端口@从5.0开始
	 */
	public static String encodePort(String port, Charset charset) {
		return encode(port, charset, HierarchicalUriComponents.Type.PORT);
	}

	/**
	 * Encode the given URI path with the given encoding.
	 * @param path the path to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded path
	 */
	/**
	 * 使用给定的编码对给定的URI路径进行编码。 
	 *  
	 * @param 路径要编码的路径
	 * @param 编码字符编码以
	 * @return 编码路径
	 */
	public static String encodePath(String path, String encoding) {
		return encode(path, encoding, HierarchicalUriComponents.Type.PATH);
	}

	/**
	 * Encode the given URI path with the given encoding.
	 * @param path the path to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded path
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI路径进行编码。 
	 *  
	 * @param 路径要编码的路径
	 * @param 设置要编码的字符编码，以
	 * @return 从5.0开始的编码路径
	 */
	public static String encodePath(String path, Charset charset) {
		return encode(path, charset, HierarchicalUriComponents.Type.PATH);
	}

	/**
	 * Encode the given URI path segment with the given encoding.
	 * @param segment the segment to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded segment
	 */
	/**
	 * 使用给定的编码对给定的URI路径段进行编码。 
	 *  
	 * @param 分段要编码的分段
	 * @param 编码字符编码以编码为
	 * @return 编码的分段
	 */
	public static String encodePathSegment(String segment, String encoding) {
		return encode(segment, encoding, HierarchicalUriComponents.Type.PATH_SEGMENT);
	}

	/**
	 * Encode the given URI path segment with the given encoding.
	 * @param segment the segment to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded segment
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI路径段进行编码。 
	 *  
	 * @param 分割要编码的段
	 * @param 字符集将字符编码编码为
	 * @return 编码的段@5.0起
	 */
	public static String encodePathSegment(String segment, Charset charset) {
		return encode(segment, charset, HierarchicalUriComponents.Type.PATH_SEGMENT);
	}

	/**
	 * Encode the given URI query with the given encoding.
	 * @param query the query to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded query
	 */
	/**
	 * 使用给定的编码对给定的URI查询进行编码。 
	 *  
	 * @param 查询要编码的查询
	 * @param 编码字符编码以
	 * @return 编码的查询
	 */
	public static String encodeQuery(String query, String encoding) {
		return encode(query, encoding, HierarchicalUriComponents.Type.QUERY);
	}

	/**
	 * Encode the given URI query with the given encoding.
	 * @param query the query to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded query
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI查询进行编码。 
	 *  
	 * @param 查询要编码的查询
	 * @param 字符集将字符编码编码为
	 * @return 编码查询@since 5.0
	 */
	public static String encodeQuery(String query, Charset charset) {
		return encode(query, charset, HierarchicalUriComponents.Type.QUERY);
	}

	/**
	 * Encode the given URI query parameter with the given encoding.
	 * @param queryParam the query parameter to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded query parameter
	 */
	/**
	 * 使用给定的编码对给定的URI查询参数进行编码。 
	 *  
	 * @param  queryParam要编码的查询参数
	 * @param 编码字符编码以
	 * @return 编码的查询参数
	 */
	public static String encodeQueryParam(String queryParam, String encoding) {
		return encode(queryParam, encoding, HierarchicalUriComponents.Type.QUERY_PARAM);
	}

	/**
	 * Encode the given URI query parameter with the given encoding.
	 * @param queryParam the query parameter to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded query parameter
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI查询参数进行编码。 
	 *  
	 * @param  queryParam要编码的查询参数
	 * @param 设置要编码的字符编码，以
	 * @return 从5.0开始的已编码查询参数
	 */
	public static String encodeQueryParam(String queryParam, Charset charset) {
		return encode(queryParam, charset, HierarchicalUriComponents.Type.QUERY_PARAM);
	}

	/**
	 * Encode the query parameters from the given {@code MultiValueMap} with UTF-8.
	 * <p>This can be used with {@link UriComponentsBuilder#queryParams(MultiValueMap)}
	 * when building a URI from an already encoded template.
	 * <pre class="code">
	 * MultiValueMap&lt;String, String&gt; params = new LinkedMultiValueMap<>(2);
	 * // add to params...
	 *
	 * ServletUriComponentsBuilder.fromCurrentRequest()
	 *         .queryParams(UriUtils.encodeQueryParams(params))
	 *         .build(true)
	 *         .toUriString();
	 * </pre>
	 * @param params the parameters to encode
	 * @return a new {@code MultiValueMap} with the encoded names and values
	 * @since 5.2.3
	 */
	/**
	 * 使用UTF-8对给定的{@code  MultiValueMap}中的查询参数进行编码。 
	 *  <p>从已编码的模板构建URI时，可以与{@link  UriComponentsBuilder＃queryParams（MultiValueMap）}一起使用。 
	 *  <pre class ="code"> MultiValueMap <String，String>参数=新的LinkedMultiValueMap <>（2）; //添加到参数中... ServletUriComponentsBuilder.fromCurrentRequest（）.queryParams（UriUtils.encodeQueryParams（params））.build（true）.toUriString（）; </ pre> 
	 * @param 参数化参数以使用编码的名称和值从5.2.3开始对新的{@code  MultiValueMap}进行编码。 
	 * 
	 */
	public static MultiValueMap<String, String> encodeQueryParams(MultiValueMap<String, String> params) {
		Charset charset = StandardCharsets.UTF_8;
		MultiValueMap<String, String> result = new LinkedMultiValueMap<>(params.size());
		for (Map.Entry<String, List<String>> entry : params.entrySet()) {
			for (String value : entry.getValue()) {
				result.add(encodeQueryParam(entry.getKey(), charset), encodeQueryParam(value, charset));
			}
		}
		return result;
	}

	/**
	 * Encode the given URI fragment with the given encoding.
	 * @param fragment the fragment to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded fragment
	 */
	/**
	 * 使用给定的编码对给定的URI片段进行编码。 
	 *  
	 * @param 片段要编码的片段
	 * @param 编码字符编码，以编码为
	 * @return 编码的片段
	 */
	public static String encodeFragment(String fragment, String encoding) {
		return encode(fragment, encoding, HierarchicalUriComponents.Type.FRAGMENT);
	}

	/**
	 * Encode the given URI fragment with the given encoding.
	 * @param fragment the fragment to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded fragment
	 * @since 5.0
	 */
	/**
	 * 使用给定的编码对给定的URI片段进行编码。 
	 *  
	 * @param 片段要编码的片段
	 * @param 字符集将字符编码编码为
	 * @return 编码的片段@从5.0开始
	 */
	public static String encodeFragment(String fragment, Charset charset) {
		return encode(fragment, charset, HierarchicalUriComponents.Type.FRAGMENT);
	}


	/**
	 * Variant of {@link #encode(String, Charset)} with a String charset.
	 * @param source the String to be encoded
	 * @param encoding the character encoding to encode to
	 * @return the encoded String
	 */
	/**
	 * {@link  #encode（String，Charset）}与String字符集的变体。 
	 *  
	 * @param 源要编码的字符串
	 * @param 编码字符编码以编码为
	 * @return 编码的字符串
	 */
	public static String encode(String source, String encoding) {
		return encode(source, encoding, HierarchicalUriComponents.Type.URI);
	}

	/**
	 * Encode all characters that are either illegal, or have any reserved
	 * meaning, anywhere within a URI, as defined in
	 * <a href="https://tools.ietf.org/html/rfc3986">RFC 3986</a>.
	 * This is useful to ensure that the given String will be preserved as-is
	 * and will not have any o impact on the structure or meaning of the URI.
	 * @param source the String to be encoded
	 * @param charset the character encoding to encode to
	 * @return the encoded String
	 * @since 5.0
	 */
	/**
	 * 按照<a href="https://tools.ietf.org/html/rfc3986"> RFC 3986 </a>中的定义，在URI中的任何位置对所有非法字符或具有保留含义的所有字符进行编码。 
	 * 这对于确保将给定的String保留为原样并且对URI的结构或含义没有任何影响非常有用。 
	 *  
	 * @param 源要编码的字符串
	 * @param 字符集将字符编码编码为
	 * @return 编码的字符串@since 5.0
	 */
	public static String encode(String source, Charset charset) {
		return encode(source, charset, HierarchicalUriComponents.Type.URI);
	}

	/**
	 * Convenience method to apply {@link #encode(String, Charset)} to all
	 * given URI variable values.
	 * @param uriVariables the URI variable values to be encoded
	 * @return the encoded String
	 * @since 5.0
	 */
	/**
	 * 将{@link  #encode（String，Charset）}应用于所有给定URI变量值的便捷方法。 
	 *  
	 * @param  uri对要编码的URI变量值进行变量设置
	 * @return 编码的字符串@5.0起
	 */
	public static Map<String, String> encodeUriVariables(Map<String, ?> uriVariables) {
		Map<String, String> result = new LinkedHashMap<>(uriVariables.size());
		uriVariables.forEach((key, value) -> {
			String stringValue = (value != null ? value.toString() : "");
			result.put(key, encode(stringValue, StandardCharsets.UTF_8));
		});
		return result;
	}

	/**
	 * Convenience method to apply {@link #encode(String, Charset)} to all
	 * given URI variable values.
	 * @param uriVariables the URI variable values to be encoded
	 * @return the encoded String
	 * @since 5.0
	 */
	/**
	 * 将{@link  #encode（String，Charset）}应用于所有给定URI变量值的便捷方法。 
	 *  
	 * @param  uri对要编码的URI变量值进行变量设置
	 * @return 编码的字符串@5.0起
	 */
	public static Object[] encodeUriVariables(Object... uriVariables) {
		return Arrays.stream(uriVariables)
				.map(value -> {
					String stringValue = (value != null ? value.toString() : "");
					return encode(stringValue, StandardCharsets.UTF_8);
				})
				.toArray();
	}

	private static String encode(String scheme, String encoding, HierarchicalUriComponents.Type type) {
		return HierarchicalUriComponents.encodeUriComponent(scheme, encoding, type);
	}

	private static String encode(String scheme, Charset charset, HierarchicalUriComponents.Type type) {
		return HierarchicalUriComponents.encodeUriComponent(scheme, charset, type);
	}


	/**
	 * Decode the given encoded URI component.
	 * <p>See {@link StringUtils#uriDecode(String, Charset)} for the decoding rules.
	 * @param source the encoded String
	 * @param encoding the character encoding to use
	 * @return the decoded value
	 * @throws IllegalArgumentException when the given source contains invalid encoded sequences
	 * @see StringUtils#uriDecode(String, Charset)
	 * @see java.net.URLDecoder#decode(String, String)
	 */
	/**
	 * 解码给定的已编码URI组件。 
	 *  <p>有关解码规则，请参见{@link  StringUtils＃uriDecode（String，Charset）}。 
	 *  
	 * @param 源编码字符串
	 * @param 编码字符编码以使用
	 * @return 解码值
	 * @throws  IllegalArgumentException当给定源包含无效编码序列时
	 * @see  StringUtils＃uriDecode（String，Charset） 
	 * @see  java.net.URLDecoder＃decode（字符串，字符串）
	 */
	public static String decode(String source, String encoding) {
		return StringUtils.uriDecode(source, Charset.forName(encoding));
	}

	/**
	 * Decode the given encoded URI component.
	 * <p>See {@link StringUtils#uriDecode(String, Charset)} for the decoding rules.
	 * @param source the encoded String
	 * @param charset the character encoding to use
	 * @return the decoded value
	 * @throws IllegalArgumentException when the given source contains invalid encoded sequences
	 * @since 5.0
	 * @see StringUtils#uriDecode(String, Charset)
	 * @see java.net.URLDecoder#decode(String, String)
	 */
	/**
	 * 解码给定的已编码URI组件。 
	 *  <p>有关解码规则，请参见{@link  StringUtils＃uriDecode（String，Charset）}。 
	 *  
	 * @param 源已编码的字符串
	 * @param 设置字符编码以使用
	 * @return 解码值
	 * @throws  IllegalArgumentException，当给定源包含无效的编码序列时（自5.0开始）
	 * @see  StringUtils＃uriDecode（String ，Charset）
	 * @see  java.net.URLDecoder＃decode（String，String）
	 */
	public static String decode(String source, Charset charset) {
		return StringUtils.uriDecode(source, charset);
	}

	/**
	 * Extract the file extension from the given URI path.
	 * @param path the URI path (e.g. "/products/index.html")
	 * @return the extracted file extension (e.g. "html")
	 * @since 4.3.2
	 */
	/**
	 * 从给定的URI路径中提取文件扩展名。 
	 *  
	 * @param 路径URI路径（例如"/products/index.html"）
	 * @return 提取的文件扩展名（例如"html"）@4.3.2起
	 */
	@Nullable
	public static String extractFileExtension(String path) {
		int end = path.indexOf('?');
		int fragmentIndex = path.indexOf('#');
		if (fragmentIndex != -1 && (end == -1 || fragmentIndex < end)) {
			end = fragmentIndex;
		}
		if (end == -1) {
			end = path.length();
		}
		int begin = path.lastIndexOf('/', end) + 1;
		int paramIndex = path.indexOf(';', begin);
		end = (paramIndex != -1 && paramIndex < end ? paramIndex : end);
		int extIndex = path.lastIndexOf('.', end);
		if (extIndex != -1 && extIndex > begin) {
			return path.substring(extIndex + 1, end);
		}
		return null;
	}

}
