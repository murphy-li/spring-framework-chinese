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

package org.springframework.web.servlet.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Resolves request paths containing a version string that can be used as part
 * of an HTTP caching strategy in which a resource is cached with a date in the
 * distant future (e.g. 1 year) and cached until the version, and therefore the
 * URL, is changed.
 *
 * <p>Different versioning strategies exist, and this resolver must be configured
 * with one or more such strategies along with path mappings to indicate which
 * strategy applies to which resources.
 *
 * <p>{@code ContentVersionStrategy} is a good default choice except in cases
 * where it cannot be used. Most notably the {@code ContentVersionStrategy}
 * cannot be combined with JavaScript module loaders. For such cases the
 * {@code FixedVersionStrategy} is a better choice.
 *
 * <p>Note that using this resolver to serve CSS files means that the
 * {@link CssLinkResourceTransformer} should also be used in order to modify
 * links within CSS files to also contain the appropriate versions generated
 * by this resolver.
 *
 * @author Brian Clozel
 * @author Rossen Stoyanchev
 * @since 4.1
 * @see VersionStrategy
 */
/**
 * 解析包含版本字符串的请求路径，该版本路径可用作HTTP缓存策略的一部分，在该HTTP缓存策略中，资源以不远的将来（例如1年）中的日期进行缓存，并一直缓存到版本和URL更改为止。 
 *  <p>存在不同的版本控制策略，并且此解析器必须配置一个或多个此类策略以及路径映射，以指示哪个策略适用于哪些资源。 
 *  <p> {<@@code> ContentVersionStrategy}是一个很好的默认选择，除非无法使用它。 
 * 最值得注意的是，{@code  ContentVersionStrategy}不能与JavaScript模块加载器结合使用。 
 * 在这种情况下，{<@code> FixedVersionStrategy}是更好的选择。 
 *  <p>请注意，使用此解析器提供CSS文件意味着还应使用{@link  CssLinkResourceTransformer}来修改CSS文件中的链接，使其也包含此解析器生成的相应版本。 
 *  @author  Brian Clozel @author  Rossen Stoyanchev @从4.1开始
 * @see  VersionStrategy
 */
public class VersionResourceResolver extends AbstractResourceResolver {

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/** Map from path pattern -> VersionStrategy. */
	/**
	 * 从路径模式映射-> VersionStrategy。 
	 * 
	 */
	private final Map<String, VersionStrategy> versionStrategyMap = new LinkedHashMap<>();


	/**
	 * Set a Map with URL paths as keys and {@code VersionStrategy} as values.
	 * <p>Supports direct URL matches and Ant-style pattern matches. For syntax
	 * details, see the {@link org.springframework.util.AntPathMatcher} javadoc.
	 * @param map map with URLs as keys and version strategies as values
	 */
	/**
	 * 将URL路径设置为键，将{@code  VersionStrategy}设置为值。 
	 *  <p>支持直接URL匹配和Ant样式模式匹配。 
	 * 有关语法的详细信息，请参见{@link  org.springframework.util.AntPathMatcher} Javadoc。 
	 *  
	 * @param 映射映射，其中URL作为键，版本策略作为值
	 */
	public void setStrategyMap(Map<String, VersionStrategy> map) {
		this.versionStrategyMap.clear();
		this.versionStrategyMap.putAll(map);
	}

	/**
	 * Return the map with version strategies keyed by path pattern.
	 */
	/**
	 * 返回带有路径策略键入版本策略的地图。 
	 * 
	 */
	public Map<String, VersionStrategy> getStrategyMap() {
		return this.versionStrategyMap;
	}

	/**
	 * Insert a content-based version in resource URLs that match the given path
	 * patterns. The version is computed from the content of the file, e.g.
	 * {@code "css/main-e36d2e05253c6c7085a91522ce43a0b4.css"}. This is a good
	 * default strategy to use except when it cannot be, for example when using
	 * JavaScript module loaders, use {@link #addFixedVersionStrategy} instead
	 * for serving JavaScript files.
	 * @param pathPatterns one or more resource URL path patterns,
	 * relative to the pattern configured with the resource handler
	 * @return the current instance for chained method invocation
	 * @see ContentVersionStrategy
	 */
	/**
	 * 在与给定路径模式匹配的资源URL中插入基于内容的版本。 
	 * 版本是根据文件的内容计算得出的，例如{@code "css / main-e36d2e05253c6c7085a91522ce43a0b4.css"}。 
	 * 这是一个很好的默认策略，除非无法使用，例如，当使用JavaScript模块加载器时，请使用{@link  #addFixedVersionStrategy}来投放JavaScript文件。 
	 *  
	 * @param  path相对于使用资源处理程序配置的模式，设置一个或多个资源URL路径模式
	 * @return 链式方法调用的当前实例
	 * @see  ContentVersionStrategy
	 */
	public VersionResourceResolver addContentVersionStrategy(String... pathPatterns) {
		addVersionStrategy(new ContentVersionStrategy(), pathPatterns);
		return this;
	}

