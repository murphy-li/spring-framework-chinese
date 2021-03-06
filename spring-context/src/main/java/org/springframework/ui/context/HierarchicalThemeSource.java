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

import org.springframework.lang.Nullable;

/**
 * Sub-interface of ThemeSource to be implemented by objects that
 * can resolve theme messages hierarchically.
 *
 * @author Jean-Pierre Pawlak
 * @author Juergen Hoeller
 */
/**
 * 由可以分层解析主题消息的对象实现的ThemeSource子接口。 
 *  @author  Jean-Pierre Pawlak @author 于尔根·霍勒（Juergen Hoeller）
 */
public interface HierarchicalThemeSource extends ThemeSource {

	/**
	 * Set the parent that will be used to try to resolve theme messages
	 * that this object can't resolve.
	 * @param parent the parent ThemeSource that will be used to
	 * resolve messages that this object can't resolve.
	 * May be {@code null}, in which case no further resolution is possible.
	 */
	/**
	 * 设置将用于尝试解决该对象无法解析的主题消息的父对象。 
	 *  
	 * @param 父级父级ThemeSource，它将用于解决该对象无法解析的消息。 
	 * 可能为{@code  null}，在这种情况下无法进一步解决。 
	 * 
	 */
	void setParentThemeSource(@Nullable ThemeSource parent);

	/**
	 * Return the parent of this ThemeSource, or {@code null} if none.
	 */
	/**
	 * 返回此ThemeSource的父级，如果没有，则返回{@code  null}。 
	 * 
	 */
	@Nullable
	ThemeSource getParentThemeSource();

}
