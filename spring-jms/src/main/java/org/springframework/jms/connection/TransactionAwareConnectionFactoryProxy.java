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

package org.springframework.jms.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TransactionInProgressException;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Proxy for a target JMS {@link javax.jms.ConnectionFactory}, adding awareness of
 * Spring-managed transactions. Similar to a transactional JNDI ConnectionFactory
 * as provided by a Java EE application server.
 *
 * <p>Messaging code which should remain unaware of Spring's JMS support can work with
 * this proxy to seamlessly participate in Spring-managed transactions. Note that the
 * transaction manager, for example {@link JmsTransactionManager}, still needs to work
 * with the underlying ConnectionFactory, <i>not</i> with this proxy.
 *
 * <p><b>Make sure that TransactionAwareConnectionFactoryProxy is the outermost
 * ConnectionFactory of a chain of ConnectionFactory proxies/adapters.</b>
 * TransactionAwareConnectionFactoryProxy can delegate either directly to the
 * target factory or to some intermediary adapter like
 * {@link UserCredentialsConnectionFactoryAdapter}.
 *
 * <p>Delegates to {@link ConnectionFactoryUtils} for automatically participating
 * in thread-bound transactions, for example managed by {@link JmsTransactionManager}.
 * {@code createSession} calls and {@code close} calls on returned Sessions
 * will behave properly within a transaction, that is, always work on the transactional
 * Session. If not within a transaction, normal ConnectionFactory behavior applies.
 *
 * <p>Note that transactional JMS Sessions will be registered on a per-Connection
 * basis. To share the same JMS Session across a transaction, make sure that you
 * operate on the same JMS Connection handle - either through reusing the handle
 * or through configuring a {@link SingleConnectionFactory} underneath.
 *
 * <p>Returned transactional Session proxies will implement the {@link SessionProxy}
 * interface to allow for access to the underlying target Session. This is only
 * intended for accessing vendor-specific Session API or for testing purposes
 * (e.g. to perform manual transaction control). For typical application purposes,
 * simply use the standard JMS Session interface.
 *
 * <p>As of Spring Framework 5, this class delegates JMS 2.0 {@code JMSContext}
 * calls and therefore requires the JMS 2.0 API to be present at runtime.
 * It may nevertheless run against a JMS 1.1 driver (bound to the JMS 2.0 API)
 * as long as no actual JMS 2.0 calls are triggered by the application's setup.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see UserCredentialsConnectionFactoryAdapter
 * @see SingleConnectionFactory
 */
/**
 * 目标JMS {@link  javax.jms.ConnectionFactory}的代理，从而增加了对Spring管理的事务的认识。 
 * 类似于Java EE应用程序服务器提供的事务性JNDI ConnectionFactory。 
 *  <p>应该不了解Spring的JMS支持的消息传递代码可以与此代理一起无缝地参与Spring管理的事务。 
 * 请注意，事务管理器（例如{@link  JmsTransactionManager}）仍需要与基础ConnectionFactory一起使用，而<i>不是</ i>与此代理一起使用。 
 *  <p> <b>确保TransactionAwareConnectionFactoryProxy是ConnectionFactory代理/适配器链的最外层ConnectionFactory。 
 * </ b> TransactionAwareConnectionFactoryProxy可以直接委托给目标工厂或某些中介适配器，例如{@link  UserCredentialsConnectionFactoryAdapter}。 
 *  <p>代表{{@link> ConnectionFactoryUtils}来自动参与线程绑定的事务，例如由{@link  JmsTransactionManager}管理。 
 * 返回的Session上的{@code  createSession}调用和{@code  close}调用将在事务中正常运行，也就是说，始终在事务Session上工作。 
 * 如果不在事务内，则将应用正常的ConnectionFactory行为。 
 *  <p>请注意，事务性JMS会话将基于每个连接进行注册。 
 * 要在整个事务中共享同一JMS会话，请确保您在同一JMS连接句柄上进行操作-通过重用该句柄或通过在其下配置{@link  SingleConnectionFactory}。 
 *  <p>返回的交易会话代理将实现{@link  SessionProxy}接口，以允许访问基础目标会话。 
 * 这仅用于访问特定于供应商的会话API或用于测试目的（例如执行手动交易控制）。 
 * 对于典型的应用目的，只需使用标准的JMS Session接口。 
 *  <p>从Spring Framework 5开始，该类委托JMS 2.0 {@code  JMSContext}调用，因此需要在运行时提供JMS 2.0 API。 
 * 但是，只要应用程序的设置未触发任何实际的JMS 2.0调用，它就可以在JMS 1.1驱动程序（绑定到JMS 2.0 API）上运行。 
 *  @author  Juergen Hoeller @自2.0起
 * @see  UserCredentialsConnectionFactoryAdapter 
 * @see  SingleConnectionFactory
 */
