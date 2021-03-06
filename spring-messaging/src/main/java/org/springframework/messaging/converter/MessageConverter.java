/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * A converter to turn the payload of a {@link Message} from serialized form to a typed
 * Object and vice versa. The {@link MessageHeaders#CONTENT_TYPE} message header may be
 * used to specify the media type of the message content.
 *
 * @author Mark Fisher
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 转换器将{@link 消息}的有效负载从序列化形式转换为类型化对象，反之亦然。 
 *  {@link  MessageHeaders＃CONTENT_TYPE}消息头可用于指定消息内容的媒体类型。 
 *  @author 马克·费舍尔@author  Rossen Stoyanchev @从4.0开始
 */
public interface MessageConverter {

	/**
	 * Convert the payload of a {@link Message} from a serialized form to a typed Object
	 * of the specified target class. The {@link MessageHeaders#CONTENT_TYPE} header
	 * should indicate the MIME type to convert from.
	 * <p>If the converter does not support the specified media type or cannot perform
	 * the conversion, it should return {@code null}.
	 * @param message the input message
	 * @param targetClass the target class for the conversion
	 * @return the result of the conversion, or {@code null} if the converter cannot
	 * perform the conversion
	 */
	/**
	 * 将{@link 消息}的有效负载从序列化形式转换为指定目标类的类型化Object。 
	 *  {@link  MessageHeaders＃CONTENT_TYPE}标头应指示要转换的MIME类型。 
	 *  <p>如果转换器不支持指定的媒体类型或无法执行转换，则应返回{@code  null}。 
	 *  
	 * @param 消息输入消息
	 * @param  targetClass转换的目标类
	 * @return 转换的结果，如果转换器无法执行转换，则返回{@code  null}
	 */
	@Nullable
	Object fromMessage(Message<?> message, Class<?> targetClass);

	/**
	 * Create a {@link Message} whose payload is the result of converting the given
	 * payload Object to serialized form. The optional {@link MessageHeaders} parameter
	 * may contain a {@link MessageHeaders#CONTENT_TYPE} header to specify the target
	 * media type for the conversion and it may contain additional headers to be added
	 * to the message.
	 * <p>If the converter does not support the specified media type or cannot perform
	 * the conversion, it should return {@code null}.
	 * @param payload the Object to convert
	 * @param headers optional headers for the message (may be {@code null})
	 * @return the new message, or {@code null} if the converter does not support the
	 * Object type or the target media type
	 */
	/**
	 * 创建一个{@link 消息}，其有效载荷是将给定的有效载荷对象转换为序列化形式的结果。 
	 * 可选的{@link  MessageHeaders}参数可以包含{@link  MessageHeaders＃CONTENT_TYPE}标头，以指定转换的目标媒体类型，并且可以包含要添加到消息中的其他标头。 
	 *  <p>如果转换器不支持指定的媒体类型或无法执行转换，则应返回{@code  null}。 
	 *  
	 * @param 负载要转换的对象
	 * @param 标头消息的可选标头（可以是{@code  null}）
	 * @return 新消息，或者{@code  null}（如果转换器执行）不支持对象类型或目标媒体类型
	 */
	@Nullable
	Message<?> toMessage(Object payload, @Nullable MessageHeaders headers);

}
