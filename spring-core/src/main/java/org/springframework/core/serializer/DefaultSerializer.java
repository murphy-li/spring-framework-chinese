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

package org.springframework.core.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * A {@link Serializer} implementation that writes an object to an output stream
 * using Java serialization.
 *
 * @author Gary Russell
 * @author Mark Fisher
 * @since 3.0.5
 */
/**
 * 一个{@link  Serializer}实现，该实现使用Java序列化将对象写入输出流。 
 *  @author 加里·罗素@author 马克·费舍尔@since 3.0.5
 */
public class DefaultSerializer implements Serializer<Object> {

	/**
	 * Writes the source object to an output stream using Java serialization.
	 * The source object must implement {@link Serializable}.
	 * @see ObjectOutputStream#writeObject(Object)
	 */
	/**
	 * 使用Java序列化将源对象写入输出流。 
	 * 源对象必须实现{@link  Serializable}。 
	 *  
	 * @see  ObjectOutputStream＃writeObject（Object）
	 */
	@Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
					"but received an object of type [" + object.getClass().getName() + "]");
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
	}

}
