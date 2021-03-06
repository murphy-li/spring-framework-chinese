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

package org.springframework.web.servlet.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContext;

/**
 * Abstract base class for {@link org.springframework.web.servlet.View}
 * implementations. Subclasses should be JavaBeans, to allow for
 * convenient configuration as Spring-managed bean instances.
 *
 * <p>Provides support for static attributes, to be made available to the view,
 * with a variety of ways to specify them. Static attributes will be merged
 * with the given dynamic attributes (the model that the controller returned)
 * for each render operation.
 *
 * <p>Extends {@link WebApplicationObjectSupport}, which will be helpful to
 * some views. Subclasses just need to implement the actual rendering.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #setAttributes
 * @see #setAttributesMap
 * @see #renderMergedOutputModel
 */
/**
 * {@link  org.springframework.web.servlet.View}实现的抽象基类。 
 * 子类应该是JavaBean，以方便配置为Spring托管的bean实例。 
 *  <p>提供对静态属性的支持，该属性可通过多种方式指定给视图，以供视图使用。 
 * 对于每个渲染操作，静态属性将与给定的动态属性（控制器返回的模型）合并。 
 *  <p>扩展{@link  WebApplicationObjectSupport}，这将对某些视图有所帮助。 
 * 子类只需要实现实际的呈现。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller 
 * @see  #setAttributes 
 * @see  #setAttributesMap 
 * @see  #renderMergedOutputModel
 */
public abstract class AbstractView extends WebApplicationObjectSupport implements View, BeanNameAware {

	/** Default content type. Overridable as bean property. */
	/**
	 * 默认内容类型。 
	 * 可重写为bean属性。 
	 * 
	 */
	public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";

	/** Initial size for the temporary output byte array (if any). */
	/**
	 * 临时输出字节数组的初始大小（如果有）。 
	 * 
	 */
	private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;


	@Nullable
	private String contentType = DEFAULT_CONTENT_TYPE;

	@Nullable
	private String requestContextAttribute;

	private final Map<String, Object> staticAttributes = new LinkedHashMap<>();

	private boolean exposePathVariables = true;

	private boolean exposeContextBeansAsAttributes = false;

	@Nullable
	private Set<String> exposedContextBeanNames;

	@Nullable
	private String beanName;



	/**
	 * Set the content type for this view.
	 * Default is "text/html;charset=ISO-8859-1".
	 * <p>May be ignored by subclasses if the view itself is assumed
	 * to set the content type, e.g. in case of JSPs.
	 */
	/**
	 * 设置此视图的内容类型。 
	 * 默认值为"text / html; charset = ISO-8859-1"。 
	 *  <p>如果假定视图本身设置了内容类型，则子类可以忽略<p>对于JSP。 
	 * 
	 */
	public void setContentType(@Nullable String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Return the content type for this view.
	 */
	/**
	 * 返回此视图的内容类型。 
	 * 
	 */
	@Override
	@Nullable
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Set the name of the RequestContext attribute for this view.
	 * Default is none.
	 */
	/**
	 * 设置此视图的RequestContext属性的名称。 
	 * 默认为无。 
	 * 
	 */
	public void setRequestContextAttribute(@Nullable String requestContextAttribute) {
		this.requestContextAttribute = requestContextAttribute;
	}

	/**
	 * Return the name of the RequestContext attribute, if any.
	 */
	/**
	 * 返回RequestContext属性的名称（如果有）。 
	 * 
	 */
	@Nullable
	public String getRequestContextAttribute() {
		return this.requestContextAttribute;
	}

	/**
	 * Set static attributes as a CSV string.
	 * Format is: attname0={value1},attname1={value1}
	 * <p>"Static" attributes are fixed attributes that are specified in
	 * the View instance configuration. "Dynamic" attributes, on the other hand,
	 * are values passed in as part of the model.
	 */
	/**
	 * 将静态属性设置为CSV字符串。 
	 * 格式为：attname0 = {value1}，attname1 = {value1} <p>"静态"属性是在View实例配置中指定的固定属性。 
	 * 另一方面，"动态"属性是作为模型一部分传递的值。 
	 * 
	 */
	public void setAttributesCSV(@Nullable String propString) throws IllegalArgumentException {
		if (propString != null) {
			StringTokenizer st = new StringTokenizer(propString, ",");
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				int eqIdx = tok.indexOf('=');
				if (eqIdx == -1) {
					throw new IllegalArgumentException(
							"Expected '=' in attributes CSV string '" + propString + "'");
				}
				if (eqIdx >= tok.length() - 2) {
					throw new IllegalArgumentException(
							"At least 2 characters ([]) required in attributes CSV string '" + propString + "'");
				}
				String name = tok.substring(0, eqIdx);
				// Delete first and last characters of value: { and }
				int beginIndex = eqIdx + 2;
				int endIndex = tok.length() - 1;
				String value = tok.substring(beginIndex, endIndex);

				addStaticAttribute(name, value);
			}
		}
	}

