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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.asm.MethodVisitor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Operation;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.CodeFlow;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

/**
 * The minus operator supports:
 * <ul>
 * <li>subtraction of numbers
 * <li>subtraction of an int from a string of one character
 * (effectively decreasing that character), so 'd'-3='a'
 * </ul>
 *
 * <p>It can be used as a unary operator for numbers.
 * The standard promotions are performed when the operand types vary (double-int=double).
 * For other options it defers to the registered overloader.
 *
 * @author Andy Clement
 * @author Juergen Hoeller
 * @author Giovanni Dall'Oglio Risso
 * @since 3.0
 */
/**
 * 减号运算符支持：<ul> <li>减去数字<li>从一个字符的字符串中减去一个int（有效地减少该字符），因此'd'-3 ='a'</ ul> <p >可用作数字的一元运算符。 
 * 当操作数类型不同（double-int = double）时，将执行标准升级。 
 * 对于其他选项，它取决于已注册的重载程序。 
 *  @author 安迪·克莱门特（Andy Clement）@author 于尔根·霍勒（Juergen Hoeller）@author 乔瓦尼·达洛（Giovanni Dall'Oglio Risso）@3.0起
 */
public class OpMinus extends Operator {

	public OpMinus(int startPos, int endPos, SpelNodeImpl... operands) {
		super("-", startPos, endPos, operands);
	}


	@Override
	public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
		SpelNodeImpl leftOp = getLeftOperand();

		if (this.children.length < 2) {  // if only one operand, then this is unary minus
			Object operand = leftOp.getValueInternal(state).getValue();
			if (operand instanceof Number) {
				if (operand instanceof BigDecimal) {
					return new TypedValue(((BigDecimal) operand).negate());
				}
				else if (operand instanceof Double) {
					this.exitTypeDescriptor = "D";
					return new TypedValue(0 - ((Number) operand).doubleValue());
				}
				else if (operand instanceof Float) {
					this.exitTypeDescriptor = "F";
					return new TypedValue(0 - ((Number) operand).floatValue());
				}
				else if (operand instanceof BigInteger) {
					return new TypedValue(((BigInteger) operand).negate());
				}
				else if (operand instanceof Long) {
					this.exitTypeDescriptor = "J";
					return new TypedValue(0 - ((Number) operand).longValue());
				}
				else if (operand instanceof Integer) {
					this.exitTypeDescriptor = "I";
					return new TypedValue(0 - ((Number) operand).intValue());
				}
				else if (operand instanceof Short) {
					return new TypedValue(0 - ((Number) operand).shortValue());
				}
				else if (operand instanceof Byte) {
					return new TypedValue(0 - ((Number) operand).byteValue());
				}
				else {
					// Unknown Number subtypes -> best guess is double subtraction
					return new TypedValue(0 - ((Number) operand).doubleValue());
				}
			}
			return state.operate(Operation.SUBTRACT, operand, null);
		}

		Object left = leftOp.getValueInternal(state).getValue();
		Object right = getRightOperand().getValueInternal(state).getValue();

		if (left instanceof Number && right instanceof Number) {
			Number leftNumber = (Number) left;
			Number rightNumber = (Number) right;

			if (leftNumber instanceof BigDecimal || rightNumber instanceof BigDecimal) {
				BigDecimal leftBigDecimal = NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
				BigDecimal rightBigDecimal = NumberUtils.convertNumberToTargetClass(rightNumber, BigDecimal.class);
				return new TypedValue(leftBigDecimal.subtract(rightBigDecimal));
			}
			else if (leftNumber instanceof Double || rightNumber instanceof Double) {
				this.exitTypeDescriptor = "D";
				return new TypedValue(leftNumber.doubleValue() - rightNumber.doubleValue());
			}
			else if (leftNumber instanceof Float || rightNumber instanceof Float) {
				this.exitTypeDescriptor = "F";
				return new TypedValue(leftNumber.floatValue() - rightNumber.floatValue());
			}
			else if (leftNumber instanceof BigInteger || rightNumber instanceof BigInteger) {
				BigInteger leftBigInteger = NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
				BigInteger rightBigInteger = NumberUtils.convertNumberToTargetClass(rightNumber, BigInteger.class);
				return new TypedValue(leftBigInteger.subtract(rightBigInteger));
			}
			else if (leftNumber instanceof Long || rightNumber instanceof Long) {
				this.exitTypeDescriptor = "J";
				return new TypedValue(leftNumber.longValue() - rightNumber.longValue());
			}
			else if (CodeFlow.isIntegerForNumericOp(leftNumber) || CodeFlow.isIntegerForNumericOp(rightNumber)) {
				this.exitTypeDescriptor = "I";
				return new TypedValue(leftNumber.intValue() - rightNumber.intValue());
			}
			else {
				// Unknown Number subtypes -> best guess is double subtraction
				return new TypedValue(leftNumber.doubleValue() - rightNumber.doubleValue());
			}
		}

		if (left instanceof String && right instanceof Integer && ((String) left).length() == 1) {
			String theString = (String) left;
			Integer theInteger = (Integer) right;
			// Implements character - int (ie. b - 1 = a)
			return new TypedValue(Character.toString((char) (theString.charAt(0) - theInteger)));
		}

		return state.operate(Operation.SUBTRACT, left, right);
	}

	@Override
	public String toStringAST() {
		if (this.children.length < 2) {  // unary minus
			return "-" + getLeftOperand().toStringAST();
		}
		return super.toStringAST();
	}

	@Override
	public SpelNodeImpl getRightOperand() {
		if (this.children.length < 2) {
			throw new IllegalStateException("No right operand");
		}
		return this.children[1];
	}

	@Override
	public boolean isCompilable() {
		if (!getLeftOperand().isCompilable()) {
			return false;
		}
		if (this.children.length > 1) {
			if (!getRightOperand().isCompilable()) {
				return false;
			}
		}
		return (this.exitTypeDescriptor != null);
	}

	@Override
	public void generateCode(MethodVisitor mv, CodeFlow cf) {
		getLeftOperand().generateCode(mv, cf);
		String leftDesc = getLeftOperand().exitTypeDescriptor;
		String exitDesc = this.exitTypeDescriptor;
		Assert.state(exitDesc != null, "No exit type descriptor");
		char targetDesc = exitDesc.charAt(0);
		CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, leftDesc, targetDesc);
		if (this.children.length > 1) {
			cf.enterCompilationScope();
			getRightOperand().generateCode(mv, cf);
			String rightDesc = getRightOperand().exitTypeDescriptor;
			cf.exitCompilationScope();
			CodeFlow.insertNumericUnboxOrPrimitiveTypeCoercion(mv, rightDesc, targetDesc);
			switch (targetDesc) {
				case 'I':
					mv.visitInsn(ISUB);
					break;
				case 'J':
					mv.visitInsn(LSUB);
					break;
				case 'F':
					mv.visitInsn(FSUB);
					break;
				case 'D':
					mv.visitInsn(DSUB);
					break;
				default:
					throw new IllegalStateException(
							"Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
			}
		}
		else {
			switch (targetDesc) {
				case 'I':
					mv.visitInsn(INEG);
					break;
				case 'J':
					mv.visitInsn(LNEG);
					break;
				case 'F':
					mv.visitInsn(FNEG);
					break;
				case 'D':
					mv.visitInsn(DNEG);
					break;
				default:
					throw new IllegalStateException(
							"Unrecognized exit type descriptor: '" + this.exitTypeDescriptor + "'");
			}
		}
		cf.pushDescriptor(this.exitTypeDescriptor);
	}

}
