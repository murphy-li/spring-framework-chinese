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

package org.springframework.web.servlet.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@link RequestToViewNameTranslator} that simply transforms the URI of
 * the incoming request into a view name.
 *
 * <p>Can be explicitly defined as the {@code viewNameTranslator} bean in a
 * {@link org.springframework.web.servlet.DispatcherServlet} context.
 * Otherwise, a plain default instance will be used.
 *
 * <p>The default transformation simply strips leading and trailing slashes
 * as well as the file extension of the URI, and returns the result as the
 * view name with the configured {@link #setPrefix prefix} and a
 * {@link #setSuffix suffix} added as appropriate.
 *
 * <p>The stripping of the leading slash and file extension can be disabled
 * using the {@link #setStripLeadingSlash stripLeadingSlash} and
 * {@link #setStripExtension stripExtension} properties, respectively.
 *
 * <p>Find below some examples of request to view name translation.
 * <ul>
 * <li>{@code http://localhost:8080/gamecast/display.html} &raquo; {@code display}</li>
 * <li>{@code http://localhost:8080/gamecast/displayShoppingCart.html} &raquo; {@code displayShoppingCart}</li>
 * <li>{@code http://localhost:8080/gamecast/admin/index.html} &raquo; {@code admin/index}</li>
 * </ul>
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.web.servlet.RequestToViewNameTranslator
 * @see org.springframework.web.servlet.ViewResolver
 */
/**
 * {@link  RequestToViewNameTranslator}仅将传入请求的URI转换为视图名称。 
 *  <p>可以在{@link  org.springframework.web.servlet.DispatcherServlet}上下文中明确定义为{@code  viewNameTranslator} bean。 
 * 否则，将使用普通的默认实例。 
 *  <p>默认转换只去除URI的前导和尾部斜杠以及文件扩展名，并以配置的{@link  #setPrefix前缀}和{@link ＃ setSuffix suffix}。 
 *  <p>可以分别使用{@link  #setStripLeadingSlash stripLeadingSlash}和{@link  #setStripExtension stripExtension}属性来禁用前导斜杠和文件扩展名的剥离。 
 *  <p>在下面找到一些请求查看名称翻译的示例。 
 *  <ul> <li> {<@code> http：// localhost：8080 / gamecast / display.html}»{@code 显示} </ li> <li> {<@code> http：// localhost ：8080 / gamecast / displayShoppingCart.html}»{@code  displayShoppingCart} </ li> <li> {<@code> http：// localhost：8080 / gamecast / admin / index.html}»{<@code > admin / index} </ li> </ ul> @author  Rob Harrop @author  Juergen Hoeller @since 2.0 
 * @see  org.springframework.web.servlet.RequestToViewNameTranslator 
 * @see  org.springframework.web .servlet.ViewResolver
 */
public class DefaultRequestToViewNameTranslator implements RequestToViewNameTranslator {

	private static final String SLASH = "/";


	private String prefix = "";

	private String suffix = "";

	private String separator = SLASH;

	private boolean stripLeadingSlash = true;

	private boolean stripTrailingSlash = true;

	private boolean stripExtension = true;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();


	/**
	 * Set the prefix to prepend to generated view names.
	 * @param prefix the prefix to prepend to generated view names
	 */
	/**
	 * 将前缀设置为在生成的视图名称之前。 
	 *  
	 * @param 在前缀之前加上生成的视图名称
	 */
	public void setPrefix(@Nullable String prefix) {
		this.prefix = (prefix != null ? prefix : "");
	}

	/**
	 * Set the suffix to append to generated view names.
	 * @param suffix the suffix to append to generated view names
	 */
	/**
	 * 设置后缀以附加到生成的视图名称。 
	 *  
	 * @param 后缀要附加到生成的视图名称的后缀
	 */
	public void setSuffix(@Nullable String suffix) {
		this.suffix = (suffix != null ? suffix : "");
	}

	/**
	 * Set the value that will replace '{@code /}' as the separator
	 * in the view name. The default behavior simply leaves '{@code /}'
	 * as the separator.
	 */
	/**
	 * 设置将替换"{@code  /}"的值作为视图名称中的分隔符。 
	 * 默认行为只是将'{@code  /}'用作分隔符。 
	 * 
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Set whether or not leading slashes should be stripped from the URI when
	 * generating the view name. Default is "true".
	 */
	/**
	 * 设置在生成视图名称时是否应从URI中删除前导斜杠。 
	 * 默认值为"true"。 
	 * 
	 */
	public void setStripLeadingSlash(boolean stripLeadingSlash) {
		this.stripLeadingSlash = stripLeadingSlash;
	}

	/**
	 * Set whether or not trailing slashes should be stripped from the URI when
	 * generating the view name. Default is "true".
	 */
	/**
	 * 设置在生成视图名称时是否应从URI中删除斜杠。 
	 * 默认值为"true"。 
	 * 
	 */
	public void setStripTrailingSlash(boolean stripTrailingSlash) {
		this.stripTrailingSlash = stripTrailingSlash;
	}

	/**
	 * Set whether or not file extensions should be stripped from the URI when
	 * generating the view name. Default is "true".
	 */
	/**
	 * 设置在生成视图名称时是否应从URI中删除文件扩展名。 
	 * 默认值为"true"。 
	 * 
	 */
	public void setStripExtension(boolean stripExtension) {
		this.stripExtension = stripExtension;
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
	 * Set if ";" (semicolon) content should be stripped from the request URI.
	 * @see org.springframework.web.util.UrlPathHelper#setRemoveSemicolonContent(boolean)
	 */
	/**
	 * 设置是否为";"（分号）内容应从请求URI中删除。 
	 *  
	 * @see  org.springframework.web.util.UrlPathHelper＃setRemoveSemicolonContent（boolean）
	 */
	public void setRemoveSemicolonContent(boolean removeSemicolonContent) {
		this.urlPathHelper.setRemoveSemicolonContent(removeSemicolonContent);
	}

	/**
	 * Set the {@link org.springframework.web.util.UrlPathHelper} to use for
	 * the resolution of lookup paths.
	 * <p>Use this to override the default UrlPathHelper with a custom subclass,
	 * or to share common UrlPathHelper settings across multiple web components.
	 */
	/**
	 * 设置{@link  org.springframework.web.util.UrlPathHelper}以用于解析查找路径。 
	 *  <p>使用它使用自定义子类覆盖默认的UrlPathHelper，或在多个Web组件之间共享通用的UrlPathHelper设置。 
	 * 
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}


	/**
	 * Translates the request URI of the incoming {@link HttpServletRequest}
	 * into the view name based on the configured parameters.
	 * @see org.springframework.web.util.UrlPathHelper#getLookupPathForRequest
	 * @see #transformPath
	 */
	/**
	 * 根据配置的参数，将传入的{@link  HttpServletRequest}的请求URI转换为视图名称。 
	 *  
	 * @see  org.springframework.web.util.UrlPathHelper＃getLookupPathForRequest 
	 * @see  #transformPath
	 */
	@Override
	public String getViewName(HttpServletRequest request) {
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request, HandlerMapping.LOOKUP_PATH);
		return (this.prefix + transformPath(lookupPath) + this.suffix);
	}

	/**
	 * Transform the request URI (in the context of the webapp) stripping
	 * slashes and extensions, and replacing the separator as required.
	 * @param lookupPath the lookup path for the current request,
	 * as determined by the UrlPathHelper
	 * @return the transformed path, with slashes and extensions stripped
	 * if desired
	 */
	/**
	 * 转换请求URI（在webapp的上下文中）以除去斜杠和扩展名，并根据需要替换分隔符。 
	 *  
	 * @param  lookupPath由UrlPathHelper确定的当前请求的查找路径
	 * @return 转换后的路径，并在需要时去除斜杠和扩展名
	 */
	@Nullable
	protected String transformPath(String lookupPath) {
		String path = lookupPath;
		if (this.stripLeadingSlash && path.startsWith(SLASH)) {
			path = path.substring(1);
		}
		if (this.stripTrailingSlash && path.endsWith(SLASH)) {
			path = path.substring(0, path.length() - 1);
		}
		if (this.stripExtension) {
			path = StringUtils.stripFilenameExtension(path);
		}
		if (!SLASH.equals(this.separator)) {
			path = StringUtils.replace(path, SLASH, this.separator);
		}
		return path;
	}

}