	/**
	 * Set static attributes for this view from a
	 * {@code java.util.Properties} object.
	 * <p>"Static" attributes are fixed attributes that are specified in
	 * the View instance configuration. "Dynamic" attributes, on the other hand,
	 * are values passed in as part of the model.
	 * <p>This is the most convenient way to set static attributes. Note that
	 * static attributes can be overridden by dynamic attributes, if a value
	 * with the same name is included in the model.
	 * <p>Can be populated with a String "value" (parsed via PropertiesEditor)
	 * or a "props" element in XML bean definitions.
	 * @see org.springframework.beans.propertyeditors.PropertiesEditor
	 */
	/**
	 * 从{@code  java.util.Properties}对象为此视图设置静态属性。 
	 *  <p>"静态"属性是在View实例配置中指定的固定属性。 
	 * 另一方面，"动态"属性是作为模型一部分传递的值。 
	 *  <p>这是设置静态属性的最便捷方法。 
	 * 请注意，如果模型中包含具有相同名称的值，则静态属性可以被动态属性覆盖。 
	 *  <p>可以用XML bean定义中的字符串"值"（通过PropertiesEditor解析）或"props"元素填充。 
	 *  
	 * @see  org.springframework.beans.propertyeditors.PropertiesEditor
	 */
	public void setAttributes(Properties attributes) {
		CollectionUtils.mergePropertiesIntoMap(attributes, this.staticAttributes);
	}

	/**
	 * Set static attributes for this view from a Map. This allows to set
	 * any kind of attribute values, for example bean references.
	 * <p>"Static" attributes are fixed attributes that are specified in
	 * the View instance configuration. "Dynamic" attributes, on the other hand,
	 * are values passed in as part of the model.
	 * <p>Can be populated with a "map" or "props" element in XML bean definitions.
	 * @param attributes a Map with name Strings as keys and attribute objects as values
	 */
	/**
	 * 在地图中为此视图设置静态属性。 
	 * 这允许设置任何种类的属性值，例如bean引用。 
	 *  <p>"静态"属性是在View实例配置中指定的固定属性。 
	 * 另一方面，"动态"属性是作为模型一部分传递的值。 
	 *  <p>可以在XML bean定义中填充"map"或"props"元素。 
	 *  
	 * @param 为名称为Map的Map分配属性，字符串为键，属性对象为值
	 */
	public void setAttributesMap(@Nullable Map<String, ?> attributes) {
		if (attributes != null) {
			attributes.forEach(this::addStaticAttribute);
		}
	}

	/**
	 * Allow Map access to the static attributes of this view,
	 * with the option to add or override specific entries.
	 * <p>Useful for specifying entries directly, for example via
	 * "attributesMap[myKey]". This is particularly useful for
	 * adding or overriding entries in child view definitions.
	 */
	/**
	 * 允许地图访问此视图的静态属性，并带有添加或覆盖特定条目的选项。 
	 *  <p>可用于直接指定条目，例如通过"attributesMap [myKey]"。 
	 * 这对于在子视图定义中添加或覆盖条目特别有用。 
	 * 
	 */
	public Map<String, Object> getAttributesMap() {
		return this.staticAttributes;
	}

