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

package org.springframework.test.context.junit.jupiter;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * {@code DisabledIfCondition} is an {@link org.junit.jupiter.api.extension.ExecutionCondition}
 * that supports the {@link DisabledIf @DisabledIf} annotation when using the <em>Spring
 * TestContext Framework</em> in conjunction with JUnit 5's <em>Jupiter</em> programming model.
 *
 * <p>Any attempt to use the {@code DisabledIfCondition} without the presence of
 * {@link DisabledIf @DisabledIf} will result in an <em>enabled</em>
 * {@link ConditionEvaluationResult}.
 *
 * @author Sam Brannen
 * @since 5.0
 * @see DisabledIf
 * @see EnabledIf
 * @see SpringExtension
 */
/**
 * {@code  DisabledIfCondition}是{@link  org.junit.jupiter.api.extension.ExecutionCondition}，它在使用<em> Spring TestContext Framework时支持{@link  DisabledIf @DisabledIf}注释</ em>与JUnit 5的<em> Jupiter </ em>编程模型结合使用。 
 *  <p>任何在没有{@link  DisabledIf @DisabledIf}的情况下使用{@code  DisabledIfCondition}的尝试都将导致<em> enabled </ em> {@link  ConditionEvaluationResult}。 
 *  @author  Sam Brannen @从5.0开始
 * @see 禁用如果
 * @see 启用如果
 * @see  SpringExtension
 */
public class DisabledIfCondition extends AbstractExpressionEvaluatingCondition {

	/**
	 * Containers and tests are disabled if {@code @DisabledIf} is present on the
	 * corresponding test class or test method and the configured expression evaluates
	 * to {@code true}.
	 */
	/**
	 * 如果在相应的测试类或测试方法上存在{@code  @DisabledIf}，并且配置的表达式的值为{@code  true}，则禁用容器和测试。 
	 * 
	 */
	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		return evaluateAnnotation(DisabledIf.class, DisabledIf::expression, DisabledIf::reason,
				DisabledIf::loadContext, false, context);
	}

}
