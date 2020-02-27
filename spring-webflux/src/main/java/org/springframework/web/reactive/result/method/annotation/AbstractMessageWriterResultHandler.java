/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.reactive.result.method.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import org.springframework.core.KotlinDetector;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Hints;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.HandlerResultHandlerSupport;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerWebExchange;

/**
 * Abstract base class for result handlers that handle return values by writing
 * to the response with {@link HttpMessageWriter}.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * 通过使用{@link  HttpMessageWriter}写入响应来处理返回值的结果处理程序的抽象基类。 
 *  @author  Rossen Stoyanchev @author 塞巴斯蒂安·德勒兹@5.0
 */
public abstract class AbstractMessageWriterResultHandler extends HandlerResultHandlerSupport {

	private static final String COROUTINES_FLOW_CLASS_NAME = "kotlinx.coroutines.flow.Flow";

	private final List<HttpMessageWriter<?>> messageWriters;


	/**
	 * Constructor with {@link HttpMessageWriter HttpMessageWriters} and a
	 * {@code RequestedContentTypeResolver}.
	 * @param messageWriters for serializing Objects to the response body stream
	 * @param contentTypeResolver for resolving the requested content type
	 */
	/**
	 * 带有{@link  HttpMessageWriter HttpMessageWriters}和{@code  RequestedContentTypeResolver}的构造方法。 
	 *  
	 * @param  messageWriters，用于将对象序列化为响应主体流。 
	 * 
	 * @param  contentTypeResolver，用于解析请求的内容类型
	 */
	protected AbstractMessageWriterResultHandler(List<HttpMessageWriter<?>> messageWriters,
			RequestedContentTypeResolver contentTypeResolver) {

		this(messageWriters, contentTypeResolver, ReactiveAdapterRegistry.getSharedInstance());
	}

	/**
	 * Constructor with an additional {@link ReactiveAdapterRegistry}.
	 * @param messageWriters for serializing Objects to the response body stream
	 * @param contentTypeResolver for resolving the requested content type
	 * @param adapterRegistry for adapting other reactive types (e.g. rx.Observable,
	 * rx.Single, etc.) to Flux or Mono
	 */
	/**
	 * 构造函数，带有一个额外的{@link  ReactiveAdapterRegistry}。 
	 *  
	 * @param  messageWriters，用于将对象序列化到响应主体流。 
	 * 
	 * @param  contentTypeResolver，用于解析请求的内容类型。 
	 * 单声道
	 */
	protected AbstractMessageWriterResultHandler(List<HttpMessageWriter<?>> messageWriters,
			RequestedContentTypeResolver contentTypeResolver, ReactiveAdapterRegistry adapterRegistry) {

		super(contentTypeResolver, adapterRegistry);
		Assert.notEmpty(messageWriters, "At least one message writer is required");
		this.messageWriters = messageWriters;
	}


	/**
	 * Return the configured message converters.
	 */
	/**
	 * 返回已配置的消息转换器。 
	 * 
	 */
	public List<HttpMessageWriter<?>> getMessageWriters() {
		return this.messageWriters;
	}


	/**
	 * Write a given body to the response with {@link HttpMessageWriter}.
	 * @param body the object to write
	 * @param bodyParameter the {@link MethodParameter} of the body to write
	 * @param exchange the current exchange
	 * @return indicates completion or error
	 * @see #writeBody(Object, MethodParameter, MethodParameter, ServerWebExchange)
	 */
	/**
	 * 使用{@link  HttpMessageWriter}将给定的正文写入响应。 
	 *  
	 * @param 正文写入对象
	 * @param  bodyParameter编写主体的{@link  MethodParameter} 
	 * @param 交换当前交换
	 * @return 表示完成或错误
	 * @see  #writeBody（对象，方法参数，方法参数，ServerWebExchange）
	 */
	protected Mono<Void> writeBody(@Nullable Object body, MethodParameter bodyParameter, ServerWebExchange exchange) {
		return this.writeBody(body, bodyParameter, null, exchange);
	}

