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

package org.springframework.jca.context;

import javax.resource.spi.BootstrapContext;
import javax.resource.spi.work.WorkManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.context.ApplicationContext} implementation
 * for a JCA ResourceAdapter. Needs to be initialized with the JCA
 * {@link javax.resource.spi.BootstrapContext}, passing it on to
 * Spring-managed beans that implement {@link BootstrapContextAware}.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see SpringContextResourceAdapter
 * @see BootstrapContextAware
 */
/**
 * JCA ResourceAdapter的{@link  org.springframework.context.ApplicationContext}实现。 
 * 需要使用JCA {@link  javax.resource.spi.BootstrapContext}进行初始化，并将其传递给实现{@link  BootstrapContextAware}的Spring管理的Bean。 
 *  @author  Juergen Hoeller @since 2.5起
 * @see  SpringContextResourceAdapter 
 * @see  BootstrapContextAware
 */
public class ResourceAdapterApplicationContext extends GenericApplicationContext {

	private final BootstrapContext bootstrapContext;


	/**
	 * Create a new ResourceAdapterApplicationContext for the given BootstrapContext.
	 * @param bootstrapContext the JCA BootstrapContext that the ResourceAdapter
	 * has been started with
	 */
	/**
	 * 为给定的BootstrapContext创建一个新的ResourceAdapterApplicationContext。 
	 *  
	 * @param  bootstrapContext启动ResourceAdapter的JCA BootstrapContext
	 */
	public ResourceAdapterApplicationContext(BootstrapContext bootstrapContext) {
		Assert.notNull(bootstrapContext, "BootstrapContext must not be null");
		this.bootstrapContext = bootstrapContext;
	}


	@Override
	protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.addBeanPostProcessor(new BootstrapContextAwareProcessor(this.bootstrapContext));
		beanFactory.ignoreDependencyInterface(BootstrapContextAware.class);
		beanFactory.registerResolvableDependency(BootstrapContext.class, this.bootstrapContext);

		// JCA WorkManager resolved lazily - may not be available.
		beanFactory.registerResolvableDependency(WorkManager.class,
				(ObjectFactory<WorkManager>) this.bootstrapContext::getWorkManager);
	}

}
