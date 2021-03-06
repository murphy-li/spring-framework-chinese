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

package org.springframework.web.servlet.config.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * Assists with the creation of a {@link MappedInterceptor}.
 *
 * @author Rossen Stoyanchev
 * @author Keith Donald
 * @since 3.1
 */
/**
 * 协助创建{@link  MappedInterceptor}。 
 *  @author  Rossen Stoyanchev @author 基思·唐纳德@since 3.1
 */
public class InterceptorRegistration {

	private final HandlerInterceptor interceptor;

	private final List<String> includePatterns = new ArrayList<>();

	private final List<String> excludePatterns = new ArrayList<>();

	@Nullable
	private PathMatcher pathMatcher;

	private int order = 0;


	/**
	 * Create an {@link InterceptorRegistration} instance.
	 */
	/**
	 * 创建一个{@link 拦截器注册}实例。 
	 * 
	 */
	public InterceptorRegistration(HandlerInterceptor interceptor) {
		Assert.notNull(interceptor, "Interceptor is required");
		this.interceptor = interceptor;
	}


	/**
	 * Add URL patterns to which the registered interceptor should apply to.
	 */
	/**
	 * 添加注册的拦截器应应用于的URL模式。 
	 * 
	 */
	public InterceptorRegistration addPathPatterns(String... patterns) {
		return addPathPatterns(Arrays.asList(patterns));
	}

	/**
	 * List-based variant of {@link #addPathPatterns(String...)}.
	 * @since 5.0.3
	 */
	/**
	 * {@link  #addPathPatterns（String ...）}的基于列表的变体。 
	 *  @5.0.3起
	 */
	public InterceptorRegistration addPathPatterns(List<String> patterns) {
		this.includePatterns.addAll(patterns);
		return this;
	}

	/**
	 * Add URL patterns to which the registered interceptor should not apply to.
	 */
	/**
	 * 添加不应应用于已注册拦截器的URL模式。 
	 * 
	 */
	public InterceptorRegistration excludePathPatterns(String... patterns) {
		return excludePathPatterns(Arrays.asList(patterns));
	}

	/**
	 * List-based variant of {@link #excludePathPatterns(String...)}.
	 * @since 5.0.3
	 */
	/**
	 * {@link  #excludePathPatterns（String ...）}的基于列表的变体。 
	 *  @5.0.3起
	 */
	public InterceptorRegistration excludePathPatterns(List<String> patterns) {
		this.excludePatterns.addAll(patterns);
		return this;
	}

	/**
	 * A PathMatcher implementation to use with this interceptor. This is an optional,
	 * advanced property required only if using custom PathMatcher implementations
	 * that support mapping metadata other than the Ant path patterns supported
	 * by default.
	 */
	/**
	 * 与该拦截器一起使用的PathMatcher实现。 
	 * 仅当使用支持映射元数据（默认情况下不支持的Ant路径模式）的自定义PathMatcher实现时，才需要此可选的高级属性。 
	 * 
	 */
	public InterceptorRegistration pathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
		return this;
	}

	/**
	 * Specify an order position to be used. Default is 0.
	 * @since 4.3.23
	 */
	/**
	 * 指定要使用的订单头寸。 
	 * 默认值为0。 
	 * @4.3.23起
	 */
	public InterceptorRegistration order(int order){
		this.order = order;
		return this;
	}

	/**
	 * Return the order position to be used.
	 */
	/**
	 * 返回要使用的订单头寸。 
	 * 
	 */
	protected int getOrder() {
		return this.order;
	}

	/**
	 * Build the underlying interceptor. If URL patterns are provided, the returned
	 * type is {@link MappedInterceptor}; otherwise {@link HandlerInterceptor}.
	 */
	/**
	 * 构建基础拦截器。 
	 * 如果提供了URL模式，则返回的类型为{@link  MappedInterceptor};否则为{@link  HandlerInterceptor}。 
	 * 
	 */
	protected Object getInterceptor() {
		if (this.includePatterns.isEmpty() && this.excludePatterns.isEmpty()) {
			return this.interceptor;
		}

		String[] include = StringUtils.toStringArray(this.includePatterns);
		String[] exclude = StringUtils.toStringArray(this.excludePatterns);
		MappedInterceptor mappedInterceptor = new MappedInterceptor(include, exclude, this.interceptor);
		if (this.pathMatcher != null) {
			mappedInterceptor.setPathMatcher(this.pathMatcher);
		}
		return mappedInterceptor;
	}

}
