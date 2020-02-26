/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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
 * 根据一项或多项贡献者许可协议获得了Apache Software Foundation（ASF）的许可。 
 * 有关版权拥有权的其他信息，请参见随本作品分发的NOTICE文件。 
 *  ASF根据Apache许可2.0版（"许可"）将该文件许可给您； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.apache.commons.logging;

/**
 * A simple logging interface abstracting logging APIs.  In order to be
 * instantiated successfully by {@link LogFactory}, classes that implement
 * this interface must have a constructor that takes a single String
 * parameter representing the "name" of this Log.
 *
 * <p>The six logging levels used by <code>Log</code> are (in order):
 * <ol>
 * <li>trace (the least serious)</li>
 * <li>debug</li>
 * <li>info</li>
 * <li>warn</li>
 * <li>error</li>
 * <li>fatal (the most serious)</li>
 * </ol>
 *
 * The mapping of these log levels to the concepts used by the underlying
 * logging system is implementation dependent.
 * The implementation should ensure, though, that this ordering behaves
 * as expected.
 *
 * <p>Performance is often a logging concern.
 * By examining the appropriate property,
 * a component can avoid expensive operations (producing information
 * to be logged).
 *
 * <p>For example,
 * <pre>
 *    if (log.isDebugEnabled()) {
 *        ... do something expensive ...
 *        log.debug(theResult);
 *    }
 * </pre>
 *
 * <p>Configuration of the underlying logging system will generally be done
 * external to the Logging APIs, through whatever mechanism is supported by
 * that system.
 *
 * @author Juergen Hoeller (for the {@code spring-jcl} variant)
 * @since 5.0
 */
/**
 * 一个简单的日志记录接口，抽象了日志记录API。 
 * 为了由{@link  LogFactory}成功实例化，实现此接口的类必须具有一个构造函数，该构造函数采用单个String参数表示此Log的"名称"。 
 *  <p> <code> Log </ code>使用的六个日志记录级别是（按顺序）：<ol> <li> trace（最不严重的）</ li> <li> debug </ li> <li>信息</ li> <li>警告</ li> <li>错误</ li> <li>致命（最严重）</ li> </ ol>这些日志级别到目标使用的概念的映射底层日志记录系统取决于实现。 
 * 但是，实现应确保此排序行为符合预期。 
 *  <p>性能通常是一个测井问题。 
 * 通过检查适当的属性，组件可以避免昂贵的操作（产生要记录的信息）。 
 *  <p>例如，<pre> if（log.isDebugEnabled（））{...做一些昂贵的事情... log.debug（theResult）; } </ pre> <p>底层日志系统的配置通常将通过该系统支持的任何机制在Logging API外部进行。 
 *  @author  Juergen Hoeller（用于{@code  spring-jcl}变体）@从5.0开始
 */
public interface Log {

	/**
	 * Is fatal logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than fatal.
	 * @return true if fatal is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用了致命日志记录？ <p>调用此方法可避免在日志级别超过致命级别时必须执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了致命功能，则为true。 
	 * 
	 */
	boolean isFatalEnabled();

	/**
	 * Is error logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than error.
	 * @return true if error is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用错误日志记录？ <p>调用此方法可避免在日志级别超过错误时必须执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了错误，则为true。 
	 * 
	 */
	boolean isErrorEnabled();

	/**
	 * Is warn logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than warn.
	 * @return true if warn is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用警告日志记录？ <p>调用此方法可避免在日志级别大于警告级别时执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了警告，则为true。 
	 * 
	 */
	boolean isWarnEnabled();

	/**
	 * Is info logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than info.
	 * @return true if info is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用了信息记录？ <p>调用此方法可避免在日志级别大于信息级别时不必执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了信息，则为true。 
	 * 
	 */
	boolean isInfoEnabled();

	/**
	 * Is debug logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than debug.
	 * @return true if debug is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用了调试日志记录？ <p>调用此方法可避免在日志级别大于调试级别时不必执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了调试，则为true。 
	 * 
	 */
	boolean isDebugEnabled();

	/**
	 * Is trace logging currently enabled?
	 * <p>Call this method to prevent having to perform expensive operations
	 * (for example, <code>String</code> concatenation)
	 * when the log level is more than trace.
	 * @return true if trace is enabled in the underlying logger.
	 */
	/**
	 * 当前是否启用了跟踪日志记录？ <p>调用此方法可避免在日志级别超过跟踪级别时不必执行昂贵的操作（例如，<code> String </ code>串联）。 
	 *  
	 * @return 如果在基础记录器中启用了跟踪，则为true。 
	 * 
	 */
	boolean isTraceEnabled();


	/**
	 * Logs a message with fatal log level.
	 * @param message log this message
	 */
	/**
	 * 使用致命日志级别记录消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void fatal(Object message);

	/**
	 * Logs an error with fatal log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用致命日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void fatal(Object message, Throwable t);

	/**
	 * Logs a message with error log level.
	 * @param message log this message
	 */
	/**
	 * 记录具有错误日志级别的消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void error(Object message);

	/**
	 * Logs an error with error log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用错误日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void error(Object message, Throwable t);

	/**
	 * Logs a message with warn log level.
	 * @param message log this message
	 */
	/**
	 * 记录警告日志级别的消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void warn(Object message);

	/**
	 * Logs an error with warn log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用警告日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void warn(Object message, Throwable t);

	/**
	 * Logs a message with info log level.
	 * @param message log this message
	 */
	/**
	 * 记录具有信息日志级别的消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void info(Object message);

	/**
	 * Logs an error with info log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用信息日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void info(Object message, Throwable t);

	/**
	 * Logs a message with debug log level.
	 * @param message log this message
	 */
	/**
	 * 用调试日志级别记录一条消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void debug(Object message);

	/**
	 * Logs an error with debug log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用调试日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void debug(Object message, Throwable t);

	/**
	 * Logs a message with trace log level.
	 * @param message log this message
	 */
	/**
	 * 记录具有跟踪日志级别的消息。 
	 *  
	 * @param 消息记录此消息
	 */
	void trace(Object message);

	/**
	 * Logs an error with trace log level.
	 * @param message log this message
	 * @param t log this cause
	 */
	/**
	 * 使用跟踪日志级别记录错误。 
	 *  
	 * @param 消息记录此消息
	 * @param  t记录此原因
	 */
	void trace(Object message, Throwable t);

}
