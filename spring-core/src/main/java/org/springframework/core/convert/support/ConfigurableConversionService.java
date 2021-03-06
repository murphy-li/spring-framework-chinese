/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2011 the original author or authors.
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
 * 版权所有2002-2011的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.convert.support;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;

/**
 * Configuration interface to be implemented by most if not all {@link ConversionService}
 * types. Consolidates the read-only operations exposed by {@link ConversionService} and
 * the mutating operations of {@link ConverterRegistry} to allow for convenient ad-hoc
 * addition and removal of {@link org.springframework.core.convert.converter.Converter
 * Converters} through. The latter is particularly useful when working against a
 * {@link org.springframework.core.env.ConfigurableEnvironment ConfigurableEnvironment}
 * instance in application context bootstrapping code.
 *
 * @author Chris Beams
 * @since 3.1
 * @see org.springframework.core.env.ConfigurablePropertyResolver#getConversionService()
 * @see org.springframework.core.env.ConfigurableEnvironment
 * @see org.springframework.context.ConfigurableApplicationContext#getEnvironment()
 */
/**
 * 大多数（如果不是全部）{@link  ConversionService}类型都将实现配置接口。 
 * 合并{@link  ConversionService}公开的只读操作和{@link  ConverterRegistry}的变异操作，以方便地临时添加和删除{@link  org.springframework.core.convert .converter.Converter Converters}。 
 * 在处理应用程序上下文引导代码中的{@link  org.springframework.core.env.ConfigurableEnvironment ConfigurableEnvironment}实例时，后者特别有用。 
 *  @author 克里斯·比姆斯（Chris Beams）自3.1起
 * @see  org.springframework.core.env.ConfigurablePropertyResolver＃getConversionService（）
 * @see  org.springframework.core.env.ConfigurableEnvironment 
 * @see  org.springframework.context.ConfigurableApplicationContext #getEnvironment（）
 */
public interface ConfigurableConversionService extends ConversionService, ConverterRegistry {

}
