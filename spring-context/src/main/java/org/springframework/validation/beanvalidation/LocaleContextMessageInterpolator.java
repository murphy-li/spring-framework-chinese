/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.validation.beanvalidation;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

/**
 * Delegates to a target {@link MessageInterpolator} implementation but enforces Spring's
 * managed Locale. Typically used to wrap the validation provider's default interpolator.
 *
 * @author Juergen Hoeller
 * @since 3.0
 * @see org.springframework.context.i18n.LocaleContextHolder#getLocale()
 */
/**
 * 委托给目标{@link  MessageInterpolator}实现，但强制执行Spring的托管语言环境。 
 * 通常用于包装验证提供程序的默认插值器。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@从3.0开始
 * @see  org.springframework.context.i18n.LocaleContextHolder＃getLocale（）
 */
public class LocaleContextMessageInterpolator implements MessageInterpolator {

	private final MessageInterpolator targetInterpolator;


	/**
	 * Create a new LocaleContextMessageInterpolator, wrapping the given target interpolator.
	 * @param targetInterpolator the target MessageInterpolator to wrap
	 */
	/**
	 * 创建一个新的LocaleContextMessageInterpolator，包装给定的目标插值器。 
	 *  
	 * @param  targetInterpolator要包装的目标MessageInterpolator
	 */
	public LocaleContextMessageInterpolator(MessageInterpolator targetInterpolator) {
		Assert.notNull(targetInterpolator, "Target MessageInterpolator must not be null");
		this.targetInterpolator = targetInterpolator;
	}


	@Override
	public String interpolate(String message, Context context) {
		return this.targetInterpolator.interpolate(message, context, LocaleContextHolder.getLocale());
	}

	@Override
	public String interpolate(String message, Context context, Locale locale) {
		return this.targetInterpolator.interpolate(message, context, locale);
	}

}
