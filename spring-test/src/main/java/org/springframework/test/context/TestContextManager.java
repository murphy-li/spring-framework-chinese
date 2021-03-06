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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * {@code TestContextManager} is the main entry point into the <em>Spring
 * TestContext Framework</em>.
 *
 * <p>Specifically, a {@code TestContextManager} is responsible for managing a
 * single {@link TestContext} and signaling events to each registered
 * {@link TestExecutionListener} at the following test execution points.
 *
 * <ul>
 * <li>{@link #beforeTestClass() before test class execution}: prior to any
 * <em>before class callbacks</em> of a particular testing framework (e.g.,
 * JUnit 4's {@link org.junit.BeforeClass @BeforeClass})</li>
 * <li>{@link #prepareTestInstance test instance preparation}:
 * immediately following instantiation of the test class</li>
 * <li>{@link #beforeTestMethod before test setup}:
 * prior to any <em>before method callbacks</em> of a particular testing framework
 * (e.g., JUnit 4's {@link org.junit.Before @Before})</li>
 * <li>{@link #beforeTestExecution before test execution}:
 * immediately before execution of the {@linkplain java.lang.reflect.Method
 * test method} but after test setup</li>
 * <li>{@link #afterTestExecution after test execution}:
 * immediately after execution of the {@linkplain java.lang.reflect.Method
 * test method} but before test tear down</li>
 * <li>{@link #afterTestMethod(Object, Method, Throwable) after test tear down}:
 * after any <em>after method callbacks</em> of a particular testing
 * framework (e.g., JUnit 4's {@link org.junit.After @After})</li>
 * <li>{@link #afterTestClass() after test class execution}: after any
 * <em>after class callbacks</em> of a particular testing framework (e.g., JUnit 4's
 * {@link org.junit.AfterClass @AfterClass})</li>
 * </ul>
 *
 * <p>Support for loading and accessing
 * {@linkplain org.springframework.context.ApplicationContext application contexts},
 * dependency injection of test instances,
 * {@linkplain org.springframework.transaction.annotation.Transactional transactional}
 * execution of test methods, etc. is provided by
 * {@link SmartContextLoader ContextLoaders} and {@code TestExecutionListeners},
 * which are configured via {@link ContextConfiguration @ContextConfiguration} and
 * {@link TestExecutionListeners @TestExecutionListeners}, respectively.
 *
 * <p>Bootstrapping of the {@code TestContext}, the default {@code ContextLoader},
 * default {@code TestExecutionListeners}, and their collaborators is performed
 * by a {@link TestContextBootstrapper}, which is configured via
 * {@link BootstrapWith @BootstrapWith}.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 * @see BootstrapWith
 * @see BootstrapContext
 * @see TestContextBootstrapper
 * @see TestContext
 * @see TestExecutionListener
 * @see TestExecutionListeners
 * @see ContextConfiguration
 * @see ContextHierarchy
 */
/**
 * {@code  TestContextManager}是<em> Spring TestContext Framework </ em>的主要入口点。 
 *  <p>具体来说，{<@code> TestContextManager}负责管理单个{@link  TestContext}，并在以下测试执行点向每个已注册的{@link  TestExecutionListener}发送信号事件。 
 *  <ul> <li> {<@link> #beforeTestClass（）在执行测试类之前}：在特定测试框架（例如，JUnit 4的{@link  org）的任何<em> before类回调</ em>之前.junit.BeforeClass @BeforeClass}）</ li> <li> {<@link> #prepareTestInstance测试实例准备}：在实例化测试类之后立即</ li> <li> {<@link> #beforeTestMethod在测试之前setup}：在特定测试框架的任何<em> before方法回调</ em>之前（例如，JUnit 4的{@link  org.junit.Before @Before}）</ li> <li> {<@link> #beforeTestExecution在测试执行之前}：在执行{@link  plain java.lang.reflect.Method测试方法}之前紧接在测试设置之后</ li> <li> {<@link> #afterTestExecution在测试之后执行}：执行{@link  plain java.lang.reflect.Method测试方法}之后但在测试拆解之前</ li> <li> {<@link> #afterTestMethod（Object，Method，Throwable）测试结束后}：在任意<em> after方法回调</ em>之后特殊的测试框架（例如，JUnit 4的{@link  org.junit.After @After}）</ li> <li> {<@link> #afterTestClass（）测试类执行之后}：在任何<em>之后特定测试框架的类回调</ em>（例如，JUnit 4的{@link  org.junit.AfterClass @AfterClass}）</ li> </ ul> <p>支持加载和访问{<@link {<@link]提供了> plain org.springframework.context.ApplicationContext应用程序上下文}，依赖注入的测试实例，{<@link> plain org.springframework.transaction.annotation.Transactional transactional}执行测试方法等。 
 *  > SmartContextLoader ContextLoaders}和{@code  TestExecutionListeners}，它们分别通过{@link  ContextConfiguration @ContextConfiguration}和{@link  TestExecutionListeners @TestExecutionListeners}进行配置。 
 *  <p> {<@code> TestContext}，默认{@code  ContextLoader}，默认{@code  TestExecutionListeners}及其协作者的引导是由{@link  TestContextBootstrapper}执行的通过{@link  BootstrapWith @BootstrapWith}配置。 
 *  @author  Sam Brannen @author  Juergen Hoeller @since 2.5起
 * @see  ContextHierarchy
 */
public class TestContextManager {

	private static final Log logger = LogFactory.getLog(TestContextManager.class);

	private final TestContext testContext;

	private final ThreadLocal<TestContext> testContextHolder = ThreadLocal.withInitial(
			// Implemented as an anonymous inner class instead of a lambda expression due to a bug
			// in Eclipse IDE: "The blank final field testContext may not have been initialized"
			new Supplier<TestContext>() {
				@Override
				public TestContext get() {
					return copyTestContext(TestContextManager.this.testContext);
				}
			});

	private final List<TestExecutionListener> testExecutionListeners = new ArrayList<>();


	/**
	 * Construct a new {@code TestContextManager} for the supplied {@linkplain Class test class}.
	 * <p>Delegates to {@link #TestContextManager(TestContextBootstrapper)} with
	 * the {@link TestContextBootstrapper} configured for the test class. If the
	 * {@link BootstrapWith @BootstrapWith} annotation is present on the test
	 * class, either directly or as a meta-annotation, then its
	 * {@link BootstrapWith#value value} will be used as the bootstrapper type;
	 * otherwise, the {@link org.springframework.test.context.support.DefaultTestContextBootstrapper
	 * DefaultTestContextBootstrapper} will be used.
	 * @param testClass the test class to be managed
	 * @see #TestContextManager(TestContextBootstrapper)
	 */
	/**
	 * 为提供的{@link  plain Class测试类}构造一个新的{@code  TestContextManager}。 
	 *  <p>使用为测试类配置的{@link  TestContextBootstrapper}委托给{@link  #TestContextManager（TestContextBootstrapper）}。 
	 * 如果测试类上有{@link  BootstrapWith @BootstrapWith}注解（直接或作为元注释）出现，则其{@link  BootstrapWith＃value value}将用作引导程序类型； 
	 * 否则，将使用{@link  org.springframework.test.context.support.DefaultTestContextBootstrapper DefaultTestContextBootstrapper}。 
	 *  
	 * @param  testClass要管理的测试类
	 * @see  #TestContextManager（TestContextBootstrapper）
	 */
	public TestContextManager(Class<?> testClass) {
		this(BootstrapUtils.resolveTestContextBootstrapper(BootstrapUtils.createBootstrapContext(testClass)));
	}

	/**
	 * Construct a new {@code TestContextManager} using the supplied {@link TestContextBootstrapper}
	 * and {@linkplain #registerTestExecutionListeners register} the necessary
	 * {@link TestExecutionListener TestExecutionListeners}.
	 * <p>Delegates to the supplied {@code TestContextBootstrapper} for building
	 * the {@code TestContext} and retrieving the {@code TestExecutionListeners}.
	 * @param testContextBootstrapper the bootstrapper to use
	 * @see TestContextBootstrapper#buildTestContext
	 * @see TestContextBootstrapper#getTestExecutionListeners
	 * @see #registerTestExecutionListeners
	 */
	/**
	 * 使用提供的{@link  TestContextBootstrapper}和{@link  plain #registerTestExecutionListeners register}必要的{@link  TestExecutionListener TestExecutionListeners}构造一个新的{@code  TestContextManager}。 
	 *  <p>委托提供的{@code  TestContextBootstrapper}来构建{@code  TestContext}和检索{@code  TestExecutionListeners}。 
	 *  
	 * @param  testContextBootstrapper引导程序以使用
	 * @see  TestContextBootstrapper＃buildTestContext 
	 * @see  TestContextBootstrapper＃getTestExecutionListeners 
	 * @see  #registerTestExecutionListeners
	 */
	public TestContextManager(TestContextBootstrapper testContextBootstrapper) {
		this.testContext = testContextBootstrapper.buildTestContext();
		registerTestExecutionListeners(testContextBootstrapper.getTestExecutionListeners());
	}

	/**
	 * Get the {@link TestContext} managed by this {@code TestContextManager}.
	 */
	/**
	 * 获取由此{@code  TestContextManager}管理的{@link  TestContext}。 
	 * 
	 */
	public final TestContext getTestContext() {
		return this.testContextHolder.get();
	}

	/**
	 * Register the supplied list of {@link TestExecutionListener TestExecutionListeners}
	 * by appending them to the list of listeners used by this {@code TestContextManager}.
	 * @see #registerTestExecutionListeners(TestExecutionListener...)
	 */
	/**
	 * 通过将提供的{@link  TestExecutionListener TestExecutionListeners}列表附加到此{@code  TestContextManager}使用的侦听器列表中，来注册它们。 
	 *  
	 * @see  #registerTestExecutionListeners（TestExecutionListener ...）
	 */
	public void registerTestExecutionListeners(List<TestExecutionListener> testExecutionListeners) {
		registerTestExecutionListeners(testExecutionListeners.toArray(new TestExecutionListener[0]));
	}

	/**
	 * Register the supplied array of {@link TestExecutionListener TestExecutionListeners}
	 * by appending them to the list of listeners used by this {@code TestContextManager}.
	 */
	/**
	 * 通过将提供的{@link  TestExecutionListener TestExecutionListeners}数组附加到此{@code  TestContextManager}使用的侦听器列表中，来注册它们。 
	 * 
	 */
	public void registerTestExecutionListeners(TestExecutionListener... testExecutionListeners) {
		for (TestExecutionListener listener : testExecutionListeners) {
			if (logger.isTraceEnabled()) {
				logger.trace("Registering TestExecutionListener: " + listener);
			}
			this.testExecutionListeners.add(listener);
		}
	}

	/**
	 * Get the current {@link TestExecutionListener TestExecutionListeners}
	 * registered for this {@code TestContextManager}.
	 * <p>Allows for modifications, e.g. adding a listener to the beginning of the list.
	 * However, make sure to keep the list stable while actually executing tests.
	 */
	/**
	 * 获取为此{@code  TestContextManager}注册的当前{@link  TestExecutionListener TestExecutionListeners}。 
	 *  <p>允许进行修改，例如将侦听器添加到列表的开头。 
	 * 但是，在实际执行测试时，请确保列表稳定。 
	 * 
	 */
	public final List<TestExecutionListener> getTestExecutionListeners() {
		return this.testExecutionListeners;
	}

	/**
	 * Get a copy of the {@link TestExecutionListener TestExecutionListeners}
	 * registered for this {@code TestContextManager} in reverse order.
	 */
	/**
	 * 以相反的顺序获取为此{@code  TestContextManager}注册的{@link  TestExecutionListener TestExecutionListeners}的副本。 
	 * 
	 */
	private List<TestExecutionListener> getReversedTestExecutionListeners() {
		List<TestExecutionListener> listenersReversed = new ArrayList<>(getTestExecutionListeners());
		Collections.reverse(listenersReversed);
		return listenersReversed;
	}

	/**
	 * Hook for pre-processing a test class <em>before</em> execution of any
	 * tests within the class. Should be called prior to any framework-specific
	 * <em>before class methods</em> (e.g., methods annotated with JUnit 4's
	 * {@link org.junit.BeforeClass @BeforeClass}).
	 * <p>An attempt will be made to give each registered
	 * {@link TestExecutionListener} a chance to pre-process the test class
	 * execution. If a listener throws an exception, however, the remaining
	 * registered listeners will <strong>not</strong> be called.
	 * @throws Exception if a registered TestExecutionListener throws an
	 * exception
	 * @since 3.0
	 * @see #getTestExecutionListeners()
	 */
	/**
	 * 在执行该类中的任何测试之前，请在<em> </ em>类之前对测试类进行预处理。 
	 * 应该在任何类特定的<em>在类方法之前</ em>之前被调用（例如，用JUnit 4的{@link  org.junit.BeforeClass @BeforeClass}注释的方法）。 
	 *  <p>将尝试给每个注册的{@link  TestExecutionListener}一个机会来预处理测试类的执行。 
	 * 但是，如果侦听器引发异常，则将<strong>不</ strong>调用其余已注册的侦听器。 
	 *  
	 * @throws 如果已注册的TestExecutionListener从3.0开始，则引发异常
	 * @see  #getTestExecutionListeners（）
	 */
	public void beforeTestClass() throws Exception {
		Class<?> testClass = getTestContext().getTestClass();
		if (logger.isTraceEnabled()) {
			logger.trace("beforeTestClass(): class [" + testClass.getName() + "]");
		}
		getTestContext().updateState(null, null, null);

		for (TestExecutionListener testExecutionListener : getTestExecutionListeners()) {
			try {
				testExecutionListener.beforeTestClass(getTestContext());
			}
			catch (Throwable ex) {
				logException(ex, "beforeTestClass", testExecutionListener, testClass);
				ReflectionUtils.rethrowException(ex);
			}
		}
	}

	/**
	 * Hook for preparing a test instance prior to execution of any individual
	 * test methods, for example for injecting dependencies, etc. Should be
	 * called immediately after instantiation of the test instance.
	 * <p>The managed {@link TestContext} will be updated with the supplied
	 * {@code testInstance}.
	 * <p>An attempt will be made to give each registered
	 * {@link TestExecutionListener} a chance to prepare the test instance. If a
	 * listener throws an exception, however, the remaining registered listeners
	 * will <strong>not</strong> be called.
	 * @param testInstance the test instance to prepare (never {@code null})
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @see #getTestExecutionListeners()
	 */
	/**
	 * 在执行任何单独的测试方法之前（例如，用于注入依赖关系等）准备进行测试实例的挂钩。 
	 * 应在测试实例实例化后立即调用。 
	 *  <p>托管的{@link  TestContext}将使用提供的{@code  testInstance}更新。 
	 *  <p>将尝试使每个注册的{@link  TestExecutionListener}有机会准备测试实例。 
	 * 但是，如果侦听器引发异常，则将<strong>不</ strong>调用其余已注册的侦听器。 
	 *  
	 * @param  testInstance要准备的测试实例（决不{<@@code> null}）
	 * @throws 如果已注册的TestExecutionListener抛出异常，则抛出异常
	 * @see  #getTestExecutionListeners（）
	 */
	public void prepareTestInstance(Object testInstance) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("prepareTestInstance(): instance [" + testInstance + "]");
		}
		getTestContext().updateState(testInstance, null, null);

		for (TestExecutionListener testExecutionListener : getTestExecutionListeners()) {
			try {
				testExecutionListener.prepareTestInstance(getTestContext());
			}
			catch (Throwable ex) {
				if (logger.isErrorEnabled()) {
					logger.error("Caught exception while allowing TestExecutionListener [" + testExecutionListener +
							"] to prepare test instance [" + testInstance + "]", ex);
				}
				ReflectionUtils.rethrowException(ex);
			}
		}
	}

	/**
	 * Hook for pre-processing a test <em>before</em> execution of <em>before</em>
	 * lifecycle callbacks of the underlying test framework &mdash; for example,
	 * setting up test fixtures, starting a transaction, etc.
	 * <p>This method <strong>must</strong> be called immediately prior to
	 * framework-specific <em>before</em> lifecycle callbacks (e.g., methods
	 * annotated with JUnit 4's {@link org.junit.Before @Before}). For historical
	 * reasons, this method is named {@code beforeTestMethod}. Since the
	 * introduction of {@link #beforeTestExecution}, a more suitable name for
	 * this method might be something like {@code beforeTestSetUp} or
	 * {@code beforeEach}; however, it is unfortunately impossible to rename
	 * this method due to backward compatibility concerns.
	 * <p>The managed {@link TestContext} will be updated with the supplied
	 * {@code testInstance} and {@code testMethod}.
	 * <p>An attempt will be made to give each registered
	 * {@link TestExecutionListener} a chance to perform its pre-processing.
	 * If a listener throws an exception, however, the remaining registered
	 * listeners will <strong>not</strong> be called.
	 * @param testInstance the current test instance (never {@code null})
	 * @param testMethod the test method which is about to be executed on the
	 * test instance
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @see #afterTestMethod
	 * @see #beforeTestExecution
	 * @see #afterTestExecution
	 * @see #getTestExecutionListeners()
	 */
	/**
	 * 挂钩对底层测试框架的<em>之前</ em>生命周期回调执行之前的测试<em>之前</ em>进行预处理-例如，设置测试装置，启动事务等。 
	 * <p>此方法<strong>必须</ strong>在特定于框架的<em>之前</ em>生命周期回调之前立即调用（例如，用JUnit 4的{@link  org.junit.Before @Before}注释的方法） 。 
	 * 由于历史原因，此方法被命名为{@code  beforeTestMethod}。 
	 * 自从引入{@link  #beforeTestExecution}以来，此方法更合适的名称可能类似于{@code  beforeTestSetUp}或{@code  beforeEach}。 
	 * 但是，由于担心向后兼容性，因此无法重命名此方法。 
	 *  <p>托管的{@link  TestContext}将使用提供的{@code  testInstance}和{@code  testMethod}更新。 
	 *  <p>将尝试使每个注册的{@link  TestExecutionListener}有机会执行其预处理。 
	 * 但是，如果侦听器引发异常，则将<strong>不</ strong>调用其余已注册的侦听器。 
	 *  
	 * @param  testInstance当前测试实例（绝不{@code  null}）
	 * @param  testMethod将在测试实例上执行的测试方法
	 * @throws 如果已注册的TestExecutionListener抛出异常，则为异常
	 * @see  #afterTestMethod 
	 * @see  #beforeTestExecution 
	 * @see  #afterTestExecution 
	 * @see  #getTestExecutionListeners（）
	 */
	public void beforeTestMethod(Object testInstance, Method testMethod) throws Exception {
		String callbackName = "beforeTestMethod";
		prepareForBeforeCallback(callbackName, testInstance, testMethod);

		for (TestExecutionListener testExecutionListener : getTestExecutionListeners()) {
			try {
				testExecutionListener.beforeTestMethod(getTestContext());
			}
			catch (Throwable ex) {
				handleBeforeException(ex, callbackName, testExecutionListener, testInstance, testMethod);
			}
		}
	}

	/**
	 * Hook for pre-processing a test <em>immediately before</em> execution of
	 * the {@linkplain java.lang.reflect.Method test method} in the supplied
	 * {@linkplain TestContext test context} &mdash; for example, for timing
	 * or logging purposes.
	 * <p>This method <strong>must</strong> be called after framework-specific
	 * <em>before</em> lifecycle callbacks (e.g., methods annotated with JUnit 4's
	 * {@link org.junit.Before @Before}).
	 * <p>The managed {@link TestContext} will be updated with the supplied
	 * {@code testInstance} and {@code testMethod}.
	 * <p>An attempt will be made to give each registered
	 * {@link TestExecutionListener} a chance to perform its pre-processing.
	 * If a listener throws an exception, however, the remaining registered
	 * listeners will <strong>not</strong> be called.
	 * @param testInstance the current test instance (never {@code null})
	 * @param testMethod the test method which is about to be executed on the
	 * test instance
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @since 5.0
	 * @see #beforeTestMethod
	 * @see #afterTestMethod
	 * @see #beforeTestExecution
	 * @see #afterTestExecution
	 * @see #getTestExecutionListeners()
	 */
	/**
	 * 请在提供的{@link  plain TestContext测试上下文}中执行{@link  plain java.lang.reflect.Method测试方法}之前，立即</ em>对测试进行预处理。 
	 * 例如，用于计时或记录目的。 
	 *  <p>必须在特定于框架的<em>之前</ em>生命周期回调之后调用此方法<strong>（例如，用JUnit 4的{@link  org.junit.Before @Before}注释的方法） ）。 
	 *  <p>托管的{@link  TestContext}将使用提供的{@code  testInstance}和{@code  testMethod}更新。 
	 *  <p>将尝试使每个注册的{@link  TestExecutionListener}有机会执行其预处理。 
	 * 但是，如果侦听器引发异常，则将<strong>不</ strong>调用其余已注册的侦听器。 
	 *  
	 * @param  testInstance当前测试实例（绝不{<@@code> null}）
	 * @param  testMethod将在测试实例上执行的测试方法
	 * @throws 如果已注册的TestExecutionListener抛出异常，则异常从5.0开始
	 * @see  #beforeTestMethod 
	 * @see  #afterTestMethod 
	 * @see  #beforeTestExecution 
	 * @see  #afterTestExecution 
	 * @see  #getTestExecutionListeners（）
	 */
	public void beforeTestExecution(Object testInstance, Method testMethod) throws Exception {
		String callbackName = "beforeTestExecution";
		prepareForBeforeCallback(callbackName, testInstance, testMethod);

		for (TestExecutionListener testExecutionListener : getTestExecutionListeners()) {
			try {
				testExecutionListener.beforeTestExecution(getTestContext());
			}
			catch (Throwable ex) {
				handleBeforeException(ex, callbackName, testExecutionListener, testInstance, testMethod);
			}
		}
	}

	/**
	 * Hook for post-processing a test <em>immediately after</em> execution of
	 * the {@linkplain java.lang.reflect.Method test method} in the supplied
	 * {@linkplain TestContext test context} &mdash; for example, for timing
	 * or logging purposes.
	 * <p>This method <strong>must</strong> be called before framework-specific
	 * <em>after</em> lifecycle callbacks (e.g., methods annotated with JUnit 4's
	 * {@link org.junit.After @After}).
	 * <p>The managed {@link TestContext} will be updated with the supplied
	 * {@code testInstance}, {@code testMethod}, and {@code exception}.
	 * <p>Each registered {@link TestExecutionListener} will be given a chance
	 * to perform its post-processing. If a listener throws an exception, the
	 * remaining registered listeners will still be called. After all listeners
	 * have executed, the first caught exception will be rethrown with any
	 * subsequent exceptions {@linkplain Throwable#addSuppressed suppressed} in
	 * the first exception.
	 * <p>Note that registered listeners will be executed in the opposite
	 * order in which they were registered.
	 * @param testInstance the current test instance (never {@code null})
	 * @param testMethod the test method which has just been executed on the
	 * test instance
	 * @param exception the exception that was thrown during execution of the
	 * test method or by a TestExecutionListener, or {@code null} if none
	 * was thrown
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @since 5.0
	 * @see #beforeTestMethod
	 * @see #afterTestMethod
	 * @see #beforeTestExecution
	 * @see #getTestExecutionListeners()
	 * @see Throwable#addSuppressed(Throwable)
	 */
	/**
	 * 在提供的{@link  plain TestContext测试上下文}中执行{@link  plain java.lang.reflect.Method测试方法}后，立即<em> </ em>执行后处理测试。 
	 * 例如，用于计时或记录目的。 
	 *  <p>此方法<strong>必须</ strong>在特定于框架的<em>之后</ em>生命周期回调之前调用（例如，用JUnit 4的{@link  org.junit.After @After}注释的方法） ）。 
	 *  <p>托管的{@link  TestContext}将使用提供的{@code  testInstance}，{<@code> testMethod}和{@code 异常}更新。 
	 *  <p>每个注册的{@link  TestExecutionListener}都将有机会执行其后处理。 
	 * 如果侦听器引发异常，则仍将调用其余已注册的侦听器。 
	 * 在所有侦听器都执行完之后，第一个捕获到的异常将与第一个异常中的所有后续异常{@link  plain Throwable＃addSuppressed抑制}一起重新抛出。 
	 *  <p>请注意，已注册的侦听器将按照注册时的相反顺序执行。 
	 *  
	 * @param  testInstance当前测试实例（绝不{<@@code> null}）
	 * @param  testMethod刚刚在测试实例上执行的测试方法
	 * @param 例外，该例外在执行过程中抛出测试方法或由TestExecutionListener抛出； 
	 * 如果未抛出任何异常，则返回{@code  null} 
	 * @throws 如果已注册的TestExecutionListener抛出异常，则抛出异常@从5.0开始
	 * @see  #beforeTestMethod <@见#beforeTestExecution 
	 * @see  #getTestExecutionListeners（）
	 * @see  Throwable＃addSuppressed（Throwable）
	 */
	public void afterTestExecution(Object testInstance, Method testMethod, @Nullable Throwable exception)
			throws Exception {

		String callbackName = "afterTestExecution";
		prepareForAfterCallback(callbackName, testInstance, testMethod, exception);
		Throwable afterTestExecutionException = null;

		// Traverse the TestExecutionListeners in reverse order to ensure proper
		// "wrapper"-style execution of listeners.
		for (TestExecutionListener testExecutionListener : getReversedTestExecutionListeners()) {
			try {
				testExecutionListener.afterTestExecution(getTestContext());
			}
			catch (Throwable ex) {
				logException(ex, callbackName, testExecutionListener, testInstance, testMethod);
				if (afterTestExecutionException == null) {
					afterTestExecutionException = ex;
				}
				else {
					afterTestExecutionException.addSuppressed(ex);
				}
			}
		}

		if (afterTestExecutionException != null) {
			ReflectionUtils.rethrowException(afterTestExecutionException);
		}
	}

	/**
	 * Hook for post-processing a test <em>after</em> execution of <em>after</em>
	 * lifecycle callbacks of the underlying test framework &mdash; for example,
	 * tearing down test fixtures, ending a transaction, etc.
	 * <p>This method <strong>must</strong> be called immediately after
	 * framework-specific <em>after</em> lifecycle callbacks (e.g., methods
	 * annotated with JUnit 4's {@link org.junit.After @After}). For historical
	 * reasons, this method is named {@code afterTestMethod}. Since the
	 * introduction of {@link #afterTestExecution}, a more suitable name for
	 * this method might be something like {@code afterTestTearDown} or
	 * {@code afterEach}; however, it is unfortunately impossible to rename
	 * this method due to backward compatibility concerns.
	 * <p>The managed {@link TestContext} will be updated with the supplied
	 * {@code testInstance}, {@code testMethod}, and {@code exception}.
	 * <p>Each registered {@link TestExecutionListener} will be given a chance
	 * to perform its post-processing. If a listener throws an exception, the
	 * remaining registered listeners will still be called. After all listeners
	 * have executed, the first caught exception will be rethrown with any
	 * subsequent exceptions {@linkplain Throwable#addSuppressed suppressed} in
	 * the first exception.
	 * <p>Note that registered listeners will be executed in the opposite
	 * @param testInstance the current test instance (never {@code null})
	 * @param testMethod the test method which has just been executed on the
	 * test instance
	 * @param exception the exception that was thrown during execution of the test
	 * method or by a TestExecutionListener, or {@code null} if none was thrown
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @see #beforeTestMethod
	 * @see #beforeTestExecution
	 * @see #afterTestExecution
	 * @see #getTestExecutionListeners()
	 * @see Throwable#addSuppressed(Throwable)
	 */
	/**
	 * 挂钩对基础测试框架的<em> after </ em>生命周期回调执行后的测试<em> </ em>执行后处理-例如，拆除测试装置，结束交易等。 
	 * <p>此方法<strong>必须</ strong>在特定于框架的<em> </ em>生命周期回调之后立即调用（例如，用JUnit 4的{@link  org.junit.After @After}注释的方法）。 
	 * 由于历史原因，此方法被命名为{@code  afterTestMethod}。 
	 * 自从引入{@link  #afterTestExecution}以来，此方法更合适的名称可能类似于{@code  afterTestTearDown}或{@code  afterEach}。 
	 * 但是，由于担心向后兼容性，因此无法重命名此方法。 
	 *  <p>托管的{@link  TestContext}将使用提供的{@code  testInstance}，{<@code> testMethod}和{@code 异常}更新。 
	 *  <p>每个注册的{@link  TestExecutionListener}都将有机会执行其后处理。 
	 * 如果侦听器引发异常，则仍将调用其余已注册的侦听器。 
	 * 在所有侦听器都执行完之后，第一个捕获到的异常将与第一个异常中的所有后续异常{@link  plain Throwable＃addSuppressed抑制}一起重新抛出。 
	 *  <p>请注意，已注册的侦听器将在与当前测试实例相反的
	 * @param  testInstance中执行（永远不会{@code  null}）
	 * @param  testMethod刚刚在测试实例上执行的测试方法
	 * @param 异常，在执行测试方法期间或由TestExecutionListener抛出的异常，如果未抛出任何异常，则返回{@code  null} 
	 * @throws 如果已注册的TestExecutionListener抛出异常，则抛出异常
	 * @see  #beforeTestMethod 
	 * @see  #beforeTestExecution 
	 * @see  #afterTestExecution 
	 * @see  #getTestExecutionListeners（）
	 * @see  Throwable＃addSuppressed（Throwable）
	 */
	public void afterTestMethod(Object testInstance, Method testMethod, @Nullable Throwable exception)
			throws Exception {

		String callbackName = "afterTestMethod";
		prepareForAfterCallback(callbackName, testInstance, testMethod, exception);
		Throwable afterTestMethodException = null;

		// Traverse the TestExecutionListeners in reverse order to ensure proper
		// "wrapper"-style execution of listeners.
		for (TestExecutionListener testExecutionListener : getReversedTestExecutionListeners()) {
			try {
				testExecutionListener.afterTestMethod(getTestContext());
			}
			catch (Throwable ex) {
				logException(ex, callbackName, testExecutionListener, testInstance, testMethod);
				if (afterTestMethodException == null) {
					afterTestMethodException = ex;
				}
				else {
					afterTestMethodException.addSuppressed(ex);
				}
			}
		}

		if (afterTestMethodException != null) {
			ReflectionUtils.rethrowException(afterTestMethodException);
		}
	}

	/**
	 * Hook for post-processing a test class <em>after</em> execution of all
	 * tests within the class. Should be called after any framework-specific
	 * <em>after class methods</em> (e.g., methods annotated with JUnit 4's
	 * {@link org.junit.AfterClass @AfterClass}).
	 * <p>Each registered {@link TestExecutionListener} will be given a chance
	 * to perform its post-processing. If a listener throws an exception, the
	 * remaining registered listeners will still be called. After all listeners
	 * have executed, the first caught exception will be rethrown with any
	 * subsequent exceptions {@linkplain Throwable#addSuppressed suppressed} in
	 * the first exception.
	 * <p>Note that registered listeners will be executed in the opposite
	 * @throws Exception if a registered TestExecutionListener throws an exception
	 * @since 3.0
	 * @see #getTestExecutionListeners()
	 * @see Throwable#addSuppressed(Throwable)
	 */
	/**
	 * 在类中所有测试执行后，对后一个测试类<em> </ em>进行挂钩。 
	 * 应该在任何特定于框架的<em>后类方法</ em>之后调用（例如，用JUnit 4的{@link  org.junit.AfterClass @AfterClass}注释的方法）。 
	 *  <p>每个注册的{@link  TestExecutionListener}都将有机会执行其后处理。 
	 * 如果侦听器引发异常，则仍将调用其余已注册的侦听器。 
	 * 在所有侦听器都执行完之后，第一个捕获到的异常将与第一个异常中的所有后续异常{@link  plain Throwable＃addSuppressed抑制}一起重新抛出。 
	 *  <p>请注意，如果已注册的TestExecutionListener引发异常，则将在相反的
	 * @throws 异常中执行@since 3.0 
	 * @see  #getTestExecutionListeners（）
	 * @see  Throwable＃addSuppressed（Throwable）
	 */
	public void afterTestClass() throws Exception {
		Class<?> testClass = getTestContext().getTestClass();
		if (logger.isTraceEnabled()) {
			logger.trace("afterTestClass(): class [" + testClass.getName() + "]");
		}
		getTestContext().updateState(null, null, null);

		Throwable afterTestClassException = null;
		// Traverse the TestExecutionListeners in reverse order to ensure proper
		// "wrapper"-style execution of listeners.
		for (TestExecutionListener testExecutionListener : getReversedTestExecutionListeners()) {
			try {
				testExecutionListener.afterTestClass(getTestContext());
			}
			catch (Throwable ex) {
				logException(ex, "afterTestClass", testExecutionListener, testClass);
				if (afterTestClassException == null) {
					afterTestClassException = ex;
				}
				else {
					afterTestClassException.addSuppressed(ex);
				}
			}
		}

		this.testContextHolder.remove();

		if (afterTestClassException != null) {
			ReflectionUtils.rethrowException(afterTestClassException);
		}
	}

	private void prepareForBeforeCallback(String callbackName, Object testInstance, Method testMethod) {
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("%s(): instance [%s], method [%s]", callbackName, testInstance, testMethod));
		}
		getTestContext().updateState(testInstance, testMethod, null);
	}

	private void prepareForAfterCallback(String callbackName, Object testInstance, Method testMethod,
			@Nullable Throwable exception) {

		if (logger.isTraceEnabled()) {
			logger.trace(String.format("%s(): instance [%s], method [%s], exception [%s]",
					callbackName, testInstance, testMethod, exception));
		}
		getTestContext().updateState(testInstance, testMethod, exception);
	}

	private void handleBeforeException(Throwable ex, String callbackName, TestExecutionListener testExecutionListener,
			Object testInstance, Method testMethod) throws Exception {

		logException(ex, callbackName, testExecutionListener, testInstance, testMethod);
		ReflectionUtils.rethrowException(ex);
	}

	private void logException(
			Throwable ex, String callbackName, TestExecutionListener testExecutionListener, Class<?> testClass) {

		if (logger.isWarnEnabled()) {
			logger.warn(String.format("Caught exception while invoking '%s' callback on " +
					"TestExecutionListener [%s] for test class [%s]", callbackName, testExecutionListener,
					testClass), ex);
		}
	}

	private void logException(Throwable ex, String callbackName, TestExecutionListener testExecutionListener,
			Object testInstance, Method testMethod) {

		if (logger.isWarnEnabled()) {
			logger.warn(String.format("Caught exception while invoking '%s' callback on " +
					"TestExecutionListener [%s] for test method [%s] and test instance [%s]",
					callbackName, testExecutionListener, testMethod, testInstance), ex);
		}
	}


	/**
	 * Attempt to create a copy of the supplied {@code TestContext} using its
	 * <em>copy constructor</em>.
	 */
	/**
	 * 尝试使用其<em> copy构造函数</ em>创建所提供的{@code  TestContext}的副本。 
	 * 
	 */
	private static TestContext copyTestContext(TestContext testContext) {
		Constructor<? extends TestContext> constructor =
				ClassUtils.getConstructorIfAvailable(testContext.getClass(), testContext.getClass());

		if (constructor != null) {
			try {
				ReflectionUtils.makeAccessible(constructor);
				return constructor.newInstance(testContext);
			}
			catch (Exception ex) {
				if (logger.isInfoEnabled()) {
					logger.info(String.format("Failed to invoke copy constructor for [%s]; " +
							"concurrent test execution is therefore likely not supported.",
							testContext), ex);
				}
			}
		}

		// Fallback to original instance
		return testContext;
	}

}
