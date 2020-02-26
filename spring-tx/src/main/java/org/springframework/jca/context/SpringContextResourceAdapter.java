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

package org.springframework.jca.context;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * JCA 1.7 {@link javax.resource.spi.ResourceAdapter} implementation
 * that loads a Spring {@link org.springframework.context.ApplicationContext},
 * starting and stopping Spring-managed beans as part of the ResourceAdapter's
 * lifecycle.
 *
 * <p>Ideal for application contexts that do not need any HTTP entry points
 * but rather just consist of message endpoints and scheduled jobs etc.
 * Beans in such a context may use application server resources such as the
 * JTA transaction manager and JNDI-bound JDBC DataSources and JMS
 * ConnectionFactory instances, and may also register with the platform's
 * JMX server - all through Spring's standard transaction management and
 * JNDI and JMX support facilities.
 *
 * <p>If the need for scheduling asynchronous work arises, consider using
 * Spring's {@link org.springframework.jca.work.WorkManagerTaskExecutor}
 * as a standard bean definition, to be injected into application beans
 * through dependency injection. This WorkManagerTaskExecutor will automatically
 * use the JCA WorkManager from the BootstrapContext that has been provided
 * to this ResourceAdapter.
 *
 * <p>The JCA {@link javax.resource.spi.BootstrapContext} may also be
 * accessed directly, through application components that implement the
 * {@link BootstrapContextAware} interface. When deployed using this
 * ResourceAdapter, the BootstrapContext is guaranteed to be passed on
 * to such components.
 *
 * <p>This ResourceAdapter is to be defined in a "META-INF/ra.xml" file
 * within a Java EE ".rar" deployment unit like as follows:
 *
 * <pre class="code">
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;connector xmlns="http://java.sun.com/xml/ns/j2ee"
 *		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *		 xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee https://java.sun.com/xml/ns/j2ee/connector_1_5.xsd"
 *		 version="1.5"&gt;
 *	 &lt;vendor-name&gt;Spring Framework&lt;/vendor-name&gt;
 *	 &lt;eis-type&gt;Spring Connector&lt;/eis-type&gt;
 *	 &lt;resourceadapter-version&gt;1.0&lt;/resourceadapter-version&gt;
 *	 &lt;resourceadapter&gt;
 *		 &lt;resourceadapter-class&gt;org.springframework.jca.context.SpringContextResourceAdapter&lt;/resourceadapter-class&gt;
 *		 &lt;config-property&gt;
 *			 &lt;config-property-name&gt;ContextConfigLocation&lt;/config-property-name&gt;
 *			 &lt;config-property-type&gt;java.lang.String&lt;/config-property-type&gt;
 *			 &lt;config-property-value&gt;META-INF/applicationContext.xml&lt;/config-property-value&gt;
 *		 &lt;/config-property&gt;
 *	 &lt;/resourceadapter&gt;
 * &lt;/connector&gt;</pre>
 *
 * Note that "META-INF/applicationContext.xml" is the default context config
 * location, so it doesn't have to specified unless you intend to specify
 * different/additional config files. So in the default case, you may remove
 * the entire {@code config-property} section above.
 *
 * <p><b>For simple deployment needs, all you need to do is the following:</b>
 * Package all application classes into a RAR file (which is just a standard
 * JAR file with a different file extension), add all required library jars
 * into the root of the RAR archive, add a "META-INF/ra.xml" deployment
 * descriptor as shown above as well as the corresponding Spring XML bean
 * definition file(s) (typically "META-INF/applicationContext.xml"),
 * and drop the resulting RAR file into your application server's
 * deployment directory!
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see #setContextConfigLocation
 * @see #loadBeanDefinitions
 * @see ResourceAdapterApplicationContext
 */
