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

package org.springframework.web.reactive.handler;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

/**
 * Common WebFlux exception handler that detects instances of
 * {@link org.springframework.web.server.ResponseStatusException}
 * (inherited from the base class) as well as exceptions annotated with
 * {@link ResponseStatus @ResponseStatus} by determining the HTTP status
 * for them and updating the status of the response accordingly.
 *
 * <p>If the response is already committed, the error remains unresolved
 * and is propagated.
 *
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 5.0.5
 */
/**
 * 常见的WebFlux异常处理程序，用于检测{@link  org.springframework.web.server.ResponseStatusException}（从基类继承）的实例，以及通过确定HTTP状态而用{@link  ResponseStatus @ResponseStatus}注释的异常为他们，并相应地更新响应的状态。 
 *  <p>如果响应已经提交，则错误仍未解决并得以传播。 
 *  @author  Juergen Hoeller @author 罗森·斯托扬切夫（Rossen Stoyanchev）@5.0.5起
 */
public class WebFluxResponseStatusExceptionHandler extends ResponseStatusExceptionHandler {

	@Override
	@Nullable
	protected HttpStatus determineStatus(Throwable ex) {
		HttpStatus status = super.determineStatus(ex);
		if (status == null) {
			ResponseStatus ann = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
			if (ann != null) {
				status = ann.code();
			}
		}
		return status;
	}

}
