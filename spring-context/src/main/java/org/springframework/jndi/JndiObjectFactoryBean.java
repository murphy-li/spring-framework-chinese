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

package org.springframework.jndi;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.naming.Context;
import javax.naming.NamingException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that looks up a
 * JNDI object. Exposes the object found in JNDI for bean references,
 * e.g. for data access object's "dataSource" property in case of a
 * {@link javax.sql.DataSource}.
 *
 * <p>The typical usage will be to register this as singleton factory
 * (e.g. for a certain JNDI-bound DataSource) in an application context,
 * and give bean references to application services that need it.
 *
 * <p>The default behavior is to look up the JNDI object on startup and cache it.
 * This can be customized through the "lookupOnStartup" and "cache" properties,
 * using a {@link JndiObjectTargetSource} underneath. Note that you need to specify
 * a "proxyInterface" in such a scenario, since the actual JNDI object type is not
 * known in advance.
 *
 * <p>Of course, bean classes in a Spring environment may lookup e.g. a DataSource
 * from JNDI themselves. This class simply enables central configuration of the
 * JNDI name, and easy switching to non-JNDI alternatives. The latter is
 * particularly convenient for test setups, reuse in standalone clients, etc.
 *
 * <p>Note that switching to e.g. DriverManagerDataSource is just a matter of
 * configuration: Simply replace the definition of this FactoryBean with a
 * {@link org.springframework.jdbc.datasource.DriverManagerDataSource} definition!
 *
 * @author Juergen Hoeller
 * @since 22.05.2003
 * @see #setProxyInterface
 * @see #setLookupOnStartup
 * @see #setCache
 * @see JndiObjectTargetSource
 */
/**
 * {@link  org.springframework.beans.factory.FactoryBean}查找JNDI对象。 
 * 公开在JNDI中找到的对象以供bean引用，例如如果是{@link  javax.sql.DataSource}，则用于数据访问对象的"dataSource"属性。 
 *  <p>通常的用法是在应用程序上下文中将其注册为单例工厂（例如，对于绑定了JNDI的特定数据源），并为需要它的应用程序服务提供Bean引用。 
 *  <p>默认行为是在启动时查找JNDI对象并将其缓存。 
 * 可以使用下面的{@link  JndiObjectTargetSource}通过"lookupOnStartup"和"cache"属性来自定义。 
 * 请注意，在这种情况下，您需要指定"proxyInterface"，因为实际的JNDI对象类型是事先未知的。 
 *  <p>当然，Spring环境中的bean类可能会查找例如来自JNDI本身的数据源。 
 * 此类仅启用JNDI名称的集中配置，并易于切换到非JNDI替代方法。 
 * 后者对于测试设置，在独立客户端中的重用等特别方便。 
 * <p>请注意，切换到例如DriverManagerDataSource只是配置问题：只需使用{@link  org.springframework.jdbc.datasource.DriverManagerDataSource}定义替换此FactoryBean的定义即可！ 
 *  @author  Juergen Hoeller @2003年5月22日起
 * @see  #setProxyInterface 
 * @see  #setLookupOnStartup 
 * @see  #setCache 
 * @see  JndiObjectTargetSource
 */
