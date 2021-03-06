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

package org.springframework.beans.factory.config;

import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

/**
 * Subclass of PropertyPlaceholderConfigurer that supports JDK 1.4's
 * Preferences API ({@code java.util.prefs}).
 *
 * <p>Tries to resolve placeholders as keys first in the user preferences,
 * then in the system preferences, then in this configurer's properties.
 * Thus, behaves like PropertyPlaceholderConfigurer if no corresponding
 * preferences defined.
 *
 * <p>Supports custom paths for the system and user preferences trees. Also
 * supports custom paths specified in placeholders ("myPath/myPlaceholderKey").
 * Uses the respective root node if not specified.
 *
 * @author Juergen Hoeller
 * @since 16.02.2004
 * @see #setSystemTreePath
 * @see #setUserTreePath
 * @see java.util.prefs.Preferences
 * @deprecated as of 5.2, along with {@link PropertyPlaceholderConfigurer}
 */
/**
 * 支持JDK 1.4的Preferences API（{@code  java.util.prefs}）的PropertyPlaceholderConfigurer的子类。 
 *  <p>尝试首先在用户首选项中，然后在系统首选项中，然后在此配置程序的属性中，将占位符解析为键。 
 * 因此，如果未定义相应的首选项，则其行为类似于PropertyPlaceholderConfigurer。 
 *  <p>支持系统和用户首选项树的自定义路径。 
 * 还支持在占位符（"myPath / myPlaceholderKey"）中指定的自定义路径。 
 * 如果未指定，则使用相应的根节点。 
 *  @author  Juergen Hoeller @2004年2月16日以来
 * @see  #setSystemTreePath 
 * @see  #setUserTreePath 
 * @see  java.util.prefs.Preferences @从5.2开始不推荐使用，以及{@link  PropertyPlaceholderConfigurer}
 */
@Deprecated
public class PreferencesPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {

	@Nullable
	private String systemTreePath;

	@Nullable
	private String userTreePath;

	private Preferences systemPrefs = Preferences.systemRoot();

	private Preferences userPrefs = Preferences.userRoot();


	/**
	 * Set the path in the system preferences tree to use for resolving
	 * placeholders. Default is the root node.
	 */
	/**
	 * 在系统偏好树中设置路径以解析占位符。 
	 * 默认值为根节点。 
	 * 
	 */
	public void setSystemTreePath(String systemTreePath) {
		this.systemTreePath = systemTreePath;
	}

	/**
	 * Set the path in the system preferences tree to use for resolving
	 * placeholders. Default is the root node.
	 */
	/**
	 * 在系统偏好树中设置路径以解析占位符。 
	 * 默认值为根节点。 
	 * 
	 */
	public void setUserTreePath(String userTreePath) {
		this.userTreePath = userTreePath;
	}


	/**
	 * This implementation eagerly fetches the Preferences instances
	 * for the required system and user tree nodes.
	 */
	/**
	 * 此实现急切地获取所需的系统和用户树节点的Preferences实例。 
	 * 
	 */
	@Override
	public void afterPropertiesSet() {
		if (this.systemTreePath != null) {
			this.systemPrefs = this.systemPrefs.node(this.systemTreePath);
		}
		if (this.userTreePath != null) {
			this.userPrefs = this.userPrefs.node(this.userTreePath);
		}
	}

	/**
	 * This implementation tries to resolve placeholders as keys first
	 * in the user preferences, then in the system preferences, then in
	 * the passed-in properties.
	 */
	/**
	 * 此实现尝试首先在用户首选项中，然后在系统首选项中，然后在传入的属性中，将占位符解析为键。 
	 * 
	 */
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String path = null;
		String key = placeholder;
		int endOfPath = placeholder.lastIndexOf('/');
		if (endOfPath != -1) {
			path = placeholder.substring(0, endOfPath);
			key = placeholder.substring(endOfPath + 1);
		}
		String value = resolvePlaceholder(path, key, this.userPrefs);
		if (value == null) {
			value = resolvePlaceholder(path, key, this.systemPrefs);
			if (value == null) {
				value = props.getProperty(placeholder);
			}
		}
		return value;
	}

	/**
	 * Resolve the given path and key against the given Preferences.
	 * @param path the preferences path (placeholder part before '/')
	 * @param key the preferences key (placeholder part after '/')
	 * @param preferences the Preferences to resolve against
	 * @return the value for the placeholder, or {@code null} if none found
	 */
	/**
	 * 根据给定的首选项解析给定的路径和键。 
	 *  
	 * @param 路径首选项路径（'/'之前的占位符部分）
	 * @param 键首选项键（'/'之后的占位符部分）
	 * @param 首选项以
	 * @return 的值来解析首选项占位符，如果找不到{{@@code> null}
	 */
	@Nullable
	protected String resolvePlaceholder(@Nullable String path, String key, Preferences preferences) {
		if (path != null) {
			// Do not create the node if it does not exist...
			try {
				if (preferences.nodeExists(path)) {
					return preferences.node(path).get(key, null);
				}
				else {
					return null;
				}
			}
			catch (BackingStoreException ex) {
				throw new BeanDefinitionStoreException("Cannot access specified node path [" + path + "]", ex);
			}
		}
		else {
			return preferences.get(key, null);
		}
	}

}
