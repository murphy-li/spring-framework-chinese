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

package org.springframework.jdbc.core.namedparam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Helper methods for named parameter parsing.
 *
 * <p>Only intended for internal use within Spring's JDBC framework.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 用于命名参数解析的辅助方法。 
 *  <p>仅供Spring的JDBC框架内部使用。 
 *  @author 托马斯·里斯伯格@author  Juergen Hoeller @since 2.0
 */
public abstract class NamedParameterUtils {

	/**
	 * Set of characters that qualify as comment or quotes starting characters.
	 */
	/**
	 * 符合注释或引号起始字符的字符集。 
	 * 
	 */
	private static final String[] START_SKIP = new String[] {"'", "\"", "--", "/*"};

	/**
	 * Set of characters that at are the corresponding comment or quotes ending characters.
	 */
/**
 * "};在处的字符集是相应的注释或引号结尾的字符。 
 * 
 */
	private static final String[] STOP_SKIP = new String[] {"'", "\"", "\n", "*/"};

	/**
	 * Set of characters that qualify as parameter separators,
	 * indicating that a parameter name in an SQL String has ended.
	 */
	/**
	 * 可以用作参数分隔符的字符集，指示SQL字符串中的参数名称已结束。 
	 * 
	 */
	private static final String PARAMETER_SEPARATORS = "\"':&,;()|=+-*%/\\<>^";

	/**
	 * An index with separator flags per character code.
	 * Technically only needed between 34 and 124 at this point.
	 */
	/**
	 * 每个字符代码带有分隔符标志的索引。 
	 * 此时技术上仅需要34到124之间。 
	 * 
	 */
	private static final boolean[] separatorIndex = new boolean[128];

	static {
		for (char c : PARAMETER_SEPARATORS.toCharArray()) {
			separatorIndex[c] = true;
		}
	}


	//-------------------------------------------------------------------------
	// Core methods used by NamedParameterJdbcTemplate and SqlQuery/SqlUpdate
	//-------------------------------------------------------------------------

	/**
	 * Parse the SQL statement and locate any placeholders or named parameters.
	 * Named parameters are substituted for a JDBC placeholder.
	 * @param sql the SQL statement
	 * @return the parsed statement, represented as ParsedSql instance
	 */
	/**
	 * 解析SQL语句并找到任何占位符或命名参数。 
	 * 命名参数代替JDBC占位符。 
	 *  
	 * @param  sql SQL语句
	 * @return 解析的语句，表示为ParsedSql实例
	 */
	public static ParsedSql parseSqlStatement(final String sql) {
		Assert.notNull(sql, "SQL must not be null");

		Set<String> namedParameters = new HashSet<>();
		String sqlToUse = sql;
		List<ParameterHolder> parameterList = new ArrayList<>();

		char[] statement = sql.toCharArray();
		int namedParameterCount = 0;
		int unnamedParameterCount = 0;
		int totalParameterCount = 0;

		int escapes = 0;
		int i = 0;
		while (i < statement.length) {
			int skipToPosition = i;
			while (i < statement.length) {
				skipToPosition = skipCommentsAndQuotes(statement, i);
				if (i == skipToPosition) {
					break;
				}
				else {
					i = skipToPosition;
				}
			}
			if (i >= statement.length) {
				break;
			}
			char c = statement[i];
			if (c == ':' || c == '&') {
				int j = i + 1;
				if (c == ':' && j < statement.length && statement[j] == ':') {
					// Postgres-style "::" casting operator should be skipped
					i = i + 2;
					continue;
				}
				String parameter = null;
				if (c == ':' && j < statement.length && statement[j] == '{') {
					// :{x} style parameter
					while (statement[j] != '}') {
						j++;
						if (j >= statement.length) {
							throw new InvalidDataAccessApiUsageException("Non-terminated named parameter declaration " +
									"at position " + i + " in statement: " + sql);
						}
						if (statement[j] == ':' || statement[j] == '{') {
							throw new InvalidDataAccessApiUsageException("Parameter name contains invalid character '" +
									statement[j] + "' at position " + i + " in statement: " + sql);
						}
					}
					if (j - i > 2) {
						parameter = sql.substring(i + 2, j);
						namedParameterCount = addNewNamedParameter(namedParameters, namedParameterCount, parameter);
						totalParameterCount = addNamedParameter(
								parameterList, totalParameterCount, escapes, i, j + 1, parameter);
					}
					j++;
				}
				else {
					while (j < statement.length && !isParameterSeparator(statement[j])) {
						j++;
					}
					if (j - i > 1) {
						parameter = sql.substring(i + 1, j);
						namedParameterCount = addNewNamedParameter(namedParameters, namedParameterCount, parameter);
						totalParameterCount = addNamedParameter(
								parameterList, totalParameterCount, escapes, i, j, parameter);
					}
				}
				i = j - 1;
			}
			else {
				if (c == '\\') {
					int j = i + 1;
					if (j < statement.length && statement[j] == ':') {
						// escaped ":" should be skipped
						sqlToUse = sqlToUse.substring(0, i - escapes) + sqlToUse.substring(i - escapes + 1);
						escapes++;
						i = i + 2;
						continue;
					}
				}
				if (c == '?') {
					int j = i + 1;
					if (j < statement.length && (statement[j] == '?' || statement[j] == '|' || statement[j] == '&')) {
						// Postgres-style "??", "?|", "?&" operator should be skipped
						i = i + 2;
						continue;
					}
					unnamedParameterCount++;
					totalParameterCount++;
				}
			}
			i++;
		}
		ParsedSql parsedSql = new ParsedSql(sqlToUse);
		for (ParameterHolder ph : parameterList) {
			parsedSql.addNamedParameter(ph.getParameterName(), ph.getStartIndex(), ph.getEndIndex());
		}
		parsedSql.setNamedParameterCount(namedParameterCount);
		parsedSql.setUnnamedParameterCount(unnamedParameterCount);
		parsedSql.setTotalParameterCount(totalParameterCount);
		return parsedSql;
	}

