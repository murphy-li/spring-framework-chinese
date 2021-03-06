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

package org.springframework.jdbc.core.simple;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * A SimpleJdbcCall is a multi-threaded, reusable object representing a call
 * to a stored procedure or a stored function. It provides meta-data processing
 * to simplify the code needed to access basic stored procedures/functions.
 * All you need to provide is the name of the procedure/function and a Map
 * containing the parameters when you execute the call. The names of the
 * supplied parameters will be matched up with in and out parameters declared
 * when the stored procedure was created.
 *
 * <p>The meta-data processing is based on the DatabaseMetaData provided by
 * the JDBC driver. Since we rely on the JDBC driver, this "auto-detection"
 * can only be used for databases that are known to provide accurate meta-data.
 * These currently include Derby, MySQL, Microsoft SQL Server, Oracle, DB2,
 * Sybase and PostgreSQL. For any other databases you are required to declare
 * all parameters explicitly. You can of course declare all parameters
 * explicitly even if the database provides the necessary meta-data. In that
 * case your declared parameters will take precedence. You can also turn off
 * any meta-data processing if you want to use parameter names that do not
 * match what is declared during the stored procedure compilation.
 *
 * <p>The actual insert is being handled using Spring's {@link JdbcTemplate}.
 *
 * <p>Many of the configuration methods return the current instance of the
 * SimpleJdbcCall in order to provide the ability to chain multiple ones
 * together in a "fluent" interface style.
 *
 * @author Thomas Risberg
 * @author Stephane Nicoll
 * @since 2.5
 * @see java.sql.DatabaseMetaData
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
/**
 * SimpleJdbcCall是一个多线程可重用对象，表示对存储过程或存储函数的调用。 
 * 它提供元数据处理，以简化访问基本存储过程/功能所需的代码。 
 * 您只需要提供过程/函数的名称以及执行调用时包含参数的Map。 
 * 提供的参数名称将与创建存储过程时声明的in和out参数匹配。 
 *  <p>元数据处理基于JDBC驱动程序提供的DatabaseMetaData。 
 * 由于我们依赖JDBC驱动程序，因此这种"自动检测"只能用于已知提供准确的元数据的数据库。 
 * 这些当前包括Derby，MySQL，Microsoft SQL Server，Oracle，DB2，Sybase和PostgreSQL。 
 * 对于任何其他数据库，都需要显式声明所有参数。 
 * 当然，即使数据库提供了必要的元数据，您也可以显式声明所有参数。 
 * 在这种情况下，您声明的参数将优先。 
 * 如果要使用与存储过程编译期间声明的名称不匹配的参数名称，则也可以关闭任何元数据处理。 
 *  <p>实际插入使用Spring的{@link  JdbcTemplate}处理。 
 *  <p>许多配置方法返回SimpleJdbcCall的当前实例，以便能够以"流畅"的界面样式将多个链接在一起。 
 *  @author 托马斯·里斯堡（Thomas Risberg）@author  Stephane Nicoll @since 2.5起
 * @see  java.sql.DatabaseMetaData 
 * @see  org.springframework.jdbc.core.JdbcTemplate
 */
public class SimpleJdbcCall extends AbstractJdbcCall implements SimpleJdbcCallOperations {

	/**
	 * Constructor that takes one parameter with the JDBC DataSource to use when
	 * creating the underlying JdbcTemplate.
	 * @param dataSource the {@code DataSource} to use
	 * @see org.springframework.jdbc.core.JdbcTemplate#setDataSource
	 */
	/**
	 * 创建基础JdbcTemplate时使用带有JDBC数据源一个参数的构造方法。 
	 *  
	 * @param  dataSource {{@@code> DataSource}以使用
	 * @see  org.springframework.jdbc.core.JdbcTemplate＃setDataSource
	 */
	public SimpleJdbcCall(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Alternative Constructor that takes one parameter with the JdbcTemplate to be used.
	 * @param jdbcTemplate the {@code JdbcTemplate} to use
	 * @see org.springframework.jdbc.core.JdbcTemplate#setDataSource
	 */
	/**
	 * 在JdbcTemplate中使用一个参数的替代构造方法。 
	 *  
	 * @param  jdbcTemplate {{@@code> JdbcTemplate}以使用
	 * @see  org.springframework.jdbc.core.JdbcTemplate＃setDataSource
	 */
	public SimpleJdbcCall(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}


	@Override
	public SimpleJdbcCall withProcedureName(String procedureName) {
		setProcedureName(procedureName);
		setFunction(false);
		return this;
	}

	@Override
	public SimpleJdbcCall withFunctionName(String functionName) {
		setProcedureName(functionName);
		setFunction(true);
		return this;
	}

	@Override
	public SimpleJdbcCall withSchemaName(String schemaName) {
		setSchemaName(schemaName);
		return this;
	}

	@Override
	public SimpleJdbcCall withCatalogName(String catalogName) {
		setCatalogName(catalogName);
		return this;
	}

	@Override
	public SimpleJdbcCall withReturnValue() {
		setReturnValueRequired(true);
		return this;
	}

	@Override
	public SimpleJdbcCall declareParameters(SqlParameter... sqlParameters) {
		for (SqlParameter sqlParameter : sqlParameters) {
			if (sqlParameter != null) {
				addDeclaredParameter(sqlParameter);
			}
		}
		return this;
	}

	@Override
	public SimpleJdbcCall useInParameterNames(String... inParameterNames) {
		setInParameterNames(new LinkedHashSet<>(Arrays.asList(inParameterNames)));
		return this;
	}

	@Override
	public SimpleJdbcCall returningResultSet(String parameterName, RowMapper<?> rowMapper) {
		addDeclaredRowMapper(parameterName, rowMapper);
		return this;
	}

	@Override
	public SimpleJdbcCall withoutProcedureColumnMetaDataAccess() {
		setAccessCallParameterMetaData(false);
		return this;
	}

	@Override
	public SimpleJdbcCall withNamedBinding() {
		setNamedBinding(true);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeFunction(Class<T> returnType, Object... args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeFunction(Class<T> returnType, Map<String, ?> args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeFunction(Class<T> returnType, SqlParameterSource args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeObject(Class<T> returnType, Object... args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeObject(Class<T> returnType, Map<String, ?> args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T executeObject(Class<T> returnType, SqlParameterSource args) {
		return (T) doExecute(args).get(getScalarOutParameterName());
	}

	@Override
	public Map<String, Object> execute(Object... args) {
		return doExecute(args);
	}

	@Override
	public Map<String, Object> execute(Map<String, ?> args) {
		return doExecute(args);
	}

	@Override
	public Map<String, Object> execute(SqlParameterSource parameterSource) {
		return doExecute(parameterSource);
	}

}
