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

import java.util.Locale;
import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

/**
 * A {@link ViewResolver} that allows direct resolution of symbolic view names
 * to URLs without explicit mapping definitions. This is useful if symbolic names
 * match the names of view resources in a straightforward manner (i.e. the
 * symbolic name is the unique part of the resource's filename), without the need
 * for a dedicated mapping to be defined for each view.
 *
 * <p>Supports {@link AbstractUrlBasedView} subclasses like
 * {@link org.springframework.web.reactive.result.view.freemarker.FreeMarkerView}.
 * The view class for all views generated by this resolver can be specified
 * via the "viewClass" property.
 *
 * <p>View names can either be resource URLs themselves, or get augmented by a
 * specified prefix and/or suffix. Exporting an attribute that holds the
 * RequestContext to all views is explicitly supported.
 *
 * <p>Example: prefix="templates/", suffix=".ftl", viewname="test" ->
 * "templates/test.ftl"
 *
 * <p>As a special feature, redirect URLs can be specified via the "redirect:"
 * prefix. E.g.: "redirect:myAction" will trigger a redirect to the given
 * URL, rather than resolution as standard view name. This is typically used
 * for redirecting to a controller URL after finishing a form workflow.
 *
 * <p>Note: This class does not support localized resolution, i.e. resolving
 * a symbolic view name to different resources depending on the current locale.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 5.0
 */
/**
 * {@link  ViewResolver}允许将符号视图名称直接解析为URL，而无需显式映射定义。 
 * 如果符号名称以直接的方式与视图资源的名称匹配（即符号名称是资源文件名的唯一部分），而无需为每个视图定义专用映射，则这将很有用。 
 *  <p>支持{@link  AbstractUrlBasedView}子类，例如{@link  org.springframework.web.reactive.result.view.freemarker.FreeMarkerView}。 
 * 可以通过"viewClass"属性指定此解析器生成的所有视图的视图类。 
 *  <p>视图名称可以是资源URL本身，也可以通过指定的前缀和/或后缀进行扩充。 
 * 明确支持将保存RequestContext的属性导出到所有视图。 
 *  <p>示例：prefix ="templates /"，后缀="。 
 * ftl"，viewname ="test"->"templates / test.ftl"<p>作为一项特殊功能，可以通过"重定向"指定重定向URL。 
 *  ："字首。 
 * 例如："redirect：myAction"将触发重定向到给定的URL，而不是解析为标准视图名称。 
 * 这通常用于在完成表单工作流程后重定向到控制器URL。 
 *  <p>注意：此类不支持本地化的解析，即根据当前语言环境将符号视图名称解析为不同的资源。 
 *  @author  Rossen Stoyanchev @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Juergen Hoeller @author  Sam Brannen @since 5.0
 */
