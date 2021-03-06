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

import java.util.Map;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Interface specifying the API for a Simple JDBC Insert implemented by {@link SimpleJdbcInsert}.
 * This interface is not often used directly, but provides the option to enhance testability,
 * as it can easily be mocked or stubbed.
 *
 * @author Thomas Risberg
 * @since 2.5
 */
/**
 * 指定由{@link  SimpleJdbcInsert}实现的简单JDBC插入的API的接口。 
 * 该接口通常不直接使用，但是提供了增强可测试性的选项，因为它很容易被嘲笑或存根。 
 *  @author 托马斯·里斯伯格@since 2.5
 */
public interface SimpleJdbcInsertOperations {

	/**
	 * Specify the table name to be used for the insert.
	 * @param tableName the name of the stored table
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 指定要用于插入的表名。 
	 *  
	 * @param  tableName存储表的名称
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations withTableName(String tableName);

	/**
	 * Specify the schema name, if any, to be used for the insert.
	 * @param schemaName the name of the schema
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 指定要用于插入的架构名称（如果有）。 
	 *  
	 * @param  schemaName模式名称
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations withSchemaName(String schemaName);

	/**
	 * Specify the catalog name, if any, to be used for the insert.
	 * @param catalogName the name of the catalog
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 指定要用于插入的目录名称（如果有）。 
	 *  
	 * @param  catalogName名称目录的名称
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations withCatalogName(String catalogName);

	/**
	 * Specify the column names that the insert statement should be limited to use.
	 * @param columnNames one or more column names
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 指定应该限制插入语句使用的列名。 
	 *  
	 * @param  columnNames一个或多个列名称
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations usingColumns(String... columnNames);

	/**
	 * Specify the names of any columns that have auto generated keys.
	 * @param columnNames one or more column names
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 指定具有自动生成键的任何列的名称。 
	 *  
	 * @param  columnNames一个或多个列名称
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations usingGeneratedKeyColumns(String... columnNames);

	/**
	 * Turn off any processing of column meta-data information obtained via JDBC.
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 关闭对通过JDBC获得的列元数据信息的任何处理。 
	 *  
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations withoutTableColumnMetaDataAccess();

	/**
	 * Include synonyms for the column meta-data lookups via JDBC.
	 * <p>Note: This is only necessary to include for Oracle since other databases
	 * supporting synonyms seems to include the synonyms automatically.
	 * @return the instance of this SimpleJdbcInsert
	 */
	/**
	 * 通过JDBC包括列元数据查找的同义词。 
	 *  <p>注意：这仅对于Oracle来说是必需的，因为其他支持同义词的数据库似乎自动包含了同义词。 
	 *  
	 * @return 此SimpleJdbcInsert的实例
	 */
	SimpleJdbcInsertOperations includeSynonymsForTableColumnMetaData();


	/**
	 * Execute the insert using the values passed in.
	 * @param args a Map containing column names and corresponding value
	 * @return the number of rows affected as returned by the JDBC driver
	 */
	/**
	 * 使用传入的值执行插入。 
	 * 
	 * @param  args一个包含列名和对应值的Map 
	 * @return  JDBC驱动程序返回的受影响的行数
	 */
	int execute(Map<String, ?> args);

	/**
	 * Execute the insert using the values passed in.
	 * @param parameterSource the SqlParameterSource containing values to use for insert
	 * @return the number of rows affected as returned by the JDBC driver
	 */
	/**
	 * 使用传入的值执行插入。 
	 * 
	 * @param  parameterSource SqlParameterSource包含要用于插入的值
	 * @return  JDBC驱动程序返回的受影响的行数
	 */
	int execute(SqlParameterSource parameterSource);

