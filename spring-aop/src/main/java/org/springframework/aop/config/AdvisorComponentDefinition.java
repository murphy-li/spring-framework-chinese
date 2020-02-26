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

package org.springframework.aop.config;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.parsing.AbstractComponentDefinition;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.beans.factory.parsing.ComponentDefinition}
 * that bridges the gap between the advisor bean definition configured
 * by the {@code <aop:advisor>} tag and the component definition
 * infrastructure.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * {@link  org.springframework.beans.factory.parsing.ComponentDefinition}弥合了由{@code  <aop：advisor>}标签配置的顾问bean定义和组件定义基础结构之间的鸿沟。 
 *  @author 罗布·哈罗普（Rob Harrop）@author  Juergen Hoeller @始于2.0
 */
public class AdvisorComponentDefinition extends AbstractComponentDefinition {

	private final String advisorBeanName;

	private final BeanDefinition advisorDefinition;

	private final String description;

	private final BeanReference[] beanReferences;

	private final BeanDefinition[] beanDefinitions;


	public AdvisorComponentDefinition(String advisorBeanName, BeanDefinition advisorDefinition) {
		this(advisorBeanName, advisorDefinition, null);
	}

	public AdvisorComponentDefinition(
			String advisorBeanName, BeanDefinition advisorDefinition, @Nullable BeanDefinition pointcutDefinition) {

		Assert.notNull(advisorBeanName, "'advisorBeanName' must not be null");
		Assert.notNull(advisorDefinition, "'advisorDefinition' must not be null");
		this.advisorBeanName = advisorBeanName;
		this.advisorDefinition = advisorDefinition;

		MutablePropertyValues pvs = advisorDefinition.getPropertyValues();
		BeanReference adviceReference = (BeanReference) pvs.get("adviceBeanName");
		Assert.state(adviceReference != null, "Missing 'adviceBeanName' property");

		if (pointcutDefinition != null) {
			this.beanReferences = new BeanReference[] {adviceReference};
			this.beanDefinitions = new BeanDefinition[] {advisorDefinition, pointcutDefinition};
			this.description = buildDescription(adviceReference, pointcutDefinition);
		}
		else {
			BeanReference pointcutReference = (BeanReference) pvs.get("pointcut");
			Assert.state(pointcutReference != null, "Missing 'pointcut' property");
			this.beanReferences = new BeanReference[] {adviceReference, pointcutReference};
			this.beanDefinitions = new BeanDefinition[] {advisorDefinition};
			this.description = buildDescription(adviceReference, pointcutReference);
		}
	}

	private String buildDescription(BeanReference adviceReference, BeanDefinition pointcutDefinition) {
		return "Advisor <advice(ref)='" +
				adviceReference.getBeanName() + "', pointcut(expression)=[" +
				pointcutDefinition.getPropertyValues().get("expression") + "]>";
	}

	private String buildDescription(BeanReference adviceReference, BeanReference pointcutReference) {
		return "Advisor <advice(ref)='" +
				adviceReference.getBeanName() + "', pointcut(ref)='" +
				pointcutReference.getBeanName() + "'>";
	}


	@Override
	public String getName() {
		return this.advisorBeanName;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public BeanDefinition[] getBeanDefinitions() {
		return this.beanDefinitions;
	}

	@Override
	public BeanReference[] getBeanReferences() {
		return this.beanReferences;
	}

	@Override
	@Nullable
	public Object getSource() {
		return this.advisorDefinition.getSource();
	}

}
