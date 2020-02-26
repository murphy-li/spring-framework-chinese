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

package org.apache.commons.logging;

/**
 * A minimal incarnation of Apache Commons Logging's {@code LogFactory} API,
 * providing just the common {@link Log} lookup methods. This is inspired
 * by the JCL-over-SLF4J bridge and should be source as well as binary
 * compatible with all common use of the Commons Logging API (in particular:
 * with {@code LogFactory.getLog(Class/String)} field initializers).
 *
 * <p>This implementation does not support Commons Logging's original provider
 * detection. It rather only checks for the presence of the Log4j 2.x API
 * and the SLF4J 1.7 API in the Spring Framework classpath, falling back to
 * {@code java.util.logging} if none of the two is available. In that sense,
 * it works as a replacement for the Log4j 2 Commons Logging bridge as well as
 * the JCL-over-SLF4J bridge, both of which become irrelevant for Spring-based
 * setups as a consequence (with no need for manual excludes of the standard
 * Commons Logging API jar anymore either). Furthermore, for simple setups
 * without an external logging provider, Spring does not require any extra jar
 * on the classpath anymore since this embedded log factory automatically
 * delegates to {@code java.util.logging} in such a scenario.
 *
 * <p><b>Note that this Commons Logging variant is only meant to be used for
 * infrastructure logging purposes in the core framework and in extensions.</b>
 * It also serves as a common bridge for third-party libraries using the
 * Commons Logging API, e.g. Apache HttpClient, and HtmlUnit, bringing
 * them into the same consistent arrangement without any extra bridge jars.
 *
 * <p><b>For logging need in application code, prefer direct use of Log4j 2.x
 * or SLF4J or {@code java.util.logging}.</b> Simply put Log4j 2.x or Logback
 * (or another SLF4J provider) onto your classpath, without any extra bridges,
 * and let the framework auto-adapt to your choice.
 *
 * @author Juergen Hoeller (for the {@code spring-jcl} variant)
 * @since 5.0
 */
/**
 * Apache Commons Logging的{@code  LogFactory} API的最小化版本，仅提供了常见的{@link  Log}查找方法。 
 * 这是受JCL-over-SLF4J桥的启发而产生的，并且应该与Commons Logging API的所有常用用法（特别是：与{@code  LogFactory.getLog（Class / String）}字段初始化程序的源和二进制兼容）兼容。 
 *  ）。 
 *  <p>此实现不支持Commons Logging的原始提供程序检测。 
 * 而是只检查Spring Framework类路径中Log4j 2.x API和SLF4J 1.7 API是否存在，如果两者都不可用，则退回到{@code  java.util.logging}。 
 * 从这个意义上讲，它可以替代Log4j 2 Commons Logging桥接器以及JCL-over-SLF4J桥接器，因此两者对于基于Spring的设置都不相关（不需要手动排除标准） Commons Logging API jar也可以）。 
 * 此外，对于没有外部日志记录提供程序的简单设置，Spring在类路径上不再需要任何额外的jar，因为在这种情况下，此嵌入式日志工厂会自动委托给{@code  java.util.logging}。 
 *  <p> <b>请注意，此Commons Logging变体仅用于核心框架和扩展中的基础结构日志记录。 
 * </ b>它还用作使用Commons Logging的第三方库的通用桥梁。 
 *  API，例如Apache HttpClient和HtmlUnit，使它们进入相同的一致安排，而无需任何额外的桥接器。 
 *  <p> <b>对于应用程序代码中的日志记录需求，建议直接使用Log4j 2.x或SLF4J或{@code  java.util.logging}。 
 * </ b>只需将Log4j 2.x或Logback（或另一个SLF4J提供程序）添加到您的类路径中，而无需任何额外的桥接，并让框架自动适应您的选择。 
 *  @author  Juergen Hoeller（用于{@code  spring-jcl}变体）@从5.0开始
 */
public abstract class LogFactory {

	/**
	 * Convenience method to return a named logger.
	 * @param clazz containing Class from which a log name will be derived
	 */
	/**
	 * 返回命名记录器的便捷方法。 
	 *  
	 * @param 包含Class的clazz，将从其派生日志名称
	 */
	public static Log getLog(Class<?> clazz) {
		return getLog(clazz.getName());
	}

	/**
	 * Convenience method to return a named logger.
	 * @param name logical name of the <code>Log</code> instance to be returned
	 */
	/**
	 * 返回命名记录器的便捷方法。 
	 *  
	 * @param 名称要返回的<code> Log </ code>实例的逻辑名称
	 */
	public static Log getLog(String name) {
		return LogAdapter.createLog(name);
	}


	/**
	 * This method only exists for compatibility with unusual Commons Logging API
	 * usage like e.g. {@code LogFactory.getFactory().getInstance(Class/String)}.
	 * @see #getInstance(Class)
	 * @see #getInstance(String)
	 * @deprecated in favor of {@link #getLog(Class)}/{@link #getLog(String)}
	 */
	/**
	 * 此方法仅用于与不常见的Commons Logging API用法（例如， {@code  LogFactory.getFactory（）。 
	 * getInstance（Class / String）}。 
	 *  
	 * @see  #getInstance（Class）
	 * @see  #getInstance（String）@弃用了{{@link> #getLog（Class）} / {<@link> #getLog（String）}
	 */
	@Deprecated
	public static LogFactory getFactory() {
		return new LogFactory() {};
	}

	/**
	 * Convenience method to return a named logger.
	 * <p>This variant just dispatches straight to {@link #getLog(Class)}.
	 * @param clazz containing Class from which a log name will be derived
	 * @deprecated in favor of {@link #getLog(Class)}
	 */
	/**
	 * 返回命名记录器的便捷方法。 
	 *  <p>此变体仅直接发送到{@link  #getLog（Class）}。 
	 *  
	 * @param 包含Class的clazz，将从中派生日志名称，@推荐使用{@link  #getLog（Class）}
	 */
	@Deprecated
	public Log getInstance(Class<?> clazz) {
		return getLog(clazz);
	}

	/**
	 * Convenience method to return a named logger.
	 * <p>This variant just dispatches straight to {@link #getLog(String)}.
	 * @param name logical name of the <code>Log</code> instance to be returned
	 * @deprecated in favor of {@link #getLog(String)}
	 */
	/**
	 * 返回命名记录器的便捷方法。 
	 *  <p>此变体仅直接发送到{@link  #getLog（String）}。 
	 *  
	 * @param 要返回的<code> Log </ code>实例的名称逻辑名称，不建议使用{@link  #getLog（String）}
	 */
	@Deprecated
	public Log getInstance(String name) {
		return getLog(name);
	}

}
