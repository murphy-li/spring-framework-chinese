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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.WebExchangeDataBinder;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.HandlerResultHandler;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.HandlerResultHandlerSupport;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerWebExchange;

/**
 * {@code HandlerResultHandler} that encapsulates the view resolution algorithm
 * supporting the following return types:
 * <ul>
 * <li>{@link Void} or no value -- default view name</li>
 * <li>{@link String} -- view name unless {@code @ModelAttribute}-annotated
 * <li>{@link View} -- View to render with
 * <li>{@link Model} -- attributes to add to the model
 * <li>{@link Map} -- attributes to add to the model
 * <li>{@link Rendering} -- use case driven API for view resolution</li>
 * <li>{@link ModelAttribute @ModelAttribute} -- attribute for the model
 * <li>Non-simple value -- attribute for the model
 * </ul>
 *
 * <p>A String-based view name is resolved through the configured
 * {@link ViewResolver} instances into a {@link View} to use for rendering.
 * If a view is left unspecified (e.g. by returning {@code null} or a
 * model-related return value), a default view name is selected.
 *
 * <p>By default this resolver is ordered at {@link Ordered#LOWEST_PRECEDENCE}
 * and generally needs to be late in the order since it interprets any String
 * return value as a view name or any non-simple value type as a model attribute
 * while other result handlers may interpret the same otherwise based on the
 * presence of annotations, e.g. for {@code @ResponseBody}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * {@code  HandlerResultHandler}封装了支持以下返回类型的视图解析算法：<ul> <li> {<@link> Void}或无值-默认视图名称</ li> <li> {<@link>字符串}-视图名称，除非{@code  @ModelAttribute}-带注释的<li> {<@link>视图}-使用<li> {<@link> Model}渲染的视图-要添加的属性到模型<li> {<@link>映射} –添加到模型<li> {<@link>渲染}的属性–用例驱动的API来实现视图分辨率</ li> <li> {<@link> ModelAttribute @ModelAttribute}-模型的属性<li>非简单值-模型的属性</ ul> <p>基于字符串的视图名称是通过配置的{@link  ViewResolver}解析的实例放入{@link 视图}进行渲染。 
 * 如果未指定视图（例如，通过返回{@code  null}或与模型相关的返回值），则会选择默认视图名称。 
 *  <p>默认情况下，此解析器在{@link  Ordered＃LOWEST_PRECEDENCE}处订购，并且通常需要延迟到该顺序，因为它将任何String返回值解释为视图名称或将任何非简单值类型解释为模型属性。 
 * 而其他结果处理程序可能会根据注释的存在以其他方式解释相同内容，例如为{@code  @ResponseBody}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class ViewResolutionResultHandler extends HandlerResultHandlerSupport implements HandlerResultHandler, Ordered {

	private static final Object NO_VALUE = new Object();

	private static final Mono<Object> NO_VALUE_MONO = Mono.just(NO_VALUE);


	private final List<ViewResolver> viewResolvers = new ArrayList<>(4);

	private final List<View> defaultViews = new ArrayList<>(4);


	/**
	 * Basic constructor with a default {@link ReactiveAdapterRegistry}.
	 * @param viewResolvers the resolver to use
	 * @param contentTypeResolver to determine the requested content type
	 */
	/**
	 * 基本构造函数，默认为{@link  ReactiveAdapterRegistry}。 
	 *  
	 * @param  viewResolvers解析器使用
	 * @param  contentTypeResolver确定请求的内容类型
	 */
	public ViewResolutionResultHandler(List<ViewResolver> viewResolvers,
			RequestedContentTypeResolver contentTypeResolver) {

		this(viewResolvers, contentTypeResolver, ReactiveAdapterRegistry.getSharedInstance());
	}

	/**
	 * Constructor with an {@link ReactiveAdapterRegistry} instance.
	 * @param viewResolvers the view resolver to use
	 * @param contentTypeResolver to determine the requested content type
	 * @param registry for adaptation to reactive types
	 */
	/**
	 * 具有{@link  ReactiveAdapterRegistry}实例的构造函数。 
	 *  
	 * @param  viewResolvers视图解析器使用
	 * @param  contentTypeResolver来确定请求的内容类型
	 * @param 注册表以适应反应性类型
	 */
	public ViewResolutionResultHandler(List<ViewResolver> viewResolvers,
			RequestedContentTypeResolver contentTypeResolver, ReactiveAdapterRegistry registry) {

		super(contentTypeResolver, registry);
		this.viewResolvers.addAll(viewResolvers);
		AnnotationAwareOrderComparator.sort(this.viewResolvers);
	}


	/**
	 * Return a read-only list of view resolvers.
	 */
	/**
	 * 返回视图解析器的只读列表。 
	 * 
	 */
	public List<ViewResolver> getViewResolvers() {
		return Collections.unmodifiableList(this.viewResolvers);
	}

	/**
	 * Set the default views to consider always when resolving view names and
	 * trying to satisfy the best matching content type.
	 */
	/**
	 * 设置默认视图，以便在解析视图名称并尝试满足最佳匹配的内容类型时始终考虑。 
	 * 
	 */
	public void setDefaultViews(@Nullable List<View> defaultViews) {
		this.defaultViews.clear();
		if (defaultViews != null) {
			this.defaultViews.addAll(defaultViews);
		}
	}

	/**
	 * Return the configured default {@code View}'s.
	 */
	/**
	 * 返回配置的默认{@code  View}。 
	 * 
	 */
	public List<View> getDefaultViews() {
		return this.defaultViews;
	}


	@Override
	public boolean supports(HandlerResult result) {
		if (hasModelAnnotation(result.getReturnTypeSource())) {
			return true;
		}

		Class<?> type = result.getReturnType().toClass();
		ReactiveAdapter adapter = getAdapter(result);
		if (adapter != null) {
			if (adapter.isNoValue()) {
				return true;
			}
			type = result.getReturnType().getGeneric().toClass();
		}

		return (CharSequence.class.isAssignableFrom(type) ||
				Rendering.class.isAssignableFrom(type) ||
				Model.class.isAssignableFrom(type) ||
				Map.class.isAssignableFrom(type) ||
				View.class.isAssignableFrom(type) ||
				!BeanUtils.isSimpleProperty(type));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
		Mono<Object> valueMono;
		ResolvableType valueType;
		ReactiveAdapter adapter = getAdapter(result);

		if (adapter != null) {
			if (adapter.isMultiValue()) {
				throw new IllegalArgumentException(
						"Multi-value reactive types not supported in view resolution: " + result.getReturnType());
			}

			valueMono = (result.getReturnValue() != null ?
					Mono.from(adapter.toPublisher(result.getReturnValue())) : Mono.empty());

			valueType = (adapter.isNoValue() ? ResolvableType.forClass(Void.class) :
					result.getReturnType().getGeneric());
		}
		else {
			valueMono = Mono.justOrEmpty(result.getReturnValue());
			valueType = result.getReturnType();
		}

		return valueMono
				.switchIfEmpty(exchange.isNotModified() ? Mono.empty() : NO_VALUE_MONO)
				.flatMap(returnValue -> {

					Mono<List<View>> viewsMono;
					Model model = result.getModel();
					MethodParameter parameter = result.getReturnTypeSource();
					Locale locale = LocaleContextHolder.getLocale(exchange.getLocaleContext());

					Class<?> clazz = valueType.toClass();
					if (clazz == Object.class) {
						clazz = returnValue.getClass();
					}

					if (returnValue == NO_VALUE || clazz == void.class || clazz == Void.class) {
						viewsMono = resolveViews(getDefaultViewName(exchange), locale);
					}
					else if (CharSequence.class.isAssignableFrom(clazz) && !hasModelAnnotation(parameter)) {
						viewsMono = resolveViews(returnValue.toString(), locale);
					}
					else if (Rendering.class.isAssignableFrom(clazz)) {
						Rendering render = (Rendering) returnValue;
						HttpStatus status = render.status();
						if (status != null) {
							exchange.getResponse().setStatusCode(status);
						}
						exchange.getResponse().getHeaders().putAll(render.headers());
						model.addAllAttributes(render.modelAttributes());
						Object view = render.view();
						if (view == null) {
							view = getDefaultViewName(exchange);
						}
						viewsMono = (view instanceof String ? resolveViews((String) view, locale) :
								Mono.just(Collections.singletonList((View) view)));
					}
					else if (Model.class.isAssignableFrom(clazz)) {
						model.addAllAttributes(((Model) returnValue).asMap());
						viewsMono = resolveViews(getDefaultViewName(exchange), locale);
					}
					else if (Map.class.isAssignableFrom(clazz) && !hasModelAnnotation(parameter)) {
						model.addAllAttributes((Map<String, ?>) returnValue);
						viewsMono = resolveViews(getDefaultViewName(exchange), locale);
					}
					else if (View.class.isAssignableFrom(clazz)) {
						viewsMono = Mono.just(Collections.singletonList((View) returnValue));
					}
					else {
						String name = getNameForReturnValue(parameter);
						model.addAttribute(name, returnValue);
						viewsMono = resolveViews(getDefaultViewName(exchange), locale);
					}
					BindingContext bindingContext = result.getBindingContext();
					updateBindingResult(bindingContext, exchange);
					return viewsMono.flatMap(views -> render(views, model.asMap(), bindingContext, exchange));
				});
	}


	private boolean hasModelAnnotation(MethodParameter parameter) {
		return parameter.hasMethodAnnotation(ModelAttribute.class);
	}

	/**
	 * Select a default view name when a controller did not specify it.
	 * Use the request path the leading and trailing slash stripped.
	 */
	/**
	 * 当控制器未指定默认视图名称时，请选择它。 
	 * 使用请求路径将前斜杠和后斜杠去除。 
	 * 
	 */
	private String getDefaultViewName(ServerWebExchange exchange) {
		String path = exchange.getRequest().getPath().pathWithinApplication().value();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return StringUtils.stripFilenameExtension(path);
	}

	private Mono<List<View>> resolveViews(String viewName, Locale locale) {
		return Flux.fromIterable(getViewResolvers())
				.concatMap(resolver -> resolver.resolveViewName(viewName, locale))
				.collectList()
				.map(views -> {
					if (views.isEmpty()) {
						throw new IllegalStateException(
								"Could not resolve view with name '" + viewName + "'.");
					}
					views.addAll(getDefaultViews());
					return views;
				});
	}

	private String getNameForReturnValue(MethodParameter returnType) {
		return Optional.ofNullable(returnType.getMethodAnnotation(ModelAttribute.class))
				.filter(ann -> StringUtils.hasText(ann.value()))
				.map(ModelAttribute::value)
				.orElseGet(() -> Conventions.getVariableNameForParameter(returnType));
	}

	private void updateBindingResult(BindingContext context, ServerWebExchange exchange) {
		Map<String, Object> model = context.getModel().asMap();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (isBindingCandidate(name, value)) {
				if (!model.containsKey(BindingResult.MODEL_KEY_PREFIX + name)) {
					WebExchangeDataBinder binder = context.createDataBinder(exchange, value, name);
					model.put(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
				}
			}
		}
	}

	private boolean isBindingCandidate(String name, @Nullable Object value) {
		return (!name.startsWith(BindingResult.MODEL_KEY_PREFIX) && value != null &&
				!value.getClass().isArray() && !(value instanceof Collection) && !(value instanceof Map) &&
				getAdapterRegistry().getAdapter(null, value) == null &&
				!BeanUtils.isSimpleValueType(value.getClass()));
	}

	private Mono<? extends Void> render(List<View> views, Map<String, Object> model,
			BindingContext bindingContext, ServerWebExchange exchange) {

		for (View view : views) {
			if (view.isRedirectView()) {
				return renderWith(view, model, null, exchange, bindingContext);
			}
		}
		List<MediaType> mediaTypes = getMediaTypes(views);
		MediaType bestMediaType = selectMediaType(exchange, () -> mediaTypes);
		if (bestMediaType != null) {
			for (View view : views) {
				for (MediaType mediaType : view.getSupportedMediaTypes()) {
					if (mediaType.isCompatibleWith(bestMediaType)) {
						return renderWith(view, model, mediaType, exchange, bindingContext);
					}
				}
			}
		}
		throw new NotAcceptableStatusException(mediaTypes);
	}

	private Mono<? extends Void> renderWith(View view, Map<String, Object> model,
			@Nullable MediaType mediaType, ServerWebExchange exchange, BindingContext bindingContext) {

		exchange.getAttributes().put(View.BINDING_CONTEXT_ATTRIBUTE, bindingContext);
		return view.render(model, mediaType, exchange)
				.doOnTerminate(() -> exchange.getAttributes().remove(View.BINDING_CONTEXT_ATTRIBUTE));
	}

	private List<MediaType> getMediaTypes(List<View> views) {
		return views.stream()
				.flatMap(view -> view.getSupportedMediaTypes().stream())
				.collect(Collectors.toList());
	}

}
