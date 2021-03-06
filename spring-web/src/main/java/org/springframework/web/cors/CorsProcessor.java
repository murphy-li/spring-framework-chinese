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

package org.springframework.web.cors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

/**
 * A strategy that takes a request and a {@link CorsConfiguration} and updates
 * the response.
 *
 * <p>This component is not concerned with how a {@code CorsConfiguration} is
 * selected but rather takes follow-up actions such as applying CORS validation
 * checks and either rejecting the response or adding CORS headers to the
 * response.
 *
 * @author Sebastien Deleuze
 * @author Rossen Stoyanchev
 * @since 4.2
 * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
 * @see org.springframework.web.servlet.handler.AbstractHandlerMapping#setCorsProcessor
 */
/**
 * 一种策略，它接受一个请求和一个{@link  CorsConfiguration}并更新响应。 
 *  <p>此组件与如何选择{@code  CorsConfiguration}无关，而是采取后续操作，例如应用CORS验证检查以及拒绝响应或向响应添加CORS标头。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author  Rossen Stoyanchev @从4.2起
 * @see  <a href="https://www.w3.org/TR/cors/"> CORS W3C建议</a> <
 * @see > org.springframework.web.servlet.handler.AbstractHandlerMapping＃setCorsProcessor
 */
public interface CorsProcessor {

	/**
	 * Process a request given a {@code CorsConfiguration}.
	 * @param configuration the applicable CORS configuration (possibly {@code null})
	 * @param request the current request
	 * @param response the current response
	 * @return {@code false} if the request is rejected, {@code true} otherwise
	 */
	/**
	 * 给定{@code  CorsConfiguration}来处理请求。 
	 *  
	 * @param 配置适用的CORS配置（可能为{@code  null}）
	 * @param 请求当前请求
	 * @param 响应当前响应
	 * @return  {@code  false}（如果请求为拒绝，否则{@code  true}
	 */
	boolean processRequest(@Nullable CorsConfiguration configuration, HttpServletRequest request,
			HttpServletResponse response) throws IOException;

}
