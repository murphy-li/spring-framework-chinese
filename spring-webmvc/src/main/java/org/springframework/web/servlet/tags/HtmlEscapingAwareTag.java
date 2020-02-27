/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.tags;

import javax.servlet.jsp.JspException;

import org.springframework.lang.Nullable;
import org.springframework.web.util.HtmlUtils;

/**
 * Superclass for tags that output content that might get HTML-escaped.
 *
 * <p>Provides a "htmlEscape" property for explicitly specifying whether to
 * apply HTML escaping. If not set, a page-level default (e.g. from the
 * HtmlEscapeTag) or an application-wide default (the "defaultHtmlEscape"
 * context-param in {@code web.xml}) is used.
 *
 * @author Juergen Hoeller
 * @author Brian Clozel
 * @since 1.1
 * @see #setHtmlEscape
 * @see HtmlEscapeTag
 * @see org.springframework.web.servlet.support.RequestContext#isDefaultHtmlEscape
 * @see org.springframework.web.util.WebUtils#getDefaultHtmlEscape
 * @see org.springframework.web.util.WebUtils#getResponseEncodedHtmlEscape
 */
/**
 * 标签的超类，这些标签输出的内容可能会被HTML转义。 
 *  <p>提供一个"htmlEscape"属性，用于明确指定是否应用HTML转义。 
 * 如果未设置，则使用页面级默认值（例如，来自HtmlEscapeTag）或应用程序范围的默认值（{@code  web.xml}中的"defaultHtmlEscape"上下文参数）。 
 *  @author 尤尔根·霍勒（Juergen Hoeller）@author  Brian Clozel @since 1.1起.web.util.WebUtils＃getDefaultHtmlEscape 
 * @see  org.springframework.web.util.WebUtils＃getResponseEncodedHtmlEscape
 */
@SuppressWarnings("serial")
public abstract class HtmlEscapingAwareTag extends RequestContextAwareTag {

	@Nullable
	private Boolean htmlEscape;


	/**
	 * Set HTML escaping for this tag, as boolean value.
	 * Overrides the default HTML escaping setting for the current page.
	 * @see HtmlEscapeTag#setDefaultHtmlEscape
	 */
	/**
	 * 将此标签的HTML转义设置为布尔值。 
	 * 覆盖当前页面的默认HTML转义设置。 
	 *  
	 * @see  HtmlEscapeTag＃setDefaultHtmlEscape
	 */
	public void setHtmlEscape(boolean htmlEscape) throws JspException {
		this.htmlEscape = htmlEscape;
	}

	/**
	 * Return the HTML escaping setting for this tag,
	 * or the default setting if not overridden.
	 * @see #isDefaultHtmlEscape()
	 */
	/**
	 * 返回此标记的HTML转义设置，如果未覆盖则返回默认设置。 
	 *  
	 * @see  #isDefaultHtmlEscape（）
	 */
	protected boolean isHtmlEscape() {
		if (this.htmlEscape != null) {
			return this.htmlEscape.booleanValue();
		}
		else {
			return isDefaultHtmlEscape();
		}
	}

	/**
	 * Return the applicable default HTML escape setting for this tag.
	 * <p>The default implementation checks the RequestContext's setting,
	 * falling back to {@code false} in case of no explicit default given.
	 * @see #getRequestContext()
	 */
	/**
	 * 返回此标记的适用默认HTML转义设置。 
	 *  <p>默认实现检查RequestContext的设置，如果未给出明确的默认值，则退回到{@code  false}。 
	 *  
	 * @see  #getRequestContext（）
	 */
	protected boolean isDefaultHtmlEscape() {
		return getRequestContext().isDefaultHtmlEscape();
	}

	/**
	 * Return the applicable default for the use of response encoding with
	 * HTML escaping for this tag.
	 * <p>The default implementation checks the RequestContext's setting,
	 * falling back to {@code false} in case of no explicit default given.
	 * @since 4.1.2
	 * @see #getRequestContext()
	 */
	/**
	 * 返回适用的默认值，以将响应编码与该标记的HTML转义一起使用。 
	 *  <p>默认实现检查RequestContext的设置，如果未给出明确的默认值，则退回到{@code  false}。 
	 *  @since 4.1.2 
	 * @see  #getRequestContext（）
	 */
	protected boolean isResponseEncodedHtmlEscape() {
		return getRequestContext().isResponseEncodedHtmlEscape();
	}

	/**
	 * HTML-encodes the given String, only if the "htmlEscape" setting is enabled.
	 * <p>The response encoding will be taken into account if the
	 * "responseEncodedHtmlEscape" setting is enabled as well.
	 * @param content the String to escape
	 * @return the escaped String
	 * @since 4.1.2
	 * @see #isHtmlEscape()
	 * @see #isResponseEncodedHtmlEscape()
	 */
	/**
	 * 仅在启用"htmlEscape"设置的情况下，HTML编码给定的字符串。 
	 *  <p>如果还启用了"responseEncodedHtmlEscape"设置，则将考虑响应编码。 
	 *  
	 * @param 内容要转义的字符串
	 * @return 已转义的字符串@since 4.1.2 
	 * @see  #isHtmlEscape（）
	 * @see  #isResponseEncodedHtmlEscape（）
	 */
	protected String htmlEscape(String content) {
		String out = content;
		if (isHtmlEscape()) {
			if (isResponseEncodedHtmlEscape()) {
				out = HtmlUtils.htmlEscape(content, this.pageContext.getResponse().getCharacterEncoding());
			}
			else {
				out = HtmlUtils.htmlEscape(content);
			}
		}
		return out;
	}

}
