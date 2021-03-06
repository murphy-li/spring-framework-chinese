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

package org.springframework.asm;

/**
 * Utility class exposing constants related to Spring's internal repackaging
 * of the ASM bytecode library: currently based on ASM 7.x plus minor patches.
 *
 * <p>See <a href="package-summary.html">package-level javadocs</a> for more
 * information on {@code org.springframework.asm}.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.2
 */
/**
 * 实用程序类公开与Spring内部重新包装ASM字节码库有关的常量：当前基于ASM 7.x和较小的补丁。 
 *  <p>有关<< @code> org.springframework.asm}的更多信息，请参见<a href="package-summary.html">包级Javadocs </a>。 
 *  @author 克里斯·比姆斯（Chris Beams）@author  Juergen Hoeller @3.2起
 */
public final class SpringAsmInfo {

	/**
	 * The ASM compatibility version for Spring's ASM visitor implementations:
	 * currently {@link Opcodes#ASM7}, as of Spring Framework 5.1.
	 */
	/**
	 * 用于Spring的ASM访问者实现的ASM兼容版本：当前为{@link  Opcodes＃ASM7}，从Spring Framework 5.1开始。 
	 * 
	 */
	public static final int ASM_VERSION = Opcodes.ASM7;

}
