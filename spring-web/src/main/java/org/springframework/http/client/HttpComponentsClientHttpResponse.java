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

package org.springframework.http.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

/**
 * {@link ClientHttpResponse} implementation based on
 * Apache HttpComponents HttpClient.
 *
 * <p>Created via the {@link HttpComponentsClientHttpRequest}.
 *
 * @author Oleg Kalnichevski
 * @author Arjen Poutsma
 * @since 3.1
 * @see HttpComponentsClientHttpRequest#execute()
 */
/**
 * 基于Apache HttpComponents HttpClient的{@link  ClientHttpResponse}实现。 
 *  <p>通过{@link  HttpComponentsClientHttpRequest}创建。 
 *  @author  Oleg Kalnichevski @author  Arjen Poutsma @自3.1起
 * @see  HttpComponentsClientHttpRequest＃execute（）
 */
final class HttpComponentsClientHttpResponse extends AbstractClientHttpResponse {

	private final HttpResponse httpResponse;

	@Nullable
	private HttpHeaders headers;


	HttpComponentsClientHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}


	@Override
	public int getRawStatusCode() throws IOException {
		return this.httpResponse.getStatusLine().getStatusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return this.httpResponse.getStatusLine().getReasonPhrase();
	}

	@Override
	public HttpHeaders getHeaders() {
		if (this.headers == null) {
			this.headers = new HttpHeaders();
			for (Header header : this.httpResponse.getAllHeaders()) {
				this.headers.add(header.getName(), header.getValue());
			}
		}
		return this.headers;
	}

	@Override
	public InputStream getBody() throws IOException {
		HttpEntity entity = this.httpResponse.getEntity();
		return (entity != null ? entity.getContent() : StreamUtils.emptyInput());
	}

	@Override
	public void close() {
		// Release underlying connection back to the connection manager
		try {
			try {
				// Attempt to keep connection alive by consuming its remaining content
				EntityUtils.consume(this.httpResponse.getEntity());
			}
			finally {
				if (this.httpResponse instanceof Closeable) {
					((Closeable) this.httpResponse).close();
				}
			}
		}
		catch (IOException ex) {
			// Ignore exception on close...
		}
	}

}