public class JndiObjectFactoryBean extends JndiObjectLocator
		implements FactoryBean<Object>, BeanFactoryAware, BeanClassLoaderAware {

	@Nullable
	private Class<?>[] proxyInterfaces;

	private boolean lookupOnStartup = true;

	private boolean cache = true;

	private boolean exposeAccessContext = false;

	@Nullable
	private Object defaultObject;

	@Nullable
	private ConfigurableBeanFactory beanFactory;

	@Nullable
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	@Nullable
	private Object jndiObject;


	/**
	 * Specify the proxy interface to use for the JNDI object.
	 * <p>Typically used in conjunction with "lookupOnStartup"=false and/or "cache"=false.
	 * Needs to be specified because the actual JNDI object type is not known
	 * in advance in case of a lazy lookup.
	 * @see #setProxyInterfaces
	 * @see #setLookupOnStartup
	 * @see #setCache
	 */
	/**
	 * 指定用于JNDI对象的代理接口。 
	 *  <p>通常与"lookupOnStartup"= false和/或"cache"= false结合使用。 
	 * 需要指定，因为在延迟查找的情况下，实际的JNDI对象类型事先未知。 
	 *  
	 * @see  #setProxyInterfaces 
	 * @see  #setLookupOnStartup 
	 * @see  #setCache
	 */
	public void setProxyInterface(Class<?> proxyInterface) {
		this.proxyInterfaces = new Class<?>[] {proxyInterface};
	}

	/**
	 * Specify multiple proxy interfaces to use for the JNDI object.
	 * <p>Typically used in conjunction with "lookupOnStartup"=false and/or "cache"=false.
	 * Note that proxy interfaces will be autodetected from a specified "expectedType",
	 * if necessary.
	 * @see #setExpectedType
	 * @see #setLookupOnStartup
	 * @see #setCache
	 */
	/**
	 * 指定用于JNDI对象的多个代理接口。 
	 *  <p>通常与"lookupOnStartup"= false和/或"cache"= false结合使用。 
	 * 请注意，如有必要，将从指定的"expectedType"中自动检测代理接口。 
	 *  
	 * @see  #setExpectedType 
	 * @see  #setLookupOnStartup 
	 * @see  #setCache
	 */
	public void setProxyInterfaces(Class<?>... proxyInterfaces) {
		this.proxyInterfaces = proxyInterfaces;
	}

	/**
	 * Set whether to look up the JNDI object on startup. Default is "true".
	 * <p>Can be turned off to allow for late availability of the JNDI object.
	 * In this case, the JNDI object will be fetched on first access.
	 * <p>For a lazy lookup, a proxy interface needs to be specified.
	 * @see #setProxyInterface
	 * @see #setCache
	 */
	/**
	 * 设置是否在启动时查找JNDI对象。 
	 * 默认值为"true"。 
	 *  <p>可以关闭以允许JNDI对象的较晚可用性。 
	 * 在这种情况下，将在首次访问时获取JNDI对象。 
	 *  <p>对于延迟查找，需要指定代理接口。 
	 *  
	 * @see  #setProxyInterface 
	 * @see  #setCache
	 */
	public void setLookupOnStartup(boolean lookupOnStartup) {
		this.lookupOnStartup = lookupOnStartup;
	}

	/**
	 * Set whether to cache the JNDI object once it has been located.
	 * Default is "true".
	 * <p>Can be turned off to allow for hot redeployment of JNDI objects.
	 * In this case, the JNDI object will be fetched for each invocation.
	 * <p>For hot redeployment, a proxy interface needs to be specified.
	 * @see #setProxyInterface
	 * @see #setLookupOnStartup
	 */
	/**
	 * 设置一旦找到JNDI对象，是否对其进行缓存。 
	 * 默认值为"true"。 
	 *  <p>可以关闭以允许JNDI对象的热重新部署。 
	 * 在这种情况下，将为每次调用获取JNDI对象。 
	 *  <p>对于热重新部署，需要指定代理接口。 
	 *  
	 * @see  #setProxyInterface 
	 * @see  #setLookupOnStartup
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	/**
	 * Set whether to expose the JNDI environment context for all access to the target
	 * object, i.e. for all method invocations on the exposed object reference.
	 * <p>Default is "false", i.e. to only expose the JNDI context for object lookup.
	 * Switch this flag to "true" in order to expose the JNDI environment (including
	 * the authorization context) for each method invocation, as needed by WebLogic
	 * for JNDI-obtained factories (e.g. JDBC DataSource, JMS ConnectionFactory)
	 * with authorization requirements.
	 */
	/**
	 * 设置是否为所有对目标对象的访问（即对公开对象引用上的所有方法调用）公开JNDI环境上下文。 
	 *  <p>默认为"false"，即仅公开JNDI上下文进行对象查找。 
	 * 将此标志切换为"true"，以便根据WebLogic对具有授权要求的JNDI获得的工厂（例如JDBC DataSource，JMS ConnectionFactory）公开的每个方法调用的JNDI环境（包括授权上下文）。 
	 * 
	 */
	public void setExposeAccessContext(boolean exposeAccessContext) {
		this.exposeAccessContext = exposeAccessContext;
	}

	/**
	 * Specify a default object to fall back to if the JNDI lookup fails.
	 * Default is none.
	 * <p>This can be an arbitrary bean reference or literal value.
	 * It is typically used for literal values in scenarios where the JNDI environment
	 * might define specific config settings but those are not required to be present.
	 * <p>Note: This is only supported for lookup on startup.
	 * If specified together with {@link #setExpectedType}, the specified value
	 * needs to be either of that type or convertible to it.
	 * @see #setLookupOnStartup
	 * @see ConfigurableBeanFactory#getTypeConverter()
	 * @see SimpleTypeConverter
	 */
	/**
	 * 指定默认对象，以在JNDI查找失败时回退。 
	 * 默认为无。 
	 *  <p>这可以是任意bean引用或文字值。 
	 * 它通常用于JNDI环境可能定义特定配置设置但不需要提供这些配置设置的场景中的文字值。 
	 *  <p>注意：仅在启动时才支持此功能。 
	 * 如果与{@link  #setExpectedType}一起指定，则指定的值必须是该类型或可转换为该类型。 
	 *  
	 * @see  #setLookupOnStartup 
	 * @see  ConfigurableBeanFactory＃getTypeConverter（）
	 * @see  SimpleTypeConverter
	 */
	public void setDefaultObject(Object defaultObject) {
		this.defaultObject = defaultObject;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory instanceof ConfigurableBeanFactory) {
			// Just optional - for getting a specifically configured TypeConverter if needed.
			// We'll simply fall back to a SimpleTypeConverter if no specific one available.
			this.beanFactory = (ConfigurableBeanFactory) beanFactory;
		}
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}


	/**
	 * Look up the JNDI object and store it.
	 */
	/**
	 * 查找JNDI对象并将其存储。 
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
		super.afterPropertiesSet();

		if (this.proxyInterfaces != null || !this.lookupOnStartup || !this.cache || this.exposeAccessContext) {
			// We need to create a proxy for this...
			if (this.defaultObject != null) {
				throw new IllegalArgumentException(
						"'defaultObject' is not supported in combination with 'proxyInterface'");
			}
			// We need a proxy and a JndiObjectTargetSource.
			this.jndiObject = JndiObjectProxyFactory.createJndiObjectProxy(this);
		}
		else {
			if (this.defaultObject != null && getExpectedType() != null &&
					!getExpectedType().isInstance(this.defaultObject)) {
				TypeConverter converter = (this.beanFactory != null ?
						this.beanFactory.getTypeConverter() : new SimpleTypeConverter());
				try {
					this.defaultObject = converter.convertIfNecessary(this.defaultObject, getExpectedType());
				}
				catch (TypeMismatchException ex) {
					throw new IllegalArgumentException("Default object [" + this.defaultObject + "] of type [" +
							this.defaultObject.getClass().getName() + "] is not of expected type [" +
							getExpectedType().getName() + "] and cannot be converted either", ex);
				}
			}
			// Locate specified JNDI object.
			this.jndiObject = lookupWithFallback();
		}
	}

	/**
	 * Lookup variant that returns the specified "defaultObject"
	 * (if any) in case of lookup failure.
	 * @return the located object, or the "defaultObject" as fallback
	 * @throws NamingException in case of lookup failure without fallback
	 * @see #setDefaultObject
	 */
	/**
	 * 查找变体，在查找失败的情况下返回指定的"defaultObject"（如果有）。 
	 *  
	 * @return 找到的对象，或将"defaultObject"作为后备
	 * @throws  NamingException，如果查找失败而没有后备
	 * @see  #setDefaultObject
	 */
	protected Object lookupWithFallback() throws NamingException {
		ClassLoader originalClassLoader = ClassUtils.overrideThreadContextClassLoader(this.beanClassLoader);
		try {
			return lookup();
		}
		catch (TypeMismatchNamingException ex) {
			// Always let TypeMismatchNamingException through -
			// we don't want to fall back to the defaultObject in this case.
			throw ex;
		}
		catch (NamingException ex) {
			if (this.defaultObject != null) {
				if (logger.isTraceEnabled()) {
					logger.trace("JNDI lookup failed - returning specified default object instead", ex);
				}
				else if (logger.isDebugEnabled()) {
					logger.debug("JNDI lookup failed - returning specified default object instead: " + ex);
				}
				return this.defaultObject;
			}
			throw ex;
		}
		finally {
			if (originalClassLoader != null) {
				Thread.currentThread().setContextClassLoader(originalClassLoader);
			}
		}
	}


	/**
	 * Return the singleton JNDI object.
	 */
	/**
	 * 返回单例JNDI对象。 
	 * 
	 */
	@Override
	@Nullable
	public Object getObject() {
		return this.jndiObject;
	}

	@Override
	public Class<?> getObjectType() {
		if (this.proxyInterfaces != null) {
			if (this.proxyInterfaces.length == 1) {
				return this.proxyInterfaces[0];
			}
			else if (this.proxyInterfaces.length > 1) {
				return createCompositeInterface(this.proxyInterfaces);
			}
		}
		if (this.jndiObject != null) {
			return this.jndiObject.getClass();
		}
		else {
			return getExpectedType();
		}
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	/**
	 * Create a composite interface Class for the given interfaces,
	 * implementing the given interfaces in one single Class.
	 * <p>The default implementation builds a JDK proxy class for the
	 * given interfaces.
	 * @param interfaces the interfaces to merge
	 * @return the merged interface as Class
	 * @see java.lang.reflect.Proxy#getProxyClass
	 */
	/**
	 * 为给定的接口创建一个复合接口Class，在单个Class中实现给定的接口。 
	 *  <p>默认实现为给定的接口构建一个JDK代理类。 
	 *  
	 * @param 接口接口以合并
	 * @return 合并的接口作为类
	 * @see  java.lang.reflect.Proxy＃getProxyClass
	 */
	protected Class<?> createCompositeInterface(Class<?>[] interfaces) {
		return ClassUtils.createCompositeInterface(interfaces, this.beanClassLoader);
	}


	/**
	 * Inner class to just introduce an AOP dependency when actually creating a proxy.
	 */
	/**
	 * 内部类，在实际创建代理时仅引入AOP依赖关系。 
	 * 
	 */
	private static class JndiObjectProxyFactory {

		private static Object createJndiObjectProxy(JndiObjectFactoryBean jof) throws NamingException {
			// Create a JndiObjectTargetSource that mirrors the JndiObjectFactoryBean's configuration.
			JndiObjectTargetSource targetSource = new JndiObjectTargetSource();
			targetSource.setJndiTemplate(jof.getJndiTemplate());
			String jndiName = jof.getJndiName();
			Assert.state(jndiName != null, "No JNDI name specified");
			targetSource.setJndiName(jndiName);
			targetSource.setExpectedType(jof.getExpectedType());
			targetSource.setResourceRef(jof.isResourceRef());
			targetSource.setLookupOnStartup(jof.lookupOnStartup);
			targetSource.setCache(jof.cache);
			targetSource.afterPropertiesSet();

			// Create a proxy with JndiObjectFactoryBean's proxy interface and the JndiObjectTargetSource.
			ProxyFactory proxyFactory = new ProxyFactory();
			if (jof.proxyInterfaces != null) {
				proxyFactory.setInterfaces(jof.proxyInterfaces);
			}
			else {
				Class<?> targetClass = targetSource.getTargetClass();
				if (targetClass == null) {
					throw new IllegalStateException(
							"Cannot deactivate 'lookupOnStartup' without specifying a 'proxyInterface' or 'expectedType'");
				}
				Class<?>[] ifcs = ClassUtils.getAllInterfacesForClass(targetClass, jof.beanClassLoader);
				for (Class<?> ifc : ifcs) {
					if (Modifier.isPublic(ifc.getModifiers())) {
						proxyFactory.addInterface(ifc);
					}
				}
			}
			if (jof.exposeAccessContext) {
				proxyFactory.addAdvice(new JndiContextExposingInterceptor(jof.getJndiTemplate()));
			}
			proxyFactory.setTargetSource(targetSource);
			return proxyFactory.getProxy(jof.beanClassLoader);
		}
	}


	/**
	 * Interceptor that exposes the JNDI context for all method invocations,
	 * according to JndiObjectFactoryBean's "exposeAccessContext" flag.
	 */
	/**
	 * 根据JndiObjectFactoryBean的"exposeAccessContext"标志，为所有方法调用公开JNDI上下文的拦截器。 
	 * 
	 */
	private static class JndiContextExposingInterceptor implements MethodInterceptor {

		private final JndiTemplate jndiTemplate;

		public JndiContextExposingInterceptor(JndiTemplate jndiTemplate) {
			this.jndiTemplate = jndiTemplate;
		}

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			Context ctx = (isEligible(invocation.getMethod()) ? this.jndiTemplate.getContext() : null);
			try {
				return invocation.proceed();
			}
			finally {
				this.jndiTemplate.releaseContext(ctx);
			}
		}

		protected boolean isEligible(Method method) {
			return (Object.class != method.getDeclaringClass());
		}
	}

}
