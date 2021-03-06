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

package org.springframework.scripting;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

/**
 * Exception to be thrown on script compilation failure.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */
/**
 * 脚本编译失败时引发的异常。 
 *  @author  Juergen Hoeller @始于2.0
 */
@SuppressWarnings("serial")
public class ScriptCompilationException extends NestedRuntimeException {

	@Nullable
	private final ScriptSource scriptSource;


	/**
	 * Constructor for ScriptCompilationException.
	 * @param msg the detail message
	 */
	/**
	 * ScriptCompilationException的构造方法。 
	 *  
	 * @param  msg详细信息
	 */
	public ScriptCompilationException(String msg) {
		super(msg);
		this.scriptSource = null;
	}

	/**
	 * Constructor for ScriptCompilationException.
	 * @param msg the detail message
	 * @param cause the root cause (usually from using an underlying script compiler API)
	 */
	/**
	 * ScriptCompilationException的构造方法。 
	 *  
	 * @param  msg详细消息
	 * @param 引起根本原因（通常是使用基础脚本编译器API）
	 */
	public ScriptCompilationException(String msg, Throwable cause) {
		super(msg, cause);
		this.scriptSource = null;
	}

	/**
	 * Constructor for ScriptCompilationException.
	 * @param scriptSource the source for the offending script
	 * @param msg the detail message
	 * @since 4.2
	 */
	/**
	 * ScriptCompilationException的构造方法。 
	 *  
	 * @param 脚本来源有问题的脚本的源
	 * @param  msg详细信息@4.2起
	 */
	public ScriptCompilationException(ScriptSource scriptSource, String msg) {
		super("Could not compile " + scriptSource + ": " + msg);
		this.scriptSource = scriptSource;
	}

	/**
	 * Constructor for ScriptCompilationException.
	 * @param scriptSource the source for the offending script
	 * @param cause the root cause (usually from using an underlying script compiler API)
	 */
	/**
	 * ScriptCompilationException的构造方法。 
	 *  
	 * @param  scriptSource引起问题的脚本
	 * @param 的根本原因（通常是使用基础脚本编译器API的原因）
	 */
	public ScriptCompilationException(ScriptSource scriptSource, Throwable cause) {
		super("Could not compile " + scriptSource, cause);
		this.scriptSource = scriptSource;
	}

	/**
	 * Constructor for ScriptCompilationException.
	 * @param scriptSource the source for the offending script
	 * @param msg the detail message
	 * @param cause the root cause (usually from using an underlying script compiler API)
	 */
	/**
	 * ScriptCompilationException的构造方法。 
	 *  
	 * @param  scriptSource引起问题的脚本的源
	 * @param  msg详细消息
	 * @param 引起根本原因（通常是使用基础脚本编译器API）
	 */
	public ScriptCompilationException(ScriptSource scriptSource, String msg, Throwable cause) {
		super("Could not compile " + scriptSource + ": " + msg, cause);
		this.scriptSource = scriptSource;
	}


	/**
	 * Return the source for the offending script.
	 * @return the source, or {@code null} if not available
	 */
	/**
	 * 返回有问题的脚本的源。 
	 *  
	 * @return 源，如果不可用，则返回{@code  null}
	 */
	@Nullable
	public ScriptSource getScriptSource() {
		return this.scriptSource;
	}

}
