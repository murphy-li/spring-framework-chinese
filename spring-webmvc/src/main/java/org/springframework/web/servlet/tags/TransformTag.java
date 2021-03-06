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

import java.beans.PropertyEditor;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.lang.Nullable;
import org.springframework.web.util.TagUtils;

/**
 * The {@code <transform>} tag provides transformation for reference data values
 * from controllers and other objects inside a {@code spring:bind} tag (or a
 * data-bound form element tag from Spring's form tag library).
 *
 * <p>The BindTag has a PropertyEditor that it uses to transform properties of
 * a bean to a String, usable in HTML forms. This tag uses that PropertyEditor
 * to transform objects passed into this tag.
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
 * <td>Set HTML escaping for this tag, as boolean value. Overrides the default HTML
 * escaping setting for the current page.</td>
 * </tr>
 * <tr>
 * <td>scope</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The scope to use when exported the result to a variable. This attribute
 * is only used when var is also set. Possible values are page, request, session
 * and application.</td>
 * </tr>
 * <tr>
 * <td>value</td>
 * <td>true</td>
 * <td>true</td>
 * <td>The value to transform. This is the actual object you want to have
 * transformed (for instance a Date). Using the PropertyEditor that is currently
 * in use by the 'spring:bind' tag.</td>
 * </tr>
 * <tr>
 * <td>var</td>
 * <td>false</td>
 * <td>true</td>
 * <td>The string to use when binding the result to the page, request, session
 * or application scope. If not specified, the result gets outputted to the
 * writer (i.e. typically directly to the JSP).</td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Alef Arendsen
 * @author Juergen Hoeller
 * @since 20.09.2003
 * @see BindTag
 */
/**
 * {@code  <transform>}标签为{@code  spring：bind}标签（或Spring的form标签库中的数据绑定表单元素标签）中的控制器和其他对象的参考数据值提供转换。 
 *  <p> BindTag具有一个PropertyEditor，用于将bean的属性转换为String，可用于HTML形式。 
 * 该标签使用该PropertyEditor来转换传递到此标签中的对象。 
 *  <table> <caption>属性摘要</ caption> <thead> <tr> <th>属性</ th> <th>是否必需？</ th> <th>运行时表达式？</ th> <th>描述< / th> </ tr> </ thead> <tbody> <tr> <td> htmlEscape </ td> <td> false </ td> <td> true </ td> <td>为此标签设置HTML转义，作为布尔值。 
 * 覆盖当前页面的默认HTML转义设置。 
 * </ td> </ tr> <tr> <td>范围</ td> <td> false </ td> <td> true </ td> <td>将结果导出到变量时使用的范围。 
 * 仅当还设置了var时，才使用此属性。 
 * 可能的值是页面，请求，会话和应用程序。 
 * </ td> </ tr> <tr> <td>值</ td> <td> true </ td> <td> true </ td> <td>价值转化。 
 * 这是您要转换的实际对象（例如，日期）。 
 * 使用当前由"spring：bind"标签使用的PropertyEditor。 
 * </ td> </ tr> <tr> <td> var </ td> <td> false </ td> <td> true </ td> <td>将结果绑定到页面，请求，会话或应用程序范围时使用的字符串。 
 * 如果未指定，结果将输出到作者（即通常直接输出到JSP）。 
 * </ td> </ tr> </ tbody> </ table> @author  Alef Arendsen @author  Juergen Hoeller @since 2003年9月20日
 * @see  BindTag
 */
@SuppressWarnings("serial")
public class TransformTag extends HtmlEscapingAwareTag {

	/** the value to transform using the appropriate property editor. */
	/**
	 * 使用适当的属性编辑器转换的值。 
	 * 
	 */
	@Nullable
	private Object value;

	/** the variable to put the result in. */
	/**
	 * 用于放入结果的变量。 
	 * 
	 */
	@Nullable
	private String var;

	/** the scope of the variable the result will be put in. */
	/**
	 * 结果将放入变量的范围。 
	 * 
	 */
	private String scope = TagUtils.SCOPE_PAGE;


	/**
	 * Set the value to transform, using the appropriate PropertyEditor
	 * from the enclosing BindTag.
	 * <p>The value can either be a plain value to transform (a hard-coded String
	 * value in a JSP or a JSP expression), or a JSP EL expression to be evaluated
	 * (transforming the result of the expression).
	 */
	/**
	 * 使用随附的BindTag中的适当PropertyEditor设置要转换的值。 
	 *  <p>该值可以是要转换的纯值（JSP或JSP表达式中的硬编码String值），也可以是要求值的JSP EL表达式（转换表达式的结果）。 
	 * 
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Set PageContext attribute name under which to expose
	 * a variable that contains the result of the transformation.
	 * @see #setScope
	 * @see javax.servlet.jsp.PageContext#setAttribute
	 */
	/**
	 * 设置PageContext属性名称，在该属性名称下公开包含转换结果的变量。 
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


	@Override
	protected final int doStartTagInternal() throws JspException {
		if (this.value != null) {
			// Find the containing EditorAwareTag (e.g. BindTag), if applicable.
			EditorAwareTag tag = (EditorAwareTag) TagSupport.findAncestorWithClass(this, EditorAwareTag.class);
			if (tag == null) {
				throw new JspException("TransformTag can only be used within EditorAwareTag (e.g. BindTag)");
			}

			// OK, let's obtain the editor...
			String result = null;
			PropertyEditor editor = tag.getEditor();
			if (editor != null) {
				// If an editor was found, edit the value.
				editor.setValue(this.value);
				result = editor.getAsText();
			}
			else {
				// Else, just do a toString.
				result = this.value.toString();
			}
			result = htmlEscape(result);
			if (this.var != null) {
				this.pageContext.setAttribute(this.var, result, TagUtils.getScope(this.scope));
			}
			else {
				try {
					// Else, just print it out.
					this.pageContext.getOut().print(result);
				}
				catch (IOException ex) {
					throw new JspException(ex);
				}
			}
		}

		return SKIP_BODY;
	}

}
