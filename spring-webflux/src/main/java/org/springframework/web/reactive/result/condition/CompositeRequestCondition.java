/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.reactive.result.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * Implements the {@link RequestCondition} contract by delegating to multiple
 * {@code RequestCondition} types and using a logical conjunction (' && ') to
 * ensure all conditions match a given request.
 *
 * <p>When {@code CompositeRequestCondition} instances are combined or compared
 * they are expected to (a) contain the same number of conditions and (b) that
 * conditions in the respective index are of the same type. It is acceptable to
 * provide {@code null} conditions or no conditions at all to the constructor.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 通过委派多个{@code  RequestCondition}类型并使用逻辑结合符（'&&'）来确保所有条件与给定请求匹配，从而实现{@link  RequestCondition}合同。 
 *  <p>当{@code  CompositeRequestCondition}实例被组合或比较时，它们预期（a）包含相同数量的条件，并且（b）各个索引中的条件属于相同类型。 
 * 向构造函数提供{@code  null}条件或完全不提供条件是可以接受的。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class CompositeRequestCondition extends AbstractRequestCondition<CompositeRequestCondition> {

	private final RequestConditionHolder[] requestConditions;


	/**
	 * Create an instance with 0 or more {@code RequestCondition} types. It is
	 * important to create {@code CompositeRequestCondition} instances with the
	 * same number of conditions so they may be compared and combined.
	 * It is acceptable to provide {@code null} conditions.
	 */
	/**
	 * 创建具有0个或更多{@code  RequestCondition}类型的实例。 
	 * 重要的是创建具有相同数量条件的{@code  CompositeRequestCondition}实例，以便可以对它们进行比较和组合。 
	 * 提供{@code  null}条件是可以接受的。 
	 * 
	 */
	public CompositeRequestCondition(RequestCondition<?>... requestConditions) {
		this.requestConditions = wrap(requestConditions);
	}

	private CompositeRequestCondition(RequestConditionHolder[] requestConditions) {
		this.requestConditions = requestConditions;
	}


	private RequestConditionHolder[] wrap(RequestCondition<?>... rawConditions) {
		RequestConditionHolder[] wrappedConditions = new RequestConditionHolder[rawConditions.length];
		for (int i = 0; i < rawConditions.length; i++) {
			wrappedConditions[i] = new RequestConditionHolder(rawConditions[i]);
		}
		return wrappedConditions;
	}

	/**
	 * Whether this instance contains 0 conditions or not.
	 */
	/**
	 * 此实例是否包含0个条件。 
	 * 
	 */
	@Override
	public boolean isEmpty() {
		return ObjectUtils.isEmpty(this.requestConditions);
	}

	/**
	 * Return the underlying conditions, possibly empty but never {@code null}.
	 */
	/**
	 * 返回基础条件，可能为空，但永远不会为{@code  null}。 
	 * 
	 */
	public List<RequestCondition<?>> getConditions() {
		return unwrap();
	}

	private List<RequestCondition<?>> unwrap() {
		List<RequestCondition<?>> result = new ArrayList<>();
		for (RequestConditionHolder holder : this.requestConditions) {
			result.add(holder.getCondition());
		}
		return result;
	}

	@Override
	protected Collection<?> getContent() {
		return (!isEmpty() ? getConditions() : Collections.emptyList());
	}

	@Override
	protected String getToStringInfix() {
		return " && ";
	}

	private int getLength() {
		return this.requestConditions.length;
	}

	/**
	 * If one instance is empty, return the other.
	 * If both instances have conditions, combine the individual conditions
	 * after ensuring they are of the same type and number.
	 */
	/**
	 * 如果一个实例为空，则返回另一个。 
	 * 如果两个实例都有条件，请在确保它们具有相同的类型和编号后合并各个条件。 
	 * 
	 */
	@Override
	public CompositeRequestCondition combine(CompositeRequestCondition other) {
		if (isEmpty() && other.isEmpty()) {
			return this;
		}
		else if (other.isEmpty()) {
			return this;
		}
		else if (isEmpty()) {
			return other;
		}
		else {
			assertNumberOfConditions(other);
			RequestConditionHolder[] combinedConditions = new RequestConditionHolder[getLength()];
			for (int i = 0; i < getLength(); i++) {
				combinedConditions[i] = this.requestConditions[i].combine(other.requestConditions[i]);
			}
			return new CompositeRequestCondition(combinedConditions);
		}
	}

	private void assertNumberOfConditions(CompositeRequestCondition other) {
		Assert.isTrue(getLength() == other.getLength(),
				"Cannot combine CompositeRequestConditions with a different number of conditions. " +
				ObjectUtils.nullSafeToString(this.requestConditions) + " and  " +
				ObjectUtils.nullSafeToString(other.requestConditions));
	}

	/**
	 * Delegate to <em>all</em> contained conditions to match the request and return the
	 * resulting "matching" condition instances.
	 * <p>An empty {@code CompositeRequestCondition} matches to all requests.
	 */
	/**
	 * 委托<em> all </ em>包含条件以匹配请求，并返回结果"匹配"条件实例。 
	 *  <p>空的{@code  CompositeRequestCondition}匹配所有请求。 
	 * 
	 */
	@Override
	public CompositeRequestCondition getMatchingCondition(ServerWebExchange exchange) {
		if (isEmpty()) {
			return this;
		}
		RequestConditionHolder[] matchingConditions = new RequestConditionHolder[getLength()];
		for (int i = 0; i < getLength(); i++) {
			matchingConditions[i] = this.requestConditions[i].getMatchingCondition(exchange);
			if (matchingConditions[i] == null) {
				return null;
			}
		}
		return new CompositeRequestCondition(matchingConditions);
	}

	/**
	 * If one instance is empty, the other "wins". If both instances have
	 * conditions, compare them in the order in which they were provided.
	 */
	/**
	 * 如果一个实例为空，则另一个"获胜"。 
	 * 如果两个实例都有条件，请按照提供它们的顺序比较它们。 
	 * 
	 */
	@Override
	public int compareTo(CompositeRequestCondition other, ServerWebExchange exchange) {
		if (isEmpty() && other.isEmpty()) {
			return 0;
		}
		else if (isEmpty()) {
			return 1;
		}
		else if (other.isEmpty()) {
			return -1;
		}
		else {
			assertNumberOfConditions(other);
			for (int i = 0; i < getLength(); i++) {
				int result = this.requestConditions[i].compareTo(other.requestConditions[i], exchange);
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	}

}
