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

package org.springframework.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Annotation indicating that a method (or all methods on a class) triggers a
 * {@link org.springframework.cache.Cache#evict(Object) cache evict} operation.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em> with attribute overrides.
 *
 * @author Costin Leau
 * @author Stephane Nicoll
 * @author Sam Brannen
 * @since 3.1
 * @see CacheConfig
 */
/**
 * 指示方法（或类上的所有方法）触发{{@link> org.springframework.cache.Cache＃Cache＃evict（Object）cache evict}操作的注释。 
 *  <p>此注释可用作<em>元注释</ em>，以创建具有属性覆盖的自定义<em>组成的注释</ em>。 
 *  @author  Costin Leau @author  Stephane Nicoll @author  Sam Brannen @从3.1开始
 * @see  CacheConfig
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvict {

	/**
	 * Alias for {@link #cacheNames}.
	 */
	/**
	 * {@link  #cacheNames}的别名。 
	 * 
	 */
	@AliasFor("cacheNames")
	String[] value() default {};

	/**
	 * Names of the caches to use for the cache eviction operation.
	 * <p>Names may be used to determine the target cache (or caches), matching
	 * the qualifier value or bean name of a specific bean definition.
	 * @since 4.2
	 * @see #value
	 * @see CacheConfig#cacheNames
	 */
	/**
	 * 用于缓存逐出操作的缓存名称。 
	 *  <p>名称可以用来确定目标缓存（或多个缓存），与特定bean定义的限定符值或bean名称匹配。 
	 *  @since 4.2 
	 * @see  #value 
	 * @see  CacheConfig＃cacheNames
	 */
	@AliasFor("value")
	String[] cacheNames() default {};

	/**
	 * Spring Expression Language (SpEL) expression for computing the key dynamically.
	 * <p>Default is {@code ""}, meaning all method parameters are considered as a key,
	 * unless a custom {@link #keyGenerator} has been set.
	 * <p>The SpEL expression evaluates against a dedicated context that provides the
	 * following meta-data:
	 * <ul>
	 * <li>{@code #result} for a reference to the result of the method invocation, which
	 * can only be used if {@link #beforeInvocation()} is {@code false}. For supported
	 * wrappers such as {@code Optional}, {@code #result} refers to the actual object,
	 * not the wrapper</li>
	 * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
	 * references to the {@link java.lang.reflect.Method method}, target object, and
	 * affected cache(s) respectively.</li>
	 * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
	 * ({@code #root.targetClass}) are also available.
	 * <li>Method arguments can be accessed by index. For instance the second argument
	 * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
	 * can also be accessed by name if that information is available.</li>
	 * </ul>
	 */
	/**
	 * Spring Expression Language（SpEL）表达式，用于动态计算密钥。 
	 *  <p>默认值为{@code ""}，这意味着除非已设置自定义{@link  #keyGenerator}，否则所有方法参数均视为键。 
	 *  <p> SpEL表达式根据提供以下元数据的专用上下文进行评估：<ul> <li> {<@code> #result}，用于引用方法调用的结果，仅在以下情况下可以使用{@link  #beforeInvocation（）}为{@code  false}。 
	 * 对于受支持的包装器，例如{@code  Optional}，{<@code> #result}指向实际对象，而不是包装器</ li> <li> {<@code>＃root.method}，{@code ＃root.target}和{@code ＃root.caches}分别引用{@link  java.lang.reflect.Method方法}，目标对象和受影响的缓存。 
	 *  </ li> <li>还提供方法名称（{@code ＃root.methodName}）和目标类（{@code ＃root.targetClass}）的快捷方式。 
	 *  <li>方法参数可以通过索引访问。 
	 * 例如，可以通过{@code ＃root.args [1]}，{<@code>＃p1}或{@code ＃a1}访问第二个参数。 
	 * 如果该信息可用，也可以按名称访问参数。 
	 * </ li> </ ul>
	 */
	String key() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.interceptor.KeyGenerator}
	 * to use.
	 * <p>Mutually exclusive with the {@link #key} attribute.
	 * @see CacheConfig#keyGenerator
	 */
	/**
	 * 要使用的自定义{@link  org.springframework.cache.interceptor.KeyGenerator}的bean名称。 
	 *  <p>与{@link  #key}属性互斥。 
	 *  
	 * @see  CacheConfig＃keyGenerator
	 */
	String keyGenerator() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.CacheManager} to use to
	 * create a default {@link org.springframework.cache.interceptor.CacheResolver} if none
	 * is set already.
	 * <p>Mutually exclusive with the {@link #cacheResolver} attribute.
	 * @see org.springframework.cache.interceptor.SimpleCacheResolver
	 * @see CacheConfig#cacheManager
	 */
	/**
	 * 自定义{@link  org.springframework.cache.CacheManager}的Bean名称，用于创建默认的{@link  org.springframework.cache.interceptor.CacheResolver}（如果尚未设置）。 
	 *  <p>与{@link  #cacheResolver}属性互斥。 
	 *  
	 * @see  org.springframework.cache.interceptor.SimpleCacheResolver 
	 * @see  CacheConfig＃cacheManager
	 */
	String cacheManager() default "";

	/**
	 * The bean name of the custom {@link org.springframework.cache.interceptor.CacheResolver}
	 * to use.
	 * @see CacheConfig#cacheResolver
	 */
	/**
	 * 要使用的定制{@link  org.springframework.cache.interceptor.CacheResolver}的bean名称。 
	 *  
	 * @see  CacheConfig＃cacheResolver
	 */
	String cacheResolver() default "";

	/**
	 * Spring Expression Language (SpEL) expression used for making the cache
	 * eviction operation conditional.
	 * <p>Default is {@code ""}, meaning the cache eviction is always performed.
	 * <p>The SpEL expression evaluates against a dedicated context that provides the
	 * following meta-data:
	 * <ul>
	 * <li>{@code #root.method}, {@code #root.target}, and {@code #root.caches} for
	 * references to the {@link java.lang.reflect.Method method}, target object, and
	 * affected cache(s) respectively.</li>
	 * <li>Shortcuts for the method name ({@code #root.methodName}) and target class
	 * ({@code #root.targetClass}) are also available.
	 * <li>Method arguments can be accessed by index. For instance the second argument
	 * can be accessed via {@code #root.args[1]}, {@code #p1} or {@code #a1}. Arguments
	 * can also be accessed by name if that information is available.</li>
	 * </ul>
	 */
	/**
	 * Spring表达式语言（SpEL）表达式，用于使缓存逐出操作成为条件。 
	 *  <p>默认值为{@code ""}，这意味着始终执行缓存逐出。 
	 *  <p> SpEL表达式根据提供以下元数据的专用上下文进行评估：<ul> <li> {<@code>＃root.method}，{<@code>＃root.target}和{@code ＃root.caches}分别引用{@link  java.lang.reflect.Method方法}，目标对象和受影响的缓存。 
	 * </ li> <li>方法名称的快捷方式（{@code ＃root.methodName}）和目标类（{@code ＃root.targetClass}）也可用。 
	 *  <li>方法参数可以通过索引访问。 
	 * 例如，可以通过{@code ＃root.args [1]}，{<@code>＃p1}或{@code ＃a1}访问第二个参数。 
	 * 如果该信息可用，也可以按名称访问参数。 
	 * </ li> </ ul>
	 */
	String condition() default "";

	/**
	 * Whether all the entries inside the cache(s) are removed.
	 * <p>By default, only the value under the associated key is removed.
	 * <p>Note that setting this parameter to {@code true} and specifying a
	 * {@link #key} is not allowed.
	 */
	/**
	 * 是否删除缓存内的所有条目。 
	 *  <p>默认情况下，仅删除关联键下的值。 
	 *  <p>请注意，不允许将此参数设置为{@code  true}并指定{@link  #key}。 
	 * 
	 */
	boolean allEntries() default false;

	/**
	 * Whether the eviction should occur before the method is invoked.
	 * <p>Setting this attribute to {@code true}, causes the eviction to
	 * occur irrespective of the method outcome (i.e., whether it threw an
	 * exception or not).
	 * <p>Defaults to {@code false}, meaning that the cache eviction operation
	 * will occur <em>after</em> the advised method is invoked successfully (i.e.
	 * only if the invocation did not throw an exception).
	 */
	/**
	 * 在调用该方法之前是否应进行驱逐。 
	 *  <p>将此属性设置为{@code  true}，将导致驱逐发生，而与方法结果无关（即，是否引发异常）。 
	 *  <p>默认为{@code  false}，这意味着将在成功调用建议方法之后（即仅在调用未引发异常的情况下）</ em>后进行缓存逐出操作。 
	 * 
	 */
	boolean beforeInvocation() default false;

}
