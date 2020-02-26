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
 * Data access exception thrown when something unintended appears to have
 * happened with an update, but the transaction hasn't already been rolled back.
 * Thrown, for example, when we wanted to update 1 row in an RDBMS but actually
 * updated 3.
 *
 * @author Rod Johnson
 */
/**
 * 如果更新中发生了意外的事情，但尚未回滚该事务，则会引发数据访问异常。 
 * 例如，当我们想要更新RDBMS中的1行但实际上更新3行时抛出。 
 * @author  Rod Johnson
 */
@SuppressWarnings("serial")
public class IncorrectUpdateSemanticsDataAccessException extends InvalidDataAccessResourceUsageException {

	/**
	 * Constructor for IncorrectUpdateSemanticsDataAccessException.
	 * @param msg the detail message
	 */
	/**
	 * IncorrectUpdateSemanticsDataAccessException的构造方法。 
	 *  
	 * @param  msg详细信息
	 */
	public IncorrectUpdateSemanticsDataAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for IncorrectUpdateSemanticsDataAccessException.
	 * @param msg the detail message
	 * @param cause the root cause from the underlying API, such as JDBC
	 */
	/**
	 * IncorrectUpdateSemanticsDataAccessException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 原因是来自底层API（例如JDBC）的根本原因
	 */
	public IncorrectUpdateSemanticsDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Return whether data was updated.
	 * If this method returns false, there's nothing to roll back.
	 * <p>The default implementation always returns true.
	 * This can be overridden in subclasses.
	 */
	/**
	 * 返回数据是否已更新。 
	 * 如果此方法返回false，则没有任何回滚。 
	 *  <p>默认实现始终返回true。 
	 * 可以在子类中覆盖它。 
	 * 
	 */
	public boolean wasDataUpdated() {
		return true;
	}

}
