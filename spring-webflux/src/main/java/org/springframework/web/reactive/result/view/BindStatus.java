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

package org.springframework.web.reactive.result.view;

import java.beans.PropertyEditor;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.util.HtmlUtils;

/**
 * Simple adapter to expose the bind status of a field or object.
 * Set as a variable by FreeMarker macros and other tag libraries.
 *
 * <p>Obviously, object status representations (i.e. errors at the object level
 * rather than the field level) do not have an expression and a value but only
 * error codes and messages. For simplicity's sake and to be able to use the same
 * tags and macros, the same status class is used for both scenarios.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 5.0
 * @see RequestContext#getBindStatus
 */
/**
 * 简单的适配器，用于显示字段或对象的绑定状态。 
 * 由FreeMarker宏和其他标签库设置为变量。 
 *  <p>很明显，对象状态表示（即对象级别而不是字段级别的错误）没有表达式和值，而只有错误代码和消息。 
 * 为了简单起见，并且为了能够使用相同的标记和宏，两种情况都使用相同的状态类。 
 *  @author  Rossen Stoyanchev @author 于尔根·霍勒（Juergen Hoeller）@从5.0起
 * @see  RequestContext＃getBindStatus
 */
public class BindStatus {

	private final RequestContext requestContext;

	private final String path;

	private final boolean htmlEscape;

	@Nullable
	private final String expression;

	@Nullable
	private final Errors errors;

	private final String[] errorCodes;

	@Nullable
	private String[] errorMessages;

	@Nullable
	private List<? extends ObjectError> objectErrors;

	@Nullable
	private Object value;

	@Nullable
	private Class<?> valueType;

	@Nullable
	private Object actualValue;

	@Nullable
	private PropertyEditor editor;

	@Nullable
	private BindingResult bindingResult;


	/**
	 * Create a new BindStatus instance, representing a field or object status.
	 * @param requestContext the current RequestContext
	 * @param path the bean and property path for which values and errors
	 * will be resolved (e.g. "customer.address.street")
	 * @param htmlEscape whether to HTML-escape error messages and string values
	 * @throws IllegalStateException if no corresponding Errors object found
	 */
	/**
	 * 创建一个新的BindStatus实例，表示一个字段或对象的状态。 
	 *  
	 * @param  requestContext当前RequestContext 
	 * @param 路径将为其解决值和错误的bean和属性路径（例如"customer.address.street"）
	 * @param  htmlEscape是否转义HTML错误消息和字符串值
	 * @throws  IllegalStateException如果找不到对应的Errors对象
	 */
	public BindStatus(RequestContext requestContext, String path, boolean htmlEscape) throws IllegalStateException {
		this.requestContext = requestContext;
		this.path = path;
		this.htmlEscape = htmlEscape;

		// determine name of the object and property
		String beanName;
		int dotPos = path.indexOf('.');
		if (dotPos == -1) {
			// property not set, only the object itself
			beanName = path;
			this.expression = null;
		}
		else {
			beanName = path.substring(0, dotPos);
			this.expression = path.substring(dotPos + 1);
		}

		this.errors = requestContext.getErrors(beanName, false);

		if (this.errors != null) {
			// Usual case: A BindingResult is available as request attribute.
			// Can determine error codes and messages for the given expression.
			// Can use a custom PropertyEditor, as registered by a form controller.
			if (this.expression != null) {
				if ("*".equals(this.expression)) {
					this.objectErrors = this.errors.getAllErrors();
				}
				else if (this.expression.endsWith("*")) {
					this.objectErrors = this.errors.getFieldErrors(this.expression);
				}
				else {
					this.objectErrors = this.errors.getFieldErrors(this.expression);
					this.value = this.errors.getFieldValue(this.expression);
					this.valueType = this.errors.getFieldType(this.expression);
					if (this.errors instanceof BindingResult) {
						this.bindingResult = (BindingResult) this.errors;
						this.actualValue = this.bindingResult.getRawFieldValue(this.expression);
						this.editor = this.bindingResult.findEditor(this.expression, null);
					}
					else {
						this.actualValue = this.value;
					}
				}
			}
			else {
				this.objectErrors = this.errors.getGlobalErrors();
			}
			this.errorCodes = initErrorCodes(this.objectErrors);
		}

		else {
			// No BindingResult available as request attribute:
			// Probably forwarded directly to a form view.
			// Let's do the best we can: extract a plain target if appropriate.
			Object target = requestContext.getModelObject(beanName);
			if (target == null) {
				throw new IllegalStateException(
						"Neither BindingResult nor plain target object for bean name '" +
						beanName + "' available as request attribute");
			}
			if (this.expression != null && !"*".equals(this.expression) && !this.expression.endsWith("*")) {
				BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(target);
				this.value = bw.getPropertyValue(this.expression);
				this.valueType = bw.getPropertyType(this.expression);
				this.actualValue = this.value;
			}
			this.errorCodes = new String[0];
			this.errorMessages = new String[0];
		}

		if (htmlEscape && this.value instanceof String) {
			this.value = HtmlUtils.htmlEscape((String) this.value);
		}
	}

