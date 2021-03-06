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

package org.springframework.web.reactive.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Adding this annotation to an {@code @Configuration} class imports the Spring
 * WebFlux configuration from {@link WebFluxConfigurationSupport} that enables
 * use of annotated controllers and functional endpoints.
 *
 * <p>For example:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableWebFlux
 * &#064;ComponentScan
 * public class MyConfiguration {
 * }
 * </pre>
 *
 * <p>To customize the imported configuration, implement
 * {@link WebFluxConfigurer} and one or more of its methods:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableWebFlux
 * &#064;ComponentScan
 * public class MyConfiguration implements WebFluxConfigurer {
 *
 *     &#064;Autowired
 *     private ObjectMapper objectMapper;
 *
 *     &#064;Override
 *     public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
 *         configurer.defaultCodecs().jackson2JsonEncoder(
 *             new Jackson2JsonEncoder(objectMapper)
 *         );
 *         configurer.defaultCodecs().jackson2JsonDecoder(
 *             new Jackson2JsonDecoder(objectMapper)
 *         );
 *     }
 *
 *     // ...
 * }
 * </pre>
 *
 * <p>Only one {@code @Configuration} class should have the {@code @EnableWebFlux}
 * annotation in order to import the Spring WebFlux configuration. There can
 * however be multiple {@code @Configuration} classes that implement
 * {@code WebFluxConfigurer} that customize the provided configuration.
 *
 * <p>If {@code WebFluxConfigurer} does not expose some setting that needs to be
 * configured, consider switching to an advanced mode by removing the
 * {@code @EnableWebFlux} annotation and extending directly from
 * {@link WebFluxConfigurationSupport} or {@link DelegatingWebFluxConfiguration} --
 * the latter allows detecting and delegating to one or more
 * {@code WebFluxConfigurer} configuration classes.
 *
 * @author Brian Clozel
 * @author Rossen Stoyanchev
 * @since 5.0
 * @see WebFluxConfigurer
 * @see WebFluxConfigurationSupport
 * @see DelegatingWebFluxConfiguration
 */
/**
 * 将此注释添加到{@code  @Configuration}类中，即可从{@link  WebFluxConfigurationSupport}导入Spring WebFlux配置，从而可以使用带注释的控制器和功能端点。 
 *  <p>例如：<pre class ="code"> @Configuration @EnableWebFlux @ComponentScan公共类MyConfiguration {} </ pre> <p>要自定义导入的配置，请实现{@link  WebFluxConfigurer}和一个或多个其方法的方法：<pre class ="code"> @Configuration @EnableWebFlux @ComponentScan公共类MyConfiguration实现WebFluxConfigurer {@Autowired私有ObjectMapper objectMapper; @Override public void configureHttpMessageCodecs（ServerCodecConfigurer configurer）{configurer.defaultCodecs（）。 
 * jackson2JsonEncoder（new Jackson2JsonEncoder（objectMapper））; configurer.defaultCodecs（）。 
 * jackson2JsonDecoder（新的Jackson2JsonDecoder（objectMapper））; } // ...} </ pre> <p>只有一个{@code  @Configuration}类应具有{@code  @EnableWebFlux}注解，以导入Spring WebFlux配置。 
 * 但是，可以有多个实现{@code  WebFluxConfigurer}的{@code  @Configuration}类可以自定义提供的配置。 
 *  <p>如果{@code  WebFluxConfigurer}没有公开某些需要配置的设置，请考虑通过删除{@code  @EnableWebFlux}注释并直接从{@link  WebFluxConfigurationSupport扩展到}或{@link  DelegatingWebFluxConfiguration} －后者允许检测并委派一个或多个{@code  WebFluxConfigurer}配置类。 
 *  @author  Brian Clozel @author  Rossen Stoyanchev @从5.0起
 * @see  WebFluxConfigurer 
 * @see  WebFluxConfigurationSupport 
 * @see  DelegatingWebFluxConfiguration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebFluxConfiguration.class)
public @interface EnableWebFlux {
}
