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

package org.springframework.core.io.buffer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.function.IntPredicate;

import org.springframework.util.Assert;

/**
 * Basic abstraction over byte buffers.
 *
 * <p>{@code DataBuffer}s has a separate {@linkplain #readPosition() read} and
 * {@linkplain #writePosition() write} position, as opposed to {@code ByteBuffer}'s
 * single {@linkplain ByteBuffer#position() position}. As such, the {@code DataBuffer}
 * does not require a {@linkplain ByteBuffer#flip() flip} to read after writing. In general,
 * the following invariant holds for the read and write positions, and the capacity:
 *
 * <blockquote>
 *     <tt>0</tt> <tt>&lt;=</tt>
 *     <i>readPosition</i> <tt>&lt;=</tt>
 *     <i>writePosition</i> <tt>&lt;=</tt>
 *     <i>capacity</i>
 * </blockquote>
 *
 * <p>The {@linkplain #capacity() capacity} of a {@code DataBuffer} is expanded on demand,
 * similar to {@code StringBuilder}.
 *
 * <p>The main purpose of the {@code DataBuffer} abstraction is to provide a convenient wrapper
 * around {@link ByteBuffer} which is similar to Netty's {@link io.netty.buffer.ByteBuf} but
 * can also be used on non-Netty platforms (i.e. Servlet containers).
 *
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @since 5.0
 * @see DataBufferFactory
 */
/**
 * 字节缓冲区的基本抽象。 
 *  <p> {<@code> DataBuffer} s具有一个单独的{@link  plain #readPosition（）read}和{@link  plain #writePosition（）write}位置，而不是{@code  ByteBuffer }的单个{@link  plain ByteBuffer＃position（）位置}。 
 * 这样，{<@code> DataBuffer}不需要{@link  plain ByteBuffer＃flip（）flip}即可在写入后读取。 
 * 通常，以下不变式适用于读取和写入位置以及容量：<blockquote> <tt> 0 </ tt> <tt> <= </ tt> <i> readPosition </ i> <tt> < = </ tt> <i> writePosition </ i> <tt> <= </ tt> <i>容量</ i> </ blockquote> <p> {<@link>普通#capacity（）容量} {@code  DataBuffer}的大小按需扩展，类似于{@code  StringBuilder}。 
 *  <p> {@code  DataBuffer}抽象的主要目的是为{@link  ByteBuffer}提供一个方便的包装器，类似于Netty的{@link  io.netty.buffer.ByteBuf}，但可以也可以在非Netty平台（即Servlet容器）上使用。 
 *  @author  Arjen Poutsma @author  Brian Clozel @从5.0开始
 * @see  DataBufferFactory
 */
public interface DataBuffer {

	/**
	 * Return the {@link DataBufferFactory} that created this buffer.
	 * @return the creating buffer factory
	 */
	/**
	 * 返回创建此缓冲区的{@link  DataBufferFactory}。 
	 *  
	 * @return 创建缓冲区工厂
	 */
	DataBufferFactory factory();

	/**
	 * Return the index of the first byte in this buffer that matches
	 * the given predicate.
	 * @param predicate the predicate to match
	 * @param fromIndex the index to start the search from
	 * @return the index of the first byte that matches {@code predicate};
	 * or {@code -1} if none match
	 */
	/**
	 * 返回此缓冲区中与给定谓词匹配的第一个字节的索引。 
	 *  
	 * @param 谓词匹配
	 * @param  fromIndex的索引，以从
	 * @return 匹配{@code 谓词}的第一个字节的索引开始搜索； 
	 * 或{@code  -1}（如果不匹配）
	 */
	int indexOf(IntPredicate predicate, int fromIndex);

