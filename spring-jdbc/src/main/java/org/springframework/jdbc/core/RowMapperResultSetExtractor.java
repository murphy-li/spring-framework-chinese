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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Adapter implementation of the ResultSetExtractor interface that delegates
 * to a RowMapper which is supposed to create an object for each row.
 * Each object is added to the results List of this ResultSetExtractor.
 *
 * <p>Useful for the typical case of one object per row in the database table.
 * The number of entries in the results list will match the number of rows.
 *
 * <p>Note that a RowMapper object is typically stateless and thus reusable;
 * just the RowMapperResultSetExtractor adapter is stateful.
 *
 * <p>A usage example with JdbcTemplate:
 *
 * <pre class="code">JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  // reusable object
 * RowMapper rowMapper = new UserRowMapper();  // reusable object
 *
 * List allUsers = (List) jdbcTemplate.query(
 *     "select * from user",
 *     new RowMapperResultSetExtractor(rowMapper, 10));
 *
 * User user = (User) jdbcTemplate.queryForObject(
 *     "select * from user where id=?", new Object[] {id},
 *     new RowMapperResultSetExtractor(rowMapper, 1));</pre>
 *
 * <p>Alternatively, consider subclassing MappingSqlQuery from the {@code jdbc.object}
 * package: Instead of working with separate JdbcTemplate and RowMapper objects,
 * you can have executable query objects (containing row-mapping logic) there.
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 * @param <T> the result element type
 * @see RowMapper
 * @see JdbcTemplate
 * @see org.springframework.jdbc.object.MappingSqlQuery
 */
/**
 * 委托给RowMapper的ResultSetExtractor接口的适配器实现，该RowMapper应该为每行创建一个对象。 
 * 每个对象都添加到此ResultSetExtractor的结果列表中。 
 *  <p>对于数据库表中每行一个对象的典型情况很有用。 
 * 结果列表中的条目数将与行数匹配。 
 *  <p>请注意，RowMapper对象通常是无状态的，因此可以重用； 
 * 仅RowMapperResultSetExtractor适配器是有状态的。 
 *  <p>带有JdbcTemplate的用法示例：<pre class ="code"> JdbcTemplate jdbcTemplate = new JdbcTemplate（dataSource）; //可重用的对象RowMapper rowMapper = new UserRowMapper（）; //可重复使用的对象List allUsers =（List）jdbcTemplate.query（"从用户中选择"，新的RowMapperResultSetExtractor（rowMapper，10））;用户用户=（用户）jdbcTemplate.queryForObject（"从用户那里选择id =？"，新Object [] {id}，新RowMapperResultSetExtractor（rowMapper，1））; </ pre> <p>或者，考虑从以下类继承MappingSqlQuery {@code  jdbc.object}包：您可以在那里具有可执行的查询对象（包含行映射逻辑），而不是使用单独的JdbcTemplate和RowMapper对象。 
 *  @author  Juergen Hoeller @since 1.0.2 
 * @param  <T>结果元素类型
 * @see  RowMapper 
 * @see  JdbcTemplate 
 * @see  org.springframework.jdbc.object.MappingSqlQuery
 */
public class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

	private final RowMapper<T> rowMapper;

	private final int rowsExpected;


	/**
	 * Create a new RowMapperResultSetExtractor.
	 * @param rowMapper the RowMapper which creates an object for each row
	 */
	/**
	 * 创建一个新的RowMapperResultSetExtractor。 
	 *  
	 * @param  rowMapper RowMapper，它为每一行创建一个对象
	 */
	public RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
		this(rowMapper, 0);
	}

	/**
	 * Create a new RowMapperResultSetExtractor.
	 * @param rowMapper the RowMapper which creates an object for each row
	 * @param rowsExpected the number of expected rows
	 * (just used for optimized collection handling)
	 */
	/**
	 * 创建一个新的RowMapperResultSetExtractor。 
	 *  
	 * @param  rowMapper RowMapper为每行创建一个对象
	 * @param  rowsExpected预期的行数（仅用于优化的集合处理）
	 */
	public RowMapperResultSetExtractor(RowMapper<T> rowMapper, int rowsExpected) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.rowsExpected = rowsExpected;
	}


	@Override
	public List<T> extractData(ResultSet rs) throws SQLException {
		List<T> results = (this.rowsExpected > 0 ? new ArrayList<>(this.rowsExpected) : new ArrayList<>());
		int rowNum = 0;
		while (rs.next()) {
			results.add(this.rowMapper.mapRow(rs, rowNum++));
		}
		return results;
	}

}
