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

package org.springframework.context.annotation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.context.index.CandidateComponentsIndexLoader;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * A component provider that provides candidate components from a base package. Can
 * use {@link CandidateComponentsIndex the index} if it is available of scans the
 * classpath otherwise. Candidate components are identified by applying exclude and
 * include filters. {@link AnnotationTypeFilter}, {@link AssignableTypeFilter} include
 * filters on an annotation/superclass that are annotated with {@link Indexed} are
 * supported: if any other include filter is specified, the index is ignored and
 * classpath scanning is used instead.
 *
 * <p>This implementation is based on Spring's
 * {@link org.springframework.core.type.classreading.MetadataReader MetadataReader}
 * facility, backed by an ASM {@link org.springframework.asm.ClassReader ClassReader}.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Ramnivas Laddad
 * @author Chris Beams
 * @author Stephane Nicoll
 * @since 2.5
 * @see org.springframework.core.type.classreading.MetadataReaderFactory
 * @see org.springframework.core.type.AnnotationMetadata
 * @see ScannedGenericBeanDefinition
 * @see CandidateComponentsIndex
 */
/**
 * 组件提供程序，提供基本程序包中的候选组件。 
 * 如果可用，则可以使用{@link  CandidateComponentsIndex the index}，否则可以扫描类路径。 
 * 通过应用排除和包含过滤器可以识别候选组件。 
 * 支持在注释/超类上使用{@link  Indexed}进行注释的{@link  AnnotationTypeFilter}，{<@link> AssignableTypeFilter}包含过滤器：如果指定了其他包含过滤器，则索引将被忽略并且类路径而是使用扫描。 
 *  <p>此实现基于Spring的{@link  org.springframework.core.type.classreading.MetadataReader MetadataReader}工具，并由ASM {@link  org.springframework.asm.ClassReader ClassReader}支持。 
 *  @author  Mark Fisher @author  Juergen Hoeller @author  Ramnivas Laddad @author  Chris Beams @author  Stephane Nicoll @since 2.5 
 * @see  org.springframework.core.type.classreading.MetadataReaderFactory <@请参阅> org.springframework.core.type.AnnotationMetadata <@请参阅> ScannedGenericBeanDefinition <@请参阅> CandidateComponentsIndex
 */
public class ClassPathScanningCandidateComponentProvider implements EnvironmentCapable, ResourceLoaderAware {

	static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";


	protected final Log logger = LogFactory.getLog(getClass());

	private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

	private final List<TypeFilter> includeFilters = new LinkedList<>();

	private final List<TypeFilter> excludeFilters = new LinkedList<>();

	@Nullable
	private Environment environment;

	@Nullable
	private ConditionEvaluator conditionEvaluator;

	@Nullable
	private ResourcePatternResolver resourcePatternResolver;

	@Nullable
	private MetadataReaderFactory metadataReaderFactory;

	@Nullable
	private CandidateComponentsIndex componentsIndex;


	/**
	 * Protected constructor for flexible subclass initialization.
	 * @since 4.3.6
	 */
/**
 * .class"； 
 * 受保护的最终Log logger = LogFactory.getLog（getClass（））； 
 * 私有的字符串resourcePattern = DEFAULT_RESOURCE_PATTERN； 
 * 私有的最终List <TypeFilter> includeFilters =新的LinkedList <>（）； 
 * 私有的最终List <TypeFilter> excludeFilters =新的LinkedList < >（）; @Nullable私有环境环境; @Nullable私有ConditionEvaluator conditionEvaluator; @Nullable私有ResourcePatternResolver resourcePatternResolver; @Nullable私有MetadataReaderFactory metadataReaderFactory; @Nullable私有CandidateComponentsIndex componentsIndex;受保护的构造函数，用于灵活子类初始化。 
 * 自4.3.6起
 */
	protected ClassPathScanningCandidateComponentProvider() {
	}

