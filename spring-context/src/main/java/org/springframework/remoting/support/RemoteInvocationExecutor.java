/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2007 the original author or authors.
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
 * 版权所有2002-2007的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.remoting.support;

import java.lang.reflect.InvocationTargetException;

/**
 * Strategy interface for executing a {@link RemoteInvocation} on a target object.
 *
 * <p>Used by {@link org.springframework.remoting.rmi.RmiServiceExporter} (for RMI invokers)
 * and by {@link org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter}.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see DefaultRemoteInvocationFactory
 * @see org.springframework.remoting.rmi.RmiServiceExporter#setRemoteInvocationExecutor
 * @see org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter#setRemoteInvocationExecutor
 */
/**
 * 用于在目标对象上执行{@link  RemoteInvocation}的策略接口。 
 *  <p>由{@link  org.springframework.remoting.rmi.RmiServiceExporter}（用于RMI调用者）和{@link  org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter}使用。 
 *  @author  Juergen Hoeller @since 1.1起
 */
public interface RemoteInvocationExecutor {

	/**
	 * Perform this invocation on the given target object.
	 * Typically called when a RemoteInvocation is received on the server.
	 * @param invocation the RemoteInvocation
	 * @param targetObject the target object to apply the invocation to
	 * @return the invocation result
	 * @throws NoSuchMethodException if the method name could not be resolved
	 * @throws IllegalAccessException if the method could not be accessed
	 * @throws InvocationTargetException if the method invocation resulted in an exception
	 * @see java.lang.reflect.Method#invoke
	 */
	/**
	 * 对给定的目标对象执行此调用。 
	 * 通常在服务器上收到RemoteInvocation时调用。 
	 *  
	 * @param 调用RemoteInvocation 
	 * @param  targetObject目标对象，以将调用应用于
	 * @return 调用结果
	 * @throws  NoSuchMethodException（如果无法解析方法名称）
	 * @throws  IllegalAccessException（如果无法解析方法）如果方法调用导致异常
	 * @see  java.lang.reflect.Method＃invoke被访问
	 * @throws  InvocationTargetException
	 */
	Object invoke(RemoteInvocation invocation, Object targetObject)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

}
