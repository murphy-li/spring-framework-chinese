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

package org.springframework.web.filter.reactive;

import java.util.Optional;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Inserts an attribute in the Reactor {@link Context} that makes the current
 * {@link ServerWebExchange} available under the attribute name
 * {@link #EXCHANGE_CONTEXT_ATTRIBUTE}. This is useful for access to the
 * exchange without explicitly passing it to components that participate in
 * request processing.
 *
 * <p>The convenience method {@link #get(Context)} looks up the exchange.
 *
 * @author Rossen Stoyanchev
 * @since 5.2
 */
/**
 * 在Reactor {@link  Context}中插入一个属性，该属性使当前{@link  ServerWebExchange}在属性名称{@link  #EXCHANGE_CONTEXT_ATTRIBUTE}下可用。 
 * 这对于访问交换而不将其显式传递给参与请求处理的组件很有用。 
 *  <p>便捷方法{@link  #get（Context）}查找交换。 
 *  @author  Rossen Stoyanchev @从5.2开始
 */
public class ServerWebExchangeContextFilter implements WebFilter {

	/** Attribute name under which the exchange is saved in the context. */
	/**
	 * 在上下文中保存交换所使用的属性名称。 
	 * 
	 */
	public static final String EXCHANGE_CONTEXT_ATTRIBUTE =
			ServerWebExchangeContextFilter.class.getName() + ".EXCHANGE_CONTEXT";


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(exchange)
				.subscriberContext(cxt -> cxt.put(EXCHANGE_CONTEXT_ATTRIBUTE, exchange));
	}


	/**
	 * Access the {@link ServerWebExchange} from the Reactor Context, if available,
	 * which is if {@link ServerWebExchangeContextFilter} is configured for use
	 * and the give context was obtained from a request processing chain.
	 * @param context the context in which to access the exchange
	 * @return the exchange
	 */
	/**
	 * 如果可用，则从Reactor上下文访问{@link  ServerWebExchange}，这是如果配置了{@link  ServerWebExchangeContextFilter}并从请求处理链中获取了Give上下文。 
	 *  
	 * @param 上下文访问交换所的上下文<@r​​eturn>交换所
	 */
	public static Optional<ServerWebExchange> get(Context context) {
		return context.getOrEmpty(EXCHANGE_CONTEXT_ATTRIBUTE);
	}

}
