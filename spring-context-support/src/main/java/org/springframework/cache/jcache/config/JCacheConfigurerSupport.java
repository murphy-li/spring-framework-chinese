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

package org.springframework.cache.jcache.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.lang.Nullable;

/**
 * An extension of {@link CachingConfigurerSupport} that also implements
 * {@link JCacheConfigurer}.
 *
 * <p>Users of JSR-107 annotations may extend from this class rather than
 * implementing from {@link JCacheConfigurer} directly.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @see JCacheConfigurer
 * @see CachingConfigurerSupport
 */
/**
 * {@link  CachingConfigurerSupport}的扩展，它也实现了{@link  JCacheConfigurer}。 
 *  <p> JSR-107注解的用户可以从此类扩展，而不是直接从{@link  JCacheConfigurer}实现。 
 *  @author  Stephane Nicoll @4.1起
 * @see  JCacheConfigurer 
 * @see  CachingConfigurerSupport
 */
public class JCacheConfigurerSupport extends CachingConfigurerSupport implements JCacheConfigurer {

	@Override
	@Nullable
	public CacheResolver exceptionCacheResolver() {
		return null;
	}

}
