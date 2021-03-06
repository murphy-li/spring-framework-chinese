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

package org.springframework.jca.support;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

/**
 * {@link org.springframework.beans.factory.FactoryBean} that creates
 * a local JCA connection factory in "non-managed" mode (as defined by the
 * Java Connector Architecture specification). This is a direct alternative
 * to a {@link org.springframework.jndi.JndiObjectFactoryBean} definition that
 * obtains a connection factory handle from a Java EE server's naming environment.
 *
 * <p>The type of the connection factory is dependent on the actual connector:
 * the connector can either expose its native API (such as a JDBC
 * {@link javax.sql.DataSource} or a JMS {@link javax.jms.ConnectionFactory})
 * or follow the standard Common Client Interface (CCI), as defined by the JCA spec.
 * The exposed interface in the CCI case is {@link javax.resource.cci.ConnectionFactory}.
 *
 * <p>In order to use this FactoryBean, you must specify the connector's
 * {@link #setManagedConnectionFactory "managedConnectionFactory"} (usually
 * configured as separate JavaBean), which will be used to create the actual
 * connection factory reference as exposed to the application. Optionally,
 * you can also specify a {@link #setConnectionManager "connectionManager"},
 * in order to use a custom ConnectionManager instead of the connector's default.
 *
 * <p><b>NOTE:</b> In non-managed mode, a connector is not deployed on an
 * application server, or more specifically not interacting with an application
 * server. Consequently, it cannot use a Java EE server's system contracts:
 * connection management, transaction management, and security management.
 * A custom ConnectionManager implementation has to be used for applying those
 * services in conjunction with a standalone transaction coordinator etc.
 *
 * <p>The connector will use a local ConnectionManager (included in the connector)
 * by default, which cannot participate in global transactions due to the lack
 * of XA enlistment. You need to specify an XA-capable ConnectionManager in
 * order to make the connector interact with an XA transaction coordinator.
 * Alternatively, simply use the native local transaction facilities of the
 * exposed API (e.g. CCI local transactions), or use a corresponding
 * implementation of Spring's PlatformTransactionManager SPI
 * (e.g. {@link org.springframework.jca.cci.connection.CciLocalTransactionManager})
 * to drive local transactions.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setManagedConnectionFactory
 * @see #setConnectionManager
 * @see javax.resource.cci.ConnectionFactory
 * @see javax.resource.cci.Connection#getLocalTransaction
 * @see org.springframework.jca.cci.connection.CciLocalTransactionManager
 */
/**
 * {@link  org.springframework.beans.factory.FactoryBean}以"非托管"模式（由Java Connector Architecture规范定义）创建本地JCA连接工厂。 
 * 这是{@link  org.springframework.jndi.JndiObjectFactoryBean}定义的直接替代方法，该定义从Java EE服务器的命名环境获取连接工厂句柄。 
 *  <p>连接工厂的类型取决于实际的连接器：连接器可以公开其本机API（例如JDBC {@link  javax.sql.DataSource}或JMS {@link  javax。 
 *  jms.ConnectionFactory}）或遵循JCA规范定义的标准通用客户端接口（CCI）。 
 * 在CCI情况下，公开的接口为{@link  javax.resource.cci.ConnectionFactory}。 
 *  <p>为了使用此FactoryBean，必须指定连接器的{@link  #setManagedConnectionFactory"managedConnectionFactory"}（通常配置为单独的JavaBean），它将用于创建对应用程序公开的实际连接工厂引用。 
 *  。 
 *  （可选）您也可以指定{@link  #setConnectionManager"connectionManager"}，以便使用自定义ConnectionManager代替连接器的默认设置。 
 *  <p> <b>注意：</ b>：在非托管模式下，连接器未部署在应用程序服务器上，或更具体地说，不与应用程序服务器交互。 
 * 因此，它不能使用Java EE服务器的系统合同：连接管理，事务管理和安全性管理。 
 * 必须使用自定义的ConnectionManager实现将这些服务与独立的事务协调器等结合使用。 
 * <p>默认情况下，连接器将使用本地ConnectionManager（包含在连接器中），由于缺少该连接器，因此无法参与全局事务XA征募。 
 * 您需要指定一个具有XA功能的ConnectionManager，以使连接器与XA事务协调器进行交互。 
 * 或者，只需使用公开API的本地本地事务处理功能（例如CCI本地事务），或使用Spring的PlatformTransactionManager SPI的相应实现（例如{@link  org.springframework.jca.cci.connection.CciLocalTransactionManager}）即可推动本地交易。 
 *  @author  Juergen Hoeller @since 1.2起
 * @see  #setManagedConnectionFactory 
 * @see  #setConnectionManager 
 * @see  javax.resource.cci.ConnectionFactory 
 * @see  javax.resource.cci.Connection＃getLocalTransaction 
 * @see  org .springframework.jca.cci.connection.CciLocalTransactionManager
 */
