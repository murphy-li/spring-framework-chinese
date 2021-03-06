/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jdbc.datasource.init;

/**
 * Thrown when we cannot determine anything more specific than "something went
 * wrong while processing an SQL script": for example, a {@link java.sql.SQLException}
 * from JDBC that we cannot pinpoint more precisely.
 *
 * @author Sam Brannen
 * @since 4.0.3
 */
/**
 * 当我们无法确定比"处理SQL脚本时出了点问题"更具体的事情时，抛出该异常：例如，来自JDBC的{@link  java.sql.SQLException}，我们无法更精确地指出。 
 *  @author  Sam Brannen @自4.0.3起
 */
@SuppressWarnings("serial")
public class UncategorizedScriptException extends ScriptException {

	/**
	 * Construct a new {@code UncategorizedScriptException}.
	 * @param message detailed message
	 */
	/**
	 * 构造一个新的{@code  UncategorizedScriptException}。 
	 *  
	 * @param 消息详细消息
	 */
	public UncategorizedScriptException(String message) {
		super(message);
	}

	/**
	 * Construct a new {@code UncategorizedScriptException}.
	 * @param message detailed message
	 * @param cause the root cause
	 */
	/**
	 * 构造一个新的{@code  UncategorizedScriptException}。 
	 *  
	 * @param 消息详细消息
	 * @param 原因是根本原因
	 */
	public UncategorizedScriptException(String message, Throwable cause) {
		super(message, cause);
	}

}
