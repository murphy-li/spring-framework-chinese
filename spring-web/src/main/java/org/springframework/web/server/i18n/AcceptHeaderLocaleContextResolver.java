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

package org.springframework.web.server.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * {@link LocaleContextResolver} implementation that simply uses the primary locale
 * specified in the "Accept-Language" header of the HTTP request (that is,
 * the locale sent by the client browser, normally that of the client's OS).
 *
 * <p>Note: Does not support {@link #setLocaleContext}, since the accept header
 * can only be changed through changing the client's locale settings.
 *
 * @author Sebastien Deleuze
 * @author Juergen Hoeller
 * @since 5.0
 */
/**
 * {@link  LocaleContextResolver}实现，仅使用HTTP请求的"Accept-Language"标头中指定的主要语言环境（即，客户端浏览器发送的语言环境，通常是客户端操作系统的语言环境）。 
 *  <p>注意：不支持{@link  #setLocaleContext}，因为只能通过更改客户端的语言环境设置来更改accept标头。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Juergen Hoeller @从5.0开始
 */
public class AcceptHeaderLocaleContextResolver implements LocaleContextResolver {

	private final List<Locale> supportedLocales = new ArrayList<>(4);

	@Nullable
	private Locale defaultLocale;


	/**
	 * Configure supported locales to check against the requested locales
	 * determined via {@link HttpHeaders#getAcceptLanguageAsLocales()}.
	 * @param locales the supported locales
	 */
	/**
	 * 配置受支持的语言环境，以检查通过{@link  HttpHeaders＃getAcceptLanguageAsLocales（）}确定的请求语言环境。 
	 *  
	 * @param 语言环境受支持的语言环境
	 */
	public void setSupportedLocales(List<Locale> locales) {
		this.supportedLocales.clear();
		this.supportedLocales.addAll(locales);
	}

	/**
	 * Return the configured list of supported locales.
	 */
	/**
	 * 返回支持的语言环境的配置列表。 
	 * 
	 */
	public List<Locale> getSupportedLocales() {
		return this.supportedLocales;
	}

	/**
	 * Configure a fixed default locale to fall back on if the request does not
	 * have an "Accept-Language" header (not set by default).
	 * @param defaultLocale the default locale to use
	 */
	/**
	 * 如果请求没有"Accept-Language"标头（默认情况下未设置），则将固定的默认语言环境配置为重新启用。 
	 *  
	 * @param  defaultLocale要使用的默认语言环境
	 */
	public void setDefaultLocale(@Nullable Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	/**
	 * The configured default locale, if any.
	 */
	/**
	 * 配置的默认语言环境（如果有）。 
	 * 
	 */
	@Nullable
	public Locale getDefaultLocale() {
		return this.defaultLocale;
	}


	@Override
	public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
		List<Locale> requestLocales = null;
		try {
			requestLocales = exchange.getRequest().getHeaders().getAcceptLanguageAsLocales();
		}
		catch (IllegalArgumentException ex) {
			// Invalid Accept-Language header: treat as empty for matching purposes
		}
		return new SimpleLocaleContext(resolveSupportedLocale(requestLocales));
	}

	@Nullable
	private Locale resolveSupportedLocale(@Nullable List<Locale> requestLocales) {
		if (CollectionUtils.isEmpty(requestLocales)) {
			return this.defaultLocale;  // may be null
		}
		List<Locale> supportedLocales = getSupportedLocales();
		if (supportedLocales.isEmpty()) {
			return requestLocales.get(0);  // never null
		}

		Locale languageMatch = null;
		for (Locale locale : requestLocales) {
			if (supportedLocales.contains(locale)) {
				if (languageMatch == null || languageMatch.getLanguage().equals(locale.getLanguage())) {
					// Full match: language + country, possibly narrowed from earlier language-only match
					return locale;
				}
			}
			else if (languageMatch == null) {
				// Let's try to find a language-only match as a fallback
				for (Locale candidate : supportedLocales) {
					if (!StringUtils.hasLength(candidate.getCountry()) &&
							candidate.getLanguage().equals(locale.getLanguage())) {
						languageMatch = candidate;
						break;
					}
				}
			}
		}
		if (languageMatch != null) {
			return languageMatch;
		}

		return (this.defaultLocale != null ? this.defaultLocale : requestLocales.get(0));
	}

	@Override
	public void setLocaleContext(ServerWebExchange exchange, @Nullable LocaleContext locale) {
		throw new UnsupportedOperationException(
				"Cannot change HTTP accept header - use a different locale context resolution strategy");
	}

}
