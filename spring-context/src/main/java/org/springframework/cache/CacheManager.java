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

package org.springframework.cache;

import java.util.Collection;

import org.springframework.lang.Nullable;

/**
 * Spring's central cache manager SPI.
 *
 * <p>Allows for retrieving named {@link Cache} regions.
 *
 * @author Costin Leau
 * @author Sam Brannen
 * @since 3.1
 */
/**
 * Spring的中央缓存管理器SPI。 
 *  <p>允许检索命名的{@link 缓存}区域。 
 *  @author  Costin Leau @author  Sam Brannen @自3.1起
 */
public interface CacheManager {

	/**
	 * Get the cache associated with the given name.
	 * <p>Note that the cache may be lazily created at runtime if the
	 * native provider supports it.
	 * @param name the cache identifier (must not be {@code null})
	 * @return the associated cache, or {@code null} if such a cache
	 * does not exist or could be not created
	 */
	/**
	 * 获取与给定名称关联的缓存。 
	 *  <p>请注意，如果本机提供程序支持，则缓存可能会在运行时延迟创建。 
	 *  
	 * @param 命名缓存标识符（不得为{@code  null}）
	 * @return 关联的缓存，如果不存在或无法创建，则为{@code  null}
	 */
	@Nullable
	Cache getCache(String name);

	/**
	 * Get a collection of the cache names known by this manager.
	 * @return the names of all caches known by the cache manager
	 */
	/**
	 * 获取此管理器已知的缓存名称的集合。 
	 *  
	 * @return 缓存管理器已知的所有缓存的名称
	 */
	Collection<String> getCacheNames();

}
