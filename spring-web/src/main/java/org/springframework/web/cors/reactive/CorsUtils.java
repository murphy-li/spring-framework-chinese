/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.cors.reactive;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for CORS reactive request handling based on the
 * <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * 基于<a href="https://www.w3.org/TR/cors/"> CORS W3C建议</a>的CORS反应性请求处理的实用程序类。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
public abstract class CorsUtils {

	/**
	 * Returns {@code true} if the request is a valid CORS one by checking {@code Origin}
	 * header presence and ensuring that origins are different via {@link #isSameOrigin}.
	 */
	/**
	 * 如果请求是有效的CORS，则返回{@code  true}，方法是检查{@code  Origin}头的存在，并通过{@link  #isSameOrigin}确保来源不同。 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static boolean isCorsRequest(ServerHttpRequest request) {
		return request.getHeaders().containsKey(HttpHeaders.ORIGIN) && !isSameOrigin(request);
	}

	/**
	 * Returns {@code true} if the request is a valid CORS pre-flight one by checking {code OPTIONS} method with
	 * {@code Origin} and {@code Access-Control-Request-Method} headers presence.
	 */
	/**
	 * 通过使用{@code  Origin}和{@code  Access-Control-Request-Method}标头检查{code OPTIONS}方法，如果请求是有效的CORS飞行前请求，则返回{@code  true} 。 
	 * 
	 */
	public static boolean isPreFlightRequest(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		return (request.getMethod() == HttpMethod.OPTIONS
				&& headers.containsKey(HttpHeaders.ORIGIN)
				&& headers.containsKey(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
	}

	/**
	 * Check if the request is a same-origin one, based on {@code Origin}, and
	 * {@code Host} headers.
	 *
	 * <p><strong>Note:</strong> as of 5.1 this method ignores
	 * {@code "Forwarded"} and {@code "X-Forwarded-*"} headers that specify the
	 * client-originated address. Consider using the {@code ForwardedHeaderFilter}
	 * to extract and use, or to discard such headers.
	 *
	 * @return {@code true} if the request is a same-origin one, {@code false} in case
	 * of a cross-origin request
	 * @deprecated as of 5.2, same-origin checks are performed directly by {@link #isCorsRequest}
	 */
	/**
	 * 根据{@code  Origin}和{@code  Host}标头，检查请求是否是相同来源的请求。 
	 *  <p> <strong>注意</ strong>：从5.1版本开始，此方法将忽略{@code "Forwarded"}和{@code "X-Forwarded-"}标头，这些标头指定了客户端起源的地址。 
	 * 考虑使用{@code  ForwardedHeaderFilter}提取和使用或丢弃此类标头。 
	 *  
	 * @return  {@code  true}（如果请求是相同来源的请求，{<@code> false}）（如果从5.2开始不推荐使用跨域请求，则直接通过以下方式执行同源检查）： {@link  #isCorsRequest}
	 */
	@Deprecated
	public static boolean isSameOrigin(ServerHttpRequest request) {
		String origin = request.getHeaders().getOrigin();
		if (origin == null) {
			return true;
		}

		URI uri = request.getURI();
		String actualScheme = uri.getScheme();
		String actualHost = uri.getHost();
		int actualPort = getPort(uri.getScheme(), uri.getPort());
		Assert.notNull(actualScheme, "Actual request scheme must not be null");
		Assert.notNull(actualHost, "Actual request host must not be null");
		Assert.isTrue(actualPort != -1, "Actual request port must not be undefined");

		UriComponents originUrl = UriComponentsBuilder.fromOriginHeader(origin).build();
		return (actualScheme.equals(originUrl.getScheme()) &&
				actualHost.equals(originUrl.getHost()) &&
				actualPort == getPort(originUrl.getScheme(), originUrl.getPort()));
	}

	private static int getPort(@Nullable String scheme, int port) {
		if (port == -1) {
			if ("http".equals(scheme) || "ws".equals(scheme)) {
				port = 80;
			}
			else if ("https".equals(scheme) || "wss".equals(scheme)) {
				port = 443;
			}
		}
		return port;
	}

}
