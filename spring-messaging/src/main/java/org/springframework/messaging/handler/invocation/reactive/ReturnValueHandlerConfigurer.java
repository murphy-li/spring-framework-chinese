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

package org.springframework.messaging.handler.invocation.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Assist with configuration for handler method return value handlers.
 * At present, it supports only providing a list of custom handlers.
 *
 * @author Rossen Stoyanchev
 * @since 5.2
 */
/**
 * 协助配置处理程序方法返回值处理程序。 
 * 目前，它仅支持提供自定义处理程序列表。 
 *  @author  Rossen Stoyanchev @从5.2开始
 */
public class ReturnValueHandlerConfigurer {

	private final List<HandlerMethodReturnValueHandler> customHandlers = new ArrayList<>(8);


	/**
	 * Configure custom return value handlers for handler methods.
	 * @param handlers the handlers to add
	 */
	/**
	 * 为处理程序方法配置自定义返回值处理程序。 
	 *  
	 * @param 处理程序要添加的处理程序
	 */
	public void addCustomHandler(HandlerMethodReturnValueHandler... handlers) {
		Assert.notNull(handlers, "'handlers' must not be null");
		this.customHandlers.addAll(Arrays.asList(handlers));
	}


	public List<HandlerMethodReturnValueHandler> getCustomHandlers() {
		return this.customHandlers;
	}

}
