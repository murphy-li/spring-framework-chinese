/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.server.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;
import org.springframework.web.server.handler.FilteringWebHandler;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;

/**
 * This builder has two purposes:
 *
 * <p>One is to assemble a processing chain that consists of a target {@link WebHandler},
 * then decorated with a set of {@link WebFilter WebFilters}, then further decorated with
 * a set of {@link WebExceptionHandler WebExceptionHandlers}.
 *
 * <p>The second purpose is to adapt the resulting processing chain to an {@link HttpHandler}:
 * the lowest-level reactive HTTP handling abstraction which can then be used with any of the
 * supported runtimes. The adaptation is done with the help of {@link HttpWebHandlerAdapter}.
 *
 * <p>The processing chain can be assembled manually via builder methods, or detected from
 * a Spring {@link ApplicationContext} via {@link #applicationContext}, or a mix of both.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 5.0
 * @see HttpWebHandlerAdapter
 */
/**
 * 该构建器有两个用途：<p>一个是组装由目标{@link  WebHandler}组成的处理链，然后用一组{@link  WebFilter WebFilters}装饰，然后进一步用一组装饰{@link  WebExceptionHandler WebExceptionHandlers}。 
 *  <p>第二个目的是使生成的处理链适应{@link  HttpHandler}：最低级别的响应HTTP处理抽象，然后可以将其与任何受支持的运行时一起使用。 
 * 适应是借助{@link  HttpWebHandlerAdapter}完成的。 
 *  <p>可以通过构建器方法手动组装处理链，也可以通过{@link  #applicationContext}从Spring {@link  ApplicationContext}进行检测，或者同时使用两者。 
 *  @author  Rossen Stoyanchev @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@从5.0起
 * @see  HttpWebHandlerAdapter
 */
public final class WebHttpHandlerBuilder {

	/** Well-known name for the target WebHandler in the bean factory. */
	/**
	 * Bean工厂中目标WebHandler的知名名称。 
	 * 
	 */
	public static final String WEB_HANDLER_BEAN_NAME = "webHandler";

	/** Well-known name for the WebSessionManager in the bean factory. */
	/**
	 * Bean工厂中WebSessionManager的知名名称。 
	 * 
	 */
	public static final String WEB_SESSION_MANAGER_BEAN_NAME = "webSessionManager";

	/** Well-known name for the ServerCodecConfigurer in the bean factory. */
	/**
	 * Bean工厂中ServerCodecConfigurer的知名名称。 
	 * 
	 */
	public static final String SERVER_CODEC_CONFIGURER_BEAN_NAME = "serverCodecConfigurer";

	/** Well-known name for the LocaleContextResolver in the bean factory. */
	/**
	 * Bean工厂中LocaleContextResolver的知名名称。 
	 * 
	 */
	public static final String LOCALE_CONTEXT_RESOLVER_BEAN_NAME = "localeContextResolver";

	/** Well-known name for the ForwardedHeaderTransformer in the bean factory. */
	/**
	 * Bean工厂中ForwardedHeaderTransformer的知名名称。 
	 * 
	 */
	public static final String FORWARDED_HEADER_TRANSFORMER_BEAN_NAME = "forwardedHeaderTransformer";


	private final WebHandler webHandler;

	@Nullable
	private final ApplicationContext applicationContext;

	private final List<WebFilter> filters = new ArrayList<>();

	private final List<WebExceptionHandler> exceptionHandlers = new ArrayList<>();

	@Nullable
	private WebSessionManager sessionManager;

	@Nullable
	private ServerCodecConfigurer codecConfigurer;

	@Nullable
	private LocaleContextResolver localeContextResolver;

	@Nullable
	private ForwardedHeaderTransformer forwardedHeaderTransformer;


	/**
	 * Private constructor to use when initialized from an ApplicationContext.
	 */
	/**
	 * 从ApplicationContext初始化时使用的私有构造函数。 
	 * 
	 */
	private WebHttpHandlerBuilder(WebHandler webHandler, @Nullable ApplicationContext applicationContext) {
		Assert.notNull(webHandler, "WebHandler must not be null");
		this.webHandler = webHandler;
		this.applicationContext = applicationContext;
	}

	/**
	 * Copy constructor.
	 */
	/**
	 * 复制构造函数。 
	 * 
	 */
	private WebHttpHandlerBuilder(WebHttpHandlerBuilder other) {
		this.webHandler = other.webHandler;
		this.applicationContext = other.applicationContext;
		this.filters.addAll(other.filters);
		this.exceptionHandlers.addAll(other.exceptionHandlers);
		this.sessionManager = other.sessionManager;
		this.codecConfigurer = other.codecConfigurer;
		this.localeContextResolver = other.localeContextResolver;
		this.forwardedHeaderTransformer = other.forwardedHeaderTransformer;
	}


