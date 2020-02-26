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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.lang.Nullable;

/**
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Arjen Poutsma
 * @since 1.1.3
 */
/**
 * 杂项收集实用程序方法。 
 * 主要供框架内部使用。 
 *  @author  Juergen Hoeller @author  Rob Harrop @author  Arjen Poutsma @自1.1.3起
 */
public abstract class CollectionUtils {

	/**
	 * Return {@code true} if the supplied Collection is {@code null} or empty.
	 * Otherwise, return {@code false}.
	 * @param collection the Collection to check
	 * @return whether the given Collection is empty
	 */
	/**
	 * 如果提供的集合为{@code  null}或为空，则返回{@code  true}。 
	 * 否则，返回{@code  false}。 
	 *  
	 * @param 收集Collection以检查
	 * @return 给定的Collection是否为空
	 */
	public static boolean isEmpty(@Nullable Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Return {@code true} if the supplied Map is {@code null} or empty.
	 * Otherwise, return {@code false}.
	 * @param map the Map to check
	 * @return whether the given Map is empty
	 */
	/**
	 * 如果提供的Map为{@code  null}或为空，则返回{@code  true}。 
	 * 否则，返回{@code  false}。 
	 *  
	 * @param 映射地图以检查
	 * @return 给定的地图是否为空
	 */
	public static boolean isEmpty(@Nullable Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * Convert the supplied array into a List. A primitive array gets converted
	 * into a List of the appropriate wrapper type.
	 * <p><b>NOTE:</b> Generally prefer the standard {@link Arrays#asList} method.
	 * This {@code arrayToList} method is just meant to deal with an incoming Object
	 * value that might be an {@code Object[]} or a primitive array at runtime.
	 * <p>A {@code null} source value will be converted to an empty List.
	 * @param source the (potentially primitive) array
	 * @return the converted List result
	 * @see ObjectUtils#toObjectArray(Object)
	 * @see Arrays#asList(Object[])
	 */
	/**
	 * 将提供的数组转换为列表。 
	 * 基本数组将转换为适当包装类型的List。 
	 *  <p> <b>注意：</ b>通常首选标准的{@link  Arrays＃asList}方法。 
	 * 此{@code  arrayToList}方法仅用于在运行时处理传入的Object值，该值可能是{@code  Object []}或原始数组。 
	 *  <p>一个{@code  null}源值将被转换为一个空列表。 
	 *  
	 * @param 源于（可能是原始的）数组
	 * @return 转换后的List结果
	 * @see  ObjectUtils＃toObjectArray（Object）
	 * @see  Arrays＃asList（Object []）
	 */
	@SuppressWarnings("rawtypes")
	public static List arrayToList(@Nullable Object source) {
		return Arrays.asList(ObjectUtils.toObjectArray(source));
	}

	/**
	 * Merge the given array into the given Collection.
	 * @param array the array to merge (may be {@code null})
	 * @param collection the target Collection to merge the array into
	 */
	/**
	 * 将给定数组合并到给定Collection中。 
	 *  
	 * @param  array要合并的数组（可以为{@code  null}）
	 * @param 收集目标Collection以将数组合并到
	 */
	@SuppressWarnings("unchecked")
	public static <E> void mergeArrayIntoCollection(@Nullable Object array, Collection<E> collection) {
		Object[] arr = ObjectUtils.toObjectArray(array);
		for (Object elem : arr) {
			collection.add((E) elem);
		}
	}

	/**
	 * Merge the given Properties instance into the given Map,
	 * copying all properties (key-value pairs) over.
	 * <p>Uses {@code Properties.propertyNames()} to even catch
	 * default properties linked into the original Properties instance.
	 * @param props the Properties instance to merge (may be {@code null})
	 * @param map the target Map to merge the properties into
	 */
	/**
	 * 将给定的Properties实例合并到给定的Map中，复制所有属性（键值对）。 
	 *  <p>使用{@code  Properties.propertyNames（）}甚至捕获链接到原始Properties实例的默认属性。 
	 *  
	 * @param 支撑Properties实例合并（可能为{@code  null}）
	 * @param 映射目标Map以将属性合并到
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> void mergePropertiesIntoMap(@Nullable Properties props, Map<K, V> map) {
		if (props != null) {
			for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.get(key);
				if (value == null) {
					// Allow for defaults fallback or potentially overridden accessor...
					value = props.getProperty(key);
				}
				map.put((K) key, (V) value);
			}
		}
	}


	/**
	 * Check whether the given Iterator contains the given element.
	 * @param iterator the Iterator to check
	 * @param element the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	/**
	 * 检查给定的Iterator是否包含给定的元素。 
	 *  
	 * @param 迭代器用于检查
	 * @param 元素以寻找
	 * @return 的迭代器{{@code> true}（如果找到），否则为{@code  false}
	 */
	public static boolean contains(@Nullable Iterator<?> iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (ObjectUtils.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Enumeration contains the given element.
	 * @param enumeration the Enumeration to check
	 * @param element the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	/**
	 * 检查给定的枚举是否包含给定的元素。 
	 *  
	 * @param 枚举枚举以检查
	 * @param 元素以查找
	 * @return  {@code  true}的元素，如果找到，则为{@code  false}
	 */
	public static boolean contains(@Nullable Enumeration<?> enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (ObjectUtils.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check whether the given Collection contains the given element instance.
	 * <p>Enforces the given instance to be present, rather than returning
	 * {@code true} for an equal element as well.
	 * @param collection the Collection to check
	 * @param element the element to look for
	 * @return {@code true} if found, {@code false} otherwise
	 */
	/**
	 * 检查给定的Collection是否包含给定的元素实例。 
	 *  <p>强制存在给定的实例，而不是对相等的元素也返回{@code  true}。 
	 *  
	 * @param 收集Collection以检查
	 * @param 元素以寻找
	 * @return  {@code  true}（如果找到），否则{@code  false}
	 */
	public static boolean containsInstance(@Nullable Collection<?> collection, Object element) {
		if (collection != null) {
			for (Object candidate : collection) {
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return {@code true} if any element in '{@code candidates}' is
	 * contained in '{@code source}'; otherwise returns {@code false}.
	 * @param source the source Collection
	 * @param candidates the candidates to search for
	 * @return whether any of the candidates has been found
	 */
	/**
	 * 如果"{@code 来源}"中包含"{@code 候选}}中的任何元素，则返回{@code  true}； 
	 * 否则返回{@code  false}。 
	 *  
	 * @param 来源来源集合
	 * @param 候选者候选者搜索
	 * @return 是否找到任何候选者
	 */
	public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return false;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the first element in '{@code candidates}' that is contained in
	 * '{@code source}'. If no element in '{@code candidates}' is present in
	 * '{@code source}' returns {@code null}. Iteration order is
	 * {@link Collection} implementation specific.
	 * @param source the source Collection
	 * @param candidates the candidates to search for
	 * @return the first present object, or {@code null} if not found
	 */
	/**
	 * 返回"{@code 源}"中包含的"{@code 候选}}中的第一个元素。 
	 * 如果"{@code 来源}"中不存在"{@code 候选}}中的元素，则返回{@code  null}。 
	 * 迭代顺序是特定于{@link 集合}实现的。 
	 *  
	 * @param 来源来源集合
	 * @param 候选者候选者要搜索
	 * @return 第一个存在的对象，如果找不到，则为{@code  null}
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return null;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return (E) candidate;
			}
		}
		return null;
	}

	/**
	 * Find a single value of the given type in the given Collection.
	 * @param collection the Collection to search
	 * @param type the type to look for
	 * @return a value of the given type found if there is a clear match,
	 * or {@code null} if none or more than one such value found
	 */
	/**
	 * 在给定的集合中找到给定类型的单个值。 
	 *  
	 * @param 收集要搜索的Collection 
	 * @param 键入要查找的类型
	 * @return 如果存在明确匹配，则找到给定类型的值； 
	 * 如果不存在或超过一个，则返回{@code  null}找到一个这样的值
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T findValueOfType(Collection<?> collection, @Nullable Class<T> type) {
		if (isEmpty(collection)) {
			return null;
		}
		T value = null;
		for (Object element : collection) {
			if (type == null || type.isInstance(element)) {
				if (value != null) {
					// More than one value found... no clear single value.
					return null;
				}
				value = (T) element;
			}
		}
		return value;
	}

	/**
	 * Find a single value of one of the given types in the given Collection:
	 * searching the Collection for a value of the first type, then
	 * searching for a value of the second type, etc.
	 * @param collection the collection to search
	 * @param types the types to look for, in prioritized order
	 * @return a value of one of the given types found if there is a clear match,
	 * or {@code null} if none or more than one such value found
	 */
	/**
	 * 在给定的集合中找到给定类型之一的单个值：在集合中搜索第一种类型的值，然后搜索第二种类型的值，依此类推。 
	 * 
	 * @param 收集集合以搜索<
	 * @param >按优先顺序键入要查找的类型，如果有明确的匹配项，则按优先顺序
	 * @return 找到的给定类型之一的值，如果没有找到一个或多个这样的值，则返回{@code  null}
	 */
	@Nullable
	public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
		if (isEmpty(collection) || ObjectUtils.isEmpty(types)) {
			return null;
		}
		for (Class<?> type : types) {
			Object value = findValueOfType(collection, type);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Determine whether the given Collection only contains a single unique object.
	 * @param collection the Collection to check
	 * @return {@code true} if the collection contains a single reference or
	 * multiple references to the same instance, {@code false} otherwise
	 */
	/**
	 * 确定给定的Collection是否仅包含单个唯一对象。 
	 *  
	 * @param 集合的集合以检查
	 * @return  {@code  true}，如果集合包含单个引用或对同一实例的多个引用，否则为{@code  false}
	 */
	public static boolean hasUniqueObject(Collection<?> collection) {
		if (isEmpty(collection)) {
			return false;
		}
		boolean hasCandidate = false;
		Object candidate = null;
		for (Object elem : collection) {
			if (!hasCandidate) {
				hasCandidate = true;
				candidate = elem;
			}
			else if (candidate != elem) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Find the common element type of the given Collection, if any.
	 * @param collection the Collection to check
	 * @return the common element type, or {@code null} if no clear
	 * common type has been found (or the collection was empty)
	 */
	/**
	 * 查找给定Collection的通用元素类型（如果有）。 
	 *  
	 * @param 收集Collection以检查
	 * @return 公共元素类型，如果未找到明确的公共类型（或集合为空），则检查{@code  null}
	 */
	@Nullable
	public static Class<?> findCommonElementType(Collection<?> collection) {
		if (isEmpty(collection)) {
			return null;
		}
		Class<?> candidate = null;
		for (Object val : collection) {
			if (val != null) {
				if (candidate == null) {
					candidate = val.getClass();
				}
				else if (candidate != val.getClass()) {
					return null;
				}
			}
		}
		return candidate;
	}

	/**
	 * Retrieve the first element of the given Set, using {@link SortedSet#first()}
	 * or otherwise using the iterator.
	 * @param set the Set to check (may be {@code null} or empty)
	 * @return the first element, or {@code null} if none
	 * @since 5.2.3
	 * @see SortedSet
	 * @see LinkedHashMap#keySet()
	 * @see java.util.LinkedHashSet
	 */
	/**
	 * 使用{@link  SortedSet＃first（）}或使用迭代器检索给定Set的第一个元素。 
	 *  
	 * @param 设置要检查的Set（可以为{@code  null}或为空）
	 * @return 第一个元素，如果没有，则为{@code  null} @5.2.3起。 
	 * 
	 * @see  SortedSet 
	 * @see  LinkedHashMap＃keySet（）
	 * @see  java.util.LinkedHashSet
	 */
	@Nullable
	public static <T> T firstElement(@Nullable Set<T> set) {
		if (isEmpty(set)) {
			return null;
		}
		if (set instanceof SortedSet) {
			return ((SortedSet<T>) set).first();
		}

		Iterator<T> it = set.iterator();
		T first = null;
		if (it.hasNext()) {
			first = it.next();
		}
		return first;
	}

	/**
	 * Retrieve the first element of the given List, accessing the zero index.
	 * @param list the List to check (may be {@code null} or empty)
	 * @return the first element, or {@code null} if none
	 * @since 5.2.3
	 */
	/**
	 * 检索给定List的第一个元素，访问零索引。 
	 *  
	 * @param 列出要检查的列表（可以为{@code  null}或为空）
	 * @return 第一个元素，如果没有，则为{@code  null} @5.2.3起
	 */
	@Nullable
	public static <T> T firstElement(@Nullable List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * Retrieve the last element of the given Set, using {@link SortedSet#last()}
	 * or otherwise iterating over all elements (assuming a linked set).
	 * @param set the Set to check (may be {@code null} or empty)
	 * @return the last element, or {@code null} if none
	 * @since 5.0.3
	 * @see SortedSet
	 * @see LinkedHashMap#keySet()
	 * @see java.util.LinkedHashSet
	 */
	/**
	 * 使用{@link  SortedSet＃last（）}或以其他方式遍历所有元素（假设有链接集），检索给定Set的最后一个元素。 
	 *  
	 * @param 设置要检查的Set（可以为{@code  null}或为空）
	 * @return 最后一个元素，如果没有，则为{@code  null} @5.0.3起
	 * @see  SortedSet 
	 * @see  LinkedHashMap＃keySet（）
	 * @see  java.util.LinkedHashSet
	 */
	@Nullable
	public static <T> T lastElement(@Nullable Set<T> set) {
		if (isEmpty(set)) {
			return null;
		}
		if (set instanceof SortedSet) {
			return ((SortedSet<T>) set).last();
		}

		// Full iteration necessary...
		Iterator<T> it = set.iterator();
		T last = null;
		while (it.hasNext()) {
			last = it.next();
		}
		return last;
	}

	/**
	 * Retrieve the last element of the given List, accessing the highest index.
	 * @param list the List to check (may be {@code null} or empty)
	 * @return the last element, or {@code null} if none
	 * @since 5.0.3
	 */
	/**
	 * 检索给定列表的最后一个元素，访问最高索引。 
	 *  
	 * @param 列出要检查的列表（可以为{@code  null}或为空）
	 * @return 最后一个元素，如果没有，则为{@code  null} @5.0.3起
	 */
	@Nullable
	public static <T> T lastElement(@Nullable List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	/**
	 * Marshal the elements from the given enumeration into an array of the given type.
	 * Enumeration elements must be assignable to the type of the given array. The array
	 * returned will be a different instance than the array given.
	 */
	/**
	 * 将给定枚举中的元素编组为给定类型的数组。 
	 * 枚举元素必须可分配给给定数组的类型。 
	 * 返回的数组将是与给定数组不同的实例。 
	 * 
	 */
	public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array) {
		ArrayList<A> elements = new ArrayList<>();
		while (enumeration.hasMoreElements()) {
			elements.add(enumeration.nextElement());
		}
		return elements.toArray(array);
	}

	/**
	 * Adapt an {@link Enumeration} to an {@link Iterator}.
	 * @param enumeration the original {@code Enumeration}
	 * @return the adapted {@code Iterator}
	 */
	/**
	 * 将{@link 枚举}修改为{@link 迭代器}。 
	 *  
	 * @param 枚举原始{@code 枚举} 
	 * @return 改编的{@code 迭代器}
	 */
	public static <E> Iterator<E> toIterator(@Nullable Enumeration<E> enumeration) {
		return (enumeration != null ? new EnumerationIterator<>(enumeration) : Collections.emptyIterator());
	}

	/**
	 * Adapt a {@code Map<K, List<V>>} to an {@code MultiValueMap<K, V>}.
	 * @param map the original map
	 * @return the multi-value map
	 * @since 3.1
	 */
	/**
	 * 将{@code  Map <K，List <V >>}调整为{@code  MultiValueMap <K，V>}。 
	 *  
	 * @param 映射原始映射
	 * @return 多值映射@since 3.1
	 */
	public static <K, V> MultiValueMap<K, V> toMultiValueMap(Map<K, List<V>> map) {
		return new MultiValueMapAdapter<>(map);
	}

	/**
	 * Return an unmodifiable view of the specified multi-value map.
	 * @param  map the map for which an unmodifiable view is to be returned.
	 * @return an unmodifiable view of the specified multi-value map.
	 * @since 3.1
	 */
	/**
	 * 返回指定的多值映射的不可修改视图。 
	 *  
	 * @param 映射要为其返回不可修改视图的地图。 
	 *  
	 * @return 指定的多值映射的不可修改视图。 
	 *  @3.1起
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> MultiValueMap<K, V> unmodifiableMultiValueMap(MultiValueMap<? extends K, ? extends V> map) {
		Assert.notNull(map, "'map' must not be null");
		Map<K, List<V>> result = new LinkedHashMap<>(map.size());
		map.forEach((key, value) -> {
			List<? extends V> values = Collections.unmodifiableList(value);
			result.put(key, (List<V>) values);
		});
		Map<K, List<V>> unmodifiableMap = Collections.unmodifiableMap(result);
		return toMultiValueMap(unmodifiableMap);
	}


	/**
	 * Iterator wrapping an Enumeration.
	 */
	/**
	 * 包装有Enumeration的迭代器。 
	 * 
	 */
	private static class EnumerationIterator<E> implements Iterator<E> {

		private final Enumeration<E> enumeration;

		public EnumerationIterator(Enumeration<E> enumeration) {
			this.enumeration = enumeration;
		}

		@Override
		public boolean hasNext() {
			return this.enumeration.hasMoreElements();
		}

		@Override
		public E next() {
			return this.enumeration.nextElement();
		}

		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Not supported");
		}
	}


	/**
	 * Adapts a Map to the MultiValueMap contract.
	 */
	/**
	 * 使Map适应MultiValueMap合同。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class MultiValueMapAdapter<K, V> implements MultiValueMap<K, V>, Serializable {

		private final Map<K, List<V>> map;

		public MultiValueMapAdapter(Map<K, List<V>> map) {
			Assert.notNull(map, "'map' must not be null");
			this.map = map;
		}

		@Override
		@Nullable
		public V getFirst(K key) {
			List<V> values = this.map.get(key);
			return (values != null ? values.get(0) : null);
		}

		@Override
		public void add(K key, @Nullable V value) {
			List<V> values = this.map.computeIfAbsent(key, k -> new LinkedList<>());
			values.add(value);
		}

		@Override
		public void addAll(K key, List<? extends V> values) {
			List<V> currentValues = this.map.computeIfAbsent(key, k -> new LinkedList<>());
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
			this.map.put(key, values);
		}

		@Override
		public void setAll(Map<K, V> values) {
			values.forEach(this::set);
		}

		@Override
		public Map<K, V> toSingleValueMap() {
			LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.map.size());
			this.map.forEach((key, value) -> singleValueMap.put(key, value.get(0)));
			return singleValueMap;
		}

		@Override
		public int size() {
			return this.map.size();
		}

		@Override
		public boolean isEmpty() {
			return this.map.isEmpty();
		}

		@Override
		public boolean containsKey(Object key) {
			return this.map.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return this.map.containsValue(value);
		}

		@Override
		public List<V> get(Object key) {
			return this.map.get(key);
		}

		@Override
		public List<V> put(K key, List<V> value) {
			return this.map.put(key, value);
		}

		@Override
		public List<V> remove(Object key) {
			return this.map.remove(key);
		}

		@Override
		public void putAll(Map<? extends K, ? extends List<V>> map) {
			this.map.putAll(map);
		}

		@Override
		public void clear() {
			this.map.clear();
		}

		@Override
		public Set<K> keySet() {
			return this.map.keySet();
		}

		@Override
		public Collection<List<V>> values() {
			return this.map.values();
		}

		@Override
		public Set<Entry<K, List<V>>> entrySet() {
			return this.map.entrySet();
		}

		@Override
		public boolean equals(@Nullable Object other) {
			if (this == other) {
				return true;
			}
			return this.map.equals(other);
		}

		@Override
		public int hashCode() {
			return this.map.hashCode();
		}

		@Override
		public String toString() {
			return this.map.toString();
		}
	}

}
