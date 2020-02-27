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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition.HeaderExpression;

/**
 * A logical disjunction (' || ') request condition to match a request's
 * 'Content-Type' header to a list of media type expressions. Two kinds of
 * media type expressions are supported, which are described in
 * {@link RequestMapping#consumes()} and {@link RequestMapping#headers()}
 * where the header name is 'Content-Type'. Regardless of which syntax is
 * used, the semantics are the same.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 逻辑析取（'||'）请求条件，用于将请求的"Content-Type"标头与媒体类型表达式列表进行匹配。 
 * 支持两种媒体类型表达式，它们在{@link  RequestMapping＃consumes（）}和{@link  RequestMapping＃headers（）}中进行了描述，其中标头名称为"Content-Type"。 
 * 无论使用哪种语法，语义都是相同的。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @始于3.1
 */
public final class ConsumesRequestCondition extends AbstractRequestCondition<ConsumesRequestCondition> {

	private static final ConsumesRequestCondition EMPTY_CONDITION = new ConsumesRequestCondition();


	private final List<ConsumeMediaTypeExpression> expressions;

	private boolean bodyRequired = true;


	/**
	 * Creates a new instance from 0 or more "consumes" expressions.
	 * @param consumes expressions with the syntax described in
	 * {@link RequestMapping#consumes()}; if 0 expressions are provided,
	 * the condition will match to every request
	 */
	/**
	 * 从0个或多个"消耗"表达式创建一个新实例。 
	 *  
	 * @param 使用{@link  RequestMapping＃consumes（）}中描述的语法使用表达式； 
	 * 如果提供0个表达式，则条件将匹配每个请求
	 */
	public ConsumesRequestCondition(String... consumes) {
		this(consumes, null);
	}

	/**
	 * Creates a new instance with "consumes" and "header" expressions.
	 * "Header" expressions where the header name is not 'Content-Type' or have
	 * no header value defined are ignored. If 0 expressions are provided in
	 * total, the condition will match to every request
	 * @param consumes as described in {@link RequestMapping#consumes()}
	 * @param headers as described in {@link RequestMapping#headers()}
	 */
	/**
	 * 用"consums"和"header"表达式创建一个新实例。 
	 * 标头名称不是"Content-Type"或未定义标头值的"标头"表达式将被忽略。 
	 * 如果总共提供了0个表达式，则条件将与{@
	 * @param>消耗的每个请求匹配，如{@link  RequestMapping＃consumes（）} 
	 * @param 标头中所述，如{@link  RequestMapping＃headers中所述（）}
	 */
	public ConsumesRequestCondition(String[] consumes, @Nullable String[] headers) {
		this.expressions = new ArrayList<>(parseExpressions(consumes, headers));
		Collections.sort(this.expressions);
	}

	/**
	 * Private constructor for internal when creating matching conditions.
	 * Note the expressions List is neither sorted nor deep copied.
	 */
	/**
	 * 创建匹配条件时用于内部的私有构造函数。 
	 * 请注意，表达式"列表"既未排序也未深度复制。 
	 * 
	 */
	private ConsumesRequestCondition(List<ConsumeMediaTypeExpression> expressions) {
		this.expressions = expressions;
	}


	private static Set<ConsumeMediaTypeExpression> parseExpressions(String[] consumes, @Nullable String[] headers) {
		Set<ConsumeMediaTypeExpression> result = new LinkedHashSet<>();
		if (headers != null) {
			for (String header : headers) {
				HeaderExpression expr = new HeaderExpression(header);
				if ("Content-Type".equalsIgnoreCase(expr.name) && expr.value != null) {
					for (MediaType mediaType : MediaType.parseMediaTypes(expr.value)) {
						result.add(new ConsumeMediaTypeExpression(mediaType, expr.isNegated));
					}
				}
			}
		}
		for (String consume : consumes) {
			result.add(new ConsumeMediaTypeExpression(consume));
		}
		return result;
	}


