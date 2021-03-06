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

package org.springframework.beans.factory;

import org.springframework.beans.FatalBeanException;

/**
 * Exception to be thrown from a FactoryBean's {@code getObject()} method
 * if the bean is not fully initialized yet, for example because it is involved
 * in a circular reference.
 *
 * <p>Note: A circular reference with a FactoryBean cannot be solved by eagerly
 * caching singleton instances like with normal beans. The reason is that
 * <i>every</i> FactoryBean needs to be fully initialized before it can
 * return the created bean, while only <i>specific</i> normal beans need
 * to be initialized - that is, if a collaborating bean actually invokes
 * them on initialization instead of just storing the reference.
 *
 * @author Juergen Hoeller
 * @since 30.10.2003
 * @see FactoryBean#getObject()
 */
/**
 * 如果bean尚未完全初始化（例如，因为它包含在循环引用中），则从FactoryBean的{@code  getObject（）}方法引发异常。 
 *  <p>注意：像普通的bean那样，急于缓存单例实例不能解决带有FactoryBean的循环引用。 
 * 原因是，每个<i>每个</ i> FactoryBean都必须先完全初始化，然后才能返回创建的bean，而只有<i>特定</ i>个普通bean才需要初始化-也就是说，如果一个协作bean实际上是在初始化时调用它们，而不仅仅是存储引用。 
 *  @author  Juergen Hoeller @2003年10月30日
 * @see  FactoryBean＃getObject（）
 */
@SuppressWarnings("serial")
public class FactoryBeanNotInitializedException extends FatalBeanException {

	/**
	 * Create a new FactoryBeanNotInitializedException with the default message.
	 */
	/**
	 * 使用默认消息创建一个新的FactoryBeanNotInitializedException。 
	 * 
	 */
	public FactoryBeanNotInitializedException() {
		super("FactoryBean is not fully initialized yet");
	}

	/**
	 * Create a new FactoryBeanNotInitializedException with the given message.
	 * @param msg the detail message
	 */
	/**
	 * 使用给定的消息创建一个新的FactoryBeanNotInitializedException。 
	 *  
	 * @param  msg详细信息
	 */
	public FactoryBeanNotInitializedException(String msg) {
		super(msg);
	}

}
