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

package org.springframework.web.jsf;

import java.util.Collection;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.web.context.WebApplicationContext;

/**
 * JSF PhaseListener implementation that delegates to one or more Spring-managed
 * PhaseListener beans coming from the Spring root WebApplicationContext.
 *
 * <p>Configure this listener multicaster in your {@code faces-config.xml} file
 * as follows:
 *
 * <pre class="code">
 * &lt;application&gt;
 *   ...
 *   &lt;phase-listener&gt;
 *     org.springframework.web.jsf.DelegatingPhaseListenerMulticaster
 *   &lt;/phase-listener&gt;
 *   ...
 * &lt;/application&gt;</pre>
 *
 * The multicaster will delegate all {@code beforePhase} and {@code afterPhase}
 * events to all target PhaseListener beans. By default, those will simply be obtained
 * by type: All beans in the Spring root WebApplicationContext that implement the
 * PhaseListener interface will be fetched and invoked.
 *
 * <p>Note: This multicaster's {@code getPhaseId()} method will always return
 * {@code ANY_PHASE}. <b>The phase id exposed by the target listener beans
 * will be ignored; all events will be propagated to all listeners.</b>
 *
 * <p>This multicaster may be subclassed to change the strategy used to obtain
 * the listener beans, or to change the strategy used to access the ApplicationContext
 * (normally obtained via {@link FacesContextUtils#getWebApplicationContext(FacesContext)}).
 *
 * @author Juergen Hoeller
 * @author Colin Sampaleanu
 * @since 1.2.7
 */
/**
 * JSF PhaseListener实现，该实现委托给一个或多个来自Spring根WebApplicationContext的Spring管理的PhaseListener Bean。 
 *  <p>在您的{@code  faces-config.xml}文件中配置此侦听器多播器，如下所示：<pre class ="code"> <application> ... <phase-listener> org.springframework.web.jsf .DelegatingPhaseListenerMulticaster </ phase-listener> ... </ application> </ pre>多播器会将所有{@code  beforePhase}和{@code  afterPhase}事件委托给所有目标PhaseListener Bean。 
 * 默认情况下，将通过类型简单地获取它们：将获取并调用Spring根WebApplicationContext中实现PhaseListener接口的所有bean。 
 *  <p>注意：此多播器的{@code  getPhaseId（）}方法将始终返回{@code  ANY_PHASE}。 
 *  <b>目标侦听器bean公开的阶段ID将被忽略； 
 * 所有事件都将传播到所有侦听器。 
 * </ b> <p>此多播器可以被子类化，以更改用于获取侦听器bean的策略，或更改用于访问ApplicationContext的策略（通常通过{<@link获得> FacesContextUtils＃getWebApplicationContext（FacesContext）}）。 
 *  @author 于尔根·霍勒（Juergen Hoeller）@author 科林·桑帕林（Colin Sampaleanu）@1.2.7起
 */
@SuppressWarnings("serial")
public class DelegatingPhaseListenerMulticaster implements PhaseListener {

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		for (PhaseListener listener : getDelegates(event.getFacesContext())) {
			listener.beforePhase(event);
		}
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		for (PhaseListener listener : getDelegates(event.getFacesContext())) {
			listener.afterPhase(event);
		}
	}


	/**
	 * Obtain the delegate PhaseListener beans from the Spring root WebApplicationContext.
	 * @param facesContext the current JSF context
	 * @return a Collection of PhaseListener objects
	 * @see #getBeanFactory
	 * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType(Class)
	 */
	/**
	 * 从Spring根WebApplicationContext获得委托PhaseListener Bean。 
	 *  
	 * @param  facesContext当前JSF上下文<@r​​eturn> PhaseListener对象的集合
	 * @see  #getBeanFactory 
	 * @see  org.springframework.beans.factory.ListableBeanFactory＃getBeansOfType（Class）
	 */
	protected Collection<PhaseListener> getDelegates(FacesContext facesContext) {
		ListableBeanFactory bf = getBeanFactory(facesContext);
		return BeanFactoryUtils.beansOfTypeIncludingAncestors(bf, PhaseListener.class, true, false).values();
	}

	/**
	 * Retrieve the Spring BeanFactory to delegate bean name resolution to.
	 * <p>The default implementation delegates to {@code getWebApplicationContext}.
	 * Can be overridden to provide an arbitrary ListableBeanFactory reference to
	 * resolve against; usually, this will be a full Spring ApplicationContext.
	 * @param facesContext the current JSF context
	 * @return the Spring ListableBeanFactory (never {@code null})
	 * @see #getWebApplicationContext
	 */
	/**
	 * 检索Spring BeanFactory来将bean名称解析委托给它。 
	 *  <p>默认实现委托给{@code  getWebApplicationContext}。 
	 * 可以重写以提供要解决的任意ListableBeanFactory引用； 
	 * 通常，这将是完整的Spring ApplicationContext。 
	 *  
	 * @param  facesContext当前的JSF上下文<@r​​eturn> Spring ListableBeanFactory（从不{@code  null}）
	 * @see  #getWebApplicationContext
	 */
	protected ListableBeanFactory getBeanFactory(FacesContext facesContext) {
		return getWebApplicationContext(facesContext);
	}

	/**
	 * Retrieve the web application context to delegate bean name resolution to.
	 * <p>The default implementation delegates to FacesContextUtils.
	 * @param facesContext the current JSF context
	 * @return the Spring web application context (never {@code null})
	 * @see FacesContextUtils#getRequiredWebApplicationContext
	 */
	/**
	 * 检索Web应用程序上下文以委托Bean名称解析。 
	 *  <p>默认实现委托给FacesContextUtils。 
	 *  
	 * @param  facesContext当前的JSF上下文<@r​​eturn> Spring Web应用程序上下文（绝不{@code  null}）
	 * @see  FacesContextUtils＃getRequiredWebApplicationContext
	 */
	protected WebApplicationContext getWebApplicationContext(FacesContext facesContext) {
		return FacesContextUtils.getRequiredWebApplicationContext(facesContext);
	}

}
