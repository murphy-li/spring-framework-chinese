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

package org.springframework.web.reactive.result.method.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.SyncHandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

/**
 * Resolver for a {@link SessionStatus} argument obtaining it from the
 * {@link BindingContext}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * {@link  SessionStatus}参数的解析器从{@link  BindingContext}获取。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
public class SessionStatusMethodArgumentResolver implements SyncHandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return SessionStatus.class == parameter.getParameterType();
	}

	@Nullable
	@Override
	public Object resolveArgumentValue(
			MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {

		Assert.isInstanceOf(InitBinderBindingContext.class, bindingContext);
		return ((InitBinderBindingContext) bindingContext).getSessionStatus();
	}

}
