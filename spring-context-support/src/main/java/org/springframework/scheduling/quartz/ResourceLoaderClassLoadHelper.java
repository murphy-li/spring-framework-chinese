/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.scheduling.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.spi.ClassLoadHelper;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Wrapper that adapts from the Quartz {@link ClassLoadHelper} interface
 * onto Spring's {@link ResourceLoader} interface. Used by default when
 * the SchedulerFactoryBean runs in a Spring ApplicationContext.
 *
 * @author Juergen Hoeller
 * @since 2.5.5
 * @see SchedulerFactoryBean#setApplicationContext
 */
/**
 * 从Quartz {@link  ClassLoadHelper}接口改编为Spring的{@link  ResourceLoader}接口的包装器。 
 * 当SchedulerFactoryBean在Spring ApplicationContext中运行时，默认情况下使用。 
 *  @author  Juergen Hoeller @2.5.5起
 * @see  SchedulerFactoryBean＃setApplicationContext
 */
public class ResourceLoaderClassLoadHelper implements ClassLoadHelper {

	protected static final Log logger = LogFactory.getLog(ResourceLoaderClassLoadHelper.class);

	@Nullable
	private ResourceLoader resourceLoader;


	/**
	 * Create a new ResourceLoaderClassLoadHelper for the default
	 * ResourceLoader.
	 * @see SchedulerFactoryBean#getConfigTimeResourceLoader()
	 */
	/**
	 * 为默认的ResourceLoader创建一个新的ResourceLoaderClassLoadHelper。 
	 *  
	 * @see  SchedulerFactoryBean＃getConfigTimeResourceLoader（）
	 */
	public ResourceLoaderClassLoadHelper() {
	}

	/**
	 * Create a new ResourceLoaderClassLoadHelper for the given ResourceLoader.
	 * @param resourceLoader the ResourceLoader to delegate to
	 */
	/**
	 * 为给定的ResourceLoader创建一个新的ResourceLoaderClassLoadHelper。 
	 *  
	 * @param  resourceLoader委托给ResourceLoader的资源
	 */
	public ResourceLoaderClassLoadHelper(@Nullable ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


	@Override
	public void initialize() {
		if (this.resourceLoader == null) {
			this.resourceLoader = SchedulerFactoryBean.getConfigTimeResourceLoader();
			if (this.resourceLoader == null) {
				this.resourceLoader = new DefaultResourceLoader();
			}
		}
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Assert.state(this.resourceLoader != null, "ResourceLoaderClassLoadHelper not initialized");
		return ClassUtils.forName(name, this.resourceLoader.getClassLoader());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<? extends T> loadClass(String name, Class<T> clazz) throws ClassNotFoundException {
		return (Class<? extends T>) loadClass(name);
	}

	@Override
	@Nullable
	public URL getResource(String name) {
		Assert.state(this.resourceLoader != null, "ResourceLoaderClassLoadHelper not initialized");
		Resource resource = this.resourceLoader.getResource(name);
		if (resource.exists()) {
			try {
				return resource.getURL();
			}
			catch (IOException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Could not load " + resource);
				}
				return null;
			}
		}
		else {
			return getClassLoader().getResource(name);
		}
	}

	@Override
	@Nullable
	public InputStream getResourceAsStream(String name) {
		Assert.state(this.resourceLoader != null, "ResourceLoaderClassLoadHelper not initialized");
		Resource resource = this.resourceLoader.getResource(name);
		if (resource.exists()) {
			try {
				return resource.getInputStream();
			}
			catch (IOException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Could not load " + resource);
				}
				return null;
			}
		}
		else {
			return getClassLoader().getResourceAsStream(name);
		}
	}

	@Override
	public ClassLoader getClassLoader() {
		Assert.state(this.resourceLoader != null, "ResourceLoaderClassLoadHelper not initialized");
		ClassLoader classLoader = this.resourceLoader.getClassLoader();
		Assert.state(classLoader != null, "No ClassLoader");
		return classLoader;
	}

}
