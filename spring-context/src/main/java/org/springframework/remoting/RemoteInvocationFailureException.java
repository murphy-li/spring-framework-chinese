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

package org.springframework.remoting;

/**
 * RemoteAccessException subclass to be thrown when the execution
 * of the target method failed on the server side, for example
 * when a method was not found on the target object.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see RemoteProxyFailureException
 */
/**
 * 当在服务器端执行目标方法失败时（例如，在目标对象上找不到方法时），将抛出RemoteAccessException子类。 
 *  @author  Juergen Hoeller @自2.5起
 * @see  RemoteProxyFailureException
 */
@SuppressWarnings("serial")
public class RemoteInvocationFailureException extends RemoteAccessException {

	/**
	 * Constructor for RemoteInvocationFailureException.
	 * @param msg the detail message
	 * @param cause the root cause from the remoting API in use
	 */
	/**
	 * RemoteInvocationFailureException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 原因是所使用的远程API的根本原因
	 */
	public RemoteInvocationFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
