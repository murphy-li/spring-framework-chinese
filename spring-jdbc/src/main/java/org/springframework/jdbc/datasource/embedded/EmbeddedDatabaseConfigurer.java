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

package org.springframework.jdbc.datasource.embedded;

import javax.sql.DataSource;

/**
 * {@code EmbeddedDatabaseConfigurer} encapsulates the configuration required to
 * create, connect to, and shut down a specific type of embedded database such as
 * HSQL, H2, or Derby.
 *
 * @author Keith Donald
 * @author Sam Brannen
 * @since 3.0
 */
/**
 * {@code  EmbeddedDatabaseConfigurer}封装了创建，连接和关闭特定类型的嵌入式数据库（例如HSQL，H2或Derby）所需的配置。 
 *  @author 基思·唐纳德@author  Sam Brannen @从3.0开始
 */
public interface EmbeddedDatabaseConfigurer {

	/**
	 * Configure the properties required to create and connect to the embedded database.
	 * @param properties connection properties to configure
	 * @param databaseName the name of the embedded database
	 */
	/**
	 * 配置创建和连接到嵌入式数据库所需的属性。 
	 *  
	 * @param 属性连接属性以配置
	 * @param  databaseName嵌入式数据库的名称
	 */
	void configureConnectionProperties(ConnectionProperties properties, String databaseName);

	/**
	 * Shut down the embedded database instance that backs the supplied {@link DataSource}.
	 * @param dataSource the corresponding {@link DataSource}
	 * @param databaseName the name of the database being shut down
	 */
	/**
	 * 关闭支持提供的{@link  DataSource}的嵌入式数据库实例。 
	 *  
	 * @param  dataSource对应的{@link  DataSource} 
	 * @param  databaseName要关闭的数据库的名称
	 */
	void shutdown(DataSource dataSource, String databaseName);

}
