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

package org.springframework.instrument.classloading;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.springframework.instrument.InstrumentationSavingAgent;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} relying on VM {@link Instrumentation}.
 *
 * <p>Start the JVM specifying the Java agent to be used &mdash; for example, as
 * follows where <code>spring-instrument-{version}.jar</code> is a JAR file
 * containing the {@link InstrumentationSavingAgent} class shipped with Spring
 * and where <code>{version}</code> is the release version of the Spring
 * Framework (e.g., {@code 5.1.5.RELEASE}).
 *
 * <p><code>-javaagent:path/to/spring-instrument-{version}.jar</code>
 *
 * <p>In Eclipse, for example, add something similar to the following to the
 * JVM arguments for the Eclipse "Run configuration":
 *
 * <p><code>-javaagent:${project_loc}/lib/spring-instrument-{version}.jar</code>
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 * @see InstrumentationSavingAgent
 */
/**
 * {@link  LoadTimeWeaver}依赖于VM {@link  Instrumentation}。 
 *  <p>启动JVM，指定要使用的Java代理，例如，如下所示，其中<code> spring-instrument- {version} .jar </ code>是一个包含{@link  InstrumentationSavingAgent}类的JAR文件Spring随附，其中<code> {version} </ code>是Spring Framework的发行版本（例如{@code  5.1.5.RELEASE}）。 
 *  <p> <code> -javaagent：path / to / spring-instrument- {version} .jar </ code> <p>例如，在Eclipse中，为Eclipse"Run"的JVM参数添加类似于以下内容的内容配置"：<p> <code> -javaagent：$ {project_loc} / lib / spring-instrument- {version} .jar </ code> @author  Rod Johnson @author  Juergen Hoeller @since 2.0起<
 * @see > InstrumentationSavingAgent
 */
public class InstrumentationLoadTimeWeaver implements LoadTimeWeaver {

	private static final boolean AGENT_CLASS_PRESENT = ClassUtils.isPresent(
			"org.springframework.instrument.InstrumentationSavingAgent",
			InstrumentationLoadTimeWeaver.class.getClassLoader());


	@Nullable
	private final ClassLoader classLoader;

	@Nullable
	private final Instrumentation instrumentation;

	private final List<ClassFileTransformer> transformers = new ArrayList<>(4);


	/**
	 * Create a new InstrumentationLoadTimeWeaver for the default ClassLoader.
	 */
	/**
	 * 为默认的ClassLoader创建一个新的InstrumentationLoadTimeWeaver。 
	 * 
	 */
	public InstrumentationLoadTimeWeaver() {
		this(ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Create a new InstrumentationLoadTimeWeaver for the given ClassLoader.
	 * @param classLoader the ClassLoader that registered transformers are supposed to apply to
	 */
	/**
	 * 为给定的ClassLoader创建一个新的InstrumentationLoadTimeWeaver。 
	 *  
	 * @param  classLoader注册的转换器应该应用于的ClassLoader
	 */
	public InstrumentationLoadTimeWeaver(@Nullable ClassLoader classLoader) {
		this.classLoader = classLoader;
		this.instrumentation = getInstrumentation();
	}


	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		Assert.notNull(transformer, "Transformer must not be null");
		FilteringClassFileTransformer actualTransformer =
				new FilteringClassFileTransformer(transformer, this.classLoader);
		synchronized (this.transformers) {
			Assert.state(this.instrumentation != null,
					"Must start with Java agent to use InstrumentationLoadTimeWeaver. See Spring documentation.");
			this.instrumentation.addTransformer(actualTransformer);
			this.transformers.add(actualTransformer);
		}
	}

	/**
	 * We have the ability to weave the current class loader when starting the
	 * JVM in this way, so the instrumentable class loader will always be the
	 * current loader.
	 */
	/**
	 * 当以这种方式启动JVM时，我们能够编织当前的类加载器，因此可检测的类加载器将始终是当前的加载器。 
	 * 
	 */
	@Override
	public ClassLoader getInstrumentableClassLoader() {
		Assert.state(this.classLoader != null, "No ClassLoader available");
		return this.classLoader;
	}

	/**
	 * This implementation always returns a {@link SimpleThrowawayClassLoader}.
	 */
	/**
	 * 此实现始终返回一个{@link  SimpleThrowawayClassLoader}。 
	 * 
	 */
	@Override
	public ClassLoader getThrowawayClassLoader() {
		return new SimpleThrowawayClassLoader(getInstrumentableClassLoader());
	}

	/**
	 * Remove all registered transformers, in inverse order of registration.
	 */
	/**
	 * 按照注册相反的顺序卸下所有已注册的变压器。 
	 * 
	 */
	public void removeTransformers() {
		synchronized (this.transformers) {
			if (this.instrumentation != null && !this.transformers.isEmpty()) {
				for (int i = this.transformers.size() - 1; i >= 0; i--) {
					this.instrumentation.removeTransformer(this.transformers.get(i));
				}
				this.transformers.clear();
			}
		}
	}


	/**
	 * Check whether an Instrumentation instance is available for the current VM.
	 * @see #getInstrumentation()
	 */
	/**
	 * 检查某个Instrumentation实例是否可用于当前VM。 
	 *  
	 * @see  #getInstrumentation（）
	 */
	public static boolean isInstrumentationAvailable() {
		return (getInstrumentation() != null);
	}

	/**
	 * Obtain the Instrumentation instance for the current VM, if available.
	 * @return the Instrumentation instance, or {@code null} if none found
	 * @see #isInstrumentationAvailable()
	 */
	/**
	 * 获取当前VM的Instrumentation实例（如果有）。 
	 *  
	 * @return  Instrumentation实例； 
	 * 如果未找到，则为{<@@code> null} 
	 * @see  #isInstrumentationAvailable（）
	 */
	@Nullable
	private static Instrumentation getInstrumentation() {
		if (AGENT_CLASS_PRESENT) {
			return InstrumentationAccessor.getInstrumentation();
		}
		else {
			return null;
		}
	}


	/**
	 * Inner class to avoid InstrumentationSavingAgent dependency.
	 */
	/**
	 * 内部类避免对InstrumentationSavingAgent的依赖。 
	 * 
	 */
	private static class InstrumentationAccessor {

		public static Instrumentation getInstrumentation() {
			return InstrumentationSavingAgent.getInstrumentation();
		}
	}


	/**
	 * Decorator that only applies the given target transformer to a specific ClassLoader.
	 */
	/**
	 * 仅将给定目标转换器应用于特定ClassLoader的修饰器。 
	 * 
	 */
	private static class FilteringClassFileTransformer implements ClassFileTransformer {

		private final ClassFileTransformer targetTransformer;

		@Nullable
		private final ClassLoader targetClassLoader;

		public FilteringClassFileTransformer(
				ClassFileTransformer targetTransformer, @Nullable ClassLoader targetClassLoader) {

			this.targetTransformer = targetTransformer;
			this.targetClassLoader = targetClassLoader;
		}

		@Override
		@Nullable
		public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
				ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

			if (this.targetClassLoader != loader) {
				return null;
			}
			return this.targetTransformer.transform(
					loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
		}

		@Override
		public String toString() {
			return "FilteringClassFileTransformer for: " + this.targetTransformer.toString();
		}
	}

}
