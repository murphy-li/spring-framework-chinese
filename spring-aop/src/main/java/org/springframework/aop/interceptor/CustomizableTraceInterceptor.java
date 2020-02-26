/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.aop.interceptor;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;

import org.springframework.core.Constants;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

/**
 * {@code MethodInterceptor} implementation that allows for highly customizable
 * method-level tracing, using placeholders.
 *
 * <p>Trace messages are written on method entry, and if the method invocation succeeds
 * on method exit. If an invocation results in an exception, then an exception message
 * is written. The contents of these trace messages is fully customizable and special
 * placeholders are available to allow you to include runtime information in your log
 * messages. The placeholders available are:
 *
 * <p><ul>
 * <li>{@code $[methodName]} - replaced with the name of the method being invoked</li>
 * <li>{@code $[targetClassName]} - replaced with the name of the class that is
 * the target of the invocation</li>
 * <li>{@code $[targetClassShortName]} - replaced with the short name of the class
 * that is the target of the invocation</li>
 * <li>{@code $[returnValue]} - replaced with the value returned by the invocation</li>
 * <li>{@code $[argumentTypes]} - replaced with a comma-separated list of the
 * short class names of the method arguments</li>
 * <li>{@code $[arguments]} - replaced with a comma-separated list of the
 * {@code String} representation of the method arguments</li>
 * <li>{@code $[exception]} - replaced with the {@code String} representation
 * of any {@code Throwable} raised during the invocation</li>
 * <li>{@code $[invocationTime]} - replaced with the time, in milliseconds,
 * taken by the method invocation</li>
 * </ul>
 *
 * <p>There are restrictions on which placeholders can be used in which messages:
 * see the individual message properties for details on the valid placeholders.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setEnterMessage
 * @see #setExitMessage
 * @see #setExceptionMessage
 * @see SimpleTraceInterceptor
 */
/**
 * {@code  MethodInterceptor}实现，允许使用占位符进行高度可自定义的方法级跟踪。 
 *  <p>跟踪消息写在方法入口，如果方法调用在方法出口成功则写。 
 * 如果调用导致异常，则将写入异常消息。 
 * 这些跟踪消息的内容是完全可定制的，并且特殊的占位符可用于允许您在日志消息中包括运行时信息。 
 * 可用的占位符为：<p> <ul> <li> {<@code> $ [methodName]}-替换为所调用方法的名称</ li> <li> {<@code> $ [targetClassName] }-替换为作为调用目标的类的名称</ li> <li> {<@code> $ [targetClassShortName]}-替换为作为调用目标的类的短名称< / li> <li> {<@code> $ [returnValue]}-替换为调用返回的值</ li> <li> {<@code> $ [argumentTypes]}-替换为逗号分隔的列表方法参数的简短类名称的一部分</ li> <li> {<@code> $ [arguments]}-替换为方法参数的{@code  String}表示形式的逗号分隔列表</ </ li> <li> {<@code> $ [exception]}-替换为调用期间引发的任何{@code  Throwable}的{@code  String}表示形式</ li> <li> {<@code> $ [invocationTime]}-替换为方法调用所花费的时间（以毫秒为单位）</ li> </ ul> <p>对whic有限制h占位符可用于消息中：有关有效占位符的详细信息，请参见各个消息属性。 
 *  @author 罗布·哈罗普（Rob Harrop）@author 于尔根·霍勒（Juergen Hoeller）@从1.2开始
 * @see  #setEnterMessage 
 * @see  #setExitMessage 
 * @see  #setExceptionMessage 
 * @see  SimpleTraceInterceptor
 */
@SuppressWarnings("serial")
public class CustomizableTraceInterceptor extends AbstractTraceInterceptor {

	/**
	 * The {@code $[methodName]} placeholder.
	 * Replaced with the name of the method being invoked.
	 */
	/**
	 * {@code  $ [methodName]}占位符。 
	 * 替换为要调用的方法的名称。 
	 * 
	 */
	public static final String PLACEHOLDER_METHOD_NAME = "$[methodName]";

	/**
	 * The {@code $[targetClassName]} placeholder.
	 * Replaced with the fully-qualified name of the {@code Class}
	 * of the method invocation target.
	 */
	/**
	 * {@code  $ [targetClassName]}占位符。 
	 * 替换为方法调用目标的{@code  Class}的完全限定名称。 
	 * 
	 */
	public static final String PLACEHOLDER_TARGET_CLASS_NAME = "$[targetClassName]";

