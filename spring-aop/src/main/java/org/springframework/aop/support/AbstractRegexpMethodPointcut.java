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

package org.springframework.aop.support;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Abstract base regular expression pointcut bean. JavaBean properties are:
 * <ul>
 * <li>pattern: regular expression for the fully-qualified method names to match.
 * The exact regexp syntax will depend on the subclass (e.g. Perl5 regular expressions)
 * <li>patterns: alternative property taking a String array of patterns.
 * The result will be the union of these patterns.
 * </ul>
 *
 * <p>Note: the regular expressions must be a match. For example,
 * {@code .*get.*} will match com.mycom.Foo.getBar().
 * {@code get.*} will not.
 *
 * <p>This base class is serializable. Subclasses should declare all fields transient;
 * the {@link #initPatternRepresentation} method will be invoked again on deserialization.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 1.1
 * @see JdkRegexpMethodPointcut
 */
/**
 * 抽象基础正则表达式切入点bean。 
 *  JavaBean属性是：<ul> <li>模式：正则表达式，用于匹配完全限定的方法名称。 
 * 确切的regexp语法将取决于子类（例如Perl5正则表达式）<li> patterns：采用String数组模式的替代属性。 
 * 结果将是这些模式的结合。 
 *  </ ul> <p>注意：正则表达式必须匹配。 
 * 例如，{@code 。 
 * 得到。 
 *  }将与com.mycom.Foo.getBar（）匹配。 
 *  {@code 获取。 
 *  } 将不会。 
 *  <p>此基类是可序列化的。 
 * 子类应声明所有字段都是瞬态的； 
 *  {@link  #initPatternRepresentation}方法将在反序列化时再次调用。 
 *  @author  Rod Johnson @author  Juergen Hoeller @author  Rob Harrop @始于1.1 
 * @see  JdkRegexpMethodPointcut
 */
@SuppressWarnings("serial")
public abstract class AbstractRegexpMethodPointcut extends StaticMethodMatcherPointcut
		implements Serializable {

	/**
	 * Regular expressions to match.
	 */
	/**
	 * 要匹配的正则表达式。 
	 * 
	 */
	private String[] patterns = new String[0];

	/**
	 * Regular expressions <strong>not</strong> to match.
	 */
	/**
	 * 正则表达式<strong>不</ strong>匹配。 
	 * 
	 */
	private String[] excludedPatterns = new String[0];


	/**
	 * Convenience method when we have only a single pattern.
	 * Use either this method or {@link #setPatterns}, not both.
	 * @see #setPatterns
	 */
	/**
	 * 当我们只有一个模式时的便捷方法。 
	 * 使用此方法或{@link  #setPatterns}，请不要同时使用两者。 
	 *  
	 * @see  #setPatterns
	 */
	public void setPattern(String pattern) {
		setPatterns(pattern);
	}

	/**
	 * Set the regular expressions defining methods to match.
	 * Matching will be the union of all these; if any match, the pointcut matches.
	 * @see #setPattern
	 */
	/**
	 * 设置正则表达式以定义匹配的方法。 
	 * 匹配将是所有这些的结合。 
	 * 如果有匹配项，则切入点匹配。 
	 *  
	 * @see  #setPattern
	 */
	public void setPatterns(String... patterns) {
		Assert.notEmpty(patterns, "'patterns' must not be empty");
		this.patterns = new String[patterns.length];
		for (int i = 0; i < patterns.length; i++) {
			this.patterns[i] = StringUtils.trimWhitespace(patterns[i]);
		}
		initPatternRepresentation(this.patterns);
	}

	/**
	 * Return the regular expressions for method matching.
	 */
	/**
	 * 返回用于方法匹配的正则表达式。 
	 * 
	 */
	public String[] getPatterns() {
		return this.patterns;
	}

	/**
	 * Convenience method when we have only a single exclusion pattern.
	 * Use either this method or {@link #setExcludedPatterns}, not both.
	 * @see #setExcludedPatterns
	 */
	/**
	 * 当我们只有一个排除模式时的便捷方法。 
	 * 使用此方法或{@link  #setExcludedPatterns}，请不要同时使用两者。 
	 *  
	 * @see  #setExcludedPatterns
	 */
	public void setExcludedPattern(String excludedPattern) {
		setExcludedPatterns(excludedPattern);
	}

	/**
	 * Set the regular expressions defining methods to match for exclusion.
	 * Matching will be the union of all these; if any match, the pointcut matches.
	 * @see #setExcludedPattern
	 */
	/**
	 * 设置正则表达式定义方法以匹配排除项。 
	 * 匹配将是所有这些的结合。 
	 * 如果有匹配项，则切入点匹配。 
	 *  
	 * @see  #setExcludedPattern
	 */
	public void setExcludedPatterns(String... excludedPatterns) {
		Assert.notEmpty(excludedPatterns, "'excludedPatterns' must not be empty");
		this.excludedPatterns = new String[excludedPatterns.length];
		for (int i = 0; i < excludedPatterns.length; i++) {
			this.excludedPatterns[i] = StringUtils.trimWhitespace(excludedPatterns[i]);
		}
		initExcludedPatternRepresentation(this.excludedPatterns);
	}

	/**
	 * Returns the regular expressions for exclusion matching.
	 */
	/**
	 * 返回排除匹配的正则表达式。 
	 * 
	 */
	public String[] getExcludedPatterns() {
		return this.excludedPatterns;
	}


	/**
	 * Try to match the regular expression against the fully qualified name
	 * of the target class as well as against the method's declaring class,
	 * plus the name of the method.
	 */
	/**
	 * 尝试将正则表达式与目标类的完全限定名称以及方法的声明类以及方法名称进行匹配。 
	 * 
	 */
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return (matchesPattern(ClassUtils.getQualifiedMethodName(method, targetClass)) ||
				(targetClass != method.getDeclaringClass() &&
						matchesPattern(ClassUtils.getQualifiedMethodName(method, method.getDeclaringClass()))));
	}

	/**
	 * Match the specified candidate against the configured patterns.
	 * @param signatureString "java.lang.Object.hashCode" style signature
	 * @return whether the candidate matches at least one of the specified patterns
	 */
	/**
	 * 将指定的候选者与配置的模式匹配。 
	 *  
	 * @param  signatureString"java.lang.Object.hashCode"样式签名
	 * @return 候选人是否与至少一种指定模式匹配
	 */
	protected boolean matchesPattern(String signatureString) {
		for (int i = 0; i < this.patterns.length; i++) {
			boolean matched = matches(signatureString, i);
			if (matched) {
				for (int j = 0; j < this.excludedPatterns.length; j++) {
					boolean excluded = matchesExclusion(signatureString, j);
					if (excluded) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}


	/**
	 * Subclasses must implement this to initialize regexp pointcuts.
	 * Can be invoked multiple times.
	 * <p>This method will be invoked from the {@link #setPatterns} method,
	 * and also on deserialization.
	 * @param patterns the patterns to initialize
	 * @throws IllegalArgumentException in case of an invalid pattern
	 */
	/**
	 * 子类必须实现此功能以初始化regexp切入点。 
	 * 可以多次调用。 
	 *  <p>将从{@link  #setPatterns}方法以及反序列化中调用此方法。 
	 *  
	 * @param 对模式进行模式化以在无效模式的情况下初始化
	 * @throws  IllegalArgumentException
	 */
	protected abstract void initPatternRepresentation(String[] patterns) throws IllegalArgumentException;

	/**
	 * Subclasses must implement this to initialize regexp pointcuts.
	 * Can be invoked multiple times.
	 * <p>This method will be invoked from the {@link #setExcludedPatterns} method,
	 * and also on deserialization.
	 * @param patterns the patterns to initialize
	 * @throws IllegalArgumentException in case of an invalid pattern
	 */
	/**
	 * 子类必须实现此功能以初始化regexp切入点。 
	 * 可以多次调用。 
	 *  <p>将从{@link  #setExcludedPatterns}方法以及反序列化中调用此方法。 
	 *  
	 * @param 对模式进行模式化以在无效模式的情况下初始化
	 * @throws  IllegalArgumentException
	 */
	protected abstract void initExcludedPatternRepresentation(String[] patterns) throws IllegalArgumentException;

	/**
	 * Does the pattern at the given index match the given String?
	 * @param pattern the {@code String} pattern to match
	 * @param patternIndex index of pattern (starting from 0)
	 * @return {@code true} if there is a match, {@code false} otherwise
	 */
	/**
	 * 给定索引处的模式与给定String匹配吗？ 
	 * @param 模式{{@@code>字符串}模式以匹配模式的
	 * @param  patternIndex模式索引（从0开始）
	 * @return  {@code  true}如果存在匹配项，则{<@code > false}否则
	 */
	protected abstract boolean matches(String pattern, int patternIndex);

	/**
	 * Does the exclusion pattern at the given index match the given String?
	 * @param pattern the {@code String} pattern to match
	 * @param patternIndex index of pattern (starting from 0)
	 * @return {@code true} if there is a match, {@code false} otherwise
	 */
	/**
	 * 给定索引处的排除模式是否匹配给定String？ 
	 * @param 模式{{@@code>字符串}模式以匹配模式的
	 * @param  patternIndex模式索引（从0开始）
	 * @return  {@code  true}如果存在匹配项，则{<@code > false}否则
	 */
	protected abstract boolean matchesExclusion(String pattern, int patternIndex);


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractRegexpMethodPointcut)) {
			return false;
		}
		AbstractRegexpMethodPointcut otherPointcut = (AbstractRegexpMethodPointcut) other;
		return (Arrays.equals(this.patterns, otherPointcut.patterns) &&
				Arrays.equals(this.excludedPatterns, otherPointcut.excludedPatterns));
	}

	@Override
	public int hashCode() {
		int result = 27;
		for (String pattern : this.patterns) {
			result = 13 * result + pattern.hashCode();
		}
		for (String excludedPattern : this.excludedPatterns) {
			result = 13 * result + excludedPattern.hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		return getClass().getName() + ": patterns " + ObjectUtils.nullSafeToString(this.patterns) +
				", excluded patterns " + ObjectUtils.nullSafeToString(this.excludedPatterns);
	}

}
