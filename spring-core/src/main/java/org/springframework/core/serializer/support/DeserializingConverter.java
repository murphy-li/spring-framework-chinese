/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.serializer.support;

import java.io.ByteArrayInputStream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.util.Assert;

/**
 * A {@link Converter} that delegates to a
 * {@link org.springframework.core.serializer.Deserializer}
 * to convert data in a byte array to an object.
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @since 3.0.5
 */
/**
 * 一个{@link 转换器}委派给{@link  org.springframework.core.serializer.Deserializer}，以将字节数组中的数据转换为对象。 
 *  @author 加里·罗素@author 马克·费舍尔@author  Juergen Hoeller @3.0.5起
 */
public class DeserializingConverter implements Converter<byte[], Object> {

	private final Deserializer<Object> deserializer;


	/**
	 * Create a {@code DeserializingConverter} with default {@link java.io.ObjectInputStream}
	 * configuration, using the "latest user-defined ClassLoader".
	 * @see DefaultDeserializer#DefaultDeserializer()
	 */
	/**
	 * 使用"最新用户定义的ClassLoader"，使用默认的{@link  java.io.ObjectInputStream}配置创建一个{@code  DeserializingConverter}。 
	 *  
	 * @see  DefaultDeserializer＃DefaultDeserializer（）
	 */
	public DeserializingConverter() {
		this.deserializer = new DefaultDeserializer();
	}

	/**
	 * Create a {@code DeserializingConverter} for using an {@link java.io.ObjectInputStream}
	 * with the given {@code ClassLoader}.
	 * @since 4.2.1
	 * @see DefaultDeserializer#DefaultDeserializer(ClassLoader)
	 */
	/**
	 * 创建一个{@code  DeserializingConverter}，以将{@link  java.io.ObjectInputStream}与给定的{@code  ClassLoader}结合使用。 
	 *  @since 4.2.1 
	 * @see  DefaultDeserializer＃DefaultDeserializer（ClassLoader）
	 */
	public DeserializingConverter(ClassLoader classLoader) {
		this.deserializer = new DefaultDeserializer(classLoader);
	}

	/**
	 * Create a {@code DeserializingConverter} that delegates to the provided {@link Deserializer}.
	 */
	/**
	 * 创建一个{@code  DeserializingConverter}委托给提供的{@link  Deserializer}。 
	 * 
	 */
	public DeserializingConverter(Deserializer<Object> deserializer) {
		Assert.notNull(deserializer, "Deserializer must not be null");
		this.deserializer = deserializer;
	}


	@Override
	public Object convert(byte[] source) {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
		try {
			return this.deserializer.deserialize(byteStream);
		}
		catch (Throwable ex) {
			throw new SerializationFailedException("Failed to deserialize payload. " +
					"Is the byte array a result of corresponding serialization for " +
					this.deserializer.getClass().getSimpleName() + "?", ex);
		}
	}

}
