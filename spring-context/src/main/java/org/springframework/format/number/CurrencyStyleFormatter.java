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

package org.springframework.format.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import org.springframework.lang.Nullable;

/**
 * A BigDecimal formatter for number values in currency style.
 *
 * <p>Delegates to {@link java.text.NumberFormat#getCurrencyInstance(Locale)}.
 * Configures BigDecimal parsing so there is no loss of precision.
 * Can apply a specified {@link java.math.RoundingMode} to parsed values.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @since 4.2
 * @see #setLenient
 * @see #setRoundingMode
 */
/**
 * 一个BigDecimal格式化程序，用于以货币样式表示数字值。 
 *  <p>代表{@link  java.text.NumberFormat＃getCurrencyInstance（Locale）}。 
 * 配置BigDecimal解析，因此不会损失精度。 
 * 可以将指定的{@link  java.math.RoundingMode}应用于已解析的值。 
 *  @author 基思·唐纳德（Keith Donald）@author 于尔根·霍勒（Juergen Hoeller）@从4.2开始
 * @see  #setLenient 
 * @see  #setRoundingMode
 */
public class CurrencyStyleFormatter extends AbstractNumberFormatter {

	private int fractionDigits = 2;

	@Nullable
	private RoundingMode roundingMode;

	@Nullable
	private Currency currency;

	@Nullable
	private String pattern;


	/**
	 * Specify the desired number of fraction digits.
	 * Default is 2.
	 */
	/**
	 * 指定所需的小数位数。 
	 * 默认值为2。 
	 * 
	 */
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	/**
	 * Specify the rounding mode to use for decimal parsing.
	 * Default is {@link java.math.RoundingMode#UNNECESSARY}.
	 */
	/**
	 * 指定用于十进制分析的舍入模式。 
	 * 默认值为{@link  java.math.RoundingMode＃UNNECESSARY}。 
	 * 
	 */
	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	/**
	 * Specify the currency, if known.
	 */
	/**
	 * 指定货币（如果知道）。 
	 * 
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Specify the pattern to use to format number values.
	 * If not specified, the default DecimalFormat pattern is used.
	 * @see java.text.DecimalFormat#applyPattern(String)
	 */
	/**
	 * 指定用于格式化数字值的模式。 
	 * 如果未指定，则使用默认的DecimalFormat模式。 
	 *  
	 * @see  java.text.DecimalFormat＃applyPattern（String）
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	@Override
	public BigDecimal parse(String text, Locale locale) throws ParseException {
		BigDecimal decimal = (BigDecimal) super.parse(text, locale);
		if (this.roundingMode != null) {
			decimal = decimal.setScale(this.fractionDigits, this.roundingMode);
		}
		else {
			decimal = decimal.setScale(this.fractionDigits);
		}
		return decimal;
	}

	@Override
	protected NumberFormat getNumberFormat(Locale locale) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		format.setParseBigDecimal(true);
		format.setMaximumFractionDigits(this.fractionDigits);
		format.setMinimumFractionDigits(this.fractionDigits);
		if (this.roundingMode != null) {
			format.setRoundingMode(this.roundingMode);
		}
		if (this.currency != null) {
			format.setCurrency(this.currency);
		}
		if (this.pattern != null) {
			format.applyPattern(this.pattern);
		}
		return format;
	}

}
