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

package org.springframework.beans.factory.config;

import java.lang.reflect.Field;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * {@link FactoryBean} which retrieves a static or non-static field value.
 *
 * <p>Typically used for retrieving public static final constants. Usage example:
 *
 * <pre class="code">
 * // standard definition for exposing a static field, specifying the "staticField" property
 * &lt;bean id="myField" class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"&gt;
 *   &lt;property name="staticField" value="java.sql.Connection.TRANSACTION_SERIALIZABLE"/&gt;
 * &lt;/bean&gt;
 *
 * // convenience version that specifies a static field pattern as bean name
 * &lt;bean id="java.sql.Connection.TRANSACTION_SERIALIZABLE"
 *       class="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"/&gt;
 * </pre>
 *
 * <p>If you are using Spring 2.0, you can also use the following style of configuration for
 * public static fields.
 *
 * <pre class="code">&lt;util:constant static-field="java.sql.Connection.TRANSACTION_SERIALIZABLE"/&gt;</pre>
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see #setStaticField
 */
/**
 * {@link  FactoryBean}检索静态或非静态字段值。 
 *  <p>通常用于检索公共静态最终常量。 
 * 用法示例：<pre class ="code"> //用于公开静态字段并指定"staticField"属性的标准定义<bean id ="myField"class ="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"> <property name ="staticField"value ="java.sql.Connection.TRANSACTION_SERIALIZABLE"/> </ bean> //将静态字段模式指定为bean名称的便捷版本<bean id ="java.sql.Connection.TRANSACTION_SERIALIZABLE"class ="org.springframework.beans.factory.config.FieldRetrievingFactoryBean"/> </ pre> <p>如果您使用的是Spring 2.0，还可以对公共静态字段使用以下配置样式。 
 *  <pre class ="code"> <util：constant static-field ="java.sql.Connection.TRANSACTION_SERIALIZABLE"/> </ pre> @author  Juergen Hoeller @since 1.1起
 * @see  #setStaticField
 */
