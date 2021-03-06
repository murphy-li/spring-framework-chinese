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

package org.springframework.web.servlet.view.feed;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

/**
 * Abstract superclass for Atom Feed views, using the
 * <a href="https://github.com/rometools/rome">ROME</a> package.
 *
 * <p>><b>NOTE: As of Spring 4.1, this is based on the {@code com.rometools}
 * variant of ROME, version 1.5. Please upgrade your build dependency.</b>
 *
 * <p>Application-specific view classes will extend this class.
 * The view will be held in the subclass itself, not in a template.
 * Main entry points are the {@link #buildFeedMetadata} and {@link #buildFeedEntries}.
 *
 * <p>Thanks to Jettro Coenradie and Sergio Bossa for the original feed view prototype!
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 3.0
 * @see #buildFeedMetadata
 * @see #buildFeedEntries
 * @see <a href="https://www.atomenabled.org/developers/syndication/">Atom Syndication Format</a>
 */
/**
 * 使用<a href="https://github.com/rometools/rome"> ROME </a>软件包的Atom Feed视图的抽象超类。 
 *  <p >> <b>注意：从Spring 4.1开始，它基于ROME 1.5版的{@code  com.rometools}变体。 
 * 请升级您的构建依赖项。 
 * </ b> <p>特定于应用程序的视图类将扩展此类。 
 * 视图将保存在子类本身中，而不是模板中。 
 * 主要入口点是{@link  #buildFeedMetadata}和{@link  #buildFeedEntries}。 
 *  <p>感谢Jettro Coenradie和Sergio Bossa提供了原始供稿视图原型！ 
 *  @author  Arjen Poutsma @author  Juergen Hoeller @从3.0开始
 * @see  #buildFeedMetadata 
 * @see  #buildFeedEntries 
 * @see  <a href ="https://www.atomenabled.org/developers/syndication/ "> Atom联合组织格式</a>
 */
public abstract class AbstractAtomFeedView extends AbstractFeedView<Feed> {

	/**
	 * The default feed type used.
	 */
	/**
	 * 使用的默认供稿类型。 
	 * 
	 */
	public static final String DEFAULT_FEED_TYPE = "atom_1.0";

	private String feedType = DEFAULT_FEED_TYPE;


	public AbstractAtomFeedView() {
		setContentType("application/atom+xml");
	}

	/**
	 * Set the Rome feed type to use.
	 * <p>Defaults to Atom 1.0.
	 * @see Feed#setFeedType(String)
	 * @see #DEFAULT_FEED_TYPE
	 */
	/**
	 * 设置要使用的罗马提要类型。 
	 *  <p>默认为Atom 1.0。 
	 *  
	 * @see  Feed＃setFeedType（String）
	 * @see  #DEFAULT_FEED_TYPE
	 */
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	/**
	 * Create a new Feed instance to hold the entries.
	 * <p>By default returns an Atom 1.0 feed, but the subclass can specify any Feed.
	 * @see #setFeedType(String)
	 */
	/**
	 * 创建一个新的Feed实例来保存条目。 
	 *  <p>默认情况下会返回Atom 1.0 Feed，但是子类可以指定任何Feed。 
	 *  
	 * @see  #setFeedType（String）
	 */
	@Override
	protected Feed newFeed() {
		return new Feed(this.feedType);
	}

	/**
	 * Invokes {@link #buildFeedEntries(Map, HttpServletRequest, HttpServletResponse)}
	 * to get a list of feed entries.
	 */
	/**
	 * 调用{@link  #buildFeedEntries（Map，HttpServletRequest，HttpServletResponse）}以获取提要条目的列表。 
	 * 
	 */
	@Override
	protected final void buildFeedEntries(Map<String, Object> model, Feed feed,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Entry> entries = buildFeedEntries(model, request, response);
		feed.setEntries(entries);
	}

	/**
	 * Subclasses must implement this method to build feed entries, given the model.
	 * <p>Note that the passed-in HTTP response is just supposed to be used for
	 * setting cookies or other HTTP headers. The built feed itself will automatically
	 * get written to the response after this method returns.
	 * @param model	the model Map
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 * @return the feed entries to be added to the feed
	 * @throws Exception any exception that occurred during document building
	 * @see Entry
	 */
	/**
	 * 给定模型，子类必须实现此方法才能构建提要条目。 
	 *  <p>请注意，传入的HTTP响应仅应用于设置cookie或其他HTTP标头。 
	 * 此方法返回后，内置的提要本身将自动写入响应中。 
	 *  
	 * @param 为模型Map 
	 * @param 请求建模，以防我们需要语言环境等。 
	 * 不应查看属性。 
	 *  
	 * @param 响应，以防我们需要设置cookie。 
	 * 不应该写。 
	 *  
	 * @return 要添加到feed中的feed条目
	 * @throws 例外，在文档构建期间发生的任何异常
	 * @see 
	 */
	protected abstract List<Entry> buildFeedEntries(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
