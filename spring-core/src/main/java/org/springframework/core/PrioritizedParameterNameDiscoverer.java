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

package org.springframework.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.springframework.lang.Nullable;

/**
 * {@link ParameterNameDiscoverer} implementation that tries several discoverer
 * delegates in succession. Those added first in the {@code addDiscoverer} method
 * have highest priority. If one returns {@code null}, the next will be tried.
 *
 * <p>The default behavior is to return {@code null} if no discoverer matches.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * {@link  ParameterNameDiscoverer}实现，该实现连续尝试多个发现者委托。 
 * 在{@code  addDiscoverer}方法中最先添加的那些优先级最高。 
 * 如果返回{@code  null}，将尝试下一个。 
 *  <p>如果没有发现者匹配，默认行为是返回{@code  null}。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller @始于2.0
 */
public class PrioritizedParameterNameDiscoverer implements ParameterNameDiscoverer {

	private final List<ParameterNameDiscoverer> parameterNameDiscoverers = new LinkedList<>();


	/**
	 * Add a further {@link ParameterNameDiscoverer} delegate to the list of
	 * discoverers that this {@code PrioritizedParameterNameDiscoverer} checks.
	 */
	/**
	 * 向此{@code  PrioritizedParameterNameDiscoverer}检查的发现者列表中添加另一个{@link  ParameterNameDiscoverer}委托。 
	 * 
	 */
	public void addDiscoverer(ParameterNameDiscoverer pnd) {
		this.parameterNameDiscoverers.add(pnd);
	}


	@Override
	@Nullable
	public String[] getParameterNames(Method method) {
		for (ParameterNameDiscoverer pnd : this.parameterNameDiscoverers) {
			String[] result = pnd.getParameterNames(method);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	@Nullable
	public String[] getParameterNames(Constructor<?> ctor) {
		for (ParameterNameDiscoverer pnd : this.parameterNameDiscoverers) {
			String[] result = pnd.getParameterNames(ctor);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
