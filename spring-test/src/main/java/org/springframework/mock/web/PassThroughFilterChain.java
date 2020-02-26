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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Implementation of the {@link javax.servlet.FilterConfig} interface which
 * simply passes the call through to a given Filter/FilterChain combination
 * (indicating the next Filter in the chain along with the FilterChain that it is
 * supposed to work on) or to a given Servlet (indicating the end of the chain).
 *
 * @author Juergen Hoeller
 * @since 2.0.3
 * @see javax.servlet.Filter
 * @see javax.servlet.Servlet
 * @see MockFilterChain
 */
/**
 * {@link  javax.servlet.FilterConfig}接口的实现，该接口将调用简单地传递到给定的Filter / FilterChain组合（指示链中的下一个Filter以及应该运行的FilterChain）。 
 * 给定的Servlet（指示链的末尾）。 
 *  @author  Juergen Hoeller @since 2.0.3 
 * @see  javax.servlet.Filter 
 * @see  javax.servlet.Servlet 
 * @see  MockFilterChain
 */
public class PassThroughFilterChain implements FilterChain {

	@Nullable
	private Filter filter;

	@Nullable
	private FilterChain nextFilterChain;

	@Nullable
	private Servlet servlet;


	/**
	 * Create a new PassThroughFilterChain that delegates to the given Filter,
	 * calling it with the given FilterChain.
	 * @param filter the Filter to delegate to
	 * @param nextFilterChain the FilterChain to use for that next Filter
	 */
	/**
	 * 创建一个委托给定Filter的新PassThroughFilterChain，并使用给定FilterChain对其进行调用。 
	 *  
	 * @param 过滤将委托给
	 * @param 的过滤器nextFilterChain将过滤器链用于下一个过滤器
	 */
	public PassThroughFilterChain(Filter filter, FilterChain nextFilterChain) {
		Assert.notNull(filter, "Filter must not be null");
		Assert.notNull(nextFilterChain, "'FilterChain must not be null");
		this.filter = filter;
		this.nextFilterChain = nextFilterChain;
	}

	/**
	 * Create a new PassThroughFilterChain that delegates to the given Servlet.
	 * @param servlet the Servlet to delegate to
	 */
	/**
	 * 创建一个委托给定Servlet的新PassThroughFilterChain。 
	 *  
	 * @param  Servlet委托给的Servlet
	 */
	public PassThroughFilterChain(Servlet servlet) {
		Assert.notNull(servlet, "Servlet must not be null");
		this.servlet = servlet;
	}


	/**
	 * Pass the call on to the Filter/Servlet.
	 */
	/**
	 * 将调用传递给Filter / Servlet。 
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		if (this.filter != null) {
			this.filter.doFilter(request, response, this.nextFilterChain);
		}
		else {
			Assert.state(this.servlet != null, "Neither a Filter not a Servlet set");
			this.servlet.service(request, response);
		}
	}

}