	/**
	 * Create a ClassPathScanningCandidateComponentProvider with a {@link StandardEnvironment}.
	 * @param useDefaultFilters whether to register the default filters for the
	 * {@link Component @Component}, {@link Repository @Repository},
	 * {@link Service @Service}, and {@link Controller @Controller}
	 * stereotype annotations
	 * @see #registerDefaultFilters()
	 */
	/**
	 * 使用{@link  StandardEnvironment}创建一个ClassPathScanningCandidateComponentProvider。 
	 *  
	 * @param  useDefaultFilters是否为{@link 组件@Component}，{<@link>存储库@Repository}，{<@link>服务@Service}和{@link 控制器）注册默认过滤器@Controller}构造型注释
	 * @see  #registerDefaultFilters（）
	 */
	public ClassPathScanningCandidateComponentProvider(boolean useDefaultFilters) {
		this(useDefaultFilters, new StandardEnvironment());
	}

	/**
	 * Create a ClassPathScanningCandidateComponentProvider with the given {@link Environment}.
	 * @param useDefaultFilters whether to register the default filters for the
	 * {@link Component @Component}, {@link Repository @Repository},
	 * {@link Service @Service}, and {@link Controller @Controller}
	 * stereotype annotations
	 * @param environment the Environment to use
	 * @see #registerDefaultFilters()
	 */
	/**
	 * 使用给定的{@link  Environment}创建一个ClassPathScanningCandidateComponentProvider。 
	 *  
	 * @param  useDefaultFilters是否为{@link 组件@Component}，{<@link>存储库@Repository}，{<@link>服务@Service}和{@link 控制器）注册默认过滤器@Controller}构造型注释
	 * @param 环境要使用的环境
	 * @see  #registerDefaultFilters（）
	 */
	public ClassPathScanningCandidateComponentProvider(boolean useDefaultFilters, Environment environment) {
		if (useDefaultFilters) {
			registerDefaultFilters();
		}
		setEnvironment(environment);
		setResourceLoader(null);
	}


	/**
	 * Set the resource pattern to use when scanning the classpath.
	 * This value will be appended to each base package name.
	 * @see #findCandidateComponents(String)
	 * @see #DEFAULT_RESOURCE_PATTERN
	 */
	/**
	 * 设置在扫描类路径时要使用的资源模式。 
	 * 此值将附加到每个基本程序包名称。 
	 *  
	 * @see  #findCandidateComponents（String）
	 * @see  #DEFAULT_RESOURCE_PATTERN
	 */
	public void setResourcePattern(String resourcePattern) {
		Assert.notNull(resourcePattern, "'resourcePattern' must not be null");
		this.resourcePattern = resourcePattern;
	}

	/**
	 * Add an include type filter to the <i>end</i> of the inclusion list.
	 */
	/**
	 * 在包含列表的<i> end </ i>中添加一个包含类型过滤器。 
	 * 
	 */
	public void addIncludeFilter(TypeFilter includeFilter) {
		this.includeFilters.add(includeFilter);
	}

	/**
	 * Add an exclude type filter to the <i>front</i> of the exclusion list.
	 */
	/**
	 * 将排除类型过滤器添加到排除列表的<i> front </ i>。 
	 * 
	 */
	public void addExcludeFilter(TypeFilter excludeFilter) {
		this.excludeFilters.add(0, excludeFilter);
	}

	/**
	 * Reset the configured type filters.
	 * @param useDefaultFilters whether to re-register the default filters for
	 * the {@link Component @Component}, {@link Repository @Repository},
	 * {@link Service @Service}, and {@link Controller @Controller}
	 * stereotype annotations
	 * @see #registerDefaultFilters()
	 */
	/**
	 * 重置已配置的类型过滤器。 
	 *  
	 * @param  useDefaultFilters是否重新注册{@link 组件@Component}，{<@link>存储库@Repository}，{<@link>服务@Service}和{<@link >控制器@Controller}构造型注释
	 * @see  #registerDefaultFilters（）
	 */
	public void resetFilters(boolean useDefaultFilters) {
		this.includeFilters.clear();
		this.excludeFilters.clear();
		if (useDefaultFilters) {
			registerDefaultFilters();
		}
	}

