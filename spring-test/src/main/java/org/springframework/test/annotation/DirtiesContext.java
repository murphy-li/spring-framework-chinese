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

package org.springframework.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation which indicates that the
 * {@link org.springframework.context.ApplicationContext ApplicationContext}
 * associated with a test is <em>dirty</em> and should therefore be closed
 * and removed from the context cache.
 *
 * <p>Use this annotation if a test has modified the context &mdash; for
 * example, by modifying the state of a singleton bean, modifying the state
 * of an embedded database, etc. Subsequent tests that request the same
 * context will be supplied a new context.
 *
 * <p>{@code @DirtiesContext} may be used as a class-level and method-level
 * annotation within the same class or class hierarchy. In such scenarios, the
 * {@code ApplicationContext} will be marked as <em>dirty</em> before or
 * after any such annotated method as well as before or after the current test
 * class, depending on the configured {@link #methodMode} and {@link #classMode}.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em>.
 *
 * <h3>Supported Test Phases</h3>
 * <ul>
 * <li><strong>Before current test class</strong>: when declared at the class
 * level with class mode set to {@link ClassMode#BEFORE_CLASS BEFORE_CLASS}</li>
 * <li><strong>Before each test method in current test class</strong>: when
 * declared at the class level with class mode set to
 * {@link ClassMode#BEFORE_EACH_TEST_METHOD BEFORE_EACH_TEST_METHOD}</li>
 * <li><strong>Before current test method</strong>: when declared at the
 * method level with method mode set to
 * {@link MethodMode#BEFORE_METHOD BEFORE_METHOD}</li>
 * <li><strong>After current test method</strong>: when declared at the
 * method level with method mode set to
 * {@link MethodMode#AFTER_METHOD AFTER_METHOD}</li>
 * <li><strong>After each test method in current test class</strong>: when
 * declared at the class level with class mode set to
 * {@link ClassMode#AFTER_EACH_TEST_METHOD AFTER_EACH_TEST_METHOD}</li>
 * <li><strong>After current test class</strong>: when declared at the
 * class level with class mode set to
 * {@link ClassMode#AFTER_CLASS AFTER_CLASS}</li>
 * </ul>
 *
 * <p>{@code BEFORE_*} modes are supported by the
 * {@link org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener DirtiesContextBeforeModesTestExecutionListener};
 * {@code AFTER_*} modes are supported by the
 * {@link org.springframework.test.context.support.DirtiesContextTestExecutionListener DirtiesContextTestExecutionListener}.
 *
 * @author Sam Brannen
 * @author Rod Johnson
 * @since 2.0
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener
 * @see org.springframework.test.context.support.DirtiesContextTestExecutionListener
 */
/**
 * 测试注解，它指示与测试关联的{@link  org.springframework.context.ApplicationContext ApplicationContext}是<em> dirty </ em>，因此应关闭并从上下文缓存中删除。 
 *  <p>如果测试已修改上下文（例如，通过修改单例bean的状态，修改嵌入式数据库的状态等），请使用此注释。 
 * 将向请求相同上下文的后续测试提供新的上下文。 
 *  <p> {<@code> @DirtiesContext}可以用作同一类或类层次结构中的类级别和方法级别的注释。 
 * 在这种情况下，{<@code> ApplicationContext}将在任何此类带注释的方法之前或之后以及当前测试类之前或之后被标记为<em> dirty </ em>，具体取决于配置的{<@link > #methodMode}和{@link  #classMode}。 
 *  <p>此注释可用作<em>元注释</ em>，以创建自定义的<em>组成的注释</ em>。 
 *  <h3>支持的测试阶段</ h3> <ul> <li> <strong>在当前测试类之前</ strong>：在类级别设置为{@link  ClassMode＃BEFORE_CLASS BEFORE_CLASS}的类级别声明时< / li> <li> <strong>当前测试类中的每个测试方法之前</ strong>：在类级别设置为{@link  ClassMode＃BEFORE_EACH_TEST_METHOD BEFORE_EACH_TEST_METHOD}的类级别声明时</ li> <li> <strong>在当前测试方法之前</ strong>：在方法级别设置为{@link  MethodMode＃BEFORE_METHOD BEFORE_METHOD}的方法级别声明时</ li> <li> <strong>在当前测试方法之后</ strong> >：在方法级别设置为{@link  MethodMode＃AFTER_METHOD AFTER_METHOD} </ li>的方法级别声明时</ li> <li> <strong>当前测试类中的每个测试方法之后</ strong>：在将课程模式设置为{@link  ClassMode＃AFTER_EACH_TEST_METHOD AFTER_EACH_TEST_METHOD} </ li> <li> <strong>当前测试课程后</ strong>：在设置了课程模式的课程级别上声明时{@link  org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener到{@link  ClassMode＃AFTER_CLASS AFTER_CLASS} </ li> </ ul> <p> {<@code> BEFORE_}模式DirtiesContextBeforeModesTestExecutionListener}; {@link  org.springframework.test.context.support.DirtiesContextTestExecutionListener DirtiesContextTestExecutionListener}支持{@code  AFTER_}模式。 
 *  @author  Sam Brannen @author  Rod Johnson @since 2.0起
 * @see  org.springframework.test.context.ContextConfiguration 
 * @see  org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener 
 * @see  org.springframework .test.context.support.DirtiesContextTestExecutionListener
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DirtiesContext {

	/**
	 * The <i>mode</i> to use when a test method is annotated with
	 * {@code @DirtiesContext}.
	 * <p>Defaults to {@link MethodMode#AFTER_METHOD AFTER_METHOD}.
	 * <p>Setting the method mode on an annotated test class has no meaning.
	 * For class-level control, use {@link #classMode} instead.
	 * @since 4.2
	 */
	/**
	 * 在用{@code  @DirtiesContext}注释测试方法时使用的<i>模式</ i>。 
	 *  <p>默认为{@link  MethodMode＃AFTER_METHOD AFTER_METHOD}。 
	 *  <p>在带注释的测试类上设置方法模式没有任何意义。 
	 * 对于类级别的控制，请改用{@link  #classMode}。 
	 *  @4.2起
	 */
	MethodMode methodMode() default MethodMode.AFTER_METHOD;

	/**
	 * The <i>mode</i> to use when a test class is annotated with
	 * {@code @DirtiesContext}.
	 * <p>Defaults to {@link ClassMode#AFTER_CLASS AFTER_CLASS}.
	 * <p>Setting the class mode on an annotated test method has no meaning.
	 * For method-level control, use {@link #methodMode} instead.
	 * @since 3.0
	 */
	/**
	 * 使用{@code  @DirtiesContext}注释测试类时使用的<i>模式</ i>。 
	 *  <p>默认为{@link  ClassMode＃AFTER_CLASS AFTER_CLASS}。 
	 *  <p>在带注释的测试方法上设置类模式没有任何意义。 
	 * 对于方法级控制，请改为使用{@link  #methodMode}。 
	 *  @从3.0起
	 */
	ClassMode classMode() default ClassMode.AFTER_CLASS;

	/**
	 * The context cache clearing <em>mode</em> to use when a context is
	 * configured as part of a hierarchy via
	 * {@link org.springframework.test.context.ContextHierarchy @ContextHierarchy}.
	 * <p>Defaults to {@link HierarchyMode#EXHAUSTIVE EXHAUSTIVE}.
	 * @since 3.2.2
	 */
	/**
	 * 通过{@link  org.springframework.test.context.ContextHierarchy @ContextHierarchy}将上下文配置为层次结构的一部分时使用的上下文缓存清除<em> mode </ em>。 
	 *  <p>默认为{@link  HierarchyMode＃EXHAUSTIVE EXHAUSTIVE}。 
	 *  @从3.2.2开始
	 */
	HierarchyMode hierarchyMode() default HierarchyMode.EXHAUSTIVE;


	/**
	 * Defines <i>modes</i> which determine how {@code @DirtiesContext} is
	 * interpreted when used to annotate a test method.
	 * @since 4.2
	 */
	/**
	 * 定义<i>模式</ i>，该模式确定在用于注释测试方法时如何解释{@code  @DirtiesContext}。 
	 *  @4.2起
	 */
	enum MethodMode {

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> before the corresponding test method.
		 */
		/**
		 * 关联的{@code  ApplicationContext}将在相应的测试方法之前标记为<em> dirty </ em>。 
		 * 
		 */
		BEFORE_METHOD,

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> after the corresponding test method.
		 */
		/**
		 * 在相应的测试方法之后，关联的{@code  ApplicationContext}将被标记为<em> dirty </ em>。 
		 * 
		 */
		AFTER_METHOD;
	}


	/**
	 * Defines <i>modes</i> which determine how {@code @DirtiesContext} is
	 * interpreted when used to annotate a test class.
	 * @since 3.0
	 */
	/**
	 * 定义<i>模式</ i>，该模式确定用于注释测试类时如何解释{@code  @DirtiesContext}。 
	 *  @从3.0起
	 */
	enum ClassMode {

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> before the test class.
		 *
		 * @since 4.2
		 */
		/**
		 * 关联的{@code  ApplicationContext}将在测试类之前标记为<em> dirty </ em>。 
		 *  @4.2起
		 */
		BEFORE_CLASS,

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> before each test method in the class.
		 *
		 * @since 4.2
		 */
		/**
		 * 相关的{@code  ApplicationContext}将在类中的每个测试方法之前标记为<em> dirty </ em>。 
		 *  @4.2起
		 */
		BEFORE_EACH_TEST_METHOD,

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> after each test method in the class.
		 */
		/**
		 * 在类中的每个测试方法之后，关联的{@code  ApplicationContext}将被标记为<em> dirty </ em>。 
		 * 
		 */
		AFTER_EACH_TEST_METHOD,

		/**
		 * The associated {@code ApplicationContext} will be marked as
		 * <em>dirty</em> after the test class.
		 */
		/**
		 * 在测试类之后，关联的{@code  ApplicationContext}将被标记为<em> dirty </ em>。 
		 * 
		 */
		AFTER_CLASS;
	}


	/**
	 * Defines <i>modes</i> which determine how the context cache is cleared
	 * when {@code @DirtiesContext} is used in a test whose context is
	 * configured as part of a hierarchy via
	 * {@link org.springframework.test.context.ContextHierarchy @ContextHierarchy}.
	 * @since 3.2.2
	 */
	/**
	 * 定义<i>模式</ i>，这些模式确定在通过{@link  org.springframework将上下文配置为层次结构一部分的测试中使用{@code  @DirtiesContext}时如何清除上下文缓存。 
	 *  test.context.ContextHierarchy @ContextHierarchy}。 
	 *  @从3.2.2开始
	 */
	enum HierarchyMode {

		/**
		 * The context cache will be cleared using an <em>exhaustive</em> algorithm
		 * that includes not only the {@linkplain HierarchyMode#CURRENT_LEVEL current level}
		 * but also all other context hierarchies that share an ancestor context
		 * common to the current test.
		 *
		 * <p>All {@code ApplicationContexts} that reside in a subhierarchy of
		 * the common ancestor context will be removed from the context cache and
		 * closed.
		 */
		/**
		 * 将使用<em>穷举</ em>算法清除上下文缓存，该算法不仅包括{@link  plain HierarchyMode＃CURRENT_LEVEL current level}，而且还包括共享当前测试所共有的祖先上下文的所有其他上下文层次结构。 
		 *  <p>驻留在公共祖先上下文的子层次结构中的所有{@code  ApplicationContexts}将从上下文缓存中删除并关闭。 
		 * 
		 */
		EXHAUSTIVE,

		/**
		 * The {@code ApplicationContext} for the <em>current level</em> in the
		 * context hierarchy and all contexts in subhierarchies of the current
		 * level will be removed from the context cache and closed.
		 *
		 * <p>The <em>current level</em> refers to the {@code ApplicationContext}
		 * at the lowest level in the context hierarchy that is visible from the
		 * current test.
		 */
		/**
		 * 上下文层次结构中<em>当前级别</ em>的{@code  ApplicationContext}和当前级别的子层次结构中的所有上下文将从上下文缓存中删除并关闭。 
		 *  <p> <em>当前级别</ em>是指从当前测试可见的上下文层次结构中最低级别的{@code  ApplicationContext}。 
		 * 
		 */
		CURRENT_LEVEL;
	}

}
