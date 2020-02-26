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

package org.springframework.cache.interceptor;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

/**
 * A strategy for handling cache-related errors. In most cases, any
 * exception thrown by the provider should simply be thrown back at
 * the client but, in some circumstances, the infrastructure may need
 * to handle cache-provider exceptions in a different way.
 *
 * <p>Typically, failing to retrieve an object from the cache with
 * a given id can be transparently managed as a cache miss by not
 * throwing back such exception.
 *
 * @author Stephane Nicoll
 * @since 4.1
 */
/**
 * 处理与缓存相关的错误的策略。 
 * 在大多数情况下，应该将提供程序引发的任何异常简单地返回给客户端，但是在某些情况下，基础结构可能需要以不同的方式处理缓存提供程序异常。 
 *  <p>通常，通过不抛出此类异常，可以透明地将无法从具有给定ID的高速缓存中检索对象的情况视为高速缓存未命中。 
 *  @author 史蒂芬·尼科尔@since 4.1
 */
public interface CacheErrorHandler {

	/**
	 * Handle the given runtime exception thrown by the cache provider when
	 * retrieving an item with the specified {@code key}, possibly
	 * rethrowing it as a fatal exception.
	 * @param exception the exception thrown by the cache provider
	 * @param cache the cache
	 * @param key the key used to get the item
	 * @see Cache#get(Object)
	 */
	/**
	 * 使用指定的{@code 键}检索由缓存提供程序抛出的给定运行时异常，可能将其作为致命异常重新抛出。 
	 *  
	 * @param 异常缓存提供者引发的异常
	 * @param 缓存缓存
	 * @param 键用于获取项目的键
	 * @see  Cache＃get（Object）
	 */
	void handleCacheGetError(RuntimeException exception, Cache cache, Object key);

	/**
	 * Handle the given runtime exception thrown by the cache provider when
	 * updating an item with the specified {@code key} and {@code value},
	 * possibly rethrowing it as a fatal exception.
	 * @param exception the exception thrown by the cache provider
	 * @param cache the cache
	 * @param key the key used to update the item
	 * @param value the value to associate with the key
	 * @see Cache#put(Object, Object)
	 */
	/**
	 * 处理使用指定的{@code 键}和{@code 值}更新项目时，缓存提供程序引发的给定运行时异常，可能会将其作为致命异常抛出。 
	 *  
	 * @param 异常高速缓存提供程序引发的异常
	 * @param 高速缓存高速缓存
	 * @param 键用于更新项目的键
	 * @param 值与键相关的值
	 * @see  Cache＃put （对象，对象）
	 */
	void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value);

	/**
	 * Handle the given runtime exception thrown by the cache provider when
	 * clearing an item with the specified {@code key}, possibly rethrowing
	 * it as a fatal exception.
	 * @param exception the exception thrown by the cache provider
	 * @param cache the cache
	 * @param key the key used to clear the item
	 */
	/**
	 * 处理使用指定的{@code 键}清除项目时由缓存提供程序引发的给定运行时异常，有可能将其作为致命异常重新抛出。 
	 *  
	 * @param 异常缓存提供程序引发的异常
	 * @param 缓存缓存
	 * @param 键用于清除项目的键
	 */
	void handleCacheEvictError(RuntimeException exception, Cache cache, Object key);

	/**
	 * Handle the given runtime exception thrown by the cache provider when
	 * clearing the specified {@link Cache}, possibly rethrowing it as a
	 * fatal exception.
	 * @param exception the exception thrown by the cache provider
	 * @param cache the cache to clear
	 */
	/**
	 * 清除指定的{@link  Cache}时，处理由缓存提供程序抛出的给定运行时异常，有可能将其作为致命异常重新抛出。 
	 *  
	 * @param 异常缓存提供程序引发的异常
	 * @param 缓存要清除的缓存
	 */
	void handleCacheClearError(RuntimeException exception, Cache cache);

}