	/**
	 * Return the contained MediaType expressions.
	 */
	/**
	 * 返回包含的MediaType表达式。 
	 * 
	 */
	public Set<MediaTypeExpression> getExpressions() {
		return new LinkedHashSet<>(this.expressions);
	}

	/**
	 * Returns the media types for this condition excluding negated expressions.
	 */
	/**
	 * 返回此条件的媒体类型，排除了否定表达式。 
	 * 
	 */
	public Set<MediaType> getConsumableMediaTypes() {
		Set<MediaType> result = new LinkedHashSet<>();
		for (ConsumeMediaTypeExpression expression : this.expressions) {
			if (!expression.isNegated()) {
				result.add(expression.getMediaType());
			}
		}
		return result;
	}

	/**
	 * Whether the condition has any media type expressions.
	 */
	/**
	 * 条件是否具有任何媒体类型表达式。 
	 * 
	 */
	@Override
	public boolean isEmpty() {
		return this.expressions.isEmpty();
	}

	@Override
	protected Collection<ConsumeMediaTypeExpression> getContent() {
		return this.expressions;
	}

	@Override
	protected String getToStringInfix() {
		return " || ";
	}

	/**
	 * Whether this condition should expect requests to have a body.
	 * <p>By default this is set to {@code true} in which case it is assumed a
	 * request body is required and this condition matches to the "Content-Type"
	 * header or falls back on "Content-Type: application/octet-stream".
	 * <p>If set to {@code false}, and the request does not have a body, then this
	 * condition matches automatically, i.e. without checking expressions.
	 * @param bodyRequired whether requests are expected to have a body
	 * @since 5.2
	 */
	/**
	 * 此条件是否应期望有一个身体。 
	 *  <p>默认情况下，此值设置为{@code  true}，在这种情况下，假定请求正文是必需的，并且此条件与"Content-Type"标头匹配，或者退回到"Content-Type：application /八位位组流"。 
	 *  <p>如果设置为{@code  false}，并且请求没有正文，则此条件自动匹配，即不检查表达式。 
	 *  
	 * @param  body必需的，要求是否要求请求都自5.2起
	 */
	public void setBodyRequired(boolean bodyRequired) {
		this.bodyRequired = bodyRequired;
	}

	/**
	 * Return the setting for {@link #setBodyRequired(boolean)}.
	 * @since 5.2
	 */
	/**
	 * 返回{@link  #setBodyRequired（boolean）}的设置。 
	 *  @5.2起
	 */
	public boolean isBodyRequired() {
		return this.bodyRequired;
	}


	/**
	 * Returns the "other" instance if it has any expressions; returns "this"
	 * instance otherwise. Practically that means a method-level "consumes"
	 * overrides a type-level "consumes" condition.
	 */
	/**
	 * 如果有任何表达式，则返回"其他"实例； 
	 * 否则返回"this"实例。 
	 * 实际上，这意味着方法级别的"消耗"将覆盖类型级别的"消耗"条件。 
	 * 
	 */
	@Override
	public ConsumesRequestCondition combine(ConsumesRequestCondition other) {
		return (!other.expressions.isEmpty() ? other : this);
	}

