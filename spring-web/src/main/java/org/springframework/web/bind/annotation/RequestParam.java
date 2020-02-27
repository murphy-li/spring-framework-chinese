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

package org.springframework.web.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.springframework.core.annotation.AliasFor;

/**
 * Annotation which indicates that a method parameter should be bound to a web
 * request parameter.
 *
 * <p>Supported for annotated handler methods in Spring MVC and Spring WebFlux
 * as follows:
 * <ul>
 * <li>In Spring MVC, "request parameters" map to query parameters, form data,
 * and parts in multipart requests. This is because the Servlet API combines
 * query parameters and form data into a single map called "parameters", and
 * that includes automatic parsing of the request body.
 * <li>In Spring WebFlux, "request parameters" map to query parameters only.
 * To work with all 3, query, form data, and multipart data, you can use data
 * binding to a command object annotated with {@link ModelAttribute}.
 * </ul>
 *
 * <p>If the method parameter type is {@link Map} and a request parameter name
 * is specified, then the request parameter value is converted to a {@link Map}
 * assuming an appropriate conversion strategy is available.
 *
 * <p>If the method parameter is {@link java.util.Map Map&lt;String, String&gt;} or
 * {@link org.springframework.util.MultiValueMap MultiValueMap&lt;String, String&gt;}
 * and a parameter name is not specified, then the map parameter is populated
 * with all request parameter names and values.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 2.5
 * @see RequestMapping
 * @see RequestHeader
 * @see CookieValue
 */
/**
 * 指示方法参数应绑定到Web请求参数的注释。 
 *  <p>在Spring MVC和Spring WebFlux中支持带注释的处理程序方法，如下所示：<ul> <li>在Spring MVC中，"请求参数"映射到查询参数，表单数据和多部分请求中的部分。 
 * 这是因为Servlet API将查询参数和表单数据组合到一个称为"参数"的映射中，并且包括对请求正文的自动解析。 
 *  <li>在Spring WebFlux中，"请求参数"仅映射到查询参数。 
 * 要使用所有3种查询，表单数据和多部分数据，可以使用数据绑定到以{@link  ModelAttribute}注释的命令对象。 
 *  </ ul> <p>如果方法参数类型为{@link  Map}并指定了请求参数名称，则假定适当的转换策略是，请求参数值将转换为{@link  Map}。 
 * 可用。 
 *  <p>如果方法参数为{@link  java.util.Map Map <String，String>}或{@link  org.springframework.util.MultiValueMap MultiValueMap <String，String>}并且参数名称为如果未指定，则将使用所有请求参数名称和值填充map参数。 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @author  Sam Brannen @since 2.5 
 * @see  RequestMapping 
 * @see  RequestHeader 
 * @see  CookieValue
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

	/**
	 * Alias for {@link #name}.
	 */
	/**
	 * {@link  #name}的别名。 
	 * 
	 */
	@AliasFor("name")
	String value() default "";

	/**
	 * The name of the request parameter to bind to.
	 * @since 4.2
	 */
	/**
	 * 要绑定到的请求参数的名称。 
	 *  @4.2起
	 */
	@AliasFor("value")
	String name() default "";

	/**
	 * Whether the parameter is required.
	 * <p>Defaults to {@code true}, leading to an exception being thrown
	 * if the parameter is missing in the request. Switch this to
	 * {@code false} if you prefer a {@code null} value if the parameter is
	 * not present in the request.
	 * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
	 * sets this flag to {@code false}.
	 */
	/**
	 * 是否需要该参数。 
	 *  <p>默认为{@code  true}，如果请求中缺少参数，则会引发异常。 
	 * 如果在请求中不存在参数，则更喜欢使用{@code  null}值，将其切换为{@code  false}。 
	 *  <p>或者，提供一个{@link  #defaultValue}，它将此标志隐式设置为{@code  false}。 
	 * 
	 */
	boolean required() default true;

	/**
	 * The default value to use as a fallback when the request parameter is
	 * not provided or has an empty value.
	 * <p>Supplying a default value implicitly sets {@link #required} to
	 * {@code false}.
	 */
	/**
	 * 当没有提供request参数或值为空时，用作回退的默认值。 
	 *  <p>提供默认值会将{@link  #required}隐式设置为{@code  false}。 
	 * 
	 */
	String defaultValue() default ValueConstants.DEFAULT_NONE;

}
