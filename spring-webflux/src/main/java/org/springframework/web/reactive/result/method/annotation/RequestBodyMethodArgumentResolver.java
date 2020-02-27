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

package org.springframework.web.reactive.result.method.annotation;

import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

/**
 * Resolves method arguments annotated with {@code @RequestBody} by reading the
 * body of the request through a compatible {@code HttpMessageReader}.
 *
 * <p>An {@code @RequestBody} method argument is also validated if it is
 * annotated with {@code @javax.validation.Valid} or
 * {@link org.springframework.validation.annotation.Validated}. Validation
 * failure results in an {@link ServerWebInputException}.
 *
 * @author Sebastien Deleuze
 * @author Stephane Maldini
 * @author Rossen Stoyanchev
 * @since 5.2
 */
/**
 * 通过通过兼容的{@code  HttpMessageReader}读取请求的正文，解析以{@code  @RequestBody}注释的方法参数。 
 *  <p>如果使用{@code  @javax.validation.Valid}或{@link  org.springframework.validation.annotation.Validated}进行注释，则{@code  @RequestBody}方法参数也将得到验证。 
 *  。 
 * 验证失败将导致{@link  ServerWebInputException}。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@author 斯蒂芬·马尔蒂尼（Stephane Maldini）@author  Rossen Stoyanchev @5.2起
 */
public class RequestBodyMethodArgumentResolver extends AbstractMessageReaderArgumentResolver {

	public RequestBodyMethodArgumentResolver(List<HttpMessageReader<?>> readers, ReactiveAdapterRegistry registry) {
		super(readers, registry);
	}


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestBody.class);
	}

	@Override
	public Mono<Object> resolveArgument(
			MethodParameter param, BindingContext bindingContext, ServerWebExchange exchange) {

		RequestBody ann = param.getParameterAnnotation(RequestBody.class);
		Assert.state(ann != null, "No RequestBody annotation");
		return readBody(param, ann.required(), bindingContext, exchange);
	}

}
