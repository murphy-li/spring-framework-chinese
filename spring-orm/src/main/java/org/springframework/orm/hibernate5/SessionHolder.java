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

package org.springframework.orm.hibernate5;

import javax.persistence.EntityManager;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.EntityManagerHolder;

/**
 * Resource holder wrapping a Hibernate {@link Session} (plus an optional {@link Transaction}).
 * {@link HibernateTransactionManager} binds instances of this class to the thread,
 * for a given {@link org.hibernate.SessionFactory}. Extends {@link EntityManagerHolder}
 * as of 5.1, automatically exposing an {@code EntityManager} handle on Hibernate 5.2+.
 *
 * <p>Note: This is an SPI class, not intended to be used by applications.
 *
 * @author Juergen Hoeller
 * @since 4.2
 * @see HibernateTransactionManager
 * @see SessionFactoryUtils
 */
/**
 * 资源持有人包装了一个Hibernate {@link 会话}（以及一个可选的{@link  Transaction}）。 
 * 对于给定的{@link  org.hibernate.SessionFactory}，{<@link> HibernateTransactionManager}将此类的实例绑定到线程。 
 * 从5.1开始扩展{@link  EntityManagerHolder}，在Hibernate 5.2+上自动暴露{@code  EntityManager}句柄。 
 *  <p>注意：这是SPI类，不适合应用程序使用。 
 *  @author  Juergen Hoeller @自4.2起
 * @see  HibernateTransactionManager 
 * @see  SessionFactoryUtils
 */
public class SessionHolder extends EntityManagerHolder {

	private final Session session;

	@Nullable
	private Transaction transaction;

	@Nullable
	private FlushMode previousFlushMode;


	public SessionHolder(Session session) {
		// Check below is always true against Hibernate >= 5.2 but not against 5.0/5.1 at runtime
		super(EntityManager.class.isInstance(session) ? session : null);
		this.session = session;
	}


	public Session getSession() {
		return this.session;
	}

	public void setTransaction(@Nullable Transaction transaction) {
		this.transaction = transaction;
		setTransactionActive(transaction != null);
	}

	@Nullable
	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setPreviousFlushMode(@Nullable FlushMode previousFlushMode) {
		this.previousFlushMode = previousFlushMode;
	}

	@Nullable
	public FlushMode getPreviousFlushMode() {
		return this.previousFlushMode;
	}


	@Override
	public void clear() {
		super.clear();
		this.transaction = null;
		this.previousFlushMode = null;
	}

}
