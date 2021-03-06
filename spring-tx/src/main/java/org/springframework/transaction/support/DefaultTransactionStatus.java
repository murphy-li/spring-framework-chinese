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

package org.springframework.transaction.support;

import org.springframework.lang.Nullable;
import org.springframework.transaction.NestedTransactionNotSupportedException;
import org.springframework.transaction.SavepointManager;
import org.springframework.util.Assert;

/**
 * Default implementation of the {@link org.springframework.transaction.TransactionStatus}
 * interface, used by {@link AbstractPlatformTransactionManager}. Based on the concept
 * of an underlying "transaction object".
 *
 * <p>Holds all status information that {@link AbstractPlatformTransactionManager}
 * needs internally, including a generic transaction object determined by the
 * concrete transaction manager implementation.
 *
 * <p>Supports delegating savepoint-related methods to a transaction object
 * that implements the {@link SavepointManager} interface.
 *
 * <p><b>NOTE:</b> This is <i>not</i> intended for use with other PlatformTransactionManager
 * implementations, in particular not for mock transaction managers in testing environments.
 * Use the alternative {@link SimpleTransactionStatus} class or a mock for the plain
 * {@link org.springframework.transaction.TransactionStatus} interface instead.
 *
 * @author Juergen Hoeller
 * @since 19.01.2004
 * @see AbstractPlatformTransactionManager
 * @see org.springframework.transaction.SavepointManager
 * @see #getTransaction
 * @see #createSavepoint
 * @see #rollbackToSavepoint
 * @see #releaseSavepoint
 * @see SimpleTransactionStatus
 */
/**
 * {@link  AbstractPlatformTransactionManager}使用的{@link  org.springframework.transaction.TransactionStatus}接口的默认实现。 
 * 基于底层"交易对象"的概念。 
 *  <p>保留{@link  AbstractPlatformTransactionManager}内部所需的所有状态信息，包括由具体事务管理器实现确定的通用事务对象。 
 *  <p>支持将与保存点相关的方法委派给实现{@link  SavepointManager}接口的事务对象。 
 *  <p> <b>注意：</ b> <i>不</ i>旨在与其他PlatformTransactionManager实现一起使用，尤其不适用于测试环境中的模拟事务管理器。 
 * 使用替代的{@link  SimpleTransactionStatus}类或模拟对象来创建普通的{@link  org.springframework.transaction.TransactionStatus}接口。 
 *  @author  Juergen Hoeller @2004年1月19日起
 * @see  AbstractPlatformTransactionManager 
 * @see  org.springframework.transaction.SavepointManager 
 * @see  #getTransaction 
 * @see  #createSavepoint 
 * @see  #rollbackToSavepoint 
 * @see ＃ releaseSavepoint 
 * @see  SimpleTransactionStatus
 */
public class DefaultTransactionStatus extends AbstractTransactionStatus {

	@Nullable
	private final Object transaction;

	private final boolean newTransaction;

	private final boolean newSynchronization;

	private final boolean readOnly;

	private final boolean debug;

	@Nullable
	private final Object suspendedResources;


	/**
	 * Create a new {@code DefaultTransactionStatus} instance.
	 * @param transaction underlying transaction object that can hold state
	 * for the internal transaction implementation
	 * @param newTransaction if the transaction is new, otherwise participating
	 * in an existing transaction
	 * @param newSynchronization if a new transaction synchronization has been
	 * opened for the given transaction
	 * @param readOnly whether the transaction is marked as read-only
	 * @param debug should debug logging be enabled for the handling of this transaction?
	 * Caching it in here can prevent repeated calls to ask the logging system whether
	 * debug logging should be enabled.
	 * @param suspendedResources a holder for resources that have been suspended
	 * for this transaction, if any
	 */
	/**
	 * 创建一个新的{@code  DefaultTransactionStatus}实例。 
	 *  
	 * @param 事务基础事务对象，可以为内部事务实现保留状态
	 * @param  newTransaction（如果事务是新事务），否则参与现有事务
	 * @param  newSynchronization（如果已为给定打开新事务同步）事务
	 * @param  readOnly是否将事务标记为只读
	 * @param  debug，是否应启用调试日志记录来处理该事务？在此处缓存它可以防止重复调用以询问日志记录系统是否应启用调试日志记录。 
	 *  
	 * @param 已暂停为该事务已暂停的资源（如果有）提供给持有者
	 */
	public DefaultTransactionStatus(
			@Nullable Object transaction, boolean newTransaction, boolean newSynchronization,
			boolean readOnly, boolean debug, @Nullable Object suspendedResources) {

		this.transaction = transaction;
		this.newTransaction = newTransaction;
		this.newSynchronization = newSynchronization;
		this.readOnly = readOnly;
		this.debug = debug;
		this.suspendedResources = suspendedResources;
	}


