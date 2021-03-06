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

package org.springframework.web.servlet;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

/**
 * Holder for both Model and View in the web MVC framework.
 * Note that these are entirely distinct. This class merely holds
 * both to make it possible for a controller to return both model
 * and view in a single return value.
 *
 * <p>Represents a model and view returned by a handler, to be resolved
 * by a DispatcherServlet. The view can take the form of a String
 * view name which will need to be resolved by a ViewResolver object;
 * alternatively a View object can be specified directly. The model
 * is a Map, allowing the use of multiple objects keyed by name.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Rossen Stoyanchev
 * @see DispatcherServlet
 * @see ViewResolver
 * @see HandlerAdapter#handle
 * @see org.springframework.web.servlet.mvc.Controller#handleRequest
 */
/**
 * Web MVC框架中的Model和View的持有人。 
 * 请注意，这些完全不同。 
 * 此类仅持有两者，以使控制器可以在单个返回值中返回模型和视图。 
 *  <p>表示处理程序返回的模型和视图，由DispatcherServlet解析。 
 * 该视图可以采用String视图名称的形式，该名称需要由ViewResolver对象解析； 
 * 或者，可以直接指定View对象。 
 * 该模型是一个Map，允许使用多个由名称作为键的对象。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Rob Harrop @author  Rossen Stoyanchev 
 * @see  DispatcherServlet 
 * @see  ViewResolver 
 * @see  HandlerAdapter＃handle 
 * @see  org.springframework.web .servlet.mvc.Controller＃handleRequest
 */
public class ModelAndView {

	/** View instance or view name String. */
	/**
	 * 视图实例或视图名称字符串。 
	 * 
	 */
	@Nullable
	private Object view;

	/** Model Map. */
	/**
	 * 模型图。 
	 * 
	 */
	@Nullable
	private ModelMap model;

	/** Optional HTTP status for the response. */
	/**
	 * 响应的可选HTTP状态。 
	 * 
	 */
	@Nullable
	private HttpStatus status;

	/** Indicates whether or not this instance has been cleared with a call to {@link #clear()}. */
	/**
	 * 指示是否通过调用{@link  #clear（）}清除了此实例。 
	 * 
	 */
	private boolean cleared = false;


	/**
	 * Default constructor for bean-style usage: populating bean
	 * properties instead of passing in constructor arguments.
	 * @see #setView(View)
	 * @see #setViewName(String)
	 */
	/**
	 * Bean风格用法的默认构造函数：填充Bean属性，而不传递构造函数参数。 
	 *  
	 * @see  #setView（视图）
	 * @see  #setViewName（字符串）
	 */
	public ModelAndView() {
	}

	/**
	 * Convenient constructor when there is no model data to expose.
	 * Can also be used in conjunction with {@code addObject}.
	 * @param viewName name of the View to render, to be resolved
	 * by the DispatcherServlet's ViewResolver
	 * @see #addObject
	 */
	/**
	 * 没有要公开的模型数据时的便捷构造函数。 
	 * 也可以与{@code  addObject}结合使用。 
	 *  
	 * @param  viewName要呈现的视图的名称，由DispatcherServlet的ViewResolver解析
	 * @see  #addObject
	 */
	public ModelAndView(String viewName) {
		this.view = viewName;
	}

	/**
	 * Convenient constructor when there is no model data to expose.
	 * Can also be used in conjunction with {@code addObject}.
	 * @param view the View object to render
	 * @see #addObject
	 */
	/**
	 * 没有要公开的模型数据时的便捷构造函数。 
	 * 也可以与{@code  addObject}结合使用。 
	 *  
	 * @param 查看要渲染的View对象
	 * @see  #addObject
	 */
	public ModelAndView(View view) {
		this.view = view;
	}

	/**
	 * Create a new ModelAndView given a view name and a model.
	 * @param viewName name of the View to render, to be resolved
	 * by the DispatcherServlet's ViewResolver
	 * @param model a Map of model names (Strings) to model objects
	 * (Objects). Model entries may not be {@code null}, but the
	 * model Map may be {@code null} if there is no model data.
	 */
	/**
	 * 给定视图名称和模型，创建一个新的ModelAndView。 
	 *  
	 * @param  viewName要呈现的视图的名称，由DispatcherServlet的ViewResolver解析。 
	 * 
	 * @param 模型模型名称（字符串）到模型对象（对象）的映射。 
	 * 模型条目可能不是{@code  null}，但是如果没有模型数据，则模型Map可能是{@code  null}。 
	 * 
	 */
	public ModelAndView(String viewName, @Nullable Map<String, ?> model) {
		this.view = viewName;
		if (model != null) {
			getModelMap().addAllAttributes(model);
		}
	}

