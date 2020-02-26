/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jdbc.datasource.init;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Strategy used to populate, initialize, or clean up a database.
 *
 * @author Keith Donald
 * @author Sam Brannen
 * @since 3.0
 * @see ResourceDatabasePopulator
 * @see DatabasePopulatorUtils
 * @see DataSourceInitializer
 */
/**
 * 用于填充，初始化或清理数据库的策略。 
 *  @author 基思·唐纳德（Keith Donald）@author  Sam Brannen @since 3.0起
 * @see  ResourceDatabasePopulator 
 * @see  DatabasePopulatorUtils 
 * @see  DataSourceInitializer
 */
@FunctionalInterface
public interface DatabasePopulator {

	/**
	 * Populate, initialize, or clean up the database using the provided JDBC
	 * connection.
	 * <p>Concrete implementations <em>may</em> throw an {@link SQLException} if
	 * an error is encountered but are <em>strongly encouraged</em> to throw a
	 * specific {@link ScriptException} instead. For example, Spring's
	 * {@link ResourceDatabasePopulator} and {@link DatabasePopulatorUtils} wrap
	 * all {@code SQLExceptions} in {@code ScriptExceptions}.
	 * @param connection the JDBC connection to use to populate the db; already
	 * configured and ready to use; never {@code null}
	 * @throws SQLException if an unrecoverable data access exception occurs
	 * during database population
	 * @throws ScriptException in all other error cases
	 * @see DatabasePopulatorUtils#execute
	 */
	/**
	 * 使用提供的JDBC连接填充，初始化或清除数据库。 
	 *  <p>具体实现<em> </ em>可能会在遇到错误时抛出{@link  SQLException}，但强烈建议<em> </ em>抛出特定的{@link  ScriptException} 。 
	 * 例如，Spring的{@link  ResourceDatabasePopulator}和{@link  DatabasePopulatorUtils}将所有{@code  SQLExceptions}包装在{@code  ScriptExceptions}中。 
	 *  
	 * @param 连接JDBC连接用于填充数据库； 
	 * 已经配置并可以使用； 
	 * 如果在所有其他错误情况下，在数据库填充期间发生了不可恢复的数据访问异常，则永远不要使用{@code  null} 
	 * @throws  SQLException 
	 * @throws  ScriptException 
	 * @see  DatabasePopulatorUtils＃execute
	 */
	void populate(Connection connection) throws SQLException, ScriptException;

}
