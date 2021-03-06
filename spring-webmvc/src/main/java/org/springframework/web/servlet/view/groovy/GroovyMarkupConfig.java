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

package org.springframework.web.servlet.view.groovy;

import groovy.text.markup.MarkupTemplateEngine;

/**
 * Interface to be implemented by objects that configure and manage a Groovy
 * {@link MarkupTemplateEngine} for automatic lookup in a web environment.
 * Detected and used by {@link GroovyMarkupView}.
 *
 * @author Brian Clozel
 * @since 4.1
 * @see GroovyMarkupConfigurer
 */
/**
 * 由配置和管理Groovy {@link  MarkupTemplateEngine}以便在Web环境中自动查找的对象所实现的接口。 
 * 由{@link  GroovyMarkupView}检测和使用。 
 *  @author  Brian Clozel @始于4.1 
 * @see  GroovyMarkupConfigurer
 */
public interface GroovyMarkupConfig {

	/**
	 * Return the Groovy {@link MarkupTemplateEngine} for the current
	 * web application context. May be unique to one servlet, or shared
	 * in the root context.
	 * @return the Groovy MarkupTemplateEngine engine
	 */
	/**
	 * 返回当前Web应用程序上下文的Groovy {@link  MarkupTemplateEngine}。 
	 * 对于一个servlet可能是唯一的，或在根上下文中共享。 
	 *  
	 * @return  Groovy MarkupTemplateEngine引擎
	 */
	MarkupTemplateEngine getTemplateEngine();

}