	/**
	 * Return the index of the last byte in this buffer that matches
	 * the given predicate.
	 * @param predicate the predicate to match
	 * @param fromIndex the index to start the search from
	 * @return the index of the last byte that matches {@code predicate};
	 * or {@code -1} if none match
	 */
	/**
	 * 返回此缓冲区中与给定谓词匹配的最后一个字节的索引。 
	 *  
	 * @param 谓词匹配
	 * @param  fromIndex的索引，以从
	 * @return 匹配{@code 谓词}的最后一个字节的索引开始搜索； 
	 * 或{@code  -1}（如果不匹配）
	 */
	int lastIndexOf(IntPredicate predicate, int fromIndex);

	/**
	 * Return the number of bytes that can be read from this data buffer.
	 * @return the readable byte count
	 */
	/**
	 * 返回可以从此数据缓冲区读取的字节数。 
	 *  
	 * @return 可读字节数
	 */
	int readableByteCount();

	/**
	 * Return the number of bytes that can be written to this data buffer.
	 * @return the writable byte count
	 * @since 5.0.1
	 */
	/**
	 * 返回可以写入此数据缓冲区的字节数。 
	 *  
	 * @return 自5.0.1起的可写字节数
	 */
	int writableByteCount();

	/**
	 * Return the number of bytes that this buffer can contain.
	 * @return the capacity
	 * @since 5.0.1
	 */
	/**
	 * 返回此缓冲区可以包含的字节数。 
	 *  
	 * @return 自@5.0.1起的容量
	 */
	int capacity();

	/**
	 * Set the number of bytes that this buffer can contain.
	 * <p>If the new capacity is lower than the current capacity, the contents
	 * of this buffer will be truncated. If the new capacity is higher than
	 * the current capacity, it will be expanded.
	 * @param capacity the new capacity
	 * @return this buffer
	 */
	/**
	 * 设置此缓冲区可以包含的字节数。 
	 *  <p>如果新容量小于当前容量，则该缓冲区的内容将被截断。 
	 * 如果新容量高于当前容量，则会对其进行扩展。 
	 *  
	 * @param 容量新容量
	 * @return 此缓冲区
	 */
	DataBuffer capacity(int capacity);

	/**
	 * Ensure that the current buffer has enough {@link #writableByteCount()}
	 * to write the amount of data given as an argument. If not, the missing
	 * capacity will be added to the buffer.
	 * @param capacity the writable capacity to check for
	 * @return this buffer
	 * @since 5.1.4
	 */
	/**
	 * 确保当前缓冲区具有足够的{@link  #writableByteCount（）}来写入作为参数给出的数据量。 
	 * 如果没有，丢失的容量将添加到缓冲区。 
	 *  
	 * @param 容量@5.1.4开始检查该缓冲区
	 * @return 的可写容量
	 */
	default DataBuffer ensureCapacity(int capacity) {
		return this;
	}

	/**
	 * Return the position from which this buffer will read.
	 * @return the read position
	 * @since 5.0.1
	 */
	/**
	 * 返回此缓冲区将从其读取的位置。 
	 *  
	 * @return 从5.0.1开始的读取位置
	 */
	int readPosition();

	/**
	 * Set the position from which this buffer will read.
	 * @param readPosition the new read position
	 * @return this buffer
	 * @throws IndexOutOfBoundsException if {@code readPosition} is smaller than 0
	 * or greater than {@link #writePosition()}
	 * @since 5.0.1
	 */
	/**
	 * 设置将从中读取该缓冲区的位置。 
	 *  
	 * @param  readPosition新的读取位置
	 * @return 此缓冲区
	 * @throws  IndexOutOfBoundsException如果{@code  readPosition}小于0或大于{@link  #writePosition（）} @5.0.1起
	 */
	DataBuffer readPosition(int readPosition);

	/**
	 * Return the position to which this buffer will write.
	 * @return the write position
	 * @since 5.0.1
	 */
	/**
	 * 返回此缓冲区将写入的位置。 
	 *  
	 * @return 从5.0.1开始的写位置
	 */
	int writePosition();

