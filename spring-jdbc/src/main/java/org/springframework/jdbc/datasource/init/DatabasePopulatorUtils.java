/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jdbc.datasource.init;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

/**
 * Utility methods for executing a {@link DatabasePopulator}.
 *
 * @author Juergen Hoeller
 * @author Oliver Gierke
 * @author Sam Brannen
 * @since 3.1
 */
/**
 * 执行{@link  DatabasePopulator}的实用方法。 
 *  @author  Juergen Hoeller @author 奥利弗·吉尔克（Oliver Gierke）@author  Sam Brannen @since 3.1
 */
public abstract class DatabasePopulatorUtils {

	/**
	 * Execute the given {@link DatabasePopulator} against the given {@link DataSource}.
	 * @param populator the {@code DatabasePopulator} to execute
	 * @param dataSource the {@code DataSource} to execute against
	 * @throws DataAccessException if an error occurs, specifically a {@link ScriptException}
	 */
	/**
	 * 对给定的{@link  DataSource}执行给定的{@link  DatabasePopulator}。 
	 *  
	 * @param 填充器{{@@code> DatabasePopulator}来执行
	 * @param 数据源{{@@code> DataSource}来针对发生错误的
	 * @throws  DataAccessException执行，特别是{@link  ScriptException}
	 */
	public static void execute(DatabasePopulator populator, DataSource dataSource) throws DataAccessException {
		Assert.notNull(populator, "DatabasePopulator must not be null");
		Assert.notNull(dataSource, "DataSource must not be null");
		try {
			Connection connection = DataSourceUtils.getConnection(dataSource);
			try {
				populator.populate(connection);
			}
			finally {
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
		catch (ScriptException ex) {
			throw ex;
		}
		catch (Throwable ex) {
			throw new UncategorizedScriptException("Failed to execute database script", ex);
		}
	}

}
