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

package org.springframework.core;

import org.springframework.lang.Nullable;

/**
 * Helper class for implementing exception classes which are capable of
 * holding nested exceptions. Necessary because we can't share a base
 * class among different exception types.
 *
 * <p>Mainly for use within the framework.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see NestedRuntimeException
 * @see NestedCheckedException
 * @see NestedIOException
 * @see org.springframework.web.util.NestedServletException
 */
/**
 * 用于实现能够容纳嵌套异常的异常类的Helper类。 
 * 这是必需的，因为我们不能在不同的异常类型之间共享基类。 
 *  <p>主要在框架内使用。 
 *  @author  Juergen Hoeller @since 2.0起
 * @see  NestedRuntimeException 
 * @see  NestedCheckedException 
 * @see  NestedIOException 
 * @see  org.springframework.web.util.NestedServletException
 */
public abstract class NestedExceptionUtils {

	/**
	 * Build a message for the given base message and root cause.
	 * @param message the base message
	 * @param cause the root cause
	 * @return the full exception message
	 */
	/**
	 * 为给定的基本消息和根本原因构建消息。 
	 *  
	 * @param 消息基本消息
	 * @param 引起根本原因
	 * @return 完整异常消息
	 */
	@Nullable
	public static String buildMessage(@Nullable String message, @Nullable Throwable cause) {
		if (cause == null) {
			return message;
		}
		StringBuilder sb = new StringBuilder(64);
		if (message != null) {
			sb.append(message).append("; ");
		}
		sb.append("nested exception is ").append(cause);
		return sb.toString();
	}

	/**
	 * Retrieve the innermost cause of the given exception, if any.
	 * @param original the original exception to introspect
	 * @return the innermost exception, or {@code null} if none
	 * @since 4.3.9
	 */
	/**
	 * 检索给定异常的最深层原因（如果有）。 
	 *  
	 * @param 最初是用于反省
	 * @return 最内层异常的原始异常，如果没有，则为{@code  null}（自4.3.9起）
	 */
	@Nullable
	public static Throwable getRootCause(@Nullable Throwable original) {
		if (original == null) {
			return null;
		}
		Throwable rootCause = null;
		Throwable cause = original.getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}

	/**
	 * Retrieve the most specific cause of the given exception, that is,
	 * either the innermost cause (root cause) or the exception itself.
	 * <p>Differs from {@link #getRootCause} in that it falls back
	 * to the original exception if there is no root cause.
	 * @param original the original exception to introspect
	 * @return the most specific cause (never {@code null})
	 * @since 4.3.9
	 */
	/**
	 * 检索给定异常的最具体原因，即最内部的原因（根本原因）或异常本身。 
	 *  <p>与{@link  #getRootCause}的不同之处在于，如果没有根本原因，它将退回到原始异常。 
	 *  
	 * @param 最初是自省的异常，<
	 * @return>最具体的原因（从未{@code  null}）@4.3.9起
	 */
	public static Throwable getMostSpecificCause(Throwable original) {
		Throwable rootCause = getRootCause(original);
		return (rootCause != null ? rootCause : original);
	}

}
