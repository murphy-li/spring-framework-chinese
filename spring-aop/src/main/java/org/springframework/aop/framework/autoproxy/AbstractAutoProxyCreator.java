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

package org.springframework.aop.framework.autoproxy;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.aop.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyProcessorSupport;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} implementation
 * that wraps each eligible bean with an AOP proxy, delegating to specified interceptors
 * before invoking the bean itself.
 *
 * <p>This class distinguishes between "common" interceptors: shared for all proxies it
 * creates, and "specific" interceptors: unique per bean instance. There need not be any
 * common interceptors. If there are, they are set using the interceptorNames property.
 * As with {@link org.springframework.aop.framework.ProxyFactoryBean}, interceptors names
 * in the current factory are used rather than bean references to allow correct handling
 * of prototype advisors and interceptors: for example, to support stateful mixins.
 * Any advice type is supported for {@link #setInterceptorNames "interceptorNames"} entries.
 *
 * <p>Such auto-proxying is particularly useful if there's a large number of beans that
 * need to be wrapped with similar proxies, i.e. delegating to the same interceptors.
 * Instead of x repetitive proxy definitions for x target beans, you can register
 * one single such post processor with the bean factory to achieve the same effect.
 *
 * <p>Subclasses can apply any strategy to decide if a bean is to be proxied, e.g. by type,
 * by name, by definition details, etc. They can also return additional interceptors that
 * should just be applied to the specific bean instance. A simple concrete implementation is
 * {@link BeanNameAutoProxyCreator}, identifying the beans to be proxied via given names.
 *
 * <p>Any number of {@link TargetSourceCreator} implementations can be used to create
 * a custom target source: for example, to pool prototype objects. Auto-proxying will
 * occur even if there is no advice, as long as a TargetSourceCreator specifies a custom
 * {@link org.springframework.aop.TargetSource}. If there are no TargetSourceCreators set,
 * or if none matches, a {@link org.springframework.aop.target.SingletonTargetSource}
 * will be used by default to wrap the target bean instance.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Rob Harrop
 * @since 13.10.2003
 * @see #setInterceptorNames
 * @see #getAdvicesAndAdvisorsForBean
 * @see BeanNameAutoProxyCreator
 * @see DefaultAdvisorAutoProxyCreator
 */
/**
 * {@link  org.springframework.beans.factory.config.BeanPostProcessor}实现，使用AOP代理包装每个合格的bean，并在调用bean本身之前委派给指定的拦截器。 
 *  <p>此类区分"通用"拦截器（由它创建的所有代理共享）和"特定"拦截器：每个bean实例唯一。 
 * 不需要任何通用的拦截器。 
 * 如果存在，则使用interceptorNames属性进行设置。 
 * 与{@link  org.springframework.aop.framework.ProxyFactoryBean}一样，使用当前工厂中的侦听器名称（而不是bean引用）来正确处理原型顾问程序和侦听器：例如，支持有状态的mixin。 
 *  {@link  #setInterceptorNames"interceptorNames"}条目支持任何建议类型。 
 *  <p>如果存在大量需要使用相似代理（即委派给相同拦截器）的Bean，则这种自动代理特别有用。 
 * 您可以向bean工厂注册一个这样的后处理器，而不是为x个目标bean进行x个重复的代理定义，以达到相同的效果。 
 *  <p>子类可以应用任何策略来确定是否要代理Bean，例如按类型，名称，定义详细信息等。 
 * 它们还可以返回应仅应用于特定bean实例的其他拦截器。 
 * 一个简单的具体实现是{@link  BeanNameAutoProxyCreator}，它通过给定的名称标识要代理的bean。 
 *  <p>任意数量的{@link  TargetSourceCreator}实现均可用于创建自定义目标源：例如，用于合并原型对象。 
 * 即使没有建议，也会自动进行代理，只要TargetSourceCreator指定自定义{@link  org.springframework.aop.TargetSource}。 
 * 如果没有设置TargetSourceCreators或没有匹配项，则默认情况下将使用{@link  org.springframework.aop.target.SingletonTargetSource}来包装目标bean实例。 
 *  @author  Juergen Hoeller @author  Rod Johnson @author  Rob Harrop @2003年10月13日
 * @see  #setInterceptorNames 
 * @see  #getAdvicesAndAdvisorsForBean 
 * @see  BeanNameAutoProxyCreator 
 * @see  DefaultAdvisorAutoProxyCreator
 */
@SuppressWarnings("serial")
public abstract class AbstractAutoProxyCreator extends ProxyProcessorSupport
		implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

	/**
	 * Convenience constant for subclasses: Return value for "do not proxy".
	 * @see #getAdvicesAndAdvisorsForBean
	 */
	/**
	 * 子类的便利常量："不代理"的返回值。 
	 *  
	 * @see  #getAdvicesAndAdvisorsForBean
	 */
	@Nullable
	protected static final Object[] DO_NOT_PROXY = null;

	/**
	 * Convenience constant for subclasses: Return value for
	 * "proxy without additional interceptors, just the common ones".
	 * @see #getAdvicesAndAdvisorsForBean
	 */
	/**
	 * 子类的便利常数："没有附加拦截器的代理，只有常见的拦截器"的返回值。 
	 *  
	 * @see  #getAdvicesAndAdvisorsForBean
	 */
	protected static final Object[] PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS = new Object[0];


	/** Logger available to subclasses. */
	/**
	 * 记录器可用于子类。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/** Default is global AdvisorAdapterRegistry. */
	/**
	 * 默认值为全局AdvisorAdapterRegistry。 
	 * 
	 */
	private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

	/**
	 * Indicates whether or not the proxy should be frozen. Overridden from super
	 * to prevent the configuration from becoming frozen too early.
	 */
	/**
	 * 指示是否应冻结代理。 
	 * 从super覆盖，以防止配置过早冻结。 
	 * 
	 */
	private boolean freezeProxy = false;

	/** Default is no common interceptors. */
	/**
	 * 默认为没有通用拦截器。 
	 * 
	 */
	private String[] interceptorNames = new String[0];

	private boolean applyCommonInterceptorsFirst = true;

	@Nullable
	private TargetSourceCreator[] customTargetSourceCreators;

	@Nullable
	private BeanFactory beanFactory;

	private final Set<String> targetSourcedBeans = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

	private final Map<Object, Object> earlyProxyReferences = new ConcurrentHashMap<>(16);

	private final Map<Object, Class<?>> proxyTypes = new ConcurrentHashMap<>(16);

	private final Map<Object, Boolean> advisedBeans = new ConcurrentHashMap<>(256);


	/**
	 * Set whether or not the proxy should be frozen, preventing advice
	 * from being added to it once it is created.
	 * <p>Overridden from the super class to prevent the proxy configuration
	 * from being frozen before the proxy is created.
	 */
	/**
	 * 设置是否应冻结代理，以防止在创建代理后向其添加建议。 
	 *  <p>从超类重写，以防止在创建代理之前冻结代理配置。 
	 * 
	 */
	@Override
	public void setFrozen(boolean frozen) {
		this.freezeProxy = frozen;
	}

	@Override
	public boolean isFrozen() {
		return this.freezeProxy;
	}

	/**
	 * Specify the {@link AdvisorAdapterRegistry} to use.
	 * <p>Default is the global {@link AdvisorAdapterRegistry}.
	 * @see org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry
	 */
	/**
	 * 指定要使用的{@link  AdvisorAdapterRegistry}。 
	 *  <p>默认为全局{@link  AdvisorAdapterRegistry}。 
	 *  
	 * @see  org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry
	 */
	public void setAdvisorAdapterRegistry(AdvisorAdapterRegistry advisorAdapterRegistry) {
		this.advisorAdapterRegistry = advisorAdapterRegistry;
	}

	/**
	 * Set custom {@code TargetSourceCreators} to be applied in this order.
	 * If the list is empty, or they all return null, a {@link SingletonTargetSource}
	 * will be created for each bean.
	 * <p>Note that TargetSourceCreators will kick in even for target beans
	 * where no advices or advisors have been found. If a {@code TargetSourceCreator}
	 * returns a {@link TargetSource} for a specific bean, that bean will be proxied
	 * in any case.
	 * <p>{@code TargetSourceCreators} can only be invoked if this post processor is used
	 * in a {@link BeanFactory} and its {@link BeanFactoryAware} callback is triggered.
	 * @param targetSourceCreators the list of {@code TargetSourceCreators}.
	 * Ordering is significant: The {@code TargetSource} returned from the first matching
	 * {@code TargetSourceCreator} (that is, the first that returns non-null) will be used.
	 */
	/**
	 * 设置自定义{@code  TargetSourceCreators}以该顺序应用。 
	 * 如果列表为空，或者它们都返回null，则将为每个bean创建一个{@link  SingletonTargetSource}。 
	 *  <p>请注意，即使没有找到建议或顾问的目标bean，TargetSourceCreators也会启动。 
	 * 如果{@code  TargetSourceCreator}返回特定bean的{@link  TargetSource}，则在任何情况下都将对该bean进行代理。 
	 * 只有在{@link  BeanFactory}中使用此后处理器并且触发了其{@link  BeanFactoryAware}回调时，才能调用<p> {<@code> TargetSourceCreators}。 
	 *  @param targetSourceCreators是{@code  TargetSourceCreators}的列表。 
	 * 顺序很重要：将使用从第一个匹配的{@code  TargetSourceCreator}（即，第一个返回非null的）返回的{@code  TargetSource}。 
	 * 
	 */
	public void setCustomTargetSourceCreators(TargetSourceCreator... targetSourceCreators) {
		this.customTargetSourceCreators = targetSourceCreators;
	}

	/**
	 * Set the common interceptors. These must be bean names in the current factory.
	 * They can be of any advice or advisor type Spring supports.
	 * <p>If this property isn't set, there will be zero common interceptors.
	 * This is perfectly valid, if "specific" interceptors such as matching
	 * Advisors are all we want.
	 */
	/**
	 * 设置公共拦截器。 
	 * 这些必须是当前工厂中的bean名称。 
	 * 它们可以是Spring支持的任何建议或顾问类型。 
	 *  <p>如果未设置此属性，则公共拦截器为零。 
	 * 如果我们只需要"特定的"拦截器（例如匹配的Advisor），则这是完全有效的。 
	 * 
	 */
	public void setInterceptorNames(String... interceptorNames) {
		this.interceptorNames = interceptorNames;
	}

	/**
	 * Set whether the common interceptors should be applied before bean-specific ones.
	 * Default is "true"; else, bean-specific interceptors will get applied first.
	 */
	/**
	 * 设置是否应在特定于bean的拦截器之前应用公共拦截器。 
	 * 默认值为"true"； 
	 * 否则，将首先应用特定于bean的拦截器。 
	 * 
	 */
	public void setApplyCommonInterceptorsFirst(boolean applyCommonInterceptorsFirst) {
		this.applyCommonInterceptorsFirst = applyCommonInterceptorsFirst;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Return the owning {@link BeanFactory}.
	 * May be {@code null}, as this post-processor doesn't need to belong to a bean factory.
	 */
	/**
	 * 返回拥有的{@link  BeanFactory}。 
	 * 可能为{@code  null}，因为此后处理器不需要属于bean工厂。 
	 * 
	 */
	@Nullable
	protected BeanFactory getBeanFactory() {
		return this.beanFactory;
	}


	@Override
	@Nullable
	public Class<?> predictBeanType(Class<?> beanClass, String beanName) {
		if (this.proxyTypes.isEmpty()) {
			return null;
		}
		Object cacheKey = getCacheKey(beanClass, beanName);
		return this.proxyTypes.get(cacheKey);
	}

	@Override
	@Nullable
	public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) {
		return null;
	}

	@Override
	public Object getEarlyBeanReference(Object bean, String beanName) {
		Object cacheKey = getCacheKey(bean.getClass(), beanName);
		this.earlyProxyReferences.put(cacheKey, bean);
		return wrapIfNecessary(bean, beanName, cacheKey);
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
		Object cacheKey = getCacheKey(beanClass, beanName);

		if (!StringUtils.hasLength(beanName) || !this.targetSourcedBeans.contains(beanName)) {
			if (this.advisedBeans.containsKey(cacheKey)) {
				return null;
			}
			if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
				this.advisedBeans.put(cacheKey, Boolean.FALSE);
				return null;
			}
		}

		// Create proxy here if we have a custom TargetSource.
		// Suppresses unnecessary default instantiation of the target bean:
		// The TargetSource will handle target instances in a custom fashion.
		TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
		if (targetSource != null) {
			if (StringUtils.hasLength(beanName)) {
				this.targetSourcedBeans.add(beanName);
			}
			Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
			Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
			this.proxyTypes.put(cacheKey, proxy.getClass());
			return proxy;
		}

		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) {
		return true;
	}

	@Override
	public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
		return pvs;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	/**
	 * Create a proxy with the configured interceptors if the bean is
	 * identified as one to proxy by the subclass.
	 * @see #getAdvicesAndAdvisorsForBean
	 */
	/**
	 * 如果bean被子类标识为要代理的代理，则使用配置的拦截器创建代理。 
	 *  
	 * @see  #getAdvicesAndAdvisorsForBean
	 */
	@Override
	public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
		if (bean != null) {
			Object cacheKey = getCacheKey(bean.getClass(), beanName);
			if (this.earlyProxyReferences.remove(cacheKey) != bean) {
				return wrapIfNecessary(bean, beanName, cacheKey);
			}
		}
		return bean;
	}


	/**
	 * Build a cache key for the given bean class and bean name.
	 * <p>Note: As of 4.2.3, this implementation does not return a concatenated
	 * class/name String anymore but rather the most efficient cache key possible:
	 * a plain bean name, prepended with {@link BeanFactory#FACTORY_BEAN_PREFIX}
	 * in case of a {@code FactoryBean}; or if no bean name specified, then the
	 * given bean {@code Class} as-is.
	 * @param beanClass the bean class
	 * @param beanName the bean name
	 * @return the cache key for the given class and name
	 */
	/**
	 * 为给定的bean类和bean名称构建一个缓存键。 
	 *  <p>注意：从4.2.3版本开始，此实现不再返回串联的类/名称String，而是可能返回的最有效的高速缓存键：纯豆名称，以{@link  BeanFactory＃FACTORY_BEAN_PREFIX}开头{@code  FactoryBean}的； 
	 * 或者，如果未指定bean名称，则给定bean {@code  Class}原样。 
	 *  @param beanClass bean类@param beanName bean名称
	 * @return 给定类和名称的缓存键
	 */
	protected Object getCacheKey(Class<?> beanClass, @Nullable String beanName) {
		if (StringUtils.hasLength(beanName)) {
			return (FactoryBean.class.isAssignableFrom(beanClass) ?
					BeanFactory.FACTORY_BEAN_PREFIX + beanName : beanName);
		}
		else {
			return beanClass;
		}
	}

	/**
	 * Wrap the given bean if necessary, i.e. if it is eligible for being proxied.
	 * @param bean the raw bean instance
	 * @param beanName the name of the bean
	 * @param cacheKey the cache key for metadata access
	 * @return a proxy wrapping the bean, or the raw bean instance as-is
	 */
	/**
	 * 必要时包装给定的bean，即是否有资格被代理。 
	 *  @param bean原始bean实例@param beanName Bean的名称@param cacheKey用于元数据访问的缓存键
	 * @return 包装bean的代理，或原始bean实例原样
	 */
	protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
		if (StringUtils.hasLength(beanName) && this.targetSourcedBeans.contains(beanName)) {
			return bean;
		}
		if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
			return bean;
		}
		if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)) {
			this.advisedBeans.put(cacheKey, Boolean.FALSE);
			return bean;
		}

		// Create proxy if we have advice.
		Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
		if (specificInterceptors != DO_NOT_PROXY) {
			this.advisedBeans.put(cacheKey, Boolean.TRUE);
			Object proxy = createProxy(
					bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
			this.proxyTypes.put(cacheKey, proxy.getClass());
			return proxy;
		}

		this.advisedBeans.put(cacheKey, Boolean.FALSE);
		return bean;
	}

	/**
	 * Return whether the given bean class represents an infrastructure class
	 * that should never be proxied.
	 * <p>The default implementation considers Advices, Advisors and
	 * AopInfrastructureBeans as infrastructure classes.
	 * @param beanClass the class of the bean
	 * @return whether the bean represents an infrastructure class
	 * @see org.aopalliance.aop.Advice
	 * @see org.springframework.aop.Advisor
	 * @see org.springframework.aop.framework.AopInfrastructureBean
	 * @see #shouldSkip
	 */
	/**
	 * 返回给定的bean类是否表示不应代理的基础结构类。 
	 *  <p>默认实现将Advices，Advisor和AopInfrastructureBeans视为基础结构类。 
	 *  @param beanClass Bean的类
	 * @return  Bean是否代表基础结构类
	 * @see  org.aopalliance.aop.Advice 
	 * @see  org.springframework.aop.Advisor 
	 * @see  org.springframework.aop .framework.AopInfrastructureBean 
	 * @see  #shouldSkip
	 */
	protected boolean isInfrastructureClass(Class<?> beanClass) {
		boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
				Pointcut.class.isAssignableFrom(beanClass) ||
				Advisor.class.isAssignableFrom(beanClass) ||
				AopInfrastructureBean.class.isAssignableFrom(beanClass);
		if (retVal && logger.isTraceEnabled()) {
			logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
		}
		return retVal;
	}

	/**
	 * Subclasses should override this method to return {@code true} if the
	 * given bean should not be considered for auto-proxying by this post-processor.
	 * <p>Sometimes we need to be able to avoid this happening, e.g. if it will lead to
	 * a circular reference or if the existing target instance needs to be preserved.
	 * This implementation returns {@code false} unless the bean name indicates an
	 * "original instance" according to {@code AutowireCapableBeanFactory} conventions.
	 * @param beanClass the class of the bean
	 * @param beanName the name of the bean
	 * @return whether to skip the given bean
	 * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#ORIGINAL_INSTANCE_SUFFIX
	 */
	/**
	 * 如果该后处理器不考虑给定的bean自动代理，则子类应重写此方法以返回{@code  true}。 
	 *  <p>有时候我们需要能够避免这种情况的发生，例如是否将导致循环引用，或者是否需要保留现有目标实例。 
	 * 除非bean名称根据{@code  AutowireCapableBeanFactory}约定指示"原始实例"，否则此实现将返回{@code  false}。 
	 *  @param beanClass Bean的类@param beanName Bean的名称
	 * @return 是否跳过给定的Bean 
	 * @see  org.springframework.beans.factory.config.AutowireCapableBeanFactory＃ORIGINAL_INSTANCE_SUFFIX
	 */
	protected boolean shouldSkip(Class<?> beanClass, String beanName) {
		return AutoProxyUtils.isOriginalInstance(beanName, beanClass);
	}

	/**
	 * Create a target source for bean instances. Uses any TargetSourceCreators if set.
	 * Returns {@code null} if no custom TargetSource should be used.
	 * <p>This implementation uses the "customTargetSourceCreators" property.
	 * Subclasses can override this method to use a different mechanism.
	 * @param beanClass the class of the bean to create a TargetSource for
	 * @param beanName the name of the bean
	 * @return a TargetSource for this bean
	 * @see #setCustomTargetSourceCreators
	 */
	/**
	 * 为bean实例创建目标源。 
	 * 如果设置，则使用任何TargetSourceCreators。 
	 * 如果不应使用自定义TargetSource，则返回{@code  null}。 
	 *  <p>此实现使用"customTargetSourceCreator"属性。 
	 * 子类可以重写此方法以使用其他机制。 
	 *  @param beanClass为@param bean创建TargetSource的bean的类beanName bean的名称
	 * @return 此bean的TargetSource 
	 * @see  #setCustomTargetSourceCreators
	 */
	@Nullable
	protected TargetSource getCustomTargetSource(Class<?> beanClass, String beanName) {
		// We can't create fancy target sources for directly registered singletons.
		if (this.customTargetSourceCreators != null &&
				this.beanFactory != null && this.beanFactory.containsBean(beanName)) {
			for (TargetSourceCreator tsc : this.customTargetSourceCreators) {
				TargetSource ts = tsc.getTargetSource(beanClass, beanName);
				if (ts != null) {
					// Found a matching TargetSource.
					if (logger.isTraceEnabled()) {
						logger.trace("TargetSourceCreator [" + tsc +
								"] found custom TargetSource for bean with name '" + beanName + "'");
					}
					return ts;
				}
			}
		}

		// No custom TargetSource found.
		return null;
	}

	/**
	 * Create an AOP proxy for the given bean.
	 * @param beanClass the class of the bean
	 * @param beanName the name of the bean
	 * @param specificInterceptors the set of interceptors that is
	 * specific to this bean (may be empty, but not null)
	 * @param targetSource the TargetSource for the proxy,
	 * already pre-configured to access the bean
	 * @return the AOP proxy for the bean
	 * @see #buildAdvisors
	 */
	/**
	 * 为给定的bean创建一个AOP代理。 
	 *  @param beanClass Bean的类@param beanName Bean的名称@param specificInterceptors特定于此bean的拦截器集（可以为空，但不能为null）@param targetSource代理的TargetSource，已经预先配置为访问Bean 
	 * @return  Bean的AOP代理
	 * @see  #buildAdvisors
	 */
	protected Object createProxy(Class<?> beanClass, @Nullable String beanName,
			@Nullable Object[] specificInterceptors, TargetSource targetSource) {

		if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
			AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName, beanClass);
		}

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.copyFrom(this);

		if (!proxyFactory.isProxyTargetClass()) {
			if (shouldProxyTargetClass(beanClass, beanName)) {
				proxyFactory.setProxyTargetClass(true);
			}
			else {
				evaluateProxyInterfaces(beanClass, proxyFactory);
			}
		}

		Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
		proxyFactory.addAdvisors(advisors);
		proxyFactory.setTargetSource(targetSource);
		customizeProxyFactory(proxyFactory);

		proxyFactory.setFrozen(this.freezeProxy);
		if (advisorsPreFiltered()) {
			proxyFactory.setPreFiltered(true);
		}

		return proxyFactory.getProxy(getProxyClassLoader());
	}

	/**
	 * Determine whether the given bean should be proxied with its target class rather than its interfaces.
	 * <p>Checks the {@link AutoProxyUtils#PRESERVE_TARGET_CLASS_ATTRIBUTE "preserveTargetClass" attribute}
	 * of the corresponding bean definition.
	 * @param beanClass the class of the bean
	 * @param beanName the name of the bean
	 * @return whether the given bean should be proxied with its target class
	 * @see AutoProxyUtils#shouldProxyTargetClass
	 */
	/**
	 * 确定是否应使用给定的bean替代其目标类而不是其接口。 
	 *  <p>检查相应bean定义的{@link  AutoProxyUtils＃PRESERVE_TARGET_CLASS_ATTRIBUTE"preserveTargetClass"属性}。 
	 *  @param beanClass Bean的类@param beanName Bean的名称
	 * @return 是否应使用给定的Bean与其目标类进行代理
	 * @see  AutoProxyUtils＃shouldProxyTargetClass
	 */
	protected boolean shouldProxyTargetClass(Class<?> beanClass, @Nullable String beanName) {
		return (this.beanFactory instanceof ConfigurableListableBeanFactory &&
				AutoProxyUtils.shouldProxyTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName));
	}

	/**
	 * Return whether the Advisors returned by the subclass are pre-filtered
	 * to match the bean's target class already, allowing the ClassFilter check
	 * to be skipped when building advisors chains for AOP invocations.
	 * <p>Default is {@code false}. Subclasses may override this if they
	 * will always return pre-filtered Advisors.
	 * @return whether the Advisors are pre-filtered
	 * @see #getAdvicesAndAdvisorsForBean
	 * @see org.springframework.aop.framework.Advised#setPreFiltered
	 */
	/**
	 * 返回子类返回的Advisor是否已预先过滤以匹配bean的目标类，从而允许在为AOP调用构建Advisor链时跳过ClassFilter检查。 
	 *  <p>默认值为{@code  false}。 
	 * 如果子类将始终返回经过预过滤的顾问程序，则可以覆盖此子类。 
	 *  
	 * @return 顾问程序是否已预先过滤
	 * @see  #getAdvicesAndAdvisorsForBean 
	 * @see  org.springframework.aop.framework.Advised＃setPreFiltered
	 */
	protected boolean advisorsPreFiltered() {
		return false;
	}

	/**
	 * Determine the advisors for the given bean, including the specific interceptors
	 * as well as the common interceptor, all adapted to the Advisor interface.
	 * @param beanName the name of the bean
	 * @param specificInterceptors the set of interceptors that is
	 * specific to this bean (may be empty, but not null)
	 * @return the list of Advisors for the given bean
	 */
	/**
	 * 确定给定bean的顾问程序，包括适用于Advisor接口的特定拦截器以及公共拦截器。 
	 *  @param beanName bean的名称@param specificInterceptors特定于此bean的拦截器集（可以为空，但不能为null）
	 * @return 给定bean的顾问程序列表
	 */
	protected Advisor[] buildAdvisors(@Nullable String beanName, @Nullable Object[] specificInterceptors) {
		// Handle prototypes correctly...
		Advisor[] commonInterceptors = resolveInterceptorNames();

		List<Object> allInterceptors = new ArrayList<>();
		if (specificInterceptors != null) {
			allInterceptors.addAll(Arrays.asList(specificInterceptors));
			if (commonInterceptors.length > 0) {
				if (this.applyCommonInterceptorsFirst) {
					allInterceptors.addAll(0, Arrays.asList(commonInterceptors));
				}
				else {
					allInterceptors.addAll(Arrays.asList(commonInterceptors));
				}
			}
		}
		if (logger.isTraceEnabled()) {
			int nrOfCommonInterceptors = commonInterceptors.length;
			int nrOfSpecificInterceptors = (specificInterceptors != null ? specificInterceptors.length : 0);
			logger.trace("Creating implicit proxy for bean '" + beanName + "' with " + nrOfCommonInterceptors +
					" common interceptors and " + nrOfSpecificInterceptors + " specific interceptors");
		}

		Advisor[] advisors = new Advisor[allInterceptors.size()];
		for (int i = 0; i < allInterceptors.size(); i++) {
			advisors[i] = this.advisorAdapterRegistry.wrap(allInterceptors.get(i));
		}
		return advisors;
	}

	/**
	 * Resolves the specified interceptor names to Advisor objects.
	 * @see #setInterceptorNames
	 */
	/**
	 * 将指定的拦截器名称解析为Advisor对象。 
	 *  
	 * @see  #setInterceptorNames
	 */
	private Advisor[] resolveInterceptorNames() {
		BeanFactory bf = this.beanFactory;
		ConfigurableBeanFactory cbf = (bf instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory) bf : null);
		List<Advisor> advisors = new ArrayList<>();
		for (String beanName : this.interceptorNames) {
			if (cbf == null || !cbf.isCurrentlyInCreation(beanName)) {
				Assert.state(bf != null, "BeanFactory required for resolving interceptor names");
				Object next = bf.getBean(beanName);
				advisors.add(this.advisorAdapterRegistry.wrap(next));
			}
		}
		return advisors.toArray(new Advisor[0]);
	}

	/**
	 * Subclasses may choose to implement this: for example,
	 * to change the interfaces exposed.
	 * <p>The default implementation is empty.
	 * @param proxyFactory a ProxyFactory that is already configured with
	 * TargetSource and interfaces and will be used to create the proxy
	 * immediately after this method returns
	 */
	/**
	 * 子类可以选择实现此目的：例如，更改公开的接口。 
	 *  <p>默认实现为空。 
	 *  @param proxyFactory一个已经使用TargetSource和接口配置的ProxyFactory，在此方法返回后将立即用于创建代理
	 */
	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
	}


	/**
	 * Return whether the given bean is to be proxied, what additional
	 * advices (e.g. AOP Alliance interceptors) and advisors to apply.
	 * @param beanClass the class of the bean to advise
	 * @param beanName the name of the bean
	 * @param customTargetSource the TargetSource returned by the
	 * {@link #getCustomTargetSource} method: may be ignored.
	 * Will be {@code null} if no custom target source is in use.
	 * @return an array of additional interceptors for the particular bean;
	 * or an empty array if no additional interceptors but just the common ones;
	 * or {@code null} if no proxy at all, not even with the common interceptors.
	 * See constants DO_NOT_PROXY and PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS.
	 * @throws BeansException in case of errors
	 * @see #DO_NOT_PROXY
	 * @see #PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS
	 */
	/**
	 * 返回是否要代理给定的bean，要应用哪些其他建议（例如AOP联盟拦截器）和顾问。 
	 *  @param beanClass用来建议@param beanName的bean的类@param customTargetSource由{@link  #getCustomTargetSource}方法返回的TargetSource：可以忽略。 
	 * 如果未使用自定义目标源，则将为{@code  null}。 
	 *  
	 * @return 特定bean的附加拦截器数组； 
	 * 如果没有其他拦截器，而只有普通拦截器，则为空数组； 
	 * 或者或{@code  null}（如果根本没有代理，甚至没有通用的拦截器）。 
	 * 请参见常量DO_NOT_PROXY和PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS。 
	 *  
	 * @throws 发生错误时BeansException 
	 * @see  #DO_NOT_PROXY 
	 * @see  #PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS
	 */
	@Nullable
	protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName,
			@Nullable TargetSource customTargetSource) throws BeansException;

}
