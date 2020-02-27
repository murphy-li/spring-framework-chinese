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

package org.springframework.web.servlet.mvc.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

/**
 * A logical disjunction (' || ') request condition that matches a request
 * against a set of URL path patterns.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
/**
 * 逻辑分离（'||'）请求条件，该条件将请求与一组URL路径模式相匹配。 
 *  @author  Rossen Stoyanchev @从3.1开始
 */
public final class PatternsRequestCondition extends AbstractRequestCondition<PatternsRequestCondition> {

	private final Set<String> patterns;

	private final UrlPathHelper pathHelper;

	private final PathMatcher pathMatcher;

	private final boolean useSuffixPatternMatch;

	private final boolean useTrailingSlashMatch;

	private final List<String> fileExtensions = new ArrayList<>();


	/**
	 * Creates a new instance with the given URL patterns. Each pattern that is
	 * not empty and does not start with "/" is prepended with "/".
	 * @param patterns 0 or more URL patterns; if 0 the condition will match to
	 * every request.
	 */
	/**
	 * 使用给定的URL模式创建一个新实例。 
	 * 每个不为空且不以"/"开头的模式都以"/"开头。 
	 *  
	 * @param 模式0个或多个URL模式； 
	 * 如果为0，则条件将匹配每个请求。 
	 * 
	 */
	public PatternsRequestCondition(String... patterns) {
		this(Arrays.asList(patterns), null, null, true, true, null);
	}

	/**
	 * Alternative constructor with additional, optional {@link UrlPathHelper},
	 * {@link PathMatcher}, and whether to automatically match trailing slashes.
	 * @param patterns the URL patterns to use; if 0, the condition will match to every request.
	 * @param urlPathHelper a {@link UrlPathHelper} for determining the lookup path for a request
	 * @param pathMatcher a {@link PathMatcher} for pattern path matching
	 * @param useTrailingSlashMatch whether to match irrespective of a trailing slash
	 * @since 5.2.4
	 */
	/**
	 * 带有附加的，可选的{@link  UrlPathHelper}，{<@link> PathMatcher}以及是否自动匹配尾部斜杠的备用构造函数。 
	 *  
	 * @param 设置要使用的URL模式； 
	 * 如果为0，则条件将匹配每个请求。 
	 *  
	 * @param  urlPathHelper a {@link  UrlPathHelper}用于确定请求的查找路径
	 * @param  pathMatcher a {@link  PathMatcher}用于模式路径匹配
	 * @param  useTrailingSlashMatch是否匹配（无论尾随如何）从5.2.4开始斜杠
	 */
	public PatternsRequestCondition(String[] patterns, @Nullable UrlPathHelper urlPathHelper,
			@Nullable PathMatcher pathMatcher, boolean useTrailingSlashMatch) {

		this(Arrays.asList(patterns), urlPathHelper, pathMatcher, false, useTrailingSlashMatch, null);
	}

	/**
	 * Alternative constructor with additional optional parameters.
	 * @param patterns the URL patterns to use; if 0, the condition will match to every request.
	 * @param urlPathHelper for determining the lookup path of a request
	 * @param pathMatcher for path matching with patterns
	 * @param useSuffixPatternMatch whether to enable matching by suffix (".*")
	 * @param useTrailingSlashMatch whether to match irrespective of a trailing slash
	 * @deprecated as of 5.2.4. See class-level note in
	 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}
	 * on the deprecation of path extension config options.
	 */
	/**
	 * 具有其他可选参数的替代构造函数。 
	 *  
	 * @param 设置要使用的URL模式； 
	 * 如果为0，则条件将匹配每个请求。 
	 *  
	 * @param  urlPathHelper，用于确定请求的查找路径
	 * @param  pathMatcher，用于与模式进行路径匹配
	 * @param  useSuffixPatternMatch是否启用后缀（"。 
	 * "）
	 * @param  useTrailingSlashMatch是否匹配从5.2.4开始不推荐使用尾部斜杠。 
	 * 有关弃用路径扩展配置选项，请参见{@link  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}中的类级别注释。 
	 * 
	 */
	@Deprecated
	public PatternsRequestCondition(String[] patterns, @Nullable UrlPathHelper urlPathHelper,
			@Nullable PathMatcher pathMatcher, boolean useSuffixPatternMatch, boolean useTrailingSlashMatch) {

		this(Arrays.asList(patterns), urlPathHelper, pathMatcher, useSuffixPatternMatch, useTrailingSlashMatch, null);
	}

