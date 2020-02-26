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

package org.springframework.mock.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * Mock implementation of the {@link javax.servlet.http.HttpServletResponse} interface.
 *
 * <p>As of Spring Framework 5.0, this set of mocks is designed on a Servlet 4.0 baseline.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Brian Clozel
 * @author Vedran Pavic
 * @author Sebastien Deleuze
 * @author Sam Brannen
 * @since 1.0.2
 */
/**
 * {@link  javax.servlet.http.HttpServletResponse}接口的模拟实现。 
 *  <p>从Spring Framework 5.0开始，这组模拟是在Servlet 4.0基线上设计的。 
 *  @author  Juergen Hoeller @author  Rod Johnson @author  Brian Clozel @author  Vedran Pavic @author  Sebastien Deleuze @author  Sam Brannen @自1.0.2起
 */
public class MockHttpServletResponse implements HttpServletResponse {

	private static final String CHARSET_PREFIX = "charset=";

	private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");


	//---------------------------------------------------------------------
	// ServletResponse properties
	//---------------------------------------------------------------------

	private boolean outputStreamAccessAllowed = true;

	private boolean writerAccessAllowed = true;

	@Nullable
	private String characterEncoding = WebUtils.DEFAULT_CHARACTER_ENCODING;

	private boolean charset = false;

	private final ByteArrayOutputStream content = new ByteArrayOutputStream(1024);

	private final ServletOutputStream outputStream = new ResponseServletOutputStream(this.content);

	@Nullable
	private PrintWriter writer;

	private long contentLength = 0;

	@Nullable
	private String contentType;

	private int bufferSize = 4096;

	private boolean committed;

	private Locale locale = Locale.getDefault();


	//---------------------------------------------------------------------
	// HttpServletResponse properties
	//---------------------------------------------------------------------

	private final List<Cookie> cookies = new ArrayList<>();

	private final Map<String, HeaderValueHolder> headers = new LinkedCaseInsensitiveMap<>();

	private int status = HttpServletResponse.SC_OK;

	@Nullable
	private String errorMessage;

	@Nullable
	private String forwardedUrl;

	private final List<String> includedUrls = new ArrayList<>();


	//---------------------------------------------------------------------
	// ServletResponse interface
	//---------------------------------------------------------------------

	/**
	 * Set whether {@link #getOutputStream()} access is allowed.
	 * <p>Default is {@code true}.
	 */
	/**
	 * 设置是否允许{@link  #getOutputStream（）}访问。 
	 *  <p>默认值为{@code  true}。 
	 * 
	 */
	public void setOutputStreamAccessAllowed(boolean outputStreamAccessAllowed) {
		this.outputStreamAccessAllowed = outputStreamAccessAllowed;
	}

	/**
	 * Return whether {@link #getOutputStream()} access is allowed.
	 */
	/**
	 * 返回是否允许{@link  #getOutputStream（）}访问。 
	 * 
	 */
	public boolean isOutputStreamAccessAllowed() {
		return this.outputStreamAccessAllowed;
	}

	/**
	 * Set whether {@link #getWriter()} access is allowed.
	 * <p>Default is {@code true}.
	 */
	/**
	 * 设置是否允许{@link  #getWriter（）}访问。 
	 *  <p>默认值为{@code  true}。 
	 * 
	 */
	public void setWriterAccessAllowed(boolean writerAccessAllowed) {
		this.writerAccessAllowed = writerAccessAllowed;
	}

	/**
	 * Return whether {@link #getOutputStream()} access is allowed.
	 */
	/**
	 * 返回是否允许{@link  #getOutputStream（）}访问。 
	 * 
	 */
	public boolean isWriterAccessAllowed() {
		return this.writerAccessAllowed;
	}

	/**
	 * Return whether the character encoding has been set.
	 * <p>If {@code false}, {@link #getCharacterEncoding()} will return a default encoding value.
	 */
	/**
	 * 返回是否设置了字符编码。 
	 *  <p>如果{@code  false}，则{@link  #getCharacterEncoding（）}将返回默认编码值。 
	 * 
	 */
	public boolean isCharset() {
		return this.charset;
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
		this.charset = true;
		updateContentTypeHeader();
	}