	/**
	 * Insert a fixed, prefix-based version in resource URLs that match the given
	 * path patterns, for example: <code>"{version}/js/main.js"</code>. This is useful (vs.
	 * content-based versions) when using JavaScript module loaders.
	 * <p>The version may be a random number, the current date, or a value
	 * fetched from a git commit sha, a property file, or environment variable
	 * and set with SpEL expressions in the configuration (e.g. see {@code @Value}
	 * in Java config).
	 * <p>If not done already, variants of the given {@code pathPatterns}, prefixed with
	 * the {@code version} will be also configured. For example, adding a {@code "/js/**"} path pattern
	 * will also cofigure automatically a {@code "/v1.0.0/js/**"} with {@code "v1.0.0"} the
	 * {@code version} String given as an argument.
	 * @param version a version string
	 * @param pathPatterns one or more resource URL path patterns,
	 * relative to the pattern configured with the resource handler
	 * @return the current instance for chained method invocation
	 * @see FixedVersionStrategy
	 */
	/**
	 * 在与给定路径模式匹配的资源URL中插入基于前缀的固定版本，例如：<code>"{version} /js/main.js"</ code>。 
	 * 使用JavaScript模块加载程序时，此功能非常有用（相对于基于内容的版本）。 
	 *  <p>版本可以是随机数，当前日期，也可以是从git commit sha，属性文件或环境变量获取的值，并在配置中使用SpEL表达式进行设置（例如，请参见{@code  @Value }）。 
	 *  <p>如果尚未完成，还将配置给定的{@code  pathPatterns}的变体，其前缀为{@code  version}。 
	 * 例如，添加{@code "/ js"}路径模式也将自动将{@code "/v1.0.0/js"}与{@code "v1.0.0"}}配置为{ @code  version}作为参数给出的字符串。 
	 *  
	 * @param 版本a版本字符串
	 * @param  path相对于使用资源处理程序配置的模式，一个或多个资源URL路径模式
	 * @return 链式方法调用的当前实例
	 * @see  FixedVersionStrategy
	 */
	public VersionResourceResolver addFixedVersionStrategy(String version, String... pathPatterns) {
		List<String> patternsList = Arrays.asList(pathPatterns);
		List<String> prefixedPatterns = new ArrayList<>(pathPatterns.length);
		String versionPrefix = "/" + version;
		for (String pattern : patternsList) {
			prefixedPatterns.add(pattern);
			if (!pattern.startsWith(versionPrefix) && !patternsList.contains(versionPrefix + pattern)) {
				prefixedPatterns.add(versionPrefix + pattern);
			}
		}
		return addVersionStrategy(new FixedVersionStrategy(version), StringUtils.toStringArray(prefixedPatterns));
	}

	/**
	 * Register a custom VersionStrategy to apply to resource URLs that match the
	 * given path patterns.
	 * @param strategy the custom strategy
	 * @param pathPatterns one or more resource URL path patterns,
	 * relative to the pattern configured with the resource handler
	 * @return the current instance for chained method invocation
	 * @see VersionStrategy
	 */
	/**
	 * 注册一个自定义的VersionStrategy，以应用于与给定路径模式匹配的资源URL。 
	 *  
	 * @param 策略自定义策略
	 * @param  path相对于使用资源处理程序配置的模式，设置一个或多个资源URL路径模式
	 * @return 链式方法调用的当前实例
	 * @see  VersionStrategy
	 */
	public VersionResourceResolver addVersionStrategy(VersionStrategy strategy, String... pathPatterns) {
		for (String pattern : pathPatterns) {
			getStrategyMap().put(pattern, strategy);
		}
		return this;
	}


