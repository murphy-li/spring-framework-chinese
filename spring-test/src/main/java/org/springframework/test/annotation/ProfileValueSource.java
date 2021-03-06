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

package org.springframework.test.annotation;

import org.springframework.lang.Nullable;

/**
 * <p>
 * Strategy interface for retrieving <em>profile values</em> for a given
 * testing environment.
 * </p>
 * <p>
 * Concrete implementations must provide a {@code public} no-args
 * constructor.
 * </p>
 * <p>
 * Spring provides the following out-of-the-box implementations:
 * </p>
 * <ul>
 * <li>{@link SystemProfileValueSource}</li>
 * </ul>
 *
 * @author Rod Johnson
 * @author Sam Brannen
 * @since 2.0
 * @see ProfileValueSourceConfiguration
 * @see IfProfileValue
 * @see ProfileValueUtils
 */
/**
 * <p>策略界面，用于检索给定测试环境的<em>配置文件值</ em>。 
 *  </ p> <p>具体实现必须提供{@code  public}无参数构造函数。 
 *  </ p> <p> Spring提供了以下现成的实现：</ p> <ul> <li> {<@link> SystemProfileValueSource} </ li> </ ul> @author  Rod约翰逊（Johnson）@author 山姆·布兰嫩（Sam Brannen）@从2.0开始
 * @see  ProfileValueSourceConfiguration 
 * @see  IfProfileValue 
 * @see  ProfileValueUtils
 */
public interface ProfileValueSource {

	/**
	 * Get the <em>profile value</em> indicated by the specified key.
	 * @param key the name of the <em>profile value</em>
	 * @return the String value of the <em>profile value</em>, or {@code null}
	 * if there is no <em>profile value</em> with that key
	 */
	/**
	 * 获取由指定键指示的<em>配置文件值</ em>。 
	 *  
	 * @param 键入<em>配置文件值的名称</ em> 
	 * @return  <em>配置文件值的字符串值</ em>； 
	 * 如果没有<，请输入{@code  null} em>配置文件值</ em>
	 */
	@Nullable
	String get(String key);

}
