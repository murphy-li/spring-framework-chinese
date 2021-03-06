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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.JavaScriptUtils;
import org.springframework.web.util.TagUtils;

/**
 * The {@code <message>} tag looks up a message in the scope of this page.
 * Messages are resolved using the ApplicationContext and thus support
 * internationalization.
 *
 * <p>Detects an HTML escaping setting, either on this tag instance, the page level,
 * or the {@code web.xml} level. Can also apply JavaScript escaping.
 *
 * <p>If "code" isn't set or cannot be resolved, "text" will be used as default
 * message. Thus, this tag can also be used for HTML escaping of any texts.
 *
 * <p>Message arguments can be specified via the {@link #setArguments(Object) arguments}
 * attribute or by using nested {@code <spring:argument>} tags.
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
 * <td>arguments</td>
 * <td>false</td>
 * <td>true</td>
 * <td>Set optional message arguments for this tag, as a (comma-)delimited
 * String (each String argument can contain JSP EL), an Object array (used as
 * argument array), or a single Object (used as single argument).</td>
 * </tr>
 * <tr>
 * <td>argumentSeparator</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The separator character to be used for splitting the arguments string
 * value; defaults to a 'comma' (',').</td>
 * </tr>
 * <tr>
 * <td>code</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The code (key) to use when looking up the message.
 * If code is not provided, the text attribute will be used.</td>
 * </tr>
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
 * <tr>
 * <td>message</td>
 * <td>false</td>
 * <td>true</td>
 * <td>A MessageSourceResolvable argument (direct or through JSP EL).
 * Fits nicely when used in conjunction with Spring’s own validation error
 * classes which all implement the MessageSourceResolvable interface.
 * For example, this allows you to iterate over all of the errors in a form,
 * passing each error (using a runtime expression) as the value of this
 * 'message' attribute, thus effecting the easy display of such error
 * messages.</td>
 * </tr>
 * <tr>
 * <td>scope</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The scope to use when exporting the result to a variable. This attribute
 * is only used when var is also set. Possible values are page, request, session
 * and application.</td>
 * </tr>
 * <tr>
 * <td>text</td>
 * <td>false</td>
 * <td>true</td>
 * <td>Default text to output when a message for the given code could not be
 * found. If both text and code are not set, the tag will output null.</td>
 * </tr>
 * <tr>
 * <td>var</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The string to use when binding the result to the page, request, session
 * or application scope. If not specified, the result gets outputted to the writer
 * (i.e. typically directly to the JSP).</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Nicholas Williams
 * @see #setCode
 * @see #setText
 * @see #setHtmlEscape
 * @see #setJavaScriptEscape
 * @see HtmlEscapeTag#setDefaultHtmlEscape
 * @see org.springframework.web.util.WebUtils#HTML_ESCAPE_CONTEXT_PARAM
 * @see ArgumentTag
 */
