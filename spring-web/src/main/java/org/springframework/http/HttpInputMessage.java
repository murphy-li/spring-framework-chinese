/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents an HTTP input message, consisting of {@linkplain #getHeaders() headers}
 * and a readable {@linkplain #getBody() body}.
 *
 * <p>Typically implemented by an HTTP request handle on the server side,
 * or an HTTP response handle on the client side.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
/**
 * 表示HTTP输入消息，由{@link  plain #getHeaders（）标头}和可读的{@link  plain #getBody（）主体}组成。 
 *  <p>通常由服务器端的HTTP请求句柄或客户端的HTTP响应句柄实现。 
 *  @author  Arjen Poutsma @从3.0开始
 */
public interface HttpInputMessage extends HttpMessage {

	/**
	 * Return the body of the message as an input stream.
	 * @return the input stream body (never {@code null})
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 返回消息的正文作为输入流。 
	 *  
	 * @return 输入流主体（永不{<@@code> null}）
	 * @throws  IOException（如果发生I / O错误）
	 */
	InputStream getBody() throws IOException;

}
