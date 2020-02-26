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

package org.springframework.dao;

/**
 * Data access exception thrown when a previously failed operation might be able
 * to succeed if the application performs some recovery steps and retries the entire
 * transaction or in the case of a distributed transaction, the transaction branch.
 * At a minimum, the recovery operation must include closing the current connection
 * and getting a new connection.
 *
 * @author Thomas Risberg
 * @since 2.5
 * @see java.sql.SQLRecoverableException
 */
/**
 * 如果应用程序执行一些恢复步骤并重试整个事务，或者在分布式事务的情况下，事务分支，则先前失败的操作可能能够成功时引发的数据访问异常。 
 * 恢复操作至少必须包括关闭当前连接并获得新连接。 
 *  @author  Thomas Risberg @从2.5开始
 * @see  java.sql.SQLRecoverableException
 */
@SuppressWarnings("serial")
public class RecoverableDataAccessException extends DataAccessException {

	/**
	 * Constructor for RecoverableDataAccessException.
	 * @param msg the detail message
	 */
	/**
	 * RecoverableDataAccessException的构造方法。 
	 *  
	 * @param  msg详细信息
	 */
	public RecoverableDataAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for RecoverableDataAccessException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using a underlying
	 * data access API such as JDBC)
	 */
	/**
	 * RecoverableDataAccessException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 引起根本原因（通常是由于使用诸如JDBC之类的基础数据访问API）
	 */
	public RecoverableDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
