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

package org.springframework.web.multipart;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.AbstractResource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Adapt {@link MultipartFile} to {@link org.springframework.core.io.Resource},
 * exposing the content as {@code InputStream} and also overriding
 * {@link #contentLength()} as well as {@link #getFilename()}.
 *
 * @author Rossen Stoyanchev
 * @since 5.1
 */
/**
 * 将{@link  MultipartFile}修改为{@link  org.springframework.core.io.Resource}，将内容公开为{@code  InputStream}，并将{@link  #contentLength（）}覆盖为以及{@link  #getFilename（）}。 
 *  @author  Rossen Stoyanchev @从5.1开始
 */
class MultipartFileResource extends AbstractResource {

	private final MultipartFile multipartFile;


	public MultipartFileResource(MultipartFile multipartFile) {
		Assert.notNull(multipartFile, "MultipartFile must not be null");
		this.multipartFile = multipartFile;
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

	@Override
	public long contentLength() {
		return this.multipartFile.getSize();
	}

	@Override
	public String getFilename() {
		return this.multipartFile.getOriginalFilename();
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
		return this.multipartFile.getInputStream();
	}

	/**
	 * This implementation returns a description that has the Multipart name.
	 */
	/**
	 * 此实现返回具有Multipart名称的描述。 
	 * 
	 */
	@Override
	public String getDescription() {
		return "MultipartFile resource [" + this.multipartFile.getName() + "]";
	}


	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof MultipartFileResource &&
				((MultipartFileResource) other).multipartFile.equals(this.multipartFile)));
	}

	@Override
	public int hashCode() {
		return this.multipartFile.hashCode();
	}

}
