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

package org.springframework.core;

/**
 * Default implementation of the {@link ParameterNameDiscoverer} strategy interface,
 * using the Java 8 standard reflection mechanism (if available), and falling back
 * to the ASM-based {@link LocalVariableTableParameterNameDiscoverer} for checking
 * debug information in the class file.
 *
 * <p>If a Kotlin reflection implementation is present,
 * {@link KotlinReflectionParameterNameDiscoverer} is added first in the list and used
 * for Kotlin classes and interfaces. When compiling or running as a Graal native image,
 * no {@link ParameterNameDiscoverer} is used.
 *
 * <p>Further discoverers may be added through {@link #addDiscoverer(ParameterNameDiscoverer)}.
 *
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @since 4.0
 * @see StandardReflectionParameterNameDiscoverer
 * @see LocalVariableTableParameterNameDiscoverer
 * @see KotlinReflectionParameterNameDiscoverer
 */
/**
 * {@link  ParameterNameDiscoverer}策略接口的默认实现，使用Java 8标准反射机制（如果可用），并回退到基于ASM的{@link  LocalVariableTableParameterNameDiscoverer}，以检查类文件中的调试信息。 
 *  <p>如果存在Kotlin反射实现，则将{@link  KotlinReflectionParameterNameDiscoverer}首先添加到列表中，并将其用于Kotlin类和接口。 
 * 当编译或作为Graal本机映像运行时，不使用{@link  ParameterNameDiscoverer}。 
 *  <p>更多发现者可以通过{@link  #addDiscoverer（ParameterNameDiscoverer）}添加。 
 *  @author  Juergen Hoeller @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@自4.0起
 * @see  StandardReflectionParameterNameDiscoverer 
 * @see  LocalVariableTableParameterNameNameDiscoverer 
 * @see  KotlinReflectionParameterNameDiscoverer
 */
public class DefaultParameterNameDiscoverer extends PrioritizedParameterNameDiscoverer {

	public DefaultParameterNameDiscoverer() {
		if (!GraalDetector.inImageCode()) {
			if (KotlinDetector.isKotlinReflectPresent()) {
				addDiscoverer(new KotlinReflectionParameterNameDiscoverer());
			}
			addDiscoverer(new StandardReflectionParameterNameDiscoverer());
			addDiscoverer(new LocalVariableTableParameterNameDiscoverer());
		}
	}

}
