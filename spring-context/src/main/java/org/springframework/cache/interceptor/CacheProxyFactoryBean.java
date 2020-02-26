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

package org.springframework.cache.interceptor;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractSingletonProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cache.CacheManager;

/**
 * Proxy factory bean for simplified declarative caching handling.
 * This is a convenient alternative to a standard AOP
 * {@link org.springframework.aop.framework.ProxyFactoryBean}
 * with a separate {@link CacheInterceptor} definition.
 *
 * <p>This class is designed to facilitate declarative cache demarcation: namely, wrapping
 * a singleton target object with a caching proxy, proxying all the interfaces that the
 * target implements. Exists primarily for third-party framework integration.
 * <strong>Users should favor the {@code cache:} XML namespace
 * {@link org.springframework.cache.annotation.Cacheable @Cacheable} annotation.</strong>
 * See the
 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotations">declarative annotation-based caching</a>
 * section of the Spring reference documentation for more information.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 3.1
 * @see org.springframework.aop.framework.ProxyFactoryBean
 * @see CacheInterceptor
 */
/**
 * 代理工厂bean，用于简化声明式缓存处理。 
 * 这是使用单独的{@link  CacheInterceptor}定义的标准AOP {@link  org.springframework.aop.framework.ProxyFactoryBean}的便捷替代方法。 
 *  <p>此类旨在促进声明性高速缓存划分：即，将单个目标对象与高速缓存代理包装在一起，代理该目标实现的所有接口。 
 * 主要存在于第三方框架集成中。 
 *  <strong>用户应偏爱{@code 缓存：} XML名称空间{@link  org.springframework.cache.annotation.Cacheable @Cacheable}注释。 
 * </ strong>请参见<a href ="https：/有关更多信息，请参阅Spring参考文档的/docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotations">基于声明式基于注释的缓存</a>部分。 
 *  @author  Costin Leau @author  Juergen Hoeller @since 3.1起
 * @see  org.springframework.aop.framework.ProxyFactoryBean 
 * @see  CacheInterceptor
 */
@SuppressWarnings("serial")
public class CacheProxyFactoryBean extends AbstractSingletonProxyFactoryBean
		implements BeanFactoryAware, SmartInitializingSingleton {

	private final CacheInterceptor cacheInterceptor = new CacheInterceptor();

	private Pointcut pointcut = Pointcut.TRUE;


	/**
	 * Set one or more sources to find cache operations.
	 * @see CacheInterceptor#setCacheOperationSources
	 */
	/**
	 * 设置一个或多个源以查找缓存操作。 
	 *  
	 * @see  CacheInterceptor＃setCacheOperationSources
	 */
	public void setCacheOperationSources(CacheOperationSource... cacheOperationSources) {
		this.cacheInterceptor.setCacheOperationSources(cacheOperationSources);
	}

	/**
	 * Set the default {@link KeyGenerator} that this cache aspect should delegate to
	 * if no specific key generator has been set for the operation.
	 * <p>The default is a {@link SimpleKeyGenerator}.
	 * @since 5.0.3
	 * @see CacheInterceptor#setKeyGenerator
	 */
	/**
	 * 设置默认的{@link  KeyGenerator}，如果尚未为该操作设置特定的密钥生成器，则此缓存方面应委派给该默认值。 
	 *  <p>默认值为{@link  SimpleKeyGenerator}。 
	 *  @since 5.0.3 
	 * @see  CacheInterceptor＃setKeyGenerator
	 */
	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.cacheInterceptor.setKeyGenerator(keyGenerator);
	}

	/**
	 * Set the default {@link CacheResolver} that this cache aspect should delegate
	 * to if no specific cache resolver has been set for the operation.
	 * <p>The default resolver resolves the caches against their names and the
	 * default cache manager.
	 * @since 5.0.3
	 * @see CacheInterceptor#setCacheResolver
	 */
	/**
	 * 设置默认的{@link  CacheResolver}，如果尚未为操作设置特定的缓存解析器，则应将此缓存方面委派给该默认{{@link> CacheResolver}。 
	 *  <p>默认解析器根据名称和默认缓存管理器解析缓存。 
	 *  @since 5.0.3 
	 * @see  CacheInterceptor＃setCacheResolver
	 */
	public void setCacheResolver(CacheResolver cacheResolver) {
		this.cacheInterceptor.setCacheResolver(cacheResolver);
	}

	/**
	 * Set the {@link CacheManager} to use to create a default {@link CacheResolver}.
	 * Replace the current {@link CacheResolver}, if any.
	 * @since 5.0.3
	 * @see CacheInterceptor#setCacheManager
	 */
	/**
	 * 设置{@link  CacheManager}以用于创建默认的{@link  CacheResolver}。 
	 * 替换当前的{@link  CacheResolver}（如果有）。 
	 *  @since 5.0.3 
	 * @see  CacheInterceptor＃setCacheManager
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheInterceptor.setCacheManager(cacheManager);
	}

	/**
	 * Set a pointcut, i.e. a bean that triggers conditional invocation of the
	 * {@link CacheInterceptor} depending on the method and attributes passed.
	 * <p>Note: Additional interceptors are always invoked.
	 * @see #setPreInterceptors
	 * @see #setPostInterceptors
	 */
	/**
	 * 设置切入点，即根据所传递的方法和属性，触发{@link  CacheInterceptor}的条件调用的bean。 
	 *  <p>注意：总是调用其他拦截器。 
	 *  
	 * @see  #setPreInterceptors 
	 * @see  #setPostInterceptors
	 */
	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.cacheInterceptor.setBeanFactory(beanFactory);
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.cacheInterceptor.afterSingletonsInstantiated();
	}


	@Override
	protected Object createMainInterceptor() {
		this.cacheInterceptor.afterPropertiesSet();
		return new DefaultPointcutAdvisor(this.pointcut, this.cacheInterceptor);
	}

}
