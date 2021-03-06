/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2016 the original author or authors.
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
 * 版权所有2002-2016的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.remoting.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.UnknownHostException;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.util.ReflectionUtils;

/**
 * Factored-out methods for performing invocations within an RMI client.
 * Can handle both RMI and non-RMI service interfaces working on an RMI stub.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @since 1.1
 */
/**
 * 用于在RMI客户端中执行调用的分解方法。 
 * 可以处理在RMI存根上工作的RMI和非RMI服务接口。 
 *  <p>注意：这是SPI类，不适合应用程序使用。 
 *  @author  Juergen Hoeller @始于1.1
 */
public abstract class RmiClientInterceptorUtils {

	private static final Log logger = LogFactory.getLog(RmiClientInterceptorUtils.class);


	/**
	 * Perform a raw method invocation on the given RMI stub,
	 * letting reflection exceptions through as-is.
	 * @param invocation the AOP MethodInvocation
	 * @param stub the RMI stub
	 * @return the invocation result, if any
	 * @throws InvocationTargetException if thrown by reflection
	 */
	/**
	 * 在给定的RMI存根上执行原始方法调用，使反射异常按原样进行。 
	 *  
	 * @param 调用AOP MethodInvocation 
	 * @param 存根RMI存根
	 * @return 调用结果，如果有的话
	 * @throws  InvocationTargetException如果通过反射抛出
	 */
	@Nullable
	public static Object invokeRemoteMethod(MethodInvocation invocation, Object stub)
			throws InvocationTargetException {

		Method method = invocation.getMethod();
		try {
			if (method.getDeclaringClass().isInstance(stub)) {
				// directly implemented
				return method.invoke(stub, invocation.getArguments());
			}
			else {
				// not directly implemented
				Method stubMethod = stub.getClass().getMethod(method.getName(), method.getParameterTypes());
				return stubMethod.invoke(stub, invocation.getArguments());
			}
		}
		catch (InvocationTargetException ex) {
			throw ex;
		}
		catch (NoSuchMethodException ex) {
			throw new RemoteProxyFailureException("No matching RMI stub method found for: " + method, ex);
		}
		catch (Throwable ex) {
			throw new RemoteProxyFailureException("Invocation of RMI stub method failed: " + method, ex);
		}
	}

	/**
	 * Wrap the given arbitrary exception that happened during remote access
	 * in either a RemoteException or a Spring RemoteAccessException (if the
	 * method signature does not support RemoteException).
	 * <p>Only call this for remote access exceptions, not for exceptions
	 * thrown by the target service itself!
	 * @param method the invoked method
	 * @param ex the exception that happened, to be used as cause for the
	 * RemoteAccessException or RemoteException
	 * @param message the message for the RemoteAccessException respectively
	 * RemoteException
	 * @return the exception to be thrown to the caller
	 */
	/**
	 * 将在远程访问期间发生的给定任意异常包装在RemoteException或Spring RemoteAccessException中（如果方法签名不支持RemoteException）。 
	 *  <p>仅针对远程访问异常而不是由目标服务本身抛出的异常调用此函数！ 
	 *  
	 * @param 方法是已发生异常的被调用方法
	 * @param ，用作RemoteAccessException或RemoteException 
	 * @param 消息的原因，分别是RemoteAccessException的消息和RemoteException 
	 * @return 的异常呼叫者，召集者
	 */
	public static Exception convertRmiAccessException(Method method, Throwable ex, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message, ex);
		}
		if (ReflectionUtils.declaresException(method, RemoteException.class)) {
			return new RemoteException(message, ex);
		}
		else {
			return new RemoteAccessException(message, ex);
		}
	}

	/**
	 * Convert the given RemoteException that happened during remote access
	 * to Spring's RemoteAccessException if the method signature does not
	 * support RemoteException. Else, return the original RemoteException.
	 * @param method the invoked method
	 * @param ex the RemoteException that happened
	 * @param serviceName the name of the service (for debugging purposes)
	 * @return the exception to be thrown to the caller
	 */
	/**
	 * 如果方法签名不支持RemoteException，则将在远程访问期间发生的给定RemoteException转换为Spring的RemoteAccessException。 
	 * 否则，返回原始的RemoteException。 
	 *  
	 * @param 方法调用的方法
	 * @param 例如发生的RemoteException 
	 * @param  serviceName服务的名称（出于调试目的）
	 * @return 要抛出给调用者的异常
	 */
	public static Exception convertRmiAccessException(Method method, RemoteException ex, String serviceName) {
		return convertRmiAccessException(method, ex, isConnectFailure(ex), serviceName);
	}

	/**
	 * Convert the given RemoteException that happened during remote access
	 * to Spring's RemoteAccessException if the method signature does not
	 * support RemoteException. Else, return the original RemoteException.
	 * @param method the invoked method
	 * @param ex the RemoteException that happened
	 * @param isConnectFailure whether the given exception should be considered
	 * a connect failure
	 * @param serviceName the name of the service (for debugging purposes)
	 * @return the exception to be thrown to the caller
	 */
	/**
	 * 如果方法签名不支持RemoteException，则将在远程访问期间发生的给定RemoteException转换为Spring的RemoteAccessException。 
	 * 否则，返回原始的RemoteException。 
	 *  
	 * @param 方法调用的方法
	 * @param 发生过的RemoteException，<
	 * @param> isConnectFailure是否应将给定的异常视为连接失败
	 * @param  serviceName服务的名称（出于调试目的）<
	 * @return >抛出给调用者的异常
	 */
	public static Exception convertRmiAccessException(
			Method method, RemoteException ex, boolean isConnectFailure, String serviceName) {

		if (logger.isDebugEnabled()) {
			logger.debug("Remote service [" + serviceName + "] threw exception", ex);
		}
		if (ReflectionUtils.declaresException(method, ex.getClass())) {
			return ex;
		}
		else {
			if (isConnectFailure) {
				return new RemoteConnectFailureException("Could not connect to remote service [" + serviceName + "]", ex);
			}
			else {
				return new RemoteAccessException("Could not access remote service [" + serviceName + "]", ex);
			}
		}
	}

	/**
	 * Determine whether the given RMI exception indicates a connect failure.
	 * <p>Treats RMI's ConnectException, ConnectIOException, UnknownHostException,
	 * NoSuchObjectException and StubNotFoundException as connect failure.
	 * @param ex the RMI exception to check
	 * @return whether the exception should be treated as connect failure
	 * @see java.rmi.ConnectException
	 * @see java.rmi.ConnectIOException
	 * @see java.rmi.UnknownHostException
	 * @see java.rmi.NoSuchObjectException
	 * @see java.rmi.StubNotFoundException
	 */
	/**
	 * 确定给定的RMI异常是否指示连接失败。 
	 *  <p>将RMI的ConnectException，ConnectIOException，UnknownHostException，NoSuchObjectException和StubNotFoundException视为连接失败。 
	 *  
	 * @param 从RMI异常中检查
	 * @return 是否将该异常视为连接失败
	 * @see  java.rmi.ConnectException 
	 * @see  java.rmi.ConnectIOException 
	 * @see  java.rmi.UnknownHostException 
	 * @see  java.rmi.NoSuchObjectException 
	 * @see  java.rmi.StubNotFoundException
	 */
	public static boolean isConnectFailure(RemoteException ex) {
		return (ex instanceof ConnectException || ex instanceof ConnectIOException ||
				ex instanceof UnknownHostException || ex instanceof NoSuchObjectException ||
				ex instanceof StubNotFoundException || ex.getCause() instanceof SocketException);
	}

}
