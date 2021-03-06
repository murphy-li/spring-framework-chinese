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

package org.springframework.test.web.servlet.setup;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

/**
 * The main class to import in order to access all available {@link MockMvcBuilder MockMvcBuilders}.
 *
 * <h3>Eclipse Users</h3>
 * <p>Consider adding this class as a Java editor favorite. To navigate to
 * this setting, open the Preferences and type "favorites".
 *
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 3.2
 * @see #webAppContextSetup(WebApplicationContext)
 * @see #standaloneSetup(Object...)
 */
/**
 * 要访问所有可用的{@link  MockMvcBuilder MockMvcBuilders}，要导入的主要类。 
 *  <h3> Eclipse用户</ h3> <p>考虑将此类添加为Java编辑器的收藏夹。 
 * 要导航到此设置，请打开"首选项"，然后键入"收藏夹"。 
 *  @author  Rossen Stoyanchev @author  Sam Brannen @从3.2起
 * @see  #webAppContextSetup（WebApplicationContext）
 * @see  #standaloneSetup（Object ...）
 */
public final class MockMvcBuilders {

	private MockMvcBuilders() {
	}


	/**
	 * Build a {@link MockMvc} instance using the given, fully initialized
	 * (i.e., <em>refreshed</em>) {@link WebApplicationContext}.
	 * <p>The {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet}
	 * will use the context to discover Spring MVC infrastructure and application
	 * controllers in it. The context must have been configured with a
	 * {@link javax.servlet.ServletContext ServletContext}.
	 */
	/**
	 * 使用给定的，完全初始化的（即<em>刷新</ em>）{<@link> WebApplicationContext}构建{@link  MockMvc}实例。 
	 *  <p> {<@link> org.springframework.web.servlet.DispatcherServlet DispatcherServlet}将使用上下文在其中发现Spring MVC基础结构和应用程序控制器。 
	 * 必须使用{@link  javax.servlet.ServletContext ServletContext}配置上下文。 
	 * 
	 */
	public static DefaultMockMvcBuilder webAppContextSetup(WebApplicationContext context) {
		return new DefaultMockMvcBuilder(context);
	}

	/**
	 * Build a {@link MockMvc} instance by registering one or more
	 * {@code @Controller} instances and configuring Spring MVC infrastructure
	 * programmatically.
	 * <p>This allows full control over the instantiation and initialization of
	 * controllers and their dependencies, similar to plain unit tests while
	 * also making it possible to test one controller at a time.
	 * <p>When this builder is used, the minimum infrastructure required by the
	 * {@link org.springframework.web.servlet.DispatcherServlet DispatcherServlet}
	 * to serve requests with annotated controllers is created automatically
	 * and can be customized, resulting in configuration that is equivalent to
	 * what MVC Java configuration provides except using builder-style methods.
	 * <p>If the Spring MVC configuration of an application is relatively
	 * straight-forward &mdash; for example, when using the MVC namespace in
	 * XML or MVC Java config &mdash; then using this builder might be a good
	 * option for testing a majority of controllers. In such cases, a much
	 * smaller number of tests can be used to focus on testing and verifying
	 * the actual Spring MVC configuration.
	 * @param controllers one or more {@code @Controller} instances to test
	 * (specified {@code Class} will be turned into instance)
	 */
	/**
	 * 通过注册一个或多个{@code  @Controller}实例并以编程方式配置Spring MVC基础结构来构建{@link  MockMvc}实例。 
	 *  <p>这允许完全控制控制器及其实例的初始化和初始化，类似于普通单元测试，同时也可以一次测试一个控制器。 
	 *  <p>使用此构建器时，{<@link> org.springframework.web.servlet.DispatcherServlet DispatcherServlet}用来为带注释的控制器提供请求的最低基础结构会自动创建，并且可以自定义，从而导致配置等同于MVC Java配置提供的功能，但使用生成器样式的方法除外。 
	 *  <p>如果应用程序的Spring MVC配置相对简单明了（例如，在XML或MVC Java配置中使用MVC名称空间），则使用此构建器可能是测试大多数控制器的不错选择。 
	 * 在这种情况下，可以使用数量少得多的测试来专注于测试和验证实际的Spring MVC配置。 
	 *  
	 * @param 控制器控制一个或多个{@code  @Controller}实例进行测试（指定的{@code  Class}将变为实例）
	 */
	public static StandaloneMockMvcBuilder standaloneSetup(Object... controllers) {
		return new StandaloneMockMvcBuilder(controllers);
	}

}
