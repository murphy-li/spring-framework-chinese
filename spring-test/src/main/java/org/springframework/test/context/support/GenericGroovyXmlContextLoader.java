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

package org.springframework.test.context.support;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * Concrete implementation of {@link AbstractGenericContextLoader} that reads
 * bean definitions from Groovy scripts <em>and</em> XML configuration files.
 *
 * <p>Default resource locations are detected using the suffixes
 * {@code "-context.xml"} and {@code "Context.groovy"}.
 *
 * @author Sam Brannen
 * @since 4.1
 * @see GroovyBeanDefinitionReader
 * @see GenericXmlContextLoader
 * @see AnnotationConfigContextLoader
 */
/**
 * {@link  AbstractGenericContextLoader}的具体实现，它从Groovy脚本<em>和</ em> XML配置文件中读取bean定义。 
 *  <p>使用后缀{@code "-context.xml"}和{@code "Context.groovy"}检测默认资源位置。 
 *  @author  Sam Brannen @始于4.1 
 * @see  GroovyBeanDefinitionReader 
 * @see  GenericXmlContextLoader 
 * @see  AnnotationConfigContextLoader
 */
public class GenericGroovyXmlContextLoader extends GenericXmlContextLoader {

	/**
	 * Load bean definitions into the supplied {@link GenericApplicationContext context}
	 * from the locations in the supplied {@code MergedContextConfiguration} using a
	 * {@link GroovyBeanDefinitionReader}.
	 * @param context the context into which the bean definitions should be loaded
	 * @param mergedConfig the merged context configuration
	 * @see org.springframework.test.context.support.AbstractGenericContextLoader#loadBeanDefinitions
	 */
	/**
	 * 使用{@link  GroovyBeanDefinitionReader}从提供的{@code  MergedContextConfiguration}中的位置将bean定义加载到提供的{@link  GenericApplicationContext上下文}中。 
	 *  
	 * @param 上下文Bean定义应加载到的上下文
	 * @param  mergedConfig合并的上下文配置
	 * @see  org.springframework.test.context.support.AbstractGenericContextLoader＃loadBeanDefinitions
	 */
	@Override
	protected void loadBeanDefinitions(GenericApplicationContext context, MergedContextConfiguration mergedConfig) {
		new GroovyBeanDefinitionReader(context).loadBeanDefinitions(mergedConfig.getLocations());
	}

	/**
	 * Returns {@code "-context.xml" and "Context.groovy"} in order to
	 * support detection of a default XML config file or Groovy script.
	 */
	/**
	 * 返回{@code "-context.xml"和"Context.groovy"}，以支持检测默认的XML配置文件或Groovy脚本。 
	 * 
	 */
	@Override
	protected String[] getResourceSuffixes() {
		return new String[] { super.getResourceSuffix(), "Context.groovy" };
	}

	/**
	 * {@code GenericGroovyXmlContextLoader} supports both Groovy and XML
	 * resource types for detection of defaults. Consequently, this method
	 * is not supported.
	 * @see #getResourceSuffixes()
	 * @throws UnsupportedOperationException in this implementation
	 */
	/**
	 * {@code  GenericGroovyXmlContextLoader}支持Groovy和XML资源类型以检测默认值。 
	 * 因此，不支持此方法。 
	 *  
	 * @see  #getResourceSuffixes（）
	 * @throws 此实现中的UnsupportedOperationException
	 */
	@Override
	protected String getResourceSuffix() {
		throw new UnsupportedOperationException(
				"GenericGroovyXmlContextLoader does not support the getResourceSuffix() method");
	}

}
