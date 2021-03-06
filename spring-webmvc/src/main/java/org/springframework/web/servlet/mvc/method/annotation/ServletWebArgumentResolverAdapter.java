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

package org.springframework.web.servlet.mvc.method.annotation;

import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter;

/**
 * A Servlet-specific
 * {@link org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter}
 * that creates a {@link NativeWebRequest} from {@link ServletRequestAttributes}.
 *
 * <p><strong>Note:</strong> This class is provided for backwards compatibility.
 * However it is recommended to re-write a {@code WebArgumentResolver} as
 * {@code HandlerMethodArgumentResolver}. For more details see javadoc of
 * {@link org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter}.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 特定于Servlet的{@link  org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter}从{@link  ServletRequestAttributes}创建一个{@link  NativeWebRequest}。 
 *  <p> <strong>注意：</ strong>提供该类是为了向后兼容。 
 * 但是，建议将{@code  WebArgumentResolver}重写为{@code  HandlerMethodArgumentResolver}。 
 * 有关更多详细信息，请参见{@link  org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter}的javadoc。 
 *  @author  Rossen Stoyanchev @从3.1开始
 */
public class ServletWebArgumentResolverAdapter extends AbstractWebArgumentResolverAdapter {

	public ServletWebArgumentResolverAdapter(WebArgumentResolver adaptee) {
		super(adaptee);
	}

	@Override
	protected NativeWebRequest getWebRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes instanceof ServletRequestAttributes, "No ServletRequestAttributes");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		return new ServletWebRequest(servletRequestAttributes.getRequest());
	}
}
