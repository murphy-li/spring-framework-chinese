/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.http;

/**
 * Represents the base interface for HTTP request and response messages.
 * Consists of {@link HttpHeaders}, retrievable via {@link #getHeaders()}.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
/**
 * 表示HTTP请求和响应消息的基本接口。 
 * 由{@link  HttpHeaders}组成，可通过{@link  #getHeaders（）}检索。 
 *  @author  Arjen Poutsma @从3.0开始
 */
public interface HttpMessage {

	/**
	 * Return the headers of this message.
	 * @return a corresponding HttpHeaders object (never {@code null})
	 */
	/**
	 * 返回此消息的标题。 
	 *  
	 * @return 对应的HttpHeaders对象（决不{<@@code> null}）
	 */
	HttpHeaders getHeaders();

}
