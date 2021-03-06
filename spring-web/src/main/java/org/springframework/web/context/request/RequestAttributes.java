/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.context.request;

import org.springframework.lang.Nullable;

/**
 * Abstraction for accessing attribute objects associated with a request.
 * Supports access to request-scoped attributes as well as to session-scoped
 * attributes, with the optional notion of a "global session".
 *
 * <p>Can be implemented for any kind of request/session mechanism,
 * in particular for servlet requests.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see ServletRequestAttributes
 */
/**
 * 用于访问与请求关联的属性对象的抽象。 
 * 支持使用"全局会话"的可选概念访问请求范围的属性以及会话范围的属性。 
 *  <p>可以为任何类型的请求/会话机制实现，尤其是对于servlet请求。 
 *  @author  Juergen Hoeller @始于2.0 
 * @see  ServletRequestAttributes
 */
public interface RequestAttributes {

	/**
	 * Constant that indicates request scope.
	 */
	/**
	 * 指示请求范围的常数。 
	 * 
	 */
	int SCOPE_REQUEST = 0;

	/**
	 * Constant that indicates session scope.
	 * <p>This preferably refers to a locally isolated session, if such
	 * a distinction is available.
	 * Else, it simply refers to the common session.
	 */
	/**
	 * 指示会话范围的常数。 
	 *  <p>如果有这样的区别，它最好是指本地隔离的会话。 
	 * 否则，它仅指普通会议。 
	 * 
	 */
	int SCOPE_SESSION = 1;


	/**
	 * Name of the standard reference to the request object: "request".
	 * @see #resolveReference
	 */
	/**
	 * 对请求对象的标准引用的名称："request"。 
	 *  
	 * @see  #resolve参考
	 */
	String REFERENCE_REQUEST = "request";

	/**
	 * Name of the standard reference to the session object: "session".
	 * @see #resolveReference
	 */
	/**
	 * 会话对象的标准引用名称："会话"。 
	 *  
	 * @see  #resolve参考
	 */
	String REFERENCE_SESSION = "session";


	/**
	 * Return the value for the scoped attribute of the given name, if any.
	 * @param name the name of the attribute
	 * @param scope the scope identifier
	 * @return the current attribute value, or {@code null} if not found
	 */
	/**
	 * 返回给定名称的范围属性的值（如果有）。 
	 *  
	 * @param 命名属性的名称
	 * @param 范围范围标识符
	 * @return 当前属性值，如果找不到，则为{@code  null}
	 */
	@Nullable
	Object getAttribute(String name, int scope);

	/**
	 * Set the value for the scoped attribute of the given name,
	 * replacing an existing value (if any).
	 * @param name the name of the attribute
	 * @param scope the scope identifier
	 * @param value the value for the attribute
	 */
	/**
	 * 设置给定名称的范围属性的值，替换现有值（如果有）。 
	 *  
	 * @param 名称属性的名称
	 * @param 范围范围标识符
	 * @param 值属性的值
	 */
	void setAttribute(String name, Object value, int scope);

	/**
	 * Remove the scoped attribute of the given name, if it exists.
	 * <p>Note that an implementation should also remove a registered destruction
	 * callback for the specified attribute, if any. It does, however, <i>not</i>
	 * need to <i>execute</i> a registered destruction callback in this case,
	 * since the object will be destroyed by the caller (if appropriate).
	 * @param name the name of the attribute
	 * @param scope the scope identifier
	 */
	/**
	 * 删除给定名称的范围属性（如果存在）。 
	 *  <p>请注意，实现还应删除指定属性的已注册销毁回调（如果有）。 
	 * 但是，在这种情况下，<i>不需要</ i>执行<i>执行</ i>已注册的销毁回调，因为调用方将销毁该对象（如果适用）。 
	 *  
	 * @param 名称属性的名称
	 * @param 作用域作用域标识符
	 */
	void removeAttribute(String name, int scope);

	/**
	 * Retrieve the names of all attributes in the scope.
	 * @param scope the scope identifier
	 * @return the attribute names as String array
	 */
	/**
	 * 检索范围中所有属性的名称。 
	 *  
	 * @param 作用域作用域标识符
	 * @return 属性名称为String数组
	 */
	String[] getAttributeNames(int scope);

	/**
	 * Register a callback to be executed on destruction of the
	 * specified attribute in the given scope.
	 * <p>Implementations should do their best to execute the callback
	 * at the appropriate time: that is, at request completion or session
	 * termination, respectively. If such a callback is not supported by the
	 * underlying runtime environment, the callback <i>must be ignored</i>
	 * and a corresponding warning should be logged.
	 * <p>Note that 'destruction' usually corresponds to destruction of the
	 * entire scope, not to the individual attribute having been explicitly
	 * removed by the application. If an attribute gets removed via this
	 * facade's {@link #removeAttribute(String, int)} method, any registered
	 * destruction callback should be disabled as well, assuming that the
	 * removed object will be reused or manually destroyed.
	 * <p><b>NOTE:</b> Callback objects should generally be serializable if
	 * they are being registered for a session scope. Otherwise the callback
	 * (or even the entire session) might not survive web app restarts.
	 * @param name the name of the attribute to register the callback for
	 * @param callback the destruction callback to be executed
	 * @param scope the scope identifier
	 */
	/**
	 * 注册在给定范围内销毁指定属性时要执行的回调。 
	 *  <p>实现应尽力在适当的时间执行回调：即分别在请求完成或会话终止时。 
	 * 如果基础运行时环境不支持此类回调，则必须忽略回调<i> </ i>，并记录相应的警告。 
	 *  <p>请注意，"销毁"通常对应于整个范围的破坏，而不是应用程序已明确删除的单个属性。 
	 * 如果通过此立面的{@link  #removeAttribute（String，int）}方法删除了属性，则假定已删除的对象将被重用或手动销毁，任何已注册的销毁回调也应被禁用。 
	 *  <p> <b>注意：</ b>如果正在为会话作用域注册回调对象，则它们通常应该可序列化。 
	 * 否则，回调（甚至整个会话）可能无法在Web应用重新启动后继续存在。 
	 *  
	 * @param 名称为要注册的回调的属性名称
	 * @param 回调要执行的销毁回调
	 * @param 范围范围标识符
	 */
	void registerDestructionCallback(String name, Runnable callback, int scope);

	/**
	 * Resolve the contextual reference for the given key, if any.
	 * <p>At a minimum: the HttpServletRequest reference for key "request", and
	 * the HttpSession reference for key "session".
	 * @param key the contextual key
	 * @return the corresponding object, or {@code null} if none found
	 */
	/**
	 * 解决给定键的上下文引用（如果有）。 
	 *  <p>至少：键"request"的HttpServletRequest引用和键"session"的HttpSession引用。 
	 *  
	 * @param 键，将上下文键
	 * @return 键对应的对象； 
	 * 如果未找到，则为{@code  null}
	 */
	@Nullable
	Object resolveReference(String key);

	/**
	 * Return an id for the current underlying session.
	 * @return the session id as String (never {@code null})
	 */
	/**
	 * 返回当前基础会话的ID。 
	 *  
	 * @return 会话ID为String（永远不为{@code  null}）
	 */
	String getSessionId();

	/**
	 * Expose the best available mutex for the underlying session:
	 * that is, an object to synchronize on for the underlying session.
	 * @return the session mutex to use (never {@code null})
	 */
	/**
	 * 公开基础会话的最佳可用互斥量：即，用于基础会话同步的对象。 
	 *  
	 * @return 要使用的会话互斥量（永不{@code  null}）
	 */
	Object getSessionMutex();

}
