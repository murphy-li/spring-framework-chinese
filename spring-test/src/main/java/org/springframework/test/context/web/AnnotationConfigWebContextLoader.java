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

package org.springframework.test.context.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoaderUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * Concrete implementation of {@link AbstractGenericWebContextLoader} that loads
 * bean definitions from annotated classes.
 *
 * <p>See the Javadoc for
 * {@link org.springframework.test.context.ContextConfiguration @ContextConfiguration}
 * for a definition of <em>annotated class</em>.
 *
 * <p>Note: {@code AnnotationConfigWebContextLoader} supports <em>annotated classes</em>
 * rather than the String-based resource locations defined by the legacy
 * {@link org.springframework.test.context.ContextLoader ContextLoader} API. Thus,
 * although {@code AnnotationConfigWebContextLoader} extends
 * {@code AbstractGenericWebContextLoader}, {@code AnnotationConfigWebContextLoader}
 * does <em>not</em> support any String-based methods defined by
 * {@link org.springframework.test.context.support.AbstractContextLoader
 * AbstractContextLoader} or {@code AbstractGenericWebContextLoader}.
 * Consequently, {@code AnnotationConfigWebContextLoader} should chiefly be
 * considered a {@link org.springframework.test.context.SmartContextLoader SmartContextLoader}
 * rather than a {@link org.springframework.test.context.ContextLoader ContextLoader}.
 *
 * @author Sam Brannen
 * @since 3.2
 * @see #processContextConfiguration(ContextConfigurationAttributes)
 * @see #detectDefaultConfigurationClasses(Class)
 * @see #loadBeanDefinitions(GenericWebApplicationContext, WebMergedContextConfiguration)
 * @see GenericXmlWebContextLoader
 * @see GenericGroovyXmlWebContextLoader
 */
/**
 * {@link  AbstractGenericWebContextLoader}的具体实现可从带注释的类中加载bean定义。 
 *  <p>请参阅Java文档{{@link> org.springframework.test.context.ContextConfiguration @ContextConfiguration}，以获取<em>带注释的类</ em>的定义。 
 *  <p>注意：{@code  AnnotationConfigWebContextLoader}支持<em>带注释的类</ em>，而不是由旧版{@link  org.springframework.test.context.ContextLoader ContextLoader}定义的基于字符串的资源位置。 
 *  API。 
 * 因此，尽管{@code  AnnotationConfigWebContextLoader}扩展了{@code  AbstractGenericWebContextLoader}，但是{@code  AnnotationConfigWebContextLoader}并不支持{@link  org定义的任何基于字符串的方法。 
 *  springframework.test.context.support.AbstractContextLoader AbstractContextLoader}或{@code  AbstractGenericWebContextLoader}。 
 * 因此，{<@code> AnnotationConfigWebContextLoader}应该主要被视为{@link  org.springframework.test.context.SmartContextLoader SmartContextLoader}而不是{@link  org.springframework.test.context.ContextLoader ContextLoader}。 
 *  @author  Sam Brannen @since 3.2 
 * @see  #processContextConfiguration（ContextConfigurationAttributes）
 * @see  #detectDefaultConfigurationClasses（Class）
 * @see  #loadBeanDefinitions（GenericWebApplicationContext，WebMergedContextConfiguration）
 * @see  GenericXmlWebContextLoader 
 * @see  GenericGroovyXmlWebContextLoader
 */
public class AnnotationConfigWebContextLoader extends AbstractGenericWebContextLoader {

	private static final Log logger = LogFactory.getLog(AnnotationConfigWebContextLoader.class);


	// SmartContextLoader

