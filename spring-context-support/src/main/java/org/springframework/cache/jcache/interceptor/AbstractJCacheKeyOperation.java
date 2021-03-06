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

package org.springframework.cache.jcache.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.cache.annotation.CacheInvocationParameter;
import javax.cache.annotation.CacheMethodDetails;

import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * A base {@link JCacheOperation} that operates with a key.
 *
 * @author Stephane Nicoll
 * @since 4.1
 * @param <A> the annotation type
 */
/**
 * 使用密钥操作的基本{@link  JCacheOperation}。 
 *  @author  Stephane Nicoll @since 4.1起
 * @param  <A>注释类型
 */
abstract class AbstractJCacheKeyOperation<A extends Annotation> extends AbstractJCacheOperation<A> {

	private final KeyGenerator keyGenerator;

	private final List<CacheParameterDetail> keyParameterDetails;


	/**
	 * Create a new instance.
	 * @param methodDetails the {@link CacheMethodDetails} related to the cached method
	 * @param cacheResolver the cache resolver to resolve regular caches
	 * @param keyGenerator the key generator to compute cache keys
	 */
	/**
	 * 创建一个新实例。 
	 *  
	 * @param  methodDetails与缓存方法相关的{@link  CacheMethodDetails} 
	 * @param  cacheResolver缓存解析器，用于解析常规缓存
	 * @param  keyGenerator密钥生成器，用于计算缓存键
	 */
	protected AbstractJCacheKeyOperation(CacheMethodDetails<A> methodDetails,
			CacheResolver cacheResolver, KeyGenerator keyGenerator) {

		super(methodDetails, cacheResolver);
		this.keyGenerator = keyGenerator;
		this.keyParameterDetails = initializeKeyParameterDetails(this.allParameterDetails);
	}


	/**
	 * Return the {@link KeyGenerator} to use to compute cache keys.
	 */
	/**
	 * 返回{@link  KeyGenerator}以用于计算缓存键。 
	 * 
	 */
	public KeyGenerator getKeyGenerator() {
		return this.keyGenerator;
	}

	/**
	 * Return the {@link CacheInvocationParameter} for the parameters that are to be
	 * used to compute the key.
	 * <p>Per the spec, if some method parameters are annotated with
	 * {@link javax.cache.annotation.CacheKey}, only those parameters should be part
	 * of the key. If none are annotated, all parameters except the parameter annotated
	 * with {@link javax.cache.annotation.CacheValue} should be part of the key.
	 * <p>The method arguments must match the signature of the related method invocation
	 * @param values the parameters value for a particular invocation
	 * @return the {@link CacheInvocationParameter} instances for the parameters to be
	 * used to compute the key
	 */
	/**
	 * 返回{@link  CacheInvocationParameter}以获取用于计算密钥的参数。 
	 *  <p>根据规范，如果某些方法参数使用{@link  javax.cache.annotation.CacheKey}进行注释，则仅那些参数应为键的一部分。 
	 * 如果未注释任何内容，则所有参数（用{@link  javax.cache.annotation.CacheValue}注释的参数除外）都应该是键的一部分。 
	 *  <p>方法参数必须与相关方法调用的签名匹配
	 * @param 值特定调用的参数值
	 * @return  {@link  CacheInvocationParameter}实例以获取用于计算密钥的参数
	 */
	public CacheInvocationParameter[] getKeyParameters(Object... values) {
		List<CacheInvocationParameter> result = new ArrayList<>();
		for (CacheParameterDetail keyParameterDetail : this.keyParameterDetails) {
			int parameterPosition = keyParameterDetail.getParameterPosition();
			if (parameterPosition >= values.length) {
				throw new IllegalStateException("Values mismatch, key parameter at position "
						+ parameterPosition + " cannot be matched against " + values.length + " value(s)");
			}
			result.add(keyParameterDetail.toCacheInvocationParameter(values[parameterPosition]));
		}
		return result.toArray(new CacheInvocationParameter[0]);
	}


	private static List<CacheParameterDetail> initializeKeyParameterDetails(List<CacheParameterDetail> allParameters) {
		List<CacheParameterDetail> all = new ArrayList<>();
		List<CacheParameterDetail> annotated = new ArrayList<>();
		for (CacheParameterDetail allParameter : allParameters) {
			if (!allParameter.isValue()) {
				all.add(allParameter);
			}
			if (allParameter.isKey()) {
				annotated.add(allParameter);
			}
		}
		return (annotated.isEmpty() ? all : annotated);
	}

}
