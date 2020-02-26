/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory;

/**
 * Interface to be implemented by beans that want to be aware of their
 * bean name in a bean factory. Note that it is not usually recommended
 * that an object depends on its bean name, as this represents a potentially
 * brittle dependence on external configuration, as well as a possibly
 * unnecessary dependence on a Spring API.
 *
 * <p>For a list of all bean lifecycle methods, see the
 * {@link BeanFactory BeanFactory javadocs}.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 01.11.2003
 * @see BeanClassLoaderAware
 * @see BeanFactoryAware
 * @see InitializingBean
 */
/**
 * 想要在bean工厂中知道其bean名称的bean所实现的接口。 
 * 请注意，通常不建议对象依赖于它的bean名称，因为这表示对外部配置的潜在脆弱依赖，以及对Spring API的不必要依赖。 
 *  <p>有关所有bean生命周期方法的列表，请参见{@link  BeanFactory BeanFactory javadocs}。 
 *  @author  Juergen Hoeller @author 克里斯·比姆斯（Chris Beams）@自01.11.2003 
 * @see  BeanClassLoaderAware 
 * @see  BeanFactoryAware 
 * @see  InitializingBean
 */
public interface BeanNameAware extends Aware {

	/**
	 * Set the name of the bean in the bean factory that created this bean.
	 * <p>Invoked after population of normal bean properties but before an
	 * init callback such as {@link InitializingBean#afterPropertiesSet()}
	 * or a custom init-method.
	 * @param name the name of the bean in the factory.
	 * Note that this name is the actual bean name used in the factory, which may
	 * differ from the originally specified name: in particular for inner bean
	 * names, the actual bean name might have been made unique through appending
	 * "#..." suffixes. Use the {@link BeanFactoryUtils#originalBeanName(String)}
	 * method to extract the original bean name (without suffix), if desired.
	 */
	/**
	 * 在创建此bean的bean工厂中设置bean的名称。 
	 *  <p>在填充正常的bean属性之后但在诸如{@link  InitializingBean＃afterPropertiesSet（）}之类的init回调或自定义init方法之前调用。 
	 *  
	 * @param 命名工厂中bean的名称。 
	 * 请注意，该名称是工厂中使用的实际bean名称，可能与最初指定的名称不同：特别是对于内部bean名称，通过添加"＃..."后缀可以使实际bean名称具有唯一性。 
	 * 如果需要，使用{@link  BeanFactoryUtils＃originalBeanName（String）}方法提取原始bean名称（不带后缀）。 
	 * 
	 */
	void setBeanName(String name);

}
