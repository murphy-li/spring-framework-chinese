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

package org.springframework.transaction.interceptor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * PropertyEditor for {@link TransactionAttribute} objects. Accepts a String of form
 * <p>{@code PROPAGATION_NAME, ISOLATION_NAME, readOnly, timeout_NNNN,+Exception1,-Exception2}
 * <p>where only propagation code is required. For example:
 * <p>{@code PROPAGATION_MANDATORY, ISOLATION_DEFAULT}
 *
 * <p>The tokens can be in <strong>any</strong> order. Propagation and isolation codes
 * must use the names of the constants in the TransactionDefinition class. Timeout values
 * are in seconds. If no timeout is specified, the transaction manager will apply a default
 * timeout specific to the particular transaction manager.
 *
 * <p>A "+" before an exception name substring indicates that transactions should commit
 * even if this exception is thrown; a "-" that they should roll back.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 24.04.2003
 * @see org.springframework.transaction.TransactionDefinition
 * @see org.springframework.core.Constants
 */
/**
 * {@link  TransactionAttribute}对象的PropertyEditor。 
 * 接受格式为<p> {<@code> PROPAGATION_NAME，ISOLATION_NAME，readOnly，timeout_NNNN，+ Exception1，-Exception2} <p>的字符串，其中仅需要传播代码。 
 * 例如：<p> {<@code> PROPAGATION_MANDATORY，ISOLATION_DEFAULT} <p>令牌可以按<strong> any </ strong>顺序排列。 
 * 传播和隔离代码必须使用TransactionDefinition类中的常量名称。 
 * 超时值以秒为单位。 
 * 如果未指定超时，则事务管理器将应用特定于特定事务管理器的默认超时。 
 *  <p>在异常名称子字符串之前的"+"表示即使抛出此异常，事务也应提交； 
 * 他们应该回滚的"-"。 
 *  @author  Rod Johnson @author  Juergen Hoeller @2003年4月24日起
 * @see  org.springframework.transaction.TransactionDefinition 
 * @see  org.springframework.core.Constants
 */
public class TransactionAttributeEditor extends PropertyEditorSupport {

	/**
	 * Format is PROPAGATION_NAME,ISOLATION_NAME,readOnly,timeout_NNNN,+Exception1,-Exception2.
	 * Null or the empty string means that the method is non transactional.
	 */
	/**
	 * 格式为PROPAGATION_NAME，ISOLATION_NAME，只读，超时_NNNN，+ Exception1，-Exception2。 
	 *  Null或空字符串表示该方法是非事务性的。 
	 * 
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasLength(text)) {
			// tokenize it with ","
			String[] tokens = StringUtils.commaDelimitedListToStringArray(text);
			RuleBasedTransactionAttribute attr = new RuleBasedTransactionAttribute();
			for (String token : tokens) {
				// Trim leading and trailing whitespace.
				String trimmedToken = StringUtils.trimWhitespace(token.trim());
				// Check whether token contains illegal whitespace within text.
				if (StringUtils.containsWhitespace(trimmedToken)) {
					throw new IllegalArgumentException(
							"Transaction attribute token contains illegal whitespace: [" + trimmedToken + "]");
				}
				// Check token type.
				if (trimmedToken.startsWith(RuleBasedTransactionAttribute.PREFIX_PROPAGATION)) {
					attr.setPropagationBehaviorName(trimmedToken);
				}
				else if (trimmedToken.startsWith(RuleBasedTransactionAttribute.PREFIX_ISOLATION)) {
					attr.setIsolationLevelName(trimmedToken);
				}
				else if (trimmedToken.startsWith(RuleBasedTransactionAttribute.PREFIX_TIMEOUT)) {
					String value = trimmedToken.substring(DefaultTransactionAttribute.PREFIX_TIMEOUT.length());
					attr.setTimeout(Integer.parseInt(value));
				}
				else if (trimmedToken.equals(RuleBasedTransactionAttribute.READ_ONLY_MARKER)) {
					attr.setReadOnly(true);
				}
				else if (trimmedToken.startsWith(RuleBasedTransactionAttribute.PREFIX_COMMIT_RULE)) {
					attr.getRollbackRules().add(new NoRollbackRuleAttribute(trimmedToken.substring(1)));
				}
				else if (trimmedToken.startsWith(RuleBasedTransactionAttribute.PREFIX_ROLLBACK_RULE)) {
					attr.getRollbackRules().add(new RollbackRuleAttribute(trimmedToken.substring(1)));
				}
				else {
					throw new IllegalArgumentException("Invalid transaction attribute token: [" + trimmedToken + "]");
				}
			}
			setValue(attr);
		}
		else {
			setValue(null);
		}
	}

}