/**
 * 加载Spring {@link  org.springframework.context.ApplicationContext}的JCA 1.7 {@link  javax.resource.spi.ResourceAdapter}实现，作为ResourceAdapter生命周期的一部分启动和停止Spring管理的bean。 
 *  <p>理想的应用程序上下文不需要任何HTTP入口点，而仅由消息端点和计划的作业等组成。 
 * 在这种上下文中的Bean可以使用应用程序服务器资源，例如JTA事务管理器和JNDI绑定的JDBC DataSources，以及JMS ConnectionFactory实例，并且还可以通过Spring的标准事务管理以及JNDI和JMX支持工具在平台的JMX服务器中注册。 
 *  <p>如果需要安排异步工作，请考虑使用Spring的{@link  org.springframework.jca.work.WorkManagerTaskExecutor}作为标准bean定义，通过依赖注入将其注入应用程序bean。 
 * 此WorkManagerTaskExecutor将自动使用已提供给此ResourceAdapter的BootstrapContext中的JCA WorkManager。 
 *  <p>还可以通过实现{@link  BootstrapContextAware}接口的应用程序组件直接访问JCA {@link  javax.resource.spi.BootstrapContext}。 
 * 使用此ResourceAdapter部署时，可以确保将BootstrapContext传递给此类组件。 
 *  <p>此ResourceAdapter将在Java EE".rar"部署单元内的"META-INF / ra.xml"文件中定义，如下所示：<pre class ="code"> <？xml version ="1.0 "encoding ="UTF-8"？> <连接器xmlns ="http://java.sun.com/xml/ns/j2ee"xmlns：xsi ="http://www.w3.org/2001/XMLSchema-实例"xsi：schemaLocation ="http://java.sun.com/xml/ns/j2ee https://java.sun.com/xml/ns/j2ee/connector_1_5.xsd"version ="1.5"> <供应商-name> Spring框架</ vendor-name> <eis-type> Spring连接器</ eis-type> <resourceadapter-version> 1.0 </ resourceadapter-version> <resourceadapter> <resourceadapter-class> org.springframework.jca。 
 *  context.SpringContextResourceAdapter </ resourceadapter-class> <config-property> <config-property-name> ContextConfigLocation </ config-property-name> <config-property-type> java.lang.String </ config-property-type> <config-property-value> META-INF / applicationContext.xml </ config-property-value> </ config-property> </ resourceadapter> </ connector> </ pre>请注意，"META-INF / applicationCont ext.xml"是默认的上下文配置位置，因此除非您打算指定其他/其他配置文件，否则不必指定。 
 * 因此，在默认情况下，您可以删除上面的整个{@code  config-property}部分。 
 *  <p> <b>对于简单的部署需求，您要做的只是以下操作：</ b>将所有应用程序类打包到RAR文件（这只是具有不同文件扩展名的标准JAR文件）中，添加所有必需的文件库震撼到RAR存档的根目录中，添加上面所示的"META-INF / ra.xml"部署描述符以及相应的Spring XML bean定义文件（通常为"META-INF / applicationContext.xml"） ），然后将生成的RAR文件拖放到应用程序服务器的部署目录中！ 
 *  @author  Juergen Hoeller @since 2.5起
 * @see  #setContextConfigLocation 
 * @see  #loadBeanDefinitions 
 * @see  ResourceAdapterApplicationContext
 */
public class SpringContextResourceAdapter implements ResourceAdapter {

	/**
	 * Any number of these characters are considered delimiters between
	 * multiple context config paths in a single String value.
	 * @see #setContextConfigLocation
	 */
	/**
	 * 在单个String值中，可以将任意数量的这些字符视为多个上下文配置路径之间的分隔符。 
	 *  
	 * @see  #setContextConfigLocation
	 */
	public static final String CONFIG_LOCATION_DELIMITERS = ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

	/**
	 * The default {@code applicationContext.xml} location.
	 */
	/**
	 * 默认的{@code  applicationContext.xml}位置。 
	 * 
	 */
	public static final String DEFAULT_CONTEXT_CONFIG_LOCATION = "META-INF/applicationContext.xml";


	protected final Log logger = LogFactory.getLog(getClass());

	private String contextConfigLocation = DEFAULT_CONTEXT_CONFIG_LOCATION;

	@Nullable
	private ConfigurableApplicationContext applicationContext;


