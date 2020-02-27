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
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.util.Assert;

/**
 * {@link org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor} implementation that uses
 * <a href="https://hc.apache.org/httpcomponents-client-ga/httpclient/">Apache HttpComponents HttpClient</a>
 * to execute POST requests.
 *
 * <p>Allows to use a pre-configured {@link org.apache.http.client.HttpClient}
 * instance, potentially with authentication, HTTP connection pooling, etc.
 * Also designed for easy subclassing, providing specific template methods.
 *
 * <p>As of Spring 4.1, this request executor requires Apache HttpComponents 4.3 or higher.
 *
 * @author Juergen Hoeller
 * @author Stephane Nicoll
 * @since 3.1
 * @see org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor
 */
/**
 * 使用<a href="https://hc.apache.org/httpcomponents-client-ga/httpclient/"> Apache HttpComponents HttpClient </a>的{@link  org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor}实现执行POST请求。 
 *  <p>允许使用预先配置的{@link  org.apache.http.client.HttpClient}实例，并可能带有身份验证，HTTP连接池等。 
 * 也为易于子类化而设计，提供了特定的模板方法。 
 *  <p>从Spring 4.1开始，此请求执行程序需要Apache HttpComponents 4.3或更高版本。 
 *  @author  Juergen Hoeller @author  Stephane Nicoll @始于3.1 
 * @see  org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor
 */
