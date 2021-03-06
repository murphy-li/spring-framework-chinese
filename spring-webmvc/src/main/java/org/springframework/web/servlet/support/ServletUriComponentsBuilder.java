/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * UriComponentsBuilder with additional static factory methods to create links
 * based on the current HttpServletRequest.
 *
 * <p><strong>Note:</strong> As of 5.1, methods in this class do not extract
 * {@code "Forwarded"} and {@code "X-Forwarded-*"} headers that specify the
 * client-originated address. Please, use
 * {@link org.springframework.web.filter.ForwardedHeaderFilter
 * ForwardedHeaderFilter}, or similar from the underlying server, to extract
 * and use such headers, or to discard them.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * UriComponentsBuilder具有其他静态工厂方法，可以基于当前HttpServletRequest创建链接。 
 *  <p> <strong>注意：</ strong>从5.1开始，此类中的方法不会提取指定客户端的{@code "Forwarded"}和{@code "X-Forwarded-"}标头起源的地址。 
 * 请从基础服务器使用{@link  org.springframework.web.filter.ForwardedHeaderFilter ForwardedHeaderFilter}或类似方法提取和使用此类标头，或将其丢弃。 
 *  @author  Rossen Stoyanchev @从3.1开始
 */
public class ServletUriComponentsBuilder extends UriComponentsBuilder {

	@Nullable
	private String originalPath;


	/**
	 * Default constructor. Protected to prevent direct instantiation.
	 * @see #fromContextPath(HttpServletRequest)
	 * @see #fromServletMapping(HttpServletRequest)
	 * @see #fromRequest(HttpServletRequest)
	 * @see #fromCurrentContextPath()
	 * @see #fromCurrentServletMapping()
 	 * @see #fromCurrentRequest()
	 */
	/**
	 * 默认构造函数。 
	 * 受保护以防止直接实例化。 
	 *  
	 * @see  #fromContextPath（HttpServletRequest）
	 * @see  #fromServletMapping（HttpServletRequest）
	 * @see  #fromRequest（HttpServletRequest）
	 * @see  #fromCurrentContextPath（）
	 * @see  #fromCurrentServletMapping（）
	 * @see  #fromCurrentRequest（ ）
	 */
	protected ServletUriComponentsBuilder() {
	}

	/**
	 * Create a deep copy of the given ServletUriComponentsBuilder.
	 * @param other the other builder to copy from
	 */
	/**
	 * 创建给定ServletUriComponentsBuilder的深层副本。 
	 *  
	 * @param 其他要复制的构建器
	 */
	protected ServletUriComponentsBuilder(ServletUriComponentsBuilder other) {
		super(other);
		this.originalPath = other.originalPath;
	}


	// Factory methods based on an HttpServletRequest

