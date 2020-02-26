/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.test.web.client.match;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.hamcrest.Matcher;
import org.w3c.dom.Node;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.util.JsonExpectationsHelper;
import org.springframework.test.util.XmlExpectationsHelper;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * Factory for request content {@code RequestMatcher}'s. An instance of this
 * class is typically accessed via {@link MockRestRequestMatchers#content()}.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 */
/**
 * 请求内容{@code  RequestMatcher}的工厂。 
 * 通常通过{@link  MockRestRequestMatchers＃content（）}访问此类的实例。 
 *  @author  Rossen Stoyanchev @从3.2开始
 */
public class ContentRequestMatchers {

	private final XmlExpectationsHelper xmlHelper;

	private final JsonExpectationsHelper jsonHelper;


	/**
	 * Class constructor, not for direct instantiation.
	 * Use {@link MockRestRequestMatchers#content()}.
	 */
	/**
	 * 类构造函数，不用于直接实例化。 
	 * 使用{@link  MockRestRequestMatchers＃content（）}。 
	 * 
	 */
	protected ContentRequestMatchers() {
		this.xmlHelper = new XmlExpectationsHelper();
		this.jsonHelper = new JsonExpectationsHelper();
	}


	/**
	 * Assert the request content type as a String.
	 */
	/**
	 * 将请求内容类型声明为字符串。 
	 * 
	 */
	public RequestMatcher contentType(String expectedContentType) {
		return contentType(MediaType.parseMediaType(expectedContentType));
	}

	/**
	 * Assert the request content type as a {@link MediaType}.
	 */
	/**
	 * 将请求内容类型声明为{@link  MediaType}。 
	 * 
	 */
	public RequestMatcher contentType(MediaType expectedContentType) {
		return request -> {
			MediaType actualContentType = request.getHeaders().getContentType();
			assertTrue("Content type not set", actualContentType != null);
			assertEquals("Content type", expectedContentType, actualContentType);
		};
	}

	/**
	 * Assert the request content type is compatible with the given
	 * content type as defined by {@link MediaType#isCompatibleWith(MediaType)}.
	 */
	/**
	 * 声明请求内容类型与{@link  MediaType＃isCompatibleWith（MediaType）}定义的给定内容类型兼容。 
	 * 
	 */
	public RequestMatcher contentTypeCompatibleWith(String contentType) {
		return contentTypeCompatibleWith(MediaType.parseMediaType(contentType));
	}

	/**
	 * Assert the request content type is compatible with the given
	 * content type as defined by {@link MediaType#isCompatibleWith(MediaType)}.
	 */
	/**
	 * 声明请求内容类型与{@link  MediaType＃isCompatibleWith（MediaType）}定义的给定内容类型兼容。 
	 * 
	 */
	public RequestMatcher contentTypeCompatibleWith(MediaType contentType) {
		return request -> {
			MediaType actualContentType = request.getHeaders().getContentType();
			assertTrue("Content type not set", actualContentType != null);
			if (actualContentType != null) {
				assertTrue("Content type [" + actualContentType + "] is not compatible with [" + contentType + "]",
						actualContentType.isCompatibleWith(contentType));
			}
		};
	}

	/**
	 * Get the body of the request as a UTF-8 string and apply the given {@link Matcher}.
	 */
	/**
	 * 以UTF-8字符串的形式获取请求的正文，并应用给定的{@link  Matcher}。 
	 * 
	 */
	public RequestMatcher string(Matcher<? super String> matcher) {
		return request -> {
			MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
			assertThat("Request content", mockRequest.getBodyAsString(), matcher);
		};
	}

	/**
	 * Get the body of the request as a UTF-8 string and compare it to the given String.
	 */
	/**
	 * 以UTF-8字符串的形式获取请求的正文，并将其与给定的String进行比较。 
	 * 
	 */
	public RequestMatcher string(String expectedContent) {
		return request -> {
			MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
			assertEquals("Request content", expectedContent, mockRequest.getBodyAsString());
		};
	}

	/**
	 * Compare the body of the request to the given byte array.
	 */
	/**
	 * 将请求的内容与给定的字节数组进行比较。 
	 * 
	 */
	public RequestMatcher bytes(byte[] expectedContent) {
		return request -> {
			MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
			assertEquals("Request content", expectedContent, mockRequest.getBodyAsBytes());
		};
	}

	/**
	 * Parse the body as form data and compare to the given {@code MultiValueMap}.
	 * @since 4.3
	 */
	/**
	 * 将正文解析为表单数据，然后与给定的{@code  MultiValueMap}比较。 
	 *  @4.3起
	 */
	public RequestMatcher formData(MultiValueMap<String, String> expectedContent) {
		return request -> {
			HttpInputMessage inputMessage = new HttpInputMessage() {
				@Override
				public InputStream getBody() throws IOException {
					MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
					return new ByteArrayInputStream(mockRequest.getBodyAsBytes());
				}
				@Override
				public HttpHeaders getHeaders() {
					return request.getHeaders();
				}
			};
			FormHttpMessageConverter converter = new FormHttpMessageConverter();
			assertEquals("Request content", expectedContent, converter.read(null, inputMessage));
		};
	}

	/**
	 * Parse the request body and the given String as XML and assert that the
	 * two are "similar" - i.e. they contain the same elements and attributes
	 * regardless of order.
	 * <p>Use of this matcher assumes the
	 * <a href="http://xmlunit.sourceforge.net/">XMLUnit</a> library is available.
	 * @param expectedXmlContent the expected XML content
	 */
	/**
	 * 将请求主体和给定的String解析为XML，并断言两者是"相似的"-即它们包含相同的元素和属性，而不管顺序如何。 
	 *  <p>使用此匹配器假定<a href="http://xmlunit.sourceforge.net/"> XMLUnit </a>库可用。 
	 *  
	 * @param  ExpectedXmlContent期望的XML内容
	 */
	public RequestMatcher xml(String expectedXmlContent) {
		return new AbstractXmlRequestMatcher() {
			@Override
			protected void matchInternal(MockClientHttpRequest request) throws Exception {
				xmlHelper.assertXmlEqual(expectedXmlContent, request.getBodyAsString());
			}
		};
	}

	/**
	 * Parse the request content as {@link Node} and apply the given {@link Matcher}.
	 */
	/**
	 * 将请求内容解析为{@link  Node}并应用给定的{@link  Matcher}。 
	 * 
	 */
	public RequestMatcher node(Matcher<? super Node> matcher) {
		return new AbstractXmlRequestMatcher() {
			@Override
			protected void matchInternal(MockClientHttpRequest request) throws Exception {
				xmlHelper.assertNode(request.getBodyAsString(), matcher);
			}
		};
	}

	/**
	 * Parse the request content as {@link DOMSource} and apply the given {@link Matcher}.
	 * @see <a href="https://code.google.com/p/xml-matchers/">https://code.google.com/p/xml-matchers/</a>
	 */
	/**
	 * 将请求内容解析为{@link  DOMSource}并应用给定的{@link  Matcher}。 
	 *  
	 * @see  <a href="https://code.google.com/p/xml-matchers/"> https://code.google.com/p/xml-matchers/ </a>
	 */
	public RequestMatcher source(Matcher<? super Source> matcher) {
		return new AbstractXmlRequestMatcher() {
			@Override
			protected void matchInternal(MockClientHttpRequest request) throws Exception {
				xmlHelper.assertSource(request.getBodyAsString(), matcher);
			}
		};
	}

	/**
	 * Parse the expected and actual strings as JSON and assert the two
	 * are "similar" - i.e. they contain the same attribute-value pairs
	 * regardless of formatting with a lenient checking (extensible, and non-strict array
	 * ordering).
	 * <p>Use of this matcher requires the <a
	 * href="https://jsonassert.skyscreamer.org/">JSONassert</a> library.
	 * @param expectedJsonContent the expected JSON content
	 * @since 5.0.5
	 */
	/**
	 * 将期望的字符串和实际的字符串解析为JSON，并断言这两个字符串是"相似的"-即它们包含相同的属性值对，而不管采用宽松的检查格式（可扩展且非严格的数组排序）。 
	 *  <p>使用此匹配器需要<a href="https://jsonassert.skyscreamer.org/"> JSONassert </a>库。 
	 *  
	 * @param  ExpectedJsonContent从5.0.5开始的预期JSON内容
	 */
	public RequestMatcher json(String expectedJsonContent) {
		return json(expectedJsonContent, false);
	}

	/**
	 * Parse the request body and the given string as JSON and assert the two
	 * are "similar" - i.e. they contain the same attribute-value pairs
	 * regardless of formatting.
	 * <p>Can compare in two modes, depending on {@code strict} parameter value:
	 * <ul>
	 * <li>{@code true}: strict checking. Not extensible, and strict array ordering.</li>
	 * <li>{@code false}: lenient checking. Extensible, and non-strict array ordering.</li>
	 * </ul>
	 * <p>Use of this matcher requires the <a
	 * href="https://jsonassert.skyscreamer.org/">JSONassert</a> library.
	 * @param expectedJsonContent the expected JSON content
	 * @param strict enables strict checking
	 * @since 5.0.5
	 */
	/**
	 * 将请求正文和给定的字符串解析为JSON，并断言两者是"相似的"-即，无论格式如何，它们都包含相同的属性值对。 
	 *  <p>可以根据{@code  strict}参数值在两种模式下进行比较：<ul> <li> {<@code> true}：严格检查。 
	 * 不可扩展，且数组排序严格。 
	 * </ li> <li> {<@code> false}：宽松的检查。 
	 * 可扩展的非严格数组排序。 
	 * </ li> </ ul> <p>使用此匹配器需要<a href="https://jsonassert.skyscreamer.org/"> JSONassert </a>库。 
	 *  
	 * @param  ExpectedJsonContent期望的JSON内容
	 * @param  strict启用严格检查@since 5.0.5
	 */
	public RequestMatcher json(String expectedJsonContent, boolean strict) {
		return request -> {
			try {
				MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
				this.jsonHelper.assertJsonEqual(expectedJsonContent, mockRequest.getBodyAsString(), strict);
			}
			catch (Exception ex) {
				throw new AssertionError("Failed to parse expected or actual JSON request content", ex);
			}
		};
	}


	/**
	 * Abstract base class for XML {@link RequestMatcher}'s.
	 */
	/**
	 * XML {@link  RequestMatcher}的抽象基类。 
	 * 
	 */
	private abstract static class AbstractXmlRequestMatcher implements RequestMatcher {

		@Override
		public final void match(ClientHttpRequest request) throws IOException, AssertionError {
			try {
				MockClientHttpRequest mockRequest = (MockClientHttpRequest) request;
				matchInternal(mockRequest);
			}
			catch (Exception ex) {
				throw new AssertionError("Failed to parse expected or actual XML request content", ex);
			}
		}

		protected abstract void matchInternal(MockClientHttpRequest request) throws Exception;
	}

}