/**
 * {@code  <message>}标签在此页面范围内查找消息。 
 * 消息使用ApplicationContext解析，因此支持国际化。 
 *  <p>在此标记实例，页面级别或{@code  web.xml}级别上检测HTML转义设置。 
 * 也可以应用JavaScript转义。 
 *  <p>如果未设置"代码"或无法解析，则"文本"将用作默认消息。 
 * 因此，该标签也可以用于任何文本的HTML转义。 
 * 消息参数可以通过{@link  #setArguments（Object）arguments}属性或使用嵌套的{@code  <spring：argument>}标签来指定。 
 *  <table> <caption>属性摘要</ caption> <thead> <tr> <th>属性</ th> <th>是否必需？</ th> <th>运行时表达式？</ th> <th>描述< / th> </ tr> </ thead> <tbody> <tr> <td>参数</ td> <td> false </ td> <td> true </ td> <td>为此设置可选的消息参数标签，以（逗号分隔）字符串（每个字符串参数可以包含JSP EL），对象数组（用作参数数组）或单个对象（用作单个参数）。 
 * </ td> </ tr> < tr> <td> argumentSeparator </ td> <td> false </ td> <td> true </ td> <td>用于分隔参数字符串值的分隔符； 
 * 默认为'逗号'（'，'）。 
 * </ td> </ tr> <tr> <td>代码</ td> <td> false </ td> <td> true </ td> <td>查找消息时使用的代码（密钥）。 
 * 如果未提供代码，则将使用text属性。 
 * </ td> </ tr> <tr> <td> htmlEscape </ td> <td> false </ td> <td> true </ td> <td >为此标签设置HTML转义符，为布尔值。 
 * 覆盖当前页面的默认HTML转义设置。 
 * </ td> </ tr> <tr> <td> javaScriptEscape </ td> <td> false </ td> <td> true </ td> <td> Set JavaScript将此标记转义为布尔值。 
 * 默认值为false。 
 * </ td> </ tr> <tr> <td>消息</ td> <td> false </ td> <td> true </ td> <td> MessageSourceResolvable参数（直接或通过JSP） EL）。 
 * 与Spring自己的验证错误类（均实现MessageSourceResolvable接口）结合使用时非常合适。 
 * 例如，这允许您遍历表单中的所有错误，将每个错误（使用运行时表达式）作为此"message"属性的值传递，从而实现了此类错误消息的轻松显示。 
 * </ td> </ tr> <tr> <td>作用域</ td> <td> false </ td> <td> true </ td> <td>将结果导出到变量时使用的范围。 
 * 仅当还设置了var时，才使用此属性。 
 * 可能的值是页面，请求，会话和应用程序。 
 * </ td> </ tr> <tr> <td> text </ td> <td> false </ td> <td> true </ td> <td>默认当找不到给定代码的消息时输出的文本。 
 * 如果未同时设置文本和代码，则标记将输出null。 
 * </ td> </ tr> <tr> <td> var </ td> <td> false </ td> <td> true </ td> <td>将结果绑定到页面，请求，会话或应用程序范围时使用的字符串。 
 * 如果未指定，结果将输出到编写器（即通常直接输出到JSP）。 
 * </ td> </ tr> </ tbody> </ table> @author  Rod Johnson @author  Juergen Hoeller <@作者>尼古拉斯·威廉姆斯
 * @see  #setCode 
 * @see  #setText 
 * @see  #setHtmlEscape 
 * @see  #setJavaScriptEscape 
 * @see  HtmlEscapeTag＃setDefaultHtmlEscape 
 * @see  org.springframework.web.util.WebUtils＃HTML_ESCAPE_CONTEXT_PARAM 
 * @see  ArgumentTag
 */
@SuppressWarnings("serial")
public class MessageTag extends HtmlEscapingAwareTag implements ArgumentAware {

	/**
	 * Default separator for splitting an arguments String: a comma (",").
	 */
	/**
	 * 用于拆分参数的默认分隔符字符串：逗号（"，"）。 
	 * 
	 */
	public static final String DEFAULT_ARGUMENT_SEPARATOR = ",";


	@Nullable
	private MessageSourceResolvable message;

	@Nullable
	private String code;

	@Nullable
	private Object arguments;

	private String argumentSeparator = DEFAULT_ARGUMENT_SEPARATOR;

	private List<Object> nestedArguments = Collections.emptyList();

	@Nullable
	private String text;

	@Nullable
	private String var;

	private String scope = TagUtils.SCOPE_PAGE;

	private boolean javaScriptEscape = false;


	/**
	 * Set the MessageSourceResolvable for this tag.
	 * <p>If a MessageSourceResolvable is specified, it effectively overrides
	 * any code, arguments or text specified on this tag.
	 */
	/**
	 * 为此标签设置MessageSourceResolvable。 
	 *  <p>如果指定了MessageSourceResolvable，则它将有效地覆盖此标记上指定的任何代码，参数或文本。 
	 * 
	 */
	public void setMessage(MessageSourceResolvable message) {
		this.message = message;
	}