	private static int addNamedParameter(
			List<ParameterHolder> parameterList, int totalParameterCount, int escapes, int i, int j, String parameter) {

		parameterList.add(new ParameterHolder(parameter, i - escapes, j - escapes));
		totalParameterCount++;
		return totalParameterCount;
	}

	private static int addNewNamedParameter(Set<String> namedParameters, int namedParameterCount, String parameter) {
		if (!namedParameters.contains(parameter)) {
			namedParameters.add(parameter);
			namedParameterCount++;
		}
		return namedParameterCount;
	}

	/**
	 * Skip over comments and quoted names present in an SQL statement.
	 * @param statement character array containing SQL statement
	 * @param position current position of statement
	 * @return next position to process after any comments or quotes are skipped
	 */
	/**
	 * 跳过SQL语句中存在的注释和带引号的名称。 
	 * 包含SQL语句的
	 * @param 语句字符数组
	 * @param 位置语句的当前位置
	 * @return 跳过任何注释或引号后要处理的下一个位置
	 */
	private static int skipCommentsAndQuotes(char[] statement, int position) {
		for (int i = 0; i < START_SKIP.length; i++) {
			if (statement[position] == START_SKIP[i].charAt(0)) {
				boolean match = true;
				for (int j = 1; j < START_SKIP[i].length(); j++) {
					if (statement[position + j] != START_SKIP[i].charAt(j)) {
						match = false;
						break;
					}
				}
				if (match) {
					int offset = START_SKIP[i].length();
					for (int m = position + offset; m < statement.length; m++) {
						if (statement[m] == STOP_SKIP[i].charAt(0)) {
							boolean endMatch = true;
							int endPos = m;
							for (int n = 1; n < STOP_SKIP[i].length(); n++) {
								if (m + n >= statement.length) {
									// last comment not closed properly
									return statement.length;
								}
								if (statement[m + n] != STOP_SKIP[i].charAt(n)) {
									endMatch = false;
									break;
								}
								endPos = m + n;
							}
							if (endMatch) {
								// found character sequence ending comment or quote
								return endPos + 1;
							}
						}
					}
					// character sequence ending comment or quote not found
					return statement.length;
				}
			}
		}
		return position;
	}

