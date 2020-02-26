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

package org.springframework.test.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.StringUtils;

/**
 * Abstract, generic extension of {@link AbstractContextLoader} that loads a
 * {@link GenericApplicationContext}.
 *
 * <ul>
 * <li>If instances of concrete subclasses are invoked via the
 * {@link org.springframework.test.context.ContextLoader ContextLoader} SPI, the
 * context will be loaded from the <em>locations</em> provided to
 * {@link #loadContext(String...)}.</li>
 * <li>If instances of concrete subclasses are invoked via the
 * {@link org.springframework.test.context.SmartContextLoader SmartContextLoader}
 * SPI, the context will be loaded from the {@link MergedContextConfiguration}
 * provided to {@link #loadContext(MergedContextConfiguration)}. In such cases, a
 * {@code SmartContextLoader} will decide whether to load the context from
 * <em>locations</em> or <em>annotated classes</em>.</li>
 * </ul>
 *
 * <p>Concrete subclasses must provide an appropriate implementation of
 * {@link #createBeanDefinitionReader createBeanDefinitionReader()},
 * potentially overriding {@link #loadBeanDefinitions loadBeanDefinitions()}
 * as well.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @author Phillip Webb
 * @since 2.5
 * @see #loadContext(MergedContextConfiguration)
 * @see #loadContext(String...)
 */
/**
 * {@link  AbstractContextLoader}的抽象，泛型扩展，用于加载{@link  GenericApplicationContext}。 
 *  <ul> <li>如果通过{@link  org.springframework.test.context.ContextLoader ContextLoader} SPI调用具体子类的实例，则将从提供给<em>位置</ em>的上下文中加载上下文。 
 *  {@link  #loadContext（String ...）}。 
 * </ li> <li>如果通过{@link  org.springframework.test.context.SmartContextLoader SmartContextLoader} SPI调用了具体子类的实例，上下文将从提供给{@link  #loadContext（MergedContextConfiguration）}的{@link  MergedContextConfiguration}加载。 
 * 在这种情况下，{<@code> SmartContextLoader}将决定是从<em>位置</ em>还是<em>带注释的类</ em>加载上下文。 
 * </ li> </ ul> <p>具体的子类必须提供{@link  #createBeanDefinitionReader createBeanDefinitionReader（）}的适当实现，并可能覆盖{@link  #loadBeanDefinitions loadBeanDefinitions（）}。 
 *  @author  Sam Brannen @author  Juergen Hoeller @author  Phillip Webb @since 2.5 
 * @see  #loadContext（MergedContextConfiguration）
 * @see  #loadContext（String ...）
 */
public abstract class AbstractGenericContextLoader extends AbstractContextLoader {

	protected static final Log logger = LogFactory.getLog(AbstractGenericContextLoader.class);


