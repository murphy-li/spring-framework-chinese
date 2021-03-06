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

package org.springframework.web.servlet.mvc.method.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;
import org.springframework.web.servlet.handler.RequestMatchResult;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;
import org.springframework.web.servlet.mvc.condition.CompositeRequestCondition;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

/**
 * Creates {@link RequestMappingInfo} instances from type and method-level
 * {@link RequestMapping @RequestMapping} annotations in
 * {@link Controller @Controller} classes.
 *
 * <p><strong>Note:</strong></p> In 5.2.4,
 * {@link #setUseSuffixPatternMatch(boolean) useSuffixPatternMatch} and
 * {@link #setUseRegisteredSuffixPatternMatch(boolean) useRegisteredSuffixPatternMatch}
 * are deprecated in order to discourage use of path extensions for request
 * mapping and for content negotiation (with similar deprecations in
 * {@link ContentNegotiationManager}). For further context, please read issue
 * <a href="https://github.com/spring-projects/spring-framework/issues/24179">#24719</a>.
 *
 * <p>In 5.3, {@link #setUseRegisteredSuffixPatternMatch(boolean) useRegisteredSuffixPatternMatch}
 * switches to being on by default so that path matching becomes constrained
 * to registered suffixes only.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @author Sam Brannen
 * @since 3.1
 */
/**
 * 根据{@link  Controller @Controller}类中的类型和方法级别的{@link  RequestMapping @RequestMapping}注解创建{@link  RequestMappingInfo}实例。 
 *  <p> <strong>注意：</ strong> </ p>在5.2.4中，{@link  #setUseSuffixPatternMatch（boolean）useSuffixPatternMatch}和{@link  #setUseRegisteredSuffixPatternMatch（boolean）useRegisteredSuffixPatternMatch}按顺序弃用不鼓励使用路径扩展进行请求映射和内容协商（在{@link  ContentNegotiationManager}中使用类似的弃用）。 
 * 有关更多背景信息，请阅读问题<a href="https://github.com/spring-projects/spring-framework/issues/24179">＃24719 </a>。 
 *  <p>在5.3中，{<@link> #setUseRegisteredSuffixPatternMatch（boolean）useRegisteredSuffixPatternMatch}默认情况下处于启用状态，因此路径匹配仅限于已注册的后缀。 
 *  @author  Arjen Poutsma @author  Rossen Stoyanchev @author  Sam Brannen @since 3.1
 */
