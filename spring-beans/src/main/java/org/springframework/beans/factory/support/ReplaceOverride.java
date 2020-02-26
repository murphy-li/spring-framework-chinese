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

package org.springframework.beans.factory.support;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Extension of MethodOverride that represents an arbitrary
 * override of a method by the IoC container.
 *
 * <p>Any non-final method can be overridden, irrespective of its
 * parameters and return types.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 1.1
 */
/**
 * MethodOverride的扩展，表示IoC容器对方法的任意替代。 
 *  <p>任何非最终方法都可以覆盖，无论其参数和返回类型如何。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @始于1.1
 */
public class ReplaceOverride extends MethodOverride {

	private final String methodReplacerBeanName;

	private List<String> typeIdentifiers = new LinkedList<>();


	/**
	 * Construct a new ReplaceOverride.
	 * @param methodName the name of the method to override
	 * @param methodReplacerBeanName the bean name of the MethodReplacer
	 */
	/**
	 * 构造一个新的ReplaceOverride。 
	 *  
	 * @param  methodName要覆盖的方法的名称
	 * @param  methodReplacerBeanName MethodReplacer的bean名称
	 */
	public ReplaceOverride(String methodName, String methodReplacerBeanName) {
		super(methodName);
		Assert.notNull(methodName, "Method replacer bean name must not be null");
		this.methodReplacerBeanName = methodReplacerBeanName;
	}


	/**
	 * Return the name of the bean implementing MethodReplacer.
	 */
	/**
	 * 返回实现MethodReplacer的bean的名称。 
	 * 
	 */
	public String getMethodReplacerBeanName() {
		return this.methodReplacerBeanName;
	}

	/**
	 * Add a fragment of a class string, like "Exception"
	 * or "java.lang.Exc", to identify a parameter type.
	 * @param identifier a substring of the fully qualified class name
	 */
	/**
	 * 添加一个类字符串的片段，例如"Exception"或"java.lang.Exc"，以标识参数类型。 
	 *  
	 * @param 标识符，是完全限定的类名的子字符串
	 */
	public void addTypeIdentifier(String identifier) {
		this.typeIdentifiers.add(identifier);
	}

	@Override
	public boolean matches(Method method) {
		if (!method.getName().equals(getMethodName())) {
			return false;
		}
		if (!isOverloaded()) {
			// Not overloaded: don't worry about arg type matching...
			return true;
		}
		// If we get here, we need to insist on precise argument matching...
		if (this.typeIdentifiers.size() != method.getParameterCount()) {
			return false;
		}
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < this.typeIdentifiers.size(); i++) {
			String identifier = this.typeIdentifiers.get(i);
			if (!parameterTypes[i].getName().contains(identifier)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (!(other instanceof ReplaceOverride) || !super.equals(other)) {
			return false;
		}
		ReplaceOverride that = (ReplaceOverride) other;
		return (ObjectUtils.nullSafeEquals(this.methodReplacerBeanName, that.methodReplacerBeanName) &&
				ObjectUtils.nullSafeEquals(this.typeIdentifiers, that.typeIdentifiers));
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.methodReplacerBeanName);
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.typeIdentifiers);
		return hashCode;
	}

	@Override
	public String toString() {
		return "Replace override for method '" + getMethodName() + "'";
	}

}