	/**
	 * Load a Spring ApplicationContext from the supplied {@link MergedContextConfiguration}.
	 *
	 * <p>Implementation details:
	 *
	 * <ul>
	 * <li>Calls {@link #validateMergedContextConfiguration(MergedContextConfiguration)}
	 * to allow subclasses to validate the supplied configuration before proceeding.</li>
	 * <li>Creates a {@link GenericApplicationContext} instance.</li>
	 * <li>If the supplied {@code MergedContextConfiguration} references a
	 * {@linkplain MergedContextConfiguration#getParent() parent configuration},
	 * the corresponding {@link MergedContextConfiguration#getParentApplicationContext()
	 * ApplicationContext} will be retrieved and
	 * {@linkplain GenericApplicationContext#setParent(ApplicationContext) set as the parent}
	 * for the context created by this method.</li>
	 * <li>Calls {@link #prepareContext(GenericApplicationContext)} for backwards
	 * compatibility with the {@link org.springframework.test.context.ContextLoader
	 * ContextLoader} SPI.</li>
	 * <li>Calls {@link #prepareContext(ConfigurableApplicationContext, MergedContextConfiguration)}
	 * to allow for customizing the context before bean definitions are loaded.</li>
	 * <li>Calls {@link #customizeBeanFactory(DefaultListableBeanFactory)} to allow for customizing the
	 * context's {@code DefaultListableBeanFactory}.</li>
	 * <li>Delegates to {@link #loadBeanDefinitions(GenericApplicationContext, MergedContextConfiguration)}
	 * to populate the context from the locations or classes in the supplied
	 * {@code MergedContextConfiguration}.</li>
	 * <li>Delegates to {@link AnnotationConfigUtils} for
	 * {@link AnnotationConfigUtils#registerAnnotationConfigProcessors registering}
	 * annotation configuration processors.</li>
	 * <li>Calls {@link #customizeContext(GenericApplicationContext)} to allow for customizing the context
	 * before it is refreshed.</li>
	 * <li>Calls {@link #customizeContext(ConfigurableApplicationContext, MergedContextConfiguration)} to
	 * allow for customizing the context before it is refreshed.</li>
	 * <li>{@link ConfigurableApplicationContext#refresh Refreshes} the
	 * context and registers a JVM shutdown hook for it.</li>
	 * </ul>
	 *
	 * @return a new application context
	 * @see org.springframework.test.context.SmartContextLoader#loadContext(MergedContextConfiguration)
	 * @see GenericApplicationContext
	 * @since 3.1
	 */
	/**
	 * 从提供的{@link  MergedContextConfiguration}加载Spring ApplicationContext。 
	 *  <p>实施细节：<ul> <li>调用{@link  #validateMergedContextConfiguration（MergedContextConfiguration）}以允许子类在继续之前验证提供的配置。 
	 * </ li> <li>创建一个{@link  GenericApplicationContext }实例。 
	 * </ li> <li>如果提供的{@code  MergedContextConfiguration}引用了{@link  plain MergedContextConfiguration＃getParent（）父配置}，则对应的{@link  MergedContextConfiguration＃getParentApplicationContext（）ApplicationContext }，并将为此方法创建的上下文{@link  plain GenericApplicationContext＃setParent（ApplicationContext）设置为父级}。 
	 * </ li> <li>调用{@link  #prepareContext（GenericApplicationContext）}与{@link  org.springframework.test.context.ContextLoader ContextLoader} SPI向后兼容。 
	 * </ li> <li>调用{@link  #prepareContext（ConfigurableApplicationContext，MergedContextConfiguration）}以允许在之前自定义上下文豆防御</ li> <li>调用{@link  #customizeBeanFactory（DefaultListableBeanFactory）}以自定义上下文的{@code  DefaultListableBeanFactory}。 
	 * </ li> <li>委托给{<@link > #loadBeanDefinitions（GenericApplicationContext，MergedContextConfiguration）}，以从提供的{@code  MergedContextConfiguration}中的位置或类中填充上下文。 
	 * </ li> <li>为{<@link}委派给{@link  AnnotationConfigUtils} </ li> <li>调用{@link  #customizeContext（GenericApplicationContext）}以允许在刷新之前自定义上下文。 
	 * </ li> <li>调用{@link  #customizeContext（ConfigurableApplicationContext，MergedContextConfiguration）}允许在刷新之前自定义上下文。 
	 * </ li> <li> {<@link> ConfigurableApplicationContext＃refresh刷新}上下文，并为其注册JVM关闭钩子。 
	 *  </ li> </ ul> <
	 * @return >新的应用程序上下文
	 * @see  org.springframework.test.context.SmartContextLoader＃loadContext（MergedContextConfiguration）
	 * @see  GenericApplicationContext @since 3.1
	 */
	@Override
	public final ConfigurableApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Loading ApplicationContext for merged context configuration [%s].",
				mergedConfig));
		}

		validateMergedContextConfiguration(mergedConfig);

		GenericApplicationContext context = new GenericApplicationContext();

		ApplicationContext parent = mergedConfig.getParentApplicationContext();
		if (parent != null) {
			context.setParent(parent);
		}
		prepareContext(context);
		prepareContext(context, mergedConfig);
		customizeBeanFactory(context.getDefaultListableBeanFactory());
		loadBeanDefinitions(context, mergedConfig);
		AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
		customizeContext(context);
		customizeContext(context, mergedConfig);
		context.refresh();
		context.registerShutdownHook();
		return context;
	}

	/**
	 * Validate the supplied {@link MergedContextConfiguration} with respect to
	 * what this context loader supports.
	 * <p>The default implementation is a <em>no-op</em> but can be overridden by
	 * subclasses as appropriate.
	 * @param mergedConfig the merged configuration to validate
	 * @throws IllegalStateException if the supplied configuration is not valid
	 * for this context loader
	 * @since 4.0.4
	 */
	/**
	 * 关于此上下文加载器支持的内容，验证提供的{@link  MergedContextConfiguration}。 
	 *  <p>默认实现是<em> no-op </ em>，但可以根据需要由子类覆盖。 
	 *  
	 * @param  mergedConfig合并的配置以验证
	 * @throws  IllegalStateException，如果提供的配置对此上下文加载器无效（自4.0.4开始）
	 */
	protected void validateMergedContextConfiguration(MergedContextConfiguration mergedConfig) {
		// no-op
	}

	/**
	 * Load a Spring ApplicationContext from the supplied {@code locations}.
	 *
	 * <p>Implementation details:
	 *
	 * <ul>
	 * <li>Creates a {@link GenericApplicationContext} instance.</li>
	 * <li>Calls {@link #prepareContext(GenericApplicationContext)} to allow for customizing the context
	 * before bean definitions are loaded.</li>
	 * <li>Calls {@link #customizeBeanFactory(DefaultListableBeanFactory)} to allow for customizing the
	 * context's {@code DefaultListableBeanFactory}.</li>
	 * <li>Delegates to {@link #createBeanDefinitionReader(GenericApplicationContext)} to create a
	 * {@link BeanDefinitionReader} which is then used to populate the context
	 * from the specified locations.</li>
	 * <li>Delegates to {@link AnnotationConfigUtils} for
	 * {@link AnnotationConfigUtils#registerAnnotationConfigProcessors registering}
	 * annotation configuration processors.</li>
	 * <li>Calls {@link #customizeContext(GenericApplicationContext)} to allow for customizing the context
	 * before it is refreshed.</li>
	 * <li>{@link ConfigurableApplicationContext#refresh Refreshes} the
	 * context and registers a JVM shutdown hook for it.</li>
	 * </ul>
	 *
	 * <p><b>Note</b>: this method does not provide a means to set active bean definition
	 * profiles for the loaded context. See {@link #loadContext(MergedContextConfiguration)}
	 * and {@link AbstractContextLoader#prepareContext(ConfigurableApplicationContext, MergedContextConfiguration)}
	 * for an alternative.
	 *
	 * @return a new application context
	 * @see org.springframework.test.context.ContextLoader#loadContext
	 * @see GenericApplicationContext
	 * @see #loadContext(MergedContextConfiguration)
	 * @since 2.5
	 */
	/**
	 * 从提供的{@code 位置}加载Spring ApplicationContext。 
	 *  <p>实现细节：<ul> <li>创建一个{@link  GenericApplicationContext}实例。 
	 * </ li> <li>调用{@link  #prepareContext（GenericApplicationContext）}以允许在bean之前自定义上下文</ li> <li>调用{@link  #customizeBeanFactory（DefaultListableBeanFactory）}以自定义上下文的{@code  DefaultListableBeanFactory}。 
	 * </ li> <li>委托给{<@link > #createBeanDefinitionReader（GenericApplicationContext）}创建一个{@link  BeanDefinitionReader}，然后将其用于从指定位置填充上下文。 
	 * </ li> <li>将{<@的代表委托给{@link  AnnotationConfigUtils} </ li> <li>调用{@link  #customizeContext（GenericApplicationContext）}以允许在刷新之前自定义上下文。 
	 * </ li> <li> {@link  ConfigurableApplicationContext＃refresh刷新}上下文并注册JVM闭包wn挂钩。 
	 * </ li> </ ul> <p> <b>注意</ b>：此方法未提供为加载的上下文设置活动bean定义配置文件的方法。 
	 * 有关替代方法，请参见{@link  #loadContext（MergedContextConfiguration）}和{@link  AbstractContextLoader＃prepareContext（ConfigurableApplicationContext，MergedContextConfiguration）}。 
	 *  
	 * @return 一个新的应用程序上下文
	 * @see  org.springframework.test.context.ContextLoader＃loadContext 
	 * @see  GenericApplicationContext 
	 * @see  #loadContext（MergedContextConfiguration）@2.5起
	 */
	@Override
	public final ConfigurableApplicationContext loadContext(String... locations) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Loading ApplicationContext for locations [%s].",
				StringUtils.arrayToCommaDelimitedString(locations)));
		}
		GenericApplicationContext context = new GenericApplicationContext();
		prepareContext(context);
		customizeBeanFactory(context.getDefaultListableBeanFactory());
		createBeanDefinitionReader(context).loadBeanDefinitions(locations);
		AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
		customizeContext(context);
		context.refresh();
		context.registerShutdownHook();
		return context;
	}

	/**
	 * Prepare the {@link GenericApplicationContext} created by this {@code ContextLoader}.
	 * Called <i>before</i> bean definitions are read.
	 *
	 * <p>The default implementation is empty. Can be overridden in subclasses to
	 * customize {@code GenericApplicationContext}'s standard settings.
	 *
	 * @param context the context that should be prepared
	 * @since 2.5
	 * @see #loadContext(MergedContextConfiguration)
	 * @see #loadContext(String...)
	 * @see GenericApplicationContext#setAllowBeanDefinitionOverriding
	 * @see GenericApplicationContext#setResourceLoader
	 * @see GenericApplicationContext#setId
	 * @see #prepareContext(ConfigurableApplicationContext, MergedContextConfiguration)
	 */
	/**
	 * 准备由此{@code  ContextLoader}创建的{@link  GenericApplicationContext}。 
	 * 读取<i>之前</ i> bean定义。 
	 *  <p>默认实现为空。 
	 * 可以在子类中重写以自定义{@code  GenericApplicationContext}的标准设置。 
	 *  
	 * @param 上下文应自2.5起准备的上下文
	 * @see  #loadContext（MergedContextConfiguration）
	 * @see  #loadContext（String ...）
	 * @see  GenericApplicationContext＃setAllowBeanDefinitionOverriding 
	 * @see  GenericApplicationContext＃setResourceLoader 
	 * @see  GenericApplicationContext＃setId 
	 * @see  #prepareContext（ConfigurableApplicationContext，MergedContextConfiguration）
	 */
	protected void prepareContext(GenericApplicationContext context) {
	}

	/**
	 * Customize the internal bean factory of the ApplicationContext created by
	 * this {@code ContextLoader}.
	 *
	 * <p>The default implementation is empty but can be overridden in subclasses
	 * to customize {@code DefaultListableBeanFactory}'s standard settings.
	 *
	 * @param beanFactory the bean factory created by this {@code ContextLoader}
	 * @since 2.5
	 * @see #loadContext(MergedContextConfiguration)
	 * @see #loadContext(String...)
	 * @see DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
	 * @see DefaultListableBeanFactory#setAllowEagerClassLoading
	 * @see DefaultListableBeanFactory#setAllowCircularReferences
	 * @see DefaultListableBeanFactory#setAllowRawInjectionDespiteWrapping
	 */
	/**
	 * 自定义此{@code  ContextLoader}创建的ApplicationContext的内部bean工厂。 
	 *  <p>默认实现为空，但可以在子类中重写以实现{@code  DefaultListableBeanFactory}的标准设置。 
	 *  
	 * @param  beanFactory由此{@code  ContextLoader}创建的bean工厂，自2.5起
	 * @see  #loadContext（MergedContextConfiguration）
	 * @see  #loadContext（String ...）
	 * @see  DefaultListableBeanFactory＃setAllowBeanDefinitionOverriding 
	 * @see  DefaultListableBeanFactory＃setAllowEagerClassLoading 
	 * @see  DefaultListableBeanFactory＃setAllowCircularReferences 
	 * @see  DefaultListableBeanFactory＃setAllowRawInjectionDespiteWrapping
	 */
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
	}

	/**
	 * Load bean definitions into the supplied {@link GenericApplicationContext context}
	 * from the locations or classes in the supplied {@code MergedContextConfiguration}.
	 *
	 * <p>The default implementation delegates to the {@link BeanDefinitionReader}
	 * returned by {@link #createBeanDefinitionReader(GenericApplicationContext)} to
	 * {@link BeanDefinitionReader#loadBeanDefinitions(String) load} the
	 * bean definitions.
	 *
	 * <p>Subclasses must provide an appropriate implementation of
	 * {@link #createBeanDefinitionReader(GenericApplicationContext)}. Alternatively subclasses
	 * may provide a <em>no-op</em> implementation of {@code createBeanDefinitionReader()}
	 * and override this method to provide a custom strategy for loading or
	 * registering bean definitions.
	 *
	 * @param context the context into which the bean definitions should be loaded
	 * @param mergedConfig the merged context configuration
	 * @since 3.1
	 * @see #loadContext(MergedContextConfiguration)
	 */
	/**
	 * 从提供的{@code  MergedContextConfiguration}中的位置或类将bean定义加载到提供的{@link  GenericApplicationContext上下文}中。 
	 *  <p>默认实现将{@link  #createBeanDefinitionReader（GenericApplicationContext）}返回的{@link  BeanDefinitionReader}委托给{@link  BeanDefinitionReader＃loadBeanDefinitions（String）load} bean定义。 
	 *  <p>子类必须提供{@link  #createBeanDefinitionReader（GenericApplicationContext）}的适当实现。 
	 * 替代地，子类可以提供{@code  createBeanDefinitionReader（）}的<em> no-op </ em>实现，并重写此方法以提供用于加载或注册Bean定义的自定义策略。 
	 *  
	 * @param 上下文Bean定义应加载到的上下文
	 * @param  mergedConfig合并的上下文配置@3.1起，@
	 * @see> #loadContext（MergedContextConfiguration）
	 */
	protected void loadBeanDefinitions(GenericApplicationContext context, MergedContextConfiguration mergedConfig) {
		createBeanDefinitionReader(context).loadBeanDefinitions(mergedConfig.getLocations());
	}

	/**
	 * Factory method for creating a new {@link BeanDefinitionReader} for loading
	 * bean definitions into the supplied {@link GenericApplicationContext context}.
	 *
	 * @param context the context for which the {@code BeanDefinitionReader}
	 * should be created
	 * @return a {@code BeanDefinitionReader} for the supplied context
	 * @since 2.5
	 * @see #loadContext(String...)
	 * @see #loadBeanDefinitions
	 * @see BeanDefinitionReader
	 */
	/**
	 * 用于创建新的{@link  BeanDefinitionReader}的工厂方法，用于将Bean定义加载到提供的{@link  GenericApplicationContext context}中。 
	 *  
	 * @param 上下文应该为其创建{@code  BeanDefinitionReader}的上下文<@r​​eturn>为所提供的上下文@{<@code> BeanDefinitionReader}，从2.5开始
	 * @see  #loadContext（String ... ）
	 * @see  #loadBeanDefinitions 
	 * @see  BeanDefinitionReader
	 */
	protected abstract BeanDefinitionReader createBeanDefinitionReader(GenericApplicationContext context);

	/**
	 * Customize the {@link GenericApplicationContext} created by this
	 * {@code ContextLoader} <i>after</i> bean definitions have been
	 * loaded into the context but <i>before</i> the context is refreshed.
	 *
	 * <p>The default implementation is empty but can be overridden in subclasses
	 * to customize the application context.
	 *
	 * @param context the newly created application context
	 * @since 2.5
	 * @see #loadContext(MergedContextConfiguration)
	 * @see #loadContext(String...)
	 * @see #customizeContext(ConfigurableApplicationContext, MergedContextConfiguration)
	 */
	/**
	 * 在将bean定义加载到上下文中之后，但在刷新上下文之前，自定义此{@code  ContextLoader}创建的{@link  GenericApplicationContext}。 
	 *  <p>默认实现为空，但可以在子类中覆盖它以自定义应用程序上下文。 
	 *  
	 * @param 上下文自2.5起的新创建的应用程序上下文
	 * @see  #loadContext（MergedContextConfiguration）
	 * @see  #loadContext（String ...）
	 * @see  #customizeContext（ConfigurableApplicationContext，MergedContextConfiguration）
	 */
	protected void customizeContext(GenericApplicationContext context) {
	}

}