	/**
	 * Return the underlying transaction object.
	 * @throws IllegalStateException if no transaction is active
	 */
	/**
	 * 返回基础交易对象。 
	 *  
	 * @throws  IllegalStateException如果没有事务处于活动状态
	 */
	public Object getTransaction() {
		Assert.state(this.transaction != null, "No transaction active");
		return this.transaction;
	}

	/**
	 * Return whether there is an actual transaction active.
	 */
	/**
	 * 返回是否有实际的事务处于活动状态。 
	 * 
	 */
	public boolean hasTransaction() {
		return (this.transaction != null);
	}

	@Override
	public boolean isNewTransaction() {
		return (hasTransaction() && this.newTransaction);
	}

	/**
	 * Return if a new transaction synchronization has been opened
	 * for this transaction.
	 */
	/**
	 * 返回是否为此事务打开了新的事务同步。 
	 * 
	 */
	public boolean isNewSynchronization() {
		return this.newSynchronization;
	}

	/**
	 * Return if this transaction is defined as read-only transaction.
	 */
	/**
	 * 如果此事务定义为只读事务，则返回。 
	 * 
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * Return whether the progress of this transaction is debugged. This is used by
	 * {@link AbstractPlatformTransactionManager} as an optimization, to prevent repeated
	 * calls to {@code logger.isDebugEnabled()}. Not really intended for client code.
	 */
	/**
	 * 返回此事务的进度是否已调试。 
	 *  {@link  AbstractPlatformTransactionManager}使用它作为优化，以防止重复调用{@code  logger.isDebugEnabled（）}。 
	 * 并非真正针对客户端代码。 
	 * 
	 */
	public boolean isDebug() {
		return this.debug;
	}

	/**
	 * Return the holder for resources that have been suspended for this transaction,
	 * if any.
	 */
	/**
	 * 返回持有人已为此事务暂停的资源（如果有）。 
	 * 
	 */
	@Nullable
	public Object getSuspendedResources() {
		return this.suspendedResources;
	}


	//---------------------------------------------------------------------
	// Enable functionality through underlying transaction object
	//---------------------------------------------------------------------

	/**
	 * Determine the rollback-only flag via checking the transaction object, provided
	 * that the latter implements the {@link SmartTransactionObject} interface.
	 * <p>Will return {@code true} if the global transaction itself has been marked
	 * rollback-only by the transaction coordinator, for example in case of a timeout.
	 * @see SmartTransactionObject#isRollbackOnly()
	 */
	/**
	 * 如果事务对象实现了{@link  SmartTransactionObject}接口，则通过检查事务对象来确定仅回滚标志。 
	 *  <p>如果全局事务本身已被事务协调器标记为仅回滚，例如在超时的情况下，将返回{@code  true}。 
	 *  
	 * @see  SmartTransactionObject＃isRollbackOnly（）
	 */
	@Override
	public boolean isGlobalRollbackOnly() {
		return ((this.transaction instanceof SmartTransactionObject) &&
				((SmartTransactionObject) this.transaction).isRollbackOnly());
	}

	/**
	 * This implementation exposes the {@link SavepointManager} interface
	 * of the underlying transaction object, if any.
	 * @throws NestedTransactionNotSupportedException if savepoints are not supported
	 * @see #isTransactionSavepointManager()
	 */
	/**
	 * 此实现公开基础事务对象的{@link  SavepointManager}接口（如果有）。 
	 *  
	 * @throws 如果不支持保存点，则为NestedTransactionNotSupportedException 
	 * @see  #isTransactionSavepointManager（）
	 */
	@Override
	protected SavepointManager getSavepointManager() {
		Object transaction = this.transaction;
		if (!(transaction instanceof SavepointManager)) {
			throw new NestedTransactionNotSupportedException(
					"Transaction object [" + this.transaction + "] does not support savepoints");
		}
		return (SavepointManager) transaction;
	}

	/**
	 * Return whether the underlying transaction implements the {@link SavepointManager}
	 * interface and therefore supports savepoints.
	 * @see #getTransaction()
	 * @see #getSavepointManager()
	 */
	/**
	 * 返回基础事务是否实现{@link  SavepointManager}接口并因此支持保存点。 
	 *  
	 * @see  #getTransaction（）
	 * @see  #getSavepointManager（）
	 */
	public boolean isTransactionSavepointManager() {
		return (this.transaction instanceof SavepointManager);
	}

	/**
	 * Delegate the flushing to the transaction object, provided that the latter
	 * implements the {@link SmartTransactionObject} interface.
	 * @see SmartTransactionObject#flush()
	 */
	/**
	 * 如果事务对象实现了{@link  SmartTransactionObject}接口，则将事务委托给事务对象。 
	 *  
	 * @see  SmartTransactionObject＃flush（）
	 */
	@Override
	public void flush() {
		if (this.transaction instanceof SmartTransactionObject) {
			((SmartTransactionObject) this.transaction).flush();
		}
	}

}
