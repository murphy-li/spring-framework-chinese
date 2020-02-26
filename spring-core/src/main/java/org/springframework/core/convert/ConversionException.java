/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2010 the original author or authors.
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
 * 版权所有2002-2010年的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.convert;

import org.springframework.core.NestedRuntimeException;

/**
 * Base class for exceptions thrown by the conversion system.
 *
 * @author Keith Donald
 * @since 3.0
 */
/**
 * 转换系统引发的异常的基类。 
 *  @author 基思·唐纳德@since 3.0
 */
@SuppressWarnings("serial")
public abstract class ConversionException extends NestedRuntimeException {

	/**
	 * Construct a new conversion exception.
	 * @param message the exception message
	 */
	/**
	 * 构造一个新的转换异常。 
	 *  
	 * @param 消息异常消息
	 */
	public ConversionException(String message) {
		super(message);
	}

	/**
	 * Construct a new conversion exception.
	 * @param message the exception message
	 * @param cause the cause
	 */
	/**
	 * 构造一个新的转换异常。 
	 *  
	 * @param 消息异常消息
	 * @param 引起原因
	 */
	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}

}
