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

package org.springframework.test.context.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.style.ToStringCreator;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.Assert;

/**
 * Default implementation of the {@link ContextCache} API.
 *
 * <p>Uses a synchronized {@link Map} configured with a maximum size
 * and a <em>least recently used</em> (LRU) eviction policy to cache
 * {@link ApplicationContext} instances.
 *
 * <p>The maximum size may be supplied as a {@linkplain #DefaultContextCache(int)
 * constructor argument} or set via a system property or Spring property named
 * {@code spring.test.context.cache.maxSize}.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 * @see ContextCacheUtils#retrieveMaxCacheSize()
 */
/**
 * {@link  ContextCache} API的默认实现。 
 *  <p>使用配置了最大大小和<em>最近最少使用</ em>（LRU）驱逐策略的同步{@link  Map}缓存{@link  ApplicationContext}实例。 
 *  <p>最大大小可以作为{@link  plain #DefaultContextCache（int）构造函数参数}提供，也可以通过名为{@code  spring.test.context.cache.maxSize}的系统属性或Spring属性进行设置。 
 *  。 
 *  @author  Sam Brannen @author 于尔根·霍勒（Juergen Hoeller）@从2.5开始
 * @see  ContextCacheUtils＃retrieveMaxCacheSize（）
 */
public class DefaultContextCache implements ContextCache {

	private static final Log statsLogger = LogFactory.getLog(CONTEXT_CACHE_LOGGING_CATEGORY);

	/**
	 * Map of context keys to Spring {@code ApplicationContext} instances.
	 */
	/**
	 * 上下文键到Spring {@code  ApplicationContext}实例的映射。 
	 * 
	 */
	private final Map<MergedContextConfiguration, ApplicationContext> contextMap =
			Collections.synchronizedMap(new LruCache(32, 0.75f));

	/**
	 * Map of parent keys to sets of children keys, representing a top-down <em>tree</em>
	 * of context hierarchies. This information is used for determining which subtrees
	 * need to be recursively removed and closed when removing a context that is a parent
	 * of other contexts.
	 */
	/**
	 * 父键到子键集的映射，代表上下文层次结构的自上而下的<em> tree </ em>。 
	 * 此信息用于确定在删除作为其他上下文父级的上下文时需要递归删除和关闭哪些子树。 
	 * 
	 */
	private final Map<MergedContextConfiguration, Set<MergedContextConfiguration>> hierarchyMap =
			new ConcurrentHashMap<>(32);

	private final int maxSize;

	private final AtomicInteger hitCount = new AtomicInteger();

	private final AtomicInteger missCount = new AtomicInteger();


	/**
	 * Create a new {@code DefaultContextCache} using the maximum cache size
	 * obtained via {@link ContextCacheUtils#retrieveMaxCacheSize()}.
	 * @since 4.3
	 * @see #DefaultContextCache(int)
	 * @see ContextCacheUtils#retrieveMaxCacheSize()
	 */
	/**
	 * 使用通过{@link  ContextCacheUtils＃retrieveMaxCacheSize（）}获得的最大缓存大小创建一个新的{@code  DefaultContextCache}。 
	 *  @since 4.3 
	 * @see  #DefaultContextCache（int）
	 * @see  ContextCacheUtils＃retrieveMaxCacheSize（）
	 */
	public DefaultContextCache() {
		this(ContextCacheUtils.retrieveMaxCacheSize());
	}

