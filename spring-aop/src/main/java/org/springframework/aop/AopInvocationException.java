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

package org.springframework.aop;

import org.springframework.core.NestedRuntimeException;

/**
 * Exception that gets thrown when an AOP invocation failed
 * because of misconfiguration or unexpected runtime issues.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 由于配置错误或意外的运行时问题而导致AOP调用失败时引发的异常。 
 *  @author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public class AopInvocationException extends NestedRuntimeException {

	/**
	 * Constructor for AopInvocationException.
	 * @param msg the detail message
	 */
	/**
	 * AopInvocationException的构造方法。 
	 *  @param msg详细信息
	 */
	public AopInvocationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for AopInvocationException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	/**
	 * AopInvocationException的构造方法。 
	 *  @param msg详细信息@param原因
	 */
	public AopInvocationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
