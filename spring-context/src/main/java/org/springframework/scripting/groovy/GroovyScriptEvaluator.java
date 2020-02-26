/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2017 the original author or authors.
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
 * 版权所有2002-2017的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.scripting.groovy;

import java.io.IOException;
import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.lang.Nullable;
import org.springframework.scripting.ScriptCompilationException;
import org.springframework.scripting.ScriptEvaluator;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Groovy-based implementation of Spring's {@link ScriptEvaluator} strategy interface.
 *
 * @author Juergen Hoeller
 * @since 4.0
 * @see GroovyShell#evaluate(String, String)
 */
/**
 * Spring {@link  ScriptEvaluator}策略界面的基于Groovy的实现。 
 *  @author  Juergen Hoeller @从4.0开始
 * @see  GroovyShell＃evaluate（String，String）
 */
public class GroovyScriptEvaluator implements ScriptEvaluator, BeanClassLoaderAware {

	@Nullable
	private ClassLoader classLoader;

	private CompilerConfiguration compilerConfiguration = new CompilerConfiguration();


	/**
	 * Construct a new GroovyScriptEvaluator.
	 */
	/**
	 * 构造一个新的GroovyScriptEvaluator。 
	 * 
	 */
	public GroovyScriptEvaluator() {
	}

	/**
	 * Construct a new GroovyScriptEvaluator.
	 * @param classLoader the ClassLoader to use as a parent for the {@link GroovyShell}
	 */
	/**
	 * 构造一个新的GroovyScriptEvaluator。 
	 *  
	 * @param  classLoader用作{@link  GroovyShell}的父级的ClassLoader
	 */
	public GroovyScriptEvaluator(@Nullable ClassLoader classLoader) {
		this.classLoader = classLoader;
	}


	/**
	 * Set a custom compiler configuration for this evaluator.
	 * @since 4.3.3
	 * @see #setCompilationCustomizers
	 */
	/**
	 * 为此评估程序设置自定义编译器配置。 
	 *  @since 4.3.3 
	 * @see  #setCompilationCustomizers
	 */
	public void setCompilerConfiguration(@Nullable CompilerConfiguration compilerConfiguration) {
		this.compilerConfiguration =
				(compilerConfiguration != null ? compilerConfiguration : new CompilerConfiguration());
	}

	/**
	 * Return this evaluator's compiler configuration (never {@code null}).
	 * @since 4.3.3
	 * @see #setCompilerConfiguration
	 */
	/**
	 * 返回此评估程序的编译器配置（不要{<@@code> null}）。 
	 *  @since 4.3.3 
	 * @see  #setCompilerConfiguration
	 */
	public CompilerConfiguration getCompilerConfiguration() {
		return this.compilerConfiguration;
	}

	/**
	 * Set one or more customizers to be applied to this evaluator's compiler configuration.
	 * <p>Note that this modifies the shared compiler configuration held by this evaluator.
	 * @since 4.3.3
	 * @see #setCompilerConfiguration
	 */
	/**
	 * 设置一个或多个定制程序以应用于此评估程序的编译器配置。 
	 *  <p>请注意，这将修改此评估程序拥有的共享编译器配置。 
	 *  @since 4.3.3 
	 * @see  #setCompilerConfiguration
	 */
	public void setCompilationCustomizers(CompilationCustomizer... compilationCustomizers) {
		this.compilerConfiguration.addCompilationCustomizers(compilationCustomizers);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}


	@Override
	@Nullable
	public Object evaluate(ScriptSource script) {
		return evaluate(script, null);
	}

	@Override
	@Nullable
	public Object evaluate(ScriptSource script, @Nullable Map<String, Object> arguments) {
		GroovyShell groovyShell = new GroovyShell(
				this.classLoader, new Binding(arguments), this.compilerConfiguration);
		try {
			String filename = (script instanceof ResourceScriptSource ?
					((ResourceScriptSource) script).getResource().getFilename() : null);
			if (filename != null) {
				return groovyShell.evaluate(script.getScriptAsString(), filename);
			}
			else {
				return groovyShell.evaluate(script.getScriptAsString());
			}
		}
		catch (IOException ex) {
			throw new ScriptCompilationException(script, "Cannot access Groovy script", ex);
		}
		catch (GroovyRuntimeException ex) {
			throw new ScriptCompilationException(script, ex);
		}
	}

}
