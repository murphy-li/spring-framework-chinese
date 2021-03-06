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

package org.springframework.cache.jcache.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.interceptor.AbstractCacheInvoker;
import org.springframework.cache.interceptor.BasicOperation;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Base class for JSR-107 caching aspects, such as the {@link JCacheInterceptor}
 * or an AspectJ aspect.
 *
 * <p>Use the Spring caching abstraction for cache-related operations. No JSR-107
 * {@link javax.cache.Cache} or {@link javax.cache.CacheManager} are required to
 * process standard JSR-107 cache annotations.
 *
 * <p>The {@link JCacheOperationSource} is used for determining caching operations
 *
 * <p>A cache aspect is serializable if its {@code JCacheOperationSource} is serializable.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @see org.springframework.cache.interceptor.CacheAspectSupport
 * @see KeyGeneratorAdapter
 * @see CacheResolverAdapter
 */
/**
 * JSR-107缓存方面的基类，例如{@link  JCacheInterceptor}或AspectJ方面。 
 *  <p>将Spring缓存抽象用于与缓存相关的操作。 
 * 不需要JSR-107 {@link  javax.cache.Cache}或{@link  javax.cache.CacheManager}即可处理标准JSR-107缓存注释。 
 *  <p> {<@link> JCacheOperationSource}用于确定缓存操作<p>如果缓存方面的{@code  JCacheOperationSource}是可序列化的，则它是可序列化的。 
 *  @author  Stephane Nicoll @since 4.1起
 * @see  org.springframework.cache.interceptor.CacheAspectSupport 
 * @see  KeyGeneratorAdapter 
 * @see  CacheResolverAdapter
 */
public class JCacheAspectSupport extends AbstractCacheInvoker implements InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private JCacheOperationSource cacheOperationSource;

	@Nullable
	private CacheResultInterceptor cacheResultInterceptor;

	@Nullable
	private CachePutInterceptor cachePutInterceptor;

	@Nullable
	private CacheRemoveEntryInterceptor cacheRemoveEntryInterceptor;

	@Nullable
	private CacheRemoveAllInterceptor cacheRemoveAllInterceptor;

	private boolean initialized = false;


	/**
	 * Set the CacheOperationSource for this cache aspect.
	 */
	/**
	 * 为此缓存方面设置CacheOperationSource。 
	 * 
	 */
	public void setCacheOperationSource(JCacheOperationSource cacheOperationSource) {
		Assert.notNull(cacheOperationSource, "JCacheOperationSource must not be null");
		this.cacheOperationSource = cacheOperationSource;
	}

	/**
	 * Return the CacheOperationSource for this cache aspect.
	 */
	/**
	 * 返回此缓存方面的CacheOperationSource。 
	 * 
	 */
	public JCacheOperationSource getCacheOperationSource() {
		Assert.state(this.cacheOperationSource != null, "The 'cacheOperationSource' property is required: " +
				"If there are no cacheable methods, then don't use a cache aspect.");
		return this.cacheOperationSource;
	}

	@Override
	public void afterPropertiesSet() {
		getCacheOperationSource();

		this.cacheResultInterceptor = new CacheResultInterceptor(getErrorHandler());
		this.cachePutInterceptor = new CachePutInterceptor(getErrorHandler());
		this.cacheRemoveEntryInterceptor = new CacheRemoveEntryInterceptor(getErrorHandler());
		this.cacheRemoveAllInterceptor = new CacheRemoveAllInterceptor(getErrorHandler());

		this.initialized = true;
	}


	@Nullable
	protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
		// Check whether aspect is enabled to cope with cases where the AJ is pulled in automatically
		if (this.initialized) {
			Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
			JCacheOperation<?> operation = getCacheOperationSource().getCacheOperation(method, targetClass);
			if (operation != null) {
				CacheOperationInvocationContext<?> context =
						createCacheOperationInvocationContext(target, args, operation);
				return execute(context, invoker);
			}
		}

		return invoker.invoke();
	}

	@SuppressWarnings("unchecked")
	private CacheOperationInvocationContext<?> createCacheOperationInvocationContext(
			Object target, Object[] args, JCacheOperation<?> operation) {

		return new DefaultCacheInvocationContext<>(
				(JCacheOperation<Annotation>) operation, target, args);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private Object execute(CacheOperationInvocationContext<?> context, CacheOperationInvoker invoker) {
		CacheOperationInvoker adapter = new CacheOperationInvokerAdapter(invoker);
		BasicOperation operation = context.getOperation();

		if (operation instanceof CacheResultOperation) {
			Assert.state(this.cacheResultInterceptor != null, "No CacheResultInterceptor");
			return this.cacheResultInterceptor.invoke(
					(CacheOperationInvocationContext<CacheResultOperation>) context, adapter);
		}
		else if (operation instanceof CachePutOperation) {
			Assert.state(this.cachePutInterceptor != null, "No CachePutInterceptor");
			return this.cachePutInterceptor.invoke(
					(CacheOperationInvocationContext<CachePutOperation>) context, adapter);
		}
		else if (operation instanceof CacheRemoveOperation) {
			Assert.state(this.cacheRemoveEntryInterceptor != null, "No CacheRemoveEntryInterceptor");
			return this.cacheRemoveEntryInterceptor.invoke(
					(CacheOperationInvocationContext<CacheRemoveOperation>) context, adapter);
		}
		else if (operation instanceof CacheRemoveAllOperation) {
			Assert.state(this.cacheRemoveAllInterceptor != null, "No CacheRemoveAllInterceptor");
			return this.cacheRemoveAllInterceptor.invoke(
					(CacheOperationInvocationContext<CacheRemoveAllOperation>) context, adapter);
		}
		else {
			throw new IllegalArgumentException("Cannot handle " + operation);
		}
	}

	/**
	 * Execute the underlying operation (typically in case of cache miss) and return
	 * the result of the invocation. If an exception occurs it will be wrapped in
	 * a {@code ThrowableWrapper}: the exception can be handled or modified but it
	 * <em>must</em> be wrapped in a {@code ThrowableWrapper} as well.
	 * @param invoker the invoker handling the operation being cached
	 * @return the result of the invocation
	 * @see CacheOperationInvoker#invoke()
	 */
	/**
	 * 执行基础操作（通常在高速缓存未命中的情况下）并返回调用结果。 
	 * 如果发生异常，则将其包装在{@code  ThrowableWrapper}中：可以处理或修改该异常，但也必须将其<em> </ em>包装在{@code  ThrowableWrapper}中。 
	 *  
	 * @param 调用程序处理正在缓存的操作的调用程序
	 * @return 调用的结果
	 * @see  CacheOperationInvoker＃invoke（）
	 */
	protected Object invokeOperation(CacheOperationInvoker invoker) {
		return invoker.invoke();
	}


	private class CacheOperationInvokerAdapter implements CacheOperationInvoker {

		private final CacheOperationInvoker delegate;

		public CacheOperationInvokerAdapter(CacheOperationInvoker delegate) {
			this.delegate = delegate;
		}

		@Override
		public Object invoke() throws ThrowableWrapper {
			return invokeOperation(this.delegate);
		}
	}

}
