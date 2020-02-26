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

package org.springframework.jca.cci;

import java.sql.SQLException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * Exception thrown when a ResultSet has been accessed in an invalid fashion.
 * Such exceptions always have a {@code java.sql.SQLException} root cause.
 *
 * <p>This typically happens when an invalid ResultSet column index or name
 * has been specified.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @see javax.resource.cci.ResultSet
 */
/**
 * 以无效方式访问ResultSet时引发的异常。 
 * 此类异常始终具有{@code  java.sql.SQLException}根本原因。 
 *  <p>通常在指定了无效的ResultSet列索引或名称时发生。 
 *  @author  Juergen Hoeller @自1.2起
 * @see  javax.resource.cci.ResultSet
 */
@SuppressWarnings("serial")
public class InvalidResultSetAccessException extends InvalidDataAccessResourceUsageException {

	/**
	 * Constructor for InvalidResultSetAccessException.
	 * @param msg message
	 * @param ex the root cause
	 */
	/**
	 * InvalidResultSetAccessException的构造方法。 
	 *  
	 * @param  msg消息
	 * @param 根本原因
	 */
	public InvalidResultSetAccessException(String msg, SQLException ex) {
		super(ex.getMessage(), ex);
	}

}