	/**
	 * Prepare a builder from the host, port, scheme, and context path of the
	 * given HttpServletRequest.
	 */
	/**
	 * 从给定HttpServletRequest的主机，端口，方案和上下文路径准备构建器。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromContextPath(HttpServletRequest request) {
		ServletUriComponentsBuilder builder = initFromRequest(request);
		builder.replacePath(request.getContextPath());
		return builder;
	}

	/**
	 * Prepare a builder from the host, port, scheme, context path, and
	 * servlet mapping of the given HttpServletRequest.
	 * <p>If the servlet is mapped by name, e.g. {@code "/main/*"}, the path
	 * will end with "/main". If the servlet is mapped otherwise, e.g.
	 * {@code "/"} or {@code "*.do"}, the result will be the same as
	 * if calling {@link #fromContextPath(HttpServletRequest)}.
	 */
	/**
	 * 根据给定HttpServletRequest的主机，端口，方案，上下文路径和Servlet映射准备构建器。 
	 *  <p>如果servlet是按名称映射的，例如{@code "/ main"}，路径将以"/ main"结尾。 
	 * 如果servlet被映射，例如{@code "/"}或{@code ".do"}，其结果与调用{@link  #fromContextPath（HttpServletRequest）}的结果相同。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromServletMapping(HttpServletRequest request) {
		ServletUriComponentsBuilder builder = fromContextPath(request);
		if (StringUtils.hasText(new UrlPathHelper().getPathWithinServletMapping(request))) {
			builder.path(request.getServletPath());
		}
		return builder;
	}

	/**
	 * Prepare a builder from the host, port, scheme, and path (but not the query)
	 * of the HttpServletRequest.
	 */
	/**
	 * 从HttpServletRequest的主机，端口，方案和路径（但不是查询）准备构建器。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromRequestUri(HttpServletRequest request) {
		ServletUriComponentsBuilder builder = initFromRequest(request);
		builder.initPath(request.getRequestURI());
		return builder;
	}

	/**
	 * Prepare a builder by copying the scheme, host, port, path, and
	 * query string of an HttpServletRequest.
	 */
	/**
	 * 通过复制HttpServletRequest的方案，主机，端口，路径和查询字符串来准备构建器。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromRequest(HttpServletRequest request) {
		ServletUriComponentsBuilder builder = initFromRequest(request);
		builder.initPath(request.getRequestURI());
		builder.query(request.getQueryString());
		return builder;
	}

	/**
	 * Initialize a builder with a scheme, host,and port (but not path and query).
	 */
	/**
	 * 使用方案，主机和端口（但不包括路径和查询）初始化构建器。 
	 * 
	 */
	private static ServletUriComponentsBuilder initFromRequest(HttpServletRequest request) {
		String scheme = request.getScheme();
		String host = request.getServerName();
		int port = request.getServerPort();

		ServletUriComponentsBuilder builder = new ServletUriComponentsBuilder();
		builder.scheme(scheme);
		builder.host(host);
		if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
			builder.port(port);
		}
		return builder;
	}


	// Alternative methods relying on RequestContextHolder to find the request

	/**
	 * Same as {@link #fromContextPath(HttpServletRequest)} except the
	 * request is obtained through {@link RequestContextHolder}.
	 */
	/**
	 * 与{@link  #fromContextPath（HttpServletRequest）}相同，只是通过{@link  RequestContextHolder}获取请求。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromCurrentContextPath() {
		return fromContextPath(getCurrentRequest());
	}

	/**
	 * Same as {@link #fromServletMapping(HttpServletRequest)} except the
	 * request is obtained through {@link RequestContextHolder}.
	 */
	/**
	 * 与{@link  #fromServletMapping（HttpServletRequest）}相同，只是通过{@link  RequestContextHolder}获取请求。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromCurrentServletMapping() {
		return fromServletMapping(getCurrentRequest());
	}

	/**
	 * Same as {@link #fromRequestUri(HttpServletRequest)} except the
	 * request is obtained through {@link RequestContextHolder}.
	 */
	/**
	 * 与{@link  #fromRequestUri（HttpServletRequest）}相同，只是通过{@link  RequestContextHolder}获取请求。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromCurrentRequestUri() {
		return fromRequestUri(getCurrentRequest());
	}

	/**
	 * Same as {@link #fromRequest(HttpServletRequest)} except the
	 * request is obtained through {@link RequestContextHolder}.
	 */
	/**
	 * 与{@link  #fromRequest（HttpServletRequest）}相同，只是通过{@link  RequestContextHolder}获取请求。 
	 * 
	 */
	public static ServletUriComponentsBuilder fromCurrentRequest() {
		return fromRequest(getCurrentRequest());
	}

	/**
	 * Obtain current request through {@link RequestContextHolder}.
	 */
	/**
	 * 通过{@link  RequestContextHolder}获取当前请求。 
	 * 
	 */
	protected static HttpServletRequest getCurrentRequest() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.state(attrs instanceof ServletRequestAttributes, "No current ServletRequestAttributes");
		return ((ServletRequestAttributes) attrs).getRequest();
	}


	private void initPath(String path) {
		this.originalPath = path;
		replacePath(path);
	}

	/**
	 * Remove any path extension from the {@link HttpServletRequest#getRequestURI()
	 * requestURI}. This method must be invoked before any calls to {@link #path(String)}
	 * or {@link #pathSegment(String...)}.
	 * <pre>
	 * GET http://www.foo.example/rest/books/6.json
	 *
	 * ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequestUri(this.request);
	 * String ext = builder.removePathExtension();
	 * String uri = builder.path("/pages/1.{ext}").buildAndExpand(ext).toUriString();
	 * assertEquals("http://www.foo.example/rest/books/6/pages/1.json", result);
	 * </pre>
	 * @return the removed path extension for possible re-use, or {@code null}
	 * @since 4.0
	 */
	/**
	 * 从{@link  HttpServletRequest＃getRequestURI（）requestURI}中删除所有路径扩展。 
	 * 必须先调用此方法，然后再调用{@link  #path（String）}或{@link  #pathSegment（String ...）}。 
	 *  <pre> GET http：//www.foo.example/rest/books/6.json ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequestUri（this.request）;字符串ext = builder.removePathExtension（）;字符串uri = builder.path（"/ pages / 1。 
	 * {ext}"）。 
	 * buildAndExpand（ext）.toUriString（）; assertEquals（"http：//www.foo.example/rest/books/6/pages/1.json"，结果）； 
	 *  </ pre> 
	 * @return 删除的路径扩展名，以供重新使用，或者{@code  null} @从4.0开始
	 */
	@Nullable
	public String removePathExtension() {
		String extension = null;
		if (this.originalPath != null) {
			extension = UriUtils.extractFileExtension(this.originalPath);
			if (StringUtils.hasLength(extension)) {
				int end = this.originalPath.length() - (extension.length() + 1);
				replacePath(this.originalPath.substring(0, end));
			}
			this.originalPath = null;
		}
		return extension;
	}

	@Override
	public ServletUriComponentsBuilder cloneBuilder() {
		return new ServletUriComponentsBuilder(this);
	}

}
