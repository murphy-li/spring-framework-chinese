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

package org.springframework.context.annotation;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * Context information for use by {@link Condition Conditions}.
 *
 * @author Phillip Webb
 * @author Juergen Hoeller
 * @since 4.0
 */
/**
 * {@link 条件条件}使用的上下文信息。 
 *  @author  Phillip Webb @author 于尔根·霍勒（Juergen Hoeller）@从4.0开始
 */
public interface ConditionContext {

	/**
	 * Return the {@link BeanDefinitionRegistry} that will hold the bean definition
	 * should the condition match.
	 * @throws IllegalStateException if no registry is available (which is unusual:
	 * only the case with a plain {@link ClassPathScanningCandidateComponentProvider})
	 */
	/**
	 * 返回条件匹配的{@link  BeanDefinitionRegistry}，它将保存Bean定义。 
	 *  
	 * @throws  IllegalStateException如果没有可用的注册表（这是不寻常的：只有带有普通{@link  ClassPathScanningCandidateComponentProvider}的情况）
	 */
	BeanDefinitionRegistry getRegistry();

	/**
	 * Return the {@link ConfigurableListableBeanFactory} that will hold the bean
	 * definition should the condition match, or {@code null} if the bean factory is
	 * not available (or not downcastable to {@code ConfigurableListableBeanFactory}).
	 */
	/**
	 * 如果条件匹配，则返回将保存Bean定义的{@link  ConfigurableListableBeanFactory}，或者如果没有可用的bean工厂（或不可向下转换为{@code  ConfigurableListableBeanFactory}，则返回{@code  null}）。 
	 * 
	 */
	@Nullable
	ConfigurableListableBeanFactory getBeanFactory();

	/**
	 * Return the {@link Environment} for which the current application is running.
	 */
	/**
	 * 返回当前应用程序正在运行的{@link  Environment}。 
	 * 
	 */
	Environment getEnvironment();

	/**
	 * Return the {@link ResourceLoader} currently being used.
	 */
	/**
	 * 返回当前正在使用的{@link  ResourceLoader}。 
	 * 
	 */
	ResourceLoader getResourceLoader();

	/**
	 * Return the {@link ClassLoader} that should be used to load additional classes
	 * (only {@code null} if even the system ClassLoader isn't accessible).
	 * @see org.springframework.util.ClassUtils#forName(String, ClassLoader)
	 */
	/**
	 * 返回应该用于加载其他类的{@link  ClassLoader}（如果甚至无法访问系统ClassLoader，则仅返回{@code  null}）。 
	 *  
	 * @see  org.springframework.util.ClassUtils＃forName（String，ClassLoader）
	 */
	@Nullable
	ClassLoader getClassLoader();

}
