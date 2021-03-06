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

package org.springframework.beans.factory.support;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.Mergeable;
import org.springframework.lang.Nullable;

/**
 * Tag collection class used to hold managed Map values, which may
 * include runtime bean references (to be resolved into bean objects).
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 27.05.2003
 * @param <K> the key type
 * @param <V> the value type
 */
/**
 * 用于保存托管Map值的标记集合类，其中可能包含运行时Bean引用（将解析为Bean对象）。 
 *  @author  Juergen Hoeller @author  Rob Harrop @2003年5月27日起
 * @param  <K>密钥类型
 * @param  <V>值类型
 */
@SuppressWarnings("serial")
public class ManagedMap<K, V> extends LinkedHashMap<K, V> implements Mergeable, BeanMetadataElement {

	@Nullable
	private Object source;

	@Nullable
	private String keyTypeName;

	@Nullable
	private String valueTypeName;

	private boolean mergeEnabled;


	public ManagedMap() {
	}

	public ManagedMap(int initialCapacity) {
		super(initialCapacity);
	}


	/**
	 * Set the configuration source {@code Object} for this metadata element.
	 * <p>The exact type of the object will depend on the configuration mechanism used.
	 */
	/**
	 * 设置此元数据元素的配置源{@code  Object}。 
	 *  <p>对象的确切类型将取决于所使用的配置机制。 
	 * 
	 */
	public void setSource(@Nullable Object source) {
		this.source = source;
	}

	@Override
	@Nullable
	public Object getSource() {
		return this.source;
	}

	/**
	 * Set the default key type name (class name) to be used for this map.
	 */
	/**
	 * 设置用于此映射的默认密钥类型名称（类名称）。 
	 * 
	 */
	public void setKeyTypeName(@Nullable String keyTypeName) {
		this.keyTypeName = keyTypeName;
	}

	/**
	 * Return the default key type name (class name) to be used for this map.
	 */
	/**
	 * 返回用于此映射的默认键类型名称（类名称）。 
	 * 
	 */
	@Nullable
	public String getKeyTypeName() {
		return this.keyTypeName;
	}

	/**
	 * Set the default value type name (class name) to be used for this map.
	 */
	/**
	 * 设置要用于此映射的默认值类型名称（类名称）。 
	 * 
	 */
	public void setValueTypeName(@Nullable String valueTypeName) {
		this.valueTypeName = valueTypeName;
	}

	/**
	 * Return the default value type name (class name) to be used for this map.
	 */
	/**
	 * 返回用于此映射的默认值类型名称（类名称）。 
	 * 
	 */
	@Nullable
	public String getValueTypeName() {
		return this.valueTypeName;
	}

	/**
	 * Set whether merging should be enabled for this collection,
	 * in case of a 'parent' collection value being present.
	 */
	/**
	 * 设置在存在"父"集合值的情况下是否应对此集合启用合并。 
	 * 
	 */
	public void setMergeEnabled(boolean mergeEnabled) {
		this.mergeEnabled = mergeEnabled;
	}

	@Override
	public boolean isMergeEnabled() {
		return this.mergeEnabled;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object merge(@Nullable Object parent) {
		if (!this.mergeEnabled) {
			throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
		}
		if (parent == null) {
			return this;
		}
		if (!(parent instanceof Map)) {
			throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
		}
		Map<K, V> merged = new ManagedMap<>();
		merged.putAll((Map<K, V>) parent);
		merged.putAll(this);
		return merged;
	}

}
