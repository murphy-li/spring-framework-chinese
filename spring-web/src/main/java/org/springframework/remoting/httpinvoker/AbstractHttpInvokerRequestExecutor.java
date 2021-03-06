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

package org.springframework.remoting.httpinvoker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.lang.Nullable;
import org.springframework.remoting.rmi.CodebaseAwareObjectInputStream;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Abstract base implementation of the HttpInvokerRequestExecutor interface.
 *
 * <p>Pre-implements serialization of RemoteInvocation objects and
 * deserialization of RemoteInvocationResults objects.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see #doExecuteRequest
 */
/**
 * HttpInvokerRequestExecutor接口的抽象基础实现。 
 *  <p>预实现RemoteInvocation对象的序列化和RemoteInvocationResults对象的反序列化。 
 *  @author  Juergen Hoeller @自1.1起
 * @see  #doExecuteRequest
 */
public abstract class AbstractHttpInvokerRequestExecutor implements HttpInvokerRequestExecutor, BeanClassLoaderAware {

	/**
	 * Default content type: "application/x-java-serialized-object".
	 */
	/**
	 * 默认内容类型："应用程序/ x-java-serialized-object"。 
	 * 
	 */
	public static final String CONTENT_TYPE_SERIALIZED_OBJECT = "application/x-java-serialized-object";

	private static final int SERIALIZED_INVOCATION_BYTE_ARRAY_INITIAL_SIZE = 1024;


	protected static final String HTTP_METHOD_POST = "POST";

	protected static final String HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language";

	protected static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";

	protected static final String HTTP_HEADER_CONTENT_ENCODING = "Content-Encoding";

	protected static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";

	protected static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";

	protected static final String ENCODING_GZIP = "gzip";


	protected final Log logger = LogFactory.getLog(getClass());

	private String contentType = CONTENT_TYPE_SERIALIZED_OBJECT;

	private boolean acceptGzipEncoding = true;

	@Nullable
	private ClassLoader beanClassLoader;


	/**
	 * Specify the content type to use for sending HTTP invoker requests.
	 * <p>Default is "application/x-java-serialized-object".
	 */
	/**
	 * 指定用于发送HTTP调用程序请求的内容类型。 
	 *  <p>默认值为"application / x-java-serialized-object"。 
	 * 
	 */
	public void setContentType(String contentType) {
		Assert.notNull(contentType, "'contentType' must not be null");
		this.contentType = contentType;
	}

	/**
	 * Return the content type to use for sending HTTP invoker requests.
	 */
	/**
	 * 返回用于发送HTTP调用程序请求的内容类型。 
	 * 
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Set whether to accept GZIP encoding, that is, whether to
	 * send the HTTP "Accept-Encoding" header with "gzip" as value.
	 * <p>Default is "true". Turn this flag off if you do not want
	 * GZIP response compression even if enabled on the HTTP server.
	 */
	/**
	 * 设置是否接受GZIP编码，即是否发送以"gzip"作为值的HTTP"Accept-Encoding"标头。 
	 *  <p>默认为"true"。 
	 * 如果即使在HTTP服务器上启用了GZIP响应压缩，也不想关闭它，请关闭此标志。 
	 * 
	 */
	public void setAcceptGzipEncoding(boolean acceptGzipEncoding) {
		this.acceptGzipEncoding = acceptGzipEncoding;
	}

	/**
	 * Return whether to accept GZIP encoding, that is, whether to
	 * send the HTTP "Accept-Encoding" header with "gzip" as value.
	 */
	/**
	 * 返回是否接受GZIP编码，即是否发送以"gzip"作为值的HTTP"Accept-Encoding"标头。 
	 * 
	 */
	public boolean isAcceptGzipEncoding() {
		return this.acceptGzipEncoding;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}

	/**
	 * Return the bean ClassLoader that this executor is supposed to use.
	 */
	/**
	 * 返回该执行程序应该使用的bean ClassLoader。 
	 * 
	 */
	@Nullable
	protected ClassLoader getBeanClassLoader() {
		return this.beanClassLoader;
	}


