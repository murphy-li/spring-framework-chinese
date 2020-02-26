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

package org.springframework.beans;

/**
 * Simple factory facade for obtaining {@link PropertyAccessor} instances,
 * in particular for {@link BeanWrapper} instances. Conceals the actual
 * target implementation classes and their extended public signature.
 *
 * @author Juergen Hoeller
 * @since 2.5.2
 */
/**
 * 用于获取{@link  PropertyAccessor}实例的简单工厂外观，尤其是对于{@link  BeanWrapper}实例。 
 * 隐藏实际的目标实现类及其扩展的公共签名。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@2.5.2起
 */
public final class PropertyAccessorFactory {

	private PropertyAccessorFactory() {
	}


	/**
	 * Obtain a BeanWrapper for the given target object,
	 * accessing properties in JavaBeans style.
	 * @param target the target object to wrap
	 * @return the property accessor
	 * @see BeanWrapperImpl
	 */
	/**
	 * 获取给定目标对象的BeanWrapper，以JavaBeans样式访问属性。 
	 *  
	 * @param 以目标对象为目标以包装
	 * @return 属性访问器
	 * @see  BeanWrapperImpl
	 */
	public static BeanWrapper forBeanPropertyAccess(Object target) {
		return new BeanWrapperImpl(target);
	}

	/**
	 * Obtain a PropertyAccessor for the given target object,
	 * accessing properties in direct field style.
	 * @param target the target object to wrap
	 * @return the property accessor
	 * @see DirectFieldAccessor
	 */
	/**
	 * 获取给定目标对象的PropertyAccessor，以直接字段样式访问属性。 
	 *  
	 * @param 以目标对象为目标以包装
	 * @return 属性访问器
	 * @see  DirectFieldAccessor
	 */
	public static ConfigurablePropertyAccessor forDirectFieldAccess(Object target) {
		return new DirectFieldAccessor(target);
	}

}
