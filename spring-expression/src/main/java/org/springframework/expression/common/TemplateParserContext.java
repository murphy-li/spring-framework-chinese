/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2013 the original author or authors.
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
 * 版权所有2002-2013的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.expression.common;

import org.springframework.expression.ParserContext;

/**
 * Configurable {@link ParserContext} implementation for template parsing. Expects the
 * expression prefix and suffix as constructor arguments.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
/**
 * 用于模板解析的可配置{@link  ParserContext}实现。 
 * 期望将表达式前缀和后缀用作构造函数参数。 
 *  @author  Juergen Hoeller @始于3.0
 */
public class TemplateParserContext implements ParserContext {

	private final String expressionPrefix;

	private final String expressionSuffix;


	/**
	 * Create a new TemplateParserContext with the default "#{" prefix and "}" suffix.
	 */
	/**
	 * 用默认的"＃{"前缀和后缀"}"创建一个新的TemplateParserContext。 
	 * 
	 */
	public TemplateParserContext() {
		this("#{", "}");
	}

	/**
	 * Create a new TemplateParserContext for the given prefix and suffix.
	 * @param expressionPrefix the expression prefix to use
	 * @param expressionSuffix the expression suffix to use
	 */
	/**
	 * 为给定的前缀和后缀创建一个新的TemplateParserContext。 
	 *  
	 * @param  expression前缀要使用的表达式前缀
	 * @param  expressionSuffix要使用的表达式后缀
	 */
	public TemplateParserContext(String expressionPrefix, String expressionSuffix) {
		this.expressionPrefix = expressionPrefix;
		this.expressionSuffix = expressionSuffix;
	}


	@Override
	public final boolean isTemplate() {
		return true;
	}

	@Override
	public final String getExpressionPrefix() {
		return this.expressionPrefix;
	}

	@Override
	public final String getExpressionSuffix() {
		return this.expressionSuffix;
	}

}
