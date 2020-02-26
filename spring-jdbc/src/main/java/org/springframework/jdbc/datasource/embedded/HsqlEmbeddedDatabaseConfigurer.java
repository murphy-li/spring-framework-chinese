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

import java.sql.Driver;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * {@link EmbeddedDatabaseConfigurer} for an HSQL embedded database instance.
 *
 * <p>Call {@link #getInstance()} to get the singleton instance of this class.
 *
 * @author Keith Donald
 * @author Oliver Gierke
 * @since 3.0
 */
/**
 * {@link  EmbeddedDatabaseConfigurer}用于HSQL嵌入式数据库实例。 
 *  <p>调用{@link  #getInstance（）}以获取此类的单例实例。 
 *  @author 基思·唐纳德@author 奥利弗·吉尔克@since 3.0
 */
final class HsqlEmbeddedDatabaseConfigurer extends AbstractEmbeddedDatabaseConfigurer {

	@Nullable
	private static HsqlEmbeddedDatabaseConfigurer instance;

	private final Class<? extends Driver> driverClass;


	/**
	 * Get the singleton {@link HsqlEmbeddedDatabaseConfigurer} instance.
	 * @return the configurer instance
	 * @throws ClassNotFoundException if HSQL is not on the classpath
	 */
	/**
	 * 获取单例{@link  HsqlEmbeddedDatabaseConfigurer}实例。 
	 *  
	 * @return 配置器实例
	 * @throws  ClassNotFoundException（如果HSQL不在类路径中）
	 */
	@SuppressWarnings("unchecked")
	public static synchronized HsqlEmbeddedDatabaseConfigurer getInstance() throws ClassNotFoundException {
		if (instance == null) {
			instance = new HsqlEmbeddedDatabaseConfigurer( (Class<? extends Driver>)
					ClassUtils.forName("org.hsqldb.jdbcDriver", HsqlEmbeddedDatabaseConfigurer.class.getClassLoader()));
		}
		return instance;
	}


	private HsqlEmbeddedDatabaseConfigurer(Class<? extends Driver> driverClass) {
		this.driverClass = driverClass;
	}

	@Override
	public void configureConnectionProperties(ConnectionProperties properties, String databaseName) {
		properties.setDriverClass(this.driverClass);
		properties.setUrl("jdbc:hsqldb:mem:" + databaseName);
		properties.setUsername("sa");
		properties.setPassword("");
	}

}
