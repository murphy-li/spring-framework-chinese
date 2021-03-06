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

package org.springframework.beans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.Nullable;

/**
 * Holder containing one or more {@link PropertyValue} objects,
 * typically comprising one update for a specific target bean.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 13 May 2001
 * @see PropertyValue
 */
/**
 * 包含一个或多个{@link  PropertyValue}对象的持有人，通常包括一个针对特定目标bean的更新。 
 *  @author  Rod Johnson @author  Juergen Hoeller @自2001年5月13日以来
 * @see  PropertyValue
 */
public interface PropertyValues extends Iterable<PropertyValue> {

	/**
	 * Return an {@link Iterator} over the property values.
	 * @since 5.1
	 */
	/**
	 * 在属性值上返回{@link 迭代器}。 
	 *  @5.1起
	 */
	@Override
	default Iterator<PropertyValue> iterator() {
		return Arrays.asList(getPropertyValues()).iterator();
	}

	/**
	 * Return a {@link Spliterator} over the property values.
	 * @since 5.1
	 */
	/**
	 * 在属性值上返回{@link 分隔符}。 
	 *  @5.1起
	 */
	@Override
	default Spliterator<PropertyValue> spliterator() {
		return Spliterators.spliterator(getPropertyValues(), 0);
	}

	/**
	 * Return a sequential {@link Stream} containing the property values.
	 * @since 5.1
	 */
	/**
	 * 返回包含属性值的顺序{@link  Stream}。 
	 *  @5.1起
	 */
	default Stream<PropertyValue> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Return an array of the PropertyValue objects held in this object.
	 */
	/**
	 * 返回此对象中保存的PropertyValue对象的数组。 
	 * 
	 */
	PropertyValue[] getPropertyValues();

	/**
	 * Return the property value with the given name, if any.
	 * @param propertyName the name to search for
	 * @return the property value, or {@code null} if none
	 */
	/**
	 * 返回具有给定名称的属性值（如果有）。 
	 *  
	 * @param  propertyName名称以搜索
	 * @return 属性值； 
	 * 如果没有，则为{@code  null}
	 */
	@Nullable
	PropertyValue getPropertyValue(String propertyName);

	/**
	 * Return the changes since the previous PropertyValues.
	 * Subclasses should also override {@code equals}.
	 * @param old old property values
	 * @return the updated or new properties.
	 * Return empty PropertyValues if there are no changes.
	 * @see Object#equals
	 */
	/**
	 * 返回自上一个PropertyValues以来的更改。 
	 * 子类也应该覆盖{@code  equals}。 
	 *  
	 * @param 旧的旧属性值
	 * @return 更新的或新的属性。 
	 * 如果没有更改，则返回空的PropertyValues。 
	 *  
	 * @see 对象＃等于
	 */
	PropertyValues changesSince(PropertyValues old);

	/**
	 * Is there a property value (or other processing entry) for this property?
	 * @param propertyName the name of the property we're interested in
	 * @return whether there is a property value for this property
	 */
	/**
	 * 此属性是否有一个属性值（或其他处理条目）？ 
	 * @param  propertyName我们感兴趣的属性的名称
	 * @return 此属性是否存在属性值
	 */
	boolean contains(String propertyName);

	/**
	 * Does this holder not contain any PropertyValue objects at all?
	 */
	/**
	 * 此持有人是否根本不包含任何PropertyValue对象？
	 */
	boolean isEmpty();

}
