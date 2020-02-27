/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.server.session;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;
import org.springframework.web.server.WebSession;

/**
 * Simple Map-based storage for {@link WebSession} instances.
 *
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 5.0
 */
/**
 * {@link  WebSession}实例的基于地图的简单存储。 
 *  @author  Rossen Stoyanchev @author  Rob Winch @从5.0开始
 */
public class InMemoryWebSessionStore implements WebSessionStore {

	private static final IdGenerator idGenerator = new JdkIdGenerator();


	private int maxSessions = 10000;

	private Clock clock = Clock.system(ZoneId.of("GMT"));

	private final Map<String, InMemoryWebSession> sessions = new ConcurrentHashMap<>();

	private final ExpiredSessionChecker expiredSessionChecker = new ExpiredSessionChecker();


	/**
	 * Set the maximum number of sessions that can be stored. Once the limit is
	 * reached, any attempt to store an additional session will result in an
	 * {@link IllegalStateException}.
	 * <p>By default set to 10000.
	 * @param maxSessions the maximum number of sessions
	 * @since 5.0.8
	 */
	/**
	 * 设置可以存储的最大会话数。 
	 * 一旦达到限制，任何尝试存储其他会话的尝试都会导致{@link  IllegalStateException}。 
	 *  <p>默认设置为10000。 
	 * 
	 * @param  maxSessions自5.0.8开始的最大会话数。 
	 * 
	 */
	public void setMaxSessions(int maxSessions) {
		this.maxSessions = maxSessions;
	}

	/**
	 * Return the maximum number of sessions that can be stored.
	 * @since 5.0.8
	 */
	/**
	 * 返回可以存储的最大会话数。 
	 *  @5.0.8起
	 */
	public int getMaxSessions() {
		return this.maxSessions;
	}

	/**
	 * Configure the {@link Clock} to use to set lastAccessTime on every created
	 * session and to calculate if it is expired.
	 * <p>This may be useful to align to different timezone or to set the clock
	 * back in a test, e.g. {@code Clock.offset(clock, Duration.ofMinutes(-31))}
	 * in order to simulate session expiration.
	 * <p>By default this is {@code Clock.system(ZoneId.of("GMT"))}.
	 * @param clock the clock to use
	 */
	/**
	 * 配置{@link  Clock}以用于在每个创建的会话上设置lastAccessTime并计算其是否过期。 
	 *  <p>这可能有助于对齐不同的时区或在测试中将时钟调回原来的时间，例如{@code  Clock.offset（clock，Duration.ofMinutes（-31））}为了模拟会话到期。 
	 *  <p>默认情况下为{@code  Clock.system（ZoneId.of（"GMT"））}。 
	 *  
	 * @param 使用时钟
	 */
	public void setClock(Clock clock) {
		Assert.notNull(clock, "Clock is required");
		this.clock = clock;
		removeExpiredSessions();
	}

	/**
	 * Return the configured clock for session lastAccessTime calculations.
	 */
	/**
	 * 返回为会话lastAccessTime计算配置的时钟。 
	 * 
	 */
	public Clock getClock() {
		return this.clock;
	}

	/**
	 * Return the map of sessions with an {@link Collections#unmodifiableMap
	 * unmodifiable} wrapper. This could be used for management purposes, to
	 * list active sessions, invalidate expired ones, etc.
	 * @since 5.0.8
	 */
	/**
	 * 使用{@link  Collections＃unmodifiableMap unmodifiable}包装器返回会话映射。 
	 * 从5.0.8开始，这可以用于管理目的，列出活动会话，使过期会话无效等。 
	 * 
	 */
	public Map<String, WebSession> getSessions() {
		return Collections.unmodifiableMap(this.sessions);
	}


