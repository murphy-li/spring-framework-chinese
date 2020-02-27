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

package org.springframework.web.servlet.resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@code HttpRequestHandler} that serves static resources in an optimized way
 * according to the guidelines of Page Speed, YSlow, etc.
 *
 * <p>The {@linkplain #setLocations "locations"} property takes a list of Spring
 * {@link Resource} locations from which static resources are allowed to be served
 * by this handler. Resources could be served from a classpath location, e.g.
 * "classpath:/META-INF/public-web-resources/", allowing convenient packaging
 * and serving of resources such as .js, .css, and others in jar files.
 *
 * <p>This request handler may also be configured with a
 * {@link #setResourceResolvers(List) resourcesResolver} and
 * {@link #setResourceTransformers(List) resourceTransformer} chains to support
 * arbitrary resolution and transformation of resources being served. By default
 * a {@link PathResourceResolver} simply finds resources based on the configured
 * "locations". An application can configure additional resolvers and transformers
 * such as the {@link VersionResourceResolver} which can resolve and prepare URLs
 * for resources with a version in the URL.
 *
 * <p>This handler also properly evaluates the {@code Last-Modified} header
 * (if present) so that a {@code 304} status code will be returned as appropriate,
 * avoiding unnecessary overhead for resources that are already cached by the client.
 *
 * @author Keith Donald
 * @author Jeremy Grelle
 * @author Juergen Hoeller
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @author Rossen Stoyanchev
 * @since 3.0.4
 */
/**
 * {@code  HttpRequestHandler}根据Page Speed，YSlow等的准则以优化的方式提供静态资源。 
 * <p> {<@link> plain #setLocations"locations"}属性采用Spring { @link  Resource}位置，此处理程序允许从这些位置提供静态资源。 
 * 可以从类路径位置提供资源，例如"classpath：/ META-INF / public-web-resources /"，允许在jar文件中方便地打包和提供资源，例如.js，.css和其他资源。 
 *  <p>此请求处理程序还可使用{@link  #setResourceResolvers（List）resourcesResolver}和{@link  #setResourceTransformers（List）resourceTransformer}链进行配置，以支持任意解析和所服务资源的转换。 
 * 默认情况下，{<@link> PathResourceResolver}仅基于配置的"位置"查找资源。 
 * 应用程序可以配置其他解析器和转换器，例如{@link  VersionResourceResolver}，它们可以使用资源中的版本来解析和准备资源的URL。 
 *  <p>此处理程序还可以正确评估{@code  Last-Modified}标头（如果存在），以便适当地返回{@code  304}状态码，从而避免了已经缓存的资源的不必要开销由客户。 
 *  @author 基思·唐纳德@author 杰里米·格莱尔@author  Juergen Hoeller @author  Arjen Poutsma @author  Brian Clozel @author  Rossen Stoyanchev @3.0.4起
 */
public class ResourceHttpRequestHandler extends WebContentGenerator
		implements HttpRequestHandler, EmbeddedValueResolverAware, InitializingBean, CorsConfigurationSource {

	private static final Log logger = LogFactory.getLog(ResourceHttpRequestHandler.class);

	private static final String URL_RESOURCE_CHARSET_PREFIX = "[charset=";


	private final List<String> locationValues = new ArrayList<>(4);

	private final List<Resource> locations = new ArrayList<>(4);

	private final Map<Resource, Charset> locationCharsets = new HashMap<>(4);

	private final List<ResourceResolver> resourceResolvers = new ArrayList<>(4);

	private final List<ResourceTransformer> resourceTransformers = new ArrayList<>(4);

	@Nullable
	private ResourceResolverChain resolverChain;

	@Nullable
	private ResourceTransformerChain transformerChain;

	@Nullable
	private ResourceHttpMessageConverter resourceHttpMessageConverter;

	@Nullable
	private ResourceRegionHttpMessageConverter resourceRegionHttpMessageConverter;

	@Nullable
	private ContentNegotiationManager contentNegotiationManager;

	private final Map<String, MediaType> mediaTypes = new HashMap<>(4);

	@Nullable
	private CorsConfiguration corsConfiguration;

	@Nullable
	private UrlPathHelper urlPathHelper;

	@Nullable
	private StringValueResolver embeddedValueResolver;


	public ResourceHttpRequestHandler() {
		super(HttpMethod.GET.name(), HttpMethod.HEAD.name());
	}


	/**
	 * An alternative to {@link #setLocations(List)} that accepts a list of
	 * String-based location values, with support for {@link UrlResource}'s
	 * (e.g. files or HTTP URLs) with a special prefix to indicate the charset
	 * to use when appending relative paths. For example
	 * {@code "[charset=Windows-31J]https://example.org/path"}.
	 * @since 4.3.13
	 */
	/**
	 * {@link  #setLocations（List）}的替代方法，它接受基于字符串的位置值列表，并支持带有特殊前缀的{@link  UrlResource}（例如文件或HTTP URL）以指示附加相对路径时要使用的字符集。 
	 * 例如{@code "[charset = Windows-31J] https://example.org/path"}。 
	 *  @自4.3.13起
	 */
	public void setLocationValues(List<String> locationValues) {
		Assert.notNull(locationValues, "Location values list must not be null");
		this.locationValues.clear();
		this.locationValues.addAll(locationValues);
	}

	/**
	 * Set the {@code List} of {@code Resource} locations to use as sources
	 * for serving static resources.
	 * @see #setLocationValues(List)
	 */
	/**
	 * 设置{@code  Resource}个位置的{@code  List}用作服务静态资源的源。 
	 *  
	 * @see  #setLocationValues（列表）
	 */
	public void setLocations(List<Resource> locations) {
		Assert.notNull(locations, "Locations list must not be null");
		this.locations.clear();
		this.locations.addAll(locations);
	}

	/**
	 * Return the configured {@code List} of {@code Resource} locations.
	 * <p>Note that if {@link #setLocationValues(List) locationValues} are provided,
	 * instead of loaded Resource-based locations, this method will return
	 * empty until after initialization via {@link #afterPropertiesSet()}.
	 * @see #setLocationValues
	 * @see #setLocations
	 */
	/**
	 * 返回已配置的{@code  Resource}个位置的{@code  List}。 
	 *  <p>请注意，如果提供了{@link  #setLocationValues（List）locationValues}，而不是加载的基于资源的位置，则此方法将返回空，直到通过{@link  #afterPropertiesSet（）}初始化之后。 
	 *  
	 * @see  #setLocationValues 
	 * @see  #setLocations
	 */
	public List<Resource> getLocations() {
		return this.locations;
	}

	/**
	 * Configure the list of {@link ResourceResolver ResourceResolvers} to use.
	 * <p>By default {@link PathResourceResolver} is configured. If using this property,
	 * it is recommended to add {@link PathResourceResolver} as the last resolver.
	 */
	/**
	 * 配置要使用的{@link  ResourceResolver ResourceResolvers}列表。 
	 *  <p>默认情况下，已配置{@link  PathResourceResolver}。 
	 * 如果使用此属性，建议添加{@link  PathResourceResolver}作为最后一个解析器。 
	 * 
	 */
	public void setResourceResolvers(@Nullable List<ResourceResolver> resourceResolvers) {
		this.resourceResolvers.clear();
		if (resourceResolvers != null) {
			this.resourceResolvers.addAll(resourceResolvers);
		}
	}

	/**
	 * Return the list of configured resource resolvers.
	 */
	/**
	 * 返回已配置资源解析器的列表。 
	 * 
	 */
	public List<ResourceResolver> getResourceResolvers() {
		return this.resourceResolvers;
	}

	/**
	 * Configure the list of {@link ResourceTransformer ResourceTransformers} to use.
	 * <p>By default no transformers are configured for use.
	 */
	/**
	 * 配置要使用的{@link  ResourceTransformer ResourceTransformers}列表。 
	 *  <p>默认情况下，未配置任何变压器供使用。 
	 * 
	 */
	public void setResourceTransformers(@Nullable List<ResourceTransformer> resourceTransformers) {
		this.resourceTransformers.clear();
		if (resourceTransformers != null) {
			this.resourceTransformers.addAll(resourceTransformers);
		}
	}

	/**
	 * Return the list of configured resource transformers.
	 */
	/**
	 * 返回已配置资源转换器的列表。 
	 * 
	 */
	public List<ResourceTransformer> getResourceTransformers() {
		return this.resourceTransformers;
	}

	/**
	 * Configure the {@link ResourceHttpMessageConverter} to use.
	 * <p>By default a {@link ResourceHttpMessageConverter} will be configured.
	 * @since 4.3
	 */
	/**
	 * 配置{@link  ResourceHttpMessageConverter}以使用。 
	 *  <p>默认情况下，将配置{@link  ResourceHttpMessageConverter}。 
	 *  @4.3起
	 */
	public void setResourceHttpMessageConverter(@Nullable ResourceHttpMessageConverter messageConverter) {
		this.resourceHttpMessageConverter = messageConverter;
	}

	/**
	 * Return the configured resource converter.
	 * @since 4.3
	 */
	/**
	 * 返回配置的资源转换器。 
	 *  @4.3起
	 */
	@Nullable
	public ResourceHttpMessageConverter getResourceHttpMessageConverter() {
		return this.resourceHttpMessageConverter;
	}

	/**
	 * Configure the {@link ResourceRegionHttpMessageConverter} to use.
	 * <p>By default a {@link ResourceRegionHttpMessageConverter} will be configured.
	 * @since 4.3
	 */
	/**
	 * 配置{@link  ResourceRegionHttpMessageConverter}以使用。 
	 *  <p>默认情况下，将配置{@link  ResourceRegionHttpMessageConverter}。 
	 *  @4.3起
	 */
	public void setResourceRegionHttpMessageConverter(@Nullable ResourceRegionHttpMessageConverter messageConverter) {
		this.resourceRegionHttpMessageConverter = messageConverter;
	}

	/**
	 * Return the configured resource region converter.
	 * @since 4.3
	 */
	/**
	 * 返回配置的资源区域转换器。 
	 *  @4.3起
	 */
	@Nullable
	public ResourceRegionHttpMessageConverter getResourceRegionHttpMessageConverter() {
		return this.resourceRegionHttpMessageConverter;
	}

	/**
	 * Configure a {@code ContentNegotiationManager} to help determine the
	 * media types for resources being served. If the manager contains a path
	 * extension strategy it will be checked for registered file extension.
	 * @since 4.3
	 * @deprecated as of 5.2.4 in favor of using {@link #setMediaTypes(Map)}
	 * with mappings possibly obtained from
	 * {@link ContentNegotiationManager#getMediaTypeMappings()}.
	 */
	/**
	 * 配置{@code  ContentNegotiationManager}以帮助确定所服务资源的媒体类型。 
	 * 如果管理器包含路径扩展策略，则将检查其注册文件扩展名。 
	 *  @since 4.3从5.2.4版本开始不推荐使用，支持使用{@link  #setMediaTypes（Map）}和可能从{@link  ContentNegotiationManager＃getMediaTypeMappings（）}获得的映射。 
	 * 
	 */
	@Deprecated
	public void setContentNegotiationManager(@Nullable ContentNegotiationManager contentNegotiationManager) {
		this.contentNegotiationManager = contentNegotiationManager;
	}

	/**
	 * Return the configured content negotiation manager.
	 * @since 4.3
	 * @deprecated as of 5.2.4.
	 */
	/**
	 * 返回配置的内容协商管理器。 
	 * 自4.3起，自5.2.4起不推荐使用。 
	 * 
	 */
	@Nullable
	@Deprecated
	public ContentNegotiationManager getContentNegotiationManager() {
		return this.contentNegotiationManager;
	}

	/**
	 * Add mappings between file extensions, extracted from the filename of a
	 * static {@link Resource}, and corresponding media type  to set on the
	 * response.
	 * <p>Use of this method is typically not necessary since mappings are
	 * otherwise determined via
	 * {@link javax.servlet.ServletContext#getMimeType(String)} or via
	 * {@link MediaTypeFactory#getMediaType(Resource)}.
	 * @param mediaTypes media type mappings
	 * @since 5.2.4
	 */
	/**
	 * 在从静态{@link 资源}的文件名中提取的文件扩展名和要在响应中设置的相应媒体类型之间添加映射。 
	 *  <p>通常不需要使用此方法，因为否则将通过{@link  javax.servlet.ServletContext＃getMimeType（String）}或通过{@link  MediaTypeFactory＃getMediaType（Resource）}确定映射。 
	 *  
	 * @param  mediaTypes媒体类型映射@自5.2.4起
	 */
	public void setMediaTypes(Map<String, MediaType> mediaTypes) {
		mediaTypes.forEach((ext, mediaType) ->
				this.mediaTypes.put(ext.toLowerCase(Locale.ENGLISH), mediaType));
	}

	/**
	 * Return the {@link #setMediaTypes(Map) configured} media types.
	 * @since 5.2.4
	 */
	/**
	 * 返回{@link  #setMediaTypes（Map）配置}媒体类型。 
	 *  @从5.2.4开始
	 */
	public Map<String, MediaType> getMediaTypes() {
		return this.mediaTypes;
	}

	/**
	 * Specify the CORS configuration for resources served by this handler.
	 * <p>By default this is not set in which allows cross-origin requests.
	 */
	/**
	 * 为该处理程序服务的资源指定CORS配置。 
	 *  <p>默认情况下，未设置允许跨域请求。 
	 * 
	 */
	public void setCorsConfiguration(CorsConfiguration corsConfiguration) {
		this.corsConfiguration = corsConfiguration;
	}

	/**
	 * Return the specified CORS configuration.
	 */
	/**
	 * 返回指定的CORS配置。 
	 * 
	 */
	@Override
	@Nullable
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		return this.corsConfiguration;
	}

	/**
	 * Provide a reference to the {@link UrlPathHelper} used to map requests to
	 * static resources. This helps to derive information about the lookup path
	 * such as whether it is decoded or not.
	 * @since 4.3.13
	 */
	/**
	 * 提供对用于将请求映射到静态资源的{@link  UrlPathHelper}的引用。 
	 * 这有助于获得有关查找路径的信息，例如是否解码。 
	 *  @自4.3.13起
	 */
	public void setUrlPathHelper(@Nullable UrlPathHelper urlPathHelper) {
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * The configured {@link UrlPathHelper}.
	 * @since 4.3.13
	 */
	/**
	 * 配置的{@link  UrlPathHelper}。 
	 *  @自4.3.13起
	 */
	@Nullable
	public UrlPathHelper getUrlPathHelper() {
		return this.urlPathHelper;
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver = resolver;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		resolveResourceLocations();

		if (logger.isWarnEnabled() && CollectionUtils.isEmpty(this.locations)) {
			logger.warn("Locations list is empty. No resources will be served unless a " +
					"custom ResourceResolver is configured as an alternative to PathResourceResolver.");
		}

		if (this.resourceResolvers.isEmpty()) {
			this.resourceResolvers.add(new PathResourceResolver());
		}

		initAllowedLocations();

		// Initialize immutable resolver and transformer chains
		this.resolverChain = new DefaultResourceResolverChain(this.resourceResolvers);
		this.transformerChain = new DefaultResourceTransformerChain(this.resolverChain, this.resourceTransformers);

		if (this.resourceHttpMessageConverter == null) {
			this.resourceHttpMessageConverter = new ResourceHttpMessageConverter();
		}
		if (this.resourceRegionHttpMessageConverter == null) {
			this.resourceRegionHttpMessageConverter = new ResourceRegionHttpMessageConverter();
		}

		ContentNegotiationManager manager = getContentNegotiationManager();
		if (manager != null) {
			setMediaTypes(manager.getMediaTypeMappings());
		}

		@SuppressWarnings("deprecation")
		org.springframework.web.accept.PathExtensionContentNegotiationStrategy strategy =
				initContentNegotiationStrategy();
		if (strategy != null) {
			setMediaTypes(strategy.getMediaTypes());
		}
	}

	private void resolveResourceLocations() {
		if (CollectionUtils.isEmpty(this.locationValues)) {
			return;
		}
		else if (!CollectionUtils.isEmpty(this.locations)) {
			throw new IllegalArgumentException("Please set either Resource-based \"locations\" or " +
					"String-based \"locationValues\", but not both.");
		}

		ApplicationContext applicationContext = obtainApplicationContext();
		for (String location : this.locationValues) {
			if (this.embeddedValueResolver != null) {
				String resolvedLocation = this.embeddedValueResolver.resolveStringValue(location);
				if (resolvedLocation == null) {
					throw new IllegalArgumentException("Location resolved to null: " + location);
				}
				location = resolvedLocation;
			}
			Charset charset = null;
			location = location.trim();
			if (location.startsWith(URL_RESOURCE_CHARSET_PREFIX)) {
				int endIndex = location.indexOf(']', URL_RESOURCE_CHARSET_PREFIX.length());
				if (endIndex == -1) {
					throw new IllegalArgumentException("Invalid charset syntax in location: " + location);
				}
				String value = location.substring(URL_RESOURCE_CHARSET_PREFIX.length(), endIndex);
				charset = Charset.forName(value);
				location = location.substring(endIndex + 1);
			}
			Resource resource = applicationContext.getResource(location);
			this.locations.add(resource);
			if (charset != null) {
				if (!(resource instanceof UrlResource)) {
					throw new IllegalArgumentException("Unexpected charset for non-UrlResource: " + resource);
				}
				this.locationCharsets.put(resource, charset);
			}
		}
	}

	/**
	 * Look for a {@code PathResourceResolver} among the configured resource
	 * resolvers and set its {@code allowedLocations} property (if empty) to
	 * match the {@link #setLocations locations} configured on this class.
	 */
	/**
	 * 在已配置的资源解析器中查找{@code  PathResourceResolver}，并设置其{@code  allowedLocations}属性（如果为空）以匹配在该类上配置的{@link  #setLocations locations}。 
	 * 
	 */
	protected void initAllowedLocations() {
		if (CollectionUtils.isEmpty(this.locations)) {
			return;
		}
		for (int i = getResourceResolvers().size() - 1; i >= 0; i--) {
			if (getResourceResolvers().get(i) instanceof PathResourceResolver) {
				PathResourceResolver pathResolver = (PathResourceResolver) getResourceResolvers().get(i);
				if (ObjectUtils.isEmpty(pathResolver.getAllowedLocations())) {
					pathResolver.setAllowedLocations(getLocations().toArray(new Resource[0]));
				}
				if (this.urlPathHelper != null) {
					pathResolver.setLocationCharsets(this.locationCharsets);
					pathResolver.setUrlPathHelper(this.urlPathHelper);
				}
				break;
			}
		}
	}

	/**
	 * Initialize the strategy to use to determine the media type for a resource.
	 * @deprecated as of 5.2.4 this method returns {@code null}, and if a
	 * sub-class returns an actual instance,the instance is used only as a
	 * source of media type mappings, if it contains any. Please, use
	 * {@link #setMediaTypes(Map)} instead, or if you need to change behavior,
	 * you can override {@link #getMediaType(HttpServletRequest, Resource)}.
	 */
	/**
	 * 初始化用于确定资源的媒体类型的策略。 
	 * 从5.2.4版本开始，不推荐使用@，此方法返回{@code  null}，如果子类返回实际实例，则该实例仅用作媒体类型映射的来源（如果包含）。 
	 * 请改用{@link  #setMediaTypes（Map）}，或者如果您需要更改行为，则可以覆盖{@link  #getMediaType（HttpServletRequest，Resource）}。 
	 * 
	 */
	@Nullable
	@Deprecated
	@SuppressWarnings("deprecation")
	protected org.springframework.web.accept.PathExtensionContentNegotiationStrategy initContentNegotiationStrategy() {
		return null;
	}

	/**
	 * Processes a resource request.
	 * <p>Checks for the existence of the requested resource in the configured list of locations.
	 * If the resource does not exist, a {@code 404} response will be returned to the client.
	 * If the resource exists, the request will be checked for the presence of the
	 * {@code Last-Modified} header, and its value will be compared against the last-modified
	 * timestamp of the given resource, returning a {@code 304} status code if the
	 * {@code Last-Modified} value  is greater. If the resource is newer than the
	 * {@code Last-Modified} value, or the header is not present, the content resource
	 * of the resource will be written to the response with caching headers
	 * set to expire one year in the future.
	 */
	/**
	 * 处理资源请求。 
	 *  <p>在配置的位置列表中检查请求的资源是否存在。 
	 * 如果资源不存在，则将向客户端返回{@code  404}响应。 
	 * 如果资源存在，将检查请求中是否存在{@code  Last-Modified}标头，并将其值与给定资源的最后修改的时间戳进行比较，并返回{@code  304}状态代码（如果{@code  Last-Modified}值较大）。 
	 * 如果资源比{@code  Last-Modified}值新，或者不存在标头，则该资源的内容资源将被写入响应，且标头设置为在一年后过期。 
	 * 
	 */
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// For very general mappings (e.g. "/") we need to check 404 first
		Resource resource = getResource(request);
		if (resource == null) {
			logger.debug("Resource not found");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			response.setHeader("Allow", getAllowHeader());
			return;
		}

		// Supported methods and required session
		checkRequest(request);

		// Header phase
		if (new ServletWebRequest(request, response).checkNotModified(resource.lastModified())) {
			logger.trace("Resource not modified");
			return;
		}

		// Apply cache settings, if any
		prepareResponse(response);

		// Check the media type for the resource
		MediaType mediaType = getMediaType(request, resource);

		// Content phase
		if (METHOD_HEAD.equals(request.getMethod())) {
			setHeaders(response, resource, mediaType);
			return;
		}

		ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
		if (request.getHeader(HttpHeaders.RANGE) == null) {
			Assert.state(this.resourceHttpMessageConverter != null, "Not initialized");
			setHeaders(response, resource, mediaType);
			this.resourceHttpMessageConverter.write(resource, mediaType, outputMessage);
		}
		else {
			Assert.state(this.resourceRegionHttpMessageConverter != null, "Not initialized");
			response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
			ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);
			try {
				List<HttpRange> httpRanges = inputMessage.getHeaders().getRange();
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				this.resourceRegionHttpMessageConverter.write(
						HttpRange.toResourceRegions(httpRanges, resource), mediaType, outputMessage);
			}
			catch (IllegalArgumentException ex) {
				response.setHeader("Content-Range", "bytes */" + resource.contentLength());
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			}
		}
	}

	@Nullable
	protected Resource getResource(HttpServletRequest request) throws IOException {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (path == null) {
			throw new IllegalStateException("Required request attribute '" +
					HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE + "' is not set");
		}

		path = processPath(path);
		if (!StringUtils.hasText(path) || isInvalidPath(path)) {
			return null;
		}
		if (isInvalidEncodedPath(path)) {
			return null;
		}

		Assert.notNull(this.resolverChain, "ResourceResolverChain not initialized.");
		Assert.notNull(this.transformerChain, "ResourceTransformerChain not initialized.");

		Resource resource = this.resolverChain.resolveResource(request, path, getLocations());
		if (resource != null) {
			resource = this.transformerChain.transform(request, resource);
		}
		return resource;
	}

	/**
	 * Process the given resource path.
	 * <p>The default implementation replaces:
	 * <ul>
	 * <li>Backslash with forward slash.
	 * <li>Duplicate occurrences of slash with a single slash.
	 * <li>Any combination of leading slash and control characters (00-1F and 7F)
	 * with a single "/" or "". For example {@code "  / // foo/bar"}
	 * becomes {@code "/foo/bar"}.
	 * </ul>
	 * @since 3.2.12
	 */
	/**
	 * 处理给定的资源路径。 
	 *  <p>默认实现替换为：<ul> <li>反斜杠带有正斜杠。 
	 *  <li>使用单个斜杠重复出现斜杠。 
	 *  <li>前导斜杠和控制字符（00-1F和7F）与单个"/"或""的任何组合。 
	 * 例如，{@code "/ // foo / bar"}变为{@code "/ foo / bar"}。 
	 *  </ ul> @3.2.12起
	 */
	protected String processPath(String path) {
		path = StringUtils.replace(path, "\\", "/");
		path = cleanDuplicateSlashes(path);
		return cleanLeadingSlash(path);
	}

	private String cleanDuplicateSlashes(String path) {
		StringBuilder sb = null;
		char prev = 0;
		for (int i = 0; i < path.length(); i++) {
			char curr = path.charAt(i);
			try {
				if ((curr == '/') && (prev == '/')) {
					if (sb == null) {
						sb = new StringBuilder(path.substring(0, i));
					}
					continue;
				}
				if (sb != null) {
					sb.append(path.charAt(i));
				}
			}
			finally {
				prev = curr;
			}
		}
		return sb != null ? sb.toString() : path;
	}

	private String cleanLeadingSlash(String path) {
		boolean slash = false;
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				slash = true;
			}
			else if (path.charAt(i) > ' ' && path.charAt(i) != 127) {
				if (i == 0 || (i == 1 && slash)) {
					return path;
				}
				return (slash ? "/" + path.substring(i) : path.substring(i));
			}
		}
		return (slash ? "/" : "");
	}

	/**
	 * Check whether the given path contains invalid escape sequences.
	 * @param path the path to validate
	 * @return {@code true} if the path is invalid, {@code false} otherwise
	 */
	/**
	 * 检查给定路径是否包含无效的转义序列。 
	 *  
	 * @param 路径验证路径
	 * @return  {@code  true}如果路径无效，否则{@code  false}
	 */
	private boolean isInvalidEncodedPath(String path) {
		if (path.contains("%")) {
			try {
				// Use URLDecoder (vs UriUtils) to preserve potentially decoded UTF-8 chars
				String decodedPath = URLDecoder.decode(path, "UTF-8");
				if (isInvalidPath(decodedPath)) {
					return true;
				}
				decodedPath = processPath(decodedPath);
				if (isInvalidPath(decodedPath)) {
					return true;
				}
			}
			catch (IllegalArgumentException ex) {
				// May not be possible to decode...
			}
			catch (UnsupportedEncodingException ex) {
				// Should never happen...
			}
		}
		return false;
	}

	/**
	 * Identifies invalid resource paths. By default rejects:
	 * <ul>
	 * <li>Paths that contain "WEB-INF" or "META-INF"
	 * <li>Paths that contain "../" after a call to
	 * {@link org.springframework.util.StringUtils#cleanPath}.
	 * <li>Paths that represent a {@link org.springframework.util.ResourceUtils#isUrl
	 * valid URL} or would represent one after the leading slash is removed.
	 * </ul>
	 * <p><strong>Note:</strong> this method assumes that leading, duplicate '/'
	 * or control characters (e.g. white space) have been trimmed so that the
	 * path starts predictably with a single '/' or does not have one.
	 * @param path the path to validate
	 * @return {@code true} if the path is invalid, {@code false} otherwise
	 * @since 3.0.6
	 */
	/**
	 * 标识无效的资源路径。 
	 * 默认情况下拒绝：<ul> <li>包含"WEB-INF"或"META-INF"的路径<li>包含在调用{@link  org.springframework.util之后的包含"../"的路径。 
	 *  StringUtils＃cleanPath}。 
	 *  <li>表示{@link  org.springframework.util.ResourceUtils＃isUrl有效URL}的路径，或者在除去前斜杠后表示的路径。 
	 *  </ ul> <p> <strong>注意：</ strong>：此方法假定已修剪掉了开头的，重复的'/'或控制字符（例如，空格），以便路径可预测地以单个'/'或没有一个。 
	 *  
	 * @param 路径验证路径
	 * @return  {@code  true}，如果该路径无效，则{@code  false}，否则@since 3.0.6
	 */
	protected boolean isInvalidPath(String path) {
		if (path.contains("WEB-INF") || path.contains("META-INF")) {
			if (logger.isWarnEnabled()) {
				logger.warn("Path with \"WEB-INF\" or \"META-INF\": [" + path + "]");
			}
			return true;
		}
		if (path.contains(":/")) {
			String relativePath = (path.charAt(0) == '/' ? path.substring(1) : path);
			if (ResourceUtils.isUrl(relativePath) || relativePath.startsWith("url:")) {
				if (logger.isWarnEnabled()) {
					logger.warn("Path represents URL or has \"url:\" prefix: [" + path + "]");
				}
				return true;
			}
		}
		if (path.contains("..") && StringUtils.cleanPath(path).contains("../")) {
			if (logger.isWarnEnabled()) {
				logger.warn("Path contains \"../\" after call to StringUtils#cleanPath: [" + path + "]");
			}
			return true;
		}
		return false;
	}

	/**
	 * Determine the media type for the given request and the resource matched
	 * to it. This implementation tries to determine the MediaType using one of
	 * the following lookups based on the resource filename and its path
	 * extension:
	 * <ol>
	 * <li>{@link javax.servlet.ServletContext#getMimeType(String)}
	 * <li>{@link #getMediaTypes()}
	 * <li>{@link MediaTypeFactory#getMediaType(String)}
	 * </ol>
	 * @param request the current request
	 * @param resource the resource to check
	 * @return the corresponding media type, or {@code null} if none found
	 */
	/**
	 * 确定给定请求的媒体类型以及与其匹配的资源。 
	 * 此实现尝试根据资源文件名及其路径扩展名使用以下查找之一来确定MediaType：<ol> <li> {<@link> javax.servlet.ServletContext＃getMimeType（String）} <li> {@link  #getMediaTypes（）} <li> {<@link> MediaTypeFactory＃getMediaType（String）} </ ol> 
	 * @param 请求当前请求
	 * @param 资源该资源以检查
	 * @return 相应的媒体类型，如果找不到，则为{@code  null}
	 */
	@Nullable
	protected MediaType getMediaType(HttpServletRequest request, Resource resource) {
		MediaType result = null;
		String mimeType = request.getServletContext().getMimeType(resource.getFilename());
		if (StringUtils.hasText(mimeType)) {
			result = MediaType.parseMediaType(mimeType);
		}
		if (result == null || MediaType.APPLICATION_OCTET_STREAM.equals(result)) {
			MediaType mediaType = null;
			String filename = resource.getFilename();
			String ext = StringUtils.getFilenameExtension(filename);
			if (ext != null) {
				mediaType = this.mediaTypes.get(ext.toLowerCase(Locale.ENGLISH));
			}
			if (mediaType == null) {
				mediaType = MediaTypeFactory.getMediaType(filename).orElse(null);
			}
			if (mediaType != null) {
				result = mediaType;
			}
		}
		return result;
	}

	/**
	 * Set headers on the given servlet response.
	 * Called for GET requests as well as HEAD requests.
	 * @param response current servlet response
	 * @param resource the identified resource (never {@code null})
	 * @param mediaType the resource's media type (never {@code null})
	 * @throws IOException in case of errors while setting the headers
	 */
	/**
	 * 在给定的Servlet响应上设置标头。 
	 * 被称为GET请求和HEAD请求。 
	 *  
	 * @param 响应当前servlet响应
	 * @param 资源标识的资源（从不{@code  null}）
	 * @param  mediaType资源的媒体类型（从不{@code  null}）
	 * @throws  IOException如果在设置标题时出错
	 */
	protected void setHeaders(HttpServletResponse response, Resource resource, @Nullable MediaType mediaType)
			throws IOException {

		long length = resource.contentLength();
		if (length > Integer.MAX_VALUE) {
			response.setContentLengthLong(length);
		}
		else {
			response.setContentLength((int) length);
		}

		if (mediaType != null) {
			response.setContentType(mediaType.toString());
		}
		if (resource instanceof HttpResource) {
			HttpHeaders resourceHeaders = ((HttpResource) resource).getResponseHeaders();
			resourceHeaders.forEach((headerName, headerValues) -> {
				boolean first = true;
				for (String headerValue : headerValues) {
					if (first) {
						response.setHeader(headerName, headerValue);
					}
					else {
						response.addHeader(headerName, headerValue);
					}
					first = false;
				}
			});
		}
		response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
	}


	@Override
	public String toString() {
		return "ResourceHttpRequestHandler " + formatLocations();
	}

	private Object formatLocations() {
		if (!this.locationValues.isEmpty()) {
			return this.locationValues.stream().collect(Collectors.joining("\", \"", "[\"", "\"]"));
		}
		else if (!this.locations.isEmpty()) {
			return this.locations;
		}
		return Collections.emptyList();
	}

}