	/**
	 * Parse the SQL statement and locate any placeholders or named parameters. Named
	 * parameters are substituted for a JDBC placeholder, and any select list is expanded
	 * to the required number of placeholders. Select lists may contain an array of
	 * objects, and in that case the placeholders will be grouped and enclosed with
	 * parentheses. This allows for the use of "expression lists" in the SQL statement
	 * like: <br /><br />
	 * {@code select id, name, state from table where (name, age) in (('John', 35), ('Ann', 50))}
	 * <p>The parameter values passed in are used to determine the number of placeholders to
	 * be used for a select list. Select lists should be limited to 100 or fewer elements.
	 * A larger number of elements is not guaranteed to be supported by the database and
	 * is strictly vendor-dependent.
	 * @param parsedSql the parsed representation of the SQL statement
	 * @param paramSource the source for named parameters
	 * @return the SQL statement with substituted parameters
	 * @see #parseSqlStatement
	 */
	/**
	 * 解析SQL语句并找到任何占位符或命名参数。 
	 * 命名参数将替换JDBC占位符，并且任何选择列表都将扩展为所需数量的占位符。 
	 * 选择列表可能包含对象数组，在这种情况下，占位符将被分组并用括号括起来。 
	 * 这样就可以在SQL语句中使用"表达式列表"，例如：<br /> <br /> {@code 从表中的（name，age）中的（（'John'， 35），（'Ann'，50））} <p>传入的参数值用于确定用于选择列表的占位符数量。 
	 * 选择列表应限制为100个或更少的元素。 
	 * 数据库不能保证支持大量元素，并且元素严格取决于供应商。 
	 *  
	 * @param  parsedSql SQL语句的解析表示形式
	 * @param  paramSource命名参数的源
	 * @return 带有替换参数的SQL语句
	 * @see  #parseSqlStatement
	 */
	public static String substituteNamedParameters(ParsedSql parsedSql, @Nullable SqlParameterSource paramSource) {
		String originalSql = parsedSql.getOriginalSql();
		List<String> paramNames = parsedSql.getParameterNames();
		if (paramNames.isEmpty()) {
			return originalSql;
		}
		StringBuilder actualSql = new StringBuilder(originalSql.length());
		int lastIndex = 0;
		for (int i = 0; i < paramNames.size(); i++) {
			String paramName = paramNames.get(i);
			int[] indexes = parsedSql.getParameterIndexes(i);
			int startIndex = indexes[0];
			int endIndex = indexes[1];
			actualSql.append(originalSql, lastIndex, startIndex);
			if (paramSource != null && paramSource.hasValue(paramName)) {
				Object value = paramSource.getValue(paramName);
				if (value instanceof SqlParameterValue) {
					value = ((SqlParameterValue) value).getValue();
				}
				if (value instanceof Iterable) {
					Iterator<?> entryIter = ((Iterable<?>) value).iterator();
					int k = 0;
					while (entryIter.hasNext()) {
						if (k > 0) {
							actualSql.append(", ");
						}
						k++;
						Object entryItem = entryIter.next();
						if (entryItem instanceof Object[]) {
							Object[] expressionList = (Object[]) entryItem;
							actualSql.append('(');
							for (int m = 0; m < expressionList.length; m++) {
								if (m > 0) {
									actualSql.append(", ");
								}
								actualSql.append('?');
							}
							actualSql.append(')');
						}
						else {
							actualSql.append('?');
						}
					}
				}
				else {
					actualSql.append('?');
				}
			}
			else {
				actualSql.append('?');
			}
			lastIndex = endIndex;
		}
		actualSql.append(originalSql, lastIndex, originalSql.length());
		return actualSql.toString();
	}

	/**
	 * Convert a Map of named parameter values to a corresponding array.
	 * @param parsedSql the parsed SQL statement
	 * @param paramSource the source for named parameters
	 * @param declaredParams the List of declared SqlParameter objects
	 * (may be {@code null}). If specified, the parameter metadata will
	 * be built into the value array in the form of SqlParameterValue objects.
	 * @return the array of values
	 */
	/**
	 * 将命名参数值的映射转换为相应的数组。 
	 *  
	 * @param  parsedSql解析的SQL语句
	 * @param  paramSource命名参数的源
	 * @param  claredParams声明的SqlParameter对象的列表（可以为{@code  null}）。 
	 * 如果指定，参数元数据将以SqlParameterValue对象的形式内置到值数组中。 
	 *  
	 * @return 值数组
	 */
	public static Object[] buildValueArray(
			ParsedSql parsedSql, SqlParameterSource paramSource, @Nullable List<SqlParameter> declaredParams) {

		Object[] paramArray = new Object[parsedSql.getTotalParameterCount()];
		if (parsedSql.getNamedParameterCount() > 0 && parsedSql.getUnnamedParameterCount() > 0) {
			throw new InvalidDataAccessApiUsageException(
					"Not allowed to mix named and traditional ? placeholders. You have " +
					parsedSql.getNamedParameterCount() + " named parameter(s) and " +
					parsedSql.getUnnamedParameterCount() + " traditional placeholder(s) in statement: " +
					parsedSql.getOriginalSql());
		}
		List<String> paramNames = parsedSql.getParameterNames();
		for (int i = 0; i < paramNames.size(); i++) {
			String paramName = paramNames.get(i);
			try {
				Object value = paramSource.getValue(paramName);
				SqlParameter param = findParameter(declaredParams, paramName, i);
				paramArray[i] = (param != null ? new SqlParameterValue(param, value) : value);
			}
			catch (IllegalArgumentException ex) {
				throw new InvalidDataAccessApiUsageException(
						"No value supplied for the SQL parameter '" + paramName + "': " + ex.getMessage());
			}
		}
		return paramArray;
	}

