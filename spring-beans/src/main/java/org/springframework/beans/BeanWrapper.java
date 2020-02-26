/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans;

import java.beans.PropertyDescriptor;

/**
 * The central interface of Spring's low-level JavaBeans infrastructure.
 *
 * <p>Typically not used directly but rather implicitly via a
 * {@link org.springframework.beans.factory.BeanFactory} or a
 * {@link org.springframework.validation.DataBinder}.
 *
 * <p>Provides operations to analyze and manipulate standard JavaBeans:
 * the ability to get and set property values (individually or in bulk),
 * get property descriptors, and query the readability/writability of properties.
 *
 * <p>This interface supports <b>nested properties</b> enabling the setting
 * of properties on subproperties to an unlimited depth.
 *
 * <p>A BeanWrapper's default for the "extractOldValueForEditor" setting
 * is "false", to avoid side effects caused by getter method invocations.
 * Turn this to "true" to expose present property values to custom editors.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 13 April 2001
 * @see PropertyAccessor
 * @see PropertyEditorRegistry
 * @see PropertyAccessorFactory#forBeanPropertyAccess
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.validation.BeanPropertyBindingResult
 * @see org.springframework.validation.DataBinder#initBeanPropertyAccess()
 */
/**
 * Spring的低级JavaBeans基础结构的中央接口。 
 *  <p>通常不直接使用，而是通过{@link  org.springframework.beans.factory.BeanFactory}或{@link  org.springframework.validation.DataBinder}隐式使用。 
 *  <p>提供用于分析和操作标准JavaBean的操作：获得和设置属性值（单独或批量），获取属性描述符以及查询属性的可读性/可写性的能力。 
 *  <p>此界面支持<b>嵌套属性</ b>，可将子属性上的属性设置为无限深度。 
 *  <p>"extractOldValueForEditor"设置的BeanWrapper的默认值为"false"，以避免由getter方法调用引起的副作用。 
 * 将此选项设置为"true"，以向定制编辑器公开当前属性值。 
 *  @author  Rod Johnson @author  Juergen Hoeller @自2001年4月13日以来
 * @see  PropertyAccessor 
 * @see  PropertyEditorRegistry 
 * @see  PropertyAccessorFactory＃forBeanPropertyAccess 
 * @see  org.springframework.beans.factory.BeanFactory <
 * @see > org.springframework.validation.BeanPropertyBindingResult 
 * @see  org.springframework.validation.DataBinder＃initBeanPropertyAccess（）
 */
public interface BeanWrapper extends ConfigurablePropertyAccessor {

	/**
	 * Specify a limit for array and collection auto-growing.
	 * <p>Default is unlimited on a plain BeanWrapper.
	 * @since 4.1
	 */
	/**
	 * 指定数组和集合自动增长的限制。 
	 *  <p>在普通的BeanWrapper上，默认值是无限的。 
	 *  @始于4.1
	 */
	void setAutoGrowCollectionLimit(int autoGrowCollectionLimit);

	/**
	 * Return the limit for array and collection auto-growing.
	 * @since 4.1
	 */
	/**
	 * 返回数组和集合自动增长的限制。 
	 *  @始于4.1
	 */
	int getAutoGrowCollectionLimit();

	/**
	 * Return the bean instance wrapped by this object.
	 */
	/**
	 * 返回此对象包装的bean实例。 
	 * 
	 */
	Object getWrappedInstance();

	/**
	 * Return the type of the wrapped bean instance.
	 */
	/**
	 * 返回包装的bean实例的类型。 
	 * 
	 */
	Class<?> getWrappedClass();

	/**
	 * Obtain the PropertyDescriptors for the wrapped object
	 * (as determined by standard JavaBeans introspection).
	 * @return the PropertyDescriptors for the wrapped object
	 */
	/**
	 * 获取包装对象的PropertyDescriptors（由标准JavaBean自省确定）。 
	 *  
	 * @return 包装对象的PropertyDescriptors
	 */
	PropertyDescriptor[] getPropertyDescriptors();

	/**
	 * Obtain the property descriptor for a specific property
	 * of the wrapped object.
	 * @param propertyName the property to obtain the descriptor for
	 * (may be a nested path, but no indexed/mapped property)
	 * @return the property descriptor for the specified property
	 * @throws InvalidPropertyException if there is no such property
	 */
	/**
	 * 获取包装对象的特定属性的属性描述符。 
	 *  
	 * @param  propertyName要为其获取描述符的属性（可以是嵌套路径，但没有索引/映射的属性）
	 * @return 指定属性的属性描述符
	 * @throws  InvalidPropertyException（如果没有此类属性）
	 */
	PropertyDescriptor getPropertyDescriptor(String propertyName) throws InvalidPropertyException;

}