	/**
	 * Alternative constructor with additional optional parameters.
	 * @param patterns the URL patterns to use; if 0, the condition will match to every request.
	 * @param urlPathHelper a {@link UrlPathHelper} for determining the lookup path for a request
	 * @param pathMatcher a {@link PathMatcher} for pattern path matching
	 * @param useSuffixPatternMatch whether to enable matching by suffix (".*")
	 * @param useTrailingSlashMatch whether to match irrespective of a trailing slash
	 * @param fileExtensions a list of file extensions to consider for path matching
	 * @deprecated as of 5.2.4. See class-level note in
	 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}
	 * on the deprecation of path extension config options.
	 */
	/**
	 * 具有其他可选参数的替代构造函数。 
	 *  
	 * @param 设置要使用的URL模式； 
	 * 如果为0，则条件将匹配每个请求。 
	 *  
	 * @param  urlPathHelper a {@link  UrlPathHelper}用于确定请求的查找路径
	 * @param  pathMatcher a {@link  PathMatcher}用于模式路径匹配
	 * @param  useSuffixPatternMatch是否启用后缀匹配（ "。 
	 * "）
	 * @param  useTrailingSlashMatch是否匹配，而不考虑后跟斜杠
	 * @param  fileExtensions从5.2.4版本开始，要考虑与@deprecated路径匹配的文件扩展名列表。 
	 * 有关弃用路径扩展配置选项，请参见{@link  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}中的类级别注释。 
	 * 
	 */
	@Deprecated
	public PatternsRequestCondition(String[] patterns, @Nullable UrlPathHelper urlPathHelper,
			@Nullable PathMatcher pathMatcher, boolean useSuffixPatternMatch,
			boolean useTrailingSlashMatch, @Nullable List<String> fileExtensions) {

		this(Arrays.asList(patterns), urlPathHelper, pathMatcher, useSuffixPatternMatch,
				useTrailingSlashMatch, fileExtensions);
	}

	/**
	 * Private constructor accepting a collection of patterns.
	 */
	/**
	 * 接受模式集合的私有构造函数。 
	 * 
	 */
	private PatternsRequestCondition(Collection<String> patterns, @Nullable UrlPathHelper urlPathHelper,
			@Nullable PathMatcher pathMatcher, boolean useSuffixPatternMatch,
			boolean useTrailingSlashMatch, @Nullable List<String> fileExtensions) {

		this.patterns = Collections.unmodifiableSet(prependLeadingSlash(patterns));
		this.pathHelper = urlPathHelper != null ? urlPathHelper : new UrlPathHelper();
		this.pathMatcher = pathMatcher != null ? pathMatcher : new AntPathMatcher();
		this.useSuffixPatternMatch = useSuffixPatternMatch;
		this.useTrailingSlashMatch = useTrailingSlashMatch;

		if (fileExtensions != null) {
			for (String fileExtension : fileExtensions) {
				if (fileExtension.charAt(0) != '.') {
					fileExtension = "." + fileExtension;
				}
				this.fileExtensions.add(fileExtension);
			}
		}
	}

	/**
	 * Private constructor for use when combining and matching.
	 */
	/**
	 * 组合和匹配时使用的私有构造函数。 
	 * 
	 */
	private PatternsRequestCondition(Set<String> patterns, PatternsRequestCondition other) {
		this.patterns = patterns;
		this.pathHelper = other.pathHelper;
		this.pathMatcher = other.pathMatcher;
		this.useSuffixPatternMatch = other.useSuffixPatternMatch;
		this.useTrailingSlashMatch = other.useTrailingSlashMatch;
		this.fileExtensions.addAll(other.fileExtensions);
	}


