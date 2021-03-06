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

package org.springframework.cache.support;

import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.lang.Nullable;

/**
 * Straightforward implementation of {@link org.springframework.cache.Cache.ValueWrapper},
 * simply holding the value as given at construction and returning it from {@link #get()}.
 *
 * @author Costin Leau
 * @since 3.1
 */
/**
 * {@link  org.springframework.cache.Cache.ValueWrapper}的简单实现，只需保存构造时给出的值，然后从{@link  #get（）}返回即可。 
 *  @author  Costin Leau @3.1起
 */
public class SimpleValueWrapper implements ValueWrapper {

	@Nullable
	private final Object value;


	/**
	 * Create a new SimpleValueWrapper instance for exposing the given value.
	 * @param value the value to expose (may be {@code null})
	 */
	/**
	 * 创建一个新的SimpleValueWrapper实例以暴露给定的值。 
	 *  
	 * @param 值要公开的值（可以为{@code  null}）
	 */
	public SimpleValueWrapper(@Nullable Object value) {
		this.value = value;
	}


	/**
	 * Simply returns the value as given at construction time.
	 */
	/**
	 * 只需返回构造时给出的值即可。 
	 * 
	 */
	@Override
	@Nullable
	public Object get() {
		return this.value;
	}

}
