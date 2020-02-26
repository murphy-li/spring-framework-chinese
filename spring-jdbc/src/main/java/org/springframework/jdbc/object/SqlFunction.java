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

package org.springframework.jdbc.object;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.lang.Nullable;

/**
 * SQL "function" wrapper for a query that returns a single row of results.
 * The default behavior is to return an int, but that can be overridden by
 * using the constructor with an extra return type parameter.
 *
 * <p>Intended to use to call SQL functions that return a single result using a
 * query like "select user()" or "select sysdate from dual". It is not intended
 * for calling more complex stored functions or for using a CallableStatement to
 * invoke a stored procedure or stored function. Use StoredProcedure or SqlCall
 * for this type of processing.
 *
 * <p>This is a concrete class, which there is often no need to subclass.
 * Code using this package can create an object of this type, declaring SQL
 * and parameters, and then invoke the appropriate {@code run} method
 * repeatedly to execute the function. Subclasses are only supposed to add
 * specialized {@code run} methods for specific parameter and return types.
 *
 * <p>Like all RdbmsOperation objects, SqlFunction objects are thread-safe.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Jean-Pierre Pawlak
 * @param <T> the result type
 * @see StoredProcedure
 */
/**
 * SQL"函数"包装，用于返回单行结果的查询。 
 * 默认行为是返回一个int，但是可以通过使用带有额外返回类型参数的构造函数来覆盖它。 
 *  <p>旨在用于调用SQL函数，这些函数使用"select user（）"或"从double选择sysdate"之类的查询返回单个结果。 
 * 它不用于调用更复杂的存储函数，也不用于使用CallableStatement调用存储过程或存储函数。 
 * 使用StoredProcedure或SqlCall进行这种类型的处理。 
 *  <p>这是一个具体的类，通常不需要子类。 
 * 使用此程序包的代码可以创建此类型的对象，声明SQL和参数，然后重复调用适当的{@code  run}方法以执行该函数。 
 * 子类仅应为特定的参数和返回类型添加专门的{@code  run}方法。 
 * 与所有RdbmsOperation对象一样，SqlFunction对象也是线程安全的。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Jean-Pierre Pawlak 
 * @param  <T>结果类型
 * @see  StoredProcedure
 */
public class SqlFunction<T> extends MappingSqlQuery<T> {

	private final SingleColumnRowMapper<T> rowMapper = new SingleColumnRowMapper<>();


	/**
	 * Constructor to allow use as a JavaBean.
	 * A DataSource, SQL and any parameters must be supplied before
	 * invoking the {@code compile} method and using this object.
	 * @see #setDataSource
	 * @see #setSql
	 * @see #compile
	 */
	/**
	 * 允许用作JavaBean的构造方法。 
	 * 在调用{@code  compile}方法并使用此对象之前，必须提供DataSource，SQL和任何参数。 
	 *  
	 * @see  #setDataSource 
	 * @see  #setSql 
	 * @see  #compile
	 */
	public SqlFunction() {
		setRowsExpected(1);
	}

	/**
	 * Create a new SqlFunction object with SQL, but without parameters.
	 * Must add parameters or settle with none.
	 * @param ds the DataSource to obtain connections from
	 * @param sql the SQL to execute
	 */
	/**
	 * 使用SQL创建新的SqlFunction对象，但不使用参数。 
	 * 必须添加参数或不设置任何参数。 
	 *  
	 * @param  ds数据源从
	 * @param  sql获取连接以执行SQL
	 */
	public SqlFunction(DataSource ds, String sql) {
		setRowsExpected(1);
		setDataSource(ds);
		setSql(sql);
	}

	/**
	 * Create a new SqlFunction object with SQL and parameters.
	 * @param ds the DataSource to obtain connections from
	 * @param sql the SQL to execute
	 * @param types the SQL types of the parameters, as defined in the
	 * {@code java.sql.Types} class
	 * @see java.sql.Types
	 */
	/**
	 * 使用SQL和参数创建一个新的SqlFunction对象。 
	 *  
	 * @param  ds数据源从
	 * @param  sql获取连接以执行SQL，而在
	 * @param 中键入参数的SQL类型，如{@code  java.sql.Types}类<@查看> java.sql.Types
	 */
	public SqlFunction(DataSource ds, String sql, int[] types) {
		setRowsExpected(1);
		setDataSource(ds);
		setSql(sql);
		setTypes(types);
	}

