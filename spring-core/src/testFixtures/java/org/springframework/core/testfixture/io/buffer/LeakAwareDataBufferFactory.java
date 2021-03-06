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

package org.springframework.core.testfixture.io.buffer;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import io.netty.buffer.PooledByteBufAllocator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.util.Assert;

/**
 * Implementation of the {@code DataBufferFactory} interface that keeps track of
 * memory leaks.
 * <p>Useful for unit tests that handle data buffers. Simply inherit from
 * {@link AbstractLeakCheckingTests} or call {@link #checkForLeaks()} in
 * a JUnit <em>after</em> method yourself, and any buffers that have not been
 * released will result in an {@link AssertionError}.
 *
 * @author Arjen Poutsma
 * @see LeakAwareDataBufferFactory
 */
/**
 * {@code  DataBufferFactory}接口的实现可跟踪内存泄漏。 
 *  <p>对于处理数据缓冲区的单元测试很有用。 
 * 只需从{@link  AbstractLeakCheckingTests}继承或自己在JUnit <em>之后</ em>方法中调用{@link  #checkForLeaks（）}，任何尚未释放的缓冲区都将导致{@link  AssertionError}。 
 *  @author  Arjen Poutsma 
 * @see  LeakAwareDataBufferFactory
 */
public class LeakAwareDataBufferFactory implements DataBufferFactory {

	private static final Log logger = LogFactory.getLog(LeakAwareDataBufferFactory.class);


	private final DataBufferFactory delegate;

	private final List<LeakAwareDataBuffer> created = new ArrayList<>();

	private final AtomicBoolean trackCreated = new AtomicBoolean(true);


	/**
	 * Creates a new {@code LeakAwareDataBufferFactory} by wrapping a
	 * {@link DefaultDataBufferFactory}.
	 */
	/**
	 * 通过包装{@link  DefaultDataBufferFactory}创建一个新的{@code  LeakAwareDataBufferFactory}。 
	 * 
	 */
	public LeakAwareDataBufferFactory() {
		this(new NettyDataBufferFactory(PooledByteBufAllocator.DEFAULT));
	}

	/**
	 * Creates a new {@code LeakAwareDataBufferFactory} by wrapping the given delegate.
	 * @param delegate the delegate buffer factory to wrap.
	 */
	/**
	 * 通过包装给定的委托来创建新的{@code  LeakAwareDataBufferFactory}。 
	 *  
	 * @param 委托要包装的委托缓冲区工厂。 
	 * 
	 */
	public LeakAwareDataBufferFactory(DataBufferFactory delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}


	/**
	 * Checks whether all of the data buffers allocated by this factory have also been released.
	 * If not, then an {@link AssertionError} is thrown. Typically used from a JUnit <em>after</em>
	 * method.
	 */
	/**
	 * 检查此工厂分配的所有数据缓冲区是否也已释放。 
	 * 如果不是，则抛出{@link  AssertionError}。 
	 * 通常在JUnit <em> after </ em>方法中使用。 
	 * 
	 */
	public void checkForLeaks() {
		this.trackCreated.set(false);
		Instant start = Instant.now();
		while (true) {
			if (this.created.stream().noneMatch(LeakAwareDataBuffer::isAllocated)) {
				return;
			}
			if (Instant.now().isBefore(start.plus(Duration.ofSeconds(5)))) {
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException ex) {
					// ignore
				}
				continue;
			}
			List<AssertionError> errors = this.created.stream()
					.filter(LeakAwareDataBuffer::isAllocated)
					.map(LeakAwareDataBuffer::leakError)
					.collect(Collectors.toList());

			errors.forEach(it -> logger.error("Leaked error: ", it));
			throw new AssertionError(errors.size() + " buffer leaks detected (see logs above)");
		}
	}

	@Override
	public DataBuffer allocateBuffer() {
		return createLeakAwareDataBuffer(this.delegate.allocateBuffer());
	}

	@Override
	public DataBuffer allocateBuffer(int initialCapacity) {
		return createLeakAwareDataBuffer(this.delegate.allocateBuffer(initialCapacity));
	}

	private DataBuffer createLeakAwareDataBuffer(DataBuffer delegateBuffer) {
		LeakAwareDataBuffer dataBuffer = new LeakAwareDataBuffer(delegateBuffer, this);
		if (this.trackCreated.get()) {
			this.created.add(dataBuffer);
		}
		return dataBuffer;
	}

	@Override
	public DataBuffer wrap(ByteBuffer byteBuffer) {
		return this.delegate.wrap(byteBuffer);
	}

	@Override
	public DataBuffer wrap(byte[] bytes) {
		return this.delegate.wrap(bytes);
	}

	@Override
	public DataBuffer join(List<? extends DataBuffer> dataBuffers) {
		// Remove LeakAwareDataBuffer wrapper so delegate can find native buffers
		dataBuffers = dataBuffers.stream()
				.map(o -> o instanceof LeakAwareDataBuffer ? ((LeakAwareDataBuffer) o).dataBuffer() : o)
				.collect(Collectors.toList());
		return new LeakAwareDataBuffer(this.delegate.join(dataBuffers), this);
	}

}
