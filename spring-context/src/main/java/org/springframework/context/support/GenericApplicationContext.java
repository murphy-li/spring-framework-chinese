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

package org.springframework.context.support;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import printer.MingLoggerImpl;

/**
 * Generic ApplicationContext implementation that holds a single internal
 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory}
 * instance and does not assume a specific bean definition format. Implements
 * the {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
 * interface in order to allow for applying any bean definition readers to it.
 *
 * <p>Typical usage is to register a variety of bean definitions via the
 * {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
 * interface and then call {@link #refresh()} to initialize those beans
 * with application context semantics (handling
 * {@link org.springframework.context.ApplicationContextAware}, auto-detecting
 * {@link org.springframework.beans.factory.config.BeanFactoryPostProcessor BeanFactoryPostProcessors},
 * etc).
 *
 * <p>In contrast to other ApplicationContext implementations that create a new
 * internal BeanFactory instance for each refresh, the internal BeanFactory of
 * this context is available right from the start, to be able to register bean
 * definitions on it. {@link #refresh()} may only be called once.
 *
 * <p>Usage example:
 *
 * <pre class="code">
 * GenericApplicationContext ctx = new GenericApplicationContext();
 * XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
 * xmlReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
 * PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(ctx);
 * propReader.loadBeanDefinitions(new ClassPathResource("otherBeans.properties"));
 * ctx.refresh();
 *
 * MyBean myBean = (MyBean) ctx.getBean("myBean");
 * ...</pre>
 *
 * For the typical case of XML bean definitions, simply use
 * {@link ClassPathXmlApplicationContext} or {@link FileSystemXmlApplicationContext},
 * which are easier to set up - but less flexible, since you can just use standard
 * resource locations for XML bean definitions, rather than mixing arbitrary bean
 * definition formats. The equivalent in a web environment is
 * {@link org.springframework.web.context.support.XmlWebApplicationContext}.
 *
 * <p>For custom application context implementations that are supposed to read
 * special bean definition formats in a refreshable manner, consider deriving
 * from the {@link AbstractRefreshableApplicationContext} base class.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 1.1.2
 * @see #registerBeanDefinition
 * @see #refresh()
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 * @see org.springframework.beans.factory.support.PropertiesBeanDefinitionReader
 */
