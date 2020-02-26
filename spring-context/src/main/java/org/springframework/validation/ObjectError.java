/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Encapsulates an object error, that is, a global reason for rejecting
 * an object.
 *
 * <p>See the {@link DefaultMessageCodesResolver} javadoc for details on
 * how a message code list is built for an {@code ObjectError}.
 *
 * @author Juergen Hoeller
 * @since 10.03.2003
 * @see FieldError
 * @see DefaultMessageCodesResolver
 */
/**
 * 封装对象错误，即拒绝对象的全局原因。 
 *  <p>有关如何为{@code  ObjectError}构建消息代码列表的详细信息，请参见{@link  DefaultMessageCodesResolver} Javadoc。 
 *  @author  Juergen Hoeller @2003年10月10日以来
 * @see  FieldError 
 * @see  DefaultMessageCodesResolver
 */
@SuppressWarnings("serial")
public class ObjectError extends DefaultMessageSourceResolvable {

	private final String objectName;

	@Nullable
	private transient Object source;


	/**
	 * Create a new instance of the ObjectError class.
	 * @param objectName the name of the affected object
	 * @param defaultMessage the default message to be used to resolve this message
	 */
	/**
	 * 创建一个ObjectError类的新实例。 
	 *  
	 * @param  objectName受影响对象的名称
	 * @param  defaultMessage用于解决此消息的默认消息
	 */
	public ObjectError(String objectName, String defaultMessage) {
		this(objectName, null, null, defaultMessage);
	}

	/**
	 * Create a new instance of the ObjectError class.
	 * @param objectName the name of the affected object
	 * @param codes the codes to be used to resolve this message
	 * @param arguments	the array of arguments to be used to resolve this message
	 * @param defaultMessage the default message to be used to resolve this message
	 */
	/**
	 * 创建一个ObjectError类的新实例。 
	 *  
	 * @param  objectName受影响对象的名称
	 * @param 编码用于解决此消息的代码
	 * @param  arguments用于解决此消息的参数数组
	 * @param  defaultMessage默认消息为用于解决此消息
	 */
	public ObjectError(
			String objectName, @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) {

		super(codes, arguments, defaultMessage);
		Assert.notNull(objectName, "Object name must not be null");
		this.objectName = objectName;
	}


	/**
	 * Return the name of the affected object.
	 */
	/**
	 * 返回受影响对象的名称。 
	 * 
	 */
	public String getObjectName() {
		return this.objectName;
	}

	/**
	 * Preserve the source behind this error: possibly an {@link Exception}
	 * (typically {@link org.springframework.beans.PropertyAccessException})
	 * or a Bean Validation {@link javax.validation.ConstraintViolation}.
	 * <p>Note that any such source object is being stored as transient:
	 * that is, it won't be part of a serialized error representation.
	 * @param source the source object
	 * @since 5.0.4
	 */
	/**
	 * 保留此错误的根源：可能是{@link 异常}（通常为{@link  org.springframework.beans.PropertyAccessException}）或Bean验证{@link  javax.validation.ConstraintViolation}。 
	 *  <p>请注意，任何此类源对象都将存储为临时对象：也就是说，它不会成为序列化错误表示的一部分。 
	 *  
	 * @param 源源对象@since 5.0.4起
	 */
	public void wrap(Object source) {
		if (this.source != null) {
			throw new IllegalStateException("Already wrapping " + this.source);
		}
		this.source = source;
	}

	/**
	 * Unwrap the source behind this error: possibly an {@link Exception}
	 * (typically {@link org.springframework.beans.PropertyAccessException})
	 * or a Bean Validation {@link javax.validation.ConstraintViolation}.
	 * <p>The cause of the outermost exception will be introspected as well,
	 * e.g. the underlying conversion exception or exception thrown from a setter
	 * (instead of having to unwrap the {@code PropertyAccessException} in turn).
	 * @return the source object of the given type
	 * @throws IllegalArgumentException if no such source object is available
	 * (i.e. none specified or not available anymore after deserialization)
	 * @since 5.0.4
	 */
	/**
	 * 展开此错误背后的源代码：可能是{@link 异常}（通常是{@link  org.springframework.beans.PropertyAccessException}）或Bean验证{@link  javax.validation.ConstraintViolation}。 
	 *  <p>最外层异常的原因也会被自省，例如基础转换异常或从setter抛出的异常（而不必依次解开{@code  PropertyAccessException}）。 
	 *  
	 * @return 给定类型的源对象
	 * @throws  IllegalArgumentException如果没有这样的源对象（即未指定或反序列化后不再可用）@5.0.4开始
	 */
	public <T> T unwrap(Class<T> sourceType) {
		if (sourceType.isInstance(this.source)) {
			return sourceType.cast(this.source);
		}
		else if (this.source instanceof Throwable) {
			Throwable cause = ((Throwable) this.source).getCause();
			if (sourceType.isInstance(cause)) {
				return sourceType.cast(cause);
			}
		}
		throw new IllegalArgumentException("No source object of the given type available: " + sourceType);
	}

	/**
	 * Check the source behind this error: possibly an {@link Exception}
	 * (typically {@link org.springframework.beans.PropertyAccessException})
	 * or a Bean Validation {@link javax.validation.ConstraintViolation}.
	 * <p>The cause of the outermost exception will be introspected as well,
	 * e.g. the underlying conversion exception or exception thrown from a setter
	 * (instead of having to unwrap the {@code PropertyAccessException} in turn).
	 * @return whether this error has been caused by a source object of the given type
	 * @since 5.0.4
	 */
	/**
	 * 检查此错误背后的源：可能是{@link 异常}（通常为{@link  org.springframework.beans.PropertyAccessException}）或Bean验证{@link  javax.validation.ConstraintViolation}。 
	 *  <p>最外层异常的原因也会被自省，例如基础转换异常或从setter抛出的异常（而不必依次解开{@code  PropertyAccessException}）。 
	 *  
	 * @return 此错误是否由给定类型的源对象引起，自5.0.4起
	 */
	public boolean contains(Class<?> sourceType) {
		return (sourceType.isInstance(this.source) ||
				(this.source instanceof Throwable && sourceType.isInstance(((Throwable) this.source).getCause())));
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || other.getClass() != getClass() || !super.equals(other)) {
			return false;
		}
		ObjectError otherError = (ObjectError) other;
		return getObjectName().equals(otherError.getObjectName());
	}

	@Override
	public int hashCode() {
		return (29 * super.hashCode() + getObjectName().hashCode());
	}

	@Override
	public String toString() {
		return "Error in object '" + this.objectName + "': " + resolvableToString();
	}

}
