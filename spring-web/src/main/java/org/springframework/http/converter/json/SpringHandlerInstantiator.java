/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.http.converter.json;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Converter;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * Allows for creating Jackson ({@link JsonSerializer}, {@link JsonDeserializer},
 * {@link KeyDeserializer}, {@link TypeResolverBuilder}, {@link TypeIdResolver})
 * beans with autowiring against a Spring {@link ApplicationContext}.
 *
 * <p>As of Spring 4.3, this overrides all factory methods in {@link HandlerInstantiator},
 * including non-abstract ones and recently introduced ones from Jackson 2.4 and 2.5:
 * for {@link ValueInstantiator}, {@link ObjectIdGenerator}, {@link ObjectIdResolver},
 * {@link PropertyNamingStrategy}, {@link Converter}, {@link VirtualBeanPropertyWriter}.
 *
 * @author Sebastien Deleuze
 * @author Juergen Hoeller
 * @since 4.1.3
 * @see Jackson2ObjectMapperBuilder#handlerInstantiator(HandlerInstantiator)
 * @see ApplicationContext#getAutowireCapableBeanFactory()
 * @see HandlerInstantiator
 */
/**
 * 允许创建具有自动装配针对春季{@link  ApplicationContext}。 
 *  <p>从Spring 4.3开始，此方法将覆盖{@link  HandlerInstantiator}中的所有工厂方法，包括非抽象方法以及最近在Jackson 2.4和2.5中引入的方法：对于{@link  ValueInstantiator}，{<@link > ObjectIdGenerator}，{<@link> ObjectIdResolver}，{<@link> PropertyNamingStrategy}，{<@link> Converter}，{<@link> VirtualBeanPropertyWriter}。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Juergen Hoeller @since 4.1.3 
 * @see  Jackson2ObjectMapperBuilder＃handlerInstantiator（HandlerInstantiator）
 * @see  ApplicationContext＃getAutowireCapableBeanFactory（）
 */
public class SpringHandlerInstantiator extends HandlerInstantiator {

	private final AutowireCapableBeanFactory beanFactory;


	/**
	 * Create a new SpringHandlerInstantiator for the given BeanFactory.
	 * @param beanFactory the target BeanFactory
	 */
	/**
	 * 为给定的BeanFactory创建一个新的SpringHandlerInstantiator。 
	 *  
	 * @param  beanFactory目标BeanFactory
	 */
	public SpringHandlerInstantiator(AutowireCapableBeanFactory beanFactory) {
		Assert.notNull(beanFactory, "BeanFactory must not be null");
		this.beanFactory = beanFactory;
	}


	@Override
	public JsonDeserializer<?> deserializerInstance(DeserializationConfig config,
			Annotated annotated, Class<?> implClass) {

		return (JsonDeserializer<?>) this.beanFactory.createBean(implClass);
	}

	@Override
	public KeyDeserializer keyDeserializerInstance(DeserializationConfig config,
			Annotated annotated, Class<?> implClass) {

		return (KeyDeserializer) this.beanFactory.createBean(implClass);
	}

	@Override
	public JsonSerializer<?> serializerInstance(SerializationConfig config,
			Annotated annotated, Class<?> implClass) {

		return (JsonSerializer<?>) this.beanFactory.createBean(implClass);
	}

	@Override
	public TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (TypeResolverBuilder<?>) this.beanFactory.createBean(implClass);
	}

	@Override
	public TypeIdResolver typeIdResolverInstance(MapperConfig<?> config, Annotated annotated, Class<?> implClass) {
		return (TypeIdResolver) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public ValueInstantiator valueInstantiatorInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (ValueInstantiator) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public ObjectIdGenerator<?> objectIdGeneratorInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (ObjectIdGenerator<?>) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public ObjectIdResolver resolverIdGeneratorInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (ObjectIdResolver) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public PropertyNamingStrategy namingStrategyInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (PropertyNamingStrategy) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public Converter<?, ?> converterInstance(MapperConfig<?> config,
			Annotated annotated, Class<?> implClass) {

		return (Converter<?, ?>) this.beanFactory.createBean(implClass);
	}

	/** @since 4.3 */
	/**
	 * @4.3起
	 */
	@Override
	public VirtualBeanPropertyWriter virtualPropertyWriterInstance(MapperConfig<?> config, Class<?> implClass) {
		return (VirtualBeanPropertyWriter) this.beanFactory.createBean(implClass);
	}

}
