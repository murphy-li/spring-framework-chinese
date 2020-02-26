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

package org.springframework.mock.web;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Mock implementation of the {@link javax.servlet.ServletConfig} interface.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 1.0.2
 */
/**
 * {{@link> javax.servlet.ServletConfig}接口的模拟实现。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @始于1.0.2
 */
public class MockServletConfig implements ServletConfig {

	private final ServletContext servletContext;

	private final String servletName;

	private final Map<String, String> initParameters = new LinkedHashMap<>();


	/**
	 * Create a new MockServletConfig with a default {@link MockServletContext}.
	 */
	/**
	 * 使用默认的{@link  MockServletContext}创建一个新的MockServletConfig。 
	 * 
	 */
	public MockServletConfig() {
		this(null, "");
	}

	/**
	 * Create a new MockServletConfig with a default {@link MockServletContext}.
	 * @param servletName the name of the servlet
	 */
	/**
	 * 使用默认的{@link  MockServletContext}创建一个新的MockServletConfig。 
	 *  
	 * @param  servletName servlet的名称
	 */
	public MockServletConfig(String servletName) {
		this(null, servletName);
	}

	/**
	 * Create a new MockServletConfig.
	 * @param servletContext the ServletContext that the servlet runs in
	 */
	/**
	 * 创建一个新的MockServletConfig。 
	 *  
	 * @param  servletContext servlet在其中运行的ServletContext
	 */
	public MockServletConfig(@Nullable ServletContext servletContext) {
		this(servletContext, "");
	}

	/**
	 * Create a new MockServletConfig.
	 * @param servletContext the ServletContext that the servlet runs in
	 * @param servletName the name of the servlet
	 */
	/**
	 * 创建一个新的MockServletConfig。 
	 *  
	 * @param  servletContext servlet在
	 * @param 中运行的ServletContext servletName servlet的名称
	 */
	public MockServletConfig(@Nullable ServletContext servletContext, String servletName) {
		this.servletContext = (servletContext != null ? servletContext : new MockServletContext());
		this.servletName = servletName;
	}


	@Override
	public String getServletName() {
		return this.servletName;
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public void addInitParameter(String name, String value) {
		Assert.notNull(name, "Parameter name must not be null");
		this.initParameters.put(name, value);
	}

	@Override
	public String getInitParameter(String name) {
		Assert.notNull(name, "Parameter name must not be null");
		return this.initParameters.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(this.initParameters.keySet());
	}

}
