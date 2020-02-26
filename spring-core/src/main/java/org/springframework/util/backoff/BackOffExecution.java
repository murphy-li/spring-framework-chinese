/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.util.backoff;

/**
 * Represent a particular back-off execution.
 *
 * <p>Implementations do not need to be thread safe.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @see BackOff
 */
/**
 * 表示特定的退避执行。 
 *  <p>实现不必是线程安全的。 
 *  @author 史蒂芬·尼科尔（Stephane Nicoll）@始于4.1 
 * @see  BackOff
 */
@FunctionalInterface
public interface BackOffExecution {

	/**
	 * Return value of {@link #nextBackOff()} that indicates that the operation
	 * should not be retried.
	 */
	/**
	 * 返回值{@link  #nextBackOff（）}指示不应重试该操作。 
	 * 
	 */
	long STOP = -1;

	/**
	 * Return the number of milliseconds to wait before retrying the operation
	 * or {@link #STOP} ({@value #STOP}) to indicate that no further attempt
	 * should be made for the operation.
	 */
	/**
	 * 返回尝试重试操作之前要等待的毫秒数，或者返回{@link  #STOP}（{@value #STOP}）以指示不应对该操作进行任何进一步尝试。 
	 * 
	 */
	long nextBackOff();

}