	/**
	 * Register the default filter for {@link Component @Component}.
	 * <p>This will implicitly register all annotations that have the
	 * {@link Component @Component} meta-annotation including the
	 * {@link Repository @Repository}, {@link Service @Service}, and
	 * {@link Controller @Controller} stereotype annotations.
	 * <p>Also supports Java EE 6's {@link javax.annotation.ManagedBean} and
	 * JSR-330's {@link javax.inject.Named} annotations, if available.
	 *
	 */
	/**
	 * 为{@link  Component @Component}注册默认过滤器。 
	 *  <p>这将隐式注册具有{@link  Component @Component}元注释的所有注释，包括{@link  Repository @Repository}，{<@link> Service @Service}和{<@link> Controller @Controller}构造型注释。 
	 *  <p>如果可用，还支持Java EE 6的{@link  javax.annotation.ManagedBean}和JSR-330的{@link  javax.inject.Named}注释。 
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void registerDefaultFilters() {
		this.includeFilters.add(new AnnotationTypeFilter(Component.class));
		ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.annotation.ManagedBean", cl)), false));
			logger.trace("JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning");
		}
		catch (ClassNotFoundException ex) {
			// JSR-250 1.1 API (as included in Java EE 6) not available - simply skip.
		}
		try {
			this.includeFilters.add(new AnnotationTypeFilter(
					((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Named", cl)), false));
			logger.trace("JSR-330 'javax.inject.Named' annotation found and supported for component scanning");
		}
		catch (ClassNotFoundException ex) {
			// JSR-330 API not available - simply skip.
		}
	}

	/**
	 * Set the Environment to use when resolving placeholders and evaluating
	 * {@link Conditional @Conditional}-annotated component classes.
	 * <p>The default is a {@link StandardEnvironment}.
	 * @param environment the Environment to use
	 */
	/**
	 * 设置用于解析占位符并评估{@link 有条件@Conditional}注释的组件类时使用的环境。 
	 *  <p>默认值为{@link  StandardEnvironment}。 
	 *  
	 * @param 环境使用的环境
	 */
	public void setEnvironment(Environment environment) {
		Assert.notNull(environment, "Environment must not be null");
		this.environment = environment;
		this.conditionEvaluator = null;
	}

	@Override
	public final Environment getEnvironment() {
		if (this.environment == null) {
			this.environment = new StandardEnvironment();
		}
		return this.environment;
	}

	/**
	 * Return the {@link BeanDefinitionRegistry} used by this scanner, if any.
	 */
	/**
	 * 返回此扫描器使用的{@link  BeanDefinitionRegistry}（如果有）。 
	 * 
	 */
	@Nullable
	protected BeanDefinitionRegistry getRegistry() {
		return null;
	}

