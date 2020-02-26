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

package org.springframework.test.context.event;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * {@link org.springframework.test.context.TestExecutionListener TestExecutionListener}
 * that publishes test execution events to the
 * {@link org.springframework.context.ApplicationContext ApplicationContext}
 * for the currently executing test. Events are only published if the
 * {@code ApplicationContext} {@linkplain TestContext#hasApplicationContext()
 * has already been loaded}.
 *
 * <h3>Supported Events</h3>
 * <ul>
 * <li>{@link BeforeTestClassEvent}</li>
 * <li>{@link PrepareTestInstanceEvent}</li>
 * <li>{@link BeforeTestMethodEvent}</li>
 * <li>{@link BeforeTestExecutionEvent}</li>
 * <li>{@link AfterTestExecutionEvent}</li>
 * <li>{@link AfterTestMethodEvent}</li>
 * <li>{@link AfterTestClassEvent}</li>
 * </ul>
 *
 * <p>These events may be consumed for various reasons, such as resetting <em>mock</em>
 * beans or tracing test execution. One advantage of consuming test events rather
 * than implementing a custom {@link TestExecutionListener} is that test events
 * may be consumed by any Spring bean registered in the test {@code ApplicationContext},
 * and such beans may benefit directly from dependency injection and other features
 * of the {@code ApplicationContext}. In contrast, a {@link TestExecutionListener}
 * is not a bean in the {@code ApplicationContext}.
 *
 * <h3>Exception Handling</h3>
 * <p>By default, if a test event listener throws an exception while consuming
 * a test event, that exception will propagate to the underlying testing framework
 * in use. For example, if the consumption of a {@code BeforeTestMethodEvent}
 * results in an exception, the corresponding test method will fail as a result
 * of the exception. In contrast, if an asynchronous test event listener throws
 * an exception, the exception will not propagate to the underlying testing framework.
 * For further details on asynchronous exception handling, consult the class-level
 * Javadoc for {@link org.springframework.context.event.EventListener @EventListener}.
 *
 * <h3>Asynchronous Listeners</h3>
 * <p>If you want a particular test event listener to process events asynchronously,
 * you can use Spring's {@link org.springframework.scheduling.annotation.Async @Async}
 * support. For further details, consult the class-level Javadoc for
 * {@link org.springframework.context.event.EventListener @EventListener}.
 *
 * @author Sam Brannen
 * @author Frank Scheffler
 * @since 5.2
 * @see org.springframework.test.context.event.annotation.BeforeTestClass @BeforeTestClass
 * @see org.springframework.test.context.event.annotation.PrepareTestInstance @PrepareTestInstance
 * @see org.springframework.test.context.event.annotation.BeforeTestMethod @BeforeTestMethod
 * @see org.springframework.test.context.event.annotation.BeforeTestExecution @BeforeTestExecution
 * @see org.springframework.test.context.event.annotation.AfterTestExecution @AfterTestExecution
 * @see org.springframework.test.context.event.annotation.AfterTestMethod @AfterTestMethod
 * @see org.springframework.test.context.event.annotation.AfterTestClass @AfterTestClass
 */
