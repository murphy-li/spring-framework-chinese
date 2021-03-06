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

package org.springframework.core.type.classreading;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

/**
 * {@link AnnotationVisitor} to recursively visit annotation attributes.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1.1
 * @deprecated As of Spring Framework 5.2, this class and related classes in this
 * package have been replaced by {@link SimpleAnnotationMetadataReadingVisitor}
 * and related classes for internal use within the framework.
 */
/**
 * {@link  AnnotationVisitor}来递归访问注释属性。 
 *  @author 克里斯·比姆斯（Chris Beams）@author  Juergen Hoeller @since 3.1.1起已弃用从Spring Framework 5.2起，此包中的此类和相关类已由{@link  SimpleAnnotationMetadataReadingVisitor}和内部使用的相关类替换。 
 * 在框架内。 
 * 
 */
@Deprecated
class RecursiveAnnotationAttributesVisitor extends AbstractRecursiveAnnotationVisitor {

	protected final String annotationType;


	public RecursiveAnnotationAttributesVisitor(
			String annotationType, AnnotationAttributes attributes, @Nullable ClassLoader classLoader) {

		super(classLoader, attributes);
		this.annotationType = annotationType;
	}


	@Override
	public void visitEnd() {
		AnnotationUtils.registerDefaultValues(this.attributes);
	}

}
