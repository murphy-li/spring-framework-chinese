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

package org.springframework.test.context.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * {@code TestExecutionListener} that provides support for executing tests
 * within <em>test-managed transactions</em> by honoring Spring's
 * {@link Transactional @Transactional} annotation.
 *
 * <h3>Test-managed Transactions</h3>
 * <p><em>Test-managed transactions</em> are transactions that are managed
 * declaratively via this listener or programmatically via
 * {@link TestTransaction}. Such transactions should not be confused with
 * <em>Spring-managed transactions</em> (i.e., those managed directly
 * by Spring within the {@code ApplicationContext} loaded for tests) or
 * <em>application-managed transactions</em> (i.e., those managed
 * programmatically within application code that is invoked via tests).
 * Spring-managed and application-managed transactions will typically
 * participate in test-managed transactions; however, caution should be
 * taken if Spring-managed or application-managed transactions are
 * configured with any propagation type other than
 * {@link org.springframework.transaction.annotation.Propagation#REQUIRED REQUIRED}
 * or {@link org.springframework.transaction.annotation.Propagation#SUPPORTS SUPPORTS}.
 *
 * <h3>Enabling and Disabling Transactions</h3>
 * <p>Annotating a test method with {@code @Transactional} causes the test
 * to be run within a transaction that will, by default, be automatically
 * <em>rolled back</em> after completion of the test. If a test class is
 * annotated with {@code @Transactional}, each test method within that class
 * hierarchy will be run within a transaction. Test methods that are
 * <em>not</em> annotated with {@code @Transactional} (at the class or method
 * level) will not be run within a transaction. Furthermore, tests that
 * <em>are</em> annotated with {@code @Transactional} but have the
 * {@link Transactional#propagation propagation} type set to
 * {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED NOT_SUPPORTED}
 * will not be run within a transaction.
 *
 * <h3>Declarative Rollback and Commit Behavior</h3>
 * <p>By default, test transactions will be automatically <em>rolled back</em>
 * after completion of the test; however, transactional commit and rollback
 * behavior can be configured declaratively via the {@link Commit @Commit}
 * and {@link Rollback @Rollback} annotations at the class level and at the
 * method level.
 *
 * <h3>Programmatic Transaction Management</h3>
 * <p>As of Spring Framework 4.1, it is possible to interact with test-managed
 * transactions programmatically via the static methods in {@link TestTransaction}.
 * {@code TestTransaction} may be used within <em>test</em> methods,
 * <em>before</em> methods, and <em>after</em> methods.
 *
 * <h3>Executing Code outside of a Transaction</h3>
 * <p>When executing transactional tests, it is sometimes useful to be able to
 * execute certain <em>set up</em> or <em>tear down</em> code outside of a
 * transaction. {@code TransactionalTestExecutionListener} provides such
 * support for methods annotated with {@link BeforeTransaction @BeforeTransaction}
 * or {@link AfterTransaction @AfterTransaction}. As of Spring Framework 4.3,
 * {@code @BeforeTransaction} and {@code @AfterTransaction} may also be declared
 * on Java 8 based interface default methods.
 *
 * <h3>Configuring a Transaction Manager</h3>
 * <p>{@code TransactionalTestExecutionListener} expects a
 * {@link PlatformTransactionManager} bean to be defined in the Spring
 * {@code ApplicationContext} for the test. In case there are multiple
 * instances of {@code PlatformTransactionManager} within the test's
 * {@code ApplicationContext}, a <em>qualifier</em> may be declared via
 * {@link Transactional @Transactional} (e.g., {@code @Transactional("myTxMgr")}
 * or {@code @Transactional(transactionManager = "myTxMgr")}, or
 * {@link org.springframework.transaction.annotation.TransactionManagementConfigurer
 * TransactionManagementConfigurer} can be implemented by an
 * {@link org.springframework.context.annotation.Configuration @Configuration}
 * class. See {@link TestContextTransactionUtils#retrieveTransactionManager}
 * for details on the algorithm used to look up a transaction manager in
 * the test's {@code ApplicationContext}.
 *
 * <h3>{@code @Transactional} Attribute Support</h3>
 * <table border="1">
 * <tr><th>Attribute</th><th>Supported for test-managed transactions</th></tr>
 * <tr><td>{@link Transactional#value value} and {@link Transactional#transactionManager transactionManager}</td><td>yes</td></tr>
 * <tr><td>{@link Transactional#propagation propagation}</td>
 * <td>only {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED NOT_SUPPORTED} is supported</td></tr>
 * <tr><td>{@link Transactional#isolation isolation}</td><td>no</td></tr>
 * <tr><td>{@link Transactional#timeout timeout}</td><td>no</td></tr>
 * <tr><td>{@link Transactional#readOnly readOnly}</td><td>no</td></tr>
 * <tr><td>{@link Transactional#rollbackFor rollbackFor} and {@link Transactional#rollbackForClassName rollbackForClassName}</td>
 * <td>no: use {@link TestTransaction#flagForRollback()} instead</td></tr>
 * <tr><td>{@link Transactional#noRollbackFor noRollbackFor} and {@link Transactional#noRollbackForClassName noRollbackForClassName}</td>
 * <td>no: use {@link TestTransaction#flagForCommit()} instead</td></tr>
 * </table>
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @since 2.5
 * @see org.springframework.transaction.annotation.TransactionManagementConfigurer
 * @see org.springframework.transaction.annotation.Transactional
 * @see org.springframework.test.annotation.Commit
 * @see org.springframework.test.annotation.Rollback
 * @see BeforeTransaction
 * @see AfterTransaction
 * @see TestTransaction
 */
/**
 * {<< @@code >> TestExecutionListener}支持Spring的{<< @link >> Transactional @Transactional}注解，从而支持在<em>测试管理的事务</ em>中执行测试。 
 *  <h3>测试管理的交易</ h3> <p> <em>测试管理的交易</ em>是通过此侦听器以声明方式管理的交易，或通过{<< @link >> TestTransaction}以编程方式进行管理的交易。 
 * 此类事务不应与<em> Spring管理的事务</ em>（即由Spring在为测试加载的{<< @code >> ApplicationContext}中直接管理的事务）或<em>应用程序管理的事务< / em>（即，在通过测试调用的应用程序代码中以编程方式管理的代码）。 
 *  Spring管理的事务和应用程序管理的事务通常将参与测试管理的事务。 
 * 但是，如果将Spring管理的事务或应用程序管理的事务配置为使用{{<< @link >> org.springframework.transaction.annotation.Propagation＃REQUIRED REQUIRED}或{<< @link> > org.springframework.transaction.annotation.Propagation＃SUPPORTS SUPPORTS}。 
 *  <h3>启用和禁用事务</ h3> <p>使用{<< @code >> @Transactional}注释测试方法将导致测试在事务中运行，默认情况下，该事务将自动<em>滚动测试完成后返回</ em>。 
 * 如果测试类使用{<< @code >> @Transactional}注释，则该类层次结构中的每个测试方法都将在事务中运行。 
 * 在类或方法级别用{<< @code >> @Transactional}注释的<em> not </ em>测试方法将不会在事务中运行。 
 * 此外，用<<< @code >> @Transactional注释<em> </ em>但将{<< @link >> Transactional＃propagation传播}类型设置为{<< @link >> org的测试.springframework.transaction.annotation.Propagation＃NOT_SUPPORTED NOT_SUPPORTED}将不会在事务中运行。 
 *  <h3>声明性回滚和提交行为</ h3> <p>默认情况下，测试事务将在测试完成后自动<em>回滚</ em>； 
 * 但是，可以在类级别和方法级别通过{<< @link >> Commit @Commit}和{<< @link >> Rollback @Rollback}注解声明性地配置事务提交和回滚行为。 
 *  <h3>程序化事务管理</ h3> <p>从Spring Framework 4.1开始，可以通过{<< @link >> TestTransaction}中的静态方法以编程方式与测试管理的事务进行交互。 
 *  {<< @code >> TestTransaction}可以在<em> test </ em>方法，<em>之前</ em>方法和<em>之后</ em>方法中使用。 
 *  <h3>在事务之外执行代码</ h3> <p>在执行事务测试时，能够执行某些<em>设置</ em>或<em>拆卸</ em>有时会很有用。 
 * 交易之外的代码。 
 *  {<< @@code >> TransactionalTestExecutionListener}为使用{<< @link >> BeforeTransaction @BeforeTransaction}或{<< @link >> AfterTransaction @AfterTransaction}注释的方法提供了这种支持。 
 * 从Spring Framework 4.3开始，还可以在基于Java 8的接口默认方法上声明{<< @code >> @BeforeTransaction}和{<< @code >> @AfterTransaction}。 
 *  <h3>配置事务管理器</ h3> <p> {<< @code >> TransactionalTestExecutionListener}期望在春季{<< @code >> ApplicationContext}中定义一个{<< @link >> PlatformTransactionManager} bean。 
 * 进行测试。 
 * 如果测试的{<< @code >> ApplicationContext}中有多个{<< @code >> PlatformTransactionManager}实例，则可以通过{<< @link >> Transactional声明<em> qualifier </ em>。 
 *  @Transactional}（例如{<< @code >> @Transactional（"myTxMgr"）}或{<< @code >> @Transactional（transactionManager ="myTxMgr"）}}或{<< @link >> org。 
 *  springframework.transaction.annotation.TransactionManagementConfigurer TransactionManagementConfigurer}可以由{<< @link >> org.springframework.context.annotation.Configuration @Configuration}类实现。 
 * 有关详细信息，请参见{<< @link >> TestContextTransactionUtils＃retrieveTransactionManager}用于在测试的{<< @code >> ApplicationContext}中查找事务管理器的算法
 */
/**
 * <h3> {<< @code >> @Transactional}属性支持</ h3> <table border ="1"> <tr> <th>属性</ th> <th>受测试管理的事务支持</ th > </ tr> <tr> <td> {<< @link >> Transactional＃value值}和{<< @link >> Transactional＃transactionManager transactionManager} </ td> <td>是</ td> </ </ tr> <tr> <td> {<< @link >>事务性传播传播} </ td>仅<td> {{<< @link >> org.springframework.transaction.annotation.Propagation＃NOT_SUPPORTED NOT_SUPPORTED}受支持</ td> </ tr> <tr> <td> {<< @link >> Transactional＃isolation隔离} </ td> <td>否</ td> </ tr> <tr> <td> {< <@link >> Transactional＃timeout超时} </ td> <td>否</ td> </ tr> <tr> <td> {<< @@link >> Transactional＃readOnly readOnly} </ td> <td >否</ td> </ tr> <tr> <td> {<< @link >> Transactional＃rollbackFor rollbackFor}和{<< @link >> Transactional＃rollbackForClassName rollbackForClassName} </ td> <td>否：使用{<< @link >> TestTransaction＃flagForRollback（）}代替</ td> </ tr> <tr> <td> {<< @@link >> Transactional＃noRollbackFor noRollbackFor}和{<< @link >> Transactional #noRo llbackForClassName noRollbackForClassName} </ td> <td>否：改用{<< @link >> TestTransaction＃flagForCommit（）} </ td> </ tr> </ table> << @author >> Sam Brannen << @作者>> Juergen Hoeller @始于2.5 << 
 * @see >> org.springframework.transaction.annotation.TransactionManagementConfigurer << 
 * @see >> org.springframework.transaction.annotation.transactional << 
 * @see >> org.springframework.test。 
 * 注解。 
 * 提交<< 
 * @see >> org.springframework.test.annotation.Rollback << 
 * @see >> BeforeTransaction << 
 * @see >> AfterTransaction << 
 * @see >> TestTransaction
 */
public class TransactionalTestExecutionListener extends AbstractTestExecutionListener {

	private static final Log logger = LogFactory.getLog(TransactionalTestExecutionListener.class);

	// Do not require @Transactional test methods to be public.
	protected final TransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(false);


	/**
	 * Returns {@code 4000}.
	 */
	/**
	 * 返回{@code  4000}。 
	 * 
	 */
	@Override
	public final int getOrder() {
		return 4000;
	}

	/**
	 * If the test method of the supplied {@linkplain TestContext test context}
	 * is configured to run within a transaction, this method will run
	 * {@link BeforeTransaction @BeforeTransaction} methods and start a new
	 * transaction.
	 * <p>Note that if a {@code @BeforeTransaction} method fails, any remaining
	 * {@code @BeforeTransaction} methods will not be invoked, and a transaction
	 * will not be started.
	 * @see Transactional @Transactional
	 * @see #getTransactionManager(TestContext, String)
	 */
	/**
	 * 如果将提供的{@link  plain TestContext测试上下文}的测试方法配置为在事务内运行，则此方法将运行{@link  BeforeTransaction @BeforeTransaction}方法并开始新的事务。 
	 *  <p>请注意，如果{@code  @BeforeTransaction}方法失败，则将不会调用任何剩余的{@code  @BeforeTransaction}方法，并且不会启动事务。 
	 *  
	 * @see 事务性@Transactional 
	 * @see  #getTransactionManager（TestContext，String）
	 */
	@Override
	public void beforeTestMethod(final TestContext testContext) throws Exception {
		Method testMethod = testContext.getTestMethod();
		Class<?> testClass = testContext.getTestClass();
		Assert.notNull(testMethod, "Test method of supplied TestContext must not be null");

		TransactionContext txContext = TransactionContextHolder.removeCurrentTransactionContext();
		Assert.state(txContext == null, "Cannot start new transaction without ending existing transaction");

		PlatformTransactionManager tm = null;
		TransactionAttribute transactionAttribute = this.attributeSource.getTransactionAttribute(testMethod, testClass);

		if (transactionAttribute != null) {
			transactionAttribute = TestContextTransactionUtils.createDelegatingTransactionAttribute(testContext,
				transactionAttribute);

			if (logger.isDebugEnabled()) {
				logger.debug("Explicit transaction definition [" + transactionAttribute +
						"] found for test context " + testContext);
			}

			if (transactionAttribute.getPropagationBehavior() == TransactionDefinition.PROPAGATION_NOT_SUPPORTED) {
				return;
			}

			tm = getTransactionManager(testContext, transactionAttribute.getQualifier());
			Assert.state(tm != null,
					() -> "Failed to retrieve PlatformTransactionManager for @Transactional test: " + testContext);
		}

		if (tm != null) {
			txContext = new TransactionContext(testContext, tm, transactionAttribute, isRollback(testContext));
			runBeforeTransactionMethods(testContext);
			txContext.startTransaction();
			TransactionContextHolder.setCurrentTransactionContext(txContext);
		}
	}

	/**
	 * If a transaction is currently active for the supplied
	 * {@linkplain TestContext test context}, this method will end the transaction
	 * and run {@link AfterTransaction @AfterTransaction} methods.
	 * <p>{@code @AfterTransaction} methods are guaranteed to be invoked even if
	 * an error occurs while ending the transaction.
	 */
	/**
	 * 如果当前对于所提供的{@link  plain TestContext测试上下文}处于活动状态，则此方法将终止该事务并运行{@link  AfterTransaction @AfterTransaction}方法。 
	 * 即使结束事务时发生错误，也可以确保调用<p> {<@code> @AfterTransaction}方法。 
	 * 
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		Method testMethod = testContext.getTestMethod();
		Assert.notNull(testMethod, "The test method of the supplied TestContext must not be null");

		TransactionContext txContext = TransactionContextHolder.removeCurrentTransactionContext();
		// If there was (or perhaps still is) a transaction...
		if (txContext != null) {
			TransactionStatus transactionStatus = txContext.getTransactionStatus();
			try {
				// If the transaction is still active...
				if (transactionStatus != null && !transactionStatus.isCompleted()) {
					txContext.endTransaction();
				}
			}
			finally {
				runAfterTransactionMethods(testContext);
			}
		}
	}

	/**
	 * Run all {@link BeforeTransaction @BeforeTransaction} methods for the
	 * specified {@linkplain TestContext test context}. If one of the methods
	 * fails, however, the caught exception will be rethrown in a wrapped
	 * {@link RuntimeException}, and the remaining methods will <strong>not</strong>
	 * be given a chance to execute.
	 * @param testContext the current test context
	 */
	/**
	 * 为指定的{@link  plain TestContext测试上下文}运行所有{@link  BeforeTransaction @BeforeTransaction}方法。 
	 * 但是，如果其中一种方法失败，则捕获的异常将在包装的{@link  RuntimeException}中重新抛出，并且<strong>不会</ strong>的其余方法将有机会执行。 
	 *  
	 * @param  testContext当前的测试上下文
	 */
	protected void runBeforeTransactionMethods(TestContext testContext) throws Exception {
		try {
			List<Method> methods = getAnnotatedMethods(testContext.getTestClass(), BeforeTransaction.class);
			Collections.reverse(methods);
			for (Method method : methods) {
				if (logger.isDebugEnabled()) {
					logger.debug("Executing @BeforeTransaction method [" + method + "] for test context " + testContext);
				}
				ReflectionUtils.makeAccessible(method);
				method.invoke(testContext.getTestInstance());
			}
		}
		catch (InvocationTargetException ex) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception encountered while executing @BeforeTransaction methods for test context " +
						testContext + ".", ex.getTargetException());
			}
			ReflectionUtils.rethrowException(ex.getTargetException());
		}
	}

	/**
	 * Run all {@link AfterTransaction @AfterTransaction} methods for the
	 * specified {@linkplain TestContext test context}. If one of the methods
	 * fails, the caught exception will be logged as an error, and the remaining
	 * methods will be given a chance to execute. After all methods have
	 * executed, the first caught exception, if any, will be rethrown.
	 * @param testContext the current test context
	 */
	/**
	 * 为指定的{@link  plain TestContext测试上下文}运行所有{@link  AfterTransaction @AfterTransaction}方法。 
	 * 如果其中一种方法失败，则捕获的异常将记录为错误，其余方法将有机会执行。 
	 * 执行完所有方法后，将重新抛出第一个捕获的异常（如果有）。 
	 *  
	 * @param  testContext当前的测试上下文
	 */
	protected void runAfterTransactionMethods(TestContext testContext) throws Exception {
		Throwable afterTransactionException = null;

		List<Method> methods = getAnnotatedMethods(testContext.getTestClass(), AfterTransaction.class);
		for (Method method : methods) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Executing @AfterTransaction method [" + method + "] for test context " + testContext);
				}
				ReflectionUtils.makeAccessible(method);
				method.invoke(testContext.getTestInstance());
			}
			catch (InvocationTargetException ex) {
				Throwable targetException = ex.getTargetException();
				if (afterTransactionException == null) {
					afterTransactionException = targetException;
				}
				logger.error("Exception encountered while executing @AfterTransaction method [" + method +
						"] for test context " + testContext, targetException);
			}
			catch (Exception ex) {
				if (afterTransactionException == null) {
					afterTransactionException = ex;
				}
				logger.error("Exception encountered while executing @AfterTransaction method [" + method +
						"] for test context " + testContext, ex);
			}
		}

		if (afterTransactionException != null) {
			ReflectionUtils.rethrowException(afterTransactionException);
		}
	}

	/**
	 * Get the {@linkplain PlatformTransactionManager transaction manager} to use
	 * for the supplied {@linkplain TestContext test context} and {@code qualifier}.
	 * <p>Delegates to {@link #getTransactionManager(TestContext)} if the
	 * supplied {@code qualifier} is {@code null} or empty.
	 * @param testContext the test context for which the transaction manager
	 * should be retrieved
	 * @param qualifier the qualifier for selecting between multiple bean matches;
	 * may be {@code null} or empty
	 * @return the transaction manager to use, or {@code null} if not found
	 * @throws BeansException if an error occurs while retrieving the transaction manager
	 * @see #getTransactionManager(TestContext)
	 */
	/**
	 * 获取{@link  plain PlatformTransactionManager事务管理器}，以用于提供的{@link  plain TestContext测试上下文}和{@code 限定符}。 
	 *  <p>如果提供的{@code 限定符}为{@code  null}或为空，则委托给{@link  #getTransactionManager（TestContext）}。 
	 *  
	 * @param  testContext应为其获取事务管理器的测试上下文。 
	 * 
	 * @param 限定符：用于在多个bean匹配之间进行选择的限定符； 
	 * 可能为{@code  null}或为空的
	 * @return 要使用的事务管理器，或者为{@code  null}（如果未找到）
	 * @throws  BeansException如果在检索事务管理器时发生错误
	 * @see  #getTransactionManager（TestContext）
	 */
	@Nullable
	protected PlatformTransactionManager getTransactionManager(TestContext testContext, @Nullable String qualifier) {
		// Look up by type and qualifier from @Transactional
		if (StringUtils.hasText(qualifier)) {
			try {
				// Use autowire-capable factory in order to support extended qualifier matching
				// (only exposed on the internal BeanFactory, not on the ApplicationContext).
				BeanFactory bf = testContext.getApplicationContext().getAutowireCapableBeanFactory();

				return BeanFactoryAnnotationUtils.qualifiedBeanOfType(bf, PlatformTransactionManager.class, qualifier);
			}
			catch (RuntimeException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn(String.format(
							"Caught exception while retrieving transaction manager with qualifier '%s' for test context %s",
							qualifier, testContext), ex);
				}
				throw ex;
			}
		}

		// else
		return getTransactionManager(testContext);
	}

	/**
	 * Get the {@linkplain PlatformTransactionManager transaction manager}
	 * to use for the supplied {@linkplain TestContext test context}.
	 * <p>The default implementation simply delegates to
	 * {@link TestContextTransactionUtils#retrieveTransactionManager}.
	 * @param testContext the test context for which the transaction manager
	 * should be retrieved
	 * @return the transaction manager to use, or {@code null} if not found
	 * @throws BeansException if an error occurs while retrieving an explicitly
	 * named transaction manager
	 * @throws IllegalStateException if more than one TransactionManagementConfigurer
	 * exists in the ApplicationContext
	 * @see #getTransactionManager(TestContext, String)
	 */
	/**
	 * 获取{@link  plain PlatformTransactionManager事务管理器}，以用于提供的{@link  plain TestContext测试上下文}。 
	 *  <p>默认实现只是将委托委托给{@link  TestContextTransactionUtils＃retrieveTransactionManager}。 
	 *  
	 * @param  testContext应为其获取事务管理器的测试上下文<@r​​eturn>要使用的事务管理器，如果找不到，则为{@code  null} 
	 * @throws  BeansException如果在显式检索显式时发生错误如果ApplicationContext中存在多个TransactionManagementConfigurer，则命名为事务管理器
	 * @throws  IllegalStateException 
	 * @see  #getTransactionManager（TestContext，String）
	 */
	@Nullable
	protected PlatformTransactionManager getTransactionManager(TestContext testContext) {
		return TestContextTransactionUtils.retrieveTransactionManager(testContext, null);
	}

	/**
	 * Determine whether or not to rollback transactions by default for the
	 * supplied {@linkplain TestContext test context}.
	 * <p>Supports {@link Rollback @Rollback} or {@link Commit @Commit} at the
	 * class-level.
	 * @param testContext the test context for which the default rollback flag
	 * should be retrieved
	 * @return the <em>default rollback</em> flag for the supplied test context
	 * @throws Exception if an error occurs while determining the default rollback flag
	 */
	/**
	 * 确定默认情况下是否为提供的{@link  plain TestContext测试上下文}回滚事务。 
	 *  <p>在类级别支持{@link 回滚@回滚}或{@link 提交@Commit}。 
	 *  
	 * @param  testContext应为其检索默认回滚标志的测试上下文<@r​​eturn>所提供的测试上下文的<em>默认回滚</ em>标志
	 * @throws 如果在确定默认值时发生错误，则为异常回滚标志
	 */
	protected final boolean isDefaultRollback(TestContext testContext) throws Exception {
		Class<?> testClass = testContext.getTestClass();
		Rollback rollback = AnnotatedElementUtils.findMergedAnnotation(testClass, Rollback.class);
		boolean rollbackPresent = (rollback != null);

		if (rollbackPresent) {
			boolean defaultRollback = rollback.value();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Retrieved default @Rollback(%s) for test class [%s].",
						defaultRollback, testClass.getName()));
			}
			return defaultRollback;
		}

		// else
		return true;
	}

	/**
	 * Determine whether or not to rollback transactions for the supplied
	 * {@linkplain TestContext test context} by taking into consideration the
	 * {@linkplain #isDefaultRollback(TestContext) default rollback} flag and a
	 * possible method-level override via the {@link Rollback @Rollback}
	 * annotation.
	 * @param testContext the test context for which the rollback flag
	 * should be retrieved
	 * @return the <em>rollback</em> flag for the supplied test context
	 * @throws Exception if an error occurs while determining the rollback flag
	 */
	/**
	 * 通过考虑{@link  plain #isDefaultRollback（TestContext）默认回滚}标志以及通过{的可能的方法级别覆盖，确定是否为提供的{@link  plain TestContext测试上下文}回滚事务。 
	 *  @link 回滚@回滚}注解。 
	 *  
	 * @param  testContext应为其获取回退标志的测试上下文<@r​​eturn>提供的测试上下文的<em> rollback </ em>标志
	 * @throws 如果在确定回退标志时发生错误，则为异常
	 */
	protected final boolean isRollback(TestContext testContext) throws Exception {
		boolean rollback = isDefaultRollback(testContext);
		Rollback rollbackAnnotation =
				AnnotatedElementUtils.findMergedAnnotation(testContext.getTestMethod(), Rollback.class);
		if (rollbackAnnotation != null) {
			boolean rollbackOverride = rollbackAnnotation.value();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format(
						"Method-level @Rollback(%s) overrides default rollback [%s] for test context %s.",
						rollbackOverride, rollback, testContext));
			}
			rollback = rollbackOverride;
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format(
						"No method-level @Rollback override: using default rollback [%s] for test context %s.",
						rollback, testContext));
			}
		}
		return rollback;
	}

	/**
	 * Get all methods in the supplied {@link Class class} and its superclasses
	 * which are annotated with the supplied {@code annotationType} but
	 * which are not <em>shadowed</em> by methods overridden in subclasses.
	 * <p>Default methods on interfaces are also detected.
	 * @param clazz the class for which to retrieve the annotated methods
	 * @param annotationType the annotation type for which to search
	 * @return all annotated methods in the supplied class and its superclasses
	 * as well as annotated interface default methods
	 */
	/**
	 * 获取提供的{@link 类类}及其超类中的所有方法，这些方法用提供的{@code 注解类型}注释，但未被子类中覆盖的方法<em>遮盖</ em>。 
	 *  <p>还会检测接口上的默认方法。 
	 *  
	 * @param 区分要为其检索带注释的方法的类
	 * @param 注解类型要为其提供搜索的注释类型
	 * @return 提供的类及其超类中的所有带注释的方法以及带接口的默认方法
	 */
	private List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationType) {
		return Arrays.stream(ReflectionUtils.getUniqueDeclaredMethods(clazz, ReflectionUtils.USER_DECLARED_METHODS))
				.filter(method -> AnnotatedElementUtils.hasAnnotation(method, annotationType))
				.collect(Collectors.toList());
	}

}
