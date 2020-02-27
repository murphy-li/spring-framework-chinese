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

package org.springframework.web.bind.support;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * SPI for resolving custom arguments for a specific handler method parameter.
 * Typically implemented to detect special parameter types, resolving
 * well-known argument values for them.
 *
 * <p>A typical implementation could look like as follows:
 *
 * <pre class="code">
 * public class MySpecialArgumentResolver implements WebArgumentResolver {
 *
 *   public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {
 *     if (methodParameter.getParameterType().equals(MySpecialArg.class)) {
 *       return new MySpecialArg("myValue");
 *     }
 *     return UNRESOLVED;
 *   }
 * }</pre>
 *
 * @author Juergen Hoeller
 * @since 2.5.2
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#setCustomArgumentResolvers
 */
/**
 * SPI，用于解析特定处理程序方法参数的自定义参数。 
 * 通常用于检测特殊参数类型，从而解决它们的知名参数值。 
 *  <p>典型的实现可能如下所示：<pre class ="code">公共类MySpecialArgumentResolver实现WebArgumentResolver {public Object resolveArgument（MethodParameter methodParameter，NativeWebRequest webRequest）{ ））{返回新的MySpecialArg（"myValue"）; }返回UNRESOLVED； 
 *  }} </ pre> @author  Juergen Hoeller @since 2.5.2 
 * @see  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter＃setCustomArgumentResolvers
 */
@FunctionalInterface
public interface WebArgumentResolver {

	/**
	 * Marker to be returned when the resolver does not know how to
	 * handle the given method parameter.
	 */
	/**
	 * 解析器不知道如何处理给定方法参数时要返回的标记。 
	 * 
	 */
	Object UNRESOLVED = new Object();


	/**
	 * Resolve an argument for the given handler method parameter within the given web request.
	 * @param methodParameter the handler method parameter to resolve
	 * @param webRequest the current web request, allowing access to the native request as well
	 * @return the argument value, or {@code UNRESOLVED} if not resolvable
	 * @throws Exception in case of resolution failure
	 */
	/**
	 * 在给定的Web请求中为给定的处理程序方法参数解析一个参数。 
	 *  
	 * @param  methodParameter处理程序方法参数，用于解析
	 * @param  webRequest当前的Web请求，并允许访问本机请求，以及
	 * @return 参数值； 
	 * 如果无法解析，则返回{@code  UNRESOLVED} <@抛出>解析失败时的异常
	 */
	@Nullable
	Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception;

}
