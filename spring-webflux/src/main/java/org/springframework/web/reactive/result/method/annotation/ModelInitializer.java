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

package org.springframework.web.reactive.result.method.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import reactor.core.publisher.Mono;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.InvocableHandlerMethod;
import org.springframework.web.server.ServerWebExchange;

/**
 * Package-private class to assist {@link RequestMappingHandlerAdapter} with
 * default model initialization through {@code @ModelAttribute} methods.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 包专用类，可通过{@code  @ModelAttribute}方法协助{@link  RequestMappingHandlerAdapter}进行默认模型初始化。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
class ModelInitializer {

	private final ControllerMethodResolver methodResolver;

	private final ReactiveAdapterRegistry adapterRegistry;


	public ModelInitializer(ControllerMethodResolver methodResolver, ReactiveAdapterRegistry adapterRegistry) {
		Assert.notNull(methodResolver, "ControllerMethodResolver is required");
		Assert.notNull(adapterRegistry, "ReactiveAdapterRegistry is required");
		this.methodResolver = methodResolver;
		this.adapterRegistry = adapterRegistry;
	}


	/**
	 * Initialize the {@link org.springframework.ui.Model Model} based on a
	 * (type-level) {@code @SessionAttributes} annotation and
	 * {@code @ModelAttribute} methods.
	 * @param handlerMethod the target controller method
	 * @param bindingContext the context containing the model
	 * @param exchange the current exchange
	 * @return a {@code Mono} for when the model is populated.
	 */
	/**
	 * 基于（类型级别）{<@code> @SessionAttributes}注解和{@code  @ModelAttribute}方法初始化{@link  org.springframework.ui.Model模型}。 
	 *  
	 * @param  handlerMethod目标控制器方法
	 * @param  bindingContext包含模型的上下文
	 * @param 在填充模型时交换当前交换
	 * @return 一个{@code  Mono}。 
	 * 
	 */
	@SuppressWarnings("Convert2MethodRef")
	public Mono<Void> initModel(HandlerMethod handlerMethod, InitBinderBindingContext bindingContext,
			ServerWebExchange exchange) {

		List<InvocableHandlerMethod> modelMethods =
				this.methodResolver.getModelAttributeMethods(handlerMethod);

		SessionAttributesHandler sessionAttributesHandler =
				this.methodResolver.getSessionAttributesHandler(handlerMethod);

		if (!sessionAttributesHandler.hasSessionAttributes()) {
			return invokeModelAttributeMethods(bindingContext, modelMethods, exchange);
		}

		return exchange.getSession()
				.flatMap(session -> {
					Map<String, Object> attributes = sessionAttributesHandler.retrieveAttributes(session);
					bindingContext.getModel().mergeAttributes(attributes);
					bindingContext.setSessionContext(sessionAttributesHandler, session);
					return invokeModelAttributeMethods(bindingContext, modelMethods, exchange)
							.doOnSuccess(aVoid ->
								findModelAttributes(handlerMethod, sessionAttributesHandler).forEach(name -> {
									if (!bindingContext.getModel().containsAttribute(name)) {
										Object value = session.getRequiredAttribute(name);
										bindingContext.getModel().addAttribute(name, value);
									}
								}));
				});
	}

	private Mono<Void> invokeModelAttributeMethods(BindingContext bindingContext,
			List<InvocableHandlerMethod> modelMethods, ServerWebExchange exchange) {

		List<Mono<HandlerResult>> resultList = new ArrayList<>();
		modelMethods.forEach(invocable -> resultList.add(invocable.invoke(exchange, bindingContext)));

		return Mono
				.zip(resultList, objectArray ->
						Arrays.stream(objectArray)
								.map(object -> handleResult(((HandlerResult) object), bindingContext))
								.collect(Collectors.toList()))
				.flatMap(Mono::when);
	}

	private Mono<Void> handleResult(HandlerResult handlerResult, BindingContext bindingContext) {
		Object value = handlerResult.getReturnValue();
		if (value != null) {
			ResolvableType type = handlerResult.getReturnType();
			ReactiveAdapter adapter = this.adapterRegistry.getAdapter(type.resolve(), value);
			if (isAsyncVoidType(type, adapter)) {
				return Mono.from(adapter.toPublisher(value));
			}
			String name = getAttributeName(handlerResult.getReturnTypeSource());
			bindingContext.getModel().asMap().putIfAbsent(name, value);
		}
		return Mono.empty();
	}

	private boolean isAsyncVoidType(ResolvableType type, @Nullable  ReactiveAdapter adapter) {
		return (adapter != null && (adapter.isNoValue() || type.resolveGeneric() == Void.class));
	}

	private String getAttributeName(MethodParameter param) {
		return Optional
				.ofNullable(AnnotatedElementUtils.findMergedAnnotation(param.getAnnotatedElement(), ModelAttribute.class))
				.filter(ann -> StringUtils.hasText(ann.value()))
				.map(ModelAttribute::value)
				.orElseGet(() -> Conventions.getVariableNameForParameter(param));
	}

	/** Find {@code @ModelAttribute} arguments also listed as {@code @SessionAttributes}. */
	/**
	 * 查找也列为{@code  @SessionAttributes}的{@code  @ModelAttribute}参数。 
	 * 
	 */
	private List<String> findModelAttributes(HandlerMethod handlerMethod,
			SessionAttributesHandler sessionAttributesHandler) {

		List<String> result = new ArrayList<>();
		for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
			if (parameter.hasParameterAnnotation(ModelAttribute.class)) {
				String name = getNameForParameter(parameter);
				Class<?> paramType = parameter.getParameterType();
				if (sessionAttributesHandler.isHandlerSessionAttribute(name, paramType)) {
					result.add(name);
				}
			}
		}
		return result;
	}

	/**
	 * Derive the model attribute name for the given method parameter based on
	 * a {@code @ModelAttribute} parameter annotation (if present) or falling
	 * back on parameter type based conventions.
	 * @param parameter a descriptor for the method parameter
	 * @return the derived name
	 * @see Conventions#getVariableNameForParameter(MethodParameter)
	 */
	/**
	 * 根据{@code  @ModelAttribute}参数注释（如果存在）或使用基于参数类型的约定推导给定方法参数的模型属性名称。 
	 *  
	 * @param 参数方法参数的描述符
	 * @return 派生名称
	 * @see  Conventions＃getVariableNameForParameter（MethodParameter）
	 */
	public static String getNameForParameter(MethodParameter parameter) {
		ModelAttribute ann = parameter.getParameterAnnotation(ModelAttribute.class);
		String name = (ann != null ? ann.value() : null);
		return (StringUtils.hasText(name) ? name : Conventions.getVariableNameForParameter(parameter));
	}

}
