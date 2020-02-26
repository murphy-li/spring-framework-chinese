/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.beans.factory.support;

import java.lang.reflect.Method;

/**
 * Interface to be implemented by classes that can reimplement any method
 * on an IoC-managed object: the <b>Method Injection</b> form of
 * Dependency Injection.
 *
 * <p>Such methods may be (but need not be) abstract, in which case the
 * container will create a concrete subclass to instantiate.
 *
 * @author Rod Johnson
 * @since 1.1
 */
/**
 * 由可以在IoC管理的对象上重新实现任何方法的类实现的接口：依赖注入的<b> Method Injection </ b>形式。 
 *  <p>这类方法可能是（但不一定是）抽象的，在这种情况下，容器将创建一个具体的子类进行实例化。 
 *  @author  Rod Johnson @始于1.1
 */
public interface MethodReplacer {

	/**
	 * Reimplement the given method.
	 * @param obj the instance we're reimplementing the method for
	 * @param method the method to reimplement
	 * @param args arguments to the method
	 * @return return value for the method
	 */
	/**
	 * 重新实现给定的方法。 
	 *  
	 * @param  obj我们正在重新实现
	 * @param 方法的方法该方法重新实现
	 * @param  args方法的参数
	 * @return 方法的返回值
	 */
	Object reimplement(Object obj, Method method, Object[] args) throws Throwable;

}
