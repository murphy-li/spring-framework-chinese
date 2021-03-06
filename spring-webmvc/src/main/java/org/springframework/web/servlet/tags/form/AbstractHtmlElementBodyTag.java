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

package org.springframework.web.servlet.tags.form;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Convenient super class for many html tags that render content using the databinding
 * features of the {@link AbstractHtmlElementTag AbstractHtmlElementTag}. The only thing
 * sub-tags need to do is override {@link #renderDefaultContent(TagWriter)}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 方便的超类，用于许多HTML标签，这些标签使用{@link  AbstractHtmlElementTag AbstractHtmlElementTag}的数据绑定功能来呈现内容。 
 * 子标签唯一需要做的就是重写{@link  #renderDefaultContent（TagWriter）}。 
 *  @author 罗布·哈罗普（Rob Harrop）@author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public abstract class AbstractHtmlElementBodyTag extends AbstractHtmlElementTag implements BodyTag {

	@Nullable
	private BodyContent bodyContent;

	@Nullable
	private TagWriter tagWriter;


	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		onWriteTagContent();
		this.tagWriter = tagWriter;
		if (shouldRender()) {
			exposeAttributes();
			return EVAL_BODY_BUFFERED;
		}
		else {
			return SKIP_BODY;
		}
	}

	/**
	 * If {@link #shouldRender rendering}, flush any buffered
	 * {@link BodyContent} or, if no {@link BodyContent} is supplied,
	 * {@link #renderDefaultContent render the default content}.
	 * @return a {@link javax.servlet.jsp.tagext.Tag#EVAL_PAGE} result
	 */
	/**
	 * 如果{{@link> #shouldRender rendering}，则刷新所有缓冲的{@link  BodyContent}，或者，如果没有提供{@link  BodyContent}，则{@link  #renderDefaultContent渲染默认内容}。 
	 *  
	 * @return 一个{@link  javax.servlet.jsp.tagext.Tag＃EVAL_PAGE}结果
	 */
	@Override
	public int doEndTag() throws JspException {
		if (shouldRender()) {
			Assert.state(this.tagWriter != null, "No TagWriter set");
			if (this.bodyContent != null && StringUtils.hasText(this.bodyContent.getString())) {
				renderFromBodyContent(this.bodyContent, this.tagWriter);
			}
			else {
				renderDefaultContent(this.tagWriter);
			}
		}
		return EVAL_PAGE;
	}

	/**
	 * Render the tag contents based on the supplied {@link BodyContent}.
	 * <p>The default implementation simply {@link #flushBufferedBodyContent flushes}
	 * the {@link BodyContent} directly to the output. Subclasses may choose to
	 * override this to add additional content to the output.
	 */
	/**
	 * 根据提供的{@link  BodyContent}呈现标签内容。 
	 *  <p>默认实现只是{@link  #flushBufferedBodyContent将{@link  BodyContent}刷新}直接到输出。 
	 * 子类可以选择重写此方法，以将其他内容添加到输出中。 
	 * 
	 */
	protected void renderFromBodyContent(BodyContent bodyContent, TagWriter tagWriter) throws JspException {
		flushBufferedBodyContent(bodyContent);
	}

	/**
	 * Clean up any attributes and stored resources.
	 */
	/**
	 * 清理所有属性和存储的资源。 
	 * 
	 */
	@Override
	public void doFinally() {
		super.doFinally();
		removeAttributes();
		this.tagWriter = null;
		this.bodyContent = null;
	}


	//---------------------------------------------------------------------
	// Template methods
	//---------------------------------------------------------------------

	/**
	 * Called at the start of {@link #writeTagContent} allowing subclasses to perform
	 * any precondition checks or setup tasks that might be necessary.
	 */
	/**
	 * 在{@link  #writeTagContent}的开头调用，允许子类执行可能需要的任何前提条件检查或设置任务。 
	 * 
	 */
	protected void onWriteTagContent() {
	}

	/**
	 * Should rendering of this tag proceed at all. Returns '{@code true}' by default
	 * causing rendering to occur always, Subclasses can override this if they
	 * provide conditional rendering.
	 */
	/**
	 * 应该完全渲染此标记。 
	 * 默认情况下返回'{@code  true}'导致渲染始终发生，如果子类提供条件渲染，则它们可以覆盖它。 
	 * 
	 */
	protected boolean shouldRender() throws JspException {
		return true;
	}

	/**
	 * Called during {@link #writeTagContent} allowing subclasses to add any attributes to the
	 * {@link javax.servlet.jsp.PageContext} as needed.
	 */
	/**
	 * 在{@link  #writeTagContent}期间调用，允许子类根据需要向{@link  javax.servlet.jsp.PageContext}添加任何属性。 
	 * 
	 */
	protected void exposeAttributes() throws JspException {
	}

	/**
	 * Called by {@link #doFinally} allowing subclasses to remove any attributes from the
	 * {@link javax.servlet.jsp.PageContext} as needed.
	 */
	/**
	 * 由{@link  #doFinally}调用，允许子类根据需要从{@link  javax.servlet.jsp.PageContext}中删除所有属性。 
	 * 
	 */
	protected void removeAttributes() {
	}

	/**
	 * The user customised the output of the error messages - flush the
	 * buffered content into the main {@link javax.servlet.jsp.JspWriter}.
	 */
	/**
	 * 用户自定义错误消息的输出-将缓冲的内容刷新到主{@link  javax.servlet.jsp.JspWriter}中。 
	 * 
	 */
	protected void flushBufferedBodyContent(BodyContent bodyContent) throws JspException {
		try {
			bodyContent.writeOut(bodyContent.getEnclosingWriter());
		}
		catch (IOException ex) {
			throw new JspException("Unable to write buffered body content.", ex);
		}
	}

	protected abstract void renderDefaultContent(TagWriter tagWriter) throws JspException;


	//---------------------------------------------------------------------
	// BodyTag implementation
	//---------------------------------------------------------------------

	@Override
	public void doInitBody() throws JspException {
		// no op
	}

	@Override
	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}

}
