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

package org.springframework.web.servlet.view.tiles3;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.tiles.locale.impl.DefaultLocaleResolver;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.NotAServletEnvironmentException;
import org.apache.tiles.request.servlet.ServletUtil;

import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Tiles LocaleResolver adapter that delegates to a Spring
 * {@link org.springframework.web.servlet.LocaleResolver}, exposing the
 * DispatcherServlet-managed locale.
 *
 * <p>This adapter gets automatically registered by {@link TilesConfigurer}.
 *
 * @author Nicolas Le Bas
 * @since 3.2
 * @see org.apache.tiles.definition.UrlDefinitionsFactory#LOCALE_RESOLVER_IMPL_PROPERTY
 */
/**
 * Tiles LocaleResolver适配器，该适配器委派给Spring {@link  org.springframework.web.servlet.LocaleResolver}，从而公开DispatcherServlet管理的语言环境。 
 *  <p>此适配器由{@link  TilesConfigurer}自动注册。 
 *  @author  Nicolas Le Bas @从3.2开始
 * @see  org.apache.tiles.definition.UrlDefinitionsFactory＃LOCALE_RESOLVER_IMPL_PROPERTY
 */
public class SpringLocaleResolver extends DefaultLocaleResolver {

	@Override
	public Locale resolveLocale(Request request) {
		try {
			HttpServletRequest servletRequest = ServletUtil.getServletRequest(request).getRequest();
			if (servletRequest != null) {
				return RequestContextUtils.getLocale(servletRequest);
			}
		}
		catch (NotAServletEnvironmentException ex) {
			// ignore
		}
		return super.resolveLocale(request);
	}

}
