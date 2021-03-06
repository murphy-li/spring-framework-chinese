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

package org.springframework.cache.jcache.interceptor;

import java.util.Collection;
import java.util.Collections;

import javax.cache.annotation.CacheInvocationContext;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.jcache.JCacheCache;
import org.springframework.util.Assert;

/**
 * Spring's {@link CacheResolver} implementation that delegates to a standard
 * JSR-107 {@link javax.cache.annotation.CacheResolver}.
 * <p>Used internally to invoke user-based JSR-107 cache resolvers.
 *
 * @author Stephane Nicoll
 * @since 4.1
 */
/**
 * Spring的{@link  CacheResolver}实现，委托给标准JSR-107 {@link  javax.cache.annotation.CacheResolver}。 
 *  <p>在内部用于调用基于用户的JSR-107缓存解析器。 
 *  @author 史蒂芬·尼科尔@since 4.1
 */
class CacheResolverAdapter implements CacheResolver {

	private final javax.cache.annotation.CacheResolver target;


	/**
	 * Create a new instance with the JSR-107 cache resolver to invoke.
	 */
	/**
	 * 使用要调用的JSR-107缓存解析器创建一个新实例。 
	 * 
	 */
	public CacheResolverAdapter(javax.cache.annotation.CacheResolver target) {
		Assert.notNull(target, "JSR-107 CacheResolver is required");
		this.target = target;
	}


	/**
	 * Return the underlying {@link javax.cache.annotation.CacheResolver}
	 * that this instance is using.
	 */
	/**
	 * 返回此实例正在使用的基础{@link  javax.cache.annotation.CacheResolver}。 
	 * 
	 */
	protected javax.cache.annotation.CacheResolver getTarget() {
		return this.target;
	}

	@Override
	public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
		if (!(context instanceof CacheInvocationContext<?>)) {
			throw new IllegalStateException("Unexpected context " + context);
		}
		CacheInvocationContext<?> cacheInvocationContext = (CacheInvocationContext<?>) context;
		javax.cache.Cache<Object, Object> cache = this.target.resolveCache(cacheInvocationContext);
		if (cache == null) {
			throw new IllegalStateException("Could not resolve cache for " + context + " using " + this.target);
		}
		return Collections.singleton(new JCacheCache(cache));
	}

}