	/**
	 * Process <em>annotated classes</em> in the supplied {@link ContextConfigurationAttributes}.
	 * <p>If the <em>annotated classes</em> are {@code null} or empty and
	 * {@link #isGenerateDefaultLocations()} returns {@code true}, this
	 * {@code SmartContextLoader} will attempt to {@linkplain
	 * #detectDefaultConfigurationClasses detect default configuration classes}.
	 * If defaults are detected they will be
	 * {@linkplain ContextConfigurationAttributes#setClasses(Class[]) set} in the
	 * supplied configuration attributes. Otherwise, properties in the supplied
	 * configuration attributes will not be modified.
	 * @param configAttributes the context configuration attributes to process
	 * @see org.springframework.test.context.SmartContextLoader#processContextConfiguration(ContextConfigurationAttributes)
	 * @see #isGenerateDefaultLocations()
	 * @see #detectDefaultConfigurationClasses(Class)
	 */
	/**
	 * 在提供的{@link  ContextConfigurationAttributes}中处理<em>带注释的类</ em>。 
	 *  <p>如果<em>带注释的类</ em>为{@code  null}或为空，并且{@link  #isGenerateDefaultLocations（）}返回{@code  true}，则此{@code  SmartContextLoader}将尝试{@link 纯#detectDefaultConfigurationClasses检测默认配置类}。 
	 * 如果检测到默认值，将在提供的配置属性中设置{@link  plain ContextConfigurationAttributes＃setClasses（Class []）set}。 
	 * 否则，将不会修改提供的配置属性中的属性。 
	 *  
	 * @param  configAttributes上下文配置属性以处理
	 * @see  org.springframework.test.context.SmartContextLoader＃processContextConfiguration（ContextConfigurationAttributes）
	 * @see  #isGenerateDefaultLocations（）
	 * @see  #detectDefaultConfigurationClasses（Class）
	 */
	@Override
	public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
		if (!configAttributes.hasClasses() && isGenerateDefaultLocations()) {
			configAttributes.setClasses(detectDefaultConfigurationClasses(configAttributes.getDeclaringClass()));
		}
	}

	/**
	 * Detect the default configuration classes for the supplied test class.
	 * <p>The default implementation simply delegates to
	 * {@link AnnotationConfigContextLoaderUtils#detectDefaultConfigurationClasses(Class)}.
	 * @param declaringClass the test class that declared {@code @ContextConfiguration}
	 * @return an array of default configuration classes, potentially empty but never {@code null}
	 * @see AnnotationConfigContextLoaderUtils
	 */
	/**
	 * 检测提供的测试类的默认配置类。 
	 *  <p>默认实现只是将其委托给{@link  AnnotationConfigContextLoaderUtils＃detectDefaultConfigurationClasses（Class）}。 
	 *  
	 * @param  declaringClass声明了{@code  @ContextConfiguration}的测试类
	 * @return 一组默认配置类，可能为空，但永远不会为{@code  null} 
	 * @see  AnnotationConfigContextLoaderUtils
	 */
	protected Class<?>[] detectDefaultConfigurationClasses(Class<?> declaringClass) {
		return AnnotationConfigContextLoaderUtils.detectDefaultConfigurationClasses(declaringClass);
	}


	// AbstractContextLoader

	/**
	 * {@code AnnotationConfigWebContextLoader} should be used as a
	 * {@link org.springframework.test.context.SmartContextLoader SmartContextLoader},
	 * not as a legacy {@link org.springframework.test.context.ContextLoader ContextLoader}.
	 * Consequently, this method is not supported.
	 * @throws UnsupportedOperationException in this implementation
	 * @see org.springframework.test.context.support.AbstractContextLoader#modifyLocations
	 */
	/**
	 * {@code  AnnotationConfigWebContextLoader}应该用作{@link  org.springframework.test.context.SmartContextLoader SmartContextLoader}，而不应用作旧版{@link  org.springframework.test.context.ContextLoader ContextLoader}。 
	 * 因此，不支持此方法。 
	 *  
	 * @throws 此实现中的UnsupportedOperationException 
	 * @see  org.springframework.test.context.support.AbstractContextLoader＃modifyLocations
	 */
	@Override
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		throw new UnsupportedOperationException(
				"AnnotationConfigWebContextLoader does not support the modifyLocations(Class, String...) method");
	}

	/**
	 * {@code AnnotationConfigWebContextLoader} should be used as a
	 * {@link org.springframework.test.context.SmartContextLoader SmartContextLoader},
	 * not as a legacy {@link org.springframework.test.context.ContextLoader ContextLoader}.
	 * Consequently, this method is not supported.
	 * @throws UnsupportedOperationException in this implementation
	 * @see org.springframework.test.context.support.AbstractContextLoader#generateDefaultLocations
	 */
	/**
	 * {@code  AnnotationConfigWebContextLoader}应该用作{@link  org.springframework.test.context.SmartContextLoader SmartContextLoader}，而不应用作旧版{@link  org.springframework.test.context.ContextLoader ContextLoader}。 
	 * 因此，不支持此方法。 
	 *  
	 * @throws 此实现中的UnsupportedOperationException 
	 * @see  org.springframework.test.context.support.AbstractContextLoader＃generateDefaultLocations
	 */
	@Override
	protected String[] generateDefaultLocations(Class<?> clazz) {
		throw new UnsupportedOperationException(
				"AnnotationConfigWebContextLoader does not support the generateDefaultLocations(Class) method");
	}

	/**
	 * {@code AnnotationConfigWebContextLoader} should be used as a
	 * {@link org.springframework.test.context.SmartContextLoader SmartContextLoader},
	 * not as a legacy {@link org.springframework.test.context.ContextLoader ContextLoader}.
	 * Consequently, this method is not supported.
	 * @throws UnsupportedOperationException in this implementation
	 * @see org.springframework.test.context.support.AbstractContextLoader#getResourceSuffix
	 */
	/**
	 * {@code  AnnotationConfigWebContextLoader}应该用作{@link  org.springframework.test.context.SmartContextLoader SmartContextLoader}，而不应用作旧版{@link  org.springframework.test.context.ContextLoader ContextLoader}。 
	 * 因此，不支持此方法。 
	 *  
	 * @throws 此实现中的UnsupportedOperationException 
	 * @see  org.springframework.test.context.support.AbstractContextLoader＃getResourceSuffix
	 */
	@Override
	protected String getResourceSuffix() {
		throw new UnsupportedOperationException(
				"AnnotationConfigWebContextLoader does not support the getResourceSuffix() method");
	}


	// AbstractGenericWebContextLoader

	/**
	 * Register classes in the supplied {@linkplain GenericWebApplicationContext context}
	 * from the classes in the supplied {@link WebMergedContextConfiguration}.
	 * <p>Each class must represent an <em>annotated class</em>. An
	 * {@link AnnotatedBeanDefinitionReader} is used to register the appropriate
	 * bean definitions.
	 * @param context the context in which the annotated classes should be registered
	 * @param webMergedConfig the merged configuration from which the classes should be retrieved
	 * @see AbstractGenericWebContextLoader#loadBeanDefinitions
	 */
	/**
	 * 从提供的{@link  WebMergedContextConfiguration}中的类中注册提供的{@link  plain GenericWebApplicationContext上下文}中的类。 
	 *  <p>每个类都必须代表一个<em>带注释的类</ em>。 
	 *  {@link  AnnotatedBeanDefinitionReader}用于注册适当的bean定义。 
	 *  
	 * @param 上下文，应在其中注册带注释的类的上下文。 
	 * 
	 */
	@Override
	protected void loadBeanDefinitions(
			GenericWebApplicationContext context, WebMergedContextConfiguration webMergedConfig) {

		Class<?>[] annotatedClasses = webMergedConfig.getClasses();
		if (logger.isDebugEnabled()) {
			logger.debug("Registering annotated classes: " + ObjectUtils.nullSafeToString(annotatedClasses));
		}
		new AnnotatedBeanDefinitionReader(context).register(annotatedClasses);
	}

	/**
	 * Ensure that the supplied {@link WebMergedContextConfiguration} does not
	 * contain {@link MergedContextConfiguration#getLocations() locations}.
	 * @since 4.0.4
	 * @see AbstractGenericWebContextLoader#validateMergedContextConfiguration
	 */
	/**
	 * 确保提供的{@link  WebMergedContextConfiguration}不包含{@link  MergedContextConfiguration＃getLocations（）位置}。 
	 *  @since 4.0.4 
	 * @see  AbstractGenericWebContextLoader＃validateMergedContextConfiguration
	 */
	@Override
	protected void validateMergedContextConfiguration(WebMergedContextConfiguration webMergedConfig) {
		if (webMergedConfig.hasLocations()) {
			String msg = String.format("Test class [%s] has been configured with @ContextConfiguration's 'locations' " +
							"(or 'value') attribute %s, but %s does not support resource locations.",
					webMergedConfig.getTestClass().getName(),
					ObjectUtils.nullSafeToString(webMergedConfig.getLocations()), getClass().getSimpleName());
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

}