	/**
	 * Set the message code for this tag.
	 */
	/**
	 * 设置该标签的消息代码。 
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Set optional message arguments for this tag, as a comma-delimited
	 * String (each String argument can contain JSP EL), an Object array
	 * (used as argument array), or a single Object (used as single argument).
	 */
	/**
	 * 为此标签设置可选的消息参数，以逗号分隔的字符串（每个String参数可以包含JSP EL），对象数组（用作参数数组）或单个对象（用作单个参数）。 
	 * 
	 */
	public void setArguments(Object arguments) {
		this.arguments = arguments;
	}

	/**
	 * Set the separator to use for splitting an arguments String.
	 * Default is a comma (",").
	 * @see #setArguments
	 */
	/**
	 * 设置用于分隔参数String的分隔符。 
	 * 默认值为逗号（"，"）。 
	 *  
	 * @see  #setArguments
	 */
	public void setArgumentSeparator(String argumentSeparator) {
		this.argumentSeparator = argumentSeparator;
	}

	@Override
	public void addArgument(@Nullable Object argument) throws JspTagException {
		this.nestedArguments.add(argument);
	}

	/**
	 * Set the message text for this tag.
	 */
	/**
	 * 设置该标签的消息文本。 
	 * 
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Set PageContext attribute name under which to expose
	 * a variable that contains the resolved message.
	 * @see #setScope
	 * @see javax.servlet.jsp.PageContext#setAttribute
	 */
	/**
	 * 设置PageContext属性名称，在该属性名称下公开包含已解析消息的变量。 
	 *  
	 * @see  #setScope 
	 * @see  javax.servlet.jsp.PageContext＃setAttribute
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * Set the scope to export the variable to.
	 * Default is SCOPE_PAGE ("page").
	 * @see #setVar
	 * @see org.springframework.web.util.TagUtils#SCOPE_PAGE
	 * @see javax.servlet.jsp.PageContext#setAttribute
	 */
	/**
	 * 设置将变量导出到的范围。 
	 * 默认值为SCOPE_PAGE（"页面"）。 
	 *  
	 * @see  #setVar 
	 * @see  org.springframework.web.util.TagUtils＃SCOPE_PAGE 
	 * @see  javax.servlet.jsp.PageContext＃setAttribute
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

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
	protected final int doStartTagInternal() throws JspException, IOException {
		this.nestedArguments = new LinkedList<>();
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * Resolves the message, escapes it if demanded,
	 * and writes it to the page (or exposes it as variable).
	 * @see #resolveMessage()
	 * @see org.springframework.web.util.HtmlUtils#htmlEscape(String)
	 * @see org.springframework.web.util.JavaScriptUtils#javaScriptEscape(String)
	 * @see #writeMessage(String)
	 */
	/**
	 * 解决消息，如果需要转义，然后将其写入页面（或将其公开为变量）。 
	 *  
	 * @see  #resolveMessage（）
	 * @see  org.springframework.web.util.HtmlUtils＃htmlEscape（String）
	 * @see  org.springframework.web.util.JavaScriptUtils＃javaScriptEscape（String）
	 * @see  #writeMessage （串）
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			// Resolve the unescaped message.
			String msg = resolveMessage();

			// HTML and/or JavaScript escape, if demanded.
			msg = htmlEscape(msg);
			msg = this.javaScriptEscape ? JavaScriptUtils.javaScriptEscape(msg) : msg;

			// Expose as variable, if demanded, else write to the page.
			if (this.var != null) {
				this.pageContext.setAttribute(this.var, msg, TagUtils.getScope(this.scope));
			}
			else {
				writeMessage(msg);
			}

			return EVAL_PAGE;
		}
		catch (IOException ex) {
			throw new JspTagException(ex.getMessage(), ex);
		}
		catch (NoSuchMessageException ex) {
			throw new JspTagException(getNoSuchMessageExceptionDescription(ex));
		}
	}

	@Override
	public void release() {
		super.release();
		this.arguments = null;
	}


	/**
	 * Resolve the specified message into a concrete message String.
	 * The returned message String should be unescaped.
	 */
	/**
	 * 将指定的消息解析为具体的消息字符串。 
	 * 返回的消息字符串应不转义。 
	 * 
	 */
	protected String resolveMessage() throws JspException, NoSuchMessageException {
		MessageSource messageSource = getMessageSource();

		// Evaluate the specified MessageSourceResolvable, if any.
		if (this.message != null) {
			// We have a given MessageSourceResolvable.
			return messageSource.getMessage(this.message, getRequestContext().getLocale());
		}

		if (this.code != null || this.text != null) {
			// We have a code or default text that we need to resolve.
			Object[] argumentsArray = resolveArguments(this.arguments);
			if (!this.nestedArguments.isEmpty()) {
				argumentsArray = appendArguments(argumentsArray, this.nestedArguments.toArray());
			}

			if (this.text != null) {
				// We have a fallback text to consider.
				String msg = messageSource.getMessage(
						this.code, argumentsArray, this.text, getRequestContext().getLocale());
				return (msg != null ? msg : "");
			}
			else {
				// We have no fallback text to consider.
				return messageSource.getMessage(
						this.code, argumentsArray, getRequestContext().getLocale());
			}
		}

		throw new JspTagException("No resolvable message");
	}

