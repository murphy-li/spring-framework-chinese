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

package org.springframework.jdbc.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * Generic utility methods for working with JDBC. Mainly for internal use
 * within the framework, but also useful for custom JDBC access code.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 */
/**
 * 用于JDBC的通用实用程序方法。 
 * 主要供框架内部使用，对于自定义JDBC访问代码也很有用。 
 *  @author 托马斯·里斯伯格@author  Juergen Hoeller
 */
public abstract class JdbcUtils {

	/**
	 * Constant that indicates an unknown (or unspecified) SQL type.
	 * @see java.sql.Types
	 */
	/**
	 * 指示未知（或未指定）SQL类型的常量。 
	 *  
	 * @see  java.sql.Types
	 */
	public static final int TYPE_UNKNOWN = Integer.MIN_VALUE;

	private static final Log logger = LogFactory.getLog(JdbcUtils.class);

	private static final Map<Integer, String> typeNames = new HashMap<>();

	static {
		try {
			for (Field field : Types.class.getFields()) {
				typeNames.put((Integer) field.get(null), field.getName());
			}
		}
		catch (Exception ex) {
			throw new IllegalStateException("Failed to resolve JDBC Types constants", ex);
		}
	}


	/**
	 * Close the given JDBC Connection and ignore any thrown exception.
	 * This is useful for typical finally blocks in manual JDBC code.
	 * @param con the JDBC Connection to close (may be {@code null})
	 */
	/**
	 * 关闭给定的JDBC连接，并忽略任何引发的异常。 
	 * 这对于手动JDBC代码中的典型的finally块很有用。 
	 *  
	 * @param 使JDBC连接关闭（可以为{@code  null}）
	 */
	public static void closeConnection(@Nullable Connection con) {
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException ex) {
				logger.debug("Could not close JDBC Connection", ex);
			}
			catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw RuntimeException or Error.
				logger.debug("Unexpected exception on closing JDBC Connection", ex);
			}
		}
	}

	/**
	 * Close the given JDBC Statement and ignore any thrown exception.
	 * This is useful for typical finally blocks in manual JDBC code.
	 * @param stmt the JDBC Statement to close (may be {@code null})
	 */
	/**
	 * 关闭给定的JDBC语句，并忽略任何引发的异常。 
	 * 这对于手动JDBC代码中的典型的finally块很有用。 
	 *  
	 * @param  stmt JDBC语句关闭（可能为{@code  null}）
	 */
	public static void closeStatement(@Nullable Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException ex) {
				logger.trace("Could not close JDBC Statement", ex);
			}
			catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw RuntimeException or Error.
				logger.trace("Unexpected exception on closing JDBC Statement", ex);
			}
		}
	}

	/**
	 * Close the given JDBC ResultSet and ignore any thrown exception.
	 * This is useful for typical finally blocks in manual JDBC code.
	 * @param rs the JDBC ResultSet to close (may be {@code null})
	 */
	/**
	 * 关闭给定的JDBC ResultSet，并忽略任何引发的异常。 
	 * 这对于手动JDBC代码中的典型的finally块很有用。 
	 *  
	 * @param  rs将关闭JDBC ResultSet（可以为{@code  null}）
	 */
	public static void closeResultSet(@Nullable ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException ex) {
				logger.trace("Could not close JDBC ResultSet", ex);
			}
			catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw RuntimeException or Error.
				logger.trace("Unexpected exception on closing JDBC ResultSet", ex);
			}
		}
	}

	/**
	 * Retrieve a JDBC column value from a ResultSet, using the specified value type.
	 * <p>Uses the specifically typed ResultSet accessor methods, falling back to
	 * {@link #getResultSetValue(java.sql.ResultSet, int)} for unknown types.
	 * <p>Note that the returned value may not be assignable to the specified
	 * required type, in case of an unknown type. Calling code needs to deal
	 * with this case appropriately, e.g. throwing a corresponding exception.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @param requiredType the required value type (may be {@code null})
	 * @return the value object (possibly not of the specified required type,
	 * with further conversion steps necessary)
	 * @throws SQLException if thrown by the JDBC API
	 * @see #getResultSetValue(ResultSet, int)
	 */
	/**
	 * 使用指定的值类型从ResultSet中检索JDBC列值。 
	 *  <p>使用专门键入的ResultSet访问器方法，对于未知类型，使用回退到{@link  #getResultSetValue（java.sql.ResultSet，int）}的方法。 
	 *  <p>请注意，如果类型未知，则返回值可能无法分配给指定的必需类型。 
	 * 调用代码需要适当处理这种情况，例如引发相应的异常。 
	 *  
	 * @param  rs是保存数据的ResultSet 
	 * @param 索引是列索引
	 * @param  requiredType必需的值类型（可能为{@code  null}）
	 * @return 值对象（可能不是指定的必需类型，并且需要进一步的转换步骤）
	 * @throws 如果由JDBC API抛出，则抛出SQLException 
	 * @see  #getResultSetValue（ResultSet，int）
	 */
	@Nullable
	public static Object getResultSetValue(ResultSet rs, int index, @Nullable Class<?> requiredType) throws SQLException {
		if (requiredType == null) {
			return getResultSetValue(rs, index);
		}

		Object value;

		// Explicitly extract typed value, as far as possible.
		if (String.class == requiredType) {
			return rs.getString(index);
		}
		else if (boolean.class == requiredType || Boolean.class == requiredType) {
			value = rs.getBoolean(index);
		}
		else if (byte.class == requiredType || Byte.class == requiredType) {
			value = rs.getByte(index);
		}
		else if (short.class == requiredType || Short.class == requiredType) {
			value = rs.getShort(index);
		}
		else if (int.class == requiredType || Integer.class == requiredType) {
			value = rs.getInt(index);
		}
		else if (long.class == requiredType || Long.class == requiredType) {
			value = rs.getLong(index);
		}
		else if (float.class == requiredType || Float.class == requiredType) {
			value = rs.getFloat(index);
		}
		else if (double.class == requiredType || Double.class == requiredType ||
				Number.class == requiredType) {
			value = rs.getDouble(index);
		}
		else if (BigDecimal.class == requiredType) {
			return rs.getBigDecimal(index);
		}
		else if (java.sql.Date.class == requiredType) {
			return rs.getDate(index);
		}
		else if (java.sql.Time.class == requiredType) {
			return rs.getTime(index);
		}
		else if (java.sql.Timestamp.class == requiredType || java.util.Date.class == requiredType) {
			return rs.getTimestamp(index);
		}
		else if (byte[].class == requiredType) {
			return rs.getBytes(index);
		}
		else if (Blob.class == requiredType) {
			return rs.getBlob(index);
		}
		else if (Clob.class == requiredType) {
			return rs.getClob(index);
		}
		else if (requiredType.isEnum()) {
			// Enums can either be represented through a String or an enum index value:
			// leave enum type conversion up to the caller (e.g. a ConversionService)
			// but make sure that we return nothing other than a String or an Integer.
			Object obj = rs.getObject(index);
			if (obj instanceof String) {
				return obj;
			}
			else if (obj instanceof Number) {
				// Defensively convert any Number to an Integer (as needed by our
				// ConversionService's IntegerToEnumConverterFactory) for use as index
				return NumberUtils.convertNumberToTargetClass((Number) obj, Integer.class);
			}
			else {
				// e.g. on Postgres: getObject returns a PGObject but we need a String
				return rs.getString(index);
			}
		}

		else {
			// Some unknown type desired -> rely on getObject.
			try {
				return rs.getObject(index, requiredType);
			}
			catch (AbstractMethodError err) {
				logger.debug("JDBC driver does not implement JDBC 4.1 'getObject(int, Class)' method", err);
			}
			catch (SQLFeatureNotSupportedException ex) {
				logger.debug("JDBC driver does not support JDBC 4.1 'getObject(int, Class)' method", ex);
			}
			catch (SQLException ex) {
				logger.debug("JDBC driver has limited support for JDBC 4.1 'getObject(int, Class)' method", ex);
			}

			// Corresponding SQL types for JSR-310 / Joda-Time types, left up
			// to the caller to convert them (e.g. through a ConversionService).
			String typeName = requiredType.getSimpleName();
			if ("LocalDate".equals(typeName)) {
				return rs.getDate(index);
			}
			else if ("LocalTime".equals(typeName)) {
				return rs.getTime(index);
			}
			else if ("LocalDateTime".equals(typeName)) {
				return rs.getTimestamp(index);
			}

			// Fall back to getObject without type specification, again
			// left up to the caller to convert the value if necessary.
			return getResultSetValue(rs, index);
		}

		// Perform was-null check if necessary (for results that the JDBC driver returns as primitives).
		return (rs.wasNull() ? null : value);
	}

	/**
	 * Retrieve a JDBC column value from a ResultSet, using the most appropriate
	 * value type. The returned value should be a detached value object, not having
	 * any ties to the active ResultSet: in particular, it should not be a Blob or
	 * Clob object but rather a byte array or String representation, respectively.
	 * <p>Uses the {@code getObject(index)} method, but includes additional "hacks"
	 * to get around Oracle 10g returning a non-standard object for its TIMESTAMP
	 * datatype and a {@code java.sql.Date} for DATE columns leaving out the
	 * time portion: These columns will explicitly be extracted as standard
	 * {@code java.sql.Timestamp} object.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @return the value object
	 * @throws SQLException if thrown by the JDBC API
	 * @see java.sql.Blob
	 * @see java.sql.Clob
	 * @see java.sql.Timestamp
	 */
	/**
	 * 使用最合适的值类型从ResultSet中检索JDBC列值。 
	 * 返回的值应该是一个分离值对象，与活动的ResultSet没有任何关系：尤其是，它不应分别是Blob或Clob对象，而应该是字节数组或String表示形式。 
	 *  <p>使用{@code  getObject（index）}方法，但包含其他"hacks"来解决Oracle 10g，返回其TIMESTAMP数据类型的非标准对象和{@code  java.sql.Date }，以排除时间部分的DATE列：这些列将显式提取为标准{@code  java.sql.Timestamp}对象。 
	 *  
	 * @param  rs是保存数据的ResultSet 
	 * @param 索引是列索引
	 * @return 值对象
	 * @throws  SQLException（如果由JDBC API抛出）
	 * @see  java.sql.Blob <
	 * @see > java.sql.Clob 
	 * @see  java.sql.Timestamp
	 */
	@Nullable
	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			Blob blob = (Blob) obj;
			obj = blob.getBytes(1, (int) blob.length());
		}
		else if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			obj = clob.getSubString(1, (int) clob.length());
		}
		else if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
			obj = rs.getTimestamp(index);
		}
		else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			}
			else {
				obj = rs.getDate(index);
			}
		}
		else if (obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}

	/**
	 * Extract database meta-data via the given DatabaseMetaDataCallback.
	 * <p>This method will open a connection to the database and retrieve the database meta-data.
	 * Since this method is called before the exception translation feature is configured for
	 * a datasource, this method can not rely on the SQLException translation functionality.
	 * <p>Any exceptions will be wrapped in a MetaDataAccessException. This is a checked exception
	 * and any calling code should catch and handle this exception. You can just log the
	 * error and hope for the best, but there is probably a more serious error that will
	 * reappear when you try to access the database again.
	 * @param dataSource the DataSource to extract meta-data for
	 * @param action callback that will do the actual work
	 * @return object containing the extracted information, as returned by
	 * the DatabaseMetaDataCallback's {@code processMetaData} method
	 * @throws MetaDataAccessException if meta-data access failed
	 */
	/**
	 * 通过给定的DatabaseMetaDataCallback提取数据库元数据。 
	 *  <p>此方法将打开与数据库的连接并检索数据库元数据。 
	 * 由于在为数据源配置异常转换功能之前调用了此方法，因此该方法不能依赖SQLException转换功能。 
	 *  <p>任何异常都将包装在MetaDataAccessException中。 
	 * 这是一个已检查的异常，任何调用代码都应捕获并处理该异常。 
	 * 您可以记录该错误并希望取得最佳结果，但是当您再次尝试访问数据库时，可能会出现一个更严重的错误。 
	 *  
	 * @param  dataSource数据源，用于为
	 * @param 动作回调提取元数据，该回调将执行包含提取的信息的实际工作
	 * @return 对象，由DatabaseMetaDataCallback的{@code  processMetaData}方法<@返回如果元数据访问失败，则抛出> MetaDataAccessException
	 */
	public static Object extractDatabaseMetaData(DataSource dataSource, DatabaseMetaDataCallback action)
			throws MetaDataAccessException {

		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			DatabaseMetaData metaData = con.getMetaData();
			if (metaData == null) {
				// should only happen in test environments
				throw new MetaDataAccessException("DatabaseMetaData returned by Connection [" + con + "] was null");
			}
			return action.processMetaData(metaData);
		}
		catch (CannotGetJdbcConnectionException ex) {
			throw new MetaDataAccessException("Could not get Connection for extracting meta-data", ex);
		}
		catch (SQLException ex) {
			throw new MetaDataAccessException("Error while extracting DatabaseMetaData", ex);
		}
		catch (AbstractMethodError err) {
			throw new MetaDataAccessException(
					"JDBC DatabaseMetaData method not implemented by JDBC driver - upgrade your driver", err);
		}
		finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	/**
	 * Call the specified method on DatabaseMetaData for the given DataSource,
	 * and extract the invocation result.
	 * @param dataSource the DataSource to extract meta-data for
	 * @param metaDataMethodName the name of the DatabaseMetaData method to call
	 * @return the object returned by the specified DatabaseMetaData method
	 * @throws MetaDataAccessException if we couldn't access the DatabaseMetaData
	 * or failed to invoke the specified method
	 * @see java.sql.DatabaseMetaData
	 */
	/**
	 * 为给定的数据源在DatabaseMetaData上调用指定的方法，并提取调用结果。 
	 *  
	 * @param  dataSource数据源，用于提取
	 * @param  metaDataMethodName的元数据。 
	 * DatabaseMetaData方法的名称，以调用
	 * @return 由指定DatabaseMetaData方法返回的对象
	 * @throws  MetaDataAccessException，如果我们无法访问DatabaseMetaData或无法调用指定的方法
	 * @see  java.sql.DatabaseMetaData
	 */
	@SuppressWarnings("unchecked")
	public static <T> T extractDatabaseMetaData(DataSource dataSource, final String metaDataMethodName)
			throws MetaDataAccessException {

		return (T) extractDatabaseMetaData(dataSource,
				dbmd -> {
					try {
						return DatabaseMetaData.class.getMethod(metaDataMethodName).invoke(dbmd);
					}
					catch (NoSuchMethodException ex) {
						throw new MetaDataAccessException("No method named '" + metaDataMethodName +
								"' found on DatabaseMetaData instance [" + dbmd + "]", ex);
					}
					catch (IllegalAccessException ex) {
						throw new MetaDataAccessException(
								"Could not access DatabaseMetaData method '" + metaDataMethodName + "'", ex);
					}
					catch (InvocationTargetException ex) {
						if (ex.getTargetException() instanceof SQLException) {
							throw (SQLException) ex.getTargetException();
						}
						throw new MetaDataAccessException(
								"Invocation of DatabaseMetaData method '" + metaDataMethodName + "' failed", ex);
					}
				});
	}

	/**
	 * Return whether the given JDBC driver supports JDBC 2.0 batch updates.
	 * <p>Typically invoked right before execution of a given set of statements:
	 * to decide whether the set of SQL statements should be executed through
	 * the JDBC 2.0 batch mechanism or simply in a traditional one-by-one fashion.
	 * <p>Logs a warning if the "supportsBatchUpdates" methods throws an exception
	 * and simply returns {@code false} in that case.
	 * @param con the Connection to check
	 * @return whether JDBC 2.0 batch updates are supported
	 * @see java.sql.DatabaseMetaData#supportsBatchUpdates()
	 */
	/**
	 * 返回给定的JDBC驱动程序是否支持JDBC 2.0批处理更新。 
	 *  <p>通常在执行给定的语句集之前调用：决定是应通过JDBC 2.0批处理机制还是仅以传统的一对一方式执行SQL语句集。 
	 *  <p>在"supportsBatchUpdates"方法引发异常并在这种情况下仅返回{@code  false}时记录警告。 
	 *  
	 * @param 进行连接以检查
	 * @return 是否支持JDBC 2.0批处理更新
	 * @see  java.sql.DatabaseMetaData＃supportsBatchUpdates（）
	 */
	public static boolean supportsBatchUpdates(Connection con) {
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd != null) {
				if (dbmd.supportsBatchUpdates()) {
					logger.debug("JDBC driver supports batch updates");
					return true;
				}
				else {
					logger.debug("JDBC driver does not support batch updates");
				}
			}
		}
		catch (SQLException ex) {
			logger.debug("JDBC driver 'supportsBatchUpdates' method threw exception", ex);
		}
		return false;
	}

	/**
	 * Extract a common name for the target database in use even if
	 * various drivers/platforms provide varying names at runtime.
	 * @param source the name as provided in database meta-data
	 * @return the common name to be used (e.g. "DB2" or "Sybase")
	 */
	/**
	 * 即使各种驱动程序/平台在运行时提供不同的名称，也要为使用中的目标数据库提取一个通用名称。 
	 *  
	 * @param 源数据库元数据中提供的名称
	 * @return 要使用的通用名称（例如"DB2"或"Sybase"）
	 */
	@Nullable
	public static String commonDatabaseName(@Nullable String source) {
		String name = source;
		if (source != null && source.startsWith("DB2")) {
			name = "DB2";
		}
		else if ("MariaDB".equals(source)) {
			name = "MySQL";
		}
		else if ("Sybase SQL Server".equals(source) ||
				"Adaptive Server Enterprise".equals(source) ||
				"ASE".equals(source) ||
				"sql server".equalsIgnoreCase(source) ) {
			name = "Sybase";
		}
		return name;
	}

	/**
	 * Check whether the given SQL type is numeric.
	 * @param sqlType the SQL type to be checked
	 * @return whether the type is numeric
	 */
	/**
	 * 检查给定的SQL类型是否为数字。 
	 *  
	 * @param  sqlType要检查的SQL类型
	 * @return 类型是否为数字
	 */
	public static boolean isNumeric(int sqlType) {
		return (Types.BIT == sqlType || Types.BIGINT == sqlType || Types.DECIMAL == sqlType ||
				Types.DOUBLE == sqlType || Types.FLOAT == sqlType || Types.INTEGER == sqlType ||
				Types.NUMERIC == sqlType || Types.REAL == sqlType || Types.SMALLINT == sqlType ||
				Types.TINYINT == sqlType);
	}

	/**
	 * Resolve the standard type name for the given SQL type, if possible.
	 * @param sqlType the SQL type to resolve
	 * @return the corresponding constant name in {@link java.sql.Types}
	 * (e.g. "VARCHAR"/"NUMERIC"), or {@code null} if not resolvable
	 * @since 5.2
	 */
	/**
	 * 如果可能，解析给定SQL类型的标准类型名称。 
	 *  
	 * @param  sqlType SQL类型以解析
	 * @return  {@link  java.sql.Types}中的相应常量名称（例如"VARCHAR"/"NUMERIC"），或者如果为<< @code> null}自5.2起无法解析
	 */
	@Nullable
	public static String resolveTypeName(int sqlType) {
		return typeNames.get(sqlType);
	}

	/**
	 * Determine the column name to use. The column name is determined based on a
	 * lookup using ResultSetMetaData.
	 * <p>This method implementation takes into account recent clarifications
	 * expressed in the JDBC 4.0 specification:
	 * <p><i>columnLabel - the label for the column specified with the SQL AS clause.
	 * If the SQL AS clause was not specified, then the label is the name of the column</i>.
	 * @param resultSetMetaData the current meta-data to use
	 * @param columnIndex the index of the column for the look up
	 * @return the column name to use
	 * @throws SQLException in case of lookup failure
	 */
	/**
	 * 确定要使用的列名称。 
	 * 列名称是根据使用ResultSetMetaData的查找确定的。 
	 *  <p>此方法实现考虑了JDBC 4.0规范中最近的说明：<p> <i> columnLabel-用SQL AS子句指定的列的标签。 
	 * 如果未指定SQL AS子句，则标签为列的名称</ i>。 
	 *  
	 * @param  resultSetMetaData要使用的当前元数据
	 * @param  columnIndex用于查找的列的索引
	 * @return 用于在查找失败的情况下使用
	 * @throws  SQLException的列名
	 */
	public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnLabel(columnIndex);
		if (!StringUtils.hasLength(name)) {
			name = resultSetMetaData.getColumnName(columnIndex);
		}
		return name;
	}

	/**
	 * Convert a column name with underscores to the corresponding property name using "camel case".
	 * A name like "customer_number" would match a "customerNumber" property name.
	 * @param name the column name to be converted
	 * @return the name using "camel case"
	 */
	/**
	 * 使用"驼峰式大小写"将带有下划线的列名称转换为相应的属性名称。 
	 * 类似于"customer_number"的名称将与"customerNumber"属性名称匹配。 
	 *  
	 * @param 使用"camel case"命名要转换的列名称
	 * @return 该名称
	 */
	public static String convertUnderscoreNameToPropertyName(@Nullable String name) {
		StringBuilder result = new StringBuilder();
		boolean nextIsUpper = false;
		if (name != null && name.length() > 0) {
			if (name.length() > 1 && name.charAt(1) == '_') {
				result.append(Character.toUpperCase(name.charAt(0)));
			}
			else {
				result.append(Character.toLowerCase(name.charAt(0)));
			}
			for (int i = 1; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c == '_') {
					nextIsUpper = true;
				}
				else {
					if (nextIsUpper) {
						result.append(Character.toUpperCase(c));
						nextIsUpper = false;
					}
					else {
						result.append(Character.toLowerCase(c));
					}
				}
			}
		}
		return result.toString();
	}

}