	/**
	 * Set the position to which this buffer will write.
	 * @param writePosition the new write position
	 * @return this buffer
	 * @throws IndexOutOfBoundsException if {@code writePosition} is smaller than
	 * {@link #readPosition()} or greater than {@link #capacity()}
	 * @since 5.0.1
	 */
	/**
	 * 设置此缓冲区将写入的位置。 
	 *  
	 * @param  writePosition新的写入位置
	 * @return 此缓冲区
	 * @throws  IndexOutOfBoundsException如果{@code  writePosition}小于{@link  #readPosition（）}或大于{@link ＃ Capacity（）} @5.0.1起
	 */
	DataBuffer writePosition(int writePosition);

	/**
	 * Read a single byte at the given index from this data buffer.
	 * @param index the index at which the byte will be read
	 * @return the byte at the given index
	 * @throws IndexOutOfBoundsException when {@code index} is out of bounds
	 * @since 5.0.4
	 */
	/**
	 * 从该数据缓冲区读取给定索引处的单个字节。 
	 *  
	 * @param 索引将读取字节的索引
	 * @return 给定索引处的字节
	 * @throws  IndexOutOfBoundsException，当{@code  index}超出范围时@@5.0.4起
	 */
	byte getByte(int index);

	/**
	 * Read a single byte from the current reading position from this data buffer.
	 * @return the byte at this buffer's current reading position
	 */
	/**
	 * 从该数据缓冲区的当前读取位置读取一个字节。 
	 *  
	 * @return 该缓冲区当前读取位置处的字节
	 */
	byte read();

	/**
	 * Read this buffer's data into the specified destination, starting at the current
	 * reading position of this buffer.
	 * @param destination the array into which the bytes are to be written
	 * @return this buffer
	 */
	/**
	 * 从该缓冲区的当前读取位置开始，将该缓冲区的数据读取到指定的目的地。 
	 *  
	 * @param 将字节写入其中的数组的目的地
	 * @return 此缓冲区
	 */
	DataBuffer read(byte[] destination);

	/**
	 * Read at most {@code length} bytes of this buffer into the specified destination,
	 * starting at the current reading position of this buffer.
	 * @param destination the array into which the bytes are to be written
	 * @param offset the index within {@code destination} of the first byte to be written
	 * @param length the maximum number of bytes to be written in {@code destination}
	 * @return this buffer
	 */
	/**
	 * 从此缓冲区的当前读取位置开始，最多读取此缓冲区的{@code  length}个字节到指定的目标。 
	 *  
	 * @param 目标待写入字节的数组
	 * @param 偏移要写入的第一个字节的{@code  destination}中的索引
	 * @param 长度最大要写入的字节数在{@code 目标} {
	 * @return>此缓冲区中
	 */
	DataBuffer read(byte[] destination, int offset, int length);

	/**
	 * Write a single byte into this buffer at the current writing position.
	 * @param b the byte to be written
	 * @return this buffer
	 */
	/**
	 * 在当前写入位置将一个字节写入此缓冲区。 
	 *  
	 * @param  b要写入的字节
	 * @return 此缓冲区
	 */
	DataBuffer write(byte b);

	/**
	 * Write the given source into this buffer, starting at the current writing position
	 * of this buffer.
	 * @param source the bytes to be written into this buffer
	 * @return this buffer
	 */
	/**
	 * 从该缓冲区的当前写入位置开始，将给定的源写入该缓冲区。 
	 *  
	 * @param 源将要写入此缓冲区的字节
	 * @return 此缓冲区
	 */
	DataBuffer write(byte[] source);

	/**
	 * Write at most {@code length} bytes of the given source into this buffer, starting
	 * at the current writing position of this buffer.
	 * @param source the bytes to be written into this buffer
	 * @param offset the index within {@code source} to start writing from
	 * @param length the maximum number of bytes to be written from {@code source}
	 * @return this buffer
	 */
	/**
	 * 从此缓冲区的当前写入位置开始，最多将给定源的{@code  length}个字节写入此缓冲区。 
	 *  
	 * @param 源将要写入此缓冲区的字节
	 * @param 偏移{@code  source}中的索引，以从
	 * @param 长度开始写入要从{<@code >源} 
	 * @return 此缓冲区
	 */
	DataBuffer write(byte[] source, int offset, int length);

