/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.expression;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;

/**
 * A type converter can convert values between different types encountered during
 * expression evaluation. This is an SPI for the expression parser; see
 * {@link org.springframework.core.convert.ConversionService} for the primary
 * user API to Spring's conversion facilities.
 *
 * @author Andy Clement
 * @author Juergen Hoeller
 * @since 3.0
 */
/**
 * 类型转换器可以在表达式求值期间遇到的不同类型之间转换值。 
 * 这是表达式解析器的SPI。 
 * 请参阅{@link  org.springframework.core.convert.ConversionService}了解Spring转换工具的主要用户API。 
 *  @author 安迪·克莱门特@author  Juergen Hoeller @从3.0开始
 */
public interface TypeConverter {

	/**
	 * Return {@code true} if the type converter can convert the specified type
	 * to the desired target type.
	 * @param sourceType a type descriptor that describes the source type
	 * @param targetType a type descriptor that describes the requested result type
	 * @return {@code true} if that conversion can be performed
	 */
	/**
	 * 如果类型转换器可以将指定的类型转换为所需的目标类型，则返回{@code  true}。 
	 *  
	 * @param  sourceType一个描述源类型的类型描述符
	 * @param  targetType一个描述所请求的结果类型的类型描述符
	 * @return  {@code  true}如果可以执行转换
	 */
	boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType);

	/**
	 * Convert (or coerce) a value from one type to another, for example from a
	 * {@code boolean} to a {@code String}.
	 * <p>The {@link TypeDescriptor} parameters enable support for typed collections:
	 * A caller may prefer a {@code List&lt;Integer&gt;}, for example, rather than
	 * simply any {@code List}.
	 * @param value the value to be converted
	 * @param sourceType a type descriptor that supplies extra information about the
	 * source object
	 * @param targetType a type descriptor that supplies extra information about the
	 * requested result type
	 * @return the converted value
	 * @throws EvaluationException if conversion failed or is not possible to begin with
	 */
	/**
	 * 将值从一种类型转换（或强制）为另一种类型，例如从{@code 布尔型}转换为{@code 字符串}。 
	 *  <p> {<@link> TypeDescriptor}参数启用对类型化集合的支持：例如，调用者可能更喜欢{@code  List <Integer>}，而不是简单的任何{@code  List}。 
	 *  
	 * @param 值要转换的值
	 * @param  sourceType提供有关源对象的额外信息的类型描述符
	 * @param  targetType提供有关请求的结果类型的额外信息的类型描述符
	 * @return 转换后的值
	 * @throws  EvaluationException如果转换失败或无法以
	 */
	@Nullable
	Object convertValue(@Nullable Object value, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType);

}
