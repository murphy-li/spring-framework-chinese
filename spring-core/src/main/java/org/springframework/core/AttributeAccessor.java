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

package org.springframework.core;

import org.springframework.lang.Nullable;

/**
 * Interface defining a generic contract for attaching and accessing metadata
 * to/from arbitrary objects.
 *
 * @author Rob Harrop
 * @since 2.0
 */
/**
 * 定义用于将元数据附加到任意对象或从任意对象访问元数据的通用协定的接口。 
 *  @author  Rob Harrop @始于2.0
 */
public interface AttributeAccessor {

	/**
	 * Set the attribute defined by {@code name} to the supplied {@code value}.
	 * If {@code value} is {@code null}, the attribute is {@link #removeAttribute removed}.
	 * <p>In general, users should take care to prevent overlaps with other
	 * metadata attributes by using fully-qualified names, perhaps using
	 * class or package names as prefix.
	 * @param name the unique attribute key
	 * @param value the attribute value to be attached
	 */
	/**
	 * 将{@code  name}定义的属性设置为提供的{@code  value}。 
	 * 如果{@code  value}为{@code  null}，则属性为{@link  #removeAttribute remove}。 
	 *  <p>通常，用户应注意通过使用完全限定的名称（可能使用类或程序包名称作为前缀）来防止与其他元数据属性重叠。 
	 *  
	 * @param name 命名唯一属性键
	 * @param value 要附加的属性值
	 */
	void setAttribute(String name, @Nullable Object value);

	/**
	 * Get the value of the attribute identified by {@code name}.
	 * Return {@code null} if the attribute doesn't exist.
	 * @param name the unique attribute key
	 * @return the current value of the attribute, if any
	 */
	/**
	 * 获取由{@code  name}标识的属性的值。 
	 * 如果属性不存在，则返回{@code  null}。 
	 *  
	 * @param name 命名唯一属性键
	 * @return 属性的当前值（如果有）
	 */
	@Nullable
	Object getAttribute(String name);

	/**
	 * Remove the attribute identified by {@code name} and return its value.
	 * Return {@code null} if no attribute under {@code name} is found.
	 * @param name the unique attribute key
	 * @return the last value of the attribute, if any
	 */
	/**
	 * 删除由{@code  name}标识的属性，并返回其值。 
	 * 如果未找到{@code  name}下的属性，则返回{@code  null}。 
	 *  
	 * @param name 命名唯一属性键
	 * @return 属性的最后一个值（如果有）
	 */
	@Nullable
	Object removeAttribute(String name);

	/**
	 * Return {@code true} if the attribute identified by {@code name} exists.
	 * Otherwise return {@code false}.
	 * @param name the unique attribute key
	 */
	/**
	 * 如果由{@code  name}标识的属性存在，则返回{@code  true}。 
	 * 否则，返回{@code  false}。 
	 *  
	 * @param name 命名唯一属性键
	 */
	boolean hasAttribute(String name);

	/**
	 * Return the names of all attributes.
	 */
	/**
	 * 返回所有属性的名称。 
	 * 
	 */
	String[] attributeNames();

}