	/**
	 * Write one or more {@code DataBuffer}s to this buffer, starting at the current
	 * writing position. It is the responsibility of the caller to
	 * {@linkplain DataBufferUtils#release(DataBuffer) release} the given data buffers.
	 * @param buffers the byte buffers to write into this buffer
	 * @return this buffer
	 */
	/**
	 * 从当前写入位置开始，将一个或多个{@code  DataBuffer}写入此缓冲区。 
	 * 调用方有责任{@link  plain DataBufferUtils＃release（DataBuffer）release}给定的数据缓冲区。 
	 *  
	 * @param 缓冲字节缓冲区以写入此缓冲区
	 * @return 此缓冲区
	 */
	DataBuffer write(DataBuffer... buffers);

	/**
	 * Write one or more {@link ByteBuffer} to this buffer, starting at the current
	 * writing position.
	 * @param buffers the byte buffers to write into this buffer
	 * @return this buffer
	 */
	/**
	 * 从当前写入位置开始，将一个或多个{@link  ByteBuffer}写入此缓冲区。 
	 *  
	 * @param 缓冲字节缓冲区以写入此缓冲区
	 * @return 此缓冲区
	 */
	DataBuffer write(ByteBuffer... buffers);

	/**
	 * Write the given {@code CharSequence} using the given {@code Charset},
	 * starting at the current writing position.
	 * @param charSequence the char sequence to write into this buffer
	 * @param charset the charset to encode the char sequence with
	 * @return this buffer
	 * @since 5.1.4
	 */
	/**
	 * 从当前写入位置开始，使用给定的{@code  Charset}编写给定的{@code  CharSequence}。 
	 *  
	 * @param  charSequence要写入此缓冲区的char序列
	 * @param  charset使用
	 * @return 此缓冲区对char序列进行编码的字符集@5.1.4起
	 */
	default DataBuffer write(CharSequence charSequence, Charset charset) {
		Assert.notNull(charSequence, "CharSequence must not be null");
		Assert.notNull(charset, "Charset must not be null");
		if (charSequence.length() != 0) {
			CharsetEncoder charsetEncoder = charset.newEncoder()
					.onMalformedInput(CodingErrorAction.REPLACE)
					.onUnmappableCharacter(CodingErrorAction.REPLACE);
			CharBuffer inBuffer = CharBuffer.wrap(charSequence);
			int estimatedSize = (int) (inBuffer.remaining() * charsetEncoder.averageBytesPerChar());
			ByteBuffer outBuffer = ensureCapacity(estimatedSize)
					.asByteBuffer(writePosition(), writableByteCount());
			while (true) {
				CoderResult cr = (inBuffer.hasRemaining() ?
						charsetEncoder.encode(inBuffer, outBuffer, true) : CoderResult.UNDERFLOW);
				if (cr.isUnderflow()) {
					cr = charsetEncoder.flush(outBuffer);
				}
				if (cr.isUnderflow()) {
					break;
				}
				if (cr.isOverflow()) {
					writePosition(writePosition() + outBuffer.position());
					int maximumSize = (int) (inBuffer.remaining() * charsetEncoder.maxBytesPerChar());
					ensureCapacity(maximumSize);
					outBuffer = asByteBuffer(writePosition(), writableByteCount());
				}
			}
			writePosition(writePosition() + outBuffer.position());
		}
		return this;
	}

