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

package org.springframework.format.datetime.joda;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MonthDay;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Configures Joda-Time's formatting system for use with Spring.
 *
 * <p><b>NOTE:</b> Spring's Joda-Time support requires Joda-Time 2.x, as of Spring 4.0.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @author Phillip Webb
 * @since 3.1
 * @see #setDateStyle
 * @see #setTimeStyle
 * @see #setDateTimeStyle
 * @see #setUseIsoFormat
 * @see FormatterRegistrar#registerFormatters
 * @see org.springframework.format.datetime.DateFormatterRegistrar
 * @see DateTimeFormatterFactoryBean
 */
/**
 * 配置Joda-Time的格式化系统以与Spring一起使用。 
 *  <p> <b>注意：</ b>从Spring 4.0开始，Spring的Joda-Time支持需要Joda-Time2.x。 
 *  @author 基思·唐纳德@author  Juergen Hoeller @author  Phillip Webb @since 3.1 
 * @see  #setDateStyle 
 * @see  #setTimeStyle 
 * @see  #setDateTimeStyle 
 * @see  #setUseIsoFormat 
 * @see  FormatterRegistrar #registerFormatters 
 * @see  org.springframework.format.datetime.DateFormatterRegistrar 
 * @see  DateTimeFormatterFactoryBean
 */
public class JodaTimeFormatterRegistrar implements FormatterRegistrar {

	private enum Type {DATE, TIME, DATE_TIME}


	/**
	 * User defined formatters.
	 */
	/**
	 * 用户定义的格式化程序。 
	 * 
	 */
	private final Map<Type, DateTimeFormatter> formatters = new EnumMap<>(Type.class);

	/**
	 * Factories used when specific formatters have not been specified.
	 */
	/**
	 * 未指定特定格式化程序时使用的工厂。 
	 * 
	 */
	private final Map<Type, DateTimeFormatterFactory> factories;


	public JodaTimeFormatterRegistrar() {
		this.factories = new EnumMap<>(Type.class);
		for (Type type : Type.values()) {
			this.factories.put(type, new DateTimeFormatterFactory());
		}
	}


	/**
	 * Set whether standard ISO formatting should be applied to all date/time types.
	 * Default is "false" (no).
	 * <p>If set to "true", the "dateStyle", "timeStyle" and "dateTimeStyle"
	 * properties are effectively ignored.
	 */
	/**
	 * 设置是否将标准ISO格式应用于所有日期/时间类型。 
	 * 默认值为"false"（否）。 
	 *  <p>如果设置为"true"，则将有效地忽略"dateStyle"，"timeStyle"和"dateTimeStyle"属性。 
	 * 
	 */
	public void setUseIsoFormat(boolean useIsoFormat) {
		this.factories.get(Type.DATE).setIso(useIsoFormat ? ISO.DATE : ISO.NONE);
		this.factories.get(Type.TIME).setIso(useIsoFormat ? ISO.TIME : ISO.NONE);
		this.factories.get(Type.DATE_TIME).setIso(useIsoFormat ? ISO.DATE_TIME : ISO.NONE);
	}

	/**
	 * Set the default format style of Joda {@link LocalDate} objects.
	 * Default is {@link DateTimeFormat#shortDate()}.
	 */
	/**
	 * 设置Joda {@link  LocalDate}对象的默认格式样式。 
	 * 默认值为{@link  DateTimeFormat＃shortDate（）}。 
	 * 
	 */
	public void setDateStyle(String dateStyle) {
		this.factories.get(Type.DATE).setStyle(dateStyle + "-");
	}

	/**
	 * Set the default format style of Joda {@link LocalTime} objects.
	 * Default is {@link DateTimeFormat#shortTime()}.
	 */
	/**
	 * 设置Joda {@link  LocalTime}对象的默认格式样式。 
	 * 默认值为{@link  DateTimeFormat＃shortTime（）}。 
	 * 
	 */
	public void setTimeStyle(String timeStyle) {
		this.factories.get(Type.TIME).setStyle("-" + timeStyle);
	}

	/**
	 * Set the default format style of Joda {@link LocalDateTime} and {@link DateTime} objects,
	 * as well as JDK {@link Date} and {@link Calendar} objects.
	 * Default is {@link DateTimeFormat#shortDateTime()}.
	 */
	/**
	 * 设置Joda {@link  LocalDateTime}和{@link  DateTime}对象以及JDK {@link  Date}和{@link  Calendar}对象的默认格式样式。 
	 * 默认值为{@link  DateTimeFormat＃shortDateTime（）}。 
	 * 
	 */
	public void setDateTimeStyle(String dateTimeStyle) {
		this.factories.get(Type.DATE_TIME).setStyle(dateTimeStyle);
	}

	/**
	 * Set the formatter that will be used for objects representing date values.
	 * <p>This formatter will be used for the {@link LocalDate} type. When specified
	 * the {@link #setDateStyle(String) dateStyle} and
	 * {@link #setUseIsoFormat(boolean) useIsoFormat} properties will be ignored.
	 * @param formatter the formatter to use
	 * @since 3.2
	 * @see #setTimeFormatter
	 * @see #setDateTimeFormatter
	 */
	/**
	 * 设置将用于表示日期值的对象的格式程序。 
	 *  <p>此格式化程序将用于{@link  LocalDate}类型。 
	 * 指定后，将忽略{@link  #setDateStyle（String）dateStyle}和{@link  #setUseIsoFormat（boolean）useIsoFormat}属性。 
	 *  
	 * @param 格式化程序格式化程序要使用@since 3.2 
	 * @see  #setTimeFormatter 
	 * @see  #setDateTimeFormatter
	 */
	public void setDateFormatter(DateTimeFormatter formatter) {
		this.formatters.put(Type.DATE, formatter);
	}

