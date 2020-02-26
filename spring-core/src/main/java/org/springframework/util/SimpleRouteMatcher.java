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

package org.springframework.util;

import java.util.Comparator;
import java.util.Map;

import org.springframework.lang.Nullable;

/**
 * {@code RouteMatcher} that delegates to a {@link PathMatcher}.
 *
 * <p><strong>Note:</strong> This implementation is not efficient since
 * {@code PathMatcher} treats paths and patterns as Strings. For more optimized
 * performance use the {@code PathPatternRouteMatcher} from {@code spring-web}
 * which enables use of parsed routes and patterns.
 *
 * @author Rossen Stoyanchev
 * @since 5.2
 */
/**
 * 委托给{@link  PathMatcher}的{@code  RouteMatcher}。 
 *  <p> <strong>注意</ strong>：由于{@code  PathMatcher}将路径和模式视为字符串，因此此实现效率不高。 
 * 为了获得更优化的性能，请使用{@code  spring-web}中的{@code  PathPatternRouteMatcher}，它可以使用已解析的路由和模式。 
 *  @author  Rossen Stoyanchev @从5.2开始
 */
public class SimpleRouteMatcher implements RouteMatcher {

	private final PathMatcher pathMatcher;


	/**
	 * Create a new {@code SimpleRouteMatcher} for the given
	 * {@link PathMatcher} delegate.
	 */
	/**
	 * 为给定的{@link  PathMatcher}委托创建一个新的{@code  SimpleRouteMatcher}。 
	 * 
	 */
	public SimpleRouteMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher is required");
		this.pathMatcher = pathMatcher;
	}

	/**
	 * Return the underlying {@link PathMatcher} delegate.
	 */
	/**
	 * 返回基础的{@link  PathMatcher}委托。 
	 * 
	 */
	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}


	@Override
	public Route parseRoute(String route) {
		return new DefaultRoute(route);
	}

	@Override
	public boolean isPattern(String route) {
		return this.pathMatcher.isPattern(route);
	}

	@Override
	public String combine(String pattern1, String pattern2) {
		return this.pathMatcher.combine(pattern1, pattern2);
	}

	@Override
	public boolean match(String pattern, Route route) {
		return this.pathMatcher.match(pattern, route.value());
	}

	@Override
	@Nullable
	public Map<String, String> matchAndExtract(String pattern, Route route) {
		if (!match(pattern, route)) {
			return null;
		}
		return this.pathMatcher.extractUriTemplateVariables(pattern, route.value());
	}

	@Override
	public Comparator<String> getPatternComparator(Route route) {
		return this.pathMatcher.getPatternComparator(route.value());
	}


	private static class DefaultRoute implements Route {

		private final String path;

		DefaultRoute(String path) {
			this.path = path;
		}

		@Override
		public String value() {
			return this.path;
		}

		@Override
		public String toString() {
			return value();
		}
	}

}
