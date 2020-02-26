/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.format.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A formatter for number values in percent style.
 *
 * <p>Delegates to {@link java.text.NumberFormat#getPercentInstance(Locale)}.
 * Configures BigDecimal parsing so there is no loss in precision.
 * The {@link #parse(String, Locale)} routine always returns a BigDecimal.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @since 4.2
 * @see #setLenient
 */
/**
 * 以百分比样式表示的数字值格式。 
 *  <p>代表{@link  java.text.NumberFormat＃getPercentInstance（Locale）}。 
 * 配置BigDecimal解析，因此不会损失精度。 
 *  {@link  #parse（String，Locale）}例程始终返回BigDecimal。 
 *  @author 基思·唐纳德@author  Juergen Hoeller @从4.2起
 * @see  #setLenient
 */
public class PercentStyleFormatter extends AbstractNumberFormatter {

	@Override
	protected NumberFormat getNumberFormat(Locale locale) {
		NumberFormat format = NumberFormat.getPercentInstance(locale);
		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}
		return format;
	}

}
