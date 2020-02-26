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

package org.springframework.instrument.classloading.glassfish;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.core.OverridingClassLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} implementation for GlassFish's
 * {@code org.glassfish.api.deployment.InstrumentableClassLoader InstrumentableClassLoader}.
 *
 * <p>As of Spring Framework 5.0, this weaver supports GlassFish 4+.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 2.0.1
 */
/**
 * GlassFish的{@code  org.glassfish.api.deployment.InstrumentableClassLoader InstrumentableClassLoader}的{@link  LoadTimeWeaver}实现。 
 *  <p>从Spring Framework 5.0开始，该编织器支持GlassFish 4+。 
 *  @author  Costin Leau @author 于尔根·霍勒（Juergen Hoeller）@2.0.1起
 */
public class GlassFishLoadTimeWeaver implements LoadTimeWeaver {

	private static final String INSTRUMENTABLE_LOADER_CLASS_NAME =
			"org.glassfish.api.deployment.InstrumentableClassLoader";


	private final ClassLoader classLoader;

	private final Method addTransformerMethod;

	private final Method copyMethod;


	/**
	 * Create a new instance of the {@link GlassFishLoadTimeWeaver} class using
	 * the default {@link ClassLoader class loader}.
	 * @see org.springframework.util.ClassUtils#getDefaultClassLoader()
	 */
	/**
	 * 使用默认的{@link  ClassLoader class loader}创建{@link  GlassFishLoadTimeWeaver}类的新实例。 
	 *  
	 * @see  org.springframework.util.ClassUtils＃getDefaultClassLoader（）
	 */
	public GlassFishLoadTimeWeaver() {
		this(ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Create a new instance of the {@link GlassFishLoadTimeWeaver} class using
	 * the supplied {@link ClassLoader}.
	 * @param classLoader the {@code ClassLoader} to delegate to for weaving
	 */
	/**
	 * 使用提供的{@link  ClassLoader}创建{@link  GlassFishLoadTimeWeaver}类的新实例。 
	 *  
	 * @param  classLoader {@code  ClassLoader}委托进行编织
	 */
	public GlassFishLoadTimeWeaver(@Nullable ClassLoader classLoader) {
		Assert.notNull(classLoader, "ClassLoader must not be null");

		Class<?> instrumentableLoaderClass;
		try {
			instrumentableLoaderClass = classLoader.loadClass(INSTRUMENTABLE_LOADER_CLASS_NAME);
			this.addTransformerMethod = instrumentableLoaderClass.getMethod("addTransformer", ClassFileTransformer.class);
			this.copyMethod = instrumentableLoaderClass.getMethod("copy");
		}
		catch (Throwable ex) {
			throw new IllegalStateException(
					"Could not initialize GlassFishLoadTimeWeaver because GlassFish API classes are not available", ex);
		}

		ClassLoader clazzLoader = null;
		// Detect transformation-aware ClassLoader by traversing the hierarchy
		// (as in GlassFish, Spring can be loaded by the WebappClassLoader).
		for (ClassLoader cl = classLoader; cl != null && clazzLoader == null; cl = cl.getParent()) {
			if (instrumentableLoaderClass.isInstance(cl)) {
				clazzLoader = cl;
			}
		}

		if (clazzLoader == null) {
			throw new IllegalArgumentException(classLoader + " and its parents are not suitable ClassLoaders: A [" +
					instrumentableLoaderClass.getName() + "] implementation is required.");
		}

		this.classLoader = clazzLoader;
	}


	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		try {
			this.addTransformerMethod.invoke(this.classLoader, transformer);
		}
		catch (InvocationTargetException ex) {
			throw new IllegalStateException("GlassFish addTransformer method threw exception", ex.getCause());
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Could not invoke GlassFish addTransformer method", ex);
		}
	}

	@Override
	public ClassLoader getInstrumentableClassLoader() {
		return this.classLoader;
	}

	@Override
	public ClassLoader getThrowawayClassLoader() {
		try {
			return new OverridingClassLoader(this.classLoader, (ClassLoader) this.copyMethod.invoke(this.classLoader));
		}
		catch (InvocationTargetException ex) {
			throw new IllegalStateException("GlassFish copy method threw exception", ex.getCause());
		}
		catch (Throwable ex) {
			throw new IllegalStateException("Could not invoke GlassFish copy method", ex);
		}
	}

}
