/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.transaction;

/**
 * Exception to be thrown when a transaction has timed out.
 *
 * <p>Thrown by Spring's local transaction strategies if the deadline
 * for a transaction has been reached when an operation is attempted,
 * according to the timeout specified for the given transaction.
 *
 * <p>Beyond such checks before each transactional operation, Spring's
 * local transaction strategies will also pass appropriate timeout values
 * to resource operations (for example to JDBC Statements, letting the JDBC
 * driver respect the timeout). Such operations will usually throw native
 * resource exceptions (for example, JDBC SQLExceptions) if their operation
 * timeout has been exceeded, to be converted to Spring's DataAccessException
 * in the respective DAO (which might use Spring's JdbcTemplate, for example).
 *
 * <p>In a JTA environment, it is up to the JTA transaction coordinator
 * to apply transaction timeouts. Usually, the corresponding JTA-aware
 * connection pool will perform timeout checks and throw corresponding
 * native resource exceptions (for example, JDBC SQLExceptions).
 *
 * @author Juergen Hoeller
 * @since 1.1.5
 * @see org.springframework.transaction.support.ResourceHolderSupport#getTimeToLiveInMillis
 * @see java.sql.Statement#setQueryTimeout
 * @see java.sql.SQLException
 */
/**
 * 事务超时时抛出的异常。 
 *  <p>根据给定事务指定的超时时间，如果尝试进行操作时已达到事务的截止日期，则由Spring的本地事务策略抛出。 
 *  <p>除了在每次事务处理操作之前进行此类检查之外，Spring的本地事务处理策略还会将适当的超时值传递给资源操作（例如传递给JDBC语句，让JDBC驱动程序遵守超时）。 
 * 如果超出了操作超时，此类操作通常将抛出本机资源异常（例如JDBC SQLExceptions），并在各自的DAO中将其转换为Spring的DataAccessException（例如，可以使用Spring的JdbcTemplate）。 
 *  <p>在JTA环境中，由JTA事务协调器来应用事务超时。 
 * 通常，相应的JTA感知连接池将执行超时检查并引发相应的本机资源异常（例如JDBC SQLExceptions）。 
 *  @author  Juergen Hoeller @1.1.5起
 * @see  org.springframework.transaction.support.ResourceHolderSupport＃getTimeToLiveInMillis 
 * @see  java.sql.Statement＃setQueryTimeout 
 * @see  java.sql.SQLException
 */
@SuppressWarnings("serial")
public class TransactionTimedOutException extends TransactionException {

	/**
	 * Constructor for TransactionTimedOutException.
	 * @param msg the detail message
	 */
	/**
	 * TransactionTimedOutException的构造方法。 
	 *  
	 * @param  msg详细信息
	 */
	public TransactionTimedOutException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for TransactionTimedOutException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	/**
	 * TransactionTimedOutException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 原因是所使用的事务API的根本原因
	 */
	public TransactionTimedOutException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
