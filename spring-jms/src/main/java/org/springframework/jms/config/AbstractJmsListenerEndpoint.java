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

package org.springframework.jms.config;

import javax.jms.MessageListener;

import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;
import org.springframework.jms.listener.endpoint.JmsMessageEndpointManager;
import org.springframework.lang.Nullable;

/**
 * Base model for a JMS listener endpoint.
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.1
 * @see MethodJmsListenerEndpoint
 * @see SimpleJmsListenerEndpoint
 */
/**
 * JMS侦听器端点的基本模型。 
 *  @author  Stephane Nicoll @author 于尔根·霍勒（Juergen Hoeller）@从4.1开始
 * @see  MethodJmsListenerEndpoint 
 * @see  SimpleJmsListenerEndpoint
 */
public abstract class AbstractJmsListenerEndpoint implements JmsListenerEndpoint {

	private String id = "";

	@Nullable
	private String destination;

	@Nullable
	private String subscription;

	@Nullable
	private String selector;

	@Nullable
	private String concurrency;


	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Set the name of the destination for this endpoint.
	 */
	/**
	 * 设置此端点的目的地的名称。 
	 * 
	 */
	public void setDestination(@Nullable String destination) {
		this.destination = destination;
	}

	/**
	 * Return the name of the destination for this endpoint.
	 */
	/**
	 * 返回此端点的目标名称。 
	 * 
	 */
	@Nullable
	public String getDestination() {
		return this.destination;
	}

	/**
	 * Set the name for the durable subscription.
	 */
	/**
	 * 设置持久订阅的名称。 
	 * 
	 */
	public void setSubscription(@Nullable String subscription) {
		this.subscription = subscription;
	}

	/**
	 * Return the name for the durable subscription, if any.
	 */
	/**
	 * 返回持久订阅的名称（如果有）。 
	 * 
	 */
	@Nullable
	public String getSubscription() {
		return this.subscription;
	}

	/**
	 * Set the JMS message selector expression.
	 * <p>See the JMS specification for a detailed definition of selector expressions.
	 */
	/**
	 * 设置JMS消息选择器表达式。 
	 *  <p>有关选择器表达式的详细定义，请参见JMS规范。 
	 * 
	 */
	public void setSelector(@Nullable String selector) {
		this.selector = selector;
	}

	/**
	 * Return the JMS message selector expression, if any.
	 */
	/**
	 * 返回JMS消息选择器表达式（如果有）。 
	 * 
	 */
	@Nullable
	public String getSelector() {
		return this.selector;
	}

	/**
	 * Set a concurrency for the listener, if any.
	 * <p>The concurrency limits can be a "lower-upper" String, e.g. "5-10", or a simple
	 * upper limit String, e.g. "10" (the lower limit will be 1 in this case).
	 * <p>The underlying container may or may not support all features. For instance, it
	 * may not be able to scale: in that case only the upper value is used.
	 */
	/**
	 * 设置侦听器的并发（如果有）。 
	 *  <p>并发限制可以是"低-上"字符串，例如"5-10"，或简单的上限字符串，例如"10"（在这种情况下，下限将为1）。 
	 *  <p>基础容器可能支持也可能不支持所有功能。 
	 * 例如，它可能无法缩放：在这种情况下，仅使用较高的值。 
	 * 
	 */
	public void setConcurrency(@Nullable String concurrency) {
		this.concurrency = concurrency;
	}

	/**
	 * Return the concurrency for the listener, if any.
	 */
	/**
	 * 返回侦听器的并发（如果有）。 
	 * 
	 */
	@Nullable
	public String getConcurrency() {
		return this.concurrency;
	}


	@Override
	public void setupListenerContainer(MessageListenerContainer listenerContainer) {
		if (listenerContainer instanceof AbstractMessageListenerContainer) {
			setupJmsListenerContainer((AbstractMessageListenerContainer) listenerContainer);
		}
		else {
			new JcaEndpointConfigurer().configureEndpoint(listenerContainer);
		}
	}

	private void setupJmsListenerContainer(AbstractMessageListenerContainer listenerContainer) {
		if (getDestination() != null) {
			listenerContainer.setDestinationName(getDestination());
		}
		if (getSubscription() != null) {
			listenerContainer.setSubscriptionName(getSubscription());
		}
		if (getSelector() != null) {
			listenerContainer.setMessageSelector(getSelector());
		}
		if (getConcurrency() != null) {
			listenerContainer.setConcurrency(getConcurrency());
		}
		setupMessageListener(listenerContainer);
	}

	/**
	 * Create a {@link MessageListener} that is able to serve this endpoint for the
	 * specified container.
	 */
	/**
	 * 创建一个{@link  MessageListener}，它可以为指定的容器提供此终结点。 
	 * 
	 */
	protected abstract MessageListener createMessageListener(MessageListenerContainer container);

	private void setupMessageListener(MessageListenerContainer container) {
		container.setupMessageListener(createMessageListener(container));
	}

	/**
	 * Return a description for this endpoint.
	 * <p>Available to subclasses, for inclusion in their {@code toString()} result.
	 */
	/**
	 * 返回此端点的描述。 
	 *  <p>子类可用，包含在它们的{@code  toString（）}结果中。 
	 * 
	 */
	protected StringBuilder getEndpointDescription() {
		StringBuilder result = new StringBuilder();
		return result.append(getClass().getSimpleName()).append("[").append(this.id).append("] destination=").
				append(this.destination).append("' | subscription='").append(this.subscription).
				append(" | selector='").append(this.selector).append("'");
	}

	@Override
	public String toString() {
		return getEndpointDescription().toString();
	}


	/**
	 * Inner class to avoid a hard dependency on the JCA API.
	 */
	/**
	 * 内部类，以避免对JCA API的严格依赖。 
	 * 
	 */
	private class JcaEndpointConfigurer {

		public void configureEndpoint(Object listenerContainer) {
			if (listenerContainer instanceof JmsMessageEndpointManager) {
				setupJcaMessageContainer((JmsMessageEndpointManager) listenerContainer);
			}
			else {
				throw new IllegalArgumentException("Could not configure endpoint with the specified container '" +
						listenerContainer + "' Only JMS (" + AbstractMessageListenerContainer.class.getName() +
						" subclass) or JCA (" + JmsMessageEndpointManager.class.getName() + ") are supported.");
			}
		}

		private void setupJcaMessageContainer(JmsMessageEndpointManager container) {
			JmsActivationSpecConfig activationSpecConfig = container.getActivationSpecConfig();
			if (activationSpecConfig == null) {
				activationSpecConfig = new JmsActivationSpecConfig();
				container.setActivationSpecConfig(activationSpecConfig);
			}
			if (getDestination() != null) {
				activationSpecConfig.setDestinationName(getDestination());
			}
			if (getSubscription() != null) {
				activationSpecConfig.setSubscriptionName(getSubscription());
			}
			if (getSelector() != null) {
				activationSpecConfig.setMessageSelector(getSelector());
			}
			if (getConcurrency() != null) {
				activationSpecConfig.setConcurrency(getConcurrency());
			}
			setupMessageListener(container);
		}
	}

}
