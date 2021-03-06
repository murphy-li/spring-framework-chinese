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

package org.springframework.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.lang.Nullable;

/**
 * The {@code <textarea>} tag renders an HTML 'textarea'.
 *
 * <p>
 * <table>
 * <caption>Attribute Summary</caption>
 * <thead>
 * <tr>
 * <th class="colFirst">Attribute</th>
 * <th class="colOne">Required?</th>
 * <th class="colOne">Runtime Expression?</th>
 * <th class="colLast">Description</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="altColor">
 * <td><p>accesskey</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>cols</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Required Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>cssClass</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>cssErrorClass</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute. Used when the bound field has errors.</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>cssStyle</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>dir</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>disabled</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute. Setting the value of this attribute to 'true'
 * will disable the HTML element.</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>htmlEscape</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>Enable/disable HTML escaping of rendered values.</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>id</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>lang</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onblur</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onchange</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onclick</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>ondblclick</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onfocus</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onkeydown</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onkeypress</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onkeyup</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onmousedown</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onmousemove</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onmouseout</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onmouseover</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>onmouseup</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onselect</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>path</p></td>
 * <td><p>true</p></td>
 * <td><p>true</p></td>
 * <td><p>Path to property for data binding</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>readonly</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute. Setting the value of this attribute to 'true'
 * will make the HTML element readonly.</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>rows</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Required Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>tabindex</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>title</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * {@code  <textarea>}标签呈现HTML'textarea'。 
 *  <p> <表> <标题>属性摘要</标题> <thead> <tr> <th class ="colFirst">属性</ th> <th class ="colOne">必需？</ th> <th class ="colOne">运行时表达式？</ th> <th class ="colLast">描述</ th> </ tr> </ thead> <tbody> <tr class ="altColor"> <td> <p > accesskey </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性< / p> </ td> </ tr> <tr class ="rowColor"> <td> <p> cols </ p> </ td> <td> <p> false </ p> </ td> < td> <p> true </ p> </ td> <td> <p> HTML必需属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> cssClass </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性</ p > </ td> </ tr> <tr class ="rowColor"> <td> <p> cssErrorClass </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性。 
 *  </ p> </ td> </ tr> <tr class ="altColor"> <td> <p> cssStyle </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性</ p> </ td> </ tr> <tr class ="rowColor "> <td> <p> dir </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p>已禁用</ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性。 
 * 将此属性的值设置为'true'将禁用HTML元素。 
 * </ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> htmlEscape </ p> </ td > <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p>启用/禁用呈现值的HTML转义。 
 * </ p> </ td> </ tr> <tr class ="altColor"> <td> <p> id </ p> </ td> <td> <p> false </ p> </ td> <td> < p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> lang </ p > </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onblur </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> onchange </ p> < / td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onclick </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true < / p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> ondblclick </ p> </ td > <td> <p> false </ p> </ td> <td> <p> true </ p> < / td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onfocus </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> onkeydown </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td > <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onkeypress </ p> </ td> <td> <p > false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class = "rowColor"> <td> <p> onkeyup </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> < td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmousedown </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor "> <td> <p> onmousemove </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmouseout </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="r owColor"> <td> <p> onmouseover </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td > <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmouseup </ p> </ td> <td> <p> false < / p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> onselect </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> < p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p>路径</ p> </ td> <td> <p> true </ p > </ td> <td> <p> true </ p> </ td> <td> <p>数据绑定属性的路径</ p> </ td> </ tr> <tr class ="rowColor "> <td> <p>只读</ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性。 
 * 将此属性的值设置为'true'将使HTML元素变为只读。 
 * </ p> </ td> </ tr> <tr class ="altColor"> <td> <p>行</ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML必需属性</ p> </ td> < / tr> <tr class ="rowColor"> <td> <p> tabindex </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p>标题</ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr > </ tbody> </ table> @author  Rob Harrop @author  Juergen Hoeller @since 2.0
 */
@SuppressWarnings("serial")
public class TextareaTag extends AbstractHtmlInputElementTag {

	public static final String ROWS_ATTRIBUTE = "rows";

	public static final String COLS_ATTRIBUTE = "cols";

	public static final String ONSELECT_ATTRIBUTE = "onselect";


	@Nullable
	private String rows;

	@Nullable
	private String cols;

	@Nullable
	private String onselect;


	/**
	 * Set the value of the '{@code rows}' attribute.
	 * May be a runtime expression.
	 */
	/**
	 * 设置"{@code 行}"属性的值。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * Get the value of the '{@code rows}' attribute.
	 */
	/**
	 * 获取"{{@@code>行}"属性的值。 
	 * 
	 */
	@Nullable
	protected String getRows() {
		return this.rows;
	}

	/**
	 * Set the value of the '{@code cols}' attribute.
	 * May be a runtime expression.
	 */
	/**
	 * 设置'{@code  cols}'属性的值。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * Get the value of the '{@code cols}' attribute.
	 */
	/**
	 * 获取'{@code  cols}'属性的值。 
	 * 
	 */
	@Nullable
	protected String getCols() {
		return this.cols;
	}

	/**
	 * Set the value of the '{@code onselect}' attribute.
	 * May be a runtime expression.
	 */
	/**
	 * 设置'{@code  onselect}'属性的值。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * Get the value of the '{@code onselect}' attribute.
	 */
	/**
	 * 获取'{@code  onselect}'属性的值。 
	 * 
	 */
	@Nullable
	protected String getOnselect() {
		return this.onselect;
	}


	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("textarea");
		writeDefaultAttributes(tagWriter);
		writeOptionalAttribute(tagWriter, ROWS_ATTRIBUTE, getRows());
		writeOptionalAttribute(tagWriter, COLS_ATTRIBUTE, getCols());
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		String value = getDisplayString(getBoundValue(), getPropertyEditor());
		tagWriter.appendValue("\r\n" + processFieldValue(getName(), value, "textarea"));
		tagWriter.endTag();
		return SKIP_BODY;
	}

}
