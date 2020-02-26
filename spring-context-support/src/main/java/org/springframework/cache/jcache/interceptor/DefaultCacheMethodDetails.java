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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.cache.annotation.CacheMethodDetails;

/**
 * The default {@link CacheMethodDetails} implementation.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @param <A> the annotation type
 */
/**
 * 默认的{@link  CacheMethodDetails}实现。 
 *  @author  Stephane Nicoll @since 4.1起
 * @param  <A>注释类型
 */
class DefaultCacheMethodDetails<A extends Annotation> implements CacheMethodDetails<A> {

	private final Method method;

	private final Set<Annotation> annotations;

	private final A cacheAnnotation;

	private final String cacheName;


	public DefaultCacheMethodDetails(Method method, A cacheAnnotation, String cacheName) {
		this.method = method;
		this.annotations = Collections.unmodifiableSet(
				new LinkedHashSet<>(Arrays.asList(method.getAnnotations())));
		this.cacheAnnotation = cacheAnnotation;
		this.cacheName = cacheName;
	}


	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public Set<Annotation> getAnnotations() {
		return this.annotations;
	}

	@Override
	public A getCacheAnnotation() {
		return this.cacheAnnotation;
	}

	@Override
	public String getCacheName() {
		return this.cacheName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CacheMethodDetails[");
		sb.append("method=").append(this.method);
		sb.append(", cacheAnnotation=").append(this.cacheAnnotation);
		sb.append(", cacheName='").append(this.cacheName).append('\'');
		sb.append(']');
		return sb.toString();
	}

}
