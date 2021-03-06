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

package org.springframework.web.servlet.support;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.Conventions;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * Base class for {@link org.springframework.web.WebApplicationInitializer}
 * implementations that register a {@link DispatcherServlet} in the servlet context.
 *
 * <p>Most applications should consider extending the Spring Java config subclass
 * {@link AbstractAnnotationConfigDispatcherServletInitializer}.
 *
 * @author Arjen Poutsma
 * @author Chris Beams
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.2
 */
/**
 * {@link  org.springframework.web.WebApplicationInitializer}实现的基类，该实现在servlet上下文中注册{@link  DispatcherServlet}。 
 *  <p>大多数应用程序应该考虑扩展Spring Java配置子类{@link  AbstractAnnotationConfigDispatcherServletInitializer}。 
 *  @author  Arjen Poutsma @author  Chris Beams @author  Rossen Stoyanchev @author  Juergen Hoeller @author 斯蒂芬·尼科尔@since 3.2
 */
public abstract class AbstractDispatcherServletInitializer extends AbstractContextLoaderInitializer {

	/**
	 * The default servlet name. Can be customized by overriding {@link #getServletName}.
	 */
	/**
	 * 缺省的servlet名称。 
	 * 可以通过覆盖{@link  #getServletName}进行自定义。 
	 * 
	 */
	public static final String DEFAULT_SERVLET_NAME = "dispatcher";


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		registerDispatcherServlet(servletContext);
	}

	/**
	 * Register a {@link DispatcherServlet} against the given servlet context.
	 * <p>This method will create a {@code DispatcherServlet} with the name returned by
	 * {@link #getServletName()}, initializing it with the application context returned
	 * from {@link #createServletApplicationContext()}, and mapping it to the patterns
	 * returned from {@link #getServletMappings()}.
	 * <p>Further customization can be achieved by overriding {@link
	 * #customizeRegistration(ServletRegistration.Dynamic)} or
	 * {@link #createDispatcherServlet(WebApplicationContext)}.
	 * @param servletContext the context to register the servlet against
	 */
	/**
	 * 针对给定的Servlet上下文注册{@link  DispatcherServlet}。 
	 *  <p>此方法将使用{@link  #getServletName（）}返回的名称创建一个{@code  DispatcherServlet}，并使用从{@link  #createServletApplicationContext（）}返回的应用程序上下文对其进行初始化，并将其映射到{@link  #getServletMappings（）}返回的模式。 
	 *  <p>可以通过重写{@link  #customizeRegistration（ServletRegistration.Dynamic）}或{@link  #createDispatcherServlet（WebApplicationContext）}来实现进一步的自定义。 
	 *  
	 * @param  servletContext要针对其注册servlet的上下文
	 */
	protected void registerDispatcherServlet(ServletContext servletContext) {
		String servletName = getServletName();
		Assert.hasLength(servletName, "getServletName() must not return null or empty");

		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext, "createServletApplicationContext() must not return null");

		FrameworkServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
		Assert.notNull(dispatcherServlet, "createDispatcherServlet(WebApplicationContext) must not return null");
		dispatcherServlet.setContextInitializers(getServletApplicationContextInitializers());

		ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
		if (registration == null) {
			throw new IllegalStateException("Failed to register servlet with name '" + servletName + "'. " +
					"Check if there is another servlet registered under the same name.");
		}

		registration.setLoadOnStartup(1);
		registration.addMapping(getServletMappings());
		registration.setAsyncSupported(isAsyncSupported());

		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				registerServletFilter(servletContext, filter);
			}
		}

		customizeRegistration(registration);
	}

	/**
	 * Return the name under which the {@link DispatcherServlet} will be registered.
	 * Defaults to {@link #DEFAULT_SERVLET_NAME}.
	 * @see #registerDispatcherServlet(ServletContext)
	 */
	/**
	 * 返回将注册{@link  DispatcherServlet}的名称。 
	 * 默认为{@link  #DEFAULT_SERVLET_NAME}。 
	 *  
	 * @see  #registerDispatcherServlet（ServletContext）
	 */
	protected String getServletName() {
		return DEFAULT_SERVLET_NAME;
	}

	/**
	 * Create a servlet application context to be provided to the {@code DispatcherServlet}.
	 * <p>The returned context is delegated to Spring's
	 * {@link DispatcherServlet#DispatcherServlet(WebApplicationContext)}. As such,
	 * it typically contains controllers, view resolvers, locale resolvers, and other
	 * web-related beans.
	 * @see #registerDispatcherServlet(ServletContext)
	 */
	/**
	 * 创建要提供给{@code  DispatcherServlet}的servlet应用程序上下文。 
	 *  <p>返回的上下文委托给Spring的{@link  DispatcherServlet＃DispatcherServlet（WebApplicationContext）}。 
	 * 因此，它通常包含控制器，视图解析器，语言环境解析器和其他与Web相关的bean。 
	 *  
	 * @see  #registerDispatcherServlet（ServletContext）
	 */
	protected abstract WebApplicationContext createServletApplicationContext();

	/**
	 * Create a {@link DispatcherServlet} (or other kind of {@link FrameworkServlet}-derived
	 * dispatcher) with the specified {@link WebApplicationContext}.
	 * <p>Note: This allows for any {@link FrameworkServlet} subclass as of 4.2.3.
	 * Previously, it insisted on returning a {@link DispatcherServlet} or subclass thereof.
	 */
	/**
	 * 使用指定的{@link  WebApplicationContext}创建一个{@link  DispatcherServlet}（或其他类型的{@link  FrameworkServlet}派遣的调度程序）。 
	 *  <p>注意：自4.2.3起，这允许所有{@link  FrameworkServlet}子类。 
	 * 以前，它坚持要返回{@link  DispatcherServlet}或其子类。 
	 * 
	 */
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		return new DispatcherServlet(servletAppContext);
	}

	/**
	 * Specify application context initializers to be applied to the servlet-specific
	 * application context that the {@code DispatcherServlet} is being created with.
	 * @since 4.2
	 * @see #createServletApplicationContext()
	 * @see DispatcherServlet#setContextInitializers
	 * @see #getRootApplicationContextInitializers()
	 */
	/**
	 * 指定要用于创建{@code  DispatcherServlet}的servlet特定应用程序上下文的应用程序上下文初始化程序。 
	 *  @since 4.2 
	 * @see  #createServletApplicationContext（）
	 * @see  DispatcherServlet＃setContextInitializers 
	 * @see  #getRootApplicationContextInitializers（）
	 */
	@Nullable
	protected ApplicationContextInitializer<?>[] getServletApplicationContextInitializers() {
		return null;
	}

	/**
	 * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
	 * for example {@code "/"}, {@code "/app"}, etc.
	 * @see #registerDispatcherServlet(ServletContext)
	 */
	/**
	 * 为{@code  DispatcherServlet}指定servlet映射，例如{@code "/"}，{<@code>"/ app"}等。 
	 * 
	 * @see  #registerDispatcherServlet（ServletContext ）
	 */
	protected abstract String[] getServletMappings();

	/**
	 * Specify filters to add and map to the {@code DispatcherServlet}.
	 * @return an array of filters or {@code null}
	 * @see #registerServletFilter(ServletContext, Filter)
	 */
	/**
	 * 指定要添加并映射到{@code  DispatcherServlet}的过滤器。 
	 *  
	 * @return 过滤器数组或{@code  null} 
	 * @see  #registerServletFilter（ServletContext，Filter）
	 */
	@Nullable
	protected Filter[] getServletFilters() {
		return null;
	}

	/**
	 * Add the given filter to the ServletContext and map it to the
	 * {@code DispatcherServlet} as follows:
	 * <ul>
	 * <li>a default filter name is chosen based on its concrete type
	 * <li>the {@code asyncSupported} flag is set depending on the
	 * return value of {@link #isAsyncSupported() asyncSupported}
	 * <li>a filter mapping is created with dispatcher types {@code REQUEST},
	 * {@code FORWARD}, {@code INCLUDE}, and conditionally {@code ASYNC} depending
	 * on the return value of {@link #isAsyncSupported() asyncSupported}
	 * </ul>
	 * <p>If the above defaults are not suitable or insufficient, override this
	 * method and register filters directly with the {@code ServletContext}.
	 * @param servletContext the servlet context to register filters with
	 * @param filter the filter to be registered
	 * @return the filter registration
	 */
	/**
	 * 将给定的过滤器添加到ServletContext并将其映射到{@code  DispatcherServlet}，如下所示：<ul> <li>根据其具体类型选择默认的过滤器名称<li> {<@code> asyncSupported}根据{@link  #isAsyncSupported（）asyncSupported}的返回值设置标志。 
	 * <li>使用调度程序类型{@code  REQUEST}，{<@code> FORWARD}，{<@code> INCLUDE}，有条件地{@code  ASYNC}，具体取决于{@link  #isAsyncSupported（）asyncSupported}的返回值</ ul> <p>如果上述默认设置不合适或不足，请覆盖此设置方法并直接使用{@code  ServletContext}注册过滤器。 
	 *  
	 * @param  servletContext Servlet上下文以使用
	 * @param 注册要注册的过滤器来注册过滤器
	 * @return 过滤器注册
	 */
	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
		String filterName = Conventions.getVariableName(filter);
		Dynamic registration = servletContext.addFilter(filterName, filter);

		if (registration == null) {
			int counter = 0;
			while (registration == null) {
				if (counter == 100) {
					throw new IllegalStateException("Failed to register filter with name '" + filterName + "'. " +
							"Check if there is another filter registered under the same name.");
				}
				registration = servletContext.addFilter(filterName + "#" + counter, filter);
				counter++;
			}
		}

		registration.setAsyncSupported(isAsyncSupported());
		registration.addMappingForServletNames(getDispatcherTypes(), false, getServletName());
		return registration;
	}

	private EnumSet<DispatcherType> getDispatcherTypes() {
		return (isAsyncSupported() ?
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC) :
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE));
	}

	/**
	 * A single place to control the {@code asyncSupported} flag for the
	 * {@code DispatcherServlet} and all filters added via {@link #getServletFilters()}.
	 * <p>The default value is "true".
	 */
	/**
	 * 一个地方来控制{@code  DispatcherServlet}的{@code  asyncSupported}标志以及所有通过{@link  #getServletFilters（）}添加的过滤器。 
	 *  <p>默认值为"true"。 
	 * 
	 */
	protected boolean isAsyncSupported() {
		return true;
	}

	/**
	 * Optionally perform further registration customization once
	 * {@link #registerDispatcherServlet(ServletContext)} has completed.
	 * @param registration the {@code DispatcherServlet} registration to be customized
	 * @see #registerDispatcherServlet(ServletContext)
	 */
	/**
	 * {{@link> #registerDispatcherServlet（ServletContext）}完成后，可以选择执行进一步的注册自定义。 
	 *  
	 * @param 注册要自定义的{@code  DispatcherServlet}注册
	 * @see  #registerDispatcherServlet（ServletContext）
	 */
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	}

}