	@Override
	public final RemoteInvocationResult executeRequest(
			HttpInvokerClientConfiguration config, RemoteInvocation invocation) throws Exception {

		ByteArrayOutputStream baos = getByteArrayOutputStream(invocation);
		if (logger.isDebugEnabled()) {
			logger.debug("Sending HTTP invoker request for service at [" + config.getServiceUrl() +
					"], with size " + baos.size());
		}
		return doExecuteRequest(config, baos);
	}

	/**
	 * Serialize the given RemoteInvocation into a ByteArrayOutputStream.
	 * @param invocation the RemoteInvocation object
	 * @return a ByteArrayOutputStream with the serialized RemoteInvocation
	 * @throws IOException if thrown by I/O methods
	 */
	/**
	 * 将给定的RemoteInvocation序列化为ByteArrayOutputStream。 
	 *  
	 * @param 调用RemoteInvocation对象
	 * @return 带有序列化RemoteInvocation 
	 * @throws  IOException的ByteArrayOutputStream（如果由I / O方法抛出）
	 */
	protected ByteArrayOutputStream getByteArrayOutputStream(RemoteInvocation invocation) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(SERIALIZED_INVOCATION_BYTE_ARRAY_INITIAL_SIZE);
		writeRemoteInvocation(invocation, baos);
		return baos;
	}

	/**
	 * Serialize the given RemoteInvocation to the given OutputStream.
	 * <p>The default implementation gives {@code decorateOutputStream} a chance
	 * to decorate the stream first (for example, for custom encryption or compression).
	 * Creates an {@code ObjectOutputStream} for the final stream and calls
	 * {@code doWriteRemoteInvocation} to actually write the object.
	 * <p>Can be overridden for custom serialization of the invocation.
	 * @param invocation the RemoteInvocation object
	 * @param os the OutputStream to write to
	 * @throws IOException if thrown by I/O methods
	 * @see #decorateOutputStream
	 * @see #doWriteRemoteInvocation
	 */
	/**
	 * 将给定的RemoteInvocation序列化为给定的OutputStream。 
	 *  <p>默认实现使{@code  decorateOutputStream}有机会首先装饰流（例如，用于自定义加密或压缩）。 
	 * 为最终流创建一个{@code  ObjectOutputStream}，并调用{@code  doWriteRemoteInvocation}以实际写入对象。 
	 *  <p>可以为调用的自定义序列化覆盖。 
	 *  
	 * @param 调用RemoteInvocation对象
	 * @param ，如果被I / O方法抛出，则OutputStream写入
	 * @throws  IOException 
	 * @see  #decorateOutputStream 
	 * @see  #doWriteRemoteInvocation
	 */
	protected void writeRemoteInvocation(RemoteInvocation invocation, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(decorateOutputStream(os));
		try {
			doWriteRemoteInvocation(invocation, oos);
		}
		finally {
			oos.close();
		}
	}

	/**
	 * Return the OutputStream to use for writing remote invocations,
	 * potentially decorating the given original OutputStream.
	 * <p>The default implementation returns the given stream as-is.
	 * Can be overridden, for example, for custom encryption or compression.
	 * @param os the original OutputStream
	 * @return the potentially decorated OutputStream
	 */
	/**
	 * 返回OutputStream以用于编写远程调用，从而可能修饰给定的原始OutputStream。 
	 *  <p>默认实现按原样返回给定的流。 
	 * 可以覆盖，例如，用于自定义加密或压缩。 
	 *  
	 * @param 是原始OutputStream的
	 * @return 可能装饰的OutputStream
	 */
	protected OutputStream decorateOutputStream(OutputStream os) throws IOException {
		return os;
	}

	/**
	 * Perform the actual writing of the given invocation object to the
	 * given ObjectOutputStream.
	 * <p>The default implementation simply calls {@code writeObject}.
	 * Can be overridden for serialization of a custom wrapper object rather
	 * than the plain invocation, for example an encryption-aware holder.
	 * @param invocation the RemoteInvocation object
	 * @param oos the ObjectOutputStream to write to
	 * @throws IOException if thrown by I/O methods
	 * @see java.io.ObjectOutputStream#writeObject
	 */
	/**
	 * 将给定的调用对象实际写入给定的ObjectOutputStream。 
	 *  <p>默认实现只是调用{@code  writeObject}。 
	 * 可以重写以用于自定义包装对象的序列化，而不是普通调用（例如，支持加密的持有人）的序列化。 
	 *  
	 * @param 调用RemoteInvocation对象
	 * @param 如果I / O方法抛出该异常，则ObjectOutputStream写入
	 * @throws  IOException 
	 * @see  java.io.ObjectOutputStream＃writeObject
	 */
	protected void doWriteRemoteInvocation(RemoteInvocation invocation, ObjectOutputStream oos) throws IOException {
		oos.writeObject(invocation);
	}


	/**
	 * Execute a request to send the given serialized remote invocation.
	 * <p>Implementations will usually call {@code readRemoteInvocationResult}
	 * to deserialize a returned RemoteInvocationResult object.
	 * @param config the HTTP invoker configuration that specifies the
	 * target service
	 * @param baos the ByteArrayOutputStream that contains the serialized
	 * RemoteInvocation object
	 * @return the RemoteInvocationResult object
	 * @throws IOException if thrown by I/O operations
	 * @throws ClassNotFoundException if thrown during deserialization
	 * @throws Exception in case of general errors
	 * @see #readRemoteInvocationResult(java.io.InputStream, String)
	 */
	/**
	 * 执行请求以发送给定的序列化远程调用。 
	 *  <p>实施通常会调用{@code  readRemoteInvocationResult}来反序列化返回的RemoteInvocationResult对象。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param 购买包含已序列化RemoteInvocation对象的ByteArrayOutputStream 
	 * @return  RemoteInvocationResult对象
	 * @throws  IOException（如果由I / O操作抛出）
	 * @throws 如果在反序列化期间抛出，则抛出ClassNotFoundException 
	 * @throws 一般错误时的异常
	 * @see  #readRemoteInvocationResult（java.io.InputStream，String）
	 */
	protected abstract RemoteInvocationResult doExecuteRequest(
			HttpInvokerClientConfiguration config, ByteArrayOutputStream baos)
			throws Exception;

	/**
	 * Deserialize a RemoteInvocationResult object from the given InputStream.
	 * <p>Gives {@code decorateInputStream} a chance to decorate the stream
	 * first (for example, for custom encryption or compression). Creates an
	 * {@code ObjectInputStream} via {@code createObjectInputStream} and
	 * calls {@code doReadRemoteInvocationResult} to actually read the object.
	 * <p>Can be overridden for custom serialization of the invocation.
	 * @param is the InputStream to read from
	 * @param codebaseUrl the codebase URL to load classes from if not found locally
	 * @return the RemoteInvocationResult object
	 * @throws IOException if thrown by I/O methods
	 * @throws ClassNotFoundException if thrown during deserialization
	 * @see #decorateInputStream
	 * @see #createObjectInputStream
	 * @see #doReadRemoteInvocationResult
	 */
	/**
	 * 从给定的InputStream反序列化RemoteInvocationResult对象。 
	 *  <p>赋予{@code  decorateInputStream}一个机会来首先装饰流（例如，用于自定义加密或压缩）。 
	 * 通过{@code  createObjectInputStream}创建一个{@code  ObjectInputStream}并调用{@code  doReadRemoteInvocationResult}来实际读取对象。 
	 *  <p>可以为调用的自定义序列化覆盖。 
	 *  
	 * @param 是要从
	 * @param  codebaseUrl读取的InputStream，如果不是在本地找到，则从中加载类的代码库URL。 
	 * 如果在反序列化期间抛出
	 * @see  #decorateInputStream 
	 * @see  #createObjectInputStream 
	 * @see  #doReadRemoteInvocationResult
	 */
	protected RemoteInvocationResult readRemoteInvocationResult(InputStream is, @Nullable String codebaseUrl)
			throws IOException, ClassNotFoundException {

		ObjectInputStream ois = createObjectInputStream(decorateInputStream(is), codebaseUrl);
		try {
			return doReadRemoteInvocationResult(ois);
		}
		finally {
			ois.close();
		}
	}

	/**
	 * Return the InputStream to use for reading remote invocation results,
	 * potentially decorating the given original InputStream.
	 * <p>The default implementation returns the given stream as-is.
	 * Can be overridden, for example, for custom encryption or compression.
	 * @param is the original InputStream
	 * @return the potentially decorated InputStream
	 */
	/**
	 * 返回InputStream以用于读取远程调用结果，从而可能修饰给定的原始InputStream。 
	 *  <p>默认实现按原样返回给定的流。 
	 * 可以覆盖，例如，用于自定义加密或压缩。 
	 *  
	 * @param 是原始InputStream 
	 * @return 可能经过修饰的InputStream
	 */
	protected InputStream decorateInputStream(InputStream is) throws IOException {
		return is;
	}

	/**
	 * Create an ObjectInputStream for the given InputStream and codebase.
	 * The default implementation creates a CodebaseAwareObjectInputStream.
	 * @param is the InputStream to read from
	 * @param codebaseUrl the codebase URL to load classes from if not found locally
	 * (can be {@code null})
	 * @return the new ObjectInputStream instance to use
	 * @throws IOException if creation of the ObjectInputStream failed
	 * @see org.springframework.remoting.rmi.CodebaseAwareObjectInputStream
	 */
	/**
	 * 为给定的InputStream和代码库创建一个ObjectInputStream。 
	 * 默认实现创建一个CodebaseAwareObjectInputStream。 
	 *  
	 * @param 是要从
	 * @param  codebaseUrl读取的InputStream，如果不是在本地找到（可以是{@code  null}），则要从中加载类的代码库URL。 
	 *  > IOException如果ObjectInputStream的创建失败
	 * @see  org.springframework.remoting.rmi.CodebaseAwareObjectInputStream
	 */
	protected ObjectInputStream createObjectInputStream(InputStream is, @Nullable String codebaseUrl) throws IOException {
		return new CodebaseAwareObjectInputStream(is, getBeanClassLoader(), codebaseUrl);
	}

	/**
	 * Perform the actual reading of an invocation object from the
	 * given ObjectInputStream.
	 * <p>The default implementation simply calls {@code readObject}.
	 * Can be overridden for deserialization of a custom wrapper object rather
	 * than the plain invocation, for example an encryption-aware holder.
	 * @param ois the ObjectInputStream to read from
	 * @return the RemoteInvocationResult object
	 * @throws IOException if thrown by I/O methods
	 * @throws ClassNotFoundException if the class name of a serialized object
	 * couldn't get resolved
	 * @see java.io.ObjectOutputStream#writeObject
	 */
	/**
	 * 从给定的ObjectInputStream执行调用对象的实际读取。 
	 *  <p>默认实现只是调用{@code  readObject}。 
	 * 可以为自定义包装对象的反序列化而不是普通调用（例如，支持加密的持有者）进行覆盖。 
	 *  
	 * @param 允许ObjectInputStream从
	 * @return 读取RemoteInvocationResult对象
	 * @throws  IOException（如果由I / O方法抛出）
	 * @throws  ClassNotFoundException如果序列化对象的类名无法解析<
	 * @see > java.io.ObjectOutputStream＃writeObject
	 */
	protected RemoteInvocationResult doReadRemoteInvocationResult(ObjectInputStream ois)
			throws IOException, ClassNotFoundException {

		Object obj = ois.readObject();
		if (!(obj instanceof RemoteInvocationResult)) {
			throw new RemoteException("Deserialized object needs to be assignable to type [" +
					RemoteInvocationResult.class.getName() + "]: " + ClassUtils.getDescriptiveType(obj));
		}
		return (RemoteInvocationResult) obj;
	}

}
