/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.convert.converter;

import org.springframework.core.convert.TypeDescriptor;

/**
 * Allows a {@link Converter}, {@link GenericConverter} or {@link ConverterFactory} to
 * conditionally execute based on attributes of the {@code source} and {@code target}
 * {@link TypeDescriptor}.
 *
 * <p>Often used to selectively match custom conversion logic based on the presence of a
 * field or class-level characteristic, such as an annotation or method. For example, when
 * converting from a String field to a Date field, an implementation might return
 * {@code true} if the target field has also been annotated with {@code @DateTimeFormat}.
 *
 * <p>As another example, when converting from a String field to an {@code Account} field,
 * an implementation might return {@code true} if the target Account class defines a
 * {@code public static findAccount(String)} method.
 *
 * @author Phillip Webb
 * @author Keith Donald
 * @since 3.2
 * @see Converter
 * @see GenericConverter
 * @see ConverterFactory
 * @see ConditionalGenericConverter
 */
/**
 * 允许{@link 转换器}，{@link  GenericConverter}或{@link  ConverterFactory}根据{@code 源}和{@code 目标}的属性有条件地执行{ @link> TypeDescriptor}。 
 *  <p>通常用于根据字段或类级别特征（例如注释或方法）的存在来选择性地匹配自定义转换逻辑。 
 * 例如，当从字符串字段转换为日期字段时，如果目标字段也已用{@code  @DateTimeFormat}注释，则实现可能返回{@code  true}。 
 *  <p>另一个示例，当从String字段转换为{@code  Account}字段时，如果目标Account类定义了{@code 公共静态findAccount，则实现可能返回{@code  true} （字符串）}方法。 
 *  @author  Phillip Webb @author  Keith Donald @从3.2起
 * @see  Converter 
 * @see  GenericConverter 
 * @see  ConverterFactory 
 * @see  ConditionalGenericConverter
 */
public interface ConditionalConverter {

	/**
	 * Should the conversion from {@code sourceType} to {@code targetType} currently under
	 * consideration be selected?
	 * @param sourceType the type descriptor of the field we are converting from
	 * @param targetType the type descriptor of the field we are converting to
	 * @return true if conversion should be performed, false otherwise
	 */
	/**
	 * 是否应选择当前正在考虑的从{@code  sourceType}到{@code  targetType}的转换？ 
	 * @param  sourceType我们正在转换的字段的类型描述符，从
	 * @param  targetType我们正在转换为
	 * @return 的字段的类型描述符，如果应该执行转换，则为true，否则为false
	 */
	boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);

}