public class TransactionAwareConnectionFactoryProxy
		implements ConnectionFactory, QueueConnectionFactory, TopicConnectionFactory {

	@Nullable
	private ConnectionFactory targetConnectionFactory;

	private boolean synchedLocalTransactionAllowed = false;


	/**
	 * Create a new TransactionAwareConnectionFactoryProxy.
	 */
	/**
	 * 创建一个新的TransactionAwareConnectionFactoryProxy。 
	 * 
	 */
	public TransactionAwareConnectionFactoryProxy() {
	}

	/**
	 * Create a new TransactionAwareConnectionFactoryProxy.
	 * @param targetConnectionFactory the target ConnectionFactory
	 */
	/**
	 * 创建一个新的TransactionAwareConnectionFactoryProxy。 
	 *  
	 * @param  targetConnectionFactory目标ConnectionFactory
	 */
	public TransactionAwareConnectionFactoryProxy(ConnectionFactory targetConnectionFactory) {
		setTargetConnectionFactory(targetConnectionFactory);
	}


	/**
	 * Set the target ConnectionFactory that this ConnectionFactory should delegate to.
	 */
	/**
	 * 设置此ConnectionFactory应该委托给的目标ConnectionFactory。 
	 * 
	 */
	public final void setTargetConnectionFactory(ConnectionFactory targetConnectionFactory) {
		Assert.notNull(targetConnectionFactory, "'targetConnectionFactory' must not be null");
		this.targetConnectionFactory = targetConnectionFactory;
	}

	/**
	 * Return the target ConnectionFactory that this ConnectionFactory should delegate to.
	 */
	/**
	 * 返回此ConnectionFactory应该委托给的目标ConnectionFactory。 
	 * 
	 */
	protected ConnectionFactory getTargetConnectionFactory() {
		ConnectionFactory target = this.targetConnectionFactory;
		Assert.state(target != null, "'targetConnectionFactory' is required");
		return target;
	}

	/**
	 * Set whether to allow for a local JMS transaction that is synchronized with a
	 * Spring-managed transaction (where the main transaction might be a JDBC-based
	 * one for a specific DataSource, for example), with the JMS transaction committing
	 * right after the main transaction. If not allowed, the given ConnectionFactory
	 * needs to handle transaction enlistment underneath the covers.
	 * <p>Default is "false": If not within a managed transaction that encompasses
	 * the underlying JMS ConnectionFactory, standard Sessions will be returned.
	 * Turn this flag on to allow participation in any Spring-managed transaction,
	 * with a local JMS transaction synchronized with the main transaction.
	 */
	/**
	 * 设置是否允许与Spring管理的事务同步的本地JMS事务（例如，主事务可能是特定数据源的基于JDBC的事务），并且JMS事务在主事务之后立即提交。 
	 * 如果不允许，给定的ConnectionFactory需要在幕后处理事务登记。 
	 *  <p>默认值为"false"：如果不在包含基础JMS ConnectionFactory的托管事务中，则将返回标准Session。 
	 * 打开此标志，以允许参与任何Spring管理的事务，并将本地JMS事务与主事务同步。 
	 * 
	 */
	public void setSynchedLocalTransactionAllowed(boolean synchedLocalTransactionAllowed) {
		this.synchedLocalTransactionAllowed = synchedLocalTransactionAllowed;
	}

	/**
	 * Return whether to allow for a local JMS transaction that is synchronized
	 * with a Spring-managed transaction.
	 */
	/**
	 * 返回是否允许与Spring管理的事务同步的本地JMS事务。 
	 * 
	 */
	protected boolean isSynchedLocalTransactionAllowed() {
		return this.synchedLocalTransactionAllowed;
	}


	@Override
	public Connection createConnection() throws JMSException {
		Connection targetConnection = getTargetConnectionFactory().createConnection();
		return getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public Connection createConnection(String username, String password) throws JMSException {
		Connection targetConnection = getTargetConnectionFactory().createConnection(username, password);
		return getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public QueueConnection createQueueConnection() throws JMSException {
		ConnectionFactory target = getTargetConnectionFactory();
		if (!(target instanceof QueueConnectionFactory)) {
			throw new javax.jms.IllegalStateException("'targetConnectionFactory' is no QueueConnectionFactory");
		}
		QueueConnection targetConnection = ((QueueConnectionFactory) target).createQueueConnection();
		return (QueueConnection) getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public QueueConnection createQueueConnection(String username, String password) throws JMSException {
		ConnectionFactory target = getTargetConnectionFactory();
		if (!(target instanceof QueueConnectionFactory)) {
			throw new javax.jms.IllegalStateException("'targetConnectionFactory' is no QueueConnectionFactory");
		}
		QueueConnection targetConnection = ((QueueConnectionFactory) target).createQueueConnection(username, password);
		return (QueueConnection) getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public TopicConnection createTopicConnection() throws JMSException {
		ConnectionFactory target = getTargetConnectionFactory();
		if (!(target instanceof TopicConnectionFactory)) {
			throw new javax.jms.IllegalStateException("'targetConnectionFactory' is no TopicConnectionFactory");
		}
		TopicConnection targetConnection = ((TopicConnectionFactory) target).createTopicConnection();
		return (TopicConnection) getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public TopicConnection createTopicConnection(String username, String password) throws JMSException {
		ConnectionFactory target = getTargetConnectionFactory();
		if (!(target instanceof TopicConnectionFactory)) {
			throw new javax.jms.IllegalStateException("'targetConnectionFactory' is no TopicConnectionFactory");
		}
		TopicConnection targetConnection = ((TopicConnectionFactory) target).createTopicConnection(username, password);
		return (TopicConnection) getTransactionAwareConnectionProxy(targetConnection);
	}

	@Override
	public JMSContext createContext() {
		return getTargetConnectionFactory().createContext();
	}

	@Override
	public JMSContext createContext(String userName, String password) {
		return getTargetConnectionFactory().createContext(userName, password);
	}

	@Override
	public JMSContext createContext(String userName, String password, int sessionMode) {
		return getTargetConnectionFactory().createContext(userName, password, sessionMode);
	}

	@Override
	public JMSContext createContext(int sessionMode) {
		return getTargetConnectionFactory().createContext(sessionMode);
	}


	/**
	 * Wrap the given Connection with a proxy that delegates every method call to it
	 * but handles Session lookup in a transaction-aware fashion.
	 * @param target the original Connection to wrap
	 * @return the wrapped Connection
	 */
	/**
	 * 用代理包装给定的Connection，该代理将每个方法调用委派给它，但以事务感知方式处理Session查找。 
	 *  
	 * @param 以原始Connection为目标以包装
	 * @return 包装的Connection
	 */
	protected Connection getTransactionAwareConnectionProxy(Connection target) {
		List<Class<?>> classes = new ArrayList<>(3);
		classes.add(Connection.class);
		if (target instanceof QueueConnection) {
			classes.add(QueueConnection.class);
		}
		if (target instanceof TopicConnection) {
			classes.add(TopicConnection.class);
		}
		return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
				ClassUtils.toClassArray(classes), new TransactionAwareConnectionInvocationHandler(target));
	}


	/**
	 * Invocation handler that exposes transactional Sessions for the underlying Connection.
	 */
	/**
	 * 公开基础连接的事务性会话的调用处理程序。 
	 * 
	 */
	private class TransactionAwareConnectionInvocationHandler implements InvocationHandler {

		private final Connection target;

		public TransactionAwareConnectionInvocationHandler(Connection target) {
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// Invocation on ConnectionProxy interface coming in...

			if (method.getName().equals("equals")) {
				// Only consider equal when proxies are identical.
				return (proxy == args[0]);
			}
			else if (method.getName().equals("hashCode")) {
				// Use hashCode of Connection proxy.
				return System.identityHashCode(proxy);
			}
			else if (Session.class == method.getReturnType()) {
				Session session = ConnectionFactoryUtils.getTransactionalSession(
						getTargetConnectionFactory(), this.target, isSynchedLocalTransactionAllowed());
				if (session != null) {
					return getCloseSuppressingSessionProxy(session);
				}
			}
			else if (QueueSession.class == method.getReturnType()) {
				QueueSession session = ConnectionFactoryUtils.getTransactionalQueueSession(
						(QueueConnectionFactory) getTargetConnectionFactory(), (QueueConnection) this.target,
						isSynchedLocalTransactionAllowed());
				if (session != null) {
					return getCloseSuppressingSessionProxy(session);
				}
			}
			else if (TopicSession.class == method.getReturnType()) {
				TopicSession session = ConnectionFactoryUtils.getTransactionalTopicSession(
						(TopicConnectionFactory) getTargetConnectionFactory(), (TopicConnection) this.target,
						isSynchedLocalTransactionAllowed());
				if (session != null) {
					return getCloseSuppressingSessionProxy(session);
				}
			}

			// Invoke method on target Connection.
			try {
				return method.invoke(this.target, args);
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}

		private Session getCloseSuppressingSessionProxy(Session target) {
			List<Class<?>> classes = new ArrayList<>(3);
			classes.add(SessionProxy.class);
			if (target instanceof QueueSession) {
				classes.add(QueueSession.class);
			}
			if (target instanceof TopicSession) {
				classes.add(TopicSession.class);
			}
			return (Session) Proxy.newProxyInstance(SessionProxy.class.getClassLoader(),
					ClassUtils.toClassArray(classes), new CloseSuppressingSessionInvocationHandler(target));
		}
	}


	/**
	 * Invocation handler that suppresses close calls for a transactional JMS Session.
	 */
	/**
	 * 调用处理程序，禁止对事务性JMS会话的关闭调用。 
	 * 
	 */
	private static class CloseSuppressingSessionInvocationHandler implements InvocationHandler {

		private final Session target;

		public CloseSuppressingSessionInvocationHandler(Session target) {
			this.target = target;
		}

		@Override
		@Nullable
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// Invocation on SessionProxy interface coming in...

			if (method.getName().equals("equals")) {
				// Only consider equal when proxies are identical.
				return (proxy == args[0]);
			}
			else if (method.getName().equals("hashCode")) {
				// Use hashCode of Connection proxy.
				return System.identityHashCode(proxy);
			}
			else if (method.getName().equals("commit")) {
				throw new TransactionInProgressException("Commit call not allowed within a managed transaction");
			}
			else if (method.getName().equals("rollback")) {
				throw new TransactionInProgressException("Rollback call not allowed within a managed transaction");
			}
			else if (method.getName().equals("close")) {
				// Handle close method: not to be closed within a transaction.
				return null;
			}
			else if (method.getName().equals("getTargetSession")) {
				// Handle getTargetSession method: return underlying Session.
				return this.target;
			}

			// Invoke method on target Session.
			try {
				return method.invoke(this.target, args);
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}

}
