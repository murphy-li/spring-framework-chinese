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

package org.springframework.web.cors;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

/**
 * Provide a per request {@link CorsConfiguration} instance based on a
 * collection of {@link CorsConfiguration} mapped on path patterns.
 *
 * <p>Exact path mapping URIs (such as {@code "/admin"}) are supported
 * as well as Ant-style path patterns (such as {@code "/admin/**"}).
 *
 * @author Sebastien Deleuze
 * @since 4.2
 */
/**
 * 根据映射到路径模式的{@link  CorsConfiguration}的集合，提供每个请求的{@link  CorsConfiguration}实例。 
 *  <p>支持精确的路径映射URI（例如{@code "/ admin"}）以及Ant样式的路径模式（例如{@code "/ admin"}）。 
 *  @author 塞巴斯蒂安·德勒兹@4.2起
 */
public class UrlBasedCorsConfigurationSource implements CorsConfigurationSource {

	private final Map<String, CorsConfiguration> corsConfigurations = new LinkedHashMap<>();

	private PathMatcher pathMatcher = new AntPathMatcher();

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Nullable
	private String lookupPathAttributeName;


	/**
	 * Set the PathMatcher implementation to use for matching URL paths
	 * against registered URL patterns. Default is AntPathMatcher.
	 * @see org.springframework.util.AntPathMatcher
	 */
	/**
	 * 设置PathMatcher实现，以将URL路径与已注册的URL模式进行匹配。 
	 * 默认值为AntPathMatcher。 
	 *  
	 * @see  org.springframework.util.AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	/**
	 * Shortcut to same property on underlying {@link #setUrlPathHelper UrlPathHelper}.
	 * @see org.springframework.web.util.UrlPathHelper#setAlwaysUseFullPath
	 */
	/**
	 * 基础{@link  #setUrlPathHelper UrlPathHelper}上相同属性的快捷方式。 
	 *  
	 * @see  org.springframework.web.util.UrlPathHelper＃setAlwaysUseFullPath
	 */
	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	/**
	 * Shortcut to same property on underlying {@link #setUrlPathHelper UrlPathHelper}.
	 * @see org.springframework.web.util.UrlPathHelper#setUrlDecode
	 */
	/**
	 * 基础{@link  #setUrlPathHelper UrlPathHelper}上相同属性的快捷方式。 
	 *  
	 * @see  org.springframework.web.util.UrlPathHelper＃setUrlDecode
	 */
	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	/**
	 * Optionally configure the name of the attribute that caches the lookupPath.
	 * This is used to make the call to
	 * {@link UrlPathHelper#getLookupPathForRequest(HttpServletRequest, String)}
	 * @param lookupPathAttributeName the request attribute to check
	 * @since 5.2
	 */
	/**
	 * （可选）配置缓存lookupPath的属性的名称。 
	 * 这用于调用{@link  UrlPathHelper＃getLookupPathForRequest（HttpServletRequest，String）} 
	 * @param  lookupPathAttributeName请求属性以检查@since 5.2
	 */
	public void setLookupPathAttributeName(@Nullable String lookupPathAttributeName) {
		this.lookupPathAttributeName = lookupPathAttributeName;
	}

	/**
	 * Shortcut to same property on underlying {@link #setUrlPathHelper UrlPathHelper}.
	 * @see org.springframework.web.util.UrlPathHelper#setRemoveSemicolonContent(boolean)
	 */
	/**
	 * 基础{@link  #setUrlPathHelper UrlPathHelper}上相同属性的快捷方式。 
	 *  
	 * @see  org.springframework.web.util.UrlPathHelper＃setRemoveSemicolonContent（boolean）
	 */
	public void setRemoveSemicolonContent(boolean removeSemicolonContent) {
		this.urlPathHelper.setRemoveSemicolonContent(removeSemicolonContent);
	}

	/**
	 * Set the UrlPathHelper to use for resolution of lookup paths.
	 * <p>Use this to override the default UrlPathHelper with a custom subclass.
	 */
	/**
	 * 设置UrlPathHelper以用于解析查找路径。 
	 *  <p>使用它使用自定义子类覆盖默认的UrlPathHelper。 
	 * 
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * Set CORS configuration based on URL patterns.
	 */
	/**
	 * 根据URL模式设置CORS配置。 
	 * 
	 */
	public void setCorsConfigurations(@Nullable Map<String, CorsConfiguration> corsConfigurations) {
		this.corsConfigurations.clear();
		if (corsConfigurations != null) {
			this.corsConfigurations.putAll(corsConfigurations);
		}
	}

	/**
	 * Get the CORS configuration.
	 */
	/**
	 * 获取CORS配置。 
	 * 
	 */
	public Map<String, CorsConfiguration> getCorsConfigurations() {
		return Collections.unmodifiableMap(this.corsConfigurations);
	}

	/**
	 * Register a {@link CorsConfiguration} for the specified path pattern.
	 */
	/**
	 * 为指定的路径模式注册{@link  CorsConfiguration}。 
	 * 
	 */
	public void registerCorsConfiguration(String path, CorsConfiguration config) {
		this.corsConfigurations.put(path, config);
	}


	@Override
	@Nullable
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request, this.lookupPathAttributeName);
		for (Map.Entry<String, CorsConfiguration> entry : this.corsConfigurations.entrySet()) {
			if (this.pathMatcher.match(entry.getKey(), lookupPath)) {
				return entry.getValue();
			}
		}
		return null;
	}

}
