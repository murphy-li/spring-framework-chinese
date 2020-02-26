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

package org.springframework.jdbc.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * One of the three central callback interfaces used by the JdbcTemplate class.
 * This interface creates a CallableStatement given a connection, provided
 * by the JdbcTemplate class. Implementations are responsible for providing
 * SQL and any necessary parameters.
 *
 * <p>Implementations <i>do not</i> need to concern themselves with
 * SQLExceptions that may be thrown from operations they attempt.
 * The JdbcTemplate class will catch and handle SQLExceptions appropriately.
 *
 * <p>A PreparedStatementCreator should also implement the SqlProvider interface
 * if it is able to provide the SQL it uses for PreparedStatement creation.
 * This allows for better contextual information in case of exceptions.
 *
 * @author Rod Johnson
 * @author Thomas Risberg
 * @see JdbcTemplate#execute(CallableStatementCreator, CallableStatementCallback)
 * @see JdbcTemplate#call
 * @see SqlProvider
 */
/**
 * JdbcTemplate类使用的三个中央回调接口之一。 
 * 该接口在给定连接的情况下创建由JdbcTemplate类提供的CallableStatement。 
 * 实现负责提供SQL和任何必要的参数。 
 *  <p>实现<i>不需要</ i>担心自己尝试执行的操作可能抛出的SQLException。 
 *  JdbcTemplate类将适当地捕获和处理SQLException。 
 *  <p> PreparedStatementCreator也应实现SqlProvider接口，前提是它能够提供用于PreparedStatement创建的SQL。 
 * 在例外情况下，这可以提供更好的上下文信息。 
 *  @author  Rod Johnson @author  Thomas Risberg 
 * @see  JdbcTemplate＃execute（CallableStatementCreator，CallableStatementCallback）
 * @see  JdbcTemplate＃call 
 * @see  SqlProvider
 */
@FunctionalInterface
public interface CallableStatementCreator {

	/**
	 * Create a callable statement in this connection. Allows implementations to use
	 * CallableStatements.
	 * @param con the Connection to use to create statement
	 * @return a callable statement
	 * @throws SQLException there is no need to catch SQLExceptions
	 * that may be thrown in the implementation of this method.
	 * The JdbcTemplate class will handle them.
	 */
	/**
	 * 在此连接中创建一个可调用的语句。 
	 * 允许实现使用CallableStatements。 
	 *  
	 * @param  con用于创建语句的连接
	 * @return 可调用语句
	 * @throws  SQLException不需要捕获在此方法的实现中可能抛出的SQLException。 
	 *  JdbcTemplate类将处理它们。 
	 * 
	 */
	CallableStatement createCallableStatement(Connection con) throws SQLException;

}
