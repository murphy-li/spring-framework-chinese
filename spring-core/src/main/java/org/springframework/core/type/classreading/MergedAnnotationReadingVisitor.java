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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.asm.Type;
import org.springframework.core.annotation.AnnotationFilter;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * {@link AnnotationVisitor} that can be used to construct a
 * {@link MergedAnnotation}.
 *
 * @author Phillip Webb
 * @since 5.2
 * @param <A> the annotation type
 */
/**
 * {@link  AnnotationVisitor}，可用于构造{@link  MergedAnnotation}。 
 *  @author  Phillip Webb @5.2起，
 * @param  <A>注释类型
 */
class MergedAnnotationReadingVisitor<A extends Annotation> extends AnnotationVisitor {

	@Nullable
	private final ClassLoader classLoader;

	@Nullable
	private final Object source;

	private final Class<A> annotationType;

	private final Consumer<MergedAnnotation<A>> consumer;

	private final Map<String, Object> attributes = new LinkedHashMap<>(4);


	public MergedAnnotationReadingVisitor(@Nullable ClassLoader classLoader, @Nullable Object source,
			Class<A> annotationType, Consumer<MergedAnnotation<A>> consumer) {

		super(SpringAsmInfo.ASM_VERSION);
		this.classLoader = classLoader;
		this.source = source;
		this.annotationType = annotationType;
		this.consumer = consumer;
	}


	@Override
	public void visit(String name, Object value) {
		if (value instanceof Type) {
			value = ((Type) value).getClassName();
		}
		this.attributes.put(name, value);
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		visitEnum(descriptor, value, enumValue -> this.attributes.put(name, enumValue));
	}

	@Override
	@Nullable
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {
		return visitAnnotation(descriptor, annotation -> this.attributes.put(name, annotation));
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		return new ArrayVisitor(value -> this.attributes.put(name, value));
	}

	@Override
	public void visitEnd() {
		MergedAnnotation<A> annotation = MergedAnnotation.of(
				this.classLoader, this.source, this.annotationType, this.attributes);
		this.consumer.accept(annotation);
	}

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> void visitEnum(String descriptor, String value, Consumer<E> consumer) {
		String className = Type.getType(descriptor).getClassName();
		Class<E> type = (Class<E>) ClassUtils.resolveClassName(className, this.classLoader);
		consumer.accept(Enum.valueOf(type, value));
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private <T extends Annotation> AnnotationVisitor visitAnnotation(
			String descriptor, Consumer<MergedAnnotation<T>> consumer) {

		String className = Type.getType(descriptor).getClassName();
		if (AnnotationFilter.PLAIN.matches(className)) {
			return null;
		}
		Class<T> type = (Class<T>) ClassUtils.resolveClassName(className, this.classLoader);
		return new MergedAnnotationReadingVisitor<>(this.classLoader, this.source, type, consumer);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	static <A extends Annotation> AnnotationVisitor get(@Nullable ClassLoader classLoader,
			@Nullable Supplier<Object> sourceSupplier, String descriptor, boolean visible,
			Consumer<MergedAnnotation<A>> consumer) {

		if (!visible) {
			return null;
		}

		String typeName = Type.getType(descriptor).getClassName();
		if (AnnotationFilter.PLAIN.matches(typeName)) {
			return null;
		}

		Object source = (sourceSupplier != null ? sourceSupplier.get() : null);
		try {
			Class<A> annotationType = (Class<A>) ClassUtils.forName(typeName, classLoader);
			return new MergedAnnotationReadingVisitor<>(classLoader, source, annotationType, consumer);
		}
		catch (ClassNotFoundException | LinkageError ex) {
			return null;
		}
	}


	/**
	 * {@link AnnotationVisitor} to deal with array attributes.
	 */
	/**
	 * {@link  AnnotationVisitor}处理数组属性。 
	 * 
	 */
	private class ArrayVisitor extends AnnotationVisitor {

		private final List<Object> elements = new ArrayList<>();

		private final Consumer<Object[]> consumer;

		ArrayVisitor(Consumer<Object[]> consumer) {
			super(SpringAsmInfo.ASM_VERSION);
			this.consumer = consumer;
		}

		@Override
		public void visit(String name, Object value) {
			if (value instanceof Type) {
				value = ((Type) value).getClassName();
			}
			this.elements.add(value);
		}

		@Override
		public void visitEnum(String name, String descriptor, String value) {
			MergedAnnotationReadingVisitor.this.visitEnum(descriptor, value, this.elements::add);
		}

		@Override
		@Nullable
		public AnnotationVisitor visitAnnotation(String name, String descriptor) {
			return MergedAnnotationReadingVisitor.this.visitAnnotation(descriptor, this.elements::add);
		}

		@Override
		public void visitEnd() {
			Class<?> componentType = getComponentType();
			Object[] array = (Object[]) Array.newInstance(componentType, this.elements.size());
			this.consumer.accept(this.elements.toArray(array));
		}

		private Class<?> getComponentType() {
			if (this.elements.isEmpty()) {
				return Object.class;
			}
			Object firstElement = this.elements.get(0);
			if (firstElement instanceof Enum) {
				return ((Enum<?>) firstElement).getDeclaringClass();
			}
			return firstElement.getClass();
		}
	}

}
