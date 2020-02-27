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

package org.springframework.web.servlet.config.annotation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

/**
 * Assists with the registration of simple automated controllers pre-configured
 * with status code and/or a view.
 *
 * @author Rossen Stoyanchev
 * @author Keith Donald
 * @since 3.1
 */
/**
 * 协助注册预先配置有状态码和/或视图的简单自动化控制器。 
 *  @author  Rossen Stoyanchev @author 基思·唐纳德@since 3.1
 */
public class ViewControllerRegistry {

	@Nullable
	private ApplicationContext applicationContext;

	private final List<ViewControllerRegistration> registrations = new ArrayList<>(4);

	private final List<RedirectViewControllerRegistration> redirectRegistrations = new ArrayList<>(10);

	private int order = 1;


	/**
	 * Class constructor with {@link ApplicationContext}.
	 * @since 4.3.12
	 */
	/**
	 * 具有{@link  ApplicationContext}的类构造函数。 
	 *  @自4.3.12起
	 */
	public ViewControllerRegistry(@Nullable ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}


	/**
	 * Map a view controller to the given URL path (or pattern) in order to render
	 * a response with a pre-configured status code and view.
	 * <p>Patterns like {@code "/admin/**"} or {@code "/articles/{articlename:\\w+}"}
	 * are allowed. See {@link org.springframework.util.AntPathMatcher} for more details on the
	 * syntax.
	 * <p><strong>Note:</strong> If an {@code @RequestMapping} method is mapped
	 * to a URL for any HTTP method then a view controller cannot handle the
	 * same URL. For this reason it is recommended to avoid splitting URL
	 * handling across an annotated controller and a view controller.
	 */
	/**
	 * 将视图控制器映射到给定的URL路径（或模式），以使用预先配置的状态代码和视图来呈现响应。 
	 *  <p>允许使用类似{@code "/ admin"}或{@code "/ articles / {articlename：\\ w +}"}}的模式。 
	 * 有关语法的更多详细信息，请参见{@link  org.springframework.util.AntPathMatcher}。 
	 *  <p> <strong>注意</ strong>：如果将{@code  @RequestMapping}方法映射到任何HTTP方法的URL，则视图控制器将无法处理相同的URL。 
	 * 因此，建议避免在带注释的控制器和视图控制器之间拆分URL处理。 
	 * 
	 */
	public ViewControllerRegistration addViewController(String urlPath) {
		ViewControllerRegistration registration = new ViewControllerRegistration(urlPath);
		registration.setApplicationContext(this.applicationContext);
		this.registrations.add(registration);
		return registration;
	}

	/**
	 * Map a view controller to the given URL path (or pattern) in order to redirect
	 * to another URL. By default the redirect URL is expected to be relative to
	 * the current ServletContext, i.e. as relative to the web application root.
	 * @since 4.1
	 */
	/**
	 * 将视图控制器映射到给定的URL路径（或模式），以便重定向到另一个URL。 
	 * 默认情况下，重定向URL应相对于当前ServletContext，即相对于Web应用程序根目录。 
	 *  @始于4.1
	 */
	public RedirectViewControllerRegistration addRedirectViewController(String urlPath, String redirectUrl) {
		RedirectViewControllerRegistration registration = new RedirectViewControllerRegistration(urlPath, redirectUrl);
		registration.setApplicationContext(this.applicationContext);
		this.redirectRegistrations.add(registration);
		return registration;
	}

	/**
	 * Map a simple controller to the given URL path (or pattern) in order to
	 * set the response status to the given code without rendering a body.
	 * @since 4.1
	 */
	/**
	 * 将简单的控制器映射到给定的URL路径（或模式），以便在不渲染正文的情况下将响应状态设置为给定的代码。 
	 *  @始于4.1
	 */
	public void addStatusController(String urlPath, HttpStatus statusCode) {
		ViewControllerRegistration registration = new ViewControllerRegistration(urlPath);
		registration.setApplicationContext(this.applicationContext);
		registration.setStatusCode(statusCode);
		registration.getViewController().setStatusOnly(true);
		this.registrations.add(registration);
	}

	/**
	 * Specify the order to use for the {@code HandlerMapping} used to map view
	 * controllers relative to other handler mappings configured in Spring MVC.
	 * <p>By default this is set to 1, i.e. right after annotated controllers,
	 * which are ordered at 0.
	 */
	/**
	 * 指定用于{@code  HandlerMapping}的顺序，该顺序用于将视图控制器相对于Spring MVC中配置的其他处理程序映射进行映射。 
	 *  <p>默认情况下将其设置为1，即紧接在带注释的控制器之后，该控制器的编号为0。 
	 * 
	 */
	public void setOrder(int order) {
		this.order = order;
	}


	/**
	 * Return the {@code HandlerMapping} that contains the registered view
	 * controller mappings, or {@code null} for no registrations.
	 * @since 4.3.12
	 */
	/**
	 * 返回包含已注册视图控制器映射的{@code  HandlerMapping}，或者返回{@code  null}表示没有注册。 
	 *  @自4.3.12起
	 */
	@Nullable
	protected SimpleUrlHandlerMapping buildHandlerMapping() {
		if (this.registrations.isEmpty() && this.redirectRegistrations.isEmpty()) {
			return null;
		}

		Map<String, Object> urlMap = new LinkedHashMap<>();
		for (ViewControllerRegistration registration : this.registrations) {
			urlMap.put(registration.getUrlPath(), registration.getViewController());
		}
		for (RedirectViewControllerRegistration registration : this.redirectRegistrations) {
			urlMap.put(registration.getUrlPath(), registration.getViewController());
		}

		return new SimpleUrlHandlerMapping(urlMap, this.order);
	}

}
