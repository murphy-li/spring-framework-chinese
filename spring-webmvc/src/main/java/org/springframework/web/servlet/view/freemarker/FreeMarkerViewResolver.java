/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2009 the original author or authors.
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
 * 版权所有2002-2009的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.servlet.view.freemarker;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * Convenience subclass of {@link org.springframework.web.servlet.view.UrlBasedViewResolver}
 * that supports {@link FreeMarkerView} (i.e. FreeMarker templates) and custom subclasses of it.
 *
 * <p>The view class for all views generated by this resolver can be specified
 * via the "viewClass" property. See UrlBasedViewResolver's javadoc for details.
 *
 * <p><b>Note:</b> When chaining ViewResolvers, a FreeMarkerViewResolver will
 * check for the existence of the specified template resources and only return
 * a non-null View object if the template was actually found.
 *
 * @author Juergen Hoeller
 * @since 1.1
 * @see #setViewClass
 * @see #setPrefix
 * @see #setSuffix
 * @see #setRequestContextAttribute
 * @see #setExposeSpringMacroHelpers
 * @see FreeMarkerView
 */
/**
 * {@link  org.springframework.web.servlet.view.UrlBasedViewResolver}的便利子类，它支持{@link  FreeMarkerView}（即FreeMarker模板）及其自定义子类。 
 *  <p>可以通过"viewClass"属性指定此解析器生成的所有视图的视图类。 
 * 有关详细信息，请参见UrlBasedViewResolver的javadoc。 
 *  <p> <b>注意：</ b>链接ViewResolvers时，FreeMarkerViewResolver将检查指定模板资源的存在，并且仅在实际找到模板时才返回非null的View对象。 
 *  @author  Juergen Hoeller @since 1.1起
 * @see  #setViewClass 
 * @see  #setPrefix 
 * @see  #setSuffix 
 * @see  #setRequestContextAttribute 
 * @see  #setExposeSpringMacroHelpers 
 * @see  FreeMarkerView
 */
public class FreeMarkerViewResolver extends AbstractTemplateViewResolver {

	/**
	 * Sets the default {@link #setViewClass view class} to {@link #requiredViewClass}:
	 * by default {@link FreeMarkerView}.
	 */
	/**
	 * 将默认的{@link  #setViewClass视图类}设置为{@link  #requiredViewClass}：默认为{@link  FreeMarkerView}。 
	 * 
	 */
	public FreeMarkerViewResolver() {
		setViewClass(requiredViewClass());
	}

	/**
	 * A convenience constructor that allows for specifying {@link #setPrefix prefix}
	 * and {@link #setSuffix suffix} as constructor arguments.
	 * @param prefix the prefix that gets prepended to view names when building a URL
	 * @param suffix the suffix that gets appended to view names when building a URL
	 * @since 4.3
	 */
	/**
	 * 一个方便的构造函数，它允许指定{@link  #setPrefix前缀}和{@link  #setSuffix后缀}作为构造函数参数。 
	 *  
	 * @param 前缀是在构建URL时前缀以查看名称的前缀
	 * @param 后缀在构建URL时要附加视图名称的后缀@since 4.3
	 */
	public FreeMarkerViewResolver(String prefix, String suffix) {
		this();
		setPrefix(prefix);
		setSuffix(suffix);
	}


	/**
	 * Requires {@link FreeMarkerView}.
	 */
	/**
	 * 需要{@link  FreeMarkerView}。 
	 * 
	 */
	@Override
	protected Class<?> requiredViewClass() {
		return FreeMarkerView.class;
	}

}
