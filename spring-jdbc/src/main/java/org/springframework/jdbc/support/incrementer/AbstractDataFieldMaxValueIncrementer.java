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

package org.springframework.jdbc.support.incrementer;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

/**
 * Base implementation of {@link DataFieldMaxValueIncrementer} that delegates
 * to a single {@link #getNextKey} template method that returns a {@code long}.
 * Uses longs for String values, padding with zeroes if required.
 *
 * @author Dmitriy Kopylenko
 * @author Juergen Hoeller
 * @author Jean-Pierre Pawlak
 * @author Juergen Hoeller
 */
/**
 * {@link  DataFieldMaxValueIncrementer}的基本实现，委派给单个{@link  #getNextKey}模板方法，该模板方法返回一个{@code  long}。 
 * 将long用作String值，如果需要，则以零填充。 
 *  @author  Dmitriy Kopylenko @author 于尔根·霍勒@author  Jean-Pierre Pawlak @author 于尔根·霍勒
 */
public abstract class AbstractDataFieldMaxValueIncrementer implements DataFieldMaxValueIncrementer, InitializingBean {

	private DataSource dataSource;

	/** The name of the sequence/table containing the sequence. */
	/**
	 * 包含序列的序列/表的名称。 
	 * 
	 */
	private String incrementerName;

	/** The length to which a string result should be pre-pended with zeroes. */
	/**
	 * 字符串结果应以零开头的长度。 
	 * 
	 */
	protected int paddingLength = 0;


	/**
	 * Default constructor for bean property style usage.
	 * @see #setDataSource
	 * @see #setIncrementerName
	 */
	/**
	 * Bean属性样式用法的默认构造函数。 
	 *  
	 * @see  #setDataSource 
	 * @see  #setIncrementerName
	 */
	public AbstractDataFieldMaxValueIncrementer() {
	}

	/**
	 * Convenience constructor.
	 * @param dataSource the DataSource to use
	 * @param incrementerName the name of the sequence/table to use
	 */
	/**
	 * 便利的构造函数。 
	 *  
	 * @param  dataSource要使用的数据源
	 * @param 增量器名称要使用的序列/表的名称
	 */
	public AbstractDataFieldMaxValueIncrementer(DataSource dataSource, String incrementerName) {
		Assert.notNull(dataSource, "DataSource must not be null");
		Assert.notNull(incrementerName, "Incrementer name must not be null");
		this.dataSource = dataSource;
		this.incrementerName = incrementerName;
	}


	/**
	 * Set the data source to retrieve the value from.
	 */
	/**
	 * 设置数据源以从中检索值。 
	 * 
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Return the data source to retrieve the value from.
	 */
	/**
	 * 返回数据源以从中检索值。 
	 * 
	 */
	public DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * Set the name of the sequence/table.
	 */
	/**
	 * 设置序列/表的名称。 
	 * 
	 */
	public void setIncrementerName(String incrementerName) {
		this.incrementerName = incrementerName;
	}

	/**
	 * Return the name of the sequence/table.
	 */
	/**
	 * 返回序列/表的名称。 
	 * 
	 */
	public String getIncrementerName() {
		return this.incrementerName;
	}

	/**
	 * Set the padding length, i.e. the length to which a string result
	 * should be pre-pended with zeroes.
	 */
	/**
	 * 设置填充长度，即字符串结果应在前面加上零的长度。 
	 * 
	 */
	public void setPaddingLength(int paddingLength) {
		this.paddingLength = paddingLength;
	}

	/**
	 * Return the padding length for String values.
	 */
	/**
	 * 返回字符串值的填充长度。 
	 * 
	 */
	public int getPaddingLength() {
		return this.paddingLength;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.dataSource == null) {
			throw new IllegalArgumentException("Property 'dataSource' is required");
		}
		if (this.incrementerName == null) {
			throw new IllegalArgumentException("Property 'incrementerName' is required");
		}
	}


	@Override
	public int nextIntValue() throws DataAccessException {
		return (int) getNextKey();
	}

	@Override
	public long nextLongValue() throws DataAccessException {
		return getNextKey();
	}

	@Override
	public String nextStringValue() throws DataAccessException {
		String s = Long.toString(getNextKey());
		int len = s.length();
		if (len < this.paddingLength) {
			StringBuilder sb = new StringBuilder(this.paddingLength);
			for (int i = 0; i < this.paddingLength - len; i++) {
				sb.append('0');
			}
			sb.append(s);
			s = sb.toString();
		}
		return s;
	}


	/**
	 * Determine the next key to use, as a long.
	 * @return the key to use as a long. It will eventually be converted later
	 * in another format by the public concrete methods of this class.
	 */
	/**
	 * 确定要使用的下一个键，很长。 
	 *  
	 * @return 键使用的时间很长。 
	 * 最终它将由此类的公共具体方法以另一种格式转换。 
	 * 
	 */
	protected abstract long getNextKey();

}