	/**
	 * Extract the error codes from the ObjectError list.
	 */
	/**
	 * 从ObjectError列表中提取错误代码。 
	 * 
	 */
	private static String[] initErrorCodes(List<? extends ObjectError> objectErrors) {
		String[] errorCodes = new String[objectErrors.size()];
		for (int i = 0; i < objectErrors.size(); i++) {
			ObjectError error = objectErrors.get(i);
			errorCodes[i] = error.getCode();
		}
		return errorCodes;
	}


	/**
	 * Return the bean and property path for which values and errors
	 * will be resolved (e.g. "customer.address.street").
	 */
	/**
	 * 返回将解决值和错误的Bean和属性路径（例如"customer.address.street"）。 
	 * 
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Return a bind expression that can be used in HTML forms as input name
	 * for the respective field, or {@code null} if not field-specific.
	 * <p>Returns a bind path appropriate for resubmission, e.g. "address.street".
	 * Note that the complete bind path as required by the bind tag is
	 * "customer.address.street", if bound to a "customer" bean.
	 */
	/**
	 * 返回可以在HTML表单中用作相应字段输入名称的绑定表达式，如果不是特定于字段的，则返回{@code  null}。 
	 *  <p>返回适合重新提交的绑定路径，例如"街道地址"。 
	 * 请注意，如果绑定标记要求，则完整的绑定路径是"customer.address.street"（如果绑定到"customer"bean）。 
	 * 
	 */
	@Nullable
	public String getExpression() {
		return this.expression;
	}

	/**
	 * Return the current value of the field, i.e. either the property value
	 * or a rejected update, or {@code null} if not field-specific.
	 * <p>This value will be an HTML-escaped String if the original value
	 * already was a String.
	 */
	/**
	 * 返回字段的当前值，即属性值或拒绝的更新； 
	 * 如果不是特定于字段，则返回{@code  null}。 
	 *  <p>如果原始值已经是一个字符串，则此值将是一个HTML转义的字符串。 
	 * 
	 */
	@Nullable
	public Object getValue() {
		return this.value;
	}

	/**
	 * Get the '{@code Class}' type of the field. Favor this instead of
	 * '{@code getValue().getClass()}' since '{@code getValue()}' may
	 * return '{@code null}'.
	 */
	/**
	 * 获取字段的"{@code 类}"类型。 
	 * 最好使用它而不是'{@code  getValue（）。 
	 * getClass（）}'，因为'{@code  getValue（）}'可能返回'{@code  null}'。 
	 * 
	 */
	@Nullable
	public Class<?> getValueType() {
		return this.valueType;
	}

	/**
	 * Return the actual value of the field, i.e. the raw property value,
	 * or {@code null} if not available.
	 */
	/**
	 * 返回字段的实际值，即原始属性值，如果不可用，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	public Object getActualValue() {
		return this.actualValue;
	}

	/**
	 * Return a suitable display value for the field, i.e. the stringified
	 * value if not null, and an empty string in case of a null value.
	 * <p>This value will be an HTML-escaped String if the original value
	 * was non-null: the {@code toString} result of the original value
	 * will get HTML-escaped.
	 */
	/**
	 * 返回适合该字段的显示值，即字符串化的值（如果不为null），如果为空，则返回一个空字符串。 
	 *  <p>如果原始值不为空，则该值将为HTML换码的字符串：原始值的{@code  toString}结果将得到HTML换码。 
	 * 
	 */
	public String getDisplayValue() {
		if (this.value instanceof String) {
			return (String) this.value;
		}
		if (this.value != null) {
			return (this.htmlEscape ?
					HtmlUtils.htmlEscape(this.value.toString()) : this.value.toString());
		}
		return "";
	}

	/**
	 * Return if this status represents a field or object error.
	 */
	/**
	 * 如果此状态表示字段或对象错误，则返回。 
	 * 
	 */
	public boolean isError() {
		return (this.errorCodes.length > 0);
	}

