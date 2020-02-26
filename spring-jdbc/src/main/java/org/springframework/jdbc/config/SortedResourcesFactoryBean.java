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

package org.springframework.jdbc.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 * {@link FactoryBean} implementation that takes a list of location Strings
 * and creates a sorted array of {@link Resource} instances.
 *
 * @author Dave Syer
 * @author Juergen Hoeller
 * @author Christian Dupuis
 * @since 3.0
 */
/**
 * {@link  FactoryBean}实现，该实现采用位置字符串列表并创建{@link  Resource}实例的排序数组。 
 *  @author  Dave Syer @author 于尔根·霍勒（Juergen Hoeller）@author  Christian Dupuis @since 3.0
 */
public class SortedResourcesFactoryBean extends AbstractFactoryBean<Resource[]> implements ResourceLoaderAware {

	private final List<String> locations;

	private ResourcePatternResolver resourcePatternResolver;


	public SortedResourcesFactoryBean(List<String> locations) {
		this.locations = locations;
		this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
	}

	public SortedResourcesFactoryBean(ResourceLoader resourceLoader, List<String> locations) {
		this.locations = locations;
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
	}


	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
	}


	@Override
	public Class<? extends Resource[]> getObjectType() {
		return Resource[].class;
	}

	@Override
	protected Resource[] createInstance() throws Exception {
		List<Resource> scripts = new ArrayList<>();
		for (String location : this.locations) {
			List<Resource> resources = new ArrayList<>(
					Arrays.asList(this.resourcePatternResolver.getResources(location)));
			resources.sort((r1, r2) -> {
				try {
					return r1.getURL().toString().compareTo(r2.getURL().toString());
				}
				catch (IOException ex) {
					return 0;
				}
			});
			scripts.addAll(resources);
		}
		return scripts.toArray(new Resource[0]);
	}

}
