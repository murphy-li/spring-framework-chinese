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

package org.springframework.web.bind;

import org.springframework.core.MethodParameter;

/**
 * {@link ServletRequestBindingException} subclass that indicates
 * that a request cookie expected in the method parameters of an
 * {@code @RequestMapping} method is not present.
 *
 * @author Juergen Hoeller
 * @since 5.1
 * @see MissingRequestHeaderException
 */
/**
 * {@link  ServletRequestBindingException}子类，它指示不存在{@code  @RequestMapping}方法的方法参数中预期的请求cookie。 
 *  @author  Juergen Hoeller @5.1起
 * @see  MissingRequestHeaderException
 */
@SuppressWarnings("serial")
public class MissingRequestCookieException extends ServletRequestBindingException {

	private final String cookieName;

	private final MethodParameter parameter;


	/**
	 * Constructor for MissingRequestCookieException.
	 * @param cookieName the name of the missing request cookie
	 * @param parameter the method parameter
	 */
	/**
	 * MissingRequestCookieException的构造方法。 
	 *  
	 * @param  cookieName缺少请求cookie的名称
	 * @param 参数方法参数
	 */
	public MissingRequestCookieException(String cookieName, MethodParameter parameter) {
		super("");
		this.cookieName = cookieName;
		this.parameter = parameter;
	}


	@Override
	public String getMessage() {
		return "Missing cookie '" + this.cookieName +
				"' for method parameter of type " + this.parameter.getNestedParameterType().getSimpleName();
	}

	/**
	 * Return the expected name of the request cookie.
	 */
	/**
	 * 返回请求cookie的期望名称。 
	 * 
	 */
	public final String getCookieName() {
		return this.cookieName;
	}

	/**
	 * Return the method parameter bound to the request cookie.
	 */
	/**
	 * 返回绑定到请求cookie的方法参数。 
	 * 
	 */
	public final MethodParameter getParameter() {
		return this.parameter;
	}

}
