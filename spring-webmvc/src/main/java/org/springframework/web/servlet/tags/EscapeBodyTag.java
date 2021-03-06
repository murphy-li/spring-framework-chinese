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

package org.springframework.web.servlet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.util.JavaScriptUtils;

/**
 * The {@code <escapeBody>} tag is used to escape its enclosed body content,
 * applying HTML escaping and/or JavaScript escaping.
 *
 * <p>Provides a "htmlEscape" property for explicitly specifying whether to
 * apply HTML escaping. If not set, a page-level default (e.g. from the
 * HtmlEscapeTag) or an application-wide default (the "defaultHtmlEscape"
 * context-param in web.xml) is used.
 *
 * <p>Provides a "javaScriptEscape" property for specifying whether to apply
 * JavaScript escaping. Can be combined with HTML escaping or used standalone.
 *
 * <table>
 * <caption>Attribute Summary</caption>
 * <thead>
 * <tr>
 * <th>Attribute</th>
 * <th>Required?</th>
 * <th>Runtime Expression?</th>
 * <th>Description</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>htmlEscape</td>
 * <td>false</td>
 * <td>true</td>
 * <td>Set HTML escaping for this tag, as boolean value.
 * Overrides the default HTML escaping setting for the current page.</td>
 * </tr>
 * <tr>
 * <td>javaScriptEscape</td>
 * <td>false</td>
 * <td>true</td>
 * <td>Set JavaScript escaping for this tag, as boolean value.
 * Default is false.</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Juergen Hoeller
 * @since 1.1.1
 * @see org.springframework.web.util.HtmlUtils
 * @see org.springframework.web.util.JavaScriptUtils
 */
/**
 * {@code  <escapeBody>}标签用于通过转义HTML和/或JavaScript来转义其包含的主体内容。 
 *  <p>提供一个"htmlEscape"属性，用于明确指定是否应用HTML转义。 
 * 如果未设置，则使用页面级默认值（例如，来自HtmlEscapeTag的值）或应用程序范围的默认值（web.xml中的"defaultHtmlEscape"上下文参数）。 
 *  <p>提供一个"javaScriptEscape"属性，用于指定是否应用JavaScript转义。 
 * 可以与HTML转义结合使用，也可以独立使用。 
 *  <table> <caption>属性摘要</ caption> <thead> <tr> <th>属性</ th> <th>是否必需？</ th> <th>运行时表达式？</ th> <th>描述< / th> </ tr> </ thead> <tbody> <tr> <td> htmlEscape </ td> <td> false </ td> <td> true </ td> <td>为此标签设置HTML转义，作为布尔值。 
 * 覆盖当前页面的默认HTML转义设置。 
 * </ td> </ tr> <tr> <td> javaScriptEscape </ td> <td> false </ td> <td> true </ td> <td> Set JavaScript将此标记转义为布尔值。 
 * 默认值为false。 
 * </ td> </ tr> </ tbody> </ table> @author  Juergen Hoeller @since 1.1.1 
 * @see  org.springframework.web.util.HtmlUtils 
 * @see  org。 
 *  springframework.web.util.JavaScriptUtils
 */
@SuppressWarnings("serial")
public class EscapeBodyTag extends HtmlEscapingAwareTag implements BodyTag {

	private boolean javaScriptEscape = false;

	@Nullable
	private BodyContent bodyContent;


	/**
	 * Set JavaScript escaping for this tag, as boolean value.
	 * Default is "false".
	 */
	/**
	 * 将此JavaScript的转义字符设置为布尔值。 
	 * 默认值为"false"。 
	 * 
	 */
	public void setJavaScriptEscape(boolean javaScriptEscape) throws JspException {
		this.javaScriptEscape = javaScriptEscape;
	}


	@Override
	protected int doStartTagInternal() {
		// do nothing
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public void doInitBody() {
		// do nothing
	}

	@Override
	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			String content = readBodyContent();
			// HTML and/or JavaScript escape, if demanded
			content = htmlEscape(content);
			content = (this.javaScriptEscape ? JavaScriptUtils.javaScriptEscape(content) : content);
			writeBodyContent(content);
		}
		catch (IOException ex) {
			throw new JspException("Could not write escaped body", ex);
		}
		return (SKIP_BODY);
	}

	/**
	 * Read the unescaped body content from the page.
	 * @return the original content
	 * @throws IOException if reading failed
	 */
	/**
	 * 从页面读取未转义的正文内容。 
	 *  
	 * @return 原始内容
	 * @throws  IOException如果读取失败
	 */
	protected String readBodyContent() throws IOException {
		Assert.state(this.bodyContent != null, "No BodyContent set");
		return this.bodyContent.getString();
	}

	/**
	 * Write the escaped body content to the page.
	 * <p>Can be overridden in subclasses, e.g. for testing purposes.
	 * @param content the content to write
	 * @throws IOException if writing failed
	 */
	/**
	 * 将转义的正文内容写入页面。 
	 *  <p>可以在子类中覆盖，例如用于测试目的。 
	 *  
	 * @param 内容要写入的内容
	 * @throws  IOException如果写入失败
	 */
	protected void writeBodyContent(String content) throws IOException {
		Assert.state(this.bodyContent != null, "No BodyContent set");
		this.bodyContent.getEnclosingWriter().print(content);
	}

}
