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

package org.springframework.web.client;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.client.ClientHttpRequest;

/**
 * Callback interface for code that operates on a {@link ClientHttpRequest}.
 * Allows manipulating the request headers, and write to the request body.
 *
 * <p>Used internally by the {@link RestTemplate}, but also useful for
 * application code. There several available factory methods:
 * <ul>
 * <li>{@link RestTemplate#acceptHeaderRequestCallback(Class)}
 * <li>{@link RestTemplate#httpEntityCallback(Object)}
 * <li>{@link RestTemplate#httpEntityCallback(Object, Type)}
 * </ul>
 *
 * @author Arjen Poutsma
 * @see RestTemplate#execute
 * @since 3.0
 */
/**
 * 在{@link  ClientHttpRequest}上运行的代码的回调接口。 
 * 允许处理请求标头，并写入请求主体。 
 *  <p>由{@link  RestTemplate}内部使用，但对应用程序代码也很有用。 
 * 有几种可用的工厂方法：<ul> <li> {<@link> RestTemplate＃acceptHeaderRequestCallback（Class）} <li> {<@link> RestTemplate＃httpEntityCallback（Object）} <li> {<@link> RestTemplate＃httpEntityCallback （（对象，类型）} </ ul> @author  Arjen Poutsma 
 * @see  RestTemplate＃execute @since 3.0
 */
@FunctionalInterface
public interface RequestCallback {

	/**
	 * Gets called by {@link RestTemplate#execute} with an opened {@code ClientHttpRequest}.
	 * Does not need to care about closing the request or about handling errors:
	 * this will all be handled by the {@code RestTemplate}.
	 * @param request the active HTTP request
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 由{@link  RestTemplate＃execute}使用打开的{@code  ClientHttpRequest}进行调用。 
	 * 无需关心关闭请求或处理错误：这一切都将由{@code  RestTemplate}处理。 
	 *  
	 * @param 请求活动的HTTP请求
	 * @throws  IOException（如果发生I / O错误）
	 */
	void doWithRequest(ClientHttpRequest request) throws IOException;

}
