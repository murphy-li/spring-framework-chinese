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

import java.util.StringJoiner;

import org.springframework.asm.MethodVisitor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.lang.Nullable;

/**
 * Represents a DOT separated expression sequence, such as
 * {@code 'property1.property2.methodOne()'}.
 *
 * @author Andy Clement
 * @since 3.0
 */
/**
 * 表示DOT分隔的表达序列，例如{@code 'property1.property2.methodOne（）'}。 
 *  @author 安迪·克莱门特@始于3.0
 */
public class CompoundExpression extends SpelNodeImpl {

	public CompoundExpression(int startPos, int endPos, SpelNodeImpl... expressionComponents) {
		super(startPos, endPos, expressionComponents);
		if (expressionComponents.length < 2) {
			throw new IllegalStateException("Do not build compound expressions with less than two entries: " +
					expressionComponents.length);
		}
	}


	@Override
	protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
		if (getChildCount() == 1) {
			return this.children[0].getValueRef(state);
		}

		SpelNodeImpl nextNode = this.children[0];
		try {
			TypedValue result = nextNode.getValueInternal(state);
			int cc = getChildCount();
			for (int i = 1; i < cc - 1; i++) {
				try {
					state.pushActiveContextObject(result);
					nextNode = this.children[i];
					result = nextNode.getValueInternal(state);
				}
				finally {
					state.popActiveContextObject();
				}
			}
			try {
				state.pushActiveContextObject(result);
				nextNode = this.children[cc - 1];
				return nextNode.getValueRef(state);
			}
			finally {
				state.popActiveContextObject();
			}
		}
		catch (SpelEvaluationException ex) {
			// Correct the position for the error before re-throwing
			ex.setPosition(nextNode.getStartPosition());
			throw ex;
		}
	}

	/**
	 * Evaluates a compound expression. This involves evaluating each piece in turn and the
	 * return value from each piece is the active context object for the subsequent piece.
	 * @param state the state in which the expression is being evaluated
	 * @return the final value from the last piece of the compound expression
	 */
	/**
	 * 计算复合表达式。 
	 * 这涉及依次评估每个片段，每个片段的返回值是后续片段的活动上下文对象。 
	 *  
	 * @param 声明正在评估表达式的状态
	 * @return 来自复合表达式最后一部分的最终值
	 */
	@Override
	public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
		ValueRef ref = getValueRef(state);
		TypedValue result = ref.getValue();
		this.exitTypeDescriptor = this.children[this.children.length - 1].exitTypeDescriptor;
		return result;
	}

	@Override
	public void setValue(ExpressionState state, @Nullable Object value) throws EvaluationException {
		getValueRef(state).setValue(value);
	}

	@Override
	public boolean isWritable(ExpressionState state) throws EvaluationException {
		return getValueRef(state).isWritable();
	}

	@Override
	public String toStringAST() {
		StringJoiner sj = new StringJoiner(".");
		for (int i = 0; i < getChildCount(); i++) {
			sj.add(getChild(i).toStringAST());
		}
		return sj.toString();
	}

	@Override
	public boolean isCompilable() {
		for (SpelNodeImpl child: this.children) {
			if (!child.isCompilable()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void generateCode(MethodVisitor mv, CodeFlow cf) {
		for (SpelNodeImpl child : this.children) {
			child.generateCode(mv, cf);
		}
		cf.pushDescriptor(this.exitTypeDescriptor);
	}

}
