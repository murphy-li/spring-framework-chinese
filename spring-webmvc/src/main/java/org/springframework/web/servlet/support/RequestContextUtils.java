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

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.ui.context.Theme;
import org.springframework.ui.context.ThemeSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for easy access to request-specific state which has been
 * set by the {@link org.springframework.web.servlet.DispatcherServlet}.
 *
 * <p>Supports lookup of current WebApplicationContext, LocaleResolver,
 * Locale, ThemeResolver, Theme, and MultipartResolver.
 *
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 03.03.2003
 * @see RequestContext
 * @see org.springframework.web.servlet.DispatcherServlet
 */
/**
 * 由{@link  org.springframework.web.servlet.DispatcherServlet}设置的实用程序类，用于轻松访问特定于请求的状态。 
 *  <p>支持查找当前的WebApplicationContext，LocaleResolver，Locale，ThemeResolver，Theme和MultipartResolver。 
 *  @author  Juergen Hoeller @author  Rossen Stoyanchev @2003年3月3日
 * @see  RequestContext 
 * @see  org.springframework.web.servlet.DispatcherServlet
 */
public abstract class RequestContextUtils {

	/**
	 * The name of the bean to use to look up in an implementation of
	 * {@link RequestDataValueProcessor} has been configured.
	 * @since 4.2.1
	 */
	/**
	 * 已配置用于在{@link  RequestDataValueProcessor}的实现中查找的bean的名称。 
	 *  @4.2.1起
	 */
	public static final String REQUEST_DATA_VALUE_PROCESSOR_BEAN_NAME = "requestDataValueProcessor";


	/**
	 * Look for the WebApplicationContext associated with the DispatcherServlet
	 * that has initiated request processing, and for the global context if none
	 * was found associated with the current request. The global context will
	 * be found via the ServletContext or via ContextLoader's current context.
	 * <p>NOTE: This variant remains compatible with Servlet 2.5, explicitly
	 * checking a given ServletContext instead of deriving it from the request.
	 * @param request current HTTP request
	 * @param servletContext current servlet context
	 * @return the request-specific WebApplicationContext, or the global one
	 * if no request-specific context has been found, or {@code null} if none
	 * @since 4.2.1
	 * @see DispatcherServlet#WEB_APPLICATION_CONTEXT_ATTRIBUTE
	 * @see WebApplicationContextUtils#getWebApplicationContext(ServletContext)
	 * @see ContextLoader#getCurrentWebApplicationContext()
	 */
	/**
	 * 查找与已启动请求处理的DispatcherServlet关联的WebApplicationContext，以及是否找不到与当前请求关联的全局上下文。 
	 * 全局上下文将通过ServletContext或ContextLoader的当前上下文找到。 
	 *  <p>注意：此变体仍与Servlet 2.5兼容，显式检查给定的ServletContext而不是从请求中派生它。 
	 *  
	 * @param 请求当前的HTTP请求
	 * @param  servletContext当前的servlet上下文<@r​​eturn>特定于请求的WebApplicationContext，如果未找到特定于请求的上下文，则为全局变量； 
	 * 如果没有，则为{@code  null} @since 4.2.1 
	 * @see  DispatcherServlet＃WEB_APPLICATION_CONTEXT_ATTRIBUTE 
	 * @see  WebApplicationContextUtils＃getWebApplicationContext（ServletContext）
	 * @see  ContextLoader＃getCurrentWebApplicationContext（）
	 */
	@Nullable
	public static WebApplicationContext findWebApplicationContext(
			HttpServletRequest request, @Nullable ServletContext servletContext) {

		WebApplicationContext webApplicationContext = (WebApplicationContext) request.getAttribute(
				DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		if (webApplicationContext == null) {
			if (servletContext != null) {
				webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}
			if (webApplicationContext == null) {
				webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			}
		}
		return webApplicationContext;
	}

	/**
	 * Look for the WebApplicationContext associated with the DispatcherServlet
	 * that has initiated request processing, and for the global context if none
	 * was found associated with the current request. The global context will
	 * be found via the ServletContext or via ContextLoader's current context.
	 * <p>NOTE: This variant requires Servlet 3.0+ and is generally recommended
	 * for forward-looking custom user code.
	 * @param request current HTTP request
	 * @return the request-specific WebApplicationContext, or the global one
	 * if no request-specific context has been found, or {@code null} if none
	 * @since 4.2.1
	 * @see #findWebApplicationContext(HttpServletRequest, ServletContext)
	 * @see ServletRequest#getServletContext()
	 * @see ContextLoader#getCurrentWebApplicationContext()
	 */
	/**
	 * 查找与已启动请求处理的DispatcherServlet关联的WebApplicationContext，以及是否找不到与当前请求关联的全局上下文。 
	 * 全局上下文将通过ServletContext或ContextLoader的当前上下文找到。 
	 *  <p>注意：此变体需要Servlet 3.0+，通常推荐用于前瞻性自定义用户代码。 
	 *  
	 * @param 请求当前的HTTP请求
	 * @return 特定于请求的WebApplicationContext； 
	 * 如果未找到特定于请求的上下文，则为全局请求； 
	 * 如果没有，则为{@code  null} @4.2.1起> #findWebApplicationContext（HttpServletRequest，ServletContext）
	 * @see  ServletRequest＃getServletContext（）
	 * @see  ContextLoader＃getCurrentWebApplicationContext（）
	 */
	@Nullable
	public static WebApplicationContext findWebApplicationContext(HttpServletRequest request) {
		return findWebApplicationContext(request, request.getServletContext());
	}

	/**
	 * Return the LocaleResolver that has been bound to the request by the
	 * DispatcherServlet.
	 * @param request current HTTP request
	 * @return the current LocaleResolver, or {@code null} if not found
	 */
	/**
	 * 返回由DispatcherServlet绑定到请求的LocaleResolver。 
	 *  
	 * @param 请求当前HTTP请求
	 * @return 当前LocaleResolver，如果找不到，则返回{@code  null}
	 */
	@Nullable
	public static LocaleResolver getLocaleResolver(HttpServletRequest request) {
		return (LocaleResolver) request.getAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE);
	}

