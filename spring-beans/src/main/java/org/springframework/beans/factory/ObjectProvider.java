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

package org.springframework.beans.factory;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * A variant of {@link ObjectFactory} designed specifically for injection points,
 * allowing for programmatic optionality and lenient not-unique handling.
 *
 * <p>As of 5.1, this interface extends {@link Iterable} and provides {@link Stream}
 * support. It can be therefore be used in {@code for} loops, provides {@link #forEach}
 * iteration and allows for collection-style {@link #stream} access.
 *
 * @author Juergen Hoeller
 * @since 4.3
 * @param <T> the object type
 * @see BeanFactory#getBeanProvider
 * @see org.springframework.beans.factory.annotation.Autowired
 */
/**
 * {@link  ObjectFactory}的一种变体，专门为注入点设计，允许程序化的可选性和宽大的非唯一处理。 
 *  <p>从5.1开始，此接口扩展了{@link  Iterable}，并提供了{@link  Stream}支持。 
 * 因此，它可以在{@code  for}循环中使用，提供{@link  #forEach}迭代，并允许以集合样式{@link  #stream}访问。 
 *  @author  Juergen Hoeller @since 4.3 
 * @param  <T>对象类型
 * @see  BeanFactory＃getBeanProvider 
 * @see  org.springframework.beans.factory.annotation.Autowired
 */
public interface ObjectProvider<T> extends ObjectFactory<T>, Iterable<T> {

	/**
	 * Return an instance (possibly shared or independent) of the object
	 * managed by this factory.
	 * <p>Allows for specifying explicit construction arguments, along the
	 * lines of {@link BeanFactory#getBean(String, Object...)}.
	 * @param args arguments to use when creating a corresponding instance
	 * @return an instance of the bean
	 * @throws BeansException in case of creation errors
	 * @see #getObject()
	 */
	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  <p>允许沿着{@link  BeanFactory＃getBean（String，Object ...）}的行指定显式构造参数。 
	 *  
	 * @param 创建相应实例时要使用的args参数
	 * @return  Bean的实例
	 * @throws  BeansException，如果出现创建错误
	 * @see  #getObject（）
	 */
	T getObject(Object... args) throws BeansException;

	/**
	 * Return an instance (possibly shared or independent) of the object
	 * managed by this factory.
	 * @return an instance of the bean, or {@code null} if not available
	 * @throws BeansException in case of creation errors
	 * @see #getObject()
	 */
	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  
	 * @return  Bean的实例，如果不可用，则返回{<@@code> null} 
	 * @throws  BeansException，如果出现创建错误
	 * @see  #getObject（）
	 */
	@Nullable
	T getIfAvailable() throws BeansException;

	/**
	 * Return an instance (possibly shared or independent) of the object
	 * managed by this factory.
	 * @param defaultSupplier a callback for supplying a default object
	 * if none is present in the factory
	 * @return an instance of the bean, or the supplied default object
	 * if no such bean is available
	 * @throws BeansException in case of creation errors
	 * @since 5.0
	 * @see #getIfAvailable()
	 */
	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  
	 * @param  defaultSupplier如果工厂中不存在默认对象，则提供默认对象的回调
	 * @return  bean的实例； 
	 * 如果没有可用的bean，则提供默认对象
	 * @throws  BeansException，如果创建错误@since 5.0 
	 * @see  #getIfAvailable（）
	 */
	default T getIfAvailable(Supplier<T> defaultSupplier) throws BeansException {
		T dependency = getIfAvailable();
		return (dependency != null ? dependency : defaultSupplier.get());
	}

	/**
	 * Consume an instance (possibly shared or independent) of the object
	 * managed by this factory, if available.
	 * @param dependencyConsumer a callback for processing the target object
	 * if available (not called otherwise)
	 * @throws BeansException in case of creation errors
	 * @since 5.0
	 * @see #getIfAvailable()
	 */
	/**
	 * 消耗此工厂管理的对象的实例（可能是共享的或独立的）（如果有）。 
	 *  
	 * @param 依赖项如果存在创建错误，则从回调中获取用于处理目标对象的回调（否则不会调用）
	 * @throws  BeansException @5.0起，@
	 * @see> #getIfAvailable（）
	 */
	default void ifAvailable(Consumer<T> dependencyConsumer) throws BeansException {
		T dependency = getIfAvailable();
		if (dependency != null) {
			dependencyConsumer.accept(dependency);
		}
	}

	/**
	 * Return an instance (possibly shared or independent) of the object
	 * managed by this factory.
	 * @return an instance of the bean, or {@code null} if not available or
	 * not unique (i.e. multiple candidates found with none marked as primary)
	 * @throws BeansException in case of creation errors
	 * @see #getObject()
	 */
	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  
	 * @return  bean的实例，如果不可用或不唯一，即为{@code  null}（即，找到多个候选者，没有将其标记为主要对象）。 
	 * 
	 * @throws  BeansException，如果发生创建错误，则
	 * @see ＃ getObject（）
	 */
	@Nullable
	T getIfUnique() throws BeansException;

	/**
	 * Return an instance (possibly shared or independent) of the object
	 * managed by this factory.
	 * @param defaultSupplier a callback for supplying a default object
	 * if no unique candidate is present in the factory
	 * @return an instance of the bean, or the supplied default object
	 * if no such bean is available or if it is not unique in the factory
	 * (i.e. multiple candidates found with none marked as primary)
	 * @throws BeansException in case of creation errors
	 * @since 5.0
	 * @see #getIfUnique()
	 */
	/**
	 * 返回此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  
	 * @param  defaultSupplier如果工厂中没有唯一的候选对象，则提供默认对象的回调
	 * @return  bean的实例； 
	 * 如果没有可用的bean，或者提供的默认对象在bean中不是唯一的，则提供所提供的默认对象工厂（即发现多个候选者，没有将其标记为主要对象）
	 * @throws  BeansException，如果创建错误，则从@5.0起
	 * @see  #getIfUnique（）
	 */
	default T getIfUnique(Supplier<T> defaultSupplier) throws BeansException {
		T dependency = getIfUnique();
		return (dependency != null ? dependency : defaultSupplier.get());
	}

	/**
	 * Consume an instance (possibly shared or independent) of the object
	 * managed by this factory, if unique.
	 * @param dependencyConsumer a callback for processing the target object
	 * if unique (not called otherwise)
	 * @throws BeansException in case of creation errors
	 * @since 5.0
	 * @see #getIfAvailable()
	 */
	/**
	 * 如果唯一，请消耗此工厂管理的对象的实例（可能是共享的或独立的）。 
	 *  
	 * @param 依赖项如果存在创建错误，则回调一个用于处理目标对象的回调（否则不会调用）
	 * @throws  BeansException（自5.0以来出现创建错误）
	 * @see  #getIfAvailable（）
	 */
	default void ifUnique(Consumer<T> dependencyConsumer) throws BeansException {
		T dependency = getIfUnique();
		if (dependency != null) {
			dependencyConsumer.accept(dependency);
		}
	}

	/**
	 * Return an {@link Iterator} over all matching object instances,
	 * without specific ordering guarantees (but typically in registration order).
	 * @since 5.1
	 * @see #stream()
	 */
	/**
	 * 在所有匹配的对象实例上返回一个{@link 迭代器}，没有特定的顺序保证（但通常以注册顺序）。 
	 *  @始于5.1 
	 * @see  #stream（）
	 */
	@Override
	default Iterator<T> iterator() {
		return stream().iterator();
	}

	/**
	 * Return a sequential {@link Stream} over all matching object instances,
	 * without specific ordering guarantees (but typically in registration order).
	 * @since 5.1
	 * @see #iterator()
	 * @see #orderedStream()
	 */
	/**
	 * 在所有匹配的对象实例上返回顺序的{@link 流}，没有特定的顺序保证（但通常以注册顺序）。 
	 *  @since 5.1 
	 * @see  #iterator（）
	 * @see  #orderedStream（）
	 */
	default Stream<T> stream() {
		throw new UnsupportedOperationException("Multi element access not supported");
	}

	/**
	 * Return a sequential {@link Stream} over all matching object instances,
	 * pre-ordered according to the factory's common order comparator.
	 * <p>In a standard Spring application context, this will be ordered
	 * according to {@link org.springframework.core.Ordered} conventions,
	 * and in case of annotation-based configuration also considering the
	 * {@link org.springframework.core.annotation.Order} annotation,
	 * analogous to multi-element injection points of list/array type.
	 * @since 5.1
	 * @see #stream()
	 * @see org.springframework.core.OrderComparator
	 */
	/**
	 * 在所有匹配的对象实例上返回顺序的{@link 流}，并根据工厂的公共顺序比较器进行了预排序。 
	 *  <p>在标准Spring应用程序上下文中，这将根据{@link  org.springframework.core.Ordered}约定进行排序，并且在基于注释的配置的情况下，还应考虑{@link  org.springframework .core.annotation.Order}注解，类似于列表/数组类型的多元素注入点。 
	 *  @since 5.1 
	 * @see  #stream（）
	 * @see  org.springframework.core.OrderComparator
	 */
	default Stream<T> orderedStream() {
		throw new UnsupportedOperationException("Ordered element access not supported");
	}

}
