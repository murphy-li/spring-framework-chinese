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

package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * Simple interface for bean definition readers.
 * Specifies load methods with Resource and String location parameters.
 *
 * <p>Concrete bean definition readers can of course add additional
 * load and register methods for bean definitions, specific to
 * their bean definition format.
 *
 * <p>Note that a bean definition reader does not have to implement
 * this interface. It only serves as suggestion for bean definition
 * readers that want to follow standard naming conventions.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see org.springframework.core.io.Resource
 */
/**
 * Bean定义阅读器的简单界面。 
 * 使用资源和字符串位置参数指定加载方法。 
 *  <p>具体的Bean定义阅读器当然可以为Bean定义添加特定于其Bean定义格式的其他加载和注册方法。 
 *  <p>请注意，bean定义读取器不必实现此接口。 
 * 它仅对希望遵循标准命名约定的Bean定义读者提供建议。 
 *  @author  Juergen Hoeller @自1.1起
 * @see  org.springframework.core.io.Resource
 */
public interface BeanDefinitionReader {

	/**
	 * Return the bean factory to register the bean definitions with.
	 * <p>The factory is exposed through the BeanDefinitionRegistry interface,
	 * encapsulating the methods that are relevant for bean definition handling.
	 */
	/**
	 * 返回Bean工厂以向其注册Bean定义。 
	 *  <p>工厂通过BeanDefinitionRegistry接口公开，封装了与Bean定义处理相关的方法。 
	 * 
	 */
	BeanDefinitionRegistry getRegistry();

	/**
	 * Return the resource loader to use for resource locations.
	 * Can be checked for the <b>ResourcePatternResolver</b> interface and cast
	 * accordingly, for loading multiple resources for a given resource pattern.
	 * <p>A {@code null} return value suggests that absolute resource loading
	 * is not available for this bean definition reader.
	 * <p>This is mainly meant to be used for importing further resources
	 * from within a bean definition resource, for example via the "import"
	 * tag in XML bean definitions. It is recommended, however, to apply
	 * such imports relative to the defining resource; only explicit full
	 * resource locations will trigger absolute resource loading.
	 * <p>There is also a {@code loadBeanDefinitions(String)} method available,
	 * for loading bean definitions from a resource location (or location pattern).
	 * This is a convenience to avoid explicit ResourceLoader handling.
	 * @see #loadBeanDefinitions(String)
	 * @see org.springframework.core.io.support.ResourcePatternResolver
	 */
	/**
	 * 返回资源加载器以用于资源位置。 
	 * 可以检查<b> ResourcePatternResolver </ b>接口并进行相应的转换，以针对给定的资源模式加载多个资源。 
	 *  <p>返回{@code  null}的值表明此bean定义阅读器无法使用绝对资源加载。 
	 *  <p>这主要用于从bean定义资源中导入其他资源，例如，通过XML bean定义中的"import"标签。 
	 * 但是，建议相对于定义资源应用此类导入； 
	 * 只有明确的完整资源位置才会触发绝对资源加载。 
	 *  <p>还有一个{@code  loadBeanDefinitions（String）}方法可用于从资源位置（或位置模式）加载Bean定义。 
	 * 这是避免显式ResourceLoader处理的便利。 
	 *  
	 * @see  #loadBeanDefinitions（String）
	 * @see  org.springframework.core.io.support.ResourcePatternResolver
	 */
	@Nullable
	ResourceLoader getResourceLoader();

	/**
	 * Return the class loader to use for bean classes.
	 * <p>{@code null} suggests to not load bean classes eagerly
	 * but rather to just register bean definitions with class names,
	 * with the corresponding Classes to be resolved later (or never).
	 */
	/**
	 * 返回用于Bean类的类加载器。 
	 *  <p> {<@code> null}建议不要急于加载Bean类，而只是用类名注册Bean定义，并在以后（或永不解决）相应的类。 
	 * 
	 */
	@Nullable
	ClassLoader getBeanClassLoader();

	/**
	 * Return the BeanNameGenerator to use for anonymous beans
	 * (without explicit bean name specified).
	 */
	/**
	 * 返回BeanNameGenerator以用于匿名Bean（未指定显式Bean名称）。 
	 * 
	 */
	BeanNameGenerator getBeanNameGenerator();


	/**
	 * Load bean definitions from the specified resource.
	 * @param resource the resource descriptor
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	/**
	 * 从指定的资源加载bean定义。 
	 *  
	 * @param 资源资源描述符
	 * @return 找到的bean定义数
	 * @throws  BeanDefinitionStoreException，如果发生加载或解析错误
	 */
	int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

	/**
	 * Load bean definitions from the specified resources.
	 * @param resources the resource descriptors
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	/**
	 * 从指定的资源加载bean定义。 
	 *  
	 * @param 资源资源描述符
	 * @return 找到的bean定义数
	 * @throws  BeanDefinitionStoreException，如果发生加载或解析错误
	 */
	int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

	/**
	 * Load bean definitions from the specified resource location.
	 * <p>The location can also be a location pattern, provided that the
	 * ResourceLoader of this bean definition reader is a ResourcePatternResolver.
	 * @param location the resource location, to be loaded with the ResourceLoader
	 * (or ResourcePatternResolver) of this bean definition reader
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 * @see #getResourceLoader()
	 * @see #loadBeanDefinitions(org.springframework.core.io.Resource)
	 * @see #loadBeanDefinitions(org.springframework.core.io.Resource[])
	 */
	/**
	 * 从指定的资源位置加载bean定义。 
	 *  <p>该位置也可以是位置模式，只要此bean定义阅读器的ResourceLoader是ResourcePatternResolver。 
	 *  
	 * @param  location该bean定义读取器的ResourceLoader（或ResourcePatternResolver）要加载的资源位置
	 * @return 找到的bean定义数
	 * @throws 发生加载或解析错误时BeanDefinitionStoreException 
	 * @see  #getResourceLoader（）
	 * @see  #loadBeanDefinitions（org.springframework.core.io.Resource）
	 * @see  #loadBeanDefinitions（org.springframework.core.io.Resource []）
	 */
	int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

	/**
	 * Load bean definitions from the specified resource locations.
	 * @param locations the resource locations, to be loaded with the ResourceLoader
	 * (or ResourcePatternResolver) of this bean definition reader
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	/**
	 * 从指定的资源位置加载bean定义。 
	 *  
	 * @param 定位要使用此bean定义阅读器的ResourceLoader（或ResourcePatternResolver）加载的资源位置
	 * @return 找到的bean定义数
	 * @throws  BeanDefinitionStoreException（在加载或解析错误的情况下）
	 */
	int loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;

}
