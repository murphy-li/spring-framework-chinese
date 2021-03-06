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

package org.springframework.mock.web;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Mock implementation of the {@link javax.servlet.http.HttpSession} interface.
 *
 * <p>As of Spring 5.0, this set of mocks is designed on a Servlet 4.0 baseline.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Mark Fisher
 * @author Sam Brannen
 * @author Vedran Pavic
 * @since 1.0.2
 */
/**
 * {@link  javax.servlet.http.HttpSession}接口的模拟实现。 
 *  <p>从Spring 5.0开始，这组模拟是在Servlet 4.0基线上设计的。 
 *  @author  Juergen Hoeller @author  Rod Johnson @author  Mark Fisher @author  Sam Brannen @author  Vedran Pavic @自1.0.2起
 */
@SuppressWarnings("deprecation")
public class MockHttpSession implements HttpSession {

	/**
	 * The session cookie name.
	 */
	/**
	 * 会话cookie名称。 
	 * 
	 */
	public static final String SESSION_COOKIE_NAME = "JSESSION";


	private static int nextId = 1;

	private String id;

	private final long creationTime = System.currentTimeMillis();

	private int maxInactiveInterval;

	private long lastAccessedTime = System.currentTimeMillis();

	private final ServletContext servletContext;

	private final Map<String, Object> attributes = new LinkedHashMap<>();

	private boolean invalid = false;

	private boolean isNew = true;


	/**
	 * Create a new MockHttpSession with a default {@link MockServletContext}.
	 * @see MockServletContext
	 */
	/**
	 * 使用默认的{@link  MockServletContext}创建一个新的MockHttpSession。 
	 *  
	 * @see  MockServletContext
	 */
	public MockHttpSession() {
		this(null);
	}

	/**
	 * Create a new MockHttpSession.
	 * @param servletContext the ServletContext that the session runs in
	 */
	/**
	 * 创建一个新的MockHttpSession。 
	 *  
	 * @param  servletContext会话运行所在的ServletContext
	 */
	public MockHttpSession(@Nullable ServletContext servletContext) {
		this(servletContext, null);
	}

	/**
	 * Create a new MockHttpSession.
	 * @param servletContext the ServletContext that the session runs in
	 * @param id a unique identifier for this session
	 */
	/**
	 * 创建一个新的MockHttpSession。 
	 *  
	 * @param  servletContext会话在
	 * @param 中运行的ServletContext id此会话的唯一标识符
	 */
	public MockHttpSession(@Nullable ServletContext servletContext, @Nullable String id) {
		this.servletContext = (servletContext != null ? servletContext : new MockServletContext());
		this.id = (id != null ? id : Integer.toString(nextId++));
	}


	@Override
	public long getCreationTime() {
		assertIsValid();
		return this.creationTime;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * As of Servlet 3.1, the id of a session can be changed.
	 * @return the new session id
	 * @since 4.0.3
	 */
	/**
	 * 从Servlet 3.1开始，可以更改会话的ID。 
	 *  
	 * @return 新会话ID @始于4.0.3
	 */
	public String changeSessionId() {
		this.id = Integer.toString(nextId++);
		return this.id;
	}

	public void access() {
		this.lastAccessedTime = System.currentTimeMillis();
		this.isNew = false;
	}

	@Override
	public long getLastAccessedTime() {
		assertIsValid();
		return this.lastAccessedTime;
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}

	@Override
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException("getSessionContext");
	}

	@Override
	public Object getAttribute(String name) {
		assertIsValid();
		Assert.notNull(name, "Attribute name must not be null");
		return this.attributes.get(name);
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		assertIsValid();
		return Collections.enumeration(new LinkedHashSet<>(this.attributes.keySet()));
	}

	@Override
	public String[] getValueNames() {
		assertIsValid();
		return StringUtils.toStringArray(this.attributes.keySet());
	}

