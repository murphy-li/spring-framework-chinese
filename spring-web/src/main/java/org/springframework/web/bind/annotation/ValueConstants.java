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

package org.springframework.web.bind.annotation;

/**
 * Common value constants shared between bind annotations.
 *
 * @author Juergen Hoeller
 * @since 3.0.1
 */
/**
 * 绑定注释之间共享的公共值常量。 
 *  @author  Juergen Hoeller @始于3.0.1
 */
public interface ValueConstants {

	/**
	 * Constant defining a value for no default - as a replacement for
	 * {@code null} which we cannot use in annotation attributes.
	 * <p>This is an artificial arrangement of 16 unicode characters,
	 * with its sole purpose being to never match user-declared values.
	 * @see RequestParam#defaultValue()
	 * @see RequestHeader#defaultValue()
	 * @see CookieValue#defaultValue()
	 */
	/**
	 * 常量，用于定义没有默认值的值-替代我们不能在注释属性中使用的{@code  null}。 
	 *  <p>这是16个unicode字符的人工排列，其唯一目的是从不与用户声明的值匹配。 
	 *  
	 * @see  RequestParam＃defaultValue（）
	 * @see  RequestHeader＃defaultValue（）
	 * @see  CookieValue＃defaultValue（）
	 */
	String DEFAULT_NONE = "\n\t\t\n\t\t\n\uE000\uE001\uE002\n\t\t\t\t\n";

}