/**
 * {@link  org.springframework.test.context.TestExecutionListener TestExecutionListener}将测试执行事件发布到当前执行的测试的{@link  org.springframework.context.ApplicationContext ApplicationContext}。 
 * 仅当{@code  ApplicationContext} {@link 普通TestContext＃hasApplicationContext（）已加载}时才发布事件。 
 *  <h3>受支持的事件</ h3> <ul> <li> {<@link> BeforeTestClassEvent} </ li> <li> {<@link> PrepareTestInstanceEvent} </ li> <li> {<@link> BeforeTestMethodEvent} </ li> <li> {<@link> BeforeTestExecutionEvent} </ li> <li> {<@link> AfterTestExecutionEvent} </ li> <li> {<@link> AfterTestMethodEvent} </ li> <li> { @link  AfterTestClassEvent} </ li> </ ul> <p>这些事件可能由于各种原因而被消耗，例如重置<em> mock </ em> bean或跟踪测试执行。 
 * 使用测试事件而不是实现自定义{@link  TestExecutionListener}的一个优点是，测试事件可以由在测试{@code  ApplicationContext}中注册的任何Spring bean所消耗，并且此类bean可以直接从依赖注入中受益和{@code  ApplicationContext}的其他功能。 
 * 相反，{<@link> TestExecutionListener}在{@code  ApplicationContext}中不是bean。 
 *  <h3>异常处理</ h3> <p>默认情况下，如果测试事件侦听器在使用测试事件时抛出异常，则该异常将传播到所使用的基础测试框架。 
 * 例如，如果使用{@code  BeforeTestMethodEvent}导致异常，则相应的测试方法将因异常而失败。 
 * 相反，如果异步测试事件侦听器引发异常，则该异常不会传播到基础测试框架。 
 * 有关异步异常处理的更多详细信息，请查阅类级Javadoc以获取{@link  org.springframework.context.event.EventListener @EventListener}。 
 *  <h3>异步侦听器</ h3> <p>如果希望特定的测试事件侦听器异步处理事件，则可以使用Spring的{@link  org.springframework.scheduling.annotation.Async @Async}支持。 
 * 有关更多详细信息，请查阅类级Javadoc以获取{@link  org.springframework.context.event.EventListener @EventListener}。 
 *  @author  Sam Brannen @author  Frank Scheffler @5.2起
 * @see  org.springframework.test.context.event.annotation.BeforeTestClass @BeforeTestClass 
 * @see  org.springframework.test.context.event.annotation。 
 *  PrepareTestInstance @PrepareTestInstance 
 * @see  org.springframework.test.context.event.annotation.BeforeTestMethod @BeforeTestMethod 
 * @see  org.springframework.test.context.event.annotation.BeforeTestExecution @BeforeTestExecution 
 * @see  org.springframework.test .context.event.annotation.AfterTestExecution @AfterTestExecution 
 * @see  org.springframework.test.context.event.annotation.AfterTestMethod @AfterTestMethod 
 * @see  org.springframework.test.context.event.annotation.AfterTestClass @AfterTestClass
 */
public class EventPublishingTestExecutionListener extends AbstractTestExecutionListener {

	/**
	 * Returns {@code 10000}.
	 */
	/**
	 * 返回{@code  10000}。 
	 * 
	 */
	@Override
	public final int getOrder() {
		return 10_000;
	}

	/**
	 * Publish a {@link BeforeTestClassEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 将{@link  TestContext}的{@link  BeforeTestClassEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void beforeTestClass(TestContext testContext) {
		testContext.publishEvent(BeforeTestClassEvent::new);
	}

	/**
	 * Publish a {@link PrepareTestInstanceEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 为提供的{@link  TestContext}将{@link  PrepareTestInstanceEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void prepareTestInstance(TestContext testContext) {
		testContext.publishEvent(PrepareTestInstanceEvent::new);
	}

	/**
	 * Publish a {@link BeforeTestMethodEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 将{@link  TestContext}的{@link  BeforeTestMethodEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) {
		testContext.publishEvent(BeforeTestMethodEvent::new);
	}

	/**
	 * Publish a {@link BeforeTestExecutionEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 为提供的{@link  TestContext}将{@link  BeforeTestExecutionEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void beforeTestExecution(TestContext testContext) {
		testContext.publishEvent(BeforeTestExecutionEvent::new);
	}

	/**
	 * Publish an {@link AfterTestExecutionEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 为提供的{@link  TestContext}将{@link  AfterTestExecutionEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void afterTestExecution(TestContext testContext) {
		testContext.publishEvent(AfterTestExecutionEvent::new);
	}

	/**
	 * Publish an {@link AfterTestMethodEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 将{@link  TestContext}的{@link  AfterTestMethodEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void afterTestMethod(TestContext testContext) {
		testContext.publishEvent(AfterTestMethodEvent::new);
	}

	/**
	 * Publish an {@link AfterTestClassEvent} to the {@code ApplicationContext}
	 * for the supplied {@link TestContext}.
	 */
	/**
	 * 将{@link  TestContext}的{@link  AfterTestClassEvent}发布到{@code  ApplicationContext}。 
	 * 
	 */
	@Override
	public void afterTestClass(TestContext testContext) {
		testContext.publishEvent(AfterTestClassEvent::new);
	}

}
