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

package org.springframework.jdbc.datasource;

import java.sql.SQLException;
import java.sql.Savepoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.NestedTransactionNotSupportedException;
import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.util.Assert;

/**
 * Convenient base class for JDBC-aware transaction objects. Can contain a
 * {@link ConnectionHolder} with a JDBC {@code Connection}, and implements the
 * {@link SavepointManager} interface based on that {@code ConnectionHolder}.
 *
 * <p>Allows for programmatic management of JDBC {@link java.sql.Savepoint Savepoints}.
 * Spring's {@link org.springframework.transaction.support.DefaultTransactionStatus}
 * automatically delegates to this, as it autodetects transaction objects which
 * implement the {@link SavepointManager} interface.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see DataSourceTransactionManager
 */
/**
 * JDBC感知的事务对象的便捷基类。 
 * 可以包含带有JDBC {@code  Connection}的{@link  ConnectionHolder}，并基于该{@code  ConnectionHolder}实现{@link  SavepointManager}接口。 
 *  <p>允许对JDBC {@link  java.sql.Savepoint Savepoints}进行程序管理。 
 *  Spring的{@link  org.springframework.transaction.support.DefaultTransactionStatus}自动委派给它，因为它会自动检测实现{@link  SavepointManager}接口的事务对象。 
 *  @author  Juergen Hoeller @始于1.1 
 * @see  DataSourceTransactionManager
 */
public abstract class JdbcTransactionObjectSupport implements SavepointManager, SmartTransactionObject {

	private static final Log logger = LogFactory.getLog(JdbcTransactionObjectSupport.class);


	@Nullable
	private ConnectionHolder connectionHolder;

	@Nullable
	private Integer previousIsolationLevel;

	private boolean readOnly = false;

	private boolean savepointAllowed = false;


	/**
	 * Set the ConnectionHolder for this transaction object.
	 */
	/**
	 * 设置此事务对象的ConnectionHolder。 
	 * 
	 */
	public void setConnectionHolder(@Nullable ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	/**
	 * Return the ConnectionHolder for this transaction object.
	 */
	/**
	 * 返回此事务对象的ConnectionHolder。 
	 * 
	 */
	public ConnectionHolder getConnectionHolder() {
		Assert.state(this.connectionHolder != null, "No ConnectionHolder available");
		return this.connectionHolder;
	}

	/**
	 * Check whether this transaction object has a ConnectionHolder.
	 */
	/**
	 * 检查此事务对象是否具有ConnectionHolder。 
	 * 
	 */
	public boolean hasConnectionHolder() {
		return (this.connectionHolder != null);
	}

	/**
	 * Set the previous isolation level to retain, if any.
	 */
	/**
	 * 设置先前的隔离级别以保留（如果有）。 
	 * 
	 */
	public void setPreviousIsolationLevel(@Nullable Integer previousIsolationLevel) {
		this.previousIsolationLevel = previousIsolationLevel;
	}

	/**
	 * Return the retained previous isolation level, if any.
	 */
	/**
	 * 返回保留的先前隔离级别（如果有）。 
	 * 
	 */
	@Nullable
	public Integer getPreviousIsolationLevel() {
		return this.previousIsolationLevel;
	}

	/**
	 * Set the read-only status of this transaction.
	 * The default is {@code false}.
	 * @since 5.2.1
	 */
	/**
	 * 设置此事务的只读状态。 
	 * 默认值为{@code  false}。 
	 *  @5.2.1起
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Return the read-only status of this transaction.
	 * @since 5.2.1
	 */
	/**
	 * 返回此事务的只读状态。 
	 *  @5.2.1起
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * Set whether savepoints are allowed within this transaction.
	 * The default is {@code false}.
	 */
	/**
	 * 设置是否在此事务中允许保存点。 
	 * 默认值为{@code  false}。 
	 * 
	 */
	public void setSavepointAllowed(boolean savepointAllowed) {
		this.savepointAllowed = savepointAllowed;
	}

	/**
	 * Return whether savepoints are allowed within this transaction.
	 */
	/**
	 * 返回是否在此事务中允许保存点。 
	 * 
	 */
	public boolean isSavepointAllowed() {
		return this.savepointAllowed;
	}

	@Override
	public void flush() {
		// no-op
	}


	//---------------------------------------------------------------------
	// Implementation of SavepointManager
	//---------------------------------------------------------------------

	/**
	 * This implementation creates a JDBC 3.0 Savepoint and returns it.
	 * @see java.sql.Connection#setSavepoint
	 */
	/**
	 * 此实现创建一个JDBC 3.0保存点并返回它。 
	 *  
	 * @see  java.sql.Connection＃setSavepoint
	 */
	@Override
	public Object createSavepoint() throws TransactionException {
		ConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			if (!conHolder.supportsSavepoints()) {
				throw new NestedTransactionNotSupportedException(
						"Cannot create a nested transaction because savepoints are not supported by your JDBC driver");
			}
			if (conHolder.isRollbackOnly()) {
				throw new CannotCreateTransactionException(
						"Cannot create savepoint for transaction which is already marked as rollback-only");
			}
			return conHolder.createSavepoint();
		}
		catch (SQLException ex) {
			throw new CannotCreateTransactionException("Could not create JDBC savepoint", ex);
		}
	}

	/**
	 * This implementation rolls back to the given JDBC 3.0 Savepoint.
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	/**
	 * 此实现回滚到给定的JDBC 3.0保存点。 
	 *  
	 * @see  java.sql.Connection＃rollback（java.sql.Savepoint）
	 */
	@Override
	public void rollbackToSavepoint(Object savepoint) throws TransactionException {
		ConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			conHolder.getConnection().rollback((Savepoint) savepoint);
			conHolder.resetRollbackOnly();
		}
		catch (Throwable ex) {
			throw new TransactionSystemException("Could not roll back to JDBC savepoint", ex);
		}
	}

	/**
	 * This implementation releases the given JDBC 3.0 Savepoint.
	 * @see java.sql.Connection#releaseSavepoint
	 */
	/**
	 * 此实现释放给定的JDBC 3.0保存点。 
	 *  
	 * @see  java.sql.Connection＃releaseSavepoint
	 */
	@Override
	public void releaseSavepoint(Object savepoint) throws TransactionException {
		ConnectionHolder conHolder = getConnectionHolderForSavepoint();
		try {
			conHolder.getConnection().releaseSavepoint((Savepoint) savepoint);
		}
		catch (Throwable ex) {
			logger.debug("Could not explicitly release JDBC savepoint", ex);
		}
	}

	protected ConnectionHolder getConnectionHolderForSavepoint() throws TransactionException {
		if (!isSavepointAllowed()) {
			throw new NestedTransactionNotSupportedException(
					"Transaction manager does not allow nested transactions");
		}
		if (!hasConnectionHolder()) {
			throw new TransactionUsageException(
					"Cannot create nested transaction when not exposing a JDBC transaction");
		}
		return getConnectionHolder();
	}

}