	/**
	 * Return the error codes for the field or object, if any.
	 * Returns an empty array instead of null if none.
	 */
	/**
	 * 返回字段或对象的错误代码（如果有）。 
	 * 返回一个空数组，如果没有则返回null。 
	 * 
	 */
	public String[] getErrorCodes() {
		return this.errorCodes;
	}

	/**
	 * Return the first error codes for the field or object, if any.
	 */
	/**
	 * 返回字段或对象的第一个错误代码（如果有）。 
	 * 
	 */
	public String getErrorCode() {
		return (!ObjectUtils.isEmpty(this.errorCodes) ? this.errorCodes[0] : "");
	}

	/**
	 * Return the resolved error messages for the field or object,
	 * if any. Returns an empty array instead of null if none.
	 */
	/**
	 * 返回已解决的字段或对象的错误消息（如果有）。 
	 * 返回一个空数组，如果没有则返回null。 
	 * 
	 */
	public String[] getErrorMessages() {
		return initErrorMessages();
	}

	/**
	 * Return the first error message for the field or object, if any.
	 */
	/**
	 * 返回字段或对象（如果有）的第一条错误消息。 
	 * 
	 */
	public String getErrorMessage() {
		String[] errorMessages = initErrorMessages();
		return (errorMessages.length > 0 ? errorMessages[0] : "");
	}

	/**
	 * Return an error message string, concatenating all messages
	 * separated by the given delimiter.
	 * @param delimiter separator string, e.g. ", " or "<br>"
	 * @return the error message string
	 */
	/**
	 * 返回一个错误消息字符串，将所有由给定分隔符分隔的消息串联起来。 
	 *  
	 * @param 分隔符字符串，例如"，"或"<br>"
	 * @return 错误消息字符串
	 */
	public String getErrorMessagesAsString(String delimiter) {
		return StringUtils.arrayToDelimitedString(initErrorMessages(), delimiter);
	}

	/**
	 * Extract the error messages from the ObjectError list.
	 */
	/**
	 * 从ObjectError列表中提取错误消息。 
	 * 
	 */
	private String[] initErrorMessages() throws NoSuchMessageException {
		if (this.errorMessages == null) {
			if (this.objectErrors != null) {
				this.errorMessages = new String[this.objectErrors.size()];
				for (int i = 0; i < this.objectErrors.size(); i++) {
					ObjectError error = this.objectErrors.get(i);
					this.errorMessages[i] = this.requestContext.getMessage(error, this.htmlEscape);
				}
			}
			else {
				this.errorMessages = new String[0];
			}
		}
		return this.errorMessages;
	}

	/**
	 * Return the Errors instance (typically a BindingResult) that this
	 * bind status is currently associated with.
	 * @return the current Errors instance, or {@code null} if none
	 * @see org.springframework.validation.BindingResult
	 */
	/**
	 * 返回当前与此绑定状态关联的Errors实例（通常是BindingResult）。 
	 *  
	 * @return 当前的Errors实例； 
	 * 如果没有，则为{<@@code> null} 
	 * @see  org.springframework.validation.BindingResult
	 */
	@Nullable
	public Errors getErrors() {
		return this.errors;
	}

	/**
	 * Return the PropertyEditor for the property that this bind status
	 * is currently bound to.
	 * @return the current PropertyEditor, or {@code null} if none
	 */
	/**
	 * 返回此绑定状态当前绑定到的属性的PropertyEditor。 
	 *  
	 * @return 当前的PropertyEditor，如果没有，则为{@code  null}
	 */
	@Nullable
	public PropertyEditor getEditor() {
		return this.editor;
	}

	/**
	 * Find a PropertyEditor for the given value class, associated with
	 * the property that this bound status is currently bound to.
	 * @param valueClass the value class that an editor is needed for
	 * @return the associated PropertyEditor, or {@code null} if none
	 */
	/**
	 * 查找给定值类的PropertyEditor，该属性与此绑定状态当前绑定到的属性相关联。 
	 *  
	 * @param  valueClass 
	 * @return 关联的PropertyEditor所需的编辑器的值类； 
	 * 如果没有，则为{@code  null}
	 */
	@Nullable
	public PropertyEditor findEditor(Class<?> valueClass) {
		return (this.bindingResult != null ?
				this.bindingResult.findEditor(this.expression, valueClass) : null);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("BindStatus: ");
		sb.append("expression=[").append(this.expression).append("]; ");
		sb.append("value=[").append(this.value).append("]");
		if (!ObjectUtils.isEmpty(this.errorCodes)) {
			sb.append("; errorCodes=").append(Arrays.asList(this.errorCodes));
		}
		return sb.toString();
	}

}
