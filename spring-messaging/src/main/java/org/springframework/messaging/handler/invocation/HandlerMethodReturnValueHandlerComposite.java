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

package org.springframework.messaging.handler.invocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * A HandlerMethodReturnValueHandler that wraps and delegates to others.
 *
 * @author Rossen Stoyanchev
 * @since 4.0
 */
/**
 * 一个包装并委托给其他人的HandlerMethodReturnValueHandler。 
 *  @author  Rossen Stoyanchev @从4.0开始
 */
public class HandlerMethodReturnValueHandlerComposite implements AsyncHandlerMethodReturnValueHandler {

	/** Public for wrapping with fallback logger. */
	/**
	 * 公开用于使用备用记录器包装。 
	 * 
	 */
	public static final Log defaultLogger = LogFactory.getLog(HandlerMethodReturnValueHandlerComposite.class);


	private Log logger = defaultLogger;

	private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();


	/**
	 * Set an alternative logger to use than the one based on the class name.
	 * @param logger the logger to use
	 * @since 5.1
	 */
	/**
	 * 根据类名称设置一个替代的记录器以供使用。 
	 *  
	 * @param 记录器记录器使用@since 5.1
	 */
	public void setLogger(Log logger) {
		this.logger = logger;
	}

	/**
	 * Return the currently configured Logger.
	 * @since 5.1
	 */
	/**
	 * 返回当前配置的记录器。 
	 *  @5.1起
	 */
	public Log getLogger() {
		return logger;
	}

	/**
	 * Return a read-only list with the configured handlers.
	 */
	/**
	 * 返回带有已配置处理程序的只读列表。 
	 * 
	 */
	public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
		return Collections.unmodifiableList(this.returnValueHandlers);
	}

	/**
	 * Clear the list of configured handlers.
	 */
	/**
	 * 清除已配置处理程序的列表。 
	 * 
	 */
	public void clear() {
		this.returnValueHandlers.clear();
	}

	/**
	 * Add the given {@link HandlerMethodReturnValueHandler}.
	 */
	/**
	 * 添加给定的{@link  HandlerMethodReturnValueHandler}。 
	 * 
	 */
	public HandlerMethodReturnValueHandlerComposite addHandler(HandlerMethodReturnValueHandler returnValueHandler) {
		this.returnValueHandlers.add(returnValueHandler);
		return this;
	}

	/**
	 * Add the given {@link HandlerMethodReturnValueHandler HandlerMethodReturnValueHandlers}.
	 */
	/**
	 * 添加给定的{@link  HandlerMethodReturnValueHandler HandlerMethodReturnValueHandlers}。 
	 * 
	 */
	public HandlerMethodReturnValueHandlerComposite addHandlers(
			@Nullable List<? extends HandlerMethodReturnValueHandler> handlers) {

		if (handlers != null) {
			this.returnValueHandlers.addAll(handlers);
		}
		return this;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return getReturnValueHandler(returnType) != null;
	}

	@SuppressWarnings("ForLoopReplaceableByForEach")
	@Nullable
	private HandlerMethodReturnValueHandler getReturnValueHandler(MethodParameter returnType) {
		for (int i = 0; i < this.returnValueHandlers.size(); i++) {
			HandlerMethodReturnValueHandler handler = this.returnValueHandlers.get(i);
			if (handler.supportsReturnType(returnType)) {
				return handler;
			}
		}
		return null;
	}

	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message)
			throws Exception {

		HandlerMethodReturnValueHandler handler = getReturnValueHandler(returnType);
		if (handler == null) {
			throw new IllegalStateException("No handler for return value type: " + returnType.getParameterType());
		}
		if (logger.isTraceEnabled()) {
			logger.trace("Processing return value with " + handler);
		}
		handler.handleReturnValue(returnValue, returnType, message);
	}

	@Override
	public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
		HandlerMethodReturnValueHandler handler = getReturnValueHandler(returnType);
		return (handler instanceof AsyncHandlerMethodReturnValueHandler &&
				((AsyncHandlerMethodReturnValueHandler) handler).isAsyncReturnValue(returnValue, returnType));
	}

	@Override
	@Nullable
	public ListenableFuture<?> toListenableFuture(Object returnValue, MethodParameter returnType) {
		HandlerMethodReturnValueHandler handler = getReturnValueHandler(returnType);
		if (handler instanceof AsyncHandlerMethodReturnValueHandler) {
			return ((AsyncHandlerMethodReturnValueHandler) handler).toListenableFuture(returnValue, returnType);
		}
		return null;
	}

}
