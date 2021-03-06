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

import java.beans.PropertyEditor;

import javax.servlet.jsp.JspException;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;

/**
 * Base class for all JSP form tags. Provides utility methods for
 * null-safe EL evaluation and for accessing and working with a {@link TagWriter}.
 *
 * <p>Subclasses should implement the {@link #writeTagContent(TagWriter)} to perform
 * actual tag rendering.
 *
 * <p>Subclasses (or test classes) can override the {@link #createTagWriter()} method to
 * redirect output to a {@link java.io.Writer} other than the {@link javax.servlet.jsp.JspWriter}
 * associated with the current {@link javax.servlet.jsp.PageContext}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 所有JSP表单标签的基类。 
 * 提供用于null安全EL评估以及访问和使用{@link  TagWriter}的实用方法。 
 *  <p>子类应实现{@link  #writeTagContent（TagWriter）}以执行实际的标签呈现。 
 *  <p>子类（或测试类）可以覆盖{@link  #createTagWriter（）}方法，以将输出重定向到{@link  javax.servlet之外的{@link  java.io.Writer}与当前{@link  javax.servlet.jsp.PageContext}关联的.jsp.JspWriter}。 
 *  @author 罗布·哈罗普（Rob Harrop）@author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public abstract class AbstractFormTag extends HtmlEscapingAwareTag {

	/**
	 * Evaluate the supplied value for the supplied attribute name.
	 * <p>The default implementation simply returns the given value as-is.
	 */
	/**
	 * 评估提供的属性名称的提供的值。 
	 *  <p>默认实现只是按原样返回给定值。 
	 * 
	 */
	@Nullable
	protected Object evaluate(String attributeName, @Nullable Object value) throws JspException {
		return value;
	}

	/**
	 * Optionally writes the supplied value under the supplied attribute name into the supplied
	 * {@link TagWriter}. In this case, the supplied value is {@link #evaluate evaluated} first
	 * and then the {@link ObjectUtils#getDisplayString String representation} is written as the
	 * attribute value. If the resultant {@code String} representation is {@code null}
	 * or empty, no attribute is written.
	 * @see TagWriter#writeOptionalAttributeValue(String, String)
	 */
	/**
	 * （可选）将提供的属性名称下的提供的值写入提供的{@link  TagWriter}中。 
	 * 在这种情况下，提供的值首先是{@link  #evaluate评估}，然后将{@link  ObjectUtils＃getDisplayString字符串表示形式}写为属性值。 
	 * 如果结果{@code 字符串}表示形式为{@code  null}或为空，则不会写入任何属性。 
	 *  
	 * @see  TagWriter＃writeOptionalAttributeValue（字符串，字符串）
	 */
	protected final void writeOptionalAttribute(TagWriter tagWriter, String attributeName, @Nullable String value)
			throws JspException {

		if (value != null) {
			tagWriter.writeOptionalAttributeValue(attributeName, getDisplayString(evaluate(attributeName, value)));
		}
	}

	/**
	 * Create the {@link TagWriter} which all output will be written to. By default,
	 * the {@link TagWriter} writes its output to the {@link javax.servlet.jsp.JspWriter}
	 * for the current {@link javax.servlet.jsp.PageContext}. Subclasses may choose to
	 * change the {@link java.io.Writer} to which output is actually written.
	 */
	/**
	 * 创建{@link  TagWriter}，所有输出都将写入其中。 
	 * 默认情况下，{<@link> TagWriter}将其输出写入当前{@link  javax.servlet.jsp.PageContext}的{@link  javax.servlet.jsp.JspWriter}。 
	 * 子类可以选择更改实际写入输出的{@link  java.io.Writer}。 
	 * 
	 */
	protected TagWriter createTagWriter() {
		return new TagWriter(this.pageContext);
	}

	/**
	 * Provide a simple template method that calls {@link #createTagWriter()} and passes
	 * the created {@link TagWriter} to the {@link #writeTagContent(TagWriter)} method.
	 * @return the value returned by {@link #writeTagContent(TagWriter)}
	 */
	/**
	 * 提供一个简单的模板方法，该方法调用{@link  #createTagWriter（）}并将创建的{@link  TagWriter}传递给{@link  #writeTagContent（TagWriter）}方法。 
	 *  
	 * @return  {@link  #writeTagContent（TagWriter）}返回的值
	 */
	@Override
	protected final int doStartTagInternal() throws Exception {
		return writeTagContent(createTagWriter());
	}

	/**
	 * Get the display value of the supplied {@code Object}, HTML escaped
	 * as required. This version is <strong>not</strong> {@link PropertyEditor}-aware.
	 */
	/**
	 * 获取提供的{@code  Object}的显示值，并根据需要转义HTML。 
	 * 此版本<strong>不</ strong>可识别{@link  PropertyEditor}。 
	 * 
	 */
	protected String getDisplayString(@Nullable Object value) {
		return ValueFormatter.getDisplayString(value, isHtmlEscape());
	}

	/**
	 * Get the display value of the supplied {@code Object}, HTML escaped
	 * as required. If the supplied value is not a {@link String} and the supplied
	 * {@link PropertyEditor} is not null then the {@link PropertyEditor} is used
	 * to obtain the display value.
	 */
	/**
	 * 获取提供的{@code  Object}的显示值，并根据需要转义HTML。 
	 * 如果提供的值不是{@link 字符串}，并且提供的{@link  PropertyEditor}不为null，则使用{@link  PropertyEditor}获取显示值。 
	 * 
	 */
	protected String getDisplayString(@Nullable Object value, @Nullable PropertyEditor propertyEditor) {
		return ValueFormatter.getDisplayString(value, propertyEditor, isHtmlEscape());
	}

	/**
	 * Overridden to default to {@code true} in case of no explicit default given.
	 */
	/**
	 * 如果没有明确的默认值，则覆盖默认值{{@code> true}。 
	 * 
	 */
	@Override
	protected boolean isDefaultHtmlEscape() {
		Boolean defaultHtmlEscape = getRequestContext().getDefaultHtmlEscape();
		return (defaultHtmlEscape == null || defaultHtmlEscape.booleanValue());
	}


	/**
	 * Subclasses should implement this method to perform tag content rendering.
	 * @return valid tag render instruction as per {@link javax.servlet.jsp.tagext.Tag#doStartTag()}.
	 */
	/**
	 * 子类应实现此方法以执行标记内容呈现。 
	 * 根据{@link  javax.servlet.jsp.tagext.Tag＃doStartTag（）}的
	 * @return 有效标签呈现指令。 
	 * 
	 */
	protected abstract int writeTagContent(TagWriter tagWriter) throws JspException;

}
