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

package org.springframework.validation;

import org.springframework.lang.Nullable;

/**
 * Extended variant of the {@link Validator} interface, adding support for
 * validation 'hints'.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.1
 */
/**
 * {@link  Validator}接口的扩展变体，增加了对验证"提示"的支持。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@author  Sam Brannen @since 3.1
 */
public interface SmartValidator extends Validator {

	/**
	 * Validate the supplied {@code target} object, which must be of a type of {@link Class}
	 * for which the {@link #supports(Class)} method typically returns {@code true}.
	 * <p>The supplied {@link Errors errors} instance can be used to report any
	 * resulting validation errors.
	 * <p><b>This variant of {@code validate()} supports validation hints, such as
	 * validation groups against a JSR-303 provider</b> (in which case, the provided hint
	 * objects need to be annotation arguments of type {@code Class}).
	 * <p>Note: Validation hints may get ignored by the actual target {@code Validator},
	 * in which case this method should behave just like its regular
	 * {@link #validate(Object, Errors)} sibling.
	 * @param target the object that is to be validated
	 * @param errors contextual state about the validation process
	 * @param validationHints one or more hint objects to be passed to the validation engine
	 * @see javax.validation.Validator#validate(Object, Class[])
	 */
	/**
	 * 验证提供的{@code  target}对象，该对象必须为{@link  #supports（Class）}方法通常返回{@code  true}的{@link  Class}类型。 
	 *  。 
	 *  <p>提供的{@link 错误错误}实例可用于报告所有由此产生的验证错误。 
	 *  <p> <b>此{@code  validate（）}的变体支持验证提示，例如针对JSR-303提供程序的验证组</ b>（在这种情况下，提供的提示对象必须是注释参数）类型为{@code  Class}）。 
	 *  <p>注意：验证提示可能会被实际目标{@code  Validator}忽略，在这种情况下，此方法的行为应与常规{{@link> #validate（Object，Errors）}同级一样。 
	 *  
	 * @param 定位要验证的对象
	 * @param 有关验证过程的上下文状态
	 * @param  validationHints将一个或多个提示对象传递给验证引擎
	 * @see  javax.validation.Validator＃ validate（Object，Class []）
	 */
	void validate(Object target, Errors errors, Object... validationHints);

	/**
	 * Validate the supplied value for the specified field on the target type,
	 * reporting the same validation errors as if the value would be bound to
	 * the field on an instance of the target class.
	 * @param targetType the target type
	 * @param fieldName the name of the field
	 * @param value the candidate value
	 * @param errors contextual state about the validation process
	 * @param validationHints one or more hint objects to be passed to the validation engine
	 * @since 5.1
	 * @see javax.validation.Validator#validateValue(Class, String, Object, Class[])
	 */
	/**
	 * 验证目标类型上指定字段的提供值，报告相同的验证错误，就好像该值将绑定到目标类实例上的字段一样。 
	 *  
	 * @param  targetType目标类型
	 * @param  fieldName字段名称
	 * @param 值候选值
	 * @param 错误有关验证过程的上下文状态
	 * @param  validation提示要传递的一个或多个提示对象到验证引擎@since 5.1 
	 * @see  javax.validation.Validator＃validateValue（Class，String，Object，Class []）
	 */
	default void validateValue(
			Class<?> targetType, String fieldName, @Nullable Object value, Errors errors, Object... validationHints) {

		throw new IllegalArgumentException("Cannot validate individual value for " + targetType);
	}

}
