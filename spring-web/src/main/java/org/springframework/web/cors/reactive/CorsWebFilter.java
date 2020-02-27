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

package org.springframework.web.cors.reactive;

import reactor.core.publisher.Mono;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;


/**
 * {@link WebFilter} that handles CORS preflight requests and intercepts
 * CORS simple and actual requests thanks to a {@link CorsProcessor} implementation
 * ({@link DefaultCorsProcessor} by default) in order to add the relevant CORS
 * response headers (like {@code Access-Control-Allow-Origin}) using the provided
 * {@link CorsConfigurationSource} (for example an {@link UrlBasedCorsConfigurationSource}
 * instance.
 *
 * <p>This is an alternative to Spring WebFlux Java config CORS configuration,
 * mostly useful for applications using the functional API.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 */
/**
 * {{@link> WebFilter}处理{{@link> CorsProcessor}实现（默认为{@link  DefaultCorsProcessor}）来处理CORS预检请求并拦截CORS简单和实际请求，以便添加相关的CORS响应标头（例如{@code  Access-Control-Allow-Origin}）使用提供的{@link  CorsConfigurationSource}（例如{@link  UrlBasedCorsConfigurationSource}实例。 
 * ）<p>这是Spring WebFlux Java的替代方法config CORS配置，对于使用功能性API的应用程序最有用。 
 * @author  Sebastien Deleuze @since 5.0 
 * @see  <a href="https://www.w3.org/TR/cors/"> CORS W3C建议</a>
 */
public class CorsWebFilter implements WebFilter {

	private final CorsConfigurationSource configSource;

	private final CorsProcessor processor;


	/**
	 * Constructor accepting a {@link CorsConfigurationSource} used by the filter
	 * to find the {@link CorsConfiguration} to use for each incoming request.
	 * @see UrlBasedCorsConfigurationSource
	 */
	/**
	 * 构造函数接受过滤器使用的{@link  CorsConfigurationSource}来查找用于每个传入请求的{@link  CorsConfiguration}。 
	 *  
	 * @see  UrlBasedCorsConfigurationSource
	 */
	public CorsWebFilter(CorsConfigurationSource configSource) {
		this(configSource, new DefaultCorsProcessor());
	}

	/**
	 * Constructor accepting a {@link CorsConfigurationSource} used by the filter
	 * to find the {@link CorsConfiguration} to use for each incoming request and a
	 * custom {@link CorsProcessor} to use to apply the matched
	 * {@link CorsConfiguration} for a request.
	 * @see UrlBasedCorsConfigurationSource
	 */
	/**
	 * 构造函数接受过滤器使用的{@link  CorsConfigurationSource}查找用于每个传入请求的{@link  CorsConfiguration}和自定义的{@link  CorsProcessor}来应用匹配的{<@link > CorsConfiguration}。 
	 *  
	 * @see  UrlBasedCorsConfigurationSource
	 */
	public CorsWebFilter(CorsConfigurationSource configSource, CorsProcessor processor) {
		Assert.notNull(configSource, "CorsConfigurationSource must not be null");
		Assert.notNull(processor, "CorsProcessor must not be null");
		this.configSource = configSource;
		this.processor = processor;
	}


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(exchange);
		boolean isValid = this.processor.process(corsConfiguration, exchange);
		if (!isValid || CorsUtils.isPreFlightRequest(request)) {
			return Mono.empty();
		}
		return chain.filter(exchange);
	}

}