	/**
	 * The {@code $[targetClassShortName]} placeholder.
	 * Replaced with the short name of the {@code Class} of the
	 * method invocation target.
	 */
	/**
	 * {@code  $ [targetClassShortName]}占位符。 
	 * 替换为方法调用目标的{@code  Class}的简称。 
	 * 
	 */
	public static final String PLACEHOLDER_TARGET_CLASS_SHORT_NAME = "$[targetClassShortName]";

	/**
	 * The {@code $[returnValue]} placeholder.
	 * Replaced with the {@code String} representation of the value
	 * returned by the method invocation.
	 */
	/**
	 * {@code  $ [returnValue]}占位符。 
	 * 用方法调用返回的值的{@code  String}表示形式替换。 
	 * 
	 */
	public static final String PLACEHOLDER_RETURN_VALUE = "$[returnValue]";

	/**
	 * The {@code $[argumentTypes]} placeholder.
	 * Replaced with a comma-separated list of the argument types for the
	 * method invocation. Argument types are written as short class names.
	 */
	/**
	 * {@code  $ [argumentTypes]}占位符。 
	 * 替换为方法调用的参数类型的逗号分隔列表。 
	 * 参数类型被写为简短的类名。 
	 * 
	 */
	public static final String PLACEHOLDER_ARGUMENT_TYPES = "$[argumentTypes]";

	/**
	 * The {@code $[arguments]} placeholder.
	 * Replaced with a comma separated list of the argument values for the
	 * method invocation. Relies on the {@code toString()} method of
	 * each argument type.
	 */
	/**
	 * {@code  $ [arguments]}占位符。 
	 * 用逗号分隔的方法调用参数值列表替换。 
	 * 依赖于每种参数类型的{@code  toString（）}方法。 
	 * 
	 */
	public static final String PLACEHOLDER_ARGUMENTS = "$[arguments]";

	/**
	 * The {@code $[exception]} placeholder.
	 * Replaced with the {@code String} representation of any
	 * {@code Throwable} raised during method invocation.
	 */
	/**
	 * {@code  $ [exception]}占位符。 
	 * 用方法调用期间引发的任何{@code  Throwable}的{@code  String}表示形式替换。 
	 * 
	 */
	public static final String PLACEHOLDER_EXCEPTION = "$[exception]";

	/**
	 * The {@code $[invocationTime]} placeholder.
	 * Replaced with the time taken by the invocation (in milliseconds).
	 */
	/**
	 * {@code  $ [invocationTime]}占位符。 
	 * 替换为调用所花费的时间（以毫秒为单位）。 
	 * 
	 */
	public static final String PLACEHOLDER_INVOCATION_TIME = "$[invocationTime]";

	/**
	 * The default message used for writing method entry messages.
	 */
	/**
	 * 用于编写方法条目消息的默认消息。 
	 * 
	 */
	private static final String DEFAULT_ENTER_MESSAGE = "Entering method '" +
			PLACEHOLDER_METHOD_NAME + "' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]";

	/**
	 * The default message used for writing method exit messages.
	 */
	/**
	 * 用于编写方法退出消息的默认消息。 
	 * 
	 */
	private static final String DEFAULT_EXIT_MESSAGE = "Exiting method '" +
			PLACEHOLDER_METHOD_NAME + "' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]";

	/**
	 * The default message used for writing exception messages.
	 */
	/**
	 * 用于编写异常消息的默认消息。 
	 * 
	 */
	private static final String DEFAULT_EXCEPTION_MESSAGE = "Exception thrown in method '" +
			PLACEHOLDER_METHOD_NAME + "' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]";

	/**
	 * The {@code Pattern} used to match placeholders.
	 */
	/**
	 * {@code 模式}用于匹配占位符。 
	 * 
	 */
	private static final Pattern PATTERN = Pattern.compile("\\$\\[\\p{Alpha}+]");

	/**
	 * The {@code Set} of allowed placeholders.
	 */
	/**
	 * {@code  Set}允许的占位符。 
	 * 
	 */
	private static final Set<Object> ALLOWED_PLACEHOLDERS =
			new Constants(CustomizableTraceInterceptor.class).getValues("PLACEHOLDER_");


	/**
	 * The message for method entry.
	 */
	/**
	 * 用于方法输入的消息。 
	 * 
	 */
	private String enterMessage = DEFAULT_ENTER_MESSAGE;

	/**
	 * The message for method exit.
	 */
	/**
	 * 方法退出的消息。 
	 * 
	 */
	private String exitMessage = DEFAULT_EXIT_MESSAGE;

	/**
	 * The message for exceptions during method execution.
	 */
	/**
	 * 方法执行期间的异常消息。 
	 * 
	 */
	private String exceptionMessage = DEFAULT_EXCEPTION_MESSAGE;


