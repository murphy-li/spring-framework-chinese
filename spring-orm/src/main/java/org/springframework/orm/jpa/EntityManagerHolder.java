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

package org.springframework.orm.jpa;

import javax.persistence.EntityManager;

import org.springframework.lang.Nullable;
import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.util.Assert;

/**
 * Resource holder wrapping a JPA {@link EntityManager}.
 * {@link JpaTransactionManager} binds instances of this class to the thread,
 * for a given {@link javax.persistence.EntityManagerFactory}.
 *
 * <p>Also serves as a base class for {@link org.springframework.orm.hibernate5.SessionHolder},
 * as of 5.1.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see JpaTransactionManager
 * @see EntityManagerFactoryUtils
 */
/**
 * 包装JPA {@link  EntityManager}的资源持有者。 
 * 对于给定的{@link  javax.persistence.EntityManagerFactory}，{<@link> JpaTransactionManager}将此类的实例绑定到线程。 
 * 从5.1开始，<p>还作为{@link  org.springframework.orm.hibernate5.SessionHolder}的基类。 
 *  <p>注意：这是SPI类，不适合应用程序使用。 
 *  @author  Juergen Hoeller @从2.0开始
 * @see  JpaTransactionManager 
 * @see  EntityManagerFactoryUtils
 */
public class EntityManagerHolder extends ResourceHolderSupport {

	@Nullable
	private final EntityManager entityManager;

	private boolean transactionActive;

	@Nullable
	private SavepointManager savepointManager;


	public EntityManagerHolder(@Nullable EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	public EntityManager getEntityManager() {
		Assert.state(this.entityManager != null, "No EntityManager available");
		return this.entityManager;
	}

	protected void setTransactionActive(boolean transactionActive) {
		this.transactionActive = transactionActive;
	}

	protected boolean isTransactionActive() {
		return this.transactionActive;
	}

	protected void setSavepointManager(@Nullable SavepointManager savepointManager) {
		this.savepointManager = savepointManager;
	}

	@Nullable
	protected SavepointManager getSavepointManager() {
		return this.savepointManager;
	}


	@Override
	public void clear() {
		super.clear();
		this.transactionActive = false;
		this.savepointManager = null;
	}

}