	/**
	 * Set the {@link ResourceLoader} to use for resource locations.
	 * This will typically be a {@link ResourcePatternResolver} implementation.
	 * <p>Default is a {@code PathMatchingResourcePatternResolver}, also capable of
	 * resource pattern resolving through the {@code ResourcePatternResolver} interface.
	 * @see org.springframework.core.io.support.ResourcePatternResolver
	 * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver
	 */
	/**
	 * 设置{@link  ResourceLoader}以用于资源位置。 
	 * 这通常是{@link  ResourcePatternResolver}的实现。 
	 *  <p>默认值为{@code  PathMatchingResourcePatternResolver}，也可以通过{@code  ResourcePatternResolver}接口解析资源模式。 
	 *  
	 * @see  org.springframework.core.io.support.ResourcePatternResolver 
	 * @see  org.springframework.core.io.support.PathMatchingResourcePatternResolver
	 */
	@Override
	public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
		this.componentsIndex = CandidateComponentsIndexLoader.loadIndex(this.resourcePatternResolver.getClassLoader());
	}

	/**
	 * Return the ResourceLoader that this component provider uses.
	 */
	/**
	 * 返回此组件提供程序使用的ResourceLoader。 
	 * 
	 */
	public final ResourceLoader getResourceLoader() {
		return getResourcePatternResolver();
	}

	private ResourcePatternResolver getResourcePatternResolver() {
		if (this.resourcePatternResolver == null) {
			this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
		}
		return this.resourcePatternResolver;
	}

	/**
	 * Set the {@link MetadataReaderFactory} to use.
	 * <p>Default is a {@link CachingMetadataReaderFactory} for the specified
	 * {@linkplain #setResourceLoader resource loader}.
	 * <p>Call this setter method <i>after</i> {@link #setResourceLoader} in order
	 * for the given MetadataReaderFactory to override the default factory.
	 */
	/**
	 * 设置{@link  MetadataReaderFactory}以使用。 
	 *  <p>对于指定的{@link  plain #setResourceLoader资源加载器}，默认值为{@link  CachingMetadataReaderFactory}。 
	 *  <p>在</ i> {@link  #setResourceLoader}之后，调用此设置方法<i>，以使给定的MetadataReaderFactory覆盖默认工厂。 
	 * 
	 */
	public void setMetadataReaderFactory(MetadataReaderFactory metadataReaderFactory) {
		this.metadataReaderFactory = metadataReaderFactory;
	}

	/**
	 * Return the MetadataReaderFactory used by this component provider.
	 */
	/**
	 * 返回此组件提供者使用的MetadataReaderFactory。 
	 * 
	 */
	public final MetadataReaderFactory getMetadataReaderFactory() {
		if (this.metadataReaderFactory == null) {
			this.metadataReaderFactory = new CachingMetadataReaderFactory();
		}
		return this.metadataReaderFactory;
	}


	/**
	 * Scan the class path for candidate components.
	 * @param basePackage the package to check for annotated classes
	 * @return a corresponding Set of autodetected bean definitions
	 */
	/**
	 * 扫描类路径以查找候选组件。 
	 *  
	 * @param  basePackage软件包以检查带注释的类
	 * @return 对应的一组自动检测到的bean定义
	 */
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		if (this.componentsIndex != null && indexSupportsIncludeFilters()) {
			return addCandidateComponentsFromIndex(this.componentsIndex, basePackage);
		}
		else {
			return scanCandidateComponents(basePackage);
		}
	}

	/**
	 * Determine if the index can be used by this instance.
	 * @return {@code true} if the index is available and the configuration of this
	 * instance is supported by it, {@code false} otherwise
	 * @since 5.0
	 */
	/**
	 * 确定该实例是否可以使用索引。 
	 *  
	 * @return  {@code  true}（如果索引可用并且支持此实例的配置），{<@code> false}否则，自5.0起
	 */
	private boolean indexSupportsIncludeFilters() {
		for (TypeFilter includeFilter : this.includeFilters) {
			if (!indexSupportsIncludeFilter(includeFilter)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine if the specified include {@link TypeFilter} is supported by the index.
	 * @param filter the filter to check
	 * @return whether the index supports this include filter
	 * @since 5.0
	 * @see #extractStereotype(TypeFilter)
	 */
	/**
	 * 确定索引是否支持指定的包含{@link  TypeFilter}。 
	 *  
	 * @param 对过滤器进行过滤以检查
	 * @return 索引是否支持此包含过滤器@从5.0起
	 * @see  #extractStereotype（TypeFilter）
	 */
	private boolean indexSupportsIncludeFilter(TypeFilter filter) {
		if (filter instanceof AnnotationTypeFilter) {
			Class<? extends Annotation> annotation = ((AnnotationTypeFilter) filter).getAnnotationType();
			return (AnnotationUtils.isAnnotationDeclaredLocally(Indexed.class, annotation) ||
					annotation.getName().startsWith("javax."));
		}
		if (filter instanceof AssignableTypeFilter) {
			Class<?> target = ((AssignableTypeFilter) filter).getTargetType();
			return AnnotationUtils.isAnnotationDeclaredLocally(Indexed.class, target);
		}
		return false;
	}

	/**
	 * Extract the stereotype to use for the specified compatible filter.
	 * @param filter the filter to handle
	 * @return the stereotype in the index matching this filter
	 * @since 5.0
	 * @see #indexSupportsIncludeFilter(TypeFilter)
	 */
	/**
	 * 提取构造型以用于指定的兼容过滤器。 
	 *  
	 * @param 过滤该过滤器以处理
	 * @return 与该过滤器匹配的索引中的构造型@从5.0起
	 * @see  #indexSupportsIncludeFilter（TypeFilter）
	 */
	@Nullable
	private String extractStereotype(TypeFilter filter) {
		if (filter instanceof AnnotationTypeFilter) {
			return ((AnnotationTypeFilter) filter).getAnnotationType().getName();
		}
		if (filter instanceof AssignableTypeFilter) {
			return ((AssignableTypeFilter) filter).getTargetType().getName();
		}
		return null;
	}

	private Set<BeanDefinition> addCandidateComponentsFromIndex(CandidateComponentsIndex index, String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		try {
			Set<String> types = new HashSet<>();
			for (TypeFilter filter : this.includeFilters) {
				String stereotype = extractStereotype(filter);
				if (stereotype == null) {
					throw new IllegalArgumentException("Failed to extract stereotype from " + filter);
				}
				types.addAll(index.getCandidateTypes(basePackage, stereotype));
			}
			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			for (String type : types) {
				MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(type);
				if (isCandidateComponent(metadataReader)) {
					AnnotatedGenericBeanDefinition sbd = new AnnotatedGenericBeanDefinition(
							metadataReader.getAnnotationMetadata());
					if (isCandidateComponent(sbd)) {
						if (debugEnabled) {
							logger.debug("Using candidate component class from index: " + type);
						}
						candidates.add(sbd);
					}
					else {
						if (debugEnabled) {
							logger.debug("Ignored because not a concrete top-level class: " + type);
						}
					}
				}
				else {
					if (traceEnabled) {
						logger.trace("Ignored because matching an exclude filter: " + type);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}

	private Set<BeanDefinition> scanCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					resolveBasePackage(basePackage) + '/' + this.resourcePattern;
			// packageSearchPath 是 扫描的包的路径
			// 重要以下代码是真正的扫描代码
			// 重要以下代码是真正的扫描代码
			// 重要以下代码是真正的扫描代码
			// 重要以下代码是真正的扫描代码
			// 重要以下代码是真正的扫描代码
			Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
			boolean traceEnabled = logger.isTraceEnabled();
			boolean debugEnabled = logger.isDebugEnabled();
			for (Resource resource : resources) {
				if (traceEnabled) {
					logger.trace("Scanning " + resource);
				}
				if (resource.isReadable()) {
					try {
						// 获得metadata
						MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
						if (isCandidateComponent(metadataReader)) {
							// 包装成beanDefinition
							ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
							sbd.setResource(resource);
							sbd.setSource(resource);
							if (isCandidateComponent(sbd)) {
								if (debugEnabled) {
									logger.debug("Identified candidate component class: " + resource);
								}
								// 将当前beanDefinition添加到候选集合中
								candidates.add(sbd);
							}
							else {
								if (debugEnabled) {
									logger.debug("Ignored because not a concrete top-level class: " + resource);
								}
							}
						}
						else {
							if (traceEnabled) {
								logger.trace("Ignored because not matching any filter: " + resource);
							}
						}
					}
					catch (Throwable ex) {
						throw new BeanDefinitionStoreException(
								"Failed to read candidate component class: " + resource, ex);
					}
				}
				else {
					if (traceEnabled) {
						logger.trace("Ignored because not readable: " + resource);
					}
				}
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}


	/**
	 * Resolve the specified base package into a pattern specification for
	 * the package search path.
	 * <p>The default implementation resolves placeholders against system properties,
	 * and converts a "."-based package path to a "/"-based resource path.
	 * @param basePackage the base package as specified by the user
	 * @return the pattern specification to be used for package searching
	 */
	/**
	 * 将指定的基本包解析为包搜索路径的模式规范。 
	 *  <p>默认实现根据系统属性解析占位符，并将基于"。 
	 * "的程序包路径转换为基于"/"的资源路径。 
	 *  
	 * @param  basePackage由用户指定的基本包
	 * @return 用于包搜索的模式规范
	 */
	protected String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(getEnvironment().resolveRequiredPlaceholders(basePackage));
	}

	/**
	 * Determine whether the given class does not match any exclude filter
	 * and does match at least one include filter.
	 * @param metadataReader the ASM ClassReader for the class
	 * @return whether the class qualifies as a candidate component
	 */
	/**
	 * 确定给定的类是否不匹配任何排除过滤器并且是否匹配至少一个包含过滤器。 
	 *  
	 * @param  metadataReader类
	 * @return 的ASM ClassReader是否该类有资格作为候选组件
	 */
	protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
		for (TypeFilter tf : this.excludeFilters) {
			if (tf.match(metadataReader, getMetadataReaderFactory())) {
				return false;
			}
		}
		for (TypeFilter tf : this.includeFilters) {
			if (tf.match(metadataReader, getMetadataReaderFactory())) {
				return isConditionMatch(metadataReader);
			}
		}
		return false;
	}

	/**
	 * Determine whether the given class is a candidate component based on any
	 * {@code @Conditional} annotations.
	 * @param metadataReader the ASM ClassReader for the class
	 * @return whether the class qualifies as a candidate component
	 */
	/**
	 * 根据任何{@code  @Conditional}注解确定给定的类是否为候选组件。 
	 *  
	 * @param  metadataReader类
	 * @return 的ASM ClassReader是否该类有资格作为候选组件
	 */
	private boolean isConditionMatch(MetadataReader metadataReader) {
		if (this.conditionEvaluator == null) {
			this.conditionEvaluator =
					new ConditionEvaluator(getRegistry(), this.environment, this.resourcePatternResolver);
		}
		return !this.conditionEvaluator.shouldSkip(metadataReader.getAnnotationMetadata());
	}

	/**
	 * Determine whether the given bean definition qualifies as candidate.
	 * <p>The default implementation checks whether the class is not an interface
	 * and not dependent on an enclosing class.
	 * <p>Can be overridden in subclasses.
	 * @param beanDefinition the bean definition to check
	 * @return whether the bean definition qualifies as a candidate component
	 */
	/**
	 * 确定给定的b​​ean定义是否符合候选条件。 
	 *  <p>默认实现检查该类是否不是接口，并且不依赖于封闭的类。 
	 *  <p>可以在子类中覆盖。 
	 *  
	 * @param  beanDefinition bean定义以检查
	 * @return  bean定义是否符合候选组件的条件
	 */
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		AnnotationMetadata metadata = beanDefinition.getMetadata();
		return (metadata.isIndependent() && (metadata.isConcrete() ||
				(metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName()))));
	}


	/**
	 * Clear the local metadata cache, if any, removing all cached class metadata.
	 */
	/**
	 * 清除本地元数据缓存（如果有），以删除所有缓存的类元数据。 
	 * 
	 */
	public void clearCache() {
		if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
			// Clear cache in externally provided MetadataReaderFactory; this is a no-op
			// for a shared cache since it'll be cleared by the ApplicationContext.
			((CachingMetadataReaderFactory) this.metadataReaderFactory).clearCache();
		}
	}

}
