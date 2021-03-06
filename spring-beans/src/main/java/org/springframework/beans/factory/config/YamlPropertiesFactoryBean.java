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

package org.springframework.beans.factory.config;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.CollectionFactory;
import org.springframework.lang.Nullable;

/**
 * Factory for {@link java.util.Properties} that reads from a YAML source,
 * exposing a flat structure of String property values.
 *
 * <p>YAML is a nice human-readable format for configuration, and it has some
 * useful hierarchical properties. It's more or less a superset of JSON, so it
 * has a lot of similar features.
 *
 * <p><b>Note: All exposed values are of type {@code String}</b> for access through
 * the common {@link Properties#getProperty} method (e.g. in configuration property
 * resolution through {@link PropertyResourceConfigurer#setProperties(Properties)}).
 * If this is not desirable, use {@link YamlMapFactoryBean} instead.
 *
 * <p>The Properties created by this factory have nested paths for hierarchical
 * objects, so for instance this YAML
 *
 * <pre class="code">
 * environments:
 *   dev:
 *     url: https://dev.bar.com
 *     name: Developer Setup
 *   prod:
 *     url: https://foo.bar.com
 *     name: My Cool App
 * </pre>
 *
 * is transformed into these properties:
 *
 * <pre class="code">
 * environments.dev.url=https://dev.bar.com
 * environments.dev.name=Developer Setup
 * environments.prod.url=https://foo.bar.com
 * environments.prod.name=My Cool App
 * </pre>
 *
 * Lists are split as property keys with <code>[]</code> dereferencers, for
 * example this YAML:
 *
 * <pre class="code">
 * servers:
 * - dev.bar.com
 * - foo.bar.com
 * </pre>
 *
 * becomes properties like this:
 *
 * <pre class="code">
 * servers[0]=dev.bar.com
 * servers[1]=foo.bar.com
 * </pre>
 *
 * <p>Requires SnakeYAML 1.18 or higher, as of Spring Framework 5.0.6.
 *
 * @author Dave Syer
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.1
 */
/**
 * 从YAML源读取的{@link  java.util.Properties}的工厂，公开了String属性值的平面结构。 
 *  <p> YAML是一种很好的人类可读的配置格式，它具有一些有用的层次结构属性。 
 * 它或多或少是JSON的超集，因此具有许多相似的功能。 
 *  <p> <b>注意：所有公开的值均为{@code 字符串} </ b>类型，可通过常见的{@link  Properties＃getProperty}方法访问（例如，通过{<@链接> PropertyResourceConfigurer＃setProperties（Properties）}）。 
 * 如果不希望这样做，请改用{@link  YamlMapFactoryBean}。 
 *  <p>此工厂创建的属性具有用于层次结构对象的嵌套路径，因此，例如，以下YAML <pre class ="code">环境：dev：url：https://dev.bar.com名称：Developer Setup产品：网址：https://foo.bar.com名称：My Cool App </ pre>转换为以下属性：<pre class ="code"> environment.dev.url = https：//dev.bar.com环境.dev.name =开发人员安装环境.prod.url = https：//foo.bar.com environment.prod.name =我的酷应用</ pre>列表使用<code> [] </ code作为属性键拆分>解除引用，例如，以下YAML：<pre class ="code">服务器：-dev.bar.com-foo.bar.com </ pre>变为如下属性：<pre class ="code">服务器[0 ] = dev.bar.com服务器[1] = foo.bar.com </ pre> <p>从Spring Framework 5.0.6开始，需要SnakeYAML 1.18或更高版本。 
 *  @author  Dave Syer @author  Stephane Nicoll @author  Juergen Hoeller @始于4.1
 */
public class YamlPropertiesFactoryBean extends YamlProcessor implements FactoryBean<Properties>, InitializingBean {

	private boolean singleton = true;

	@Nullable
	private Properties properties;


	/**
	 * Set if a singleton should be created, or a new object on each request
	 * otherwise. Default is {@code true} (a singleton).
	 */
	/**
	 * 设置是否应该创建一个单例，否则在每个请求上创建一个新对象。 
	 * 默认值为{@code  true}（单例）。 
	 * 
	 */
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	@Override
	public boolean isSingleton() {
		return this.singleton;
	}

	@Override
	public void afterPropertiesSet() {
		if (isSingleton()) {
			this.properties = createProperties();
		}
	}

	@Override
	@Nullable
	public Properties getObject() {
		return (this.properties != null ? this.properties : createProperties());
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}


	/**
	 * Template method that subclasses may override to construct the object
	 * returned by this factory. The default implementation returns a
	 * properties with the content of all resources.
	 * <p>Invoked lazily the first time {@link #getObject()} is invoked in
	 * case of a shared singleton; else, on each {@link #getObject()} call.
	 * @return the object returned by this factory
	 * @see #process(MatchCallback)
	 */
	/**
	 * 子类的模板方法可以重写以构造此工厂返回的对象。 
	 * 默认实现返回带有所有资源内容的属性。 
	 *  <p>在共享单例的情况下，第一次调用{@link  #getObject（）}时会延迟调用； 
	 * 否则，在每个{@link  #getObject（）}调用上。 
	 *  
	 * @return 此工厂返回的对象
	 * @see  #process（MatchCallback）
	 */
	protected Properties createProperties() {
		Properties result = CollectionFactory.createStringAdaptingProperties();
		process((properties, map) -> result.putAll(properties));
		return result;
	}

}
