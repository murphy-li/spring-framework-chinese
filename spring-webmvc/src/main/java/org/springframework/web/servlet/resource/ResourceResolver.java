/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * A strategy for resolving a request to a server-side resource.
 *
 * <p>Provides mechanisms for resolving an incoming request to an actual
 * {@link org.springframework.core.io.Resource} and for obtaining the
 * public URL path that clients should use when requesting the resource.
 *
 * @author Jeremy Grelle
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 4.1
 * @see org.springframework.web.servlet.resource.ResourceResolverChain
 */
/**
 * 解决对服务器端资源的请求的策略。 
 *  <p>提供了用于解决对实际{@link  org.springframework.core.io.Resource}的传入请求以及获取客户端在请求资源时应使用的公共URL路径的机制。 
 *  @author  Jeremy Grelle @author  Rossen Stoyanchev @author  Sam Brannen @始于4.1 
 * @see  org.springframework.web.servlet.resource.ResourceResolverChain
 */
public interface ResourceResolver {

	/**
	 * Resolve the supplied request and request path to a {@link Resource} that
	 * exists under one of the given resource locations.
	 * @param request the current request (may not be present in some calls)
	 * @param requestPath the portion of the request path to use
	 * @param locations the locations to search in when looking up resources
	 * @param chain the chain of remaining resolvers to delegate to
	 * @return the resolved resource, or {@code null} if unresolved
	 */
	/**
	 * 将提供的请求和请求路径解析为给定资源位置之一下存在的{@link  Resource}。 
	 *  
	 * @param 请求当前请求（某些调用中可能不存在）
	 * @param  requestPath请求路径的一部分，以使用
	 * @param 位置查找资源时查找的位置
	 * @param 链其余解析程序的链，以委托给
	 * @return 已解析的资源； 
	 * 如果未解析，则为{@code  null}
	 */
	@Nullable
	Resource resolveResource(@Nullable HttpServletRequest request, String requestPath,
			List<? extends Resource> locations, ResourceResolverChain chain);

	/**
	 * Resolve the externally facing <em>public</em> URL path for clients to use
	 * to access the resource that is located at the given <em>internal</em>
	 * resource path.
	 * <p>This is useful when rendering URL links to clients.
	 * @param resourcePath the internal resource path
	 * @param locations the locations to search in when looking up resources
	 * @param chain the chain of resolvers to delegate to
	 * @return the resolved public URL path, or {@code null} if unresolved
	 */
	/**
	 * 解析面向外部的<em> public </ em> URL路径，供客户端用来访问位于给定的<em> internal </ em>资源路径中的资源。 
	 *  <p>当呈现指向客户端的URL链接时，这很有用。 
	 *  
	 * @param  resourcePath内部资源路径
	 * @param 定位查找资源时要搜索的位置
	 * @param 链接解析程序链以委托给
	 * @return 解析的公共URL路径，或{<@code > null}（如果未解决）
	 */
	@Nullable
	String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain);

}
