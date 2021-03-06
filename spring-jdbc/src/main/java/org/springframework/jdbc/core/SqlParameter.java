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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Object to represent an SQL parameter definition.
 *
 * <p>Parameters may be anonymous, in which case "name" is {@code null}.
 * However, all parameters must define an SQL type according to {@link java.sql.Types}.
 *
 * @author Rod Johnson
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @see java.sql.Types
 */
/**
 * 表示SQL参数定义的对象。 
 *  <p>参数可以是匿名的，在这种情况下，"名称"为{@code  null}。 
 * 但是，所有参数都必须根据{@link  java.sql.Types}定义SQL类型。 
 *  @author  Rod Johnson @author  Thomas Risberg @author  Juergen Hoeller 
 * @see  java.sql。 
 * 类型
 */
public class SqlParameter {

	// The name of the parameter, if any
	@Nullable
	private String name;

	// SQL type constant from {@code java.sql.Types}
	private final int sqlType;

	// Used for types that are user-named like: STRUCT, DISTINCT, JAVA_OBJECT, named array types
	@Nullable
	private String typeName;

	// The scale to apply in case of a NUMERIC or DECIMAL type, if any
	@Nullable
	private Integer scale;


	/**
	 * Create a new anonymous SqlParameter, supplying the SQL type.
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 */
	/**
	 * 创建一个提供SQL类型的新匿名SqlParameter。 
	 *  
	 * @param  sqlType根据{@code  java.sql.Types}的参数的SQL类型。 
	 * 
	 */
	public SqlParameter(int sqlType) {
		this.sqlType = sqlType;
	}

	/**
	 * Create a new anonymous SqlParameter, supplying the SQL type.
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 * @param typeName the type name of the parameter (optional)
	 */
	/**
	 * 创建一个提供SQL类型的新匿名SqlParameter。 
	 *  
	 * @param  sqlType根据{@code  java.sql.Types}的参数的SQL类型。 
	 * 
	 * @param  typeName参数的类型名称（可选）
	 */
	public SqlParameter(int sqlType, @Nullable String typeName) {
		this.sqlType = sqlType;
		this.typeName = typeName;
	}

	/**
	 * Create a new anonymous SqlParameter, supplying the SQL type.
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 * @param scale the number of digits after the decimal point
	 * (for DECIMAL and NUMERIC types)
	 */
	/**
	 * 创建一个提供SQL类型的新匿名SqlParameter。 
	 *  
	 * @param  sqlType根据{@code  java.sql.Types}的参数的SQL类型。 
	 * 
	 * @param 缩放小数点后的位数（对于DECIMAL和NUMERIC类型）
	 */
	public SqlParameter(int sqlType, int scale) {
		this.sqlType = sqlType;
		this.scale = scale;
	}

	/**
	 * Create a new SqlParameter, supplying name and SQL type.
	 * @param name name of the parameter, as used in input and output maps
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 */
	/**
	 * 创建一个新的SqlParameter，提供名称和SQL类型。 
	 *  
	 * @param 名称输入和输出映射中使用的参数名称。 
	 * 
	 * @param  sqlType根据{@code  java.sql.Types}，参数的SQL类型。 
	 * 
	 */
	public SqlParameter(String name, int sqlType) {
		this.name = name;
		this.sqlType = sqlType;
	}

	/**
	 * Create a new SqlParameter, supplying name and SQL type.
	 * @param name name of the parameter, as used in input and output maps
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 * @param typeName the type name of the parameter (optional)
	 */
	/**
	 * 创建一个新的SqlParameter，提供名称和SQL类型。 
	 *  
	 * @param 名称输入和输出映射中使用的参数名称。 
	 * 
	 * @param  sqlType根据{@code  java.sql.Types} 
	 * @param  typeName的类型名称，指定参数的SQL类型。 
	 * 参数（可选）
	 */
	public SqlParameter(String name, int sqlType, @Nullable String typeName) {
		this.name = name;
		this.sqlType = sqlType;
		this.typeName = typeName;
	}

