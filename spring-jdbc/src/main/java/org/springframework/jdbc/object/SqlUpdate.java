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

package org.springframework.jdbc.object;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Reusable operation object representing an SQL update.
 *
 * <p>This class provides a number of {@code update} methods,
 * analogous to the {@code execute} methods of query objects.
 *
 * <p>This class is concrete. Although it can be subclassed (for example
 * to add a custom update method) it can easily be parameterized by setting
 * SQL and declaring parameters.
 *
 * <p>Like all {@code RdbmsOperation} classes that ship with the Spring
 * Framework, {@code SqlQuery} instances are thread-safe after their
 * initialization is complete. That is, after they are constructed and configured
 * via their setter methods, they can be used safely from multiple threads.
 *
 * @author Rod Johnson
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @see SqlQuery
 */
/**
 * 表示SQL更新的可重用操作对象。 
 *  <p>此类提供了许多{@code  update}方法，类似于查询对象的{@code  execute}方法。 
 *  <p>此类是具体的。 
 * 尽管可以将其子类化（例如，添加自定义更新方法），但可以通过设置SQL和声明参数来轻松地对其进行参数化。 
 *  <p>就像Spring框架附带的所有{@code  RdbmsOperation}类一样，{<@code> SqlQuery}实例在初始化完成后也是线程安全的。 
 * 也就是说，在通过其setter方法构造和配置它们之后，可以从多个线程中安全地使用它们。 
 *  @author  Rod Johnson @author  Thomas Risberg @author  Juergen Hoeller 
 * @see  SqlQuery
 */
public class SqlUpdate extends SqlOperation {

	/**
	 * Maximum number of rows the update may affect. If more are
	 * affected, an exception will be thrown. Ignored if 0.
	 */
	/**
	 * 更新可能影响的最大行数。 
	 * 如果更多受影响，则将引发异常。 
	 * 如果为0，则忽略。 
	 * 
	 */
	private int maxRowsAffected = 0;

	/**
	 * An exact number of rows that must be affected.
	 * Ignored if 0.
	 */
	/**
	 * 必须受影响的确切行数。 
	 * 如果为0，则忽略。 
	 * 
	 */
	private int requiredRowsAffected = 0;


	/**
	 * Constructor to allow use as a JavaBean. DataSource and SQL
	 * must be supplied before compilation and use.
	 * @see #setDataSource
	 * @see #setSql
	 */
	/**
	 * 允许用作JavaBean的构造方法。 
	 * 在编译和使用之前必须提供DataSource和SQL。 
	 *  
	 * @see  #setDataSource 
	 * @see  #setSql
	 */
	public SqlUpdate() {
	}

	/**
	 * Constructs an update object with a given DataSource and SQL.
	 * @param ds the DataSource to use to obtain connections
	 * @param sql the SQL statement to execute
	 */
	/**
	 * 使用给定的DataSource和SQL构造一个更新对象。 
	 *  
	 * @param  ds用来获取连接的数据源
	 * @param  sql要执行的SQL语句
	 */
	public SqlUpdate(DataSource ds, String sql) {
		setDataSource(ds);
		setSql(sql);
	}

	/**
	 * Construct an update object with a given DataSource, SQL
	 * and anonymous parameters.
	 * @param ds the DataSource to use to obtain connections
	 * @param sql the SQL statement to execute
	 * @param types the SQL types of the parameters, as defined in the
	 * {@code java.sql.Types} class
	 * @see java.sql.Types
	 */
	/**
	 * 使用给定的数据源，SQL和匿名参数构造一个更新对象。 
	 *  
	 * @param  ds数据源用于获取连接
	 * @param  sql执行SQL语句的
	 * @param 类型参数的SQL类型，如{@code  java.sql.Types}类中所定义
	 * @see  java.sql.Types
	 */
	public SqlUpdate(DataSource ds, String sql, int[] types) {
		setDataSource(ds);
		setSql(sql);
		setTypes(types);
	}

	/**
	 * Construct an update object with a given DataSource, SQL,
	 * anonymous parameters and specifying the maximum number of rows
	 * that may be affected.
	 * @param ds the DataSource to use to obtain connections
	 * @param sql the SQL statement to execute
	 * @param types the SQL types of the parameters, as defined in the
	 * {@code java.sql.Types} class
	 * @param maxRowsAffected the maximum number of rows that may
	 * be affected by the update
	 * @see java.sql.Types
	 */
	/**
	 * 使用给定的数据源，SQL，匿名参数并指定可能受影响的最大行数来构造更新对象。 
	 *  
	 * @param  ds数据源用于获取连接
	 * @param  sql执行SQL语句的
	 * @param 类型参数的SQL类型，如{@code  java.sql.Types}类中所定义
	 * @param  maxRows受影响的可能受更新影响的最大行数
	 * @see  java.sql.Types
	 */
	public SqlUpdate(DataSource ds, String sql, int[] types, int maxRowsAffected) {
		setDataSource(ds);
		setSql(sql);
		setTypes(types);
		this.maxRowsAffected = maxRowsAffected;
	}


