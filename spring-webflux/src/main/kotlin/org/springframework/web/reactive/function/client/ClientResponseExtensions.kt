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

package org.springframework.web.reactive.function.client

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.asFlow
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Extension for [ClientResponse.bodyToMono] providing a `bodyToMono<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ClientResponse.bodyToMono]的扩展提供了一个利用Kotlin修饰类型参数的`bodyToMono <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ClientResponse.bodyToMono(): Mono<T> =
		bodyToMono(object : ParameterizedTypeReference<T>() {})

/**
 * Extension for [ClientResponse.bodyToFlux] providing a `bodyToFlux<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ClientResponse.bodyToFlux]的扩展提供了一个利用Kotlin修饰类型参数的`bodyToFlux <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ClientResponse.bodyToFlux(): Flux<T> =
		bodyToFlux(object : ParameterizedTypeReference<T>() {})

/**
 * Coroutines [kotlinx.coroutines.flow.Flow] based variant of [ClientResponse.bodyToFlux].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * 基于协程[kotlinx.coroutines.flow.Flow]的[ClientResponse.bodyToFlux]变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
inline fun <reified T : Any> ClientResponse.bodyToFlow(): Flow<T> =
		bodyToFlux<T>().asFlow()

/**
 * Extension for [ClientResponse.toEntity] providing a `toEntity<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ClientResponse.toEntity]的扩展提供了一个利用Kotlin修饰类型参数的`toEntity <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ClientResponse.toEntity(): Mono<ResponseEntity<T>> =
		toEntity(object : ParameterizedTypeReference<T>() {})

/**
 * Extension for [ClientResponse.toEntityList] providing a `bodyToEntityList<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [ClientResponse.toEntityList]的扩展提供了一个利用Kotlin修饰类型参数的`bodyToEntityList <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> ClientResponse.toEntityList(): Mono<ResponseEntity<List<T>>> =
		toEntityList(object : ParameterizedTypeReference<T>() {})

/**
 * Non-nullable Coroutines variant of [ClientResponse.bodyToMono].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [ClientResponse.bodyToMono]的非空协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend inline fun <reified T : Any> ClientResponse.awaitBody(): T =
		bodyToMono<T>().awaitSingle()

/**
 * Nullable coroutines variant of [ClientResponse.bodyToMono].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [ClientResponse.bodyToMono]的空协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend inline fun <reified T : Any> ClientResponse.awaitBodyOrNull(): T? =
		bodyToMono<T>().awaitFirstOrNull()

/**
 * Coroutines variant of [ClientResponse.toEntity].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [ClientResponse.toEntity]的协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend inline fun <reified T : Any> ClientResponse.awaitEntity(): ResponseEntity<T> =
		toEntity<T>().awaitSingle()

/**
 * Coroutines variant of [ClientResponse.toEntityList].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [ClientResponse.toEntityList]的协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend inline fun <reified T : Any> ClientResponse.awaitEntityList(): ResponseEntity<List<T>> =
		toEntityList<T>().awaitSingle()
