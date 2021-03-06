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

package org.springframework.http.server.reactive;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Operators;

import org.springframework.core.log.LogDelegateFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Abstract base class for {@code Publisher} implementations that bridge between
 * event-listener read APIs and Reactive Streams.
 *
 * <p>Specifically a base class for reading from the HTTP request body with
 * Servlet 3.1 non-blocking I/O and Undertow XNIO as well as handling incoming
 * WebSocket messages with standard Java WebSocket (JSR-356), Jetty, and
 * Undertow.
 *
 * @author Arjen Poutsma
 * @author Violeta Georgieva
 * @author Rossen Stoyanchev
 * @since 5.0
 * @param <T> the type of element signaled
 */
/**
 * {@code  Publisher}实现的抽象基类，它在事件侦听器读取API和响应流之间架起桥梁。 
 *  <p>特别是一个基类，用于使用Servlet 3.1非阻塞I / O和Undertow XNIO从HTTP请求主体读取，以及使用标准Java WebSocket（JSR-356），Jetty和Undertow处理传入的WebSocket消息。 
 *  @author  Arjen Poutsma @author  Violeta Georgieva @author  Rossen Stoyanchev @since 5.0 
 * @param  <T>发出信号的元素类型
 */
public abstract class AbstractListenerReadPublisher<T> implements Publisher<T> {

	/**
	 * Special logger for debugging Reactive Streams signals.
	 * @see LogDelegateFactory#getHiddenLog(Class)
	 * @see AbstractListenerWriteProcessor#rsWriteLogger
	 * @see AbstractListenerWriteFlushProcessor#rsWriteFlushLogger
	 * @see WriteResultPublisher#rsWriteResultLogger
	 */
	/**
	 * 用于调试反应式流信号的特殊记录器。 
	 *  
	 * @see  LogDelegateFactory＃getHiddenLog（Class）
	 * @see  AbstractListenerWriteProcessor＃rsWriteLogger 
	 * @see  AbstractListenerWriteFlushProcessor＃rsWriteFlushLogger 
	 * @see  WriteResultPublisher＃rsWriteResultLogger
	 */
	protected static Log rsReadLogger = LogDelegateFactory.getHiddenLog(AbstractListenerReadPublisher.class);


	private final AtomicReference<State> state = new AtomicReference<>(State.UNSUBSCRIBED);

	private volatile long demand;

	@SuppressWarnings("rawtypes")
	private static final AtomicLongFieldUpdater<AbstractListenerReadPublisher> DEMAND_FIELD_UPDATER =
			AtomicLongFieldUpdater.newUpdater(AbstractListenerReadPublisher.class, "demand");

	@Nullable
	private volatile Subscriber<? super T> subscriber;

	private volatile boolean completionBeforeDemand;

	@Nullable
	private volatile Throwable errorBeforeDemand;

	private final String logPrefix;


	public AbstractListenerReadPublisher() {
		this("");
	}

	/**
	 * Create an instance with the given log prefix.
	 * @since 5.1
	 */
	/**
	 * 创建具有给定日志前缀的实例。 
	 *  @5.1起
	 */
	public AbstractListenerReadPublisher(String logPrefix) {
		this.logPrefix = logPrefix;
	}


	/**
	 * Return the configured log message prefix.
	 * @since 5.1
	 */
	/**
	 * 返回配置的日志消息前缀。 
	 *  @5.1起
	 */
	public String getLogPrefix() {
		return this.logPrefix;
	}


	// Publisher implementation...

	@Override
	public void subscribe(Subscriber<? super T> subscriber) {
		this.state.get().subscribe(this, subscriber);
	}


	// Async I/O notification methods...

	/**
	 * Invoked when reading is possible, either in the same thread after a check
	 * via {@link #checkOnDataAvailable()}, or as a callback from the underlying
	 * container.
	 */
	/**
	 * 当可以进行读取时调用，或者通过{@link  #checkOnDataAvailable（）}进行检查后在同一线程中调用，或者作为来自基础容器的回调。 
	 * 
	 */
	public final void onDataAvailable() {
		rsReadLogger.trace(getLogPrefix() + "onDataAvailable");
		this.state.get().onDataAvailable(this);
	}

