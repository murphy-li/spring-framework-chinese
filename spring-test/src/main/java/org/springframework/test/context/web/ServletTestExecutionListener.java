/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.test.context.web;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Conventions;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * {@code TestExecutionListener} which provides mock Servlet API support to
 * {@link WebApplicationContext WebApplicationContexts} loaded by the <em>Spring
 * TestContext Framework</em>.
 *
 * <p>Specifically, {@code ServletTestExecutionListener} sets up thread-local
 * state via Spring Web's {@link RequestContextHolder} during {@linkplain
 * #prepareTestInstance(TestContext) test instance preparation} and {@linkplain
 * #beforeTestMethod(TestContext) before each test method} and creates a {@link
 * MockHttpServletRequest}, {@link MockHttpServletResponse}, and
 * {@link ServletWebRequest} based on the {@link MockServletContext} present in
 * the {@code WebApplicationContext}. This listener also ensures that the
 * {@code MockHttpServletResponse} and {@code ServletWebRequest} can be injected
 * into the test instance, and once the test is complete this listener {@linkplain
 * #afterTestMethod(TestContext) cleans up} thread-local state.
 *
 * <p>Note that {@code ServletTestExecutionListener} is enabled by default but
 * generally takes no action if the {@linkplain TestContext#getTestClass() test
 * class} is not annotated with {@link WebAppConfiguration @WebAppConfiguration}.
 * See the javadocs for individual methods in this class for details.
 *
 * @author Sam Brannen
 * @author Phillip Webb
 * @since 3.2
 */
/**
 * {@code  TestExecutionListener}可以为<em> Spring TestContext Framework </ em>加载的{@link  WebApplicationContext WebApplicationContexts}提供模拟Servlet API支持。 
 *  <p>具体来说，{<@code> ServletTestExecutionListener}在{@link  plain #prepareTestInstance（TestContext）测试实例准备}和{@link 期间，通过Spring Web的{@link  RequestContextHolder}设置线程本地状态。 
 * 在每个测试方法之前添加一个简单的#beforeTestMethod（TestContext），并根据其中存在的{@link  MockServletContext}创建一个{@link  MockHttpServletRequest}，{<@link> MockHttpServletResponse}和{@link  ServletWebRequest} {@code  WebApplicationContext}。 
 * 此侦听器还确保可以将{@code  MockHttpServletResponse}和{@code  ServletWebRequest}注入到测试实例中，并且在测试完成后，此侦听器{@link  plain #afterTestMethod（TestContext）会被清理。 
 * 线程本地状态。 
 *  <p>请注意，{<@code> ServletTestExecutionListener}默认情况下处于启用状态，但是如果{{@link> plain TestContext＃getTestClass（）测试类}未使用{@link  WebAppConfiguration @WebAppConfiguration}进行注释，则通常不执行任何操作。 
 * 有关详细信息，请参见此类中各个方法的javadocs。 
 *  @author  Sam Brannen @author 菲利普·韦伯@3.2起
 */
public class ServletTestExecutionListener extends AbstractTestExecutionListener {

	/**
	 * Attribute name for a {@link TestContext} attribute which indicates
	 * whether or not the {@code ServletTestExecutionListener} should {@linkplain
	 * RequestContextHolder#resetRequestAttributes() reset} Spring Web's
	 * {@code RequestContextHolder} in {@link #afterTestMethod(TestContext)}.
	 * <p>Permissible values include {@link Boolean#TRUE} and {@link Boolean#FALSE}.
	 */
	/**
	 * {@link  TestContext}属性的属性名称，该属性名称指示{@link  plain RequestContextHolder＃resetRequestAttributes（）reset} {@code  ServletTestExecutionListener}是否应该在{ @link  #afterTestMethod（TestContext）}。 
	 *  <p>允许的值包括{@link  Boolean＃TRUE}和{@link  Boolean＃FALSE}。 
	 * 
	 */
	public static final String RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE = Conventions.getQualifiedAttributeName(
			ServletTestExecutionListener.class, "resetRequestContextHolder");

	/**
	 * Attribute name for a {@link TestContext} attribute which indicates that
	 * {@code ServletTestExecutionListener} has already populated Spring Web's
	 * {@code RequestContextHolder}.
	 * <p>Permissible values include {@link Boolean#TRUE} and {@link Boolean#FALSE}.
	 */
	/**
	 * {@link  TestContext}属性的属性名称，该属性名称指示{@code  ServletTestExecutionListener}已经填充了Spring Web的{@code  RequestContextHolder}。 
	 *  <p>允许的值包括{@link  Boolean＃TRUE}和{@link  Boolean＃FALSE}。 
	 * 
	 */
	public static final String POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE = Conventions.getQualifiedAttributeName(
			ServletTestExecutionListener.class, "populatedRequestContextHolder");

	/**
	 * Attribute name for a request attribute which indicates that the
	 * {@link MockHttpServletRequest} stored in the {@link RequestAttributes}
	 * in Spring Web's {@link RequestContextHolder} was created by the TestContext
	 * framework.
	 * <p>Permissible values include {@link Boolean#TRUE} and {@link Boolean#FALSE}.
	 * @since 4.2
	 */
	/**
	 * 请求属性的属性名称，该属性名称指示存储在Spring Web {@link  RequestContextHolder}的{@link  RequestAttributes}中的{@link  MockHttpServletRequest}是由TestContext框架创建的。 
	 *  <p>允许的值包括{@link  Boolean＃TRUE}和{@link  Boolean＃FALSE}。 
	 *  @4.2起
	 */
	public static final String CREATED_BY_THE_TESTCONTEXT_FRAMEWORK = Conventions.getQualifiedAttributeName(
			ServletTestExecutionListener.class, "createdByTheTestContextFramework");

	/**
	 * Attribute name for a {@link TestContext} attribute which indicates that the
	 * {@code ServletTestExecutionListener} should be activated. When not set to
	 * {@code true}, activation occurs when the {@linkplain TestContext#getTestClass()
	 * test class} is annotated with {@link WebAppConfiguration @WebAppConfiguration}.
	 * <p>Permissible values include {@link Boolean#TRUE} and {@link Boolean#FALSE}.
	 * @since 4.3
	 */
	/**
	 * {@link  TestContext}属性的属性名称，该属性名称指示应激活{@code  ServletTestExecutionListener}。 
	 * 如果未设置为{@code  true}，则在{@link  WebAppConfiguration @WebAppConfiguration}注释{@link  plain TestContext＃getTestClass（）测试类}时，将发生激活。 
	 *  <p>允许的值包括{@link  Boolean＃TRUE}和{@link  Boolean＃FALSE}。 
	 *  @4.3起
	 */
	public static final String ACTIVATE_LISTENER = Conventions.getQualifiedAttributeName(
			ServletTestExecutionListener.class, "activateListener");


	private static final Log logger = LogFactory.getLog(ServletTestExecutionListener.class);


	/**
	 * Returns {@code 1000}.
	 */
	/**
	 * 返回{@code  1000}。 
	 * 
	 */
	@Override
	public final int getOrder() {
		return 1000;
	}

	/**
	 * Sets up thread-local state during the <em>test instance preparation</em>
	 * callback phase via Spring Web's {@link RequestContextHolder}, but only if
	 * the {@linkplain TestContext#getTestClass() test class} is annotated with
	 * {@link WebAppConfiguration @WebAppConfiguration}.
	 * @see TestExecutionListener#prepareTestInstance(TestContext)
	 * @see #setUpRequestContextIfNecessary(TestContext)
	 */
	/**
	 * 通过Spring Web的{@link  RequestContextHolder}在<em>测试实例准备</ em>回调阶段设置线程本地状态，但前提是{@link  plain TestContext＃getTestClass（）测试类}为带有{@link  WebAppConfiguration @WebAppConfiguration}注释。 
	 *  
	 * @see  TestExecutionListener＃prepareTestInstance（TestContext）
	 * @see  #setUpRequestContextIfNecessary（TestContext）
	 */
	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		setUpRequestContextIfNecessary(testContext);
	}

	/**
	 * Sets up thread-local state before each test method via Spring Web's
	 * {@link RequestContextHolder}, but only if the
	 * {@linkplain TestContext#getTestClass() test class} is annotated with
	 * {@link WebAppConfiguration @WebAppConfiguration}.
	 * @see TestExecutionListener#beforeTestMethod(TestContext)
	 * @see #setUpRequestContextIfNecessary(TestContext)
	 */
	/**
	 * 通过Spring Web的{@link  RequestContextHolder}在每个测试方法之前设置线程本地状态，但前提是{{@link> plain TestContext＃getTestClass（）测试类}用{@link  WebAppConfiguration @WebAppConfiguration注释}。 
	 *  
	 * @see  TestExecutionListener＃beforeTestMethod（TestContext）
	 * @see  #setUpRequestContextIfNecessary（TestContext）
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		setUpRequestContextIfNecessary(testContext);
	}

	/**
	 * If the {@link #RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE} in the supplied
	 * {@code TestContext} has a value of {@link Boolean#TRUE}, this method will
	 * (1) clean up thread-local state after each test method by {@linkplain
	 * RequestContextHolder#resetRequestAttributes() resetting} Spring Web's
	 * {@code RequestContextHolder} and (2) ensure that new mocks are injected
	 * into the test instance for subsequent tests by setting the
	 * {@link DependencyInjectionTestExecutionListener#REINJECT_DEPENDENCIES_ATTRIBUTE}
	 * in the test context to {@code true}.
	 * <p>The {@link #RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE} and
	 * {@link #POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE} will be subsequently
	 * removed from the test context, regardless of their values.
	 * @see TestExecutionListener#afterTestMethod(TestContext)
	 */
	/**
	 * 如果提供的{@code  TestContext}中的{@link  #RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE}的值为{@link  Boolean＃TRUE}，则此方法将（1）在每个测试方法之后清除线程本地状态通过{@link  plain RequestContextHolder＃resetRequestAttributes（）reset}的重置，Spring Web的{@code  RequestContextHolder}和（2）通过设置{@link  DependencyInjectionTestExecutionListener＃，确保将新的模拟注入到测试实例中以进行后续测试在测试上下文中将REINJECT_DEPENDENCIES_ATTRIBUTE}设置为{@code  true}。 
	 *  <p> {<@link> #RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE}和{@link  #POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE}随后将被从测试上下文中删除，无论它们的值如何。 
	 *  
	 * @see  TestExecutionListener＃afterTestMethod（TestContext）
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		if (Boolean.TRUE.equals(testContext.getAttribute(RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE))) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Resetting RequestContextHolder for test context %s.", testContext));
			}
			RequestContextHolder.resetRequestAttributes();
			testContext.setAttribute(DependencyInjectionTestExecutionListener.REINJECT_DEPENDENCIES_ATTRIBUTE,
				Boolean.TRUE);
		}
		testContext.removeAttribute(POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE);
		testContext.removeAttribute(RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE);
	}

	private boolean isActivated(TestContext testContext) {
		return (Boolean.TRUE.equals(testContext.getAttribute(ACTIVATE_LISTENER)) ||
				AnnotatedElementUtils.hasAnnotation(testContext.getTestClass(), WebAppConfiguration.class));
	}

	private boolean alreadyPopulatedRequestContextHolder(TestContext testContext) {
		return Boolean.TRUE.equals(testContext.getAttribute(POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE));
	}

	private void setUpRequestContextIfNecessary(TestContext testContext) {
		if (!isActivated(testContext) || alreadyPopulatedRequestContextHolder(testContext)) {
			return;
		}

		ApplicationContext context = testContext.getApplicationContext();

		if (context instanceof WebApplicationContext) {
			WebApplicationContext wac = (WebApplicationContext) context;
			ServletContext servletContext = wac.getServletContext();
			Assert.state(servletContext instanceof MockServletContext, () -> String.format(
						"The WebApplicationContext for test context %s must be configured with a MockServletContext.",
						testContext));

			if (logger.isDebugEnabled()) {
				logger.debug(String.format(
						"Setting up MockHttpServletRequest, MockHttpServletResponse, ServletWebRequest, and RequestContextHolder for test context %s.",
						testContext));
			}

			MockServletContext mockServletContext = (MockServletContext) servletContext;
			MockHttpServletRequest request = new MockHttpServletRequest(mockServletContext);
			request.setAttribute(CREATED_BY_THE_TESTCONTEXT_FRAMEWORK, Boolean.TRUE);
			MockHttpServletResponse response = new MockHttpServletResponse();
			ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);

			RequestContextHolder.setRequestAttributes(servletWebRequest);
			testContext.setAttribute(POPULATED_REQUEST_CONTEXT_HOLDER_ATTRIBUTE, Boolean.TRUE);
			testContext.setAttribute(RESET_REQUEST_CONTEXT_HOLDER_ATTRIBUTE, Boolean.TRUE);

			if (wac instanceof ConfigurableApplicationContext) {
				@SuppressWarnings("resource")
				ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) wac;
				ConfigurableListableBeanFactory bf = configurableApplicationContext.getBeanFactory();
				bf.registerResolvableDependency(MockHttpServletResponse.class, response);
				bf.registerResolvableDependency(ServletWebRequest.class, servletWebRequest);
			}
		}
	}

}
