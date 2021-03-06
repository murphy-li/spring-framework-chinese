/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.ui.context;

import org.springframework.context.MessageSource;

/**
 * A Theme can resolve theme-specific messages, codes, file paths, etcetera
 * (e&#46;g&#46; CSS and image files in a web environment).
 * The exposed {@link org.springframework.context.MessageSource} supports
 * theme-specific parameterization and internationalization.
 *
 * @author Juergen Hoeller
 * @since 17.06.2003
 * @see ThemeSource
 * @see org.springframework.web.servlet.ThemeResolver
 */
/**
 * 主题可以解析主题特定的消息，代码，文件路径等（例如，网络环境中的CSS和图像文件）。 
 * 公开的{@link  org.springframework.context.MessageSource}支持特定于主题的参数化和国际化。 
 *  @author  Juergen Hoeller @2003年6月17日起
 * @see  ThemeSource 
 * @see  org.springframework.web.servlet.ThemeResolver
 */
public interface Theme {

	/**
	 * Return the name of the theme.
	 * @return the name of the theme (never {@code null})
	 */
	/**
	 * 返回主题名称。 
	 *  
	 * @return 主题名称（从不{<@@code> null}）
	 */
	String getName();

	/**
	 * Return the specific MessageSource that resolves messages
	 * with respect to this theme.
	 * @return the theme-specific MessageSource (never {@code null})
	 */
	/**
	 * 返回用于解析与此主题相关的消息的特定MessageSource。 
	 *  
	 * @return 特定于主题的MessageSource（决不{<@@code> null}）
	 */
	MessageSource getMessageSource();

}
