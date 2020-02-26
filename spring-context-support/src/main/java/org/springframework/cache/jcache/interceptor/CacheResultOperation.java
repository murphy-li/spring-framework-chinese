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

import javax.cache.annotation.CacheMethodDetails;
import javax.cache.annotation.CacheResult;

import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.Nullable;
import org.springframework.util.ExceptionTypeFilter;
import org.springframework.util.StringUtils;

/**
 * The {@link JCacheOperation} implementation for a {@link CacheResult} operation.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @see CacheResult
 */
/**
 * {@link  CacheResult}操作的{@link  JCacheOperation}实现。 
 *  @author  Stephane Nicoll @4.1起
 * @see  CacheResult
 */
class CacheResultOperation extends AbstractJCacheKeyOperation<CacheResult> {

	private final ExceptionTypeFilter exceptionTypeFilter;

	@Nullable
	private final CacheResolver exceptionCacheResolver;

	@Nullable
	private final String exceptionCacheName;


	public CacheResultOperation(CacheMethodDetails<CacheResult> methodDetails, CacheResolver cacheResolver,
			KeyGenerator keyGenerator, @Nullable CacheResolver exceptionCacheResolver) {

		super(methodDetails, cacheResolver, keyGenerator);

		CacheResult ann = methodDetails.getCacheAnnotation();
		this.exceptionTypeFilter = createExceptionTypeFilter(ann.cachedExceptions(), ann.nonCachedExceptions());
		this.exceptionCacheResolver = exceptionCacheResolver;
		this.exceptionCacheName = (StringUtils.hasText(ann.exceptionCacheName()) ? ann.exceptionCacheName() : null);
	}


	@Override
	public ExceptionTypeFilter getExceptionTypeFilter() {
		return this.exceptionTypeFilter;
	}

	/**
	 * Specify if the method should always be invoked regardless of a cache hit.
	 * By default, the method is only invoked in case of a cache miss.
	 * @see javax.cache.annotation.CacheResult#skipGet()
	 */
	/**
	 * 指定是否无论缓存命中如何始终调用该方法。 
	 * 默认情况下，仅在高速缓存未命中时才调用该方法。 
	 *  
	 * @see  javax.cache.annotation.CacheResult＃skipGet（）
	 */
	public boolean isAlwaysInvoked() {
		return getCacheAnnotation().skipGet();
	}

	/**
	 * Return the {@link CacheResolver} instance to use to resolve the cache to
	 * use for matching exceptions thrown by this operation.
	 */
	/**
	 * 返回{@link  CacheResolver}实例，用于解析用于匹配此操作引发的异常的缓存。 
	 * 
	 */
	@Nullable
	public CacheResolver getExceptionCacheResolver() {
		return this.exceptionCacheResolver;
	}

	/**
	 * Return the name of the cache to cache exceptions, or {@code null} if
	 * caching exceptions should be disabled.
	 * @see javax.cache.annotation.CacheResult#exceptionCacheName()
	 */
	/**
	 * 返回缓存名称以缓存异常； 
	 * 如果应禁用缓存异常，则返回{@code  null}。 
	 *  
	 * @see  javax.cache.annotation.CacheResult＃exceptionCacheName（）
	 */
	@Nullable
	public String getExceptionCacheName() {
		return this.exceptionCacheName;
	}

}
