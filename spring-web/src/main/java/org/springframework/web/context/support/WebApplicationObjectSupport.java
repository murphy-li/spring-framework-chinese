/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.context.support;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.WebUtils;

/**
 * Convenient superclass for application objects running in a {@link WebApplicationContext}.
 * Provides {@code getWebApplicationContext()}, {@code getServletContext()}, and
 * {@code getTempDir()} accessors.
 *
 * <p>Note: It is generally recommended to use individual callback interfaces for the actual
 * callbacks needed. This broad base class is primarily intended for use within the framework,
 * in case of {@link ServletContext} access etc typically being needed.
 *
 * @author Juergen Hoeller
 * @since 28.08.2003
 * @see SpringBeanAutowiringSupport
 */
/**
 * 在{@link  WebApplicationContext}中运行的应用程序对象的便捷超类。 
 * 提供{@code  getWebApplicationContext（）}，{<@code> getServletContext（）}和{@code  getTempDir（）}访问器。 
 *  <p>注意：通常建议将各个回调接口用于所需的实际回调。 
 * 在通常需要{@link  ServletContext}访问等的情况下，这种广泛的基类主要用于框架内。 
 *  @author  Juergen Hoeller @2003年8月28日起
 * @see  SpringBeanAutowiringSupport
 */
public abstract class WebApplicationObjectSupport extends ApplicationObjectSupport implements ServletContextAware {

	@Nullable
	private ServletContext servletContext;


	@Override
	public final void setServletContext(ServletContext servletContext) {
		if (servletContext != this.servletContext) {
			this.servletContext = servletContext;
			initServletContext(servletContext);
		}
	}

	/**
	 * Overrides the base class behavior to enforce running in an ApplicationContext.
	 * All accessors will throw IllegalStateException if not running in a context.
	 * @see #getApplicationContext()
	 * @see #getMessageSourceAccessor()
	 * @see #getWebApplicationContext()
	 * @see #getServletContext()
	 * @see #getTempDir()
	 */
	/**
	 * 重写基类的行为以强制在ApplicationContext中运行。 
	 * 如果未在上下文中运行，则所有访问器都将引发IllegalStateException。 
	 *  
	 * @see  #getApplicationContext（）
	 * @see  #getMessageSourceAccessor（）
	 * @see  #getWebApplicationContext（）
	 * @see  #getServletContext（）
	 * @see  #getTempDir（）
	 */
	@Override
	protected boolean isContextRequired() {
		return true;
	}

	/**
	 * Calls {@link #initServletContext(javax.servlet.ServletContext)} if the
	 * given ApplicationContext is a {@link WebApplicationContext}.
	 */
	/**
	 * 如果给定的ApplicationContext是{@link  WebApplicationContext}，则调用{@link  #initServletContext（javax.servlet.ServletContext）}。 
	 * 
	 */
	@Override
	protected void initApplicationContext(ApplicationContext context) {
		super.initApplicationContext(context);
		if (this.servletContext == null && context instanceof WebApplicationContext) {
			this.servletContext = ((WebApplicationContext) context).getServletContext();
			if (this.servletContext != null) {
				initServletContext(this.servletContext);
			}
		}
	}

	/**
	 * Subclasses may override this for custom initialization based
	 * on the ServletContext that this application object runs in.
	 * <p>The default implementation is empty. Called by
	 * {@link #initApplicationContext(org.springframework.context.ApplicationContext)}
	 * as well as {@link #setServletContext(javax.servlet.ServletContext)}.
	 * @param servletContext the ServletContext that this application object runs in
	 * (never {@code null})
	 */
	/**
	 * 子类可以基于此应用程序对象在其中运行的ServletContext重写此值以进行自定义初始化。 
	 * <p>默认实现为空。 
	 * 由{@link  #initApplicationContext（org.springframework.context.ApplicationContext）}和{@link  #setServletContext（javax.servlet.ServletContext）}调用。 
	 *  
	 * @param  servletContext此应用程序对象在其中运行的ServletContext（从不{@code  null}）
	 */
	protected void initServletContext(ServletContext servletContext) {
	}

	/**
	 * Return the current application context as WebApplicationContext.
	 * <p><b>NOTE:</b> Only use this if you actually need to access
	 * WebApplicationContext-specific functionality. Preferably use
	 * {@code getApplicationContext()} or {@code getServletContext()}
	 * else, to be able to run in non-WebApplicationContext environments as well.
	 * @throws IllegalStateException if not running in a WebApplicationContext
	 * @see #getApplicationContext()
	 */
	/**
	 * 将当前应用程序上下文作为WebApplicationContext返回。 
	 *  <p> <b>注意：</ b>仅在确实需要访问特定于WebApplicationContext的功能时才使用此功能。 
	 * 最好使用{@code  getApplicationContext（）}或{@code  getServletContext（）}其他，以便也可以在非WebApplicationContext环境中运行。 
	 *  
	 * @throws  IllegalStateException如果未在WebApplicationContext中运行
	 * @see  #getApplicationContext（）
	 */
	@Nullable
	protected final WebApplicationContext getWebApplicationContext() throws IllegalStateException {
		ApplicationContext ctx = getApplicationContext();
		if (ctx instanceof WebApplicationContext) {
			return (WebApplicationContext) getApplicationContext();
		}
		else if (isContextRequired()) {
			throw new IllegalStateException("WebApplicationObjectSupport instance [" + this +
					"] does not run in a WebApplicationContext but in: " + ctx);
		}
		else {
			return null;
		}
	}

	/**
	 * Return the current ServletContext.
	 * @throws IllegalStateException if not running within a required ServletContext
	 * @see #isContextRequired()
	 */
	/**
	 * 返回当前的ServletContext。 
	 *  
	 * @throws  IllegalStateException如果未在必需的ServletContext中运行
	 * @see  #isContextRequired（）
	 */
	@Nullable
	protected final ServletContext getServletContext() throws IllegalStateException {
		if (this.servletContext != null) {
			return this.servletContext;
		}
		ServletContext servletContext = null;
		WebApplicationContext wac = getWebApplicationContext();
		if (wac != null) {
			servletContext = wac.getServletContext();
		}
		if (servletContext == null && isContextRequired()) {
			throw new IllegalStateException("WebApplicationObjectSupport instance [" + this +
					"] does not run within a ServletContext. Make sure the object is fully configured!");
		}
		return servletContext;
	}

	/**
	 * Return the temporary directory for the current web application,
	 * as provided by the servlet container.
	 * @return the File representing the temporary directory
	 * @throws IllegalStateException if not running within a ServletContext
	 * @see org.springframework.web.util.WebUtils#getTempDir(javax.servlet.ServletContext)
	 */
	/**
	 * 返回servlet容器提供的当前Web应用程序的临时目录。 
	 *  
	 * @return 表示临时目录的文件
	 * @throws  IllegalStateException如果不在ServletContext中运行
	 * @see  org.springframework.web.util.WebUtils＃getTempDir（javax.servlet.ServletContext）
	 */
	protected final File getTempDir() throws IllegalStateException {
		ServletContext servletContext = getServletContext();
		Assert.state(servletContext != null, "ServletContext is required");
		return WebUtils.getTempDir(servletContext);
	}

}