	/**
	 * Retrieve the current locale from the given request, using the
	 * LocaleResolver bound to the request by the DispatcherServlet
	 * (if available), falling back to the request's accept-header Locale.
	 * <p>This method serves as a straightforward alternative to the standard
	 * Servlet {@link javax.servlet.http.HttpServletRequest#getLocale()} method,
	 * falling back to the latter if no more specific locale has been found.
	 * <p>Consider using {@link org.springframework.context.i18n.LocaleContextHolder#getLocale()}
	 * which will normally be populated with the same Locale.
	 * @param request current HTTP request
	 * @return the current locale for the given request, either from the
	 * LocaleResolver or from the plain request itself
	 * @see #getLocaleResolver
	 * @see org.springframework.context.i18n.LocaleContextHolder#getLocale()
	 */
	/**
	 * 使用由DispatcherServlet绑定到请求的LocaleResolver从给定请求中检索当前语言环境（如果可用），并回退到请求的接受标头语言环境。 
	 *  <p>此方法可以直接替代标准Servlet {@link  javax.servlet.http.HttpServletRequest＃getLocale（）}方法，如果找不到更多特定的语言环境，则退回到后者。 
	 *  <p>考虑使用{@link  org.springframework.context.i18n.LocaleContextHolder＃getLocale（）}，通常会使用相同的Locale进行填充。 
	 *  
	 * @param 请求当前HTTP请求
	 * @return 给定请求的当前语言环境，可以是LocaleResolver或普通请求本身的
	 * @see  #getLocaleResolver 
	 * @see  org.springframework.context.i18n.LocaleContextHolder＃ getLocale（）
	 */
	public static Locale getLocale(HttpServletRequest request) {
		LocaleResolver localeResolver = getLocaleResolver(request);
		return (localeResolver != null ? localeResolver.resolveLocale(request) : request.getLocale());
	}

