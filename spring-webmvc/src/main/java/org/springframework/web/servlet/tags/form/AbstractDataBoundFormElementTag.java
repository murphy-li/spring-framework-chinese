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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.springframework.web.servlet.tags.EditorAwareTag;
import org.springframework.web.servlet.tags.NestedPathTag;

/**
 * Base tag for all data-binding aware JSP form tags.
 *
 * <p>Provides the common {@link #setPath path} and {@link #setId id} properties.
 * Provides sub-classes with utility methods for accessing the {@link BindStatus}
 * of their bound value and also for {@link #writeOptionalAttribute interacting}
 * with the {@link TagWriter}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 所有了解数据绑定的JSP表单标签的基本标签。 
 *  <p>提供公用的{@link  #setPath path}和{@link  #setId id}属性。 
 * 为子类提供实用程序方法，以访问其绑定值的{@link  BindStatus}以及与{@link  TagWriter}进行{@link  #writeOptionalAttribute interacting}的方法。 
 *  @author 罗布·哈罗普（Rob Harrop）@author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public abstract class AbstractDataBoundFormElementTag extends AbstractFormTag implements EditorAwareTag {

	/**
	 * Name of the exposed path variable within the scope of this tag: "nestedPath".
	 * Same value as {@link org.springframework.web.servlet.tags.NestedPathTag#NESTED_PATH_VARIABLE_NAME}.
	 */
	/**
	 * 在此标签范围内的公开路径变量的名称："nestedPath"。 
	 * 与{@link  org.springframework.web.servlet.tags.NestedPathTag＃NESTED_PATH_VARIABLE_NAME}的值相同。 
	 * 
	 */
	protected static final String NESTED_PATH_VARIABLE_NAME = NestedPathTag.NESTED_PATH_VARIABLE_NAME;


	/**
	 * The property path from the {@link FormTag#setModelAttribute form object}.
	 */
	/**
	 * {@link  FormTag＃setModelAttribute表单对象}的属性路径。 
	 * 
	 */
	@Nullable
	private String path;

	/**
	 * The value of the '{@code id}' attribute.
	 */
	/**
	 * "{{@@code> id}"属性的值。 
	 * 
	 */
	@Nullable
	private String id;

	/**
	 * The {@link BindStatus} of this tag.
	 */
	/**
	 * 此标记的{@link  BindStatus}。 
	 * 
	 */
	@Nullable
	private BindStatus bindStatus;


	/**
	 * Set the property path from the {@link FormTag#setModelAttribute form object}.
	 * May be a runtime expression.
	 */
	/**
	 * 从{@link  FormTag＃setModelAttribute表单对象}设置属性路径。 
	 * 可能是运行时表达式。 
	 * 
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Get the {@link #evaluate resolved} property path for the
	 * {@link FormTag#setModelAttribute form object}.
	 */
	/**
	 * 获取{@link  FormTag＃setModelAttribute表单对象}的{@link  #evaluate解决的}属性路径。 
	 * 
	 */
	protected final String getPath() throws JspException {
		String resolvedPath = (String) evaluate("path", this.path);
		return (resolvedPath != null ? resolvedPath : "");
	}

	/**
	 * Set the value of the '{@code id}' attribute.
	 * <p>May be a runtime expression; defaults to the value of {@link #getName()}.
	 * Note that the default value may not be valid for certain tags.
	 */
	/**
	 * 设置'{@code  id}'属性的值。 
	 *  <p>可能是运行时表达式； 
	 * 默认为{@link  #getName（）}的值。 
	 * 请注意，默认值对于某些标签可能无效。 
	 * 
	 */
	@Override
	public void setId(@Nullable String id) {
		this.id = id;
	}

	/**
	 * Get the value of the '{@code id}' attribute.
	 */
	/**
	 * 获取"{@code  id}"属性的值。 
	 * 
	 */
	@Override
	@Nullable
	public String getId() {
		return this.id;
	}


	/**
	 * Writes the default set of attributes to the supplied {@link TagWriter}.
	 * Further abstract sub-classes should override this method to add in
	 * any additional default attributes but <strong>must</strong> remember
	 * to call the {@code super} method.
	 * <p>Concrete sub-classes should call this method when/if they want
	 * to render default attributes.
	 * @param tagWriter the {@link TagWriter} to which any attributes are to be written
	 */
	/**
	 * 将默认属性集写入提供的{@link  TagWriter}。 
	 * 进一步的抽象子类应重写此方法以添加任何其他默认属性，但<strong>必须</ strong>请记住调用{@code  super}方法。 
	 *  <p>具体子类在/如果要呈现默认属性时应调用此方法。 
	 *  
	 * @param  tagWriter将任何属性写入的{@link  TagWriter}
	 */
	protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
		writeOptionalAttribute(tagWriter, "id", resolveId());
		writeOptionalAttribute(tagWriter, "name", getName());
	}

	/**
	 * Determine the '{@code id}' attribute value for this tag,
	 * autogenerating one if none specified.
	 * @see #getId()
	 * @see #autogenerateId()
	 */
	/**
	 * 确定此标记的'{@code  id}'属性值，如果未指定则自动生成一个。 
	 *  
	 * @see  #getId（）
	 * @see  #autogenerateId（）
	 */
	@Nullable
	protected String resolveId() throws JspException {
		Object id = evaluate("id", getId());
		if (id != null) {
			String idString = id.toString();
			return (StringUtils.hasText(idString) ? idString : null);
		}
		return autogenerateId();
	}

	/**
	 * Autogenerate the '{@code id}' attribute value for this tag.
	 * <p>The default implementation simply delegates to {@link #getName()},
	 * deleting invalid characters (such as "[" or "]").
	 */
	/**
	 * 为此标签自动生成'{@code  id}'属性值。 
	 *  <p>默认实现只是将委托委托给{@link  #getName（）}，删除无效字符（例如"["或"]"）。 
	 * 
	 */
	@Nullable
	protected String autogenerateId() throws JspException {
		String name = getName();
		return (name != null ? StringUtils.deleteAny(name, "[]") : null);
	}

	/**
	 * Get the value for the HTML '{@code name}' attribute.
	 * <p>The default implementation simply delegates to
	 * {@link #getPropertyPath()} to use the property path as the name.
	 * For the most part this is desirable as it links with the server-side
	 * expectation for data binding. However, some subclasses may wish to change
	 * the value of the '{@code name}' attribute without changing the bind path.
	 * @return the value for the HTML '{@code name}' attribute
	 */
	/**
	 * 获取HTML'{@code  name}'属性的值。 
	 *  <p>默认实现只是将委托委托给{@link  #getPropertyPath（）}以使用属性路径作为名称。 
	 * 在大多数情况下，这是可取的，因为它与服务器端对数据绑定的期望相关联。 
	 * 但是，某些子类可能希望更改'{@code  name}'属性的值而不更改绑定路径。 
	 *  
	 * @return  HTML"{<@code>名称}"属性的值
	 */
	@Nullable
	protected String getName() throws JspException {
		return getPropertyPath();
	}

	/**
	 * Get the {@link BindStatus} for this tag.
	 */
	/**
	 * 获取此标签的{@link  BindStatus}。 
	 * 
	 */
	protected BindStatus getBindStatus() throws JspException {
		if (this.bindStatus == null) {
			// HTML escaping in tags is performed by the ValueFormatter class.
			String nestedPath = getNestedPath();
			String pathToUse = (nestedPath != null ? nestedPath + getPath() : getPath());
			if (pathToUse.endsWith(PropertyAccessor.NESTED_PROPERTY_SEPARATOR)) {
				pathToUse = pathToUse.substring(0, pathToUse.length() - 1);
			}
			this.bindStatus = new BindStatus(getRequestContext(), pathToUse, false);
		}
		return this.bindStatus;
	}

	/**
	 * Get the value of the nested path that may have been exposed by the
	 * {@link NestedPathTag}.
	 */
	/**
	 * 获取{@link  NestedPathTag}可能已公开的嵌套路径的值。 
	 * 
	 */
	@Nullable
	protected String getNestedPath() {
		return (String) this.pageContext.getAttribute(NESTED_PATH_VARIABLE_NAME, PageContext.REQUEST_SCOPE);
	}

	/**
	 * Build the property path for this tag, including the nested path
	 * but <i>not</i> prefixed with the name of the form attribute.
	 * @see #getNestedPath()
	 * @see #getPath()
	 */
	/**
	 * 为此标签构建属性路径，包括嵌套路径，但<i> not </ i>前缀为form属性的名称。 
	 *  
	 * @see  #getNestedPath（）
	 * @see  #getPath（）
	 */
	protected String getPropertyPath() throws JspException {
		String expression = getBindStatus().getExpression();
		return (expression != null ? expression : "");
	}

	/**
	 * Get the bound value.
	 * @see #getBindStatus()
	 */
	/**
	 * 获取绑定值。 
	 *  
	 * @see  #getBindStatus（）
	 */
	@Nullable
	protected final Object getBoundValue() throws JspException {
		return getBindStatus().getValue();
	}

	/**
	 * Get the {@link PropertyEditor}, if any, in use for value bound to this tag.
	 */
	/**
	 * 获取{@link  PropertyEditor}（如果有），用于绑定到此标签的值。 
	 * 
	 */
	@Nullable
	protected PropertyEditor getPropertyEditor() throws JspException {
		return getBindStatus().getEditor();
	}

	/**
	 * Exposes the {@link PropertyEditor} for {@link EditorAwareTag}.
	 * <p>Use {@link #getPropertyEditor()} for internal rendering purposes.
	 */
	/**
	 * 公开{@link  EditorAwareTag}的{@link  PropertyEditor}。 
	 *  <p>将{@link  #getPropertyEditor（）}用于内部渲染。 
	 * 
	 */
	@Override
	@Nullable
	public final PropertyEditor getEditor() throws JspException {
		return getPropertyEditor();
	}

	/**
	 * Get a display String for the given value, converted by a PropertyEditor
	 * that the BindStatus may have registered for the value's Class.
	 */
	/**
	 * 获取给定值的显示字符串，由属性编辑器转换为BindStatus可能已为该值的类注册的给定值。 
	 * 
	 */
	protected String convertToDisplayString(@Nullable Object value) throws JspException {
		PropertyEditor editor = (value != null ? getBindStatus().findEditor(value.getClass()) : null);
		return getDisplayString(value, editor);
	}

	/**
	 * Process the given form field through a {@link RequestDataValueProcessor}
	 * instance if one is configured or otherwise returns the same value.
	 */
	/**
	 * 如果配置了{@link  RequestDataValueProcessor}实例，则处理给定的表单字段，否则返回相同的值。 
	 * 
	 */
	protected final String processFieldValue(@Nullable String name, String value, String type) {
		RequestDataValueProcessor processor = getRequestContext().getRequestDataValueProcessor();
		ServletRequest request = this.pageContext.getRequest();
		if (processor != null && request instanceof HttpServletRequest) {
			value = processor.processFormFieldValue((HttpServletRequest) request, name, value, type);
		}
		return value;
	}

	/**
	 * Disposes of the {@link BindStatus} instance.
	 */
	/**
	 * 处置{@link  BindStatus}实例。 
	 * 
	 */
	@Override
	public void doFinally() {
		super.doFinally();
		this.bindStatus = null;
	}

}
