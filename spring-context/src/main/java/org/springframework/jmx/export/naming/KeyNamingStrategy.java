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

package org.springframework.jmx.export.naming;

import java.io.IOException;
import java.util.Properties;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * {@code ObjectNamingStrategy} implementation that builds
 * {@code ObjectName} instances from the key used in the
 * "beans" map passed to {@code MBeanExporter}.
 *
 * <p>Can also check object name mappings, given as {@code Properties}
 * or as {@code mappingLocations} of properties files. The key used
 * to look up is the key used in {@code MBeanExporter}'s "beans" map.
 * If no mapping is found for a given key, the key itself is used to
 * build an {@code ObjectName}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setMappings
 * @see #setMappingLocation
 * @see #setMappingLocations
 * @see org.springframework.jmx.export.MBeanExporter#setBeans
 */
/**
 * 通过从传递给{@code  MBeanExporter}的"beans"映射中使用的键构建{@code  ObjectName}实例的{@code  ObjectNamingStrategy}实现。 
 *  <p>还可以检查对象名称映射，以属性文件的{@code  Properties}或{@code  mappingLocations}的形式给出。 
 * 用于查找的密钥是{@code  MBeanExporter}的"beans"映射中使用的密钥。 
 * 如果找不到给定键的映射，则该键本身将用于构建{@code  ObjectName}。 
 *  @author 罗布·哈罗普（Rob Harrop）@author 于尔根·霍勒（Juergen Hoeller）@从1.2起
 */
public class KeyNamingStrategy implements ObjectNamingStrategy, InitializingBean {

	/**
	 * {@code Log} instance for this class.
	 */
	/**
	 * 此类的{@code  Log}实例。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Stores the mappings of bean key to {@code ObjectName}.
	 */
	/**
	 * 存储bean键到{@code  ObjectName}的映射。 
	 * 
	 */
	@Nullable
	private Properties mappings;

	/**
	 * Stores the {@code Resource}s containing properties that should be loaded
	 * into the final merged set of {@code Properties} used for {@code ObjectName}
	 * resolution.
	 */
	/**
	 * 存储包含应加载到用于{@code  ObjectName}解析的{@code  Properties}的最终合并集合中的属性的{@code  Resource}。 
	 * 
	 */
	@Nullable
	private Resource[] mappingLocations;

	/**
	 * Stores the result of merging the {@code mappings} {@code Properties}
	 * with the properties stored in the resources defined by {@code mappingLocations}.
	 */
	/**
	 * 存储将{@code  mappings} {@code  Properties}与由{@code  mappingLocations}定义的资源中存储的属性合并的结果。 
	 * 
	 */
	@Nullable
	private Properties mergedMappings;


	/**
	 * Set local properties, containing object name mappings, e.g. via
	 * the "props" tag in XML bean definitions. These can be considered
	 * defaults, to be overridden by properties loaded from files.
	 */
	/**
	 * 设置包含对象名称映射的本地属性，例如通过XML bean定义中的"props"标签。 
	 * 这些可以被视为默认值，这些默认值将被文件加载的属性所覆盖。 
	 * 
	 */
	public void setMappings(Properties mappings) {
		this.mappings = mappings;
	}

	/**
	 * Set a location of a properties file to be loaded,
	 * containing object name mappings.
	 */
	/**
	 * 设置要加载的属性文件的位置，其中包含对象名称映射。 
	 * 
	 */
	public void setMappingLocation(Resource location) {
		this.mappingLocations = new Resource[] {location};
	}

	/**
	 * Set location of properties files to be loaded,
	 * containing object name mappings.
	 */
	/**
	 * 设置要加载的属性文件的位置，其中包含对象名称映射。 
	 * 
	 */
	public void setMappingLocations(Resource... mappingLocations) {
		this.mappingLocations = mappingLocations;
	}


	/**
	 * Merges the {@code Properties} configured in the {@code mappings} and
	 * {@code mappingLocations} into the final {@code Properties} instance
	 * used for {@code ObjectName} resolution.
	 */
	/**
	 * 将在{@code 映射}和{@code  mappingLocations}中配置的{@code 属性}合并到用于{@code  ObjectName}解析的最终{@code  Properties}实例中。 
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws IOException {
		this.mergedMappings = new Properties();
		CollectionUtils.mergePropertiesIntoMap(this.mappings, this.mergedMappings);

		if (this.mappingLocations != null) {
			for (Resource location : this.mappingLocations) {
				if (logger.isDebugEnabled()) {
					logger.debug("Loading JMX object name mappings file from " + location);
				}
				PropertiesLoaderUtils.fillProperties(this.mergedMappings, location);
			}
		}
	}


	/**
	 * Attempts to retrieve the {@code ObjectName} via the given key, trying to
	 * find a mapped value in the mappings first.
	 */
	/**
	 * 尝试通过给定的键检索{@code  ObjectName}，尝试首先在映射中查找映射的值。 
	 * 
	 */
	@Override
	public ObjectName getObjectName(Object managedBean, @Nullable String beanKey) throws MalformedObjectNameException {
		Assert.notNull(beanKey, "KeyNamingStrategy requires bean key");
		String objectName = null;
		if (this.mergedMappings != null) {
			objectName = this.mergedMappings.getProperty(beanKey);
		}
		if (objectName == null) {
			objectName = beanKey;
		}
		return ObjectNameManager.getInstance(objectName);
	}

}
