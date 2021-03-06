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

package org.springframework.core.codec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import org.springframework.lang.Nullable;

/**
 * Constants and convenience methods for working with hints.
 *
 * @author Rossen Stoyanchev
 * @since 5.1
 * @see ResourceRegionEncoder#BOUNDARY_STRING_HINT
 */
/**
 * 使用提示的常量和便捷方法。 
 *  @author  Rossen Stoyanchev @从5.1起
 * @see  ResourceRegionEncoder＃BOUNDARY_STRING_HINT
 */
public abstract class Hints {

	/**
	 * Name of hint exposing a prefix to use for correlating log messages.
	 */
	/**
	 * 提示的名称，该提示暴露了用于关联日志消息的前缀。 
	 * 
	 */
	public static final String LOG_PREFIX_HINT = Log.class.getName() + ".PREFIX";

	/**
	 * Name of boolean hint whether to avoid logging data either because it's
	 * potentially sensitive, or because it has been logged by a composite
	 * encoder, e.g. for multipart requests.
	 */
	/**
	 * 布尔名称的名称，用于提示是否要避免记录数据，要么是因为它可能是敏感的，要么是因为它已由复合编码器记录下来，例如多部分请求。 
	 * 
	 */
	public static final String SUPPRESS_LOGGING_HINT = Log.class.getName() + ".SUPPRESS_LOGGING";


	/**
	 * Create a map wit a single hint via {@link Collections#singletonMap}.
	 * @param hintName the hint name
	 * @param value the hint value
	 * @return the created map
	 */
	/**
	 * 通过{@link  Collections＃singletonMap}创建带有单个提示的地图。 
	 *  
	 * @param  hintName提示名称
	 * @param 值提示值
	 * @return 创建的映射
	 */
	public static Map<String, Object> from(String hintName, Object value) {
		return Collections.singletonMap(hintName, value);
	}

	/**
	 * Return an empty map of hints via {@link Collections#emptyMap()}.
	 * @return the empty map
	 */
	/**
	 * 通过{@link  Collections＃emptyMap（）}返回一个空的提示映射。 
	 *  
	 * @return 空图
	 */
	public static Map<String, Object> none() {
		return Collections.emptyMap();
	}

	/**
	 * Obtain the value for a required hint.
	 * @param hints the hints map
	 * @param hintName the required hint name
	 * @param <T> the hint type to cast to
	 * @return the hint value
	 * @throws IllegalArgumentException if the hint is not found
	 */
	/**
	 * 获取所需提示的值。 
	 *  
	 * @param 提示提示映射
	 * @param  hintName必需的提示名称
	 * @param  <T>提示类型强制转换为
	 * @return 提示值
	 * @throws  IllegalArgumentException如果找不到提示
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRequiredHint(@Nullable Map<String, Object> hints, String hintName) {
		if (hints == null) {
			throw new IllegalArgumentException("No hints map for required hint '" + hintName + "'");
		}
		T hint = (T) hints.get(hintName);
		if (hint == null) {
			throw new IllegalArgumentException("Hints map must contain the hint '" + hintName + "'");
		}
		return hint;
	}

	/**
	 * Obtain the hint {@link #LOG_PREFIX_HINT}, if present, or an empty String.
	 * @param hints the hints passed to the encode method
	 * @return the log prefix
	 */
	/**
	 * 获取提示{@link  #LOG_PREFIX_HINT}（如果存在）或空字符串。 
	 *  
	 * @param 提示传递给编码方法的提示
	 * @return 日志前缀
	 */
	public static String getLogPrefix(@Nullable Map<String, Object> hints) {
		return (hints != null ? (String) hints.getOrDefault(LOG_PREFIX_HINT, "") : "");
	}

	/**
	 * Whether to suppress logging based on the hint {@link #SUPPRESS_LOGGING_HINT}.
	 * @param hints the hints map
	 * @return whether logging of data is allowed
	 */
	/**
	 * 是否基于提示{@link  #SUPPRESS_LOGGING_HINT}禁止记录。 
	 *  
	 * @param 提示提示映射
	 * @return 是否允许记录数据
	 */
	public static boolean isLoggingSuppressed(@Nullable Map<String, Object> hints) {
		return (hints != null && (boolean) hints.getOrDefault(SUPPRESS_LOGGING_HINT, false));
	}

	/**
	 * Merge two maps of hints, creating and copying into a new map if both have
	 * values, or returning the non-empty map, or an empty map if both are empty.
	 * @param hints1 1st map of hints
	 * @param hints2 2nd map of hints
	 * @return a single map with hints from both
	 */
	/**
	 * 合并两个提示映射，如果两个都有值，则创建并复制到新映射中，或者返回非空映射，如果两个都为空则返回一个空映射。 
	 *  
	 * @param  hints1第一个提示映射
	 * @param  hints2第二个提示映射
	 * @return 包含两个提示的单个映射
	 */
	public static Map<String, Object> merge(Map<String, Object> hints1, Map<String, Object> hints2) {
		if (hints1.isEmpty() && hints2.isEmpty()) {
			return Collections.emptyMap();
		}
		else if (hints2.isEmpty()) {
			return hints1;
		}
		else if (hints1.isEmpty()) {
			return hints2;
		}
		else {
			Map<String, Object> result = new HashMap<>(hints1.size() + hints2.size());
			result.putAll(hints1);
			result.putAll(hints2);
			return result;
		}
	}

	/**
	 * Merge a single hint into a map of hints, possibly creating and copying
	 * all hints into a new map, or otherwise if the map of hints is empty,
	 * creating a new single entry map.
	 * @param hints a map of hints to be merge
	 * @param hintName the hint name to merge
	 * @param hintValue the hint value to merge
	 * @return a single map with all hints
	 */
	/**
	 * 将单个提示合并到提示映射中，可能会创建所有提示并将其复制到新映射中； 
	 * 否则，如果提示映射为空，则会创建一个新的单个条目映射。 
	 *  
	 * @param 提示要合并的提示映射
	 * @param  hintName要合并的提示名称
	 * @param  hintValue提示要合并的提示值
	 * @return 具有所有提示的单个映射
	 */
	public static Map<String, Object> merge(Map<String, Object> hints, String hintName, Object hintValue) {
		if (hints.isEmpty()) {
			return Collections.singletonMap(hintName, hintValue);
		}
		else {
			Map<String, Object> result = new HashMap<>(hints.size() + 1);
			result.putAll(hints);
			result.put(hintName, hintValue);
			return result;
		}
	}

}
