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

package org.springframework.jdbc.support.lob;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.lang.Nullable;

/**
 * Interface that abstracts potentially database-specific creation of large binary
 * fields and large text fields. Does not work with {@code java.sql.Blob}
 * and {@code java.sql.Clob} instances in the API, as some JDBC drivers
 * do not support these types as such.
 *
 * <p>The LOB creation part is where {@link LobHandler} implementations usually
 * differ. Possible strategies include usage of
 * {@code PreparedStatement.setBinaryStream/setCharacterStream} but also
 * {@code PreparedStatement.setBlob/setClob} with either a stream argument
 * (requires JDBC 4.0) or {@code java.sql.Blob/Clob} wrapper objects.
 *
 * <p>A LobCreator represents a session for creating BLOBs: It is <i>not</i>
 * thread-safe and needs to be instantiated for each statement execution or for
 * each transaction. Each LobCreator needs to be closed after completion.
 *
 * <p>For convenient working with a PreparedStatement and a LobCreator,
 * consider using {@link org.springframework.jdbc.core.JdbcTemplate} with an
 *{@link org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback}
 * implementation. See the latter's javadoc for details.
 *
 * @author Juergen Hoeller
 * @since 04.12.2003
 * @see #close()
 * @see LobHandler#getLobCreator()
 * @see DefaultLobHandler.DefaultLobCreator
 * @see java.sql.PreparedStatement#setBlob
 * @see java.sql.PreparedStatement#setClob
 * @see java.sql.PreparedStatement#setBytes
 * @see java.sql.PreparedStatement#setBinaryStream
 * @see java.sql.PreparedStatement#setString
 * @see java.sql.PreparedStatement#setAsciiStream
 * @see java.sql.PreparedStatement#setCharacterStream
 */
/**
 * 该接口抽象潜在的特定于数据库的大型二进制字段和大型文本字段的创建。 
 * 不适用于API中的{@code  java.sql.Blob}和{@code  java.sql.Clob}实例，因为某些JDBC驱动程序本身不支持这些类型。 
 *  <p> LOB创建部分通常是{@link  LobHandler}实现的不同之处。 
 * 可能的策略包括使用{@code  PreparedStatement.setBinaryStream / setCharacterStream}以及{@code  PreparedStatement.setBlob / setClob}和流参数（需要JDBC 4.0）或{@code  java.sql.Blob / Clob}包装器对象。 
 *  <p> LobCreator代表用于创建BLOB的会话：<i>不是</ i>线程安全的，并且需要为每个语句执行或每个事务实例化。 
 * 完成后，需要关闭每个LobCreator。 
 *  <p>为方便使用PreparedStatement和LobCreator，请考虑将{@link  org.springframework.jdbc.core.JdbcTemplate}与{@link  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback}实现一起使用。 
 * 有关详细信息，请参见后者的javadoc。 
 *  @author  Juergen Hoeller @2003年12月4日起
 * @see  #close（）
 * @see  LobHandler＃getLobCreator（）
 * @see  DefaultLobHandler.DefaultLobCreator 
 * @see  java.sql.PreparedStatement＃setBlob 
 * @see  java.sql.PreparedStatement＃setClob 
 * @see  java.sql.PreparedStatement＃setBytes 
 * @see  java.sql.PreparedStatement＃setBinaryStream 
 * @see  java.sql.PreparedStatement＃setString 
 * @see  java.sql.PreparedStatement＃ setAsciiStream 
 * @see  java.sql.PreparedStatement＃setCharacterStream
 */
public interface LobCreator extends Closeable {

	/**
	 * Set the given content as bytes on the given statement, using the given
	 * parameter index. Might simply invoke {@code PreparedStatement.setBytes}
	 * or create a Blob instance for it, depending on the database and driver.
	 * @param ps the PreparedStatement to the set the content on
	 * @param paramIndex the parameter index to use
	 * @param content the content as byte array, or {@code null} for SQL NULL
	 * @throws SQLException if thrown by JDBC methods
	 * @see java.sql.PreparedStatement#setBytes
	 */
	/**
	 * 使用给定的参数索引，将给定的内容设置为给定语句上的字节。 
	 * 可以简单地调用{@code  PreparedStatement.setBytes}或为其创建Blob实例，具体取决于数据库和驱动程序。 
	 *  
	 * @param  ps PreparedStatement设置
	 * @param 上的内容paramIndex参数索引以将
	 * @param 内容用作字节数组，或者将<< @code> null}用作SQL NULL 
	 * @throws  SQLException如果由JDBC方法抛出
	 * @see  java.sql.PreparedStatement＃setBytes
	 */
	void setBlobAsBytes(PreparedStatement ps, int paramIndex, @Nullable byte[] content)
			throws SQLException;

