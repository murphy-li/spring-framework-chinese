/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.ejb.access;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.naming.NamingException;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.lang.Nullable;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.rmi.RmiClientInterceptorUtils;

/**
 * Basic invoker for a remote Stateless Session Bean.
 * Designed for EJB 2.x, but works for EJB 3 Session Beans as well.
 *
 * <p>"Creates" a new EJB instance for each invocation, or caches the session
 * bean instance for all invocations (see {@link #setCacheSessionBean}).
 * See {@link org.springframework.jndi.JndiObjectLocator} for info on
 * how to specify the JNDI location of the target EJB.
 *
 * <p>In a bean container, this class is normally best used as a singleton. However,
 * if that bean container pre-instantiates singletons (as do the XML ApplicationContext
 * variants) you may have a problem if the bean container is loaded before the EJB
 * container loads the target EJB. That is because by default the JNDI lookup will be
 * performed in the init method of this class and cached, but the EJB will not have been
 * bound at the target location yet. The best solution is to set the "lookupHomeOnStartup"
 * property to "false", in which case the home will be fetched on first access to the EJB.
 * (This flag is only true by default for backwards compatibility reasons).
 *
 * <p>This invoker is typically used with an RMI business interface, which serves
 * as super-interface of the EJB component interface. Alternatively, this invoker
 * can also proxy a remote SLSB with a matching non-RMI business interface, i.e. an
 * interface that mirrors the EJB business methods but does not declare RemoteExceptions.
 * In the latter case, RemoteExceptions thrown by the EJB stub will automatically get
 * converted to Spring's unchecked RemoteAccessException.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 09.05.2003
 * @see org.springframework.remoting.RemoteAccessException
 * @see AbstractSlsbInvokerInterceptor#setLookupHomeOnStartup
 * @see AbstractSlsbInvokerInterceptor#setCacheHome
 * @see AbstractRemoteSlsbInvokerInterceptor#setRefreshHomeOnConnectFailure
 */
/**
 * 远程无状态会话Bean的基本调用程序。 
 * 专为EJB 2.x设计，但也适用于EJB 3会话Bean。 
 *  <p>为每个调用"创建"一个新的EJB实例，或为所有调用缓存会话bean实例（请参阅{@link  #setCacheSessionBean}）。 
 * 有关如何指定目标EJB的JNDI位置的信息，请参见{@link  org.springframework.jndi.JndiObjectLocator}。 
 *  <p>在bean容器中，此类通常最好用作单例。 
 * 但是，如果该bean容器预先实例化了单例（如XML ApplicationContext变体一样），则在EJB容器加载目标EJB之前加载bean容器时，您可能会遇到问题。 
 * 这是因为默认情况下，将在此类的init方法中执行JNDI查找并将其缓存，但是尚未在目标位置绑定EJB。 
 * 最好的解决方案是将"lookupHomeOnStartup"属性设置为"false"，在这种情况下，将在首次访问EJB时获取主目录。 
 *  （由于向后兼容的原因，此标志默认情况下仅为true）。 
 *  <p>此调用程序通常与RMI业务接口一起使用，该接口用作EJB组件接口的超级接口。 
 * 或者，此调用程序还可以使用匹配的非RMI业务接口（即反映EJB业务方法但不声明RemoteExceptions的接口）代理远程SLSB。 
 * 在后一种情况下，EJB存根引发的RemoteExceptions将自动转换为Spring的未经检查的RemoteAccessException。 
 *  @author 罗德·约翰逊（Rod Johnson）@author  Juergen Hoeller @since 09.05.2003 
 * @see  org.springframework.remoting.RemoteAccessException 
 * @see  AbstractSlsbInvokerInterceptor＃setLookupHomeOnStartup 
 * @see  AbstractSlsbInvokerInterceptor＃setCacheFaker <Connect>
 */