public class UrlBasedViewResolver extends ViewResolverSupport
		implements ViewResolver, ApplicationContextAware, InitializingBean {

	/**
	 * Prefix for special view names that specify a redirect URL (usually
	 * to a controller after a form has been submitted and processed).
	 * Such view names will not be resolved in the configured default
	 * way but rather be treated as special shortcut.
	 */
	/**
	 * 指定重定向URL的特殊视图名称的前缀（通常在提交和处理表单后添加到控制器）。 
	 * 此类视图名称将不会以配置的默认方式解析，而是被视为特殊快捷方式。 
	 * 
	 */
	public static final String REDIRECT_URL_PREFIX = "redirect:";


	@Nullable
	private Class<?> viewClass;

	private String prefix = "";

	private String suffix = "";

	@Nullable
	private String[] viewNames;

	private Function<String, RedirectView> redirectViewProvider = RedirectView::new;

	@Nullable
	private String requestContextAttribute;

	@Nullable
	private ApplicationContext applicationContext;


	/**
	 * Set the view class to instantiate through {@link #createView(String)}.
	 * @param viewClass a class that is assignable to the required view class
	 * which by default is AbstractUrlBasedView
	 */
	/**
	 * 将视图类设置为通过{@link  #createView（String）}实例化。 
	 *  
	 * @param  viewClass一个可分配给所需视图类的类，默认情况下该类为AbstractUrlBasedView
	 */
	public void setViewClass(@Nullable Class<?> viewClass) {
		if (viewClass != null && !requiredViewClass().isAssignableFrom(viewClass)) {
			String name = viewClass.getName();
			throw new IllegalArgumentException("Given view class [" + name + "] " +
					"is not of type [" + requiredViewClass().getName() + "]");
		}
		this.viewClass = viewClass;
	}

	/**
	 * Return the view class to be used to create views.
	 */
	/**
	 * 返回用于创建视图的视图类。 
	 * 
	 */
	@Nullable
	protected Class<?> getViewClass() {
		return this.viewClass;
	}

	/**
	 * Return the required type of view for this resolver.
	 * This implementation returns {@link AbstractUrlBasedView}.
	 * @see AbstractUrlBasedView
	 */
	/**
	 * 返回此解析器所需的视图类型。 
	 * 此实现返回{@link  AbstractUrlBasedView}。 
	 *  
	 * @see  AbstractUrlBasedView
	 */
	protected Class<?> requiredViewClass() {
		return AbstractUrlBasedView.class;
	}

	/**
	 * Set the prefix that gets prepended to view names when building a URL.
	 */
	/**
	 * 设置在构建URL时可用来查看名称的前缀。 
	 * 
	 */
	public void setPrefix(@Nullable String prefix) {
		this.prefix = (prefix != null ? prefix : "");
	}

	/**
	 * Return the prefix that gets prepended to view names when building a URL.
	 */
	/**
	 * 返回在构建URL时前缀为查看名称的前缀。 
	 * 
	 */
	protected String getPrefix() {
		return this.prefix;
	}

	/**
	 * Set the suffix that gets appended to view names when building a URL.
	 */
	/**
	 * 设置在构建URL时附加到视图名称的后缀。 
	 * 
	 */
	public void setSuffix(@Nullable String suffix) {
		this.suffix = (suffix != null ? suffix : "");
	}

	/**
	 * Return the suffix that gets appended to view names when building a URL.
	 */
	/**
	 * 返回构建URL时添加到视图名称后的后缀。 
	 * 
	 */
	protected String getSuffix() {
		return this.suffix;
	}

	/**
	 * Set the view names (or name patterns) that can be handled by this
	 * {@link ViewResolver}. View names can contain simple wildcards such that
	 * 'my*', '*Report' and '*Repo*' will all match the view name 'myReport'.
	 * @see #canHandle
	 */
	/**
	 * 设置此{@link  ViewResolver}可以处理的视图名称（或名称模式）。 
	 * 视图名称可以包含简单的通配符，例如"my"，"Report"和"Repo"都将与视图名称"myReport"匹配。 
	 *  
	 * @see  #canHandle
	 */
	public void setViewNames(@Nullable String... viewNames) {
		this.viewNames = viewNames;
	}

	/**
	 * Return the view names (or name patterns) that can be handled by this
	 * {@link ViewResolver}.
	 */
	/**
	 * 返回此{@link  ViewResolver}可以处理的视图名称（或名称模式）。 
	 * 
	 */
	@Nullable
	protected String[] getViewNames() {
		return this.viewNames;
	}

	/**
	 * URL based {@link RedirectView} provider which can be used to provide, for example,
	 * redirect views with a custom default status code.
	 */
	/**
	 * 基于URL的{@link  RedirectView}提供程序，可用于提供例如带有自定义默认状态代码的重定向视图。 
	 * 
	 */
	public void setRedirectViewProvider(Function<String, RedirectView> redirectViewProvider) {
		this.redirectViewProvider = redirectViewProvider;
	}

	/**
	 * Set the name of the {@link RequestContext} attribute for all views.
	 * @param requestContextAttribute name of the RequestContext attribute
	 * @see AbstractView#setRequestContextAttribute
	 */
	/**
	 * 为所有视图设置{@link  RequestContext}属性的名称。 
	 *  
	 * @param  requestContextAttribute RequestContext属性的名称
	 * @see  AbstractView＃setRequestContextAttribute
	 */
	public void setRequestContextAttribute(@Nullable String requestContextAttribute) {
		this.requestContextAttribute = requestContextAttribute;
	}

	/**
	 * Return the name of the {@link RequestContext} attribute for all views, if any.
	 */
	/**
	 * 返回所有视图的{@link  RequestContext}属性的名称（如果有）。 
	 * 
	 */
	@Nullable
	protected String getRequestContextAttribute() {
		return this.requestContextAttribute;
	}

	/**
	 * Accept the containing {@code ApplicationContext}, if any.
	 * <p>To be used for the initialization of newly created {@link View} instances,
	 * applying lifecycle callbacks and providing access to the containing environment.
	 * @see #setViewClass
	 * @see #createView
	 * @see #applyLifecycleMethods
	 */
	/**
	 * 接受包含的{@code  ApplicationContext}（如果有）。 
	 *  <p>用于初始化新创建的{@link  View}实例，应用生命周期回调并提供对包含环境的访问。 
	 *  
	 * @see  #setViewClass 
	 * @see  #createView 
	 * @see  #applyLifecycleMethods
	 */
	@Override
	public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Return the containing {@code ApplicationContext}, if any.
	 * @see #setApplicationContext
	 */
	/**
	 * 返回包含的{@code  ApplicationContext}（如果有）。 
	 *  
	 * @see  #setApplicationContext
	 */
	@Nullable
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		if (getViewClass() == null) {
			throw new IllegalArgumentException("Property 'viewClass' is required");
		}
	}


	@Override
	public Mono<View> resolveViewName(String viewName, Locale locale) {
		if (!canHandle(viewName, locale)) {
			return Mono.empty();
		}

		AbstractUrlBasedView urlBasedView;
		if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
			String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
			urlBasedView = this.redirectViewProvider.apply(redirectUrl);
		}
		else {
			urlBasedView = createView(viewName);
		}

		View view = applyLifecycleMethods(viewName, urlBasedView);
		try {
			return (urlBasedView.checkResourceExists(locale) ? Mono.just(view) : Mono.empty());
		}
		catch (Exception ex) {
			return Mono.error(ex);
		}
	}

	/**
	 * Indicates whether or not this {@link ViewResolver} can handle the supplied
	 * view name. If not, an empty result is returned. The default implementation
	 * checks against the configured {@link #setViewNames view names}.
	 * @param viewName the name of the view to retrieve
	 * @param locale the Locale to retrieve the view for
	 * @return whether this resolver applies to the specified view
	 * @see org.springframework.util.PatternMatchUtils#simpleMatch(String, String)
	 */
	/**
	 * 指示此{@link  ViewResolver}是否可以处理提供的视图名称。 
	 * 如果不是，则返回空结果。 
	 * 默认实现会对照已配置的{@link  #setViewNames视图名称}进行检查。 
	 *  
	 * @param  viewName要检索的视图的名称
	 * @param 语言环境为
	 * @return 检索视图的语言环境是否此解析器适用于指定的视图
	 * @see  org.springframework.util.PatternMatchUtils＃simpleMatch（字符串，字符串）
	 */
	protected boolean canHandle(String viewName, Locale locale) {
		String[] viewNames = getViewNames();
		return (viewNames == null || PatternMatchUtils.simpleMatch(viewNames, viewName));
	}

	/**
	 * Creates a new View instance of the specified view class and configures it.
	 * <p>Does <i>not</i> perform any lookup for pre-defined View instances.
	 * <p>Spring lifecycle methods as defined by the bean container do not have to
	 * be called here: They will be automatically applied afterwards, provided
	 * that an {@link #setApplicationContext ApplicationContext} is available.
	 * @param viewName the name of the view to build
	 * @return the View instance
	 * @see #getViewClass()
	 * @see #applyLifecycleMethods
	 */
	/**
	 * 创建指定视图类的新View实例并对其进行配置。 
	 *  <p> <i>不</ i>不对预定义的View实例执行任何查找。 
	 *  <p> bean容器定义的Spring生命周期方法不必在这里调用：如果可以使用{@link  #setApplicationContext ApplicationContext}，它们将在之后自动应用。 
	 *  
	 * @param  viewName要构建的视图的名称
	 * @return  View实例
	 * @see  #getViewClass（）
	 * @see  #applyLifecycleMethods
	 */
	protected AbstractUrlBasedView createView(String viewName) {
		Class<?> viewClass = getViewClass();
		Assert.state(viewClass != null, "No view class");

		AbstractUrlBasedView view = (AbstractUrlBasedView) BeanUtils.instantiateClass(viewClass);
		view.setSupportedMediaTypes(getSupportedMediaTypes());
		view.setDefaultCharset(getDefaultCharset());
		view.setUrl(getPrefix() + viewName + getSuffix());

		String requestContextAttribute = getRequestContextAttribute();
		if (requestContextAttribute != null) {
			view.setRequestContextAttribute(requestContextAttribute);
		}

		return view;
	}

	/**
	 * Apply the containing {@link ApplicationContext}'s lifecycle methods
	 * to the given {@link View} instance, if such a context is available.
	 * @param viewName the name of the view
	 * @param view the freshly created View instance, pre-configured with
	 * {@link AbstractUrlBasedView}'s properties
	 * @return the {@link View} instance to use (either the original one
	 * or a decorated variant)
	 * @see #getApplicationContext()
	 * @see ApplicationContext#getAutowireCapableBeanFactory()
	 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#initializeBean
	 */
	/**
	 * 如果有这样的上下文，请将包含的{@link  ApplicationContext}的生命周期方法应用于给定的{@link  View}实例。 
	 *  
	 * @param  viewName视图的名称
	 * @param 视图新创建的View实例，已预先配置了{@link  AbstractUrlBasedView}的属性
	 * @return  {@link  View}实例以供使用（原始的或装饰的变体）
	 * @see  #getApplicationContext（）
	 * @see  ApplicationContext＃getAutowireCapableBeanFactory（）
	 * @see  org.springframework.beans.factory.config.AutowireCapableBeanFactory＃initializeBean
	 */
	protected View applyLifecycleMethods(String viewName, AbstractUrlBasedView view) {
		ApplicationContext context = getApplicationContext();
		if (context != null) {
			Object initialized = context.getAutowireCapableBeanFactory().initializeBean(view, viewName);
			if (initialized instanceof View) {
				return (View) initialized;
			}
		}
		return view;
	}

}
