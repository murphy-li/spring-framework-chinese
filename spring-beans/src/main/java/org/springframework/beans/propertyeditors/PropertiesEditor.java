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

package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import org.springframework.lang.Nullable;

/**
 * Custom {@link java.beans.PropertyEditor} for {@link Properties} objects.
 *
 * <p>Handles conversion from content {@link String} to {@code Properties} object.
 * Also handles {@link Map} to {@code Properties} conversion, for populating
 * a {@code Properties} object via XML "map" entries.
 *
 * <p>The required format is defined in the standard {@code Properties}
 * documentation. Each property must be on a new line.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see java.util.Properties#load
 */
/**
 * 自定义{@link  Properties}对象的{@link  java.beans.PropertyEditor}。 
 *  <p>处理从内容{@link 字符串}到{@code  Properties}对象的转换。 
 * 还处理{@link  Map}到{@code  Properties}的转换，以便通过XML"map"条目填充{@code  Properties}对象。 
 *  <p>所需的格式在标准{@code 属性}文档中定义。 
 * 每个属性必须在新行上。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller 
 * @see  java.util.Properties＃load
 */
public class PropertiesEditor extends PropertyEditorSupport {

	/**
	 * Convert {@link String} into {@link Properties}, considering it as
	 * properties content.
	 * @param text the text to be so converted
	 */
	/**
	 * 将{@link 字符串}转换为{@link 属性}，将其视为属性内容。 
	 *  
	 * @param 文本要转换的文本
	 */
	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {
		Properties props = new Properties();
		if (text != null) {
			try {
				// Must use the ISO-8859-1 encoding because Properties.load(stream) expects it.
				props.load(new ByteArrayInputStream(text.getBytes(StandardCharsets.ISO_8859_1)));
			}
			catch (IOException ex) {
				// Should never happen.
				throw new IllegalArgumentException(
						"Failed to parse [" + text + "] into Properties", ex);
			}
		}
		setValue(props);
	}

	/**
	 * Take {@link Properties} as-is; convert {@link Map} into {@code Properties}.
	 */
	/**
	 * 照原样使用{@link 属性}； 
	 * 将{@link 映射}转换为{@code 属性}。 
	 * 
	 */
	@Override
	public void setValue(Object value) {
		if (!(value instanceof Properties) && value instanceof Map) {
			Properties props = new Properties();
			props.putAll((Map<?, ?>) value);
			super.setValue(props);
		}
		else {
			super.setValue(value);
		}
	}

}
