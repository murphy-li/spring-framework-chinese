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

package org.springframework.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.springframework.lang.Nullable;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.util.Assert;

/**
 * Resource holder wrapping a JDBC {@link Connection}.
 * {@link DataSourceTransactionManager} binds instances of this class
 * to the thread, for a specific {@link javax.sql.DataSource}.
 *
 * <p>Inherits rollback-only support for nested JDBC transactions
 * and reference count functionality from the base class.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @since 06.05.2003
 * @see DataSourceTransactionManager
 * @see DataSourceUtils
 */
/**
 * 资源持有人包装了JDBC {@link 连接}。 
 * 对于特定的{@link  javax.sql.DataSource}，{<@link> DataSourceTransactionManager}将此类的实例绑定到线程。 
 *  <p>从基类继承对嵌套JDBC事务和引用计数功能的仅回滚支持。 
 *  <p>注意：这是SPI类，不适合应用程序使用。 
 *  @author  Juergen Hoeller @2003年6月5日起
 * @see  DataSourceTransactionManager 
 * @see  DataSourceUtils
 */
public class ConnectionHolder extends ResourceHolderSupport {

	/**
	 * Prefix for savepoint names.
	 */
	/**
	 * 保存点名称的前缀。 
	 * 
	 */
	public static final String SAVEPOINT_NAME_PREFIX = "SAVEPOINT_";


	@Nullable
	private ConnectionHandle connectionHandle;

	@Nullable
	private Connection currentConnection;

	private boolean transactionActive = false;

	@Nullable
	private Boolean savepointsSupported;

	private int savepointCounter = 0;


	/**
	 * Create a new ConnectionHolder for the given ConnectionHandle.
	 * @param connectionHandle the ConnectionHandle to hold
	 */
	/**
	 * 为给定的ConnectionHandle创建一个新的ConnectionHolder。 
	 *  
	 * @param  connectionHandle持有的ConnectionHandle
	 */
	public ConnectionHolder(ConnectionHandle connectionHandle) {
		Assert.notNull(connectionHandle, "ConnectionHandle must not be null");
		this.connectionHandle = connectionHandle;
	}

	/**
	 * Create a new ConnectionHolder for the given JDBC Connection,
	 * wrapping it with a {@link SimpleConnectionHandle},
	 * assuming that there is no ongoing transaction.
	 * @param connection the JDBC Connection to hold
	 * @see SimpleConnectionHandle
	 * @see #ConnectionHolder(java.sql.Connection, boolean)
	 */
	/**
	 * 假定没有正在进行的事务，请为给定的JDBC连接创建一个新的ConnectionHolder，并用{@link  SimpleConnectionHandle}对其进行包装。 
	 *  
	 * @param 连接JDBC连接以容纳
	 * @see  SimpleConnectionHandle 
	 * @see  #ConnectionHolder（java.sql.Connection，boolean）
	 */
	public ConnectionHolder(Connection connection) {
		this.connectionHandle = new SimpleConnectionHandle(connection);
	}

	/**
	 * Create a new ConnectionHolder for the given JDBC Connection,
	 * wrapping it with a {@link SimpleConnectionHandle}.
	 * @param connection the JDBC Connection to hold
	 * @param transactionActive whether the given Connection is involved
	 * in an ongoing transaction
	 * @see SimpleConnectionHandle
	 */
	/**
	 * 为给定的JDBC连接创建一个新的ConnectionHolder，并用{@link  SimpleConnectionHandle}对其进行包装。 
	 *  
	 * @param 连接JDBC连接以保持
	 * @param  transactionActive是否给定的Connection涉及正在进行的事务
	 * @see  SimpleConnectionHandle
	 */
	public ConnectionHolder(Connection connection, boolean transactionActive) {
		this(connection);
		this.transactionActive = transactionActive;
	}


	/**
	 * Return the ConnectionHandle held by this ConnectionHolder.
	 */
	/**
	 * 返回此ConnectionHolder持有的ConnectionHandle。 
	 * 
	 */
	@Nullable
	public ConnectionHandle getConnectionHandle() {
		return this.connectionHandle;
	}

	/**
	 * Return whether this holder currently has a Connection.
	 */
	/**
	 * 返回此持有人当前是否具有连接。 
	 * 
	 */
	protected boolean hasConnection() {
		return (this.connectionHandle != null);
	}

	/**
	 * Set whether this holder represents an active, JDBC-managed transaction.
	 * @see DataSourceTransactionManager
	 */
	/**
	 * 设置此持有人是否代表一个活动的，由JDBC管理的事务。 
	 *  
	 * @see  DataSourceTransactionManager
	 */
	protected void setTransactionActive(boolean transactionActive) {
		this.transactionActive = transactionActive;
	}