	/**
	 * Create a new ModelAndView given a View object and a model.
	 * <em>Note: the supplied model data is copied into the internal
	 * storage of this class. You should not consider to modify the supplied
	 * Map after supplying it to this class</em>
	 * @param view the View object to render
	 * @param model a Map of model names (Strings) to model objects
	 * (Objects). Model entries may not be {@code null}, but the
	 * model Map may be {@code null} if there is no model data.
	 */
	/**
	 * 给定一个View对象和一个模型，创建一个新的ModelAndView。 
	 *  <em>注意：将提供的模型数据复制到此类的内部存储器中。 
	 * 将提供的Map提供给此类后，您不应该考虑对其进行修改。 
	 * </ em> 
	 * @param 查看View对象以将
	 * @param 模型呈现为模型名称（字符串）到模型对象（Object）的Map。 
	 * 模型条目可能不是{@code  null}，但是如果没有模型数据，则模型Map可能是{@code  null}。 
	 * 
	 */
	public ModelAndView(View view, @Nullable Map<String, ?> model) {
		this.view = view;
		if (model != null) {
			getModelMap().addAllAttributes(model);
		}
	}

	/**
	 * Create a new ModelAndView given a view name and HTTP status.
	 * @param viewName name of the View to render, to be resolved
	 * by the DispatcherServlet's ViewResolver
	 * @param status an HTTP status code to use for the response
	 * (to be set just prior to View rendering)
	 * @since 4.3.8
	 */
	/**
	 * 给定视图名称和HTTP状态，创建一个新的ModelAndView。 
	 *  
	 * @param  viewName要呈现的View的名称，由DispatcherServlet的ViewResolver解析。 
	 * 
	 * @param  status HTTP状态代码，用于响应（在View呈现之前设置）@4.3.8起
	 */
	public ModelAndView(String viewName, HttpStatus status) {
		this.view = viewName;
		this.status = status;
	}

	/**
	 * Create a new ModelAndView given a view name, model, and HTTP status.
	 * @param viewName name of the View to render, to be resolved
	 * by the DispatcherServlet's ViewResolver
	 * @param model a Map of model names (Strings) to model objects
	 * (Objects). Model entries may not be {@code null}, but the
	 * model Map may be {@code null} if there is no model data.
	 * @param status an HTTP status code to use for the response
	 * (to be set just prior to View rendering)
	 * @since 4.3
	 */
	/**
	 * 给定视图名称，模型和HTTP状态，创建一个新的ModelAndView。 
	 *  
	 * @param  viewName要呈现的视图的名称，由DispatcherServlet的ViewResolver解析。 
	 * 
	 * @param 模型模型名称（字符串）到模型对象（对象）的映射。 
	 * 模型条目可能不是{@code  null}，但是如果没有模型数据，则模型Map可能是{@code  null}。 
	 *  
	 * @param  status用于响应的HTTP状态代码（将在View渲染之前设置）@4.3开始
	 */
	public ModelAndView(@Nullable String viewName, @Nullable Map<String, ?> model, @Nullable HttpStatus status) {
		this.view = viewName;
		if (model != null) {
			getModelMap().addAllAttributes(model);
		}
		this.status = status;
	}

	/**
	 * Convenient constructor to take a single model object.
	 * @param viewName name of the View to render, to be resolved
	 * by the DispatcherServlet's ViewResolver
	 * @param modelName name of the single entry in the model
	 * @param modelObject the single model object
	 */
	/**
	 * 方便的构造方法，可以采用单个模型对象。 
	 *  
	 * @param  viewName要呈现的视图的名称，由DispatcherServlet的ViewResolver解析。 
	 * 
	 * @param  modelName模型中单个条目的名称
	 * @param  modelObject单个模型对象
	 */
	public ModelAndView(String viewName, String modelName, Object modelObject) {
		this.view = viewName;
		addObject(modelName, modelObject);
	}