	@Override
	public void setAttribute(String name, @Nullable Object value) {
		assertIsValid();
		Assert.notNull(name, "Attribute name must not be null");
		if (value != null) {
			Object oldValue = this.attributes.put(name, value);
			if (value != oldValue) {
				if (oldValue instanceof HttpSessionBindingListener) {
					((HttpSessionBindingListener) oldValue).valueUnbound(new HttpSessionBindingEvent(this, name, oldValue));
				}
				if (value instanceof HttpSessionBindingListener) {
					((HttpSessionBindingListener) value).valueBound(new HttpSessionBindingEvent(this, name, value));
				}
			}
		}
		else {
			removeAttribute(name);
		}
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		assertIsValid();
		Assert.notNull(name, "Attribute name must not be null");
		Object value = this.attributes.remove(name);
		if (value instanceof HttpSessionBindingListener) {
			((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
		}
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	/**
	 * Clear all of this session's attributes.
	 */
	/**
	 * 清除此会话的所有属性。 
	 * 
	 */
	public void clearAttributes() {
		for (Iterator<Map.Entry<String, Object>> it = this.attributes.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			String name = entry.getKey();
			Object value = entry.getValue();
			it.remove();
			if (value instanceof HttpSessionBindingListener) {
				((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
			}
		}
	}

	/**
	 * Invalidates this session then unbinds any objects bound to it.
	 * @throws IllegalStateException if this method is called on an already invalidated session
	 */
	/**
	 * 使该会话无效，然后取消绑定到该会话的任何对象。 
	 *  
	 * @throws  IllegalStateException如果在已经无效的会话上调用此方法
	 */
	@Override
	public void invalidate() {
		assertIsValid();
		this.invalid = true;
		clearAttributes();
	}

	public boolean isInvalid() {
		return this.invalid;
	}

	/**
	 * Convenience method for asserting that this session has not been
	 * {@linkplain #invalidate() invalidated}.
	 * @throws IllegalStateException if this session has been invalidated
	 */
	/**
	 * 断言此会话尚未{@link 普通#invalidate（）无效）的便捷方法。 
	 *  
	 * @throws  IllegalStateException如果此会话已无效
	 */
	private void assertIsValid() {
		Assert.state(!isInvalid(), "The session has already been invalidated");
	}

	public void setNew(boolean value) {
		this.isNew = value;
	}

	@Override
	public boolean isNew() {
		assertIsValid();
		return this.isNew;
	}

	/**
	 * Serialize the attributes of this session into an object that can be
	 * turned into a byte array with standard Java serialization.
	 * @return a representation of this session's serialized state
	 */
	/**
	 * 将此会话的属性序列化为一个对象，该对象可以通过标准Java序列化转换为字节数组。 
	 *  
	 * @return 此会话的序列化状态的表示形式
	 */
	public Serializable serializeState() {
		HashMap<String, Serializable> state = new HashMap<>();
		for (Iterator<Map.Entry<String, Object>> it = this.attributes.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			String name = entry.getKey();
			Object value = entry.getValue();
			it.remove();
			if (value instanceof Serializable) {
				state.put(name, (Serializable) value);
			}
			else {
				// Not serializable... Servlet containers usually automatically
				// unbind the attribute in this case.
				if (value instanceof HttpSessionBindingListener) {
					((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
				}
			}
		}
		return state;
	}

	/**
	 * Deserialize the attributes of this session from a state object created by
	 * {@link #serializeState()}.
	 * @param state a representation of this session's serialized state
	 */
	/**
	 * 从{@link  #serializeState（）}创建的状态对象中反序列化此会话的属性。 
	 *  
	 * @param 表示此会话的序列化状态的表示形式
	 */
	@SuppressWarnings("unchecked")
	public void deserializeState(Serializable state) {
		Assert.isTrue(state instanceof Map, "Serialized state needs to be of type [java.util.Map]");
		this.attributes.putAll((Map<String, Object>) state);
	}

}
