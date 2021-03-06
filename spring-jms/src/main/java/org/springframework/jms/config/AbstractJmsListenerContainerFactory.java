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

import javax.jms.ConnectionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

/**
 * Base {@link JmsListenerContainerFactory} for Spring's base container implementation.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @param <C> the container type
 * @see AbstractMessageListenerContainer
 */
/**
 * Spring的基本容器实现的基础{@link  JmsListenerContainerFactory}。 
 *  @author  Stephane Nicoll @since 4.1起
 * @param  <C>容器类型
 * @see  AbstractMessageListenerContainer
 */
public abstract class AbstractJmsListenerContainerFactory<C extends AbstractMessageListenerContainer>
		implements JmsListenerContainerFactory<C> {

	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private ConnectionFactory connectionFactory;

	@Nullable
	private DestinationResolver destinationResolver;

	@Nullable
	private ErrorHandler errorHandler;

	@Nullable
	private MessageConverter messageConverter;

	@Nullable
	private Boolean sessionTransacted;

	@Nullable
	private Integer sessionAcknowledgeMode;

	@Nullable
	private Boolean pubSubDomain;

	@Nullable
	private Boolean replyPubSubDomain;

	@Nullable
	private QosSettings replyQosSettings;

	@Nullable
	private Boolean subscriptionDurable;

	@Nullable
	private Boolean subscriptionShared;

	@Nullable
	private String clientId;

	@Nullable
	private Integer phase;

	@Nullable
	private Boolean autoStartup;


	/**
	 * @see AbstractMessageListenerContainer#setConnectionFactory(ConnectionFactory)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setConnectionFactory（ConnectionFactory）
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * @see AbstractMessageListenerContainer#setDestinationResolver(DestinationResolver)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setDestinationResolver（DestinationResolver）
	 */
	public void setDestinationResolver(DestinationResolver destinationResolver) {
		this.destinationResolver = destinationResolver;
	}

	/**
	 * @see AbstractMessageListenerContainer#setErrorHandler(ErrorHandler)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setErrorHandler（ErrorHandler）
	 */
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/**
	 * @see AbstractMessageListenerContainer#setMessageConverter(MessageConverter)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setMessageConverter（MessageConverter）
	 */
	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	/**
	 * @see AbstractMessageListenerContainer#setSessionTransacted(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setSessionTransacted（boolean）
	 */
	public void setSessionTransacted(Boolean sessionTransacted) {
		this.sessionTransacted = sessionTransacted;
	}

	/**
	 * @see AbstractMessageListenerContainer#setSessionAcknowledgeMode(int)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setSessionAcknowledgeMode（int）
	 */
	public void setSessionAcknowledgeMode(Integer sessionAcknowledgeMode) {
		this.sessionAcknowledgeMode = sessionAcknowledgeMode;
	}

	/**
	 * @see AbstractMessageListenerContainer#setPubSubDomain(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setPubSubDomain（boolean）
	 */
	public void setPubSubDomain(Boolean pubSubDomain) {
		this.pubSubDomain = pubSubDomain;
	}

	/**
	 * @see AbstractMessageListenerContainer#setReplyPubSubDomain(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setReplyPubSubDomain（boolean）
	 */
	public void setReplyPubSubDomain(Boolean replyPubSubDomain) {
		this.replyPubSubDomain = replyPubSubDomain;
	}

	/**
	 * @see AbstractMessageListenerContainer#setReplyQosSettings(QosSettings)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setReplyQosSettings（QosSettings）
	 */
	public void setReplyQosSettings(QosSettings replyQosSettings) {
		this.replyQosSettings = replyQosSettings;
	}

	/**
	 * @see AbstractMessageListenerContainer#setSubscriptionDurable(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setSubscriptionDurable（boolean）
	 */
	public void setSubscriptionDurable(Boolean subscriptionDurable) {
		this.subscriptionDurable = subscriptionDurable;
	}

	/**
	 * @see AbstractMessageListenerContainer#setSubscriptionShared(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setSubscriptionShared（boolean）
	 */
	public void setSubscriptionShared(Boolean subscriptionShared) {
		this.subscriptionShared = subscriptionShared;
	}

	/**
	 * @see AbstractMessageListenerContainer#setClientId(String)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setClientId（String）
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @see AbstractMessageListenerContainer#setPhase(int)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setPhase（int）
	 */
	public void setPhase(int phase) {
		this.phase = phase;
	}

	/**
	 * @see AbstractMessageListenerContainer#setAutoStartup(boolean)
	 */
	/**
	 * 
	 * @see  AbstractMessageListenerContainer＃setAutoStartup（boolean）
	 */
	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}

	@Override
	public C createListenerContainer(JmsListenerEndpoint endpoint) {
		C instance = createContainerInstance();

		if (this.connectionFactory != null) {
			instance.setConnectionFactory(this.connectionFactory);
		}
		if (this.destinationResolver != null) {
			instance.setDestinationResolver(this.destinationResolver);
		}
		if (this.errorHandler != null) {
			instance.setErrorHandler(this.errorHandler);
		}
		if (this.messageConverter != null) {
			instance.setMessageConverter(this.messageConverter);
		}
		if (this.sessionTransacted != null) {
			instance.setSessionTransacted(this.sessionTransacted);
		}
		if (this.sessionAcknowledgeMode != null) {
			instance.setSessionAcknowledgeMode(this.sessionAcknowledgeMode);
		}
		if (this.pubSubDomain != null) {
			instance.setPubSubDomain(this.pubSubDomain);
		}
		if (this.replyPubSubDomain != null) {
			instance.setReplyPubSubDomain(this.replyPubSubDomain);
		}
		if (this.replyQosSettings != null) {
			instance.setReplyQosSettings(this.replyQosSettings);
		}
		if (this.subscriptionDurable != null) {
			instance.setSubscriptionDurable(this.subscriptionDurable);
		}
		if (this.subscriptionShared != null) {
			instance.setSubscriptionShared(this.subscriptionShared);
		}
		if (this.clientId != null) {
			instance.setClientId(this.clientId);
		}
		if (this.phase != null) {
			instance.setPhase(this.phase);
		}
		if (this.autoStartup != null) {
			instance.setAutoStartup(this.autoStartup);
		}

		initializeContainer(instance);
		endpoint.setupListenerContainer(instance);

		return instance;
	}

	/**
	 * Create an empty container instance.
	 */
	/**
	 * 创建一个空的容器实例。 
	 * 
	 */
	protected abstract C createContainerInstance();

	/**
	 * Further initialize the specified container.
	 * <p>Subclasses can inherit from this method to apply extra
	 * configuration if necessary.
	 */
	/**
	 * 进一步初始化指定的容器。 
	 *  <p>子类可以从此方法继承以在必要时应用额外的配置。 
	 * 
	 */
	protected void initializeContainer(C instance) {
	}

}
