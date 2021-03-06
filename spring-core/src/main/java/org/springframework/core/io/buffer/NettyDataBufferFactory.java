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

package org.springframework.core.io.buffer;

import java.nio.ByteBuffer;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import org.springframework.util.Assert;

/**
 * Implementation of the {@code DataBufferFactory} interface based on a
 * Netty {@link ByteBufAllocator}.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 5.0
 * @see io.netty.buffer.PooledByteBufAllocator
 * @see io.netty.buffer.UnpooledByteBufAllocator
 */
/**
 * 基于Netty {@link  ByteBufAllocator}的{@code  DataBufferFactory}接口的实现。 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @从5.0起
 * @see  io.netty.buffer.PooledByteBufAllocator 
 * @see  io.netty.buffer.UnpooledByteBufAllocator
 */
public class NettyDataBufferFactory implements DataBufferFactory {

	private final ByteBufAllocator byteBufAllocator;


	/**
	 * Create a new {@code NettyDataBufferFactory} based on the given factory.
	 * @param byteBufAllocator the factory to use
	 * @see io.netty.buffer.PooledByteBufAllocator
	 * @see io.netty.buffer.UnpooledByteBufAllocator
	 */
	/**
	 * 根据给定的工厂创建一个新的{@code  NettyDataBufferFactory}。 
	 *  
	 * @param  byteBufAllocator要使用的工厂
	 * @see  io.netty.buffer.PooledByteBufAllocator 
	 * @see  io.netty.buffer.UnpooledByteBufAllocator
	 */
	public NettyDataBufferFactory(ByteBufAllocator byteBufAllocator) {
		Assert.notNull(byteBufAllocator, "ByteBufAllocator must not be null");
		this.byteBufAllocator = byteBufAllocator;
	}


	/**
	 * Return the {@code ByteBufAllocator} used by this factory.
	 */
	/**
	 * 返回此工厂使用的{@code  ByteBufAllocator}。 
	 * 
	 */
	public ByteBufAllocator getByteBufAllocator() {
		return this.byteBufAllocator;
	}

	@Override
	public NettyDataBuffer allocateBuffer() {
		ByteBuf byteBuf = this.byteBufAllocator.buffer();
		return new NettyDataBuffer(byteBuf, this);
	}

	@Override
	public NettyDataBuffer allocateBuffer(int initialCapacity) {
		ByteBuf byteBuf = this.byteBufAllocator.buffer(initialCapacity);
		return new NettyDataBuffer(byteBuf, this);
	}

	@Override
	public NettyDataBuffer wrap(ByteBuffer byteBuffer) {
		ByteBuf byteBuf = Unpooled.wrappedBuffer(byteBuffer);
		return new NettyDataBuffer(byteBuf, this);
	}

	@Override
	public DataBuffer wrap(byte[] bytes) {
		ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
		return new NettyDataBuffer(byteBuf, this);
	}

	/**
	 * Wrap the given Netty {@link ByteBuf} in a {@code NettyDataBuffer}.
	 * @param byteBuf the Netty byte buffer to wrap
	 * @return the wrapped buffer
	 */
	/**
	 * 将给定的Netty {@link  ByteBuf}包装在{@code  NettyDataBuffer}中。 
	 *  
	 * @param  byteBuf要包装的Netty字节缓冲区
	 * @return 包装的缓冲区
	 */
	public NettyDataBuffer wrap(ByteBuf byteBuf) {
		byteBuf.touch();
		return new NettyDataBuffer(byteBuf, this);
	}

	/**
	 * {@inheritDoc}
	 * <p>This implementation uses Netty's {@link CompositeByteBuf}.
	 */
	/**
	 * {@inheritDoc} <p>此实现使用Netty的{@link  CompositeByteBuf}。 
	 * 
	 */
	@Override
	public DataBuffer join(List<? extends DataBuffer> dataBuffers) {
		Assert.notEmpty(dataBuffers, "DataBuffer List must not be empty");
		int bufferCount = dataBuffers.size();
		if (bufferCount == 1) {
			return dataBuffers.get(0);
		}
		CompositeByteBuf composite = this.byteBufAllocator.compositeBuffer(bufferCount);
		for (DataBuffer dataBuffer : dataBuffers) {
			Assert.isInstanceOf(NettyDataBuffer.class, dataBuffer);
			composite.addComponent(true, ((NettyDataBuffer) dataBuffer).getNativeBuffer());
		}
		return new NettyDataBuffer(composite, this);
	}

	/**
	 * Return the given Netty {@link DataBuffer} as a {@link ByteBuf}.
	 * <p>Returns the {@linkplain NettyDataBuffer#getNativeBuffer() native buffer}
	 * if {@code buffer} is a {@link NettyDataBuffer}; returns
	 * {@link Unpooled#wrappedBuffer(ByteBuffer)} otherwise.
	 * @param buffer the {@code DataBuffer} to return a {@code ByteBuf} for
	 * @return the netty {@code ByteBuf}
	 */
	/**
	 * 返回给定的Netty {@link  DataBuffer}作为{@link  ByteBuf}。 
	 *  <p>如果{@code  buffer}是{@link  NettyDataBuffer}，则返回{@link  plain NettyDataBuffer＃getNativeBuffer（）本机缓冲区}； 
	 * 否则返回{@link  Unpooled＃wrappedBuffer（ByteBuffer）}。 
	 *  
	 * @param 缓冲{@code  DataBuffer}以返回{
	 * @return  netty {@code  ByteBuf}的{@code  ByteBuf}
	 */
	public static ByteBuf toByteBuf(DataBuffer buffer) {
		if (buffer instanceof NettyDataBuffer) {
			return ((NettyDataBuffer) buffer).getNativeBuffer();
		}
		else {
			return Unpooled.wrappedBuffer(buffer.asByteBuffer());
		}
	}


	@Override
	public String toString() {
		return "NettyDataBufferFactory (" + this.byteBufAllocator + ")";
	}

}
