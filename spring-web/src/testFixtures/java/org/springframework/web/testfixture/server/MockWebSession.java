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

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.InMemoryWebSessionStore;

/**
 * Implementation of {@code WebSession} that delegates to a session instance
 * obtained via {@link InMemoryWebSessionStore}.
 *
 * <p>This is intended for use with the
 * {@link MockServerWebExchange.Builder#session(WebSession) session(WebSession)}
 * method of the {@code MockServerWebExchange} builder, eliminating the need
 * to use {@code WebSessionManager} or {@code WebSessionStore} altogether.
 *
 * @author Rossen Stoyanchev
 * @since 5.1
 */
/**
 * {@code  WebSession}的实现，委派给通过{@link  InMemoryWebSessionStore}获取的会话实例。 
 *  <p>此功能旨在与{@code  MockServerWebExchange}构建器的{@link  MockServerWebExchange.Builder＃session（WebSession）session（WebSession）}方法一起使用，而无需使用{@code  WebSessionManager}或{@code  WebSessionStore}一起。 
 *  @author  Rossen Stoyanchev @从5.1开始
 */
public class MockWebSession implements WebSession {

	private final WebSession delegate;


	public MockWebSession() {
		this(null);
	}

	public MockWebSession(@Nullable Clock clock) {
		InMemoryWebSessionStore sessionStore = new InMemoryWebSessionStore();
		if (clock != null) {
			sessionStore.setClock(clock);
		}
		WebSession session = sessionStore.createWebSession().block();
		Assert.state(session != null, "WebSession must not be null");
		this.delegate = session;
	}


	@Override
	public String getId() {
		return this.delegate.getId();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.delegate.getAttributes();
	}

	@Override
	public void start() {
		this.delegate.start();
	}

	@Override
	public boolean isStarted() {
		return this.delegate.isStarted();
	}

	@Override
	public Mono<Void> changeSessionId() {
		return this.delegate.changeSessionId();
	}

	@Override
	public Mono<Void> invalidate() {
		return this.delegate.invalidate();
	}

	@Override
	public Mono<Void> save() {
		return this.delegate.save();
	}

	@Override
	public boolean isExpired() {
		return this.delegate.isExpired();
	}

	@Override
	public Instant getCreationTime() {
		return this.delegate.getCreationTime();
	}

	@Override
	public Instant getLastAccessTime() {
		return this.delegate.getLastAccessTime();
	}

	@Override
	public void setMaxIdleTime(Duration maxIdleTime) {
		this.delegate.setMaxIdleTime(maxIdleTime);
	}

	@Override
	public Duration getMaxIdleTime() {
		return this.delegate.getMaxIdleTime();
	}

}
