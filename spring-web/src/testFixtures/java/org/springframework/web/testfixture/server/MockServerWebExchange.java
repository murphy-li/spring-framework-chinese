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
package org.springframework.web.testfixture.server;

import reactor.core.publisher.Mono;

import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.Nullable;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.testfixture.http.server.reactive.MockServerHttpResponse;

/**
 * Extension of {@link DefaultServerWebExchange} for use in tests, along with
 * {@link MockServerHttpRequest} and {@link MockServerHttpResponse}.
 *
 * <p>See static factory methods to create an instance.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * {@link  DefaultServerWebExchange}的扩展与测试一起使用，以及{@link  MockServerHttpRequest}和{@link  MockServerHttpResponse}。 
 *  <p>请参见静态工厂方法以创建实例。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public final class MockServerWebExchange extends DefaultServerWebExchange {


	private MockServerWebExchange(MockServerHttpRequest request, WebSessionManager sessionManager) {
		super(request, new MockServerHttpResponse(), sessionManager,
				ServerCodecConfigurer.create(), new AcceptHeaderLocaleContextResolver());
	}


	@Override
	public MockServerHttpResponse getResponse() {
		return (MockServerHttpResponse) super.getResponse();
	}


	/**
	 * Create a {@link MockServerWebExchange} from the given mock request.
	 * @param request the request to use.
	 * @return the exchange
	 */
	/**
	 * 从给定的模拟请求创建一个{@link  MockServerWebExchange}。 
	 *  
	 * @param 请求使用请求。 
	 *  
	 * @return 交流
	 */
	public static MockServerWebExchange from(MockServerHttpRequest request) {
		return builder(request).build();
	}

	/**
	 * Variant of {@link #from(MockServerHttpRequest)} with a mock request builder.
	 * @param requestBuilder the builder for the mock request.
	 * @return the exchange
	 */
	/**
	 * {@link  #from（MockServerHttpRequest）}与模拟请求生成器的变体。 
	 *  
	 * @param  requestBuilder模拟请求的生成器。 
	 *  
	 * @return 交流
	 */
	public static MockServerWebExchange from(MockServerHttpRequest.BaseBuilder<?> requestBuilder) {
		return builder(requestBuilder).build();
	}

	/**
	 * Create a {@link Builder} starting with the given mock request.
	 * @param request the request to use.
	 * @return the exchange builder
	 * @since 5.1
	 */
	/**
	 * 从给定的模拟请求开始创建一个{@link  Builder}。 
	 *  
	 * @param 请求使用请求。 
	 *  
	 * @return 交流生成器@始于5.1
	 */
	public static MockServerWebExchange.Builder builder(MockServerHttpRequest request) {
		return new MockServerWebExchange.Builder(request);
	}

	/**
	 * Variant of {@link #builder(MockServerHttpRequest)} with a mock request builder.
	 * @param requestBuilder the builder for the mock request.
	 * @return the exchange builder
	 * @since 5.1
	 */
	/**
	 * {@link  #builder（MockServerHttpRequest）}与模拟请求生成器的变体。 
	 *  
	 * @param  requestBuilder模拟请求的生成器。 
	 *  
	 * @return 交流生成器@始于5.1
	 */
	public static MockServerWebExchange.Builder builder(MockServerHttpRequest.BaseBuilder<?> requestBuilder) {
		return new MockServerWebExchange.Builder(requestBuilder.build());
	}


	/**
	 * Builder for a {@link MockServerWebExchange}.
	 * @since 5.1
	 */
	/**
	 * {@link  MockServerWebExchange}的生成器。 
	 *  @5.1起
	 */
	public static class Builder {

		private final MockServerHttpRequest request;

		@Nullable
		private WebSessionManager sessionManager;


		public Builder(MockServerHttpRequest request) {
			this.request = request;
		}


		/**
		 * Set the session to use for the exchange.
		 * <p>This is mutually exclusive with {@link #sessionManager(WebSessionManager)}.
		 * @param session the session to use
		 */
		/**
		 * 设置用于交换的会话。 
		 *  <p>这与{@link  #sessionManager（WebSessionManager）}互斥。 
		 *  
		 * @param 会话要使用的会话
		 */
		public Builder session(WebSession session) {
			this.sessionManager = exchange -> Mono.just(session);
			return this;
		}

		/**
		 * Provide a {@code WebSessionManager} instance to use with the exchange.
		 * <p>This is mutually exclusive with {@link #session(WebSession)}.
		 * @param sessionManager the session manager to use
		 */
		/**
		 * 提供一个{@code  WebSessionManager}实例以用于交换。 
		 *  <p>这与{@link  #session（WebSession）}互斥。 
		 *  
		 * @param  sessionManager会话管理器要使用
		 */
		public Builder sessionManager(WebSessionManager sessionManager) {
			this.sessionManager = sessionManager;
			return this;
		}

		/**
		 * Build the {@code MockServerWebExchange} instance.
		 */
		/**
		 * 生成{@code  MockServerWebExchange}实例。 
		 * 
		 */
		public MockServerWebExchange build() {
			return new MockServerWebExchange(this.request,
					this.sessionManager != null ? this.sessionManager : new DefaultWebSessionManager());
		}
	}

}
