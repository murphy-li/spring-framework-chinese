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

package org.springframework.test.context.junit.jupiter.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * {@code @SpringJUnitWebConfig} is a <em>composed annotation</em> that combines
 * {@link ExtendWith @ExtendWith(SpringExtension.class)} from JUnit Jupiter with
 * {@link ContextConfiguration @ContextConfiguration} and
 * {@link WebAppConfiguration @WebAppConfiguration} from the <em>Spring TestContext
 * Framework</em>.
 *
 * @author Sam Brannen
 * @since 5.0
 * @see ExtendWith
 * @see SpringExtension
 * @see ContextConfiguration
 * @see WebAppConfiguration
 * @see org.springframework.test.context.junit.jupiter.SpringJUnitConfig
 */
/**
 * {@code  @SpringJUnitWebConfig}是由<em>组成的注释</ em>，它将来自JUnit Jupiter的{@link  ExtendWith @ExtendWith（SpringExtension.class）}与{@link  ContextConfiguration @ContextConfiguration}和{ <em> Spring TestContext Framework </ em>中的@link  WebAppConfiguration @WebAppConfiguration}。 
 *  @author  Sam Brannen @since 5.0 
 * @see  ExtendWith 
 * @see  SpringExtension 
 * @see  ContextConfiguration 
 * @see  WebAppConfiguration 
 * @see  org.springframework.test.context.junit.jupiter.SpringJUnitConfig
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringJUnitWebConfig {

	/**
	 * Alias for {@link ContextConfiguration#classes}.
	 */
	/**
	 * {@link  ContextConfiguration＃classes}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class, attribute = "classes")
	Class<?>[] value() default {};

	/**
	 * Alias for {@link ContextConfiguration#classes}.
	 */
	/**
	 * {@link  ContextConfiguration＃classes}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	Class<?>[] classes() default {};

	/**
	 * Alias for {@link ContextConfiguration#locations}.
	 */
	/**
	 * {@link  ContextConfiguration＃locations}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	String[] locations() default {};

	/**
	 * Alias for {@link ContextConfiguration#initializers}.
	 */
	/**
	 * {@link  ContextConfiguration＃initializers}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	Class<? extends ApplicationContextInitializer<?>>[] initializers() default {};

	/**
	 * Alias for {@link ContextConfiguration#inheritLocations}.
	 */
	/**
	 * {@link  ContextConfiguration＃inheritLocations}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	boolean inheritLocations() default true;

	/**
	 * Alias for {@link ContextConfiguration#inheritInitializers}.
	 */
	/**
	 * {@link  ContextConfiguration＃inheritInitializers}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	boolean inheritInitializers() default true;

	/**
	 * Alias for {@link ContextConfiguration#name}.
	 */
	/**
	 * {@link  ContextConfiguration＃name}的别名。 
	 * 
	 */
	@AliasFor(annotation = ContextConfiguration.class)
	String name() default "";

	/**
	 * Alias for {@link WebAppConfiguration#value}.
	 */
	/**
	 * {@link  WebAppConfiguration＃value}的别名。 
	 * 
	 */
	@AliasFor(annotation = WebAppConfiguration.class, attribute = "value")
	String resourcePath() default "src/main/webapp";

}
