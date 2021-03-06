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

package org.springframework.web.testfixture.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.Assert;

/**
 * Mock implementation of the {@link javax.servlet.RequestDispatcher} interface.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 1.0.2
 * @see MockHttpServletRequest#getRequestDispatcher(String)
 */
/**
 * {@link  javax.servlet.RequestDispatcher}接口的模拟实现。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Sam Brannen @始于1.0.2 
 * @see  MockHttpServletRequest＃getRequestDispatcher（String）
 */
public class MockRequestDispatcher implements RequestDispatcher {

	private final Log logger = LogFactory.getLog(getClass());

	private final String resource;


	/**
	 * Create a new MockRequestDispatcher for the given resource.
	 * @param resource the server resource to dispatch to, located at a
	 * particular path or given by a particular name
	 */
	/**
	 * 为给定资源创建一个新的MockRequestDispatcher。 
	 *  
	 * @param 资源服务器资源，要调度到的服务器，位于特定路径或由特定名称指定
	 */
	public MockRequestDispatcher(String resource) {
		Assert.notNull(resource, "Resource must not be null");
		this.resource = resource;
	}


	@Override
	public void forward(ServletRequest request, ServletResponse response) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(response, "Response must not be null");
		Assert.state(!response.isCommitted(), "Cannot perform forward - response is already committed");
		getMockHttpServletResponse(response).setForwardedUrl(this.resource);
		if (logger.isDebugEnabled()) {
			logger.debug("MockRequestDispatcher: forwarding to [" + this.resource + "]");
		}
	}

	@Override
	public void include(ServletRequest request, ServletResponse response) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(response, "Response must not be null");
		getMockHttpServletResponse(response).addIncludedUrl(this.resource);
		if (logger.isDebugEnabled()) {
			logger.debug("MockRequestDispatcher: including [" + this.resource + "]");
		}
	}

	/**
	 * Obtain the underlying {@link MockHttpServletResponse}, unwrapping
	 * {@link HttpServletResponseWrapper} decorators if necessary.
	 */
	/**
	 * 获取基础的{@link  MockHttpServletResponse}，并在必要时展开{@link  HttpServletResponseWrapper}装饰器。 
	 * 
	 */
	protected MockHttpServletResponse getMockHttpServletResponse(ServletResponse response) {
		if (response instanceof MockHttpServletResponse) {
			return (MockHttpServletResponse) response;
		}
		if (response instanceof HttpServletResponseWrapper) {
			return getMockHttpServletResponse(((HttpServletResponseWrapper) response).getResponse());
		}
		throw new IllegalArgumentException("MockRequestDispatcher requires MockHttpServletResponse");
	}

}
