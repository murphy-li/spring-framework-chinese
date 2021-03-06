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

package org.springframework.expression.spel.support;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Default implementation of the {@link TypeConverter} interface,
 * delegating to a core Spring {@link ConversionService}.
 *
 * @author Juergen Hoeller
 * @author Andy Clement
 * @since 3.0
 * @see org.springframework.core.convert.ConversionService
 */
/**
 * {@link  TypeConverter}接口的默认实现，委派给核心Spring {@link  ConversionService}。 
 *  @author  Juergen Hoeller @author 安迪·克莱门特（Andy Clement）@从3.0开始
 * @see  org.springframework.core.convert.ConversionService
 */
public class StandardTypeConverter implements TypeConverter {

	private final ConversionService conversionService;


	/**
	 * Create a StandardTypeConverter for the default ConversionService.
	 * @see DefaultConversionService#getSharedInstance()
	 */
	/**
	 * 为默认的ConversionService创建一个StandardTypeConverter。 
	 *  
	 * @see  DefaultConversionService＃getSharedInstance（）
	 */
	public StandardTypeConverter() {
		this.conversionService = DefaultConversionService.getSharedInstance();
	}

	/**
	 * Create a StandardTypeConverter for the given ConversionService.
	 * @param conversionService the ConversionService to delegate to
	 */
	/**
	 * 为给定的ConversionService创建一个StandardTypeConverter。 
	 *  
	 * @param  conversionService要委托的ConversionService
	 */
	public StandardTypeConverter(ConversionService conversionService) {
		Assert.notNull(conversionService, "ConversionService must not be null");
		this.conversionService = conversionService;
	}


	@Override
	public boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
		return this.conversionService.canConvert(sourceType, targetType);
	}

	@Override
	@Nullable
	public Object convertValue(@Nullable Object value, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return this.conversionService.convert(value, sourceType, targetType);
		}
		catch (ConversionException ex) {
			throw new SpelEvaluationException(ex, SpelMessage.TYPE_CONVERSION_ERROR,
					(sourceType != null ? sourceType.toString() : (value != null ? value.getClass().getName() : "null")),
					targetType.toString());
		}
	}

}