	/**
	 * Write a given body to the response with {@link HttpMessageWriter}.
	 * @param body the object to write
	 * @param bodyParameter the {@link MethodParameter} of the body to write
	 * @param actualParam the actual return type of the method that returned the value;
	 * could be different from {@code bodyParameter} when processing {@code HttpEntity}
	 * for example
	 * @param exchange the current exchange
	 * @return indicates completion or error
	 * @since 5.0.2
	 */
	/**
	 * 使用{@link  HttpMessageWriter}将给定的正文写入响应。 
	 *  
	 * @param  body要写入的对象
	 * @param  bodyParameter {{@@link> MethodParameter}要写入的主体
	 * @param  actualParam返回值的方法的实际返回类型； 
	 * 在处理{@code  HttpEntity}时可能与{@code  bodyParameter}不同，例如
	 * @param 交换当前交换
	 * @return 表示完成或错误，因为5.0.2起
	 */
	@SuppressWarnings({"unchecked", "rawtypes", "ConstantConditions"})
	protected Mono<Void> writeBody(@Nullable Object body, MethodParameter bodyParameter,
			@Nullable MethodParameter actualParam, ServerWebExchange exchange) {

		ResolvableType bodyType = ResolvableType.forMethodParameter(bodyParameter);
		ResolvableType actualType = (actualParam != null ? ResolvableType.forMethodParameter(actualParam) : bodyType);
		ReactiveAdapter adapter = getAdapterRegistry().getAdapter(bodyType.resolve(), body);

		Publisher<?> publisher;
		ResolvableType elementType;
		ResolvableType actualElementType;
		if (adapter != null) {
			publisher = adapter.toPublisher(body);
			boolean isUnwrapped = KotlinDetector.isKotlinReflectPresent() &&
					KotlinDetector.isKotlinType(bodyParameter.getContainingClass()) &&
					KotlinDelegate.isSuspend(bodyParameter.getMethod()) &&
					!COROUTINES_FLOW_CLASS_NAME.equals(bodyType.toClass().getName());
			ResolvableType genericType = isUnwrapped ? bodyType : bodyType.getGeneric();
			elementType = getElementType(adapter, genericType);
			actualElementType = elementType;
		}
		else {
			publisher = Mono.justOrEmpty(body);
			actualElementType = body != null ? ResolvableType.forInstance(body) : bodyType;
			elementType = (bodyType.toClass() == Object.class && body != null ? actualElementType : bodyType);
		}

		if (elementType.resolve() == void.class || elementType.resolve() == Void.class) {
			return Mono.from((Publisher<Void>) publisher);
		}

		MediaType bestMediaType = selectMediaType(exchange, () -> getMediaTypesFor(elementType));
		if (bestMediaType != null) {
			String logPrefix = exchange.getLogPrefix();
			if (logger.isDebugEnabled()) {
				logger.debug(logPrefix +
						(publisher instanceof Mono ? "0..1" : "0..N") + " [" + elementType + "]");
			}
			for (HttpMessageWriter<?> writer : getMessageWriters()) {
				if (writer.canWrite(actualElementType, bestMediaType)) {
					return writer.write((Publisher) publisher, actualType, elementType,
							bestMediaType, exchange.getRequest(), exchange.getResponse(),
							Hints.from(Hints.LOG_PREFIX_HINT, logPrefix));
				}
			}
		}

		MediaType contentType = exchange.getResponse().getHeaders().getContentType();
		boolean isPresentMediaType = (contentType != null && contentType.equals(bestMediaType));
		Set<MediaType> producibleTypes = exchange.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
		if (isPresentMediaType || !CollectionUtils.isEmpty(producibleTypes)) {
			return Mono.error(new HttpMessageNotWritableException(
					"No Encoder for [" + elementType + "] with preset Content-Type '" + contentType + "'"));
		}

		List<MediaType> mediaTypes = getMediaTypesFor(elementType);
		if (bestMediaType == null && mediaTypes.isEmpty()) {
			return Mono.error(new IllegalStateException("No HttpMessageWriter for " + elementType));
		}

		return Mono.error(new NotAcceptableStatusException(mediaTypes));
	}

	private ResolvableType getElementType(ReactiveAdapter adapter, ResolvableType genericType) {
		if (adapter.isNoValue()) {
			return ResolvableType.forClass(Void.class);
		}
		else if (genericType != ResolvableType.NONE) {
			return genericType;
		}
		else {
			return ResolvableType.forClass(Object.class);
		}
	}

	private List<MediaType> getMediaTypesFor(ResolvableType elementType) {
		List<MediaType> writableMediaTypes = new ArrayList<>();
		for (HttpMessageWriter<?> converter : getMessageWriters()) {
			if (converter.canWrite(elementType, null)) {
				writableMediaTypes.addAll(converter.getWritableMediaTypes());
			}
		}
		return writableMediaTypes;
	}


	/**
	 * Inner class to avoid a hard dependency on Kotlin at runtime.
	 */
	/**
	 * 内部类，以避免在运行时严重依赖Kotlin。 
	 * 
	 */
	private static class KotlinDelegate {

		static private boolean isSuspend(Method method) {
			KFunction<?> function = ReflectJvmMapping.getKotlinFunction(method);
			return function != null && function.isSuspend();
		}
	}

}