	/**
	 * Set the maximum number of rows that may be affected by this update.
	 * The default value is 0, which does not limit the number of rows affected.
	 * @param maxRowsAffected the maximum number of rows that can be affected by
	 * this update without this class's update method considering it an error
	 */
	/**
	 * 设置此更新可能影响的最大行数。 
	 * 默认值为0，它不限制受影响的行数。 
	 *  
	 * @param  maxRows在没有此类的更新方法将其视为错误的情况下，受此更新影响的最大行数
	 */
	public void setMaxRowsAffected(int maxRowsAffected) {
		this.maxRowsAffected = maxRowsAffected;
	}

	/**
	 * Set the <i>exact</i> number of rows that must be affected by this update.
	 * The default value is 0, which allows any number of rows to be affected.
	 * <p>This is an alternative to setting the <i>maximum</i> number of rows
	 * that may be affected.
	 * @param requiredRowsAffected the exact number of rows that must be affected
	 * by this update without this class's update method considering it an error
	 */
	/**
	 * 设置此更新必须影响的<i>确切</ i>行数。 
	 * 默认值为0，它允许任何数量的行受到影响。 
	 *  <p>这是设置<i>最大</ i>可能受影响的行数的替代方法。 
	 *  
	 * @param  requiredRows受影响的此更新必须影响的确切行数，而无需此类的update方法将其视为错误
	 */
	public void setRequiredRowsAffected(int requiredRowsAffected) {
		this.requiredRowsAffected = requiredRowsAffected;
	}

	/**
	 * Check the given number of affected rows against the
	 * specified maximum number or required number.
	 * @param rowsAffected the number of affected rows
	 * @throws JdbcUpdateAffectedIncorrectNumberOfRowsException
	 * if the actually affected rows are out of bounds
	 * @see #setMaxRowsAffected
	 * @see #setRequiredRowsAffected
	 */
	/**
	 * 将给定的受影响的行数与指定的最大数量或所需数量进行核对。 
	 *  
	 * @param  rowsAffected受影响的行数
	 * @throws 如果实际受影响的行超出范围，则JdbcUpdateAffectedIncorrectNumberOfRowsException 
	 * @see  #setMaxRowsAffected 
	 * @see  #setRequiredRowsAffected
	 */
	protected void checkRowsAffected(int rowsAffected) throws JdbcUpdateAffectedIncorrectNumberOfRowsException {
		if (this.maxRowsAffected > 0 && rowsAffected > this.maxRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(resolveSql(), this.maxRowsAffected, rowsAffected);
		}
		if (this.requiredRowsAffected > 0 && rowsAffected != this.requiredRowsAffected) {
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(resolveSql(), this.requiredRowsAffected, rowsAffected);
		}
	}


	/**
	 * Generic method to execute the update given parameters.
	 * All other update methods invoke this method.
	 * @param params array of parameters objects
	 * @return the number of rows affected by the update
	 */
	/**
	 * 执行给定参数更新的通用方法。 
	 * 所有其他更新方法都调用此方法。 
	 *  
	 * @param 参数对象的params数组
	 * @return 受更新影响的行数
	 */
	public int update(Object... params) throws DataAccessException {
		validateParameters(params);
		int rowsAffected = getJdbcTemplate().update(newPreparedStatementCreator(params));
		checkRowsAffected(rowsAffected);
		return rowsAffected;
	}

