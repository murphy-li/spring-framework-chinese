/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.config;

import java.lang.reflect.Constructor;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * Extension of the {@link InstantiationAwareBeanPostProcessor} interface,
 * adding a callback for predicting the eventual type of a processed bean.
 *
 * <p><b>NOTE:</b> This interface is a special purpose interface, mainly for
 * internal use within the framework. In general, application-provided
 * post-processors should simply implement the plain {@link BeanPostProcessor}
 * interface or derive from the {@link InstantiationAwareBeanPostProcessorAdapter}
 * class. New methods might be added to this interface even in point releases.
 *
 * @author Juergen Hoeller
 * @since 2.0.3
 * @see InstantiationAwareBeanPostProcessorAdapter
 */
/**
 * {@link  InstantiationAwareBeanPostProcessor}接口的扩展，添加了一个用于预测已处理bean的最终类型的回调。 
 *  <p> <b>注意：</ b>此接口是一个专用接口，主要供框架内部使用。 
 * 通常，应用程序提供的后处理器应仅实现简单的{@link  BeanPostProcessor}接口或从{@link  InstantiationAwareBeanPostProcessorAdapter}类派生。 
 * 即使在点发行版中，新方法也可能会添加到此接口。 
 *  @author  Juergen Hoeller @始于2.0.3 
 * @see  InstantiationAwareBeanPostProcessorAdapter
 */
public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor {

	/**
	 * Predict the type of the bean to be eventually returned from this
	 * processor's {@link #postProcessBeforeInstantiation} callback.
	 * <p>The default implementation returns {@code null}.
	 * @param beanClass the raw class of the bean
	 * @param beanName the name of the bean
	 * @return the type of the bean, or {@code null} if not predictable
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	/**
	 * 预测最终从此处理器的{@link  #postProcessBeforeInstantiation}回调返回的bean的类型。 
	 *  <p>默认实现返回{@code  null}。 
	 *  
	 * @param  beanClass Bean的原始类
	 * @param  beanName Bean的名称
	 * @return  Bean的类型，如果无法预测
	 * @throws  org.springframework，则为{@code  null}。 
	 * 发生错误时的beans.BeansException
	 */
	@Nullable
	default Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	/**
	 * Determine the candidate constructors to use for the given bean.
	 * <p>The default implementation returns {@code null}.
	 * @param beanClass the raw class of the bean (never {@code null})
	 * @param beanName the name of the bean
	 * @return the candidate constructors, or {@code null} if none specified
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	/**
	 * 确定用于给定bean的候选构造函数。 
	 *  <p>默认实现返回{@code  null}。 
	 *  
	 * @param  beanClass bean的原始类（绝不{<@@code> null}）
	 * @param  beanName bean的名称
	 * @return 候选构造函数，如果未指定，则为{@code  null} 
	 * @throws 发生错误时的org.springframework.beans.BeansException
	 */
	@Nullable
	default Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName)
			throws BeansException {

		return null;
	}

	/**
	 * Obtain a reference for early access to the specified bean,
	 * typically for the purpose of resolving a circular reference.
	 * <p>This callback gives post-processors a chance to expose a wrapper
	 * early - that is, before the target bean instance is fully initialized.
	 * The exposed object should be equivalent to the what
	 * {@link #postProcessBeforeInitialization} / {@link #postProcessAfterInitialization}
	 * would expose otherwise. Note that the object returned by this method will
	 * be used as bean reference unless the post-processor returns a different
	 * wrapper from said post-process callbacks. In other words: Those post-process
	 * callbacks may either eventually expose the same reference or alternatively
	 * return the raw bean instance from those subsequent callbacks (if the wrapper
	 * for the affected bean has been built for a call to this method already,
	 * it will be exposes as final bean reference by default).
	 * <p>The default implementation returns the given {@code bean} as-is.
	 * @param bean the raw bean instance
	 * @param beanName the name of the bean
	 * @return the object to expose as bean reference
	 * (typically with the passed-in bean instance as default)
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	/**
	 * 获取一个引用，以便尽早访问指定的bean，通常是为了解决循环引用。 
	 *  <p>此回调使后处理器有机会及早公开包装器，也就是在目标Bean实例完全初始化之前。 
	 * 暴露的对象应等效于{@link  #postProcessBeforeInitialization} / {@link  #postProcessAfterInitialization}否则将暴露的对象。 
	 * 注意，除非后处理器返回与所述后处理回调不同的包装，否则此方法返回的对象将用作Bean引用。 
	 * 换句话说：这些后处理回调可能最终会公开相同的引用，或者从这些后续回调中返回原始bean实例（如果已经为该方法的调用构建了受影响的bean的包装，它将被公开。 
	 * 作为默认的最终bean参考）。 
	 *  <p>默认实现按原样返回给定的{@code  bean}。 
	 *  
	 * @param  bean原始bean实例
	 * @param  beanName bean的名称
	 * @return 作为bean引用公开的对象（通常将传入的bean实例作为默认值）
	 * @throws  org.springframework。 
	 * 发生错误时的beans.BeansException
	 */
	default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
