/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.transaction.interceptor;

import org.springframework.aop.SpringProxy;

/**
 * A marker interface for manually created transactional proxies.
 *
 * <p>{@link TransactionAttributeSourcePointcut} will ignore such existing
 * transactional proxies during AOP auto-proxying and therefore avoid
 * re-processing transaction metadata on them.
 *
 * @author Juergen Hoeller
 * @since 4.1.7
 */
/**
 * 手动创建的交易代理的标记界面。 
 *  <p> {<@link> TransactionAttributeSourcePointcut}将在AOP自动代理过程中忽略此类现有的事务代理，因此避免重新处理它们上的事务元数据。 
 *  @author  Juergen Hoeller @始于4.1.7
 */
public interface TransactionalProxy extends SpringProxy {

}
