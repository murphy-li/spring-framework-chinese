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

package org.springframework.beans.factory.config;

import java.io.Serializable;

import javax.inject.Provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A {@link org.springframework.beans.factory.FactoryBean} implementation that
 * returns a value which is a JSR-330 {@link javax.inject.Provider} that in turn
 * returns a bean sourced from a {@link org.springframework.beans.factory.BeanFactory}.
 *
 * <p>This is basically a JSR-330 compliant variant of Spring's good old
 * {@link ObjectFactoryCreatingFactoryBean}. It can be used for traditional
 * external dependency injection configuration that targets a property or
 * constructor argument of type {@code javax.inject.Provider}, as an
 * alternative to JSR-330's {@code @Inject} annotation-driven approach.
 *
 * @author Juergen Hoeller
 * @since 3.0.2
 * @see javax.inject.Provider
 * @see ObjectFactoryCreatingFactoryBean
 */
/**
 * 一个{@link  org.springframework.beans.factory.FactoryBean}实现，该实现返回一个值为JSR-330 {@link  javax.inject.Provider}的值，该值又返回一个源自{<@链接> org.springframework.beans.factory.BeanFactory}。 
 *  <p>这基本上是Spring的旧版{@link  ObjectFactoryCreatingFactoryBean}的JSR-330兼容变体。 
 * 它可用于以类型为<< @code> javax.inject.Provider}的属性或构造函数参数为目标的传统外部依赖项注入配置，作为JSR-330的{@code  @Inject}注释驱动方法的替代方法。 
 *  @author  Juergen Hoeller @since 3.0.2起
 * @see  javax.inject.Provider 
 * @see  ObjectFactoryCreatingFactoryBean
 */
public class ProviderCreatingFactoryBean extends AbstractFactoryBean<Provider<Object>> {

	@Nullable
	private String targetBeanName;


	/**
	 * Set the name of the target bean.
	 * <p>The target does not <i>have</i> to be a non-singleton bean, but realistically
	 * always will be (because if the target bean were a singleton, then said singleton
	 * bean could simply be injected straight into the dependent object, thus obviating
	 * the need for the extra level of indirection afforded by this factory approach).
	 */
	/**
	 * 设置目标bean的名称。 
	 *  <p>目标不是<i>不是非单例bean，但实际上总是这样（因为如果目标bean是单例，则可以简单地将单例bean直接注入从属豆中。 
	 * 对象，从而消除了这种工厂方法所提供的额外的间接级别需求。 
	 * 
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.targetBeanName, "Property 'targetBeanName' is required");
		super.afterPropertiesSet();
	}


	@Override
	public Class<?> getObjectType() {
		return Provider.class;
	}

	@Override
	protected Provider<Object> createInstance() {
		BeanFactory beanFactory = getBeanFactory();
		Assert.state(beanFactory != null, "No BeanFactory available");
		Assert.state(this.targetBeanName != null, "No target bean name specified");
		return new TargetBeanProvider(beanFactory, this.targetBeanName);
	}


	/**
	 * Independent inner class - for serialization purposes.
	 */
	/**
	 * 独立的内部类-用于序列化目的。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class TargetBeanProvider implements Provider<Object>, Serializable {

		private final BeanFactory beanFactory;

		private final String targetBeanName;

		public TargetBeanProvider(BeanFactory beanFactory, String targetBeanName) {
			this.beanFactory = beanFactory;
			this.targetBeanName = targetBeanName;
		}

		@Override
		public Object get() throws BeansException {
			return this.beanFactory.getBean(this.targetBeanName);
		}
	}

}
