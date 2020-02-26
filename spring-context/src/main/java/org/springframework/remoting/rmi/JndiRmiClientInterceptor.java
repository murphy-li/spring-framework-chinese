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

package org.springframework.remoting.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.NamingException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jndi.JndiObjectLocator;
import org.springframework.lang.Nullable;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.util.Assert;

/**
 * {@link org.aopalliance.intercept.MethodInterceptor} for accessing RMI services
 * from JNDI. Typically used for RMI-IIOP but can also be used for EJB home objects
 * (for example, a Stateful Session Bean home). In contrast to a plain JNDI lookup,
 * this accessor also performs narrowing through PortableRemoteObject.
 *
 * <p>With conventional RMI services, this invoker is typically used with the RMI
 * service interface. Alternatively, this invoker can also proxy a remote RMI service
 * with a matching non-RMI business interface, i.e. an interface that mirrors the RMI
 * service methods but does not declare RemoteExceptions. In the latter case,
 * RemoteExceptions thrown by the RMI stub will automatically get converted to
 * Spring's unchecked RemoteAccessException.
 *
 * <p>The JNDI environment can be specified as "jndiEnvironment" property,
 * or be configured in a {@code jndi.properties} file or as system properties.
 * For example:
 *
 * <pre class="code">&lt;property name="jndiEnvironment"&gt;
 * 	 &lt;props>
 *		 &lt;prop key="java.naming.factory.initial"&gt;com.sun.jndi.cosnaming.CNCtxFactory&lt;/prop&gt;
 *		 &lt;prop key="java.naming.provider.url"&gt;iiop://localhost:1050&lt;/prop&gt;
 *	 &lt;/props&gt;
 * &lt;/property&gt;</pre>
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see #setJndiTemplate
 * @see #setJndiEnvironment
 * @see #setJndiName
 * @see JndiRmiServiceExporter
 * @see JndiRmiProxyFactoryBean
 * @see org.springframework.remoting.RemoteAccessException
 * @see java.rmi.RemoteException
 * @see java.rmi.Remote
 */
/**
 * {@link  org.aopalliance.intercept.MethodInterceptor}用于从JNDI访问RMI服务。 
 * 通常用于RMI-IIOP，但也可以用于EJB主对象（例如，有状态会话Bean主对象）。 
 * 与普通的JNDI查找相反，此访问器还通过PortableRemoteObject执行缩小。 
 *  <p>对于传统的RMI服务，此调用程序通常与RMI服务接口一起使用。 
 * 替代地，该调用者还可以用匹配的非RMI业务接口代理远程RMI服务，即，该接口镜像RMI服务方法但不声明RemoteExceptions。 
 * 在后一种情况下，由RMI存根引发的RemoteExceptions将自动转换为Spring的未经检查的RemoteAccessException。 
 *  <p> JNDI环境可以指定为"jndiEnvironment"属性，也可以在{@code  jndi.properties}文件中配置，也可以配置为系统属性。 
 * 例如：<pre class ="code"> <property name ="jndiEnvironment"> <props> <prop key ="java.naming.factory.initial"> com.sun.jndi.cosnaming.CNCtxFactory </ prop> < prop key ="java.naming.provider.url"> iiop：// localhost：1050 </ prop> </ props> </ property> </ pre> @author  Juergen Hoeller @since 1.1 
 * @see ＃ setJndiTemplate 
 * @see  #setJndiEnvironment 
 * @see  #setJndiName 
 * @see  JndiRmiServiceExporter 
 * @see  JndiRmiProxyFactoryBean 
 * @see  org.springframework.remoting.RemoteAccessException 
 * @see  java.rmi.RemoteException 
 * @see  java.rmi 。 
 * 远程
 */
public class JndiRmiClientInterceptor extends JndiObjectLocator implements MethodInterceptor, InitializingBean {

	private Class<?> serviceInterface;

	private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();

	private boolean lookupStubOnStartup = true;

	private boolean cacheStub = true;

	private boolean refreshStubOnConnectFailure = false;

