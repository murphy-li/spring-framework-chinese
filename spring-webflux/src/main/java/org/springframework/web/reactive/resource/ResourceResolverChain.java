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

package org.springframework.web.reactive.resource;

import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;

/**
 * A contract for invoking a chain of {@link ResourceResolver ResourceResolvers} where each resolver
 * is given a reference to the chain allowing it to delegate when necessary.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 用于调用{@link  ResourceResolver ResourceResolvers}链的合同，其中为每个解析器提供对该链的引用，以便在必要时进行委托。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public interface ResourceResolverChain {

	/**
	 * Resolve the supplied request and request path to a {@link Resource} that
	 * exists under one of the given resource locations.
	 * @param exchange the current exchange
	 * @param requestPath the portion of the request path to use
	 * @param locations the locations to search in when looking up resources
	 * @return the resolved resource; or an empty {@code Mono} if unresolved
	 */
	/**
	 * 将提供的请求和请求路径解析为给定资源位置之一下存在的{@link  Resource}。 
	 *  
	 * @param 交换当前交换
	 * @param  requestPath请求路径的一部分以使用
	 * @param 位置查找资源时查找的位置
	 * @return 解析的资源； 
	 * 或空的{@code  Mono}（如果未解决）
	 */
	Mono<Resource> resolveResource(@Nullable ServerWebExchange exchange, String requestPath,
			List<? extends Resource> locations);

	/**
	 * Resolve the externally facing <em>public</em> URL path for clients to use
	 * to access the resource that is located at the given <em>internal</em>
	 * resource path.
	 * <p>This is useful when rendering URL links to clients.
	 * @param resourcePath the internal resource path
	 * @param locations the locations to search in when looking up resources
	 * @return the resolved public URL path; or an empty {@code Mono} if unresolved
	 */
	/**
	 * 解析面向外部的<em> public </ em> URL路径，供客户端用来访问位于给定的<em> internal </ em>资源路径中的资源。 
	 *  <p>当呈现指向客户端的URL链接时，这很有用。 
	 *  
	 * @param  resourcePath内部资源路径
	 * @param 定位查找资源时查找的位置
	 * @return 解析的公共URL路径； 
	 * 或空的{@code  Mono}（如果未解决）
	 */
	Mono<String> resolveUrlPath(String resourcePath, List<? extends Resource> locations);

}