	/**
	 * Create a new SqlFunction object with SQL, parameters and a result type.
	 * @param ds the DataSource to obtain connections from
	 * @param sql the SQL to execute
	 * @param types the SQL types of the parameters, as defined in the
	 * {@code java.sql.Types} class
	 * @param resultType the type that the result object is required to match
	 * @see #setResultType(Class)
	 * @see java.sql.Types
	 */
	/**
	 * 使用SQL，参数和结果类型创建一个新的SqlFunction对象。 
	 *  
	 * @param  ds数据源从
	 * @param  sql获取连接以执行SQL，而在
	 * @param 中键入参数的SQL类型，如{@code  java.sql.Types}类<
	 * @param> resultType匹配结果对象所需的类型
	 * @see  #setResultType（Class）
	 * @see  java.sql.Types
	 */
	public SqlFunction(DataSource ds, String sql, int[] types, Class<T> resultType) {
		setRowsExpected(1);
		setDataSource(ds);
		setSql(sql);
		setTypes(types);
		setResultType(resultType);
	}


	/**
	 * Specify the type that the result object is required to match.
	 * <p>If not specified, the result value will be exposed as
	 * returned by the JDBC driver.
	 */
	/**
	 * 指定结果对象必须匹配的类型。 
	 *  <p>如果未指定，则结果值将按JDBC驱动程序返回的方式公开。 
	 * 
	 */
	public void setResultType(Class<T> resultType) {
		this.rowMapper.setRequiredType(resultType);
	}


	/**
	 * This implementation of this method extracts a single value from the
	 * single row returned by the function. If there are a different number
	 * of rows returned, this is treated as an error.
	 */
	/**
	 * 此方法的此实现从函数返回的单行中提取单个值。 
	 * 如果返回的行数不同，则将其视为错误。 
	 * 
	 */
	@Override
	@Nullable
	protected T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return this.rowMapper.mapRow(rs, rowNum);
	}


	/**
	 * Convenient method to run the function without arguments.
	 * @return the value of the function
	 */
	/**
	 * 方便的方法来运行不带参数的函数。 
	 *  
	 * @return 函数的值
	 */
	public int run() {
		return run(new Object[0]);
	}

	/**
	 * Convenient method to run the function with a single int argument.
	 * @param parameter single int parameter
	 * @return the value of the function
	 */
	/**
	 * 使用单个int参数运行函数的便捷方法。 
	 *  
	 * @param 参数单int参数
	 * @return 函数的值
	 */
	public int run(int parameter) {
		return run(new Object[] {parameter});
	}

	/**
	 * Analogous to the SqlQuery.execute([]) method. This is a
	 * generic method to execute a query, taken a number of arguments.
	 * @param parameters array of parameters. These will be objects or
	 * object wrapper types for primitives.
	 * @return the value of the function
	 */
	/**
	 * 类似于SqlQuery.execute（[]）方法。 
	 * 这是执行查询的通用方法，它带有多个参数。 
	 *  
	 * @param  parameters参数数组。 
	 * 这些将是基元的对象或对象包装器类型。 
	 *  
	 * @return 函数的值
	 */
	public int run(Object... parameters) {
		Object obj = super.findObject(parameters);
		if (!(obj instanceof Number)) {
			throw new TypeMismatchDataAccessException("Could not convert result object [" + obj + "] to int");
		}
		return ((Number) obj).intValue();
	}

	/**
	 * Convenient method to run the function without arguments,
	 * returning the value as an object.
	 * @return the value of the function
	 */
	/**
	 * 方便的方法来运行不带参数的函数，将值作为对象返回。 
	 *  
	 * @return 函数的值
	 */
	@Nullable
	public Object runGeneric() {
		return findObject((Object[]) null, null);
	}

	/**
	 * Convenient method to run the function with a single int argument.
	 * @param parameter single int parameter
	 * @return the value of the function as an Object
	 */
	/**
	 * 使用单个int参数运行函数的便捷方法。 
	 *  
	 * @param 参数single int参数
	 * @return 函数值作为对象
	 */
	@Nullable
	public Object runGeneric(int parameter) {
		return findObject(parameter);
	}

	/**
	 * Analogous to the {@code SqlQuery.findObject(Object[])} method.
	 * This is a generic method to execute a query, taken a number of arguments.
	 * @param parameters array of parameters. These will be objects or
	 * object wrapper types for primitives.
	 * @return the value of the function, as an Object
	 * @see #execute(Object[])
	 */
	/**
	 * 类似于{@code  SqlQuery.findObject（Object []）}方法。 
	 * 这是执行查询的通用方法，它带有多个参数。 
	 *  
	 * @param  parameters参数数组。 
	 * 这些将是基元的对象或对象包装器类型。 
	 *  
	 * @return 函数的值，作为对象
	 * @see  #execute（Object []）
	 */
	@Nullable
	public Object runGeneric(Object[] parameters) {
		return findObject(parameters);
	}

}
