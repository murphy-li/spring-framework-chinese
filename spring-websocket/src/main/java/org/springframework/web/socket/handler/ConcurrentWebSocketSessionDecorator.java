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

package org.springframework.web.socket.handler;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Wrap a {@link org.springframework.web.socket.WebSocketSession WebSocketSession}
 * to guarantee only one thread can send messages at a time.
 *
 * <p>If a send is slow, subsequent attempts to send more messages from other threads
 * will not be able to acquire the flush lock and messages will be buffered instead.
 * At that time, the specified buffer-size limit and send-time limit will be checked
 * and the session will be closed if the limits are exceeded.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 4.0.3
 */
/**
 * 包装一个{@link  org.springframework.web.socket.WebSocketSession WebSocketSession}以确保一次只能有一个线程发送消息。 
 *  <p>如果发送速度很慢，则随后尝试从其他线程发送更多消息的尝试将无法获取刷新锁，而会将消息缓冲。 
 * 届时，将检查指定的缓冲区大小限制和发送时间限制，如果超过限制，则会话将关闭。 
 *  @author 罗森·斯托扬切夫（Rossen Stoyanchev）@author  Juergen Hoeller @始于4.0.3
 */
public class ConcurrentWebSocketSessionDecorator extends WebSocketSessionDecorator {

	private static final Log logger = LogFactory.getLog(ConcurrentWebSocketSessionDecorator.class);


	private final int sendTimeLimit;

	private final int bufferSizeLimit;

	private final OverflowStrategy overflowStrategy;

	private final Queue<WebSocketMessage<?>> buffer = new LinkedBlockingQueue<>();

	private final AtomicInteger bufferSize = new AtomicInteger();

	private volatile long sendStartTime;

	private volatile boolean limitExceeded;

	private volatile boolean closeInProgress;

	private final Lock flushLock = new ReentrantLock();

	private final Lock closeLock = new ReentrantLock();


	/**
	 * Basic constructor.
	 * @param delegate the {@code WebSocketSession} to delegate to
	 * @param sendTimeLimit the send-time limit (milliseconds)
	 * @param bufferSizeLimit the buffer-size limit (number of bytes)
	 */
	/**
	 * 基本构造函数。 
	 *  
	 * @param 委托{@code  WebSocketSession}委托给
	 * @param  sendTimeLimit发送时间限制（毫秒）
	 * @param  bufferSizeLimit缓冲区大小限制（字节数）
	 */
	public ConcurrentWebSocketSessionDecorator(WebSocketSession delegate, int sendTimeLimit, int bufferSizeLimit) {
		this(delegate, sendTimeLimit, bufferSizeLimit, OverflowStrategy.TERMINATE);
	}

	/**
	 * Constructor that also specifies the overflow strategy to use.
	 * @param delegate the {@code WebSocketSession} to delegate to
	 * @param sendTimeLimit the send-time limit (milliseconds)
	 * @param bufferSizeLimit the buffer-size limit (number of bytes)
	 * @param overflowStrategy the overflow strategy to use; by default the
	 * session is terminated.
	 * @since 5.1
	 */
	/**
	 * 构造函数，还指定要使用的溢出策略。 
	 *  
	 * @param 委托{@code  WebSocketSession}委托给
	 * @param  sendTimeLimit发送时间限制（毫秒）
	 * @param  bufferSizeLimit缓冲区大小限制（字节数）
	 * @param 溢出策略使用溢出策略； 
	 * 默认情况下，会话终止。 
	 *  @5.1起
	 */
	public ConcurrentWebSocketSessionDecorator(
			WebSocketSession delegate, int sendTimeLimit, int bufferSizeLimit, OverflowStrategy overflowStrategy) {

		super(delegate);
		this.sendTimeLimit = sendTimeLimit;
		this.bufferSizeLimit = bufferSizeLimit;
		this.overflowStrategy = overflowStrategy;
	}


	/**
	 * Return the configured send-time limit (milliseconds).
	 * @since 4.3.13
	 */
	/**
	 * 返回配置的发送时间限制（毫秒）。 
	 *  @自4.3.13起
	 */
	public int getSendTimeLimit() {
		return this.sendTimeLimit;
	}

	/**
	 * Return the configured buffer-size limit (number of bytes).
	 * @since 4.3.13
	 */
	/**
	 * 返回配置的缓冲区大小限制（字节数）。 
	 *  @自4.3.13起
	 */
	public int getBufferSizeLimit() {
		return this.bufferSizeLimit;
	}

	/**
	 * Return the current buffer size (number of bytes).
	 */
	/**
	 * 返回当前缓冲区的大小（字节数）。 
	 * 
	 */
	public int getBufferSize() {
		return this.bufferSize.get();
	}

