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

package org.springframework.messaging.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link DestinationResolver} implementation that proxies a target DestinationResolver,
 * caching its {@link #resolveDestination} results. Such caching is particularly useful
 * if the destination resolving process is expensive (e.g. the destination has to be
 * resolved through an external system) and the resolution results are stable anyway.
 *
 * @author Agim Emruli
 * @author Juergen Hoeller
 * @since 4.1
 * @param <D> the destination type
 * @see DestinationResolver#resolveDestination
 */
/**
 * 代理目标DestinationResolver的{@link  DestinationResolver}实现，并缓存其{@link  #resolveDestination}结果。 
 * 如果目标解析过程比较昂贵（例如，目标必须通过外部系统解析）并且解析结果仍然稳定，则此类缓存特别有用。 
 *  @author  Agim Emruli @author  Juergen Hoeller @自4.1起
 * @param  <D>目标类型
 * @see  DestinationResolver＃resolveDestination
 */
public class CachingDestinationResolverProxy<D> implements DestinationResolver<D>, InitializingBean {

	private final Map<String, D> resolvedDestinationCache = new ConcurrentHashMap<>();

	@Nullable
	private DestinationResolver<D> targetDestinationResolver;


	/**
	 * Create a new CachingDestinationResolverProxy, setting the target DestinationResolver
	 * through the {@link #setTargetDestinationResolver} bean property.
	 */
	/**
	 * 创建一个新的CachingDestinationResolverProxy，通过{@link  #setTargetDestinationResolver} bean属性设置目标DestinationResolver。 
	 * 
	 */
	public CachingDestinationResolverProxy() {
	}

	/**
	 * Create a new CachingDestinationResolverProxy using the given target
	 * DestinationResolver to actually resolve destinations.
	 * @param targetDestinationResolver the target DestinationResolver to delegate to
	 */
	/**
	 * 使用给定的目标DestinationResolver创建一个新的CachingDestinationResolverProxy来实际解析目标。 
	 *  
	 * @param  targetDestinationResolver要委托给的目标DestinationResolver
	 */
	public CachingDestinationResolverProxy(DestinationResolver<D> targetDestinationResolver) {
		Assert.notNull(targetDestinationResolver, "Target DestinationResolver must not be null");
		this.targetDestinationResolver = targetDestinationResolver;
	}


	/**
	 * Set the target DestinationResolver to delegate to.
	 */
	/**
	 * 设置目标DestinationResolver委托给。 
	 * 
	 */
	public void setTargetDestinationResolver(DestinationResolver<D> targetDestinationResolver) {
		this.targetDestinationResolver = targetDestinationResolver;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.targetDestinationResolver == null) {
			throw new IllegalArgumentException("Property 'targetDestinationResolver' is required");
		}
	}


	/**
	 * Resolves and caches destinations if successfully resolved by the target
	 * DestinationResolver implementation.
	 * @param name the destination name to be resolved
	 * @return the currently resolved destination or an already cached destination
	 * @throws DestinationResolutionException if the target DestinationResolver
	 * reports an error during destination resolution
	 */
	/**
	 * 如果目标DestinationResolver实现成功解析，则解析并缓存目标。 
	 *  
	 * @param 命名要解析的目标名称
	 * @return 当前解析的目标或已缓存的目标
	 * @throws  DestinationResolutionException如果目标DestinationResolver在解析目标期间报告错误
	 */
	@Override
	public D resolveDestination(String name) throws DestinationResolutionException {
		D destination = this.resolvedDestinationCache.get(name);
		if (destination == null) {
			Assert.state(this.targetDestinationResolver != null, "No target DestinationResolver set");
			destination = this.targetDestinationResolver.resolveDestination(name);
			this.resolvedDestinationCache.put(name, destination);
		}
		return destination;
	}

}
