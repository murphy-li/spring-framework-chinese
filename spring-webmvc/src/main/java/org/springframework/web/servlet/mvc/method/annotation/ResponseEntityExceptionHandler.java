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

package org.springframework.web.servlet.mvc.method.annotation;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

/**
 * A convenient base class for {@link ControllerAdvice @ControllerAdvice} classes
 * that wish to provide centralized exception handling across all
 * {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
 *
 * <p>This base class provides an {@code @ExceptionHandler} method for handling
 * internal Spring MVC exceptions. This method returns a {@code ResponseEntity}
 * for writing to the response with a {@link HttpMessageConverter message converter},
 * in contrast to
 * {@link org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 * DefaultHandlerExceptionResolver} which returns a
 * {@link org.springframework.web.servlet.ModelAndView ModelAndView}.
 *
 * <p>If there is no need to write error content to the response body, or when
 * using view resolution (e.g., via {@code ContentNegotiatingViewResolver}),
 * then {@code DefaultHandlerExceptionResolver} is good enough.
 *
 * <p>Note that in order for an {@code @ControllerAdvice} subclass to be
 * detected, {@link ExceptionHandlerExceptionResolver} must be configured.
 *
 * @author Rossen Stoyanchev
 * @since 3.2
 * @see #handleException(Exception, WebRequest)
 * @see org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 */
/**
 * 一个方便的基础类，用于{@link  ControllerAdvice @ControllerAdvice}类，这些类希望通过{@code  @ExceptionHandler}方法跨所有{@code  @RequestMapping}方法提供集中式异常处理。 
 *  <p>此基类提供了一个{@code  @ExceptionHandler}方法，用于处理内部Spring MVC异常。 
 * 与{@link  org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver相比，此方法返回一个{@code  ResponseEntity}以使用{@link  HttpMessageConverter消息转换器}写入响应。 
 *  }，它返回一个{@link  org.springframework.web.servlet.ModelAndView ModelAndView}。 
 *  <p>如果不需要将错误内容写入响应主体，或者使用视图分辨率时（例如，通过{@code  ContentNegotiatingViewResolver}），那么{@code  DefaultHandlerExceptionResolver}就足够了。 
 *  <p>请注意，为了检测到{@code  @ControllerAdvice}子类，必须配置{@link  ExceptionHandlerExceptionResolver}。 
 *  @author  Rossen Stoyanchev @自3.2起
 * @see  #handleException（Exception，WebRequest）
 * @see  org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 */
public abstract class ResponseEntityExceptionHandler {

	/**
	 * Log category to use when no mapped handler is found for a request.
	 * @see #pageNotFoundLogger
	 */
	/**
	 * 未找到请求的映射处理程序时要使用的日志类别。 
	 *  
	 * @see  #pageNotFoundLogger
	 */
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";

	/**
	 * Specific logger to use when no mapped handler is found for a request.
	 * @see #PAGE_NOT_FOUND_LOG_CATEGORY
	 */
	/**
	 * 在找不到请求的映射处理程序时要使用的特定记录器。 
	 *  
	 * @see  #PAGE_NOT_FOUND_LOG_CATEGORY
	 */
	protected static final Log pageNotFoundLogger = LogFactory.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

