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

package org.springframework.test.context.transaction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Test annotation which indicates that the annotated {@code void} method
 * should be executed <em>before</em> a transaction is started for a test method
 * configured to run within a transaction via Spring's {@code @Transactional}
 * annotation.
 *
 * <p>{@code @BeforeTransaction} methods declared in superclasses or as interface
 * default methods will be executed before those of the current test class.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em>.
 *
 * <p>As of Spring Framework 4.3, {@code @BeforeTransaction} may also be
 * declared on Java 8 based interface default methods.
 *
 * @author Sam Brannen
 * @since 2.5
 * @see org.springframework.transaction.annotation.Transactional
 * @see AfterTransaction
 */
/**
 * <p>测试注释，该注释指示已注释的{@code  void}方法应在事务启动前<em> </ em>执行，用于配置为通过Spring的{@code  @Transactional}注释。 
 * 在超类或接口默认方法中声明的<p> {<@code> @BeforeTransaction}方法将在当前测试类的方法之前执行。 
 *  <p>此注释可用作<em>元注释</ em>，以创建自定义的<em>组成的注释</ em>。 
 *  <p>从Spring Framework 4.3开始，还可以在基于Java 8的接口默认方法中声明{@code  @BeforeTransaction}。 
 *  @author  Sam Brannen @从2.5开始
 * @see  org.springframework.transaction.annotation.Transactional 
 * @see  AfterTransaction
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeforeTransaction {
}
