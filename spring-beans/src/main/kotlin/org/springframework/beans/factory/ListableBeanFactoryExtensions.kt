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

package org.springframework.beans.factory

/**
 * Extension for [ListableBeanFactory.getBeanNamesForType] providing a
 * `getBeanNamesForType<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ListableBeanFactory.getBeanNamesForType]的扩展，提供`getBeanNamesForType <Foo>（）`变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ListableBeanFactory.getBeanNamesForType(includeNonSingletons: Boolean = true,
		allowEagerInit: Boolean = true): Array<out String> =
		getBeanNamesForType(T::class.java, includeNonSingletons, allowEagerInit)

/**
 * Extension for [ListableBeanFactory.getBeansOfType] providing a `getBeansOfType<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ListableBeanFactory.getBeansOfType]的扩展，提供一个`getBeansOfType <Foo>（）`变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ListableBeanFactory.getBeansOfType(includeNonSingletons: Boolean = true,
		allowEagerInit: Boolean = true): Map<String, T> =
		getBeansOfType(T::class.java, includeNonSingletons, allowEagerInit)

/**
 * Extension for [ListableBeanFactory.getBeanNamesForAnnotation] providing a
 * `getBeansOfType<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ListableBeanFactory.getBeanNamesForAnnotation]的扩展，提供一个`getBeansOfType <Foo>（）`变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Annotation> ListableBeanFactory.getBeanNamesForAnnotation(): Array<out String> =
		getBeanNamesForAnnotation(T::class.java)

/**
 * Extension for [ListableBeanFactory.getBeansWithAnnotation] providing a
 * `getBeansWithAnnotation<Foo>()` variant.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ListableBeanFactory.getBeansWithAnnotation]的扩展，提供了一个`getBeansWithAnnotation <Foo>（）`变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Annotation> ListableBeanFactory.getBeansWithAnnotation(): Map<String, Any> =
		getBeansWithAnnotation(T::class.java)

/**
 * Extension for [ListableBeanFactory.findAnnotationOnBean] providing a
 * `findAnnotationOnBean<Foo>("foo")` variant.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ListableBeanFactory.findAnnotationOnBean]的扩展，提供了一个`findAnnotationOnBean <Foo>（"foo"）`变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Annotation> ListableBeanFactory.findAnnotationOnBean(beanName:String): Annotation? =
		findAnnotationOnBean(beanName, T::class.java)