	/**
	 * Common logger for use in subclasses.
	 */
	/**
	 * 子类中使用的通用记录器。 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());


	/**
	 * Provides handling for standard Spring MVC exceptions.
	 * @param ex the target exception
	 * @param request the current request
	 */
	/**
	 * 提供标准Spring MVC异常的处理。 
	 *  
	 * @param 排除目标异常
	 * @param 请求当前请求
	 */
	@ExceptionHandler({
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeNotSupportedException.class,
			HttpMediaTypeNotAcceptableException.class,
			MissingPathVariableException.class,
			MissingServletRequestParameterException.class,
			ServletRequestBindingException.class,
			ConversionNotSupportedException.class,
			TypeMismatchException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			MethodArgumentNotValidException.class,
			MissingServletRequestPartException.class,
			BindException.class,
			NoHandlerFoundException.class,
			AsyncRequestTimeoutException.class
		})
	@Nullable
	public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) throws Exception {
		HttpHeaders headers = new HttpHeaders();

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
			return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, headers, status, request);
		}
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, headers, status, request);
		}
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, headers, status, request);
		}
		else if (ex instanceof MissingPathVariableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleMissingPathVariable((MissingPathVariableException) ex, headers, status, request);
		}
		else if (ex instanceof MissingServletRequestParameterException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, headers, status, request);
		}
		else if (ex instanceof ServletRequestBindingException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleServletRequestBindingException((ServletRequestBindingException) ex, headers, status, request);
		}
		else if (ex instanceof ConversionNotSupportedException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleConversionNotSupported((ConversionNotSupportedException) ex, headers, status, request);
		}
		else if (ex instanceof TypeMismatchException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleTypeMismatch((TypeMismatchException) ex, headers, status, request);
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, headers, status, request);
		}
		else if (ex instanceof HttpMessageNotWritableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, headers, status, request);
		}
		else if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, headers, status, request);
		}
		else if (ex instanceof MissingServletRequestPartException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestPart((MissingServletRequestPartException) ex, headers, status, request);
		}
		else if (ex instanceof BindException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleBindException((BindException) ex, headers, status, request);
		}
		else if (ex instanceof NoHandlerFoundException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoHandlerFoundException((NoHandlerFoundException) ex, headers, status, request);
		}
		else if (ex instanceof AsyncRequestTimeoutException) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			return handleAsyncRequestTimeoutException((AsyncRequestTimeoutException) ex, headers, status, request);
		}
		else {
			// Unknown exception, typically a wrapper with a common MVC exception as cause
			// (since @ExceptionHandler type declarations also match first-level causes):
			// We only deal with top-level MVC exceptions here, so let's rethrow the given
			// exception for further processing through the HandlerExceptionResolver chain.
			throw ex;
		}
	}

	/**
	 * Customize the response for HttpRequestMethodNotSupportedException.
	 * <p>This method logs a warning, sets the "Allow" header, and delegates to
	 * {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义HttpRequestMethodNotSupportedException的响应。 
	 *  <p>此方法记录警告，设置"允许"标头，并将其委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		pageNotFoundLogger.warn(ex.getMessage());

		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for HttpMediaTypeNotSupportedException.
	 * <p>This method sets the "Accept" header and delegates to
	 * {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义HttpMediaTypeNotSupportedException的响应。 
	 *  <p>此方法设置"Accept"标头并将其委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for HttpMediaTypeNotAcceptableException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义HttpMediaTypeNotAcceptableException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
			HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for MissingPathVariableException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 * @since 4.2
	 */
	/**
	 * 自定义MissingPathVariableException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，<
	 * @param>标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例@4.2起
	 */
	protected ResponseEntity<Object> handleMissingPathVariable(
			MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for MissingServletRequestParameterException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义MissingServletRequestParameterException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for ServletRequestBindingException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义ServletRequestBindingException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for ConversionNotSupportedException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义ConversionNotSupportedException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleConversionNotSupported(
			ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for TypeMismatchException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义TypeMismatchException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for HttpMessageNotReadableException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义HttpMessageNotReadableException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for HttpMessageNotWritableException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义HttpMessageNotWritableException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleHttpMessageNotWritable(
			HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义MethodArgumentNotValidException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for MissingServletRequestPartException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义MissingServletRequestPartException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for BindException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	/**
	 * 自定义BindException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例
	 */
	protected ResponseEntity<Object> handleBindException(
			BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for NoHandlerFoundException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 * @since 4.0
	 */
	/**
	 * 自定义NoHandlerFoundException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 例外，
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param 请求当前请求
	 * @return 一个{@code  ResponseEntity}实例@始于4.0
	 */
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	/**
	 * Customize the response for AsyncRequestTimeoutException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 * @param ex the exception
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param webRequest the current request
	 * @return a {@code ResponseEntity} instance
	 * @since 4.2.8
	 */
	/**
	 * 自定义AsyncRequestTimeoutException的响应。 
	 *  <p>此方法委托给{@link  #handleExceptionInternal}。 
	 *  
	 * @param 头信息除外
	 * @param 标头要写入响应的标头
	 * @param 状态所选响应状态
	 * @param  webRequest当前请求
	 * @return 一个{@code  ResponseEntity}实例@从4.2.8开始
	 */
	@Nullable
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
			AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		if (webRequest instanceof ServletWebRequest) {
			ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
			HttpServletResponse response = servletWebRequest.getResponse();
			if (response != null && response.isCommitted()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Async request timed out");
				}
				return null;
			}
		}

		return handleExceptionInternal(ex, null, headers, status, webRequest);
	}

	/**
	 * A single place to customize the response body of all exception types.
	 * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
	 * request attribute and creates a {@link ResponseEntity} from the given
	 * body, headers, and status.
	 * @param ex the exception
	 * @param body the body for the response
	 * @param headers the headers for the response
	 * @param status the response status
	 * @param request the current request
	 */
	/**
	 * 一个地方来定制所有异常类型的响应主体。 
	 *  <p>默认实现会设置{@link  WebUtils＃ERROR_EXCEPTION_ATTRIBUTE}请求属性，并根据给定的正文，标头和状态创建一个{@link  ResponseEntity}。 
	 *  
	 * @param 除外
	 * @param 正文响应正文
	 * @param 标头响应标头
	 * @param  status响应状态
	 * @param 请求当前请求
	 */
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}

}
