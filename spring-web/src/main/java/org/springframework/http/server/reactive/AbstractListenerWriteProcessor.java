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
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import org.springframework.core.log.LogDelegateFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Abstract base class for {@code Processor} implementations that bridge between
 * event-listener write APIs and Reactive Streams.
 *
 * <p>Specifically a base class for writing to the HTTP response body with
 * Servlet 3.1 non-blocking I/O and Undertow XNIO as well for writing WebSocket
 * messages through the Java WebSocket API (JSR-356), Jetty, and Undertow.
 *
 * @author Arjen Poutsma
 * @author Violeta Georgieva
 * @author Rossen Stoyanchev
 * @since 5.0
 * @param <T> the type of element signaled to the {@link Subscriber}
 */
/**
 * {@code  Processor}实现的抽象基类，它们在事件侦听器编写API和响应流之间架起桥梁。 
 *  <p>特别是一个基类，用于通过Servlet 3.1非阻塞I / O和Undertow XNIO写入HTTP响应主体，以及通过Java WebSocket API（JSR-356），Jetty和Undertow编写WebSocket消息。 
 *  @author  Arjen Poutsma @author  Violeta Georgieva @author  Rossen Stoyanchev @since 5.0 
 * @param  <T>发送给{@link 订阅者}的元素类型
 */
public abstract class AbstractListenerWriteProcessor<T> implements Processor<T, Void> {

	/**
	 * Special logger for debugging Reactive Streams signals.
	 * @see LogDelegateFactory#getHiddenLog(Class)
	 * @see AbstractListenerReadPublisher#rsReadLogger
	 * @see AbstractListenerWriteFlushProcessor#rsWriteFlushLogger
	 * @see WriteResultPublisher#rsWriteResultLogger
	 */
	/**
	 * 用于调试反应式流信号的特殊记录器。 
	 *  
	 * @see  LogDelegateFactory＃getHiddenLog（Class）
	 * @see  AbstractListenerReadPublisher＃rsReadLogger 
	 * @see  AbstractListenerWriteFlushProcessor＃rsWriteFlushLogger 
	 * @see  WriteResultPublisher＃rsWriteResultLogger
	 */
	protected static final Log rsWriteLogger = LogDelegateFactory.getHiddenLog(AbstractListenerWriteProcessor.class);


	private final AtomicReference<State> state = new AtomicReference<>(State.UNSUBSCRIBED);

	@Nullable
	private Subscription subscription;

	@Nullable
	private volatile T currentData;

	/* Indicates "onComplete" was received during the (last) write. */
	/**
	 * 表示在（最后一次）写入过程中收到"onComplete"。 
	 * 
	 */
	private volatile boolean subscriberCompleted;

	/**
	 * Indicates we're waiting for one last isReady-onWritePossible cycle
	 * after "onComplete" because some Servlet containers expect this to take
	 * place prior to calling AsyncContext.complete().
	 * See https://github.com/eclipse-ee4j/servlet-api/issues/273
	 */
	/**
	 * 表示我们正在等待"onComplete"之后的最后一个isReady-onWritePossible周期，因为某些Servlet容器希望此过程在调用AsyncContext.complete（）之前发生。 
	 * 参见https://github.com/eclipse-ee4j/servlet-api/issues/273
	 */
	private volatile boolean readyToCompleteAfterLastWrite;

	private final WriteResultPublisher resultPublisher;

	private final String logPrefix;


	public AbstractListenerWriteProcessor() {
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
	public AbstractListenerWriteProcessor(String logPrefix) {
		this.logPrefix = logPrefix;
		this.resultPublisher = new WriteResultPublisher(logPrefix);
	}


	/**
	 * Create an instance with the given log prefix.
	 * @since 5.1
	 */
	/**
	 * 创建具有给定日志前缀的实例。 
	 *  @5.1起
	 */
	public String getLogPrefix() {
		return this.logPrefix;
	}


	// Subscriber methods and async I/O notification methods...

	@Override
	public final void onSubscribe(Subscription subscription) {
		this.state.get().onSubscribe(this, subscription);
	}

	@Override
	public final void onNext(T data) {
		if (rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + "Item to write");
		}
		this.state.get().onNext(this, data);
	}

