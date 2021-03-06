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

package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link Resource} implementation for a given {@link InputStream}.
 * <p>Should only be used if no other specific {@code Resource} implementation
 * is applicable. In particular, prefer {@link ByteArrayResource} or any of the
 * file-based {@code Resource} implementations where possible.
 *
 * <p>In contrast to other {@code Resource} implementations, this is a descriptor
 * for an <i>already opened</i> resource - therefore returning {@code true} from
 * {@link #isOpen()}. Do not use an {@code InputStreamResource} if you need to
 * keep the resource descriptor somewhere, or if you need to read from a stream
 * multiple times.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 28.12.2003
 * @see ByteArrayResource
 * @see ClassPathResource
 * @see FileSystemResource
 * @see UrlResource
 */
/**
 * 给定的{@link  InputStream}的{@link  Resource}实现。 
 *  <p>仅在没有其他特定的{@code  Resource}实现适用时才应使用。 
 * 特别是，在可能的情况下，最好使用{@link  ByteArrayResource}或任何基于文件的{@code  Resource}实现。 
 *  <p>与其他{@code  Resource}实现相反，这是<i>已经打开的</ i>资源的描述符-因此从{@link  #isOpen返回{@code  true} （）}。 
 * 如果需要将资源描述符保留在某个地方，或者需要多次从流中读取，请不要使用{@code  InputStreamResource}。 
 *  @author  Juergen Hoeller @author  Sam Brannen @自28.12.2003 
 * @see  ByteArrayResource 
 * @see  ClassPathResource 
 * @see  FileSystemResource 
 * @see  UrlResource
 */
public class InputStreamResource extends AbstractResource {

	private final InputStream inputStream;

	private final String description;

	private boolean read = false;


	/**
	 * Create a new InputStreamResource.
	 * @param inputStream the InputStream to use
	 */
	/**
	 * 创建一个新的InputStreamResource。 
	 *  
	 * @param  inputStream要使用的InputStream
	 */
	public InputStreamResource(InputStream inputStream) {
		this(inputStream, "resource loaded through InputStream");
	}

	/**
	 * Create a new InputStreamResource.
	 * @param inputStream the InputStream to use
	 * @param description where the InputStream comes from
	 */
	/**
	 * 创建一个新的InputStreamResource。 
	 *  
	 * @param  inputStream要使用InputStream来源的
	 * @param 描述的InputStream
	 */
	public InputStreamResource(InputStream inputStream, @Nullable String description) {
		Assert.notNull(inputStream, "InputStream must not be null");
		this.inputStream = inputStream;
		this.description = (description != null ? description : "");
	}


	/**
	 * This implementation always returns {@code true}.
	 */
	/**
	 * 此实现始终返回{@code  true}。 
	 * 
	 */
	@Override
	public boolean exists() {
		return true;
	}

	/**
	 * This implementation always returns {@code true}.
	 */
	/**
	 * 此实现始终返回{@code  true}。 
	 * 
	 */
	@Override
	public boolean isOpen() {
		return true;
	}

	/**
	 * This implementation throws IllegalStateException if attempting to
	 * read the underlying stream multiple times.
	 */
	/**
	 * 如果尝试多次读取基础流，则此实现将引发IllegalStateException。 
	 * 
	 */
	@Override
	public InputStream getInputStream() throws IOException, IllegalStateException {
		if (this.read) {
			throw new IllegalStateException("InputStream has already been read - " +
					"do not use InputStreamResource if a stream needs to be read multiple times");
		}
		this.read = true;
		return this.inputStream;
	}

	/**
	 * This implementation returns a description that includes the passed-in
	 * description, if any.
	 */
	/**
	 * 此实现返回包含传入的描述（如果有）的描述。 
	 * 
	 */
	@Override
	public String getDescription() {
		return "InputStream resource [" + this.description + "]";
	}


	/**
	 * This implementation compares the underlying InputStream.
	 */
	/**
	 * 此实现比较基础InputStream。 
	 * 
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof InputStreamResource &&
				((InputStreamResource) other).inputStream.equals(this.inputStream)));
	}

	/**
	 * This implementation returns the hash code of the underlying InputStream.
	 */
	/**
	 * 此实现返回基础InputStream的哈希码。 
	 * 
	 */
	@Override
	public int hashCode() {
		return this.inputStream.hashCode();
	}

}
