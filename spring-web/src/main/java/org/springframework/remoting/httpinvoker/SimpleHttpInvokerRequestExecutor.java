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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.remoting.support.RemoteInvocationResult;

/**
 * {@link org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor} implementation
 * that uses standard Java facilities to execute POST requests, without support for HTTP
 * authentication or advanced configuration options.
 *
 * <p>Designed for easy subclassing, customizing specific template methods. However,
 * consider {@code HttpComponentsHttpInvokerRequestExecutor} for more sophisticated needs:
 * The standard {@link HttpURLConnection} class is rather limited in its capabilities.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see java.net.HttpURLConnection
 */
/**
 * {@link  org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor}实现，该实现使用标准Java工具执行POST请求，而不支持HTTP身份验证或高级配置选项。 
 *  <p>为易于子类化而设计，可定制特定的模板方法。 
 * 但是，可以考虑使用{@code  HttpComponentsHttpInvokerRequestExecutor}来满足更复杂的需求：标准{@link  HttpURLConnection}类的功能受到很大限制。 
 *  @author  Juergen Hoeller @始于1.1 
 * @see  java.net.HttpURLConnection
 */
public class SimpleHttpInvokerRequestExecutor extends AbstractHttpInvokerRequestExecutor {

	private int connectTimeout = -1;

	private int readTimeout = -1;


	/**
	 * Set the underlying URLConnection's connect timeout (in milliseconds).
	 * A timeout value of 0 specifies an infinite timeout.
	 * <p>Default is the system's default timeout.
	 * @see URLConnection#setConnectTimeout(int)
	 */
	/**
	 * 设置基础URLConnection的连接超时（以毫秒为单位）。 
	 * 超时值为0指定无限超时。 
	 *  <p> Default是系统的默认超时。 
	 *  
	 * @see  URLConnection＃setConnectTimeout（int）
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * Set the underlying URLConnection's read timeout (in milliseconds).
	 * A timeout value of 0 specifies an infinite timeout.
	 * <p>Default is the system's default timeout.
	 * @see URLConnection#setReadTimeout(int)
	 */
	/**
	 * 设置基础URLConnection的读取超时（以毫秒为单位）。 
	 * 超时值为0指定无限超时。 
	 *  <p> Default是系统的默认超时。 
	 *  
	 * @see  URLConnection＃setReadTimeout（int）
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}


	/**
	 * Execute the given request through a standard {@link HttpURLConnection}.
	 * <p>This method implements the basic processing workflow:
	 * The actual work happens in this class's template methods.
	 * @see #openConnection
	 * @see #prepareConnection
	 * @see #writeRequestBody
	 * @see #validateResponse
	 * @see #readResponseBody
	 */
	/**
	 * 通过标准{@link  HttpURLConnection}执行给定的请求。 
	 *  <p>此方法实现基本的处理工作流程：实际工作发生在此类的模板方法中。 
	 *  
	 * @see  #openConnection 
	 * @see  #prepareConnection 
	 * @see  #writeRequestBody 
	 * @see  #validateResponse 
	 * @see  #readResponseBody
	 */
	@Override
	protected RemoteInvocationResult doExecuteRequest(
			HttpInvokerClientConfiguration config, ByteArrayOutputStream baos)
			throws IOException, ClassNotFoundException {

		HttpURLConnection con = openConnection(config);
		prepareConnection(con, baos.size());
		writeRequestBody(config, con, baos);
		validateResponse(config, con);
		InputStream responseBody = readResponseBody(config, con);

		return readRemoteInvocationResult(responseBody, config.getCodebaseUrl());
	}