	/**
	 * Return whether this holder represents an active, JDBC-managed transaction.
	 */
	/**
	 * 返回此持有人是否代表一个活动的，由JDBC管理的事务。 
	 * 
	 */
	protected boolean isTransactionActive() {
		return this.transactionActive;
	}


	/**
	 * Override the existing Connection handle with the given Connection.
	 * Reset the handle if given {@code null}.
	 * <p>Used for releasing the Connection on suspend (with a {@code null}
	 * argument) and setting a fresh Connection on resume.
	 */
	/**
	 * 用给定的Connection覆盖现有的Connection句柄。 
	 * 如果给定{@code  null}，则重置句柄。 
	 *  <p>用于在挂起时释放Connection（带有{@code  null}参数），并在恢复时设置新的Connection。 
	 * 
	 */
	protected void setConnection(@Nullable Connection connection) {
		if (this.currentConnection != null) {
			if (this.connectionHandle != null) {
				this.connectionHandle.releaseConnection(this.currentConnection);
			}
			this.currentConnection = null;
		}
		if (connection != null) {
			this.connectionHandle = new SimpleConnectionHandle(connection);
		}
		else {
			this.connectionHandle = null;
		}
	}

	/**
	 * Return the current Connection held by this ConnectionHolder.
	 * <p>This will be the same Connection until {@code released}
	 * gets called on the ConnectionHolder, which will reset the
	 * held Connection, fetching a new Connection on demand.
	 * @see ConnectionHandle#getConnection()
	 * @see #released()
	 */
	/**
	 * 返回此ConnectionHolder持有的当前Connection。 
	 *  <p>在ConnectionHolder上调用{@code  released}之前，这将是相同的Connection，它将重置保留的Connection，并按需获取新的Connection。 
	 *  
	 * @see  ConnectionHandle＃getConnection（）
	 * @see  #released（）
	 */
	public Connection getConnection() {
		Assert.notNull(this.connectionHandle, "Active Connection is required");
		if (this.currentConnection == null) {
			this.currentConnection = this.connectionHandle.getConnection();
		}
		return this.currentConnection;
	}

	/**
	 * Return whether JDBC 3.0 Savepoints are supported.
	 * Caches the flag for the lifetime of this ConnectionHolder.
	 * @throws SQLException if thrown by the JDBC driver
	 */
	/**
	 * 返回是否支持JDBC 3.0保存点。 
	 * 在此ConnectionHolder的生存期内缓存该标志。 
	 *  
	 * @throws 如果由JDBC驱动程序抛出，则抛出SQLException
	 */
	public boolean supportsSavepoints() throws SQLException {
		if (this.savepointsSupported == null) {
			this.savepointsSupported = getConnection().getMetaData().supportsSavepoints();
		}
		return this.savepointsSupported;
	}

	/**
	 * Create a new JDBC 3.0 Savepoint for the current Connection,
	 * using generated savepoint names that are unique for the Connection.
	 * @return the new Savepoint
	 * @throws SQLException if thrown by the JDBC driver
	 */
	/**
	 * 使用生成的保存点名称为该连接唯一，为当前连接创建一个新的JDBC 3.0保存点。 
	 *  
	 * @return 新的保存点
	 * @throws  SQLException（如果由JDBC驱动程序抛出）
	 */
	public Savepoint createSavepoint() throws SQLException {
		this.savepointCounter++;
		return getConnection().setSavepoint(SAVEPOINT_NAME_PREFIX + this.savepointCounter);
	}

	/**
	 * Releases the current Connection held by this ConnectionHolder.
	 * <p>This is necessary for ConnectionHandles that expect "Connection borrowing",
	 * where each returned Connection is only temporarily leased and needs to be
	 * returned once the data operation is done, to make the Connection available
	 * for other operations within the same transaction.
	 */
	/**
	 * 释放此ConnectionHolder持有的当前Connection。 
	 *  <p>这对于期望"连接借用"的ConnectionHandles是必需的，其中每个返回的Connection仅被临时租用，并且一旦完成数据操作就需要将其返回，以使Connection可用于同一事务内的其他操作。 
	 * 
	 */
	@Override
	public void released() {
		super.released();
		if (!isOpen() && this.currentConnection != null) {
			if (this.connectionHandle != null) {
				this.connectionHandle.releaseConnection(this.currentConnection);
			}
			this.currentConnection = null;
		}
	}


	@Override
	public void clear() {
		super.clear();
		this.transactionActive = false;
		this.savepointsSupported = null;
		this.savepointCounter = 0;
	}

}
