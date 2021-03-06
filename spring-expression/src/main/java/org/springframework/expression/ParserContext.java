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

package org.springframework.expression;

/**
 * Input provided to an expression parser that can influence an expression
 * parsing/compilation routine.
 *
 * @author Keith Donald
 * @author Andy Clement
 * @since 3.0
 */
/**
 * 提供给表达式解析器的输入可能会影响表达式解析/编译例程。 
 *  @author 基思·唐纳德@author 安迪·克莱门特@始于3.0
 */
public interface ParserContext {

	/**
	 * Whether or not the expression being parsed is a template. A template expression
	 * consists of literal text that can be mixed with evaluatable blocks. Some examples:
	 * <pre class="code">
	 * 	   Some literal text
	 *     Hello #{name.firstName}!
	 *     #{3 + 4}
	 * </pre>
	 * @return true if the expression is a template, false otherwise
	 */
	/**
	 * 解析的表达式是否是模板。 
	 * 模板表达式由可与可评估块混合的文字文本组成。 
	 * 一些示例：<pre class ="code">一些文字文本Hello＃{name.firstName}！ 
	 *  ＃{3 + 4} </ pre> 
	 * @return 如果表达式是模板，则为true，否则为false
	 */
	boolean isTemplate();

	/**
	 * For template expressions, returns the prefix that identifies the start of an
	 * expression block within a string. For example: "${"
	 * @return the prefix that identifies the start of an expression
	 */
	/**
	 * 对于模板表达式，返回标识字符串中表达式块开始的前缀。 
	 * 例如："$ {"
	 * @return 标识表达式开始的前缀
	 */
	String getExpressionPrefix();

	/**
	 * For template expressions, return the prefix that identifies the end of an
	 * expression block within a string. For example: "}"
	 * @return the suffix that identifies the end of an expression
	 */
	/**
	 * 对于模板表达式，返回标识字符串中表达式块结尾的前缀。 
	 * 例如："}"
	 * @return 标识表达式结尾的后缀
	 */
	String getExpressionSuffix();


	/**
	 * The default ParserContext implementation that enables template expression
	 * parsing mode. The expression prefix is "#{" and the expression suffix is "}".
	 * @see #isTemplate()
	 */
	/**
	 * 启用模板表达式解析模式的默认ParserContext实现。 
	 * 表达式前缀为"＃{"，表达式后缀为"}"。 
	 *  
	 * @see  #isTemplate（）
	 */
	ParserContext TEMPLATE_EXPRESSION = new ParserContext() {

		@Override
		public boolean isTemplate() {
			return true;
		}

		@Override
		public String getExpressionPrefix() {
			return "#{";
		}

		@Override
		public String getExpressionSuffix() {
			return "}";
		}
	};

}
