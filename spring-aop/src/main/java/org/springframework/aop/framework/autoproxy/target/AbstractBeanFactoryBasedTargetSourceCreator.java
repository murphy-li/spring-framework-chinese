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

package org.springframework.aop.framework.autoproxy.target;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.autoproxy.TargetSourceCreator;
import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.lang.Nullable;

/**
 * Convenient superclass for
 * {@link org.springframework.aop.framework.autoproxy.TargetSourceCreator}
 * implementations that require creating multiple instances of a prototype bean.
 *
 * <p>Uses an internal BeanFactory to manage the target instances,
 * copying the original bean definition to this internal factory.
 * This is necessary because the original BeanFactory will just
 * contain the proxy instance created through auto-proxying.
 *
 * <p>Requires running in an
 * {@link org.springframework.beans.factory.support.AbstractBeanFactory}.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource
 * @see org.springframework.beans.factory.support.AbstractBeanFactory
 */
/**
 * 需要创建原型bean的多个实例的{@link  org.springframework.aop.framework.autoproxy.TargetSourceCreator}实现的便捷超类。 
 *  <p>使用内部BeanFactory管理目标实例，将原始bean定义复制到此内部工厂。 
 * 这是必需的，因为原始BeanFactory仅包含通过自动代理创建的代理实例。 
 *  <p>需要在{@link  org.springframework.beans.factory.support.AbstractBeanFactory}中运行。 
 *  @author  Rod Johnson @author  Juergen Hoeller 
 * @see  org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource 
 * @see  org.springframework.beans.factory.support.AbstractBeanFactory
 */