public class HttpComponentsHttpInvokerRequestExecutor extends AbstractHttpInvokerRequestExecutor {

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;

	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);


	private HttpClient httpClient;

	@Nullable
	private RequestConfig requestConfig;


	/**
	 * Create a new instance of the HttpComponentsHttpInvokerRequestExecutor with a default
	 * {@link HttpClient} that uses a default {@code org.apache.http.impl.conn.PoolingClientConnectionManager}.
	 */
	/**
	 * 使用默认的{@link  HttpClient}使用默认的{@code  org.apache.http.impl.conn.PoolingClientConnectionManager}创建HttpComponentsHttpInvokerRequestExecutor的新实例。 
	 * 
	 */
	public HttpComponentsHttpInvokerRequestExecutor() {
		this(createDefaultHttpClient(), RequestConfig.custom()
				.setSocketTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build());
	}

	/**
	 * Create a new instance of the HttpComponentsClientHttpRequestFactory
	 * with the given {@link HttpClient} instance.
	 * @param httpClient the HttpClient instance to use for this request executor
	 */
	/**
	 * 使用给定的{@link  HttpClient}实例创建HttpComponentsClientHttpRequestFactory的新实例。 
	 *  
	 * @param  httpClient用于此请求执行程序的HttpClient实例
	 */
	public HttpComponentsHttpInvokerRequestExecutor(HttpClient httpClient) {
		this(httpClient, null);
	}

	private HttpComponentsHttpInvokerRequestExecutor(HttpClient httpClient, @Nullable RequestConfig requestConfig) {
		this.httpClient = httpClient;
		this.requestConfig = requestConfig;
	}


	private static HttpClient createDefaultHttpClient() {
		Registry<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(schemeRegistry);
		connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

		return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	}


	/**
	 * Set the {@link HttpClient} instance to use for this request executor.
	 */
	/**
	 * 设置{@link  HttpClient}实例以用于此请求执行程序。 
	 * 
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * Return the {@link HttpClient} instance that this request executor uses.
	 */
	/**
	 * 返回此请求执行程序使用的{@link  HttpClient}实例。 
	 * 
	 */
	public HttpClient getHttpClient() {
		return this.httpClient;
	}

	/**
	 * Set the connection timeout for the underlying HttpClient.
	 * A timeout value of 0 specifies an infinite timeout.
	 * <p>Additional properties can be configured by specifying a
	 * {@link RequestConfig} instance on a custom {@link HttpClient}.
	 * @param timeout the timeout value in milliseconds
	 * @see RequestConfig#getConnectTimeout()
	 */
	/**
	 * 设置基础HttpClient的连接超时。 
	 * 超时值为0指定无限超时。 
	 * 可以通过在自定义{@link  HttpClient}上指定一个{@link  RequestConfig}实例来配置<p>其他属性。 
	 *  
	 * @param 超时以毫秒为单位的超时值
	 * @see  RequestConfig＃getConnectTimeout（）
	 */
	public void setConnectTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = cloneRequestConfig().setConnectTimeout(timeout).build();
	}

	/**
	 * Set the timeout in milliseconds used when requesting a connection from the connection
	 * manager using the underlying HttpClient.
	 * A timeout value of 0 specifies an infinite timeout.
	 * <p>Additional properties can be configured by specifying a
	 * {@link RequestConfig} instance on a custom {@link HttpClient}.
	 * @param connectionRequestTimeout the timeout value to request a connection in milliseconds
	 * @see RequestConfig#getConnectionRequestTimeout()
	 */
	/**
	 * 设置使用底层HttpClient向连接管理器请求连接时所用的超时（以毫秒为单位）。 
	 * 超时值为0指定无限超时。 
	 * 可以通过在自定义{@link  HttpClient}上指定一个{@link  RequestConfig}实例来配置<p>其他属性。 
	 *  
	 * @param  connectionRequestTimeout请求连接的超时值（以毫秒为单位）
	 * @see  RequestConfig＃getConnectionRequestTimeout（）
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.requestConfig = cloneRequestConfig().setConnectionRequestTimeout(connectionRequestTimeout).build();
	}

	/**
	 * Set the socket read timeout for the underlying HttpClient.
	 * A timeout value of 0 specifies an infinite timeout.
	 * <p>Additional properties can be configured by specifying a
	 * {@link RequestConfig} instance on a custom {@link HttpClient}.
	 * @param timeout the timeout value in milliseconds
	 * @see #DEFAULT_READ_TIMEOUT_MILLISECONDS
	 * @see RequestConfig#getSocketTimeout()
	 */
	/**
	 * 设置基础HttpClient的套接字读取超时。 
	 * 超时值为0指定无限超时。 
	 * 可以通过在自定义{@link  HttpClient}上指定一个{@link  RequestConfig}实例来配置<p>其他属性。 
	 *  
	 * @param 超时以毫秒为单位的超时值
	 * @see  #DEFAULT_READ_TIMEOUT_MILLISECONDS 
	 * @see  RequestConfig＃getSocketTimeout（）
	 */
	public void setReadTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = cloneRequestConfig().setSocketTimeout(timeout).build();
	}

	private RequestConfig.Builder cloneRequestConfig() {
		return (this.requestConfig != null ? RequestConfig.copy(this.requestConfig) : RequestConfig.custom());
	}


	/**
	 * Execute the given request through the HttpClient.
	 * <p>This method implements the basic processing workflow:
	 * The actual work happens in this class's template methods.
	 * @see #createHttpPost
	 * @see #setRequestBody
	 * @see #executeHttpPost
	 * @see #validateResponse
	 * @see #getResponseBody
	 */
	/**
	 * 通过HttpClient执行给定的请求。 
	 *  <p>此方法实现基本的处理工作流程：实际工作发生在此类的模板方法中。 
	 *  
	 * @see  #createHttpPost 
	 * @see  #setRequestBody 
	 * @see  #executeHttpPost 
	 * @see  #validateResponse 
	 * @see  #getResponseBody
	 */
	@Override
	protected RemoteInvocationResult doExecuteRequest(
			HttpInvokerClientConfiguration config, ByteArrayOutputStream baos)
			throws IOException, ClassNotFoundException {

		HttpPost postMethod = createHttpPost(config);
		setRequestBody(config, postMethod, baos);
		try {
			HttpResponse response = executeHttpPost(config, getHttpClient(), postMethod);
			validateResponse(config, response);
			InputStream responseBody = getResponseBody(config, response);
			return readRemoteInvocationResult(responseBody, config.getCodebaseUrl());
		}
		finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * Create an HttpPost for the given configuration.
	 * <p>The default implementation creates a standard HttpPost with
	 * "application/x-java-serialized-object" as "Content-Type" header.
	 * @param config the HTTP invoker configuration that specifies the
	 * target service
	 * @return the HttpPost instance
	 * @throws java.io.IOException if thrown by I/O methods
	 */
	/**
	 * 为给定的配置创建一个HttpPost。 
	 *  <p>默认实现使用"application / x-java-serialized-object"作为"Content-Type"标头创建标准的HttpPost。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @return  HttpPost实例
	 * @throws  java.io.IOException（如果由I / O方法抛出）
	 */
	protected HttpPost createHttpPost(HttpInvokerClientConfiguration config) throws IOException {
		HttpPost httpPost = new HttpPost(config.getServiceUrl());

		RequestConfig requestConfig = createRequestConfig(config);
		if (requestConfig != null) {
			httpPost.setConfig(requestConfig);
		}

		LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
		if (localeContext != null) {
			Locale locale = localeContext.getLocale();
			if (locale != null) {
				httpPost.addHeader(HTTP_HEADER_ACCEPT_LANGUAGE, locale.toLanguageTag());
			}
		}

		if (isAcceptGzipEncoding()) {
			httpPost.addHeader(HTTP_HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
		}

		return httpPost;
	}

	/**
	 * Create a {@link RequestConfig} for the given configuration. Can return {@code null}
	 * to indicate that no custom request config should be set and the defaults of the
	 * {@link HttpClient} should be used.
	 * <p>The default implementation tries to merge the defaults of the client with the
	 * local customizations of the instance, if any.
	 * @param config the HTTP invoker configuration that specifies the
	 * target service
	 * @return the RequestConfig to use
	 */
	/**
	 * 为给定的配置创建一个{@link  RequestConfig}。 
	 * 可以返回{@code  null}表示不应设置自定义请求配置，而应使用{@link  HttpClient}的默认值。 
	 *  <p>默认实现会尝试将客户端的默认值与实例的本地自定义项合并（如果有）。 
	 *  
	 * @param  config配置HTTP调用程序配置，该配置指定目标服务
	 * @return 要使用的RequestConfig
	 */
	@Nullable
	protected RequestConfig createRequestConfig(HttpInvokerClientConfiguration config) {
		HttpClient client = getHttpClient();
		if (client instanceof Configurable) {
			RequestConfig clientRequestConfig = ((Configurable) client).getConfig();
			return mergeRequestConfig(clientRequestConfig);
		}
		return this.requestConfig;
	}

	private RequestConfig mergeRequestConfig(RequestConfig defaultRequestConfig) {
		if (this.requestConfig == null) {  // nothing to merge
			return defaultRequestConfig;
		}

		RequestConfig.Builder builder = RequestConfig.copy(defaultRequestConfig);
		int connectTimeout = this.requestConfig.getConnectTimeout();
		if (connectTimeout >= 0) {
			builder.setConnectTimeout(connectTimeout);
		}
		int connectionRequestTimeout = this.requestConfig.getConnectionRequestTimeout();
		if (connectionRequestTimeout >= 0) {
			builder.setConnectionRequestTimeout(connectionRequestTimeout);
		}
		int socketTimeout = this.requestConfig.getSocketTimeout();
		if (socketTimeout >= 0) {
			builder.setSocketTimeout(socketTimeout);
		}
		return builder.build();
	}

	/**
	 * Set the given serialized remote invocation as request body.
	 * <p>The default implementation simply sets the serialized invocation as the
	 * HttpPost's request body. This can be overridden, for example, to write a
	 * specific encoding and to potentially set appropriate HTTP request headers.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param httpPost the HttpPost to set the request body on
	 * @param baos the ByteArrayOutputStream that contains the serialized
	 * RemoteInvocation object
	 * @throws java.io.IOException if thrown by I/O methods
	 */
	/**
	 * 将给定的序列化远程调用设置为请求正文。 
	 *  <p>默认实现只是将序列化的调用设置为HttpPost的请求正文。 
	 * 例如，可以重写此方法以编写特定的编码并可能设置适当的HTTP请求标头。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param  httpPost HttpPost以在
	 * @param 上设置请求正文，如果ByteArrayOutputStream包含序列化的RemoteInvocation对象
	 * @throws  java.io.IOException，如果由I / O方法抛出
	 */
	protected void setRequestBody(
			HttpInvokerClientConfiguration config, HttpPost httpPost, ByteArrayOutputStream baos)
			throws IOException {

		ByteArrayEntity entity = new ByteArrayEntity(baos.toByteArray());
		entity.setContentType(getContentType());
		httpPost.setEntity(entity);
	}

	/**
	 * Execute the given HttpPost instance.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param httpClient the HttpClient to execute on
	 * @param httpPost the HttpPost to execute
	 * @return the resulting HttpResponse
	 * @throws java.io.IOException if thrown by I/O methods
	 */
	/**
	 * 执行给定的HttpPost实例。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param  httpClient要在
	 * @param 上执行的HttpClient httpPost HttpPost来执行
	 * @return 产生的HttpResponse 
	 * @throws  java.io.IOException由I / O方法抛出
	 */
	protected HttpResponse executeHttpPost(
			HttpInvokerClientConfiguration config, HttpClient httpClient, HttpPost httpPost)
			throws IOException {

		return httpClient.execute(httpPost);
	}

	/**
	 * Validate the given response as contained in the HttpPost object,
	 * throwing an exception if it does not correspond to a successful HTTP response.
	 * <p>Default implementation rejects any HTTP status code beyond 2xx, to avoid
	 * parsing the response body and trying to deserialize from a corrupted stream.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param response the resulting HttpResponse to validate
	 * @throws java.io.IOException if validation failed
	 */
	/**
	 * 验证给定的响应是否包含在HttpPost对象中，如果它不对应于成功的HTTP响应，则抛出异常。 
	 *  <p>默认实现拒绝2xx以后的任何HTTP状态代码，以避免解析响应正文并尝试从损坏的流中反序列化。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param 如果验证失败，则响应结果HttpResponse以验证
	 * @throws  java.io.IOException
	 */
	protected void validateResponse(HttpInvokerClientConfiguration config, HttpResponse response)
			throws IOException {

		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() >= 300) {
			throw new NoHttpResponseException(
					"Did not receive successful HTTP response: status code = " + status.getStatusCode() +
					", status message = [" + status.getReasonPhrase() + "]");
		}
	}

	/**
	 * Extract the response body from the given executed remote invocation request.
	 * <p>The default implementation simply fetches the HttpPost's response body stream.
	 * If the response is recognized as GZIP response, the InputStream will get wrapped
	 * in a GZIPInputStream.
	 * @param config the HTTP invoker configuration that specifies the target service
	 * @param httpResponse the resulting HttpResponse to read the response body from
	 * @return an InputStream for the response body
	 * @throws java.io.IOException if thrown by I/O methods
	 * @see #isGzipResponse
	 * @see java.util.zip.GZIPInputStream
	 */
	/**
	 * 从给定的已执行远程调用请求中提取响应主体。 
	 *  <p>默认实现只是获取HttpPost的响应主体流。 
	 * 如果该响应被识别为GZIP响应，则InputStream将被包装在GZIPInputStream中。 
	 *  
	 * @param  config指定目标服务的HTTP调用程序配置
	 * @param  httpResponse生成的HttpResponse从
	 * @return 的InputStream读取响应主体
	 * @throws  java.io.IOException的响应主体（如果抛出） I / O方法
	 * @see  #isGzipResponse 
	 * @see  java.util.zip.GZIPInputStream
	 */
	protected InputStream getResponseBody(HttpInvokerClientConfiguration config, HttpResponse httpResponse)
			throws IOException {

		if (isGzipResponse(httpResponse)) {
			return new GZIPInputStream(httpResponse.getEntity().getContent());
		}
		else {
			return httpResponse.getEntity().getContent();
		}
	}

	/**
	 * Determine whether the given response indicates a GZIP response.
	 * <p>The default implementation checks whether the HTTP "Content-Encoding"
	 * header contains "gzip" (in any casing).
	 * @param httpResponse the resulting HttpResponse to check
	 * @return whether the given response indicates a GZIP response
	 */
	/**
	 * 确定给定的响应是否指示GZIP响应。 
	 *  <p>默认实现检查HTTP"Content-Encoding"标头是否包含"gzip"（在任何情况下）。 
	 *  
	 * @param  httpResponse生成的HttpResponse以检查
	 * @return 给定的响应是否表示GZIP响应
	 */
	protected boolean isGzipResponse(HttpResponse httpResponse) {
		Header encodingHeader = httpResponse.getFirstHeader(HTTP_HEADER_CONTENT_ENCODING);
		return (encodingHeader != null && encodingHeader.getValue() != null &&
				encodingHeader.getValue().toLowerCase().contains(ENCODING_GZIP));
	}

}
