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

package org.springframework.jdbc.core.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;

/**
 * Abstract implementation of the {@link InterruptibleBatchPreparedStatementSetter}
 * interface, combining the check for available values and setting of those
 * into a single callback method {@link #setValuesIfAvailable}.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see #setValuesIfAvailable
 */
/**
 * {@link  InterruptibleBatchPreparedStatementSetter}接口的抽象实现，将对可用值的检查和对这些值的设置组合到单个回调方法{@link  #setValuesIfAvailable}中。 
 *  @author  Juergen Hoeller @自2.0起
 * @see  #setValuesIfAvailable
 */
public abstract class AbstractInterruptibleBatchPreparedStatementSetter
		implements InterruptibleBatchPreparedStatementSetter {

	private boolean exhausted;


	/**
	 * This implementation calls {@link #setValuesIfAvailable}
	 * and sets this instance's exhaustion flag accordingly.
	 */
	/**
	 * 此实现调用{@link  #setValuesIfAvailable}并相应地设置此实例的耗尽标志。 
	 * 
	 */
	@Override
	public final void setValues(PreparedStatement ps, int i) throws SQLException {
		this.exhausted = !setValuesIfAvailable(ps, i);
	}

	/**
	 * This implementation return this instance's current exhaustion flag.
	 */
	/**
	 * 此实现返回此实例的当前耗尽标志。 
	 * 
	 */
	@Override
	public final boolean isBatchExhausted(int i) {
		return this.exhausted;
	}

	/**
	 * This implementation returns {@code Integer.MAX_VALUE}.
	 * Can be overridden in subclasses to lower the maximum batch size.
	 */
	/**
	 * 此实现返回{@code  Integer.MAX_VALUE}。 
	 * 可以在子类中重写以减小最大批处理大小。 
	 * 
	 */
	@Override
	public int getBatchSize() {
		return Integer.MAX_VALUE;
	}


	/**
	 * Check for available values and set them on the given PreparedStatement.
	 * If no values are available anymore, return {@code false}.
	 * @param ps the PreparedStatement we'll invoke setter methods on
	 * @param i index of the statement we're issuing in the batch, starting from 0
	 * @return whether there were values to apply (that is, whether the applied
	 * parameters should be added to the batch and this method should be called
	 * for a further iteration)
	 * @throws SQLException if an SQLException is encountered
	 * (i.e. there is no need to catch SQLException)
	 */
	/**
	 * 检查可用值，然后在给定的PreparedStatement上设置它们。 
	 * 如果没有可用的值，则返回{@code  false}。 
	 *  
	 * @param  ps PreparedStatement，我们将在
	 * @param 上我要在批处理中发出的语句的索引上调用setter方法，从0开始。 
	 * 
	 * @return 是否存在要应用的值（即是否存在应将应用的参数添加到批处理中，并且应调用此方法以进行进一步的迭代）
	 * @throws 如果遇到SQLException（即，无需捕获SQLException），则抛出SQLException
	 */
	protected abstract boolean setValuesIfAvailable(PreparedStatement ps, int i) throws SQLException;

}
