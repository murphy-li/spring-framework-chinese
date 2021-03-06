/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as being eligible for Spring-driven configuration.
 *
 * <p>Typically used with the AspectJ {@code AnnotationBeanConfigurerAspect}.
 *
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Adrian Colyer
 * @author Ramnivas Laddad
 * @since 2.0
 */
/**
 * 将一个类标记为可以进行Spring驱动的配置。 
 *  <p>通常与AspectJ {@code  AnnotationBeanConfigurerAspect}一起使用。 
 *  @author  Rod Johnson @author  Rob Harrop @author  Adrian Colyer @author  Ramnivas Laddad @since 2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Configurable {

	/**
	 * The name of the bean definition that serves as the configuration template.
	 */
	/**
	 * 充当配置模板的bean定义的名称。 
	 * 
	 */
	String value() default "";

	/**
	 * Are dependencies to be injected via autowiring?
	 */
	/**
	 * 是否通过自动装配注入依赖项？
	 */
	Autowire autowire() default Autowire.NO;

	/**
	 * Is dependency checking to be performed for configured objects?
	 */
	/**
	 * 是否要对已配置的对象执行依赖性检查？
	 */
	boolean dependencyCheck() default false;

	/**
	 * Are dependencies to be injected prior to the construction of an object?
	 */
	/**
	 * 是否在构造对象之前注入依赖项？
	 */
	boolean preConstruction() default false;

}
