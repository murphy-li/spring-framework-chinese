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

package org.springframework.test.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.Conventions;
import org.springframework.test.context.TestContext;

/**
 * {@code TestExecutionListener} which provides support for dependency
 * injection and initialization of test instances.
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 */
/**
 * {@code  TestExecutionListener}提供对依赖项注入和测试实例初始化的支持。 
 *  @author  Sam Brannen @author 于尔根·霍勒（Juergen Hoeller）@从2.5开始
 */
public class DependencyInjectionTestExecutionListener extends AbstractTestExecutionListener {

	/**
	 * Attribute name for a {@link TestContext} attribute which indicates
	 * whether or not the dependencies of a test instance should be
	 * <em>reinjected</em> in
	 * {@link #beforeTestMethod(TestContext) beforeTestMethod()}. Note that
	 * dependencies will be injected in
	 * {@link #prepareTestInstance(TestContext) prepareTestInstance()} in any
	 * case.
	 * <p>Clients of a {@link TestContext} (e.g., other
	 * {@link org.springframework.test.context.TestExecutionListener TestExecutionListeners})
	 * may therefore choose to set this attribute to signal that dependencies
	 * should be reinjected <em>between</em> execution of individual test
	 * methods.
	 * <p>Permissible values include {@link Boolean#TRUE} and {@link Boolean#FALSE}.
	 */
	/**
	 * {@link  TestContext}属性的属性名称，该属性名称指示是否应在{@link  #beforeTestMethod（TestContext）beforeTestMethod（）}中<em>重新注入测试实例的依赖项</ em>。 
	 * 请注意，在任何情况下，依赖项都将注入{@link  #prepareTestInstance（TestContext）prepareTestInstance（）}中。 
	 * 因此，{<@link> TestContext}的客户端（例如，其他{@link  org.springframework.test.context.TestExecutionListener TestExecutionListeners}）的客户端可以选择设置此属性，以表示应重新注入依赖项<em >在各个测试方法之间执行。 
	 *  <p>允许的值包括{@link  Boolean＃TRUE}和{@link  Boolean＃FALSE}。 
	 * 
	 */
	public static final String REINJECT_DEPENDENCIES_ATTRIBUTE = Conventions.getQualifiedAttributeName(
			DependencyInjectionTestExecutionListener.class, "reinjectDependencies");

	private static final Log logger = LogFactory.getLog(DependencyInjectionTestExecutionListener.class);


	/**
	 * Returns {@code 2000}.
	 */
	/**
	 * 返回{@code  2000}。 
	 * 
	 */
	@Override
	public final int getOrder() {
		return 2000;
	}

	/**
	 * Performs dependency injection on the
	 * {@link TestContext#getTestInstance() test instance} of the supplied
	 * {@link TestContext test context} by
	 * {@link AutowireCapableBeanFactory#autowireBeanProperties(Object, int, boolean) autowiring}
	 * and
	 * {@link AutowireCapableBeanFactory#initializeBean(Object, String) initializing}
	 * the test instance via its own
	 * {@link TestContext#getApplicationContext() application context} (without
	 * checking dependencies).
	 * <p>The {@link #REINJECT_DEPENDENCIES_ATTRIBUTE} will be subsequently removed
	 * from the test context, regardless of its value.
	 */
	/**
	 * 通过{@link  AutowireCapableBeanFactory＃autowireBeanProperties（Object，int，boolean）autowiring}和{对提供的{@link  TestContext测试上下文}的{@link  TestContext＃getTestInstance（）测试实例}执行依赖项注入@link  AutowireCapableBeanFactory＃initializeBean（Object，String）通过其自己的{@link  TestContext＃getApplicationContext（）应用程序上下文}初始化测试实例（不检查依赖项）。 
	 *  <p> {<@link> #REINJECT_DEPENDENCIES_ATTRIBUTE}随后将从测试上下文中删除，无论其值如何。 
	 * 
	 */
	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Performing dependency injection for test context [" + testContext + "].");
		}
		injectDependencies(testContext);
	}

	/**
	 * If the {@link #REINJECT_DEPENDENCIES_ATTRIBUTE} in the supplied
	 * {@link TestContext test context} has a value of {@link Boolean#TRUE},
	 * this method will have the same effect as
	 * {@link #prepareTestInstance(TestContext) prepareTestInstance()};
	 * otherwise, this method will have no effect.
	 */
	/**
	 * 如果提供的{@link  TestContext测试上下文}中的{@link  #REINJECT_DEPENDENCIES_ATTRIBUTE}的值为{@link  Boolean＃TRUE}，则此方法将与{@link ＃ prepareTestInstance（TestContext）prepareTestInstance（）};否则，此方法将无效。 
	 * 
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		if (Boolean.TRUE.equals(testContext.getAttribute(REINJECT_DEPENDENCIES_ATTRIBUTE))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Reinjecting dependencies for test context [" + testContext + "].");
			}
			injectDependencies(testContext);
		}
	}

	/**
	 * Performs dependency injection and bean initialization for the supplied
	 * {@link TestContext} as described in
	 * {@link #prepareTestInstance(TestContext) prepareTestInstance()}.
	 * <p>The {@link #REINJECT_DEPENDENCIES_ATTRIBUTE} will be subsequently removed
	 * from the test context, regardless of its value.
	 * @param testContext the test context for which dependency injection should
	 * be performed (never {@code null})
	 * @throws Exception allows any exception to propagate
	 * @see #prepareTestInstance(TestContext)
	 * @see #beforeTestMethod(TestContext)
	 */
	/**
	 * 如{@link  #prepareTestInstance（TestContext）prepareTestInstance（）}中所述，对提供的{@link  TestContext}执行依赖项注入和bean初始化。 
	 *  <p> {<@link> #REINJECT_DEPENDENCIES_ATTRIBUTE}随后将从测试上下文中删除，无论其值如何。 
	 *  
	 * @param  testContext应该对其执行依赖项注入的测试上下文（从不{@code  null}）
	 * @throws 异常允许任何异常传播
	 * @see  #prepareTestInstance（TestContext）
	 * @see  #beforeTestMethod （TestContext）
	 */
	protected void injectDependencies(TestContext testContext) throws Exception {
		Object bean = testContext.getTestInstance();
		Class<?> clazz = testContext.getTestClass();
		AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
		beanFactory.initializeBean(bean, clazz.getName() + AutowireCapableBeanFactory.ORIGINAL_INSTANCE_SUFFIX);
		testContext.removeAttribute(REINJECT_DEPENDENCIES_ATTRIBUTE);
	}

}
