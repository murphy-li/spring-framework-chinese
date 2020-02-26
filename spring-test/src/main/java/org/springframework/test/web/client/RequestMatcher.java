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

package org.springframework.test.web.client;

import java.io.IOException;

import org.springframework.http.client.ClientHttpRequest;

/**
 * A contract for matching requests to expectations.
 *
 * <p>See {@link org.springframework.test.web.client.match.MockRestRequestMatchers
 * MockRestRequestMatchers} for static factory methods.
 *
 * @author Craig Walls
 * @since 3.2
 */
/**
 * 使请求与期望匹配的合同。 
 *  <p>有关静态工厂方法，请参见{@link  org.springframework.test.web.client.match.MockRestRequestMatchers MockRestRequestMatchers}。 
 *  @author 克雷格·沃尔斯@3.2起
 */
@FunctionalInterface
public interface RequestMatcher {

	/**
	 * Match the given request against specific expectations.
	 * @param request the request to make assertions on
	 * @throws IOException in case of I/O errors
	 * @throws AssertionError if expectations are not met
	 */
	/**
	 * 将给定的请求与特定期望相匹配。 
	 *  
	 * @param 请求在I / O错误的情况下对
	 * @throws  IOException进行断言的请求
	 * @throws  AssertionError如果未达到期望
	 */
	void match(ClientHttpRequest request) throws IOException, AssertionError;

}
