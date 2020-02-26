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

package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Property editor for Maps, converting any source Map
 * to a given target Map type.
 *
 * @author Juergen Hoeller
 * @since 2.0.1
 * @see java.util.Map
 * @see java.util.SortedMap
 */
/**
 * Maps的属性编辑器，可将任何源Map转换为给定的目标Map类型。 
 *  @author  Juergen Hoeller @2.0.1起
 * @see  java.util.Map 
 * @see  java.util.SortedMap
 */
public class CustomMapEditor extends PropertyEditorSupport {

	@SuppressWarnings("rawtypes")
	private final Class<? extends Map> mapType;

	private final boolean nullAsEmptyMap;


	/**
	 * Create a new CustomMapEditor for the given target type,
	 * keeping an incoming {@code null} as-is.
	 * @param mapType the target type, which needs to be a
	 * sub-interface of Map or a concrete Map class
	 * @see java.util.Map
	 * @see java.util.HashMap
	 * @see java.util.TreeMap
	 * @see java.util.LinkedHashMap
	 */
	/**
	 * 为给定的目标类型创建一个新的CustomMapEditor，并按原样保留传入的{@code  null}。 
	 *  
	 * @param  mapType目标类型，它必须是Map的子接口或具体的Map类。 
	 * 
	 * @see  java.util.Map 
	 * @see  java.util.HashMap 
	 * @see  java.util。 
	 *  TreeMap 
	 * @see  java.util.LinkedHashMap
	 */
	@SuppressWarnings("rawtypes")
	public CustomMapEditor(Class<? extends Map> mapType) {
		this(mapType, false);
	}

	/**
	 * Create a new CustomMapEditor for the given target type.
	 * <p>If the incoming value is of the given type, it will be used as-is.
	 * If it is a different Map type or an array, it will be converted
	 * to a default implementation of the given Map type.
	 * If the value is anything else, a target Map with that single
	 * value will be created.
	 * <p>The default Map implementations are: TreeMap for SortedMap,
	 * and LinkedHashMap for Map.
	 * @param mapType the target type, which needs to be a
	 * sub-interface of Map or a concrete Map class
	 * @param nullAsEmptyMap ap whether to convert an incoming {@code null}
	 * value to an empty Map (of the appropriate type)
	 * @see java.util.Map
	 * @see java.util.TreeMap
	 * @see java.util.LinkedHashMap
	 */
	/**
	 * 为给定的目标类型创建一个新的CustomMapEditor。 
	 *  <p>如果输入值是给定类型，则将按原样使用它。 
	 * 如果它是其他Map类型或数组，则将其转换为给定Map类型的默认实现。 
	 * 如果该值为其他值，则将创建具有该单个值的目标Map。 
	 *  <p>默认的Map实现是：TreeMap用于SortedMap，而LinkedHashMap用于Map。 
	 *  
	 * @param  mapType目标类型，它需要是Map的子接口或具体的Map类
	 * @param  nullAsEmptyMap ap是否将传入的{@code  null}值转换为空Map（适当的类型）
	 * @see  java.util.Map 
	 * @see  java.util.TreeMap 
	 * @see  java.util.LinkedHashMap
	 */
	@SuppressWarnings("rawtypes")
	public CustomMapEditor(Class<? extends Map> mapType, boolean nullAsEmptyMap) {
		Assert.notNull(mapType, "Map type is required");
		if (!Map.class.isAssignableFrom(mapType)) {
			throw new IllegalArgumentException(
					"Map type [" + mapType.getName() + "] does not implement [java.util.Map]");
		}
		this.mapType = mapType;
		this.nullAsEmptyMap = nullAsEmptyMap;
	}


	/**
	 * Convert the given text value to a Map with a single element.
	 */
	/**
	 * 使用单个元素将给定的文本值转换为Map。 
	 * 
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
	}

	/**
	 * Convert the given value to a Map of the target type.
	 */
	/**
	 * 将给定值转换为目标类型的Map。 
	 * 
	 */
	@Override
	public void setValue(@Nullable Object value) {
		if (value == null && this.nullAsEmptyMap) {
			super.setValue(createMap(this.mapType, 0));
		}
		else if (value == null || (this.mapType.isInstance(value) && !alwaysCreateNewMap())) {
			// Use the source value as-is, as it matches the target type.
			super.setValue(value);
		}
		else if (value instanceof Map) {
			// Convert Map elements.
			Map<?, ?> source = (Map<?, ?>) value;
			Map<Object, Object> target = createMap(this.mapType, source.size());
			source.forEach((key, val) -> target.put(convertKey(key), convertValue(val)));
			super.setValue(target);
		}
		else {
			throw new IllegalArgumentException("Value cannot be converted to Map: " + value);
		}
	}