	/**
	 * Set the template used for method entry log messages.
	 * This template can contain any of the following placeholders:
	 * <ul>
	 * <li>{@code $[targetClassName]}</li>
	 * <li>{@code $[targetClassShortName]}</li>
	 * <li>{@code $[argumentTypes]}</li>
	 * <li>{@code $[arguments]}</li>
	 * </ul>
	 */
	/**
	 * 设置用于方法输入日志消息的模板。 
	 * 该模板可以包含以下任何占位符：<ul> <li> {<@code> $ [targetClassName]} </ li> <li> {<@code> $ [targetClassShortName]} </ li> <li> {@code  $ [argumentTypes]} </ li> <li> {<@code> $ [arguments]} </ li> </ ul>
	 */
	public void setEnterMessage(String enterMessage) throws IllegalArgumentException {
		Assert.hasText(enterMessage, "enterMessage must not be empty");
		checkForInvalidPlaceholders(enterMessage);
		Assert.doesNotContain(enterMessage, PLACEHOLDER_RETURN_VALUE,
				"enterMessage cannot contain placeholder " + PLACEHOLDER_RETURN_VALUE);
		Assert.doesNotContain(enterMessage, PLACEHOLDER_EXCEPTION,
				"enterMessage cannot contain placeholder " + PLACEHOLDER_EXCEPTION);
		Assert.doesNotContain(enterMessage, PLACEHOLDER_INVOCATION_TIME,
				"enterMessage cannot contain placeholder " + PLACEHOLDER_INVOCATION_TIME);
		this.enterMessage = enterMessage;
	}

	/**
	 * Set the template used for method exit log messages.
	 * This template can contain any of the following placeholders:
	 * <ul>
	 * <li>{@code $[targetClassName]}</li>
	 * <li>{@code $[targetClassShortName]}</li>
	 * <li>{@code $[argumentTypes]}</li>
	 * <li>{@code $[arguments]}</li>
	 * <li>{@code $[returnValue]}</li>
	 * <li>{@code $[invocationTime]}</li>
	 * </ul>
	 */
	/**
	 * 设置用于方法出口日志消息的模板。 
	 * 该模板可以包含以下任何占位符：<ul> <li> {<@code> $ [targetClassName]} </ li> <li> {<@code> $ [targetClassShortName]} </ li> <li> {@code  $ [argumentTypes]} </ li> <li> {<@code> $ [arguments]} </ li> <li> {<@code> $ [returnValue]} </ li> <li > {<@code> $ [invocationTime]} </ li> </ ul>
	 */
	public void setExitMessage(String exitMessage) {
		Assert.hasText(exitMessage, "exitMessage must not be empty");
		checkForInvalidPlaceholders(exitMessage);
		Assert.doesNotContain(exitMessage, PLACEHOLDER_EXCEPTION,
				"exitMessage cannot contain placeholder" + PLACEHOLDER_EXCEPTION);
		this.exitMessage = exitMessage;
	}

	/**
	 * Set the template used for method exception log messages.
	 * This template can contain any of the following placeholders:
	 * <ul>
	 * <li>{@code $[targetClassName]}</li>
	 * <li>{@code $[targetClassShortName]}</li>
	 * <li>{@code $[argumentTypes]}</li>
	 * <li>{@code $[arguments]}</li>
	 * <li>{@code $[exception]}</li>
	 * </ul>
	 */
	/**
	 * 设置用于方法异常日志消息的模板。 
	 * 该模板可以包含以下任何占位符：<ul> <li> {<@code> $ [targetClassName]} </ li> <li> {<@code> $ [targetClassShortName]} </ li> <li> {@code  $ [argumentTypes]} </ li> <li> {<@code> $ [arguments]} </ li> <li> {<@code> $ [exception]} </ li> </ ul>
	 */
	public void setExceptionMessage(String exceptionMessage) {
		Assert.hasText(exceptionMessage, "exceptionMessage must not be empty");
		checkForInvalidPlaceholders(exceptionMessage);
		Assert.doesNotContain(exceptionMessage, PLACEHOLDER_RETURN_VALUE,
				"exceptionMessage cannot contain placeholder " + PLACEHOLDER_RETURN_VALUE);
		this.exceptionMessage = exceptionMessage;
	}


