/** Generated by english-annotation-buster, Powered by Google Translate.**/
/**
 * This package contains classes used to determine the requested the media types in a request.
 *
 * <p>{@link org.springframework.web.accept.ContentNegotiationStrategy} is the main
 * abstraction for determining requested {@linkplain org.springframework.http.MediaType media types}
 * with implementations based on
 * {@linkplain org.springframework.web.accept.PathExtensionContentNegotiationStrategy path extensions}, a
 * {@linkplain org.springframework.web.accept.ParameterContentNegotiationStrategy a request parameter}, the
 * {@linkplain org.springframework.web.accept.HeaderContentNegotiationStrategy 'Accept' header}, or a
 * {@linkplain org.springframework.web.accept.FixedContentNegotiationStrategy default content type}.
 *
 * <p>{@link org.springframework.web.accept.ContentNegotiationManager} is used to delegate to one
 * ore more of the above strategies in a specific order.
 */
/**
 * 该程序包包含用于确定请求中请求的媒体类型的类。 
 *  <p> {<@link> org.springframework.web.accept.ContentNegotiationStrategy}是用于确定请求的{@link  plain org.springframework.http.MediaType媒体类型}的主要抽象，其实现基于{@link 普通org.springframework.web.accept.PathExtensionContentNegotiationStrategy路径扩展}，{<@link>平原org.springframework.web.accept.ParameterContentNegotiationStrategy请求参数}，{<@link>平原org.springframework.web.accept。 
 *  HeaderContentNegotiationStrategy'Accept'标头}，或{@link 纯org.springframework.web.accept.FixedContentNegotiationStrategy默认内容类型}。 
 *  <p> {<@link> org.springframework.web.accept.ContentNegotiationManager}用于以特定顺序委托上述一种或多种策略。 
 * 
 */
@NonNullApi
@NonNullFields
package org.springframework.web.accept;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;
