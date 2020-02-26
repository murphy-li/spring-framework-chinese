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

import java.lang.annotation.Annotation;

import javax.cache.annotation.CacheKeyInvocationContext;

import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * A base interceptor for JSR-107 key-based cache annotations.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @param <O> the operation type
 * @param <A> the annotation type
 */
/**
 * 用于基于JSR-107键的缓存注释的基本拦截器。 
 *  @author  Stephane Nicoll @since 4.1起
 * @param  <O>操作类型
 * @param  <A>注释类型
 */
@SuppressWarnings("serial")
abstract class AbstractKeyCacheInterceptor<O extends AbstractJCacheKeyOperation<A>, A extends Annotation>
		extends AbstractCacheInterceptor<O, A> {

	protected AbstractKeyCacheInterceptor(CacheErrorHandler errorHandler) {
		super(errorHandler);
	}


	/**
	 * Generate a key for the specified invocation.
	 * @param context the context of the invocation
	 * @return the key to use
	 */
	/**
	 * 为指定的调用生成一个密钥。 
	 *  
	 * @param 上下文调用的上下文<@r​​eturn>使用的键
	 */
	protected Object generateKey(CacheOperationInvocationContext<O> context) {
		KeyGenerator keyGenerator = context.getOperation().getKeyGenerator();
		Object key = keyGenerator.generate(context.getTarget(), context.getMethod(), context.getArgs());
		if (logger.isTraceEnabled()) {
			logger.trace("Computed cache key " + key + " for operation " + context.getOperation());
		}
		return key;
	}

	/**
	 * Create a {@link CacheKeyInvocationContext} based on the specified invocation.
	 * @param context the context of the invocation.
	 * @return the related {@code CacheKeyInvocationContext}
	 */
	/**
	 * 根据指定的调用创建一个{@link  CacheKeyInvocationContext}。 
	 *  
	 * @param  context调用的上下文。 
	 *  
	 * @return 相关的{@code  CacheKeyInvocationContext}
	 */
	protected CacheKeyInvocationContext<A> createCacheKeyInvocationContext(CacheOperationInvocationContext<O> context) {
		return new DefaultCacheKeyInvocationContext<>(context.getOperation(), context.getTarget(), context.getArgs());
	}

}
