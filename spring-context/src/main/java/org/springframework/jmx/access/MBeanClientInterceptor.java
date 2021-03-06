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

package org.springframework.jmx.access;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.management.Attribute;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.JMException;
import javax.management.JMX;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.ReflectionException;
import javax.management.RuntimeErrorException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXServiceURL;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.CollectionFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * {@link org.aopalliance.intercept.MethodInterceptor} that routes calls to an
 * MBean running on the supplied {@code MBeanServerConnection}.
 * Works for both local and remote {@code MBeanServerConnection}s.
 *
 * <p>By default, the {@code MBeanClientInterceptor} will connect to the
 * {@code MBeanServer} and cache MBean metadata at startup. This can
 * be undesirable when running against a remote {@code MBeanServer}
 * that may not be running when the application starts. Through setting the
 * {@link #setConnectOnStartup(boolean) connectOnStartup} property to "false",
 * you can defer this process until the first invocation against the proxy.
 *
 * <p>This functionality is usually used through {@link MBeanProxyFactoryBean}.
 * See the javadoc of that class for more information.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.2
 * @see MBeanProxyFactoryBean
 * @see #setConnectOnStartup
 */
/**
 * {@link  org.aopalliance.intercept.MethodInterceptor}，它将呼叫路由到在提供的{@code  MBeanServerConnection}上运行的MBean。 
 * 适用于本地和远程{@code  MBeanServerConnection}。 
 *  <p>默认情况下，{<@code> MBeanClientInterceptor}将连接到{@code  MBeanServer}并在启动时缓存MBean元数据。 
 * 当在应用程序启动时可能未运行的远程{@code  MBeanServer}上运行时，这可能是不希望的。 
 * 通过将{@link  #setConnectOnStartup（boolean）connectOnStartup}属性设置为"false"，您可以将此过程推迟到首次对代理调用之前。 
 *  <p>通常通过{@link  MBeanProxyFactoryBean}使用此功能。 
 * 有关更多信息，请参见该类的javadoc。 
 *  @author  Rob Harrop @author  Juergen Hoeller @始于1.2 
 * @see  MBeanProxyFactoryBean 
 * @see  #setConnectOnStartup
 */
public class MBeanClientInterceptor
		implements MethodInterceptor, BeanClassLoaderAware, InitializingBean, DisposableBean {

	/** Logger available to subclasses. */
	/**
	 * 记录器可用于子类。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private MBeanServerConnection server;

	@Nullable
	private JMXServiceURL serviceUrl;

	@Nullable
	private Map<String, ?> environment;

	@Nullable
	private String agentId;

	private boolean connectOnStartup = true;

	private boolean refreshOnConnectFailure = false;

	@Nullable
	private ObjectName objectName;

	private boolean useStrictCasing = true;

	@Nullable
	private Class<?> managementInterface;

	@Nullable
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	private final ConnectorDelegate connector = new ConnectorDelegate();

	@Nullable
	private MBeanServerConnection serverToUse;

	@Nullable
	private MBeanServerInvocationHandler invocationHandler;

	private Map<String, MBeanAttributeInfo> allowedAttributes = Collections.emptyMap();

	private Map<MethodCacheKey, MBeanOperationInfo> allowedOperations = Collections.emptyMap();

	private final Map<Method, String[]> signatureCache = new HashMap<>();

	private final Object preparationMonitor = new Object();


	/**
	 * Set the {@code MBeanServerConnection} used to connect to the
	 * MBean which all invocations are routed to.
	 */
	/**
	 * 设置用于连接到所有调用都路由到的MBean的{@code  MBeanServerConnection}。 
	 * 
	 */
	public void setServer(MBeanServerConnection server) {
		this.server = server;
	}

	/**
	 * Set the service URL of the remote {@code MBeanServer}.
	 */
	/**
	 * 设置远程{@code  MBeanServer}的服务URL。 
	 * 
	 */
	public void setServiceUrl(String url) throws MalformedURLException {
		this.serviceUrl = new JMXServiceURL(url);
	}

	/**
	 * Specify the environment for the JMX connector.
	 * @see javax.management.remote.JMXConnectorFactory#connect(javax.management.remote.JMXServiceURL, java.util.Map)
	 */
	/**
	 * 指定JMX连接器的环境。 
	 *  
	 * @see  javax.management.remote.JMXConnectorFactory＃connect（javax.management.remote.JMXServiceURL，java.util.Map）
	 */
	public void setEnvironment(@Nullable Map<String, ?> environment) {
		this.environment = environment;
	}

	/**
	 * Allow Map access to the environment to be set for the connector,
	 * with the option to add or override specific entries.
	 * <p>Useful for specifying entries directly, for example via
	 * "environment[myKey]". This is particularly useful for
	 * adding or overriding entries in child bean definitions.
	 */
	/**
	 * 允许为连接器设置对环境的Map访问，并可以选择添加或覆盖特定条目。 
	 *  <p>可用于直接指定条目，例如通过"environment [myKey]"。 
	 * 这对于在子bean定义中添加或覆盖条目特别有用。 
	 * 
	 */
	@Nullable
	public Map<String, ?> getEnvironment() {
		return this.environment;
	}

	/**
	 * Set the agent id of the {@code MBeanServer} to locate.
	 * <p>Default is none. If specified, this will result in an
	 * attempt being made to locate the attendant MBeanServer, unless
	 * the {@link #setServiceUrl "serviceUrl"} property has been set.
	 * @see javax.management.MBeanServerFactory#findMBeanServer(String)
	 * <p>Specifying the empty String indicates the platform MBeanServer.
	 */
	/**
	 * 设置{@code  MBeanServer}的代理ID以进行定位。 
	 *  <p>默认为无。 
	 * 如果指定，除非设置了{@link  #setServiceUrl"serviceUrl"}属性，否则将导致尝试查找伴随的MBeanServer。 
	 *  
	 * @see  javax.management.MBeanServerFactory＃findMBeanServer（String）<p>指定空String表示平台MBeanServer。 
	 * 
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * Set whether or not the proxy should connect to the {@code MBeanServer}
	 * at creation time ("true") or the first time it is invoked ("false").
	 * Default is "true".
	 */
	/**
	 * 设置是否在创建时（"true"）或第一次调用它（"false"）时将代理连接到{@code  MBeanServer}。 
	 * 默认值为"true"。 
	 * 
	 */
	public void setConnectOnStartup(boolean connectOnStartup) {
		this.connectOnStartup = connectOnStartup;
	}

	/**
	 * Set whether to refresh the MBeanServer connection on connect failure.
	 * Default is "false".
	 * <p>Can be turned on to allow for hot restart of the JMX server,
	 * automatically reconnecting and retrying in case of an IOException.
	 */
	/**
	 * 设置是否在连接失败时刷新MBeanServer连接。 
	 * 默认值为"false"。 
	 *  <p>可以打开以允许JMX服务器热重启，并在发生IOException的情况下自动重新连接并重试。 
	 * 
	 */
	public void setRefreshOnConnectFailure(boolean refreshOnConnectFailure) {
		this.refreshOnConnectFailure = refreshOnConnectFailure;
	}

	/**
	 * Set the {@code ObjectName} of the MBean which calls are routed to,
	 * as {@code ObjectName} instance or as {@code String}.
	 */
	/**
	 * 将调用路由到的MBean的{@code  ObjectName}设置为{@code  ObjectName}实例或{@code  String}。 
	 * 
	 */
	public void setObjectName(Object objectName) throws MalformedObjectNameException {
		this.objectName = ObjectNameManager.getInstance(objectName);
	}

	/**
	 * Set whether to use strict casing for attributes. Enabled by default.
	 * <p>When using strict casing, a JavaBean property with a getter such as
	 * {@code getFoo()} translates to an attribute called {@code Foo}.
	 * With strict casing disabled, {@code getFoo()} would translate to just
	 * {@code foo}.
	 */
	/**
	 * 设置是否对属性使用严格的大小写。 
	 * 默认启用。 
	 *  <p>使用严格的大小写时，带有getter的JavaBean属性（例如{@code  getFoo（）}）将转换为名为{@code  Foo}的属性。 
	 * 在禁用严格大小写的情况下，{<@code> getFoo（）}将仅转换为{@code  foo}。 
	 * 
	 */
	public void setUseStrictCasing(boolean useStrictCasing) {
		this.useStrictCasing = useStrictCasing;
	}

	/**
	 * Set the management interface of the target MBean, exposing bean property
	 * setters and getters for MBean attributes and conventional Java methods
	 * for MBean operations.
	 */
	/**
	 * 设置目标MBean的管理接口，公开用于MBean属性的bean属性设置器和获取器，以及用于MBean操作的常规Java方法。 
	 * 
	 */
	public void setManagementInterface(@Nullable Class<?> managementInterface) {
		this.managementInterface = managementInterface;
	}

	/**
	 * Return the management interface of the target MBean,
	 * or {@code null} if none specified.
	 */
	/**
	 * 返回目标MBean的管理接口，如果未指定，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	protected final Class<?> getManagementInterface() {
		return this.managementInterface;
	}

	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}


	/**
	 * Prepares the {@code MBeanServerConnection} if the "connectOnStartup"
	 * is turned on (which it is by default).
	 */
	/**
	 * 如果"connectOnStartup"已打开（默认情况下），则准备{@code  MBeanServerConnection}。 
	 * 
	 */
	@Override
	public void afterPropertiesSet() {
		if (this.server != null && this.refreshOnConnectFailure) {
			throw new IllegalArgumentException("'refreshOnConnectFailure' does not work when setting " +
					"a 'server' reference. Prefer 'serviceUrl' etc instead.");
		}
		if (this.connectOnStartup) {
			prepare();
		}
	}

	/**
	 * Ensures that an {@code MBeanServerConnection} is configured and attempts
	 * to detect a local connection if one is not supplied.
	 */
	/**
	 * 确保配置了{@code  MBeanServerConnection}并尝试检测本地连接（如果未提供）。 
	 * 
	 */
	public void prepare() {
		synchronized (this.preparationMonitor) {
			if (this.server != null) {
				this.serverToUse = this.server;
			}
			else {
				this.serverToUse = null;
				this.serverToUse = this.connector.connect(this.serviceUrl, this.environment, this.agentId);
			}
			this.invocationHandler = null;
			if (this.useStrictCasing) {
				Assert.state(this.objectName != null, "No ObjectName set");
				// Use the JDK's own MBeanServerInvocationHandler, in particular for native MXBean support.
				this.invocationHandler = new MBeanServerInvocationHandler(this.serverToUse, this.objectName,
						(this.managementInterface != null && JMX.isMXBeanInterface(this.managementInterface)));
			}
			else {
				// Non-strict casing can only be achieved through custom invocation handling.
				// Only partial MXBean support available!
				retrieveMBeanInfo(this.serverToUse);
			}
		}
	}
	/**
	 * Loads the management interface info for the configured MBean into the caches.
	 * This information is used by the proxy when determining whether an invocation matches
	 * a valid operation or attribute on the management interface of the managed resource.
	 */
	/**
	 * 将已配置的MBean的管理接口信息加载到缓存中。 
	 * 在确定调用是否与托管资源的管理界面上的有效操作或属性相匹配时，代理将使用此信息。 
	 * 
	 */
	private void retrieveMBeanInfo(MBeanServerConnection server) throws MBeanInfoRetrievalException {
		try {
			MBeanInfo info = server.getMBeanInfo(this.objectName);

			MBeanAttributeInfo[] attributeInfo = info.getAttributes();
			this.allowedAttributes = new HashMap<>(attributeInfo.length);
			for (MBeanAttributeInfo infoEle : attributeInfo) {
				this.allowedAttributes.put(infoEle.getName(), infoEle);
			}

			MBeanOperationInfo[] operationInfo = info.getOperations();
			this.allowedOperations = new HashMap<>(operationInfo.length);
			for (MBeanOperationInfo infoEle : operationInfo) {
				Class<?>[] paramTypes = JmxUtils.parameterInfoToTypes(infoEle.getSignature(), this.beanClassLoader);
				this.allowedOperations.put(new MethodCacheKey(infoEle.getName(), paramTypes), infoEle);
			}
		}
		catch (ClassNotFoundException ex) {
			throw new MBeanInfoRetrievalException("Unable to locate class specified in method signature", ex);
		}
		catch (IntrospectionException ex) {
			throw new MBeanInfoRetrievalException("Unable to obtain MBean info for bean [" + this.objectName + "]", ex);
		}
		catch (InstanceNotFoundException ex) {
			// if we are this far this shouldn't happen, but...
			throw new MBeanInfoRetrievalException("Unable to obtain MBean info for bean [" + this.objectName +
					"]: it is likely that this bean was unregistered during the proxy creation process",
					ex);
		}
		catch (ReflectionException ex) {
			throw new MBeanInfoRetrievalException("Unable to read MBean info for bean [ " + this.objectName + "]", ex);
		}
		catch (IOException ex) {
			throw new MBeanInfoRetrievalException("An IOException occurred when communicating with the " +
					"MBeanServer. It is likely that you are communicating with a remote MBeanServer. " +
					"Check the inner exception for exact details.", ex);
		}
	}

	/**
	 * Return whether this client interceptor has already been prepared,
	 * i.e. has already looked up the server and cached all metadata.
	 */
	/**
	 * 返回此客户端拦截器是否已经准备好，即是否已经查找服务器并缓存了所有元数据。 
	 * 
	 */
	protected boolean isPrepared() {
		synchronized (this.preparationMonitor) {
			return (this.serverToUse != null);
		}
	}


	/**
	 * Route the invocation to the configured managed resource..
	 * @param invocation the {@code MethodInvocation} to re-route
	 * @return the value returned as a result of the re-routed invocation
	 * @throws Throwable an invocation error propagated to the user
	 * @see #doInvoke
	 * @see #handleConnectFailure
	 */
	/**
	 * 将调用路由到配置的托管资源。 
	 * .
	 * @param 调用{@code  MethodInvocation}以重新路由
	 * @return 由于重新路由的调用而返回的值
	 * @throws 可抛出的调用错误传播给用户
	 * @see  #doInvoke 
	 * @see  #handleConnectFailure
	 */
	@Override
	@Nullable
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// Lazily connect to MBeanServer if necessary.
		synchronized (this.preparationMonitor) {
			if (!isPrepared()) {
				prepare();
			}
		}
		try {
			return doInvoke(invocation);
		}
		catch (MBeanConnectFailureException | IOException ex) {
			return handleConnectFailure(invocation, ex);
		}
	}

	/**
	 * Refresh the connection and retry the MBean invocation if possible.
	 * <p>If not configured to refresh on connect failure, this method
	 * simply rethrows the original exception.
	 * @param invocation the invocation that failed
	 * @param ex the exception raised on remote invocation
	 * @return the result value of the new invocation, if succeeded
	 * @throws Throwable an exception raised by the new invocation,
	 * if it failed as well
	 * @see #setRefreshOnConnectFailure
	 * @see #doInvoke
	 */
	/**
	 * 刷新连接，并在可能的情况下重试MBean调用。 
	 *  <p>如果未配置为在连接失败时刷新，则此方法仅引发原始异常。 
	 *  
	 * @param 调用失败的调用
	 * @param 除远程调用时引发的异常
	 * @return 新调用的结果值（如果成功）
	 * @throws 可抛出新调用引发的异常（如果失败）以及
	 * @see  #setRefreshOnConnectFailure 
	 * @see  #doInvoke
	 */
	@Nullable
	protected Object handleConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
		if (this.refreshOnConnectFailure) {
			String msg = "Could not connect to JMX server - retrying";
			if (logger.isDebugEnabled()) {
				logger.warn(msg, ex);
			}
			else if (logger.isWarnEnabled()) {
				logger.warn(msg);
			}
			prepare();
			return doInvoke(invocation);
		}
		else {
			throw ex;
		}
	}

	/**
	 * Route the invocation to the configured managed resource. Correctly routes JavaBean property
	 * access to {@code MBeanServerConnection.get/setAttribute} and method invocation to
	 * {@code MBeanServerConnection.invoke}.
	 * @param invocation the {@code MethodInvocation} to re-route
	 * @return the value returned as a result of the re-routed invocation
	 * @throws Throwable an invocation error propagated to the user
	 */
	/**
	 * 将调用路由到已配置的托管资源。 
	 * 正确地将JavaBean属性访问路由到{@code  MBeanServerConnection.get / setAttribute}，并将方法调用路由到{@code  MBeanServerConnection.invoke}。 
	 *  
	 * @param 调用{@code  MethodInvocation}来重新路由
	 * @return 由于重新路由的调用而返回的值
	 * @throws 可抛出将传播到用户的调用错误
	 */
	@Nullable
	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		try {
			Object result;
			if (this.invocationHandler != null) {
				result = this.invocationHandler.invoke(invocation.getThis(), method, invocation.getArguments());
			}
			else {
				PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
				if (pd != null) {
					result = invokeAttribute(pd, invocation);
				}
				else {
					result = invokeOperation(method, invocation.getArguments());
				}
			}
			return convertResultValueIfNecessary(result, new MethodParameter(method, -1));
		}
		catch (MBeanException ex) {
			throw ex.getTargetException();
		}
		catch (RuntimeMBeanException ex) {
			throw ex.getTargetException();
		}
		catch (RuntimeErrorException ex) {
			throw ex.getTargetError();
		}
		catch (RuntimeOperationsException ex) {
			// This one is only thrown by the JMX 1.2 RI, not by the JDK 1.5 JMX code.
			RuntimeException rex = ex.getTargetException();
			if (rex instanceof RuntimeMBeanException) {
				throw ((RuntimeMBeanException) rex).getTargetException();
			}
			else if (rex instanceof RuntimeErrorException) {
				throw ((RuntimeErrorException) rex).getTargetError();
			}
			else {
				throw rex;
			}
		}
		catch (OperationsException ex) {
			if (ReflectionUtils.declaresException(method, ex.getClass())) {
				throw ex;
			}
			else {
				throw new InvalidInvocationException(ex.getMessage());
			}
		}
		catch (JMException ex) {
			if (ReflectionUtils.declaresException(method, ex.getClass())) {
				throw ex;
			}
			else {
				throw new InvocationFailureException("JMX access failed", ex);
			}
		}
		catch (IOException ex) {
			if (ReflectionUtils.declaresException(method, ex.getClass())) {
				throw ex;
			}
			else {
				throw new MBeanConnectFailureException("I/O failure during JMX access", ex);
			}
		}
	}

	@Nullable
	private Object invokeAttribute(PropertyDescriptor pd, MethodInvocation invocation)
			throws JMException, IOException {

		Assert.state(this.serverToUse != null, "No MBeanServerConnection available");

		String attributeName = JmxUtils.getAttributeName(pd, this.useStrictCasing);
		MBeanAttributeInfo inf = this.allowedAttributes.get(attributeName);
		// If no attribute is returned, we know that it is not defined in the
		// management interface.
		if (inf == null) {
			throw new InvalidInvocationException(
					"Attribute '" + pd.getName() + "' is not exposed on the management interface");
		}

		if (invocation.getMethod().equals(pd.getReadMethod())) {
			if (inf.isReadable()) {
				return this.serverToUse.getAttribute(this.objectName, attributeName);
			}
			else {
				throw new InvalidInvocationException("Attribute '" + attributeName + "' is not readable");
			}
		}
		else if (invocation.getMethod().equals(pd.getWriteMethod())) {
			if (inf.isWritable()) {
				this.serverToUse.setAttribute(this.objectName, new Attribute(attributeName, invocation.getArguments()[0]));
				return null;
			}
			else {
				throw new InvalidInvocationException("Attribute '" + attributeName + "' is not writable");
			}
		}
		else {
			throw new IllegalStateException(
					"Method [" + invocation.getMethod() + "] is neither a bean property getter nor a setter");
		}
	}

	/**
	 * Routes a method invocation (not a property get/set) to the corresponding
	 * operation on the managed resource.
	 * @param method the method corresponding to operation on the managed resource.
	 * @param args the invocation arguments
	 * @return the value returned by the method invocation.
	 */
	/**
	 * 将方法调用（不是属性的获取/设置）路由到托管资源上的相应操作。 
	 *  
	 * @param 方法对应于对托管资源进行操作的方法。 
	 *  
	 * @param 将调用参数
	 * @return 赋给方法调用返回的值。 
	 * 
	 */
	private Object invokeOperation(Method method, Object[] args) throws JMException, IOException {
		Assert.state(this.serverToUse != null, "No MBeanServerConnection available");

		MethodCacheKey key = new MethodCacheKey(method.getName(), method.getParameterTypes());
		MBeanOperationInfo info = this.allowedOperations.get(key);
		if (info == null) {
			throw new InvalidInvocationException("Operation '" + method.getName() +
					"' is not exposed on the management interface");
		}

		String[] signature;
		synchronized (this.signatureCache) {
			signature = this.signatureCache.get(method);
			if (signature == null) {
				signature = JmxUtils.getMethodSignature(method);
				this.signatureCache.put(method, signature);
			}
		}

		return this.serverToUse.invoke(this.objectName, method.getName(), args, signature);
	}

	/**
	 * Convert the given result object (from attribute access or operation invocation)
	 * to the specified target class for returning from the proxy method.
	 * @param result the result object as returned by the {@code MBeanServer}
	 * @param parameter the method parameter of the proxy method that's been invoked
	 * @return the converted result object, or the passed-in object if no conversion
	 * is necessary
	 */
	/**
	 * 将给定的结果对象（从属性访问或操作调用）转换为指定的目标类，以从代理方法返回。 
	 *  
	 * @param 结果由{@code  MBeanServer}参数返回的结果对象
	 * @param 被调用的代理方法的方法参数
	 * @return 转换后的结果对象，如果传入则返回无需转换
	 */
	@Nullable
	protected Object convertResultValueIfNecessary(@Nullable Object result, MethodParameter parameter) {
		Class<?> targetClass = parameter.getParameterType();
		try {
			if (result == null) {
				return null;
			}
			if (ClassUtils.isAssignableValue(targetClass, result)) {
				return result;
			}
			if (result instanceof CompositeData) {
				Method fromMethod = targetClass.getMethod("from", CompositeData.class);
				return ReflectionUtils.invokeMethod(fromMethod, null, result);
			}
			else if (result instanceof CompositeData[]) {
				CompositeData[] array = (CompositeData[]) result;
				if (targetClass.isArray()) {
					return convertDataArrayToTargetArray(array, targetClass);
				}
				else if (Collection.class.isAssignableFrom(targetClass)) {
					Class<?> elementType =
							ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
					if (elementType != null) {
						return convertDataArrayToTargetCollection(array, targetClass, elementType);
					}
				}
			}
			else if (result instanceof TabularData) {
				Method fromMethod = targetClass.getMethod("from", TabularData.class);
				return ReflectionUtils.invokeMethod(fromMethod, null, result);
			}
			else if (result instanceof TabularData[]) {
				TabularData[] array = (TabularData[]) result;
				if (targetClass.isArray()) {
					return convertDataArrayToTargetArray(array, targetClass);
				}
				else if (Collection.class.isAssignableFrom(targetClass)) {
					Class<?> elementType =
							ResolvableType.forMethodParameter(parameter).asCollection().resolveGeneric();
					if (elementType != null) {
						return convertDataArrayToTargetCollection(array, targetClass, elementType);
					}
				}
			}
			throw new InvocationFailureException(
					"Incompatible result value [" + result + "] for target type [" + targetClass.getName() + "]");
		}
		catch (NoSuchMethodException ex) {
			throw new InvocationFailureException(
					"Could not obtain 'from(CompositeData)' / 'from(TabularData)' method on target type [" +
							targetClass.getName() + "] for conversion of MXBean data structure [" + result + "]");
		}
	}

	private Object convertDataArrayToTargetArray(Object[] array, Class<?> targetClass) throws NoSuchMethodException {
		Class<?> targetType = targetClass.getComponentType();
		Method fromMethod = targetType.getMethod("from", array.getClass().getComponentType());
		Object resultArray = Array.newInstance(targetType, array.length);
		for (int i = 0; i < array.length; i++) {
			Array.set(resultArray, i, ReflectionUtils.invokeMethod(fromMethod, null, array[i]));
		}
		return resultArray;
	}

	private Collection<?> convertDataArrayToTargetCollection(Object[] array, Class<?> collectionType, Class<?> elementType)
			throws NoSuchMethodException {

		Method fromMethod = elementType.getMethod("from", array.getClass().getComponentType());
		Collection<Object> resultColl = CollectionFactory.createCollection(collectionType, Array.getLength(array));
		for (int i = 0; i < array.length; i++) {
			resultColl.add(ReflectionUtils.invokeMethod(fromMethod, null, array[i]));
		}
		return resultColl;
	}


	@Override
	public void destroy() {
		this.connector.close();
	}


	/**
	 * Simple wrapper class around a method name and its signature.
	 * Used as the key when caching methods.
	 */
	/**
	 * 方法名称及其签名周围的简单包装器类。 
	 * 在缓存方法时用作键。 
	 * 
	 */
	private static final class MethodCacheKey implements Comparable<MethodCacheKey> {

		private final String name;

		private final Class<?>[] parameterTypes;

		/**
		 * Create a new instance of {@code MethodCacheKey} with the supplied
		 * method name and parameter list.
		 * @param name the name of the method
		 * @param parameterTypes the arguments in the method signature
		 */
		/**
		 * 使用提供的方法名称和参数列表创建{@code  MethodCacheKey}的新实例。 
		 *  
		 * @param 名称方法的名称
		 * @param  parameterType方法签名中的参数
		 */
		public MethodCacheKey(String name, @Nullable Class<?>[] parameterTypes) {
			this.name = name;
			this.parameterTypes = (parameterTypes != null ? parameterTypes : new Class<?>[0]);
		}

		@Override
		public boolean equals(@Nullable Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof MethodCacheKey)) {
				return false;
			}
			MethodCacheKey otherKey = (MethodCacheKey) other;
			return (this.name.equals(otherKey.name) && Arrays.equals(this.parameterTypes, otherKey.parameterTypes));
		}

		@Override
		public int hashCode() {
			return this.name.hashCode();
		}

		@Override
		public String toString() {
			return this.name + "(" + StringUtils.arrayToCommaDelimitedString(this.parameterTypes) + ")";
		}

		@Override
		public int compareTo(MethodCacheKey other) {
			int result = this.name.compareTo(other.name);
			if (result != 0) {
				return result;
			}
			if (this.parameterTypes.length < other.parameterTypes.length) {
				return -1;
			}
			if (this.parameterTypes.length > other.parameterTypes.length) {
				return 1;
			}
			for (int i = 0; i < this.parameterTypes.length; i++) {
				result = this.parameterTypes[i].getName().compareTo(other.parameterTypes[i].getName());
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	}

}
