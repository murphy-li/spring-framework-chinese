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
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.util.TagUtils;

/**
 * The {@code <option>} tag renders a single HTML 'option'. Sets 'selected' as
 * appropriate based on bound value.
 *
 * <p><b>Must be used nested inside a {@link SelectTag}.</b>
 *
 * <p>Provides full support for databinding by marking an
 * '{@code option}' as 'selected' if the {@link #setValue value}
 * matches the value bound to the out {@link SelectTag}.
 *
 * <p>The {@link #setValue value} property is required and corresponds to
 * the '{@code value}' attribute of the rendered '{@code option}'.
 *
 * <p>An optional {@link #setLabel label} property can be specified, the
 * value of which corresponds to inner text of the rendered
 * '{@code option}' tag. If no {@link #setLabel label} is specified
 * then the {@link #setValue value} property will be used when rendering
 * the inner text.
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
 * <td><p>cssClass</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>cssErrorClass</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute. Used when the bound field has
 * errors.</p></td>
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
 * <td><p>label</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>lang</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Standard Attribute</p></td>
 * </tr>
 * <tr class="rowColor">
 * <td><p>onclick</p></td>
 * <td><p>false</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Event Attribute</p></td>
 * </tr>
 * <tr class="altColor">
 * <td><p>ondblclick</p></td>
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
 * <tr class="rowColor">
 * <td><p>value</p></td>
 * <td><p>true</p></td>
 * <td><p>true</p></td>
 * <td><p>HTML Optional Attribute</p></td>
 * </tr>
 * </tbody>
 * </table>
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * {@code  <option>}标签呈现单个HTML"选项"。 
 * 根据绑定值适当设置"选定"。 
 *  <p> <b>必须嵌套在{@link  SelectTag}中使用。 
 * </ b> <p>通过将'{@code  option}'标记为'selected'，来提供对数据绑定的完全支持。 
 *  {@link  #setValue value}与绑定到外部{@link  SelectTag}的值匹配。 
 *  <p> {<@link> #setValue值}属性是必需的，它对应于呈现的"{@code 选项}"的"{@code 值}"属性。 
 *  <p>可以指定一个可选的{@link  #setLabel label}属性，该属性的值对应于呈现的"{@code  option}"标记的内部文本。 
 * 如果未指定{@link  #setLabel标签}，则在渲染内部文本时将使用{@link  #setValue value}属性。 
 *  <p> <表> <标题>属性摘要</标题> <thead> <tr> <th class ="colFirst">属性</ th> <th class ="colOne">必需？</ th> <th class ="colOne">运行时表达式？</ th> <th class ="colLast">描述</ th> </ tr> </ thead> <tbody> <tr class ="altColor"> <td> <p > cssClass </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性< / p> </ td> </ tr> <tr class ="rowColor"> <td> <p> cssErrorClass </ p> </ td> <td> <p> false </ p> </ td> < td> <p> true </ p> </ td> <td> <p> HTML可选属性。 
 *  </ p> </ td> </ tr> <tr class ="altColor"> <td> <p> cssStyle </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性</ p> </ td> </ tr> <tr class ="rowColor "> <td> <p> dir </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p>已禁用</ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性。 
 * 将此属性的值设置为'true'将禁用HTML元素。 
 * </ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> htmlEscape </ p> </ td > <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p>启用/禁用呈现值的HTML转义。 
 * </ p> </ td> </ tr> <tr class ="altColor"> <td> <p> id </ p> </ td> <td> <p> false </ p> </ td> <td> < p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p>标签</ p > </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML可选属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> lang </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> onclick </ p> < / td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> ondblclick </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true < / p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="rowColor"> <td> <p> onkeydown </ p> </ td > <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onkeypress </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr > <tr class ="rowColor"> <td> <p> onkeyup </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmousedown </ p> </ td> <td > <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> < tr class ="rowColor"> <td> <p> onmousemove </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmouseout </ p> </ td> <td> < p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr类="rowColor"> <td> <p> onmouseover </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p> onmouseup </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML事件属性</ p> </ td> </ tr> <tr c lass ="rowColor"> <td> <p> tabindex </ p> </ td> <td> <p> false </ p> </ td> <td> <p> true </ p> </ td > <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class ="altColor"> <td> <p>标题</ p> </ td> <td> <p > false </ p> </ td> <td> <p> true </ p> </ td> <td> <p> HTML标准属性</ p> </ td> </ tr> <tr class = "rowColor"> <td> <p>值</ p> </ td> <td> <p> true </ p> </ td> <td> <p> true </ p> </ td> < td> <p> HTML可选属性</ p> </ td> </ tr> </ tbody> </ table> @author  Rob Harrop @author  Juergen Hoeller @since 2.0
 */
