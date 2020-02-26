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

package org.springframework.transaction.event;

import org.springframework.transaction.support.TransactionSynchronization;

/**
 * The phase at which a transactional event listener applies.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.2
 * @see TransactionalEventListener
 */
/**
 * 事务性事件侦听器适用的阶段。 
 *  @author  Stephane Nicoll @author 于尔根·霍勒（Juergen Hoeller）@从4.2起
 * @see  TransactionalEventListener
 */
public enum TransactionPhase {

	/**
	 * Fire the event before transaction commit.
	 * @see TransactionSynchronization#beforeCommit(boolean)
	 */
	/**
	 * 在事务提交之前触发事件。 
	 *  
	 * @see  TransactionSynchronization＃beforeCommit（boolean）
	 */
	BEFORE_COMMIT,

	/**
	 * Fire the event after the commit has completed successfully.
	 * <p>Note: This is a specialization of {@link #AFTER_COMPLETION} and
	 * therefore executes in the same after-completion sequence of events,
	 * (and not in {@link TransactionSynchronization#afterCommit()}).
	 * @see TransactionSynchronization#afterCompletion(int)
	 * @see TransactionSynchronization#STATUS_COMMITTED
	 */
	/**
	 * 提交成功完成后，触发事件。 
	 *  <p>注意：这是{@link  #AFTER_COMPLETION}的特化，因此以相同的完成后事件序列执行（而不是在{@link  TransactionSynchronization＃afterCommit（）}中执行）。 
	 *  
	 * @see  TransactionSynchronization＃afterCompletion（int）
	 * @see  TransactionSynchronization＃STATUS_COMMITTED
	 */
	AFTER_COMMIT,

	/**
	 * Fire the event if the transaction has rolled back.
	 * <p>Note: This is a specialization of {@link #AFTER_COMPLETION} and
	 * therefore executes in the same after-completion sequence of events.
	 * @see TransactionSynchronization#afterCompletion(int)
	 * @see TransactionSynchronization#STATUS_ROLLED_BACK
	 */
	/**
	 * 如果事务已回滚，则触发事件。 
	 *  <p>注意：这是{@link  #AFTER_COMPLETION}的特化，因此以相同的完成后事件顺序执行。 
	 *  
	 * @see  TransactionSynchronization＃afterCompletion（int）
	 * @see  TransactionSynchronization＃STATUS_ROLLED_BACK
	 */
	AFTER_ROLLBACK,

	/**
	 * Fire the event after the transaction has completed.
	 * <p>For more fine-grained events, use {@link #AFTER_COMMIT} or
	 * {@link #AFTER_ROLLBACK} to intercept transaction commit
	 * or rollback, respectively.
	 * @see TransactionSynchronization#afterCompletion(int)
	 */
	/**
	 * 交易完成后触发事件。 
	 *  <p>有关更细粒度的事件，请分别使用{@link  #AFTER_COMMIT}或{@link  #AFTER_ROLLBACK}拦截事务提交或回滚。 
	 *  
	 * @see  TransactionSynchronization＃afterCompletion（int）
	 */
	AFTER_COMPLETION

}
