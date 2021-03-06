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

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * Editor for a {@link Character}, to populate a property
 * of type {@code Character} or {@code char} from a String value.
 *
 * <p>Note that the JDK does not contain a default
 * {@link java.beans.PropertyEditor property editor} for {@code char}!
 * {@link org.springframework.beans.BeanWrapperImpl} will register this
 * editor by default.
 *
 * <p>Also supports conversion from a Unicode character sequence; e.g.
 * {@code u0041} ('A').
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Rick Evans
 * @since 1.2
 * @see Character
 * @see org.springframework.beans.BeanWrapperImpl
 */
/**
 * {@link 字符}的编辑器，用于从字符串值填充{@code 字符}或{@code  char}类型的属性。 
 *  <p>请注意，JDK不包含{@code  char}的默认{@link  java.beans.PropertyEditor属性编辑器}！ 
 *  {@link  org.springframework.beans.BeanWrapperImpl}将默认注册此编辑器。 
 *  <p>还支持从Unicode字符序列进行转换； 
 * 例如{@code  u0041}（'A'）。 
 *  @author  Juergen Hoeller @author  Rob Harrop @author  Rick Evans @since 1.2 
 * @see 字符
 * @see  org.springframework.beans.BeanWrapperImpl
 */
public class CharacterEditor extends PropertyEditorSupport {

	/**
	 * The prefix that identifies a string as being a Unicode character sequence.
	 */
	/**
	 * 将字符串标识为Unicode字符序列的前缀。 
	 * 
	 */
	private static final String UNICODE_PREFIX = "\\u";

	/**
	 * The length of a Unicode character sequence.
	 */
	/**
	 * Unicode字符序列的长度。 
	 * 
	 */
	private static final int UNICODE_LENGTH = 6;


	private final boolean allowEmpty;


	/**
	 * Create a new CharacterEditor instance.
	 * <p>The "allowEmpty" parameter controls whether an empty String is to be
	 * allowed in parsing, i.e. be interpreted as the {@code null} value when
	 * {@link #setAsText(String) text is being converted}. If {@code false},
	 * an {@link IllegalArgumentException} will be thrown at that time.
	 * @param allowEmpty if empty strings are to be allowed
	 */
	/**
	 * 创建一个新的CharacterEditor实例。 
	 *  <p>"allowEmpty"参数控制在解析中是否允许使用空字符串，即在转换{@link  #setAsText（String）文本}时将其解释为{@code  null}值。 
	 * 如果{@code  false}，则此时将抛出{@link  IllegalArgumentException}。 
	 *  
	 * @param  allowEmpty如果要允许空字符串
	 */
	public CharacterEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}


	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasLength(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if (text == null) {
			throw new IllegalArgumentException("null String cannot be converted to char type");
		}
		else if (isUnicodeCharacterSequence(text)) {
			setAsUnicode(text);
		}
		else if (text.length() == 1) {
			setValue(Character.valueOf(text.charAt(0)));
		}
		else {
			throw new IllegalArgumentException("String [" + text + "] with length " +
					text.length() + " cannot be converted to char type: neither Unicode nor single character");
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
	}


	private boolean isUnicodeCharacterSequence(String sequence) {
		return (sequence.startsWith(UNICODE_PREFIX) && sequence.length() == UNICODE_LENGTH);
	}

	private void setAsUnicode(String text) {
		int code = Integer.parseInt(text.substring(UNICODE_PREFIX.length()), 16);
		setValue(Character.valueOf((char) code));
	}

}
