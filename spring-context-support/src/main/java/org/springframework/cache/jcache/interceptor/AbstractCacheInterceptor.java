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

package org.springframework.cache.jcache.interceptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.AbstractCacheInvoker;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

/**
 * A base interceptor for JSR-107 cache annotations.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @param <O> the operation type
 * @param <A> the annotation type
 */
/**
 * JSR-107高速缓存注解的基本拦截器。 
 *  @author  Stephane Nicoll @since 4.1起
 * @param  <O>操作类型
 * @param  <A>注释类型
 */
@SuppressWarnings("serial")
abstract class AbstractCacheInterceptor<O extends AbstractJCacheOperation<A>, A extends Annotation>
		extends AbstractCacheInvoker implements Serializable {

	protected final Log logger = LogFactory.getLog(getClass());


	protected AbstractCacheInterceptor(CacheErrorHandler errorHandler) {
		super(errorHandler);
	}


	@Nullable
	protected abstract Object invoke(CacheOperationInvocationContext<O> context, CacheOperationInvoker invoker)
			throws Throwable;


	/**
	 * Resolve the cache to use.
	 * @param context the invocation context
	 * @return the cache to use (never {@code null})
	 */
	/**
	 * 解决要使用的缓存。 
	 *  
	 * @param 上下文调用上下文<@r​​eturn>要使用的缓存（从不<{@@code> null}）
	 */
	protected Cache resolveCache(CacheOperationInvocationContext<O> context) {
		Collection<? extends Cache> caches = context.getOperation().getCacheResolver().resolveCaches(context);
		Cache cache = extractFrom(caches);
		if (cache == null) {
			throw new IllegalStateException("Cache could not have been resolved for " + context.getOperation());
		}
		return cache;
	}

	/**
	 * Convert the collection of caches in a single expected element.
	 * <p>Throw an {@link IllegalStateException} if the collection holds more than one element
	 * @return the single element, or {@code null} if the collection is empty
	 */
	/**
	 * 在单个预期元素中转换缓存的集合。 
	 *  <p>如果集合包含多个元素，则抛出一个{@link  IllegalStateException} <单个元素，返回一个@
	 * @return>，如果集合为空，则抛出一个{@code  null}
	 */
	@Nullable
	static Cache extractFrom(Collection<? extends Cache> caches) {
		if (CollectionUtils.isEmpty(caches)) {
			return null;
		}
		else if (caches.size() == 1) {
			return caches.iterator().next();
		}
		else {
			throw new IllegalStateException("Unsupported cache resolution result " + caches +
					": JSR-107 only supports a single cache.");
		}
	}

}
