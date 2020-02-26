/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.dao;

/**
 * Data access exception thrown when a result was expected to have at least
 * one row (or element) but zero rows (or elements) were actually returned.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see IncorrectResultSizeDataAccessException
 */
/**
 * 当预期结果至少有一行（或元素）但实际上有零行（或元素）时，抛出数据访问异常。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@从2.0开始
 * @see  IncorrectResultSizeDataAccessException
 */
@SuppressWarnings("serial")
public class EmptyResultDataAccessException extends IncorrectResultSizeDataAccessException {

	/**
	 * Constructor for EmptyResultDataAccessException.
	 * @param expectedSize the expected result size
	 */
	/**
	 * EmptyResultDataAccessException的构造方法。 
	 *  
	 * @param  ExpectedSize预期结果的大小
	 */
	public EmptyResultDataAccessException(int expectedSize) {
		super(expectedSize, 0);
	}

	/**
	 * Constructor for EmptyResultDataAccessException.
	 * @param msg the detail message
	 * @param expectedSize the expected result size
	 */
	/**
	 * EmptyResultDataAccessException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param  ExpectedSize预期结果大小
	 */
	public EmptyResultDataAccessException(String msg, int expectedSize) {
		super(msg, expectedSize, 0);
	}

	/**
	 * Constructor for EmptyResultDataAccessException.
	 * @param msg the detail message
	 * @param expectedSize the expected result size
	 * @param ex the wrapped exception
	 */
	/**
	 * EmptyResultDataAccessException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param  ExpectedSize预期结果大小
	 * @param 除包装的异常外
	 */
	public EmptyResultDataAccessException(String msg, int expectedSize, Throwable ex) {
		super(msg, expectedSize, 0, ex);
	}

}