	/**
	 * Find a matching parameter in the given list of declared parameters.
	 * @param declaredParams the declared SqlParameter objects
	 * @param paramName the name of the desired parameter
	 * @param paramIndex the index of the desired parameter
	 * @return the declared SqlParameter, or {@code null} if none found
	 */
	/**
	 * 在声明的参数的给定列表中找到匹配的参数。 
	 *  
	 * @param  clarifiedParams已声明的SqlParameter对象
	 * @param  paramName所需参数的名称
	 * @param  paramIndex所需参数的索引
	 * @return 声明的SqlParameter，如果找不到则为{@code  null}
	 */
	@Nullable
	private static SqlParameter findParameter(
			@Nullable List<SqlParameter> declaredParams, String paramName, int paramIndex) {

		if (declaredParams != null) {
			// First pass: Look for named parameter match.
			for (SqlParameter declaredParam : declaredParams) {
				if (paramName.equals(declaredParam.getName())) {
					return declaredParam;
				}
			}
			// Second pass: Look for parameter index match.
			if (paramIndex < declaredParams.size()) {
				SqlParameter declaredParam = declaredParams.get(paramIndex);
				// Only accept unnamed parameters for index matches.
				if (declaredParam.getName() == null) {
					return declaredParam;
				}
			}
		}
		return null;
	}

	/**
	 * Determine whether a parameter name ends at the current position,
	 * that is, whether the given character qualifies as a separator.
	 */
	/**
	 * 确定参数名称是否在当前位置结束，即，给定字符是否符合分隔符的条件。 
	 * 
	 */
	private static boolean isParameterSeparator(char c) {
		return (c < 128 && separatorIndex[c]) || Character.isWhitespace(c);
	}

	/**
	 * Convert parameter types from an SqlParameterSource into a corresponding int array.
	 * This is necessary in order to reuse existing methods on JdbcTemplate.
	 * Any named parameter types are placed in the correct position in the
	 * Object array based on the parsed SQL statement info.
	 * @param parsedSql the parsed SQL statement
	 * @param paramSource the source for named parameters
	 */
	/**
	 * 将参数类型从SqlParameterSource转换为相应的int数组。 
	 * 为了重用JdbcTemplate上的现有方法，这是必需的。 
	 * 根据已解析的SQL语句信息，将任何命名的参数类型放置在Object数组中的正确位置。 
	 *  
	 * @param  parsedSql解析的SQL语句
	 * @param  paramSource命名参数的来源
	 */
	public static int[] buildSqlTypeArray(ParsedSql parsedSql, SqlParameterSource paramSource) {
		int[] sqlTypes = new int[parsedSql.getTotalParameterCount()];
		List<String> paramNames = parsedSql.getParameterNames();
		for (int i = 0; i < paramNames.size(); i++) {
			String paramName = paramNames.get(i);
			sqlTypes[i] = paramSource.getSqlType(paramName);
		}
		return sqlTypes;
	}

