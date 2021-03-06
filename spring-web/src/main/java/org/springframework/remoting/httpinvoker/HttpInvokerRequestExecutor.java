/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.remoting.httpinvoker;

import java.io.IOException;

import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

/**
 * Strategy interface for actual execution of an HTTP invoker request.
 * Used by HttpInvokerClientInterceptor and its subclass
 * HttpInvokerProxyFactoryBean.
 *
 * <p>Two implementations are provided out of the box:
 * <ul>
 * <li><b>{@code SimpleHttpInvokerRequestExecutor}:</b>
 * Uses JDK facilities to execute POST requests, without support
 * for HTTP authentication or advanced configuration options.
 * <li><b>{@code HttpComponentsHttpInvokerRequestExecutor}:</b>
 * Uses Apache's Commons HttpClient to execute POST requests,
 * allowing to use a preconfigured HttpClient instance
 * (potentially with authentication, HTTP connection pooling, etc).
 * </ul>
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see HttpInvokerClientInterceptor#setHttpInvokerRequestExecutor
 */
/**
 * 实际执行HTTP调用程序请求的策略接口。 
 * 由HttpInvokerClientInterceptor及其子类HttpInvokerProxyFactoryBean使用。 
 *  <p>提供了两种开箱即用的实现：<ul> <li> <b> {<@code> SimpleHttpInvokerRequestExecutor}：</ b>使用JDK工具执行POST请求，不支持HTTP身份验证或高级配置选项。 
 *  <li> <b> {<@code> HttpComponentsHttpInvokerRequestExecutor}：</ b>使用Apache的Commons HttpClient执行POST请求，从而允许使用预先配置的HttpClient实例（可能具有身份验证，HTTP连接池等）。 
 *  </ ul> @author  Juergen Hoeller @始于1.1 
 * @see  HttpInvokerClientInterceptor＃setHttpInvokerRequestExecutor
 */
@FunctionalInterface
public interface HttpInvokerRequestExecutor {

	/**
	 * Execute a request to send the given remote invocation.
	 * @param config the HTTP invoker configuration that specifies the
	 * target service
	 * @param invocation the RemoteInvocation to execute
	 * @return the RemoteInvocationResult object
	 * @throws IOException if thrown by I/O operations
	 * @throws ClassNotFoundException if thrown during deserialization
	 * @throws Exception in case of general errors
	 */
	/**
	 * 执行请求以发送给定的远程调用。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param 调用RemoteInvocation以执行
	 * @return  RemoteInvocationResult对象
	 * @throws  IOException（如果由I / O操作抛出）
	 * @throws  ClassNotFoundException（如果在I / O操作抛出）反序列化
	 * @throws 发生一般错误时发生异常
	 */
	RemoteInvocationResult executeRequest(HttpInvokerClientConfiguration config, RemoteInvocation invocation)
			throws Exception;

}
