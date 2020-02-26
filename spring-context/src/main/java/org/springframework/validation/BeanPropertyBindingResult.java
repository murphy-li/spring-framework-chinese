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

package org.springframework.validation;

import java.io.Serializable;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.lang.Nullable;

/**
 * Default implementation of the {@link Errors} and {@link BindingResult}
 * interfaces, for the registration and evaluation of binding errors on
 * JavaBean objects.
 *
 * <p>Performs standard JavaBean property access, also supporting nested
 * properties. Normally, application code will work with the
 * {@code Errors} interface or the {@code BindingResult} interface.
 * A {@link DataBinder} returns its {@code BindingResult} via
 * {@link DataBinder#getBindingResult()}.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see DataBinder#getBindingResult()
 * @see DataBinder#initBeanPropertyAccess()
 * @see DirectFieldBindingResult
 */
/**
 * {@link 错误}和{@link  BindingResult}接口的默认实现，用于注册和评估JavaBean对象上的绑定错误。 
 *  <p>执行标准JavaBean属性访问，还支持嵌套属性。 
 * 通常，应用程序代码将与{@code 错误}接口或{@code  BindingResult}接口一起使用。 
 *  {@link  DataBinder}通过{@link  DataBinder＃getBindingResult（）}返回其{@code  BindingResult}。 
 *  @author  Juergen Hoeller @自2.0起
 * @see  DataBinder＃getBindingResult（）
 * @see  DataBinder＃initBeanPropertyAccess（）
 * @see  DirectFieldBindingResult
 */
@SuppressWarnings("serial")
public class BeanPropertyBindingResult extends AbstractPropertyBindingResult implements Serializable {

	@Nullable
	private final Object target;

	private final boolean autoGrowNestedPaths;

	private final int autoGrowCollectionLimit;

	@Nullable
	private transient BeanWrapper beanWrapper;


	/**
	 * Creates a new instance of the {@link BeanPropertyBindingResult} class.
	 * @param target the target bean to bind onto
	 * @param objectName the name of the target object
	 */
	/**
	 * 创建{@link  BeanPropertyBindingResult}类的新实例。 
	 *  
	 * @param 将目标bean绑定到
	 * @param  objectName目标对象的名称
	 */
	public BeanPropertyBindingResult(@Nullable Object target, String objectName) {
		this(target, objectName, true, Integer.MAX_VALUE);
	}

	/**
	 * Creates a new instance of the {@link BeanPropertyBindingResult} class.
	 * @param target the target bean to bind onto
	 * @param objectName the name of the target object
	 * @param autoGrowNestedPaths whether to "auto-grow" a nested path that contains a null value
	 * @param autoGrowCollectionLimit the limit for array and collection auto-growing
	 */
	/**
	 * 创建{@link  BeanPropertyBindingResult}类的新实例。 
	 *  
	 * @param 将目标bean绑定到目标
	 * @param  objectName目标对象的名称
	 * @param  autoGrowNestedPaths是否"自动增长"包含空值的嵌套路径
	 * @param  autoGrowCollection限制数组和集合自动增长
	 */
	public BeanPropertyBindingResult(@Nullable Object target, String objectName,
			boolean autoGrowNestedPaths, int autoGrowCollectionLimit) {

		super(objectName);
		this.target = target;
		this.autoGrowNestedPaths = autoGrowNestedPaths;
		this.autoGrowCollectionLimit = autoGrowCollectionLimit;
	}


	@Override
	@Nullable
	public final Object getTarget() {
		return this.target;
	}

	/**
	 * Returns the {@link BeanWrapper} that this instance uses.
	 * Creates a new one if none existed before.
	 * @see #createBeanWrapper()
	 */
	/**
	 * 返回此实例使用的{@link  BeanWrapper}。 
	 * 如果以前不存在，则创建一个新的。 
	 *  
	 * @see  #createBeanWrapper（）
	 */
	@Override
	public final ConfigurablePropertyAccessor getPropertyAccessor() {
		if (this.beanWrapper == null) {
			this.beanWrapper = createBeanWrapper();
			this.beanWrapper.setExtractOldValueForEditor(true);
			this.beanWrapper.setAutoGrowNestedPaths(this.autoGrowNestedPaths);
			this.beanWrapper.setAutoGrowCollectionLimit(this.autoGrowCollectionLimit);
		}
		return this.beanWrapper;
	}

	/**
	 * Create a new {@link BeanWrapper} for the underlying target object.
	 * @see #getTarget()
	 */
	/**
	 * 为基础目标对象创建一个新的{@link  BeanWrapper}。 
	 *  
	 * @see  #getTarget（）
	 */
	protected BeanWrapper createBeanWrapper() {
		if (this.target == null) {
			throw new IllegalStateException("Cannot access properties on null bean instance '" + getObjectName() + "'");
		}
		return PropertyAccessorFactory.forBeanPropertyAccess(this.target);
	}

}
