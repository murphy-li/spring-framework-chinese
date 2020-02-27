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

package org.springframework.web.reactive.result.view.script;

import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

/**
 * Convenience subclass of {@link UrlBasedViewResolver} that supports
 * {@link ScriptTemplateView} and custom subclasses of it.
 *
 * <p>The view class for all views created by this resolver can be specified
 * via the {@link #setViewClass(Class)} property.
 *
 * <p><b>Note:</b> When chaining ViewResolvers this resolver will check for the
 * existence of the specified template resources and only return a non-null
 * View object if a template is actually found.
 *
 * @author Sebastien Deleuze
 * @since 5.0
 * @see ScriptTemplateConfigurer
 */
/**
 * {@link  UrlBasedViewResolver}的便利子类支持{@link  ScriptTemplateView}及其自定义子类。 
 *  <p>可以通过{@link  #setViewClass（Class）}属性指定此解析器创建的所有视图的视图类。 
 *  <p> <b>注意：</ b>链接ViewResolvers时，此解析器将检查指定模板资源的存在，并且仅在实际找到模板时才返回非null的View对象。 
 *  @author 塞巴斯蒂安·德勒兹（Sebastien Deleuze）@从5.0开始
 * @see  ScriptTemplateConfigurer
 */
public class ScriptTemplateViewResolver extends UrlBasedViewResolver {

	/**
	 * Sets the default {@link #setViewClass view class} to {@link #requiredViewClass}:
	 * by default {@link ScriptTemplateView}.
	 */
	/**
	 * 将默认的{@link  #setViewClass视图类}设置为{@link  #requiredViewClass}：默认为{@link  ScriptTemplateView}。 
	 * 
	 */
	public ScriptTemplateViewResolver() {
		setViewClass(requiredViewClass());
	}

	/**
	 * A convenience constructor that allows for specifying {@link #setPrefix prefix}
	 * and {@link #setSuffix suffix} as constructor arguments.
	 * @param prefix the prefix that gets prepended to view names when building a URL
	 * @param suffix the suffix that gets appended to view names when building a URL
	 */
	/**
	 * 一个方便的构造函数，它允许指定{@link  #setPrefix前缀}和{@link  #setSuffix后缀}作为构造函数参数。 
	 *  
	 * @param 前缀是在构建URL时被前缀为视图名称的前缀
	 * @param 后缀在构建URL时被附加为视图名称的后缀
	 */
	public ScriptTemplateViewResolver(String prefix, String suffix) {
		this();
		setPrefix(prefix);
		setSuffix(suffix);
	}


	@Override
	protected Class<?> requiredViewClass() {
		return ScriptTemplateView.class;
	}

}