	/**
	 * Method to execute the update given arguments and
	 * retrieve the generated keys using a KeyHolder.
	 * @param params array of parameter objects
	 * @param generatedKeyHolder the KeyHolder that will hold the generated keys
	 * @return the number of rows affected by the update
	 */
	/**
	 * 执行给定参数更新并使用KeyHolder检索生成的键的方法。 
	 *  
	 * @param 参数对象的params数组
	 * @param  generateKeyHolder将保存生成的键的KeyHolder 
	 * @return 受更新影响的行数
	 */
	public int update(Object[] params, KeyHolder generatedKeyHolder) throws DataAccessException {
		if (!isReturnGeneratedKeys() && getGeneratedKeysColumnNames() == null) {
			throw new InvalidDataAccessApiUsageException(
					"The update method taking a KeyHolder should only be used when generated keys have " +
					"been configured by calling either 'setReturnGeneratedKeys' or " +
					"'setGeneratedKeysColumnNames'.");
		}
		validateParameters(params);
		int rowsAffected = getJdbcTemplate().update(newPreparedStatementCreator(params), generatedKeyHolder);
		checkRowsAffected(rowsAffected);
		return rowsAffected;
	}

	/**
	 * Convenience method to execute an update with no parameters.
	 */
	/**
	 * 不带参数执行更新的便捷方法。 
	 * 
	 */
	public int update() throws DataAccessException {
		return update(new Object[0]);
	}

	/**
	 * Convenient method to execute an update given one int arg.
	 */
	/**
	 * 给定一个int arg的便捷方法来执行更新。 
	 * 
	 */
	public int update(int p1) throws DataAccessException {
		return update(new Object[] {p1});
	}

	/**
	 * Convenient method to execute an update given two int args.
	 */
	/**
	 * 给定两个int args的便捷方法来执行更新。 
	 * 
	 */
	public int update(int p1, int p2) throws DataAccessException {
		return update(new Object[] {p1, p2});
	}

	/**
	 * Convenient method to execute an update given one long arg.
	 */
	/**
	 * 给定一个长参数，执行更新的便捷方法。 
	 * 
	 */
	public int update(long p1) throws DataAccessException {
		return update(new Object[] {p1});
	}

	/**
	 * Convenient method to execute an update given two long args.
	 */
	/**
	 * 给定两个长参数，执行更新的便捷方法。 
	 * 
	 */
	public int update(long p1, long p2) throws DataAccessException {
		return update(new Object[] {p1, p2});
	}

	/**
	 * Convenient method to execute an update given one String arg.
	 */
	/**
	 * 给定一个String arg即可执行更新的便捷方法。 
	 * 
	 */
	public int update(String p) throws DataAccessException {
		return update(new Object[] {p});
	}

	/**
	 * Convenient method to execute an update given two String args.
	 */
	/**
	 * 给定两个String参数，执行更新的便捷方法。 
	 * 
	 */
	public int update(String p1, String p2) throws DataAccessException {
		return update(new Object[] {p1, p2});
	}

	/**
	 * Generic method to execute the update given named parameters.
	 * All other update methods invoke this method.
	 * @param paramMap a Map of parameter name to parameter object,
	 * matching named parameters specified in the SQL statement
	 * @return the number of rows affected by the update
	 */
	/**
	 * 给定命名参数执行更新的通用方法。 
	 * 所有其他更新方法都调用此方法。 
	 *  
	 * @param  paramMap参数名称到参数对象的映射，匹配SQL语句中指定的命名参数
	 * @return 更新影响的行数
	 */
	public int updateByNamedParam(Map<String, ?> paramMap) throws DataAccessException {
		validateNamedParameters(paramMap);
		ParsedSql parsedSql = getParsedSql();
		MapSqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, getDeclaredParameters());
		int rowsAffected = getJdbcTemplate().update(newPreparedStatementCreator(sqlToUse, params));
		checkRowsAffected(rowsAffected);
		return rowsAffected;
	}

	/**
	 * Method to execute the update given arguments and
	 * retrieve the generated keys using a KeyHolder.
	 * @param paramMap a Map of parameter name to parameter object,
	 * matching named parameters specified in the SQL statement
	 * @param generatedKeyHolder the KeyHolder that will hold the generated keys
	 * @return the number of rows affected by the update
	 */
	/**
	 * 执行给定参数更新并使用KeyHolder检索生成的键的方法。 
	 *  
	 * @param  paramMap参数名称到参数对象的映射，匹配SQL语句中指定的命名参数
	 * @param  generateKeyHolder将保存生成的键的KeyHolder 
	 * @return 受更新影响的行数
	 */
	public int updateByNamedParam(Map<String, ?> paramMap, KeyHolder generatedKeyHolder) throws DataAccessException {
		validateNamedParameters(paramMap);
		ParsedSql parsedSql = getParsedSql();
		MapSqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, getDeclaredParameters());
		int rowsAffected = getJdbcTemplate().update(newPreparedStatementCreator(sqlToUse, params), generatedKeyHolder);
		checkRowsAffected(rowsAffected);
		return rowsAffected;
	}

}