	/**
	 * Static factory method to create a new builder instance.
	 * @param webHandler the target handler for the request
	 * @return the prepared builder
	 */
	/**
	 * 用于创建新的构建器实例的静态工厂方法。 
	 *  
	 * @param  webHandler请求的目标处理程序
	 * @return 准备好的构建器
	 */
	public static WebHttpHandlerBuilder webHandler(WebHandler webHandler) {
		return new WebHttpHandlerBuilder(webHandler, null);
	}

	/**
	 * Static factory method to create a new builder instance by detecting beans
	 * in an {@link ApplicationContext}. The following are detected:
	 * <ul>
	 * <li>{@link WebHandler} [1] -- looked up by the name
	 * {@link #WEB_HANDLER_BEAN_NAME}.
	 * <li>{@link WebFilter} [0..N] -- detected by type and ordered,
	 * see {@link AnnotationAwareOrderComparator}.
	 * <li>{@link WebExceptionHandler} [0..N] -- detected by type and
	 * ordered.
	 * <li>{@link WebSessionManager} [0..1] -- looked up by the name
	 * {@link #WEB_SESSION_MANAGER_BEAN_NAME}.
	 * <li>{@link ServerCodecConfigurer} [0..1] -- looked up by the name
	 * {@link #SERVER_CODEC_CONFIGURER_BEAN_NAME}.
	 * <li>{@link LocaleContextResolver} [0..1] -- looked up by the name
	 * {@link #LOCALE_CONTEXT_RESOLVER_BEAN_NAME}.
	 * </ul>
	 * @param context the application context to use for the lookup
	 * @return the prepared builder
	 */
	/**
	 * 通过检测{@link  ApplicationContext}中的bean来创建新的构建器实例的静态工厂方法。 
	 * 检测到以下内容：<ul> <li> {<@link> WebHandler} [1]-通过名称{@link  #WEB_HANDLER_BEAN_NAME}查找。 
	 *  <li> {<@link> WebFilter} [0..N]-按类型和顺序检测，请参见{@link  AnnotationAwareOrderComparator}。 
	 *  <li> {<@link> WebExceptionHandler} [0..N]-按类型检测并排序。 
	 *  <li> {<@link> WebSessionManager} [0..1]-通过名称{@link  #WEB_SESSION_MANAGER_BEAN_NAME}查找。 
	 *  <li> {<@link> ServerCodecConfigurer} [0..1]-通过名称{@link  #SERVER_CODEC_CONFIGURER_BEAN_NAME}查找。 
	 *  <li> {<@link> LocaleContextResolver} [0..1]-通过名称{@link  #LOCALE_CONTEXT_RESOLVER_BEAN_NAME}查找。 
	 *  </ ul> 
	 * @param 上下文用于查找的应用程序上下文<@r​​eturn>准备好的构建器
	 */
	public static WebHttpHandlerBuilder applicationContext(ApplicationContext context) {
		WebHttpHandlerBuilder builder = new WebHttpHandlerBuilder(
				context.getBean(WEB_HANDLER_BEAN_NAME, WebHandler.class), context);

		List<WebFilter> webFilters = context
				.getBeanProvider(WebFilter.class)
				.orderedStream()
				.collect(Collectors.toList());
		builder.filters(filters -> filters.addAll(webFilters));
		List<WebExceptionHandler> exceptionHandlers = context
				.getBeanProvider(WebExceptionHandler.class)
				.orderedStream()
				.collect(Collectors.toList());
		builder.exceptionHandlers(handlers -> handlers.addAll(exceptionHandlers));

		try {
			builder.sessionManager(
					context.getBean(WEB_SESSION_MANAGER_BEAN_NAME, WebSessionManager.class));
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Fall back on default
		}

		try {
			builder.codecConfigurer(
					context.getBean(SERVER_CODEC_CONFIGURER_BEAN_NAME, ServerCodecConfigurer.class));
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Fall back on default
		}

		try {
			builder.localeContextResolver(
					context.getBean(LOCALE_CONTEXT_RESOLVER_BEAN_NAME, LocaleContextResolver.class));
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Fall back on default
		}

		try {
			builder.localeContextResolver(
					context.getBean(LOCALE_CONTEXT_RESOLVER_BEAN_NAME, LocaleContextResolver.class));
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Fall back on default
		}

		try {
			builder.forwardedHeaderTransformer(
					context.getBean(FORWARDED_HEADER_TRANSFORMER_BEAN_NAME, ForwardedHeaderTransformer.class));
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Fall back on default
		}

		return builder;
	}


