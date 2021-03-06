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

import org.aopalliance.intercept.MethodInvocation;

/**
 * Strategy interface for creating a {@link RemoteInvocation} from an AOP Alliance
 * {@link org.aopalliance.intercept.MethodInvocation}.
 *
 * <p>Used by {@link org.springframework.remoting.rmi.RmiClientInterceptor} (for RMI invokers)
 * and by {@link org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor}.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see DefaultRemoteInvocationFactory
 * @see org.springframework.remoting.rmi.RmiClientInterceptor#setRemoteInvocationFactory
 * @see org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor#setRemoteInvocationFactory
 */
/**
 * 用于从AOP联盟{@link  org.aopalliance.intercept.MethodInvocation}创建{@link  RemoteInvocation}的策略界面。 
 *  <p>由{@link  org.springframework.remoting.rmi.RmiClientInterceptor}（用于RMI调用者）和{@link  org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor}使用。 
 *  @author 尤尔根·霍勒（Juergen Hoeller）@自1.1起
 */
public interface RemoteInvocationFactory {

	/**
	 * Create a serializable RemoteInvocation object from the given AOP
	 * MethodInvocation.
	 * <p>Can be implemented to add custom context information to the
	 * remote invocation, for example user credentials.
	 * @param methodInvocation the original AOP MethodInvocation object
	 * @return the RemoteInvocation object
	 */
	/**
	 * 从给定的AOP MethodInvocation创建可序列化的RemoteInvocation对象。 
	 *  <p>可以实现将自定义上下文信息添加到远程调用中，例如用户凭据。 
	 *  
	 * @param  methodInvocation原始AOP MethodInvocation对象
	 * @return  RemoteInvocation对象
	 */
	RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation);

}
