/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.env;

import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Specialization of {@link MapPropertySource} designed for use with
 * {@linkplain AbstractEnvironment#getSystemEnvironment() system environment variables}.
 * Compensates for constraints in Bash and other shells that do not allow for variables
 * containing the period character and/or hyphen character; also allows for uppercase
 * variations on property names for more idiomatic shell use.
 *
 * <p>For example, a call to {@code getProperty("foo.bar")} will attempt to find a value
 * for the original property or any 'equivalent' property, returning the first found:
 * <ul>
 * <li>{@code foo.bar} - the original name</li>
 * <li>{@code foo_bar} - with underscores for periods (if any)</li>
 * <li>{@code FOO.BAR} - original, with upper case</li>
 * <li>{@code FOO_BAR} - with underscores and upper case</li>
 * </ul>
 * Any hyphen variant of the above would work as well, or even mix dot/hyphen variants.
 *
 * <p>The same applies for calls to {@link #containsProperty(String)}, which returns
 * {@code true} if any of the above properties are present, otherwise {@code false}.
 *
 * <p>This feature is particularly useful when specifying active or default profiles as
 * environment variables. The following is not allowable under Bash:
 *
 * <pre class="code">spring.profiles.active=p1 java -classpath ... MyApp</pre>
 *
 * However, the following syntax is permitted and is also more conventional:
 *
 * <pre class="code">SPRING_PROFILES_ACTIVE=p1 java -classpath ... MyApp</pre>
 *
 * <p>Enable debug- or trace-level logging for this class (or package) for messages
 * explaining when these 'property name resolutions' occur.
 *
 * <p>This property source is included by default in {@link StandardEnvironment}
 * and all its subclasses.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see StandardEnvironment
 * @see AbstractEnvironment#getSystemEnvironment()
 * @see AbstractEnvironment#ACTIVE_PROFILES_PROPERTY_NAME
 */
/**
 * 专为{{@link> plain AbstractEnvironment＃getSystemEnvironment（）系统环境变量}设计的{@link  MapPropertySource}的专业化。 
 * 补偿Bash和其他shell中的约束，这些约束不允许包含句点字符和/或连字符的变量； 
 * 还允许在属性名称上使用大写形式，以更方便地使用shell。 
 *  <p>例如，对{@code  getProperty（"foo.bar"）}的调用将尝试查找原始属性或任何"等效"属性的值，并返回找到的第一个：<ul> <li > {<@code> foo.bar}-原始名称</ li> <li> {<@code> foo_bar}-带有下划线的句点（如果有）</ li> <li> {<@code> FOO .BAR}-原始，带有大写字母</ li> <li> {<@code> FOO_BAR}-带下划线和大写字母</ li> </ ul>上面的任何连字符变体也可以使用，甚至混合点/连字符变体。 
 *  <p>对{@link  #containsProperty（String）}的调用也是如此，如果存在上述任何属性，则返回{@code  true}，否则返回{@code  false}。 
 *  <p>当将活动或默认配置文件指定为环境变量时，此功能特别有用。 
 * 在Bash中不允许以下内容：<pre class ="code"> spring.profiles.active = p1 java -classpath ... MyApp </ pre>但是，以下语法是允许的，并且也是更常规的：<pre class ="code"> SPRING_PROFILES_ACTIVE = p1 java -classpath ... MyApp </ pre> <p>为此类（或程序包）启用调试或跟踪级别的日志记录，以获取解释何时发生这些"属性名称解析"的消息。 
 *  <p>此属性源默认包含在{@link  StandardEnvironment}及其所有子类中。 
 *  @author 克里斯·比姆斯（Chris Beams）@author 于尔根·霍勒（Juergen Hoeller）@从3.1开始
 * @see  StandardEnvironment 
 * @see  AbstractEnvironment＃getSystemEnvironment（）
 */
public class SystemEnvironmentPropertySource extends MapPropertySource {

	/**
	 * Create a new {@code SystemEnvironmentPropertySource} with the given name and
	 * delegating to the given {@code MapPropertySource}.
	 */
	/**
	 * 使用给定的名称创建一个新的{@code  SystemEnvironmentPropertySource}，并委派给给定的{@code  MapPropertySource}。 
	 * 
	 */
	public SystemEnvironmentPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}


	/**
	 * Return {@code true} if a property with the given name or any underscore/uppercase variant
	 * thereof exists in this property source.
	 */
	/**
	 * 如果此属性源中存在具有给定名称或其下划线/大写变体的属性，则返回{@code  true}。 
	 * 
	 */
	@Override
	public boolean containsProperty(String name) {
		return (getProperty(name) != null);
	}

	/**
	 * This implementation returns {@code true} if a property with the given name or
	 * any underscore/uppercase variant thereof exists in this property source.
	 */
	/**
	 * 如果此属性源中存在具有给定名称或其下划线/大写变体的属性，则此实现返回{@code  true}。 
	 * 
	 */
	@Override
	@Nullable
	public Object getProperty(String name) {
		String actualName = resolvePropertyName(name);
		if (logger.isDebugEnabled() && !name.equals(actualName)) {
			logger.debug("PropertySource '" + getName() + "' does not contain property '" + name +
					"', but found equivalent '" + actualName + "'");
		}
		return super.getProperty(actualName);
	}

	/**
	 * Check to see if this property source contains a property with the given name, or
	 * any underscore / uppercase variation thereof. Return the resolved name if one is
	 * found or otherwise the original name. Never returns {@code null}.
	 */
	/**
	 * 检查此属性源是否包含具有给定名称的属性，或其任何下划线/大写字母变体。 
	 * 如果找到一个，则返回解析的名称，否则返回原始名称。 
	 * 从不返回{@code  null}。 
	 * 
	 */
	protected final String resolvePropertyName(String name) {
		Assert.notNull(name, "Property name must not be null");
		String resolvedName = checkPropertyName(name);
		if (resolvedName != null) {
			return resolvedName;
		}
		String uppercasedName = name.toUpperCase();
		if (!name.equals(uppercasedName)) {
			resolvedName = checkPropertyName(uppercasedName);
			if (resolvedName != null) {
				return resolvedName;
			}
		}
		return name;
	}

	@Nullable
	private String checkPropertyName(String name) {
		// Check name as-is
		if (containsKey(name)) {
			return name;
		}
		// Check name with just dots replaced
		String noDotName = name.replace('.', '_');
		if (!name.equals(noDotName) && containsKey(noDotName)) {
			return noDotName;
		}
		// Check name with just hyphens replaced
		String noHyphenName = name.replace('-', '_');
		if (!name.equals(noHyphenName) && containsKey(noHyphenName)) {
			return noHyphenName;
		}
		// Check name with dots and hyphens replaced
		String noDotNoHyphenName = noDotName.replace('-', '_');
		if (!noDotName.equals(noDotNoHyphenName) && containsKey(noDotNoHyphenName)) {
			return noDotNoHyphenName;
		}
		// Give up
		return null;
	}

	private boolean containsKey(String name) {
		return (isSecurityManagerPresent() ? this.source.keySet().contains(name) : this.source.containsKey(name));
	}

	protected boolean isSecurityManagerPresent() {
		return (System.getSecurityManager() != null);
	}

}
