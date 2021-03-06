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

package org.springframework.jdbc;

import java.sql.SQLException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * Exception thrown when SQL specified is invalid. Such exceptions always have
 * a {@code java.sql.SQLException} root cause.
 *
 * <p>It would be possible to have subclasses for no such table, no such column etc.
 * A custom SQLExceptionTranslator could create such more specific exceptions,
 * without affecting code using this class.
 *
 * @author Rod Johnson
 * @see InvalidResultSetAccessException
 */
/**
 * 指定的SQL无效时引发的异常。 
 * 此类异常始终具有{@code  java.sql.SQLException}根本原因。 
 *  <p>可能没有此类表，此类列等的子类。 
 * 自定义SQLExceptionTranslator可以创建此类更具体的异常，而不会影响使用此类的代码。 
 *  @author 罗德·约翰逊
 * @see  InvalidResultSetAccessException
 */
@SuppressWarnings("serial")
public class BadSqlGrammarException extends InvalidDataAccessResourceUsageException {

	private final String sql;


	/**
	 * Constructor for BadSqlGrammarException.
	 * @param task name of current task
	 * @param sql the offending SQL statement
	 * @param ex the root cause
	 */
	/**
	 * BadSqlGrammarException的构造方法。 
	 *  
	 * @param 当前任务的任务名称
	 * @param  sql引起问题的SQL语句
	 * @param 的根本原因
	 */
	public BadSqlGrammarException(String task, String sql, SQLException ex) {
		super(task + "; bad SQL grammar [" + sql + "]", ex);
		this.sql = sql;
	}


	/**
	 * Return the wrapped SQLException.
	 */
	/**
	 * 返回包装的SQLException。 
	 * 
	 */
	public SQLException getSQLException() {
		return (SQLException) getCause();
	}

	/**
	 * Return the SQL that caused the problem.
	 */
	/**
	 * 返回导致问题的SQL。 
	 * 
	 */
	public String getSql() {
		return this.sql;
	}

}