public class LocalConnectionFactoryBean implements FactoryBean<Object>, InitializingBean {

	@Nullable
	private ManagedConnectionFactory managedConnectionFactory;

	@Nullable
	private ConnectionManager connectionManager;

	@Nullable
	private Object connectionFactory;


	/**
	 * Set the JCA ManagerConnectionFactory that should be used to create
	 * the desired connection factory.
	 * <p>The ManagerConnectionFactory will usually be set up as separate bean
	 * (potentially as inner bean), populated with JavaBean properties:
	 * a ManagerConnectionFactory is encouraged to follow the JavaBean pattern
	 * by the JCA specification, analogous to a JDBC DataSource and a JPA
	 * EntityManagerFactory.
	 * <p>Note that the ManagerConnectionFactory implementation might expect
	 * a reference to its JCA 1.7 ResourceAdapter, expressed through the
	 * {@link javax.resource.spi.ResourceAdapterAssociation} interface.
	 * Simply inject the corresponding ResourceAdapter instance into its
	 * "resourceAdapter" bean property in this case, before passing the
	 * ManagerConnectionFactory into this LocalConnectionFactoryBean.
	 * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory()
	 */
	/**
	 * 设置应用于创建所需连接工厂的JCA ManagerConnectionFactory。 
	 *  <p>通常将ManagerConnectionFactory设置为单独的bean（可能是内部bean），并填充JavaBean属性：JCA规范鼓励ManagerConnectionFactory遵循JavaBean模式，类似于JDBC DataSource和JPA EntityManagerFactory。 
	 *  <p>请注意，ManagerConnectionFactory实现可能期望引用其JCA 1.7 ResourceAdapter，该引用通过{@link  javax.resource.spi.ResourceAdapterAssociation}接口表示。 
	 * 在这种情况下，只需将相应的ResourceAdapter实例注入其"resourceAdapter"bean属性中，然后再将ManagerConnectionFactory传递到此LocalConnectionFactoryBean中即可。 
	 *  
	 * @see  javax.resource.spi.ManagedConnectionFactory＃createConnectionFactory（）
	 */
	public void setManagedConnectionFactory(ManagedConnectionFactory managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * Set the JCA ConnectionManager that should be used to create the
	 * desired connection factory.
	 * <p>A ConnectionManager implementation for local usage is often
	 * included with a JCA connector. Such an included ConnectionManager
	 * might be set as default, with no need to explicitly specify one.
	 * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory(javax.resource.spi.ConnectionManager)
	 */
	/**
	 * 设置用于创建所需连接工厂的JCA ConnectionManager。 
	 *  <p> JCA连接器通常包含用于本地使用的ConnectionManager实现。 
	 * 这样包含的ConnectionManager可以设置为默认值，而无需显式指定。 
	 *  
	 * @see  javax.resource.spi.ManagedConnectionFactory＃createConnectionFactory（javax.resource.spi.ConnectionManager）
	 */
	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public void afterPropertiesSet() throws ResourceException {
		if (this.managedConnectionFactory == null) {
			throw new IllegalArgumentException("Property 'managedConnectionFactory' is required");
		}
		if (this.connectionManager != null) {
			this.connectionFactory = this.managedConnectionFactory.createConnectionFactory(this.connectionManager);
		}
		else {
			this.connectionFactory = this.managedConnectionFactory.createConnectionFactory();
		}
	}


	@Override
	@Nullable
	public Object getObject() {
		return this.connectionFactory;
	}

	@Override
	public Class<?> getObjectType() {
		return (this.connectionFactory != null ? this.connectionFactory.getClass() : null);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
