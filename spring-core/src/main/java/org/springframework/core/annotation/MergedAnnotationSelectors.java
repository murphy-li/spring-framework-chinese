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

package org.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * {@link MergedAnnotationSelector} implementations that provide various options
 * for {@link MergedAnnotation} instances.
 *
 * @author Phillip Webb
 * @since 5.2
 * @see MergedAnnotations#get(Class, Predicate, MergedAnnotationSelector)
 * @see MergedAnnotations#get(String, Predicate, MergedAnnotationSelector)
 */
/**
 * {{@link> MergedAnnotationSelector}实现为{@link  MergedAnnotation}实例提供各种选项。 
 *  @author 菲利普·韦伯（Phillip Webb）@since 5.2起
 */
public abstract class MergedAnnotationSelectors {

	private static final MergedAnnotationSelector<?> NEAREST = new Nearest();

	private static final MergedAnnotationSelector<?> FIRST_DIRECTLY_DECLARED = new FirstDirectlyDeclared();


	private MergedAnnotationSelectors() {
	}


	/**
	 * Select the nearest annotation, i.e. the one with the lowest distance.
	 * @return a selector that picks the annotation with the lowest distance
	 */
	/**
	 * 选择最近的注释，即距离最小的注释。 
	 *  
	 * @return 一个选择器，该选择器选择距离最小的注释
	 */
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> MergedAnnotationSelector<A> nearest() {
		return (MergedAnnotationSelector<A>) NEAREST;
	}

	/**
	 * Select the first directly declared annotation when possible. If no direct
	 * annotations are declared then the nearest annotation is selected.
	 * @return a selector that picks the first directly declared annotation whenever possible
	 */
	/**
	 * 尽可能选择第一个直接声明的注释。 
	 * 如果未声明直接注释，则选择最近的注释。 
	 *  
	 * @return 选择器，它会尽可能选择第一个直接声明的注释
	 */
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> MergedAnnotationSelector<A> firstDirectlyDeclared() {
		return (MergedAnnotationSelector<A>) FIRST_DIRECTLY_DECLARED;
	}


	/**
	 * {@link MergedAnnotationSelector} to select the nearest annotation.
	 */
	/**
	 * {@link  MergedAnnotationSelector}选择最近的注释。 
	 * 
	 */
	private static class Nearest implements MergedAnnotationSelector<Annotation> {

		@Override
		public boolean isBestCandidate(MergedAnnotation<Annotation> annotation) {
			return annotation.getDistance() == 0;
		}

		@Override
		public MergedAnnotation<Annotation> select(
				MergedAnnotation<Annotation> existing, MergedAnnotation<Annotation> candidate) {

			if (candidate.getDistance() < existing.getDistance()) {
				return candidate;
			}
			return existing;
		}

	}


	/**
	 * {@link MergedAnnotationSelector} to select the first directly declared
	 * annotation.
	 */
	/**
	 * {@link  MergedAnnotationSelector}选择第一个直接声明的注释。 
	 * 
	 */
	private static class FirstDirectlyDeclared implements MergedAnnotationSelector<Annotation> {

		@Override
		public boolean isBestCandidate(MergedAnnotation<Annotation> annotation) {
			return annotation.getDistance() == 0;
		}

		@Override
		public MergedAnnotation<Annotation> select(
				MergedAnnotation<Annotation> existing, MergedAnnotation<Annotation> candidate) {

			if (existing.getDistance() > 0 && candidate.getDistance() == 0) {
				return candidate;
			}
			return existing;
		}

	}

}
