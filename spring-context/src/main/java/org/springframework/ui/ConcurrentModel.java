/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
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
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.ui;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.Conventions;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Implementation of the {@link Model} interface based on a {@link ConcurrentHashMap}
 * for use in concurrent scenarios.
 *
 * <p>Exposed to handler methods by Spring WebFlux, typically via a declaration of the
 * {@link Model} interface. There is typically no need to create it within user code.
 * If necessary a handler method can return a regular {@code java.util.Map},
 * likely a {@code java.util.ConcurrentMap}, for a pre-determined model.
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
/**
 * 基于{@link  ConcurrentHashMap}的{@link  Model}接口的实现，用于并发场景。 
 *  <p>通常由{@link  Model}接口的声明通过Spring WebFlux暴露给处理程序方法。 
 * 通常无需在用户代码中创建它。 
 * 如有必要，处理程序方法可以为预定模型返回常规的{@code  java.util.Map}，可能返回{@code  java.util.ConcurrentMap}。 
 *  @author  Rossen Stoyanchev @从5.0开始
 */
@SuppressWarnings("serial")
public class ConcurrentModel extends ConcurrentHashMap<String, Object> implements Model {

	/**
	 * Construct a new, empty {@code ConcurrentModel}.
	 */
	/**
	 * 构造一个新的空{@code  ConcurrentModel}。 
	 * 
	 */
	public ConcurrentModel() {
	}

	/**
	 * Construct a new {@code ModelMap} containing the supplied attribute
	 * under the supplied name.
	 * @see #addAttribute(String, Object)
	 */
	/**
	 * 构造一个新的{@code  ModelMap}，其中包含以提供的名称提供的属性。 
	 *  
	 * @see  #addAttribute（字符串，对象）
	 */
	public ConcurrentModel(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue);
	}

	/**
	 * Construct a new {@code ModelMap} containing the supplied attribute.
	 * Uses attribute name generation to generate the key for the supplied model
	 * object.
	 * @see #addAttribute(Object)
	 */
	/**
	 * 构造一个包含提供的属性的新{@code  ModelMap}。 
	 * 使用属性名称生成来为提供的模型对象生成密钥。 
	 *  
	 * @see  #addAttribute（对象）
	 */
	public ConcurrentModel(Object attributeValue) {
		addAttribute(attributeValue);
	}


	@Override
	public Object put(String key, Object value) {
		if (value != null) {
			return super.put(key, value);
		}
		else {
			return remove(key);
		}
	}

	@Override
	public void putAll(Map<? extends String, ?> map) {
		for (Map.Entry<? extends String, ?> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Add the supplied attribute under the supplied name.
	 * @param attributeName the name of the model attribute (never {@code null})
	 * @param attributeValue the model attribute value (ignored if {@code null},
	 * just removing an existing entry if any)
	 */
	/**
	 * 在提供的名称下添加提供的属性。 
	 *  
	 * @param  attributeName模型属性的名称（绝不{<@@code> null}）
	 * @param  attributeValue模型属性值（如果{@code  null}则忽略，仅删除现有条目（如果有））
	 */
	@Override
	public ConcurrentModel addAttribute(String attributeName, @Nullable Object attributeValue) {
		Assert.notNull(attributeName, "Model attribute name must not be null");
		put(attributeName, attributeValue);
		return this;
	}

	/**
	 * Add the supplied attribute to this {@code Map} using a
	 * {@link org.springframework.core.Conventions#getVariableName generated name}.
	 * <p><i>Note: Empty {@link Collection Collections} are not added to
	 * the model when using this method because we cannot correctly determine
	 * the true convention name. View code should check for {@code null} rather
	 * than for empty collections as is already done by JSTL tags.</i>
	 * @param attributeValue the model attribute value (never {@code null})
	 */
	/**
	 * 使用{@link  org.springframework.core.Conventions＃getVariableName生成的名称}将提供的属性添加到此{@code  Map}。 
	 *  <p> <i>注意：使用此方法时，不会将空的{@link 集合集合}添加到模型中，因为我们无法正确确定真实的约定名称。 
	 * 查看代码应检查{@code  null}，而不是JSTL标记已完成的空集合。 
	 * </ i> 
	 * @param  attributeValue模型属性值（切勿{@code  null}）
	 */
	@Override
	public ConcurrentModel addAttribute(Object attributeValue) {
		Assert.notNull(attributeValue, "Model attribute value must not be null");
		if (attributeValue instanceof Collection && ((Collection<?>) attributeValue).isEmpty()) {
			return this;
		}
		return addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
	}

	/**
	 * Copy all attributes in the supplied {@code Collection} into this
	 * {@code Map}, using attribute name generation for each element.
	 * @see #addAttribute(Object)
	 */
	/**
	 * 使用每个元素的属性名称生成，将提供的{@code 集合}中的所有属性复制到此{@code  Map}中。 
	 *  
	 * @see  #addAttribute（对象）
	 */
	@Override
	public ConcurrentModel addAllAttributes(@Nullable Collection<?> attributeValues) {
		if (attributeValues != null) {
			for (Object attributeValue : attributeValues) {
				addAttribute(attributeValue);
			}
		}
		return this;
	}

	/**
	 * Copy all attributes in the supplied {@code Map} into this {@code Map}.
	 * @see #addAttribute(String, Object)
	 */
	/**
	 * 将提供的{@code 映射}中的所有属性复制到此{@code 映射}中。 
	 *  
	 * @see  #addAttribute（字符串，对象）
	 */
	@Override
	public ConcurrentModel addAllAttributes(@Nullable Map<String, ?> attributes) {
		if (attributes != null) {
			putAll(attributes);
		}
		return this;
	}

	/**
	 * Copy all attributes in the supplied {@code Map} into this {@code Map},
	 * with existing objects of the same name taking precedence (i.e. not getting
	 * replaced).
	 */
	/**
	 * 将提供的{@code 映射}中的所有属性复制到此{@code 映射}中，同名的现有对象优先（即不被替换）。 
	 * 
	 */
	@Override
	public ConcurrentModel mergeAttributes(@Nullable Map<String, ?> attributes) {
		if (attributes != null) {
			attributes.forEach((key, value) -> {
				if (!containsKey(key)) {
					put(key, value);
				}
			});
		}
		return this;
	}

	/**
	 * Does this model contain an attribute of the given name?
	 * @param attributeName the name of the model attribute (never {@code null})
	 * @return whether this model contains a corresponding attribute
	 */
	/**
	 * 此模型是否包含给定名称的属性？ 
	 * @param  attributeName模型属性的名称（不要{<@@code> null}）
	 * @return 此模型是否包含相应的属性
	 */
	@Override
	public boolean containsAttribute(String attributeName) {
		return containsKey(attributeName);
	}

	@Override
	@Nullable
	public Object getAttribute(String attributeName) {
		return get(attributeName);
	}

	@Override
	public Map<String, Object> asMap() {
		return this;
	}

}
