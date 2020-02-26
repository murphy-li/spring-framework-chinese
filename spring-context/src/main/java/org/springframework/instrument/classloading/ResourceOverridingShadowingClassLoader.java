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

package org.springframework.instrument.classloading;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Subclass of ShadowingClassLoader that overrides attempts to
 * locate certain files.
 *
 * @author Rod Johnson
 * @author Adrian Colyer
 * @since 2.0
 */
/**
 * ShadowingClassLoader的子类，它覆盖尝试查找某些文件的尝试。 
 *  @author 罗德·约翰逊@author  Adrian Colyer @since 2.0
 */
public class ResourceOverridingShadowingClassLoader extends ShadowingClassLoader {

	private static final Enumeration<URL> EMPTY_URL_ENUMERATION = new Enumeration<URL>() {
		@Override
		public boolean hasMoreElements() {
			return false;
		}
		@Override
		public URL nextElement() {
			throw new UnsupportedOperationException("Should not be called. I am empty.");
		}
	};


	/**
	 * Key is asked for value: value is actual value.
	 */
	/**
	 * 询问键值：值是实际值。 
	 * 
	 */
	private Map<String, String> overrides = new HashMap<>();


	/**
	 * Create a new ResourceOverridingShadowingClassLoader,
	 * decorating the given ClassLoader.
	 * @param enclosingClassLoader the ClassLoader to decorate
	 */
	/**
	 * 创建一个新的ResourceOverridingShadowingClassLoader，装饰给定的ClassLoader。 
	 *  
	 * @param  enclosingClassLoader要装饰的ClassLoader
	 */
	public ResourceOverridingShadowingClassLoader(ClassLoader enclosingClassLoader) {
		super(enclosingClassLoader);
	}


	/**
	 * Return the resource (if any) at the new path
	 * on an attempt to locate a resource at the old path.
	 * @param oldPath the path requested
	 * @param newPath the actual path to be looked up
	 */
	/**
	 * 尝试在旧路径上找到资源，将资源（如果有）返回新路径。 
	 *  
	 * @param  oldPath请求的路径
	 * @param  newPath要查找的实际路径
	 */
	public void override(String oldPath, String newPath) {
		this.overrides.put(oldPath, newPath);
	}

	/**
	 * Ensure that a resource with the given path is not found.
	 * @param oldPath the path of the resource to hide even if
	 * it exists in the parent ClassLoader
	 */
	/**
	 * 确保找不到具有给定路径的资源。 
	 *  
	 * @param  oldPath即使父ClassLoader中存在资源，也要隐藏的资源路径
	 */
	public void suppress(String oldPath) {
		this.overrides.put(oldPath, null);
	}

	/**
	 * Copy all overrides from the given ClassLoader.
	 * @param other the other ClassLoader to copy from
	 */
	/**
	 * 从给定的ClassLoader复制所有替代。 
	 *  
	 * @param 其他要从中复制的其他ClassLoader
	 */
	public void copyOverrides(ResourceOverridingShadowingClassLoader other) {
		Assert.notNull(other, "Other ClassLoader must not be null");
		this.overrides.putAll(other.overrides);
	}


	@Override
	public URL getResource(String requestedPath) {
		if (this.overrides.containsKey(requestedPath)) {
			String overriddenPath = this.overrides.get(requestedPath);
			return (overriddenPath != null ? super.getResource(overriddenPath) : null);
		}
		else {
			return super.getResource(requestedPath);
		}
	}

	@Override
	@Nullable
	public InputStream getResourceAsStream(String requestedPath) {
		if (this.overrides.containsKey(requestedPath)) {
			String overriddenPath = this.overrides.get(requestedPath);
			return (overriddenPath != null ? super.getResourceAsStream(overriddenPath) : null);
		}
		else {
			return super.getResourceAsStream(requestedPath);
		}
	}

	@Override
	public Enumeration<URL> getResources(String requestedPath) throws IOException {
		if (this.overrides.containsKey(requestedPath)) {
			String overriddenLocation = this.overrides.get(requestedPath);
			return (overriddenLocation != null ?
					super.getResources(overriddenLocation) : EMPTY_URL_ENUMERATION);
		}
		else {
			return super.getResources(requestedPath);
		}
	}

}
