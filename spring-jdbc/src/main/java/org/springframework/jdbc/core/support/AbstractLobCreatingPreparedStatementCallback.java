/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2013 the original author or authors.
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
 * 版权所有2002-2013的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jdbc.core.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.Assert;

/**
 * Abstract {@link PreparedStatementCallback} implementation that manages a {@link LobCreator}.
 * Typically used as inner class, with access to surrounding method arguments.
 *
 * <p>Delegates to the {@code setValues} template method for setting values
 * on the PreparedStatement, using a given LobCreator for BLOB/CLOB arguments.
 *
 * <p>A usage example with {@link org.springframework.jdbc.core.JdbcTemplate}:
 *
 * <pre class="code">JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  // reusable object
 * LobHandler lobHandler = new DefaultLobHandler();  // reusable object
 *
 * jdbcTemplate.execute(
 *     "INSERT INTO imagedb (image_name, content, description) VALUES (?, ?, ?)",
 *     new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
 *       protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
 *         ps.setString(1, name);
 *         lobCreator.setBlobAsBinaryStream(ps, 2, contentStream, contentLength);
 *         lobCreator.setClobAsString(ps, 3, description);
 *       }
 *     }
 * );</pre>
 *
 * @author Juergen Hoeller
 * @since 1.0.2
 * @see org.springframework.jdbc.support.lob.LobCreator
 */
/**
 * 管理{@link  LobCreator}的抽象{@link  PreparedStatementCallback}实现。 
 * 通常用作内部类，可以访问周围的方法参数。 
 *  <p>委托{@code  setValues}模板方法，使用给定的LobCreator for BLOB / CLOB参数在PreparedStatement上设置值。 
 *  <p>带有{@link  org.springframework.jdbc.core.JdbcTemplate}的用法示例：<pre class ="code"> JdbcTemplate jdbcTemplate = new JdbcTemplate（dataSource）; //可重用的对象LobHandler lobHandler = new DefaultLobHandler（）; //可重复使用的对象jdbcTemplate.execute（"将INTO插入imagedb（图像名称，内容，描述）值（？，？，？）"，新的AbstractLobCreatingPreparedStatementCallback（lobHandler）{protected void setValues（PreparedStatement ps，LobCreator lobCreator）引发SQLException {ps。 
 *  setString（1，name）; lobCreator.setBlobAsBinaryStream（ps，2，contentStream，contentLength）; lobCreator.setClobAsString（ps，3，description）;}}）; </ pre> @author  Juergen Hoeller @since 1.0.2起
 * @see  org.springframework.jdbc.support.lob.LobCreator
 */
public abstract class AbstractLobCreatingPreparedStatementCallback implements PreparedStatementCallback<Integer> {

	private final LobHandler lobHandler;


	/**
	 * Create a new AbstractLobCreatingPreparedStatementCallback for the
	 * given LobHandler.
	 * @param lobHandler the LobHandler to create LobCreators with
	 */
	/**
	 * 为给定的LobHandler创建一个新的AbstractLobCreatingPreparedStatementCallback。 
	 *  
	 * @param  lobHandler LobHandler使用以下命令创建LobCreators
	 */
	public AbstractLobCreatingPreparedStatementCallback(LobHandler lobHandler) {
		Assert.notNull(lobHandler, "LobHandler must not be null");
		this.lobHandler = lobHandler;
	}


	@Override
	public final Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
		LobCreator lobCreator = this.lobHandler.getLobCreator();
		try {
			setValues(ps, lobCreator);
			return ps.executeUpdate();
		}
		finally {
			lobCreator.close();
		}
	}

	/**
	 * Set values on the given PreparedStatement, using the given
	 * LobCreator for BLOB/CLOB arguments.
	 * @param ps the PreparedStatement to use
	 * @param lobCreator the LobCreator to use
	 * @throws SQLException if thrown by JDBC methods
	 * @throws DataAccessException in case of custom exceptions
	 */
	/**
	 * 使用给定的LobCreator的BLOB / CLOB参数在给定的PreparedStatement上设置值。 
	 *  
	 * @param  ps PreparedStatement使用
	 * @param  lobCreator LobCreator使用
	 * @throws  SQLException（如果由JDBC方法抛出）
	 * @throws  DataAccessException（如果发生自定义异常）
	 */
	protected abstract void setValues(PreparedStatement ps, LobCreator lobCreator)
			throws SQLException, DataAccessException;

}