	/**
	 * Create a new {@code DataBuffer} whose contents is a shared subsequence of this
	 * data buffer's content.  Data between this data buffer and the returned buffer is
	 * shared; though changes in the returned buffer's position will not be reflected
	 * in the reading nor writing position of this data buffer.
	 * <p><strong>Note</strong> that this method will <strong>not</strong> call
	 * {@link DataBufferUtils#retain(DataBuffer)} on the resulting slice: the reference
	 * count will not be increased.
	 * @param index the index at which to start the slice
	 * @param length the length of the slice
	 * @return the specified slice of this data buffer
	 */
	/**
	 * 创建一个新的{@code  DataBuffer}，其内容是此数据缓冲区内容的共享子序列。 
	 * 此数据缓冲区和返回的缓冲区之间的数据是共享的； 
	 * 尽管返回缓冲区位置的变化不会反映在该数据缓冲区的读取或写入位置中。 
	 *  <p> <strong>注意</ strong>，该方法将<strong>不</ strong>调用生成的切片上的{@link  DataBufferUtils＃retain（DataBuffer）}：不会增加引用计数。 
	 *  
	 * @param 索引开始切片的索引
	 * @param 长度切片的长度
	 * @return 此数据缓冲区的指定切片
	 */
	DataBuffer slice(int index, int length);

	/**
	 * Create a new {@code DataBuffer} whose contents is a shared, retained subsequence of this
	 * data buffer's content.  Data between this data buffer and the returned buffer is
	 * shared; though changes in the returned buffer's position will not be reflected
	 * in the reading nor writing position of this data buffer.
	 * <p><strong>Note</strong> that unlike {@link #slice(int, int)}, this method
	 * <strong>will</strong> call {@link DataBufferUtils#retain(DataBuffer)} (or equivalent) on the
	 * resulting slice.
	 * @param index the index at which to start the slice
	 * @param length the length of the slice
	 * @return the specified, retained slice of this data buffer
	 * @since 5.2
	 */
	/**
	 * 创建一个新的{@code  DataBuffer}，其内容是该数据缓冲区内容的共享保留子序列。 
	 * 此数据缓冲区和返回的缓冲区之间的数据是共享的； 
	 * 尽管返回缓冲区位置的变化不会反映在该数据缓冲区的读取或写入位置中。 
	 *  <p> <strong>注意</ strong>，与{@link  #slice（int，int）}不同，此方法<strong>将</ strong>调用{@link  DataBufferUtils＃retain（DataBuffer）} （或等效）在结果切片上。 
	 *  
	 * @param 索引开始切片的索引
	 * @param 长度切片的长度
	 * @return 此数据缓冲区的指定保留切片，从5.2开始
	 */
	default DataBuffer retainedSlice(int index, int length) {
		return DataBufferUtils.retain(slice(index, length));
	}

	/**
	 * Expose this buffer's bytes as a {@link ByteBuffer}. Data between this
	 * {@code DataBuffer} and the returned {@code ByteBuffer} is shared; though
	 * changes in the returned buffer's {@linkplain ByteBuffer#position() position}
	 * will not be reflected in the reading nor writing position of this data buffer.
	 * @return this data buffer as a byte buffer
	 */
	/**
	 * 将此缓冲区的字节公开为{@link  ByteBuffer}。 
	 * 此{@code  DataBuffer}与返回的{@code  ByteBuffer}之间的数据是共享的； 
	 * 尽管返回的缓冲区的{@link  plain ByteBuffer＃position（）position}中的更改将不会反映在此数据缓冲区的读写位置中。 
	 *  
	 * @return 将此数据缓冲区作为字节缓冲区
	 */
	ByteBuffer asByteBuffer();

	/**
	 * Expose a subsequence of this buffer's bytes as a {@link ByteBuffer}. Data between
	 * this {@code DataBuffer} and the returned {@code ByteBuffer} is shared; though
	 * changes in the returned buffer's {@linkplain ByteBuffer#position() position}
	 * will not be reflected in the reading nor writing position of this data buffer.
	 * @param index the index at which to start the byte buffer
	 * @param length the length of the returned byte buffer
	 * @return this data buffer as a byte buffer
	 * @since 5.0.1
	 */
	/**
	 * 将此缓冲区字节的子序列公开为{@link  ByteBuffer}。 
	 * 此{@code  DataBuffer}与返回的{@code  ByteBuffer}之间的数据是共享的； 
	 * 尽管返回的缓冲区的{@link  plain ByteBuffer＃position（）position}中的更改将不会反映在此数据缓冲区的读写位置中。 
	 *  
	 * @param 索引开始字节缓冲区的索引
	 * @param 长度返回的字节缓冲区的长度
	 * @return 此数据缓冲区作为字节缓冲区@5.0.1起
	 */
	ByteBuffer asByteBuffer(int index, int length);

