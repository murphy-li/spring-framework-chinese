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

package org.springframework.aop.target.dynamic;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

/**
 * Refreshable TargetSource that fetches fresh target beans from a BeanFactory.
 *
 * <p>Can be subclassed to override {@code requiresRefresh()} to suppress
 * unnecessary refreshes. By default, a refresh will be performed every time
 * the "refreshCheckDelay" has elapsed.
 *
 * @author Rob Harrop
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @since 2.0
 * @see org.springframework.beans.factory.BeanFactory
 * @see #requiresRefresh()
 * @see #setRefreshCheckDelay
 */
/**
 * 可刷新的TargetSource，用于从BeanFactory中获取新鲜的目标bean。 
 *  <p>可以子类化以覆盖{@code  requireRefresh（）}以禁止不必要的刷新。 
 * 默认情况下，每次经过"refreshCheckDelay"都会执行刷新。 
 *  @author  Rob Harrop @author  Rod Johnson @author  Juergen Hoeller @author 马克·费舍尔@since 2.0 
 * @see  org.springframework.beans.factory.BeanFactory 
 * @see  #requiresRefresh（）<@查看> #setRefreshCheckDelay
 */
public class BeanFactoryRefreshableTargetSource extends AbstractRefreshableTargetSource {

	private final BeanFactory beanFactory;

	private final String beanName;


	/**
	 * Create a new BeanFactoryRefreshableTargetSource for the given
	 * bean factory and bean name.
	 * <p>Note that the passed-in BeanFactory should have an appropriate
	 * bean definition set up for the given bean name.
	 * @param beanFactory the BeanFactory to fetch beans from
	 * @param beanName the name of the target bean
	 */
	/**
	 * 为给定的bean工厂和bean名称创建一个新的BeanFactoryRefreshableTargetSource。 
	 *  <p>请注意，传入的BeanFactory应该为给定的bean名称设置适当的bean定义。 
	 *  
	 * @param  beanFactory BeanFactory，用于从
	 * @param  bean中获取bean名称目标bean的名称
	 */
	public BeanFactoryRefreshableTargetSource(BeanFactory beanFactory, String beanName) {
		Assert.notNull(beanFactory, "BeanFactory is required");
		Assert.notNull(beanName, "Bean name is required");
		this.beanFactory = beanFactory;
		this.beanName = beanName;
	}


	/**
	 * Retrieve a fresh target object.
	 */
	/**
	 * 检索一个新的目标对象。 
	 * 
	 */
	@Override
	protected final Object freshTarget() {
		return this.obtainFreshBean(this.beanFactory, this.beanName);
	}

	/**
	 * A template method that subclasses may override to provide a
	 * fresh target object for the given bean factory and bean name.
	 * <p>This default implementation fetches a new target bean
	 * instance from the bean factory.
	 * @see org.springframework.beans.factory.BeanFactory#getBean
	 */
	/**
	 * 子类的模板方法可以重写以为给定的bean工厂和bean名称提供新的目标对象。 
	 *  <p>此默认实现从Bean工厂获取新的目标Bean实例。 
	 *  
	 * @see  org.springframework.beans.factory.BeanFactory＃getBean
	 */
	protected Object obtainFreshBean(BeanFactory beanFactory, String beanName) {
		return beanFactory.getBean(beanName);
	}

}
