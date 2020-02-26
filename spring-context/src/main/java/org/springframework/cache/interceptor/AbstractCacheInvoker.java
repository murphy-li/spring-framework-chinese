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

package org.springframework.cache.interceptor;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;
import org.springframework.util.function.SingletonSupplier;

/**
 * A base component for invoking {@link Cache} operations and using a
 * configurable {@link CacheErrorHandler} when an exception occurs.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.1
 * @see org.springframework.cache.interceptor.CacheErrorHandler
 */
/**
 * 一个基本组件，用于在发生异常时调用{@link  Cache}操作并使用可配置的{@link  CacheErrorHandler}。 
 *  @author  Stephane Nicoll @author  Juergen Hoeller @始于4.1 
 * @see  org.springframework.cache.interceptor.CacheErrorHandler
 */
public abstract class AbstractCacheInvoker {

	protected SingletonSupplier<CacheErrorHandler> errorHandler;


	protected AbstractCacheInvoker() {
		this.errorHandler = SingletonSupplier.of(SimpleCacheErrorHandler::new);
	}

	protected AbstractCacheInvoker(CacheErrorHandler errorHandler) {
		this.errorHandler = SingletonSupplier.of(errorHandler);
	}


	/**
	 * Set the {@link CacheErrorHandler} instance to use to handle errors
	 * thrown by the cache provider. By default, a {@link SimpleCacheErrorHandler}
	 * is used who throws any exception as is.
	 */
	/**
	 * 设置{@link  CacheErrorHandler}实例以处理由缓存提供程序引发的错误。 
	 * 默认情况下，使用{@link  SimpleCacheErrorHandler}照原样抛出任何异常。 
	 * 
	 */
	public void setErrorHandler(CacheErrorHandler errorHandler) {
		this.errorHandler = SingletonSupplier.of(errorHandler);
	}

	/**
	 * Return the {@link CacheErrorHandler} to use.
	 */
	/**
	 * 返回{@link  CacheErrorHandler}以使用。 
	 * 
	 */
	public CacheErrorHandler getErrorHandler() {
		return this.errorHandler.obtain();
	}


	/**
	 * Execute {@link Cache#get(Object)} on the specified {@link Cache} and
	 * invoke the error handler if an exception occurs. Return {@code null}
	 * if the handler does not throw any exception, which simulates a cache
	 * miss in case of error.
	 * @see Cache#get(Object)
	 */
	/**
	 * 在指定的{@link 缓存}上执行{@link  Cache＃get（Object）}，如果发生异常，则调用错误处理程序。 
	 * 如果处理程序未引发任何异常（在发生错误的情况下模拟高速缓存未命中），则返回{@code  null}。 
	 *  
	 * @see  Cache＃get（对象）
	 */
	@Nullable
	protected Cache.ValueWrapper doGet(Cache cache, Object key) {
		try {
			return cache.get(key);
		}
		catch (RuntimeException ex) {
			getErrorHandler().handleCacheGetError(ex, cache, key);
			return null;  // If the exception is handled, return a cache miss
		}
	}

	/**
	 * Execute {@link Cache#put(Object, Object)} on the specified {@link Cache}
	 * and invoke the error handler if an exception occurs.
	 */
	/**
	 * 在指定的{@link 缓存}上执行{@link  Cache＃put（Object，Object）}，如果发生异常，则调用错误处理程序。 
	 * 
	 */
	protected void doPut(Cache cache, Object key, @Nullable Object result) {
		try {
			cache.put(key, result);
		}
		catch (RuntimeException ex) {
			getErrorHandler().handleCachePutError(ex, cache, key, result);
		}
	}

	/**
	 * Execute {@link Cache#evict(Object)}/{@link Cache#evictIfPresent(Object)} on the
	 * specified {@link Cache} and invoke the error handler if an exception occurs.
	 */
	/**
	 * 在指定的{@link 缓存}上执行{@link  Cache＃evict（Object）} / {<@link> Cache＃evictIfPresent（Object）}，如果发生异常，则调用错误处理程序。 
	 * 
	 */
	protected void doEvict(Cache cache, Object key, boolean immediate) {
		try {
			if (immediate) {
				cache.evictIfPresent(key);
			}
			else {
				cache.evict(key);
			}
		}
		catch (RuntimeException ex) {
			getErrorHandler().handleCacheEvictError(ex, cache, key);
		}
	}

	/**
	 * Execute {@link Cache#clear()} on the specified {@link Cache} and
	 * invoke the error handler if an exception occurs.
	 */
	/**
	 * 在指定的{@link  Cache}上执行{@link  Cache＃clear（）}，如果发生异常，则调用错误处理程序。 
	 * 
	 */
	protected void doClear(Cache cache, boolean immediate) {
		try {
			if (immediate) {
				cache.invalidate();
			}
			else {
				cache.clear();
			}
		}
		catch (RuntimeException ex) {
			getErrorHandler().handleCacheClearError(ex, cache);
		}
	}

}
