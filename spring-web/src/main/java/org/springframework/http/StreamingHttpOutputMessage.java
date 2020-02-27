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
import java.io.OutputStream;

/**
 * Represents an HTTP output message that allows for setting a streaming body.
 * Note that such messages typically do not support {@link #getBody()} access.
 *
 * @author Arjen Poutsma
 * @since 4.0
 * @see #setBody
 */
/**
 * 表示允许设置流主体的HTTP输出消息。 
 * 请注意，此类消息通常不支持{@link  #getBody（）}访问。 
 *  @author  Arjen Poutsma @从4.0开始
 * @see  #setBody
 */
public interface StreamingHttpOutputMessage extends HttpOutputMessage {

	/**
	 * Set the streaming body callback for this message.
	 * @param body the streaming body callback
	 */
	/**
	 * 设置此消息的流式主体回调。 
	 *  
	 * @param 正文流式主体回调
	 */
	void setBody(Body body);


	/**
	 * Defines the contract for bodies that can be written directly to an
	 * {@link OutputStream}. Useful with HTTP client libraries that provide
	 * indirect access to an {@link OutputStream} via a callback mechanism.
	 */
	/**
	 * 为可以直接写入{@link  OutputStream}的主体定义合同。 
	 * 对于HTTP客户端库很有用，该客户端库通过回调机制提供对{@link  OutputStream}的间接访问。 
	 * 
	 */
	@FunctionalInterface
	interface Body {

		/**
		 * Write this body to the given {@link OutputStream}.
		 * @param outputStream the output stream to write to
		 * @throws IOException in case of I/O errors
		 */
		/**
		 * 将此正文写入给定的{@link  OutputStream}。 
		 *  
		 * @param  outputStream输出流，以在发生I / O错误时写入
		 * @throws  IOException
		 */
		void writeTo(OutputStream outputStream) throws IOException;
	}

}
