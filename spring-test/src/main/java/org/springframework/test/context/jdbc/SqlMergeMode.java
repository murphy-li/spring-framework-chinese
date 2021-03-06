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

package org.springframework.test.context.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @SqlMergeMode} is used to annotate a test class or test method to
 * configure whether method-level {@code @Sql} declarations are merged with
 * class-level {@code @Sql} declarations.
 *
 * <p>A method-level {@code @SqlMergeMode} declaration overrides a class-level
 * declaration.
 *
 * <p>If {@code @SqlMergeMode} is not declared on a test class or test method,
 * {@link MergeMode#OVERRIDE} will be used by default.
 *
 * <p>This annotation may be used as a <em>meta-annotation</em> to create custom
 * <em>composed annotations</em> with attribute overrides.
 *
 * @author Sam Brannen
 * @author Dmitry Semukhin
 * @since 5.2
 * @see Sql
 * @see MergeMode#MERGE
 * @see MergeMode#OVERRIDE
 */
/**
 * {@code  @SqlMergeMode}用于注释测试类或测试方法，以配置是否将方法级{@code  @Sql}声明与类级{@code  @Sql}声明合并。 
 *  <p>方法级别的{@code  @SqlMergeMode}声明将覆盖类级别的声明。 
 *  <p>如果未在测试类或测试方法上声明{@code  @SqlMergeMode}，则默认情况下将使用{@link  MergeMode＃OVERRIDE}。 
 *  <p>此注释可用作<em>元注释</ em>，以创建具有属性覆盖的自定义<em>组成的注释</ em>。 
 *  @author  Sam Brannen @author  Dmitry Semukhin @从5.2起
 * @see  Sql 
 * @see  MergeMode＃MERGE 
 * @see  MergeMode＃OVERRIDE
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlMergeMode {

	/**
	 * Indicates whether method-level {@code @Sql} annotations should be merged
	 * with class-level {@code @Sql} annotations or override them.
	 */
	/**
	 * 指示是将方法级别的{@code  @Sql}注释与类级别的{@code  @Sql}注释合并还是覆盖它们。 
	 * 
	 */
	MergeMode value();


	/**
	 * Enumeration of <em>modes</em> that dictate whether method-level {@code @Sql}
	 * declarations are merged with class-level {@code @Sql} declarations.
	 */
	/**
	 * <em>模式</ em>的枚举，指示方法级{@code  @Sql}声明是否与类级{@code  @Sql}声明合并。 
	 * 
	 */
	enum MergeMode {

		/**
		 * Indicates that method-level {@code @Sql} declarations should be merged
		 * with class-level {@code @Sql} declarations, with class-level SQL
		 * scripts and statements executed before method-level scripts and
		 * statements.
		 */
		/**
		 * 指示应将方法级{@code  @Sql}声明与类级{@code  @Sql}声明合并，并在方法级脚本和语句之前执行类级SQL脚本和语句。 
		 * 
		 */
		MERGE,

		/**
		 * Indicates that method-level {@code @Sql} declarations should override
		 * class-level {@code @Sql} declarations.
		 */
		/**
		 * 指示方法级别的{@code  @Sql}声明应覆盖类级别的{@code  @Sql}声明。 
		 * 
		 */
		OVERRIDE

	}

}
