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

package org.springframework.jmx.access;

import java.io.IOException;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jmx.MBeanServerNotFoundException;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.lang.Nullable;

/**
 * Internal helper class for managing a JMX connector.
 *
 * @author Juergen Hoeller
 * @since 2.5.2
 */
/**
 * 内部助手类，用于管理JMX连接器。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@2.5.2起
 */
class ConnectorDelegate {

	private static final Log logger = LogFactory.getLog(ConnectorDelegate.class);

	@Nullable
	private JMXConnector connector;


	/**
	 * Connects to the remote {@code MBeanServer} using the configured {@code JMXServiceURL}:
	 * to the specified JMX service, or to a local MBeanServer if no service URL specified.
	 * @param serviceUrl the JMX service URL to connect to (may be {@code null})
	 * @param environment the JMX environment for the connector (may be {@code null})
	 * @param agentId the local JMX MBeanServer's agent id (may be {@code null})
	 */
	/**
	 * 使用配置的{@code  JMXServiceURL}连接到远程{@code  MBeanServer}：连接到指定的JMX服务； 
	 * 如果未指定服务URL，则连接到本地MBeanServer。 
	 *  
	 * @param  serviceUrl要连接到的JMX服务URL（可能为{@code  null}）
	 * @param 环境连接器的JMX环境（可能为{@code  null}）
	 * @param  agentId本地JMX MBeanServer的代理ID（可以为{@code  null}）
	 */
	public MBeanServerConnection connect(@Nullable JMXServiceURL serviceUrl, @Nullable Map<String, ?> environment, @Nullable String agentId)
			throws MBeanServerNotFoundException {

		if (serviceUrl != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Connecting to remote MBeanServer at URL [" + serviceUrl + "]");
			}
			try {
				this.connector = JMXConnectorFactory.connect(serviceUrl, environment);
				return this.connector.getMBeanServerConnection();
			}
			catch (IOException ex) {
				throw new MBeanServerNotFoundException("Could not connect to remote MBeanServer [" + serviceUrl + "]", ex);
			}
		}
		else {
			logger.debug("Attempting to locate local MBeanServer");
			return JmxUtils.locateMBeanServer(agentId);
		}
	}

	/**
	 * Closes any {@code JMXConnector} that may be managed by this interceptor.
	 */
	/**
	 * 关闭此拦截器可能管理的任何{@code  JMXConnector}。 
	 * 
	 */
	public void close() {
		if (this.connector != null) {
			try {
				this.connector.close();
			}
			catch (IOException ex) {
				logger.debug("Could not close JMX connector", ex);
			}
		}
	}

}
