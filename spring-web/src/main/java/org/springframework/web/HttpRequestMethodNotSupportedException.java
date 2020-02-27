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

package org.springframework.web;

import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * Exception thrown when a request handler does not support a
 * specific request method.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 请求处理程序不支持特定请求方法时引发的异常。 
 *  @author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public class HttpRequestMethodNotSupportedException extends ServletException {

	private final String method;

	@Nullable
	private final String[] supportedMethods;


	/**
	 * Create a new HttpRequestMethodNotSupportedException.
	 * @param method the unsupported HTTP request method
	 */
	/**
	 * 创建一个新的HttpRequestMethodNotSupportedException。 
	 *  
	 * @param 方法不受支持的HTTP请求方法
	 */
	public HttpRequestMethodNotSupportedException(String method) {
		this(method, (String[]) null);
	}

	/**
	 * Create a new HttpRequestMethodNotSupportedException.
	 * @param method the unsupported HTTP request method
	 * @param msg the detail message
	 */
	/**
	 * 创建一个新的HttpRequestMethodNotSupportedException。 
	 *  
	 * @param 方法不受支持的HTTP请求方法
	 * @param  msg详细消息
	 */
	public HttpRequestMethodNotSupportedException(String method, String msg) {
		this(method, null, msg);
	}

	/**
	 * Create a new HttpRequestMethodNotSupportedException.
	 * @param method the unsupported HTTP request method
	 * @param supportedMethods the actually supported HTTP methods (may be {@code null})
	 */
	/**
	 * 创建一个新的HttpRequestMethodNotSupportedException。 
	 *  
	 * @param 方法不受支持的HTTP请求方法
	 * @param  supportMethods实际支持的HTTP方法（可以为{@code  null}）
	 */
	public HttpRequestMethodNotSupportedException(String method, @Nullable Collection<String> supportedMethods) {
		this(method, (supportedMethods != null ? StringUtils.toStringArray(supportedMethods) : null));
	}

	/**
	 * Create a new HttpRequestMethodNotSupportedException.
	 * @param method the unsupported HTTP request method
	 * @param supportedMethods the actually supported HTTP methods (may be {@code null})
	 */
	/**
	 * 创建一个新的HttpRequestMethodNotSupportedException。 
	 *  
	 * @param 方法不受支持的HTTP请求方法
	 * @param  supportMethods实际支持的HTTP方法（可以为{@code  null}）
	 */
	public HttpRequestMethodNotSupportedException(String method, @Nullable String[] supportedMethods) {
		this(method, supportedMethods, "Request method '" + method + "' not supported");
	}

	/**
	 * Create a new HttpRequestMethodNotSupportedException.
	 * @param method the unsupported HTTP request method
	 * @param supportedMethods the actually supported HTTP methods
	 * @param msg the detail message
	 */
	/**
	 * 创建一个新的HttpRequestMethodNotSupportedException。 
	 *  
	 * @param 方法不受支持的HTTP请求方法
	 * @param 支持的方法实际支持的HTTP方法
	 * @param  msg详细消息
	 */
	public HttpRequestMethodNotSupportedException(String method, @Nullable String[] supportedMethods, String msg) {
		super(msg);
		this.method = method;
		this.supportedMethods = supportedMethods;
	}


	/**
	 * Return the HTTP request method that caused the failure.
	 */
	/**
	 * 返回导致失败的HTTP请求方法。 
	 * 
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Return the actually supported HTTP methods, or {@code null} if not known.
	 */
	/**
	 * 返回实际支持的HTTP方法； 
	 * 如果未知，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	public String[] getSupportedMethods() {
		return this.supportedMethods;
	}

	/**
	 * Return the actually supported HTTP methods as {@link HttpMethod} instances,
	 * or {@code null} if not known.
	 * @since 3.2
	 */
	/**
	 * 返回实际支持的HTTP方法作为{@link  HttpMethod}实例，如果未知，则返回{@code  null}。 
	 *  @3.2起
	 */
	@Nullable
	public Set<HttpMethod> getSupportedHttpMethods() {
		if (this.supportedMethods == null) {
			return null;
		}
		List<HttpMethod> supportedMethods = new LinkedList<>();
		for (String value : this.supportedMethods) {
			HttpMethod resolved = HttpMethod.resolve(value);
			if (resolved != null) {
				supportedMethods.add(resolved);
			}
		}
		return EnumSet.copyOf(supportedMethods);
	}

}
