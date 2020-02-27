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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;

public abstract class AbstractHttpHandlerIntegrationTests {

	/**
	 * Custom JUnit Jupiter extension that handles exceptions thrown by test methods.
	 *
	 * <p>If the test method throws an {@link HttpServerErrorException}, this
	 * extension will throw an {@link AssertionError} that wraps the
	 * {@code HttpServerErrorException} using the
	 * {@link HttpServerErrorException#getResponseBodyAsString() response body}
	 * as the failure message.
	 *
	 * <p>This mechanism provides an actually meaningful failure message if the
	 * test fails due to an {@code AssertionError} on the server.
	 */
	/**
	 * 定制JUnit Jupiter扩展，用于处理测试方法引发的异常。 
	 *  <p>如果测试方法抛出一个{@link  HttpServerErrorException}，则此扩展将抛出一个{{@link> AssertionError}，它使用{@link  HttpServerErrorException＃getResponseBodyAsString（ ）响应正文}作为失败消息。 
	 *  <p>如果由于服务器上的{@code  AssertionError}而导致测试失败，则此机制会提供实际上有意义的失败消息。 
	 * 
	 */
	@RegisterExtension
	TestExecutionExceptionHandler serverErrorToAssertionErrorConverter = (context, throwable) -> {
		if (throwable instanceof HttpServerErrorException) {
			HttpServerErrorException ex = (HttpServerErrorException) throwable;
			String responseBody = ex.getResponseBodyAsString();
			if (StringUtils.hasText(responseBody)) {
				String prefix = AssertionError.class.getName() + ": ";
				if (responseBody.startsWith(prefix)) {
					responseBody = responseBody.substring(prefix.length());
				}
				throw new AssertionError(responseBody, ex);
			}
		}
		// Else throw as-is in order to comply with the contract of TestExecutionExceptionHandler.
		throw throwable;
	};

	protected final Log logger = LogFactory.getLog(getClass());

	protected HttpServer server;

	protected int port;


	protected void startServer(HttpServer httpServer) throws Exception {
		this.server = httpServer;
		this.server.setHandler(createHttpHandler());
		this.server.afterPropertiesSet();
		this.server.start();

		// Set dynamically chosen port
		this.port = this.server.getPort();
	}

	@AfterEach
	void stopServer() {
		if (this.server != null) {
			this.server.stop();
			this.port = 0;
		}
	}


	protected abstract HttpHandler createHttpHandler();


	/**
	 * Return an interval stream of N number of ticks and buffer the emissions
	 * to avoid back pressure failures (e.g. on slow CI server).
	 *
	 * <p>Use this method as follows:
	 * <ul>
	 * <li>Tests that verify N number of items followed by verifyOnComplete()
	 * should set the number of emissions to N.
	 * <li>Tests that verify N number of items followed by thenCancel() should
	 * set the number of buffered to an arbitrary number greater than N.
	 * </ul>
	 */
	/**
	 * 返回间隔为N个滴答声的间隔流并缓冲排放，以避免背压故障（例如在慢速CI服务器上）。 
	 *  <p>按如下所示使用此方法：<ul> <li>先验证N个项目的测试，然后将verifyOnComplete（）设置为N。 
	 * <li>先验证N个项目的测试，然后将thenCancel（ ）应将缓冲数设置为大于N的任意数。 
	 * </ ul>
	 */
	public static Flux<Long> testInterval(Duration period, int count) {
		return Flux.interval(period).take(count).onBackpressureBuffer(count);
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource("org.springframework.web.testfixture.http.server.reactive.bootstrap.AbstractHttpHandlerIntegrationTests#httpServers()")
	// public for Kotlin
	public @interface ParameterizedHttpServerTest {
	}

	static Stream<HttpServer> httpServers() {
		return Stream.of(
				new JettyHttpServer(),
				new ReactorHttpServer(),
				new TomcatHttpServer(),
				new UndertowHttpServer()
		);
	}

}
