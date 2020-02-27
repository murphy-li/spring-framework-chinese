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

package org.springframework.remoting.jaxws;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.WebServiceProvider;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Abstract exporter for JAX-WS services, autodetecting annotated service beans
 * (through the JAX-WS {@link javax.jws.WebService} annotation).
 *
 * <p>Subclasses need to implement the {@link #publishEndpoint} template methods
 * for actual endpoint exposure.
 *
 * @author Juergen Hoeller
 * @since 2.5.5
 * @see javax.jws.WebService
 * @see javax.xml.ws.Endpoint
 * @see SimpleJaxWsServiceExporter
 */
/**
 * JAX-WS服务的抽象导出器，（通过JAX-WS {@link  javax.jws.WebService}注释）自动检测带注释的服务bean。 
 *  <p>子类需要实现{@link  #publishEndpoint}模板方法以实际暴露端点。 
 *  @author  Juergen Hoeller @since 2.5.5 
 * @see  javax.jws.WebService 
 * @see  javax.xml.ws.Endpoint 
 * @see  SimpleJaxWsServiceExporter
 */
public abstract class AbstractJaxWsServiceExporter implements BeanFactoryAware, InitializingBean, DisposableBean {

	@Nullable
	private Map<String, Object> endpointProperties;

	@Nullable
	private Executor executor;

	@Nullable
	private String bindingType;

	@Nullable
	private WebServiceFeature[] endpointFeatures;

	@Nullable
	private ListableBeanFactory beanFactory;

	private final Set<Endpoint> publishedEndpoints = new LinkedHashSet<>();


	/**
	 * Set the property bag for the endpoint, including properties such as
	 * "javax.xml.ws.wsdl.service" or "javax.xml.ws.wsdl.port".
	 * @see javax.xml.ws.Endpoint#setProperties
	 * @see javax.xml.ws.Endpoint#WSDL_SERVICE
	 * @see javax.xml.ws.Endpoint#WSDL_PORT
	 */
	/**
	 * 设置端点的属性包，包括"javax.xml.ws.wsdl.service"或"javax.xml.ws.wsdl.port"之类的属性。 
	 *  
	 * @see  javax.xml.ws.Endpoint＃setProperties 
	 * @see  javax.xml.ws.Endpoint＃WSDL_SERVICE 
	 * @see  javax.xml.ws.Endpoint＃WSDL_PORT
	 */
	public void setEndpointProperties(Map<String, Object> endpointProperties) {
		this.endpointProperties = endpointProperties;
	}

	/**
	 * Set the JDK concurrent executor to use for dispatching incoming requests
	 * to exported service instances.
	 * @see javax.xml.ws.Endpoint#setExecutor
	 */
	/**
	 * 设置JDK并发执行器，以用于将传入的请求调度到导出的服务实例。 
	 *  
	 * @see  javax.xml.ws.Endpoint＃setExecutor
	 */
	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	/**
	 * Specify the binding type to use, overriding the value of
	 * the JAX-WS {@link javax.xml.ws.BindingType} annotation.
	 */
	/**
	 * 指定要使用的绑定类型，以覆盖JAX-WS {@link  javax.xml.ws.BindingType}注解的值。 
	 * 
	 */
	public void setBindingType(String bindingType) {
		this.bindingType = bindingType;
	}

	/**
	 * Specify WebServiceFeature objects (e.g. as inner bean definitions)
	 * to apply to JAX-WS endpoint creation.
	 * @since 4.0
	 */
	/**
	 * 指定WebServiceFeature对象（例如，作为内部bean定义）以应用于JAX-WS端点创建。 
	 *  @始于4.0
	 */
	public void setEndpointFeatures(WebServiceFeature... endpointFeatures) {
		this.endpointFeatures = endpointFeatures;
	}

	/**
	 * Obtains all web service beans and publishes them as JAX-WS endpoints.
	 */
	/**
	 * 获取所有Web服务Bean并将其发布为JAX-WS端点。 
	 * 
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ListableBeanFactory)) {
			throw new IllegalStateException(getClass().getSimpleName() + " requires a ListableBeanFactory");
		}
		this.beanFactory = (ListableBeanFactory) beanFactory;
	}


	/**
	 * Immediately publish all endpoints when fully configured.
	 * @see #publishEndpoints()
	 */
	/**
	 * 完全配置后立即发布所有端点。 
	 *  
	 * @see  #publishEndpoints（）
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		publishEndpoints();
	}

	/**
	 * Publish all {@link javax.jws.WebService} annotated beans in the
	 * containing BeanFactory.
	 * @see #publishEndpoint
	 */
	/**
	 * 在包含的BeanFactory中发布所有{@link  javax.jws.WebService}带注释的bean。 
	 *  
	 * @see  #publishEndpoint
	 */
	public void publishEndpoints() {
		Assert.state(this.beanFactory != null, "No BeanFactory set");

		Set<String> beanNames = new LinkedHashSet<>(this.beanFactory.getBeanDefinitionCount());
		Collections.addAll(beanNames, this.beanFactory.getBeanDefinitionNames());
		if (this.beanFactory instanceof ConfigurableBeanFactory) {
			Collections.addAll(beanNames, ((ConfigurableBeanFactory) this.beanFactory).getSingletonNames());
		}

		for (String beanName : beanNames) {
			try {
				Class<?> type = this.beanFactory.getType(beanName);
				if (type != null && !type.isInterface()) {
					WebService wsAnnotation = type.getAnnotation(WebService.class);
					WebServiceProvider wsProviderAnnotation = type.getAnnotation(WebServiceProvider.class);
					if (wsAnnotation != null || wsProviderAnnotation != null) {
						Endpoint endpoint = createEndpoint(this.beanFactory.getBean(beanName));
						if (this.endpointProperties != null) {
							endpoint.setProperties(this.endpointProperties);
						}
						if (this.executor != null) {
							endpoint.setExecutor(this.executor);
						}
						if (wsAnnotation != null) {
							publishEndpoint(endpoint, wsAnnotation);
						}
						else {
							publishEndpoint(endpoint, wsProviderAnnotation);
						}
						this.publishedEndpoints.add(endpoint);
					}
				}
			}
			catch (CannotLoadBeanClassException ex) {
				// ignore beans where the class is not resolvable
			}
		}
	}

	/**
	 * Create the actual Endpoint instance.
	 * @param bean the service object to wrap
	 * @return the Endpoint instance
	 * @see Endpoint#create(Object)
	 * @see Endpoint#create(String, Object)
	 */
	/**
	 * 创建实际的端点实例。 
	 *  
	 * @param  bean服务对象来包装
	 * @return 端点实例
	 * @see  Endpoint＃create（Object）
	 * @see  Endpoint＃create（String，Object）
	 */
	protected Endpoint createEndpoint(Object bean) {
		return (this.endpointFeatures != null ?
				Endpoint.create(this.bindingType, bean, this.endpointFeatures) :
				Endpoint.create(this.bindingType, bean));
	}


	/**
	 * Actually publish the given endpoint. To be implemented by subclasses.
	 * @param endpoint the JAX-WS Endpoint object
	 * @param annotation the service bean's WebService annotation
	 */
	/**
	 * 实际发布给定的端点。 
	 * 由子类实现。 
	 *  
	 * @param 终结点JAX-WS Endpoint对象
	 * @param 注释服务bean的WebService注释
	 */
	protected abstract void publishEndpoint(Endpoint endpoint, WebService annotation);

	/**
	 * Actually publish the given provider endpoint. To be implemented by subclasses.
	 * @param endpoint the JAX-WS Provider Endpoint object
	 * @param annotation the service bean's WebServiceProvider annotation
	 */
	/**
	 * 实际发布给定的提供者端点。 
	 * 由子类实现。 
	 *  
	 * @param 终结点JAX-WS Provider终结点对象
	 * @param 注释服务bean的WebServiceProvider注释
	 */
	protected abstract void publishEndpoint(Endpoint endpoint, WebServiceProvider annotation);


	/**
	 * Stops all published endpoints, taking the web services offline.
	 */
	/**
	 * 停止所有已发布的端点，使Web服务脱机。 
	 * 
	 */
	@Override
	public void destroy() {
		for (Endpoint endpoint : this.publishedEndpoints) {
			endpoint.stop();
		}
	}

}
