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

package org.springframework.core.codec;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

/**
 * General error that indicates a problem while encoding and decoding to and
 * from an Object stream.
 *
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 常规错误，指示在与对象流之间进行编码和解码时出现问题。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Rossen Stoyanchev @从5.0开始
 */
@SuppressWarnings("serial")
public class CodecException extends NestedRuntimeException {

	/**
	 * Create a new CodecException.
	 * @param msg the detail message
	 */
	/**
	 * 创建一个新的CodecException。 
	 *  
	 * @param  msg详细信息
	 */
	public CodecException(String msg) {
		super(msg);
	}

	/**
	 * Create a new CodecException.
	 * @param msg the detail message
	 * @param cause root cause for the exception, if any
	 */
	/**
	 * 创建一个新的CodecException。 
	 *  
	 * @param  msg详细消息
	 * @param 导致异常的根本原因（如果有）
	 */
	public CodecException(String msg, @Nullable Throwable cause) {
		super(msg, cause);
	}

}
