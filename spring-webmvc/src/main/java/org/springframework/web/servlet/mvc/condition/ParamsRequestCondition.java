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

package org.springframework.web.servlet.mvc.condition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

/**
 * A logical conjunction (' && ') request condition that matches a request against
 * a set parameter expressions with syntax defined in {@link RequestMapping#params()}.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 逻辑合取（'&&'）请求条件，该条件将请求与使用{@link  RequestMapping＃params（）}中定义的语法的设置参数表达式进行匹配。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @始于3.1
 */
public final class ParamsRequestCondition extends AbstractRequestCondition<ParamsRequestCondition> {

	private final Set<ParamExpression> expressions;


	/**
	 * Create a new instance from the given param expressions.
	 * @param params expressions with syntax defined in {@link RequestMapping#params()};
	 * 	if 0, the condition will match to every request.
	 */
	/**
	 * 从给定的param表达式创建一个新实例。 
	 *  
	 * @param 使用{@link  RequestMapping＃params（）}中定义的语法对表达式进行参数化； 
	 * 如果为0，则条件将匹配每个请求。 
	 * 
	 */
	public ParamsRequestCondition(String... params) {
		this(parseExpressions(params));
	}

	private ParamsRequestCondition(Collection<ParamExpression> conditions) {
		this.expressions = Collections.unmodifiableSet(new LinkedHashSet<>(conditions));
	}


	private static Collection<ParamExpression> parseExpressions(String... params) {
		Set<ParamExpression> expressions = new LinkedHashSet<>();
		for (String param : params) {
			expressions.add(new ParamExpression(param));
		}
		return expressions;
	}


	/**
	 * Return the contained request parameter expressions.
	 */
	/**
	 * 返回包含的请求参数表达式。 
	 * 
	 */
	public Set<NameValueExpression<String>> getExpressions() {
		return new LinkedHashSet<>(this.expressions);
	}

	@Override
	protected Collection<ParamExpression> getContent() {
		return this.expressions;
	}

	@Override
	protected String getToStringInfix() {
		return " && ";
	}

	/**
	 * Returns a new instance with the union of the param expressions
	 * from "this" and the "other" instance.
	 */
	/**
	 * 返回带有"this"和"other"实例的参数表达式的并集的新实例。 
	 * 
	 */
	@Override
	public ParamsRequestCondition combine(ParamsRequestCondition other) {
		Set<ParamExpression> set = new LinkedHashSet<>(this.expressions);
		set.addAll(other.expressions);
		return new ParamsRequestCondition(set);
	}

	/**
	 * Returns "this" instance if the request matches all param expressions;
	 * or {@code null} otherwise.
	 */
	/**
	 * 如果请求与所有参数表达式匹配，则返回"this"实例； 
	 * 否则为{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public ParamsRequestCondition getMatchingCondition(HttpServletRequest request) {
		for (ParamExpression expression : this.expressions) {
			if (!expression.match(request)) {
				return null;
			}
		}
		return this;
	}

	/**
	 * Compare to another condition based on parameter expressions. A condition
	 * is considered to be a more specific match, if it has:
	 * <ol>
	 * <li>A greater number of expressions.
	 * <li>A greater number of non-negated expressions with a concrete value.
	 * </ol>
	 * <p>It is assumed that both instances have been obtained via
	 * {@link #getMatchingCondition(HttpServletRequest)} and each instance
	 * contains the matching parameter expressions only or is otherwise empty.
	 */
	/**
	 * 与基于参数表达式的另一个条件进行比较。 
	 * 如果条件具有：<ol> <li>更多数量的表达式，则被认为是更具体的匹配。 
	 *  <li>更多具有具体值的非取反表达式。 
	 *  </ ol> <p>假定两个实例都是通过{@link  #getMatchingCondition（HttpServletRequest）}获得的，并且每个实例仅包含匹配的参数表达式，否则为空。 
	 * 
	 */
	@Override
	public int compareTo(ParamsRequestCondition other, HttpServletRequest request) {
		int result = other.expressions.size() - this.expressions.size();
		if (result != 0) {
			return result;
		}
		return (int) (getValueMatchCount(other.expressions) - getValueMatchCount(this.expressions));
	}

	private long getValueMatchCount(Set<ParamExpression> expressions) {
		long count = 0;
		for (ParamExpression e : expressions) {
			if (e.getValue() != null && !e.isNegated()) {
				count++;
			}
		}
		return count;
	}


	/**
	 * Parses and matches a single param expression to a request.
	 */
	/**
	 * 解析单个参数表达式并将其与请求匹配。 
	 * 
	 */
	static class ParamExpression extends AbstractNameValueExpression<String> {

		private final Set<String> namesToMatch = new HashSet<>(WebUtils.SUBMIT_IMAGE_SUFFIXES.length + 1);


		ParamExpression(String expression) {
			super(expression);
			this.namesToMatch.add(getName());
			for (String suffix : WebUtils.SUBMIT_IMAGE_SUFFIXES) {
				this.namesToMatch.add(getName() + suffix);
			}
		}

		@Override
		protected boolean isCaseSensitiveName() {
			return true;
		}

		@Override
		protected String parseValue(String valueExpression) {
			return valueExpression;
		}

		@Override
		protected boolean matchName(HttpServletRequest request) {
			for (String current : this.namesToMatch) {
				if (request.getParameterMap().get(current) != null) {
					return true;
				}
			}
			return request.getParameterMap().containsKey(this.name);
		}

		@Override
		protected boolean matchValue(HttpServletRequest request) {
			return ObjectUtils.nullSafeEquals(this.value, request.getParameter(this.name));
		}
	}

}
