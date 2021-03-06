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

package org.springframework.scheduling.support;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

/**
 * Runnable wrapper that catches any exception or error thrown from its
 * delegate Runnable and allows an {@link ErrorHandler} to handle it.
 *
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 3.0
 */
/**
 * 可运行包装程序，可捕获从其委托Runnable引发的任何异常或错误，并允许{@link  ErrorHandler}对其进行处理。 
 *  @author  Juergen Hoeller @author 马克·费舍尔@since 3.0
 */
public class DelegatingErrorHandlingRunnable implements Runnable {

	private final Runnable delegate;

	private final ErrorHandler errorHandler;


	/**
	 * Create a new DelegatingErrorHandlingRunnable.
	 * @param delegate the Runnable implementation to delegate to
	 * @param errorHandler the ErrorHandler for handling any exceptions
	 */
	/**
	 * 创建一个新的DelegatingErrorHandlingRunnable。 
	 *  
	 * @param 委托Runnable实现将<Handler>委托给
	 * @param  errorHandler用于处理任何异常的ErrorHandler
	 */
	public DelegatingErrorHandlingRunnable(Runnable delegate, ErrorHandler errorHandler) {
		Assert.notNull(delegate, "Delegate must not be null");
		Assert.notNull(errorHandler, "ErrorHandler must not be null");
		this.delegate = delegate;
		this.errorHandler = errorHandler;
	}

	@Override
	public void run() {
		try {
			this.delegate.run();
		}
		catch (UndeclaredThrowableException ex) {
			this.errorHandler.handleError(ex.getUndeclaredThrowable());
		}
		catch (Throwable ex) {
			this.errorHandler.handleError(ex);
		}
	}

	@Override
	public String toString() {
		return "DelegatingErrorHandlingRunnable for " + this.delegate;
	}

}
