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

package org.springframework.cache.support;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

/**
 * Abstract base class implementing the common {@link CacheManager} methods.
 * Useful for 'static' environments where the backing caches do not change.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.1
 */
/**
 * 实现常见的{@link  CacheManager}方法的抽象基类。 
 * 对于备用缓存不更改的"静态"环境很有用。 
 *  @author  Costin Leau @author 于尔根·霍勒（Juergen Hoeller）@author  Stephane Nicoll @since 3.1
 */
public abstract class AbstractCacheManager implements CacheManager, InitializingBean {

	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

	private volatile Set<String> cacheNames = Collections.emptySet();


	// Early cache initialization on startup

	@Override
	public void afterPropertiesSet() {
		initializeCaches();
	}

	/**
	 * Initialize the static configuration of caches.
	 * <p>Triggered on startup through {@link #afterPropertiesSet()};
	 * can also be called to re-initialize at runtime.
	 * @since 4.2.2
	 * @see #loadCaches()
	 */
	/**
	 * 初始化缓存的静态配置。 
	 *  <p>在启动时通过{@link  #afterPropertiesSet（）}触发； 
	 * 也可以在运行时调用以重新初始化。 
	 *  @since 4.2.2 
	 * @see  #loadCaches（）
	 */
	public void initializeCaches() {
		Collection<? extends Cache> caches = loadCaches();

		synchronized (this.cacheMap) {
			this.cacheNames = Collections.emptySet();
			this.cacheMap.clear();
			Set<String> cacheNames = new LinkedHashSet<>(caches.size());
			for (Cache cache : caches) {
				String name = cache.getName();
				this.cacheMap.put(name, decorateCache(cache));
				cacheNames.add(name);
			}
			this.cacheNames = Collections.unmodifiableSet(cacheNames);
		}
	}

	/**
	 * Load the initial caches for this cache manager.
	 * <p>Called by {@link #afterPropertiesSet()} on startup.
	 * The returned collection may be empty but must not be {@code null}.
	 */
	/**
	 * 加载此缓存管理器的初始缓存。 
	 *  <p>在启动时由{@link  #afterPropertiesSet（）}调用。 
	 * 返回的集合可以为空，但不能为{@code  null}。 
	 * 
	 */
	protected abstract Collection<? extends Cache> loadCaches();


	// Lazy cache initialization on access

	@Override
	@Nullable
	public Cache getCache(String name) {
		// Quick check for existing cache...
		Cache cache = this.cacheMap.get(name);
		if (cache != null) {
			return cache;
		}

		// The provider may support on-demand cache creation...
		Cache missingCache = getMissingCache(name);
		if (missingCache != null) {
			// Fully synchronize now for missing cache registration
			synchronized (this.cacheMap) {
				cache = this.cacheMap.get(name);
				if (cache == null) {
					cache = decorateCache(missingCache);
					this.cacheMap.put(name, cache);
					updateCacheNames(name);
				}
			}
		}
		return cache;
	}

	@Override
	public Collection<String> getCacheNames() {
		return this.cacheNames;
	}


	// Common cache initialization delegates for subclasses

	/**
	 * Check for a registered cache of the given name.
	 * In contrast to {@link #getCache(String)}, this method does not trigger
	 * the lazy creation of missing caches via {@link #getMissingCache(String)}.
	 * @param name the cache identifier (must not be {@code null})
	 * @return the associated Cache instance, or {@code null} if none found
	 * @since 4.1
	 * @see #getCache(String)
	 * @see #getMissingCache(String)
	 */
	/**
	 * 检查给定名称的已注册缓存。 
	 * 与{@link  #getCache（String）}相比，此方法不会通过{@link  #getMissingCache（String）}触发丢失缓存的延迟创建。 
	 *  
	 * @param 命名缓存标识符（不得为{@code  null}）
	 * @return 关联的缓存实例； 
	 * 如果未找到@，则为{@code  null} @自4.1起
	 * @see  #getCache（字符串）
	 * @see  #getMissingCache（String）
	 */
	@Nullable
	protected final Cache lookupCache(String name) {
		return this.cacheMap.get(name);
	}

