/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.config.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * A {@link WebMvcConfigurer} that delegates to one or more others.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 一个委托给一个或多个其他人的{@link  WebMvcConfigurer}。 
 *  @author  Rossen Stoyanchev @从3.1开始
 */
class WebMvcConfigurerComposite implements WebMvcConfigurer {

	private final List<WebMvcConfigurer> delegates = new ArrayList<>();


	public void addWebMvcConfigurers(List<WebMvcConfigurer> configurers) {
		if (!CollectionUtils.isEmpty(configurers)) {
			this.delegates.addAll(configurers);
		}
	}


	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configurePathMatch(configurer);
		}
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureContentNegotiation(configurer);
		}
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureAsyncSupport(configurer);
		}
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureDefaultServletHandling(configurer);
		}
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addFormatters(registry);
		}
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addInterceptors(registry);
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addResourceHandlers(registry);
		}
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addCorsMappings(registry);
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addViewControllers(registry);
		}
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureViewResolvers(registry);
		}
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addArgumentResolvers(argumentResolvers);
		}
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addReturnValueHandlers(returnValueHandlers);
		}
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureMessageConverters(converters);
		}
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.extendMessageConverters(converters);
		}
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.configureHandlerExceptionResolvers(exceptionResolvers);
		}
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.extendHandlerExceptionResolvers(exceptionResolvers);
		}
	}

	@Override
	public Validator getValidator() {
		Validator selected = null;
		for (WebMvcConfigurer configurer : this.delegates) {
			Validator validator = configurer.getValidator();
			if (validator != null) {
				if (selected != null) {
					throw new IllegalStateException("No unique Validator found: {" +
							selected + ", " + validator + "}");
				}
				selected = validator;
			}
		}
		return selected;
	}

	@Override
	@Nullable
	public MessageCodesResolver getMessageCodesResolver() {
		MessageCodesResolver selected = null;
		for (WebMvcConfigurer configurer : this.delegates) {
			MessageCodesResolver messageCodesResolver = configurer.getMessageCodesResolver();
			if (messageCodesResolver != null) {
				if (selected != null) {
					throw new IllegalStateException("No unique MessageCodesResolver found: {" +
							selected + ", " + messageCodesResolver + "}");
				}
				selected = messageCodesResolver;
			}
		}
		return selected;
	}

}