	/**
	 * Retrieve the current time zone from the given request, using the
	 * TimeZoneAwareLocaleResolver bound to the request by the DispatcherServlet
	 * (if available), falling back to the system's default time zone.
	 * <p>Note: This method returns {@code null} if no specific time zone can be
	 * resolved for the given request. This is in contrast to {@link #getLocale}
	 * where there is always the request's accept-header locale to fall back to.
	 * <p>Consider using {@link org.springframework.context.i18n.LocaleContextHolder#getTimeZone()}
	 * which will normally be populated with the same TimeZone: That method only
	 * differs in terms of its fallback to the system time zone if the LocaleResolver
	 * hasn't provided a specific time zone (instead of this method's {@code null}).
	 * @param request current HTTP request
	 * @return the current time zone for the given request, either from the
	 * TimeZoneAwareLocaleResolver or {@code null} if none associated
	 * @see #getLocaleResolver
	 * @see org.springframework.context.i18n.LocaleContextHolder#getTimeZone()
	 */
	/**
	 * 使用由DispatcherServlet绑定到请求的TimeZoneAwareLocaleResolver从给定请求中检索当前时区（如果可用），并回退到系统的默认时区。 
	 *  <p>注意：如果无法为给定请求解析特定的时区，则此方法返回{@code  null}。 
	 * 这与{@link  #getLocale}形成对比，在{@link  #getLocale}中总是存在请求的接受标头语言环境。 
	 *  <p>请考虑使用{@link  org.springframework.context.i18n.LocaleContextHolder＃getTimeZone（）}（通常会使用相同的TimeZone进行填充）：该方法仅在回退到系统时区方面有所不同LocaleResolver没有提供特定的时区（而不是此方法的{@code  null}）。 
	 *  
	 * @param 请求当前HTTP请求
	 * @return 给定请求的当前时区，来自TimeZoneAwareLocaleResolver或{@code  null}（如果没有关联）
	 * @see  #getLocaleResolver 
	 * @see  org.springframework。 
	 *  context.i18n.LocaleContextHolder＃getTimeZone（）
	 */
	@Nullable
	public static TimeZone getTimeZone(HttpServletRequest request) {
		LocaleResolver localeResolver = getLocaleResolver(request);
		if (localeResolver instanceof LocaleContextResolver) {
			LocaleContext localeContext = ((LocaleContextResolver) localeResolver).resolveLocaleContext(request);
			if (localeContext instanceof TimeZoneAwareLocaleContext) {
				return ((TimeZoneAwareLocaleContext) localeContext).getTimeZone();
			}
		}
		return null;
	}

	/**
	 * Return the ThemeResolver that has been bound to the request by the
	 * DispatcherServlet.
	 * @param request current HTTP request
	 * @return the current ThemeResolver, or {@code null} if not found
	 */
	/**
	 * 返回由DispatcherServlet绑定到请求的ThemeResolver。 
	 *  
	 * @param 请求当前的HTTP请求
	 * @return 当前的ThemeResolver，如果找不到，则为{@code  null}
	 */
	@Nullable
	public static ThemeResolver getThemeResolver(HttpServletRequest request) {
		return (ThemeResolver) request.getAttribute(DispatcherServlet.THEME_RESOLVER_ATTRIBUTE);
	}

	/**
	 * Return the ThemeSource that has been bound to the request by the
	 * DispatcherServlet.
	 * @param request current HTTP request
	 * @return the current ThemeSource
	 */
	/**
	 * 返回由DispatcherServlet绑定到请求的ThemeSource。 
	 *  
	 * @param 请求当前的HTTP请求
	 * @return 当前的ThemeSource
	 */
	@Nullable
	public static ThemeSource getThemeSource(HttpServletRequest request) {
		return (ThemeSource) request.getAttribute(DispatcherServlet.THEME_SOURCE_ATTRIBUTE);
	}

