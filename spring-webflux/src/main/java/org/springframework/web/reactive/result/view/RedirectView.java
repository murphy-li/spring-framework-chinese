/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.reactive.result.view;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

/**
 * View that redirects to an absolute or context relative URL. The URL may be a
 * URI template in which case the URI template variables will be replaced with
 * values from the model or with URI variables from the current request.
 *
 * <p>By default {@link HttpStatus#SEE_OTHER} is used but alternate status codes
 * may be via constructor or setters arguments.
 *
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 重定向到绝对或上下文相关URL的视图。 
 *  URL可以是URI模板，在这种情况下，URI模板变量将被模型中的值或当前请求中的URI变量替换。 
 *  <p>默认情况下使用{@link  HttpStatus＃SEE_OTHER}，但备用状态代码可以通过构造函数或setters参数来实现。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Rossen Stoyanchev @从5.0开始
 */
public class RedirectView extends AbstractUrlBasedView {

	private static final Pattern URI_TEMPLATE_VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)}");


	private HttpStatus statusCode = HttpStatus.SEE_OTHER;

	private boolean contextRelative = true;

	private boolean propagateQuery = false;

	@Nullable
	private String[] hosts;


	/**
	 * Constructor for use as a bean.
	 */
	/**
	 * 用作bean的构造方法。 
	 * 
	 */
	public RedirectView() {
	}

	/**
	 * Create a new {@code RedirectView} with the given redirect URL.
	 * Status code {@link HttpStatus#SEE_OTHER} is used by default.
	 */
	/**
	 * 使用给定的重定向URL创建一个新的{@code  RedirectView}。 
	 * 默认情况下使用状态代码{@link  HttpStatus＃SEE_OTHER}。 
	 * 
	 */
	public RedirectView(String redirectUrl) {
		super(redirectUrl);
	}

	/**
	 * Create a new {@code RedirectView} with the given URL and an alternate
	 * redirect status code such as {@link HttpStatus#TEMPORARY_REDIRECT} or
	 * {@link HttpStatus#PERMANENT_REDIRECT}.
	 */
	/**
	 * 使用给定的URL和备用重定向状态代码（例如{@link  HttpStatus＃TEMPORARY_REDIRECT}或{@link  HttpStatus＃PERMANENT_REDIRECT}）创建一个新的{@code  RedirectView}。 
	 * 
	 */
	public RedirectView(String redirectUrl, HttpStatus statusCode) {
		super(redirectUrl);
		setStatusCode(statusCode);
	}


	/**
	 * Set an alternate redirect status code such as
	 * {@link HttpStatus#TEMPORARY_REDIRECT} or
	 * {@link HttpStatus#PERMANENT_REDIRECT}.
	 */
	/**
	 * 设置备用重定向状态代码，例如{@link  HttpStatus＃TEMPORARY_REDIRECT}或{@link  HttpStatus＃PERMANENT_REDIRECT}。 
	 * 
	 */
	public void setStatusCode(HttpStatus statusCode) {
		Assert.isTrue(statusCode.is3xxRedirection(), "Not a redirect status code");
		this.statusCode = statusCode;
	}

	/**
	 * Get the redirect status code to use.
	 */
	/**
	 * 获取要使用的重定向状态代码。 
	 * 
	 */
	public HttpStatus getStatusCode() {
		return this.statusCode;
	}

	/**
	 * Whether to interpret a given redirect URLs that starts with a slash ("/")
	 * as relative to the current context path ({@code true}, the default) or to
	 * the web server root ({@code false}).
	 */
	/**
	 * 是否将相对于当前上下文路径（默认为{@code  true}或Web服务器根目录（{@code  false}）的斜杠（"/"）开头的给定重定向URL解释为）。 
	 * 
	 */
	public void setContextRelative(boolean contextRelative) {
		this.contextRelative = contextRelative;
	}

	/**
	 * Whether to interpret URLs as relative to the current context path.
	 */
	/**
	 * 是否将URL解释为相对于当前上下文路径。 
	 * 
	 */
	public boolean isContextRelative() {
		return this.contextRelative;
	}

	/**
	 * Whether to append the query string of the current URL to the redirect URL
	 * ({@code true}) or not ({@code false}, the default).
	 */
	/**
	 * 是否将当前URL的查询字符串附加到重定向URL（{@code  true}）（缺省值{{@code  false}）之后。 
	 * 
	 */
	public void setPropagateQuery(boolean propagateQuery) {
		this.propagateQuery = propagateQuery;
	}

	/**
	 * Whether the query string of the current URL is appended to the redirect URL.
	 */
	/**
	 * 当前URL的查询字符串是否附加到重定向URL。 
	 * 
	 */
	public boolean isPropagateQuery() {
		return this.propagateQuery;
	}

	/**
	 * Configure one or more hosts associated with the application.
	 * All other hosts will be considered external hosts.
	 * <p>In effect this provides a way turn off encoding for URLs that
	 * have a host and that host is not listed as a known host.
	 * <p>If not set (the default) all redirect URLs are encoded.
	 * @param hosts one or more application hosts
	 */
	/**
	 * 配置与该应用程序关联的一台或多台主机。 
	 * 所有其他主机将被视为外部主机。 
	 *  <p>实际上，这提供了一种关闭具有主机且未将该主机列为已知主机的URL编码的方法。 
	 *  <p>如果未设置（默认设置），则所有重定向URL均已编码。 
	 *  
	 * @param 托管一个或多个应用程序主机
	 */
	public void setHosts(@Nullable String... hosts) {
		this.hosts = hosts;
	}

	/**
	 * Return the configured application hosts.
	 */
	/**
	 * 返回配置的应用程序主机。 
	 * 
	 */
	@Nullable
	public String[] getHosts() {
		return this.hosts;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}


	@Override
	public boolean isRedirectView() {
		return true;
	}

	@Override
	public boolean checkResourceExists(Locale locale) throws Exception {
		return true;
	}

	/**
	 * Convert model to request parameters and redirect to the given URL.
	 */
	/**
	 * 将模型转换为请求参数，然后重定向到给定的URL。 
	 * 
	 */
	@Override
	protected Mono<Void> renderInternal(
			Map<String, Object> model, @Nullable MediaType contentType, ServerWebExchange exchange) {

		String targetUrl = createTargetUrl(model, exchange);
		return sendRedirect(targetUrl, exchange);
	}

	/**
	 * Create the target URL and, if necessary, pre-pend the contextPath, expand
	 * URI template variables, append the current request query, and apply the
	 * configured {@link #getRequestDataValueProcessor()
	 * RequestDataValueProcessor}.
	 */
	/**
	 * 创建目标URL，并在必要时添加contextPath，展开URI模板变量，附加当前请求查询，然后应用配置的{@link  #getRequestDataValueProcessor（）RequestDataValueProcessor}。 
	 * 
	 */
	protected final String createTargetUrl(Map<String, Object> model, ServerWebExchange exchange) {
		String url = getUrl();
		Assert.state(url != null, "'url' not set");

		ServerHttpRequest request = exchange.getRequest();

		StringBuilder targetUrl = new StringBuilder();
		if (isContextRelative() && url.startsWith("/")) {
			targetUrl.append(request.getPath().contextPath().value());
		}
		targetUrl.append(url);

		if (StringUtils.hasText(targetUrl)) {
			Map<String, String> uriVars = getCurrentUriVariables(exchange);
			targetUrl = expandTargetUrlTemplate(targetUrl.toString(), model, uriVars);
		}

		if (isPropagateQuery()) {
			targetUrl = appendCurrentRequestQuery(targetUrl.toString(), request);
		}

		String result = targetUrl.toString();

		RequestDataValueProcessor processor = getRequestDataValueProcessor();
		return (processor != null ? processor.processUrl(exchange, result) : result);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getCurrentUriVariables(ServerWebExchange exchange) {
		String name = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
		return exchange.getAttributeOrDefault(name, Collections.<String, String>emptyMap());
	}

	/**
	 * Expand URI template variables in the target URL with either model
	 * attribute values or as a fallback with URI variable values from the
	 * current request. Values are encoded.
	 */
	/**
	 * 使用模型属性值或使用当前请求中的URI变量值作为后备，在目标URL中扩展URI模板变量。 
	 * 值被编码。 
	 * 
	 */
	protected StringBuilder expandTargetUrlTemplate(String targetUrl,
			Map<String, Object> model, Map<String, String> uriVariables) {

		Matcher matcher = URI_TEMPLATE_VARIABLE_PATTERN.matcher(targetUrl);
		boolean found = matcher.find();
		if (!found) {
			return new StringBuilder(targetUrl);
		}
		StringBuilder result = new StringBuilder();
		int endLastMatch = 0;
		while (found) {
			String name = matcher.group(1);
			Object value = (model.containsKey(name) ? model.get(name) : uriVariables.get(name));
			Assert.notNull(value, () -> "No value for URI variable '" + name + "'");
			result.append(targetUrl.substring(endLastMatch, matcher.start()));
			result.append(encodeUriVariable(value.toString()));
			endLastMatch = matcher.end();
			found = matcher.find();
		}
		result.append(targetUrl.substring(endLastMatch, targetUrl.length()));
		return result;
	}

	private String encodeUriVariable(String text) {
		// Strict encoding of all reserved URI characters
		return UriUtils.encode(text, StandardCharsets.UTF_8);
	}

	/**
	 * Append the query of the current request to the target redirect URL.
	 */
	/**
	 * 将当前请求的查询追加到目标重定向URL。 
	 * 
	 */
	protected StringBuilder appendCurrentRequestQuery(String targetUrl, ServerHttpRequest request) {
		String query = request.getURI().getRawQuery();
		if (!StringUtils.hasText(query)) {
			return new StringBuilder(targetUrl);
		}

		int index = targetUrl.indexOf('#');
		String fragment = (index > -1 ? targetUrl.substring(index) : null);

		StringBuilder result = new StringBuilder();
		result.append(index != -1 ? targetUrl.substring(0, index) : targetUrl);
		result.append(targetUrl.indexOf('?') < 0 ? '?' : '&').append(query);

		if (fragment != null) {
			result.append(fragment);
		}

		return result;
	}

	/**
	 * Send a redirect back to the HTTP client.
	 * @param targetUrl the target URL to redirect to
	 * @param exchange current exchange
	 */
	/**
	 * 将重定向发送回HTTP客户端。 
	 *  
	 * @param  targetUrl目标URL重定向到
	 * @param 交流当前交流
	 */
	protected Mono<Void> sendRedirect(String targetUrl, ServerWebExchange exchange) {
		String transformedUrl = (isRemoteHost(targetUrl) ? targetUrl : exchange.transformUrl(targetUrl));
		ServerHttpResponse response = exchange.getResponse();
		response.getHeaders().setLocation(URI.create(transformedUrl));
		response.setStatusCode(getStatusCode());
		return Mono.empty();
	}

	/**
	 * Whether the given targetUrl has a host that is a "foreign" system in which
	 * case {@link javax.servlet.http.HttpServletResponse#encodeRedirectURL} will not be applied.
	 * This method returns {@code true} if the {@link #setHosts(String[])}
	 * property is configured and the target URL has a host that does not match.
	 * @param targetUrl the target redirect URL
	 * @return {@code true} the target URL has a remote host, {@code false} if it
	 * the URL does not have a host or the "host" property is not configured.
	 */
	/**
	 * 给定的targetUrl是否具有作为"外部"系统的主机，在这种情况下，将不应用{@link  javax.servlet.http.HttpServletResponse＃encodeRedirectURL}。 
	 * 如果配置了{@link  #setHosts（String []）}属性，并且目标URL的主机不匹配，则此方法返回{@code  true}。 
	 *  
	 * @param  targetUrl目标重定向URL 
	 * @return  {@code  true}目标URL具有远程主机，如果URL不具有主机或"host"属性，则为{@code  false}未配置。 
	 * 
	 */
	protected boolean isRemoteHost(String targetUrl) {
		if (ObjectUtils.isEmpty(this.hosts)) {
			return false;
		}
		String targetHost = UriComponentsBuilder.fromUriString(targetUrl).build().getHost();
		if (!StringUtils.hasLength(targetHost)) {
			return false;
		}
		for (String host : this.hosts) {
			if (targetHost.equals(host)) {
				return false;
			}
		}
		return true;
	}

}
