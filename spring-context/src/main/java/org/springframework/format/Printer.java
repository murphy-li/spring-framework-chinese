/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.format;

import java.util.Locale;

/**
 * Prints objects of type T for display.
 *
 * @author Keith Donald
 * @since 3.0
 * @param <T> the type of object this Printer prints
 */
/**
 * 打印类型为T的对象以进行显示。 
 *  @author  Keith Donald @从3.0开始
 * @param  <T>本打印机打印的对象类型
 */
@FunctionalInterface
public interface Printer<T> {

	/**
	 * Print the object of type T for display.
	 * @param object the instance to print
	 * @param locale the current user locale
	 * @return the printed text string
	 */
	/**
	 * 打印类型为T的对象以进行显示。 
	 *  
	 * @param 对象实例以打印
	 * @param 语言环境当前用户语言环境
	 * @return 打印的文本字符串
	 */
	String print(T object, Locale locale);

}
