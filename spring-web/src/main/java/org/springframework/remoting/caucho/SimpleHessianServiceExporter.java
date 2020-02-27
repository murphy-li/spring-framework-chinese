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

package org.springframework.remoting.caucho;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.springframework.util.FileCopyUtils;

/**
 * HTTP request handler that exports the specified service bean as
 * Hessian service endpoint, accessible via a Hessian proxy.
 * Designed for Sun's JRE 1.6 HTTP server, implementing the
 * {@link com.sun.net.httpserver.HttpHandler} interface.
 *
 * <p>Hessian is a slim, binary RPC protocol.
 * For information on Hessian, see the
 * <a href="http://hessian.caucho.com">Hessian website</a>.
 * <b>Note: As of Spring 4.0, this exporter requires Hessian 4.0 or above.</b>
 *
 * <p>Hessian services exported with this class can be accessed by
 * any Hessian client, as there isn't any special handling involved.
 *
 * @author Juergen Hoeller
 * @since 2.5.1
 * @see org.springframework.remoting.caucho.HessianClientInterceptor
 * @see org.springframework.remoting.caucho.HessianProxyFactoryBean
 * @deprecated as of Spring Framework 5.1, in favor of {@link HessianServiceExporter}
 */
/**
 * HTTP请求处理程序，用于将指定的服务bean导出为Hessian服务终结点，可通过Hessian代理进行访问。 
 * 专为Sun的JRE 1.6 HTTP服务器而设计，实现了{@link  com.sun.net.httpserver.HttpHandler}接口。 
 *  <p> Hessian是一种苗条的二进制RPC协议。 
 * 有关黑森州的信息，请访问<a href="http://hessian.caucho.com">黑森州网站</a>。 
 *  <b>注意：从Spring 4.0开始，此导出器要求使用Hessian 4.0或更高版本。 
 * </ b> <p>任何此类的导出程序都可以由任何Hessian客户端访问，因为不涉及任何特殊处理。 
 *  @author  Juergen Hoeller @since 2.5.1 
 * @see  org.springframework.remoting.caucho.HessianClientInterceptor 
 * @see  org.springframework.remoting.caucho.HessianProxyFactoryBean @从Spring Framework 5.1开始弃用，赞成{@link  HessianServiceExporter}
 */
@Deprecated
@org.springframework.lang.UsesSunHttpServer
public class SimpleHessianServiceExporter extends HessianExporter implements HttpHandler {

	/**
	 * Processes the incoming Hessian request and creates a Hessian response.
	 */
	/**
	 * 处理传入的Hessian请求并创建一个Hessian响应。 
	 * 
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (!"POST".equals(exchange.getRequestMethod())) {
			exchange.getResponseHeaders().set("Allow", "POST");
			exchange.sendResponseHeaders(405, -1);
			return;
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		try {
			invoke(exchange.getRequestBody(), output);
		}
		catch (Throwable ex) {
			exchange.sendResponseHeaders(500, -1);
			logger.error("Hessian skeleton invocation failed", ex);
			return;
		}

		exchange.getResponseHeaders().set("Content-Type", CONTENT_TYPE_HESSIAN);
		exchange.sendResponseHeaders(200, output.size());
		FileCopyUtils.copy(output.toByteArray(), exchange.getResponseBody());
	}

}
