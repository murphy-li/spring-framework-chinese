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

package org.springframework.jms.core.support;

import javax.jms.ConnectionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.lang.Nullable;

/**
 * Convenient super class for application classes that need JMS access.
 *
 * <p>Requires a ConnectionFactory or a JmsTemplate instance to be set.
 * It will create its own JmsTemplate if a ConnectionFactory is passed in.
 * A custom JmsTemplate instance can be created for a given ConnectionFactory
 * through overriding the {@link #createJmsTemplate} method.
 *
 * @author Mark Pollack
 * @since 1.1.1
 * @see #setConnectionFactory
 * @see #setJmsTemplate
 * @see #createJmsTemplate
 * @see org.springframework.jms.core.JmsTemplate
 */
/**
 * 方便的超类，用于需要JMS访问的应用程序类。 
 *  <p>需要设置ConnectionFactory或JmsTemplate实例。 
 * 如果传入ConnectionFactory，它将创建自己的JmsTemplate。 
 * 可以通过重写{@link  #createJmsTemplate}方法为给定的ConnectionFactory创建自定义JmsTemplate实例。 
 *  @author  Mark Pollack @since 1.1.1 
 * @see  #setConnectionFactory 
 * @see  #setJmsTemplate 
 * @see  #createJmsTemplate 
 * @see  org.springframework.jms.core.JmsTemplate
 */
public abstract class JmsGatewaySupport implements InitializingBean {

	/** Logger available to subclasses. */
	/**
	 * 记录器可用于子类。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private JmsTemplate jmsTemplate;


	/**
	 * Set the JMS connection factory to be used by the gateway.
	 * Will automatically create a JmsTemplate for the given ConnectionFactory.
	 * @see #createJmsTemplate
	 * @see #setConnectionFactory(javax.jms.ConnectionFactory)
	 */
	/**
	 * 设置网关要使用的JMS连接工厂。 
	 * 将自动为给定的ConnectionFactory创建一个JmsTemplate。 
	 *  
	 * @see  #createJmsTemplate 
	 * @see  #setConnectionFactory（javax.jms.ConnectionFactory）
	 */
	public final void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.jmsTemplate = createJmsTemplate(connectionFactory);
	}

	/**
	 * Create a JmsTemplate for the given ConnectionFactory.
	 * Only invoked if populating the gateway with a ConnectionFactory reference.
	 * <p>Can be overridden in subclasses to provide a JmsTemplate instance with
	 * a different configuration.
	 * @param connectionFactory the JMS ConnectionFactory to create a JmsTemplate for
	 * @return the new JmsTemplate instance
	 * @see #setConnectionFactory
	 */
	/**
	 * 为给定的ConnectionFactory创建一个JmsTemplate。 
	 * 仅在使用ConnectionFactory引用填充网关时调用。 
	 * 可以在子类中覆盖<p>，以为JmsTemplate实例提供不同的配置。 
	 *  
	 * @param  connectionFactory JMS ConnectionFactory为
	 * @return 新的JmsTemplate实例
	 * @see  #setConnectionFactory创建JmsTemplate
	 */
	protected JmsTemplate createJmsTemplate(ConnectionFactory connectionFactory) {
		return new JmsTemplate(connectionFactory);
	}

	/**
	 * Return the JMS ConnectionFactory used by the gateway.
	 */
	/**
	 * 返回网关使用的JMS ConnectionFactory。 
	 * 
	 */
	@Nullable
	public final ConnectionFactory getConnectionFactory() {
		return (this.jmsTemplate != null ? this.jmsTemplate.getConnectionFactory() : null);
	}

	/**
	 * Set the JmsTemplate for the gateway.
	 * @see #setConnectionFactory(javax.jms.ConnectionFactory)
	 */
	/**
	 * 设置网关的JmsTemplate。 
	 *  
	 * @see  #setConnectionFactory（javax.jms.ConnectionFactory）
	 */
	public final void setJmsTemplate(@Nullable JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * Return the JmsTemplate for the gateway.
	 */
	/**
	 * 返回网关的JmsTemplate。 
	 * 
	 */
	@Nullable
	public final JmsTemplate getJmsTemplate() {
		return this.jmsTemplate;
	}

	@Override
	public final void afterPropertiesSet() throws IllegalArgumentException, BeanInitializationException {
		if (this.jmsTemplate == null) {
			throw new IllegalArgumentException("'connectionFactory' or 'jmsTemplate' is required");
		}
		try {
			initGateway();
		}
		catch (Exception ex) {
			throw new BeanInitializationException("Initialization of JMS gateway failed: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Subclasses can override this for custom initialization behavior.
	 * Gets called after population of this instance's bean properties.
	 * @throws java.lang.Exception if initialization fails
	 */
	/**
	 * 子类可以重写此方法以实现自定义初始化行为。 
	 * 在填充该实例的bean属性之后调用。 
	 *  
	 * @throws  java.lang.Exception如果初始化失败
	 */
	protected void initGateway() throws Exception {
	}

}
