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

package org.springframework.jca.cci.connection;

import javax.resource.cci.Connection;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * Resource holder wrapping a CCI {@link Connection}.
 * {@link CciLocalTransactionManager} binds instances of this class to the thread,
 * for a given {@link javax.resource.cci.ConnectionFactory}.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Thierry Templier
 * @author Juergen Hoeller
 * @since 1.2
 * @see CciLocalTransactionManager
 * @see ConnectionFactoryUtils
 */
/**
 * 资源持有人包装了CCI {@link 连接}。 
 * 对于给定的{@link  javax.resource.cci.ConnectionFactory}，{<@link> CciLocalTransactionManager}将此类的实例绑定到线程。 
 *  <p>注意：这是SPI类，不适合应用程序使用。 
 *  @author  Thierry Templier @author  Juergen Hoeller @始于1.2 
 * @see  CciLocalTransactionManager 
 * @see  ConnectionFactoryUtils
 */
public class ConnectionHolder extends ResourceHolderSupport {

	private final Connection connection;


	public ConnectionHolder(Connection connection) {
		this.connection = connection;
	}


	public Connection getConnection() {
		return this.connection;
	}

}