	/**
	 * Return the time (milliseconds) since the current send started,
	 * or 0 if no send is currently in progress.
	 */
	/**
	 * 返回自当前发送开始以来的时间（毫秒），如果当前没有正在进行的发送，则返回0。 
	 * 
	 */
	public long getTimeSinceSendStarted() {
		long start = this.sendStartTime;
		return (start > 0 ? (System.currentTimeMillis() - start) : 0);
	}


	@Override
	public void sendMessage(WebSocketMessage<?> message) throws IOException {
		if (shouldNotSend()) {
			return;
		}

		this.buffer.add(message);
		this.bufferSize.addAndGet(message.getPayloadLength());

		do {
			if (!tryFlushMessageBuffer()) {
				if (logger.isTraceEnabled()) {
					logger.trace(String.format("Another send already in progress: " +
							"session id '%s':, \"in-progress\" send time %d (ms), buffer size %d bytes",
							getId(), getTimeSinceSendStarted(), getBufferSize()));
				}
				checkSessionLimits();
				break;
			}
		}
		while (!this.buffer.isEmpty() && !shouldNotSend());
	}

	private boolean shouldNotSend() {
		return (this.limitExceeded || this.closeInProgress);
	}

	private boolean tryFlushMessageBuffer() throws IOException {
		if (this.flushLock.tryLock()) {
			try {
				while (true) {
					WebSocketMessage<?> message = this.buffer.poll();
					if (message == null || shouldNotSend()) {
						break;
					}
					this.bufferSize.addAndGet(-message.getPayloadLength());
					this.sendStartTime = System.currentTimeMillis();
					getDelegate().sendMessage(message);
					this.sendStartTime = 0;
				}
			}
			finally {
				this.sendStartTime = 0;
				this.flushLock.unlock();
			}
			return true;
		}
		return false;
	}

	private void checkSessionLimits() {
		if (!shouldNotSend() && this.closeLock.tryLock()) {
			try {
				if (getTimeSinceSendStarted() > getSendTimeLimit()) {
					String format = "Send time %d (ms) for session '%s' exceeded the allowed limit %d";
					String reason = String.format(format, getTimeSinceSendStarted(), getId(), getSendTimeLimit());
					limitExceeded(reason);
				}
				else if (getBufferSize() > getBufferSizeLimit()) {
					switch (this.overflowStrategy) {
						case TERMINATE:
							String format = "Buffer size %d bytes for session '%s' exceeds the allowed limit %d";
							String reason = String.format(format, getBufferSize(), getId(), getBufferSizeLimit());
							limitExceeded(reason);
							break;
						case DROP:
							int i = 0;
							while (getBufferSize() > getBufferSizeLimit()) {
								WebSocketMessage<?> message = this.buffer.poll();
								if (message == null) {
									break;
								}
								this.bufferSize.addAndGet(-message.getPayloadLength());
								i++;
							}
							if (logger.isDebugEnabled()) {
								logger.debug("Dropped " + i + " messages, buffer size: " + getBufferSize());
							}
							break;
						default:
							// Should never happen..
							throw new IllegalStateException("Unexpected OverflowStrategy: " + this.overflowStrategy);
					}
				}
			}
			finally {
				this.closeLock.unlock();
			}
		}
	}

	private void limitExceeded(String reason) {
		this.limitExceeded = true;
		throw new SessionLimitExceededException(reason, CloseStatus.SESSION_NOT_RELIABLE);
	}

	@Override
	public void close(CloseStatus status) throws IOException {
		this.closeLock.lock();
		try {
			if (this.closeInProgress) {
				return;
			}
			if (!CloseStatus.SESSION_NOT_RELIABLE.equals(status)) {
				try {
					checkSessionLimits();
				}
				catch (SessionLimitExceededException ex) {
					// Ignore
				}
				if (this.limitExceeded) {
					if (logger.isDebugEnabled()) {
						logger.debug("Changing close status " + status + " to SESSION_NOT_RELIABLE.");
					}
					status = CloseStatus.SESSION_NOT_RELIABLE;
				}
			}
			this.closeInProgress = true;
			super.close(status);
		}
		finally {
			this.closeLock.unlock();
		}
	}


	@Override
	public String toString() {
		return getDelegate().toString();
	}


	/**
	 * Enum for options of what to do when the buffer fills up.
	 * @since 5.1
	 */
	/**
	 * 枚举有关缓冲区已满时该怎么办的选项。 
	 *  @5.1起
	 */
	public enum OverflowStrategy {

		/**
		 * Throw {@link SessionLimitExceededException} that would will result
		 * in the session being terminated.
		 */
		/**
		 * 抛出{@link  SessionLimitExceededException}，这将导致会话终止。 
		 * 
		 */
		TERMINATE,

		/**
		 * Drop the oldest messages from the buffer.
		 */
		/**
		 * 从缓冲区中删除最早的消息。 
		 * 
		 */
		DROP
	}

}
