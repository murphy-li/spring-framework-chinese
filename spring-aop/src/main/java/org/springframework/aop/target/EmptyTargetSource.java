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

package org.springframework.aop.target;

import java.io.Serializable;

import org.springframework.aop.TargetSource;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * Canonical {@code TargetSource} when there is no target
 * (or just the target class known), and behavior is supplied
 * by interfaces and advisors only.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
/**
 * 在没有目标（或只有已知的目标类）且行为仅由接口和顾问程序提供的情况下，规范化{@code  TargetSource}。 
 *  @author 罗德·约翰逊@author  Juergen Hoeller
 */
public final class EmptyTargetSource implements TargetSource, Serializable {

	/** use serialVersionUID from Spring 1.2 for interoperability. */
	/**
	 * 使用Spring 1.2中的serialVersionUID来实现互操作性。 
	 * 
	 */
	private static final long serialVersionUID = 3680494563553489691L;


	//---------------------------------------------------------------------
	// Static factory methods
	//---------------------------------------------------------------------

	/**
	 * The canonical (Singleton) instance of this {@link EmptyTargetSource}.
	 */
	/**
	 * 此{@link  EmptyTargetSource}的规范（Singleton）实例。 
	 * 
	 */
	public static final EmptyTargetSource INSTANCE = new EmptyTargetSource(null, true);


	/**
	 * Return an EmptyTargetSource for the given target Class.
	 * @param targetClass the target Class (may be {@code null})
	 * @see #getTargetClass()
	 */
	/**
	 * 返回给定目标类的EmptyTargetSource。 
	 *  
	 * @param  targetClass目标类（可以为{@code  null}）
	 * @see  #getTargetClass（）
	 */
	public static EmptyTargetSource forClass(@Nullable Class<?> targetClass) {
		return forClass(targetClass, true);
	}

	/**
	 * Return an EmptyTargetSource for the given target Class.
	 * @param targetClass the target Class (may be {@code null})
	 * @param isStatic whether the TargetSource should be marked as static
	 * @see #getTargetClass()
	 */
	/**
	 * 返回给定目标类的EmptyTargetSource。 
	 *  
	 * @param  targetClass目标类（可以为{@code  null}）
	 * @param  isStatic是否应将TargetSource标记为静态
	 * @see  #getTargetClass（）
	 */
	public static EmptyTargetSource forClass(@Nullable Class<?> targetClass, boolean isStatic) {
		return (targetClass == null && isStatic ? INSTANCE : new EmptyTargetSource(targetClass, isStatic));
	}


	//---------------------------------------------------------------------
	// Instance implementation
	//---------------------------------------------------------------------

	private final Class<?> targetClass;

	private final boolean isStatic;


	/**
	 * Create a new instance of the {@link EmptyTargetSource} class.
	 * <p>This constructor is {@code private} to enforce the
	 * Singleton pattern / factory method pattern.
	 * @param targetClass the target class to expose (may be {@code null})
	 * @param isStatic whether the TargetSource is marked as static
	 */
	/**
	 * 创建{@link  EmptyTargetSource}类的新实例。 
	 *  <p>此构造函数是{@code  private}，用于实施Singleton模式/工厂方法模式。 
	 *  
	 * @param  targetClass要公开的目标类（可能为{@code  null}）
	 * @param  isStatic是否将TargetSource标记为静态
	 */
	private EmptyTargetSource(@Nullable Class<?> targetClass, boolean isStatic) {
		this.targetClass = targetClass;
		this.isStatic = isStatic;
	}


	/**
	 * Always returns the specified target Class, or {@code null} if none.
	 */
	/**
	 * 始终返回指定的目标类； 
	 * 如果没有，则返回{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	/**
	 * Always returns {@code true}.
	 */
	/**
	 * 始终返回{@code  true}。 
	 * 
	 */
	@Override
	public boolean isStatic() {
		return this.isStatic;
	}

	/**
	 * Always returns {@code null}.
	 */
	/**
	 * 始终返回{@code  null}。 
	 * 
	 */
	@Override
	@Nullable
	public Object getTarget() {
		return null;
	}

	/**
	 * Nothing to release.
	 */
	/**
	 * 什么都没有释放。 
	 * 
	 */
	@Override
	public void releaseTarget(Object target) {
	}


	/**
	 * Returns the canonical instance on deserialization in case
	 * of no target class, thus protecting the Singleton pattern.
	 */
	/**
	 * 如果没有目标类，则返回反序列化的规范实例，从而保护Singleton模式。 
	 * 
	 */
	private Object readResolve() {
		return (this.targetClass == null && this.isStatic ? INSTANCE : this);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmptyTargetSource)) {
			return false;
		}
		EmptyTargetSource otherTs = (EmptyTargetSource) other;
		return (ObjectUtils.nullSafeEquals(this.targetClass, otherTs.targetClass) && this.isStatic == otherTs.isStatic);
	}

	@Override
	public int hashCode() {
		return EmptyTargetSource.class.hashCode() * 13 + ObjectUtils.nullSafeHashCode(this.targetClass);
	}

	@Override
	public String toString() {
		return "EmptyTargetSource: " +
				(this.targetClass != null ? "target class [" + this.targetClass.getName() + "]" : "no target class") +
				", " + (this.isStatic ? "static" : "dynamic");
	}

}
