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

package org.springframework.scheduling.annotation;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncExecutionInterceptor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

/**
 * Specialization of {@link AsyncExecutionInterceptor} that delegates method execution to
 * an {@code Executor} based on the {@link Async} annotation. Specifically designed to
 * support use of {@link Async#value()} executor qualification mechanism introduced in
 * Spring 3.1.2. Supports detecting qualifier metadata via {@code @Async} at the method or
 * declaring class level. See {@link #getExecutorQualifier(Method)} for details.
 *
 * @author Chris Beams
 * @author Stephane Nicoll
 * @since 3.1.2
 * @see org.springframework.scheduling.annotation.Async
 * @see org.springframework.scheduling.annotation.AsyncAnnotationAdvisor
 */
/**
 * {@link  AsyncExecutionInterceptor}的专业化，它基于{@link  Async}注解将方法执行委派给{@code  Executor}。 
 * 专门设计用于支持使用Spring 3.1.2中引入的{@link  Async＃value（）}执行者限定机制。 
 * 支持在方法或声明类级别通过{@code  @Async}检测限定符元数据。 
 * 有关详细信息，请参见{@link  #getExecutorQualifier（Method）}。 
 *  @author 克里斯·比姆斯（Chris Beams）@author  Stephane Nicoll @since 3.1.2 
 * @see  org.springframework.scheduling.annotation.Async 
 * @see  org.springframework.scheduling.annotation.AsyncAnnotationAdvisor
 */
public class AnnotationAsyncExecutionInterceptor extends AsyncExecutionInterceptor {

	/**
	 * Create a new {@code AnnotationAsyncExecutionInterceptor} with the given executor
	 * and a simple {@link AsyncUncaughtExceptionHandler}.
	 * @param defaultExecutor the executor to be used by default if no more specific
	 * executor has been qualified at the method level using {@link Async#value()};
	 * as of 4.2.6, a local executor for this interceptor will be built otherwise
	 */
	/**
	 * 使用给定的执行器和一个简单的{@link  AsyncUncaughtExceptionHandler}创建一个新的{@code  AnnotationAsyncExecutionInterceptor}。 
	 *  
	 * @param  defaultExecutor如果没有使用{@link  Async＃value（）}在方法级别对特定执行器进行限定，则默认使用该执行器。 
	 * 从4.2.6开始，将为该拦截器构建本地执行程序
	 */
	public AnnotationAsyncExecutionInterceptor(@Nullable Executor defaultExecutor) {
		super(defaultExecutor);
	}

	/**
	 * Create a new {@code AnnotationAsyncExecutionInterceptor} with the given executor.
	 * @param defaultExecutor the executor to be used by default if no more specific
	 * executor has been qualified at the method level using {@link Async#value()};
	 * as of 4.2.6, a local executor for this interceptor will be built otherwise
	 * @param exceptionHandler the {@link AsyncUncaughtExceptionHandler} to use to
	 * handle exceptions thrown by asynchronous method executions with {@code void}
	 * return type
	 */
	/**
	 * 使用给定的执行程序创建一个新的{@code  AnnotationAsyncExecutionInterceptor}。 
	 *  
	 * @param  defaultExecutor如果没有使用{@link  Async＃value（）}在方法级别对特定执行器进行限定，则默认使用该执行器。 
	 * 从4.2.6版本开始，将为该拦截器构建一个本地执行程序，否则将使用
	 * @param  exceptionHandler和{@link  AsyncUncaughtExceptionHandler}来处理由具有{@code  void}返回类型的异步方法执行引发的异常
	 */
	public AnnotationAsyncExecutionInterceptor(@Nullable Executor defaultExecutor, AsyncUncaughtExceptionHandler exceptionHandler) {
		super(defaultExecutor, exceptionHandler);
	}


	/**
	 * Return the qualifier or bean name of the executor to be used when executing the
	 * given method, specified via {@link Async#value} at the method or declaring
	 * class level. If {@code @Async} is specified at both the method and class level, the
	 * method's {@code #value} takes precedence (even if empty string, indicating that
	 * the default executor should be used preferentially).
	 * @param method the method to inspect for executor qualifier metadata
	 * @return the qualifier if specified, otherwise empty string indicating that the
	 * {@linkplain #setExecutor(Executor) default executor} should be used
	 * @see #determineAsyncExecutor(Method)
	 */
	/**
	 * 返回在执行给定方法时要使用的执行程序的限定符或bean名称，该限定符或bean名称通过方法或声明类级别上的{@link  Async＃value}指定。 
	 * 如果在方法级别和类级别均指定了{@code  @Async}，则该方法的{@code  #value}优先（即使为空字符串，也表明应优先使用默认执行程序）。 
	 *  
	 * @param 方法检查执行者限定符元数据的方法
	 * @return 限定符（如果已指定），否则为空字符串，指示应使用{@link  plain #setExecutor（Executor）默认执行者} 
	 * @see ＃ defineAsyncExecutor（Method）
	 */
	@Override
	@Nullable
	protected String getExecutorQualifier(Method method) {
		// Maintainer's note: changes made here should also be made in
		// AnnotationAsyncExecutionAspect#getExecutorQualifier
		Async async = AnnotatedElementUtils.findMergedAnnotation(method, Async.class);
		if (async == null) {
			async = AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), Async.class);
		}
		return (async != null ? async.value() : null);
	}

}
