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

package org.springframework.web.multipart.support;

import java.io.IOException;

import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Custom {@link java.beans.PropertyEditor} for converting
 * {@link MultipartFile MultipartFiles} to byte arrays.
 *
 * @author Juergen Hoeller
 * @since 13.10.2003
 */
/**
 * 自定义{@link  java.beans.PropertyEditor}，用于将{@link  MultipartFile MultipartFiles}转换为字节数组。 
 *  @author  Juergen Hoeller @2003年10月13日
 */
public class ByteArrayMultipartFileEditor extends ByteArrayPropertyEditor {

	@Override
	public void setValue(@Nullable Object value) {
		if (value instanceof MultipartFile) {
			MultipartFile multipartFile = (MultipartFile) value;
			try {
				super.setValue(multipartFile.getBytes());
			}
			catch (IOException ex) {
				throw new IllegalArgumentException("Cannot read contents of multipart file", ex);
			}
		}
		else if (value instanceof byte[]) {
			super.setValue(value);
		}
		else {
			super.setValue(value != null ? value.toString().getBytes() : null);
		}
	}

	@Override
	public String getAsText() {
		byte[] value = (byte[]) getValue();
		return (value != null ? new String(value) : "");
	}

}
