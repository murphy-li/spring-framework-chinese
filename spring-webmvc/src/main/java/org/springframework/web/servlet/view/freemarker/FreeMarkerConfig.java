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

package org.springframework.web.servlet.view.freemarker;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;

/**
 * Interface to be implemented by objects that configure and manage a
 * FreeMarker Configuration object in a web environment. Detected and
 * used by {@link FreeMarkerView}.
 *
 * @author Darren Davison
 * @author Rob Harrop
 * @since 03.03.2004
 * @see FreeMarkerConfigurer
 * @see FreeMarkerView
 */
/**
 * 由在Web环境中配置和管理FreeMarker Configuration对象的对象所实现的接口。 
 * 由{@link  FreeMarkerView}检测和使用。 
 *  @author  Darren Davison @author  Rob Harrop @2004年3月3日起
 * @see  FreeMarkerConfigurer 
 * @see  FreeMarkerView
 */
public interface FreeMarkerConfig {

	/**
	 * Return the FreeMarker {@link Configuration} object for the current
	 * web application context.
	 * <p>A FreeMarker Configuration object may be used to set FreeMarker
	 * properties and shared objects, and allows to retrieve templates.
	 * @return the FreeMarker Configuration
	 */
	/**
	 * 返回当前Web应用程序上下文的FreeMarker {@link  Configuration}对象。 
	 *  <p> FreeMarker配置对象可用于设置FreeMarker属性和共享对象，并允许检索模板。 
	 *  
	 * @return  FreeMarker配置
	 */
	Configuration getConfiguration();

	/**
	 * Return the {@link TaglibFactory} used to enable JSP tags to be
	 * accessed from FreeMarker templates.
	 */
	/**
	 * 返回用于允许从FreeMarker模板访问JSP标记的{@link  TaglibFactory}。 
	 * 
	 */
	TaglibFactory getTaglibFactory();

}
