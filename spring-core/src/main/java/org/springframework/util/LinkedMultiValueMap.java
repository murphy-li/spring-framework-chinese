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

package org.springframework.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.lang.Nullable;

/**
 * Simple implementation of {@link MultiValueMap} that wraps a {@link LinkedHashMap},
 * storing multiple values in a {@link LinkedList}.
 *
 * <p>This Map implementation is generally not thread-safe. It is primarily designed
 * for data structures exposed from request objects, for use in a single thread only.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 * @param <K> the key type
 * @param <V> the value element type
 */
/**
 * {@link  MultiValueMap}的简单实现，它包装了一个{@link  LinkedHashMap}，并在{@link  LinkedList}中存储了多个值。 
 *  <p>此Map实现通常不是线程安全的。 
 * 它主要用于从请求对象公开的数据结构，仅在单个线程中使用。 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @since 3.0 
 * @param  <K>键类型
 * @param  <V>值元​​素类型
 */
public class LinkedMultiValueMap<K, V> implements MultiValueMap<K, V>, Serializable, Cloneable {

	private static final long serialVersionUID = 3801124242820219131L;

	private final Map<K, List<V>> targetMap;


	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}.
	 */
	/**
	 * 创建一个包装了{@link  LinkedHashMap}的新LinkedMultiValueMap。 
	 * 
	 */
	public LinkedMultiValueMap() {
		this.targetMap = new LinkedHashMap<>();
	}

	/**
	 * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}
	 * with the given initial capacity.
	 * @param initialCapacity the initial capacity
	 */
	/**
	 * 创建一个新的LinkedMultiValueMap，它包装具有给定初始容量的{@link  LinkedHashMap}。 
	 *  
	 * @param  initialCapacity初始容量
	 */
	public LinkedMultiValueMap(int initialCapacity) {
		this.targetMap = new LinkedHashMap<>(initialCapacity);
	}

	/**
	 * Copy constructor: Create a new LinkedMultiValueMap with the same mappings as
	 * the specified Map. Note that this will be a shallow copy; its value-holding
	 * List entries will get reused and therefore cannot get modified independently.
	 * @param otherMap the Map whose mappings are to be placed in this Map
	 * @see #clone()
	 * @see #deepCopy()
	 */
	/**
	 * 复制构造函数：使用与指定Map相同的映射创建一个新的LinkedMultiValueMap。 
	 * 注意这将是一个浅表副本； 
	 * 其值保留列表条目将被重用，因此无法独立进行修改。 
	 *  
	 * @param  otherMap要在其上放置其映射的Map 
	 * @see  #clone（）
	 * @see  #deepCopy（）
	 */
	public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
		this.targetMap = new LinkedHashMap<>(otherMap);
	}


	// MultiValueMap implementation

	@Override
	@Nullable
	public V getFirst(K key) {
		List<V> values = this.targetMap.get(key);
		return (values != null && !values.isEmpty() ? values.get(0) : null);
	}

	@Override
	public void add(K key, @Nullable V value) {
		List<V> values = this.targetMap.computeIfAbsent(key, k -> new LinkedList<>());
		values.add(value);
	}

	@Override
	public void addAll(K key, List<? extends V> values) {
		List<V> currentValues = this.targetMap.computeIfAbsent(key, k -> new LinkedList<>());
		currentValues.addAll(values);
	}

	@Override
	public void addAll(MultiValueMap<K, V> values) {
		for (Entry<K, List<V>> entry : values.entrySet()) {
			addAll(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void set(K key, @Nullable V value) {
		List<V> values = new LinkedList<>();
		values.add(value);
		this.targetMap.put(key, values);
	}

	@Override
	public void setAll(Map<K, V> values) {
		values.forEach(this::set);
	}

	@Override
	public Map<K, V> toSingleValueMap() {
		LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.targetMap.size());
		this.targetMap.forEach((key, values) -> {
			if (values != null && !values.isEmpty()) {
				singleValueMap.put(key, values.get(0));
			}
		});
		return singleValueMap;
	}


	// Map implementation

	@Override
	public int size() {
		return this.targetMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.targetMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.targetMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.targetMap.containsValue(value);
	}

	@Override
	@Nullable
	public List<V> get(Object key) {
		return this.targetMap.get(key);
	}

	@Override
	@Nullable
	public List<V> put(K key, List<V> value) {
		return this.targetMap.put(key, value);
	}

	@Override
	@Nullable
	public List<V> remove(Object key) {
		return this.targetMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends List<V>> map) {
		this.targetMap.putAll(map);
	}

	@Override
	public void clear() {
		this.targetMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return this.targetMap.keySet();
	}

	@Override
	public Collection<List<V>> values() {
		return this.targetMap.values();
	}

	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return this.targetMap.entrySet();
	}


	/**
	 * Create a deep copy of this Map.
	 * @return a copy of this Map, including a copy of each value-holding List entry
	 * (consistently using an independent modifiable {@link LinkedList} for each entry)
	 * along the lines of {@code MultiValueMap.addAll} semantics
	 * @since 4.2
	 * @see #addAll(MultiValueMap)
	 * @see #clone()
	 */
	/**
	 * 创建此地图的深层副本。 
	 *  
	 * @return 此Map的副本，包括每个值持有列表条目的副本（每个条目始终使用独立的可修改的{@link  LinkedList}），并沿{@code  MultiValueMap.addAll}语义@since 4.2 
	 * @see  #addAll（MultiValueMap）
	 * @see  #clone（）
	 */
	public LinkedMultiValueMap<K, V> deepCopy() {
		LinkedMultiValueMap<K, V> copy = new LinkedMultiValueMap<>(this.targetMap.size());
		this.targetMap.forEach((key, value) -> copy.put(key, new LinkedList<>(value)));
		return copy;
	}

	/**
	 * Create a regular copy of this Map.
	 * @return a shallow copy of this Map, reusing this Map's value-holding List entries
	 * (even if some entries are shared or unmodifiable) along the lines of standard
	 * {@code Map.put} semantics
	 * @since 4.2
	 * @see #put(Object, List)
	 * @see #putAll(Map)
	 * @see LinkedMultiValueMap#LinkedMultiValueMap(Map)
	 * @see #deepCopy()
	 */
	/**
	 * 创建此地图的常规副本。 
	 *  
	 * @return 此Map的浅表副本，按照标准{@code  Map.put}语义@4.2起重复使用此Map的值持有列表条目（即使某些条目是共享的或不可修改的）。 
	 *  > #put（Object，List）
	 * @see  #putAll（Map）
	 * @see  LinkedMultiValueMap＃LinkedMultiValueMap（Map）
	 * @see  #deepCopy（）
	 */
	@Override
	public LinkedMultiValueMap<K, V> clone() {
		return new LinkedMultiValueMap<>(this);
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		return this.targetMap.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.targetMap.hashCode();
	}

	@Override
	public String toString() {
		return this.targetMap.toString();
	}

}
