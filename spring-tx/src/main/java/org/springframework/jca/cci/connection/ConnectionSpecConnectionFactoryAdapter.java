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

package org.springframework.jca.cci.connection;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * An adapter for a target CCI {@link javax.resource.cci.ConnectionFactory},
 * applying the given ConnectionSpec to every standard {@code getConnection()}
 * call, that is, implicitly invoking {@code getConnection(ConnectionSpec)}
 * on the target. All other methods simply delegate to the corresponding methods
 * of the target ConnectionFactory.
 *
 * <p>Can be used to proxy a target JNDI ConnectionFactory that does not have a
 * ConnectionSpec configured. Client code can work with the ConnectionFactory
 * without passing in a ConnectionSpec on every {@code getConnection()} call.
 *
 * <p>In the following example, client code can simply transparently work with
 * the preconfigured "myConnectionFactory", implicitly accessing
 * "myTargetConnectionFactory" with the specified user credentials.
 *
 * <pre class="code">
 * &lt;bean id="myTargetConnectionFactory" class="org.springframework.jndi.JndiObjectFactoryBean"&gt;
 *   &lt;property name="jndiName" value="java:comp/env/cci/mycf"/&gt;
 * &lt;/bean>
 *
 * &lt;bean id="myConnectionFactory" class="org.springframework.jca.cci.connection.ConnectionSpecConnectionFactoryAdapter"&gt;
 *   &lt;property name="targetConnectionFactory" ref="myTargetConnectionFactory"/&gt;
 *   &lt;property name="connectionSpec"&gt;
 *     &lt;bean class="your.resource.adapter.ConnectionSpecImpl"&gt;
 *       &lt;property name="username" value="myusername"/&gt;
 *       &lt;property name="password" value="mypassword"/&gt;
 *     &lt;/bean&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;</pre>
 *
 * <p>If the "connectionSpec" is empty, this proxy will simply delegate to the
 * standard {@code getConnection()} method of the target ConnectionFactory.
 * This can be used to keep a UserCredentialsConnectionFactoryAdapter bean definition
 * just for the <i>option</i> of implicitly passing in a ConnectionSpec if the
 * particular target ConnectionFactory requires it.
 *
 * @author Juergen Hoeller
 * @since 1.2
 * @see #getConnection
 */
/**
 * 目标CCI {@link  javax.resource.cci.ConnectionFactory}的适配器，将给定的ConnectionSpec应用于每个标准{@code  getConnection（）}调用，即隐式调用{@code  getConnection（目标上的ConnectionSpec）}。 
 * 所有其他方法仅委托给目标ConnectionFactory的相应方法。 
 *  <p>可用于代理未配置ConnectionSpec的目标JNDI ConnectionFactory。 
 * 客户端代码可以与ConnectionFactory一起使用，而无需在每个{@code  getConnection（）}调用中都传递ConnectionSpec。 
 *  <p>在以下示例中，客户端代码可以简单地与预配置的"myConnectionFactory"透明地工作，并使用指定的用户凭据隐式访问"myTargetConnectionFactory"。 
 *  <pre class ="code"> <bean id ="myTargetConnectionFactory"class ="org.springframework.jndi.JndiObjectFactoryBean"> <属性名称="jndiName"value ="java：comp / env / cci / mycf"/> < / bean> <bean id ="myConnectionFactory"class ="org.springframework.jca.cci.connection.ConnectionSpecConnectionFactoryAdapter"> <属性名称="targetConnectionFactory"ref ="myTargetConnectionFactory"/> <属性名称="connectionSpec"> <bean class ="your.resource.adapter.ConnectionSpecImpl"> <property name ="username"value ="myusername"/> <property name ="password"value ="mypassword"/> </ bean> </ property> </ bean> </ pre> <p>如果"connectionSpec"为空，则此代理将仅委派给目标ConnectionFactory的标准{@code  getConnection（）}方法。 
 * 如果特定的目标ConnectionFactory需要，则可以仅将<spec> </ i>隐式地传递给ConnectionSpec的<i> option </ i>来使用UserCredentialsConnectionFactoryAdapter bean定义。 
 *  @author  Juergen Hoeller @始于1.2 
 * @see  #getConnection
 */
@SuppressWarnings("serial")
public class ConnectionSpecConnectionFactoryAdapter extends DelegatingConnectionFactory {