	/**
	 * Set the location of the context configuration files, within the
	 * resource adapter's deployment unit. This can be a delimited
	 * String that consists of multiple resource location, separated
	 * by commas, semicolons, whitespace, or line breaks.
	 * <p>This can be specified as "ContextConfigLocation" config
	 * property in the {@code ra.xml} deployment descriptor.
	 * <p>The default is "classpath:META-INF/applicationContext.xml".
	 */
	/**
	 * 在资源适配器的部署单元内设置上下文配置文件的位置。 
	 * 这可以是一个由多个资源位置组成的定界字符串，用逗号，分号，空格或换行符分隔。 
	 *  <p>可以在{@code  ra.xml}部署描述符中将其指定为"ContextConfigLocation"配置属性。 
	 *  <p>默认值为"classpath：META-INF / applicationContext.xml"。 
	 * 
	 */
	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}

	/**
	 * Return the specified context configuration files.
	 */
	/**
	 * 返回指定的上下文配置文件。 
	 * 
	 */
	protected String getContextConfigLocation() {
		return this.contextConfigLocation;
	}

	/**
	 * Return a new {@link StandardEnvironment}.
	 * <p>Subclasses may override this method in order to supply
	 * a custom {@link ConfigurableEnvironment} implementation.
	 */
	/**
	 * 返回一个新的{@link  StandardEnvironment}。 
	 *  <p>子类可以重写此方法，以提供自定义的{@link  ConfigurableEnvironment}实现。 
	 * 
	 */
	protected ConfigurableEnvironment createEnvironment() {
		return new StandardEnvironment();
	}

	/**
	 * This implementation loads a Spring ApplicationContext through the
	 * {@link #createApplicationContext} template method.
	 */
	/**
	 * 此实现通过{@link  #createApplicationContext}模板方法加载Spring ApplicationContext。 
	 * 
	 */
	@Override
	public void start(BootstrapContext bootstrapContext) throws ResourceAdapterInternalException {
		if (logger.isDebugEnabled()) {
			logger.debug("Starting SpringContextResourceAdapter with BootstrapContext: " + bootstrapContext);
		}
		this.applicationContext = createApplicationContext(bootstrapContext);
	}

	/**
	 * Build a Spring ApplicationContext for the given JCA BootstrapContext.
	 * <p>The default implementation builds a {@link ResourceAdapterApplicationContext}
	 * and delegates to {@link #loadBeanDefinitions} for actually parsing the
	 * specified configuration files.
	 * @param bootstrapContext this ResourceAdapter's BootstrapContext
	 * @return the Spring ApplicationContext instance
	 */
	/**
	 * 为给定的JCA BootstrapContext构建一个Spring ApplicationContext。 
	 *  <p>默认实现构建一个{@link  ResourceAdapterApplicationContext}并委托给{@link  #loadBeanDefinitions}来实际解析指定的配置文件。 
	 *  
	 * @param  bootstrapContext此ResourceAdapter的BootstrapContext 
	 * @return  Spring ApplicationContext实例
	 */
	protected ConfigurableApplicationContext createApplicationContext(BootstrapContext bootstrapContext) {
		ResourceAdapterApplicationContext applicationContext =
				new ResourceAdapterApplicationContext(bootstrapContext);

		// Set ResourceAdapter's ClassLoader as bean class loader.
		applicationContext.setClassLoader(getClass().getClassLoader());

		// Extract individual config locations.
		String[] configLocations =
				StringUtils.tokenizeToStringArray(getContextConfigLocation(), CONFIG_LOCATION_DELIMITERS);

		loadBeanDefinitions(applicationContext, configLocations);
		applicationContext.refresh();

		return applicationContext;
	}

	/**
	 * Load the bean definitions into the given registry,
	 * based on the specified configuration files.
	 * @param registry the registry to load into
	 * @param configLocations the parsed config locations
	 * @see #setContextConfigLocation
	 */
	/**
	 * 根据指定的配置文件，将Bean定义加载到给定的注册表中。 
	 *  
	 * @param 注册表注册表加载到
	 * @param  configLocations中，已解析的配置位置
	 * @see  #setContextConfigLocation
	 */
	protected void loadBeanDefinitions(BeanDefinitionRegistry registry, String[] configLocations) {
		new XmlBeanDefinitionReader(registry).loadBeanDefinitions(configLocations);
	}

	/**
	 * This implementation closes the Spring ApplicationContext.
	 */
	/**
	 * 此实现关闭Spring ApplicationContext。 
	 * 
	 */
	@Override
	public void stop() {
		logger.debug("Stopping SpringContextResourceAdapter");
		if (this.applicationContext != null) {
			this.applicationContext.close();
		}
	}


	/**
	 * This implementation always throws a NotSupportedException.
	 */
	/**
	 * 此实现始终抛出NotSupportedException。 
	 * 
	 */
	@Override
	public void endpointActivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec)
			throws ResourceException {

		throw new NotSupportedException("SpringContextResourceAdapter does not support message endpoints");
	}

	/**
	 * This implementation does nothing.
	 */
	/**
	 * 此实现不执行任何操作。 
	 * 
	 */
	@Override
	public void endpointDeactivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec) {
	}

	/**
	 * This implementation always returns {@code null}.
	 */
	/**
	 * 此实现始终返回{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException {
		return null;
	}


	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof SpringContextResourceAdapter &&
				ObjectUtils.nullSafeEquals(getContextConfigLocation(),
						((SpringContextResourceAdapter) other).getContextConfigLocation())));
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(getContextConfigLocation());
	}

}
