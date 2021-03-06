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

package org.springframework.context.annotation;

import java.lang.annotation.Annotation;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Convenient base class for {@link ImportSelector} implementations that select imports
 * based on an {@link AdviceMode} value from an annotation (such as the {@code @Enable*}
 * annotations).
 *
 * @author Chris Beams
 * @since 3.1
 * @param <A> annotation containing {@linkplain #getAdviceModeAttributeName() AdviceMode attribute}
 */
/**
 * 方便的{@link  ImportSelector}实现的基类，该实现基于来自注释的{@link  AdviceMode}值（例如{@code  @Enable}注释）选择导入。 
 *  @author  Chris Beams @since 3.1起，@
 * @param> <A>注释包含{@link  plain #getAdviceModeAttributeName（）AdviceMode属性}
 */
public abstract class AdviceModeImportSelector<A extends Annotation> implements ImportSelector {

	/**
	 * The default advice mode attribute name.
	 */
	/**
	 * 默认建议模式属性名称。 
	 * 
	 */
	public static final String DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME = "mode";


	/**
	 * The name of the {@link AdviceMode} attribute for the annotation specified by the
	 * generic type {@code A}. The default is {@value #DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME},
	 * but subclasses may override in order to customize.
	 */
	/**
	 * 通用类型{@code  A}指定的注释的{@link  AdviceMode}属性的名称。 
	 * 默认值为{@value #DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME}，但是可以重写子类以进行自定义。 
	 * 
	 */
	protected String getAdviceModeAttributeName() {
		return DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME;
	}

	/**
	 * This implementation resolves the type of annotation from generic metadata and
	 * validates that (a) the annotation is in fact present on the importing
	 * {@code @Configuration} class and (b) that the given annotation has an
	 * {@linkplain #getAdviceModeAttributeName() advice mode attribute} of type
	 * {@link AdviceMode}.
	 * <p>The {@link #selectImports(AdviceMode)} method is then invoked, allowing the
	 * concrete implementation to choose imports in a safe and convenient fashion.
	 * @throws IllegalArgumentException if expected annotation {@code A} is not present
	 * on the importing {@code @Configuration} class or if {@link #selectImports(AdviceMode)}
	 * returns {@code null}
	 */
	/**
	 * 此实现从通用元数据解析注释的类型，并验证（a）注释实际上存在于导入的{@code  @Configuration}类上，以及（b）给定的注释具有{@link  plain类型为{@link  AdviceMode}的#getAdviceModeAttributeName（）咨询模式属性}。 
	 *  <p>然后调用{@link  #selectImports（AdviceMode）}方法，从而允许具体实现以安全便捷的方式选择导入。 
	 *  
	 * @throws  IllegalArgumentException如果在导入的{@code  @Configuration}类上不存在预期的注释{@code  A}，或者如果{@link  #selectImports（AdviceMode）}返回{@code  null }
	 */
	@Override
	public final String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), AdviceModeImportSelector.class);
		Assert.state(annType != null, "Unresolvable type argument for AdviceModeImportSelector");

		AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(importingClassMetadata, annType);
		if (attributes == null) {
			throw new IllegalArgumentException(String.format(
					"@%s is not present on importing class '%s' as expected",
					annType.getSimpleName(), importingClassMetadata.getClassName()));
		}

		AdviceMode adviceMode = attributes.getEnum(getAdviceModeAttributeName());
		String[] imports = selectImports(adviceMode);
		if (imports == null) {
			throw new IllegalArgumentException("Unknown AdviceMode: " + adviceMode);
		}
		return imports;
	}

	/**
	 * Determine which classes should be imported based on the given {@code AdviceMode}.
	 * <p>Returning {@code null} from this method indicates that the {@code AdviceMode}
	 * could not be handled or was unknown and that an {@code IllegalArgumentException}
	 * should be thrown.
	 * @param adviceMode the value of the {@linkplain #getAdviceModeAttributeName()
	 * advice mode attribute} for the annotation specified via generics.
	 * @return array containing classes to import (empty array if none;
	 * {@code null} if the given {@code AdviceMode} is unknown)
	 */
	/**
	 * 根据给定的{@code  AdviceMode}确定应导入哪些类。 
	 *  <p>从此方法返回{@code  null}表示{@code  AdviceMode}无法处理或未知，因此应抛出{@code  IllegalArgumentException}。 
	 *  
	 * @param  advisorMode通过泛型指定的注释的{@link  plain #getAdviceModeAttributeName（）通知模式属性}的值。 
	 *  
	 * @return 数组，其中包含要导入的类（如果没有则为空数组； 
	 * 如果给定的{@code  AdviceMode}未知，则为{@code  null}）
	 */
	@Nullable
	protected abstract String[] selectImports(AdviceMode adviceMode);

}
