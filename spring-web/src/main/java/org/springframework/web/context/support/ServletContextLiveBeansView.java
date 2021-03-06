/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.context.support;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.LiveBeansView;
import org.springframework.util.Assert;

/**
 * {@link LiveBeansView} subclass which looks for all ApplicationContexts
 * in the web application, as exposed in ServletContext attributes.
 *
 * @author Juergen Hoeller
 * @since 3.2
 */
/**
 * {@link  LiveBeansView}子类，用于在Web应用程序中查找所有ServletContext属性中公开的ApplicationContext。 
 *  @author  Juergen Hoeller @自3.2起
 */
public class ServletContextLiveBeansView extends LiveBeansView {

	private final ServletContext servletContext;

	/**
	 * Create a new LiveBeansView for the given ServletContext.
	 * @param servletContext current ServletContext
	 */
	/**
	 * 为给定的ServletContext创建一个新的LiveBeansView。 
	 *  
	 * @param  servletContext当前的ServletContext
	 */
	public ServletContextLiveBeansView(ServletContext servletContext) {
		Assert.notNull(servletContext, "ServletContext must not be null");
		this.servletContext = servletContext;
	}

	@Override
	protected Set<ConfigurableApplicationContext> findApplicationContexts() {
		Set<ConfigurableApplicationContext> contexts = new LinkedHashSet<>();
		Enumeration<String> attrNames = this.servletContext.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			Object attrValue = this.servletContext.getAttribute(attrName);
			if (attrValue instanceof ConfigurableApplicationContext) {
				contexts.add((ConfigurableApplicationContext) attrValue);
			}
		}
		return contexts;
	}

}
