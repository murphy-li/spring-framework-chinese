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

package org.springframework.beans.factory.config;

import java.beans.PropertyEditor;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * {@link BeanFactoryPostProcessor} implementation that allows for convenient
 * registration of custom {@link PropertyEditor property editors}.
 *
 * <p>In case you want to register {@link PropertyEditor} instances,
 * the recommended usage as of Spring 2.0 is to use custom
 * {@link PropertyEditorRegistrar} implementations that in turn register any
 * desired editor instances on a given
 * {@link org.springframework.beans.PropertyEditorRegistry registry}. Each
 * PropertyEditorRegistrar can register any number of custom editors.
 *
 * <pre class="code">
 * &lt;bean id="customEditorConfigurer" class="org.springframework.beans.factory.config.CustomEditorConfigurer"&gt;
 *   &lt;property name="propertyEditorRegistrars"&gt;
 *     &lt;list&gt;
 *       &lt;bean class="mypackage.MyCustomDateEditorRegistrar"/&gt;
 *       &lt;bean class="mypackage.MyObjectEditorRegistrar"/&gt;
 *     &lt;/list&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * It's perfectly fine to register {@link PropertyEditor} <em>classes</em> via
 * the {@code customEditors} property. Spring will create fresh instances of
 * them for each editing attempt then:
 *
 * <pre class="code">
 * &lt;bean id="customEditorConfigurer" class="org.springframework.beans.factory.config.CustomEditorConfigurer"&gt;
 *   &lt;property name="customEditors"&gt;
 *     &lt;map&gt;
 *       &lt;entry key="java.util.Date" value="mypackage.MyCustomDateEditor"/&gt;
 *       &lt;entry key="mypackage.MyObject" value="mypackage.MyObjectEditor"/&gt;
 *     &lt;/map&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * Note, that you shouldn't register {@link PropertyEditor} bean instances via
 * the {@code customEditors} property as {@link PropertyEditor PropertyEditors} are stateful
 * and the instances will then have to be synchronized for every editing
 * attempt. In case you need control over the instantiation process of
 * {@link PropertyEditor PropertyEditors}, use a {@link PropertyEditorRegistrar} to register
 * them.
 *
 * <p>
 * Also supports "java.lang.String[]"-style array class names and primitive
 * class names (e.g. "boolean"). Delegates to {@link ClassUtils} for actual
 * class name resolution.
 *
 * <p><b>NOTE:</b> Custom property editors registered with this configurer do
 * <i>not</i> apply to data binding. Custom editors for data binding need to
 * be registered on the {@link org.springframework.validation.DataBinder}:
 * Use a common base class or delegate to common PropertyEditorRegistrar
 * implementations to reuse editor registration there.
 *
 * @author Juergen Hoeller
 * @since 27.02.2004
 * @see java.beans.PropertyEditor
 * @see org.springframework.beans.PropertyEditorRegistrar
 * @see ConfigurableBeanFactory#addPropertyEditorRegistrar
 * @see ConfigurableBeanFactory#registerCustomEditor
 * @see org.springframework.validation.DataBinder#registerCustomEditor
 */
