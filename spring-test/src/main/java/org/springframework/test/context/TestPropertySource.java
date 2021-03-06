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

package org.springframework.test.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * {@code @TestPropertySource} is a class-level annotation that is used to
 * configure the {@link #locations} of properties files and inlined
 * {@link #properties} to be added to the {@code Environment}'s set of
 * {@code PropertySources} for an
 * {@link org.springframework.context.ApplicationContext ApplicationContext}
 * for integration tests.
 *
 * <h3>Precedence</h3>
 * <p>Test property sources have higher precedence than those loaded from the
 * operating system's environment or Java system properties as well as property
 * sources added by the application declaratively via
 * {@link org.springframework.context.annotation.PropertySource @PropertySource}
 * or programmatically (e.g., via an
 * {@link org.springframework.context.ApplicationContextInitializer ApplicationContextInitializer}
 * or some other means). Thus, test property sources can be used to selectively
 * override properties defined in system and application property sources.
 * Furthermore, inlined {@link #properties} have higher precedence than
 * properties loaded from resource {@link #locations}.
 *
 * <h3>Default Properties File Detection</h3>
 * <p>If {@code @TestPropertySource} is declared as an <em>empty</em> annotation
 * (i.e., without explicit values for {@link #locations} or {@link #properties}),
 * an attempt will be made to detect a <em>default</em> properties file relative
 * to the class that declared the annotation. For example, if the annotated test
 * class is {@code com.example.MyTest}, the corresponding default properties file
 * is {@code "classpath:com/example/MyTest.properties"}. If the default cannot be
 * detected, an {@link IllegalStateException} will be thrown.
 *
 * <h3>Enabling &#064;TestPropertySource</h3>
 * <p>{@code @TestPropertySource} is enabled if the configured
 * {@linkplain ContextConfiguration#loader context loader} honors it. Every
 * {@code SmartContextLoader} that is a subclass of either
 * {@link org.springframework.test.context.support.AbstractGenericContextLoader AbstractGenericContextLoader} or
 * {@link org.springframework.test.context.web.AbstractGenericWebContextLoader AbstractGenericWebContextLoader}
 * provides automatic support for {@code @TestPropertySource}, and this includes
 * every {@code SmartContextLoader} provided by the Spring TestContext Framework.
 *
 * <h3>Miscellaneous</h3>
 * <ul>
 * <li>Typically, {@code @TestPropertySource} will be used in conjunction with
 * {@link ContextConfiguration @ContextConfiguration}.</li>
 * <li>As of Spring Framework 5.2, {@code @TestPropertySource} can be used as a
 * <em>{@linkplain Repeatable repeatable}</em> annotation.</li>
 * <li>This annotation may be used as a <em>meta-annotation</em> to create
 * custom <em>composed annotations</em>; however, caution should be taken if
 * this annotation and {@code @ContextConfiguration} are combined on a composed
 * annotation since the {@code locations} and {@code inheritLocations} attributes
 * of both annotations can lead to ambiguity during the attribute resolution
 * process.</li>
 * </ul>
 *
 * @author Sam Brannen
 * @since 4.1
 * @see ContextConfiguration
 * @see org.springframework.core.env.Environment
 * @see org.springframework.core.env.PropertySource
 * @see org.springframework.context.annotation.PropertySource
 */
/**
 * {@code  @TestPropertySource}是一个类级别的注释，用于配置属性文件的{@link  #locations}以及内联的{@link  #properties}，以添加到{<@code >环境}的一组{@code  PropertySources}，用于{@link  org.springframework.context.ApplicationContext ApplicationContext}，以进行集成测试。 
 *  <h3>优先级</ h3> <p>测试属性源的优先级高于从操作系统环境或Java系统属性以及应用程序通过{@link  org.springframework.context声明性添加的属性源加载的优先级。 
 *  .annotation.PropertySource @PropertySource}或以编程方式（例如，通过{@link  org.springframework.context.ApplicationContextInitializer ApplicationContextInitializer}或其他方式）。 
 * 因此，测试属性源可用于选择性覆盖系统和应用程序属性源中定义的属性。 
 * 此外，内联的{@link  #properties}的优先级高于从资源{@link  #locations}加载的属性。 
 *  <h3>默认属性文件检测</ h3> <p>如果将{@code  @TestPropertySource}声明为<em> empty </ em>注释（即，对于{@link  #locations没有显式值） }或{@link  #properties}），将尝试检测相对于声明了注释的类的<em> default </ em>属性文件。 
 * 例如，如果带注释的测试类是{@code  com.example.MyTest}，则相应的默认属性文件是{@code "classpath：com / example / MyTest.properties"}。 
 * 如果无法检测到默认值，则会抛出{@link  IllegalStateException}。 
 *  <h3>启用@TestPropertySource </ h3> <p> {<@code> @TestPropertySource}如果已配置的{@link  plain ContextConfiguration＃loader context loader}兑现，则启用该功能。 
 * 每个{@code  SmartContextLoader}都是{@link  org.springframework.test.context.support.AbstractGenericContextLoader AbstractGenericContextLoader}或{@link  org.springframework.test.context.web.AbstractGenericWebContextLoader AbstractGenericWebContextLoader的子类}为{@code  @TestPropertySource}提供自动支持，其中包括Spring TestContext Framework提供的每个{@code  SmartContextLoader}。 
 *  <h3>其他</ h3> <ul> <li>通常，{<@code> @TestPropertySource}将与{@link  ContextConfiguration @ContextConfiguration}结合使用。 
 * </ li> <li>从Spring开始框架5.2 {@code  @TestPropertySource}可用作<em> {<@link> plain Repeatable repeatable} </ em>注释。 
 * </ li> <li>该注释可用作<em> > meta-annotation </ em>创建自定义的<em>组成的注释</ em>； 
 * 但是，如果将此注释和{@code  @ContextConfiguration}组合在组合的注释上，则应格外小心，因为两个注释的{@code  locations}和{@code  InheritLocations}属性都可能在处理过程中导致歧义属性解析过程。 
 * </ li> </ ul> @author  Sam Brannen @since 4.1 
 * @see  ContextConfiguration 
 * @see  org.springframework.core.env.Environment 
 * @see  org.springframework.core。 
 *  env.PropertySource 
 * @see  org.springframework.context.annotation.PropertySource
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(TestPropertySources.class)
public @interface TestPropertySource {

	/**
	 * Alias for {@link #locations}.
	 * <p>This attribute may <strong>not</strong> be used in conjunction with
	 * {@link #locations}, but it may be used <em>instead</em> of {@link #locations}.
	 * @see #locations
	 */
	/**
	 * {@link  #locations}的别名。 
	 *  <p>此属性可能<strong>不</ strong>与{@link  #locations}结合使用，但可以代替{@link  #locations}的<em> </ em>使用。 
	 *  
	 * @see ＃地点
	 */
	@AliasFor("locations")
	String[] value() default {};

	/**
	 * The resource locations of properties files to be loaded into the
	 * {@code Environment}'s set of {@code PropertySources}. Each location
	 * will be added to the enclosing {@code Environment} as its own property
	 * source, in the order declared.
	 * <h3>Supported File Formats</h3>
	 * <p>Both traditional and XML-based properties file formats are supported
	 * &mdash; for example, {@code "classpath:/com/example/test.properties"}
	 * or {@code "file:/path/to/file.xml"}.
	 * <h3>Path Resource Semantics</h3>
	 * <p>Each path will be interpreted as a Spring
	 * {@link org.springframework.core.io.Resource Resource}. A plain path
	 * &mdash; for example, {@code "test.properties"} &mdash; will be treated as a
	 * classpath resource that is <em>relative</em> to the package in which the
	 * test class is defined. A path starting with a slash will be treated as an
	 * <em>absolute</em> classpath resource, for example:
	 * {@code "/org/example/test.xml"}. A path which references a
	 * URL (e.g., a path prefixed with
	 * {@link org.springframework.util.ResourceUtils#CLASSPATH_URL_PREFIX classpath:},
	 * {@link org.springframework.util.ResourceUtils#FILE_URL_PREFIX file:},
	 * {@code http:}, etc.) will be loaded using the specified resource protocol.
	 * Resource location wildcards (e.g. <code>*&#42;/*.properties</code>)
	 * are not permitted: each location must evaluate to exactly one
	 * {@code .properties} or {@code .xml} resource. Property placeholders
	 * in paths (i.e., <code>${...}</code>) will be
	 * {@linkplain org.springframework.core.env.Environment#resolveRequiredPlaceholders(String) resolved}
	 * against the {@code Environment}.
	 * <h3>Default Properties File Detection</h3>
	 * <p>See the class-level Javadoc for a discussion on detection of defaults.
	 * <h3>Precedence</h3>
	 * <p>Properties loaded from resource locations have lower precedence than
	 * inlined {@link #properties}.
	 * <p>This attribute may <strong>not</strong> be used in conjunction with
	 * {@link #value}, but it may be used <em>instead</em> of {@link #value}.
	 * @see #inheritLocations
	 * @see #value
	 * @see #properties
	 * @see org.springframework.core.env.PropertySource
	 */
	/**
	 * 要加载到{@code  Environment}的{@code  PropertySources}集中的属性文件的资源位置。 
	 * 将按照声明的顺序将每个位置作为其自己的属性源添加到封闭的{@code  Environment}中。 
	 *  <h3>受支持的文件格式</ h3> <p>同时支持传统和基于XML的属性文件格式，例如{@code "classpath：/com/example/test.properties"}或{<@代码>"文件：/path/to/file.xml"}。 
	 *  <h3>路径资源语义</ h3> <p>每个路径将被解释为Spring {@link  org.springframework.core.io.Resource Resource}。 
	 * 普通路径（例如{@code "test.properties"}）将被视为与定义测试类的包<em>相对</ em>相关的类路径资源。 
	 * 以斜杠开头的路径将被视为<em>绝对</ em>类路径资源，例如：{@code "/org/example/test.xml"}。 
	 * 引用URL的路径（例如，以{@link  org.springframework.util.ResourceUtils＃CLASSPATH_URL_PREFIX classpath：}，{<@link> org.springframework.util.ResourceUtils＃FILE_URL_PREFIX file：}，{ @code  http：}等）将使用指定的资源协议加载。 
	 * 不允许使用资源位置通配符（例如<code> * .properties </ code>）：每个位置必须精确评估为一个{@code  .properties}或{@code  .xml}资源。 
	 * 路径中的属性占位符（即<code> $ {...} </ code>）将是{@link  plain org.springframework.core.env.Environment＃resolveRequiredPlaceholders（String）resolve}针对{<@代码>环境}。 
	 *  <h3>默认属性文件检测</ h3> <p>有关检测默认值的讨论，请参见类级Javadoc。 
	 *  <h3>优先级</ h3> <p>从资源位置加载的属性的优先级低于内联的{@link  #properties}。 
	 *  <p>此属性可能<strong>不</ strong>与{@link  #value}结合使用，但可以代替{@link  #value}的<em> </ em>使用。 
	 *  
	 * @see  #inheritLocations 
	 * @see  #value 
	 * @see  #properties 
	 * @see  org.springframework.core.env.PropertySource
	 */
	@AliasFor("value")
	String[] locations() default {};

	/**
	 * Whether or not test property source {@link #locations} from superclasses
	 * should be <em>inherited</em>.
	 * <p>The default value is {@code true}, which means that a test class will
	 * <em>inherit</em> property source locations defined by a superclass.
	 * Specifically, the property source locations for a test class will be
	 * appended to the list of property source locations defined by a superclass.
	 * Thus, subclasses have the option of <em>extending</em> the list of test
	 * property source locations.
	 * <p>If {@code inheritLocations} is set to {@code false}, the property
	 * source locations for the test class will <em>shadow</em> and effectively
	 * replace any property source locations defined by a superclass.
	 * <p>In the following example, the {@code ApplicationContext} for
	 * {@code BaseTest} will be loaded using only the {@code "base.properties"}
	 * file as a test property source. In contrast, the {@code ApplicationContext}
	 * for {@code ExtendedTest} will be loaded using the {@code "base.properties"}
	 * <strong>and</strong> {@code "extended.properties"} files as test property
	 * source locations.
	 * <pre class="code">
	 * &#064;TestPropertySource(&quot;base.properties&quot;)
	 * &#064;ContextConfiguration
	 * public class BaseTest {
	 *   // ...
	 * }
	 *
	 * &#064;TestPropertySource(&quot;extended.properties&quot;)
	 * &#064;ContextConfiguration
	 * public class ExtendedTest extends BaseTest {
	 *   // ...
	 * }</pre>
	 * <p>If {@code @TestPropertySource} is used as a <em>{@linkplain Repeatable
	 * repeatable}</em> annotation, the following special rules apply.
	 * <ol>
	 * <li>All {@code @TestPropertySource} annotations at a given level in the
	 * test class hierarchy (i.e., directly present or meta-present on a test
	 * class) are considered to be <em>local</em> annotations, in contrast to
	 * {@code @TestPropertySource} annotations that are inherited from a
	 * superclass.</li>
	 * <li>All local {@code @TestPropertySource} annotations must declare the
	 * same value for the {@code inheritLocations} flag.</li>
	 * <li>The {@code inheritLocations} flag is not taken into account between
	 * local {@code @TestPropertySource} annotations. Specifically, the property
	 * source locations for one local annotation will be appended to the list of
	 * property source locations defined by previous local annotations. This
	 * allows a local annotation to extend the list of test property source
	 * locations, potentially overriding individual properties.</li>
	 * </ol>
	 * @see #locations
	 */
	/**
	 * 是否应该继承来自超类的测试属性源{@link  #locations}。 
	 *  <p>默认值为{@code  true}，这意味着测试类将<em>继承</ em>由超类定义的属性源位置。 
	 * 具体来说，测试类的属性源位置将附加到超类定义的属性源位置列表中。 
	 * 因此，子类可以选择<em>扩展</ em>测试属性源位置列表。 
	 *  <p>如果{@code  InheritLocations}设置为{@code  false}，则测试类的属性源位置将成为<em> shadow </ em>，并有效替换超类定义的所有属性源位置。 
	 *  <p>在以下示例中，将仅使用{@code "base.properties"}文件作为测试属性源来加载{@code  BaseTest}的{@code  ApplicationContext}。 
	 * 相比之下，将使用{@code "base.properties"} <strong>和</ strong> {@code "extended来加载{@code  ExtendedTest}的{@code  ApplicationContext} .properties"}文件作为测试属性源位置。 
	 *  <pre class ="code"> @TestPropertySource（"base.properties"）@ContextConfiguration公共类BaseTest {// ...} @TestPropertySource（"extended.properties"）@ContextConfiguration公共类ExtendedTest扩展了BaseTest {// .. 。 
	 * } </ pre> <p>如果将{@code  @TestPropertySource}用作<em> {<@link> plain Repeatable repeatable} </ em>注释，则适用以下特殊规则。 
	 *  <ol> <li>在测试类层次结构中给定级别的所有{@code  @TestPropertySource}注解（即，直接存在于测试类中或元存在于测试类中）都被认为是<em> local </ em >注解，而不是从超类继承的{@code  @TestPropertySource}注解。 
	 * </ li> <li>所有本地{@code  @TestPropertySource}注解必须为{<@</ li> <li>在本地{@code  @TestPropertySource}注解之间不考虑{@code  InheritLocations}标志。 
	 * 具体来说，一个本地注释的属性源位置将被附加到先前本地注释定义的属性源位置列表中。 
	 * 这允许本地注释扩展测试属性源位置的列表，从而有可能覆盖单个属性。 
	 * </ li> </ ol> 
	 * @see  #locations
	 */
	boolean inheritLocations() default true;

	/**
	 * <em>Inlined properties</em> in the form of <em>key-value</em> pairs that
	 * should be added to the Spring
	 * {@link org.springframework.core.env.Environment Environment} before the
	 * {@code ApplicationContext} is loaded for the test. All key-value pairs
	 * will be added to the enclosing {@code Environment} as a single test
	 * {@code PropertySource} with the highest precedence.
	 * <h3>Supported Syntax</h3>
	 * <p>The supported syntax for key-value pairs is the same as the
	 * syntax defined for entries in a Java
	 * {@linkplain java.util.Properties#load(java.io.Reader) properties file}:
	 * <ul>
	 * <li>{@code "key=value"}</li>
	 * <li>{@code "key:value"}</li>
	 * <li>{@code "key value"}</li>
	 * </ul>
	 * <h3>Precedence</h3>
	 * <p>Properties declared via this attribute have higher precedence than
	 * properties loaded from resource {@link #locations}.
	 * <p>This attribute may be used in conjunction with {@link #value}
	 * <em>or</em> {@link #locations}.
	 * @see #inheritProperties
	 * @see #locations
	 * @see org.springframework.core.env.PropertySource
	 */
	/**
	 * 以<em>键值</ em>对形式的<em>内联属性</ em>，应在{前添加到Spring {@link  org.springframework.core.env.Environment Environment}中@code  ApplicationContext}已加载以进行测试。 
	 * 所有键值对将作为优先级最高的单个测试{@code  PropertySource}添加到封闭的{@code  Environment}中。 
	 *  <h3>支持的语法</ h3> <p>键值对的支持语法与Java {@link  plain java.util.Properties＃load（java.io。 
	 * 读者）属性文件}：<ul> <li> {<@code>"key = value"} </ li> <li> {<@code>"key：value"} </ li> <li> {@code "键值"} </ li> </ ul> <h3>优先级</ h3> <p>通过此属性声明的属性的优先级高于从资源{@link  #locations}加载的属性。 
	 *  <p>此属性可以与{@link  #value} <em>或</ em> {@link  #locations}结合使用。 
	 *  
	 * @see  #inheritProperties 
	 * @see  #locations 
	 * @see  org.springframework.core.env.PropertySource
	 */
	String[] properties() default {};

	/**
	 * Whether or not inlined test {@link #properties} from superclasses should
	 * be <em>inherited</em>.
	 * <p>The default value is {@code true}, which means that a test class will
	 * <em>inherit</em> inlined properties defined by a superclass. Specifically,
	 * the inlined properties for a test class will be appended to the list of
	 * inlined properties defined by a superclass. Thus, subclasses have the
	 * option of <em>extending</em> the list of inlined test properties.
	 * <p>If {@code inheritProperties} is set to {@code false}, the inlined
	 * properties for the test class will <em>shadow</em> and effectively
	 * replace any inlined properties defined by a superclass.
	 * <p>In the following example, the {@code ApplicationContext} for
	 * {@code BaseTest} will be loaded using only the inlined {@code key1}
	 * property. In contrast, the {@code ApplicationContext} for
	 * {@code ExtendedTest} will be loaded using the inlined {@code key1}
	 * <strong>and</strong> {@code key2} properties.
	 * <pre class="code">
	 * &#064;TestPropertySource(properties = &quot;key1 = value1&quot;)
	 * &#064;ContextConfiguration
	 * public class BaseTest {
	 *   // ...
	 * }
	 * &#064;TestPropertySource(properties = &quot;key2 = value2&quot;)
	 * &#064;ContextConfiguration
	 * public class ExtendedTest extends BaseTest {
	 *   // ...
	 * }</pre>
	 * <p>If {@code @TestPropertySource} is used as a <em>{@linkplain Repeatable
	 * repeatable}</em> annotation, the following special rules apply.
	 * <ol>
	 * <li>All {@code @TestPropertySource} annotations at a given level in the
	 * test class hierarchy (i.e., directly present or meta-present on a test
	 * class) are considered to be <em>local</em> annotations, in contrast to
	 * {@code @TestPropertySource} annotations that are inherited from a
	 * superclass.</li>
	 * <li>All local {@code @TestPropertySource} annotations must declare the
	 * same value for the {@code inheritProperties} flag.</li>
	 * <li>The {@code inheritProperties} flag is not taken into account between
	 * local {@code @TestPropertySource} annotations. Specifically, inlined
	 * properties for one local annotation will be appended to the list of
	 * inlined properties defined by previous local annotations. This allows a
	 * local annotation to extend the list of inlined properties, potentially
	 * overriding individual properties.</li>
	 * </ol>
	 * @see #properties
	 */
	/**
	 * 是否应该继承超类的内联测试{@link  #properties}。 
	 *  <p>默认值为{@code  true}，这意味着测试类将<em>继承</ em>由超类定义的内联属性。 
	 * 具体来说，测试类的内联属性将附加到超类定义的内联属性列表中。 
	 * 因此，子类可以选择<em>扩展</ em>内联测试属性列表。 
	 *  <p>如果{@code  InheritedProperties}设置为{@code  false}，则测试类的内联属性将<em> shadow </ em>并有效替换超类定义的所有内联属性。 
	 *  <p>在下面的示例中，将仅使用内联的{@code  key1}属性来加载{@code  BaseTest}的{@code  ApplicationContext}。 
	 * 相反，将使用内联的{@code  key1} <strong>和</ strong> {@code  key2}属性来加载{@code  ExtendedTest}的{@code  ApplicationContext}。 
	 *  <pre class ="code"> @TestPropertySource（属性="key1 = value1"）@ContextConfiguration公共类BaseTest {// ...} @TestPropertySource（属性="key2 = value2"）@ContextConfiguration公共类ExtendedTest扩展了BaseTest { // ...} </ pre> <p>如果将{@code  @TestPropertySource}用作<em> {<@link> plain Repeatable repeatable} </ em>注解，则适用以下特殊规则。 
	 *  <ol> <li>在测试类层次结构中给定级别的所有{@code  @TestPropertySource}注解（即，直接存在于测试类中或元存在于测试类中）都被认为是<em> local </ em >注解，而不是从超类继承的{@code  @TestPropertySource}注解。 
	 * </ li> <li>所有本地{@code  @TestPropertySource}注解必须为{<@</ li> <li>在本地{@code  @TestPropertySource}注释之间不考虑{@code  InheritProperties}标志。 
	 * 具体来说，一个本地注释的内联属性将被附加到以前的本地注释定义的内联属性列表中。 
	 * 这允许本地注释扩展内联属性的列表，从而有可能覆盖单个属性。 
	 * </ li> </ ol> 
	 * @see  #properties
	 */
	boolean inheritProperties() default true;

}