public abstract class AbstractBeanFactoryBasedTargetSourceCreator
		implements TargetSourceCreator, BeanFactoryAware, DisposableBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private ConfigurableBeanFactory beanFactory;

	/** Internally used DefaultListableBeanFactory instances, keyed by bean name. */
	/**
	 * 内部使用的DefaultListableBeanFactory实例，以bean名称为键。 
	 * 
	 */
	private final Map<String, DefaultListableBeanFactory> internalBeanFactories =
			new HashMap<>();


	@Override
	public final void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableBeanFactory)) {
			throw new IllegalStateException("Cannot do auto-TargetSource creation with a BeanFactory " +
					"that doesn't implement ConfigurableBeanFactory: " + beanFactory.getClass());
		}
		this.beanFactory = (ConfigurableBeanFactory) beanFactory;
	}

	/**
	 * Return the BeanFactory that this TargetSourceCreators runs in.
	 */
	/**
	 * 返回运行此TargetSourceCreators的BeanFactory。 
	 * 
	 */
	protected final BeanFactory getBeanFactory() {
		return this.beanFactory;
	}


	//---------------------------------------------------------------------
	// Implementation of the TargetSourceCreator interface
	//---------------------------------------------------------------------

	@Override
	@Nullable
	public final TargetSource getTargetSource(Class<?> beanClass, String beanName) {
		AbstractBeanFactoryBasedTargetSource targetSource =
				createBeanFactoryBasedTargetSource(beanClass, beanName);
		if (targetSource == null) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Configuring AbstractBeanFactoryBasedTargetSource: " + targetSource);
		}

		DefaultListableBeanFactory internalBeanFactory = getInternalBeanFactoryForBean(beanName);

		// We need to override just this bean definition, as it may reference other beans
		// and we're happy to take the parent's definition for those.
		// Always use prototype scope if demanded.
		BeanDefinition bd = this.beanFactory.getMergedBeanDefinition(beanName);
		GenericBeanDefinition bdCopy = new GenericBeanDefinition(bd);
		if (isPrototypeBased()) {
			bdCopy.setScope(BeanDefinition.SCOPE_PROTOTYPE);
		}
		internalBeanFactory.registerBeanDefinition(beanName, bdCopy);

		// Complete configuring the PrototypeTargetSource.
		targetSource.setTargetBeanName(beanName);
		targetSource.setBeanFactory(internalBeanFactory);

		return targetSource;
	}

	/**
	 * Return the internal BeanFactory to be used for the specified bean.
	 * @param beanName the name of the target bean
	 * @return the internal BeanFactory to be used
	 */
	/**
	 * 返回要用于指定bean的内部BeanFactory。 
	 *  @param beanName目标bean的名称
	 * @return 要使用的内部BeanFactory
	 */
	protected DefaultListableBeanFactory getInternalBeanFactoryForBean(String beanName) {
		synchronized (this.internalBeanFactories) {
			DefaultListableBeanFactory internalBeanFactory = this.internalBeanFactories.get(beanName);
			if (internalBeanFactory == null) {
				internalBeanFactory = buildInternalBeanFactory(this.beanFactory);
				this.internalBeanFactories.put(beanName, internalBeanFactory);
			}
			return internalBeanFactory;
		}
	}

	/**
	 * Build an internal BeanFactory for resolving target beans.
	 * @param containingFactory the containing BeanFactory that originally defines the beans
	 * @return an independent internal BeanFactory to hold copies of some target beans
	 */
	/**
	 * 构建一个内部BeanFactory来解析目标bean。 
	 *  @param containsFactory包含BeanFactory的最初定义Bean的
	 * @return 一个独立的内部BeanFactory，用于保存某些目标Bean的副本
	 */
	protected DefaultListableBeanFactory buildInternalBeanFactory(ConfigurableBeanFactory containingFactory) {
		// Set parent so that references (up container hierarchies) are correctly resolved.
		DefaultListableBeanFactory internalBeanFactory = new DefaultListableBeanFactory(containingFactory);

		// Required so that all BeanPostProcessors, Scopes, etc become available.
		internalBeanFactory.copyConfigurationFrom(containingFactory);

		// Filter out BeanPostProcessors that are part of the AOP infrastructure,
		// since those are only meant to apply to beans defined in the original factory.
		internalBeanFactory.getBeanPostProcessors().removeIf(beanPostProcessor ->
				beanPostProcessor instanceof AopInfrastructureBean);

		return internalBeanFactory;
	}

	/**
	 * Destroys the internal BeanFactory on shutdown of the TargetSourceCreator.
	 * @see #getInternalBeanFactoryForBean
	 */
	/**
	 * 在关闭TargetSourceCreator时销毁内部BeanFactory。 
	 *  
	 * @see  #getInternalBeanFactoryForBean
	 */
	@Override
	public void destroy() {
		synchronized (this.internalBeanFactories) {
			for (DefaultListableBeanFactory bf : this.internalBeanFactories.values()) {
				bf.destroySingletons();
			}
		}
	}


	//---------------------------------------------------------------------
	// Template methods to be implemented by subclasses
	//---------------------------------------------------------------------

	/**
	 * Return whether this TargetSourceCreator is prototype-based.
	 * The scope of the target bean definition will be set accordingly.
	 * <p>Default is "true".
	 * @see org.springframework.beans.factory.config.BeanDefinition#isSingleton()
	 */
	/**
	 * 返回此TargetSourceCreator是否基于原型。 
	 * 将相应地设置目标bean定义的范围。 
	 *  <p>默认为"true"。 
	 *  
	 * @see  org.springframework.beans.factory.config.BeanDefinition＃isSingleton（）
	 */
	protected boolean isPrototypeBased() {
		return true;
	}

	/**
	 * Subclasses must implement this method to return a new AbstractPrototypeBasedTargetSource
	 * if they wish to create a custom TargetSource for this bean, or {@code null} if they are
	 * not interested it in, in which case no special target source will be created.
	 * Subclasses should not call {@code setTargetBeanName} or {@code setBeanFactory}
	 * on the AbstractPrototypeBasedTargetSource: This class' implementation of
	 * {@code getTargetSource()} will do that.
	 * @param beanClass the class of the bean to create a TargetSource for
	 * @param beanName the name of the bean
	 * @return the AbstractPrototypeBasedTargetSource, or {@code null} if we don't match this
	 */
	/**
	 * 如果子类希望为此bean创建一个自定义TargetSource，则子类必须实现此方法以返回新的AbstractPrototypeBasedTargetSource； 
	 * 如果对子类不感兴趣，则返回{@code  null}，在这种情况下，将不会创建特殊的目标源。 
	 * 子类不应在AbstractPrototypeBasedTargetSource上调用{@code  setTargetBeanName}或{@code  setBeanFactory}：{@code  getTargetSource（）}的此类实现将做到这一点。 
	 *  @param beanClass为@param beanName创建TargetSource的bean的类bean的名称
	 * @return  AbstractPrototypeBasedTargetSource，如果我们不匹配，则为{@code  null}
	 */
	@Nullable
	protected abstract AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(
			Class<?> beanClass, String beanName);

}
