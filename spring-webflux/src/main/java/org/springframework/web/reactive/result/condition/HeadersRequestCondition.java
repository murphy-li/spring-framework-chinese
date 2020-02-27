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
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * A logical conjunction (' && ') request condition that matches a request against
 * a set of header expressions with syntax defined in {@link RequestMapping#headers()}.
 *
 * <p>Expressions passed to the constructor with header names 'Accept' or
 * 'Content-Type' are ignored. See {@link ConsumesRequestCondition} and
 * {@link ProducesRequestCondition} for those.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 逻辑合取（'&&'）请求条件，该条件将请求与使用{@link  RequestMapping＃headers（）}中定义的语法的一组标头表达式进行匹配。 
 *  <p>传递给构造函数的标头名称为'Accept'或'Content-Type'的表达式将被忽略。 
 * 有关这些信息，请参见{@link  ConsumesRequestCondition}和{@link  ProducesRequestCondition}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public final class HeadersRequestCondition extends AbstractRequestCondition<HeadersRequestCondition> {

	private static final HeadersRequestCondition PRE_FLIGHT_MATCH = new HeadersRequestCondition();


	private final Set<HeaderExpression> expressions;


	/**
	 * Create a new instance from the given header expressions. Expressions with
	 * header names 'Accept' or 'Content-Type' are ignored. See {@link ConsumesRequestCondition}
	 * and {@link ProducesRequestCondition} for those.
	 * @param headers media type expressions with syntax defined in {@link RequestMapping#headers()};
	 * if 0, the condition will match to every request
	 */
	/**
	 * 从给定的头表达式创建一个新实例。 
	 * 标头名称为"Accept"或"Content-Type"的表达式将被忽略。 
	 * 有关这些信息，请参见{@link  ConsumesRequestCondition}和{@link  ProducesRequestCondition}。 
	 *  
	 * @param 头媒体类型表达式，其语法在{@link  RequestMapping＃headers（）}中定义； 
	 * 如果为0，则条件将匹配每个请求
	 */
	public HeadersRequestCondition(String... headers) {
		this(parseExpressions(headers));
	}

	private HeadersRequestCondition(Set<HeaderExpression> conditions) {
		this.expressions = conditions;
	}


	private static Set<HeaderExpression> parseExpressions(String... headers) {
		Set<HeaderExpression> expressions = new LinkedHashSet<>();
		if (headers != null) {
			for (String header : headers) {
				HeaderExpression expr = new HeaderExpression(header);
				if ("Accept".equalsIgnoreCase(expr.name) || "Content-Type".equalsIgnoreCase(expr.name)) {
					continue;
				}
				expressions.add(expr);
			}
		}
		return expressions;
	}

	/**
	 * Return the contained request header expressions.
	 */
	/**
	 * 返回包含的请求标头表达式。 
	 * 
	 */
	public Set<NameValueExpression<String>> getExpressions() {
		return new LinkedHashSet<>(this.expressions);
	}

	@Override
	protected Collection<HeaderExpression> getContent() {
		return this.expressions;
	}

	@Override
	protected String getToStringInfix() {
		return " && ";
	}

	/**
	 * Returns a new instance with the union of the header expressions
	 * from "this" and the "other" instance.
	 */
	/**
	 * 返回带有"this"和"other"实例的标头表达式的并集的新实例。 
	 * 
	 */
	@Override
	public HeadersRequestCondition combine(HeadersRequestCondition other) {
		Set<HeaderExpression> set = new LinkedHashSet<>(this.expressions);
		set.addAll(other.expressions);
		return new HeadersRequestCondition(set);
	}

	/**
	 * Returns "this" instance if the request matches all expressions;
	 * or {@code null} otherwise.
	 */
	/**
	 * 如果请求与所有表达式匹配，则返回"this"实例； 
	 * 否则为{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public HeadersRequestCondition getMatchingCondition(ServerWebExchange exchange) {
		if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
			return PRE_FLIGHT_MATCH;
		}
		for (HeaderExpression expression : this.expressions) {
			if (!expression.match(exchange)) {
				return null;
			}
		}
		return this;
	}

	/**
	 * Compare to another condition based on header expressions. A condition
	 * is considered to be a more specific match, if it has:
	 * <ol>
	 * <li>A greater number of expressions.
	 * <li>A greater number of non-negated expressions with a concrete value.
	 * </ol>
	 * <p>It is assumed that both instances have been obtained via
	 * {@link #getMatchingCondition(ServerWebExchange)} and each instance
	 * contains the matching header expression only or is otherwise empty.
	 */
	/**
	 * 与基于标头表达式的另一个条件进行比较。 
	 * 如果条件具有：<ol> <li>更多数量的表达式，则被认为是更具体的匹配。 
	 *  <li>更多具有具体值的非取反表达式。 
	 *  </ ol> <p>假定两个实例都是通过{@link  #getMatchingCondition（ServerWebExchange）}获得的，并且每个实例仅包含匹配的标头表达式，否则为空。 
	 * 
	 */
	@Override
	public int compareTo(HeadersRequestCondition other, ServerWebExchange exchange) {
		int result = other.expressions.size() - this.expressions.size();
		if (result != 0) {
			return result;
		}
		return (int) (getValueMatchCount(other.expressions) - getValueMatchCount(this.expressions));
	}

	private long getValueMatchCount(Set<HeaderExpression> expressions) {
		long count = 0;
		for (HeaderExpression e : expressions) {
			if (e.getValue() != null && !e.isNegated()) {
				count++;
			}
		}
		return count;
	}


	/**
	 * Parses and matches a single header expression to a request.
	 */
	/**
	 * 解析单个标头表达式并将其匹配到请求。 
	 * 
	 */
	static class HeaderExpression extends AbstractNameValueExpression<String> {

		public HeaderExpression(String expression) {
			super(expression);
		}

		@Override
		protected boolean isCaseSensitiveName() {
			return false;
		}

		@Override
		protected String parseValue(String valueExpression) {
			return valueExpression;
		}

		@Override
		protected boolean matchName(ServerWebExchange exchange) {
			return (exchange.getRequest().getHeaders().get(this.name) != null);
		}

		@Override
		protected boolean matchValue(ServerWebExchange exchange) {
			return (this.value != null && this.value.equals(exchange.getRequest().getHeaders().getFirst(this.name)));
		}
	}

}