	/**
	 * Sub-classes can call this method to delegate a contain notification when
	 * all data has been read.
	 */
	/**
	 * 读取所有数据后，子类可以调用此方法来委托包含通知。 
	 * 
	 */
	public void onAllDataRead() {
		rsReadLogger.trace(getLogPrefix() + "onAllDataRead");
		this.state.get().onAllDataRead(this);
	}

	/**
	 * Sub-classes can call this to delegate container error notifications.
	 */
	/**
	 * 子类可以调用它来委托容器错误通知。 
	 * 
	 */
	public final void onError(Throwable ex) {
		if (rsReadLogger.isTraceEnabled()) {
			rsReadLogger.trace(getLogPrefix() + "Connection error: " + ex);
		}
		this.state.get().onError(this, ex);
	}


	// Read API methods to be implemented or template methods to override...

	/**
	 * Check if data is available and either call {@link #onDataAvailable()}
	 * immediately or schedule a notification.
	 */
	/**
	 * 检查数据是否可用，然后立即调用{@link  #onDataAvailable（）}或安排通知。 
	 * 
	 */
	protected abstract void checkOnDataAvailable();

	/**
	 * Read once from the input, if possible.
	 * @return the item that was read; or {@code null}
	 */
	/**
	 * 如果可能，请从输入中读取一次。 
	 *  
	 * @return 读取的项目； 
	 * 或{@code  null}
	 */
	@Nullable
	protected abstract T read() throws IOException;

	/**
	 * Invoked when reading is paused due to a lack of demand.
	 * <p><strong>Note:</strong> This method is guaranteed not to compete with
	 * {@link #checkOnDataAvailable()} so it can be used to safely suspend
	 * reading, if the underlying API supports it, i.e. without competing with
	 * an implicit call to resume via {@code checkOnDataAvailable()}.
	 * @since 5.0.2
	 */
	/**
	 * 由于需求不足而暂停读取时调用。 
	 *  <p> <strong>注意：</ strong>该方法保证不与{@link  #checkOnDataAvailable（）}竞争，因此，如果基础API支持该方法，则可以安全地暂停读取，即无需竞争通过{@code  checkOnDataAvailable（）}进行隐式调用以恢复。 
	 *  @从5.0.2开始
	 */
	protected abstract void readingPaused();

	/**
	 * Invoked after an I/O read error from the underlying server or after a
	 * cancellation signal from the downstream consumer to allow sub-classes
	 * to discard any current cached data they might have.
	 * @since 5.0.11
	 */
	/**
	 * 在来自底层服务器的I / O读取错误之后或在来自下游使用者的取消信号之后调用，以允许子类丢弃它们可能具有的任何当前缓存的数据。 
	 *  @从5.0.11开始
	 */
	protected abstract void discardData();


	// Private methods for use in State...

	/**
	 * Read and publish data one at a time until there is no more data, no more
	 * demand, or perhaps we completed in the mean time.
	 * @return {@code true} if there is more demand; {@code false} if there is
	 * no more demand or we have completed.
	 */
	/**
	 * 一次读取和发布一次数据，直到没有更多数据，没有更多需求或者我们在此期间完成为止。 
	 *  
	 * @return  {@code  true}，如果有更多需求； 
	 *  {@code  false}如果没有更多需求或我们已经完成。 
	 * 
	 */
	private boolean readAndPublish() throws IOException {
		long r;
		while ((r = this.demand) > 0 && !this.state.get().equals(State.COMPLETED)) {
			T data = read();
			if (data != null) {
				if (r != Long.MAX_VALUE) {
					DEMAND_FIELD_UPDATER.addAndGet(this, -1L);
				}
				Subscriber<? super T> subscriber = this.subscriber;
				Assert.state(subscriber != null, "No subscriber");
				if (rsReadLogger.isTraceEnabled()) {
					rsReadLogger.trace(getLogPrefix() + "Publishing data read");
				}
				subscriber.onNext(data);
			}
			else {
				if (rsReadLogger.isTraceEnabled()) {
					rsReadLogger.trace(getLogPrefix() + "No more data to read");
				}
				return true;
			}
		}
		return false;
	}

	private boolean changeState(State oldState, State newState) {
		boolean result = this.state.compareAndSet(oldState, newState);
		if (result && rsReadLogger.isTraceEnabled()) {
			rsReadLogger.trace(getLogPrefix() + oldState + " -> " + newState);
		}
		return result;
	}

