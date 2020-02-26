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

package org.springframework.format.datetime.standard;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * {@link Formatter} implementation for a JSR-310 {@link java.time.Instant},
 * following JSR-310's parsing rules for an Instant (that is, not using a
 * configurable {@link java.time.format.DateTimeFormatter}): accepting the
 * default {@code ISO_INSTANT} format as well as {@code RFC_1123_DATE_TIME}
 * (which is commonly used for HTTP date header values), as of Spring 4.3.
 *
 * @author Juergen Hoeller
 * @author Andrei Nevedomskii
 * @since 4.0
 * @see java.time.Instant#parse
 * @see java.time.format.DateTimeFormatter#ISO_INSTANT
 * @see java.time.format.DateTimeFormatter#RFC_1123_DATE_TIME
 */
/**
 * 针对JSR-310 {@link  java.time.Instant}的{@link  Formatter}实现，遵循JSR-310对Instant的解析规则（即，不使用可配置的{@link  java.time .format.DateTimeFormatter}）：从Spring 4.3开始，接受默认的{@code  ISO_INSTANT}格式以及{@code  RFC_1123_DATE_TIME}（通常用于HTTP日期标头值）。 
 *  @author  Juergen Hoeller @author  Andrei Nevedomskii @从4.0起＃RFC_1123_DATE_TIME
 */
public class InstantFormatter implements Formatter<Instant> {

	@Override
	public Instant parse(String text, Locale locale) throws ParseException {
		if (text.length() > 0 && Character.isAlphabetic(text.charAt(0))) {
			// assuming RFC-1123 value a la "Tue, 3 Jun 2008 11:05:30 GMT"
			return Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(text));
		}
		else {
			// assuming UTC instant a la "2007-12-03T10:15:30.00Z"
			return Instant.parse(text);
		}
	}

	@Override
	public String print(Instant object, Locale locale) {
		return object.toString();
	}

}
