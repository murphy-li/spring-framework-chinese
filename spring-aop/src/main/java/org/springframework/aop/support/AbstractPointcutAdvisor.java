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

package org.springframework.aop.support;

import java.io.Serializable;

import org.aopalliance.aop.Advice;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * Abstract base class for {@link org.springframework.aop.PointcutAdvisor}
 * implementations. Can be subclassed for returning a specific pointcut/advice
 * or a freely configurable pointcut/advice.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 1.1.2
 * @see AbstractGenericPointcutAdvisor
 */
/**
 * {@link  org.springframework.aop.PointcutAdvisor}实现的抽象基类。 
 * 可以归类为返回特定切入点/建议或可自由配置的切入点/建议。 
 *  @author  Rod Johnson @author  Juergen Hoeller @1.1.2起
 * @see  AbstractGenericPointcutAdvisor
 */
@SuppressWarnings("serial")
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor, Ordered, Serializable {

	@Nullable
	private Integer order;


	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		if (this.order != null) {
			return this.order;
		}
		Advice advice = getAdvice();
		if (advice instanceof Ordered) {
			return ((Ordered) advice).getOrder();
		}
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PointcutAdvisor)) {
			return false;
		}
		PointcutAdvisor otherAdvisor = (PointcutAdvisor) other;
		return (ObjectUtils.nullSafeEquals(getAdvice(), otherAdvisor.getAdvice()) &&
				ObjectUtils.nullSafeEquals(getPointcut(), otherAdvisor.getPointcut()));
	}

	@Override
	public int hashCode() {
		return PointcutAdvisor.class.hashCode();
	}

}
