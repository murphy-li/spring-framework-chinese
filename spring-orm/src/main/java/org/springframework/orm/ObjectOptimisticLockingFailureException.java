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

package org.springframework.orm;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.lang.Nullable;

/**
 * Exception thrown on an optimistic locking violation for a mapped object.
 * Provides information about the persistent class and the identifier.
 *
 * @author Juergen Hoeller
 * @since 13.10.2003
 */
/**
 * 映射对象的乐观锁定冲突引发异常。 
 * 提供有关持久类和标识符的信息。 
 *  @author  Juergen Hoeller @2003年10月13日
 */
@SuppressWarnings("serial")
public class ObjectOptimisticLockingFailureException extends OptimisticLockingFailureException {

	@Nullable
	private final Object persistentClass;

	@Nullable
	private final Object identifier;


	/**
	 * Create a general ObjectOptimisticLockingFailureException with the given message,
	 * without any information on the affected object.
	 * @param msg the detail message
	 * @param cause the source exception
	 */
	/**
	 * 使用给定的消息创建一个普通的ObjectOptimisticLockingFailureException，而无需任何有关受影响对象的信息。 
	 *  
	 * @param  msg详细消息
	 * @param 导致源异常
	 */
	public ObjectOptimisticLockingFailureException(String msg, Throwable cause) {
		super(msg, cause);
		this.persistentClass = null;
		this.identifier = null;
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the default "optimistic locking failed" message.
	 * @param persistentClass the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 */
	/**
	 * 为给定对象创建一个新的ObjectOptimisticLockingFailureException，并带有默认的"乐观锁定失败"消息。 
	 *  
	 * @param  persistentClass持久类
	 * @param 标识符锁定失败的对象的ID
	 */
	public ObjectOptimisticLockingFailureException(Class<?> persistentClass, Object identifier) {
		this(persistentClass, identifier, null);
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the default "optimistic locking failed" message.
	 * @param persistentClass the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 * @param cause the source exception
	 */
	/**
	 * 为给定对象创建一个新的ObjectOptimisticLockingFailureException，并带有默认的"乐观锁定失败"消息。 
	 *  
	 * @param  persistantClass持久类
	 * @param 标识符锁定失败的对象的ID 
	 * @param 导致源异常
	 */
	public ObjectOptimisticLockingFailureException(
			Class<?> persistentClass, Object identifier, @Nullable Throwable cause) {

		this(persistentClass, identifier,
				"Object of class [" + persistentClass.getName() + "] with identifier [" + identifier +
				"]: optimistic locking failed", cause);
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the given explicit message.
	 * @param persistentClass the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 * @param msg the detail message
	 * @param cause the source exception
	 */
	/**
	 * 使用给定的显式消息为给定的对象创建一个新的ObjectOptimisticLockingFailureException。 
	 *  
	 * @param  persistantClass持久类
	 * @param 标识符锁定失败的对象的ID 
	 * @param  msg详细消息
	 * @param 导致源异常
	 */
	public ObjectOptimisticLockingFailureException(
			Class<?> persistentClass, Object identifier, String msg, @Nullable Throwable cause) {

		super(msg, cause);
		this.persistentClass = persistentClass;
		this.identifier = identifier;
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the default "optimistic locking failed" message.
	 * @param persistentClassName the name of the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 */
	/**
	 * 为给定对象创建一个新的ObjectOptimisticLockingFailureException，并带有默认的"乐观锁定失败"消息。 
	 *  
	 * @param  persistentClassName持久类的名称
	 * @param 标识符锁定失败的对象的ID
	 */
	public ObjectOptimisticLockingFailureException(String persistentClassName, Object identifier) {
		this(persistentClassName, identifier, null);
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the default "optimistic locking failed" message.
	 * @param persistentClassName the name of the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 * @param cause the source exception
	 */
	/**
	 * 为给定对象创建一个新的ObjectOptimisticLockingFailureException，并带有默认的"乐观锁定失败"消息。 
	 *  
	 * @param  persistentClassName持久类的名称
	 * @param 标识符锁定失败的对象的ID 
	 * @param 导致源异常
	 */
	public ObjectOptimisticLockingFailureException(
			String persistentClassName, Object identifier, @Nullable Throwable cause) {

		this(persistentClassName, identifier,
				"Object of class [" + persistentClassName + "] with identifier [" + identifier +
				"]: optimistic locking failed", cause);
	}

	/**
	 * Create a new ObjectOptimisticLockingFailureException for the given object,
	 * with the given explicit message.
	 * @param persistentClassName the name of the persistent class
	 * @param identifier the ID of the object for which the locking failed
	 * @param msg the detail message
	 * @param cause the source exception
	 */
	/**
	 * 使用给定的显式消息为给定的对象创建一个新的ObjectOptimisticLockingFailureException。 
	 *  
	 * @param  persistentClassName持久类的名称
	 * @param 标识符锁定失败的对象的ID 
	 * @param  msg详细消息
	 * @param 导致源异常
	 */
	public ObjectOptimisticLockingFailureException(
			String persistentClassName, Object identifier, String msg, @Nullable Throwable cause) {

		super(msg, cause);
		this.persistentClass = persistentClassName;
		this.identifier = identifier;
	}


	/**
	 * Return the persistent class of the object for which the locking failed.
	 * If no Class was specified, this method returns null.
	 */
	/**
	 * 返回锁定失败的对象的持久类。 
	 * 如果未指定Class，则此方法返回null。 
	 * 
	 */
	@Nullable
	public Class<?> getPersistentClass() {
		return (this.persistentClass instanceof Class ? (Class<?>) this.persistentClass : null);
	}

	/**
	 * Return the name of the persistent class of the object for which the locking failed.
	 * Will work for both Class objects and String names.
	 */
	/**
	 * 返回锁定失败的对象的持久类的名称。 
	 * 适用于Class对象和String名称。 
	 * 
	 */
	@Nullable
	public String getPersistentClassName() {
		if (this.persistentClass instanceof Class) {
			return ((Class<?>) this.persistentClass).getName();
		}
		return (this.persistentClass != null ? this.persistentClass.toString() : null);
	}

	/**
	 * Return the identifier of the object for which the locking failed.
	 */
	/**
	 * 返回锁定失败的对象的标识符。 
	 * 
	 */
	@Nullable
	public Object getIdentifier() {
		return this.identifier;
	}

}
