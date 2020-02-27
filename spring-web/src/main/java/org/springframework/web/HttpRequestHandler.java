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

package org.springframework.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Plain handler interface for components that process HTTP requests,
 * analogous to a Servlet. Only declares {@link javax.servlet.ServletException}
 * and {@link java.io.IOException}, to allow for usage within any
 * {@link javax.servlet.http.HttpServlet}. This interface is essentially the
 * direct equivalent of an HttpServlet, reduced to a central handle method.
 *
 * <p>The easiest way to expose an HttpRequestHandler bean in Spring style
 * is to define it in Spring's root web application context and define
 * an {@link org.springframework.web.context.support.HttpRequestHandlerServlet}
 * in {@code web.xml}, pointing to the target HttpRequestHandler bean
 * through its {@code servlet-name} which needs to match the target bean name.
 *
 * <p>Supported as a handler type within Spring's
 * {@link org.springframework.web.servlet.DispatcherServlet}, being able
 * to interact with the dispatcher's advanced mapping and interception
 * facilities. This is the recommended way of exposing an HttpRequestHandler,
 * while keeping the handler implementations free of direct dependencies
 * on a DispatcherServlet environment.
 *
 * <p>Typically implemented to generate binary responses directly,
 * with no separate view resource involved. This differentiates it from a
 * {@link org.springframework.web.servlet.mvc.Controller} within Spring's Web MVC
 * framework. The lack of a {@link org.springframework.web.servlet.ModelAndView}
 * return value gives a clearer signature to callers other than the
 * DispatcherServlet, indicating that there will never be a view to render.
 *
 * <p>As of Spring 2.0, Spring's HTTP-based remote exporters, such as
 * {@link org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter}
 * and {@link org.springframework.remoting.caucho.HessianServiceExporter},
 * implement this interface rather than the more extensive Controller interface,
 * for minimal dependencies on Spring-specific web infrastructure.
 *
 * <p>Note that HttpRequestHandlers may optionally implement the
 * {@link org.springframework.web.servlet.mvc.LastModified} interface,
 * just like Controllers can, <i>provided that they run within Spring's
 * DispatcherServlet</i>. However, this is usually not necessary, since
 * HttpRequestHandlers typically only support POST requests to begin with.
 * Alternatively, a handler may implement the "If-Modified-Since" HTTP
 * header processing manually within its {@code handle} method.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.web.context.support.HttpRequestHandlerServlet
 * @see org.springframework.web.servlet.DispatcherServlet
 * @see org.springframework.web.servlet.ModelAndView
 * @see org.springframework.web.servlet.mvc.Controller
 * @see org.springframework.web.servlet.mvc.LastModified
 * @see org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
 * @see org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter
 * @see org.springframework.remoting.caucho.HessianServiceExporter
 */
/**
 * 用于处理HTTP请求的组件的普通处理程序接口，类似于Servlet。 
 * 仅声明{@link  javax.servlet.ServletException}和{@link  java.io.IOException}，以允许在任何{@link  javax.servlet.http.HttpServlet}中使用。 
 * 此接口实质上是HttpServlet的直接等效项，简化为中央处理方法。 
 *  <p>以Spring样式公开HttpRequestHandler bean的最简单方法是在Spring的根Web应用程序上下文中定义它，并在{@code 中定义一个{@link  org.springframework.web.context.support.HttpRequestHandlerServlet} web.xml}，通过其{@code  servlet-name}指向目标HttpRequestHandler bean，它需要与目标bean名称匹配。 
 *  <p>作为Spring的{@link  org.springframework.web.servlet.DispatcherServlet}中的处理程序类型受支持，能够与调度程序的高级映射和侦听功能进行交互。 
 * 这是公开HttpRequestHandler的推荐方法，同时使处理程序实现不受DispatcherServlet环境的直接依赖。 
 *  <p>通常实现为直接生成二进制响应，而不涉及单独的视图资源。 
 * 这与Spring的Web MVC框架中的{@link  org.springframework.web.servlet.mvc.Controller}有所不同。 
 * 缺少{@link  org.springframework.web.servlet.ModelAndView}返回值可以使除DispatcherServlet以外的其他调用者获得更清晰的签名，表明将永远不会呈现视图。 
 *  <p>从Spring 2.0开始，Spring的基于HTTP的远程导出器（例如{@link  org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter}和{@link  org.springframework.remoting.caucho.HessianServiceExporter}）实现了此接口，而不是更广泛的Controller接口，以最小化对特定于Spring的Web基础结构的依赖。 
 *  <p>请注意，HttpController可以选择实现{@link  org.springframework.web.servlet.mvc.LastModified}接口，就像Controller可以<i>提供它们在Spring的DispatcherServlet </ i>中运行一样。 
 * 但是，这通常不是必需的，因为HttpRequestHandlers通常仅支持以POST请求开头。 
 * 或者，处理程序可以在其{@code  handle}方法中手动实现"If-Modified-Since"HTTP标头处理。 
 *  @author  Juergen Hoeller @since 2.0起
 * @see  org.springframework.web.context.support.HttpRequestHandlerServlet 
 * @see  org.springframework.web.servlet.DispatcherServlet 
 * @see  org.springframework.web.servlet.ModelAndView 
 * @see  org.springframework.web.servlet.mvc.Controller 
 * @see  org.springframework.web.servlet.mvc.LastModified 
 * @see  org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter 
 * @see  org .springframework.remoting.httpinvoker.HttpInvokerServiceExporter 
 * @see  org.springframework.remoting.caucho.HessianServiceExporter
 */
@FunctionalInterface
public interface HttpRequestHandler {

	/**
	 * Process the given request, generating a response.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @throws ServletException in case of general errors
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 处理给定的请求，生成响应。 
	 *  
	 * @param 请求当前HTTP请求
	 * @param 响应当前HTTP响应
	 * @throws 发生一般错误时发生ServletException 
	 * @throws 发生I / O错误时发生IOException
	 */
	void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

}