	/**
	 * Add the given filter(s).
	 * @param filters the filter(s) to add that's
	 */
	/**
	 * 添加给定的过滤器。 
	 *  
	 * @param 过滤要添加的过滤器
	 */
	public WebHttpHandlerBuilder filter(WebFilter... filters) {
		if (!ObjectUtils.isEmpty(filters)) {
			this.filters.addAll(Arrays.asList(filters));
			updateFilters();
		}
		return this;
	}

	/**
	 * Manipulate the "live" list of currently configured filters.
	 * @param consumer the consumer to use
	 */
	/**
	 * 操作当前配置的过滤器的"实时"列表。 
	 *  
	 * @param 消费者消费者使用
	 */
	public WebHttpHandlerBuilder filters(Consumer<List<WebFilter>> consumer) {
		consumer.accept(this.filters);
		updateFilters();
		return this;
	}

	private void updateFilters() {

		if (this.filters.isEmpty()) {
			return;
		}

		List<WebFilter> filtersToUse = this.filters.stream()
				.peek(filter -> {
					if (filter instanceof ForwardedHeaderTransformer && this.forwardedHeaderTransformer == null) {
						this.forwardedHeaderTransformer = (ForwardedHeaderTransformer) filter;
					}
				})
				.filter(filter -> !(filter instanceof ForwardedHeaderTransformer))
				.collect(Collectors.toList());

		this.filters.clear();
		this.filters.addAll(filtersToUse);
	}

	/**
	 * Add the given exception handler(s).
	 * @param handlers the exception handler(s)
	 */
	/**
	 * 添加给定的异常处理程序。 
	 *  
	 * @param 处理程序异常处理程序
	 */
	public WebHttpHandlerBuilder exceptionHandler(WebExceptionHandler... handlers) {
		if (!ObjectUtils.isEmpty(handlers)) {
			this.exceptionHandlers.addAll(Arrays.asList(handlers));
		}
		return this;
	}

	/**
	 * Manipulate the "live" list of currently configured exception handlers.
	 * @param consumer the consumer to use
	 */
	/**
	 * 操作当前配置的异常处理程序的"实时"列表。 
	 *  
	 * @param 消费者消费者使用
	 */
	public WebHttpHandlerBuilder exceptionHandlers(Consumer<List<WebExceptionHandler>> consumer) {
		consumer.accept(this.exceptionHandlers);
		return this;
	}

	/**
	 * Configure the {@link WebSessionManager} to set on the
	 * {@link ServerWebExchange WebServerExchange}.
	 * <p>By default {@link DefaultWebSessionManager} is used.
	 * @param manager the session manager
	 * @see HttpWebHandlerAdapter#setSessionManager(WebSessionManager)
	 */
	/**
	 * 配置{@link  WebSessionManager}以在{@link  ServerWebExchange WebServerExchange}上进行设置。 
	 *  <p>默认情况下使用{@link  DefaultWebSessionManager}。 
	 *  
	 * @param 管理器会话管理器
	 * @see  HttpWebHandlerAdapter＃setSessionManager（WebSessionManager）
	 */
	public WebHttpHandlerBuilder sessionManager(WebSessionManager manager) {
		this.sessionManager = manager;
		return this;
	}

	/**
	 * Whether a {@code WebSessionManager} is configured or not, either detected from an
	 * {@code ApplicationContext} or explicitly configured via {@link #sessionManager}.
	 * @since 5.0.9
	 */
	/**
	 * 从{@code  ApplicationContext}中检测到还是通过{@link  #sessionManager}显式配置了是否配置了{@code  WebSessionManager}。 
	 *  @5.0.9起
	 */
	public boolean hasSessionManager() {
		return (this.sessionManager != null);
	}

	/**
	 * Configure the {@link ServerCodecConfigurer} to set on the {@code WebServerExchange}.
	 * @param codecConfigurer the codec configurer
	 */
	/**
	 * 配置{@link  ServerCodecConfigurer}以在{@code  WebServerExchange}上进行设置。 
	 *  
	 * @param  codecConfigurer编解码器配置器
	 */
	public WebHttpHandlerBuilder codecConfigurer(ServerCodecConfigurer codecConfigurer) {
		this.codecConfigurer = codecConfigurer;
		return this;
	}