	/**
	 * Execute the insert using the values passed in and return the generated key.
	 * <p>This requires that the name of the columns with auto generated keys have been specified.
	 * This method will always return a KeyHolder but the caller must verify that it actually
	 * contains the generated keys.
	 * @param args a Map containing column names and corresponding value
	 * @return the generated key value
	 */
	/**
	 * 使用传入的值执行插入并返回生成的密钥。 
	 *  <p>这需要指定具有自动生成键的列的名称。 
	 * 此方法将始终返回KeyHolder，但调用者必须验证它是否实际包含生成的密钥。 
	 *  
	 * @param  args一个包含列名和对应值的Map 
	 * @return 生成的键值
	 */
	Number executeAndReturnKey(Map<String, ?> args);

	/**
	 * Execute the insert using the values passed in and return the generated key.
	 * <p>This requires that the name of the columns with auto generated keys have been specified.
	 * This method will always return a KeyHolder but the caller must verify that it actually
	 * contains the generated keys.
	 * @param parameterSource the SqlParameterSource containing values to use for insert
	 * @return the generated key value.
	 */
	/**
	 * 使用传入的值执行插入并返回生成的密钥。 
	 *  <p>这需要指定具有自动生成键的列的名称。 
	 * 此方法将始终返回KeyHolder，但调用者必须验证它是否实际包含生成的密钥。 
	 *  
	 * @param  parameterSource SqlParameterSource包含用于插入
	 * @return 生成的键值的值。 
	 * 
	 */
	Number executeAndReturnKey(SqlParameterSource parameterSource);

	/**
	 * Execute the insert using the values passed in and return the generated keys.
	 * <p>This requires that the name of the columns with auto generated keys have been specified.
	 * This method will always return a KeyHolder but the caller must verify that it actually
	 * contains the generated keys.
	 * @param args a Map containing column names and corresponding value
	 * @return the KeyHolder containing all generated keys
	 */
	/**
	 * 使用传入的值执行插入并返回生成的键。 
	 *  <p>这需要指定具有自动生成键的列的名称。 
	 * 此方法将始终返回KeyHolder，但调用者必须验证它是否实际包含生成的密钥。 
	 *  
	 * @param  args一个包含列名和对应值的Map 
	 * @return 包含所有已生成键的KeyHolder
	 */
	KeyHolder executeAndReturnKeyHolder(Map<String, ?> args);

	/**
	 * Execute the insert using the values passed in and return the generated keys.
	 * <p>This requires that the name of the columns with auto generated keys have been specified.
	 * This method will always return a KeyHolder but the caller must verify that it actually
	 * contains the generated keys.
	 * @param parameterSource the SqlParameterSource containing values to use for insert
	 * @return the KeyHolder containing all generated keys
	 */
	/**
	 * 使用传入的值执行插入并返回生成的键。 
	 *  <p>这需要指定具有自动生成键的列的名称。 
	 * 此方法将始终返回KeyHolder，但调用者必须验证它是否实际包含生成的密钥。 
	 *  
	 * @param  parameterSource SqlParameterSource包含用于插入的值
	 * @return 包含所有已生成键的KeyHolder
	 */
	KeyHolder executeAndReturnKeyHolder(SqlParameterSource parameterSource);

	/**
	 * Execute a batch insert using the batch of values passed in.
	 * @param batch an array of Maps containing a batch of column names and corresponding value
	 * @return the array of number of rows affected as returned by the JDBC driver
	 */
	/**
	 * 使用传入的值的批处理执行批处理插入。 
	 * 
	 * @param 批处理包含一批列名和对应值的Maps数组
	 * @return  JDBC驱动程序返回的受影响的行数数组
	 */
	@SuppressWarnings("unchecked")
	int[] executeBatch(Map<String, ?>... batch);

	/**
	 * Execute a batch insert using the batch of values passed in.
	 * @param batch an array of SqlParameterSource containing values for the batch
	 * @return the array of number of rows affected as returned by the JDBC driver
	 */
	/**
	 * 使用传入的值的批处理执行批处理插入。 
	 * 
	 * @param 批处理SqlParameterSource数组，其中包含批处理的值
	 * @return  JDBC驱动程序返回的受影响的行数数组
	 */
	int[] executeBatch(SqlParameterSource... batch);

}
