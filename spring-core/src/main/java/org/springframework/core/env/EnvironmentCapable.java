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

package org.springframework.core.env;

/**
 * Interface indicating a component that contains and exposes an {@link Environment} reference.
 *
 * <p>All Spring application contexts are EnvironmentCapable, and the interface is used primarily
 * for performing {@code instanceof} checks in framework methods that accept BeanFactory
 * instances that may or may not actually be ApplicationContext instances in order to interact
 * with the environment if indeed it is available.
 *
 * <p>As mentioned, {@link org.springframework.context.ApplicationContext ApplicationContext}
 * extends EnvironmentCapable, and thus exposes a {@link #getEnvironment()} method; however,
 * {@link org.springframework.context.ConfigurableApplicationContext ConfigurableApplicationContext}
 * redefines {@link org.springframework.context.ConfigurableApplicationContext#getEnvironment
 * getEnvironment()} and narrows the signature to return a {@link ConfigurableEnvironment}.
 * The effect is that an Environment object is 'read-only' until it is being accessed from
 * a ConfigurableApplicationContext, at which point it too may be configured.
 *
 * @author Chris Beams
 * @since 3.1
 * @see Environment
 * @see ConfigurableEnvironment
 * @see org.springframework.context.ConfigurableApplicationContext#getEnvironment()
 */
/**
 * 指示包含并公开{@link  Environment}引用的组件的接口。 
 *  <p>所有Spring应用程序上下文都具有EnvironmentCapable功能，并且该接口主要用于在接受BeanFactory实例的框架方法中执行{@code  instanceof}检查，以便与环境进行交互，以便与环境进行交互。 
 * 确实可用。 
 *  <p>如前所述，{<@link> org.springframework.context.ApplicationContext ApplicationContext}扩展了EnvironmentCapable，因此公开了一个{@link  #getEnvironment（）}方法； 
 * 但是，{<@link> org.springframework.context.ConfigurableApplicationContext ConfigurableApplicationContext}重新定义了{@link  org.springframework.context.ConfigurableApplicationContext＃getEnvironment getEnvironment（）}并缩小了签名范围，以返回{@link  ConfigurableEnvironment}。 
 * 结果是环境对象是"只读的"，直到从ConfigurableApplicationContext访问它为止，此时也可以对其进行配置。 
 *  @author  Chris Beams @since 3.1起
 * @see 环境
 * @see  ConfigurableEnvironment 
 * @see  org.springframework.context.ConfigurableApplicationContext＃getEnvironment（）
 */
public interface EnvironmentCapable {

	/**
	 * Return the {@link Environment} associated with this component.
	 */
	/**
	 * 返回与此组件关联的{@link  Environment}。 
	 * 
	 */
	Environment getEnvironment();

}
