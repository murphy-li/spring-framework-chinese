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

package org.springframework.aop;

import org.springframework.lang.Nullable;

/**
 * A {@code TargetSource} is used to obtain the current "target" of
 * an AOP invocation, which will be invoked via reflection if no around
 * advice chooses to end the interceptor chain itself.
 *
 * <p>If a {@code TargetSource} is "static", it will always return
 * the same target, allowing optimizations in the AOP framework. Dynamic
 * target sources can support pooling, hot swapping, etc.
 *
 * <p>Application developers don't usually need to work with
 * {@code TargetSources} directly: this is an AOP framework interface.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
/**
 * {@code  TargetSource}用于获取AOP调用的当前"目标"，如果没有周围建议选择终止拦截器链本身，则将通过反射来调用它。 
 *  <p>如果{@code  TargetSource}是"静态"的，它将始终返回相同的目标，从而允许在AOP框架中进行优化。 
 * 动态目标源可以支持池化，热交换等。 
 * <p>应用程序开发人员通常不需要直接与{@code  TargetSources}一起工作：这是AOP框架接口。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller
 */
public interface TargetSource extends TargetClassAware {

	/**
	 * Return the type of targets returned by this {@link TargetSource}.
	 * <p>Can return {@code null}, although certain usages of a {@code TargetSource}
	 * might just work with a predetermined target class.
	 * @return the type of targets returned by this {@link TargetSource}
	 */
	/**
	 * 返回此{@link  TargetSource}返回的目标的类型。 
	 *  <p>可以返回{@code  null}，尽管{@code  TargetSource}的某些用法可能只适用于预定的目标类。 
	 *  
	 * @return 此{@link  TargetSource}返回的目标的类型
	 */
	@Override
	@Nullable
	Class<?> getTargetClass();

	/**
	 * Will all calls to {@link #getTarget()} return the same object?
	 * <p>In that case, there will be no need to invoke {@link #releaseTarget(Object)},
	 * and the AOP framework can cache the return value of {@link #getTarget()}.
	 * @return {@code true} if the target is immutable
	 * @see #getTarget
	 */
	/**
	 * 所有对{@link  #getTarget（）}的调用都会返回相同的对象吗？ <p>在这种情况下，无需调用{@link  #releaseTarget（Object）}，并且AOP框架可以缓存{@link  #getTarget（）}的返回值。 
	 *  
	 * @return  {@code  true}，如果目标是不可变的
	 * @see  #getTarget
	 */
	boolean isStatic();

	/**
	 * Return a target instance. Invoked immediately before the
	 * AOP framework calls the "target" of an AOP method invocation.
	 * @return the target object which contains the joinpoint,
	 * or {@code null} if there is no actual target instance
	 * @throws Exception if the target object can't be resolved
	 */
	/**
	 * 返回目标实例。 
	 * 在AOP框架调用AOP方法调用的"目标"之前立即调用。 
	 *  
	 * @return 包含连接点的目标对象，如果没有实际的目标实例，则为{@code  null} 
	 * @throws 如果无法解析目标对象，则为异常
	 */
	@Nullable
	Object getTarget() throws Exception;

	/**
	 * Release the given target object obtained from the
	 * {@link #getTarget()} method, if any.
	 * @param target object obtained from a call to {@link #getTarget()}
	 * @throws Exception if the object can't be released
	 */
	/**
	 * 释放从{@link  #getTarget（）}方法获得的给定目标对象（如果有）。 
	 * 通过调用{@link  #getTarget（）}获得的
	 * @param 目标对象
	 * @throws 如果无法释放该对象，则异常
	 */
	void releaseTarget(Object target) throws Exception;

}