public class FieldRetrievingFactoryBean
		implements FactoryBean<Object>, BeanNameAware, BeanClassLoaderAware, InitializingBean {

	@Nullable
	private Class<?> targetClass;

	@Nullable
	private Object targetObject;

	@Nullable
	private String targetField;

	@Nullable
	private String staticField;

	@Nullable
	private String beanName;

	@Nullable
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	// the field we will retrieve
	@Nullable
	private Field fieldObject;


	/**
	 * Set the target class on which the field is defined.
	 * Only necessary when the target field is static; else,
	 * a target object needs to be specified anyway.
	 * @see #setTargetObject
	 * @see #setTargetField
	 */
	/**
	 * 设置定义字段的目标类。 
	 * 仅在目标字段为静态时才需要； 
	 * 否则，仍然需要指定目标对象。 
	 *  
	 * @see  #setTargetObject 
	 * @see  #setTargetField
	 */
	public void setTargetClass(@Nullable Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * Return the target class on which the field is defined.
	 */
	/**
	 * 返回定义字段的目标类。 
	 * 
	 */
	@Nullable
	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	/**
	 * Set the target object on which the field is defined.
	 * Only necessary when the target field is not static;
	 * else, a target class is sufficient.
	 * @see #setTargetClass
	 * @see #setTargetField
	 */
	/**
	 * 设置在其上定义字段的目标对象。 
	 * 仅在目标字段不是静态的时才需要； 
	 * 否则，目标类别就足够了。 
	 *  
	 * @see  #setTargetClass 
	 * @see  #setTargetField
	 */
	public void setTargetObject(@Nullable Object targetObject) {
		this.targetObject = targetObject;
	}

	/**
	 * Return the target object on which the field is defined.
	 */
	/**
	 * 返回定义了字段的目标对象。 
	 * 
	 */
	@Nullable
	public Object getTargetObject() {
		return this.targetObject;
	}

	/**
	 * Set the name of the field to be retrieved.
	 * Refers to either a static field or a non-static field,
	 * depending on a target object being set.
	 * @see #setTargetClass
	 * @see #setTargetObject
	 */
	/**
	 * 设置要检索的字段的名称。 
	 * 取决于要设置的目标对象，是指静态字段还是非静态字段。 
	 *  
	 * @see  #setTargetClass 
	 * @see  #setTargetObject
	 */
	public void setTargetField(@Nullable String targetField) {
		this.targetField = (targetField != null ? StringUtils.trimAllWhitespace(targetField) : null);
	}

	/**
	 * Return the name of the field to be retrieved.
	 */
	/**
	 * 返回要检索的字段的名称。 
	 * 
	 */
	@Nullable
	public String getTargetField() {
		return this.targetField;
	}

	/**
	 * Set a fully qualified static field name to retrieve,
	 * e.g. "example.MyExampleClass.MY_EXAMPLE_FIELD".
	 * Convenient alternative to specifying targetClass and targetField.
	 * @see #setTargetClass
	 * @see #setTargetField
	 */
	/**
	 * 设置要检索的标准静态字段名称，例如"example.MyExampleClass.MY_EXAMPLE_FIELD"。 
	 * 指定targetClass和targetField的方便替代方法。 
	 *  
	 * @see  #setTargetClass 
	 * @see  #setTargetField
	 */
	public void setStaticField(String staticField) {
		this.staticField = StringUtils.trimAllWhitespace(staticField);
	}

	/**
	 * The bean name of this FieldRetrievingFactoryBean will be interpreted
	 * as "staticField" pattern, if neither "targetClass" nor "targetObject"
	 * nor "targetField" have been specified.
	 * This allows for concise bean definitions with just an id/name.
	 */
	/**
	 * 如果未指定"targetClass"，"targetObject"或"targetField"，则此FieldRetrievingFactoryBean的bean名称将解释为"staticField"模式。 
	 * 这允许仅使用id / name的简洁bean定义。 
	 * 
	 */
	@Override
	public void setBeanName(String beanName) {
		this.beanName = StringUtils.trimAllWhitespace(BeanFactoryUtils.originalBeanName(beanName));
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}


	@Override
	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchFieldException {
		if (this.targetClass != null && this.targetObject != null) {
			throw new IllegalArgumentException("Specify either targetClass or targetObject, not both");
		}

		if (this.targetClass == null && this.targetObject == null) {
			if (this.targetField != null) {
				throw new IllegalArgumentException(
						"Specify targetClass or targetObject in combination with targetField");
			}

			// If no other property specified, consider bean name as static field expression.
			if (this.staticField == null) {
				this.staticField = this.beanName;
				Assert.state(this.staticField != null, "No target field specified");
			}

			// Try to parse static field into class and field.
			int lastDotIndex = this.staticField.lastIndexOf('.');
			if (lastDotIndex == -1 || lastDotIndex == this.staticField.length()) {
				throw new IllegalArgumentException(
						"staticField must be a fully qualified class plus static field name: " +
						"e.g. 'example.MyExampleClass.MY_EXAMPLE_FIELD'");
			}
			String className = this.staticField.substring(0, lastDotIndex);
			String fieldName = this.staticField.substring(lastDotIndex + 1);
			this.targetClass = ClassUtils.forName(className, this.beanClassLoader);
			this.targetField = fieldName;
		}

		else if (this.targetField == null) {
			// Either targetClass or targetObject specified.
			throw new IllegalArgumentException("targetField is required");
		}

		// Try to get the exact method first.
		Class<?> targetClass = (this.targetObject != null ? this.targetObject.getClass() : this.targetClass);
		this.fieldObject = targetClass.getField(this.targetField);
	}


	@Override
	@Nullable
	public Object getObject() throws IllegalAccessException {
		if (this.fieldObject == null) {
			throw new FactoryBeanNotInitializedException();
		}
		ReflectionUtils.makeAccessible(this.fieldObject);
		if (this.targetObject != null) {
			// instance field
			return this.fieldObject.get(this.targetObject);
		}
		else {
			// class field
			return this.fieldObject.get(null);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return (this.fieldObject != null ? this.fieldObject.getType() : null);
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
