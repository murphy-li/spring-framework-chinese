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

package org.springframework.web.servlet.view.tiles3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tiles.TilesException;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.preparer.factory.NoSuchPreparerException;

import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * Tiles {@link org.apache.tiles.preparer.PreparerFactory} implementation
 * that expects preparer class names and builds preparer instances for those,
 * creating them through the Spring ApplicationContext in order to apply
 * Spring container callbacks and configured Spring BeanPostProcessors.
 *
 * @author Juergen Hoeller
 * @since 3.2
 * @see SpringBeanPreparerFactory
 */
/**
 * 平铺{@link  org.apache.tiles.preparer.PreparerFactory}实现，该实现期望准备器类名称并为其构建准备器实例，并通过Spring ApplicationContext创建它们，以便应用Spring容器回调和已配置的Spring BeanPostProcessors。 
 *  @author  Juergen Hoeller @从3.2开始
 * @see  SpringBeanPreparerFactory
 */
public class SimpleSpringPreparerFactory extends AbstractSpringPreparerFactory {

	/** Cache of shared ViewPreparer instances: bean name -> bean instance. */
	/**
	 * 共享ViewPreparer实例的缓存：bean名称-> bean实例。 
	 * 
	 */
	private final Map<String, ViewPreparer> sharedPreparers = new ConcurrentHashMap<>(16);


	@Override
	protected ViewPreparer getPreparer(String name, WebApplicationContext context) throws TilesException {
		// Quick check on the concurrent map first, with minimal locking.
		ViewPreparer preparer = this.sharedPreparers.get(name);
		if (preparer == null) {
			synchronized (this.sharedPreparers) {
				preparer = this.sharedPreparers.get(name);
				if (preparer == null) {
					try {
						Class<?> beanClass = ClassUtils.forName(name, context.getClassLoader());
						if (!ViewPreparer.class.isAssignableFrom(beanClass)) {
							throw new PreparerException(
									"Invalid preparer class [" + name + "]: does not implement ViewPreparer interface");
						}
						preparer = (ViewPreparer) context.getAutowireCapableBeanFactory().createBean(beanClass);
						this.sharedPreparers.put(name, preparer);
					}
					catch (ClassNotFoundException ex) {
						throw new NoSuchPreparerException("Preparer class [" + name + "] not found", ex);
					}
				}
			}
		}
		return preparer;
	}

}
