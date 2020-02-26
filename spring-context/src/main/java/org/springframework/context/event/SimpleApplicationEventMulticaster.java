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

package org.springframework.context.event;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

/**
 * Simple implementation of the {@link ApplicationEventMulticaster} interface.
 *
 * <p>Multicasts all events to all registered listeners, leaving it up to
 * the listeners to ignore events that they are not interested in.
 * Listeners will usually perform corresponding {@code instanceof}
 * checks on the passed-in event object.
 *
 * <p>By default, all listeners are invoked in the calling thread.
 * This allows the danger of a rogue listener blocking the entire application,
 * but adds minimal overhead. Specify an alternative task executor to have
 * listeners executed in different threads, for example from a thread pool.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @see #setTaskExecutor
 */
/**
 * {@link  ApplicationEventMulticaster}接口的简单实现。 
 *  <p>将所有事件多播到所有已注册的侦听器，让侦听器忽略它们不感兴趣的事件。 
 * 侦听器通常将对传入的事件对象执行相应的{@code  instanceof}检查。 
 *  <p>默认情况下，所有侦听器都在调用线程中被调用。 
 * 这带来了恶意侦听器阻塞整个应用程序的危险，但增加了最小的开销。 
 * 指定替代任务执行程序，以使侦听器在不同的线程中执行，例如从线程池中执行。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @author  Stephane Nicoll 
 * @see  #setTaskExecutor
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

	@Nullable
	private Executor taskExecutor;

	@Nullable
	private ErrorHandler errorHandler;


	/**
	 * Create a new SimpleApplicationEventMulticaster.
	 */
	/**
	 * 创建一个新的SimpleApplicationEventMulticaster。 
	 * 
	 */
	public SimpleApplicationEventMulticaster() {
	}

	/**
	 * Create a new SimpleApplicationEventMulticaster for the given BeanFactory.
	 */
	/**
	 * 为给定的BeanFactory创建一个新的SimpleApplicationEventMulticaster。 
	 * 
	 */
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}


	/**
	 * Set a custom executor (typically a {@link org.springframework.core.task.TaskExecutor})
	 * to invoke each listener with.
	 * <p>Default is equivalent to {@link org.springframework.core.task.SyncTaskExecutor},
	 * executing all listeners synchronously in the calling thread.
	 * <p>Consider specifying an asynchronous task executor here to not block the
	 * caller until all listeners have been executed. However, note that asynchronous
	 * execution will not participate in the caller's thread context (class loader,
	 * transaction association) unless the TaskExecutor explicitly supports this.
	 * @see org.springframework.core.task.SyncTaskExecutor
	 * @see org.springframework.core.task.SimpleAsyncTaskExecutor
	 */
	/**
	 * 设置一个自定义执行程序（通常为{@link  org.springframework.core.task.TaskExecutor}）来调用每个侦听器。 
	 *  <p>默认值等同于{@link  org.springframework.core.task.SyncTaskExecutor}，在调用线程中同步执行所有侦听器。 
	 *  <p>请考虑在此处指定一个异步任务执行程序，直到执行完所有侦听器后才阻止调用者。 
	 * 但是，请注意，除非TaskExecutor明确支持，否则异步执行将不参与调用者的线程上下文（类加载器，事务关联）。 
	 *  
	 * @see  org.springframework.core.task.SyncTaskExecutor 
	 * @see  org.springframework.core.task.SimpleAsyncTaskExecutor
	 */
	public void setTaskExecutor(@Nullable Executor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Return the current task executor for this multicaster.
	 */
	/**
	 * 返回此多播程序的当前任务执行程序。 
	 * 
	 */
	@Nullable
	protected Executor getTaskExecutor() {
		return this.taskExecutor;
	}

	/**
	 * Set the {@link ErrorHandler} to invoke in case an exception is thrown
	 * from a listener.
	 * <p>Default is none, with a listener exception stopping the current
	 * multicast and getting propagated to the publisher of the current event.
	 * If a {@linkplain #setTaskExecutor task executor} is specified, each
	 * individual listener exception will get propagated to the executor but
	 * won't necessarily stop execution of other listeners.
	 * <p>Consider setting an {@link ErrorHandler} implementation that catches
	 * and logs exceptions (a la
	 * {@link org.springframework.scheduling.support.TaskUtils#LOG_AND_SUPPRESS_ERROR_HANDLER})
	 * or an implementation that logs exceptions while nevertheless propagating them
	 * (e.g. {@link org.springframework.scheduling.support.TaskUtils#LOG_AND_PROPAGATE_ERROR_HANDLER}).
	 * @since 4.1
	 */
	/**
	 * 设置{@link  ErrorHandler}以在从侦听器引发异常的情况下调用。 
	 *  <p>默认为none，侦听器异常会停止当前的多播并传播到当前事件的发布者。 
	 * 如果指定了{@link  plain #setTaskExecutor任务执行程序}，则每个侦听器异常都将传播到该执行程序，但不一定停止其他侦听器的执行。 
	 *  <p>请考虑设置捕获并记录异常的{@link  ErrorHandler}实现（例如{@link  org.springframework.scheduling.support.TaskUtils＃LOG_AND_SUPPRESS_ERROR_HANDLER}）或在记录异常的同时传播它们的实现（例如{@link  org.springframework.scheduling.support.TaskUtils＃LOG_AND_PROPAGATE_ERROR_HANDLER}）。 
	 *  @始于4.1
	 */
	public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/**
	 * Return the current error handler for this multicaster.
	 * @since 4.1
	 */
	/**
	 * 返回此多播程序的当前错误处理程序。 
	 *  @始于4.1
	 */
	@Nullable
	protected ErrorHandler getErrorHandler() {
		return this.errorHandler;
	}


	@Override
	public void multicastEvent(ApplicationEvent event) {
		multicastEvent(event, resolveDefaultEventType(event));
	}

	@Override
	public void multicastEvent(final ApplicationEvent event, @Nullable ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
		Executor executor = getTaskExecutor();
		for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
			if (executor != null) {
				executor.execute(() -> invokeListener(listener, event));
			}
			else {
				invokeListener(listener, event);
			}
		}
	}

	private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
		return ResolvableType.forInstance(event);
	}

	/**
	 * Invoke the given listener with the given event.
	 * @param listener the ApplicationListener to invoke
	 * @param event the current event to propagate
	 * @since 4.1
	 */
	/**
	 * 使用给定的事件调用给定的侦听器。 
	 *  
	 * @param 监听器ApplicationListener调用
	 * @param 事件将当前事件传播到@since 4.1起
	 */
	protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
		ErrorHandler errorHandler = getErrorHandler();
		if (errorHandler != null) {
			try {
				doInvokeListener(listener, event);
			}
			catch (Throwable err) {
				errorHandler.handleError(err);
			}
		}
		else {
			doInvokeListener(listener, event);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
		try {
			listener.onApplicationEvent(event);
		}
		catch (ClassCastException ex) {
			String msg = ex.getMessage();
			if (msg == null || matchesClassCastMessage(msg, event.getClass())) {
				// Possibly a lambda-defined listener which we could not resolve the generic event type for
				// -> let's suppress the exception and just log a debug message.
				Log logger = LogFactory.getLog(getClass());
				if (logger.isTraceEnabled()) {
					logger.trace("Non-matching event type for listener: " + listener, ex);
				}
			}
			else {
				throw ex;
			}
		}
	}

	private boolean matchesClassCastMessage(String classCastMessage, Class<?> eventClass) {
		// On Java 8, the message starts with the class name: "java.lang.String cannot be cast..."
		if (classCastMessage.startsWith(eventClass.getName())) {
			return true;
		}
		// On Java 11, the message starts with "class ..." a.k.a. Class.toString()
		if (classCastMessage.startsWith(eventClass.toString())) {
			return true;
		}
		// On Java 9, the message used to contain the module name: "java.base/java.lang.String cannot be cast..."
		int moduleSeparatorIndex = classCastMessage.indexOf('/');
		if (moduleSeparatorIndex != -1 && classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1)) {
			return true;
		}
		// Assuming an unrelated class cast failure...
		return false;
	}

}
