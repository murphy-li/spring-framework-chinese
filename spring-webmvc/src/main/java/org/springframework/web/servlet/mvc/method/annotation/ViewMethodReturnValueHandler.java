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

package org.springframework.web.servlet.mvc.method.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

/**
 * Handles return values that are of type {@link View}.
 *
 * <p>A {@code null} return value is left as-is leaving it to the configured
 * {@link RequestToViewNameTranslator} to select a view name by convention.
 *
 * <p>A {@link View} return type has a set purpose. Therefore this handler
 * should be configured ahead of handlers that support any return value type
 * annotated with {@code @ModelAttribute} or {@code @ResponseBody} to ensure
 * they don't take over.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 处理类型为{@link  View}的返回值。 
 *  <p>一个{@code  null}返回值保留不变，留给配置的{@link  RequestToViewNameTranslator}，以按照约定选择视图名称。 
 *  <p> {<@link> View}返回类型具有设定的用途。 
 * 因此，应在支持任何以{@code  @ModelAttribute}或{@code  @ResponseBody}注释的任何返回值类型的处理程序之前配置此处理程序，以确保它们不会接管。 
 *  @author  Rossen Stoyanchev @从3.1开始
 */
public class ViewMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return View.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

		if (returnValue instanceof View) {
			View view = (View) returnValue;
			mavContainer.setView(view);
			if (view instanceof SmartView && ((SmartView) view).isRedirectView()) {
				mavContainer.setRedirectModelScenario(true);
			}
		}
		else if (returnValue != null) {
			// should not happen
			throw new UnsupportedOperationException("Unexpected return type: " +
					returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
		}
	}

}
