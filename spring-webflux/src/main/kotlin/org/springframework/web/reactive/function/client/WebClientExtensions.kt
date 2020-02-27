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
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.asFlow
import org.reactivestreams.Publisher
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Extension for [WebClient.RequestBodySpec.body] providing a `body(Publisher<T>)` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [WebClient.RequestBodySpec.body]的扩展提供了一个利用Kotlin修饰类型参数的body（Publisher <T>）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any, S : Publisher<T>> RequestBodySpec.body(publisher: S): RequestHeadersSpec<*> =
		body(publisher, object : ParameterizedTypeReference<T>() {})

/**
 * Extension for [WebClient.RequestBodySpec.body] providing a `body(Flow<T>)` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 * @param flow the [Flow] to write to the request
 * @param T the type of the elements contained in the flow
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [WebClient.RequestBodySpec.body]的扩展，它利用Kotlin的类型化参数提供`body（Flow <T>）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  
 * @param 流[Flow]写入请求
 * @param  T流中包含的元素的类型@author  Sebastien Deleuze @since 5.2
 */
inline fun <reified T : Any> RequestBodySpec.body(flow: Flow<T>): RequestHeadersSpec<*> =
		body(flow, object : ParameterizedTypeReference<T>() {})

/**
 * Extension for [WebClient.RequestBodySpec.body] providing a `body<T>(Any)` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 * @param producer the producer to write to the request. This must be a
 * [Publisher] or another producer adaptable to a
 * [Publisher] via [org.springframework.core.ReactiveAdapterRegistry]
 * @param T the type of the elements contained in the producer
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [WebClient.RequestBodySpec.body]的扩展，它利用Kotlin的类型化参数提供`body <T>（Any）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  
 * @param 生产者生产者写入请求。 
 * 它必须是[Publisher]或通过[org.springframework.core.ReactiveAdapterRegistry]适应[Publisher]的其他生产者
 * @param  T包含在生产者中的元素类型@author  Sebastien Deleuze @since 5.2
 */
inline fun <reified T : Any> RequestBodySpec.body(producer: Any): RequestHeadersSpec<*> =
		body(producer, object : ParameterizedTypeReference<T>() {})

/**
 * Coroutines variant of [WebClient.RequestHeadersSpec.exchange].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [WebClient.RequestHeadersSpec.exchange]的协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend fun RequestHeadersSpec<out RequestHeadersSpec<*>>.awaitExchange(): ClientResponse =
		exchange().awaitSingle()


/**
 * Extension for [WebClient.ResponseSpec.bodyToMono] providing a `bodyToMono<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [WebClient.ResponseSpec.bodyToMono]的扩展提供了一个利用Kotlin修饰类型参数的`bodyToMono <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> WebClient.ResponseSpec.bodyToMono(): Mono<T> =
		bodyToMono(object : ParameterizedTypeReference<T>() {})


/**
 * Extension for [WebClient.ResponseSpec.bodyToFlux] providing a `bodyToFlux<Foo>()` variant
 * leveraging Kotlin reified type parameters. This extension is not subject to type
 * erasure and retains actual generic type arguments.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * [WebClient.ResponseSpec.bodyToFlux]的扩展提供了一个利用Kotlin修饰类型参数的`bodyToFlux <Foo>（）`变体。 
 * 此扩展名不受类型擦除的约束，并保留实际的泛型类型参数。 
 *  @author 塞巴斯蒂安·德勒兹@5.0起
 */
inline fun <reified T : Any> WebClient.ResponseSpec.bodyToFlux(): Flux<T> =
		bodyToFlux(object : ParameterizedTypeReference<T>() {})

/**
 * Coroutines [kotlinx.coroutines.flow.Flow] based variant of [WebClient.ResponseSpec.bodyToFlux].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * 基于协同程序[kotlinx.coroutines.flow.Flow]的[WebClient.ResponseSpec.bodyToFlux]变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
inline fun <reified T : Any> WebClient.ResponseSpec.bodyToFlow(): Flow<T> =
		bodyToFlux<T>().asFlow()

/**
 * Coroutines variant of [WebClient.ResponseSpec.bodyToMono].
 *
 * @author Sebastien Deleuze
 * @since 5.2
 */
/**
 * [WebClient.ResponseSpec.bodyToMono]的协程变体。 
 *  @author 塞巴斯蒂安·德勒兹@5.2起
 */
suspend inline fun <reified T : Any> WebClient.ResponseSpec.awaitBody() : T =
		bodyToMono<T>().awaitSingle()
