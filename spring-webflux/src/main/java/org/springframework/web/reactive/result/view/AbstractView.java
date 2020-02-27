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

package org.springframework.web.reactive.result.view;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;

/**
 * Base class for {@link View} implementations.
 *
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 5.0
 */
/**
 * {@link  View}实现的基类。 
 *  @author  Rossen Stoyanchev @author  Sam Brannen @从5.0开始
 */
public abstract class AbstractView implements View, BeanNameAware, ApplicationContextAware {

	/** Well-known name for the RequestDataValueProcessor in the bean factory. */
	/**
	 * Bean工厂中RequestDataValueProcessor的知名名称。 
	 * 
	 */
	public static final String REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME = "requestDataValueProcessor";


	/** Logger that is available to subclasses. */
	/**
	 * 子类可用的记录器。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	private final ReactiveAdapterRegistry adapterRegistry;

	private final List<MediaType> mediaTypes = new ArrayList<>(4);

	private Charset defaultCharset = StandardCharsets.UTF_8;

	@Nullable
	private String requestContextAttribute;

	@Nullable
	private String beanName;

	@Nullable
	private ApplicationContext applicationContext;


	public AbstractView() {
		this(ReactiveAdapterRegistry.getSharedInstance());
	}

	public AbstractView(ReactiveAdapterRegistry reactiveAdapterRegistry) {
		this.adapterRegistry = reactiveAdapterRegistry;
		this.mediaTypes.add(ViewResolverSupport.DEFAULT_CONTENT_TYPE);
	}


	/**
	 * Set the supported media types for this view.
	 * <p>Default is {@code "text/html;charset=UTF-8"}.
	 */
	/**
	 * 设置此视图支持的媒体类型。 
	 *  <p>默认值为{@code "text / html; charset = UTF-8"}。 
	 * 
	 */
	public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
		Assert.notEmpty(supportedMediaTypes, "MediaType List must not be empty");
		this.mediaTypes.clear();
		this.mediaTypes.addAll(supportedMediaTypes);
	}

	/**
	 * Get the configured media types supported by this view.
	 */
	/**
	 * 获取此视图支持的已配置媒体类型。 
	 * 
	 */
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return this.mediaTypes;
	}

	/**
	 * Set the default charset for this view, used when the
	 * {@linkplain #setSupportedMediaTypes(List) content type} does not contain one.
	 * <p>Default is {@linkplain StandardCharsets#UTF_8 UTF 8}.
	 */
	/**
	 * 设置此视图的默认字符集，当{@link  plain #setSupportedMediaTypes（List）内容类型}不包含一个字符集时使用。 
	 *  <p>默认值为{@link 普通StandardCharsets＃UTF_8 UTF 8}。 
	 * 
	 */
	public void setDefaultCharset(Charset defaultCharset) {
		Assert.notNull(defaultCharset, "'defaultCharset' must not be null");
		this.defaultCharset = defaultCharset;
	}

	/**
	 * Get the default charset, used when the
	 * {@linkplain #setSupportedMediaTypes(List) content type} does not contain one.
	 */
	/**
	 * 获取默认字符集，当{@link  plain #setSupportedMediaTypes（List）内容类型}不包含一个字符集时使用。 
	 * 
	 */
	public Charset getDefaultCharset() {
		return this.defaultCharset;
	}

	/**
	 * Set the name of the {@code RequestContext} attribute for this view.
	 * <p>Default is none ({@code null}).
	 */
	/**
	 * 设置此视图的{@code  RequestContext}属性的名称。 
	 *  <p>默认为无（{@code  null}）。 
	 * 
	 */
	public void setRequestContextAttribute(@Nullable String requestContextAttribute) {
		this.requestContextAttribute = requestContextAttribute;
	}

	/**
	 * Get the name of the {@code RequestContext} attribute for this view, if any.
	 */
	/**
	 * 获取此视图的{@code  RequestContext}属性的名称（如果有）。 
	 * 
	 */
	@Nullable
	public String getRequestContextAttribute() {
		return this.requestContextAttribute;
	}

	/**
	 * Set the view's name. Helpful for traceability.
	 * <p>Framework code must call this when constructing views.
	 */
	/**
	 * 设置视图的名称。 
	 * 有助于追溯。 
	 *  <p>框架代码在构造视图时必须调用它。 
	 * 
	 */
	@Override
	public void setBeanName(@Nullable String beanName) {
		this.beanName = beanName;
	}

	/**
	 * Get the view's name.
	 * <p>Should never be {@code null} if the view was correctly configured.
	 */
	/**
	 * 获取视图的名称。 
	 *  <p>如果视图配置正确，则永远不应为{@code  null}。 
	 * 
	 */
	@Nullable
	public String getBeanName() {
		return this.beanName;
	}

	@Override
	public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Nullable
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	/**
	 * Obtain the {@link ApplicationContext} for actual use.
	 * @return the {@code ApplicationContext} (never {@code null})
	 * @throws IllegalStateException if the ApplicationContext cannot be obtained
	 * @see #getApplicationContext()
	 */
	/**
	 * 获取{@link  ApplicationContext}以供实际使用。 
	 *  
	 * @return  {@code  ApplicationContext}（从不{@code  null}）
	 * @throws 如果无法获取ApplicationContext，则抛出IllegalStateException 
	 * @see  #getApplicationContext（）
	 */
	protected final ApplicationContext obtainApplicationContext() {
		ApplicationContext applicationContext = getApplicationContext();
		Assert.state(applicationContext != null, "No ApplicationContext");
		return applicationContext;
	}


	/**
	 * Prepare the model to render.
	 * @param model a map with attribute names as keys and corresponding model
	 * objects as values (the map can also be {@code null} in case of an empty model)
	 * @param contentType the content type selected to render with, which should
	 * match one of the {@link #getSupportedMediaTypes() supported media types}
	 * @param exchange the current exchange
	 * @return a {@code Mono} that represents when and if rendering succeeds
	 */
	/**
	 * 准备要渲染的模型。 
	 *  
	 * @param 使用属性名称作为键并使用对应的模型对象作为值对地图进行建模（如果模型为空，则地图也可以为{@code  null}）
	 * @param  contentType选择用于渲染的内容类型，它应与{@link  #getSupportedMediaTypes（）支持的媒体类型之一）
	 * @param 交换当前交换
	 * @return 一个{@code  Mono}，代表何时以及是否成功渲染
	 */
	@Override
	public Mono<Void> render(@Nullable Map<String, ?> model, @Nullable MediaType contentType,
			ServerWebExchange exchange) {

		if (logger.isDebugEnabled()) {
			logger.debug(exchange.getLogPrefix() + "View " + formatViewName() +
					", model " + (model != null ? model : Collections.emptyMap()));
		}

		if (contentType != null) {
			exchange.getResponse().getHeaders().setContentType(contentType);
		}

		return getModelAttributes(model, exchange).flatMap(mergedModel -> {
			// Expose RequestContext?
			if (this.requestContextAttribute != null) {
				mergedModel.put(this.requestContextAttribute, createRequestContext(exchange, mergedModel));
			}
			return renderInternal(mergedModel, contentType, exchange);
		});
	}

	/**
	 * Prepare the model to use for rendering.
	 * <p>The default implementation creates a combined output Map that includes
	 * model as well as static attributes with the former taking precedence.
	 */
	/**
	 * 准备要用于渲染的模型。 
	 *  <p>默认实现会创建一个包含模型和静态属性的组合输出Map，其中前者优先。 
	 * 
	 */
	protected Mono<Map<String, Object>> getModelAttributes(
			@Nullable Map<String, ?> model, ServerWebExchange exchange) {

		Map<String, Object> attributes;
		if (model != null) {
			attributes = new ConcurrentHashMap<>(model.size());
			for (Map.Entry<String, ?> entry : model.entrySet()) {
				if (entry.getValue() != null) {
					attributes.put(entry.getKey(), entry.getValue());
				}
			}
		}
		else {
			attributes = new ConcurrentHashMap<>(0);
		}

		//noinspection deprecation
		return resolveAsyncAttributes(attributes)
				.then(resolveAsyncAttributes(attributes, exchange))
				.doOnTerminate(() -> exchange.getAttributes().remove(BINDING_CONTEXT_ATTRIBUTE))
				.thenReturn(attributes);
	}

	/**
	 * Use the configured {@link ReactiveAdapterRegistry} to adapt asynchronous
	 * attributes to {@code Mono<T>} or {@code Mono<List<T>>} and then wait to
	 * resolve them into actual values. When the returned {@code Mono<Void>}
	 * completes, the asynchronous attributes in the model will have been
	 * replaced with their corresponding resolved values.
	 * @return result a {@code Mono} that completes when the model is ready
	 * @since 5.1.8
	 */
	/**
	 * 使用配置的{@link  ReactiveAdapterRegistry}将异步属性调整为{@code  Mono <T>}或{@code  Mono <List <T >>}，然后等待将其解析为实际值。 
	 * 当返回的{@code  Mono <Void>}完成时，模型中的异步属性将被替换为其对应的解析值。 
	 *  
	 * @return 生成一个{@code  Mono}，该模型在模型准备就绪时完成，从5.1.8版本开始
	 */
	protected Mono<Void> resolveAsyncAttributes(Map<String, Object> model, ServerWebExchange exchange) {
		List<Mono<?>> asyncAttributes = null;
		for (Map.Entry<String, ?> entry : model.entrySet()) {
			Object value =  entry.getValue();
			if (value == null) {
				continue;
			}
			ReactiveAdapter adapter = this.adapterRegistry.getAdapter(null, value);
			if (adapter != null) {
				if (asyncAttributes == null) {
					asyncAttributes = new ArrayList<>();
				}
				String name = entry.getKey();
				if (adapter.isMultiValue()) {
					asyncAttributes.add(
							Flux.from(adapter.toPublisher(value))
									.collectList()
									.doOnSuccess(result -> model.put(name, result)));
				}
				else {
					asyncAttributes.add(
							Mono.from(adapter.toPublisher(value))
									.doOnSuccess(result -> {
										if (result != null) {
											model.put(name, result);
											addBindingResult(name, result, model, exchange);
										}
										else {
											model.remove(name);
										}
									}));
				}
			}
		}
		return asyncAttributes != null ? Mono.when(asyncAttributes) : Mono.empty();
	}

	private void addBindingResult(String name, Object value, Map<String, Object> model, ServerWebExchange exchange) {
		BindingContext context = exchange.getAttribute(BINDING_CONTEXT_ATTRIBUTE);
		if (context == null || value.getClass().isArray() || value instanceof Collection ||
				value instanceof Map || BeanUtils.isSimpleValueType(value.getClass())) {
			return;
		}
		BindingResult result = context.createDataBinder(exchange, value, name).getBindingResult();
		model.put(BindingResult.MODEL_KEY_PREFIX + name, result);
	}

	/**
	 * Use the configured {@link ReactiveAdapterRegistry} to adapt asynchronous
	 * attributes to {@code Mono<T>} or {@code Mono<List<T>>} and then wait to
	 * resolve them into actual values. When the returned {@code Mono<Void>}
	 * completes, the asynchronous attributes in the model would have been
	 * replaced with their corresponding resolved values.
	 * @return result {@code Mono} that completes when the model is ready
	 * @deprecated as of 5.1.8 this method is still invoked but it is a no-op.
	 * Please use {@link #resolveAsyncAttributes(Map, ServerWebExchange)}
	 * instead. It is invoked after this one and does the actual work.
	 */
	/**
	 * 使用配置的{@link  ReactiveAdapterRegistry}将异步属性调整为{@code  Mono <T>}或{@code  Mono <List <T >>}，然后等待将其解析为实际值。 
	 * 当返回的{@code  Mono <Void>}完成时，模型中的异步属性将被替换为其相应的解析值。 
	 *  
	 * @return 结果{@code  Mono}在模型准备就绪时完成（@5.1.8弃用），此方法仍被调用，但它是空操作。 
	 * 请改用{@link  #resolveAsyncAttributes（Map，ServerWebExchange）}。 
	 * 在此之后将调用它并完成实际工作。 
	 * 
	 */
	@Deprecated
	protected Mono<Void> resolveAsyncAttributes(Map<String, Object> model) {
		return Mono.empty();
	}

	/**
	 * Create a {@link RequestContext} to expose under the
	 * {@linkplain #setRequestContextAttribute specified attribute name}.
	 * <p>The default implementation creates a standard {@code RequestContext}
	 * instance for the given exchange and model.
	 * <p>Can be overridden in subclasses to create custom instances.
	 * @param exchange the current exchange
	 * @param model a combined output Map (never {@code null}), with dynamic values
	 * taking precedence over static attributes
	 * @return the {@code RequestContext} instance
	 * @see #setRequestContextAttribute
	 */
	/**
	 * 创建一个{@link  RequestContext}，以在{@link  plain #setRequestContextAttribute指定的属性名称}下显示。 
	 *  <p>默认实现为给定的交换和模型创建一个标准的{@code  RequestContext}实例。 
	 *  <p>可以在子类中重写以创建自定义实例。 
	 *  
	 * @param 交换当前交换
	 * @param 为组合输出Map（永远{@code  null}）建模，动态值优先于静态属性
	 * @return  {@code  RequestContext}实例
	 * @see  #setRequestContextAttribute
	 */
	protected RequestContext createRequestContext(ServerWebExchange exchange, Map<String, Object> model) {
		return new RequestContext(exchange, model, obtainApplicationContext(), getRequestDataValueProcessor());
	}

	/**
	 * Get the {@link RequestDataValueProcessor} to use.
	 * <p>The default implementation looks in the {@link #getApplicationContext()
	 * ApplicationContext} for a {@code RequestDataValueProcessor} bean with
	 * the name {@link #REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME}.
	 * @return the {@code RequestDataValueProcessor}, or {@code null} if there is
	 * none in the application context
	 */
	/**
	 * 获取{@link  RequestDataValueProcessor}以使用。 
	 *  <p>默认实现在{@link  #getApplicationContext（）ApplicationContext}中查找名称为{@link  #REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME}的{@code  RequestDataValueProcessor} bean。 
	 *  
	 * @return  {@code  RequestDataValueProcessor}，如果应用程序上下文中没有，则为{@code  null}
	 */
	@Nullable
	protected RequestDataValueProcessor getRequestDataValueProcessor() {
		ApplicationContext context = getApplicationContext();
		if (context != null && context.containsBean(REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME)) {
			return context.getBean(REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME, RequestDataValueProcessor.class);
		}
		return null;
	}

	/**
	 * Subclasses must implement this method to actually render the view.
	 * @param renderAttributes combined output Map (never {@code null}),
	 * with dynamic values taking precedence over static attributes
	 * @param contentType the content type selected to render with, which should
	 * match one of the {@linkplain #getSupportedMediaTypes() supported media types}
	 * @param exchange current exchange
	 * @return a {@code Mono} that represents when and if rendering succeeds
	 */
	/**
	 * 子类必须实现此方法才能实际呈现视图。 
	 *  
	 * @param  renderAttributes组合了输出Map（永远不为{@code  null}），动态值优先于静态属性
	 * @param  contentType选择要呈现的内容类型，该类型应与{<@link > plain #getSupportedMediaTypes（）支持的媒体类型} 
	 * @param 交换当前交换
	 * @return 一个{@code  Mono}，表示何时以及是否成功渲染
	 */
	protected abstract Mono<Void> renderInternal(Map<String, Object> renderAttributes,
			@Nullable MediaType contentType, ServerWebExchange exchange);


	@Override
	public String toString() {
		return getClass().getName() + ": " + formatViewName();
	}

	protected String formatViewName() {
		return (getBeanName() != null ?
				"name '" + getBeanName() + "'" : "[" + getClass().getSimpleName() + "]");
	}

}