	private Object[] appendArguments(@Nullable Object[] sourceArguments, Object[] additionalArguments) {
		if (ObjectUtils.isEmpty(sourceArguments)) {
			return additionalArguments;
		}
		Object[] arguments = new Object[sourceArguments.length + additionalArguments.length];
		System.arraycopy(sourceArguments, 0, arguments, 0, sourceArguments.length);
		System.arraycopy(additionalArguments, 0, arguments, sourceArguments.length, additionalArguments.length);
		return arguments;
	}

	/**
	 * Resolve the given arguments Object into an arguments array.
	 * @param arguments the specified arguments Object
	 * @return the resolved arguments as array
	 * @throws JspException if argument conversion failed
	 * @see #setArguments
	 */
	/**
	 * 将给定的arguments对象解析为arguments数组。 
	 *  
	 * @param 参数指定的参数Object 
	 * @return 解析的参数作为数组
	 * @throws 如果参数转换失败，则JspException 
	 * @see  #setArguments
	 */
	@Nullable
	protected Object[] resolveArguments(@Nullable Object arguments) throws JspException {
		if (arguments instanceof String) {
			String[] stringArray =
					StringUtils.delimitedListToStringArray((String) arguments, this.argumentSeparator);
			if (stringArray.length == 1) {
				Object argument = stringArray[0];
				if (argument != null && argument.getClass().isArray()) {
					return ObjectUtils.toObjectArray(argument);
				}
				else {
					return new Object[] {argument};
				}
			}
			else {
				return stringArray;
			}
		}
		else if (arguments instanceof Object[]) {
			return (Object[]) arguments;
		}
		else if (arguments instanceof Collection) {
			return ((Collection<?>) arguments).toArray();
		}
		else if (arguments != null) {
			// Assume a single argument object.
			return new Object[] {arguments};
		}
		else {
			return null;
		}
	}

	/**
	 * Write the message to the page.
	 * <p>Can be overridden in subclasses, e.g. for testing purposes.
	 * @param msg the message to write
	 * @throws IOException if writing failed
	 */
	/**
	 * 将消息写到页面。 
	 *  <p>可以在子类中覆盖，例如用于测试目的。 
	 *  
	 * @param  msg如果写入失败，则写入
	 * @throws  IOException的消息
	 */
	protected void writeMessage(String msg) throws IOException {
		this.pageContext.getOut().write(String.valueOf(msg));
	}

	/**
	 * Use the current RequestContext's application context as MessageSource.
	 */
	/**
	 * 使用当前RequestContext的应用程序上下文作为MessageSource。 
	 * 
	 */
	protected MessageSource getMessageSource() {
		return getRequestContext().getMessageSource();
	}

	/**
	 * Return default exception message.
	 */
	/**
	 * 返回默认异常消息。 
	 * 
	 */
	protected String getNoSuchMessageExceptionDescription(NoSuchMessageException ex) {
		return ex.getMessage();
	}

}
