/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.env;

import org.springframework.lang.Nullable;

/**
 * Interface for resolving properties against any underlying source.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see Environment
 * @see PropertySourcesPropertyResolver
 */
/**
 * 用于针对任何基础源解析属性的接口。 
 *  @author 克里斯·比姆斯（Chris Beams）@author  Juergen Hoeller @始于3.1 
 * @see 环境
 * @see  PropertySourcesPropertyResolver
 */
public interface PropertyResolver {

	/**
	 * Return whether the given property key is available for resolution,
	 * i.e. if the value for the given key is not {@code null}.
	 */
	/**
	 * 返回给定的属性键是否可用于解析，即给定的键的值不是{@code  null}。 
	 * 
	 */
	boolean containsProperty(String key);

	/**
	 * Return the property value associated with the given key,
	 * or {@code null} if the key cannot be resolved.
	 * @param key the property name to resolve
	 * @see #getProperty(String, String)
	 * @see #getProperty(String, Class)
	 * @see #getRequiredProperty(String)
	 */
	/**
	 * 返回与给定键关联的属性值； 
	 * 如果键无法解析，则返回{@code  null}。 
	 *  
	 * @param 键入要解析的属性名称
	 * @see  #getProperty（String，String）
	 * @see  #getProperty（String，Class）
	 * @see  #getRequiredProperty（String）
	 */
	@Nullable
	String getProperty(String key);

	/**
	 * Return the property value associated with the given key, or
	 * {@code defaultValue} if the key cannot be resolved.
	 * @param key the property name to resolve
	 * @param defaultValue the default value to return if no value is found
	 * @see #getRequiredProperty(String)
	 * @see #getProperty(String, Class)
	 */
	/**
	 * 返回与给定键关联的属性值； 
	 * 如果键无法解析，则返回{@code  defaultValue}。 
	 *  
	 * @param 键入要解析的属性名称
	 * @param  defaultValue如果找不到值，则返回默认值
	 * @see  #getRequiredProperty（String）
	 * @see  #getProperty（String，Class）
	 */
	String getProperty(String key, String defaultValue);

	/**
	 * Return the property value associated with the given key,
	 * or {@code null} if the key cannot be resolved.
	 * @param key the property name to resolve
	 * @param targetType the expected type of the property value
	 * @see #getRequiredProperty(String, Class)
	 */
	/**
	 * 返回与给定键关联的属性值； 
	 * 如果键无法解析，则返回{@code  null}。 
	 *  
	 * @param 键入要解析的属性名称
	 * @param  targetType属性值的预期类型
	 * @see  #getRequiredProperty（String，Class）
	 */
	@Nullable
	<T> T getProperty(String key, Class<T> targetType);

	/**
	 * Return the property value associated with the given key,
	 * or {@code defaultValue} if the key cannot be resolved.
	 * @param key the property name to resolve
	 * @param targetType the expected type of the property value
	 * @param defaultValue the default value to return if no value is found
	 * @see #getRequiredProperty(String, Class)
	 */
	/**
	 * 返回与给定键关联的属性值； 
	 * 如果键无法解析，则返回{@code  defaultValue}。 
	 *  
	 * @param 键入要解析的属性名称
	 * @param  targetType属性值的预期类型
	 * @param  defaultValue如果找不到值，则返回默认值
	 * @see  #getRequiredProperty（String，Class）
	 */
	<T> T getProperty(String key, Class<T> targetType, T defaultValue);

	/**
	 * Return the property value associated with the given key (never {@code null}).
	 * @throws IllegalStateException if the key cannot be resolved
	 * @see #getRequiredProperty(String, Class)
	 */
	/**
	 * 返回与给定键关联的属性值（永不{@code  null}）。 
	 *  
	 * @throws 如果无法解析键，则抛出IllegalStateException 
	 * @see  #getRequiredProperty（String，Class）
	 */
	String getRequiredProperty(String key) throws IllegalStateException;

	/**
	 * Return the property value associated with the given key, converted to the given
	 * targetType (never {@code null}).
	 * @throws IllegalStateException if the given key cannot be resolved
	 */
	/**
	 * 返回与给定键关联的属性值，并转换为给定的targetType（从不<< @code> null}）。 
	 *  
	 * @throws  IllegalStateException如果给定的密钥无法解析
	 */
	<T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

	/**
	 * Resolve ${...} placeholders in the given text, replacing them with corresponding
	 * property values as resolved by {@link #getProperty}. Unresolvable placeholders with
	 * no default value are ignored and passed through unchanged.
	 * @param text the String to resolve
	 * @return the resolved String (never {@code null})
	 * @throws IllegalArgumentException if given text is {@code null}
	 * @see #resolveRequiredPlaceholders
	 * @see org.springframework.util.SystemPropertyUtils#resolvePlaceholders(String)
	 */
	/**
	 * 在给定的文本中解析$ {...}占位符，将其替换为由{@link  #getProperty}解析的相应属性值。 
	 * 没有默认值的无法解析的占位符将被忽略，并按原样传递。 
	 *  
	 * @param 将要解析的字符串文本化为
	 * @return 解析后的字符串（绝不为{@code  null}）
	 * @throws  IllegalArgumentException如果给定的文本为{@code  null} 
	 * @see  #resolveRequiredPlaceholders <@请参见> org.springframework.util.SystemPropertyUtils＃resolvePlaceholders（String）
	 */
	String resolvePlaceholders(String text);

	/**
	 * Resolve ${...} placeholders in the given text, replacing them with corresponding
	 * property values as resolved by {@link #getProperty}. Unresolvable placeholders with
	 * no default value will cause an IllegalArgumentException to be thrown.
	 * @return the resolved String (never {@code null})
	 * @throws IllegalArgumentException if given text is {@code null}
	 * or if any placeholders are unresolvable
	 * @see org.springframework.util.SystemPropertyUtils#resolvePlaceholders(String, boolean)
	 */
	/**
	 * 在给定的文本中解析$ {...}占位符，将其替换为由{@link  #getProperty}解析的相应属性值。 
	 * 没有默认值的无法解析的占位符将导致引发IllegalArgumentException。 
	 *  
	 * @return 解析的字符串（从不{<@@code> null}）
	 * @throws 如果给定的文本为{@code  null}或任何占位符不可解析，则抛出IllegalArgumentException 
	 * @see  org.springframework.util.SystemPropertyUtils #resolvePlaceholders（String，boolean）
	 */
	String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;

}