	/**
	 * Checks if any of the contained media type expressions match the given
	 * request 'Content-Type' header and returns an instance that is guaranteed
	 * to contain matching expressions only. The match is performed via
	 * {@link MediaType#includes(MediaType)}.
	 * @param request the current request
	 * @return the same instance if the condition contains no expressions;
	 * or a new condition with matching expressions only;
	 * or {@code null} if no expressions match
	 */
	/**
	 * 检查所包含的任何媒体类型表达式是否与给定的请求"Content-Type"标头匹配，并返回保证仅包含匹配表达式的实例。 
	 * 匹配通过{@link  MediaType＃includes（MediaType）}执行。 
	 * 如果条件不包含任何表达式，则
	 * @param 请求当前请求
	 * @return 相同的实例； 
	 * 或仅具有匹配表达式的新条件； 
	 * 或{@code  null}（如果没有匹配的表达式）
	 */
	@Override
	@Nullable
	public ConsumesRequestCondition getMatchingCondition(HttpServletRequest request) {
		if (CorsUtils.isPreFlightRequest(request)) {
			return EMPTY_CONDITION;
		}
		if (isEmpty()) {
			return this;
		}
		if (!hasBody(request) && !this.bodyRequired) {
			return EMPTY_CONDITION;
		}

		// Common media types are cached at the level of MimeTypeUtils

		MediaType contentType;
		try {
			contentType = StringUtils.hasLength(request.getContentType()) ?
					MediaType.parseMediaType(request.getContentType()) :
					MediaType.APPLICATION_OCTET_STREAM;
		}
		catch (InvalidMediaTypeException ex) {
			return null;
		}

		List<ConsumeMediaTypeExpression> result = getMatchingExpressions(contentType);
		return !CollectionUtils.isEmpty(result) ? new ConsumesRequestCondition(result) : null;
	}

	private boolean hasBody(HttpServletRequest request) {
		String contentLength = request.getHeader(HttpHeaders.CONTENT_LENGTH);
		String transferEncoding = request.getHeader(HttpHeaders.TRANSFER_ENCODING);
		return StringUtils.hasText(transferEncoding) ||
				(StringUtils.hasText(contentLength) && !contentLength.trim().equals("0"));
	}

	@Nullable
	private List<ConsumeMediaTypeExpression> getMatchingExpressions(MediaType contentType) {
		List<ConsumeMediaTypeExpression> result = null;
		for (ConsumeMediaTypeExpression expression : this.expressions) {
			if (expression.match(contentType)) {
				result = result != null ? result : new ArrayList<>();
				result.add(expression);
			}
		}
		return result;
	}

	/**
	 * Returns:
	 * <ul>
	 * <li>0 if the two conditions have the same number of expressions
	 * <li>Less than 0 if "this" has more or more specific media type expressions
	 * <li>Greater than 0 if "other" has more or more specific media type expressions
	 * </ul>
	 * <p>It is assumed that both instances have been obtained via
	 * {@link #getMatchingCondition(HttpServletRequest)} and each instance contains
	 * the matching consumable media type expression only or is otherwise empty.
	 */
	/**
	 * 返回：<ul> <li> 0，如果两个条件具有相同数量的表达式<li>如果"this"具有更多或更多特定的媒体类型表达式，则小于0如果<other>具有更多或更多的媒体类型表达式，则<li>大于0更具体的媒体类型表达式</ ul> <p>假定两个实例都是通过{@link  #getMatchingCondition（HttpServletRequest）}获取的，并且每个实例仅包含匹配的可消耗媒体类型表达式，否则为空。 
	 * 
	 */
	@Override
	public int compareTo(ConsumesRequestCondition other, HttpServletRequest request) {
		if (this.expressions.isEmpty() && other.expressions.isEmpty()) {
			return 0;
		}
		else if (this.expressions.isEmpty()) {
			return 1;
		}
		else if (other.expressions.isEmpty()) {
			return -1;
		}
		else {
			return this.expressions.get(0).compareTo(other.expressions.get(0));
		}
	}


	/**
	 * Parses and matches a single media type expression to a request's 'Content-Type' header.
	 */
	/**
	 * 解析单个媒体类型表达式并将其与请求的"Content-Type"标头匹配。 
	 * 
	 */
	static class ConsumeMediaTypeExpression extends AbstractMediaTypeExpression {

		ConsumeMediaTypeExpression(String expression) {
			super(expression);
		}

		ConsumeMediaTypeExpression(MediaType mediaType, boolean negated) {
			super(mediaType, negated);
		}

		public final boolean match(MediaType contentType) {
			boolean match = getMediaType().includes(contentType);
			return !isNegated() == match;
		}
	}

}
