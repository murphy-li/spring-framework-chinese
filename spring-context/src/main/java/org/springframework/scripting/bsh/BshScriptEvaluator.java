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

package org.springframework.scripting.bsh;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import bsh.EvalError;
import bsh.Interpreter;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.lang.Nullable;
import org.springframework.scripting.ScriptCompilationException;
import org.springframework.scripting.ScriptEvaluator;
import org.springframework.scripting.ScriptSource;

/**
 * BeanShell-based implementation of Spring's {@link ScriptEvaluator} strategy interface.
 *
 * @author Juergen Hoeller
 * @since 4.0
 * @see Interpreter#eval(String)
 */
/**
 * Spring的{@link  ScriptEvaluator}策略接口的基于BeanShell的实现。 
 *  @author  Juergen Hoeller @从4.0开始
 * @see 解释器#eval（字符串）
 */
public class BshScriptEvaluator implements ScriptEvaluator, BeanClassLoaderAware {

	@Nullable
	private ClassLoader classLoader;


	/**
	 * Construct a new BshScriptEvaluator.
	 */
	/**
	 * 构造一个新的BshScriptEvaluator。 
	 * 
	 */
	public BshScriptEvaluator() {
	}

	/**
	 * Construct a new BshScriptEvaluator.
	 * @param classLoader the ClassLoader to use for the {@link Interpreter}
	 */
	/**
	 * 构造一个新的BshScriptEvaluator。 
	 *  
	 * @param  classLoader用于{@link 解释器}的ClassLoader
	 */
	public BshScriptEvaluator(ClassLoader classLoader) {
		this.classLoader = classLoader;
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
		try {
			Interpreter interpreter = new Interpreter();
			interpreter.setClassLoader(this.classLoader);
			if (arguments != null) {
				for (Map.Entry<String, Object> entry : arguments.entrySet()) {
					interpreter.set(entry.getKey(), entry.getValue());
				}
			}
			return interpreter.eval(new StringReader(script.getScriptAsString()));
		}
		catch (IOException ex) {
			throw new ScriptCompilationException(script, "Cannot access BeanShell script", ex);
		}
		catch (EvalError ex) {
			throw new ScriptCompilationException(script, ex);
		}
	}

}