	@Override
	public Mono<WebSession> createWebSession() {

		// Opportunity to clean expired sessions
		Instant now = this.clock.instant();
		this.expiredSessionChecker.checkIfNecessary(now);

		return Mono.<WebSession>fromSupplier(() -> new InMemoryWebSession(now))
				.subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Mono<WebSession> retrieveSession(String id) {
		Instant now = this.clock.instant();
		this.expiredSessionChecker.checkIfNecessary(now);
		InMemoryWebSession session = this.sessions.get(id);
		if (session == null) {
			return Mono.empty();
		}
		else if (session.isExpired(now)) {
			this.sessions.remove(id);
			return Mono.empty();
		}
		else {
			session.updateLastAccessTime(now);
			return Mono.just(session);
		}
	}

	@Override
	public Mono<Void> removeSession(String id) {
		this.sessions.remove(id);
		return Mono.empty();
	}

	@Override
	public Mono<WebSession> updateLastAccessTime(WebSession session) {
		return Mono.fromSupplier(() -> {
			Assert.isInstanceOf(InMemoryWebSession.class, session);
			((InMemoryWebSession) session).updateLastAccessTime(this.clock.instant());
			return session;
		});
	}

	/**
	 * Check for expired sessions and remove them. Typically such checks are
	 * kicked off lazily during calls to {@link #createWebSession() create} or
	 * {@link #retrieveSession retrieve}, no less than 60 seconds apart.
	 * This method can be called to force a check at a specific time.
	 * @since 5.0.8
	 */
	/**
	 * 检查过期的会话并将其删除。 
	 * 通常，在调用{@link  #createWebSession（）create}或{@link  #retrieveSession检索}的过程中，此类检查会延迟启动，间隔不少于60秒。 
	 * 可以调用此方法在特定时间强制检查。 
	 *  @5.0.8起
	 */
	public void removeExpiredSessions() {
		this.expiredSessionChecker.removeExpiredSessions(this.clock.instant());
	}


	private class InMemoryWebSession implements WebSession {

		private final AtomicReference<String> id = new AtomicReference<>(String.valueOf(idGenerator.generateId()));

		private final Map<String, Object> attributes = new ConcurrentHashMap<>();

		private final Instant creationTime;

		private volatile Instant lastAccessTime;

		private volatile Duration maxIdleTime = Duration.ofMinutes(30);

		private final AtomicReference<State> state = new AtomicReference<>(State.NEW);


		public InMemoryWebSession(Instant creationTime) {
			this.creationTime = creationTime;
			this.lastAccessTime = this.creationTime;
		}

		@Override
		public String getId() {
			return this.id.get();
		}

		@Override
		public Map<String, Object> getAttributes() {
			return this.attributes;
		}

		@Override
		public Instant getCreationTime() {
			return this.creationTime;
		}

		@Override
		public Instant getLastAccessTime() {
			return this.lastAccessTime;
		}

		@Override
		public void setMaxIdleTime(Duration maxIdleTime) {
			this.maxIdleTime = maxIdleTime;
		}

		@Override
		public Duration getMaxIdleTime() {
			return this.maxIdleTime;
		}

		@Override
		public void start() {
			this.state.compareAndSet(State.NEW, State.STARTED);
		}

		@Override
		public boolean isStarted() {
			return this.state.get().equals(State.STARTED) || !getAttributes().isEmpty();
		}

		@Override
		public Mono<Void> changeSessionId() {
			String currentId = this.id.get();
			InMemoryWebSessionStore.this.sessions.remove(currentId);
			String newId = String.valueOf(idGenerator.generateId());
			this.id.set(newId);
			InMemoryWebSessionStore.this.sessions.put(this.getId(), this);
			return Mono.empty();
		}

		@Override
		public Mono<Void> invalidate() {
			this.state.set(State.EXPIRED);
			getAttributes().clear();
			InMemoryWebSessionStore.this.sessions.remove(this.id.get());
			return Mono.empty();
		}

		@Override
		public Mono<Void> save() {

			checkMaxSessionsLimit();

			// Implicitly started session..
			if (!getAttributes().isEmpty()) {
				this.state.compareAndSet(State.NEW, State.STARTED);
			}

			if (isStarted()) {
				// Save
				InMemoryWebSessionStore.this.sessions.put(this.getId(), this);

				// Unless it was invalidated
				if (this.state.get().equals(State.EXPIRED)) {
					InMemoryWebSessionStore.this.sessions.remove(this.getId());
					return Mono.error(new IllegalStateException("Session was invalidated"));
				}
			}

			return Mono.empty();
		}

		private void checkMaxSessionsLimit() {
			if (sessions.size() >= maxSessions) {
				expiredSessionChecker.removeExpiredSessions(clock.instant());
				if (sessions.size() >= maxSessions) {
					throw new IllegalStateException("Max sessions limit reached: " + sessions.size());
				}
			}
		}

		@Override
		public boolean isExpired() {
			return isExpired(clock.instant());
		}

		private boolean isExpired(Instant now) {
			if (this.state.get().equals(State.EXPIRED)) {
				return true;
			}
			if (checkExpired(now)) {
				this.state.set(State.EXPIRED);
				return true;
			}
			return false;
		}

		private boolean checkExpired(Instant currentTime) {
			return isStarted() && !this.maxIdleTime.isNegative() &&
					currentTime.minus(this.maxIdleTime).isAfter(this.lastAccessTime);
		}

		private void updateLastAccessTime(Instant currentTime) {
			this.lastAccessTime = currentTime;
		}
	}


	private class ExpiredSessionChecker {

		/** Max time between expiration checks. */
		/**
		 * 两次到期检查之间的最长时间。 
		 * 
		 */
		private static final int CHECK_PERIOD = 60 * 1000;


		private final ReentrantLock lock = new ReentrantLock();

		private Instant checkTime = clock.instant().plus(CHECK_PERIOD, ChronoUnit.MILLIS);


		public void checkIfNecessary(Instant now) {
			if (this.checkTime.isBefore(now)) {
				removeExpiredSessions(now);
			}
		}

		public void removeExpiredSessions(Instant now) {
			if (sessions.isEmpty()) {
				return;
			}
			if (this.lock.tryLock()) {
				try {
					Iterator<InMemoryWebSession> iterator = sessions.values().iterator();
					while (iterator.hasNext()) {
						InMemoryWebSession session = iterator.next();
						if (session.isExpired(now)) {
							iterator.remove();
							session.invalidate();
						}
					}
				}
				finally {
					this.checkTime = now.plus(CHECK_PERIOD, ChronoUnit.MILLIS);
					this.lock.unlock();
				}
			}
		}
	}


	private enum State { NEW, STARTED, EXPIRED }

}