	private void changeToDemandState(State oldState) {
		if (changeState(oldState, State.DEMAND)) {
			// Protect from infinite recursion in Undertow, where we can't check if data
			// is available, so all we can do is to try to read.
			// Generally, no need to check if we just came out of readAndPublish()...
			if (!oldState.equals(State.READING)) {
				checkOnDataAvailable();
			}
		}
	}

	private void handleCompletionOrErrorBeforeDemand() {
		State state = this.state.get();
		if (!state.equals(State.UNSUBSCRIBED) && !state.equals(State.SUBSCRIBING)) {
			if (this.completionBeforeDemand) {
				rsReadLogger.trace(getLogPrefix() + "Completed before demand");
				this.state.get().onAllDataRead(this);
			}
			Throwable ex = this.errorBeforeDemand;
			if (ex != null) {
				if (rsReadLogger.isTraceEnabled()) {
					rsReadLogger.trace(getLogPrefix() + "Completed with error before demand: " + ex);
				}
				this.state.get().onError(this, ex);
			}
		}
	}

	private Subscription createSubscription() {
		return new ReadSubscription();
	}


	/**
	 * Subscription that delegates signals to State.
	 */
	/**
	 * 将信号委托给State的订阅。 
	 * 
	 */
	private final class ReadSubscription implements Subscription {


		@Override
		public final void request(long n) {
			if (rsReadLogger.isTraceEnabled()) {
				rsReadLogger.trace(getLogPrefix() + n + " requested");
			}
			state.get().request(AbstractListenerReadPublisher.this, n);
		}

		@Override
		public final void cancel() {
			if (rsReadLogger.isTraceEnabled()) {
				rsReadLogger.trace(getLogPrefix() + "Cancellation");
			}
			state.get().cancel(AbstractListenerReadPublisher.this);
		}
	}


	/**
	 * Represents a state for the {@link Publisher} to be in.
	 * <p><pre>
	 *        UNSUBSCRIBED
	 *             |
	 *             v
	 *        SUBSCRIBING
	 *             |
	 *             v
	 *    +---- NO_DEMAND ---------------> DEMAND ---+
	 *    |        ^                         ^       |
	 *    |        |                         |       |
	 *    |        +------- READING <--------+       |
	 *    |                    |                     |
	 *    |                    v                     |
	 *    +--------------> COMPLETED <---------------+
	 * </pre>
	 */
	/**
	 * 表示{@link  Publisher}所在的状态。 
	 * <p> <pre>取消订阅| v订阅| v + ---- NO_DEMAND ---------------> DEMAND --- + | ^ ^ | | | | | | + -------阅读<-------- + | | | | | v | + -------------->已完成<--------------- + </ pre>
	 */
	private enum State {

		UNSUBSCRIBED {
			@Override
			<T> void subscribe(AbstractListenerReadPublisher<T> publisher, Subscriber<? super T> subscriber) {
				Assert.notNull(publisher, "Publisher must not be null");
				Assert.notNull(subscriber, "Subscriber must not be null");
				if (publisher.changeState(this, SUBSCRIBING)) {
					Subscription subscription = publisher.createSubscription();
					publisher.subscriber = subscriber;
					subscriber.onSubscribe(subscription);
					publisher.changeState(SUBSCRIBING, NO_DEMAND);
					publisher.handleCompletionOrErrorBeforeDemand();
				}
				else {
					throw new IllegalStateException("Failed to transition to SUBSCRIBING, " +
							"subscriber: " + subscriber);
				}
			}

			@Override
			<T> void onAllDataRead(AbstractListenerReadPublisher<T> publisher) {
				publisher.completionBeforeDemand = true;
				publisher.handleCompletionOrErrorBeforeDemand();
			}

			@Override
			<T> void onError(AbstractListenerReadPublisher<T> publisher, Throwable ex) {
				publisher.errorBeforeDemand = ex;
				publisher.handleCompletionOrErrorBeforeDemand();
			}
		},