	/**
	 * Create a new SqlParameter, supplying name and SQL type.
	 * @param name name of the parameter, as used in input and output maps
	 * @param sqlType the SQL type of the parameter according to {@code java.sql.Types}
	 * @param scale the number of digits after the decimal point
	 * (for DECIMAL and NUMERIC types)
	 */
	/**
	 * 创建一个新的SqlParameter，提供名称和SQL类型。 
	 *  
	 * @param 名称输入和输出映射中使用的参数名称
	 * @param  sqlType根据{@code  java.sql.Types} 
	 * @param 缩放参数的SQL类型小数点后（对于DECIMAL和NUMERIC类型）
	 */
	public SqlParameter(String name, int sqlType, int scale) {
		this.name = name;
		this.sqlType = sqlType;
		this.scale = scale;
	}

	/**
	 * Copy constructor.
	 * @param otherParam the SqlParameter object to copy from
	 */
	/**
	 * 复制构造函数。 
	 *  
	 * @param  otherParam要复制的SqlParameter对象
	 */
	public SqlParameter(SqlParameter otherParam) {
		Assert.notNull(otherParam, "SqlParameter object must not be null");
		this.name = otherParam.name;
		this.sqlType = otherParam.sqlType;
		this.typeName = otherParam.typeName;
		this.scale = otherParam.scale;
	}


	/**
	 * Return the name of the parameter, or {@code null} if anonymous.
	 */
	/**
	 * 返回参数的名称； 
	 * 如果匿名，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	public String getName() {
		return this.name;
	}

	/**
	 * Return the SQL type of the parameter.
	 */
	/**
	 * 返回参数的SQL类型。 
	 * 
	 */
	public int getSqlType() {
		return this.sqlType;
	}

	/**
	 * Return the type name of the parameter, if any.
	 */
	/**
	 * 返回参数的类型名称（如果有）。 
	 * 
	 */
	@Nullable
	public String getTypeName() {
		return this.typeName;
	}

	/**
	 * Return the scale of the parameter, if any.
	 */
	/**
	 * 返回参数的小数位数（如果有）。 
	 * 
	 */
	@Nullable
	public Integer getScale() {
		return this.scale;
	}


	/**
	 * Return whether this parameter holds input values that should be set
	 * before execution even if they are {@code null}.
	 * <p>This implementation always returns {@code true}.
	 */
	/**
	 * 返回此参数是否保留应在执行之前设置的输入值，即使它们是{@code  null}。 
	 *  <p>此实现始终返回{@code  true}。 
	 * 
	 */
	public boolean isInputValueProvided() {
		return true;
	}

	/**
	 * Return whether this parameter is an implicit return parameter used during the
	 * results processing of {@code CallableStatement.getMoreResults/getUpdateCount}.
	 * <p>This implementation always returns {@code false}.
	 */
	/**
	 * 返回此参数是否为{@code  CallableStatement.getMoreResults / getUpdateCount}的结果处理期间使用的隐式返回参数。 
	 *  <p>此实现始终返回{@code  false}。 
	 * 
	 */
	public boolean isResultsParameter() {
		return false;
	}


	/**
	 * Convert a list of JDBC types, as defined in {@code java.sql.Types},
	 * to a List of SqlParameter objects as used in this package.
	 */
	/**
	 * 将{@code  java.sql.Types}中定义的JDBC类型的列表转换为此包中使用的SqlParameter对象的列表。 
	 * 
	 */
	public static List<SqlParameter> sqlTypesToAnonymousParameterList(@Nullable int... types) {
		if (types == null) {
			return new LinkedList<>();
		}
		List<SqlParameter> result = new ArrayList<>(types.length);
		for (int type : types) {
			result.add(new SqlParameter(type));
		}
		return result;
	}

}
