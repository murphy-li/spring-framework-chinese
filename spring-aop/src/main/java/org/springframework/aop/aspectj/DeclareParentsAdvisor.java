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

package org.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.aop.support.ClassFilters;
import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * Introduction advisor delegating to the given object.
 * Implements AspectJ annotation-style behavior for the DeclareParents annotation.
 *
 * @author Rod Johnson
 * @author Ramnivas Laddad
 * @since 2.0
 */
/**
 * 介绍顾问委托给定对象。 
 * 为DeclareParents注释实现AspectJ注释样式的行为。 
 *  @author 罗德·约翰逊@author  Ramnivas Laddad @从2.0开始
 */
public class DeclareParentsAdvisor implements IntroductionAdvisor {

	private final Advice advice;

	private final Class<?> introducedInterface;

	private final ClassFilter typePatternClassFilter;


	/**
	 * Create a new advisor for this DeclareParents field.
	 * @param interfaceType static field defining the introduction
	 * @param typePattern type pattern the introduction is restricted to
	 * @param defaultImpl the default implementation class
	 */
	/**
	 * 为此DeclareParents字段创建一个新的顾问。 
	 *  @param interfaceType定义引言的静态字段@param type引言的模式类型模式仅限于@param defaultImpl默认实现类
	 */
	public DeclareParentsAdvisor(Class<?> interfaceType, String typePattern, Class<?> defaultImpl) {
		this(interfaceType, typePattern,
				new DelegatePerTargetObjectIntroductionInterceptor(defaultImpl, interfaceType));
	}

	/**
	 * Create a new advisor for this DeclareParents field.
	 * @param interfaceType static field defining the introduction
	 * @param typePattern type pattern the introduction is restricted to
	 * @param delegateRef the delegate implementation object
	 */
	/**
	 * 为此DeclareParents字段创建一个新的顾问。 
	 *  @param interfaceType定义引言的静态字段@param type引言仅限于@param委托的样式类型模式委托委托实现对象
	 */
	public DeclareParentsAdvisor(Class<?> interfaceType, String typePattern, Object delegateRef) {
		this(interfaceType, typePattern, new DelegatingIntroductionInterceptor(delegateRef));
	}

	/**
	 * Private constructor to share common code between impl-based delegate and reference-based delegate
	 * (cannot use method such as init() to share common code, due the use of final fields).
	 * @param interfaceType static field defining the introduction
	 * @param typePattern type pattern the introduction is restricted to
	 * @param interceptor the delegation advice as {@link IntroductionInterceptor}
	 */
	/**
	 * 私有构造函数在基于impl的委托和基于引用的委托之间共享通用代码（由于使用final字段，因此无法使用init（）之类的方法共享通用代码）。 
	 *  @param interfaceType定义引言的静态字段@param type引言仅限于@param拦截器的类型类型模式委托建议为{@link  IntroductionInterceptor}
	 */
	private DeclareParentsAdvisor(Class<?> interfaceType, String typePattern, IntroductionInterceptor interceptor) {
		this.advice = interceptor;
		this.introducedInterface = interfaceType;

		// Excludes methods implemented.
		ClassFilter typePatternFilter = new TypePatternClassFilter(typePattern);
		ClassFilter exclusion = (clazz -> !this.introducedInterface.isAssignableFrom(clazz));
		this.typePatternClassFilter = ClassFilters.intersection(typePatternFilter, exclusion);
	}


	@Override
	public ClassFilter getClassFilter() {
		return this.typePatternClassFilter;
	}

	@Override
	public void validateInterfaces() throws IllegalArgumentException {
		// Do nothing
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public Class<?>[] getInterfaces() {
		return new Class<?>[] {this.introducedInterface};
	}

}
