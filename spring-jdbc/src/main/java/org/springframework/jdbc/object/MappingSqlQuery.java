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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.lang.Nullable;

/**
 * Reusable query in which concrete subclasses must implement the abstract
 * mapRow(ResultSet, int) method to convert each row of the JDBC ResultSet
 * into an object.
 *
 * <p>Simplifies MappingSqlQueryWithParameters API by dropping parameters and
 * context. Most subclasses won't care about parameters. If you don't use
 * contextual information, subclass this instead of MappingSqlQueryWithParameters.
 *
 * @author Rod Johnson
 * @author Thomas Risberg
 * @author Jean-Pierre Pawlak
 * @param <T> the result type
 * @see MappingSqlQueryWithParameters
 */
/**
 * 可重用的查询，其中具体子类必须实现抽象的mapRow（ResultSet，int）方法，以将JDBC ResultSet的每一行转换为一个对象。 
 *  <p>通过删除参数和上下文来简化MappingSqlQueryWithParameters API。 
 * 大多数子类都不关心参数。 
 * 如果不使用上下文信息，则将其子类化，而不是MappingSqlQueryWithParameters。 
 *  @author  Rod Johnson @author  Thomas Risberg @author  Jean-Pierre Pawlak 
 * @param  <T>结果类型
 * @see  MappingSqlQueryWithParameters
 */
public abstract class MappingSqlQuery<T> extends MappingSqlQueryWithParameters<T> {

	/**
	 * Constructor that allows use as a JavaBean.
	 */
	/**
	 * 允许用作JavaBean的构造方法。 
	 * 
	 */
	public MappingSqlQuery() {
	}

	/**
	 * Convenient constructor with DataSource and SQL string.
	 * @param ds the DataSource to use to obtain connections
	 * @param sql the SQL to run
	 */
	/**
	 * 带有DataSource和SQL字符串的便捷构造函数。 
	 *  
	 * @param  ds数据源用于获取连接
	 * @param  sql要运行的SQL
	 */
	public MappingSqlQuery(DataSource ds, String sql) {
		super(ds, sql);
	}


	/**
	 * This method is implemented to invoke the simpler mapRow
	 * template method, ignoring parameters.
	 * @see #mapRow(ResultSet, int)
	 */
	/**
	 * 实现此方法以调用更简单的mapRow模板方法，而忽略参数。 
	 *  
	 * @see  #mapRow（ResultSet，int）
	 */
	@Override
	@Nullable
	protected final T mapRow(ResultSet rs, int rowNum, @Nullable Object[] parameters, @Nullable Map<?, ?> context)
			throws SQLException {

		return mapRow(rs, rowNum);
	}

	/**
	 * Subclasses must implement this method to convert each row of the
	 * ResultSet into an object of the result type.
	 * <p>Subclasses of this class, as opposed to direct subclasses of
	 * MappingSqlQueryWithParameters, don't need to concern themselves
	 * with the parameters to the execute method of the query object.
	 * @param rs the ResultSet we're working through
	 * @param rowNum row number (from 0) we're up to
	 * @return an object of the result type
	 * @throws SQLException if there's an error extracting data.
	 * Subclasses can simply not catch SQLExceptions, relying on the
	 * framework to clean up.
	 */
	/**
	 * 子类必须实现此方法，才能将ResultSet的每一行转换为结果类型的对象。 
	 * 与MappingSqlQueryWithParameters的直接子类相反，此类的子类不需要与查询对象的execute方法的参数有关。 
	 *  
	 * @param 是我们正在处理的ResultSet，通过
	 * @param  rowNum行号（从0开始），直到提取数据时出错，我们最多将
	 * @return 一个结果类型为
	 * @throws  SQLException的对象。 
	 * 子类不能仅仅依靠框架来捕获SQLExceptions。 
	 * 
	 */
	@Nullable
	protected abstract T mapRow(ResultSet rs, int rowNum) throws SQLException;

}
