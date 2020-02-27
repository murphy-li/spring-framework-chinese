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

package org.springframework.web.method;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Encapsulates information about an {@link ControllerAdvice @ControllerAdvice}
 * Spring-managed bean without necessarily requiring it to be instantiated.
 *
 * <p>The {@link #findAnnotatedBeans(ApplicationContext)} method can be used to
 * discover such beans. However, a {@code ControllerAdviceBean} may be created
 * from any object, including ones without an {@code @ControllerAdvice} annotation.
 *
 * @author Rossen Stoyanchev
 * @author Brian Clozel
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.2
 */
/**
 * 封装有关{@link  ControllerAdvice @ControllerAdvice} Spring管理的bean的信息，而不必要求对其进行实例化。 
 *  <p> {<@link> #findAnnotatedBeans（ApplicationContext）}方法可用于发现此类bean。 
 * 但是，可以从任何对象创建一个{@code  ControllerAdviceBean}，包括没有{@code  @ControllerAdvice}注解的对象。 
 *  @author  Rossen Stoyanchev @author  Brian Clozel @author  Juergen Hoeller @author  Sam Brannen @自3.2起
 */
public class ControllerAdviceBean implements Ordered {

	/**
	 * Reference to the actual bean instance or a {@code String} representing
	 * the bean name.
	 */
	/**
	 * 引用实际的bean实例或表示bean名称的{@code  String}。 
	 * 
	 */
	private final Object beanOrName;

	private final boolean isSingleton;

	/**
	 * Reference to the resolved bean instance, potentially lazily retrieved
	 * via the {@code BeanFactory}.
	 */
	/**
	 * 对解析的bean实例的引用，可能通过{@code  BeanFactory}延迟检索。 
	 * 
	 */
	@Nullable
	private Object resolvedBean;

	@Nullable
	private final Class<?> beanType;

	private final HandlerTypePredicate beanTypePredicate;

	@Nullable
	private final BeanFactory beanFactory;

	@Nullable
	private Integer order;


	/**
	 * Create a {@code ControllerAdviceBean} using the given bean instance.
	 * @param bean the bean instance
	 */
	/**
	 * 使用给定的bean实例创建一个{@code  ControllerAdviceBean}。 
	 *  
	 * @param  bean bean实例
	 */
	public ControllerAdviceBean(Object bean) {
		Assert.notNull(bean, "Bean must not be null");
		this.beanOrName = bean;
		this.isSingleton = true;
		this.resolvedBean = bean;
		this.beanType = ClassUtils.getUserClass(bean.getClass());
		this.beanTypePredicate = createBeanTypePredicate(this.beanType);
		this.beanFactory = null;
	}

	/**
	 * Create a {@code ControllerAdviceBean} using the given bean name and
	 * {@code BeanFactory}.
	 * @param beanName the name of the bean
	 * @param beanFactory a {@code BeanFactory} to retrieve the bean type initially
	 * and later to resolve the actual bean
	 */
	/**
	 * 使用给定的bean名称和{@code  BeanFactory}创建一个{@code  ControllerAdviceBean}。 
	 *  
	 * @param  beanName bean的名称
	 * @param  beanFactory a {@code  BeanFactory}最初用于检索bean类型，稍后再解析实际的bean
	 */
	public ControllerAdviceBean(String beanName, BeanFactory beanFactory) {
		this(beanName, beanFactory, null);
	}

	/**
	 * Create a {@code ControllerAdviceBean} using the given bean name,
	 * {@code BeanFactory}, and {@link ControllerAdvice @ControllerAdvice}
	 * annotation.
	 * @param beanName the name of the bean
	 * @param beanFactory a {@code BeanFactory} to retrieve the bean type initially
	 * and later to resolve the actual bean
	 * @param controllerAdvice the {@code @ControllerAdvice} annotation for the
	 * bean, or {@code null} if not yet retrieved
	 * @since 5.2
	 */
	/**
	 * 使用给定的bean名称，{<@code> BeanFactory}和{@link  ControllerAdvice @ControllerAdvice}注解创建一个{@code  ControllerAdviceBean}。 
	 *  
	 * @param  beanName Bean的名称
	 * @param  beanFactory a {@code  BeanFactory}最初检索Bean类型，稍后再解析实际的Bean 
	 * @param  controller建议{{@@code> @ControllerAdvice} Bean的注释，如果尚未检索，则为{@code  null} @since 5.2
	 */
	public ControllerAdviceBean(String beanName, BeanFactory beanFactory, @Nullable ControllerAdvice controllerAdvice) {
		Assert.hasText(beanName, "Bean name must contain text");
		Assert.notNull(beanFactory, "BeanFactory must not be null");
		Assert.isTrue(beanFactory.containsBean(beanName), () -> "BeanFactory [" + beanFactory +
				"] does not contain specified controller advice bean '" + beanName + "'");

		this.beanOrName = beanName;
		this.isSingleton = beanFactory.isSingleton(beanName);
		this.beanType = getBeanType(beanName, beanFactory);
		this.beanTypePredicate = (controllerAdvice != null ? createBeanTypePredicate(controllerAdvice) :
				createBeanTypePredicate(this.beanType));
		this.beanFactory = beanFactory;
	}


	/**
	 * Get the order value for the contained bean.
	 * <p>As of Spring Framework 5.2, the order value is lazily retrieved using
	 * the following algorithm and cached. Note, however, that a
	 * {@link ControllerAdvice @ControllerAdvice} bean that is configured as a
	 * scoped bean &mdash; for example, as a request-scoped or session-scoped
	 * bean &mdash; will not be eagerly resolved. Consequently, {@link Ordered} is
	 * not honored for scoped {@code @ControllerAdvice} beans.
	 * <ul>
	 * <li>If the {@linkplain #resolveBean resolved bean} implements {@link Ordered},
	 * use the value returned by {@link Ordered#getOrder()}.</li>
	 * <li>If the {@linkplain #getBeanType() bean type} is known, use the value returned
	 * by {@link OrderUtils#getOrder(Class, int)} with {@link Ordered#LOWEST_PRECEDENCE}
	 * used as the default order value.</li>
	 * <li>Otherwise use {@link Ordered#LOWEST_PRECEDENCE} as the default, fallback
	 * order value.</li>
	 * </ul>
	 * @see #resolveBean()
	 */
	/**
	 * 获取所包含的bean的订单值。 
	 *  <p>从Spring Framework 5.2开始，使用以下算法延迟检索订单值并进行缓存。 
	 * 但是请注意，不会急切地解决一个{@link  ControllerAdvice @ControllerAdvice} bean，它被配置为作用域的bean（例如，请求作用域或会话作用域的bean）。 
	 * 因此，对于有作用域的{@code  @ControllerAdvice} Bean，不推荐使用{@link  Ordered}。 
	 *  <ul> <li>如果{@link  plain #resolveBean解析的bean}实现了{@link  Ordered}，请使用{@link  Ordered＃getOrder（）}返回的值。 
	 * </ li> < li>如果已知{@link  plain #getBeanType（）bean类型}，请使用{@link  OrderUtils＃getOrder（Class，int）}返回的值，并使用{@link  Ordered＃LOWEST_PRECEDENCE}作为默认订单值。 
	 * </ li> <li>否则，请使用{@link  Ordered＃LOWEST_PRECEDENCE}作为默认的备用订单值。 
	 * </ li> </ ul> 
	 * @see  #resolveBean（）
	 */
	@Override
	public int getOrder() {
		if (this.order == null) {
			Object resolvedBean = null;
			if (this.beanFactory != null && this.beanOrName instanceof String) {
				String beanName = (String) this.beanOrName;
				String targetBeanName = ScopedProxyUtils.getTargetBeanName(beanName);
				boolean isScopedProxy = this.beanFactory.containsBean(targetBeanName);
				// Avoid eager @ControllerAdvice bean resolution for scoped proxies,
				// since attempting to do so during context initialization would result
				// in an exception due to the current absence of the scope. For example,
				// an HTTP request or session scope is not active during initialization.
				if (!isScopedProxy && !ScopedProxyUtils.isScopedTarget(beanName)) {
					resolvedBean = resolveBean();
				}
			}
			else {
				resolvedBean = resolveBean();
			}

			if (resolvedBean instanceof Ordered) {
				this.order = ((Ordered) resolvedBean).getOrder();
			}
			else if (this.beanType != null) {
				this.order = OrderUtils.getOrder(this.beanType, Ordered.LOWEST_PRECEDENCE);
			}
			else {
				this.order = Ordered.LOWEST_PRECEDENCE;
			}
		}
		return this.order;
	}

	/**
	 * Return the type of the contained bean.
	 * <p>If the bean type is a CGLIB-generated class, the original user-defined
	 * class is returned.
	 */
	/**
	 * 返回所包含的bean的类型。 
	 *  <p>如果bean类型是CGLIB生成的类，则返回原始的用户定义类。 
	 * 
	 */
	@Nullable
	public Class<?> getBeanType() {
		return this.beanType;
	}

	/**
	 * Get the bean instance for this {@code ControllerAdviceBean}, if necessary
	 * resolving the bean name through the {@link BeanFactory}.
	 * <p>As of Spring Framework 5.2, once the bean instance has been resolved it
	 * will be cached if it is a singleton, thereby avoiding repeated lookups in
	 * the {@code BeanFactory}.
	 */
	/**
	 * 获取此{@code  ControllerAdviceBean}的bean实例，必要时通过{@link  BeanFactory}解析bean名称。 
	 *  <p>从Spring Framework 5.2开始，一旦解决了bean实例（如果是单例），则将对其进行缓存，从而避免在{@code  BeanFactory}中进行重复查找。 
	 * 
	 */
	public Object resolveBean() {
		if (this.resolvedBean == null) {
			// this.beanOrName must be a String representing the bean name if
			// this.resolvedBean is null.
			Object resolvedBean = obtainBeanFactory().getBean((String) this.beanOrName);
			// Don't cache non-singletons (e.g., prototypes).
			if (!this.isSingleton) {
				return resolvedBean;
			}
			this.resolvedBean = resolvedBean;
		}
		return this.resolvedBean;
	}

	private BeanFactory obtainBeanFactory() {
		Assert.state(this.beanFactory != null, "No BeanFactory set");
		return this.beanFactory;
	}

	/**
	 * Check whether the given bean type should be advised by this
	 * {@code ControllerAdviceBean}.
	 * @param beanType the type of the bean to check
	 * @since 4.0
	 * @see ControllerAdvice
	 */
	/**
	 * 检查此{@code  ControllerAdviceBean}是否应建议给定的bean类型。 
	 *  
	 * @param  bean键入要从@4.0开始检查的bean的类型
	 * @see  ControllerAdvice
	 */
	public boolean isApplicableToBeanType(@Nullable Class<?> beanType) {
		return this.beanTypePredicate.test(beanType);
	}


	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ControllerAdviceBean)) {
			return false;
		}
		ControllerAdviceBean otherAdvice = (ControllerAdviceBean) other;
		return (this.beanOrName.equals(otherAdvice.beanOrName) && this.beanFactory == otherAdvice.beanFactory);
	}

	@Override
	public int hashCode() {
		return this.beanOrName.hashCode();
	}

	@Override
	public String toString() {
		return this.beanOrName.toString();
	}


	/**
	 * Find beans annotated with {@link ControllerAdvice @ControllerAdvice} in the
	 * given {@link ApplicationContext} and wrap them as {@code ControllerAdviceBean}
	 * instances.
	 * <p>As of Spring Framework 5.2, the {@code ControllerAdviceBean} instances
	 * in the returned list are sorted using {@link OrderComparator#sort(List)}.
	 * @see #getOrder()
	 * @see OrderComparator
	 * @see Ordered
	 */
	/**
	 * 在给定的{@link  ApplicationContext}中找到用{@link  ControllerAdvice @ControllerAdvice}注释的bean，并将它们包装为{@code  ControllerAdviceBean}实例。 
	 *  <p>从Spring Framework 5.2开始，使用{@link  OrderComparator＃sort（List）}对返回列表中的{@code  ControllerAdviceBean}实例进行排序。 
	 *  
	 * @see  #getOrder（）
	 * @see  OrderComparator 
	 * @see 已订购
	 */
	public static List<ControllerAdviceBean> findAnnotatedBeans(ApplicationContext context) {
		List<ControllerAdviceBean> adviceBeans = new ArrayList<>();
		for (String name : BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context, Object.class)) {
			if (!ScopedProxyUtils.isScopedTarget(name)) {
				ControllerAdvice controllerAdvice = context.findAnnotationOnBean(name, ControllerAdvice.class);
				if (controllerAdvice != null) {
					// Use the @ControllerAdvice annotation found by findAnnotationOnBean()
					// in order to avoid a subsequent lookup of the same annotation.
					adviceBeans.add(new ControllerAdviceBean(name, context, controllerAdvice));
				}
			}
		}
		OrderComparator.sort(adviceBeans);
		return adviceBeans;
	}

	@Nullable
	private static Class<?> getBeanType(String beanName, BeanFactory beanFactory) {
		Class<?> beanType = beanFactory.getType(beanName);
		return (beanType != null ? ClassUtils.getUserClass(beanType) : null);
	}

	private static HandlerTypePredicate createBeanTypePredicate(@Nullable Class<?> beanType) {
		ControllerAdvice controllerAdvice = (beanType != null ?
				AnnotatedElementUtils.findMergedAnnotation(beanType, ControllerAdvice.class) : null);
		return createBeanTypePredicate(controllerAdvice);
	}

	private static HandlerTypePredicate createBeanTypePredicate(@Nullable ControllerAdvice controllerAdvice) {
		if (controllerAdvice != null) {
			return HandlerTypePredicate.builder()
					.basePackage(controllerAdvice.basePackages())
					.basePackageClass(controllerAdvice.basePackageClasses())
					.assignableType(controllerAdvice.assignableTypes())
					.annotation(controllerAdvice.annotations())
					.build();
		}
		return HandlerTypePredicate.forAnyHandlerType();
	}

}
