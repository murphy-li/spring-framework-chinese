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

package org.springframework.aop.aspectj;

import java.lang.reflect.InvocationTargetException;

import org.springframework.aop.framework.AopConfigException;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Implementation of {@link AspectInstanceFactory} that creates a new instance
 * of the specified aspect class for every {@link #getAspectInstance()} call.
 *
 * @author Juergen Hoeller
 * @since 2.0.4
 */
/**
 * {@link  AspectInstanceFactory}的实现为每个{@link  #getAspectInstance（）}调用创建一个指定方面类的新实例。 
 *  @author  Juergen Hoeller @始于2.0.4
 */
public class SimpleAspectInstanceFactory implements AspectInstanceFactory {

	private final Class<?> aspectClass;


	/**
	 * Create a new SimpleAspectInstanceFactory for the given aspect class.
	 * @param aspectClass the aspect class
	 */
	/**
	 * 为给定的方面类创建一个新的SimpleAspectInstanceFactory。 
	 *  @param AspectClass方面类
	 */
	public SimpleAspectInstanceFactory(Class<?> aspectClass) {
		Assert.notNull(aspectClass, "Aspect class must not be null");
		this.aspectClass = aspectClass;
	}


	/**
	 * Return the specified aspect class (never {@code null}).
	 */
	/**
	 * 返回指定的方面类（不要{@code  null}）。 
	 * 
	 */
	public final Class<?> getAspectClass() {
		return this.aspectClass;
	}

	@Override
	public final Object getAspectInstance() {
		try {
			return ReflectionUtils.accessibleConstructor(this.aspectClass).newInstance();
		}
		catch (NoSuchMethodException ex) {
			throw new AopConfigException(
					"No default constructor on aspect class: " + this.aspectClass.getName(), ex);
		}
		catch (InstantiationException ex) {
			throw new AopConfigException(
					"Unable to instantiate aspect class: " + this.aspectClass.getName(), ex);
		}
		catch (IllegalAccessException ex) {
			throw new AopConfigException(
					"Could not access aspect constructor: " + this.aspectClass.getName(), ex);
		}
		catch (InvocationTargetException ex) {
			throw new AopConfigException(
					"Failed to invoke aspect constructor: " + this.aspectClass.getName(), ex.getTargetException());
		}
	}

	@Override
	@Nullable
	public ClassLoader getAspectClassLoader() {
		return this.aspectClass.getClassLoader();
	}

	/**
	 * Determine the order for this factory's aspect instance,
	 * either an instance-specific order expressed through implementing
	 * the {@link org.springframework.core.Ordered} interface,
	 * or a fallback order.
	 * @see org.springframework.core.Ordered
	 * @see #getOrderForAspectClass
	 */
	/**
	 * 确定该工厂的方面实例的顺序，可以是通过实现{@link  org.springframework.core.Ordered}接口表示的实例特定顺序，也可以是后备顺序。 
	 *  
	 * @see  org.springframework.core.Ordered 
	 * @see  #getOrderForAspectClass
	 */
	@Override
	public int getOrder() {
		return getOrderForAspectClass(this.aspectClass);
	}

	/**
	 * Determine a fallback order for the case that the aspect instance
	 * does not express an instance-specific order through implementing
	 * the {@link org.springframework.core.Ordered} interface.
	 * <p>The default implementation simply returns {@code Ordered.LOWEST_PRECEDENCE}.
	 * @param aspectClass the aspect class
	 */
	/**
	 * 通过实现{@link  org.springframework.core.Ordered}接口，确定方面实例未表达特定于实例的顺序的情况下的后备顺序。 
	 *  <p>默认实现只返回{@code  Ordered.LOWEST_PRECEDENCE}。 
	 *  @param AspectClass方面类
	 */
	protected int getOrderForAspectClass(Class<?> aspectClass) {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