	@Nullable
	private ConnectionSpec connectionSpec;

	private final ThreadLocal<ConnectionSpec> threadBoundSpec =
			new NamedThreadLocal<>("Current CCI ConnectionSpec");


	/**
	 * Set the ConnectionSpec that this adapter should use for retrieving Connections.
	 * Default is none.
	 */
	/**
	 * 设置此适配器用于检索连接的ConnectionSpec。 
	 * 默认为无。 
	 * 
	 */
	public void setConnectionSpec(ConnectionSpec connectionSpec) {
		this.connectionSpec = connectionSpec;
	}

	/**
	 * Set a ConnectionSpec for this proxy and the current thread.
	 * The given ConnectionSpec will be applied to all subsequent
	 * {@code getConnection()} calls on this ConnectionFactory proxy.
	 * <p>This will override any statically specified "connectionSpec" property.
	 * @param spec the ConnectionSpec to apply
	 * @see #removeConnectionSpecFromCurrentThread
	 */
	/**
	 * 设置此代理和当前线程的ConnectionSpec。 
	 * 给定的ConnectionSpec将应用于此ConnectionFactory代理上的所有后续{@code  getConnection（）}调用。 
	 *  <p>这将覆盖任何静态指定的"connectionSpec"属性。 
	 *  
	 * @param 指定要应用的ConnectionSpec 
	 * @see  #removeConnectionSpecFromCurrentThread
	 */
	public void setConnectionSpecForCurrentThread(ConnectionSpec spec) {
		this.threadBoundSpec.set(spec);
	}

	/**
	 * Remove any ConnectionSpec for this proxy from the current thread.
	 * A statically specified ConnectionSpec applies again afterwards.
	 * @see #setConnectionSpecForCurrentThread
	 */
	/**
	 * 从当前线程中删除此代理的所有ConnectionSpec。 
	 * 静态指定的ConnectionSpec之后将再次应用。 
	 *  
	 * @see  #setConnectionSpecForCurrentThread
	 */
	public void removeConnectionSpecFromCurrentThread() {
		this.threadBoundSpec.remove();
	}


	/**
	 * Determine whether there is currently a thread-bound ConnectionSpec,
	 * using it if available, falling back to the statically specified
	 * "connectionSpec" property else.
	 * @see #doGetConnection
	 */
	/**
	 * 确定当前是否存在线程绑定的ConnectionSpec，如果可用的话，使用它，然后回退到静态指定的"connectionSpec"属性。 
	 *  
	 * @see  #doGetConnection
	 */
	@Override
	public final Connection getConnection() throws ResourceException {
		ConnectionSpec threadSpec = this.threadBoundSpec.get();
		if (threadSpec != null) {
			return doGetConnection(threadSpec);
		}
		else {
			return doGetConnection(this.connectionSpec);
		}
	}

	/**
	 * This implementation delegates to the {@code getConnection(ConnectionSpec)}
	 * method of the target ConnectionFactory, passing in the specified user credentials.
	 * If the specified username is empty, it will simply delegate to the standard
	 * {@code getConnection()} method of the target ConnectionFactory.
	 * @param spec the ConnectionSpec to apply
	 * @return the Connection
	 * @see javax.resource.cci.ConnectionFactory#getConnection(javax.resource.cci.ConnectionSpec)
	 * @see javax.resource.cci.ConnectionFactory#getConnection()
	 */
	/**
	 * 此实现委托目标ConnectionFactory的{@code  getConnection（ConnectionSpec）}方法，传入指定的用户凭据。 
	 * 如果指定的用户名为空，它将仅委托给目标ConnectionFactory的标准{@code  getConnection（）}方法。 
	 *  
	 * @param 规范要应用的ConnectionSpec 
	 * @return 连接
	 * @see  javax.resource.cci.ConnectionFactory＃getConnection（javax.resource.cci.ConnectionSpec）
	 * @see  javax.resource.cci.ConnectionFactory＃getConnection （）
	 */
	protected Connection doGetConnection(@Nullable ConnectionSpec spec) throws ResourceException {
		ConnectionFactory connectionFactory = getTargetConnectionFactory();
		Assert.state(connectionFactory != null, "No 'targetConnectionFactory' set");
		return (spec != null ? connectionFactory.getConnection(spec) : connectionFactory.getConnection());
	}

}
