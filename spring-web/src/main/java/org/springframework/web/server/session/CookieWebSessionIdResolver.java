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

package org.springframework.web.server.session;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

/**
 * Cookie-based {@link WebSessionIdResolver}.
 *
 * @author Rossen Stoyanchev
 * @author Brian Clozel
 * @since 5.0
 */
/**
 * 基于Cookie的{@link  WebSessionIdResolver}。 
 *  @author 罗森·斯托扬切夫@author  Brian Clozel @5.0起
 */
public class CookieWebSessionIdResolver implements WebSessionIdResolver {

	private String cookieName = "SESSION";

	private Duration cookieMaxAge = Duration.ofSeconds(-1);

	@Nullable
	private Consumer<ResponseCookie.ResponseCookieBuilder> cookieInitializer = null;


	/**
	 * Set the name of the cookie to use for the session id.
	 * <p>By default set to "SESSION".
	 * @param cookieName the cookie name
	 */
	/**
	 * 设置用于会话ID的Cookie的名称。 
	 *  <p>默认情况下设置为"会话"。 
	 *  
	 * @param  cookieName cookie名称
	 */
	public void setCookieName(String cookieName) {
		Assert.hasText(cookieName, "'cookieName' must not be empty");
		this.cookieName = cookieName;
	}

	/**
	 * Return the configured cookie name.
	 */
	/**
	 * 返回配置的cookie名称。 
	 * 
	 */
	public String getCookieName() {
		return this.cookieName;
	}

	/**
	 * Set the value for the "Max-Age" attribute of the cookie that holds the
	 * session id. For the range of values see {@link ResponseCookie#getMaxAge()}.
	 * <p>By default set to -1.
	 * @param maxAge the maxAge duration value
	 */
	/**
	 * 为保存会话ID的cookie的"Max-Age"属性设置值。 
	 * 有关值的范围，请参见{@link  ResponseCookie＃getMaxAge（）}。 
	 *  <p>默认设置为-1。 
	 *  
	 * @param  maxAge maxAge持续时间值
	 */
	public void setCookieMaxAge(Duration maxAge) {
		this.cookieMaxAge = maxAge;
	}

	/**
	 * Return the configured "Max-Age" attribute value for the session cookie.
	 */
	/**
	 * 返回会话cookie的已配置"Max-Age"属性值。 
	 * 
	 */
	public Duration getCookieMaxAge() {
		return this.cookieMaxAge;
	}

	/**
	 * Add a {@link Consumer} for a {@code ResponseCookieBuilder} that will be invoked
	 * for each cookie being built, just before the call to {@code build()}.
	 * @param initializer consumer for a cookie builder
	 * @since 5.1
	 */
	/**
	 * 在{@code  build（）}调用之前，为{@code  ResponseCookieBuilder}添加一个{@link 使用者}，此ID将为正在构建的每个cookie调用。 
	 *  
	 * @param  Cookie生成器的初始化器使用者@始于5.1
	 */
	public void addCookieInitializer(Consumer<ResponseCookie.ResponseCookieBuilder> initializer) {
		this.cookieInitializer = this.cookieInitializer != null ?
				this.cookieInitializer.andThen(initializer) : initializer;
	}


	@Override
	public List<String> resolveSessionIds(ServerWebExchange exchange) {
		MultiValueMap<String, HttpCookie> cookieMap = exchange.getRequest().getCookies();
		List<HttpCookie> cookies = cookieMap.get(getCookieName());
		if (cookies == null) {
			return Collections.emptyList();
		}
		return cookies.stream().map(HttpCookie::getValue).collect(Collectors.toList());
	}

	@Override
	public void setSessionId(ServerWebExchange exchange, String id) {
		Assert.notNull(id, "'id' is required");
		ResponseCookie cookie = initSessionCookie(exchange, id, getCookieMaxAge());
		exchange.getResponse().getCookies().set(this.cookieName, cookie);
	}

	@Override
	public void expireSession(ServerWebExchange exchange) {
		ResponseCookie cookie = initSessionCookie(exchange, "", Duration.ZERO);
		exchange.getResponse().getCookies().set(this.cookieName, cookie);
	}

	private ResponseCookie initSessionCookie(
			ServerWebExchange exchange, String id, Duration maxAge) {

		ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(this.cookieName, id)
				.path(exchange.getRequest().getPath().contextPath().value() + "/")
				.maxAge(maxAge)
				.httpOnly(true)
				.secure("https".equalsIgnoreCase(exchange.getRequest().getURI().getScheme()))
				.sameSite("Lax");

		if (this.cookieInitializer != null) {
			this.cookieInitializer.accept(cookieBuilder);
		}

		return cookieBuilder.build();
	}

}