	/**
	 * Add static data to this view, exposed in each view.
	 * <p>"Static" attributes are fixed attributes that are specified in
	 * the View instance configuration. "Dynamic" attributes, on the other hand,
	 * are values passed in as part of the model.
	 * <p>Must be invoked before any calls to {@code render}.
	 * @param name the name of the attribute to expose
	 * @param value the attribute value to expose
	 * @see #render
	 */
	/**
	 * 将静态数据添加到此视图，在每个视图中公开。 
	 *  <p>"静态"属性是在View实例配置中指定的固定属性。 
	 * 另一方面，"动态"属性是作为模型一部分传递的值。 
	 *  <p>必须在调用{@code  render}之前被调用。 
	 *  
	 * @param 命名要公开的属性的名称
	 * @param 值公开要公开的属性的值
	 * @see  #render
	 */
	public void addStaticAttribute(String name, Object value) {
		this.staticAttributes.put(name, value);
	}

	/**
	 * Return the static attributes for this view. Handy for testing.
	 * <p>Returns an unmodifiable Map, as this is not intended for
	 * manipulating the Map but rather just for checking the contents.
	 * @return the static attributes in this view
	 */
	/**
	 * 返回此视图的静态属性。 
	 * 方便进行测试。 
	 *  <p>返回一个不可修改的Map，因为它不是用于操作Map而是用于检查内容。 
	 *  
	 * @return 此视图中的静态属性
	 */
	public Map<String, Object> getStaticAttributes() {
		return Collections.unmodifiableMap(this.staticAttributes);
	}

	/**
	 * Specify whether to add path variables to the model or not.
	 * <p>Path variables are commonly bound to URI template variables through the {@code @PathVariable}
	 * annotation. They're are effectively URI template variables with type conversion applied to
	 * them to derive typed Object values. Such values are frequently needed in views for
	 * constructing links to the same and other URLs.
	 * <p>Path variables added to the model override static attributes (see {@link #setAttributes(Properties)})
	 * but not attributes already present in the model.
	 * <p>By default this flag is set to {@code true}. Concrete view types can override this.
	 * @param exposePathVariables {@code true} to expose path variables, and {@code false} otherwise
	 */
	/**
	 * 指定是否将路径变量添加到模型。 
	 *  <p>路径变量通常通过{@code  @PathVariable}注解绑定到URI模板变量。 
	 * 它们是有效的URI模板变量，对其应用了类型转换以派生类型化的Object值。 
	 * 在视图中，通常需要使用这些值来构建指向相同URL和其他URL的链接。 
	 *  <p>添加到模型中的路径变量将覆盖静态属性（请参见{@link  #setAttributes（Properties）}），但模型中已不存在的属性。 
	 *  <p>默认情况下，此标志设置为{@code  true}。 
	 * 具体的视图类型可以覆盖它。 
	 *  
	 * @param  ExposurePathVariables {@code  true}公开路径变量，否则为{@code  false}
	 */
	public void setExposePathVariables(boolean exposePathVariables) {
		this.exposePathVariables = exposePathVariables;
	}

	/**
	 * Return whether to add path variables to the model or not.
	 */
	/**
	 * 返回是否向模型添加路径变量。 
	 * 
	 */
	public boolean isExposePathVariables() {
		return this.exposePathVariables;
	}