	/**
	 * Whether a {@code ServerCodecConfigurer} is configured or not, either detected from an
	 * {@code ApplicationContext} or explicitly configured via {@link #codecConfigurer}.
	 * @since 5.0.9
	 */
	/**
	 * 是否配置了{@code  ServerCodecConfigurer}，或者从{@code  ApplicationContext}中检测到，还是通过{@link  #codecConfigurer}进行了显式配置。 
	 *  @5.0.9起
	 */
	public boolean hasCodecConfigurer() {
		return (this.codecConfigurer != null);
	}

	/**
	 * Configure the {@link LocaleContextResolver} to set on the
	 * {@link ServerWebExchange WebServerExchange}.
	 * @param localeContextResolver the locale context resolver
	 */
	/**
	 * 配置{@link  LocaleContextResolver}以在{@link  ServerWebExchange WebServerExchange}上进行设置。 
	 *  
	 * @param  localeContextResolver语言环境上下文解析器
	 */
	public WebHttpHandlerBuilder localeContextResolver(LocaleContextResolver localeContextResolver) {
		this.localeContextResolver = localeContextResolver;
		return this;
	}

	/**
	 * Whether a {@code LocaleContextResolver} is configured or not, either detected from an
	 * {@code ApplicationContext} or explicitly configured via {@link #localeContextResolver}.
	 * @since 5.0.9
	 */
	/**
	 * 从{@code  ApplicationContext}检测到还是通过{@link  #localeContextResolver}显式配置了{@code  LocaleContextResolver}是否已配置。 
	 *  @5.0.9起
	 */
	public boolean hasLocaleContextResolver() {
		return (this.localeContextResolver != null);
	}

	/**
	 * Configure the {@link ForwardedHeaderTransformer} for extracting and/or
	 * removing forwarded headers.
	 * @param transformer the transformer
	 * @since 5.1
	 */
	/**
	 * 配置{@link  ForwardedHeaderTransformer}以提取和/或删除转发的标头。 
	 *  
	 * @param 变压器@5.1起
	 */
	public WebHttpHandlerBuilder forwardedHeaderTransformer(ForwardedHeaderTransformer transformer) {
		this.forwardedHeaderTransformer = transformer;
		return this;
	}

	/**
	 * Whether a {@code ForwardedHeaderTransformer} is configured or not, either
	 * detected from an {@code ApplicationContext} or explicitly configured via
	 * {@link #forwardedHeaderTransformer(ForwardedHeaderTransformer)}.
	 * @since 5.1
	 */
	/**
	 * 从{@code  ApplicationContext}检测到还是通过{@link  #forwardedHeaderTransformer（ForwardedHeaderTransformer）}显式配置了{@code  ForwardedHeaderTransformer}是否已配置。 
	 *  @5.1起
	 */
	public boolean hasForwardedHeaderTransformer() {
		return (this.forwardedHeaderTransformer != null);
	}


	/**
	 * Build the {@link HttpHandler}.
	 */
	/**
	 * 生成{@link  HttpHandler}。 
	 * 
	 */
	public HttpHandler build() {

		WebHandler decorated = new FilteringWebHandler(this.webHandler, this.filters);
		decorated = new ExceptionHandlingWebHandler(decorated,  this.exceptionHandlers);

		HttpWebHandlerAdapter adapted = new HttpWebHandlerAdapter(decorated);
		if (this.sessionManager != null) {
			adapted.setSessionManager(this.sessionManager);
		}
		if (this.codecConfigurer != null) {
			adapted.setCodecConfigurer(this.codecConfigurer);
		}
		if (this.localeContextResolver != null) {
			adapted.setLocaleContextResolver(this.localeContextResolver);
		}
		if (this.forwardedHeaderTransformer != null) {
			adapted.setForwardedHeaderTransformer(this.forwardedHeaderTransformer);
		}
		if (this.applicationContext != null) {
			adapted.setApplicationContext(this.applicationContext);
		}
		adapted.afterPropertiesSet();

		return adapted;
	}

	/**
	 * Clone this {@link WebHttpHandlerBuilder}.
	 * @return the cloned builder instance
	 */
	/**
	 * 克隆此{@link  WebHttpHandlerBuilder}。 
	 *  
	 * @return 克隆的构建器实例
	 */
	@Override
	public WebHttpHandlerBuilder clone() {
		return new WebHttpHandlerBuilder(this);
	}

}
