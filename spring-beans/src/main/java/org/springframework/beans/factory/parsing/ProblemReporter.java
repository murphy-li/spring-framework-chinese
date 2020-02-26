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

/**
 * SPI interface allowing tools and other external processes to handle errors
 * and warnings reported during bean definition parsing.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 * @see Problem
 */
/**
 * SPI接口允许工具和其他外部过程处理在bean定义解析期间报告的错误和警告。 
 *  @author  Rob Harrop @author 于尔根·霍勒（Juergen Hoeller）@从2.0开始
 * @see 问题
 */
public interface ProblemReporter {

	/**
	 * Called when a fatal error is encountered during the parsing process.
	 * <p>Implementations must treat the given problem as fatal,
	 * i.e. they have to eventually raise an exception.
	 * @param problem the source of the error (never {@code null})
	 */
	/**
	 * 在解析过程中遇到致命错误时调用。 
	 *  <p>实施必须将给定的问题视为致命问题，即最终必须提出例外。 
	 *  
	 * @param 问题是错误的根源（从不{<@@code> null}）
	 */
	void fatal(Problem problem);

	/**
	 * Called when an error is encountered during the parsing process.
	 * <p>Implementations may choose to treat errors as fatal.
	 * @param problem the source of the error (never {@code null})
	 */
	/**
	 * 在解析过程中遇到错误时调用。 
	 *  <p>实施可能选择将错误视为致命错误。 
	 *  
	 * @param 问题是错误的根源（从不{<@@code> null}）
	 */
	void error(Problem problem);

	/**
	 * Called when a warning is raised during the parsing process.
	 * <p>Warnings are <strong>never</strong> considered to be fatal.
	 * @param problem the source of the warning (never {@code null})
	 */
	/**
	 * 在解析过程中发出警告时调用。 
	 *  <p>警告<strong>绝不会</ strong>是致命的。 
	 *  
	 * @param 问题警告的来源（切勿{<@@code> null}）
	 */
	void warning(Problem problem);

}