	/**
	 * Convenient constructor to take a single model object.
	 * @param view the View object to render
	 * @param modelName name of the single entry in the model
	 * @param modelObject the single model object
	 */
	/**
	 * 方便的构造方法，可以采用单个模型对象。 
	 *  
	 * @param 查看View对象以呈现
	 * @param  modelName模型中单个条目的名称
	 * @param  modelObject单个模型对象
	 */
	public ModelAndView(View view, String modelName, Object modelObject) {
		this.view = view;
		addObject(modelName, modelObject);
	}


	/**
	 * Set a view name for this ModelAndView, to be resolved by the
	 * DispatcherServlet via a ViewResolver. Will override any
	 * pre-existing view name or View.
	 */
	/**
	 * 设置此ModelAndView的视图名称，该名称将由DispatcherServlet通过ViewResolver解析。 
	 * 将覆盖任何先前存在的视图名称或视图。 
	 * 
	 */
	public void setViewName(@Nullable String viewName) {
		this.view = viewName;
	}

	/**
	 * Return the view name to be resolved by the DispatcherServlet
	 * via a ViewResolver, or {@code null} if we are using a View object.
	 */
	/**
	 * 返回要由DispatcherServlet通过ViewResolver解析的视图名称，如果我们使用的是View对象，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	public String getViewName() {
		return (this.view instanceof String ? (String) this.view : null);
	}

	/**
	 * Set a View object for this ModelAndView. Will override any
	 * pre-existing view name or View.
	 */
	/**
	 * 为此ModelAndView设置一个View对象。 
	 * 将覆盖任何先前存在的视图名称或视图。 
	 * 
	 */
	public void setView(@Nullable View view) {
		this.view = view;
	}

	/**
	 * Return the View object, or {@code null} if we are using a view name
	 * to be resolved by the DispatcherServlet via a ViewResolver.
	 */
	/**
	 * 返回View对象，如果我们使用的视图名称由DispatcherServlet通过ViewResolver解析，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	public View getView() {
		return (this.view instanceof View ? (View) this.view : null);
	}

	/**
	 * Indicate whether or not this {@code ModelAndView} has a view, either
	 * as a view name or as a direct {@link View} instance.
	 */
	/**
	 * 指示此{@code  ModelAndView}是否具有视图，可以是视图名称，也可以是直接的{@link  View}实例。 
	 * 
	 */
	public boolean hasView() {
		return (this.view != null);
	}

	/**
	 * Return whether we use a view reference, i.e. {@code true}
	 * if the view has been specified via a name to be resolved by the
	 * DispatcherServlet via a ViewResolver.
	 */
	/**
	 * 返回是否使用视图引用，即{@code  true}，如果视图是通过名称指定的，DispatcherServlet将通过ViewResolver解析该名称。 
	 * 
	 */
	public boolean isReference() {
		return (this.view instanceof String);
	}

	/**
	 * Return the model map. May return {@code null}.
	 * Called by DispatcherServlet for evaluation of the model.
	 */
	/**
	 * 返回模型图。 
	 * 可能返回{@code  null}。 
	 * 由DispatcherServlet调用以评估模型。 
	 * 
	 */
	@Nullable
	protected Map<String, Object> getModelInternal() {
		return this.model;
	}

	/**
	 * Return the underlying {@code ModelMap} instance (never {@code null}).
	 */
	/**
	 * 返回基础的{@code  ModelMap}实例（永远不返回{@code  null}）。 
	 * 
	 */
	public ModelMap getModelMap() {
		if (this.model == null) {
			this.model = new ModelMap();
		}
		return this.model;
	}

	/**
	 * Return the model map. Never returns {@code null}.
	 * To be called by application code for modifying the model.
	 */
	/**
	 * 返回模型图。 
	 * 从不返回{@code  null}。 
	 * 由应用程序代码调用以修改模型。 
	 * 
	 */
	public Map<String, Object> getModel() {
		return getModelMap();
	}

	/**
	 * Set the HTTP status to use for the response.
	 * <p>The response status is set just prior to View rendering.
	 * @since 4.3
	 */
	/**
	 * 设置用于响应的HTTP状态。 
	 *  <p>仅在View渲染之前设置响应状态。 
	 *  @4.3起
	 */
	public void setStatus(@Nullable HttpStatus status) {
		this.status = status;
	}

	/**
	 * Return the configured HTTP status for the response, if any.
	 * @since 4.3
	 */
	/**
	 * 返回响应的已配置HTTP状态（如果有）。 
	 *  @4.3起
	 */
	@Nullable
	public HttpStatus getStatus() {
		return this.status;
	}


