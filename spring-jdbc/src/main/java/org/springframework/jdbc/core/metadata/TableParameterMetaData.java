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

/**
 * Holder of meta-data for a specific parameter that is used for table processing.
 *
 * @author Thomas Risberg
 * @since 2.5
 * @see GenericTableMetaDataProvider
 */
/**
 * 用于表处理的特定参数的元数据持有者。 
 *  @author  Thomas Risberg @从2.5开始
 * @see  GenericTableMetaDataProvider
 */
public class TableParameterMetaData {

	private final String parameterName;

	private final int sqlType;

	private final boolean nullable;


	/**
	 * Constructor taking all the properties.
	 */
	/**
	 * 构造函数采用所有属性。 
	 * 
	 */
	public TableParameterMetaData(String columnName, int sqlType, boolean nullable) {
		this.parameterName = columnName;
		this.sqlType = sqlType;
		this.nullable = nullable;
	}


	/**
	 * Get the parameter name.
	 */
	/**
	 * 获取参数名称。 
	 * 
	 */
	public String getParameterName() {
		return this.parameterName;
	}

	/**
	 * Get the parameter SQL type.
	 */
	/**
	 * 获取参数SQL类型。 
	 * 
	 */
	public int getSqlType() {
		return this.sqlType;
	}

	/**
	 * Get whether the parameter/column is nullable.
	 */
	/**
	 * 获取参数/列是否可为空。 
	 * 
	 */
	public boolean isNullable() {
		return this.nullable;
	}

}
