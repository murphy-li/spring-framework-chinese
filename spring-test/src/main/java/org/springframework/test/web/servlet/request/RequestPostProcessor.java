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

package org.springframework.test.web.servlet.request;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Extension point for applications or 3rd party libraries that wish to further
 * initialize a {@link MockHttpServletRequest} instance after it has been built
 * by {@link MockHttpServletRequestBuilder} or its subclass
 * {@link MockMultipartHttpServletRequestBuilder}.
 *
 * <p>Implementations of this interface can be provided to
 * {@link MockHttpServletRequestBuilder#with(RequestPostProcessor)} at the time
 * when a request is about to be constructed.
 *
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 3.2
 */
/**
 * 应用程序或第三方库的扩展点，它们希望由{@link  MockHttpServletRequestBuilder}或其子类{@link  MockMultipartHttpServletRequestBuilder}构建{{@link> MockHttpServletRequest}实例后进一步初始化。 
 *  <p>在即将构建请求时，可以向{@link  MockHttpServletRequestBuilder＃with（RequestPostProcessor）}提供此接口的实现。 
 *  @author  Rossen Stoyanchev @author  Rob Winch @从3.2开始
 */
@FunctionalInterface
public interface RequestPostProcessor {

	/**
	 * Post-process the given {@code MockHttpServletRequest} after its creation
	 * and initialization through a {@code MockHttpServletRequestBuilder}.
	 * @param request the request to initialize
	 * @return the request to use, either the one passed in or a wrapped one
	 */
	/**
	 * 在通过{@code  MockHttpServletRequestBuilder}创建和初始化给定的{@code  MockHttpServletRequest}之后，对其进行后处理。 
	 *  
	 * @param 请求初始化请求的请求
	 * @return 使用的请求，无论是传入的还是包装的
	 */
	MockHttpServletRequest postProcessRequest(MockHttpServletRequest request);

}