	@Override
	protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, String requestPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {

		Resource resolved = chain.resolveResource(request, requestPath, locations);
		if (resolved != null) {
			return resolved;
		}

		VersionStrategy versionStrategy = getStrategyForPath(requestPath);
		if (versionStrategy == null) {
			return null;
		}

		String candidateVersion = versionStrategy.extractVersion(requestPath);
		if (!StringUtils.hasLength(candidateVersion)) {
			return null;
		}

		String simplePath = versionStrategy.removeVersion(requestPath, candidateVersion);
		Resource baseResource = chain.resolveResource(request, simplePath, locations);
		if (baseResource == null) {
			return null;
		}

		String actualVersion = versionStrategy.getResourceVersion(baseResource);
		if (candidateVersion.equals(actualVersion)) {
			return new FileNameVersionedResource(baseResource, candidateVersion);
		}
		else {
			if (logger.isTraceEnabled()) {
				logger.trace("Found resource for \"" + requestPath + "\", but version [" +
						candidateVersion + "] does not match");
			}
			return null;
		}
	}

	@Override
	protected String resolveUrlPathInternal(String resourceUrlPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {

		String baseUrl = chain.resolveUrlPath(resourceUrlPath, locations);
		if (StringUtils.hasText(baseUrl)) {
			VersionStrategy versionStrategy = getStrategyForPath(resourceUrlPath);
			if (versionStrategy == null) {
				return baseUrl;
			}
			Resource resource = chain.resolveResource(null, baseUrl, locations);
			Assert.state(resource != null, "Unresolvable resource");
			String version = versionStrategy.getResourceVersion(resource);
			return versionStrategy.addVersion(baseUrl, version);
		}
		return baseUrl;
	}

	/**
	 * Find a {@code VersionStrategy} for the request path of the requested resource.
	 * @return an instance of a {@code VersionStrategy} or null if none matches that request path
	 */
	/**
	 * 查找一个{@code  VersionStrategy}作为所请求资源的请求路径。 
	 *  
	 * @return  {@code  VersionStrategy}的实例； 
	 * 如果没有一个与该请求路径匹配，则为null
	 */
	@Nullable
	protected VersionStrategy getStrategyForPath(String requestPath) {
		String path = "/".concat(requestPath);
		List<String> matchingPatterns = new ArrayList<>();
		for (String pattern : this.versionStrategyMap.keySet()) {
			if (this.pathMatcher.match(pattern, path)) {
				matchingPatterns.add(pattern);
			}
		}
		if (!matchingPatterns.isEmpty()) {
			Comparator<String> comparator = this.pathMatcher.getPatternComparator(path);
			matchingPatterns.sort(comparator);
			return this.versionStrategyMap.get(matchingPatterns.get(0));
		}
		return null;
	}


	private class FileNameVersionedResource extends AbstractResource implements HttpResource {

		private final Resource original;

		private final String version;

		public FileNameVersionedResource(Resource original, String version) {
			this.original = original;
			this.version = version;
		}

		@Override
		public boolean exists() {
			return this.original.exists();
		}

		@Override
		public boolean isReadable() {
			return this.original.isReadable();
		}

		@Override
		public boolean isOpen() {
			return this.original.isOpen();
		}

		@Override
		public boolean isFile() {
			return this.original.isFile();
		}

		@Override
		public URL getURL() throws IOException {
			return this.original.getURL();
		}

		@Override
		public URI getURI() throws IOException {
			return this.original.getURI();
		}

		@Override
		public File getFile() throws IOException {
			return this.original.getFile();
		}

		@Override
		@Nullable
		public String getFilename() {
			return this.original.getFilename();
		}

		@Override
		public long contentLength() throws IOException {
			return this.original.contentLength();
		}

		@Override
		public long lastModified() throws IOException {
			return this.original.lastModified();
		}

		@Override
		public Resource createRelative(String relativePath) throws IOException {
			return this.original.createRelative(relativePath);
		}

		@Override
		public String getDescription() {
			return this.original.getDescription();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return this.original.getInputStream();
		}

		@Override
		public HttpHeaders getResponseHeaders() {
			HttpHeaders headers = (this.original instanceof HttpResource ?
					((HttpResource) this.original).getResponseHeaders() : new HttpHeaders());
			headers.setETag("\"" + this.version + "\"");
			return headers;
		}
	}

}
