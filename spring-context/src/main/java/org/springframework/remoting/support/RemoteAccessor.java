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

package org.springframework.remoting.support;

import org.springframework.util.Assert;

/**
 * Abstract base class for classes that access a remote service.
 * Provides a "serviceInterface" bean property.
 *
 * <p>Note that the service interface being used will show some signs of
 * remotability, like the granularity of method calls that it offers.
 * Furthermore, it has to have serializable arguments etc.
 *
 * <p>Accessors are supposed to throw Spring's generic
 * {@link org.springframework.remoting.RemoteAccessException} in case
 * of remote invocation failure, provided that the service interface
 * does not declare {@code java.rmi.RemoteException}.
 *
 * @author Juergen Hoeller
 * @since 13.05.2003
 * @see org.springframework.remoting.RemoteAccessException
 * @see java.rmi.RemoteException
 */
/**
 * 访问远程服务的类的抽象基类。 
 * 提供一个"serviceInterface"bean属性。 
 *  <p>请注意，正在使用的服务接口将显示一些可远程性的迹象，例如它提供的方法调用的粒度。 
 * 此外，它必须具有可序列化的参数等。 
 * <p>在服务调用未声明{<的情况下，如果远程调用失败，访问器应该抛出Spring的通用{@link  org.springframework.remoting.RemoteAccessException}。 
 *  @code> java.rmi.RemoteException}。 
 *  @author  Juergen Hoeller @2003年5月13日起
 * @see  org.springframework.remoting.RemoteAccessException 
 * @see  java.rmi.RemoteException
 */
public abstract class RemoteAccessor extends RemotingSupport {

	private Class<?> serviceInterface;


	/**
	 * Set the interface of the service to access.
	 * The interface must be suitable for the particular service and remoting strategy.
	 * <p>Typically required to be able to create a suitable service proxy,
	 * but can also be optional if the lookup returns a typed proxy.
	 */
	/**
	 * 设置要访问的服务接口。 
	 * 该接口必须适合于特定的服务和远程策略。 
	 *  <p>通常需要能够创建合适的服务代理，但是如果查找返回类型化的代理，它也可以是可选的。 
	 * 
	 */
	public void setServiceInterface(Class<?> serviceInterface) {
		Assert.notNull(serviceInterface, "'serviceInterface' must not be null");
		Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
		this.serviceInterface = serviceInterface;
	}

	/**
	 * Return the interface of the service to access.
	 */
	/**
	 * 返回服务的接口以进行访问。 
	 * 
	 */
	public Class<?> getServiceInterface() {
		return this.serviceInterface;
	}

}
