/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.test.context.cache;

import org.springframework.core.SpringProperties;
import org.springframework.util.StringUtils;

/**
 * Collection of utilities for working with {@link ContextCache ContextCaches}.
 *
 * @author Sam Brannen
 * @since 4.3
 */
/**
 * 用于使用{@link  ContextCache ContextCaches}的实用程序的集合。 
 *  @author  Sam Brannen @从4.3开始
 */
public abstract class ContextCacheUtils {

	/**
	 * Retrieve the maximum size of the {@link ContextCache}.
	 * <p>Uses {@link SpringProperties} to retrieve a system property or Spring
	 * property named {@code spring.test.context.cache.maxSize}.
	 * <p>Falls back to the value of the {@link ContextCache#DEFAULT_MAX_CONTEXT_CACHE_SIZE}
	 * if no such property has been set or if the property is not an integer.
	 * @return the maximum size of the context cache
	 * @see ContextCache#MAX_CONTEXT_CACHE_SIZE_PROPERTY_NAME
	 */
	/**
	 * 检索{@link  ContextCache}的最大大小。 
	 *  <p>使用{@link  SpringProperties}检索名为{@code  spring.test.context.cache.maxSize}的系统属性或Spring属性。 
	 *  <p>如果未设置任何此类属性，或者该属性不是整数，则退回到{@link  ContextCache＃DEFAULT_MAX_CONTEXT_CACHE_SIZE}的值。 
	 *  
	 * @return 上下文缓存的最大大小
	 * @see  ContextCache＃MAX_CONTEXT_CACHE_SIZE_PROPERTY_NAME
	 */
	public static int retrieveMaxCacheSize() {
		try {
			String maxSize = SpringProperties.getProperty(ContextCache.MAX_CONTEXT_CACHE_SIZE_PROPERTY_NAME);
			if (StringUtils.hasText(maxSize)) {
				return Integer.parseInt(maxSize.trim());
			}
		}
		catch (Exception ex) {
			// ignore
		}

		// Fallback
		return ContextCache.DEFAULT_MAX_CONTEXT_CACHE_SIZE;
	}

}