		/**
		 * Very brief state where we know we have a Subscriber but must not
		 * send onComplete and onError until we after onSubscribe.
		 */
		/**
		 * 非常简短的状态，我们知道我们有一个订阅服务器，但是在onSubscribe之后我们才可以发送onComplete和onError。 
		 * 
		 */
		SUBSCRIBING {
			@Override
			<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
				if (Operators.validate(n)) {
					Operators.addCap(DEMAND_FIELD_UPDATER, publisher, n);
					publisher.changeToDemandState(this);
				}
			}

			@Override
			<T> void onAllDataRead(AbstractListenerReadPublisher<T> publisher) {
				publisher.completionBeforeDemand = true;
				publisher.handleCompletionOrErrorBeforeDemand();
			}

			@Override
			<T> void onError(AbstractListenerReadPublisher<T> publisher, Throwable ex) {
				publisher.errorBeforeDemand = ex;
				publisher.handleCompletionOrErrorBeforeDemand();
			}
		},

		NO_DEMAND {
			@Override
			<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
				if (Operators.validate(n)) {
					Operators.addCap(DEMAND_FIELD_UPDATER, publisher, n);
					publisher.changeToDemandState(this);
				}
			}
		},

		DEMAND {
			@Override
			<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
				if (Operators.validate(n)) {
					Operators.addCap(DEMAND_FIELD_UPDATER, publisher, n);
					// Did a concurrent read transition to NO_DEMAND just before us?
					publisher.changeToDemandState(NO_DEMAND);
				}
			}

			@Override
			<T> void onDataAvailable(AbstractListenerReadPublisher<T> publisher) {
				if (publisher.changeState(this, READING)) {
					try {
						boolean demandAvailable = publisher.readAndPublish();
						if (demandAvailable) {
							publisher.changeToDemandState(READING);
						}
						else {
							publisher.readingPaused();
							if (publisher.changeState(READING, NO_DEMAND)) {
								// Demand may have arrived since readAndPublish returned
								long r = publisher.demand;
								if (r > 0) {
									publisher.changeToDemandState(NO_DEMAND);
								}
							}
						}
					}
					catch (IOException ex) {
						publisher.onError(ex);
					}
				}
				// Else, either competing onDataAvailable (request vs container), or concurrent completion
			}
		},

		READING {
			@Override
			<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
				if (Operators.validate(n)) {
					Operators.addCap(DEMAND_FIELD_UPDATER, publisher, n);
					// Did a concurrent read transition to NO_DEMAND just before us?
					publisher.changeToDemandState(NO_DEMAND);
				}
			}
		},

		COMPLETED {
			@Override
			<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
				// ignore
			}
			@Override
			<T> void cancel(AbstractListenerReadPublisher<T> publisher) {
				// ignore
			}
			@Override
			<T> void onAllDataRead(AbstractListenerReadPublisher<T> publisher) {
				// ignore
			}
			@Override
			<T> void onError(AbstractListenerReadPublisher<T> publisher, Throwable t) {
				// ignore
			}
		};

		<T> void subscribe(AbstractListenerReadPublisher<T> publisher, Subscriber<? super T> subscriber) {
			throw new IllegalStateException(toString());
		}

		<T> void request(AbstractListenerReadPublisher<T> publisher, long n) {
			throw new IllegalStateException(toString());
		}

		<T> void cancel(AbstractListenerReadPublisher<T> publisher) {
			if (publisher.changeState(this, COMPLETED)) {
				publisher.discardData();
			}
			else {
				publisher.state.get().cancel(publisher);
			}
		}

		<T> void onDataAvailable(AbstractListenerReadPublisher<T> publisher) {
			// ignore
		}

		<T> void onAllDataRead(AbstractListenerReadPublisher<T> publisher) {
			if (publisher.changeState(this, COMPLETED)) {
				Subscriber<? super T> s = publisher.subscriber;
				if (s != null) {
					s.onComplete();
				}
			}
			else {
				publisher.state.get().onAllDataRead(publisher);
			}
		}

		<T> void onError(AbstractListenerReadPublisher<T> publisher, Throwable t) {
			if (publisher.changeState(this, COMPLETED)) {
				publisher.discardData();
				Subscriber<? super T> s = publisher.subscriber;
				if (s != null) {
					s.onError(t);
				}
			}
			else {
				publisher.state.get().onError(publisher, t);
			}
		}
	}

}
