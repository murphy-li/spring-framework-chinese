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
import org.springframework.util.Assert;

/**
 * Abstract base class to provide common methods for implementing databinding-aware
 * JSP tags for rendering a <i>single</i> HTML '{@code input}' element with a
 * '{@code type}' of '{@code checkbox}' or '{@code radio}'.
 *
 * @author Juergen Hoeller
 * @since 2.5.2
 */
/**
 * 抽象基类，提供用于实现感知数据绑定的JSP标记的通用方法，以呈现<i>单个</ i> HTML的"{@code  input}"元素和"{{@@code> type}"元素@code 复选框}"或"{@code 收音机}"。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@2.5.2起
 */
@SuppressWarnings("serial")
public abstract class AbstractSingleCheckedElementTag extends AbstractCheckedElementTag {

	/**
	 * The value of the '{@code value}' attribute.
	 */
	/**
	 * "{@code  value}"属性的值。 
	 * 
	 */
	@Nullable
	private Object value;

	/**
	 * The value of the '{@code label}' attribute.
	 */
	/**
	 * "{{@@code>标签}"属性的值。 
	 * 
	 */
	@Nullable
	private Object label;


	/**
	 * Set the value of the '{@code value}' attribute.
	 * May be a runtime expression.
	 */
	/**
	 * 设置"{@code  value}"属性的值。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Get the value of the '{@code value}' attribute.
	 */
	/**
	 * 获取"{@code  value}"属性的值。 
	 * 
	 */
	@Nullable
	protected Object getValue() {
		return this.value;
	}

	/**
	 * Set the value of the '{@code label}' attribute.
	 * May be a runtime expression.
	 */
	/**
	 * 设置"{@code 标签}"属性的值。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setLabel(Object label) {
		this.label = label;
	}

	/**
	 * Get the value of the '{@code label}' attribute.
	 */
	/**
	 * 获取"{@code 标签}"属性的值。 
	 * 
	 */
	@Nullable
	protected Object getLabel() {
		return this.label;
	}


	/**
	 * Renders the '{@code input(radio)}' element with the configured
	 * {@link #setValue(Object) value}. Marks the element as checked if the
	 * value matches the {@link #getValue bound value}.
	 */
	/**
	 * 使用配置的{@link  #setValue（Object）value}渲染'{@code  input（radio）}'元素。 
	 * 如果值与{@link  #getValue绑定值}相匹配，则将元素标记为已选中。 
	 * 
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("input");
		String id = resolveId();
		writeOptionalAttribute(tagWriter, "id", id);
		writeOptionalAttribute(tagWriter, "name", getName());
		writeOptionalAttributes(tagWriter);
		writeTagDetails(tagWriter);
		tagWriter.endTag();

		Object resolvedLabel = evaluate("label", getLabel());
		if (resolvedLabel != null) {
			Assert.state(id != null, "Label id is required");
			tagWriter.startTag("label");
			tagWriter.writeAttribute("for", id);
			tagWriter.appendValue(convertToDisplayString(resolvedLabel));
			tagWriter.endTag();
		}

		return SKIP_BODY;
	}

	/**
	 * Write the details for the given primary tag:
	 * i.e. special attributes and the tag's value.
	 */
	/**
	 * 写下给定主要标签的详细信息：即特殊属性和标签的值。 
	 * 
	 */
	protected abstract void writeTagDetails(TagWriter tagWriter) throws JspException;

}