	/**
	 * Writes a log message before the invocation based on the value of {@code enterMessage}.
	 * If the invocation succeeds, then a log message is written on exit based on the value
	 * {@code exitMessage}. If an exception occurs during invocation, then a message is
	 * written based on the value of {@code exceptionMessage}.
	 * @see #setEnterMessage
	 * @see #setExitMessage
	 * @see #setExceptionMessage
	 */
	/**
	 * 根据{@code  enterMessage}的值在调用之前写一条日志消息。 
	 * 如果调用成功，则会在退出时基于值{@code  exitMessage}写入一条日志消息。 
	 * 如果在调用过程中发生异常，则会根据{@code  exceptionMessage}的值编写一条消息。 
	 *  
	 * @see  #setEnterMessage 
	 * @see  #setExitMessage 
	 * @see  #setExceptionMessage
	 */
	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		String name = ClassUtils.getQualifiedMethodName(invocation.getMethod());
		StopWatch stopWatch = new StopWatch(name);
		Object returnValue = null;
		boolean exitThroughException = false;
		try {
			stopWatch.start(name);
			writeToLog(logger,
					replacePlaceholders(this.enterMessage, invocation, null, null, -1));
			returnValue = invocation.proceed();
			return returnValue;
		}
		catch (Throwable ex) {
			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}
			exitThroughException = true;
			writeToLog(logger, replacePlaceholders(
					this.exceptionMessage, invocation, null, ex, stopWatch.getTotalTimeMillis()), ex);
			throw ex;
		}
		finally {
			if (!exitThroughException) {
				if (stopWatch.isRunning()) {
					stopWatch.stop();
				}
				writeToLog(logger, replacePlaceholders(
						this.exitMessage, invocation, returnValue, null, stopWatch.getTotalTimeMillis()));
			}
		}
	}

	/**
	 * Replace the placeholders in the given message with the supplied values,
	 * or values derived from those supplied.
	 * @param message the message template containing the placeholders to be replaced
	 * @param methodInvocation the {@code MethodInvocation} being logged.
	 * Used to derive values for all placeholders except {@code $[exception]}
	 * and {@code $[returnValue]}.
	 * @param returnValue any value returned by the invocation.
	 * Used to replace the {@code $[returnValue]} placeholder. May be {@code null}.
	 * @param throwable any {@code Throwable} raised during the invocation.
	 * The value of {@code Throwable.toString()} is replaced for the
	 * {@code $[exception]} placeholder. May be {@code null}.
	 * @param invocationTime the value to write in place of the
	 * {@code $[invocationTime]} placeholder
	 * @return the formatted output to write to the log
	 */
	/**
	 * 用提供的值或从提供的值派生的值替换给定消息中的占位符。 
	 *  
	 * @param 消息，消息模板包含要替换的占位符。 
	 * 
	 * @param  methodInvocation正在记录的{@code  MethodInvocation}。 
	 * 用于导出所有占位符的值，除了{@code  $ [exception]}和{@code  $ [returnValue]}。 
	 *  
	 * @param  returnValue调用返回的任何值。 
	 * 用于替换{@code  $ [returnValue]}占位符。 
	 * 可能为{@code  null}。 
	 *  
	 * @param 抛出任何在调用过程中引发的{@code  Throwable}。 
	 *  {@code  $ Throwable.toString（）}的值将替换为{@code  $ [exception]}占位符。 
	 * 可能为{@code  null}。 
	 *  
	 * @param  invocationTime该值代替{@code  $ [invocationTime]}占位符
	 * @return 格式化后的输出写入日志
	 */
	protected String replacePlaceholders(String message, MethodInvocation methodInvocation,
			@Nullable Object returnValue, @Nullable Throwable throwable, long invocationTime) {

		Matcher matcher = PATTERN.matcher(message);

		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			String match = matcher.group();
			if (PLACEHOLDER_METHOD_NAME.equals(match)) {
				matcher.appendReplacement(output, Matcher.quoteReplacement(methodInvocation.getMethod().getName()));
			}
			else if (PLACEHOLDER_TARGET_CLASS_NAME.equals(match)) {
				String className = getClassForLogging(methodInvocation.getThis()).getName();
				matcher.appendReplacement(output, Matcher.quoteReplacement(className));
			}
			else if (PLACEHOLDER_TARGET_CLASS_SHORT_NAME.equals(match)) {
				String shortName = ClassUtils.getShortName(getClassForLogging(methodInvocation.getThis()));
				matcher.appendReplacement(output, Matcher.quoteReplacement(shortName));
			}
			else if (PLACEHOLDER_ARGUMENTS.equals(match)) {
				matcher.appendReplacement(output,
						Matcher.quoteReplacement(StringUtils.arrayToCommaDelimitedString(methodInvocation.getArguments())));
			}
			else if (PLACEHOLDER_ARGUMENT_TYPES.equals(match)) {
				appendArgumentTypes(methodInvocation, matcher, output);
			}
			else if (PLACEHOLDER_RETURN_VALUE.equals(match)) {
				appendReturnValue(methodInvocation, matcher, output, returnValue);
			}
			else if (throwable != null && PLACEHOLDER_EXCEPTION.equals(match)) {
				matcher.appendReplacement(output, Matcher.quoteReplacement(throwable.toString()));
			}
			else if (PLACEHOLDER_INVOCATION_TIME.equals(match)) {
				matcher.appendReplacement(output, Long.toString(invocationTime));
			}
			else {
				// Should not happen since placeholders are checked earlier.
				throw new IllegalArgumentException("Unknown placeholder [" + match + "]");
			}
		}
		matcher.appendTail(output);

		return output.toString();
	}

	/**
	 * Adds the {@code String} representation of the method return value
	 * to the supplied {@code StringBuffer}. Correctly handles
	 * {@code null} and {@code void} results.
	 * @param methodInvocation the {@code MethodInvocation} that returned the value
	 * @param matcher the {@code Matcher} containing the matched placeholder
	 * @param output the {@code StringBuffer} to write output to
	 * @param returnValue the value returned by the method invocation.
	 */
	/**
	 * 将方法返回值的{@code  String}表示形式添加到提供的{@code  StringBuffer}中。 
	 * 正确处理{@code  null}和{@code  void}的结果。 
	 *  
	 * @param  methodInvocation {{@@code> MethodInvocation}返回值
	 * @param 匹配器{{@@code> Matcher}包含匹配的占位符
	 * @param 输出{@code  StringBuffer}写入输出返回值
	 * @param  returnValue方法调用返回的值。 
	 * 
	 */
	private void appendReturnValue(
			MethodInvocation methodInvocation, Matcher matcher, StringBuffer output, @Nullable Object returnValue) {

		if (methodInvocation.getMethod().getReturnType() == void.class) {
			matcher.appendReplacement(output, "void");
		}
		else if (returnValue == null) {
			matcher.appendReplacement(output, "null");
		}
		else {
			matcher.appendReplacement(output, Matcher.quoteReplacement(returnValue.toString()));
		}
	}

	/**
	 * Adds a comma-separated list of the short {@code Class} names of the
	 * method argument types to the output. For example, if a method has signature
	 * {@code put(java.lang.String, java.lang.Object)} then the value returned
	 * will be {@code String, Object}.
	 * @param methodInvocation the {@code MethodInvocation} being logged.
	 * Arguments will be retrieved from the corresponding {@code Method}.
	 * @param matcher the {@code Matcher} containing the state of the output
	 * @param output the {@code StringBuffer} containing the output
	 */
	/**
	 * 将方法参数类型的短{@code  Class}名称的逗号分隔列表添加到输出中。 
	 * 例如，如果方法的签名为{@code  put（java.lang.String，java.lang.Object）}，则返回的值为{@code  String，Object}。 
	 *  
	 * @param  methodInvocation正在记录的{@code  MethodInvocation}。 
	 * 参数将从相应的{@code 方法}中检索。 
	 *  
	 * @param 匹配器{@code  Matcher}包含输出的状态
	 * @param 输出{{@@code> StringBuffer}包含输出
	 */
	private void appendArgumentTypes(MethodInvocation methodInvocation, Matcher matcher, StringBuffer output) {
		Class<?>[] argumentTypes = methodInvocation.getMethod().getParameterTypes();
		String[] argumentTypeShortNames = new String[argumentTypes.length];
		for (int i = 0; i < argumentTypeShortNames.length; i++) {
			argumentTypeShortNames[i] = ClassUtils.getShortName(argumentTypes[i]);
		}
		matcher.appendReplacement(output,
				Matcher.quoteReplacement(StringUtils.arrayToCommaDelimitedString(argumentTypeShortNames)));
	}

	/**
	 * Checks to see if the supplied {@code String} has any placeholders
	 * that are not specified as constants on this class and throws an
	 * {@code IllegalArgumentException} if so.
	 */
	/**
	 * 检查提供的{@code  String}是否具有未在此类上指定为常量的任何占位符，如果存在，则抛出{@code  IllegalArgumentException}。 
	 * 
	 */
	private void checkForInvalidPlaceholders(String message) throws IllegalArgumentException {
		Matcher matcher = PATTERN.matcher(message);
		while (matcher.find()) {
			String match = matcher.group();
			if (!ALLOWED_PLACEHOLDERS.contains(match)) {
				throw new IllegalArgumentException("Placeholder [" + match + "] is not valid");
			}
		}
	}

}