	private static Set<String> prependLeadingSlash(Collection<String> patterns) {
		if (patterns.isEmpty()) {
			return Collections.singleton("");
		}
		Set<String> result = new LinkedHashSet<>(patterns.size());
		for (String pattern : patterns) {
			if (StringUtils.hasLength(pattern) && !pattern.startsWith("/")) {
				pattern = "/" + pattern;
			}
			result.add(pattern);
		}
		return result;
	}

	public Set<String> getPatterns() {
		return this.patterns;
	}

	@Override
	protected Collection<String> getContent() {
		return this.patterns;
	}

	@Override
	protected String getToStringInfix() {
		return " || ";
	}

	/**
	 * Returns a new instance with URL patterns from the current instance ("this") and
	 * the "other" instance as follows:
	 * <ul>
	 * <li>If there are patterns in both instances, combine the patterns in "this" with
	 * the patterns in "other" using {@link PathMatcher#combine(String, String)}.
	 * <li>If only one instance has patterns, use them.
	 * <li>If neither instance has patterns, use an empty String (i.e. "").
	 * </ul>
	 */
	/**
	 * 返回一个新实例，该实例具有来自当前实例（"this"）和"other"实例的URL模式，如下所示：<ul> <li>如果两个实例中都有模式，请将"this"中的模式与"其他"使用{@link  PathMatcher＃combine（String，String）}。 
	 *  <li>如果只有一个实例具有模式，请使用它们。 
	 *  <li>如果两个实例都没有模式，请使用空字符串（即""）。 
	 *  </ ul>
	 */
	@Override
	public PatternsRequestCondition combine(PatternsRequestCondition other) {
		Set<String> result = new LinkedHashSet<>();
		if (!this.patterns.isEmpty() && !other.patterns.isEmpty()) {
			for (String pattern1 : this.patterns) {
				for (String pattern2 : other.patterns) {
					result.add(this.pathMatcher.combine(pattern1, pattern2));
				}
			}
		}
		else if (!this.patterns.isEmpty()) {
			result.addAll(this.patterns);
		}
		else if (!other.patterns.isEmpty()) {
			result.addAll(other.patterns);
		}
		else {
			result.add("");
		}
		return new PatternsRequestCondition(result, this);
	}

	/**
	 * Checks if any of the patterns match the given request and returns an instance
	 * that is guaranteed to contain matching patterns, sorted via
	 * {@link PathMatcher#getPatternComparator(String)}.
	 * <p>A matching pattern is obtained by making checks in the following order:
	 * <ul>
	 * <li>Direct match
	 * <li>Pattern match with ".*" appended if the pattern doesn't already contain a "."
	 * <li>Pattern match
	 * <li>Pattern match with "/" appended if the pattern doesn't already end in "/"
	 * </ul>
	 * @param request the current request
	 * @return the same instance if the condition contains no patterns;
	 * or a new condition with sorted matching patterns;
	 * or {@code null} if no patterns match.
	 */
	/**
	 * 检查是否有任何模式与给定请求匹配，并返回保证包含匹配模式的实例，该实例通过{@link  PathMatcher＃getPatternComparator（String）}进行排序。 
	 *  <p>通过按以下顺序进行检查来获得匹配的模式：<ul> <li>直接匹配<li>如果模式不包含"。 
	 * "，则将模式匹配添加"。 
	 * "。 
	 *  <li>模式匹配<li>如果模式尚未以"/"结尾，则在模式匹配后附加"/"</ ul> 
	 * @param 请求当前请求
	 * @return 同一实例（如果条件包含）没有模式； 
	 * 或具有匹配模式已排序的新条件； 
	 * 或{@code  null}（如果没有任何模式匹配）。 
	 * 
	 */
	@Override
	@Nullable
	public PatternsRequestCondition getMatchingCondition(HttpServletRequest request) {
		if (this.patterns.isEmpty()) {
			return this;
		}
		String lookupPath = this.pathHelper.getLookupPathForRequest(request, HandlerMapping.LOOKUP_PATH);
		List<String> matches = getMatchingPatterns(lookupPath);
		return !matches.isEmpty() ? new PatternsRequestCondition(new LinkedHashSet<>(matches), this) : null;
	}

