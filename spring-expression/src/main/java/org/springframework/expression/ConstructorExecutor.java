/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2013 the original author or authors.
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
 * 版权所有2002-2013的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.expression;


// TODO Is the resolver/executor model too pervasive in this package?
/**
 * Executors are built by resolvers and can be cached by the infrastructure to repeat an
 * operation quickly without going back to the resolvers. For example, the particular
 * constructor to run on a class may be discovered by the reflection constructor resolver
 * - it will then build a ConstructorExecutor that executes that constructor and the
 * ConstructorExecutor can be reused without needing to go back to the resolver to
 * discover the constructor again.
 *
 * <p>They can become stale, and in that case should throw an AccessException - this will
 * cause the infrastructure to go back to the resolvers to ask for a new one.
 *
 * @author Andy Clement
 * @since 3.0
 */
/**
 * 执行程序由解析程序构建，并且可以由基础结构缓存以快速重复操作，而无需返回解析程序。 
 * 例如，反射构造函数解析器可能会发现要在类上运行的特定构造函数-然后它将构建一个执行该构造函数的ConstructorExecutor，并且可以重用ConstructorExecutor，而无需返回到解析器再次发现该构造函数。 
 *  <p>它们可能会变得过时，并且在这种情况下，应引发AccessException-这将导致基础结构返回到解析器以请求新的解析器。 
 *  @author 安迪·克莱门特@始于3.0
 */
public interface ConstructorExecutor {

	/**
	 * Execute a constructor in the specified context using the specified arguments.
	 * @param context the evaluation context in which the command is being executed
	 * @param arguments the arguments to the constructor call, should match (in terms
	 * of number and type) whatever the command will need to run
	 * @return the new object
	 * @throws AccessException if there is a problem executing the command or the
	 * CommandExecutor is no longer valid
	 */
	/**
	 * 使用指定的参数在指定的上下文中执行构造函数。 
	 *  
	 * @param 上下文在其中执行命令的评估上下文
	 * @param 自变量构造函数调用的自变量应匹配（在数量和类型方面）命令运行新命令所需要的
	 * @return 如果执行命令有问题或CommandExecutor不再有效，则对象
	 * @throws  AccessException
	 */
	TypedValue execute(EvaluationContext context, Object... arguments) throws AccessException;

}
