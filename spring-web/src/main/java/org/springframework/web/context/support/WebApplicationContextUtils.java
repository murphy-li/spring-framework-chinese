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

package org.springframework.web.context.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource.StubPropertySource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.context.request.WebRequest;

/**
 * Convenience methods for retrieving the root {@link WebApplicationContext} for
 * a given {@link ServletContext}. This is useful for programmatically accessing
 * a Spring application context from within custom web views or MVC actions.
 *
 * <p>Note that there are more convenient ways of accessing the root context for
 * many web frameworks, either part of Spring or available as an external library.
 * This helper class is just the most generic way to access the root context.
 *
 * @author Juergen Hoeller
 * @see org.springframework.web.context.ContextLoader
 * @see org.springframework.web.servlet.FrameworkServlet
 * @see org.springframework.web.servlet.DispatcherServlet
 * @see org.springframework.web.jsf.FacesContextUtils
 * @see org.springframework.web.jsf.el.SpringBeanFacesELResolver
 */
/**
 * 检索给定{@link  ServletContext}的根{@link  WebApplicationContext}的便捷方法。 
 * 这对于在自定义Web视图或MVC操作中以编程方式访问Spring应用程序上下文很有用。 
 *  <p>请注意，对于许多Web框架（Spring的一部分或作为外部库可用），有更方便的访问根上下文的方法。 
 * 此类是访问根上下文的最通用方法。 
 *  @author  Juergen Hoeller 
 * @see  org.springframework.web.context.ContextLoader 
 * @see  org.springframework.web.servlet.FrameworkServlet 
 * @see  org.springframework.web.servlet.DispatcherServlet 
 * @see  org .springframework.web.jsf.FacesContextUtils 
 * @see  org.springframework.web.jsf.el.SpringBeanFacesELResolver
 */
public abstract class WebApplicationContextUtils {

	private static final boolean jsfPresent =
			ClassUtils.isPresent("javax.faces.context.FacesContext", RequestContextHolder.class.getClassLoader());


	/**
	 * Find the root {@code WebApplicationContext} for this web app, typically
	 * loaded via {@link org.springframework.web.context.ContextLoaderListener}.
	 * <p>Will rethrow an exception that happened on root context startup,
	 * to differentiate between a failed context startup and no context at all.
	 * @param sc the ServletContext to find the web application context for
	 * @return the root WebApplicationContext for this web app
	 * @throws IllegalStateException if the root WebApplicationContext could not be found
	 * @see org.springframework.web.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
	 */
	/**
	 * 找到此Web应用程序的根{@code  WebApplicationContext}，通常通过{@link  org.springframework.web.context.ContextLoaderListener}加载。 
	 *  <p>将重新抛出根上下文启动时发生的异常，以区分失败的上下文启动和根本没有上下文。 
	 *  
	 * @param 通过ServletContext查找此Web应用程序的根WebApplicationContext 
	 * @return 的Web应用程序上下文
	 * @throws  IllegalStateException，如果找不到根WebApplicationContext 
	 * @see  org.springframework.web.context。 
	 *  WebApplicationContext＃ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
	 */
	public static WebApplicationContext getRequiredWebApplicationContext(ServletContext sc) throws IllegalStateException {
		WebApplicationContext wac = getWebApplicationContext(sc);
		if (wac == null) {
			throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
		}
		return wac;
	}