public class SimpleRemoteSlsbInvokerInterceptor extends AbstractRemoteSlsbInvokerInterceptor
		implements DisposableBean {

	private boolean cacheSessionBean = false;

	@Nullable
	private Object beanInstance;

	private final Object beanInstanceMonitor = new Object();


	/**
	 * Set whether to cache the actual session bean object.
	 * <p>Off by default for standard EJB compliance. Turn this flag
	 * on to optimize session bean access for servers that are
	 * known to allow for caching the actual session bean object.
	 * @see #setCacheHome
	 */
	/**
	 * 设置是否缓存实际的会话bean对象。 
	 *  <p>默认情况下为标准EJB合规性关闭。 
	 * 启用此标志可优化已知允许缓存实际会话Bean对象的服务器的会话Bean访问。 
	 *  
	 * @see  #setCacheHome
	 */
	public void setCacheSessionBean(boolean cacheSessionBean) {
		this.cacheSessionBean = cacheSessionBean;
	}


	/**
	 * This implementation "creates" a new EJB instance for each invocation.
	 * Can be overridden for custom invocation strategies.
	 * <p>Alternatively, override {@link #getSessionBeanInstance} and
	 * {@link #releaseSessionBeanInstance} to change EJB instance creation,
	 * for example to hold a single shared EJB component instance.
	 */
	/**
	 * 此实现为每次调用"创建"一个新的EJB实例。 
	 * 可以为自定义调用策略覆盖。 
	 *  <p>或者，重写{@link  #getSessionBeanInstance}和{@link  #releaseSessionBeanInstance}来更改EJB实例的创建，例如，保存一个共享的EJB组件实例。 
	 * 
	 */
	@Override
	@Nullable
	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		Object ejb = null;
		try {
			ejb = getSessionBeanInstance();
			return RmiClientInterceptorUtils.invokeRemoteMethod(invocation, ejb);
		}
		catch (NamingException ex) {
			throw new RemoteLookupFailureException("Failed to locate remote EJB [" + getJndiName() + "]", ex);
		}
		catch (InvocationTargetException ex) {
			Throwable targetEx = ex.getTargetException();
			if (targetEx instanceof RemoteException) {
				RemoteException rex = (RemoteException) targetEx;
				throw RmiClientInterceptorUtils.convertRmiAccessException(
					invocation.getMethod(), rex, isConnectFailure(rex), getJndiName());
			}
			else if (targetEx instanceof CreateException) {
				throw RmiClientInterceptorUtils.convertRmiAccessException(
					invocation.getMethod(), targetEx, "Could not create remote EJB [" + getJndiName() + "]");
			}
			throw targetEx;
		}
		finally {
			if (ejb instanceof EJBObject) {
				releaseSessionBeanInstance((EJBObject) ejb);
			}
		}
	}

	/**
	 * Return an EJB component instance to delegate the call to.
	 * <p>The default implementation delegates to {@link #newSessionBeanInstance}.
	 * @return the EJB component instance
	 * @throws NamingException if thrown by JNDI
	 * @throws InvocationTargetException if thrown by the create method
	 * @see #newSessionBeanInstance
	 */
	/**
	 * 返回EJB组件实例以将调用委派给该实例。 
	 *  <p>默认实现委托给{@link  #newSessionBeanInstance}。 
	 *  
	 * @return  EJB组件实例
	 * @throws 如果是由JNDI抛出，则为NamingException 
	 * @throws 如果是通过创建方法
	 * @see  #newSessionBeanInstance抛出，则为InvocationTargetException
	 */
	protected Object getSessionBeanInstance() throws NamingException, InvocationTargetException {
		if (this.cacheSessionBean) {
			synchronized (this.beanInstanceMonitor) {
				if (this.beanInstance == null) {
					this.beanInstance = newSessionBeanInstance();
				}
				return this.beanInstance;
			}
		}
		else {
			return newSessionBeanInstance();
		}
	}

	/**
	 * Release the given EJB instance.
	 * <p>The default implementation delegates to {@link #removeSessionBeanInstance}.
	 * @param ejb the EJB component instance to release
	 * @see #removeSessionBeanInstance
	 */
	/**
	 * 释放给定的EJB实例。 
	 *  <p>默认实现委托给{@link  #removeSessionBeanInstance}。 
	 *  
	 * @param  ejb EJB组件实例发布
	 * @see  #removeSessionBeanInstance
	 */
	protected void releaseSessionBeanInstance(EJBObject ejb) {
		if (!this.cacheSessionBean) {
			removeSessionBeanInstance(ejb);
		}
	}

	/**
	 * Reset the cached session bean instance, if necessary.
	 */
	/**
	 * 如有必要，重置缓存的会话bean实例。 
	 * 
	 */
	@Override
	protected void refreshHome() throws NamingException {
		super.refreshHome();
		if (this.cacheSessionBean) {
			synchronized (this.beanInstanceMonitor) {
				this.beanInstance = null;
			}
		}
	}

	/**
	 * Remove the cached session bean instance, if necessary.
	 */
	/**
	 * 如有必要，删除高速缓存的会话bean实例。 
	 * 
	 */
	@Override
	public void destroy() {
		if (this.cacheSessionBean) {
			synchronized (this.beanInstanceMonitor) {
				if (this.beanInstance instanceof EJBObject) {
					removeSessionBeanInstance((EJBObject) this.beanInstance);
				}
			}
		}
	}

}