	/**
	 * Create a Map of the given type, with the given
	 * initial capacity (if supported by the Map type).
	 * @param mapType a sub-interface of Map
	 * @param initialCapacity the initial capacity
	 * @return the new Map instance
	 */
	/**
	 * 创建具有给定初始容量的给定类型的Map（如果Map类型支持）。 
	 *  
	 * @param  mapType Map的子接口
	 * @param  initialCapacity初始容量
	 * @return 新的Map实例
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected Map<Object, Object> createMap(Class<? extends Map> mapType, int initialCapacity) {
		if (!mapType.isInterface()) {
			try {
				return ReflectionUtils.accessibleConstructor(mapType).newInstance();
			}
			catch (Throwable ex) {
				throw new IllegalArgumentException(
						"Could not instantiate map class: " + mapType.getName(), ex);
			}
		}
		else if (SortedMap.class == mapType) {
			return new TreeMap<>();
		}
		else {
			return new LinkedHashMap<>(initialCapacity);
		}
	}

	/**
	 * Return whether to always create a new Map,
	 * even if the type of the passed-in Map already matches.
	 * <p>Default is "false"; can be overridden to enforce creation of a
	 * new Map, for example to convert elements in any case.
	 * @see #convertKey
	 * @see #convertValue
	 */
	/**
	 * 返回是否始终创建一个新的Map，即使传入的Map的类型已经匹配。 
	 *  <p>默认为"false"； 
	 * 可以重写以强制创建新Map，例如在任何情况下都可以转换元素。 
	 *  
	 * @see  #convertKey 
	 * @see  #convertValue
	 */
	protected boolean alwaysCreateNewMap() {
		return false;
	}

	/**
	 * Hook to convert each encountered Map key.
	 * The default implementation simply returns the passed-in key as-is.
	 * <p>Can be overridden to perform conversion of certain keys,
	 * for example from String to Integer.
	 * <p>Only called if actually creating a new Map!
	 * This is by default not the case if the type of the passed-in Map
	 * already matches. Override {@link #alwaysCreateNewMap()} to
	 * enforce creating a new Map in every case.
	 * @param key the source key
	 * @return the key to be used in the target Map
	 * @see #alwaysCreateNewMap
	 */
	/**
	 * 挂钩转换每个遇到的Map键。 
	 * 默认实现只是按原样返回传入的键。 
	 *  <p>可以重写以执行某些键的转换，例如从String到Integer的转换。 
	 *  <p>仅在实际创建新地图时调用！ 
	 * 如果传入的Map的类型已经匹配，则默认情况下不是这种情况。 
	 * 覆盖{@link  #alwaysCreateNewMap（）}以在每种情况下强制创建新的Map。 
	 *  
	 * @param 键源键
	 * @return 在目标Map中使用的键
	 * @see  #alwaysCreateNewMap
	 */
	protected Object convertKey(Object key) {
		return key;
	}

	/**
	 * Hook to convert each encountered Map value.
	 * The default implementation simply returns the passed-in value as-is.
	 * <p>Can be overridden to perform conversion of certain values,
	 * for example from String to Integer.
	 * <p>Only called if actually creating a new Map!
	 * This is by default not the case if the type of the passed-in Map
	 * already matches. Override {@link #alwaysCreateNewMap()} to
	 * enforce creating a new Map in every case.
	 * @param value the source value
	 * @return the value to be used in the target Map
	 * @see #alwaysCreateNewMap
	 */
	/**
	 * 钩子转换每个遇到的Map值。 
	 * 默认实现只是按原样返回传入的值。 
	 *  <p>可以重写以执行某些值的转换，例如从String到Integer的转换。 
	 *  <p>仅在实际创建新地图时调用！ 
	 * 如果传入的Map的类型已经匹配，则默认情况下不是这种情况。 
	 * 覆盖{@link  #alwaysCreateNewMap（）}以在每种情况下强制创建新的Map。 
	 *  
	 * @param 值源值
	 * @return 在目标Map中使用的值
	 * @see  #alwaysCreateNewMap
	 */
	protected Object convertValue(Object value) {
		return value;
	}


	/**
	 * This implementation returns {@code null} to indicate that
	 * there is no appropriate text representation.
	 */
	/**
	 * 此实现返回{@code  null}表示没有适当的文本表示形式。 
	 * 
	 */
	@Override
	@Nullable
	public String getAsText() {
		return null;
	}

}