	private boolean exposeAccessContext = false;

	private Object cachedStub;

	private final Object stubMonitor = new Object();


	/**
	 * Set the interface of the service to access.
	 * The interface must be suitable for the particular service and remoting tool.
	 * <p>Typically required to be able to create a suitable service proxy,
	 * but can also be optional if the lookup returns a typed stub.
	 */
	/**
	 * 设置要访问的服务接口。 
	 * 该接口必须适合于特定的服务和远程工具。 
	 *  <p>通常要求能够创建合适的服务代理，但是如果查找返回类型化的存根，也可以是可选的。 
	 * 
	 */
	public void setServiceInterface(Class<?> serviceInterface) {
		Assert.notNull(serviceInterface, "'serviceInterface' must not be null");
		Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
		this.serviceInterface = serviceInterface;
	}

	/**
	 * Return the interface of the service to access.
	 */
	/**
	 * 返回服务的接口以进行访问。 
	 * 
	 */
	public Class<?> getServiceInterface() {
		return this.serviceInterface;
	}

	/**
	 * Set the RemoteInvocationFactory to use for this accessor.
	 * Default is a {@link DefaultRemoteInvocationFactory}.
	 * <p>A custom invocation factory can add further context information
	 * to the invocation, for example user credentials.
	 */
	/**
	 * 设置要用于此访问器的RemoteInvocationFactory。 
	 * 默认值为{@link  DefaultRemoteInvocationFactory}。 
	 *  <p>自定义调用工厂可以向调用添加其他上下文信息，例如用户凭据。 
	 * 
	 */
	public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
		this.remoteInvocationFactory = remoteInvocationFactory;
	}

	/**
	 * Return the RemoteInvocationFactory used by this accessor.
	 */
	/**
	 * 返回此访问器使用的RemoteInvocationFactory。 
	 * 
	 */
	public RemoteInvocationFactory getRemoteInvocationFactory() {
		return this.remoteInvocationFactory;
	}

	/**
	 * Set whether to look up the RMI stub on startup. Default is "true".
	 * <p>Can be turned off to allow for late start of the RMI server.
	 * In this case, the RMI stub will be fetched on first access.
	 * @see #setCacheStub
	 */
	/**
	 * 设置是否在启动时查找RMI存根。 
	 * 默认值为"true"。 
	 *  <p>可以关闭以允许RMI服务器延迟启动。 
	 * 在这种情况下，将在首次访问时获取RMI存根。 
	 *  
	 * @see  #setCacheStub
	 */
	public void setLookupStubOnStartup(boolean lookupStubOnStartup) {
		this.lookupStubOnStartup = lookupStubOnStartup;
	}

	/**
	 * Set whether to cache the RMI stub once it has been located.
	 * Default is "true".
	 * <p>Can be turned off to allow for hot restart of the RMI server.
	 * In this case, the RMI stub will be fetched for each invocation.
	 * @see #setLookupStubOnStartup
	 */
	/**
	 * 设置一旦找到RMI存根，是否要对其进行缓存。 
	 * 默认值为"true"。 
	 *  <p>可以关闭以允许RMI服务器热重启。 
	 * 在这种情况下，将为每次调用获取RMI存根。 
	 *  
	 * @see  #setLookupStubOnStartup
	 */
	public void setCacheStub(boolean cacheStub) {
		this.cacheStub = cacheStub;
	}

	/**
	 * Set whether to refresh the RMI stub on connect failure.
	 * Default is "false".
	 * <p>Can be turned on to allow for hot restart of the RMI server.
	 * If a cached RMI stub throws an RMI exception that indicates a
	 * remote connect failure, a fresh proxy will be fetched and the
	 * invocation will be retried.
	 * @see java.rmi.ConnectException
	 * @see java.rmi.ConnectIOException
	 * @see java.rmi.NoSuchObjectException
	 */
	/**
	 * 设置是否在连接失败时刷新RMI存根。 
	 * 默认值为"false"。 
	 *  <p>可以打开以允许RMI服务器热重启。 
	 * 如果缓存的RMI存根抛出RMI异常，指示远程连接失败，则将获取新的代理并重试调用。 
	 *  
	 * @see  java.rmi.ConnectException 
	 * @see  java.rmi.ConnectIOException 
	 * @see  java.rmi.NoSuchObjectException
	 */
	public void setRefreshStubOnConnectFailure(boolean refreshStubOnConnectFailure) {
		this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
	}

	/**
	 * Set whether to expose the JNDI environment context for all access to the target
	 * RMI stub, i.e. for all method invocations on the exposed object reference.
	 * <p>Default is "false", i.e. to only expose the JNDI context for object lookup.
	 * Switch this flag to "true" in order to expose the JNDI environment (including
	 * the authorization context) for each RMI invocation, as needed by WebLogic
	 * for RMI stubs with authorization requirements.
	 */
	/**
	 * 设置是否为所有对目标RMI存根的访问公开JNDI环境上下文，即对公开对象引用上的所有方法调用。 
	 *  <p>默认为"false"，即仅公开JNDI上下文进行对象查找。 
	 * 将此标志切换为"true"，以便根据WebLogic对于具有授权要求的RMI存根的需要，为每个RMI调用公开JNDI环境（包括授权上下文）。 
	 * 
	 */
	public void setExposeAccessContext(boolean exposeAccessContext) {
		this.exposeAccessContext = exposeAccessContext;
	}


	@Override
	public void afterPropertiesSet() throws NamingException {
		super.afterPropertiesSet();
		prepare();
	}

	/**
	 * Fetches the RMI stub on startup, if necessary.
	 * @throws RemoteLookupFailureException if RMI stub creation failed
	 * @see #setLookupStubOnStartup
	 * @see #lookupStub
	 */
	/**
	 * 如有必要，在启动时获取RMI存根。 
	 *  
	 * @throws  RemoteLookupFailureException如果RMI存根创建失败
	 * @see  #setLookupStubOnStartup 
	 * @see  #lookupStub
	 */
	public void prepare() throws RemoteLookupFailureException {
		// Cache RMI stub on initialization?
		if (this.lookupStubOnStartup) {
			Object remoteObj = lookupStub();
			if (logger.isDebugEnabled()) {
				if (remoteObj instanceof RmiInvocationHandler) {
					logger.debug("JNDI RMI object [" + getJndiName() + "] is an RMI invoker");
				}
				else if (getServiceInterface() != null) {
					boolean isImpl = getServiceInterface().isInstance(remoteObj);
					logger.debug("Using service interface [" + getServiceInterface().getName() +
							"] for JNDI RMI object [" + getJndiName() + "] - " +
							(!isImpl ? "not " : "") + "directly implemented");
				}
			}
			if (this.cacheStub) {
				this.cachedStub = remoteObj;
			}
		}
	}

	/**
	 * Create the RMI stub, typically by looking it up.
	 * <p>Called on interceptor initialization if "cacheStub" is "true";
	 * else called for each invocation by {@link #getStub()}.
	 * <p>The default implementation retrieves the service from the
	 * JNDI environment. This can be overridden in subclasses.
	 * @return the RMI stub to store in this interceptor
	 * @throws RemoteLookupFailureException if RMI stub creation failed
	 * @see #setCacheStub
	 * @see #lookup
	 */
	/**
	 * 通常通过查找来创建RMI存根。 
	 *  <p>如果"cacheStub"为"true"，则调用拦截器初始化； 
	 * 否则通过{@link  #getStub（）}进行每次调用。 
	 *  <p>默认实现从JNDI环境检索服务。 
	 * 可以在子类中覆盖它。 
	 *  
	 * @return 要存储在此拦截器中的RMI存根
	 * @throws  RemoteLookupFailureException如果RMI存根创建失败
	 * @see  #setCacheStub 
	 * @see  #lookup
	 */
	protected Object lookupStub() throws RemoteLookupFailureException {
		try {
			return lookup();
		}
		catch (NamingException ex) {
			throw new RemoteLookupFailureException("JNDI lookup for RMI service [" + getJndiName() + "] failed", ex);
		}
	}

	/**
	 * Return the RMI stub to use. Called for each invocation.
	 * <p>The default implementation returns the stub created on initialization,
	 * if any. Else, it invokes {@link #lookupStub} to get a new stub for
	 * each invocation. This can be overridden in subclasses, for example in
	 * order to cache a stub for a given amount of time before recreating it,
	 * or to test the stub whether it is still alive.
	 * @return the RMI stub to use for an invocation
	 * @throws NamingException if stub creation failed
	 * @throws RemoteLookupFailureException if RMI stub creation failed
	 */
	/**
	 * 返回要使用的RMI存根。 
	 * 要求每次调用。 
	 *  <p>默认实现返回在初始化时创建的存根（如果有）。 
	 * 否则，它将调用{@link  #lookupStub}来为每次调用获取一个新的存根。 
	 * 可以在子类中重写此方法，例如，以便在重新创建存根之前将其存入给定的时间，或者测试存根是否仍然存在。 
	 *  
	 * @return 用于调用的RMI存根
	 * @throws  NamingException（如果存根创建失败）
	 * @throws  RemoteLookupFailureException如果RMI存根创建失败
	 */
	protected Object getStub() throws NamingException, RemoteLookupFailureException {
		if (!this.cacheStub || (this.lookupStubOnStartup && !this.refreshStubOnConnectFailure)) {
			return (this.cachedStub != null ? this.cachedStub : lookupStub());
		}
		else {
			synchronized (this.stubMonitor) {
				if (this.cachedStub == null) {
					this.cachedStub = lookupStub();
				}
				return this.cachedStub;
			}
		}
	}


	/**
	 * Fetches an RMI stub and delegates to {@link #doInvoke}.
	 * If configured to refresh on connect failure, it will call
	 * {@link #refreshAndRetry} on corresponding RMI exceptions.
	 * @see #getStub
	 * @see #doInvoke
	 * @see #refreshAndRetry
	 * @see java.rmi.ConnectException
	 * @see java.rmi.ConnectIOException
	 * @see java.rmi.NoSuchObjectException
	 */
	/**
	 * 获取RMI存根并将其委托给{@link  #doInvoke}。 
	 * 如果配置为在连接失败时刷新，它将在相应的RMI异常上调用{@link  #refreshAndRetry}。 
	 *  
	 * @see  #getStub 
	 * @see  #doInvoke 
	 * @see  #refreshAndRetry 
	 * @see  java.rmi.ConnectException 
	 * @see  java.rmi.ConnectIOException 
	 * @see  java.rmi.NoSuchObjectException
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object stub;
		try {
			stub = getStub();
		}
		catch (NamingException ex) {
			throw new RemoteLookupFailureException("JNDI lookup for RMI service [" + getJndiName() + "] failed", ex);
		}

		Context ctx = (this.exposeAccessContext ? getJndiTemplate().getContext() : null);
		try {
			return doInvoke(invocation, stub);
		}
		catch (RemoteConnectFailureException ex) {
			return handleRemoteConnectFailure(invocation, ex);
		}
		catch (RemoteException ex) {
			if (isConnectFailure(ex)) {
				return handleRemoteConnectFailure(invocation, ex);
			}
			else {
				throw ex;
			}
		}
		finally {
			getJndiTemplate().releaseContext(ctx);
		}
	}

	/**
	 * Determine whether the given RMI exception indicates a connect failure.
	 * <p>The default implementation delegates to
	 * {@link RmiClientInterceptorUtils#isConnectFailure}.
	 * @param ex the RMI exception to check
	 * @return whether the exception should be treated as connect failure
	 */
	/**
	 * 确定给定的RMI异常是否指示连接失败。 
	 *  <p>默认实现委托给{@link  RmiClientInterceptorUtils＃isConnectFailure}。 
	 *  
	 * @param 排除RMI异常以检查
	 * @return 异常是否应视为连接失败
	 */
	protected boolean isConnectFailure(RemoteException ex) {
		return RmiClientInterceptorUtils.isConnectFailure(ex);
	}

	/**
	 * Refresh the stub and retry the remote invocation if necessary.
	 * <p>If not configured to refresh on connect failure, this method
	 * simply rethrows the original exception.
	 * @param invocation the invocation that failed
	 * @param ex the exception raised on remote invocation
	 * @return the result value of the new invocation, if succeeded
	 * @throws Throwable an exception raised by the new invocation, if failed too.
	 */
	/**
	 * 刷新存根，并在必要时重试远程调用。 
	 *  <p>如果未配置为在连接失败时刷新，则此方法仅引发原始异常。 
	 *  
	 * @param 调用失败的调用
	 * @param 除远程调用时引发的异常
	 * @return 新调用的结果值（如果成功）
	 * @throws 可抛出新调用引发的异常，如果也失败。 
	 * 
	 */
	private Object handleRemoteConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
		if (this.refreshStubOnConnectFailure) {
			if (logger.isDebugEnabled()) {
				logger.debug("Could not connect to RMI service [" + getJndiName() + "] - retrying", ex);
			}
			else if (logger.isInfoEnabled()) {
				logger.info("Could not connect to RMI service [" + getJndiName() + "] - retrying");
			}
			return refreshAndRetry(invocation);
		}
		else {
			throw ex;
		}
	}

	/**
	 * Refresh the RMI stub and retry the given invocation.
	 * Called by invoke on connect failure.
	 * @param invocation the AOP method invocation
	 * @return the invocation result, if any
	 * @throws Throwable in case of invocation failure
	 * @see #invoke
	 */
	/**
	 * 刷新RMI存根，然后重试给定的调用。 
	 * 在连接失败时由invoke调用。 
	 *  
	 * @param 调用AOP方法调用
	 * @return 调用结果（如果有）
	 * @throws 调用失败时可抛出
	 * @see  #invoke
	 */
	@Nullable
	protected Object refreshAndRetry(MethodInvocation invocation) throws Throwable {
		Object freshStub;
		synchronized (this.stubMonitor) {
			this.cachedStub = null;
			freshStub = lookupStub();
			if (this.cacheStub) {
				this.cachedStub = freshStub;
			}
		}
		return doInvoke(invocation, freshStub);
	}


	/**
	 * Perform the given invocation on the given RMI stub.
	 * @param invocation the AOP method invocation
	 * @param stub the RMI stub to invoke
	 * @return the invocation result, if any
	 * @throws Throwable in case of invocation failure
	 */
	/**
	 * 在给定的RMI存根上执行给定的调用。 
	 *  
	 * @param 调用AOP方法调用
	 * @param 存根RMI存根调用
	 * @return 调用结果（如果有的话）
	 * @throws 调用失败时可抛出
	 */
	@Nullable
	protected Object doInvoke(MethodInvocation invocation, Object stub) throws Throwable {
		if (stub instanceof RmiInvocationHandler) {
			// RMI invoker
			try {
				return doInvoke(invocation, (RmiInvocationHandler) stub);
			}
			catch (RemoteException ex) {
				throw convertRmiAccessException(ex, invocation.getMethod());
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
			catch (Throwable ex) {
				throw new RemoteInvocationFailureException("Invocation of method [" + invocation.getMethod() +
						"] failed in RMI service [" + getJndiName() + "]", ex);
			}
		}
		else {
			// traditional RMI stub
			try {
				return RmiClientInterceptorUtils.invokeRemoteMethod(invocation, stub);
			}
			catch (InvocationTargetException ex) {
				Throwable targetEx = ex.getTargetException();
				if (targetEx instanceof RemoteException) {
					throw convertRmiAccessException((RemoteException) targetEx, invocation.getMethod());
				}
				else {
					throw targetEx;
				}
			}
		}
	}

	/**
	 * Apply the given AOP method invocation to the given {@link RmiInvocationHandler}.
	 * <p>The default implementation delegates to {@link #createRemoteInvocation}.
	 * @param methodInvocation the current AOP method invocation
	 * @param invocationHandler the RmiInvocationHandler to apply the invocation to
	 * @return the invocation result
	 * @throws RemoteException in case of communication errors
	 * @throws NoSuchMethodException if the method name could not be resolved
	 * @throws IllegalAccessException if the method could not be accessed
	 * @throws InvocationTargetException if the method invocation resulted in an exception
	 * @see org.springframework.remoting.support.RemoteInvocation
	 */
	/**
	 * 将给定的AOP方法调用应用于给定的{@link  RmiInvocationHandler}。 
	 *  <p>默认实现委托给{@link  #createRemoteInvocation}。 
	 *  
	 * @param  methodInvocation当前AOP方法调用
	 * @param  invocationHandler RmiInvocationHandler在通信错误的情况下将调用应用于
	 * @return 调用结果
	 * @throws  RemoteException 
	 * @throws  NoSuchMethodException如果方法名称不能如果无法访问该方法，则被解决
	 * @throws  IllegalAccessException 
	 * @throws  InvocationTargetException如果方法调用导致异常
	 * @see  org.springframework.remoting.support.RemoteInvocation
	 */
	protected Object doInvoke(MethodInvocation methodInvocation, RmiInvocationHandler invocationHandler)
			throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		if (AopUtils.isToStringMethod(methodInvocation.getMethod())) {
			return "RMI invoker proxy for service URL [" + getJndiName() + "]";
		}

		return invocationHandler.invoke(createRemoteInvocation(methodInvocation));
	}

	/**
	 * Create a new RemoteInvocation object for the given AOP method invocation.
	 * <p>The default implementation delegates to the configured
	 * {@link #setRemoteInvocationFactory RemoteInvocationFactory}.
	 * This can be overridden in subclasses in order to provide custom RemoteInvocation
	 * subclasses, containing additional invocation parameters (e.g. user credentials).
	 * <p>Note that it is preferable to build a custom RemoteInvocationFactory
	 * as a reusable strategy, instead of overriding this method.
	 * @param methodInvocation the current AOP method invocation
	 * @return the RemoteInvocation object
	 * @see RemoteInvocationFactory#createRemoteInvocation
	 */
	/**
	 * 为给定的AOP方法调用创建一个新的RemoteInvocation对象。 
	 *  <p>默认实现将委派给已配置的{@link  #setRemoteInvocationFactory RemoteInvocationFactory}。 
	 * 为了提供包含附加调用参数（例如，用户凭据）的自定义RemoteInvocation子类，可以在子类中重写此方法。 
	 *  <p>请注意，最好将自定义RemoteInvocationFactory构建为可重用策略，而不是重写此方法。 
	 *  
	 * @param  methodInvocation当前AOP方法调用
	 * @return  RemoteInvocation对象
	 * @see  RemoteInvocationFactory＃createRemoteInvocation
	 */
	protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
		return getRemoteInvocationFactory().createRemoteInvocation(methodInvocation);
	}

	/**
	 * Convert the given RMI RemoteException that happened during remote access
	 * to Spring's RemoteAccessException if the method signature does not declare
	 * RemoteException. Else, return the original RemoteException.
	 * @param method the invoked method
	 * @param ex the RemoteException that happened
	 * @return the exception to be thrown to the caller
	 */
	/**
	 * 如果方法签名未声明RemoteException，则将在远程访问期间发生的给定RMI RemoteException转换为Spring的RemoteAccessException。 
	 * 否则，返回原始的RemoteException。 
	 *  
	 * @param 方法，已调用的方法
	 * @param ，发生在RemoteException中，发生在
	 * @return 中，该异常将被抛出给调用者
	 */
	private Exception convertRmiAccessException(RemoteException ex, Method method) {
		return RmiClientInterceptorUtils.convertRmiAccessException(method, ex, isConnectFailure(ex), getJndiName());
	}

}