	/**
	 * Add an attribute to the model.
	 * @param attributeName name of the object to add to the model (never {@code null})
	 * @param attributeValue object to add to the model (can be {@code null})
	 * @see ModelMap#addAttribute(String, Object)
	 * @see #getModelMap()
	 */
	/**
	 * 向模型添加属性。 
	 *  
	 * @param  attributeName要添加到模型中的对象的名称（从不{@code  null}）
	 * @param  attributeValue对象要添加到模型中的对象（可以为{@code  null}）
	 * @see  ModelMap＃addAttribute（String，Object）
	 * @see  #getModelMap（）
	 */
	public ModelAndView addObject(String attributeName, @Nullable Object attributeValue) {
		getModelMap().addAttribute(attributeName, attributeValue);
		return this;
	}

	/**
	 * Add an attribute to the model using parameter name generation.
	 * @param attributeValue the object to add to the model (never {@code null})
	 * @see ModelMap#addAttribute(Object)
	 * @see #getModelMap()
	 */
	/**
	 * 使用参数名称生成将属性添加到模型。 
	 *  
	 * @param  attributeValue要添加到模型中的对象（决不{<@@code> null}）
	 * @see  ModelMap＃addAttribute（Object）
	 * @see  #getModelMap（）
	 */
	public ModelAndView addObject(Object attributeValue) {
		getModelMap().addAttribute(attributeValue);
		return this;
	}

	/**
	 * Add all attributes contained in the provided Map to the model.
	 * @param modelMap a Map of attributeName -> attributeValue pairs
	 * @see ModelMap#addAllAttributes(Map)
	 * @see #getModelMap()
	 */
	/**
	 * 将提供的Map中包含的所有属性添加到模型。 
	 *  
	 * @param  modelMap attributeName的映射-> attributeValue对
	 * @see  ModelMap＃addAllAttributes（Map）
	 * @see  #getModelMap（）
	 */
	public ModelAndView addAllObjects(@Nullable Map<String, ?> modelMap) {
		getModelMap().addAllAttributes(modelMap);
		return this;
	}


	/**
	 * Clear the state of this ModelAndView object.
	 * The object will be empty afterwards.
	 * <p>Can be used to suppress rendering of a given ModelAndView object
	 * in the {@code postHandle} method of a HandlerInterceptor.
	 * @see #isEmpty()
	 * @see HandlerInterceptor#postHandle
	 */
	/**
	 * 清除此ModelAndView对象的状态。 
	 * 此后该对象将为空。 
	 *  <p>可用于禁止在HandlerInterceptor的{@code  postHandle}方法中呈现给定的ModelAndView对象。 
	 *  
	 * @see  #isEmpty（）
	 * @see  HandlerInterceptor＃postHandle
	 */
	public void clear() {
		this.view = null;
		this.model = null;
		this.cleared = true;
	}

	/**
	 * Return whether this ModelAndView object is empty,
	 * i.e. whether it does not hold any view and does not contain a model.
	 */
	/**
	 * 返回此ModelAndView对象是否为空，即是否不包含任何视图且不包含模型。 
	 * 
	 */
	public boolean isEmpty() {
		return (this.view == null && CollectionUtils.isEmpty(this.model));
	}

	/**
	 * Return whether this ModelAndView object is empty as a result of a call to {@link #clear}
	 * i.e. whether it does not hold any view and does not contain a model.
	 * <p>Returns {@code false} if any additional state was added to the instance
	 * <strong>after</strong> the call to {@link #clear}.
	 * @see #clear()
	 */
	/**
	 * 返回由于调用{@link  #clear}而导致的ModelAndView对象是否为空，即，该对象是否不包含任何视图且不包含模型。 
	 *  <p>如果在</ strong>对{@link  #clear}的调用之后<strong>添加了任何其他状态，则返回{@code  false}。 
	 *  
	 * @see  #clear（）
	 */
	public boolean wasCleared() {
		return (this.cleared && isEmpty());
	}


	/**
	 * Return diagnostic information about this model and view.
	 */
	/**
	 * 返回有关此模型和视图的诊断信息。 
	 * 
	 */
	@Override
	public String toString() {
		return "ModelAndView [view=" + formatView() + "; model=" + this.model + "]";
	}

	private String formatView() {
		return isReference() ? "\"" + this.view + "\"" : "[" + this.view + "]";
	}

}
