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

package org.springframework.web.socket.config.annotation;

import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import org.springframework.web.util.UrlPathHelper;

/**
 * A contract for registering STOMP over WebSocket endpoints.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 通过WebSocket端点注册STOMP的合同。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public interface StompEndpointRegistry {

	/**
	 * Register a STOMP over WebSocket endpoint at the given mapping path.
	 */
	/**
	 * 在给定的映射路径上通过WebSocket端点注册STOMP。 
	 * 
	 */
	StompWebSocketEndpointRegistration addEndpoint(String... paths);

	/**
	 * Set the order of the {@link org.springframework.web.servlet.HandlerMapping}
	 * used for STOMP endpoints relative to other Spring MVC handler mappings.
	 * <p>By default this is set to 1.
	 */
	/**
	 * 相对于其他Spring MVC处理程序映射，设置用于STOMP端点的{@link  org.springframework.web.servlet.HandlerMapping}的顺序。 
	 *  <p>默认情况下设置为1。 
	 * 
	 */
	void setOrder(int order);

	/**
	 * Configure a customized {@link UrlPathHelper} for the STOMP endpoint
	 * {@link org.springframework.web.servlet.HandlerMapping HandlerMapping}.
	 */
	/**
	 * 为STOMP端点{@link  org.springframework.web.servlet.HandlerMapping HandlerMapping}配置自定义的{@link  UrlPathHelper}。 
	 * 
	 */
	void setUrlPathHelper(UrlPathHelper urlPathHelper);

	/**
	 * Configure a handler for customizing or handling STOMP ERROR frames to clients.
	 * @param errorHandler the error handler
	 * @since 4.2
	 */
	/**
	 * 配置一个处理程序，以自定义或处理客户端的STOMP ERROR帧。 
	 *  
	 * @param  errorHandler错误处理程序@4.2起
	 */
	WebMvcStompEndpointRegistry setErrorHandler(StompSubProtocolErrorHandler errorHandler);

}