/**
 * 通用ApplicationContext实现，它具有单个内部{@link  org.springframework.beans.factory.support.DefaultListableBeanFactory}实例，并且不采用特定的bean定义格式。 
 * 实现{@link  org.springframework.beans.factory.support.BeanDefinitionRegistry}接口，以允许将任何bean定义读取器应用于该接口。 
 *  <p>典型用法是通过{@link  org.springframework.beans.factory.support.BeanDefinitionRegistry}接口注册各种bean定义，然后调用{@link  #refresh（）}初始化那些bean具有应用程序上下文语义（处理{@link  org.springframework.context.ApplicationContextAware}，自动检测{@link  org.springframework.beans.factory.config.BeanFactoryPostProcessor BeanFactoryPostProcessors}等）。 
 *  <p>与为每次刷新创建一个新的内部BeanFactory实例的其他ApplicationContext实现相反，此上下文的内部BeanFactory从一开始就可用，以便能够在其上注册Bean定义。 
 *  {@link  #refresh（）}只能被调用一次。 
 *  <p>用法示例：<pre class ="code"> GenericApplicationContext ctx = new GenericApplicationContext（）; XmlBeanDefinitionReader xmlReader =新的XmlBeanDefinitionReader（ctx）; xmlReader.loadBeanDefinitions（new ClassPathResource（"applicationContext.xml"））; PropertiesBeanDefinitionReader propReader =新的PropertiesBeanDefinitionReader（ctx）; propReader.loadBeanDefinitions（new ClassPathResource（"otherBeans.properties"））; ctx.refresh（）; MyBean myBean =（MyBean）ctx.getBean（"myBean"）; ... </ pre>对于XML Bean定义的典型情况，只需使用{@link  ClassPathXmlApplicationContext}或{@link  FileSystemXmlApplicationContext}，它们更易于设置-但灵活性较差，因为您可以使用XML bean定义的标准资源位置，而不是混合使用任意bean定义格式。 
 * 在网络环境中，等效项是{@link  org.springframework.web.context.support.XmlWebApplicationContext}。 
 *  <p>对于应该以可刷新方式读取特殊bean定义格式的自定义应用程序上下文实现，请考虑从{@link  AbstractRefreshableApplicationContext}基类派生。 
 *  @author  Juergen Hoeller @author 克里斯·比姆斯（Chris Beams）从1.1.2开始
 * @see  #registerBeanDefinition 
 * @see  #refresh（） org.springframework.beans.factory.support.PropertiesBeanDefinitionReader
 */
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

	private final DefaultListableBeanFactory beanFactory;

	@Nullable
	private ResourceLoader resourceLoader;

	private boolean customClassLoader = false;

	private final AtomicBoolean refreshed = new AtomicBoolean();


	/**
	 * Create a new GenericApplicationContext.
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 创建一个新的GenericApplicationContext。 
	 *  
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public GenericApplicationContext() {
		super();

		this.beanFactory = new DefaultListableBeanFactory();
	}

	/**
	 * Create a new GenericApplicationContext with the given DefaultListableBeanFactory.
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 使用给定的DefaultListableBeanFactory创建一个新的GenericApplicationContext。 
	 *  
	 * @param  beanFactory用于此上下文的DefaultListableBeanFactory实例
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public GenericApplicationContext(DefaultListableBeanFactory beanFactory) {
		Assert.notNull(beanFactory, "BeanFactory must not be null");
		this.beanFactory = beanFactory;
	}

	/**
	 * Create a new GenericApplicationContext with the given parent.
	 * @param parent the parent application context
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 使用给定的父级创建一个新的GenericApplicationContext。 
	 *  
	 * @param 父级父级应用程序上下文
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public GenericApplicationContext(@Nullable ApplicationContext parent) {
		this();
		setParent(parent);
	}

	/**
	 * Create a new GenericApplicationContext with the given DefaultListableBeanFactory.
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 * @param parent the parent application context
	 * @see #registerBeanDefinition
	 * @see #refresh
	 */
	/**
	 * 使用给定的DefaultListableBeanFactory创建一个新的GenericApplicationContext。 
	 *  
	 * @param  beanFactory用于此上下文的DefaultListableBeanFactory实例
	 * @param 父级父应用程序上下文
	 * @see  #registerBeanDefinition 
	 * @see  #refresh
	 */
	public GenericApplicationContext(DefaultListableBeanFactory beanFactory, ApplicationContext parent) {
		this(beanFactory);
		setParent(parent);
	}


	/**
	 * Set the parent of this application context, also setting
	 * the parent of the internal BeanFactory accordingly.
	 * @see org.springframework.beans.factory.config.ConfigurableBeanFactory#setParentBeanFactory
	 */
	/**
	 * 设置此应用程序上下文的父级，还相应地设置内部BeanFactory的父级。 
	 *  
	 * @see  org.springframework.beans.factory.config.ConfigurableBeanFactory＃setParentBeanFactory
	 */
	@Override
	public void setParent(@Nullable ApplicationContext parent) {
		super.setParent(parent);
		this.beanFactory.setParentBeanFactory(getInternalParentBeanFactory());
	}

	/**
	 * Set whether it should be allowed to override bean definitions by registering
	 * a different definition with the same name, automatically replacing the former.
	 * If not, an exception will be thrown. Default is "true".
	 * @since 3.0
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
	 */
	/**
	 * 通过注册具有相同名称的其他定义（自动替换前者）来设置是否应允许它覆盖bean定义。 
	 * 否则，将引发异常。 
	 * 默认值为"true"。 
	 *  @since 3.0 
	 * @see  org.springframework.beans.factory.support.DefaultListableBeanFactory＃setAllowBeanDefinitionOverriding
	 */
	public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
		this.beanFactory.setAllowBeanDefinitionOverriding(allowBeanDefinitionOverriding);
	}

	/**
	 * Set whether to allow circular references between beans - and automatically
	 * try to resolve them.
	 * <p>Default is "true". Turn this off to throw an exception when encountering
	 * a circular reference, disallowing them completely.
	 * @since 3.0
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowCircularReferences
	 */
	/**
	 * 设置是否在bean之间允许循环引用-并自动尝试解决它们。 
	 *  <p>默认为"true"。 
	 * 禁用此选项会在遇到循环引用时引发异常，从而完全禁止它们。 
	 *  @since 3.0 
	 * @see  org.springframework.beans.factory.support.DefaultListableBeanFactory＃setAllowCircularReferences
	 */
	public void setAllowCircularReferences(boolean allowCircularReferences) {
		this.beanFactory.setAllowCircularReferences(allowCircularReferences);
	}

	/**
	 * Set a ResourceLoader to use for this context. If set, the context will
	 * delegate all {@code getResource} calls to the given ResourceLoader.
	 * If not set, default resource loading will apply.
	 * <p>The main reason to specify a custom ResourceLoader is to resolve
	 * resource paths (without URL prefix) in a specific fashion.
	 * The default behavior is to resolve such paths as class path locations.
	 * To resolve resource paths as file system locations, specify a
	 * FileSystemResourceLoader here.
	 * <p>You can also pass in a full ResourcePatternResolver, which will
	 * be autodetected by the context and used for {@code getResources}
	 * calls as well. Else, default resource pattern matching will apply.
	 * @see #getResource
	 * @see org.springframework.core.io.DefaultResourceLoader
	 * @see org.springframework.core.io.FileSystemResourceLoader
	 * @see org.springframework.core.io.support.ResourcePatternResolver
	 * @see #getResources
	 */
	/**
	 * 设置用于该上下文的ResourceLoader。 
	 * 如果设置，则上下文会将所有{@code  getResource}调用委派给给定的ResourceLoader。 
	 * 如果未设置，则将应用默认资源加载。 
	 *  <p>指定自定义ResourceLoader的主要原因是要以特定方式解析资源路径（无URL前缀）。 
	 * 默认行为是将此类路径解析为类路径位置。 
	 * 要将资源路径解析为文件系统位置，请在此处指定FileSystemResourceLoader。 
	 *  <p>您还可以传入完整的ResourcePatternResolver，它将由上下文自动检测到，并也用于{@code  getResources}调用。 
	 * 否则，将应用默认资源模式匹配。 
	 *  
	 * @see  #getResource 
	 * @see  org.springframework.core.io.DefaultResourceLoader 
	 * @see  org.springframework.core.io.FileSystemResourceLoader 
	 * @see  org.springframework.core.io.support.ResourcePatternResolver <
	 * @see > #getResources
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


	//---------------------------------------------------------------------
	// ResourceLoader / ResourcePatternResolver override if necessary
	//---------------------------------------------------------------------

	/**
	 * This implementation delegates to this context's ResourceLoader if set,
	 * falling back to the default superclass behavior else.
	 * @see #setResourceLoader
	 */
	/**
	 * 如果已设置，则此实现将委派给该上下文的ResourceLoader，其他情况则转而使用默认的超类行为。 
	 *  
	 * @see  #setResourceLoader
	 */
	@Override
	public Resource getResource(String location) {
		if (this.resourceLoader != null) {
			return this.resourceLoader.getResource(location);
		}
		return super.getResource(location);
	}

	/**
	 * This implementation delegates to this context's ResourceLoader if it
	 * implements the ResourcePatternResolver interface, falling back to the
	 * default superclass behavior else.
	 * @see #setResourceLoader
	 */
	/**
	 * 如果实现了ResourcePatternResolver接口，则此实现将委派给该上下文的ResourceLoader，否则返回默认的超类行为。 
	 *  
	 * @see  #setResourceLoader
	 */
	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		if (this.resourceLoader instanceof ResourcePatternResolver) {
			return ((ResourcePatternResolver) this.resourceLoader).getResources(locationPattern);
		}
		return super.getResources(locationPattern);
	}

	@Override
	public void setClassLoader(@Nullable ClassLoader classLoader) {
		super.setClassLoader(classLoader);
		this.customClassLoader = true;
	}

	@Override
	@Nullable
	public ClassLoader getClassLoader() {
		if (this.resourceLoader != null && !this.customClassLoader) {
			return this.resourceLoader.getClassLoader();
		}
		return super.getClassLoader();
	}


	//---------------------------------------------------------------------
	// Implementations of AbstractApplicationContext's template methods
	//---------------------------------------------------------------------

	/**
	 * Do nothing: We hold a single internal BeanFactory and rely on callers
	 * to register beans through our public methods (or the BeanFactory's).
	 * @see #registerBeanDefinition
	 */
	/**
	 * 什么都不做：我们只拥有一个内部BeanFactory并依靠调用者通过我们的公共方法（或BeanFactory的方法）注册Bean。 
	 *  
	 * @see  #registerBeanDefinition
	 */
	@Override
	protected final void refreshBeanFactory() throws IllegalStateException {


		if (!this.refreshed.compareAndSet(false, true)) {
			throw new IllegalStateException(
					"GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
		}

		this.beanFactory.setSerializationId(getId());
	}

	@Override
	protected void cancelRefresh(BeansException ex) {
		this.beanFactory.setSerializationId(null);
		super.cancelRefresh(ex);
	}

	/**
	 * Not much to do: We hold a single internal BeanFactory that will never
	 * get released.
	 */
	/**
	 * 没什么可做的：我们拥有一个永远不会释放的内部BeanFactory。 
	 * 
	 */
	@Override
	protected final void closeBeanFactory() {
		this.beanFactory.setSerializationId(null);
	}

	/**
	 * Return the single internal BeanFactory held by this context
	 * (as ConfigurableListableBeanFactory).
	 */
	/**
	 * 返回此上下文拥有的单个内部BeanFactory（作为ConfigurableListableBeanFactory）。 
	 * 
	 */
	@Override
	public final ConfigurableListableBeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	/**
	 * Return the underlying bean factory of this context,
	 * available for registering bean definitions.
	 * <p><b>NOTE:</b> You need to call {@link #refresh()} to initialize the
	 * bean factory and its contained beans with application context semantics
	 * (autodetecting BeanFactoryPostProcessors, etc).
	 * @return the internal bean factory (as DefaultListableBeanFactory)
	 */
	/**
	 * 返回此上下文的基础bean工厂，可用于注册bean定义。 
	 *  <p> <b>注意：</ b>您需要调用{@link  #refresh（）}以使用应用程序上下文语义（自动检测BeanFactoryPostProcessors等）初始化bean工厂及其包含的bean。 
	 *  
	 * @return 内部bean工厂（作为DefaultListableBeanFactory）
	 */
	public final DefaultListableBeanFactory getDefaultListableBeanFactory() {
		return this.beanFactory;
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		assertBeanFactoryActive();
		return this.beanFactory;
	}


	//---------------------------------------------------------------------
	// Implementation of BeanDefinitionRegistry
	//---------------------------------------------------------------------

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {

		this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
	}

	@Override
	public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		this.beanFactory.removeBeanDefinition(beanName);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
		return this.beanFactory.getBeanDefinition(beanName);
	}

	@Override
	public boolean isBeanNameInUse(String beanName) {
		return this.beanFactory.isBeanNameInUse(beanName);
	}

	@Override
	public void registerAlias(String beanName, String alias) {
		this.beanFactory.registerAlias(beanName, alias);
	}

	@Override
	public void removeAlias(String alias) {
		this.beanFactory.removeAlias(alias);
	}

	@Override
	public boolean isAlias(String beanName) {
		return this.beanFactory.isAlias(beanName);
	}


	//---------------------------------------------------------------------
	// Convenient methods for registering individual beans
	//---------------------------------------------------------------------

	/**
	 * Register a bean from the given bean class, optionally providing explicit
	 * constructor arguments for consideration in the autowiring process.
	 * @param beanClass the class of the bean
	 * @param constructorArgs custom argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.2 (since 5.0 on the AnnotationConfigApplicationContext subclass)
	 */
	/**
	 * 从给定的bean类中注册一个bean，可以选择提供明确的构造函数参数，以供自动装配过程中考虑。 
	 *  
	 * @param  beanClass bean 
	 * @param 构造函数的类将自定义参数值输入到Spring的构造函数解析算法中，解析所有参数或仅解析特定参数，其余参数则通过常规自动装配来解决（可能是{@code  null}或为空）@since 5.2（从AnnotationConfigApplicationContext子类的5.0开始）
	 */
	public <T> void registerBean(Class<T> beanClass, Object... constructorArgs) {
		registerBean(null, beanClass, constructorArgs);
	}

	/**
	 * Register a bean from the given bean class, optionally providing explicit
	 * constructor arguments for consideration in the autowiring process.
	 * @param beanName the name of the bean (may be {@code null})
	 * @param beanClass the class of the bean
	 * @param constructorArgs custom argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.2 (since 5.0 on the AnnotationConfigApplicationContext subclass)
	 */
	/**
	 * 从给定的bean类中注册一个bean，可以选择提供明确的构造函数参数，以供自动装配过程中考虑。 
	 *  
	 * @param  beanName bean的名称（可以为{@code  null}）
	 * @param  beanClass bean的类
	 * @param 构造函数将自定义参数值输入到Spring的构造函数解析算法中，以解决所有参数或仅特定参数，其余参数可通过常规自动装配（可能为{@code  null}或为空）从5.2版开始（从AnnotationConfigApplicationContext子类的5.0版开始）
	 */
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, Object... constructorArgs) {
		registerBean(beanName, beanClass, (Supplier<T>) null,
				bd -> {
					for (Object arg : constructorArgs) {
						bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
					}
				});
	}

	/**
	 * Register a bean from the given bean class, optionally customizing its
	 * bean definition metadata (typically declared as a lambda expression).
	 * @param beanClass the class of the bean (resolving a public constructor
	 * to be autowired, possibly simply the default constructor)
	 * @param customizers one or more callbacks for customizing the factory's
	 * {@link BeanDefinition}, e.g. setting a lazy-init or primary flag
	 * @since 5.0
	 * @see #registerBean(String, Class, Supplier, BeanDefinitionCustomizer...)
	 */
	/**
	 * 从给定的bean类中注册一个bean，可以选择自定义其bean定义元数据（通常声明为lambda表达式）。 
	 *  
	 * @param  beanClass bean的类（解析要自动装配的公共构造函数，可能只是默认构造函数）
	 * @param 定制程序一个或多个回调，用于自定义工厂的{@link  BeanDefinition}，例如设置一个惰性初始或主要标志@since 5.0 
	 * @see  #registerBean（String，Class，Supplier，BeanDefinitionCustomizer ...）
	 */
	public final <T> void registerBean(Class<T> beanClass, BeanDefinitionCustomizer... customizers) {
		registerBean(null, beanClass, null, customizers);
	}

	/**
	 * Register a bean from the given bean class, optionally customizing its
	 * bean definition metadata (typically declared as a lambda expression).
	 * @param beanName the name of the bean (may be {@code null})
	 * @param beanClass the class of the bean (resolving a public constructor
	 * to be autowired, possibly simply the default constructor)
	 * @param customizers one or more callbacks for customizing the factory's
	 * {@link BeanDefinition}, e.g. setting a lazy-init or primary flag
	 * @since 5.0
	 * @see #registerBean(String, Class, Supplier, BeanDefinitionCustomizer...)
	 */
	/**
	 * 从给定的bean类中注册一个bean，可以选择自定义其bean定义元数据（通常声明为lambda表达式）。 
	 *  
	 * @param  beanName bean的名称（可以为{@code  null}）
	 * @param  beanClass bean的类（解析要自动装配的公共构造函数，可能只是默认构造函数）
	 * @param 定制程序一个或多个用于自定义工厂的{@link  BeanDefinition}的回调，例如设置一个惰性初始或主要标志@since 5.0 
	 * @see  #registerBean（String，Class，Supplier，BeanDefinitionCustomizer ...）
	 */
	public final <T> void registerBean(
			@Nullable String beanName, Class<T> beanClass, BeanDefinitionCustomizer... customizers) {

		registerBean(beanName, beanClass, null, customizers);
	}

	/**
	 * Register a bean from the given bean class, using the given supplier for
	 * obtaining a new instance (typically declared as a lambda expression or
	 * method reference), optionally customizing its bean definition metadata
	 * (again typically declared as a lambda expression).
	 * @param beanClass the class of the bean
	 * @param supplier a callback for creating an instance of the bean
	 * @param customizers one or more callbacks for customizing the factory's
	 * {@link BeanDefinition}, e.g. setting a lazy-init or primary flag
	 * @since 5.0
	 * @see #registerBean(String, Class, Supplier, BeanDefinitionCustomizer...)
	 */
	/**
	 * 使用给定的供应商从给定的bean类中注册一个bean，以获取新实例（通常声明为lambda表达式或方法引用），还可以选择自定义其bean定义元数据（再次通常声明为lambda表达式）。 
	 *  
	 * @param  beanClass Bean的类
	 * @param 供应商用于创建bean实例的回调
	 * @param 定制器一个或多个用于定制工厂的{@link  BeanDefinition}的回调，例如设置一个惰性初始或主要标志@since 5.0 
	 * @see  #registerBean（String，Class，Supplier，BeanDefinitionCustomizer ...）
	 */
	public final <T> void registerBean(
			Class<T> beanClass, Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {

		registerBean(null, beanClass, supplier, customizers);
	}

	/**
	 * Register a bean from the given bean class, using the given supplier for
	 * obtaining a new instance (typically declared as a lambda expression or
	 * method reference), optionally customizing its bean definition metadata
	 * (again typically declared as a lambda expression).
	 * <p>This method can be overridden to adapt the registration mechanism for
	 * all {@code registerBean} methods (since they all delegate to this one).
	 * @param beanName the name of the bean (may be {@code null})
	 * @param beanClass the class of the bean
	 * @param supplier a callback for creating an instance of the bean (in case
	 * of {@code null}, resolving a public constructor to be autowired instead)
	 * @param customizers one or more callbacks for customizing the factory's
	 * {@link BeanDefinition}, e.g. setting a lazy-init or primary flag
	 * @since 5.0
	 */
	/**
	 * 使用给定的供应商从给定的bean类中注册一个bean，以获取新实例（通常声明为lambda表达式或方法引用），还可以选择自定义其bean定义元数据（再次通常声明为lambda表达式）。 
	 *  <p>可以重写此方法以为所有{@code  registerBean}方法改编注册机制（因为它们都委托给了该方法）。 
	 *  
	 * @param  beanName bean的名称（可以为{@code  null}）
	 * @param  beanClass bean的类
	 * @param 供应商用于创建bean实例的回调（如果使用{ @code  null}，从而解决了要自动装配的公共构造函数的问题。 
	 * ）
	 * @param 定制程序一个或多个回调，用于定制工厂的{@link  BeanDefinition}，例如从5.0开始设置lazy-init或primary标志
	 */
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass,
			@Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {

		ClassDerivedBeanDefinition beanDefinition = new ClassDerivedBeanDefinition(beanClass);
		if (supplier != null) {
			beanDefinition.setInstanceSupplier(supplier);
		}
		for (BeanDefinitionCustomizer customizer : customizers) {
			customizer.customize(beanDefinition);
		}

		String nameToUse = (beanName != null ? beanName : beanClass.getName());
		registerBeanDefinition(nameToUse, beanDefinition);
	}


	/**
	 * {@link RootBeanDefinition} marker subclass for {@code #registerBean} based
	 * registrations with flexible autowiring for public constructors.
	 */
	/**
	 * 用于基于{@code  #registerBean}的注册的{@link  RootBeanDefinition}标记子类，并为公共构造函数提供了灵活的自动装配。 
	 * 
	 */
	@SuppressWarnings("serial")
	private static class ClassDerivedBeanDefinition extends RootBeanDefinition {

		public ClassDerivedBeanDefinition(Class<?> beanClass) {
			super(beanClass);
		}

		public ClassDerivedBeanDefinition(ClassDerivedBeanDefinition original) {
			super(original);
		}

		@Override
		@Nullable
		public Constructor<?>[] getPreferredConstructors() {
			Class<?> clazz = getBeanClass();
			Constructor<?> primaryCtor = BeanUtils.findPrimaryConstructor(clazz);
			if (primaryCtor != null) {
				return new Constructor<?>[] {primaryCtor};
			}
			Constructor<?>[] publicCtors = clazz.getConstructors();
			if (publicCtors.length > 0) {
				return publicCtors;
			}
			return null;
		}

		@Override
		public RootBeanDefinition cloneBeanDefinition() {
			return new ClassDerivedBeanDefinition(this);
		}
	}

}
