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

package org.springframework.test.context.jdbc;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig.ErrorMode;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.transaction.TestContextTransactionUtils;
import org.springframework.test.context.util.TestContextResourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * {@code TestExecutionListener} that provides support for executing SQL
 * {@link Sql#scripts scripts} and inlined {@link Sql#statements statements}
 * configured via the {@link Sql @Sql} annotation.
 *
 * <p>Scripts and inlined statements will be executed {@linkplain #beforeTestMethod(TestContext) before}
 * or {@linkplain #afterTestMethod(TestContext) after} execution of the corresponding
 * {@linkplain java.lang.reflect.Method test method}, depending on the configured
 * value of the {@link Sql#executionPhase executionPhase} flag.
 *
 * <p>Scripts and inlined statements will be executed without a transaction,
 * within an existing Spring-managed transaction, or within an isolated transaction,
 * depending on the configured value of {@link SqlConfig#transactionMode} and the
 * presence of a transaction manager.
 *
 * <h3>Script Resources</h3>
 * <p>For details on default script detection and how script resource locations
 * are interpreted, see {@link Sql#scripts}.
 *
 * <h3>Required Spring Beans</h3>
 * <p>A {@link PlatformTransactionManager} <em>and</em> a {@link DataSource},
 * just a {@link PlatformTransactionManager}, or just a {@link DataSource}
 * must be defined as beans in the Spring {@link ApplicationContext} for the
 * corresponding test. Consult the javadocs for {@link SqlConfig#transactionMode},
 * {@link SqlConfig#transactionManager}, {@link SqlConfig#dataSource},
 * {@link TestContextTransactionUtils#retrieveDataSource}, and
 * {@link TestContextTransactionUtils#retrieveTransactionManager} for details
 * on permissible configuration constellations and on the algorithms used to
 * locate these beans.
 *
 * @author Sam Brannen
 * @author Dmitry Semukhin
 * @since 4.1
 * @see Sql
 * @see SqlConfig
 * @see SqlGroup
 * @see org.springframework.test.context.transaction.TestContextTransactionUtils
 * @see org.springframework.test.context.transaction.TransactionalTestExecutionListener
 * @see org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
 * @see org.springframework.jdbc.datasource.init.ScriptUtils
 */
/**
 * {@code  TestExecutionListener}为执行SQL {@link  Sql＃scripts脚本}和通过{@link  Sql @Sql}注释配置的内联{@link  Sql＃statements语句}提供支持。 
 *  <p>脚本和内联语句将在{@link  plain java.lang执行之后{{@@link> plain #beforeTestMethod（TestContext）之前}或{@link  plain #afterTestMethod（TestContext）之后}执行。 
 *  .reflect.Method测试方法}，具体取决于{@link  Sql＃executionPhase executionPhase}标志的配置值。 
 *  <p>脚本和内联语句将在没有事务的情况下在现有的Spring管理的事务中或在隔离的事务中执行，这取决于{@link  SqlConfig＃transactionMode}的配置值和事务管理器的存在。 
 *  <h3>脚本资源</ h3> <p>有关默认脚本检测以及如何解释脚本资源位置的详细信息，请参见{@link  Sql＃scripts}。 
 *  <h3>必需的Spring Beans </ h3> <p>一个{@link  PlatformTransactionManager} <em>和</ em>一个{@link  DataSource}，一个{@link  PlatformTransactionManager}或仅必须在春季{@link  ApplicationContext}中将{@link  DataSource}定义为bean，以进行相应的测试。 
 * 请向javadocs咨询{@link  SqlConfig＃transactionMode}，{<@link> SqlConfig＃transactionManager}，{<@link> SqlConfig＃dataSource}，{<@link> TestContextTransactionUtils＃retrieveDataSource}和{@link 请参见TestContextTransactionUtils＃retrieveTransactionManager}，以获取有关允许的配置星座以及用于定位这些bean的算法的详细信息。 
 *  @author  Sam Brannen @author  Dmitry Semukhin @从4.1起
 * @see  Sql 
 * @see  SqlConfig 
 * @see  SqlGroup 
 * @see  org.springframework.test.context.transaction.TestContextTransactionUtils 
 * @see  org .springframework.test.context.transaction.TransactionalTestExecutionListener 
 * @see  org.springframework.jdbc.datasource.init.ResourceDatabasePopulator 
 * @see  org.springframework.jdbc.datasource.init.ScriptUtils
 */
public class SqlScriptsTestExecutionListener extends AbstractTestExecutionListener {

	private static final Log logger = LogFactory.getLog(SqlScriptsTestExecutionListener.class);


	/**
	 * Returns {@code 5000}.
	 */
	/**
	 * 返回{@code  5000}。 
	 * 
	 */
	@Override
	public final int getOrder() {
		return 5000;
	}

	/**
	 * Execute SQL scripts configured via {@link Sql @Sql} for the supplied
	 * {@link TestContext} <em>before</em> the current test method.
	 */
	/**
	 * 在当前测试方法之前，执行通过{@link  Sql @Sql}为提供的{@link  TestContext} <em> </ em>配置的SQL脚本。 
	 * 
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) {
		executeSqlScripts(testContext, ExecutionPhase.BEFORE_TEST_METHOD);
	}

	/**
	 * Execute SQL scripts configured via {@link Sql @Sql} for the supplied
	 * {@link TestContext} <em>after</em> the current test method.
	 */
	/**
	 * 在当前测试方法之后，为提供的{@link  TestContext} <em> </ em>执行通过{@link  Sql @Sql}配置的SQL脚本。 
	 * 
	 */
	@Override
	public void afterTestMethod(TestContext testContext) {
		executeSqlScripts(testContext, ExecutionPhase.AFTER_TEST_METHOD);
	}

	/**
	 * Execute SQL scripts configured via {@link Sql @Sql} for the supplied
	 * {@link TestContext} and {@link ExecutionPhase}.
	 */
	/**
	 * 为提供的{@link  TestContext}和{@link  ExecutionPhase}执行通过{@link  Sql @Sql}配置的SQL脚本。 
	 * 
	 */
	private void executeSqlScripts(TestContext testContext, ExecutionPhase executionPhase) {
		Method testMethod = testContext.getTestMethod();
		Class<?> testClass = testContext.getTestClass();

		if (mergeSqlAnnotations(testContext)) {
			executeSqlScripts(getSqlAnnotationsFor(testClass), testContext, executionPhase, true);
			executeSqlScripts(getSqlAnnotationsFor(testMethod), testContext, executionPhase, false);
		}
		else {
			Set<Sql> methodLevelSqlAnnotations = getSqlAnnotationsFor(testMethod);
			if (!methodLevelSqlAnnotations.isEmpty()) {
				executeSqlScripts(methodLevelSqlAnnotations, testContext, executionPhase, false);
			}
			else {
				executeSqlScripts(getSqlAnnotationsFor(testClass), testContext, executionPhase, true);
			}
		}
	}

	/**
	 * Determine if method-level {@code @Sql} annotations should be merged with
	 * class-level {@code @Sql} annotations.
	 */
	/**
	 * 确定是否应将方法级别的{@code  @Sql}注释与类级别的{@code  @Sql}注释合并。 
	 * 
	 */
	private boolean mergeSqlAnnotations(TestContext testContext) {
		SqlMergeMode sqlMergeMode = getSqlMergeModeFor(testContext.getTestMethod());
		if (sqlMergeMode == null) {
			sqlMergeMode = getSqlMergeModeFor(testContext.getTestClass());
		}
		return (sqlMergeMode != null && sqlMergeMode.value() == MergeMode.MERGE);
	}

	/**
	 * Get the {@code @SqlMergeMode} annotation declared on the supplied {@code element}.
	 */
	/**
	 * 获取在提供的{@code 元素}上声明的{@code  @SqlMergeMode}注解。 
	 * 
	 */
	@Nullable
	private SqlMergeMode getSqlMergeModeFor(AnnotatedElement element) {
		return AnnotatedElementUtils.findMergedAnnotation(element, SqlMergeMode.class);
	}

	/**
	 * Get the {@code @Sql} annotations declared on the supplied {@code element}.
	 */
	/**
	 * 获取在提供的{@code 元素}上声明的{@code  @Sql}注解。 
	 * 
	 */
	private Set<Sql> getSqlAnnotationsFor(AnnotatedElement element) {
		return AnnotatedElementUtils.getMergedRepeatableAnnotations(element, Sql.class, SqlGroup.class);
	}

	/**
	 * Execute SQL scripts for the supplied {@link Sql @Sql} annotations.
	 */
	/**
	 * 为提供的{@link  Sql @Sql}注释执行SQL脚本。 
	 * 
	 */
	private void executeSqlScripts(
			Set<Sql> sqlAnnotations, TestContext testContext, ExecutionPhase executionPhase, boolean classLevel) {

		sqlAnnotations.forEach(sql -> executeSqlScripts(sql, executionPhase, testContext, classLevel));
	}

	/**
	 * Execute the SQL scripts configured via the supplied {@link Sql @Sql}
	 * annotation for the given {@link ExecutionPhase} and {@link TestContext}.
	 * <p>Special care must be taken in order to properly support the configured
	 * {@link SqlConfig#transactionMode}.
	 * @param sql the {@code @Sql} annotation to parse
	 * @param executionPhase the current execution phase
	 * @param testContext the current {@code TestContext}
	 * @param classLevel {@code true} if {@link Sql @Sql} was declared at the class level
	 */
	/**
	 * 对给定的{@link  ExecutionPhase}和{@link  TestContext}执行通过提供的{@link  Sql @Sql}注释配置的SQL脚本。 
	 *  <p>必须特别注意才能正确支持已配置的{@link  SqlConfig＃transactionMode}。 
	 *  
	 * @param  sql {{@@code> @Sql}注释以解析
	 * @param 执行阶段当前执行阶段
	 * @param  testContext当前{@code  TestContext} 
	 * @param  classLevel {@code 如果在类级别声明了{@link  Sql @Sql}，则返回true
	 */
	private void executeSqlScripts(
			Sql sql, ExecutionPhase executionPhase, TestContext testContext, boolean classLevel) {

		if (executionPhase != sql.executionPhase()) {
			return;
		}

		MergedSqlConfig mergedSqlConfig = new MergedSqlConfig(sql.config(), testContext.getTestClass());
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Processing %s for execution phase [%s] and test context %s.",
					mergedSqlConfig, executionPhase, testContext));
		}

		String[] scripts = getScripts(sql, testContext, classLevel);
		scripts = TestContextResourceUtils.convertToClasspathResourcePaths(testContext.getTestClass(), scripts);
		List<Resource> scriptResources = TestContextResourceUtils.convertToResourceList(
				testContext.getApplicationContext(), scripts);
		for (String stmt : sql.statements()) {
			if (StringUtils.hasText(stmt)) {
				stmt = stmt.trim();
				scriptResources.add(new ByteArrayResource(stmt.getBytes(), "from inlined SQL statement: " + stmt));
			}
		}

		ResourceDatabasePopulator populator = createDatabasePopulator(mergedSqlConfig);
		populator.setScripts(scriptResources.toArray(new Resource[0]));
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL scripts: " + ObjectUtils.nullSafeToString(scriptResources));
		}

		String dsName = mergedSqlConfig.getDataSource();
		String tmName = mergedSqlConfig.getTransactionManager();
		DataSource dataSource = TestContextTransactionUtils.retrieveDataSource(testContext, dsName);
		PlatformTransactionManager txMgr = TestContextTransactionUtils.retrieveTransactionManager(testContext, tmName);
		boolean newTxRequired = (mergedSqlConfig.getTransactionMode() == TransactionMode.ISOLATED);

		if (txMgr == null) {
			Assert.state(!newTxRequired, () -> String.format("Failed to execute SQL scripts for test context %s: " +
					"cannot execute SQL scripts using Transaction Mode " +
					"[%s] without a PlatformTransactionManager.", testContext, TransactionMode.ISOLATED));
			Assert.state(dataSource != null, () -> String.format("Failed to execute SQL scripts for test context %s: " +
					"supply at least a DataSource or PlatformTransactionManager.", testContext));
			// Execute scripts directly against the DataSource
			populator.execute(dataSource);
		}
		else {
			DataSource dataSourceFromTxMgr = getDataSourceFromTransactionManager(txMgr);
			// Ensure user configured an appropriate DataSource/TransactionManager pair.
			if (dataSource != null && dataSourceFromTxMgr != null && !dataSource.equals(dataSourceFromTxMgr)) {
				throw new IllegalStateException(String.format("Failed to execute SQL scripts for test context %s: " +
						"the configured DataSource [%s] (named '%s') is not the one associated with " +
						"transaction manager [%s] (named '%s').", testContext, dataSource.getClass().getName(),
						dsName, txMgr.getClass().getName(), tmName));
			}
			if (dataSource == null) {
				dataSource = dataSourceFromTxMgr;
				Assert.state(dataSource != null, () -> String.format("Failed to execute SQL scripts for " +
						"test context %s: could not obtain DataSource from transaction manager [%s] (named '%s').",
						testContext, txMgr.getClass().getName(), tmName));
			}
			final DataSource finalDataSource = dataSource;
			int propagation = (newTxRequired ? TransactionDefinition.PROPAGATION_REQUIRES_NEW :
					TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionAttribute txAttr = TestContextTransactionUtils.createDelegatingTransactionAttribute(
					testContext, new DefaultTransactionAttribute(propagation));
			new TransactionTemplate(txMgr, txAttr).executeWithoutResult(s -> populator.execute(finalDataSource));
		}
	}

	@NonNull
	private ResourceDatabasePopulator createDatabasePopulator(MergedSqlConfig mergedSqlConfig) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setSqlScriptEncoding(mergedSqlConfig.getEncoding());
		populator.setSeparator(mergedSqlConfig.getSeparator());
		populator.setCommentPrefixes(mergedSqlConfig.getCommentPrefixes());
		populator.setBlockCommentStartDelimiter(mergedSqlConfig.getBlockCommentStartDelimiter());
		populator.setBlockCommentEndDelimiter(mergedSqlConfig.getBlockCommentEndDelimiter());
		populator.setContinueOnError(mergedSqlConfig.getErrorMode() == ErrorMode.CONTINUE_ON_ERROR);
		populator.setIgnoreFailedDrops(mergedSqlConfig.getErrorMode() == ErrorMode.IGNORE_FAILED_DROPS);
		return populator;
	}

	@Nullable
	private DataSource getDataSourceFromTransactionManager(PlatformTransactionManager transactionManager) {
		try {
			Method getDataSourceMethod = transactionManager.getClass().getMethod("getDataSource");
			Object obj = ReflectionUtils.invokeMethod(getDataSourceMethod, transactionManager);
			if (obj instanceof DataSource) {
				return (DataSource) obj;
			}
		}
		catch (Exception ex) {
			// ignore
		}
		return null;
	}

	private String[] getScripts(Sql sql, TestContext testContext, boolean classLevel) {
		String[] scripts = sql.scripts();
		if (ObjectUtils.isEmpty(scripts) && ObjectUtils.isEmpty(sql.statements())) {
			scripts = new String[] {detectDefaultScript(testContext, classLevel)};
		}
		return scripts;
	}

	/**
	 * Detect a default SQL script by implementing the algorithm defined in
	 * {@link Sql#scripts}.
	 */
	/**
	 * 通过实现{@link  Sql＃scripts}中定义的算法来检测默认的SQL脚本。 
	 * 
	 */
	private String detectDefaultScript(TestContext testContext, boolean classLevel) {
		Class<?> clazz = testContext.getTestClass();
		Method method = testContext.getTestMethod();
		String elementType = (classLevel ? "class" : "method");
		String elementName = (classLevel ? clazz.getName() : method.toString());

		String resourcePath = ClassUtils.convertClassNameToResourcePath(clazz.getName());
		if (!classLevel) {
			resourcePath += "." + method.getName();
		}
		resourcePath += ".sql";

		String prefixedResourcePath = ResourceUtils.CLASSPATH_URL_PREFIX + resourcePath;
		ClassPathResource classPathResource = new ClassPathResource(resourcePath);

		if (classPathResource.exists()) {
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Detected default SQL script \"%s\" for test %s [%s]",
						prefixedResourcePath, elementType, elementName));
			}
			return prefixedResourcePath;
		}
		else {
			String msg = String.format("Could not detect default SQL script for test %s [%s]: " +
					"%s does not exist. Either declare statements or scripts via @Sql or make the " +
					"default SQL script available.", elementType, elementName, classPathResource);
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
	}

}
