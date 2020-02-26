/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2014 the original author or authors.
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
 * 版权所有2002-2014的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.orm.jpa.persistenceunit;

import java.util.List;

import javax.persistence.spi.PersistenceUnitInfo;

/**
 * Extension of the standard JPA PersistenceUnitInfo interface, for advanced collaboration
 * between Spring's {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}
 * and {@link PersistenceUnitManager} implementations.
 *
 * @author Juergen Hoeller
 * @since 3.0.1
 * @see PersistenceUnitManager
 * @see org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
/**
 * 标准JPA PersistenceUnitInfo接口的扩展，用于Spring的{@link  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean}和{@link  PersistenceUnitManager}实现之间的高级协作。 
 *  @author  Juergen Hoeller @since 3.0.1起
 * @see  PersistenceUnitManager 
 * @see  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
public interface SmartPersistenceUnitInfo extends PersistenceUnitInfo {

	/**
	 * Return a list of managed Java packages, to be introspected by the persistence provider.
	 * Typically found through scanning but not exposable through {@link #getManagedClassNames()}.
	 * @return a list of names of managed Java packages (potentially empty)
	 * @since 4.1
	 */
	/**
	 * 返回持久化提供程序自省的托管Java软件包列表。 
	 * 通常通过扫描找到，但不能通过{@link  #getManagedClassNames（）}暴露。 
	 *  
	 * @return 自4.1开始的托管Java软件包名称列表（可能为空）
	 */
	List<String> getManagedPackages();

	/**
	 * Set the persistence provider's own package name, for exclusion from class transformation.
	 * @see #addTransformer(javax.persistence.spi.ClassTransformer)
	 * @see #getNewTempClassLoader()
	 */
	/**
	 * 设置持久性提供程序自己的程序包名称，以将其从类转换中排除。 
	 *  
	 * @see  #addTransformer（javax.persistence.spi.ClassTransformer）
	 * @see  #getNewTempClassLoader（）
	 */
	void setPersistenceProviderPackageName(String persistenceProviderPackageName);

}
