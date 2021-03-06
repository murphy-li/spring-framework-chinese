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

package org.springframework.jndi;

import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.lang.Nullable;

/**
 * Callback interface to be implemented by classes that need to perform an
 * operation (such as a lookup) in a JNDI context. This callback approach
 * is valuable in simplifying error handling, which is performed by the
 * JndiTemplate class. This is a similar to JdbcTemplate's approach.
 *
 * <p>Note that there is hardly any need to implement this callback
 * interface, as JndiTemplate provides all usual JNDI operations via
 * convenience methods.
 *
 * @author Rod Johnson
 * @param <T> the resulting object type
 * @see JndiTemplate
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
/**
 * 回调接口将由需要在JNDI上下文中执行操作（例如查找）的类实现。 
 * 这种回调方法对于简化由JndiTemplate类执行的错误处理非常有用。 
 * 这类似于JdbcTemplate的方法。 
 *  <p>请注意，几乎不需要实现此回调接口，因为JndiTemplate通过便捷方法提供了所有常用的JNDI操作。 
 *  @author  Rod Johnson 
 * @param  <T>结果对象类型
 * @see  JndiTemplate 
 * @see  org.springframework.jdbc.core.JdbcTemplate
 */
@FunctionalInterface
public interface JndiCallback<T> {

	/**
	 * Do something with the given JNDI context.
	 * <p>Implementations don't need to worry about error handling
	 * or cleanup, as the JndiTemplate class will handle this.
	 * @param ctx the current JNDI context
	 * @throws NamingException if thrown by JNDI methods
	 * @return a result object, or {@code null}
	 */
	/**
	 * 使用给定的JNDI上下文进行操作。 
	 *  <p>实现无需担心错误处理或清除，因为JndiTemplate类将处理此问题。 
	 *  
	 * @param  ctx当前JNDI上下文
	 * @throws  NamingException（如果由JNDI方法抛出）
	 * @return 结果对象，或者{@code  null}
	 */
	@Nullable
	T doInContext(Context ctx) throws NamingException;

}

