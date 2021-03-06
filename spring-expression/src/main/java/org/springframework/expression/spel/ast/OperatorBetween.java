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

package org.springframework.expression.spel.ast;

import java.util.List;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelMessage;
import org.springframework.expression.spel.support.BooleanTypedValue;

/**
 * Represents the between operator. The left operand to between must be a single value and
 * the right operand must be a list - this operator returns true if the left operand is
 * between (using the registered comparator) the two elements in the list. The definition
 * of between being inclusive follows the SQL BETWEEN definition.
 *
 * @author Andy Clement
 * @since 3.0
 */
/**
 * 表示之间的运算符。 
 * 介于之间的左操作数必须是一个值，而右操作数必须是一个列表-如果左操作数在列表中的两个元素之间（使用已注册的比较器），则此运算符返回true。 
 * 包含之间的定义遵循SQL BETWEEN定义。 
 *  @author 安迪·克莱门特@始于3.0
 */
public class OperatorBetween extends Operator {

	public OperatorBetween(int startPos, int endPos, SpelNodeImpl... operands) {
		super("between", startPos, endPos, operands);
	}


	/**
	 * Returns a boolean based on whether a value is in the range expressed. The first
	 * operand is any value whilst the second is a list of two values - those two values
	 * being the bounds allowed for the first operand (inclusive).
	 * @param state the expression state
	 * @return true if the left operand is in the range specified, false otherwise
	 * @throws EvaluationException if there is a problem evaluating the expression
	 */
	/**
	 * 根据值是否在所表示的范围内返回布尔值。 
	 * 第一个操作数是任何值，而第二个操作数是两个值的列表-这两个值是第一个操作数（含）的范围。 
	 *  
	 * @param 声明表达式状态
	 * @return 如果左操作数在指定范围内，则为true，否则为false，如果表达式的评估存在问题，则为@
	 * @throws EvaluationException
	 */
	@Override
	public BooleanTypedValue getValueInternal(ExpressionState state) throws EvaluationException {
		Object left = getLeftOperand().getValueInternal(state).getValue();
		Object right = getRightOperand().getValueInternal(state).getValue();
		if (!(right instanceof List) || ((List<?>) right).size() != 2) {
			throw new SpelEvaluationException(getRightOperand().getStartPosition(),
					SpelMessage.BETWEEN_RIGHT_OPERAND_MUST_BE_TWO_ELEMENT_LIST);
		}

		List<?> list = (List<?>) right;
		Object low = list.get(0);
		Object high = list.get(1);
		TypeComparator comp = state.getTypeComparator();
		try {
			return BooleanTypedValue.forValue(comp.compare(left, low) >= 0 && comp.compare(left, high) <= 0);
		}
		catch (SpelEvaluationException ex) {
			ex.setPosition(getStartPosition());
			throw ex;
		}
	}

}