	/**
	 * Convert parameter declarations from an SqlParameterSource to a corresponding List of SqlParameters.
	 * This is necessary in order to reuse existing methods on JdbcTemplate.
	 * The SqlParameter for a named parameter is placed in the correct position in the
	 * resulting list based on the parsed SQL statement info.
	 * @param parsedSql the parsed SQL statement
	 * @param paramSource the source for named parameters
	 */
	/**
	 * 将参数声明从SqlParameterSource转换为相应的SqlParameters列表。 
	 * 为了重用JdbcTemplate上的现有方法，这是必需的。 
	 * 基于解析的SQL语句信息，将命名参数的SqlParameter放置在结果列表中的正确位置。 
	 *  
	 * @param  parsedSql解析的SQL语句
	 * @param  paramSource命名参数的来源
	 */
	public static List<SqlParameter> buildSqlParameterList(ParsedSql parsedSql, SqlParameterSource paramSource) {
		List<String> paramNames = parsedSql.getParameterNames();
		List<SqlParameter> params = new ArrayList<>(paramNames.size());
		for (String paramName : paramNames) {
			params.add(new SqlParameter(
					paramName, paramSource.getSqlType(paramName), paramSource.getTypeName(paramName)));
		}
		return params;
	}


	//-------------------------------------------------------------------------
	// Convenience methods operating on a plain SQL String
	//-------------------------------------------------------------------------

	/**
	 * Parse the SQL statement and locate any placeholders or named parameters.
	 * Named parameters are substituted for a JDBC placeholder.
	 * <p>This is a shortcut version of
	 * {@link #parseSqlStatement(String)} in combination with
	 * {@link #substituteNamedParameters(ParsedSql, SqlParameterSource)}.
	 * @param sql the SQL statement
	 * @return the actual (parsed) SQL statement
	 */
	/**
	 * 解析SQL语句并找到任何占位符或命名参数。 
	 * 命名参数代替JDBC占位符。 
	 *  <p>这是{@link  #parseSqlStatement（String）}与{@link  #substituteNamedParameters（ParsedSql，SqlParameterSource）}组合的快捷方式版本。 
	 *  
	 * @param  sql SQL语句
	 * @return 实际（已解析）SQL语句
	 */
	public static String parseSqlStatementIntoString(String sql) {
		ParsedSql parsedSql = parseSqlStatement(sql);
		return substituteNamedParameters(parsedSql, null);
	}

	/**
	 * Parse the SQL statement and locate any placeholders or named parameters.
	 * Named parameters are substituted for a JDBC placeholder and any select list
	 * is expanded to the required number of placeholders.
	 * <p>This is a shortcut version of
	 * {@link #substituteNamedParameters(ParsedSql, SqlParameterSource)}.
	 * @param sql the SQL statement
	 * @param paramSource the source for named parameters
	 * @return the SQL statement with substituted parameters
	 */
	/**
	 * 解析SQL语句并找到任何占位符或命名参数。 
	 * 命名参数将替换JDBC占位符，并且任何选择列表都将扩展到所需的占位符数量。 
	 *  <p>这是{@link  #substituteNamedParameters（ParsedSql，SqlParameterSource）}的快捷方式版本。 
	 *  
	 * @param  sql SQL语句
	 * @param  paramSource命名参数的源
	 * @return 带有替换参数的SQL语句
	 */
	public static String substituteNamedParameters(String sql, SqlParameterSource paramSource) {
		ParsedSql parsedSql = parseSqlStatement(sql);
		return substituteNamedParameters(parsedSql, paramSource);
	}

	/**
	 * Convert a Map of named parameter values to a corresponding array.
	 * <p>This is a shortcut version of
	 * {@link #buildValueArray(ParsedSql, SqlParameterSource, java.util.List)}.
	 * @param sql the SQL statement
	 * @param paramMap the Map of parameters
	 * @return the array of values
	 */
	/**
	 * 将命名参数值的映射转换为相应的数组。 
	 *  <p>这是{@link  #buildValueArray（ParsedSql，SqlParameterSource，java.util.List）}的快捷方式版本。 
	 *  
	 * @param  sql SQL语句
	 * @param  paramMap参数映射
	 * @return 值数组
	 */
	public static Object[] buildValueArray(String sql, Map<String, ?> paramMap) {
		ParsedSql parsedSql = parseSqlStatement(sql);
		return buildValueArray(parsedSql, new MapSqlParameterSource(paramMap), null);
	}


	private static class ParameterHolder {

		private final String parameterName;

		private final int startIndex;

		private final int endIndex;

		public ParameterHolder(String parameterName, int startIndex, int endIndex) {
			this.parameterName = parameterName;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		public String getParameterName() {
			return this.parameterName;
		}

		public int getStartIndex() {
			return this.startIndex;
		}

		public int getEndIndex() {
			return this.endIndex;
		}
	}

}