public class RequestMappingHandlerMapping extends RequestMappingInfoHandlerMapping
		implements MatchableHandlerMapping, EmbeddedValueResolverAware {

	private boolean useSuffixPatternMatch = true;

	private boolean useRegisteredSuffixPatternMatch = false;

	private boolean useTrailingSlashMatch = true;

	private Map<String, Predicate<Class<?>>> pathPrefixes = new LinkedHashMap<>();

	private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

	@Nullable
	private StringValueResolver embeddedValueResolver;

	private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();


	/**
	 * Whether to use suffix pattern match (".*") when matching patterns to
	 * requests. If enabled a method mapped to "/users" also matches to "/users.*".
	 * <p>The default value is {@code true}.
	 * <p>Also see {@link #setUseRegisteredSuffixPatternMatch(boolean)} for
	 * more fine-grained control over specific suffixes to allow.
	 * @deprecated as of 5.2.4. See class level comment about deprecation of
	 * path extension config options.
	 */
	/**
	 * 在将模式与请求匹配时是否使用后缀模式匹配（"。 
	 * "）。 
	 * 如果启用，则映射到"/ users"的方法也将匹配"/ users"。 
	 *  <p>默认值为{@code  true}。 
	 *  <p>另请参见{@link  #setUseRegisteredSuffixPatternMatch（boolean）}，以更精细地控制要允许的特定后缀。 
	 * 从5.2.4开始不推荐使用。 
	 * 请参阅类级注释，以弃用路径扩展配置选项。 
	 * 
	 */
	@Deprecated
	public void setUseSuffixPatternMatch(boolean useSuffixPatternMatch) {
		this.useSuffixPatternMatch = useSuffixPatternMatch;
	}

	/**
	 * Whether suffix pattern matching should work only against path extensions
	 * explicitly registered with the {@link ContentNegotiationManager}. This
	 * is generally recommended to reduce ambiguity and to avoid issues such as
	 * when a "." appears in the path for other reasons.
	 * <p>By default this is set to "false".
	 * @deprecated as of 5.2.4. See class level comment about deprecation of
	 * path extension config options note also that in 5.3 the default for this
	 * property will switch from {@code false} to {@code true}.
	 */
	/**
	 * 后缀模式匹配是否只应对在{@link  ContentNegotiationManager}中显式注册的路径扩展有效。 
	 * 通常建议这样做以减少歧义并避免出现诸如"。 
	 * "之类的问题。 
	 * 由于其他原因出现在路径中。 
	 *  <p>默认情况下将其设置为"false"。 
	 * 从5.2.4开始不推荐使用。 
	 * 请参阅有关弃用路径扩展配置选项的类级别注释，另请注意，在5.3中，此属性的默认值将从{@code  false}切换为{@code  true}。 
	 * 
	 */
	@Deprecated
	public void setUseRegisteredSuffixPatternMatch(boolean useRegisteredSuffixPatternMatch) {
		this.useRegisteredSuffixPatternMatch = useRegisteredSuffixPatternMatch;
		this.useSuffixPatternMatch = (useRegisteredSuffixPatternMatch || this.useSuffixPatternMatch);
	}

	/**
	 * Whether to match to URLs irrespective of the presence of a trailing slash.
	 * If enabled a method mapped to "/users" also matches to "/users/".
	 * <p>The default value is {@code true}.
	 */
	/**
	 * 是否与URL匹配，无论是否存在斜杠。 
	 * 如果启用，则映射到"/ users"的方法也将匹配"/ users /"。 
	 *  <p>默认值为{@code  true}。 
	 * 
	 */
	public void setUseTrailingSlashMatch(boolean useTrailingSlashMatch) {
		this.useTrailingSlashMatch = useTrailingSlashMatch;
	}

	/**
	 * Configure path prefixes to apply to controller methods.
	 * <p>Prefixes are used to enrich the mappings of every {@code @RequestMapping}
	 * method whose controller type is matched by the corresponding
	 * {@code Predicate}. The prefix for the first matching predicate is used.
	 * <p>Consider using {@link org.springframework.web.method.HandlerTypePredicate
	 * HandlerTypePredicate} to group controllers.
	 * @param prefixes a map with path prefixes as key
	 * @since 5.1
	 */
	/**
	 * 配置路径前缀以应用于控制器方法。 
	 *  <p>前缀用于丰富其控制器类型与相应的{@code 谓词}匹配的每个{@code  @RequestMapping}方法的映射。 
	 * 使用第一个匹配谓词的前缀。 
	 *  <p>考虑使用{@link  org.springframework.web.method.HandlerTypePredicate HandlerTypePredicate}对控制器进行分组。 
	 *  
	 * @param 为路径添加前缀，路径前缀为键@@since 5.1
	 */
	public void setPathPrefixes(Map<String, Predicate<Class<?>>> prefixes) {
		this.pathPrefixes = Collections.unmodifiableMap(new LinkedHashMap<>(prefixes));
	}

	/**
	 * The configured path prefixes as a read-only, possibly empty map.
	 * @since 5.1
	 */
	/**
	 * 配置的路径前缀为只读，可能为空的映射。 
	 *  @5.1起
	 */
	public Map<String, Predicate<Class<?>>> getPathPrefixes() {
		return this.pathPrefixes;
	}

	/**
	 * Set the {@link ContentNegotiationManager} to use to determine requested media types.
	 * If not set, the default constructor is used.
	 */
	/**
	 * 设置{@link  ContentNegotiationManager}以用于确定请求的媒体类型。 
	 * 如果未设置，则使用默认构造函数。 
	 * 
	 */
	public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
		Assert.notNull(contentNegotiationManager, "ContentNegotiationManager must not be null");
		this.contentNegotiationManager = contentNegotiationManager;
	}

	/**
	 * Return the configured {@link ContentNegotiationManager}.
	 */
	/**
	 * 返回已配置的{@link  ContentNegotiationManager}。 
	 * 
	 */
	public ContentNegotiationManager getContentNegotiationManager() {
		return this.contentNegotiationManager;
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver = resolver;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void afterPropertiesSet() {
		this.config = new RequestMappingInfo.BuilderConfiguration();
		this.config.setUrlPathHelper(getUrlPathHelper());
		this.config.setPathMatcher(getPathMatcher());
		this.config.setSuffixPatternMatch(useSuffixPatternMatch());
		this.config.setTrailingSlashMatch(useTrailingSlashMatch());
		this.config.setRegisteredSuffixPatternMatch(useRegisteredSuffixPatternMatch());
		this.config.setContentNegotiationManager(getContentNegotiationManager());

		super.afterPropertiesSet();
	}


	/**
	 * Whether to use registered suffixes for pattern matching.
	 * @deprecated as of 5.2.4. See class-level note on the deprecation of path
	 * extension config options.
	 */
	/**
	 * 是否使用注册的后缀进行模式匹配。 
	 * 从5.2.4开始不推荐使用。 
	 * 请参阅有关弃用路径扩展配置选项的类级别注释。 
	 * 
	 */
	@Deprecated
	public boolean useSuffixPatternMatch() {
		return this.useSuffixPatternMatch;
	}

	/**
	 * Whether to use registered suffixes for pattern matching.
	 * @deprecated as of 5.2.4. See class-level note on the deprecation of path
	 * extension config options.
	 */
	/**
	 * 是否使用注册的后缀进行模式匹配。 
	 * 从5.2.4开始不推荐使用。 
	 * 请参阅有关弃用路径扩展配置选项的类级别注释。 
	 * 
	 */
	@Deprecated
	public boolean useRegisteredSuffixPatternMatch() {
		return this.useRegisteredSuffixPatternMatch;
	}

	/**
	 * Whether to match to URLs irrespective of the presence of a trailing slash.
	 */
	/**
	 * 是否与URL匹配，无论是否存在斜杠。 
	 * 
	 */
	public boolean useTrailingSlashMatch() {
		return this.useTrailingSlashMatch;
	}

	/**
	 * Return the file extensions to use for suffix pattern matching.
	 * @deprecated as of 5.2.4. See class-level note on the deprecation of path
	 * extension config options.
	 */
	/**
	 * 返回用于后缀模式匹配的文件扩展名。 
	 * 从5.2.4开始不推荐使用。 
	 * 请参阅有关弃用路径扩展配置选项的类级别注释。 
	 * 
	 */
	@Nullable
	@Deprecated
	@SuppressWarnings("deprecation")
	public List<String> getFileExtensions() {
		return this.config.getFileExtensions();
	}


	/**
	 * {@inheritDoc}
	 * <p>Expects a handler to have either a type-level @{@link Controller}
	 * annotation or a type-level @{@link RequestMapping} annotation.
	 */
	/**
	 * {@inheritDoc} <p>期望处理程序具有类型级别@{<@link> Controller}注释或类型级别@{<@link> RequestMapping}注释。 
	 * 
	 */
	@Override
	protected boolean isHandler(Class<?> beanType) {
		return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
				AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
	}

	/**
	 * Uses method and type-level @{@link RequestMapping} annotations to create
	 * the RequestMappingInfo.
	 * @return the created RequestMappingInfo, or {@code null} if the method
	 * does not have a {@code @RequestMapping} annotation.
	 * @see #getCustomMethodCondition(Method)
	 * @see #getCustomTypeCondition(Class)
	 */
	/**
	 * 使用方法和类型级别的@{<@link> RequestMapping}注解创建RequestMappingInfo。 
	 *  
	 * @return 创建的RequestMappingInfo，如果该方法没有{@code  @RequestMapping}注解，则为{@code  null}。 
	 *  
	 * @see  #getCustomMethodCondition（方法）
	 * @see  #getCustomTypeCondition（类）
	 */
	@Override
	@Nullable
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo info = createRequestMappingInfo(method);
		if (info != null) {
			RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
			if (typeInfo != null) {
				info = typeInfo.combine(info);
			}
			String prefix = getPathPrefix(handlerType);
			if (prefix != null) {
				info = RequestMappingInfo.paths(prefix).options(this.config).build().combine(info);
			}
		}
		return info;
	}

	@Nullable
	String getPathPrefix(Class<?> handlerType) {
		for (Map.Entry<String, Predicate<Class<?>>> entry : this.pathPrefixes.entrySet()) {
			if (entry.getValue().test(handlerType)) {
				String prefix = entry.getKey();
				if (this.embeddedValueResolver != null) {
					prefix = this.embeddedValueResolver.resolveStringValue(prefix);
				}
				return prefix;
			}
		}
		return null;
	}

	/**
	 * Delegates to {@link #createRequestMappingInfo(RequestMapping, RequestCondition)},
	 * supplying the appropriate custom {@link RequestCondition} depending on whether
	 * the supplied {@code annotatedElement} is a class or method.
	 * @see #getCustomTypeCondition(Class)
	 * @see #getCustomMethodCondition(Method)
	 */
	/**
	 * 委托给{@link  #createRequestMappingInfo（RequestMapping，RequestCondition）}，根据提供的{@code  annotatedElement}是类还是方法，提供适当的自定义{@link  RequestCondition}。 
	 *  
	 * @see  #getCustomTypeCondition（Class）
	 * @see  #getCustomMethodCondition（Method）
	 */
	@Nullable
	private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
		RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
		RequestCondition<?> condition = (element instanceof Class ?
				getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
		return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
	}

	/**
	 * Provide a custom type-level request condition.
	 * The custom {@link RequestCondition} can be of any type so long as the
	 * same condition type is returned from all calls to this method in order
	 * to ensure custom request conditions can be combined and compared.
	 * <p>Consider extending {@link AbstractRequestCondition} for custom
	 * condition types and using {@link CompositeRequestCondition} to provide
	 * multiple custom conditions.
	 * @param handlerType the handler type for which to create the condition
	 * @return the condition, or {@code null}
	 */
	/**
	 * 提供自定义类型级别的请求条件。 
	 * 自定义{@link  RequestCondition}可以是任何类型，只要从对该方法的所有调用返回相同的条件类型即可，以确保可以组合和比较自定义请求条件。 
	 *  <p>请考虑将{@link  AbstractRequestCondition}扩展为自定义条件类型，并使用{@link  CompositeRequestCondition}提供多个自定义条件。 
	 *  
	 * @param  handlerType输入要为其创建条件的处理程序类型
	 * @return 条件，或{@code  null}
	 */
	@Nullable
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		return null;
	}

	/**
	 * Provide a custom method-level request condition.
	 * The custom {@link RequestCondition} can be of any type so long as the
	 * same condition type is returned from all calls to this method in order
	 * to ensure custom request conditions can be combined and compared.
	 * <p>Consider extending {@link AbstractRequestCondition} for custom
	 * condition types and using {@link CompositeRequestCondition} to provide
	 * multiple custom conditions.
	 * @param method the handler method for which to create the condition
	 * @return the condition, or {@code null}
	 */
	/**
	 * 提供自定义方法级别的请求条件。 
	 * 自定义{@link  RequestCondition}可以是任何类型，只要从对该方法的所有调用返回相同的条件类型即可，以确保可以组合和比较自定义请求条件。 
	 *  <p>请考虑将{@link  AbstractRequestCondition}扩展为自定义条件类型，并使用{@link  CompositeRequestCondition}提供多个自定义条件。 
	 *  
	 * @param 方法为其创建条件的处理程序方法
	 * @return 条件，或{@code  null}
	 */
	@Nullable
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		return null;
	}

	/**
	 * Create a {@link RequestMappingInfo} from the supplied
	 * {@link RequestMapping @RequestMapping} annotation, which is either
	 * a directly declared annotation, a meta-annotation, or the synthesized
	 * result of merging annotation attributes within an annotation hierarchy.
	 */
	/**
	 * 从提供的{@link  RequestMapping @RequestMapping}注释创建一个{@link  RequestMappingInfo}，该注释可以是直接声明的注释，元注释，也可以是在注释层次结构中合并注释属性的综合结果。 
	 * 
	 */
	protected RequestMappingInfo createRequestMappingInfo(
			RequestMapping requestMapping, @Nullable RequestCondition<?> customCondition) {

		RequestMappingInfo.Builder builder = RequestMappingInfo
				.paths(resolveEmbeddedValuesInPatterns(requestMapping.path()))
				.methods(requestMapping.method())
				.params(requestMapping.params())
				.headers(requestMapping.headers())
				.consumes(requestMapping.consumes())
				.produces(requestMapping.produces())
				.mappingName(requestMapping.name());
		if (customCondition != null) {
			builder.customCondition(customCondition);
		}
		return builder.options(this.config).build();
	}

	/**
	 * Resolve placeholder values in the given array of patterns.
	 * @return a new array with updated patterns
	 */
	/**
	 * 在给定的模式数组中解析占位符值。 
	 *  
	 * @return 具有更新模式的新数组
	 */
	protected String[] resolveEmbeddedValuesInPatterns(String[] patterns) {
		if (this.embeddedValueResolver == null) {
			return patterns;
		}
		else {
			String[] resolvedPatterns = new String[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				resolvedPatterns[i] = this.embeddedValueResolver.resolveStringValue(patterns[i]);
			}
			return resolvedPatterns;
		}
	}

	@Override
	public void registerMapping(RequestMappingInfo mapping, Object handler, Method method) {
		super.registerMapping(mapping, handler, method);
		updateConsumesCondition(mapping, method);
	}

	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		super.registerHandlerMethod(handler, method, mapping);
		updateConsumesCondition(mapping, method);
	}

	private void updateConsumesCondition(RequestMappingInfo info, Method method) {
		ConsumesRequestCondition condition = info.getConsumesCondition();
		if (!condition.isEmpty()) {
			for (Parameter parameter : method.getParameters()) {
				MergedAnnotation<RequestBody> annot = MergedAnnotations.from(parameter).get(RequestBody.class);
				if (annot.isPresent()) {
					condition.setBodyRequired(annot.getBoolean("required"));
					break;
				}
			}
		}
	}

	@Override
	public RequestMatchResult match(HttpServletRequest request, String pattern) {
		RequestMappingInfo info = RequestMappingInfo.paths(pattern).options(this.config).build();
		RequestMappingInfo matchingInfo = info.getMatchingCondition(request);
		if (matchingInfo == null) {
			return null;
		}
		Set<String> patterns = matchingInfo.getPatternsCondition().getPatterns();
		String lookupPath = getUrlPathHelper().getLookupPathForRequest(request, LOOKUP_PATH);
		return new RequestMatchResult(patterns.iterator().next(), lookupPath, getPathMatcher());
	}

	@Override
	protected CorsConfiguration initCorsConfiguration(Object handler, Method method, RequestMappingInfo mappingInfo) {
		HandlerMethod handlerMethod = createHandlerMethod(handler, method);
		Class<?> beanType = handlerMethod.getBeanType();
		CrossOrigin typeAnnotation = AnnotatedElementUtils.findMergedAnnotation(beanType, CrossOrigin.class);
		CrossOrigin methodAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, CrossOrigin.class);

		if (typeAnnotation == null && methodAnnotation == null) {
			return null;
		}

		CorsConfiguration config = new CorsConfiguration();
		updateCorsConfig(config, typeAnnotation);
		updateCorsConfig(config, methodAnnotation);

		if (CollectionUtils.isEmpty(config.getAllowedMethods())) {
			for (RequestMethod allowedMethod : mappingInfo.getMethodsCondition().getMethods()) {
				config.addAllowedMethod(allowedMethod.name());
			}
		}
		return config.applyPermitDefaultValues();
	}

	private void updateCorsConfig(CorsConfiguration config, @Nullable CrossOrigin annotation) {
		if (annotation == null) {
			return;
		}
		for (String origin : annotation.origins()) {
			config.addAllowedOrigin(resolveCorsAnnotationValue(origin));
		}
		for (RequestMethod method : annotation.methods()) {
			config.addAllowedMethod(method.name());
		}
		for (String header : annotation.allowedHeaders()) {
			config.addAllowedHeader(resolveCorsAnnotationValue(header));
		}
		for (String header : annotation.exposedHeaders()) {
			config.addExposedHeader(resolveCorsAnnotationValue(header));
		}

		String allowCredentials = resolveCorsAnnotationValue(annotation.allowCredentials());
		if ("true".equalsIgnoreCase(allowCredentials)) {
			config.setAllowCredentials(true);
		}
		else if ("false".equalsIgnoreCase(allowCredentials)) {
			config.setAllowCredentials(false);
		}
		else if (!allowCredentials.isEmpty()) {
			throw new IllegalStateException("@CrossOrigin's allowCredentials value must be \"true\", \"false\", " +
					"or an empty string (\"\"): current value is [" + allowCredentials + "]");
		}

		if (annotation.maxAge() >= 0 && config.getMaxAge() == null) {
			config.setMaxAge(annotation.maxAge());
		}
	}

	private String resolveCorsAnnotationValue(String value) {
		if (this.embeddedValueResolver != null) {
			String resolved = this.embeddedValueResolver.resolveStringValue(value);
			return (resolved != null ? resolved : "");
		}
		else {
			return value;
		}
	}

}
