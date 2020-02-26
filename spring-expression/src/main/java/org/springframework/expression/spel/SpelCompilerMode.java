/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.expression.spel;

/**
 * Captures the possible configuration settings for a compiler that can be
 * used when evaluating expressions.
 *
 * @author Andy Clement
 * @since 4.1
 */
/**
 * 捕获可能在评估表达式时使用的编译器的配置设置。 
 *  @author 安迪·克莱门特@始于4.1
 */
public enum SpelCompilerMode {

	/**
	 * The compiler is switched off; this is the default.
	 */
	/**
	 * 编译器已关闭； 
	 * 这是默认值。 
	 * 
	 */
	OFF,

	/**
	 * In immediate mode, expressions are compiled as soon as possible (usually after 1 interpreted run).
	 * If a compiled expression fails it will throw an exception to the caller.
	 */
	/**
	 * 在立即模式下，表达式将尽快编译（通常在1个解释运行之后）。 
	 * 如果编译的表达式失败，它将向调用方抛出异常。 
	 * 
	 */
	IMMEDIATE,

	/**
	 * In mixed mode, expression evaluation silently switches between interpreted and compiled over time.
	 * After a number of runs the expression gets compiled. If it later fails (possibly due to inferred
	 * type information changing) then that will be caught internally and the system switches back to
	 * interpreted mode. It may subsequently compile it again later.
	 */
	/**
	 * 在混合模式下，表达式评估会随时间静默在解释和编译之间切换。 
	 * 经过多次运行后，表达式将被编译。 
	 * 如果以后失败（可能是由于推断的类型信息发生了更改），那么它将在内部捕获，并且系统将切换回解释模式。 
	 * 它可能随后会在以后再次编译它。 
	 * 
	 */
	MIXED

}
