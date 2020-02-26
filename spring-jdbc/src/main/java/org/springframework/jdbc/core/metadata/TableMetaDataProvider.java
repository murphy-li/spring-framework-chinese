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

package org.springframework.jdbc.core.metadata;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.lang.Nullable;

/**
 * Interface specifying the API to be implemented by a class providing table meta-data.
 * This is intended for internal use by the Simple JDBC classes.
 *
 * @author Thomas Risberg
 * @since 2.5
 */
/**
 * 指定要由提供表元数据的类实现的API的接口。 
 * 这是供Simple JDBC类内部使用的。 
 *  @author 托马斯·里斯伯格@since 2.5
 */
public interface TableMetaDataProvider {

	/**
	 * Initialize using the database meta-data provided.
	 * @param databaseMetaData used to retrieve database specific information
	 * @throws SQLException in case of initialization failure
	 */
	/**
	 * 使用提供的数据库元数据进行初始化。 
	 *  
	 * @param  databaseMetaData用于检索数据库特定信息
	 * @throws  SQLException，如果初始化失败
	 */
	void initializeWithMetaData(DatabaseMetaData databaseMetaData) throws SQLException;

	/**
	 * Initialize using provided database meta-data, table and column information.
	 * This initialization can be turned off by specifying that column meta-data should not be used.
	 * @param databaseMetaData used to retrieve database specific information
	 * @param catalogName name of catalog to use (or {@code null} if none)
	 * @param schemaName name of schema name to use (or {@code null} if none)
	 * @param tableName name of the table
	 * @throws SQLException in case of initialization failure
	 */
	/**
	 * 使用提供的数据库元数据，表和列信息进行初始化。 
	 * 可以通过指定不使用列元数据来关闭此初始化。 
	 *  
	 * @param  databaseMetaData用于检索数据库特定信息
	 * @param  catalogName要使用的目录名称（如果没有，则为{@code  null}）
	 * @param  schemaName要使用的架构名称（或{<@code > null}，如果没有）
	 * @param  tableName表名
	 * @throws 初始化失败时的SQLException
	 */
	void initializeWithTableColumnMetaData(DatabaseMetaData databaseMetaData, @Nullable String catalogName,
			@Nullable String schemaName, @Nullable String tableName) throws SQLException;

	/**
	 * Get the table name formatted based on meta-data information.
	 * This could include altering the case.
	 */
	/**
	 * 获取基于元数据信息格式化的表名。 
	 * 这可能包括更改案例。 
	 * 
	 */
	@Nullable
	String tableNameToUse(@Nullable String tableName);

	/**
	 * Get the catalog name formatted based on meta-data information.
	 * This could include altering the case.
	 */
	/**
	 * 获取基于元数据信息格式化的目录名称。 
	 * 这可能包括更改案例。 
	 * 
	 */
	@Nullable
	String catalogNameToUse(@Nullable String catalogName);

	/**
	 * Get the schema name formatted based on meta-data information.
	 * This could include altering the case.
	 */
	/**
	 * 获取基于元数据信息格式化的架构名称。 
	 * 这可能包括更改案例。 
	 * 
	 */
	@Nullable
	String schemaNameToUse(@Nullable String schemaName);

	/**
	 * Provide any modification of the catalog name passed in to match the meta-data currently used.
	 * The returned value will be used for meta-data lookups.
	 * This could include altering the case used or providing a base catalog if none is provided.
	 */
	/**
	 * 提供传入目录名称的任何修改，以匹配当前使用的元数据。 
	 * 返回的值将用于元数据查找。 
	 * 这可能包括更改使用的案例或提供基本目录（如果未提供）。 
	 * 
	 */
	@Nullable
	String metaDataCatalogNameToUse(@Nullable String catalogName) ;

	/**
	 * Provide any modification of the schema name passed in to match the meta-data currently used.
	 * The returned value will be used for meta-data lookups.
	 * This could include altering the case used or providing a base schema if none is provided.
	 */
	/**
	 * 提供传入的架构名称的任何修改，以匹配当前使用的元数据。 
	 * 返回的值将用于元数据查找。 
	 * 这可能包括更改使用的大小写或提供基本模式（如果未提供）。 
	 * 
	 */
	@Nullable
	String metaDataSchemaNameToUse(@Nullable String schemaName) ;

	/**
	 * Are we using the meta-data for the table columns?
	 */
	/**
	 * 我们是否在表列中使用元数据？
	 */
	boolean isTableColumnMetaDataUsed();

	/**
	 * Does this database support the JDBC 3.0 feature of retrieving generated keys:
	 * {@link java.sql.DatabaseMetaData#supportsGetGeneratedKeys()}?
	 */
	/**
	 * 此数据库是否支持JDBC 3.0功能，用于检索生成的密钥：{@link  java.sql.DatabaseMetaData＃supportsGetGeneratedKeys（）}？
	 */
	boolean isGetGeneratedKeysSupported();

	/**
	 * Does this database support a simple query to retrieve the generated key when
	 * the JDBC 3.0 feature of retrieving generated keys is not supported?
	 * @see #isGetGeneratedKeysSupported()
	 */
	/**
	 * 当不支持检索生成的键的JDBC 3.0功能时，此数据库是否支持简单查询来检索生成的键？ 
	 * @see  #isGetGeneratedKeysSupported（）
	 */
	boolean isGetGeneratedKeysSimulated();

	/**
	 * Get the simple query to retrieve a generated key.
	 */
	/**
	 * 获取简单查询以检索生成的密钥。 
	 * 
	 */
	@Nullable
	String getSimpleQueryForGetGeneratedKey(String tableName, String keyColumnName);

	/**
	 * Does this database support a column name String array for retrieving generated keys:
	 * {@link java.sql.Connection#createStruct(String, Object[])}?
	 */
	/**
	 * 此数据库是否支持用于检索生成的键的列名String数组：{@link  java.sql.Connection＃createStruct（String，Object []）}？
	 */
	boolean isGeneratedKeysColumnNameArraySupported();

	/**
	 * Get the table parameter meta-data that is currently used.
	 * @return a List of {@link TableParameterMetaData}
	 */
	/**
	 * 获取当前使用的表参数元数据。 
	 *  
	 * @return  {@link  TableParameterMetaData}的列表
	 */
	List<TableParameterMetaData> getTableParameterMetaData();

}