	/**
	 * Error signal from the upstream, write Publisher. This is also used by
	 * sub-classes to delegate error notifications from the container.
	 */
	/**
	 * 来自上游的错误信号，写Publisher。 
	 * 子类也使用它来委托来自容器的错误通知。 
	 * 
	 */
	@Override
	public final void onError(Throwable ex) {
		if (rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + "Write source error: " + ex);
		}
		this.state.get().onError(this, ex);
	}

	/**
	 * Completion signal from the upstream, write Publisher. This is also used
	 * by sub-classes to delegate completion notifications from the container.
	 */
	/**
	 * 来自上游的完成信号，写Publisher。 
	 * 子类也使用它从容器中委派完成通知。 
	 * 
	 */
	@Override
	public final void onComplete() {
		if (rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + "No more items to write");
		}
		this.state.get().onComplete(this);
	}

	/**
	 * Invoked when writing is possible, either in the same thread after a check
	 * via {@link #isWritePossible()}, or as a callback from the underlying
	 * container.
	 */
	/**
	 * 当可以进行写入时调用，可以通过{@link  #isWritePossible（）}在检查后在同一线程中调用，也可以从基础容器中回调。 
	 * 
	 */
	public final void onWritePossible() {
		if (rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + "onWritePossible");
		}
		this.state.get().onWritePossible(this);
	}

	/**
	 * Invoked during an error or completion callback from the underlying
	 * container to cancel the upstream subscription.
	 */
	/**
	 * 在错误或完成回调期间从基础容器调用以取消上游订阅。 
	 * 
	 */
	public void cancel() {
		rsWriteLogger.trace(getLogPrefix() + "Cancellation");
		if (this.subscription != null) {
			this.subscription.cancel();
		}
	}

	// Publisher implementation for result notifications...

	@Override
	public final void subscribe(Subscriber<? super Void> subscriber) {
		// Technically, cancellation from the result subscriber should be propagated
		// to the upstream subscription. In practice, HttpHandler server adapters
		// don't have a reason to cancel the result subscription.
		this.resultPublisher.subscribe(subscriber);
	}


	// Write API methods to be implemented or template methods to override...

	/**
	 * Whether the given data item has any content to write.
	 * If false the item is not written.
	 */
	/**
	 * 给定的数据项是否有任何要写入的内容。 
	 * 如果为false，则不写入该项目。 
	 * 
	 */
	protected abstract boolean isDataEmpty(T data);

	/**
	 * Template method invoked after a data item to write is received via
	 * {@link Subscriber#onNext(Object)}. The default implementation saves the
	 * data item for writing once that is possible.
	 */
	/**
	 * 通过{@link  Subscriber＃onNext（Object）}接收到要写入的数据项后调用的模板方法。 
	 * 默认实现将数据项保存为可能的一次写入。 
	 * 
	 */
	protected void dataReceived(T data) {
		T prev = this.currentData;
		if (prev != null) {
			// This shouldn't happen:
			//   1. dataReceived can only be called from REQUESTED state
			//   2. currentData is cleared before requesting
			discardData(data);
			cancel();
			onError(new IllegalStateException("Received new data while current not processed yet."));
		}
		this.currentData = data;
	}

	/**
	 * Whether writing is possible.
	 */
	/**
	 * 是否可以写作。 
	 * 
	 */
	protected abstract boolean isWritePossible();

	/**
	 * Write the given item.
	 * <p><strong>Note:</strong> Sub-classes are responsible for releasing any
	 * data buffer associated with the item, once fully written, if pooled
	 * buffers apply to the underlying container.
	 * @param data the item to write
	 * @return whether the current data item was written and another one
	 * requested ({@code true}), or or otherwise if more writes are required.
	 */
	/**
	 * 写给定的项目。 
	 *  <p> <strong>注意：</ strong>：如果已将缓冲池应用于基础容器，则子类负责释放与该项目关联的任何数据缓冲池。 
	 *  
	 * @param 对要写入的项目进行数据
	 * @return 是否写入了当前数据并请求了另一个数据（{@code  true}），或者如果需要更多写入，则为其他方式。 
	 * 
	 */
	protected abstract boolean write(T data) throws IOException;

	/**
	 * Invoked after the current data has been written and before requesting
	 * the next item from the upstream, write Publisher.
	 * <p>The default implementation is a no-op.
	 * @deprecated originally introduced for Undertow to stop write notifications
	 * when no data is available, but deprecated as of as of 5.0.6 since constant
	 * switching on every requested item causes a significant slowdown.
	 */
	/**
	 * 在写入当前数据之后并且在从上游请求下一个项目之前调用，请编写Publisher。 
	 *  <p>默认实现是无操作。 
	 * 最初为Undertow引入的@deprecated在没有可用数据时停止写通知，但自5.0.6版本开始不推荐使用，因为对每个请求项的持续切换会导致速度显着下降。 
	 * 
	 */
	@Deprecated
	protected void writingPaused() {
	}

	/**
	 * Invoked after onComplete or onError notification.
	 * <p>The default implementation is a no-op.
	 */
	/**
	 * 在onComplete或onError通知之后调用。 
	 *  <p>默认实现是无操作。 
	 * 
	 */
	protected void writingComplete() {
	}

	/**
	 * Invoked when an I/O error occurs during a write. Sub-classes may choose
	 * to ignore this if they know the underlying API will provide an error
	 * notification in a container thread.
	 * <p>Defaults to no-op.
	 */
	/**
	 * 写入期间发生I / O错误时调用。 
	 * 如果子类知道基础API将在容器线程中提供错误通知，则可以选择忽略此子类。 
	 *  <p>默认为无操作。 
	 * 
	 */
	protected void writingFailed(Throwable ex) {
	}

	/**
	 * Invoked after any error (either from the upstream write Publisher, or
	 * from I/O operations to the underlying server) and cancellation
	 * to discard in-flight data that was in
	 * the process of being written when the error took place.
	 * @param data the data to be released
	 * @since 5.0.11
	 */
	/**
	 * 在发生任何错误（从上游write Publisher或从I / O操作到基础服务器）后调用，然后取消以丢弃发生错误时正在写入的正在进行的数据。 
	 *  
	 * @param  data自5.0.11起要发布的数据
	 */
	protected abstract void discardData(T data);


	// Private methods for use from State's...

	private boolean changeState(State oldState, State newState) {
		boolean result = this.state.compareAndSet(oldState, newState);
		if (result && rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + oldState + " -> " + newState);
		}
		return result;
	}

	private void changeStateToReceived(State oldState) {
		if (changeState(oldState, State.RECEIVED)) {
			writeIfPossible();
		}
	}

	private void changeStateToComplete(State oldState) {
		if (changeState(oldState, State.COMPLETED)) {
			discardCurrentData();
			writingComplete();
			this.resultPublisher.publishComplete();
		}
		else {
			this.state.get().onComplete(this);
		}
	}

	private void writeIfPossible() {
		boolean result = isWritePossible();
		if (!result && rsWriteLogger.isTraceEnabled()) {
			rsWriteLogger.trace(getLogPrefix() + "isWritePossible: false");
		}
		if (result) {
			onWritePossible();
		}
	}

	private void discardCurrentData() {
		T data = this.currentData;
		this.currentData = null;
		if (data != null) {
			discardData(data);
		}
	}


	/**
	 * Represents a state for the {@link Processor} to be in.
	 *
	 * <p><pre>
	 *        UNSUBSCRIBED
	 *             |
	 *             v
	 *   +--- REQUESTED -------------> RECEIVED ---+
	 *   |        ^                       ^        |
	 *   |        |                       |        |
	 *   |        + ------ WRITING <------+        |
	 *   |                    |                    |
	 *   |                    v                    |
	 *   +--------------> COMPLETED <--------------+
	 * </pre>
	 */
	/**
	 * 表示{@link 处理器}所处的状态。 
	 * <p> <pre>取消订阅| v + ---要求------------->收到--- + | ^ ^ | | | | | | + ------写<------ + | | | | | v | + -------------->已完成<-------------- + </ pre>
	 */
	private enum State {

		UNSUBSCRIBED {
			@Override
			public <T> void onSubscribe(AbstractListenerWriteProcessor<T> processor, Subscription subscription) {
				Assert.notNull(subscription, "Subscription must not be null");
				if (processor.changeState(this, REQUESTED)) {
					processor.subscription = subscription;
					subscription.request(1);
				}
				else {
					super.onSubscribe(processor, subscription);
				}
			}

			@Override
			public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
				// This can happen on (very early) completion notification from container..
				processor.changeStateToComplete(this);
			}
		},

		REQUESTED {
			@Override
			public <T> void onNext(AbstractListenerWriteProcessor<T> processor, T data) {
				if (processor.isDataEmpty(data)) {
					Assert.state(processor.subscription != null, "No subscription");
					processor.subscription.request(1);
				}
				else {
					processor.dataReceived(data);
					processor.changeStateToReceived(this);
				}
			}
			@Override
			public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
				processor.readyToCompleteAfterLastWrite = true;
				processor.changeStateToReceived(this);
			}
		},

		RECEIVED {
			@SuppressWarnings("deprecation")
			@Override
			public <T> void onWritePossible(AbstractListenerWriteProcessor<T> processor) {
				if (processor.readyToCompleteAfterLastWrite) {
					processor.changeStateToComplete(RECEIVED);
				}
				else if (processor.changeState(this, WRITING)) {
					T data = processor.currentData;
					Assert.state(data != null, "No data");
					try {
						if (processor.write(data)) {
							if (processor.changeState(WRITING, REQUESTED)) {
								processor.currentData = null;
								if (processor.subscriberCompleted) {
									processor.readyToCompleteAfterLastWrite = true;
									processor.changeStateToReceived(REQUESTED);
								}
								else {
									processor.writingPaused();
									Assert.state(processor.subscription != null, "No subscription");
									processor.subscription.request(1);
								}
							}
						}
						else {
							processor.changeStateToReceived(WRITING);
						}
					}
					catch (IOException ex) {
						processor.writingFailed(ex);
					}
				}
			}

			@Override
			public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
				processor.subscriberCompleted = true;
				// A competing write might have completed very quickly
				if (processor.state.get().equals(State.REQUESTED)) {
					processor.changeStateToComplete(State.REQUESTED);
				}
			}
		},

		WRITING {
			@Override
			public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
				processor.subscriberCompleted = true;
				// A competing write might have completed very quickly
				if (processor.state.get().equals(State.REQUESTED)) {
					processor.changeStateToComplete(State.REQUESTED);
				}
			}
		},

		COMPLETED {
			@Override
			public <T> void onNext(AbstractListenerWriteProcessor<T> processor, T data) {
				// ignore
			}
			@Override
			public <T> void onError(AbstractListenerWriteProcessor<T> processor, Throwable ex) {
				// ignore
			}
			@Override
			public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
				// ignore
			}
		};

		public <T> void onSubscribe(AbstractListenerWriteProcessor<T> processor, Subscription subscription) {
			subscription.cancel();
		}

		public <T> void onNext(AbstractListenerWriteProcessor<T> processor, T data) {
			processor.discardData(data);
			processor.cancel();
			processor.onError(new IllegalStateException("Illegal onNext without demand"));
		}

		public <T> void onError(AbstractListenerWriteProcessor<T> processor, Throwable ex) {
			if (processor.changeState(this, COMPLETED)) {
				processor.discardCurrentData();
				processor.writingComplete();
				processor.resultPublisher.publishError(ex);
			}
			else {
				processor.state.get().onError(processor, ex);
			}
		}

		public <T> void onComplete(AbstractListenerWriteProcessor<T> processor) {
			throw new IllegalStateException(toString());
		}

		public <T> void onWritePossible(AbstractListenerWriteProcessor<T> processor) {
			// ignore
		}
	}

}
