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

package org.springframework.test.web.servlet.result;

import java.lang.reflect.Method;

import org.hamcrest.Matcher;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodInvocationInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

/**
 * Factory for assertions on the selected handler or handler method.
 *
 * <p>An instance of this class is typically accessed via
 * {@link MockMvcResultMatchers#handler}.
 *
 * <p><strong>Note:</strong> Expectations that assert the controller method
 * used to process the request work only for requests processed with
 * {@link RequestMappingHandlerMapping} and {@link RequestMappingHandlerAdapter}
 * which is used by default with the Spring MVC Java config and XML namespace.
 *
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 3.2
 */
/**
 * 工厂，用于对选定的处理程序或处理程序方法进行断言。 
 *  <p>通常通过{@link  MockMvcResultMatchers＃handler}访问此类的实例。 
 *  <p> <strong>注意：</ strong>要求断言用于处理请求的控制器方法的期望仅对使用{@link  RequestMappingHandlerMapping}和{@link  RequestMappingHandlerAdapter}（默认情况下与Spring MVC Java配置和XML名称空间。 
 *  @author  Rossen Stoyanchev @author 山姆·布兰嫩@3.2
 */
public class HandlerResultMatchers {

	/**
	 * Protected constructor.
	 * Use {@link MockMvcResultMatchers#handler()}.
	 */
	/**
	 * 受保护的构造函数。 
	 * 使用{@link  MockMvcResultMatchers＃handler（）}。 
	 * 
	 */
	protected HandlerResultMatchers() {
	}


	/**
	 * Assert the type of the handler that processed the request.
	 */
	/**
	 * 声明处理请求的处理程序的类型。 
	 * 
	 */
	public ResultMatcher handlerType(Class<?> type) {
		return result -> {
			Object handler = result.getHandler();
			assertNotNull("No handler", handler);
			if (handler != null) {
				Class<?> actual = handler.getClass();
				if (HandlerMethod.class.isInstance(handler)) {
					actual = ((HandlerMethod) handler).getBeanType();
				}
				assertEquals("Handler type", type, ClassUtils.getUserClass(actual));
			}
		};
	}

	/**
	 * Assert the controller method used to process the request.
	 * <p>The expected method is specified through a "mock" controller method
	 * invocation similar to {@link MvcUriComponentsBuilder#fromMethodCall(Object)}.
	 * <p>For example, given this controller:
	 * <pre class="code">
	 * &#064;RestController
	 * public class SimpleController {
	 *
	 *     &#064;RequestMapping("/")
	 *     public ResponseEntity&lt;Void&gt; handle() {
	 *         return ResponseEntity.ok().build();
	 *     }
	 * }
	 * </pre>
	 * <p>A test that has statically imported {@link MvcUriComponentsBuilder#on}
	 * can be performed as follows:
	 * <pre class="code">
	 * mockMvc.perform(get("/"))
	 *     .andExpect(handler().methodCall(on(SimpleController.class).handle()));
	 * </pre>
	 * @param obj either the value returned from a "mock" controller invocation
	 * or the "mock" controller itself after an invocation
	 */
	/**
	 * 声明用于处理请求的控制器方法。 
	 *  <p>通过类似于{@link  MvcUriComponentsBuilder＃fromMethodCall（Object）}的"模拟"控制器方法调用来指定期望的方法。 
	 *  <p>例如，给定该控制器：<pre class ="code"> @RestController公共类SimpleController {@RequestMapping（"/"）public ResponseEntity <Void> handle（）{return ResponseEntity.ok（）。 
	 * build（） ; }} </ pre> <p>已静态导入{@link  MvcUriComponentsBuilder＃on}的测试可以按以下方式执行：<pre class ="code"> mockMvc.perform（get（"/"））。 
	 *  andExpect（handler（）。 
	 * methodCall（on（SimpleController.class）.handle（）））; </ pre> 
	 * @param  obj要么是从"模拟"控制器调用返回的值，要么是调用后从"模拟"控制器本身返回的值
	 */
	public ResultMatcher methodCall(Object obj) {
		return result -> {
			if (!(obj instanceof MethodInvocationInfo)) {
				fail(String.format("The supplied object [%s] is not an instance of %s. " +
						"Ensure that you invoke the handler method via MvcUriComponentsBuilder.on().",
						obj, MethodInvocationInfo.class.getName()));
			}
			MethodInvocationInfo invocationInfo = (MethodInvocationInfo) obj;
			Method expected = invocationInfo.getControllerMethod();
			Method actual = getHandlerMethod(result).getMethod();
			assertEquals("Handler method", expected, actual);
		};
	}

	/**
	 * Assert the name of the controller method used to process the request
	 * using the given Hamcrest {@link Matcher}.
	 */
	/**
	 * 使用给定的Hamcrest {@link  Matcher}声明用于处理请求的控制器方法的名称。 
	 * 
	 */
	public ResultMatcher methodName(Matcher<? super String> matcher) {
		return result -> {
			HandlerMethod handlerMethod = getHandlerMethod(result);
			assertThat("Handler method", handlerMethod.getMethod().getName(), matcher);
		};
	}

	/**
	 * Assert the name of the controller method used to process the request.
	 */
	/**
	 * 声明用于处理请求的控制器方法的名称。 
	 * 
	 */
	public ResultMatcher methodName(String name) {
		return result -> {
			HandlerMethod handlerMethod = getHandlerMethod(result);
			assertEquals("Handler method", name, handlerMethod.getMethod().getName());
		};
	}

	/**
	 * Assert the controller method used to process the request.
	 */
	/**
	 * 声明用于处理请求的控制器方法。 
	 * 
	 */
	public ResultMatcher method(Method method) {
		return result -> {
			HandlerMethod handlerMethod = getHandlerMethod(result);
			assertEquals("Handler method", method, handlerMethod.getMethod());
		};
	}


	private static HandlerMethod getHandlerMethod(MvcResult result) {
		Object handler = result.getHandler();
		assertTrue("Not a HandlerMethod: " + handler, handler instanceof HandlerMethod);
		return (HandlerMethod) handler;
	}

}
