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

package org.springframework.jms.connection;

import java.util.ArrayList;
import java.util.List;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.util.Assert;

/**
 * Implementation of the JMS ExceptionListener interface that supports chaining,
 * allowing the addition of multiple ExceptionListener instances in order.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 支持链接的JMS ExceptionListener接口的实现，允许按顺序添加多个ExceptionListener实例。 
 *  @author  Juergen Hoeller @始于2.0
 */
public class ChainedExceptionListener implements ExceptionListener {

	/** List of ExceptionListeners. */
	/**
	 * ExceptionListener的列表。 
	 * 
	 */
	private final List<ExceptionListener> delegates = new ArrayList<>(2);


	/**
	 * Add an ExceptionListener to the chained delegate list.
	 */
	/**
	 * 将ExceptionListener添加到链接的委托列表。 
	 * 
	 */
	public final void addDelegate(ExceptionListener listener) {
		Assert.notNull(listener, "ExceptionListener must not be null");
		this.delegates.add(listener);
	}

	/**
	 * Return all registered ExceptionListener delegates (as array).
	 */
	/**
	 * 返回所有已注册的ExceptionListener委托（作为数组）。 
	 * 
	 */
	public final ExceptionListener[] getDelegates() {
		return this.delegates.toArray(new ExceptionListener[0]);
	}


	@Override
	public void onException(JMSException ex) {
		for (ExceptionListener listener : this.delegates) {
			listener.onException(ex);
		}
	}

}
