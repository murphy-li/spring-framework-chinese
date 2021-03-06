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

package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * {@link java.beans.PropertyEditor} implementation for standard JDK
 * {@link java.util.ResourceBundle ResourceBundles}.
 *
 * <p>Only supports conversion <i>from</i> a String, but not <i>to</i> a String.
 *
 * Find below some examples of using this class in a (properly configured)
 * Spring container using XML-based metadata:
 *
 * <pre class="code"> &lt;bean id="errorDialog" class="..."&gt;
 *    &lt;!--
 *        the 'messages' property is of type java.util.ResourceBundle.
 *        the 'DialogMessages.properties' file exists at the root of the CLASSPATH
 *    --&gt;
 *    &lt;property name="messages" value="DialogMessages"/&gt;
 * &lt;/bean&gt;</pre>
 *
 * <pre class="code"> &lt;bean id="errorDialog" class="..."&gt;
 *    &lt;!--
 *        the 'DialogMessages.properties' file exists in the 'com/messages' package
 *    --&gt;
 *    &lt;property name="messages" value="com/messages/DialogMessages"/&gt;
 * &lt;/bean&gt;</pre>
 *
 * <p>A 'properly configured' Spring {@link org.springframework.context.ApplicationContext container}
 * might contain a {@link org.springframework.beans.factory.config.CustomEditorConfigurer}
 * definition such that the conversion can be effected transparently:
 *
 * <pre class="code"> &lt;bean class="org.springframework.beans.factory.config.CustomEditorConfigurer"&gt;
 *    &lt;property name="customEditors"&gt;
 *        &lt;map&gt;
 *            &lt;entry key="java.util.ResourceBundle"&gt;
 *                &lt;bean class="org.springframework.beans.propertyeditors.ResourceBundleEditor"/&gt;
 *            &lt;/entry&gt;
 *        &lt;/map&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;</pre>
 *
 * <p>Please note that this {@link java.beans.PropertyEditor} is <b>not</b>
 * registered by default with any of the Spring infrastructure.
 *
 * <p>Thanks to David Leal Valmana for the suggestion and initial prototype.
 *
 * @author Rick Evans
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 标准JDK {@link  java.util.ResourceBundle ResourceBundles}的{@link  java.beans.PropertyEditor}实现。 
 *  <p>仅支持将<i>从</ i>转换为字符串，但不支持将<i>转换为</ i>。 
 * 在下面找到一些使用基于XML的元数据在（正确配置的）Spring容器中使用此类的示例：<pre class ="code"> <bean id ="errorDialog"class ="..."> <！ 
 * - "messages"属性的类型为java.util.ResourceBundle。 
 *  "DialogMessages.properties"文件位于CLASSPATH的根目录-> <属性名称="messages"value ="DialogMessages"/> </ bean> </ pre> <pre class ="code"> <bean id ="errorDialog"class ="..."> <！ 
 * -'DialogMessages.properties'文件存在于'com / messages'包中-> <属性名称="messages"value ="com / messages / DialogMessages "/> </ bean> </ pre> <p>"正确配置"的Spring {@link  org.springframework.context.ApplicationContext容器}可能包含一个{@link  org.springframework.beans.factory。 
 *  config.CustomEditorConfigurer}定义，以便可以透明地进行转换：<pre class ="code"> <bean class ="org.springframework.beans.factory.config.CustomEditorConfigurer"> <property name ="customEditors"> <map > <entry key ="java.util.ResourceBundle"> <bean class ="org.springframework.beans.propertyeditors.ResourceBundleEditor"/> </ entry> </ map> </ property> </ bean> </ pre> <p>请注意，此{@link  java.beans.PropertyEditor}是<b>未</ b>默认情况下未在任何Spring基础结构中注册。 
 *  <p>感谢David Leal Valmana的建议和最初的原型。 
 *  @author 里克·埃文斯（Rick Evans）@author  Juergen Hoeller @始于2.0
 */
public class ResourceBundleEditor extends PropertyEditorSupport {

	/**
	 * The separator used to distinguish between the base name and the locale
	 * (if any) when {@link #setAsText(String) converting from a String}.
	 */
	/**
	 * {{@link> #setAsText（String）从字符串转换}时，用于区分基本名称和语言环境（如果有）的分隔符。 
	 * 
	 */
	public static final String BASE_NAME_SEPARATOR = "_";


	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Assert.hasText(text, "'text' must not be empty");
		String name = text.trim();

		int separator = name.indexOf(BASE_NAME_SEPARATOR);
		if (separator == -1) {
			setValue(ResourceBundle.getBundle(name));
		}
		else {
			// The name potentially contains locale information
			String baseName = name.substring(0, separator);
			if (!StringUtils.hasText(baseName)) {
				throw new IllegalArgumentException("Invalid ResourceBundle name: '" + text + "'");
			}
			String localeString = name.substring(separator + 1);
			Locale locale = StringUtils.parseLocaleString(localeString);
			setValue(locale != null ? ResourceBundle.getBundle(baseName, locale) : ResourceBundle.getBundle(baseName));
		}
	}

}
