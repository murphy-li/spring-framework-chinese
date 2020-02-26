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

package org.springframework.test.context.support;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.test.context.SmartContextLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Utility methods for {@link SmartContextLoader SmartContextLoaders} that deal
 * with component classes (e.g., {@link Configuration @Configuration} classes).
 *
 * @author Sam Brannen
 * @since 3.2
 */
/**
 * {@link  SmartContextLoader SmartContextLoaders}的实用程序方法，用于处理组件类（例如{@link  Configuration @Configuration}类）。 
 *  @author  Sam Brannen @自3.2起
 */
public abstract class AnnotationConfigContextLoaderUtils {

	private static final Log logger = LogFactory.getLog(AnnotationConfigContextLoaderUtils.class);


	/**
	 * Detect the default configuration classes for the supplied test class.
	 * <p>The returned class array will contain all static nested classes of
	 * the supplied class that meet the requirements for {@code @Configuration}
	 * class implementations as specified in the documentation for
	 * {@link Configuration @Configuration}.
	 * <p>The implementation of this method adheres to the contract defined in the
	 * {@link org.springframework.test.context.SmartContextLoader SmartContextLoader}
	 * SPI. Specifically, this method uses introspection to detect default
	 * configuration classes that comply with the constraints required of
	 * {@code @Configuration} class implementations. If a potential candidate
	 * configuration class does not meet these requirements, this method will log a
	 * debug message, and the potential candidate class will be ignored.
	 * @param declaringClass the test class that declared {@code @ContextConfiguration}
	 * @return an array of default configuration classes, potentially empty but
	 * never {@code null}
	 */
	/**
	 * 检测提供的测试类的默认配置类。 
	 *  <p>返回的类数组将包含提供的类的所有静态嵌套类，这些类满足{@link  Configuration @Configuration}文档中对{@code  @Configuration}类实现的要求。 
	 *  <p>此方法的实现遵循{{@link> org.springframework.test.context.SmartContextLoader SmartContextLoader} SPI中定义的约定。 
	 * 具体来说，此方法使用自省功能来检测符合{@code  @Configuration}类实现所需的约束的默认配置类。 
	 * 如果潜在的候选配置类不满足这些要求，则此方法将记录一条调试消息，并且潜在的候选类将被忽略。 
	 *  
	 * @param  declaringClass声明了{@code  @ContextConfiguration}的测试类
	 * @return 一组默认配置类，可能为空，但从未为{@code  null}
	 */
	public static Class<?>[] detectDefaultConfigurationClasses(Class<?> declaringClass) {
		Assert.notNull(declaringClass, "Declaring class must not be null");

		List<Class<?>> configClasses = new ArrayList<>();

		for (Class<?> candidate : declaringClass.getDeclaredClasses()) {
			if (isDefaultConfigurationClassCandidate(candidate)) {
				configClasses.add(candidate);
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format(
						"Ignoring class [%s]; it must be static, non-private, non-final, and annotated " +
								"with @Configuration to be considered a default configuration class.",
						candidate.getName()));
				}
			}
		}

		if (configClasses.isEmpty()) {
			if (logger.isInfoEnabled()) {
				logger.info(String.format("Could not detect default configuration classes for test class [%s]: " +
						"%s does not declare any static, non-private, non-final, nested classes " +
						"annotated with @Configuration.", declaringClass.getName(), declaringClass.getSimpleName()));
			}
		}

		return ClassUtils.toClassArray(configClasses);
	}

	/**
	 * Determine if the supplied {@link Class} meets the criteria for being
	 * considered a <em>default configuration class</em> candidate.
	 * <p>Specifically, such candidates:
	 * <ul>
	 * <li>must not be {@code null}</li>
	 * <li>must not be {@code private}</li>
	 * <li>must not be {@code final}</li>
	 * <li>must be {@code static}</li>
	 * <li>must be annotated or meta-annotated with {@code @Configuration}</li>
	 * </ul>
	 * @param clazz the class to check
	 * @return {@code true} if the supplied class meets the candidate criteria
	 */
	/**
	 * 确定提供的{@link 类}是否满足被视为<em>默认配置类</ em>候选标准。 
	 *  <p>具体来说，这样的候选者：<ul> <li>不得为{@code  null} </ li> <li>不得为{@code  private} </ li> <li>不得是{@code 最终} </ li> <li>必须是{@code 静态} </ li> <li>必须使用{@code  @Configuration}进行注解或元注解</ li> > </ ul> 
	 * @param 要求班级检查
	 * @return  {@code  true}是否提供的班级符合候选标准
	 */
	private static boolean isDefaultConfigurationClassCandidate(@Nullable Class<?> clazz) {
		return (clazz != null && isStaticNonPrivateAndNonFinal(clazz) &&
				AnnotatedElementUtils.hasAnnotation(clazz, Configuration.class));
	}

	private static boolean isStaticNonPrivateAndNonFinal(Class<?> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		int modifiers = clazz.getModifiers();
		return (Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers) && !Modifier.isFinal(modifiers));
	}

}