	/**
	 * Dynamically register an additional Cache with this manager.
	 * @param cache the Cache to register
	 * @deprecated as of Spring 4.3, in favor of {@link #getMissingCache(String)}
	 */
	/**
	 * 在此管理器中动态注册其他缓存。 
	 *  
	 * @param 缓存从Spring 4.3开始不推荐使用的缓存，以注册{@@link> #getMissingCache（String）}
	 */
	@Deprecated
	protected final void addCache(Cache cache) {
		String name = cache.getName();
		synchronized (this.cacheMap) {
			if (this.cacheMap.put(name, decorateCache(cache)) == null) {
				updateCacheNames(name);
			}
		}
	}

	/**
	 * Update the exposed {@link #cacheNames} set with the given name.
	 * <p>This will always be called within a full {@link #cacheMap} lock
	 * and effectively behaves like a {@code CopyOnWriteArraySet} with
	 * preserved order but exposed as an unmodifiable reference.
	 * @param name the name of the cache to be added
	 */
	/**
	 * 用给定名称更新公开的{@link  #cacheNames}集。 
	 *  <p>这将始终在完整的{@link  #cacheMap}锁内调用，并有效地表现为具有保留顺序的{@code  CopyOnWriteArraySet}，但作为不可修改的引用公开。 
	 *  
	 * @param 名称要添加的缓存的名称
	 */
	private void updateCacheNames(String name) {
		Set<String> cacheNames = new LinkedHashSet<>(this.cacheNames.size() + 1);
		cacheNames.addAll(this.cacheNames);
		cacheNames.add(name);
		this.cacheNames = Collections.unmodifiableSet(cacheNames);
	}


	// Overridable template methods for cache initialization

	/**
	 * Decorate the given Cache object if necessary.
	 * @param cache the Cache object to be added to this CacheManager
	 * @return the decorated Cache object to be used instead,
	 * or simply the passed-in Cache object by default
	 */
	/**
	 * 如有必要，装饰给定的Cache对象。 
	 *  
	 * @param 缓存要添加到此CacheManager的Cache对象
	 * @return 用作装饰的Cache对象，或者默认情况下只是传入的Cache对象
	 */
	protected Cache decorateCache(Cache cache) {
		return cache;
	}

	/**
	 * Return a missing cache with the specified {@code name}, or {@code null} if
	 * such a cache does not exist or could not be created on demand.
	 * <p>Caches may be lazily created at runtime if the native provider supports it.
	 * If a lookup by name does not yield any result, an {@code AbstractCacheManager}
	 * subclass gets a chance to register such a cache at runtime. The returned cache
	 * will be automatically added to this cache manager.
	 * @param name the name of the cache to retrieve
	 * @return the missing cache, or {@code null} if no such cache exists or could be
	 * created on demand
	 * @since 4.1
	 * @see #getCache(String)
	 */
	/**
	 * 返回具有指定的{@code 名称}的丢失的缓存，如果不存在或无法根据需要创建这样的缓存，则返回{@code  null}。 
	 *  <p>如果本机提供程序支持，则缓存可能会在运行时延迟创建。 
	 * 如果按名称查找未产生任何结果，则{@code  AbstractCacheManager}子类将有机会在运行时注册此类缓存。 
	 * 返回的缓存将自动添加到此缓存管理器。 
	 *  
	 * @param 命名要检索
	 * @return 丢失的缓存的缓存的名称，如果没有这样的缓存或可以根据需要自4.1起创建，则返回{@code  null} @
	 * @see> #getCache（String ）
	 */
	@Nullable
	protected Cache getMissingCache(String name) {
		return null;
	}

}
