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

package org.springframework.format.datetime;

import java.util.Calendar;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Configures basic date formatting for use with Spring, primarily for
 * {@link org.springframework.format.annotation.DateTimeFormat} declarations.
 * Applies to fields of type {@link Date}, {@link Calendar} and {@code long}.
 *
 * <p>Designed for direct instantiation but also exposes the static
 * {@link #addDateConverters(ConverterRegistry)} utility method for
 * ad-hoc use against any {@code ConverterRegistry} instance.
 *
 * @author Phillip Webb
 * @since 3.2
 * @see org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
 * @see org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar
 * @see FormatterRegistrar#registerFormatters
 */
/**
 * 配置与Spring一起使用的基本日期格式，主要用于{@link  org.springframework.format.annotation.DateTimeFormat}声明。 
 * 适用于{@link 日期}，{<@link>日历}和{@code  long}类型的字段。 
 *  <p>专为直接实例化而设计，但还公开了静态的{@link  #addDateConverters（ConverterRegistry）}实用程序方法，可临时用于任何{@code  ConverterRegistry}实例。 
 *  @author 菲利普·韦伯（Phillip Webb）@3.2起
 */
public class DateFormatterRegistrar implements FormatterRegistrar {

	@Nullable
	private DateFormatter dateFormatter;


	/**
	 * Set a global date formatter to register.
	 * <p>If not specified, no general formatter for non-annotated
	 * {@link Date} and {@link Calendar} fields will be registered.
	 */
	/**
	 * 设置一个全球日期格式器进行注册。 
	 *  <p>如果未指定，则不会为未注释的{@link 日期}和{@link 日历}字段注册通用格式程序。 
	 * 
	 */
	public void setFormatter(DateFormatter dateFormatter) {
		Assert.notNull(dateFormatter, "DateFormatter must not be null");
		this.dateFormatter = dateFormatter;
	}


	@Override
	public void registerFormatters(FormatterRegistry registry) {
		addDateConverters(registry);
		// In order to retain back compatibility we only register Date/Calendar
		// types when a user defined formatter is specified (see SPR-10105)
		if (this.dateFormatter != null) {
			registry.addFormatter(this.dateFormatter);
			registry.addFormatterForFieldType(Calendar.class, this.dateFormatter);
		}
		registry.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
	}

	/**
	 * Add date converters to the specified registry.
	 * @param converterRegistry the registry of converters to add to
	 */
	/**
	 * 将日期转换器添加到指定的注册表。 
	 *  
	 * @param  converterRegistry要添加到的转换器注册表
	 */
	public static void addDateConverters(ConverterRegistry converterRegistry) {
		converterRegistry.addConverter(new DateToLongConverter());
		converterRegistry.addConverter(new DateToCalendarConverter());
		converterRegistry.addConverter(new CalendarToDateConverter());
		converterRegistry.addConverter(new CalendarToLongConverter());
		converterRegistry.addConverter(new LongToDateConverter());
		converterRegistry.addConverter(new LongToCalendarConverter());
	}


	private static class DateToLongConverter implements Converter<Date, Long> {

		@Override
		public Long convert(Date source) {
			return source.getTime();
		}
	}


	private static class DateToCalendarConverter implements Converter<Date, Calendar> {

		@Override
		public Calendar convert(Date source) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(source);
			return calendar;
		}
	}


	private static class CalendarToDateConverter implements Converter<Calendar, Date> {

		@Override
		public Date convert(Calendar source) {
			return source.getTime();
		}
	}


	private static class CalendarToLongConverter implements Converter<Calendar, Long> {

		@Override
		public Long convert(Calendar source) {
			return source.getTimeInMillis();
		}
	}


	private static class LongToDateConverter implements Converter<Long, Date> {

		@Override
		public Date convert(Long source) {
			return new Date(source);
		}
	}


	private static class LongToCalendarConverter implements Converter<Long, Calendar> {

		@Override
		public Calendar convert(Long source) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(source);
			return calendar;
		}
	}

}