	/**
	 * Find the root {@code WebApplicationContext} for this web app, typically
	 * loaded via {@link org.springframework.web.context.ContextLoaderListener}.
	 * <p>Will rethrow an exception that happened on root context startup,
	 * to differentiate between a failed context startup and no context at all.
	 * @param sc the ServletContext to find the web application context for
	 * @return the root WebApplicationContext for this web app, or {@code null} if none
	 * @see org.springframework.web.context.WebApplicationContext#ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
	 */
	/**
	 * 找到此Web应用程序的根{@code  WebApplicationContext}，通常通过{@link  org.springframework.web.context.ContextLoaderListener}加载。 
	 *  <p>将重新抛出根上下文启动时发生的异常，以区分失败的上下文启动和根本没有上下文。 
	 *  
	 * @param 通过ServletContext查找此Web应用程序的根WebApplicationContext的Web应用程序上下文，如果没有，则为{@code  null}（如果没有）
	 * @see  org.springframework.web.context.WebApplicationContext＃ ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
	 */
	@Nullable
	public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
		return getWebApplicationContext(sc, WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	/**
	 * Find a custom {@code WebApplicationContext} for this web app.
	 * @param sc the ServletContext to find the web application context for
	 * @param attrName the name of the ServletContext attribute to look for
	 * @return the desired WebApplicationContext for this web app, or {@code null} if none
	 */
	/**
	 * 查找此Web应用程序的自定义{@code  WebApplicationContext}。 
	 *  
	 * @param  sc ServletContext以查找
	 * @param 的Web应用程序上下文attrName ServletContext属性的名称以查找
	 * @return 该Web应用程序所需的WebApplicationContext，如果没有，则为{@code  null}
	 */
	@Nullable
	public static WebApplicationContext getWebApplicationContext(ServletContext sc, String attrName) {
		Assert.notNull(sc, "ServletContext must not be null");
		Object attr = sc.getAttribute(attrName);
		if (attr == null) {
			return null;
		}
		if (attr instanceof RuntimeException) {
			throw (RuntimeException) attr;
		}
		if (attr instanceof Error) {
			throw (Error) attr;
		}
		if (attr instanceof Exception) {
			throw new IllegalStateException((Exception) attr);
		}
		if (!(attr instanceof WebApplicationContext)) {
			throw new IllegalStateException("Context attribute is not of type WebApplicationContext: " + attr);
		}
		return (WebApplicationContext) attr;
	}

	/**
	 * Find a unique {@code WebApplicationContext} for this web app: either the
	 * root web app context (preferred) or a unique {@code WebApplicationContext}
	 * among the registered {@code ServletContext} attributes (typically coming
	 * from a single {@code DispatcherServlet} in the current web application).
	 * <p>Note that {@code DispatcherServlet}'s exposure of its context can be
	 * controlled through its {@code publishContext} property, which is {@code true}
	 * by default but can be selectively switched to only publish a single context
	 * despite multiple {@code DispatcherServlet} registrations in the web app.
	 * @param sc the ServletContext to find the web application context for
	 * @return the desired WebApplicationContext for this web app, or {@code null} if none
	 * @since 4.2
	 * @see #getWebApplicationContext(ServletContext)
	 * @see ServletContext#getAttributeNames()
	 */
	/**
	 * 在此Web应用程序中找到一个唯一的{@code  WebApplicationContext}：在注册的{@code  ServletContext}属性（通常来自一个Web应用程序）中，最好是根Web应用程序上下文（首选）或唯一的{@code  WebApplicationContext}。 
	 * 当前Web应用程序中的单个{@code  DispatcherServlet}）。 
	 *  <p>请注意，{<@code> DispatcherServlet}的上下文公开可以通过其{@code  publishContext}属性进行控制，默认情况下该属性为{@code  true}，但可以有选择地切换为尽管在Web应用程序中进行了多个{@code  DispatcherServlet}注册，仍发布了单个上下文。 
	 *  
	 * @param  sc ServletContext来找到该应用程序所需的WebApplicationContext的Web应用程序上下文，以
	 * @return 或{@code  null}（自4.2起）@
	 * @see> #getWebApplicationContext（ServletContext）<@请参阅> ServletContext＃getAttributeNames（）
	 */
	@Nullable
	public static WebApplicationContext findWebApplicationContext(ServletContext sc) {
		WebApplicationContext wac = getWebApplicationContext(sc);
		if (wac == null) {
			Enumeration<String> attrNames = sc.getAttributeNames();
			while (attrNames.hasMoreElements()) {
				String attrName = attrNames.nextElement();
				Object attrValue = sc.getAttribute(attrName);
				if (attrValue instanceof WebApplicationContext) {
					if (wac != null) {
						throw new IllegalStateException("No unique WebApplicationContext found: more than one " +
								"DispatcherServlet registered with publishContext=true?");
					}
					wac = (WebApplicationContext) attrValue;
				}
			}
		}
		return wac;
	}


	/**
	 * Register web-specific scopes ("request", "session", "globalSession")
	 * with the given BeanFactory, as used by the WebApplicationContext.
	 * @param beanFactory the BeanFactory to configure
	 */
	/**
	 * 根据WebApplicationContext的使用，使用给定的BeanFactory注册特定于Web的范围（"请求"，"会话"，"globalSession"）。 
	 *  
	 * @param  beanFactory要配置的BeanFactory
	 */
	public static void registerWebApplicationScopes(ConfigurableListableBeanFactory beanFactory) {
		registerWebApplicationScopes(beanFactory, null);
	}

	/**
	 * Register web-specific scopes ("request", "session", "globalSession", "application")
	 * with the given BeanFactory, as used by the WebApplicationContext.
	 * @param beanFactory the BeanFactory to configure
	 * @param sc the ServletContext that we're running within
	 */
	/**
	 * 使用WebApplicationContext使用的给定BeanFactory注册特定于Web的范围（"请求"，"会话"，"globalSession"，"应用程序"）。 
	 *  
	 * @param  beanFactory BeanFactory配置
	 * @param  sc我们正在其中运行的ServletContext
	 */
	public static void registerWebApplicationScopes(ConfigurableListableBeanFactory beanFactory,
			@Nullable ServletContext sc) {

		beanFactory.registerScope(WebApplicationContext.SCOPE_REQUEST, new RequestScope());
		beanFactory.registerScope(WebApplicationContext.SCOPE_SESSION, new SessionScope());
		if (sc != null) {
			ServletContextScope appScope = new ServletContextScope(sc);
			beanFactory.registerScope(WebApplicationContext.SCOPE_APPLICATION, appScope);
			// Register as ServletContext attribute, for ContextCleanupListener to detect it.
			sc.setAttribute(ServletContextScope.class.getName(), appScope);
		}

		beanFactory.registerResolvableDependency(ServletRequest.class, new RequestObjectFactory());
		beanFactory.registerResolvableDependency(ServletResponse.class, new ResponseObjectFactory());
		beanFactory.registerResolvableDependency(HttpSession.class, new SessionObjectFactory());
		beanFactory.registerResolvableDependency(WebRequest.class, new WebRequestObjectFactory());
		if (jsfPresent) {
			FacesDependencyRegistrar.registerFacesDependencies(beanFactory);
		}
	}

	/**
	 * Register web-specific environment beans ("contextParameters", "contextAttributes")
	 * with the given BeanFactory, as used by the WebApplicationContext.
	 * @param bf the BeanFactory to configure
	 * @param sc the ServletContext that we're running within
	 */
	/**
	 * 使用WebApplicationContext所使用的给定BeanFactory注册特定于Web的环境Bean（"contextParameters"，"contextAttributes"）。 
	 *  
	 * @param 通过BeanFactory来配置
	 * @param  sc我们正在其中运行的ServletContext
	 */
	public static void registerEnvironmentBeans(ConfigurableListableBeanFactory bf, @Nullable ServletContext sc) {
		registerEnvironmentBeans(bf, sc, null);
	}

	/**
	 * Register web-specific environment beans ("contextParameters", "contextAttributes")
	 * with the given BeanFactory, as used by the WebApplicationContext.
	 * @param bf the BeanFactory to configure
	 * @param servletContext the ServletContext that we're running within
	 * @param servletConfig the ServletConfig
	 */
	/**
	 * 使用WebApplicationContext所使用的给定BeanFactory注册特定于Web的环境Bean（"contextParameters"，"contextAttributes"）。 
	 *  
	 * @param 通过BeanFactory来配置
	 * @param  servletContext我们正在
	 * @param 内运行的ServletContext servletConfig ServletConfig
	 */
	public static void registerEnvironmentBeans(ConfigurableListableBeanFactory bf,
			@Nullable ServletContext servletContext, @Nullable ServletConfig servletConfig) {

		if (servletContext != null && !bf.containsBean(WebApplicationContext.SERVLET_CONTEXT_BEAN_NAME)) {
			bf.registerSingleton(WebApplicationContext.SERVLET_CONTEXT_BEAN_NAME, servletContext);
		}

		if (servletConfig != null && !bf.containsBean(ConfigurableWebApplicationContext.SERVLET_CONFIG_BEAN_NAME)) {
			bf.registerSingleton(ConfigurableWebApplicationContext.SERVLET_CONFIG_BEAN_NAME, servletConfig);
		}

		if (!bf.containsBean(WebApplicationContext.CONTEXT_PARAMETERS_BEAN_NAME)) {
			Map<String, String> parameterMap = new HashMap<>();
			if (servletContext != null) {
				Enumeration<?> paramNameEnum = servletContext.getInitParameterNames();
				while (paramNameEnum.hasMoreElements()) {
					String paramName = (String) paramNameEnum.nextElement();
					parameterMap.put(paramName, servletContext.getInitParameter(paramName));
				}
			}
			if (servletConfig != null) {
				Enumeration<?> paramNameEnum = servletConfig.getInitParameterNames();
				while (paramNameEnum.hasMoreElements()) {
					String paramName = (String) paramNameEnum.nextElement();
					parameterMap.put(paramName, servletConfig.getInitParameter(paramName));
				}
			}
			bf.registerSingleton(WebApplicationContext.CONTEXT_PARAMETERS_BEAN_NAME,
					Collections.unmodifiableMap(parameterMap));
		}

		if (!bf.containsBean(WebApplicationContext.CONTEXT_ATTRIBUTES_BEAN_NAME)) {
			Map<String, Object> attributeMap = new HashMap<>();
			if (servletContext != null) {
				Enumeration<?> attrNameEnum = servletContext.getAttributeNames();
				while (attrNameEnum.hasMoreElements()) {
					String attrName = (String) attrNameEnum.nextElement();
					attributeMap.put(attrName, servletContext.getAttribute(attrName));
				}
			}
			bf.registerSingleton(WebApplicationContext.CONTEXT_ATTRIBUTES_BEAN_NAME,
					Collections.unmodifiableMap(attributeMap));
		}
	}

	/**
	 * Convenient variant of {@link #initServletPropertySources(MutablePropertySources,
	 * ServletContext, ServletConfig)} that always provides {@code null} for the
	 * {@link ServletConfig} parameter.
	 * @see #initServletPropertySources(MutablePropertySources, ServletContext, ServletConfig)
	 */
	/**
	 * {@link  #initServletPropertySources（MutablePropertySources，ServletContext，ServletConfig）}的便捷变体，它始终为{@link  ServletConfig}参数提供{@code  null}。 
	 *  
	 * @see  #initServletPropertySources（MutablePropertySources，ServletContext，ServletConfig）
	 */
	public static void initServletPropertySources(MutablePropertySources propertySources, ServletContext servletContext) {
		initServletPropertySources(propertySources, servletContext, null);
	}

	/**
	 * Replace {@code Servlet}-based {@link StubPropertySource stub property sources} with
	 * actual instances populated with the given {@code servletContext} and
	 * {@code servletConfig} objects.
	 * <p>This method is idempotent with respect to the fact it may be called any number
	 * of times but will perform replacement of stub property sources with their
	 * corresponding actual property sources once and only once.
	 * @param sources the {@link MutablePropertySources} to initialize (must not
	 * be {@code null})
	 * @param servletContext the current {@link ServletContext} (ignored if {@code null}
	 * or if the {@link StandardServletEnvironment#SERVLET_CONTEXT_PROPERTY_SOURCE_NAME
	 * servlet context property source} has already been initialized)
	 * @param servletConfig the current {@link ServletConfig} (ignored if {@code null}
	 * or if the {@link StandardServletEnvironment#SERVLET_CONFIG_PROPERTY_SOURCE_NAME
	 * servlet config property source} has already been initialized)
	 * @see org.springframework.core.env.PropertySource.StubPropertySource
	 * @see org.springframework.core.env.ConfigurableEnvironment#getPropertySources()
	 */
	/**
	 * 将基于{@code  Servlet}的{@link  StubPropertySource stub属性源}替换为使用给定的{@code  servletContext}和{@code  servletConfig}对象填充的实际实例。 
	 *  <p>该方法可以调用任意多次，但它只能执行一次，而只能用其相应的实际属性源替换存根属性源。 
	 *  
	 * @param 源{{@@link> MutablePropertySources}进行初始化（不得为{@code  null}）
	 * @param  servletContext当前的{@link  ServletContext}（如果{@code  null }，或者{{@link> StandardServletEnvironment＃SERVLET_CONTEXT_PROPERTY_SOURCE_NAME servlet上下文属性源}已被初始化）
	 * @param  servletConfig当前的{@link  ServletConfig}（如果{@code  null}或{ @link  StandardServletEnvironment＃SERVLET_CONFIG_PROPERTY_SOURCE_NAME servlet配置属性源}已被初始化）。 
	 * @
	 * @see> org.springframework.core.env.PropertySource.StubPropertySource
	 */
	public static void initServletPropertySources(MutablePropertySources sources,
			@Nullable ServletContext servletContext, @Nullable ServletConfig servletConfig) {

		Assert.notNull(sources, "'propertySources' must not be null");
		String name = StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME;
		if (servletContext != null && sources.contains(name) && sources.get(name) instanceof StubPropertySource) {
			sources.replace(name, new ServletContextPropertySource(name, servletContext));
		}
		name = StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME;
		if (servletConfig != null && sources.contains(name) && sources.get(name) instanceof StubPropertySource) {
			sources.replace(name, new ServletConfigPropertySource(name, servletConfig));
		}
	}

	/**
	 * Return the current RequestAttributes instance as ServletRequestAttributes.
	 * @see RequestContextHolder#currentRequestAttributes()
	 */
	/**
	 * 返回当前的RequestAttributes实例作为ServletRequestAttributes。 
	 *  
	 * @see  RequestContextHolder＃currentRequestAttributes（）
	 */
	private static ServletRequestAttributes currentRequestAttributes() {
		RequestAttributes requestAttr = RequestContextHolder.currentRequestAttributes();
		if (!(requestAttr instanceof ServletRequestAttributes)) {
			throw new IllegalStateException("Current request is not a servlet request");
		}
		return (ServletRequestAttributes) requestAttr;
	}


	/**
	 * Factory that exposes the current request object on demand.
	 */
	/**
	 * 根据需要公开当前请求对象的工厂。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class RequestObjectFactory implements ObjectFactory<ServletRequest>, Serializable {

		@Override
		public ServletRequest getObject() {
			return currentRequestAttributes().getRequest();
		}

		@Override
		public String toString() {
			return "Current HttpServletRequest";
		}
	}


	/**
	 * Factory that exposes the current response object on demand.
	 */
	/**
	 * 根据需要公开当前响应对象的工厂。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class ResponseObjectFactory implements ObjectFactory<ServletResponse>, Serializable {

		@Override
		public ServletResponse getObject() {
			ServletResponse response = currentRequestAttributes().getResponse();
			if (response == null) {
				throw new IllegalStateException("Current servlet response not available - " +
						"consider using RequestContextFilter instead of RequestContextListener");
			}
			return response;
		}

		@Override
		public String toString() {
			return "Current HttpServletResponse";
		}
	}


	/**
	 * Factory that exposes the current session object on demand.
	 */
	/**
	 * 根据需要公开当前会话对象的工厂。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class SessionObjectFactory implements ObjectFactory<HttpSession>, Serializable {

		@Override
		public HttpSession getObject() {
			return currentRequestAttributes().getRequest().getSession();
		}

		@Override
		public String toString() {
			return "Current HttpSession";
		}
	}


	/**
	 * Factory that exposes the current WebRequest object on demand.
	 */
	/**
	 * 根据需要公开当前WebRequest对象的工厂。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class WebRequestObjectFactory implements ObjectFactory<WebRequest>, Serializable {

		@Override
		public WebRequest getObject() {
			ServletRequestAttributes requestAttr = currentRequestAttributes();
			return new ServletWebRequest(requestAttr.getRequest(), requestAttr.getResponse());
		}

		@Override
		public String toString() {
			return "Current ServletWebRequest";
		}
	}


	/**
	 * Inner class to avoid hard-coded JSF dependency.
 	 */
	/**
	 * 内部类避免硬编码的JSF依赖关系。 
	 * 
	 */
	private static class FacesDependencyRegistrar {

		public static void registerFacesDependencies(ConfigurableListableBeanFactory beanFactory) {
			beanFactory.registerResolvableDependency(FacesContext.class, new ObjectFactory<FacesContext>() {
				@Override
				public FacesContext getObject() {
					return FacesContext.getCurrentInstance();
				}
				@Override
				public String toString() {
					return "Current JSF FacesContext";
				}
			});
			beanFactory.registerResolvableDependency(ExternalContext.class, new ObjectFactory<ExternalContext>() {
				@Override
				public ExternalContext getObject() {
					return FacesContext.getCurrentInstance().getExternalContext();
				}
				@Override
				public String toString() {
					return "Current JSF ExternalContext";
				}
			});
		}
	}

}
