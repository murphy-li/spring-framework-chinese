/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2015 the original author or authors.
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
 * 版权所有2002-2015的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.scripting.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@code NamespaceHandler} that supports the wiring of
 * objects backed by dynamic languages such as Groovy, JRuby and
 * BeanShell. The following is an example (from the reference
 * documentation) that details the wiring of a Groovy backed bean:
 *
 * <pre class="code">
 * &lt;lang:groovy id="messenger"
 *     refresh-check-delay="5000"
 *     script-source="classpath:Messenger.groovy"&gt;
 * &lt;lang:property name="message" value="I Can Do The Frug"/&gt;
 * &lt;/lang:groovy&gt;
 * </pre>
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 2.0
 */
/**
 * {@code  NamespaceHandler}支持对由动态语言（例如Groovy，JRuby和BeanShell）支持的对象进行连接。 
 * 以下是一个示例（来自参考文档），详细说明了Groovy支持的bean的连接：<pre class ="code"> <lang：groovy id ="messenger"refresh-check-delay ="5000"脚本源="classpath：Messenger.groovy"> <lang：属性名称="message"value ="我可以节俭"/> </ lang：groovy> </ pre> @author  Rob Harrop @author  Juergen Hoeller @author 马克·费舍尔@since 2.0
 */
public class LangNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerScriptBeanDefinitionParser("groovy", "org.springframework.scripting.groovy.GroovyScriptFactory");
		registerScriptBeanDefinitionParser("bsh", "org.springframework.scripting.bsh.BshScriptFactory");
		registerScriptBeanDefinitionParser("std", "org.springframework.scripting.support.StandardScriptFactory");
		registerBeanDefinitionParser("defaults", new ScriptingDefaultsParser());
	}

	private void registerScriptBeanDefinitionParser(String key, String scriptFactoryClassName) {
		registerBeanDefinitionParser(key, new ScriptBeanDefinitionParser(scriptFactoryClassName));
	}

}
