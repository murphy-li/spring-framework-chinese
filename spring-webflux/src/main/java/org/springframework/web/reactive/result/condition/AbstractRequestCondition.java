/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.reactive.result.condition;

import java.util.Collection;
import java.util.StringJoiner;

import org.springframework.lang.Nullable;

/**
 * A base class for {@link RequestCondition} types providing implementations of
 * {@link #equals(Object)}, {@link #hashCode()}, and {@link #toString()}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 * @param <T> the type of objects that this RequestCondition can be combined
 * with and compared to
 */
/**
 * {@link  RequestCondition}类型的基类提供{@link  #equals（Object）}，{<@link> #hashCode（）}和{@link  #toString（）}的实现。 
 *  @author  Rossen Stoyanchev @从5.0开始
 * @param  <T>此RequestCondition可以与之组合并比较的对象类型
 */
public abstract class AbstractRequestCondition<T extends AbstractRequestCondition<T>> implements RequestCondition<T> {

	/**
	 * Indicates whether this condition is empty, i.e. whether or not it
	 * contains any discrete items.
	 * @return {@code true} if empty; {@code false} otherwise
	 */
	/**
	 * 指示此条件是否为空，即它是否包含任何离散项。 
	 *  
	 * @return  {@code  true}，如果为空； 
	 *  {@code  false}否则
	 */
	public boolean isEmpty() {
		return getContent().isEmpty();
	}

	/**
	 * Return the discrete items a request condition is composed of.
	 * <p>For example URL patterns, HTTP request methods, param expressions, etc.
	 * @return a collection of objects (never {@code null})
	 */
	/**
	 * 返回请求条件组成的离散项。 
	 *  <p>例如URL模式，HTTP请求方法，参数表达式等。 
	 * 
	 * @return 对象的集合（永远{<@@code> null}）
	 */
	protected abstract Collection<?> getContent();

	/**
	 * The notation to use when printing discrete items of content.
	 * <p>For example {@code " || "} for URL patterns or {@code " && "}
	 * for param expressions.
	 */
	/**
	 * 打印离散内容项时使用的符号。 
	 *  <p>例如，URL模式使用{@code "||"}或param表达式使用{@code "&&"}。 
	 * 
	 */
	protected abstract String getToStringInfix();


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		return getContent().equals(((AbstractRequestCondition<?>) other).getContent());
	}

	@Override
	public int hashCode() {
		return getContent().hashCode();
	}

	@Override
	public String toString() {
		String infix = getToStringInfix();
		StringJoiner joiner = new StringJoiner(infix, "[", "]");
		for (Object expression : getContent()) {
			joiner.add(expression.toString());
		}
		return joiner.toString();
	}

}