/**
 * {@link  BeanFactoryPostProcessor}实现，可以方便地注册自定义{@link  PropertyEditor属性编辑器}。 
 *  <p>如果要注册{@link  PropertyEditor}实例，从Spring 2.0开始，推荐的用法是使用自定义{@link  PropertyEditorRegistrar}实现，该实现又会在给定的{@link  org.springframework.beans.PropertyEditorRegistry注册表}。 
 * 每个PropertyEditorRegistrar可以注册任意数量的自定义编辑器。 
 *  <pre class ="code"> <bean id ="customEditorConfigurer"class ="org.springframework.beans.factory.config.CustomEditorConfigurer"> <property name ="propertyEditorRegistrars"> <list> <bean class ="mypackage.MyCustomDateEditorRegistrar "/> <bean class ="mypackage.MyObjectEditorRegistrar"/> </ list> </ property> </ bean> </ pre> <p>注册{@link  PropertyEditor} <em> classes < / em>通过{@code  customEditors}属性。 
 *  Spring将为每次编辑尝试创建它们的新实例，然后：<pre class ="code"> <bean id ="customEditorConfigurer"class ="org.springframework.beans.factory.config.CustomEditorConfigurer"> <property name ="customEditors "> <map> <entry key ="java.util.Date"value ="mypackage.MyCustomDateEditor"/> <entry key ="mypackage.MyObject"value ="mypackage.MyObjectEditor"/> </ map> </ property > </ bean> </ pre> <p>注意，您不应该通过{@code  customEditors}属性注册{@link  PropertyEditor} bean实例，因为{@link  PropertyEditor PropertyEditors}是有状态的然后必须为每次编辑尝试同步实例。 
 * 如果需要控制{@link  PropertyEditor PropertyEditors}的实例化过程，请使用{@link  PropertyEditorRegistrar}进行注册。 
 *  <p>还支持"java.lang.String []"样式的数组类名称和原始类名称（例如"boolean"）。 
 * 代表{@link  ClassUtils}进行实际的类名解析。 
 *  <p> <b>注意：</ b>在此配置程序中注册的自定义属性编辑器<i>不</ i>适用于数据绑定。 
 * 用于数据绑定的自定义编辑器需要在{@link  org.springframework.validation.DataBinder}上注册：使用公共基类或委托给常见的PropertyEditorRegistrar实现，以在此处重用编辑器注册。 
 *  @author  Juergen Hoeller @2004年2月27日起
 * @see  java.beans.PropertyEditor 
 * @see  org.springframework.beans.PropertyEditorRegistrar 
 * @see  ConfigurableBeanFactory＃addPropertyEditorRegistrar 
 * @see  ConfigurableBeanFactory＃registerCustomEditor 
 * @see  org .springframework.validation.DataBinder＃registerCustomEditor
 */
public class CustomEditorConfigurer implements BeanFactoryPostProcessor, Ordered {

	protected final Log logger = LogFactory.getLog(getClass());

	private int order = Ordered.LOWEST_PRECEDENCE;  // default: same as non-Ordered

	@Nullable
	private PropertyEditorRegistrar[] propertyEditorRegistrars;

	@Nullable
	private Map<Class<?>, Class<? extends PropertyEditor>> customEditors;


	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	/**
	 * Specify the {@link PropertyEditorRegistrar PropertyEditorRegistrars}
	 * to apply to beans defined within the current application context.
	 * <p>This allows for sharing {@code PropertyEditorRegistrars} with
	 * {@link org.springframework.validation.DataBinder DataBinders}, etc.
	 * Furthermore, it avoids the need for synchronization on custom editors:
	 * A {@code PropertyEditorRegistrar} will always create fresh editor
	 * instances for each bean creation attempt.
	 * @see ConfigurableListableBeanFactory#addPropertyEditorRegistrar
	 */
	/**
	 * 指定{@link  PropertyEditorRegistrar PropertyEditorRegistrars}以应用于当前应用程序上下文中定义的bean。 
	 *  <p>这允许与{@link  org.springframework.validation.DataBinder DataBinders}等共享{@code  PropertyEditorRegistrars}。 
	 * 此外，它避免了自定义编辑器上的同步需求：A {@code  PropertyEditorRegistrar}将始终为每次创建bean的尝试创建新的编辑器实例。 
	 *  
	 * @see  ConfigurableListableBeanFactory＃addPropertyEditorRegistrar
	 */
	public void setPropertyEditorRegistrars(PropertyEditorRegistrar[] propertyEditorRegistrars) {
		this.propertyEditorRegistrars = propertyEditorRegistrars;
	}

	/**
	 * Specify the custom editors to register via a {@link Map}, using the
	 * class name of the required type as the key and the class name of the
	 * associated {@link PropertyEditor} as value.
	 * @see ConfigurableListableBeanFactory#registerCustomEditor
	 */
	/**
	 * 使用所需类型的类名称作为键并使用关联的{@link  PropertyEditor}的类名称作为值，指定自定义编辑器以通过{@link  Map}注册。 
	 *  
	 * @see  ConfigurableListableBeanFactory＃registerCustomEditor
	 */
	public void setCustomEditors(Map<Class<?>, Class<? extends PropertyEditor>> customEditors) {
		this.customEditors = customEditors;
	}


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (this.propertyEditorRegistrars != null) {
			for (PropertyEditorRegistrar propertyEditorRegistrar : this.propertyEditorRegistrars) {
				beanFactory.addPropertyEditorRegistrar(propertyEditorRegistrar);
			}
		}
		if (this.customEditors != null) {
			this.customEditors.forEach(beanFactory::registerCustomEditor);
		}
	}

}
