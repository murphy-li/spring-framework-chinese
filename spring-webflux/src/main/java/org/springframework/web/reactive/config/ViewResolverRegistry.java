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

package org.springframework.web.reactive.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import org.springframework.web.reactive.result.view.View;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.reactive.result.view.script.ScriptTemplateConfigurer;
import org.springframework.web.reactive.result.view.script.ScriptTemplateViewResolver;

/**
 * Assist with the configuration of a chain of {@link ViewResolver}'s supporting
 * different template mechanisms.
 *
 * <p>In addition, you can also configure {@link #defaultViews(View...)
 * defaultViews} for rendering according to the requested content type, e.g.
 * JSON, XML, etc.
 *
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @since 5.0
 */
/**
 * 协助配置{@link  ViewResolver}的链，以支持不同的模板机制。 
 *  <p>此外，您还可以配置{@link  #defaultViews（View ...）defaultViews}以根据请求的内容类型进行渲染，例如JSON，XML等。 
 * @author  Rossen Stoyanchev @author  Sebastien Deleuze @since 5.0
 */
public class ViewResolverRegistry {

	@Nullable
	private final ApplicationContext applicationContext;

	private final List<ViewResolver> viewResolvers = new ArrayList<>(4);

	private final List<View> defaultViews = new ArrayList<>(4);

	@Nullable
	private Integer order;


	public ViewResolverRegistry(@Nullable ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}


	/**
	 * Register a {@code FreeMarkerViewResolver} with a ".ftl" suffix.
	 * <p><strong>Note</strong> that you must also configure FreeMarker by
	 * adding a {@link FreeMarkerConfigurer} bean.
	 */
	/**
	 * 注册带有后缀".ftl"的{@code  FreeMarkerViewResolver}。 
	 *  <p> <strong>注意</ strong>，您还必须通过添加{@link  FreeMarkerConfigurer} bean来配置FreeMarker。 
	 * 
	 */
	public UrlBasedViewResolverRegistration freeMarker() {
		if (!checkBeanOfType(FreeMarkerConfigurer.class)) {
			throw new BeanInitializationException("In addition to a FreeMarker view resolver " +
					"there must also be a single FreeMarkerConfig bean in this web application context " +
					"(or its parent): FreeMarkerConfigurer is the usual implementation. " +
					"This bean may be given any name.");
		}
		FreeMarkerRegistration registration = new FreeMarkerRegistration();
		UrlBasedViewResolver resolver = registration.getViewResolver();
		if (this.applicationContext != null) {
			resolver.setApplicationContext(this.applicationContext);
		}
		this.viewResolvers.add(resolver);
		return registration;
	}

	/**
	 * Register a script template view resolver with an empty default view name prefix and suffix.
	 * <p><strong>Note</strong> that you must also configure script templating by
	 * adding a {@link ScriptTemplateConfigurer} bean.
	 * @since 5.0.4
	 */
	/**
	 * 使用空的默认视图名称前缀和后缀注册脚本模板视图解析器。 
	 *  <p> <strong>注意</ strong>，您还必须通过添加{@link  ScriptTemplateConfigurer} bean来配置脚本模板。 
	 *  @5.0.4起
	 */
	public UrlBasedViewResolverRegistration scriptTemplate() {
		if (!checkBeanOfType(ScriptTemplateConfigurer.class)) {
			throw new BeanInitializationException("In addition to a script template view resolver " +
					"there must also be a single ScriptTemplateConfig bean in this web application context " +
					"(or its parent): ScriptTemplateConfigurer is the usual implementation. " +
					"This bean may be given any name.");
		}
		ScriptRegistration registration = new ScriptRegistration();
		UrlBasedViewResolver resolver = registration.getViewResolver();
		if (this.applicationContext != null) {
			resolver.setApplicationContext(this.applicationContext);
		}
		this.viewResolvers.add(resolver);
		return registration;
	}

	/**
	 * Register a {@link ViewResolver} bean instance. This may be useful to
	 * configure a 3rd party resolver implementation or as an alternative to
	 * other registration methods in this class when they don't expose some
	 * more advanced property that needs to be set.
	 */
	/**
	 * 注册一个{@link  ViewResolver} bean实例。 
	 * 这对于配置第三方解析器实现或在此类的其他注册方法不公开需要设置的更高级属性时可能是有用的。 
	 * 
	 */
	public void viewResolver(ViewResolver viewResolver) {
		this.viewResolvers.add(viewResolver);
	}

	/**
	 * Set default views associated with any view name and selected based on the
	 * best match for the requested content type.
	 * <p>Use {@link HttpMessageWriterView
	 * HttpMessageWriterView} to adapt and use any existing
	 * {@code HttpMessageWriter} (e.g. JSON, XML) as a {@code View}.
	 */
	/**
	 * 设置与任何视图名称关联的默认视图，并根据所请求内容类型的最佳匹配进行选择。 
	 *  <p>使用{@link  HttpMessageWriterView HttpMessageWriterView}来适应和使用任何现有的{@code  HttpMessageWriter}（例如JSON，XML）作为{@code  View}。 
	 * 
	 */
	public void defaultViews(View... defaultViews) {
		this.defaultViews.addAll(Arrays.asList(defaultViews));
	}

	/**
	 * Whether any view resolvers have been registered.
	 */
	/**
	 * 是否已注册任何视图解析器。 
	 * 
	 */
	public boolean hasRegistrations() {
		return (!this.viewResolvers.isEmpty());
	}

	/**
	 * Set the order for the
	 * {@link org.springframework.web.reactive.result.view.ViewResolutionResultHandler
	 * ViewResolutionResultHandler}.
	 * <p>By default this property is not set, which means the result handler is
	 * ordered at {@link Ordered#LOWEST_PRECEDENCE}.
	 */
	/**
	 * 设置{@link  org.springframework.web.reactive.result.view.ViewResolutionResultHandler ViewResolutionResultHandler}的顺序。 
	 *  <p>默认情况下未设置此属性，这意味着结果处理程序在{@link  Ordered＃LOWEST_PRECEDENCE}处进行排序。 
	 * 
	 */
	public void order(int order) {
		this.order = order;
	}


	private boolean checkBeanOfType(Class<?> beanType) {
		return (this.applicationContext == null ||
				!ObjectUtils.isEmpty(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
						this.applicationContext, beanType, false, false)));
	}

	protected int getOrder() {
		return (this.order != null ? this.order : Ordered.LOWEST_PRECEDENCE);
	}

	protected List<ViewResolver> getViewResolvers() {
		return this.viewResolvers;
	}

	protected List<View> getDefaultViews() {
		return this.defaultViews;
	}


	private static class FreeMarkerRegistration extends UrlBasedViewResolverRegistration {

		public FreeMarkerRegistration() {
			super(new FreeMarkerViewResolver());
			getViewResolver().setSuffix(".ftl");
		}
	}

	private static class ScriptRegistration extends UrlBasedViewResolverRegistration {

		public ScriptRegistration() {
			super(new ScriptTemplateViewResolver());
			getViewResolver();
		}
	}

}