	/**
	 * Set the given content as binary stream on the given statement, using the given
	 * parameter index. Might simply invoke {@code PreparedStatement.setBinaryStream}
	 * or create a Blob instance for it, depending on the database and driver.
	 * @param ps the PreparedStatement to the set the content on
	 * @param paramIndex the parameter index to use
	 * @param contentStream the content as binary stream, or {@code null} for SQL NULL
	 * @throws SQLException if thrown by JDBC methods
	 * @see java.sql.PreparedStatement#setBinaryStream
	 */
	/**
	 * 使用给定的参数索引将给定的内容设置为给定语句上的二进制流。 
	 * 可以简单地调用{@code  PreparedStatement.setBinaryStream}或为其创建Blob实例，具体取决于数据库和驱动程序。 
	 *  
	 * @param  ps PreparedStatement设置
	 * @param 上的内容paramIndex参数索引以将
	 * @param  contentStream用作二进制流，或将<< @code> null}用于SQL NULL 
	 * @throws  SQLException如果由JDBC方法抛出
	 * @see  java.sql.PreparedStatement＃setBinaryStream
	 */
	void setBlobAsBinaryStream(
			PreparedStatement ps, int paramIndex, @Nullable InputStream contentStream, int contentLength)
			throws SQLException;

	/**
	 * Set the given content as String on the given statement, using the given
	 * parameter index. Might simply invoke {@code PreparedStatement.setString}
	 * or create a Clob instance for it, depending on the database and driver.
	 * @param ps the PreparedStatement to the set the content on
	 * @param paramIndex the parameter index to use
	 * @param content the content as String, or {@code null} for SQL NULL
	 * @throws SQLException if thrown by JDBC methods
	 * @see java.sql.PreparedStatement#setBytes
	 */
	/**
	 * 使用给定的参数索引，在给定的语句上将给定的内容设置为String。 
	 * 可以简单地调用{@code  PreparedStatement.setString}或为其创建Clob实例，具体取决于数据库和驱动程序。 
	 *  
	 * @param  ps PreparedStatement设置
	 * @param 上的内容paramIndex参数索引以将
	 * @param 内容用作字符串，或者对于SQL NULL，将{@code  null}用作
	 * @throws  SQLException JDBC方法抛出的@
	 * @seejava.sql.PreparedStatement＃setBytes
	 */
	void setClobAsString(PreparedStatement ps, int paramIndex, @Nullable String content)
			throws SQLException;

	/**
	 * Set the given content as ASCII stream on the given statement, using the given
	 * parameter index. Might simply invoke {@code PreparedStatement.setAsciiStream}
	 * or create a Clob instance for it, depending on the database and driver.
	 * @param ps the PreparedStatement to the set the content on
	 * @param paramIndex the parameter index to use
	 * @param asciiStream the content as ASCII stream, or {@code null} for SQL NULL
	 * @throws SQLException if thrown by JDBC methods
	 * @see java.sql.PreparedStatement#setAsciiStream
	 */
	/**
	 * 使用给定的参数索引，在给定的语句上将给定的内容设置为ASCII流。 
	 * 可以简单地调用{@code  PreparedStatement.setAsciiStream}或为其创建Clob实例，具体取决于数据库和驱动程序。 
	 *  
	 * @param  ps PreparedStatement设置
	 * @param 上的内容paramIndex参数索引以使用
	 * @param  asciiStream内容作为ASCII流，或对于SQL NULL而言为{@code  null}，<
	 * @throws> SQLException如果由JDBC方法抛出
	 * @see  java.sql.PreparedStatement＃setAsciiStream
	 */
	void setClobAsAsciiStream(
			PreparedStatement ps, int paramIndex, @Nullable InputStream asciiStream, int contentLength)
			throws SQLException;

	/**
	 * Set the given content as character stream on the given statement, using the given
	 * parameter index. Might simply invoke {@code PreparedStatement.setCharacterStream}
	 * or create a Clob instance for it, depending on the database and driver.
	 * @param ps the PreparedStatement to the set the content on
	 * @param paramIndex the parameter index to use
	 * @param characterStream the content as character stream, or {@code null} for SQL NULL
	 * @throws SQLException if thrown by JDBC methods
	 * @see java.sql.PreparedStatement#setCharacterStream
	 */
	/**
	 * 使用给定的参数索引，将给定的内容设置为给定语句上的字符流。 
	 * 可以简单地调用{@code  PreparedStatement.setCharacterStream}或为其创建Clob实例，具体取决于数据库和驱动程序。 
	 *  
	 * @param  ps PreparedStatement设置
	 * @param 上的内容paramIndex参数索引以使用
	 * @param  characterStream内容作为字符流，或{@code  null}表示SQL NULL 
	 * @throws  SQLException如果被JDBC方法抛出
	 * @see  java.sql.PreparedStatement＃setCharacterStream
	 */
	void setClobAsCharacterStream(
			PreparedStatement ps, int paramIndex, @Nullable Reader characterStream, int contentLength)
			throws SQLException;

	/**
	 * Close this LobCreator session and free its temporarily created BLOBs and CLOBs.
	 * Will not need to do anything if using PreparedStatement's standard methods,
	 * but might be necessary to free database resources if using proprietary means.
	 * <p><b>NOTE</b>: Needs to be invoked after the involved PreparedStatements have
	 * been executed or the affected O/R mapping sessions have been flushed.
	 * Otherwise, the database resources for the temporary BLOBs might stay allocated.
	 */
	/**
	 * 关闭此LobCreator会话并释放其临时创建的BLOB和CLOB。 
	 * 如果使用PreparedStatement的标准方法，则无需执行任何操作，但如果使用专有方法，则可能需要释放数据库资源。 
	 *  <p> <b>注意</ b>：在执行了相关的PreparedStatement或刷新了受影响的O / R映射会话后，需要调用该方法。 
	 * 否则，临时BLOB的数据库资源可能会保持分配状态。 
	 * 
	 */
	@Override
	void close();

}