@SuppressWarnings("serial")
public class OptionTag extends AbstractHtmlElementBodyTag implements BodyTag {

	/**
	 * The name of the JSP variable used to expose the value for this tag.
	 */
	/**
	 * 用于公开此标记值的JSP变量的名称。 
	 * 
	 */
	public static final String VALUE_VARIABLE_NAME = "value";

	/**
	 * The name of the JSP variable used to expose the display value for this tag.
	 */
	/**
	 * 用于公开此标记的显示值的JSP变量的名称。 
	 * 
	 */
	public static final String DISPLAY_VALUE_VARIABLE_NAME = "displayValue";

	/**
	 * The name of the '{@code selected}' attribute.
	 */
	/**
	 * "{{@code  selected}"属性的名称。 
	 * 
	 */
	private static final String SELECTED_ATTRIBUTE = "selected";

	/**
	 * The name of the '{@code value}' attribute.
	 */
	/**
	 * "{{@code  value}"属性的名称。 
	 * 
	 */
	private static final String VALUE_ATTRIBUTE = VALUE_VARIABLE_NAME;

	/**
	 * The name of the '{@code disabled}' attribute.
	 */
	/**
	 * "{{@code  disabled}"属性的名称。 
	 * 
	 */
	private static final String DISABLED_ATTRIBUTE = "disabled";


	/**
	 * The 'value' attribute of the rendered HTML {@code <option>} tag.
	 */
	/**
	 * 呈现的HTML {@code  <option>}标签的'value'属性。 
	 * 
	 */
	@Nullable
	private Object value;

	/**
	 * The text body of the rendered HTML {@code <option>} tag.
	 */
	/**
	 * 呈现的HTML {@code  <option>}标签的文本主体。 
	 * 
	 */
	@Nullable
	private String label;

	@Nullable
	private Object oldValue;

	@Nullable
	private Object oldDisplayValue;

	private boolean disabled;


	/**
	 * Set the 'value' attribute of the rendered HTML {@code <option>} tag.
	 */
	/**
	 * 设置呈现的HTML {@code  <option>}标签的'value'属性。 
	 * 
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Get the 'value' attribute of the rendered HTML {@code <option>} tag.
	 */
	/**
	 * 获取呈现的HTML {@code  <option>}标签的'value'属性。 
	 * 
	 */
	@Nullable
	protected Object getValue() {
		return this.value;
	}

	/**
	 * Set the value of the '{@code disabled}' attribute.
	 */
	/**
	 * 设置'{@code  disabled}'属性的值。 
	 * 
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Get the value of the '{@code disabled}' attribute.
	 */
	/**
	 * 获取"{{@code  disabled}"属性的值。 
	 * 
	 */
	protected boolean isDisabled() {
		return this.disabled;
	}

	/**
	 * Set the text body of the rendered HTML {@code <option>} tag.
	 * <p>May be a runtime expression.
	 */
	/**
	 * 设置呈现的HTML {@code  <option>}标签的文本主体。 
	 *  <p>可能是运行时表达式。 
	 * 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get the text body of the rendered HTML {@code <option>} tag.
	 */
	/**
	 * 获取呈现的HTML {@code  <option>}标签的文本主体。 
	 * 
	 */
	@Nullable
	protected String getLabel() {
		return this.label;
	}


	@Override
	protected void renderDefaultContent(TagWriter tagWriter) throws JspException {
		Object value = this.pageContext.getAttribute(VALUE_VARIABLE_NAME);
		String label = getLabelValue(value);
		renderOption(value, label, tagWriter);
	}

