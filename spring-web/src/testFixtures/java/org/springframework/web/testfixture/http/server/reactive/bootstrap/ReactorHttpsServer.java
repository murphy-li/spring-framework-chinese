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

package org.springframework.web.testfixture.http.server.reactive.bootstrap;

import java.util.concurrent.atomic.AtomicReference;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.SslProvider.DefaultConfigurationType;

import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;

/**
 * @author Stephane Maldini
 */
/**
 * @author 斯蒂芬·马尔蒂尼
 */
public class ReactorHttpsServer extends AbstractHttpServer {

	private ReactorHttpHandlerAdapter reactorHandler;

	private reactor.netty.http.server.HttpServer reactorServer;

	private AtomicReference<DisposableServer> serverRef = new AtomicReference<>();


	@Override
	protected void initServer() throws Exception {

		SelfSignedCertificate cert = new SelfSignedCertificate();
		SslContextBuilder builder = SslContextBuilder.forServer(cert.certificate(), cert.privateKey());

		this.reactorHandler = createHttpHandlerAdapter();
		this.reactorServer = reactor.netty.http.server.HttpServer.create()
			.host(getHost())
			.port(getPort())
			.secure(spec -> spec.sslContext(builder).defaultConfiguration(DefaultConfigurationType.TCP));
	}

	private ReactorHttpHandlerAdapter createHttpHandlerAdapter() {
		return new ReactorHttpHandlerAdapter(resolveHttpHandler());
	}

	@Override
	protected void startInternal() {
		DisposableServer server = this.reactorServer.handle(this.reactorHandler).bind().block();
		setPort(server.address().getPort());
		this.serverRef.set(server);
	}

	@Override
	protected void stopInternal() {
		this.serverRef.get().dispose();
	}

	@Override
	protected void resetInternal() {
		this.reactorServer = null;
		this.reactorHandler = null;
		this.serverRef.set(null);
	}

}