	private void updateContentTypeHeader() {
		if (this.contentType != null) {
			String value = this.contentType;
			if (this.charset && !this.contentType.toLowerCase().contains(CHARSET_PREFIX)) {
				value = value + ';' + CHARSET_PREFIX + this.characterEncoding;
			}
			doAddHeaderValue(HttpHeaders.CONTENT_TYPE, value, true);
		}
	}

	@Override
	@Nullable
	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

	@Override
	public ServletOutputStream getOutputStream() {
		Assert.state(this.outputStreamAccessAllowed, "OutputStream access not allowed");
		return this.outputStream;
	}

	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException {
		Assert.state(this.writerAccessAllowed, "Writer access not allowed");
		if (this.writer == null) {
			Writer targetWriter = (this.characterEncoding != null ?
					new OutputStreamWriter(this.content, this.characterEncoding) :
					new OutputStreamWriter(this.content));
			this.writer = new ResponsePrintWriter(targetWriter);
		}
		return this.writer;
	}

	public byte[] getContentAsByteArray() {
		return this.content.toByteArray();
	}

	/**
	 * Get the content of the response body as a {@code String}, using the charset
	 * specified for the response by the application, either through
	 * {@link HttpServletResponse} methods or through a charset parameter on the
	 * {@code Content-Type}.
	 * @return the content as a {@code String}
	 * @throws UnsupportedEncodingException if the character encoding is not supported
	 * @see #getContentAsString(Charset)
	 */
	/**
	 * 使用应用程序为响应指定的字符集，通过{@link  HttpServletResponse}方法或通过{@code 上的charset参数，以{@code 字符串}的形式获取响应主体的内容。 
	 * 内容类型}。 
	 *  
	 * @return 作为{@code 字符串}的内容
	 * @throws  UnsupportedEncodingException如果不支持字符编码
	 * @see  #getContentAsString（Charset）
	 */
	public String getContentAsString() throws UnsupportedEncodingException {
		return (this.characterEncoding != null ?
				this.content.toString(this.characterEncoding) : this.content.toString());
	}

	/**
	 * Get the content of the response body as a {@code String}, using the provided
	 * {@code fallbackCharset} if no charset has been explicitly defined and otherwise
	 * using the charset specified for the response by the application, either
	 * through {@link HttpServletResponse} methods or through a charset parameter on the
	 * {@code Content-Type}.
	 * @return the content as a {@code String}
	 * @throws UnsupportedEncodingException if the character encoding is not supported
	 * @since 5.2
	 * @see #getContentAsString()
	 */
	/**
	 * 如果未明确定义任何字符集，则使用提供的{@code  fallbackCharset}来获取响应正文的内容为{@code  String}； 
	 * 否则，请通过{ @link  HttpServletResponse}方法或通过{@code  Content-Type}上的字符集参数。 
	 *  
	 * @return 作为{@code 字符串}的内容
	 * @throws  UnsupportedEncodingException如果自5.2起不支持字符编码
	 * @see  #getContentAsString（）
	 */
	public String getContentAsString(Charset fallbackCharset) throws UnsupportedEncodingException {
		return (isCharset() && this.characterEncoding != null ?
				this.content.toString(this.characterEncoding) :
				this.content.toString(fallbackCharset.name()));
	}

