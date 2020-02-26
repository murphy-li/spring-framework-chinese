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

package org.springframework.test.context.web;

import java.util.Set;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.style.ToStringCreator;
import org.springframework.lang.Nullable;
import org.springframework.test.context.CacheAwareContextLoaderDelegate;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * {@code WebMergedContextConfiguration} encapsulates the <em>merged</em>
 * context configuration declared on a test class and all of its superclasses
 * via {@link org.springframework.test.context.ContextConfiguration @ContextConfiguration},
 * {@link WebAppConfiguration @WebAppConfiguration}, and
 * {@link org.springframework.test.context.ActiveProfiles @ActiveProfiles}.
 *
 * <p>{@code WebMergedContextConfiguration} extends the contract of
 * {@link MergedContextConfiguration} by adding support for the {@link
 * #getResourceBasePath() resource base path} configured via {@code @WebAppConfiguration}.
 * This allows the {@link org.springframework.test.context.TestContext TestContext}
 * to properly cache the corresponding {@link
 * org.springframework.web.context.WebApplicationContext WebApplicationContext}
 * that was loaded using properties of this {@code WebMergedContextConfiguration}.
 *
 * @author Sam Brannen
 * @since 3.2
 * @see WebAppConfiguration
 * @see MergedContextConfiguration
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.ActiveProfiles
 * @see org.springframework.test.context.ContextConfigurationAttributes
 * @see org.springframework.test.context.SmartContextLoader#loadContext(MergedContextConfiguration)
 */
/**
 * {@code  WebMergedContextConfiguration}封装了在测试类及其所有超类上通过{@link  org.springframework.test.context.ContextConfiguration @ContextConfiguration}，{@link  WebAppConfiguration @WebAppConfiguration}和{@link  org.springframework.test.context.ActiveProfiles @ActiveProfiles}。 
 *  <p> {<@@code> WebMergedContextConfiguration}通过添加对通过{@code  @WebAppConfiguration}配置的{@link  #getResourceBasePath（）资源基础路径}的支持，扩展了{@link  MergedContextConfiguration}的合同。 
 * 这允许{@link  org.springframework.test.context.TestContext TestContext}正确缓存使用该{<@的属性加载的相应的{@link  org.springframework.web.context.WebApplicationContext WebApplicationContext}代码> WebMergedContextConfiguration}。 
 *  @author  Sam Brannen @自3.2起
 * @see  WebAppConfiguration 
 * @see  MergedContextConfiguration 
 * @see  org.springframework.test.context.ContextConfiguration 
 * @see  org.springframework.test.context.ActiveProfiles 
 * @see  org .springframework.test.context.ContextConfigurationAttributes 
 * @see  org.springframework.test.context.SmartContextLoader＃loadContext（MergedContextConfiguration）
 */
public class WebMergedContextConfiguration extends MergedContextConfiguration {

	private static final long serialVersionUID = 7323361588604247458L;

	private final String resourceBasePath;


	/**
	 * Create a new {@code WebMergedContextConfiguration} instance by copying
	 * all properties from the supplied {@code MergedContextConfiguration}.
	 * <p>If an <em>empty</em> value is supplied for the {@code resourceBasePath}
	 * an empty string will be used.
	 * @param resourceBasePath the resource path to the root directory of the web application
	 * @since 4.1
	 */
	/**
	 * 通过复制提供的{@code  MergedContextConfiguration}中的所有属性，创建一个新的{@code  WebMergedContextConfiguration}实例。 
	 *  <p>如果为{@code  resourceBasePath}提供了<em> empty </ em>值，则将使用空字符串。 
	 *  
	 * @param  resourceBasePath Web应用程序根目录的资源路径，自4.1起
	 */
	public WebMergedContextConfiguration(MergedContextConfiguration mergedConfig, String resourceBasePath) {
		super(mergedConfig);
		this.resourceBasePath = !StringUtils.hasText(resourceBasePath) ? "" : resourceBasePath;
	}

	/**
	 * Create a new {@code WebMergedContextConfiguration} instance for the
	 * supplied parameters.
	 * <p>If a {@code null} value is supplied for {@code locations},
	 * {@code classes}, {@code activeProfiles}, {@code propertySourceLocations},
	 * or {@code propertySourceProperties} an empty array will be stored instead.
	 * If a {@code null} value is supplied for the
	 * {@code contextInitializerClasses} an empty set will be stored instead.
	 * If an <em>empty</em> value is supplied for the {@code resourceBasePath}
	 * an empty string will be used. Furthermore, active profiles will be sorted,
	 * and duplicate profiles will be removed.
	 * @param testClass the test class for which the configuration was merged
	 * @param locations the merged resource locations
	 * @param classes the merged annotated classes
	 * @param contextInitializerClasses the merged context initializer classes
	 * @param activeProfiles the merged active bean definition profiles
	 * @param propertySourceLocations the merged {@code PropertySource} locations
	 * @param propertySourceProperties the merged {@code PropertySource} properties
	 * @param resourceBasePath the resource path to the root directory of the web application
	 * @param contextLoader the resolved {@code ContextLoader}
	 * @param cacheAwareContextLoaderDelegate a cache-aware context loader
	 * delegate with which to retrieve the parent context
	 * @param parent the parent configuration or {@code null} if there is no parent
	 * @since 4.1
	 */
	/**
	 * 为提供的参数创建一个新的{@code  WebMergedContextConfiguration}实例。 
	 *  <p>如果为{@code 位置}，{<@code>类}，{<@code> activeProfiles}，{<@code> propertySourceLocations}或{ @code  propertySourceProperties}将会存储一个空数组。 
	 * 如果为{@code  contextInitializerClasses}提供了一个{@code  null}值，则会存储一个空集。 
	 * 如果为{@code  resourceBasePath}提供了<em> empty </ em>值，则将使用空字符串。 
	 * 此外，将对活动配置文件进行排序，并删除重复的配置文件。 
	 *  
	 * @param  testClass为其配置合并的测试类
	 * @param 位置合并资源的位置
	 * @param 合并注释的类的类
	 * @param  contextInitializerClasses合并上下文初始化器的类
	 * @param  activeProfiles合并的活动类Bean定义配置文件
	 * @param  propertySourceLocations合并的{@code  PropertySource}位置
	 * @param  propertySourceProperties合并的{@code  PropertySource}属性
	 * @param  resourceBasePath Web应用程序根目录的资源路径
	 * @param  contextLoader已解决的{@code  ContextLoader} 
	 * @param  cacheAwareContextLoaderDelegate一个可感知缓存的上下文加载器委托，通过该委托检索父上下文
	 * @param 父配置或{@code  null}从4.1开始没有父母
	 */
	public WebMergedContextConfiguration(Class<?> testClass, @Nullable String[] locations, @Nullable Class<?>[] classes,
			@Nullable Set<Class<? extends ApplicationContextInitializer<?>>> contextInitializerClasses,
			@Nullable String[] activeProfiles, @Nullable String[] propertySourceLocations, @Nullable String[] propertySourceProperties,
			String resourceBasePath, ContextLoader contextLoader,
			CacheAwareContextLoaderDelegate cacheAwareContextLoaderDelegate, @Nullable MergedContextConfiguration parent) {

		this(testClass, locations, classes, contextInitializerClasses, activeProfiles, propertySourceLocations,
			propertySourceProperties, null, resourceBasePath, contextLoader, cacheAwareContextLoaderDelegate, parent);
	}

	/**
	 * Create a new {@code WebMergedContextConfiguration} instance for the
	 * supplied parameters.
	 * <p>If a {@code null} value is supplied for {@code locations},
	 * {@code classes}, {@code activeProfiles}, {@code propertySourceLocations},
	 * or {@code propertySourceProperties} an empty array will be stored instead.
	 * If a {@code null} value is supplied for {@code contextInitializerClasses}
	 * or {@code contextCustomizers}, an empty set will be stored instead.
	 * If an <em>empty</em> value is supplied for the {@code resourceBasePath}
	 * an empty string will be used. Furthermore, active profiles will be sorted,
	 * and duplicate profiles will be removed.
	 * @param testClass the test class for which the configuration was merged
	 * @param locations the merged context resource locations
	 * @param classes the merged annotated classes
	 * @param contextInitializerClasses the merged context initializer classes
	 * @param activeProfiles the merged active bean definition profiles
	 * @param propertySourceLocations the merged {@code PropertySource} locations
	 * @param propertySourceProperties the merged {@code PropertySource} properties
	 * @param contextCustomizers the context customizers
	 * @param resourceBasePath the resource path to the root directory of the web application
	 * @param contextLoader the resolved {@code ContextLoader}
	 * @param cacheAwareContextLoaderDelegate a cache-aware context loader
	 * delegate with which to retrieve the parent context
	 * @param parent the parent configuration or {@code null} if there is no parent
	 * @since 4.3
	 */
	/**
	 * 为提供的参数创建一个新的{@code  WebMergedContextConfiguration}实例。 
	 *  <p>如果为{@code 位置}，{<@code>类}，{<@code> activeProfiles}，{<@code> propertySourceLocations}或{ @code  propertySourceProperties}将会存储一个空数组。 
	 * 如果为{@code  contextInitializerClasses}或{@code  contextCustomizers}提供了一个{@code  null}值，则将存储一个空集。 
	 * 如果为{@code  resourceBasePath}提供了<em> empty </ em>值，则将使用空字符串。 
	 * 此外，将对活动配置文件进行排序，并删除重复的配置文件。 
	 *  
	 * @param  testClass为其配置合并的测试类
	 * @param 位置合并上下文资源的位置
	 * @param 合并注释的类的类
	 * @param  contextInitializerClasses合并上下文初始化器的类
	 * @param  activeProfiles合并的活动的bean定义配置文件
	 * @param  propertySourceLocations合并的{@code  PropertySource}位置
	 * @param  propertySourceProperties合并的{@code  PropertySource}属性
	 * @param  contextCustomizers上下文定制器
	 * @param  resourceBasePath资源路径已解析的{@code  ContextLoader} 
	 * @param  cacheAwareContextLoaderDelegate一个可感知缓存的上下文加载器委托，可用来检索父上下文
	 * @param 父配置或父配置，将其保存到Web应用程序的根目录
	 * @param  contextLoader {@code  null}，如果没有父代@4.3起
	 */
	public WebMergedContextConfiguration(Class<?> testClass, @Nullable String[] locations, @Nullable Class<?>[] classes,
			@Nullable Set<Class<? extends ApplicationContextInitializer<?>>> contextInitializerClasses,
			@Nullable String[] activeProfiles, @Nullable String[] propertySourceLocations, @Nullable String[] propertySourceProperties,
			@Nullable Set<ContextCustomizer> contextCustomizers, String resourceBasePath, ContextLoader contextLoader,
			CacheAwareContextLoaderDelegate cacheAwareContextLoaderDelegate, @Nullable MergedContextConfiguration parent) {

		super(testClass, locations, classes, contextInitializerClasses, activeProfiles, propertySourceLocations,
			propertySourceProperties, contextCustomizers, contextLoader, cacheAwareContextLoaderDelegate, parent);

		this.resourceBasePath = (StringUtils.hasText(resourceBasePath) ? resourceBasePath : "");
	}

	/**
	 * Get the resource path to the root directory of the web application for the
	 * {@linkplain #getTestClass() test class}, configured via {@code @WebAppConfiguration}.
	 * @see WebAppConfiguration
	 */
	/**
	 * 获取通过{@code  @WebAppConfiguration}配置的{@link  plain #getTestClass（）测试类}的Web应用程序根目录的资源路径。 
	 *  
	 * @see  WebAppConfiguration
	 */
	public String getResourceBasePath() {
		return this.resourceBasePath;
	}


	/**
	 * Determine if the supplied object is equal to this {@code WebMergedContextConfiguration}
	 * instance by comparing both object's {@linkplain #getLocations() locations},
	 * {@linkplain #getClasses() annotated classes},
	 * {@linkplain #getContextInitializerClasses() context initializer classes},
	 * {@linkplain #getActiveProfiles() active profiles},
	 * {@linkplain #getResourceBasePath() resource base path},
	 * {@linkplain #getParent() parents}, and the fully qualified names of their
	 * {@link #getContextLoader() ContextLoaders}.
	 */
	/**
	 * 通过比较两个对象的{@link  plain #getLocations（）位置}，{<@link> plain #getClasses（）注释的类}，{<来确定提供的对象是否与此{@code  WebMergedContextConfiguration}实例相等。 
	 *  @link> plain #getContextInitializerClasses（）上下文初始化程序类}，{<@link> plain #getActiveProfiles（）活动配置文件}，{<@link> plain #getResourceBasePath（）资源基本路径}，{<@link> plain #getParent （）parent}，以及其{@link  #getContextLoader（）ContextLoaders}的完全限定名称。 
	 * 
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (super.equals(other) &&
				this.resourceBasePath.equals(((WebMergedContextConfiguration) other).resourceBasePath)));
	}

	/**
	 * Generate a unique hash code for all properties of this
	 * {@code WebMergedContextConfiguration} excluding the
	 * {@linkplain #getTestClass() test class}.
	 */
	/**
	 * 为此{@code  WebMergedContextConfiguration}的所有属性生成唯一的哈希码，但不包括{@link  plain #getTestClass（）测试类}。 
	 * 
	 */
	@Override
	public int hashCode() {
		return (31 * super.hashCode() + this.resourceBasePath.hashCode());
	}

	/**
	 * Provide a String representation of the {@linkplain #getTestClass() test class},
	 * {@linkplain #getLocations() locations}, {@linkplain #getClasses() annotated classes},
	 * {@linkplain #getContextInitializerClasses() context initializer classes},
	 * {@linkplain #getActiveProfiles() active profiles},
	 * {@linkplain #getPropertySourceLocations() property source locations},
	 * {@linkplain #getPropertySourceProperties() property source properties},
	 * {@linkplain #getContextCustomizers() context customizers},
	 * {@linkplain #getResourceBasePath() resource base path}, the name of the
	 * {@link #getContextLoader() ContextLoader}, and the
	 * {@linkplain #getParent() parent configuration}.
	 */
	/**
	 * 提供{@link  plain #getTestClass（）测试类}，{<@link> plain #getLocations（）位置}，{<@link> plain #getClasses（）注释类}，{<@link> plain #getContextInitializerClasses（）上下文初始化程序类}，{<@link> plain #getActiveProfiles（）活动配置文件}，{<@link> plain #getPropertySourceLocations（）属性源位置}，{<@link> plain #getPropertySourceProperties（ ）属性源属性}，{<@link> plain #getContextCustomizers（）上下文定制器}，{<@link> plain #getResourceBasePath（）资源基路径}，{<@link> #getContextLoader（）ContextLoader} ，以及{@link  plain #getParent（）父级配置}。 
	 * 
	 */
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("testClass", getTestClass())
				.append("locations", ObjectUtils.nullSafeToString(getLocations()))
				.append("classes", ObjectUtils.nullSafeToString(getClasses()))
				.append("contextInitializerClasses", ObjectUtils.nullSafeToString(getContextInitializerClasses()))
				.append("activeProfiles", ObjectUtils.nullSafeToString(getActiveProfiles()))
				.append("propertySourceLocations", ObjectUtils.nullSafeToString(getPropertySourceLocations()))
				.append("propertySourceProperties", ObjectUtils.nullSafeToString(getPropertySourceProperties()))
				.append("contextCustomizers", getContextCustomizers())
				.append("resourceBasePath", getResourceBasePath())
				.append("contextLoader", nullSafeClassName(getContextLoader()))
				.append("parent", getParent())
				.toString();
	}

}