	/**
	 * Create a new {@code DefaultContextCache} using the supplied maximum
	 * cache size.
	 * @param maxSize the maximum cache size
	 * @throws IllegalArgumentException if the supplied {@code maxSize} value
	 * is not positive
	 * @since 4.3
	 * @see #DefaultContextCache()
	 */
	/**
	 * 使用提供的最大缓存大小创建一个新的{@code  DefaultContextCache}。 
	 *  
	 * @param  maxSize最大缓存大小
	 * @throws 如果提供的{@code  maxSize}值不是正值，则从IllegalArgumentException @从4.3开始@
	 */
	public DefaultContextCache(int maxSize) {
		Assert.isTrue(maxSize > 0, "'maxSize' must be positive");
		this.maxSize = maxSize;
	}


	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(MergedContextConfiguration key) {
		Assert.notNull(key, "Key must not be null");
		return this.contextMap.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public ApplicationContext get(MergedContextConfiguration key) {
		Assert.notNull(key, "Key must not be null");
		ApplicationContext context = this.contextMap.get(key);
		if (context == null) {
			this.missCount.incrementAndGet();
		}
		else {
			this.hitCount.incrementAndGet();
		}
		return context;
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void put(MergedContextConfiguration key, ApplicationContext context) {
		Assert.notNull(key, "Key must not be null");
		Assert.notNull(context, "ApplicationContext must not be null");

		this.contextMap.put(key, context);
		MergedContextConfiguration child = key;
		MergedContextConfiguration parent = child.getParent();
		while (parent != null) {
			Set<MergedContextConfiguration> list = this.hierarchyMap.computeIfAbsent(parent, k -> new HashSet<>());
			list.add(child);
			child = parent;
			parent = child.getParent();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(MergedContextConfiguration key, @Nullable HierarchyMode hierarchyMode) {
		Assert.notNull(key, "Key must not be null");

		// startKey is the level at which to begin clearing the cache,
		// depending on the configured hierarchy mode.s
		MergedContextConfiguration startKey = key;
		if (hierarchyMode == HierarchyMode.EXHAUSTIVE) {
			MergedContextConfiguration parent = startKey.getParent();
			while (parent != null) {
				startKey = parent;
				parent = startKey.getParent();
			}
		}

		List<MergedContextConfiguration> removedContexts = new ArrayList<>();
		remove(removedContexts, startKey);

		// Remove all remaining references to any removed contexts from the
		// hierarchy map.
		for (MergedContextConfiguration currentKey : removedContexts) {
			for (Set<MergedContextConfiguration> children : this.hierarchyMap.values()) {
				children.remove(currentKey);
			}
		}

		// Remove empty entries from the hierarchy map.
		for (Map.Entry<MergedContextConfiguration, Set<MergedContextConfiguration>> entry : this.hierarchyMap.entrySet()) {
			if (entry.getValue().isEmpty()) {
				this.hierarchyMap.remove(entry.getKey());
			}
		}
	}

	private void remove(List<MergedContextConfiguration> removedContexts, MergedContextConfiguration key) {
		Assert.notNull(key, "Key must not be null");

		Set<MergedContextConfiguration> children = this.hierarchyMap.get(key);
		if (children != null) {
			for (MergedContextConfiguration child : children) {
				// Recurse through lower levels
				remove(removedContexts, child);
			}
			// Remove the set of children for the current context from the hierarchy map.
			this.hierarchyMap.remove(key);
		}

		// Physically remove and close leaf nodes first (i.e., on the way back up the
		// stack as opposed to prior to the recursive call).
		ApplicationContext context = this.contextMap.remove(key);
		if (context instanceof ConfigurableApplicationContext) {
			((ConfigurableApplicationContext) context).close();
		}
		removedContexts.add(key);
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.contextMap.size();
	}

	/**
	 * Get the maximum size of this cache.
	 */
	/**
	 * 获取此缓存的最大大小。 
	 * 
	 */
	public int getMaxSize() {
		return this.maxSize;
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getParentContextCount() {
		return this.hierarchyMap.size();
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHitCount() {
		return this.hitCount.get();
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMissCount() {
		return this.missCount.get();
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		synchronized (this.contextMap) {
			clear();
			clearStatistics();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		synchronized (this.contextMap) {
			this.contextMap.clear();
			this.hierarchyMap.clear();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearStatistics() {
		synchronized (this.contextMap) {
			this.hitCount.set(0);
			this.missCount.set(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logStatistics() {
		if (statsLogger.isDebugEnabled()) {
			statsLogger.debug("Spring test ApplicationContext cache statistics: " + this);
		}
	}

	/**
	 * Generate a text string containing the implementation type of this
	 * cache and its statistics.
	 * <p>The string returned by this method contains all information
	 * required for compliance with the contract for {@link #logStatistics()}.
	 * @return a string representation of this cache, including statistics
	 */
	/**
	 * 生成一个文本字符串，其中包含此缓存的实现类型及其统计信息。 
	 *  <p>此方法返回的字符串包含遵守{@link  #logStatistics（）}的合同所需的所有信息。 
	 *  
	 * @return 此缓存的字符串表示形式，包括统计信息
	 */
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("size", size())
				.append("maxSize", getMaxSize())
				.append("parentContextCount", getParentContextCount())
				.append("hitCount", getHitCount())
				.append("missCount", getMissCount())
				.toString();
	}


	/**
	 * Simple cache implementation based on {@link LinkedHashMap} with a maximum
	 * size and a <em>least recently used</em> (LRU) eviction policy that
	 * properly closes application contexts.
	 * @since 4.3
	 */
	/**
	 * 基于{@link  LinkedHashMap}的简单缓存实现，该缓存具有最大大小和<em>最近最少使用</ em>（LRU）驱逐策略，可以正确关闭应用程序上下文。 
	 *  @4.3起
	 */
	@SuppressWarnings("serial")
	private class LruCache extends LinkedHashMap<MergedContextConfiguration, ApplicationContext> {

		/**
		 * Create a new {@code LruCache} with the supplied initial capacity
		 * and load factor.
		 * @param initialCapacity the initial capacity
		 * @param loadFactor the load factor
		 */
		/**
		 * 使用提供的初始容量和负载系数创建一个新的{@code  LruCache}。 
		 *  
		 * @param  initialCapacity初始容量
		 * @param  loadFactor负载系数
		 */
		LruCache(int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor, true);
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry<MergedContextConfiguration, ApplicationContext> eldest) {
			if (this.size() > DefaultContextCache.this.getMaxSize()) {
				// Do NOT delete "DefaultContextCache.this."; otherwise, we accidentally
				// invoke java.util.Map.remove(Object, Object).
				DefaultContextCache.this.remove(eldest.getKey(), HierarchyMode.CURRENT_LEVEL);
			}

			// Return false since we invoke a custom eviction algorithm.
			return false;
		}
	}

}