	/**
	 * Find the patterns matching the given lookup path. Invoking this method should
	 * yield results equivalent to those of calling {@link #getMatchingCondition}.
	 * This method is provided as an alternative to be used if no request is available
	 * (e.g. introspection, tooling, etc).
	 * @param lookupPath the lookup path to match to existing patterns
	 * @return a collection of matching patterns sorted with the closest match at the top
	 */
	/**
	 * 查找与给定查找路径匹配的模式。 
	 * 调用此方法应产生与调用{@link  #getMatchingCondition}相同的结果。 
	 * 如果没有可用的请求（例如自省，工具等），则可以使用此方法作为替代方法。 
	 *  
	 * @param  lookupPath与现有模式匹配的查找路径
	 * @return 匹配模式的集合，其排序最接近的匹配项位于顶部
	 */
	public List<String> getMatchingPatterns(String lookupPath) {
		List<String> matches = null;
		for (String pattern : this.patterns) {
			String match = getMatchingPattern(pattern, lookupPath);
			if (match != null) {
				matches = matches != null ? matches : new ArrayList<>();
				matches.add(match);
			}
		}
		if (matches == null) {
			return Collections.emptyList();
		}
		if (matches.size() > 1) {
			matches.sort(this.pathMatcher.getPatternComparator(lookupPath));
		}
		return matches;
	}

	@Nullable
	private String getMatchingPattern(String pattern, String lookupPath) {
		if (pattern.equals(lookupPath)) {
			return pattern;
		}
		if (this.useSuffixPatternMatch) {
			if (!this.fileExtensions.isEmpty() && lookupPath.indexOf('.') != -1) {
				for (String extension : this.fileExtensions) {
					if (this.pathMatcher.match(pattern + extension, lookupPath)) {
						return pattern + extension;
					}
				}
			}
			else {
				boolean hasSuffix = pattern.indexOf('.') != -1;
				if (!hasSuffix && this.pathMatcher.match(pattern + ".*", lookupPath)) {
					return pattern + ".*";
				}
			}
		}
		if (this.pathMatcher.match(pattern, lookupPath)) {
			return pattern;
		}
		if (this.useTrailingSlashMatch) {
			if (!pattern.endsWith("/") && this.pathMatcher.match(pattern + "/", lookupPath)) {
				return pattern + "/";
			}
		}
		return null;
	}

	/**
	 * Compare the two conditions based on the URL patterns they contain.
	 * Patterns are compared one at a time, from top to bottom via
	 * {@link PathMatcher#getPatternComparator(String)}. If all compared
	 * patterns match equally, but one instance has more patterns, it is
	 * considered a closer match.
	 * <p>It is assumed that both instances have been obtained via
	 * {@link #getMatchingCondition(HttpServletRequest)} to ensure they
	 * contain only patterns that match the request and are sorted with
	 * the best matches on top.
	 */
	/**
	 * 根据它们包含的URL模式比较两个条件。 
	 * 通过{@link  PathMatcher＃getPatternComparator（String）}从上到下一次对模式进行比较。 
	 * 如果所有比较的模式均等地匹配，但是一个实例具有更多模式，则认为这是更接近的匹配。 
	 *  <p>假设两个实例都是通过{@link  #getMatchingCondition（HttpServletRequest）}获得的，以确保它们仅包含与请求匹配的模式，并且在最上面匹配最佳。 
	 * 
	 */
	@Override
	public int compareTo(PatternsRequestCondition other, HttpServletRequest request) {
		String lookupPath = this.pathHelper.getLookupPathForRequest(request, HandlerMapping.LOOKUP_PATH);
		Comparator<String> patternComparator = this.pathMatcher.getPatternComparator(lookupPath);
		Iterator<String> iterator = this.patterns.iterator();
		Iterator<String> iteratorOther = other.patterns.iterator();
		while (iterator.hasNext() && iteratorOther.hasNext()) {
			int result = patternComparator.compare(iterator.next(), iteratorOther.next());
			if (result != 0) {
				return result;
			}
		}
		if (iterator.hasNext()) {
			return -1;
		}
		else if (iteratorOther.hasNext()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
