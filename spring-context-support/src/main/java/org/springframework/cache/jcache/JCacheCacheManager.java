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

package org.springframework.cache.jcache;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.cache.CacheManager;
import javax.cache.Caching;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.cache.CacheManager} implementation
 * backed by a JCache {@link CacheManager javax.cache.CacheManager}.
 *
 * <p>Note: This class has been updated for JCache 1.0, as of Spring 4.0.
 *
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.2
 */
/**
 * 由JCache {@link  CacheManager javax.cache.CacheManager}支持的{@link  org.springframework.cache.CacheManager}实现。 
 *  <p>注意：此类从Spring 4.0开始已针对JCache 1.0更新。 
 *  @author  Juergen Hoeller @author 斯蒂芬·尼科尔（Stephane Nicoll）@3.2起
 */
public class JCacheCacheManager extends AbstractTransactionSupportingCacheManager {

	@Nullable
	private CacheManager cacheManager;

	private boolean allowNullValues = true;


	/**
	 * Create a new {@code JCacheCacheManager} without a backing JCache
	 * {@link CacheManager javax.cache.CacheManager}.
	 * <p>The backing JCache {@code javax.cache.CacheManager} can be set via the
	 * {@link #setCacheManager} bean property.
	 */
	/**
	 * 创建没有支持JCache {@link  CacheManager javax.cache.CacheManager}的新{@code  JCacheCacheManager}。 
	 *  <p>可以通过{@link  #setCacheManager} bean属性来设置支持JCache {@code  javax.cache.CacheManager}。 
	 * 
	 */
	public JCacheCacheManager() {
	}

	/**
	 * Create a new {@code JCacheCacheManager} for the given backing JCache
	 * {@link CacheManager javax.cache.CacheManager}.
	 * @param cacheManager the backing JCache {@code javax.cache.CacheManager}
	 */
	/**
	 * 为给定的支持JCache {@link  CacheManager javax.cache.CacheManager}创建一个新的{@code  JCacheCacheManager}。 
	 *  
	 * @param  cacheManager支持JCache {@code  javax.cache.CacheManager}
	 */
	public JCacheCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}


	/**
	 * Set the backing JCache {@link CacheManager javax.cache.CacheManager}.
	 */
	/**
	 * 设置支持JCache {@link  CacheManager javax.cache.CacheManager}。 
	 * 
	 */
	public void setCacheManager(@Nullable CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * Return the backing JCache {@link CacheManager javax.cache.CacheManager}.
	 */
	/**
	 * 返回支持的JCache {@link  CacheManager javax.cache.CacheManager}。 
	 * 
	 */
	@Nullable
	public CacheManager getCacheManager() {
		return this.cacheManager;
	}

	/**
	 * Specify whether to accept and convert {@code null} values for all caches
	 * in this cache manager.
	 * <p>Default is "true", despite JSR-107 itself not supporting {@code null} values.
	 * An internal holder object will be used to store user-level {@code null}s.
	 */
	/**
	 * 指定是否为此缓存管理器中的所有缓存接受并转换{@code  null}值。 
	 *  <p>尽管JSR-107本身不支持{@code  null}值，但默认值为"true"。 
	 * 内部持有人对象将用于存储用户级别的{@code  null}。 
	 * 
	 */
	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	/**
	 * Return whether this cache manager accepts and converts {@code null} values
	 * for all of its caches.
	 */
	/**
	 * 返回此缓存管理器是否为其所有缓存接受并转换{@code  null}值。 
	 * 
	 */
	public boolean isAllowNullValues() {
		return this.allowNullValues;
	}

	@Override
	public void afterPropertiesSet() {
		if (getCacheManager() == null) {
			setCacheManager(Caching.getCachingProvider().getCacheManager());
		}
		super.afterPropertiesSet();
	}


	@Override
	protected Collection<Cache> loadCaches() {
		CacheManager cacheManager = getCacheManager();
		Assert.state(cacheManager != null, "No CacheManager set");

		Collection<Cache> caches = new LinkedHashSet<>();
		for (String cacheName : cacheManager.getCacheNames()) {
			javax.cache.Cache<Object, Object> jcache = cacheManager.getCache(cacheName);
			caches.add(new JCacheCache(jcache, isAllowNullValues()));
		}
		return caches;
	}

	@Override
	protected Cache getMissingCache(String name) {
		CacheManager cacheManager = getCacheManager();
		Assert.state(cacheManager != null, "No CacheManager set");

		// Check the JCache cache again (in case the cache was added at runtime)
		javax.cache.Cache<Object, Object> jcache = cacheManager.getCache(name);
		if (jcache != null) {
			return new JCacheCache(jcache, isAllowNullValues());
		}
		return null;
	}

}
