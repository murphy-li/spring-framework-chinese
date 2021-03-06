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

package org.springframework.beans.factory.support;

import org.springframework.beans.BeanMetadataAttributeAccessor;
import org.springframework.util.Assert;

/**
 * Qualifier for resolving autowire candidates. A bean definition that
 * includes one or more such qualifiers enables fine-grained matching
 * against annotations on a field or parameter to be autowired.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 2.5
 * @see org.springframework.beans.factory.annotation.Qualifier
 */
/**
 * 解决自动装配候选者的限定符。 
 * 包含一个或多个此类限定符的Bean定义可实现与要自动装配的字段或参数上的注释的细粒度匹配。 
 *  @author  Mark Fisher @author  Juergen Hoeller @从2.5开始
 * @see  org.springframework.beans.factory.annotation.Qualifier
 */
@SuppressWarnings("serial")
public class AutowireCandidateQualifier extends BeanMetadataAttributeAccessor {

	/**
	 * The name of the key used to store the value.
	 */
	/**
	 * 用于存储值的键的名称。 
	 * 
	 */
	public static final String VALUE_KEY = "value";

	private final String typeName;


	/**
	 * Construct a qualifier to match against an annotation of the
	 * given type.
	 * @param type the annotation type
	 */
	/**
	 * 构造一个限定词以匹配给定类型的注释。 
	 *  
	 * @param 键入注释类型
	 */
	public AutowireCandidateQualifier(Class<?> type) {
		this(type.getName());
	}

	/**
	 * Construct a qualifier to match against an annotation of the
	 * given type name.
	 * <p>The type name may match the fully-qualified class name of
	 * the annotation or the short class name (without the package).
	 * @param typeName the name of the annotation type
	 */
	/**
	 * 构造一个限定词以匹配给定类型名称的注释。 
	 *  <p>类型名称可能与注释的完全限定的类名称或简短的类名称（不带包装）匹配。 
	 *  
	 * @param  typeName注释类型的名称
	 */
	public AutowireCandidateQualifier(String typeName) {
		Assert.notNull(typeName, "Type name must not be null");
		this.typeName = typeName;
	}

	/**
	 * Construct a qualifier to match against an annotation of the
	 * given type whose {@code value} attribute also matches
	 * the specified value.
	 * @param type the annotation type
	 * @param value the annotation value to match
	 */
	/**
	 * 构造一个限定词以与给定类型的注解匹配，该注解的{@code  value}属性也与指定值匹配。 
	 *  
	 * @param 键入注释类型
	 * @param 值注释值以匹配
	 */
	public AutowireCandidateQualifier(Class<?> type, Object value) {
		this(type.getName(), value);
	}

	/**
	 * Construct a qualifier to match against an annotation of the
	 * given type name whose {@code value} attribute also matches
	 * the specified value.
	 * <p>The type name may match the fully-qualified class name of
	 * the annotation or the short class name (without the package).
	 * @param typeName the name of the annotation type
	 * @param value the annotation value to match
	 */
	/**
	 * 构造一个限定词以匹配其{@code  value}属性也与指定值匹配的给定类型名称的注释。 
	 *  <p>类型名称可能与注释的完全限定的类名称或简短的类名称（不带包装）匹配。 
	 *  
	 * @param  typeName注释类型的名称
	 * @param 值要匹配的注释值
	 */
	public AutowireCandidateQualifier(String typeName, Object value) {
		Assert.notNull(typeName, "Type name must not be null");
		this.typeName = typeName;
		setAttribute(VALUE_KEY, value);
	}


	/**
	 * Retrieve the type name. This value will be the same as the
	 * type name provided to the constructor or the fully-qualified
	 * class name if a Class instance was provided to the constructor.
	 */
	/**
	 * 检索类型名称。 
	 * 该值将与提供给构造函数的类型名称相同，或者如果提供给构造函数的Class实例则为完全限定的类名称。 
	 * 
	 */
	public String getTypeName() {
		return this.typeName;
	}

}
