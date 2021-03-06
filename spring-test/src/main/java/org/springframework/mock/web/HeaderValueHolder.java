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

package org.springframework.mock.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Internal helper class that serves as value holder for request headers.
 *
 * @author Juergen Hoeller
 * @author Rick Evans
 * @since 2.0.1
 */
/**
 * 内部帮助程序类，用作请求标头的值持有者。 
 *  @author  Juergen Hoeller @author 里克·埃文斯（Rick Evans）@2.0.1起
 */
class HeaderValueHolder {

	private final List<Object> values = new LinkedList<>();


	public void setValue(@Nullable Object value) {
		this.values.clear();
		if (value != null) {
			this.values.add(value);
		}
	}

	public void addValue(Object value) {
		this.values.add(value);
	}

	public void addValues(Collection<?> values) {
		this.values.addAll(values);
	}

	public void addValueArray(Object values) {
		CollectionUtils.mergeArrayIntoCollection(values, this.values);
	}

	public List<Object> getValues() {
		return Collections.unmodifiableList(this.values);
	}

	public List<String> getStringValues() {
		List<String> stringList = new ArrayList<>(this.values.size());
		for (Object value : this.values) {
			stringList.add(value.toString());
		}
		return Collections.unmodifiableList(stringList);
	}

	@Nullable
	public Object getValue() {
		return (!this.values.isEmpty() ? this.values.get(0) : null);
	}

	@Nullable
	public String getStringValue() {
		return (!this.values.isEmpty() ? String.valueOf(this.values.get(0)) : null);
	}

	@Override
	public String toString() {
		return this.values.toString();
	}


	/**
	 * Find a HeaderValueHolder by name, ignoring casing.
	 * @param headers the Map of header names to HeaderValueHolders
	 * @param name the name of the desired header
	 * @return the corresponding HeaderValueHolder, or {@code null} if none found
	 * @deprecated as of 5.1.10 in favor of using
	 * {@link org.springframework.util.LinkedCaseInsensitiveMap}.
	 */
	/**
	 * 通过名称查找HeaderValueHolder，而忽略大小写。 
	 *  
	 * @param 标头将标头名称映射到HeaderValueHolders 
	 * @param 命名所需标头的名称
	 * @return 对应的HeaderValueHolder，如果找不到{@@code> null}，则自5.1.10开始不推荐使用倾向于使用{@link  org.springframework.util.LinkedCaseInsensitiveMap}。 
	 * 
	 */
	@Nullable
	@Deprecated
	public static HeaderValueHolder getByName(Map<String, HeaderValueHolder> headers, String name) {
		Assert.notNull(name, "Header name must not be null");
		for (Map.Entry<String, HeaderValueHolder> entry : headers.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(name)) {
				return entry.getValue();
			}
		}
		return null;
	}

}