	/**
	 * Retrieves the current theme from the given request, using the ThemeResolver
	 * and ThemeSource bound to the request by the DispatcherServlet.
	 * @param request current HTTP request
	 * @return the current theme, or {@code null} if not found
	 * @see #getThemeResolver
	 */
	/**
	 * 使用DispatcherServlet绑定到请求的ThemeResolver和ThemeSource从给定请求中检索当前主题。 
	 *  
	 * @param 请求当前HTTP请求
	 * @return 当前主题，如果找不到，则返回{@code  null} 
	 * @see  #getThemeResolver
	 */
	@Nullable
	public static Theme getTheme(HttpServletRequest request) {
		ThemeResolver themeResolver = getThemeResolver(request);
		ThemeSource themeSource = getThemeSource(request);
		if (themeResolver != null && themeSource != null) {
			String themeName = themeResolver.resolveThemeName(request);
			return themeSource.getTheme(themeName);
		}
		else {
			return null;
		}
	}

	/**
	 * Return read-only "input" flash attributes from request before redirect.
	 * @param request current request
	 * @return a read-only Map, or {@code null} if not found
	 * @see FlashMap
	 */
	/**
	 * 重定向之前，从请求中返回只读的"输入"闪存属性。 
	 *  
	 * @param 请求当前请求
	 * @return 一个只读Map，如果找不到，则返回{@code  null} 
	 * @see  FlashMap
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public static Map<String, ?> getInputFlashMap(HttpServletRequest request) {
		return (Map<String, ?>) request.getAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE);
	}

	/**
	 * Return "output" FlashMap to save attributes for request after redirect.
	 * @param request current request
	 * @return a {@link FlashMap} instance, never {@code null} within a
	 * {@code DispatcherServlet}-handled request
	 */
	/**
	 * 返回"输出"FlashMap，以保存重定向后的请求属性。 
	 *  
	 * @param 请求当前请求
	 * @return 一个{@link  FlashMap}实例，决不能在{@code  DispatcherServlet}处理的请求中使用{@code  null}
	 */
	public static FlashMap getOutputFlashMap(HttpServletRequest request) {
		return (FlashMap) request.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
	}

	/**
	 * Return the {@code FlashMapManager} instance to save flash attributes.
	 * <p>As of 5.0 the convenience method {@link #saveOutputFlashMap} may be
	 * used to save the "output" FlashMap.
	 * @param request the current request
	 * @return a {@link FlashMapManager} instance, never {@code null} within a
	 * {@code DispatcherServlet}-handled request
	 */
	/**
	 * 返回{@code  FlashMapManager}实例以保存Flash属性。 
	 *  <p>从5.0版本开始，便捷方法{@link  #saveOutputFlashMap}可用于保存"输出"FlashMap。 
	 *  
	 * @param 请求当前请求
	 * @return 一个{@link  FlashMapManager}实例，决不能在{@code  DispatcherServlet}处理的请求中使用{@code  null}
	 */
	@Nullable
	public static FlashMapManager getFlashMapManager(HttpServletRequest request) {
		return (FlashMapManager) request.getAttribute(DispatcherServlet.FLASH_MAP_MANAGER_ATTRIBUTE);
	}

	/**
	 * Convenience method that retrieves the {@link #getOutputFlashMap "output"
	 * FlashMap}, updates it with the path and query params of the target URL,
	 * and then saves it using the {@link #getFlashMapManager FlashMapManager}.
	 * @param location the target URL for the redirect
	 * @param request the current request
	 * @param response the current response
	 * @since 5.0
	 */
	/**
	 * 便捷的方法，该方法检索{@link  #getOutputFlashMap"输出"FlashMap}，并使用目标URL的路径和查询参数对其进行更新，然后使用{@link  #getFlashMapManager FlashMapManager}保存它。 
	 *  
	 * @param 定位重定向的目标URL 
	 * @param 请求当前请求
	 * @param 响应当前响应@5.0
	 */
	public static void saveOutputFlashMap(String location, HttpServletRequest request, HttpServletResponse response) {
		FlashMap flashMap = getOutputFlashMap(request);
		if (CollectionUtils.isEmpty(flashMap)) {
			return;
		}

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(location).build();
		flashMap.setTargetRequestPath(uriComponents.getPath());
		flashMap.addTargetRequestParams(uriComponents.getQueryParams());

		FlashMapManager manager = getFlashMapManager(request);
		Assert.state(manager != null, "No FlashMapManager. Is this a DispatcherServlet handled request?");
		manager.saveOutputFlashMap(flashMap, request, response);
	}

}
