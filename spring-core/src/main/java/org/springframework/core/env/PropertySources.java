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

package org.springframework.core.env;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.Nullable;

/**
 * Holder containing one or more {@link PropertySource} objects.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see PropertySource
 */
/**
 * 包含一个或多个{@link  PropertySource}对象的持有人。 
 *  @author 克里斯·比姆斯（Chris Beams）@author 于尔根·霍勒（Juergen Hoeller）@自3.1起
 * @see  PropertySource
 */
public interface PropertySources extends Iterable<PropertySource<?>> {

	/**
	 * Return a sequential {@link Stream} containing the property sources.
	 * @since 5.1
	 */
	/**
	 * 返回包含属性源的顺序{@link  Stream}。 
	 *  @5.1起
	 */
	default Stream<PropertySource<?>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Return whether a property source with the given name is contained.
	 * @param name the {@linkplain PropertySource#getName() name of the property source} to find
	 */
	/**
	 * 返回是否包含具有给定名称的属性源。 
	 *  
	 * @param 命名{@link  plain PropertySource＃getName（）属性源名称）以查找
	 */
	boolean contains(String name);

	/**
	 * Return the property source with the given name, {@code null} if not found.
	 * @param name the {@linkplain PropertySource#getName() name of the property source} to find
	 */
	/**
	 * 返回具有给定名称的属性源，如果找不到，则返回{@code  null}。 
	 *  
	 * @param 命名{@link  plain PropertySource＃getName（）属性源名称）以查找
	 */
	@Nullable
	PropertySource<?> get(String name);

}
