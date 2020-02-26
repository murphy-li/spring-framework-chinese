/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.instrument.classloading.weblogic;

import java.lang.instrument.ClassFileTransformer;

import org.springframework.core.OverridingClassLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * {@link LoadTimeWeaver} implementation for WebLogic's instrumentable
 * ClassLoader.
 *
 * <p><b>NOTE:</b> Requires BEA WebLogic version 10 or higher.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 2.5
 */
/**
 * WebLogic的可插入ClassLoader的{@link  LoadTimeWeaver}实现。 
 *  <p> <b>注意：</ b>需要BEA WebLogic版本10或更高版本。 
 *  @author  Costin Leau @author  Juergen Hoeller @从2.5开始
 */
public class WebLogicLoadTimeWeaver implements LoadTimeWeaver {

	private final WebLogicClassLoaderAdapter classLoader;


	/**
	 * Creates a new instance of the {@link WebLogicLoadTimeWeaver} class using
	 * the default {@link ClassLoader class loader}.
	 * @see org.springframework.util.ClassUtils#getDefaultClassLoader()
	 */
	/**
	 * 使用默认的{@link  ClassLoader class loader}创建{@link  WebLogicLoadTimeWeaver}类的新实例。 
	 *  
	 * @see  org.springframework.util.ClassUtils＃getDefaultClassLoader（）
	 */
	public WebLogicLoadTimeWeaver() {
		this(ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Creates a new instance of the {@link WebLogicLoadTimeWeaver} class using
	 * the supplied {@link ClassLoader}.
	 * @param classLoader the {@code ClassLoader} to delegate to for weaving
	 */
	/**
	 * 使用提供的{@link  ClassLoader}创建{@link  WebLogicLoadTimeWeaver}类的新实例。 
	 *  
	 * @param  classLoader {@code  ClassLoader}委托进行编织
	 */
	public WebLogicLoadTimeWeaver(@Nullable ClassLoader classLoader) {
		Assert.notNull(classLoader, "ClassLoader must not be null");
		this.classLoader = new WebLogicClassLoaderAdapter(classLoader);
	}


	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		this.classLoader.addTransformer(transformer);
	}

	@Override
	public ClassLoader getInstrumentableClassLoader() {
		return this.classLoader.getClassLoader();
	}

	@Override
	public ClassLoader getThrowawayClassLoader() {
		return new OverridingClassLoader(this.classLoader.getClassLoader(),
				this.classLoader.getThrowawayClassLoader());
	}

}
