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

package org.springframework.instrument.classloading.weblogic;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.springframework.lang.Nullable;

/**
 * Adapter that implements WebLogic ClassPreProcessor interface, delegating to a
 * standard JDK {@link ClassFileTransformer} underneath.
 *
 * <p>To avoid compile time checks again the vendor API, a dynamic proxy is
 * being used.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 2.5
 */
/**
 * 实现WebLogic ClassPreProcessor接口的适配器，委派给下面的标准JDK {@link  ClassFileTransformer}。 
 *  <p>为避免再次进行编译时检查供应商API，正在使用动态代理。 
 *  @author  Costin Leau @author  Juergen Hoeller @从2.5开始
 */
class WebLogicClassPreProcessorAdapter implements InvocationHandler {

	private final ClassFileTransformer transformer;

	private final ClassLoader loader;


	/**
	 * Construct a new {@link WebLogicClassPreProcessorAdapter}.
	 */
	/**
	 * 构造一个新的{@link  WebLogicClassPreProcessorAdapter}。 
	 * 
	 */
	public WebLogicClassPreProcessorAdapter(ClassFileTransformer transformer, ClassLoader loader) {
		this.transformer = transformer;
		this.loader = loader;
	}


	@Override
	@Nullable
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String name = method.getName();
		if ("equals".equals(name)) {
			return (proxy == args[0]);
		}
		else if ("hashCode".equals(name)) {
			return hashCode();
		}
		else if ("toString".equals(name)) {
			return toString();
		}
		else if ("initialize".equals(name)) {
			initialize((Hashtable<?, ?>) args[0]);
			return null;
		}
		else if ("preProcess".equals(name)) {
			return preProcess((String) args[0], (byte[]) args[1]);
		}
		else {
			throw new IllegalArgumentException("Unknown method: " + method);
		}
	}

	public void initialize(Hashtable<?, ?> params) {
	}

	public byte[] preProcess(String className, byte[] classBytes) {
		try {
			byte[] result = this.transformer.transform(this.loader, className, null, null, classBytes);
			return (result != null ? result : classBytes);
		}
		catch (IllegalClassFormatException ex) {
			throw new IllegalStateException("Cannot transform due to illegal class format", ex);
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + " for transformer: " + this.transformer;
	}

}
