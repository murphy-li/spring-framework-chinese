/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jmx.export.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method-level annotation that indicates to expose a given bean property as a
 * JMX attribute, corresponding to the {@code ManagedAttribute} attribute.
 * Only valid when used on a JavaBean getter or setter.
 *
 * @author Rob Harrop
 * @since 1.2
 * @see org.springframework.jmx.export.metadata.ManagedAttribute
 */
/**
 * 方法级别的注释，指示将给定的bean属性公开为JMX属性，与{@code  ManagedAttribute}属性相对应。 
 * 仅在用于JavaBean getter或setter上时有效。 
 *  @author  Rob Harrop @始于1.2 
 * @see  org.springframework.jmx.export.metadata.ManagedAttribute
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManagedAttribute {

	String defaultValue() default "";

	String description() default "";

	int currencyTimeLimit() default -1;

	String persistPolicy() default "";

	int persistPeriod() default -1;

}
