/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.orm.hibernate5;

import org.hibernate.QueryException;

import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * Hibernate-specific subclass of InvalidDataAccessResourceUsageException,
 * thrown on invalid HQL query syntax.
 *
 * @author Juergen Hoeller
 * @since 4.2
 * @see SessionFactoryUtils#convertHibernateAccessException
 */
/**
 * 无效的HQL查询语法引发的InvalidDataAccessResourceUsageException的特定于Hibernate的子类。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@从4.2开始
 * @see  SessionFactoryUtils＃convertHibernateAccessException
 */
@SuppressWarnings("serial")
public class HibernateQueryException extends InvalidDataAccessResourceUsageException {

	public HibernateQueryException(QueryException ex) {
		super(ex.getMessage(), ex);
	}

	/**
	 * Return the HQL query string that was invalid.
	 */
	/**
	 * 返回无效的HQL查询字符串。 
	 * 
	 */
	public String getQueryString() {
		return ((QueryException) getCause()).getQueryString();
	}

}
