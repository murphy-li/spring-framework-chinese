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

package org.springframework.core.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Abstract base class representing a source of name/value property pairs. The underlying
 * {@linkplain #getSource() source object} may be of any type {@code T} that encapsulates
 * properties. Examples include {@link java.util.Properties} objects, {@link java.util.Map}
 * objects, {@code ServletContext} and {@code ServletConfig} objects (for access to init
 * parameters). Explore the {@code PropertySource} type hierarchy to see provided
 * implementations.
 *
 * <p>{@code PropertySource} objects are not typically used in isolation, but rather
 * through a {@link PropertySources} object, which aggregates property sources and in
 * conjunction with a {@link PropertyResolver} implementation that can perform
 * precedence-based searches across the set of {@code PropertySources}.
 *
 * <p>{@code PropertySource} identity is determined not based on the content of
 * encapsulated properties, but rather based on the {@link #getName() name} of the
 * {@code PropertySource} alone. This is useful for manipulating {@code PropertySource}
 * objects when in collection contexts. See operations in {@link MutablePropertySources}
 * as well as the {@link #named(String)} and {@link #toString()} methods for details.
 *
 * <p>Note that when working with @{@link
 * org.springframework.context.annotation.Configuration Configuration} classes that
 * the @{@link org.springframework.context.annotation.PropertySource PropertySource}
 * annotation provides a convenient and declarative way of adding property sources to the
 * enclosing {@code Environment}.
 *
 * @author Chris Beams
 * @since 3.1
 * @param <T> the source type
 * @see PropertySources
 * @see PropertyResolver
 * @see PropertySourcesPropertyResolver
 * @see MutablePropertySources
 * @see org.springframework.context.annotation.PropertySource
 */
/**
 * 表示名称/值属性对源的抽象基类。 
 * 底层{@link  plain #getSource（）源对象}可以是封装属性的任何类型的{@code  T}。 
 * 示例包括{@link  java.util.Properties}对象，{<@link> java.util.Map}对象，{<@code> ServletContext}和{@code  ServletConfig}对象（用于访问初始参数） ）。 
 * 探索{@code  PropertySource}类型层次结构，以查看提供的实现。 
 *  <p> {<@code> PropertySource}对象通常不是孤立地使用，而是通过{@link  PropertySources}对象，该对象聚合属性源并与可以实现以下目的的{@link  PropertyResolver}实现结合使用对{@code  PropertySources}集执行基于优先级的搜索。 
 *  <p> {<@code> PropertySource}的标识不是基于封装属性的内容，而是仅基于{@code  PropertySource}的{@link  #getName（）name}来确定。 
 * 当在集合上下文中操作{@code  PropertySource}对象时，这很有用。 
 * 有关详细信息，请参见{@link  MutablePropertySources}中的操作以及{@link  #named（String）}和{@link  #toString（）}方法中的操作。 
 *  <p>请注意，在使用@{<@link> org.springframework.context.annotation.Configuration Configuration}类时，@{<@link> org.springframework.context.annotation.PropertySource PropertySource}注释提供了便捷和将属性源添加到封闭的{@code  Environment}的声明方式。 
 *  @author  Chris Beams @since 3.1 
 * @param  <T>源类型
 * @see  PropertySources 
 * @see  PropertyResolver 
 * @see  PropertySourcesPropertyResolver 
 * @see  MutablePropertySources 
 * @see  org.springframework.context.annotation .PropertySource
 */
public abstract class PropertySource<T> {

	protected final Log logger = LogFactory.getLog(getClass());

	protected final String name;

	protected final T source;


	/**
	 * Create a new {@code PropertySource} with the given name and source object.
	 */
	/**
	 * 使用给定的名称和源对象创建一个新的{@code  PropertySource}。 
	 * 
	 */
	public PropertySource(String name, T source) {
		Assert.hasText(name, "Property source name must contain at least one character");
		Assert.notNull(source, "Property source must not be null");
		this.name = name;
		this.source = source;
	}

	/**
	 * Create a new {@code PropertySource} with the given name and with a new
	 * {@code Object} instance as the underlying source.
	 * <p>Often useful in testing scenarios when creating anonymous implementations
	 * that never query an actual source but rather return hard-coded values.
	 */
	/**
	 * 使用给定的名称和新的{@code  Object}实例作为基础源，创建一个新的{@code  PropertySource}。 
	 *  <p>在创建匿名实现时通常非常有用，这些匿名实现从不查询实际源，而是返回硬编码值。 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PropertySource(String name) {
		this(name, (T) new Object());
	}


	/**
	 * Return the name of this {@code PropertySource}.
	 */
	/**
	 * 返回此{@code  PropertySource}的名称。 
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the underlying source object for this {@code PropertySource}.
	 */
	/**
	 * 返回此{@code  PropertySource}的基础源对象。 
	 * 
	 */
	public T getSource() {
		return this.source;
	}

	/**
	 * Return whether this {@code PropertySource} contains the given name.
	 * <p>This implementation simply checks for a {@code null} return value
	 * from {@link #getProperty(String)}. Subclasses may wish to implement
	 * a more efficient algorithm if possible.
	 * @param name the property name to find
	 */
	/**
	 * 返回此{@code  PropertySource}是否包含给定名称。 
	 *  <p>此实现仅检查来自{@link  #getProperty（String）}的{@code  null}返回值。 
	 * 如果可能，子类可能希望实现更有效的算法。 
	 *  
	 * @param 命名要查找的属性名称
	 */
	public boolean containsProperty(String name) {
		return (getProperty(name) != null);
	}

	/**
	 * Return the value associated with the given name,
	 * or {@code null} if not found.
	 * @param name the property to find
	 * @see PropertyResolver#getRequiredProperty(String)
	 */
	/**
	 * 返回与给定名称关联的值，如果找不到，则返回{@code  null}。 
	 *  
	 * @param 命名要查找的属性
	 * @see  PropertyResolver＃getRequiredProperty（String）
	 */
	@Nullable
	public abstract Object getProperty(String name);


	/**
	 * This {@code PropertySource} object is equal to the given object if:
	 * <ul>
	 * <li>they are the same instance
	 * <li>the {@code name} properties for both objects are equal
	 * </ul>
	 * <p>No properties other than {@code name} are evaluated.
	 */
	/**
	 * 如果满足以下条件，则此{@code  PropertySource}对象等于给定对象：<ul> <li>它们是相同的实例<li>两个对象的{@code  name}属性都相等</ ul> < p>不评估{@code  name}以外的其他属性。 
	 * 
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof PropertySource &&
				ObjectUtils.nullSafeEquals(this.name, ((PropertySource<?>) other).name)));
	}

	/**
	 * Return a hash code derived from the {@code name} property
	 * of this {@code PropertySource} object.
	 */
	/**
	 * 返回从此{@code  PropertySource}对象的{@code  name}属性获得的哈希码。 
	 * 
	 */
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.name);
	}

	/**
	 * Produce concise output (type and name) if the current log level does not include
	 * debug. If debug is enabled, produce verbose output including the hash code of the
	 * PropertySource instance and every name/value property pair.
	 * <p>This variable verbosity is useful as a property source such as system properties
	 * or environment variables may contain an arbitrary number of property pairs,
	 * potentially leading to difficult to read exception and log messages.
	 * @see Log#isDebugEnabled()
	 */
	/**
	 * 如果当前日志级别不包括调试，则产生简洁的输出（类型和名称）。 
	 * 如果启用了调试，则产生详细的输出，包括PropertySource实例的哈希码和每个名称/值属性对。 
	 *  <p>此变量的详细程度非常有用，因为诸如系统属性或环境变量之类的属性源可能包含任意数量的属性对，从而可能导致难以读取异常和日志消息。 
	 *  
	 * @see  Log＃isDebugEnabled（）
	 */
	@Override
	public String toString() {
		if (logger.isDebugEnabled()) {
			return getClass().getSimpleName() + "@" + System.identityHashCode(this) +
					" {name='" + this.name + "', properties=" + this.source + "}";
		}
		else {
			return getClass().getSimpleName() + " {name='" + this.name + "'}";
		}
	}


	/**
	 * Return a {@code PropertySource} implementation intended for collection comparison purposes only.
	 * <p>Primarily for internal use, but given a collection of {@code PropertySource} objects, may be
	 * used as follows:
	 * <pre class="code">
	 * {@code List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
	 * sources.add(new MapPropertySource("sourceA", mapA));
	 * sources.add(new MapPropertySource("sourceB", mapB));
	 * assert sources.contains(PropertySource.named("sourceA"));
	 * assert sources.contains(PropertySource.named("sourceB"));
	 * assert !sources.contains(PropertySource.named("sourceC"));
	 * }</pre>
	 * The returned {@code PropertySource} will throw {@code UnsupportedOperationException}
	 * if any methods other than {@code equals(Object)}, {@code hashCode()}, and {@code toString()}
	 * are called.
	 * @param name the name of the comparison {@code PropertySource} to be created and returned.
	 */
	/**
	 * 返回仅用于集合比较目的的{@code  PropertySource}实现。 
	 *  <p>主要供内部使用，但被赋予{@code  PropertySource}对象的集合，可以按以下方式使用：<pre class ="code"> {@code  List <PropertySource <？>> new ArrayList <PropertySource <？>>（）; sources.add（new MapPropertySource（"sourceA"，mapA））; sources.add（new MapPropertySource（"sourceB"，mapB））;断言sources.contains（PropertySource.named（"sourceA"））;断言sources.contains（PropertySource.named（"sourceB"）））;断言！ 
	 * sources.contains（PropertySource.named（"sourceC"））; } </ pre>如果除{@code  equals（Object）}，{<@code> hashCode（）}和{@code  hashSource（）}之外的任何其他方法，返回的{@code  PropertySource}将抛出{@code  UnsupportedOperationException} {@code  toString（）}被调用。 
	 *  
	 * @param 命名要创建并返回的比较的名称{@code  PropertySource}。 
	 * 
	 */
	public static PropertySource<?> named(String name) {
		return new ComparisonPropertySource(name);
	}


	/**
	 * {@code PropertySource} to be used as a placeholder in cases where an actual
	 * property source cannot be eagerly initialized at application context
	 * creation time.  For example, a {@code ServletContext}-based property source
	 * must wait until the {@code ServletContext} object is available to its enclosing
	 * {@code ApplicationContext}.  In such cases, a stub should be used to hold the
	 * intended default position/order of the property source, then be replaced
	 * during context refresh.
	 * @see org.springframework.context.support.AbstractApplicationContext#initPropertySources()
	 * @see org.springframework.web.context.support.StandardServletEnvironment
	 * @see org.springframework.web.context.support.ServletContextPropertySource
	 */
	/**
	 * {@code  PropertySource}用作占位符，以防在应用程序上下文创建时无法急切初始化实际属性源的情况。 
	 * 例如，基于{@code  ServletContext}的属性源必须等待，直到{@code  ServletContext}对象可用于其封闭的{@code  ApplicationContext}。 
	 * 在这种情况下，应使用存根保留属性源的预期默认位置/顺序，然后在上下文刷新期间将其替换。 
	 *  
	 * @see  org.springframework.context.support.AbstractApplicationContext＃initPropertySources（）
	 * @see  org.springframework.web.context.support.StandardServletEnvironment 
	 * @see  org.springframework.web.context.support.ServletContextPropertySource
	 */
	public static class StubPropertySource extends PropertySource<Object> {

		public StubPropertySource(String name) {
			super(name, new Object());
		}

		/**
		 * Always returns {@code null}.
		 */
		/**
		 * 始终返回{@code  null}。 
		 * 
		 */
		@Override
		@Nullable
		public String getProperty(String name) {
			return null;
		}
	}


	/**
	 * A {@code PropertySource} implementation intended for collection comparison
	 * purposes.
	 *
	 * @see PropertySource#named(String)
	 */
	/**
	 * 一个{@code  PropertySource}实现，旨在进行集合比较。 
	 *  
	 * @see  PropertySource＃named（字符串）
	 */
	static class ComparisonPropertySource extends StubPropertySource {

		private static final String USAGE_ERROR =
				"ComparisonPropertySource instances are for use with collection comparison only";

		public ComparisonPropertySource(String name) {
			super(name);
		}

		@Override
		public Object getSource() {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		public boolean containsProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}

		@Override
		@Nullable
		public String getProperty(String name) {
			throw new UnsupportedOperationException(USAGE_ERROR);
		}
	}

}
