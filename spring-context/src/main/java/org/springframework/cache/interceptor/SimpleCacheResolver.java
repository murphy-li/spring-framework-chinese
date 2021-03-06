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

package org.springframework.cache.interceptor;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

/**
 * A simple {@link CacheResolver} that resolves the {@link Cache} instance(s)
 * based on a configurable {@link CacheManager} and the name of the
 * cache(s) as provided by {@link BasicOperation#getCacheNames() getCacheNames()}.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.1
 * @see BasicOperation#getCacheNames()
 */
/**
 * 一个简单的{@link  CacheResolver}，它根据可配置的{@link  CacheManager}和{<@link提供的缓存名称，来解析{@link  Cache}实例。 
 *  > BasicOperation＃getCacheNames（）getCacheNames（）}。 
 *  @author  Stephane Nicoll @author 于尔根·霍勒（Juergen Hoeller）@自4.1起
 * @see  BasicOperation＃getCacheNames（）
 */
public class SimpleCacheResolver extends AbstractCacheResolver {

	/**
	 * Construct a new {@code SimpleCacheResolver}.
	 * @see #setCacheManager
	 */
	/**
	 * 构造一个新的{@code  SimpleCacheResolver}。 
	 *  
	 * @see  #setCacheManager
	 */
	public SimpleCacheResolver() {
	}

	/**
	 * Construct a new {@code SimpleCacheResolver} for the given {@link CacheManager}.
	 * @param cacheManager the CacheManager to use
	 */
	/**
	 * 为给定的{@link  CacheManager}构造一个新的{@code  SimpleCacheResolver}。 
	 *  
	 * @param  cacheManager要使用的CacheManager
	 */
	public SimpleCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}


	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		return context.getOperation().getCacheNames();
	}


	/**
	 * Return a {@code SimpleCacheResolver} for the given {@link CacheManager}.
	 * @param cacheManager the CacheManager (potentially {@code null})
	 * @return the SimpleCacheResolver ({@code null} if the CacheManager was {@code null})
	 * @since 5.1
	 */
	/**
	 * 为给定的{@link  CacheManager}返回一个{@code  SimpleCacheResolver}。 
	 *  
	 * @param  cacheManager CacheManager（可能为{@code  null}）
	 * @return  SimpleCacheResolver（如果CacheManager为{@code  null}，则为{@code  null}）@5.1
	 */
	@Nullable
	static SimpleCacheResolver of(@Nullable CacheManager cacheManager) {
		return (cacheManager != null ? new SimpleCacheResolver(cacheManager) : null);
	}

}