	@Override
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
		doAddHeaderValue(HttpHeaders.CONTENT_LENGTH, contentLength, true);
	}

	public int getContentLength() {
		return (int) this.contentLength;
	}

	@Override
	public void setContentLengthLong(long contentLength) {
		this.contentLength = contentLength;
		doAddHeaderValue(HttpHeaders.CONTENT_LENGTH, contentLength, true);
	}

	public long getContentLengthLong() {
		return this.contentLength;
	}

	@Override
	public void setContentType(@Nullable String contentType) {
		this.contentType = contentType;
		if (contentType != null) {
			try {
				MediaType mediaType = MediaType.parseMediaType(contentType);
				if (mediaType.getCharset() != null) {
					this.characterEncoding = mediaType.getCharset().name();
					this.charset = true;
				}
			}
			catch (Exception ex) {
				// Try to get charset value anyway
				int charsetIndex = contentType.toLowerCase().indexOf(CHARSET_PREFIX);
				if (charsetIndex != -1) {
					this.characterEncoding = contentType.substring(charsetIndex + CHARSET_PREFIX.length());
					this.charset = true;
				}
			}
			updateContentTypeHeader();
		}
	}

	@Override
	@Nullable
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	@Override
	public int getBufferSize() {
		return this.bufferSize;
	}

	@Override
	public void flushBuffer() {
		setCommitted(true);
	}

	@Override
	public void resetBuffer() {
		Assert.state(!isCommitted(), "Cannot reset buffer - response is already committed");
		this.content.reset();
	}

	private void setCommittedIfBufferSizeExceeded() {
		int bufSize = getBufferSize();
		if (bufSize > 0 && this.content.size() > bufSize) {
			setCommitted(true);
		}
	}

	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

	@Override
	public boolean isCommitted() {
		return this.committed;
	}

	@Override
	public void reset() {
		resetBuffer();
		this.characterEncoding = null;
		this.contentLength = 0;
		this.contentType = null;
		this.locale = Locale.getDefault();
		this.cookies.clear();
		this.headers.clear();
		this.status = HttpServletResponse.SC_OK;
		this.errorMessage = null;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
		doAddHeaderValue(HttpHeaders.CONTENT_LANGUAGE, locale.toLanguageTag(), true);
	}

	@Override
	public Locale getLocale() {
		return this.locale;
	}


	//---------------------------------------------------------------------
	// HttpServletResponse interface
	//---------------------------------------------------------------------

	@Override
	public void addCookie(Cookie cookie) {
		Assert.notNull(cookie, "Cookie must not be null");
		this.cookies.add(cookie);
		doAddHeaderValue(HttpHeaders.SET_COOKIE, getCookieHeader(cookie), false);
	}

	private String getCookieHeader(Cookie cookie) {
		StringBuilder buf = new StringBuilder();
		buf.append(cookie.getName()).append('=').append(cookie.getValue() == null ? "" : cookie.getValue());
		if (StringUtils.hasText(cookie.getPath())) {
			buf.append("; Path=").append(cookie.getPath());
		}
		if (StringUtils.hasText(cookie.getDomain())) {
			buf.append("; Domain=").append(cookie.getDomain());
		}
		int maxAge = cookie.getMaxAge();
		if (maxAge >= 0) {
			buf.append("; Max-Age=").append(maxAge);
			buf.append("; Expires=");
			ZonedDateTime expires = (cookie instanceof MockCookie ? ((MockCookie) cookie).getExpires() : null);
			if (expires != null) {
				buf.append(expires.format(DateTimeFormatter.RFC_1123_DATE_TIME));
			}
			else {
				HttpHeaders headers = new HttpHeaders();
				headers.setExpires(maxAge > 0 ? System.currentTimeMillis() + 1000L * maxAge : 0);
				buf.append(headers.getFirst(HttpHeaders.EXPIRES));
			}
		}

		if (cookie.getSecure()) {
			buf.append("; Secure");
		}
		if (cookie.isHttpOnly()) {
			buf.append("; HttpOnly");
		}
		if (cookie instanceof MockCookie) {
			MockCookie mockCookie = (MockCookie) cookie;
			if (StringUtils.hasText(mockCookie.getSameSite())) {
				buf.append("; SameSite=").append(mockCookie.getSameSite());
			}
		}
		return buf.toString();
	}

	public Cookie[] getCookies() {
		return this.cookies.toArray(new Cookie[0]);
	}

	@Nullable
	public Cookie getCookie(String name) {
		Assert.notNull(name, "Cookie name must not be null");
		for (Cookie cookie : this.cookies) {
			if (name.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	@Override
	public boolean containsHeader(String name) {
		return (this.headers.get(name) != null);
	}

	/**
	 * Return the names of all specified headers as a Set of Strings.
	 * <p>As of Servlet 3.0, this method is also defined HttpServletResponse.
	 * @return the {@code Set} of header name {@code Strings}, or an empty {@code Set} if none
	 */
	/**
	 * 将所有指定的标头的名称作为一组字符串返回。 
	 *  <p>从Servlet 3.0开始，此方法也定义为HttpServletResponse。 
	 *  
	 * @return 标头名称{<@@code> Strings}的{@code  Set}，如果没有，则为空的{@code  Set}
	 */
	@Override
	public Collection<String> getHeaderNames() {
		return this.headers.keySet();
	}

	/**
	 * Return the primary value for the given header as a String, if any.
	 * Will return the first value in case of multiple values.
	 * <p>As of Servlet 3.0, this method is also defined in HttpServletResponse.
	 * As of Spring 3.1, it returns a stringified value for Servlet 3.0 compatibility.
	 * Consider using {@link #getHeaderValue(String)} for raw Object access.
	 * @param name the name of the header
	 * @return the associated header value, or {@code null} if none
	 */
	/**
	 * 如果有的话，以字符串形式返回给定标头的主要值。 
	 * 如果有多个值，将返回第一个值。 
	 *  <p>从Servlet 3.0开始，此方法也在HttpServletResponse中定义。 
	 * 从Spring 3.1开始，它返回一个与Servlet 3.0兼容的字符串化值。 
	 * 考虑使用{@link  #getHeaderValue（String）}进行原始对象访问。 
	 *  
	 * @param 命名标题的名称
	 * @return 关联的标题值，如果没有则为{@code  null}
	 */
	@Override
	@Nullable
	public String getHeader(String name) {
		HeaderValueHolder header = this.headers.get(name);
		return (header != null ? header.getStringValue() : null);
	}

	/**
	 * Return all values for the given header as a List of Strings.
	 * <p>As of Servlet 3.0, this method is also defined in HttpServletResponse.
	 * As of Spring 3.1, it returns a List of stringified values for Servlet 3.0 compatibility.
	 * Consider using {@link #getHeaderValues(String)} for raw Object access.
	 * @param name the name of the header
	 * @return the associated header values, or an empty List if none
	 */
	/**
	 * 以字符串列表形式返回给定标头的所有值。 
	 *  <p>从Servlet 3.0开始，此方法也在HttpServletResponse中定义。 
	 * 从Spring 3.1开始，它返回Servlet 3.0兼容性的字符串化值列表。 
	 * 考虑使用{@link  #getHeaderValues（String）}进行原始对象访问。 
	 *  
	 * @param 命名标题的名称
	 * @return 关联的标题值，如果没有则为空List
	 */
	@Override
	public List<String> getHeaders(String name) {
		HeaderValueHolder header = this.headers.get(name);
		if (header != null) {
			return header.getStringValues();
		}
		else {
			return Collections.emptyList();
		}
	}

	/**
	 * Return the primary value for the given header, if any.
	 * <p>Will return the first value in case of multiple values.
	 * @param name the name of the header
	 * @return the associated header value, or {@code null} if none
	 */
	/**
	 * 返回给定标头的主要值（如果有）。 
	 *  <p>如果有多个值，将返回第一个值。 
	 *  
	 * @param 命名标题的名称
	 * @return 关联的标题值，如果没有则为{@code  null}
	 */
	@Nullable
	public Object getHeaderValue(String name) {
		HeaderValueHolder header = this.headers.get(name);
		return (header != null ? header.getValue() : null);
	}

	/**
	 * Return all values for the given header as a List of value objects.
	 * @param name the name of the header
	 * @return the associated header values, or an empty List if none
	 */
	/**
	 * 以值对象列表的形式返回给定标头的所有值。 
	 *  
	 * @param 命名标题的名称
	 * @return 关联的标题值，如果没有则为空List
	 */
	public List<Object> getHeaderValues(String name) {
		HeaderValueHolder header = this.headers.get(name);
		if (header != null) {
			return header.getValues();
		}
		else {
			return Collections.emptyList();
		}
	}

	/**
	 * The default implementation returns the given URL String as-is.
	 * <p>Can be overridden in subclasses, appending a session id or the like.
	 */
	/**
	 * 默认实现按原样返回给定的URL字符串。 
	 *  <p>可以在子类中覆盖，可以附加会话ID等。 
	 * 
	 */
	@Override
	public String encodeURL(String url) {
		return url;
	}

	/**
	 * The default implementation delegates to {@link #encodeURL},
	 * returning the given URL String as-is.
	 * <p>Can be overridden in subclasses, appending a session id or the like
	 * in a redirect-specific fashion. For general URL encoding rules,
	 * override the common {@link #encodeURL} method instead, applying
	 * to redirect URLs as well as to general URLs.
	 */
	/**
	 * 默认实现委托给{@link  #encodeURL}，并按原样返回给定的URL字符串。 
	 *  <p>可以在子类中重写，以特定于重定向的方式附加会话ID等。 
	 * 对于常规URL编码规则，请改用常见的{@link  #encodeURL}方法，该方法适用于重定向URL以及常规URL。 
	 * 
	 */
	@Override
	public String encodeRedirectURL(String url) {
		return encodeURL(url);
	}

	@Override
	@Deprecated
	public String encodeUrl(String url) {
		return encodeURL(url);
	}

	@Override
	@Deprecated
	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	@Override
	public void sendError(int status, String errorMessage) throws IOException {
		Assert.state(!isCommitted(), "Cannot set error status - response is already committed");
		this.status = status;
		this.errorMessage = errorMessage;
		setCommitted(true);
	}

	@Override
	public void sendError(int status) throws IOException {
		Assert.state(!isCommitted(), "Cannot set error status - response is already committed");
		this.status = status;
		setCommitted(true);
	}

	@Override
	public void sendRedirect(String url) throws IOException {
		Assert.state(!isCommitted(), "Cannot send redirect - response is already committed");
		Assert.notNull(url, "Redirect URL must not be null");
		setHeader(HttpHeaders.LOCATION, url);
		setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		setCommitted(true);
	}

	@Nullable
	public String getRedirectedUrl() {
		return getHeader(HttpHeaders.LOCATION);
	}

	@Override
	public void setDateHeader(String name, long value) {
		setHeaderValue(name, formatDate(value));
	}

	@Override
	public void addDateHeader(String name, long value) {
		addHeaderValue(name, formatDate(value));
	}

	public long getDateHeader(String name) {
		String headerValue = getHeader(name);
		if (headerValue == null) {
			return -1;
		}
		try {
			return newDateFormat().parse(getHeader(name)).getTime();
		}
		catch (ParseException ex) {
			throw new IllegalArgumentException(
					"Value for header '" + name + "' is not a valid Date: " + headerValue);
		}
	}

	private String formatDate(long date) {
		return newDateFormat().format(new Date(date));
	}

	private DateFormat newDateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		dateFormat.setTimeZone(GMT);
		return dateFormat;
	}

	@Override
	public void setHeader(String name, String value) {
		setHeaderValue(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		addHeaderValue(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		setHeaderValue(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		addHeaderValue(name, value);
	}

	private void setHeaderValue(String name, Object value) {
		boolean replaceHeader = true;
		if (setSpecialHeader(name, value, replaceHeader)) {
			return;
		}
		doAddHeaderValue(name, value, replaceHeader);
	}

	private void addHeaderValue(String name, Object value) {
		boolean replaceHeader = false;
		if (setSpecialHeader(name, value, replaceHeader)) {
			return;
		}
		doAddHeaderValue(name, value, replaceHeader);
	}

	private boolean setSpecialHeader(String name, Object value, boolean replaceHeader) {
		if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(name)) {
			setContentType(value.toString());
			return true;
		}
		else if (HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(name)) {
			setContentLength(value instanceof Number ? ((Number) value).intValue() :
					Integer.parseInt(value.toString()));
			return true;
		}
		else if (HttpHeaders.CONTENT_LANGUAGE.equalsIgnoreCase(name)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_LANGUAGE, value.toString());
			Locale language = headers.getContentLanguage();
			setLocale(language != null ? language : Locale.getDefault());
			return true;
		}
		else if (HttpHeaders.SET_COOKIE.equalsIgnoreCase(name)) {
			MockCookie cookie = MockCookie.parse(value.toString());
			if (replaceHeader) {
				setCookie(cookie);
			}
			else {
				addCookie(cookie);
			}
			return true;
		}
		else {
			return false;
		}
	}

	private void doAddHeaderValue(String name, Object value, boolean replace) {
		HeaderValueHolder header = this.headers.get(name);
		Assert.notNull(value, "Header value must not be null");
		if (header == null) {
			header = new HeaderValueHolder();
			this.headers.put(name, header);
		}
		if (replace) {
			header.setValue(value);
		}
		else {
			header.addValue(value);
		}
	}

	/**
	 * Set the {@code Set-Cookie} header to the supplied {@link Cookie},
	 * overwriting any previous cookies.
	 * @param cookie the {@code Cookie} to set
	 * @since 5.1.10
	 * @see #addCookie(Cookie)
	 */
	/**
	 * 将{@code  Set-Cookie}标头设置为提供的{@link  Cookie}，覆盖以前的所有cookie。 
	 *  
	 * @param  cookie {{@@code> Cookie}设置为@.5.1.10起
	 * @see  #addCookie（Cookie）
	 */
	private void setCookie(Cookie cookie) {
		Assert.notNull(cookie, "Cookie must not be null");
		this.cookies.clear();
		this.cookies.add(cookie);
		doAddHeaderValue(HttpHeaders.SET_COOKIE, getCookieHeader(cookie), true);
	}

	@Override
	public void setStatus(int status) {
		if (!this.isCommitted()) {
			this.status = status;
		}
	}

	@Override
	@Deprecated
	public void setStatus(int status, String errorMessage) {
		if (!this.isCommitted()) {
			this.status = status;
			this.errorMessage = errorMessage;
		}
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@Nullable
	public String getErrorMessage() {
		return this.errorMessage;
	}


	//---------------------------------------------------------------------
	// Methods for MockRequestDispatcher
	//---------------------------------------------------------------------

	public void setForwardedUrl(@Nullable String forwardedUrl) {
		this.forwardedUrl = forwardedUrl;
	}

	@Nullable
	public String getForwardedUrl() {
		return this.forwardedUrl;
	}

	public void setIncludedUrl(@Nullable String includedUrl) {
		this.includedUrls.clear();
		if (includedUrl != null) {
			this.includedUrls.add(includedUrl);
		}
	}

	@Nullable
	public String getIncludedUrl() {
		int count = this.includedUrls.size();
		Assert.state(count <= 1,
				() -> "More than 1 URL included - check getIncludedUrls instead: " + this.includedUrls);
		return (count == 1 ? this.includedUrls.get(0) : null);
	}

	public void addIncludedUrl(String includedUrl) {
		Assert.notNull(includedUrl, "Included URL must not be null");
		this.includedUrls.add(includedUrl);
	}

	public List<String> getIncludedUrls() {
		return this.includedUrls;
	}


	/**
	 * Inner class that adapts the ServletOutputStream to mark the
	 * response as committed once the buffer size is exceeded.
	 */
	/**
	 * 超出缓冲区大小后，使ServletOutputStream适应以将响应标记为已提交的内部类。 
	 * 
	 */
	private class ResponseServletOutputStream extends DelegatingServletOutputStream {

		public ResponseServletOutputStream(OutputStream out) {
			super(out);
		}

		@Override
		public void write(int b) throws IOException {
			super.write(b);
			super.flush();
			setCommittedIfBufferSizeExceeded();
		}

		@Override
		public void flush() throws IOException {
			super.flush();
			setCommitted(true);
		}
	}


	/**
	 * Inner class that adapts the PrintWriter to mark the
	 * response as committed once the buffer size is exceeded.
	 */
	/**
	 * 超出缓冲区大小后，使PrintWriter适应以将响应标记为已提交的内部类。 
	 * 
	 */
	private class ResponsePrintWriter extends PrintWriter {

		public ResponsePrintWriter(Writer out) {
			super(out, true);
		}

		@Override
		public void write(char[] buf, int off, int len) {
			super.write(buf, off, len);
			super.flush();
			setCommittedIfBufferSizeExceeded();
		}

		@Override
		public void write(String s, int off, int len) {
			super.write(s, off, len);
			super.flush();
			setCommittedIfBufferSizeExceeded();
		}

		@Override
		public void write(int c) {
			super.write(c);
			super.flush();
			setCommittedIfBufferSizeExceeded();
		}

		@Override
		public void flush() {
			super.flush();
			setCommitted(true);
		}

		@Override
		public void close() {
			super.flush();
			super.close();
			setCommitted(true);
		}
	}

}