	/**
	 * Open an {@link HttpURLConnection} for the given remote invocation request.
	 * @param config the HTTP invoker configuration that specifies the
	 * target service
	 * @return the HttpURLConnection for the given request
	 * @throws IOException if thrown by I/O methods
	 * @see java.net.URL#openConnection()
	 */
	/**
	 * 为给定的远程调用请求打开{@link  HttpURLConnection}。 
	 *  
	 * @param  config指定请求服务的HTTP调用程序配置
	 * @return 给定请求的HttpURLConnection 
	 * @throws  IOException（如果由I / O方法抛出）
	 * @see  java.net.URL＃openConnection（）
	 */
	protected HttpURLConnection openConnection(HttpInvokerClientConfiguration config) throws IOException {
		URLConnection con = new URL(config.getServiceUrl()).openConnection();
		if (!(con instanceof HttpURLConnection)) {
			throw new IOException(
					"Service URL [" + config.getServiceUrl() + "] does not resolve to an HTTP connection");
		}
		return (HttpURLConnection) con;
	}

	/**
	 * Prepare the given HTTP connection.
	 * <p>The default implementation specifies POST as method,
	 * "application/x-java-serialized-object" as "Content-Type" header,
	 * and the given content length as "Content-Length" header.
	 * @param connection the HTTP connection to prepare
	 * @param contentLength the length of the content to send
	 * @throws IOException if thrown by HttpURLConnection methods
	 * @see java.net.HttpURLConnection#setRequestMethod
	 * @see java.net.HttpURLConnection#setRequestProperty
	 */
	/**
	 * 准备给定的HTTP连接。 
	 *  <p>默认实现将POST指定为方法，将"application / x-java-serialized-object"指定为"Content-Type"标头，并将给定的内容长度指定为"Content-Length"标头。 
	 *  
	 * @param 连接HTTP连接以准备
	 * @param  contentLength要发送的内容的长度
	 * @throws  IOException（如果由HttpURLConnection方法抛出）
	 * @see  java.net.HttpURLConnection＃setRequestMethod 
	 * @see  java.net .HttpURLConnection＃setRequestProperty
	 */
	protected void prepareConnection(HttpURLConnection connection, int contentLength) throws IOException {
		if (this.connectTimeout >= 0) {
			connection.setConnectTimeout(this.connectTimeout);
		}
		if (this.readTimeout >= 0) {
			connection.setReadTimeout(this.readTimeout);
		}

		connection.setDoOutput(true);
		connection.setRequestMethod(HTTP_METHOD_POST);
		connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, getContentType());
		connection.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH, Integer.toString(contentLength));

		LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
		if (localeContext != null) {
			Locale locale = localeContext.getLocale();
			if (locale != null) {
				connection.setRequestProperty(HTTP_HEADER_ACCEPT_LANGUAGE, locale.toLanguageTag());
			}
		}

		if (isAcceptGzipEncoding()) {
			connection.setRequestProperty(HTTP_HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
		}
	}

	/**
	 * Set the given serialized remote invocation as request body.
	 * <p>The default implementation simply write the serialized invocation to the
	 * HttpURLConnection's OutputStream. This can be overridden, for example, to write
	 * a specific encoding and potentially set appropriate HTTP request headers.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param con the HttpURLConnection to write the request body to
	 * @param baos the ByteArrayOutputStream that contains the serialized
	 * RemoteInvocation object
	 * @throws IOException if thrown by I/O methods
	 * @see java.net.HttpURLConnection#getOutputStream()
	 * @see java.net.HttpURLConnection#setRequestProperty
	 */
	/**
	 * 将给定的序列化远程调用设置为请求正文。 
	 *  <p>默认实现只是将序列化的调用写入HttpURLConnection的OutputStream。 
	 * 例如，可以重写此方法以编写特定的编码并可能设置适当的HTTP请求标头。 
	 *  
	 * @param  config指定目标服务
	 * @param 的HTTP调用程序配置，将HttpURLConnection写入请求正文至
	 * @param ，如果ByteArrayOutputStream包含序列化的RemoteInvocation对象
	 * @throws  IOException，则由I /抛出O方法
	 * @see  java.net.HttpURLConnection＃getOutputStream（）
	 * @see  java.net.HttpURLConnection＃setRequestProperty
	 */
	protected void writeRequestBody(
			HttpInvokerClientConfiguration config, HttpURLConnection con, ByteArrayOutputStream baos)
			throws IOException {

		baos.writeTo(con.getOutputStream());
	}

	/**
	 * Validate the given response as contained in the {@link HttpURLConnection} object,
	 * throwing an exception if it does not correspond to a successful HTTP response.
	 * <p>Default implementation rejects any HTTP status code beyond 2xx, to avoid
	 * parsing the response body and trying to deserialize from a corrupted stream.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param con the HttpURLConnection to validate
	 * @throws IOException if validation failed
	 * @see java.net.HttpURLConnection#getResponseCode()
	 */
	/**
	 * 验证{@link  HttpURLConnection}对象中包含的给定响应，如果它不对应于成功的HTTP响应，则抛出异常。 
	 *  <p>默认实现拒绝2xx以后的任何HTTP状态代码，以避免解析响应正文并尝试从损坏的流中反序列化。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param 如果验证失败，则通过HttpURLConnection验证
	 * @throws  IOException 
	 * @see  java.net.HttpURLConnection＃getResponseCode（）
	 */
	protected void validateResponse(HttpInvokerClientConfiguration config, HttpURLConnection con)
			throws IOException {

		if (con.getResponseCode() >= 300) {
			throw new IOException(
					"Did not receive successful HTTP response: status code = " + con.getResponseCode() +
					", status message = [" + con.getResponseMessage() + "]");
		}
	}

	/**
	 * Extract the response body from the given executed remote invocation
	 * request.
	 * <p>The default implementation simply reads the serialized invocation
	 * from the HttpURLConnection's InputStream. If the response is recognized
	 * as GZIP response, the InputStream will get wrapped in a GZIPInputStream.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param con the HttpURLConnection to read the response body from
	 * @return an InputStream for the response body
	 * @throws IOException if thrown by I/O methods
	 * @see #isGzipResponse
	 * @see java.util.zip.GZIPInputStream
	 * @see java.net.HttpURLConnection#getInputStream()
	 * @see java.net.HttpURLConnection#getHeaderField(int)
	 * @see java.net.HttpURLConnection#getHeaderFieldKey(int)
	 */
	/**
	 * 从给定的已执行远程调用请求中提取响应主体。 
	 *  <p>默认实现只是从HttpURLConnection的InputStream读取序列化的调用。 
	 * 如果该响应被识别为GZIP响应，则InputStream将被包装在GZIPInputStream中。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param 通过HttpURLConnection从
	 * @return 的响应主体的InputStream读取响应主体
	 * @throws  IOException（如果由I / O方法抛出）
	 * @see  #isGzipResponse 
	 * @see  java.util.zip.GZIPInputStream 
	 * @see  java.net.HttpURLConnection＃getInputStream（）
	 * @see  java.net.HttpURLConnection＃getHeaderField（int）
	 * @see  java.net .HttpURLConnection＃getHeaderFieldKey（int）
	 */
	protected InputStream readResponseBody(HttpInvokerClientConfiguration config, HttpURLConnection con)
			throws IOException {

		if (isGzipResponse(con)) {
			// GZIP response found - need to unzip.
			return new GZIPInputStream(con.getInputStream());
		}
		else {
			// Plain response found.
			return con.getInputStream();
		}
	}

	/**
	 * Determine whether the given response is a GZIP response.
	 * <p>Default implementation checks whether the HTTP "Content-Encoding"
	 * header contains "gzip" (in any casing).
	 * @param con the HttpURLConnection to check
	 */
	/**
	 * 确定给定的响应是否是GZIP响应。 
	 *  <p>默认实现检查HTTP"Content-Encoding"标头是否包含"gzip"（在任何情况下）。 
	 *  
	 * @param 配置HttpURLConnection进行检查
	 */
	protected boolean isGzipResponse(HttpURLConnection con) {
		String encodingHeader = con.getHeaderField(HTTP_HEADER_CONTENT_ENCODING);
		return (encodingHeader != null && encodingHeader.toLowerCase().contains(ENCODING_GZIP));
	}

}
