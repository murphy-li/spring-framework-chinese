/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2013 the original author or authors.
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
 * 版权所有2002-2013的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.format.datetime.joda;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.format.Parser;

/**
 * Parses Joda {@link org.joda.time.LocalDate} instances using a
 * {@link org.joda.time.format.DateTimeFormatter}.
 *
 * @author Juergen Hoeller
 * @since 4.0
 */
/**
 * 使用{@link  org.joda.time.format.DateTimeFormatter}解析Joda {@link  org.joda.time.LocalDate}实例。 
 *  @author  Juergen Hoeller @始于4.0
 */
public final class LocalDateParser implements Parser<LocalDate> {

	private final DateTimeFormatter formatter;


	/**
	 * Create a new DateTimeParser.
	 * @param formatter the Joda DateTimeFormatter instance
	 */
	/**
	 * 创建一个新的DateTimeParser。 
	 *  
	 * @param 格式化Joda DateTimeFormatter实例
	 */
	public LocalDateParser(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}


	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return JodaTimeContextHolder.getFormatter(this.formatter, locale).parseLocalDate(text);
	}

}
