/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.transaction;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Exception thrown when a general transaction system error is encountered,
 * like on commit or rollback.
 *
 * @author Juergen Hoeller
 * @since 24.03.2003
 */
/**
 * 遇到一般事务系统错误时（例如在提交或回滚时）引发的异常。 
 *  @author  Juergen Hoeller @自24.03.2003起
 */
@SuppressWarnings("serial")
public class TransactionSystemException extends TransactionException {

	@Nullable
	private Throwable applicationException;


	/**
	 * Constructor for TransactionSystemException.
	 * @param msg the detail message
	 */
	/**
	 * TransactionSystemException的构造方法。 
	 *  
	 * @param  msg详细信息
	 */
	public TransactionSystemException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for TransactionSystemException.
	 * @param msg the detail message
	 * @param cause the root cause from the transaction API in use
	 */
	/**
	 * TransactionSystemException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 原因是所使用的事务API的根本原因
	 */
	public TransactionSystemException(String msg, Throwable cause) {
		super(msg, cause);
	}


	/**
	 * Set an application exception that was thrown before this transaction exception,
	 * preserving the original exception despite the overriding TransactionSystemException.
	 * @param ex the application exception
	 * @throws IllegalStateException if this TransactionSystemException already holds an
	 * application exception
	 */
	/**
	 * 设置在此事务异常之前引发的应用程序异常，尽管重写了TransactionSystemException，但仍保留原始异常。 
	 *  
	 * @param 应用程序异常
	 * @throws  IllegalStateException如果此TransactionSystemException已包含应用程序异常
	 */
	public void initApplicationException(Throwable ex) {
		Assert.notNull(ex, "Application exception must not be null");
		if (this.applicationException != null) {
			throw new IllegalStateException("Already holding an application exception: " + this.applicationException);
		}
		this.applicationException = ex;
	}

	/**
	 * Return the application exception that was thrown before this transaction exception,
	 * if any.
	 * @return the application exception, or {@code null} if none set
	 */
	/**
	 * 返回在此事务异常之前引发的应用程序异常（如果有）。 
	 *  
	 * @return 应用程序异常，如果未设置，则为{@code  null}
	 */
	@Nullable
	public final Throwable getApplicationException() {
		return this.applicationException;
	}

	/**
	 * Return the exception that was the first to be thrown within the failed transaction:
	 * i.e. the application exception, if any, or the TransactionSystemException's own cause.
	 * @return the original exception, or {@code null} if there was none
	 */
	/**
	 * 返回在失败的事务中第一个引发的异常：即应用程序异常（如果有）或TransactionSystemException自身原因。 
	 *  
	 * @return 原始异常，如果没有则为{@code  null}
	 */
	@Nullable
	public Throwable getOriginalException() {
		return (this.applicationException != null ? this.applicationException : getCause());
	}

	@Override
	public boolean contains(@Nullable Class<?> exType) {
		return super.contains(exType) || (exType != null && exType.isInstance(this.applicationException));
	}

}
