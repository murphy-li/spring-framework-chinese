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

package org.springframework.core.type;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

/**
 * Interface that defines abstract access to the annotations of a specific
 * class, in a form that does not require that class to be loaded yet.
 *
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Phillip Webb
 * @author Sam Brannen
 * @since 2.5
 * @see StandardAnnotationMetadata
 * @see org.springframework.core.type.classreading.MetadataReader#getAnnotationMetadata()
 * @see AnnotatedTypeMetadata
 */
/**
 * 该接口定义了对特定类的注解的抽象访问，其形式尚不要求加载该类。 
 *  @author  Juergen Hoeller @author  Mark Fisher @author  Phillip Webb @author  Sam Brannen @since 2.5 
 * @see  StandardAnnotationMetadata 
 * @see  org.springframework.core.type.classreading.MetadataReader＃getAnnotationMetadata（ ）
 * @see  AnnotatedTypeMetadata
 */
public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {

	/**
	 * Get the fully qualified class names of all annotation types that
	 * are <em>present</em> on the underlying class.
	 * @return the annotation type names
	 */
	/**
	 * 获取在基础类上<em>存在</ em>的所有注释类型的标准类名。 
	 *  
	 * @return 注释类型名称
	 */
	default Set<String> getAnnotationTypes() {
		return getAnnotations().stream()
				.filter(MergedAnnotation::isDirectlyPresent)
				.map(annotation -> annotation.getType().getName())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Get the fully qualified class names of all meta-annotation types that
	 * are <em>present</em> on the given annotation type on the underlying class.
	 * @param annotationName the fully qualified class name of the meta-annotation
	 * type to look for
	 * @return the meta-annotation type names, or an empty set if none found
	 */
	/**
	 * 获取在基础类的给定注释类型上<em>存在</ em>的所有元注释类型的全限定类名。 
	 *  
	 * @param 注释名称元注释类型的完全限定的类名，以查找
	 * @return 元注释类型的名称； 
	 * 如果未找到，则为空集
	 */
	default Set<String> getMetaAnnotationTypes(String annotationName) {
		MergedAnnotation<?> annotation = getAnnotations().get(annotationName, MergedAnnotation::isDirectlyPresent);
		if (!annotation.isPresent()) {
			return Collections.emptySet();
		}
		return MergedAnnotations.from(annotation.getType(), SearchStrategy.INHERITED_ANNOTATIONS).stream()
				.map(mergedAnnotation -> mergedAnnotation.getType().getName())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Determine whether an annotation of the given type is <em>present</em> on
	 * the underlying class.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @return {@code true} if a matching annotation is present
	 */
	/**
	 * 确定给定类型的注释在基础类上是否为<em> present </ em>。 
	 *  
	 * @param 注释名称如果存在匹配的注释，则注释类型的完全限定的类名称，以查找
	 * @return  {@code  true}
	 */
	default boolean hasAnnotation(String annotationName) {
		return getAnnotations().isDirectlyPresent(annotationName);
	}

	/**
	 * Determine whether the underlying class has an annotation that is itself
	 * annotated with the meta-annotation of the given type.
	 * @param metaAnnotationName the fully qualified class name of the
	 * meta-annotation type to look for
	 * @return {@code true} if a matching meta-annotation is present
	 */
	/**
	 * 确定基础类是否具有本身使用给定类型的元注释进行注释的注释。 
	 *  
	 * @param  metaAnnotationName如果存在匹配的元注释，则为元注释类型的完全限定的类名，以查找
	 * @return  {@code  true}
	 */
	default boolean hasMetaAnnotation(String metaAnnotationName) {
		return getAnnotations().get(metaAnnotationName,
				MergedAnnotation::isMetaPresent).isPresent();
	}

	/**
	 * Determine whether the underlying class has any methods that are
	 * annotated (or meta-annotated) with the given annotation type.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 */
	/**
	 * 确定基础类是否具有使用给定注释类型进行注释（或元注释）的任何方法。 
	 *  
	 * @param 注解名称要查找的注解类型的全限定类名
	 */
	default boolean hasAnnotatedMethods(String annotationName) {
		return !getAnnotatedMethods(annotationName).isEmpty();
	}

	/**
	 * Retrieve the method metadata for all methods that are annotated
	 * (or meta-annotated) with the given annotation type.
	 * <p>For any returned method, {@link MethodMetadata#isAnnotated} will
	 * return {@code true} for the given annotation type.
	 * @param annotationName the fully qualified class name of the annotation
	 * type to look for
	 * @return a set of {@link MethodMetadata} for methods that have a matching
	 * annotation. The return value will be an empty set if no methods match
	 * the annotation type.
	 */
	/**
	 * 检索所有使用给定注释类型进行注释（或元注释）的方法的方法元数据。 
	 *  <p>对于任何返回的方法，对于给定的注释类型，{<@link> MethodMetadata＃isAnnotated}将返回{@code  true}。 
	 *  
	 * @param 注解名称注解类型的完全限定的类名，以查找
	 * @return 一组{@link  MethodMetadata}以查找具有匹配注解的方法。 
	 * 如果没有方法与注释类型匹配，则返回值将为空集。 
	 * 
	 */
	Set<MethodMetadata> getAnnotatedMethods(String annotationName);


	/**
	 * Factory method to create a new {@link AnnotationMetadata} instance
	 * for the given class using standard reflection.
	 * @param type the class to introspect
	 * @return a new {@link AnnotationMetadata} instance
	 * @since 5.2
	 */
	/**
	 * 使用标准反射为给定类创建新的{@link  AnnotationMetadata}实例的工厂方法。 
	 *  
	 * @param type 要进行自省的类
	 * @return 一个新的{@link  AnnotationMetadata}实例@5.2起
	 */
	static AnnotationMetadata introspect(Class<?> type) {
		return StandardAnnotationMetadata.from(type);
	}

}