	/**
	 * Expose this buffer's data as an {@link InputStream}. Both data and read position are
	 * shared between the returned stream and this data buffer. The underlying buffer will
	 * <strong>not</strong> be {@linkplain DataBufferUtils#release(DataBuffer) released}
	 * when the input stream is {@linkplain InputStream#close() closed}.
	 * @return this data buffer as an input stream
	 * @see #asInputStream(boolean)
	 */
	/**
	 * 将此缓冲区的数据公开为{@link  InputStream}。 
	 * 数据和读取位置在返回的流和此数据缓冲区之间共享。 
	 * 当输入流{{@link> plain InputStream＃close（）已关闭）时，基础缓冲区将<strong>不</ strong>被{@link  plain DataBufferUtils＃release（DataBuffer）发布}。 
	 *  
	 * @return 将此数据缓冲区作为输入流
	 * @see  #asInputStream（boolean）
	 */
	InputStream asInputStream();

	/**
	 * Expose this buffer's data as an {@link InputStream}. Both data and read position are
	 * shared between the returned stream and this data buffer.
	 * @param releaseOnClose whether the underlying buffer will be
	 * {@linkplain DataBufferUtils#release(DataBuffer) released} when the input stream is
	 * {@linkplain InputStream#close() closed}.
	 * @return this data buffer as an input stream
	 * @since 5.0.4
	 */
	/**
	 * 将此缓冲区的数据公开为{@link  InputStream}。 
	 * 数据和读取位置在返回的流和此数据缓冲区之间共享。 
	 *  
	 * @param  releaseOnClose当输入流被{@link  plain InputStream＃close（）关闭}时，基础缓冲区是否将被{@link  plain DataBufferUtils＃release（DataBuffer）释放}。 
	 * 从5.0.4开始
	 * @return 此数据缓冲区作为输入流
	 */
	InputStream asInputStream(boolean releaseOnClose);

	/**
	 * Expose this buffer's data as an {@link OutputStream}. Both data and write position are
	 * shared between the returned stream and this data buffer.
	 * @return this data buffer as an output stream
	 */
	/**
	 * 将此缓冲区的数据公开为{@link  OutputStream}。 
	 * 数据和写入位置在返回的流和此数据缓冲区之间共享。 
	 *  
	 * @return 此数据缓冲区作为输出流
	 */
	OutputStream asOutputStream();

	/**
	 * Return this buffer's data a String using the specified charset. Default implementation
	 * delegates to {@code toString(readPosition(), readableByteCount(), charset)}.
	 * @param charset the character set to use
	 * @return a string representation of all this buffers data
	 * @since 5.2
	 */
	/**
	 * 使用指定的字符集将此缓冲区的数据返回一个String。 
	 * 默认实现委托给{@code  toString（readPosition（），readByteByte（），charset）}。 
	 *  
	 * @param 设置字符集以使用
	 * @return 所有此缓冲区数据的字符串表示形式（从5.2开始）
	 */
	default String toString(Charset charset) {
		Assert.notNull(charset, "Charset must not be null");
		return toString(readPosition(), readableByteCount(), charset);
	}

	/**
	 * Return a part of this buffer's data as a String using the specified charset.
	 * @param index the index at which to start the string
	 * @param length the number of bytes to use for the string
	 * @param charset the charset to use
	 * @return a string representation of a part of this buffers data
	 * @since 5.2
	 */
	/**
	 * 使用指定的字符集以String形式返回此缓冲区数据的一部分。 
	 *  
	 * @param 索引开始字符串的索引
	 * @param 长度用于字符串的字节数
	 * @param 字符集字符集使用
	 * @return 此缓冲区数据的一部分的字符串表示形式@5.2起
	 */
	String toString(int index, int length, Charset charset);

}
