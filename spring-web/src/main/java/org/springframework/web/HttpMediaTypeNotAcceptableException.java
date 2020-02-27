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

package org.springframework.web;

import java.util.List;

import org.springframework.http.MediaType;

/**
 * Exception thrown when the request handler cannot generate a response that is acceptable by the client.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
/**
 * 当请求处理程序无法生成客户端可接受的响应时引发的异常。 
 *  @author  Arjen Poutsma @从3.0开始
 */
@SuppressWarnings("serial")
public class HttpMediaTypeNotAcceptableException extends HttpMediaTypeException {

	/**
	 * Create a new HttpMediaTypeNotAcceptableException.
	 * @param message the exception message
	 */
	/**
	 * 创建一个新的HttpMediaTypeNotAcceptableException。 
	 *  
	 * @param 消息异常消息
	 */
	public HttpMediaTypeNotAcceptableException(String message) {
		super(message);
	}

	/**
	 * Create a new HttpMediaTypeNotSupportedException.
	 * @param supportedMediaTypes the list of supported media types
	 */
	/**
	 * 创建一个新的HttpMediaTypeNotSupportedException。 
	 *  
	 * @param  supportedMediaTypes支持的媒体类型列表
	 */
	public HttpMediaTypeNotAcceptableException(List<MediaType> supportedMediaTypes) {
		super("Could not find acceptable representation", supportedMediaTypes);
	}

}
