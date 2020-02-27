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

package org.springframework.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.lang.Nullable;
import org.springframework.web.WebApplicationInitializer;

/**
 * Convenient base class for {@link WebApplicationInitializer} implementations
 * that register a {@link ContextLoaderListener} in the servlet context.
 *
 * <p>The only method required to be implemented by subclasses is
 * {@link #createRootApplicationContext()}, which gets invoked from
 * {@link #registerContextLoaderListener(ServletContext)}.
 *
 * @author Arjen Poutsma
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.2
 */
/**
 * {@link  WebApplicationInitializer}实现的便捷基类，该实现在Servlet上下文中注册了{@link  ContextLoaderListener}。 
 *  <p>子类唯一需要实现的方法是{@link  #createRootApplicationContext（）}，它是从{@link  #registerContextLoaderListener（ServletContext）}调用的。 
 *  @author  Arjen Poutsma @author 克里斯·比姆斯（Chris Beams）@author  Juergen Hoeller @3.2起
 */
public abstract class AbstractContextLoaderInitializer implements WebApplicationInitializer {

	/** Logger available to subclasses. */
	/**
	 * 记录器可用于子类。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerContextLoaderListener(servletContext);
	}

	/**
	 * Register a {@link ContextLoaderListener} against the given servlet context. The
	 * {@code ContextLoaderListener} is initialized with the application context returned
	 * from the {@link #createRootApplicationContext()} template method.
	 * @param servletContext the servlet context to register the listener against
	 */
	/**
	 * 针对给定的servlet上下文注册{@link  ContextLoaderListener}。 
	 *  {@code  ContextLoaderListener}使用从{@link  #createRootApplicationContext（）}模板方法返回的应用程序上下文初始化。 
	 *  
	 * @param  servletContext用来注册侦听器的servlet上下文
	 */
	protected void registerContextLoaderListener(ServletContext servletContext) {
		WebApplicationContext rootAppContext = createRootApplicationContext();
		if (rootAppContext != null) {
			ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
			listener.setContextInitializers(getRootApplicationContextInitializers());
			servletContext.addListener(listener);
		}
		else {
			logger.debug("No ContextLoaderListener registered, as " +
					"createRootApplicationContext() did not return an application context");
		}
	}

	/**
	 * Create the "<strong>root</strong>" application context to be provided to the
	 * {@code ContextLoaderListener}.
	 * <p>The returned context is delegated to
	 * {@link ContextLoaderListener#ContextLoaderListener(WebApplicationContext)} and will
	 * be established as the parent context for any {@code DispatcherServlet} application
	 * contexts. As such, it typically contains middle-tier services, data sources, etc.
	 * @return the root application context, or {@code null} if a root context is not
	 * desired
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
	 */
	/**
	 * 创建要提供给{@code  ContextLoaderListener}的应用程序上下文"<strong> root </ strong>"。 
	 *  <p>返回的上下文委派给{@link  ContextLoaderListener＃ContextLoaderListener（WebApplicationContext）}，并将其建立为任何{@code  DispatcherServlet}应用程序上下文的父上下文。 
	 * 这样，它通常包含中间层服务，数据源等。 
	 * 
	 * @return 根应用程序上下文，如果不需要根上下文，则为{@code  null} 
	 * @see  org.springframework.web。 
	 *  servlet.support.AbstractDispatcherServletInitializer
	 */
	@Nullable
	protected abstract WebApplicationContext createRootApplicationContext();

	/**
	 * Specify application context initializers to be applied to the root application
	 * context that the {@code ContextLoaderListener} is being created with.
	 * @since 4.2
	 * @see #createRootApplicationContext()
	 * @see ContextLoaderListener#setContextInitializers
	 */
	/**
	 * 指定应用程序上下文初始化程序，该初始化程序将应用于创建{@code  ContextLoaderListener}的根应用程序上下文。 
	 *  @since 4.2 
	 * @see  #createRootApplicationContext（）
	 * @see  ContextLoaderListener＃setContextInitializers
	 */
	@Nullable
	protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
		return null;
	}

}
