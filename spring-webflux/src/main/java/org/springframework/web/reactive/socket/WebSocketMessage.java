/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */
package org.springframework.web.reactive.socket;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Representation of a WebSocket message.
 *
 * <p>See static factory methods in {@link WebSocketSession} for creating messages with
 * the {@link org.springframework.core.io.buffer.DataBufferFactory} for the session.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * WebSocket消息的表示形式。 
 *  <p>请参阅{@link  WebSocketSession}中的静态工厂方法，以使用{@link  org.springframework.core.io.buffer.DataBufferFactory}创建会话的消息。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class WebSocketMessage {

	private final Type type;

	private final DataBuffer payload;


	/**
	 * Constructor for a WebSocketMessage.
	 * <p>See static factory methods in {@link WebSocketSession} or alternatively
	 * use {@link WebSocketSession#bufferFactory()} to create the payload and
	 * then invoke this constructor.
	 */
	/**
	 * WebSocketMessage的构造方法。 
	 *  <p>请参见{@link  WebSocketSession}中的静态工厂方法，或者使用{@link  WebSocketSession＃bufferFactory（）}创建有效负载，然后调用此构造函数。 
	 * 
	 */
	public WebSocketMessage(Type type, DataBuffer payload) {
		Assert.notNull(type, "'type' must not be null");
		Assert.notNull(payload, "'payload' must not be null");
		this.type = type;
		this.payload = payload;
	}


	/**
	 * Return the message type (text, binary, etc).
	 */
	/**
	 * 返回消息类型（文本，二进制等）。 
	 * 
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Return the message payload.
	 */
	/**
	 * 返回消息有效负载。 
	 * 
	 */
	public DataBuffer getPayload() {
		return this.payload;
	}

	/**
	 * A variant of {@link #getPayloadAsText(Charset)} that uses {@code UTF-8}
	 * for decoding the raw content to text.
	 */
	/**
	 * {@link  #getPayloadAsText（Charset）}的变体，它使用{@code  UTF-8}将原始内容解码为文本。 
	 * 
	 */
	public String getPayloadAsText() {
		return getPayloadAsText(StandardCharsets.UTF_8);
	}

	/**
	 * A shortcut for decoding the raw content of the message to text with the
	 * given character encoding. This is useful for text WebSocket messages, or
	 * otherwise when the payload is expected to contain text.
	 * @param charset the character encoding
	 * @since 5.0.5
	 */
	/**
	 * 使用给定字符编码将消息的原始内容解码为文本的快捷方式。 
	 * 这对于文本WebSocket消息或在有效载荷应包含文本时非常有用。 
	 *  
	 * @param 将字符编码设置为@5.0.5起
	 */
	public String getPayloadAsText(Charset charset) {
		byte[] bytes = new byte[this.payload.readableByteCount()];
		this.payload.read(bytes);
		return new String(bytes, charset);
	}

	/**
	 * Retain the data buffer for the message payload, which is useful on
	 * runtimes (e.g. Netty) with pooled buffers. A shortcut for:
	 * <pre>
	 * DataBuffer payload = message.getPayload();
	 * DataBufferUtils.retain(payload);
	 * </pre>
	 * @see DataBufferUtils#retain(DataBuffer)
	 */
	/**
	 * 保留消息有效负载的数据缓冲区，这在带有缓冲池的运行时（例如Netty）中很有用。 
	 * 快捷方式：<pre> DataBuffer有效负载= message.getPayload（）; DataBufferUtils.retain（有效载荷）; </ pre> 
	 * @see  DataBufferUtils＃retain（DataBuffer）
	 */
	public WebSocketMessage retain() {
		DataBufferUtils.retain(this.payload);
		return this;
	}

	/**
	 * Release the payload {@code DataBuffer} which is useful on runtimes
	 * (e.g. Netty) with pooled buffers such as Netty. A shortcut for:
	 * <pre>
	 * DataBuffer payload = message.getPayload();
	 * DataBufferUtils.release(payload);
	 * </pre>
	 * @see DataBufferUtils#release(DataBuffer)
	 */
	/**
	 * 释放有效负载{@code  DataBuffer}，该负载在运行时（例如Netty）中使用缓冲池（如Netty）很有用。 
	 * 快捷方式：<pre> DataBuffer有效负载= message.getPayload（）; DataBufferUtils.release（payload）; </ pre> 
	 * @see  DataBufferUtils＃release（DataBuffer）
	 */
	public void release() {
		DataBufferUtils.release(this.payload);
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WebSocketMessage)) {
			return false;
		}
		WebSocketMessage otherMessage = (WebSocketMessage) other;
		return (this.type.equals(otherMessage.type) &&
				ObjectUtils.nullSafeEquals(this.payload, otherMessage.payload));
	}

	@Override
	public int hashCode() {
		return this.type.hashCode() * 29 + this.payload.hashCode();
	}

	@Override
	public String toString() {
		return "WebSocket " + this.type.name() + " message (" + this.payload.readableByteCount() + " bytes)";
	}


	/**
	 * WebSocket message types.
	 */
	/**
	 * WebSocket消息类型。 
	 * 
	 */
	public enum Type {
		/**
		 * Text WebSocket message.
		 */
		/**
		 * 文本WebSocket消息。 
		 * 
		 */
		TEXT,
		/**
		 * Binary WebSocket message.
		 */
		/**
		 * 二进制WebSocket消息。 
		 * 
		 */
		BINARY,
		/**
		 * WebSocket ping.
		 */
		/**
		 * WebSocket ping。 
		 * 
		 */
		PING,
		/**
		 * WebSocket pong.
		 */
		/**
		 * WebSocket乒乓球。 
		 * 
		 */
		PONG
	}

}
