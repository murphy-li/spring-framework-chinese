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

package org.springframework.core.codec;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;

/**
 * Abstract base class for {@link Decoder} implementations.
 *
 * @author Sebastien Deleuze
 * @author Arjen Poutsma
 * @since 5.0
 * @param <T> the element type
 */
/**
 * {@link  Decoder}实现的抽象基类。 
 *  @author  Sebastien Deleuze @author  Arjen Poutsma @从5.0起
 * @param  <T>元素类型
 */
public abstract class AbstractEncoder<T> implements Encoder<T> {

	private final List<MimeType> encodableMimeTypes;

	protected Log logger = LogFactory.getLog(getClass());


	protected AbstractEncoder(MimeType... supportedMimeTypes) {
		this.encodableMimeTypes = Arrays.asList(supportedMimeTypes);
	}


	/**
	 * Set an alternative logger to use than the one based on the class name.
	 * @param logger the logger to use
	 * @since 5.1
	 */
	/**
	 * 根据类名称设置一个替代的记录器以供使用。 
	 *  
	 * @param 记录器记录器使用@since 5.1
	 */
	public void setLogger(Log logger) {
		this.logger = logger;
	}

	/**
	 * Return the currently configured Logger.
	 * @since 5.1
	 */
	/**
	 * 返回当前配置的记录器。 
	 *  @5.1起
	 */
	public Log getLogger() {
		return logger;
	}


	@Override
	public List<MimeType> getEncodableMimeTypes() {
		return this.encodableMimeTypes;
	}

	@Override
	public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
		if (mimeType == null) {
			return true;
		}
		for (MimeType candidate : this.encodableMimeTypes) {
			if (candidate.isCompatibleWith(mimeType)) {
				return true;
			}
		}
		return false;
	}

}
