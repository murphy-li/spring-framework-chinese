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

package org.springframework.web.servlet.handler;

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor}
 * that applies initialization and destruction callbacks to beans that
 * implement the {@link javax.servlet.Servlet} interface.
 *
 * <p>After initialization of the bean instance, the Servlet {@code init}
 * method will be called with a ServletConfig that contains the bean name
 * of the Servlet and the ServletContext that it is running in.
 *
 * <p>Before destruction of the bean instance, the Servlet {@code destroy}
 * will be called.
 *
 * <p><b>Note that this post-processor does not support Servlet initialization
 * parameters.</b> Bean instances that implement the Servlet interface are
 * supposed to be configured like any other Spring bean, that is, through
 * constructor arguments or bean properties.
 *
 * <p>For reuse of a Servlet implementation in a plain Servlet container
 * and as a bean in a Spring context, consider deriving from Spring's
 * {@link org.springframework.web.servlet.HttpServletBean} base class that
 * applies Servlet initialization parameters as bean properties, supporting
 * both the standard Servlet and the Spring bean initialization style.
 *
 * <p><b>Alternatively, consider wrapping a Servlet with Spring's
 * {@link org.springframework.web.servlet.mvc.ServletWrappingController}.</b>
 * This is particularly appropriate for existing Servlet classes,
 * allowing to specify Servlet initialization parameters etc.
 *
 * @author Juergen Hoeller
 * @since 1.1.5
 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
 * @see javax.servlet.Servlet#destroy()
 * @see SimpleServletHandlerAdapter
 */
/**
 * {@link  org.springframework.beans.factory.config.BeanPostProcessor}将初始化和销毁​​回调应用于实现{@link  javax.servlet.Servlet}接口的bean。 
 *  <p>初始化bean实例后，将使用ServletConfig调用Servlet {@code  init}方法，该方法包含Servlet的bean名称和运行它的ServletContext。 
 * <p>销毁Servlet之前bean实例，将调用Servlet {@code  destroy}。 
 *  <p> <b>请注意，此后处理器不支持Servlet初始化参数。 
 * </ b>应该实现实现Servlet接口的Bean实例，就像配置其他任何Spring Bean一样，即通过构造函数参数或Bean属性进行配置。 
 *  <p>要在普通Servlet容器中以及在Spring上下文中作为Bean重用Servlet实现，请考虑派生自Spring的{@link  org.springframework.web.servlet.HttpServletBean}基类，该基类将Servlet初始化参数应用为bean属性，同时支持标准Servlet和Spring bean初始化样式。 
 *  <p> <b>或者，考虑使用Spring的{@link  org.springframework.web.servlet.mvc.ServletWrappingController}包装Servlet。 
 * </ b>这特别适用于现有Servlet类，允许指定Servlet初始化参数等。 
 * @author  Juergen Hoeller @since 1.1.5 
 * @see  javax.servlet.Servlet＃init（javax.servlet.ServletConfig）
 * @see  javax.servlet.Servlet＃destroy（）
 * @see  SimpleServletHandlerAdapter
 */
public class SimpleServletPostProcessor implements
		DestructionAwareBeanPostProcessor, ServletContextAware, ServletConfigAware {

	private boolean useSharedServletConfig = true;

	@Nullable
	private ServletContext servletContext;

	@Nullable
	private ServletConfig servletConfig;


	/**
	 * Set whether to use the shared ServletConfig object passed in
	 * through {@code setServletConfig}, if available.
	 * <p>Default is "true". Turn this setting to "false" to pass in
	 * a mock ServletConfig object with the bean name as servlet name,
	 * holding the current ServletContext.
	 * @see #setServletConfig
	 */
	/**
	 * 设置是否使用通过{@code  setServletConfig}传入的共享ServletConfig对象（如果有）。 
	 *  <p>默认为"true"。 
	 * 将此设置设置为"false"以传入模拟ServletConfig对象，该对象以Bean名称作为Servlet名称，并保留当前的ServletContext。 
	 *  
	 * @see  #setServletConfig
	 */
	public void setUseSharedServletConfig(boolean useSharedServletConfig) {
		this.useSharedServletConfig = useSharedServletConfig;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof Servlet) {
			ServletConfig config = this.servletConfig;
			if (config == null || !this.useSharedServletConfig) {
				config = new DelegatingServletConfig(beanName, this.servletContext);
			}
			try {
				((Servlet) bean).init(config);
			}
			catch (ServletException ex) {
				throw new BeanInitializationException("Servlet.init threw exception", ex);
			}
		}
		return bean;
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
		if (bean instanceof Servlet) {
			((Servlet) bean).destroy();
		}
	}

	@Override
	public boolean requiresDestruction(Object bean) {
		return (bean instanceof Servlet);
	}


	/**
	 * Internal implementation of the {@link ServletConfig} interface,
	 * to be passed to the wrapped servlet.
	 */
	/**
	 * {@link  ServletConfig}接口的内部实现，将传递给包装的servlet。 
	 * 
	 */
	private static class DelegatingServletConfig implements ServletConfig {

		private final String servletName;

		@Nullable
		private final ServletContext servletContext;

		public DelegatingServletConfig(String servletName, @Nullable ServletContext servletContext) {
			this.servletName = servletName;
			this.servletContext = servletContext;
		}

		@Override
		public String getServletName() {
			return this.servletName;
		}

		@Override
		@Nullable
		public ServletContext getServletContext() {
			return this.servletContext;
		}

		@Override
		@Nullable
		public String getInitParameter(String paramName) {
			return null;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(Collections.emptySet());
		}
	}

}