	/**
	 * Set the formatter that will be used for objects representing time values.
	 * <p>This formatter will be used for the {@link LocalTime} type. When specified
	 * the {@link #setTimeStyle(String) timeStyle} and
	 * {@link #setUseIsoFormat(boolean) useIsoFormat} properties will be ignored.
	 * @param formatter the formatter to use
	 * @since 3.2
	 * @see #setDateFormatter
	 * @see #setDateTimeFormatter
	 */
	/**
	 * 设置将用于表示时间值的对象的格式化程序。 
	 *  <p>此格式化程序将用于{@link  LocalTime}类型。 
	 * 指定后，{@link  #setTimeStyle（String）timeStyle}和{@link  #setUseIsoFormat（boolean）useIsoFormat}属性将被忽略。 
	 *  
	 * @param 格式化程序格式化程序要使用@since 3.2 
	 * @see  #setDateFormatter 
	 * @see  #setDateTimeFormatter
	 */
	public void setTimeFormatter(DateTimeFormatter formatter) {
		this.formatters.put(Type.TIME, formatter);
	}

	/**
	 * Set the formatter that will be used for objects representing date and time values.
	 * <p>This formatter will be used for {@link LocalDateTime}, {@link ReadableInstant},
	 * {@link Date} and {@link Calendar} types. When specified
	 * the {@link #setDateTimeStyle(String) dateTimeStyle} and
	 * {@link #setUseIsoFormat(boolean) useIsoFormat} properties will be ignored.
	 * @param formatter the formatter to use
	 * @since 3.2
	 * @see #setDateFormatter
	 * @see #setTimeFormatter
	 */
	/**
	 * 设置将用于表示日期和时间值的对象的格式化程序。 
	 *  <p>此格式化程序将用于{@link  LocalDateTime}，{<@link> ReadableInstant}，{<@link> Date}和{@link  Calendar}类型。 
	 * 指定后，将忽略{@link  #setDateTimeStyle（String）dateTimeStyle}和{@link  #setUseIsoFormat（boolean）useIsoFormat}属性。 
	 *  
	 * @param 格式化程序格式化程序要使用@since 3.2 
	 * @see  #setDateFormatter 
	 * @see  #setTimeFormatter
	 */
	public void setDateTimeFormatter(DateTimeFormatter formatter) {
		this.formatters.put(Type.DATE_TIME, formatter);
	}


	@Override
	public void registerFormatters(FormatterRegistry registry) {
		JodaTimeConverters.registerConverters(registry);

		DateTimeFormatter dateFormatter = getFormatter(Type.DATE);
		DateTimeFormatter timeFormatter = getFormatter(Type.TIME);
		DateTimeFormatter dateTimeFormatter = getFormatter(Type.DATE_TIME);

		addFormatterForFields(registry,
				new ReadablePartialPrinter(dateFormatter),
				new LocalDateParser(dateFormatter),
				LocalDate.class);

		addFormatterForFields(registry,
				new ReadablePartialPrinter(timeFormatter),
				new LocalTimeParser(timeFormatter),
				LocalTime.class);

		addFormatterForFields(registry,
				new ReadablePartialPrinter(dateTimeFormatter),
				new LocalDateTimeParser(dateTimeFormatter),
				LocalDateTime.class);

		addFormatterForFields(registry,
				new ReadableInstantPrinter(dateTimeFormatter),
				new DateTimeParser(dateTimeFormatter),
				ReadableInstant.class);

		// In order to retain backwards compatibility we only register Date/Calendar
		// types when a user defined formatter is specified (see SPR-10105)
		if (this.formatters.containsKey(Type.DATE_TIME)) {
			addFormatterForFields(registry,
					new ReadableInstantPrinter(dateTimeFormatter),
					new DateTimeParser(dateTimeFormatter),
					Date.class, Calendar.class);
		}

		registry.addFormatterForFieldType(Period.class, new PeriodFormatter());
		registry.addFormatterForFieldType(Duration.class, new DurationFormatter());
		registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
		registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());

		registry.addFormatterForFieldAnnotation(new JodaDateTimeFormatAnnotationFormatterFactory());
	}

	private DateTimeFormatter getFormatter(Type type) {
		DateTimeFormatter formatter = this.formatters.get(type);
		if (formatter != null) {
			return formatter;
		}
		DateTimeFormatter fallbackFormatter = getFallbackFormatter(type);
		return this.factories.get(type).createDateTimeFormatter(fallbackFormatter);
	}

	private DateTimeFormatter getFallbackFormatter(Type type) {
		switch (type) {
			case DATE: return DateTimeFormat.shortDate();
			case TIME: return DateTimeFormat.shortTime();
			default: return DateTimeFormat.shortDateTime();
		}
	}

	private void addFormatterForFields(FormatterRegistry registry, Printer<?> printer,
			Parser<?> parser, Class<?>... fieldTypes) {

		for (Class<?> fieldType : fieldTypes) {
			registry.addFormatterForFieldType(fieldType, printer, parser);
		}
	}

}
