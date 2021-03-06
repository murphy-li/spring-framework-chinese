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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Represent a map in an expression, e.g. '{name:'foo',age:12}'
 *
 * @author Andy Clement
 * @since 4.1
 */
/**
 * 在表达式中表示地图，例如'{name：'foo'，age：12}'@author  Andy Clement @自4.1起
 */
public class InlineMap extends SpelNodeImpl {

	// If the map is purely literals, it is a constant value and can be computed and cached
	@Nullable
	private TypedValue constant;


	public InlineMap(int startPos, int endPos, SpelNodeImpl... args) {
		super(startPos, endPos, args);
		checkIfConstant();
	}


	/**
	 * If all the components of the map are constants, or lists/maps that themselves
	 * contain constants, then a constant list can be built to represent this node.
	 * This will speed up later getValue calls and reduce the amount of garbage created.
	 */
	/**
	 * 如果映射的所有组件都是常量，或者列表/映射本身包含常量，则可以构建一个常量列表来表示此节点。 
	 * 这将加速以后的getValue调用，并减少创建的垃圾量。 
	 * 
	 */
	private void checkIfConstant() {
		boolean isConstant = true;
		for (int c = 0, max = getChildCount(); c < max; c++) {
			SpelNode child = getChild(c);
			if (!(child instanceof Literal)) {
				if (child instanceof InlineList) {
					InlineList inlineList = (InlineList) child;
					if (!inlineList.isConstant()) {
						isConstant = false;
						break;
					}
				}
				else if (child instanceof InlineMap) {
					InlineMap inlineMap = (InlineMap) child;
					if (!inlineMap.isConstant()) {
						isConstant = false;
						break;
					}
				}
				else if (!(c % 2 == 0 && child instanceof PropertyOrFieldReference)) {
					isConstant = false;
					break;
				}
			}
		}
		if (isConstant) {
			Map<Object, Object> constantMap = new LinkedHashMap<>();
			int childCount = getChildCount();
			for (int c = 0; c < childCount; c++) {
				SpelNode keyChild = getChild(c++);
				SpelNode valueChild = getChild(c);
				Object key = null;
				Object value = null;
				if (keyChild instanceof Literal) {
					key = ((Literal) keyChild).getLiteralValue().getValue();
				}
				else if (keyChild instanceof PropertyOrFieldReference) {
					key = ((PropertyOrFieldReference) keyChild).getName();
				}
				else {
					return;
				}
				if (valueChild instanceof Literal) {
					value = ((Literal) valueChild).getLiteralValue().getValue();
				}
				else if (valueChild instanceof InlineList) {
					value = ((InlineList) valueChild).getConstantValue();
				}
				else if (valueChild instanceof InlineMap) {
					value = ((InlineMap) valueChild).getConstantValue();
				}
				constantMap.put(key, value);
			}
			this.constant = new TypedValue(Collections.unmodifiableMap(constantMap));
		}
	}

	@Override
	public TypedValue getValueInternal(ExpressionState expressionState) throws EvaluationException {
		if (this.constant != null) {
			return this.constant;
		}
		else {
			Map<Object, Object> returnValue = new LinkedHashMap<>();
			int childcount = getChildCount();
			for (int c = 0; c < childcount; c++) {
				// TODO allow for key being PropertyOrFieldReference like Indexer on maps
				SpelNode keyChild = getChild(c++);
				Object key = null;
				if (keyChild instanceof PropertyOrFieldReference) {
					PropertyOrFieldReference reference = (PropertyOrFieldReference) keyChild;
					key = reference.getName();
				}
				else {
					key = keyChild.getValue(expressionState);
				}
				Object value = getChild(c).getValue(expressionState);
				returnValue.put(key,  value);
			}
			return new TypedValue(returnValue);
		}
	}

	@Override
	public String toStringAST() {
		StringBuilder sb = new StringBuilder("{");
		int count = getChildCount();
		for (int c = 0; c < count; c++) {
			if (c > 0) {
				sb.append(",");
			}
			sb.append(getChild(c++).toStringAST());
			sb.append(":");
			sb.append(getChild(c).toStringAST());
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Return whether this list is a constant value.
	 */
	/**
	 * 返回此列表是否为常数。 
	 * 
	 */
	public boolean isConstant() {
		return this.constant != null;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public Map<Object, Object> getConstantValue() {
		Assert.state(this.constant != null, "No constant");
		return (Map<Object, Object>) this.constant.getValue();
	}

}
