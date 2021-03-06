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

package org.springframework.web.servlet.mvc.method.annotation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.ServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves servlet backed response-related method arguments. Supports values of the
 * following types:
 * <ul>
 * <li>{@link ServletResponse}
 * <li>{@link OutputStream}
 * <li>{@link Writer}
 * </ul>
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 3.1
 */
/**
 * 解决Servlet支持的与响应相关的方法参数。 
 * 支持以下类型的值：<ul> <li> {<@link> ServletResponse} <li> {<@link> OutputStream} <li> {<@link> Writer} </ ul> @author  Arjen Poutsma @author  Rossen Stoyanchev @author 于尔根·霍勒（Juergen Hoeller）@从3.1开始
 */
public class ServletResponseMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> paramType = parameter.getParameterType();
		return (ServletResponse.class.isAssignableFrom(paramType) ||
				OutputStream.class.isAssignableFrom(paramType) ||
				Writer.class.isAssignableFrom(paramType));
	}

	/**
	 * Set {@link ModelAndViewContainer#setRequestHandled(boolean)} to
	 * {@code false} to indicate that the method signature provides access
	 * to the response. If subsequently the underlying method returns
	 * {@code null}, the request is considered directly handled.
	 */
	/**
	 * 将{@link  ModelAndViewContainer＃setRequestHandled（boolean）}设置为{@code  false}，以指示方法签名提供对响应的访问。 
	 * 如果随后基础方法返回{@code  null}，则认为该请求已直接处理。 
	 * 
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

		if (mavContainer != null) {
			mavContainer.setRequestHandled(true);
		}

		Class<?> paramType = parameter.getParameterType();

		// ServletResponse, HttpServletResponse
		if (ServletResponse.class.isAssignableFrom(paramType)) {
			return resolveNativeResponse(webRequest, paramType);
		}

		// ServletResponse required for all further argument types
		return resolveArgument(paramType, resolveNativeResponse(webRequest, ServletResponse.class));
	}

	private <T> T resolveNativeResponse(NativeWebRequest webRequest, Class<T> requiredType) {
		T nativeResponse = webRequest.getNativeResponse(requiredType);
		if (nativeResponse == null) {
			throw new IllegalStateException(
					"Current response is not of type [" + requiredType.getName() + "]: " + webRequest);
		}
		return nativeResponse;
	}

	private Object resolveArgument(Class<?> paramType, ServletResponse response) throws IOException {
		if (OutputStream.class.isAssignableFrom(paramType)) {
			return response.getOutputStream();
		}
		else if (Writer.class.isAssignableFrom(paramType)) {
			return response.getWriter();
		}

		// Should never happen...
		throw new UnsupportedOperationException("Unknown parameter type: " + paramType);
	}

}
