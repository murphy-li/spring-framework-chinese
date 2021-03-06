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

package org.springframework.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Comparator capable of sorting exceptions based on their depth from the thrown exception type.
 *
 * @author Juergen Hoeller
 * @author Arjen Poutsma
 * @since 3.0.3
 */
/**
 * 比较器能够根据引发异常类型的深度对异常进行排序。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@author  Arjen Poutsma @3.0.3起
 */
public class ExceptionDepthComparator implements Comparator<Class<? extends Throwable>> {

	private final Class<? extends Throwable> targetException;


	/**
	 * Create a new ExceptionDepthComparator for the given exception.
	 * @param exception the target exception to compare to when sorting by depth
	 */
	/**
	 * 为给定的异常创建一个新的ExceptionDepthComparator。 
	 *  
	 * @param 异常按深度排序时要比较的目标异常
	 */
	public ExceptionDepthComparator(Throwable exception) {
		Assert.notNull(exception, "Target exception must not be null");
		this.targetException = exception.getClass();
	}

	/**
	 * Create a new ExceptionDepthComparator for the given exception type.
	 * @param exceptionType the target exception type to compare to when sorting by depth
	 */
	/**
	 * 为给定的异常类型创建一个新的ExceptionDepthComparator。 
	 *  
	 * @param  exceptionType按深度排序时要比较的目标异常类型
	 */
	public ExceptionDepthComparator(Class<? extends Throwable> exceptionType) {
		Assert.notNull(exceptionType, "Target exception type must not be null");
		this.targetException = exceptionType;
	}


	@Override
	public int compare(Class<? extends Throwable> o1, Class<? extends Throwable> o2) {
		int depth1 = getDepth(o1, this.targetException, 0);
		int depth2 = getDepth(o2, this.targetException, 0);
		return (depth1 - depth2);
	}

	private int getDepth(Class<?> declaredException, Class<?> exceptionToMatch, int depth) {
		if (exceptionToMatch.equals(declaredException)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionToMatch == Throwable.class) {
			return Integer.MAX_VALUE;
		}
		return getDepth(declaredException, exceptionToMatch.getSuperclass(), depth + 1);
	}


	/**
	 * Obtain the closest match from the given exception types for the given target exception.
	 * @param exceptionTypes the collection of exception types
	 * @param targetException the target exception to find a match for
	 * @return the closest matching exception type from the given collection
	 */
	/**
	 * 从给定异常类型的给定目标异常中获取最接近的匹配项。 
	 *  
	 * @param  exceptionTypes异常类型的集合
	 * @param  targetException目标异常，以查找
	 * @return 与给定集合中最接近的匹配异常类型的匹配项
	 */
	public static Class<? extends Throwable> findClosestMatch(
			Collection<Class<? extends Throwable>> exceptionTypes, Throwable targetException) {

		Assert.notEmpty(exceptionTypes, "Exception types must not be empty");
		if (exceptionTypes.size() == 1) {
			return exceptionTypes.iterator().next();
		}
		List<Class<? extends Throwable>> handledExceptions = new ArrayList<>(exceptionTypes);
		handledExceptions.sort(new ExceptionDepthComparator(targetException));
		return handledExceptions.get(0);
	}

}