	@Override
	protected void renderFromBodyContent(BodyContent bodyContent, TagWriter tagWriter) throws JspException {
		Object value = this.pageContext.getAttribute(VALUE_VARIABLE_NAME);
		String label = bodyContent.getString();
		renderOption(value, label, tagWriter);
	}

	/**
	 * Make sure we are under a '{@code select}' tag before proceeding.
	 */
	/**
	 * 在继续之前，请确保我们位于'{@code  select}'标签下。 
	 * 
	 */
	@Override
	protected void onWriteTagContent() {
		assertUnderSelectTag();
	}

	@Override
	protected void exposeAttributes() throws JspException {
		Object value = resolveValue();
		this.oldValue = this.pageContext.getAttribute(VALUE_VARIABLE_NAME);
		this.pageContext.setAttribute(VALUE_VARIABLE_NAME, value);
		this.oldDisplayValue = this.pageContext.getAttribute(DISPLAY_VALUE_VARIABLE_NAME);
		this.pageContext.setAttribute(DISPLAY_VALUE_VARIABLE_NAME, getDisplayString(value, getBindStatus().getEditor()));
	}

	@Override
	protected BindStatus getBindStatus() {
		return (BindStatus) this.pageContext.getAttribute(SelectTag.LIST_VALUE_PAGE_ATTRIBUTE);
	}

	@Override
	protected void removeAttributes() {
		if (this.oldValue != null) {
			this.pageContext.setAttribute(VALUE_ATTRIBUTE, this.oldValue);
			this.oldValue = null;
		}
		else {
			this.pageContext.removeAttribute(VALUE_VARIABLE_NAME);
		}

		if (this.oldDisplayValue != null) {
			this.pageContext.setAttribute(DISPLAY_VALUE_VARIABLE_NAME, this.oldDisplayValue);
			this.oldDisplayValue = null;
		}
		else {
			this.pageContext.removeAttribute(DISPLAY_VALUE_VARIABLE_NAME);
		}
	}

	private void renderOption(Object value, String label, TagWriter tagWriter) throws JspException {
		tagWriter.startTag("option");
		writeOptionalAttribute(tagWriter, "id", resolveId());
		writeOptionalAttributes(tagWriter);
		String renderedValue = getDisplayString(value, getBindStatus().getEditor());
		renderedValue = processFieldValue(getSelectTag().getName(), renderedValue, "option");
		tagWriter.writeAttribute(VALUE_ATTRIBUTE, renderedValue);
		if (isSelected(value)) {
			tagWriter.writeAttribute(SELECTED_ATTRIBUTE, SELECTED_ATTRIBUTE);
		}
		if (isDisabled()) {
			tagWriter.writeAttribute(DISABLED_ATTRIBUTE, "disabled");
		}
		tagWriter.appendValue(label);
		tagWriter.endTag();
	}

	@Override
	protected String autogenerateId() throws JspException {
		return null;
	}

	/**
	 * Return the value of the label for this '{@code option}' element.
	 * <p>If the {@link #setLabel label} property is set then the resolved value
	 * of that property is used, otherwise the value of the {@code resolvedValue}
	 * argument is used.
	 */
	/**
	 * 返回此"{@code 选项}"元素的标签值。 
	 *  <p>如果设置了{@link  #setLabel label}属性，则使用该属性的解析值，否则使用{@code  resolveValue}参数的值。 
	 * 
	 */
	private String getLabelValue(Object resolvedValue) throws JspException {
		String label = getLabel();
		Object labelObj = (label == null ? resolvedValue : evaluate("label", label));
		return getDisplayString(labelObj, getBindStatus().getEditor());
	}

	private void assertUnderSelectTag() {
		TagUtils.assertHasAncestorOfType(this, SelectTag.class, "option", "select");
	}

	private SelectTag getSelectTag() {
		return (SelectTag) findAncestorWithClass(this, SelectTag.class);
	}

	private boolean isSelected(Object resolvedValue) {
		return SelectedValueComparator.isSelected(getBindStatus(), resolvedValue);
	}

	@Nullable
	private Object resolveValue() throws JspException {
		return evaluate(VALUE_VARIABLE_NAME, getValue());
	}

}
