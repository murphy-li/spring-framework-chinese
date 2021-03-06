/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.parsing;

import org.springframework.util.StringUtils;

/**
 * {@link ParseState} entry representing a JavaBean property.
 *
 * @author Rob Harrop
 * @since 2.0
 */
/**
 * 代表JavaBean属性的{@link  ParseState}条目。 
 *  @author  Rob Harrop @始于2.0
 */
public class PropertyEntry implements ParseState.Entry {

	private final String name;


	/**
	 * Creates a new instance of the {@link PropertyEntry} class.
	 * @param name the name of the JavaBean property represented by this instance
	 * @throws IllegalArgumentException if the supplied {@code name} is {@code null}
	 * or consists wholly of whitespace
	 */
	/**
	 * 创建{@link  PropertyEntry}类的新实例。 
	 *  
	 * @param 名称此实例表示的JavaBean属性的名称
	 * @throws  IllegalArgumentException如果提供的{@code 名称}为{@code  null}或全部由空格组成
	 */
	public PropertyEntry(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Invalid property name '" + name + "'.");
		}
		this.name = name;
	}


	@Override
	public String toString() {
		return "Property '" + this.name + "'";
	}

}