	/**
	 * Set whether to make all Spring beans in the application context accessible
	 * as request attributes, through lazy checking once an attribute gets accessed.
	 * <p>This will make all such beans accessible in plain {@code ${...}}
	 * expressions in a JSP 2.0 page, as well as in JSTL's {@code c:out}
	 * value expressions.
	 * <p>Default is "false". Switch this flag on to transparently expose all
	 * Spring beans in the request attribute namespace.
	 * <p><b>NOTE:</b> Context beans will override any custom request or session
	 * attributes of the same name that have been manually added. However, model
	 * attributes (as explicitly exposed to this view) of the same name will
	 * always override context beans.
	 * @see #getRequestToExpose
	 */
	/**
	 * 设置是否通过访问属性后进行惰性检查来使应用程序上下文中的所有Spring Bean都可作为请求属性进行访问。 
	 *  <p>这将使所有这样的bean在JSP 2.0页面中的普通{@code  $ {...}}表达式以及JSTL的{@code  c：out}值表达式中均可访问。 
	 *  <p>默认为"false"。 
	 * 启用此标志可以透明地公开请求属性名称空间中的所有Spring Bean。 
	 *  <p> <b>注意：</ b>上下文Bean将覆盖已手动添加的任何同名自定义请求或会话属性。 
	 * 但是，具有相同名称的模型属性（明确显示在此视图中）将始终覆盖上下文Bean。 
	 *  
	 * @see  #getRequestToExpose
	 */
	public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
		this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
	}

	/**
	 * Specify the names of beans in the context which are supposed to be exposed.
	 * If this is non-null, only the specified beans are eligible for exposure as
	 * attributes.
	 * <p>If you'd like to expose all Spring beans in the application context, switch
	 * the {@link #setExposeContextBeansAsAttributes "exposeContextBeansAsAttributes"}
	 * flag on but do not list specific bean names for this property.
	 */
	/**
	 * 在上下文中指定应该公开的bean名称。 
	 * 如果这不是非null，则只有指定的bean才有资格作为属性公开。 
	 *  <p>如果要在应用程序上下文中公开所有Spring Bean，请打开{@link  #setExposeContextBeansAsAttributes"exposeContextBeansAsAttributes"}标志，但不要为此属性列出特定的Bean名称。 
	 * 
	 */
	public void setExposedContextBeanNames(String... exposedContextBeanNames) {
		this.exposedContextBeanNames = new HashSet<>(Arrays.asList(exposedContextBeanNames));
	}

	/**
	 * Set the view's name. Helpful for traceability.
	 * <p>Framework code must call this when constructing views.
	 */
	/**
	 * 设置视图的名称。 
	 * 有助于追溯。 
	 *  <p>框架代码在构造视图时必须调用它。 
	 * 
	 */
	@Override
	public void setBeanName(@Nullable String beanName) {
		this.beanName = beanName;
	}

	/**
	 * Return the view's name. Should never be {@code null},
	 * if the view was correctly configured.
	 */
	/**
	 * 返回视图的名称。 
	 * 如果视图配置正确，则永远不应为{@code  null}。 
	 * 
	 */
	@Nullable
	public String getBeanName() {
		return this.beanName;
	}


	/**
	 * Prepares the view given the specified model, merging it with static
	 * attributes and a RequestContext attribute, if necessary.
	 * Delegates to renderMergedOutputModel for the actual rendering.
	 * @see #renderMergedOutputModel
	 */
	/**
	 * 根据给定的模型准备视图，并在必要时将其与静态属性和RequestContext属性合并。 
	 * 委托renderMergedOutputModel进行实际渲染。 
	 *  
	 * @see  #renderMergedOutputModel
	 */
	@Override
	public void render(@Nullable Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("View " + formatViewName() +
					", model " + (model != null ? model : Collections.emptyMap()) +
					(this.staticAttributes.isEmpty() ? "" : ", static attributes " + this.staticAttributes));
		}

		Map<String, Object> mergedModel = createMergedOutputModel(model, request, response);
		prepareResponse(request, response);
		renderMergedOutputModel(mergedModel, getRequestToExpose(request), response);
	}

	/**
	 * Creates a combined output Map (never {@code null}) that includes dynamic values and static attributes.
	 * Dynamic values take precedence over static attributes.
	 */
	/**
	 * 创建一个包含动态值和静态属性的组合输出Map（永不{@code  null}）。 
	 * 动态值优先于静态属性。 
	 * 
	 */
	protected Map<String, Object> createMergedOutputModel(@Nullable Map<String, ?> model,
			HttpServletRequest request, HttpServletResponse response) {

		@SuppressWarnings("unchecked")
		Map<String, Object> pathVars = (this.exposePathVariables ?
				(Map<String, Object>) request.getAttribute(View.PATH_VARIABLES) : null);

		// Consolidate static and dynamic model attributes.
		int size = this.staticAttributes.size();
		size += (model != null ? model.size() : 0);
		size += (pathVars != null ? pathVars.size() : 0);

		Map<String, Object> mergedModel = new LinkedHashMap<>(size);
		mergedModel.putAll(this.staticAttributes);
		if (pathVars != null) {
			mergedModel.putAll(pathVars);
		}
		if (model != null) {
			mergedModel.putAll(model);
		}

		// Expose RequestContext?
		if (this.requestContextAttribute != null) {
			mergedModel.put(this.requestContextAttribute, createRequestContext(request, response, mergedModel));
		}

		return mergedModel;
	}

	/**
	 * Create a RequestContext to expose under the specified attribute name.
	 * <p>The default implementation creates a standard RequestContext instance for the
	 * given request and model. Can be overridden in subclasses for custom instances.
	 * @param request current HTTP request
	 * @param model combined output Map (never {@code null}),
	 * with dynamic values taking precedence over static attributes
	 * @return the RequestContext instance
	 * @see #setRequestContextAttribute
	 * @see org.springframework.web.servlet.support.RequestContext
	 */
	/**
	 * 创建一个RequestContext以在指定的属性名称下公开。 
	 *  <p>默认实现为给定的请求和模型创建一个标准的RequestContext实例。 
	 * 可以在自定义实例的子类中覆盖。 
	 *  
	 * @param 请求当前的HTTP请求
	 * @param 模型组合输出Map（从不{@code  null}），动态值优先于静态属性
	 * @return  RequestContext实例
	 * @see  #setRequestContextAttribute <@参见> org.springframework.web.servlet.support.RequestContext
	 */
	protected RequestContext createRequestContext(
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		return new RequestContext(request, response, getServletContext(), model);
	}

	/**
	 * Prepare the given response for rendering.
	 * <p>The default implementation applies a workaround for an IE bug
	 * when sending download content via HTTPS.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 */
	/**
	 * 准备给定的响应以进行渲染。 
	 *  <p>当通过HTTPS发送下载内容时，默认实现会应用针对IE错误的解决方法。 
	 *  
	 * @param 请求当前HTTP请求
	 * @param 响应当前HTTP响应
	 */
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		if (generatesDownloadContent()) {
			response.setHeader("Pragma", "private");
			response.setHeader("Cache-Control", "private, must-revalidate");
		}
	}

	/**
	 * Return whether this view generates download content
	 * (typically binary content like PDF or Excel files).
	 * <p>The default implementation returns {@code false}. Subclasses are
	 * encouraged to return {@code true} here if they know that they are
	 * generating download content that requires temporary caching on the
	 * client side, typically via the response OutputStream.
	 * @see #prepareResponse
	 * @see javax.servlet.http.HttpServletResponse#getOutputStream()
	 */
	/**
	 * 返回此视图是否生成下载内容（通常是二进制内容，例如PDF或Excel文件）。 
	 *  <p>默认实现返回{@code  false}。 
	 * 如果子类知道它们正在生成需要客户端临时缓存的下载内容（通常通过响应OutputStream），则鼓励子类在此处返回{@code  true}。 
	 *  
	 * @see  #prepareResponse 
	 * @see  javax.servlet.http.HttpServletResponse＃getOutputStream（）
	 */
	protected boolean generatesDownloadContent() {
		return false;
	}

	/**
	 * Get the request handle to expose to {@link #renderMergedOutputModel}, i.e. to the view.
	 * <p>The default implementation wraps the original request for exposure of Spring beans
	 * as request attributes (if demanded).
	 * @param originalRequest the original servlet request as provided by the engine
	 * @return the wrapped request, or the original request if no wrapping is necessary
	 * @see #setExposeContextBeansAsAttributes
	 * @see #setExposedContextBeanNames
	 * @see org.springframework.web.context.support.ContextExposingHttpServletRequest
	 */
	/**
	 * 获取请求句柄以暴露给{@link  #renderMergedOutputModel}，即视图。 
	 *  <p>默认实现将原始的Spring Bean请求包装为请求属性（如果需要）。 
	 *  
	 * @param  originalRequest由引擎提供的原始servlet请求
	 * @return 包装的请求，或者如果不需要包装则为原始请求
	 * @see  #setExposeContextBeansAsAttributes 
	 * @see  #setExposedContextBeanNames 
	 * @see  org.springframework .web.context.support.ContextExposedHttpServletRequest
	 */
	protected HttpServletRequest getRequestToExpose(HttpServletRequest originalRequest) {
		if (this.exposeContextBeansAsAttributes || this.exposedContextBeanNames != null) {
			WebApplicationContext wac = getWebApplicationContext();
			Assert.state(wac != null, "No WebApplicationContext");
			return new ContextExposingHttpServletRequest(originalRequest, wac, this.exposedContextBeanNames);
		}
		return originalRequest;
	}

	/**
	 * Subclasses must implement this method to actually render the view.
	 * <p>The first step will be preparing the request: In the JSP case,
	 * this would mean setting model objects as request attributes.
	 * The second step will be the actual rendering of the view,
	 * for example including the JSP via a RequestDispatcher.
	 * @param model combined output Map (never {@code null}),
	 * with dynamic values taking precedence over static attributes
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @throws Exception if rendering failed
	 */
	/**
	 * 子类必须实现此方法才能实际呈现视图。 
	 *  <p>第一步将是准备请求：在JSP情况下，这意味着将模型对象设置为请求属性。 
	 * 第二步将是视图的实际呈现，例如，通过RequestDispatcher包含JSP。 
	 *  
	 * @param 模型组合的输出Map（从不{<@@code> null}），动态值优先于静态属性
	 * @param 请求当前HTTP请求
	 * @param 响应当前HTTP响应
	 * @throws 呈现时异常失败了
	 */
	protected abstract void renderMergedOutputModel(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;


	/**
	 * Expose the model objects in the given map as request attributes.
	 * Names will be taken from the model Map.
	 * This method is suitable for all resources reachable by {@link javax.servlet.RequestDispatcher}.
	 * @param model a Map of model objects to expose
	 * @param request current HTTP request
	 */
	/**
	 * 将给定地图中的模型对象公开为请求属性。 
	 * 名称将从模型Map中获取。 
	 * 此方法适用于{@link  javax.servlet.RequestDispatcher}可以访问的所有资源。 
	 *  
	 * @param 对模型对象的映射进行建模以公开
	 * @param 请求当前的HTTP请求
	 */
	protected void exposeModelAsRequestAttributes(Map<String, Object> model,
			HttpServletRequest request) throws Exception {

		model.forEach((name, value) -> {
			if (value != null) {
				request.setAttribute(name, value);
			}
			else {
				request.removeAttribute(name);
			}
		});
	}

	/**
	 * Create a temporary OutputStream for this view.
	 * <p>This is typically used as IE workaround, for setting the content length header
	 * from the temporary stream before actually writing the content to the HTTP response.
	 */
	/**
	 * 为此视图创建一个临时OutputStream。 
	 *  <p>通常用作IE解决方法，用于在将内容实际写入HTTP响应之前从临时流设置内容长度标头。 
	 * 
	 */
	protected ByteArrayOutputStream createTemporaryOutputStream() {
		return new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);
	}

	/**
	 * Write the given temporary OutputStream to the HTTP response.
	 * @param response current HTTP response
	 * @param baos the temporary OutputStream to write
	 * @throws IOException if writing/flushing failed
	 */
	/**
	 * 将给定的临时OutputStream写入HTTP响应。 
	 *  
	 * @param 响应当前的HTTP响应
	 * @param 如果写入/刷新失败，则将临时OutputStream打包以写入
	 * @throws  IOException
	 */
	protected void writeToResponse(HttpServletResponse response, ByteArrayOutputStream baos) throws IOException {
		// Write content type and also length (determined via byte array).
		response.setContentType(getContentType());
		response.setContentLength(baos.size());

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
	}

	/**
	 * Set the content type of the response to the configured
	 * {@link #setContentType(String) content type} unless the
	 * {@link View#SELECTED_CONTENT_TYPE} request attribute is present and set
	 * to a concrete media type.
	 */
	/**
	 * 除非存在{@link  View＃SELECTED_CONTENT_TYPE}请求属性并将其设置为具体的媒体类型，否则将响应的内容类型设置为已配置的{@link  #setContentType（String）内容类型}。 
	 * 
	 */
	protected void setResponseContentType(HttpServletRequest request, HttpServletResponse response) {
		MediaType mediaType = (MediaType) request.getAttribute(View.SELECTED_CONTENT_TYPE);
		if (mediaType != null && mediaType.isConcrete()) {
			response.setContentType(mediaType.toString());
		}
		else {
			response.setContentType(getContentType());
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + ": " + formatViewName();
	}

	protected String formatViewName() {
		return (getBeanName() != null ? "name '" + getBeanName() + "'" : "[" + getClass().getSimpleName() + "]");
	}

}
