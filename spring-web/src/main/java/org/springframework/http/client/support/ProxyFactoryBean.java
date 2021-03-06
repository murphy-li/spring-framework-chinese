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

package org.springframework.http.client.support;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link FactoryBean} that creates a {@link Proxy java.net.Proxy}.
 *
 * @author Arjen Poutsma
 * @since 3.0.4
 * @see	FactoryBean
 * @see Proxy
 */
/**
 * 用于创建{@link 代理java.net.Proxy}的{@link  FactoryBean}。 
 *  @author  Arjen Poutsma @从3.0.4开始
 * @see  FactoryBean 
 * @see 代理
 */
public class ProxyFactoryBean implements FactoryBean<Proxy>, InitializingBean {

	private Proxy.Type type = Proxy.Type.HTTP;

	@Nullable
	private String hostname;

	private int port = -1;

	@Nullable
	private Proxy proxy;


	/**
	 * Set the proxy type.
	 * <p>Defaults to {@link java.net.Proxy.Type#HTTP}.
	 */
	/**
	 * 设置代理类型。 
	 *  <p>默认为{@link  java.net.Proxy.Type＃HTTP}。 
	 * 
	 */
	public void setType(Proxy.Type type) {
		this.type = type;
	}

	/**
	 * Set the proxy host name.
	 */
	/**
	 * 设置代理主机名。 
	 * 
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Set the proxy port.
	 */
	/**
	 * 设置代理端口。 
	 * 
	 */
	public void setPort(int port) {
		this.port = port;
	}


	@Override
	public void afterPropertiesSet() throws IllegalArgumentException {
		Assert.notNull(this.type, "Property 'type' is required");
		Assert.notNull(this.hostname, "Property 'hostname' is required");
		if (this.port < 0 || this.port > 65535) {
			throw new IllegalArgumentException("Property 'port' value out of range: " + this.port);
		}

		SocketAddress socketAddress = new InetSocketAddress(this.hostname, this.port);
		this.proxy = new Proxy(this.type, socketAddress);
	}


	@Override
	@Nullable
	public Proxy getObject() {
		return this.proxy;
	}

	@Override
	public Class<?> getObjectType() {
		return Proxy.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
