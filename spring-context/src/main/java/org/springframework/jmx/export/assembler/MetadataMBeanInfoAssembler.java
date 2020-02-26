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

package org.springframework.jmx.export.assembler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.management.Descriptor;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.metadata.JmxMetadataUtils;
import org.springframework.jmx.export.metadata.ManagedAttribute;
import org.springframework.jmx.export.metadata.ManagedMetric;
import org.springframework.jmx.export.metadata.ManagedNotification;
import org.springframework.jmx.export.metadata.ManagedOperation;
import org.springframework.jmx.export.metadata.ManagedOperationParameter;
import org.springframework.jmx.export.metadata.ManagedResource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of the {@link MBeanInfoAssembler} interface that reads
 * the management interface information from source level metadata.
 *
 * <p>Uses the {@link JmxAttributeSource} strategy interface, so that
 * metadata can be read using any supported implementation. Out of the box,
 * Spring provides an implementation based on annotations:
 * {@code AnnotationJmxAttributeSource}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @author Jennifer Hickey
 * @since 1.2
 * @see #setAttributeSource
 * @see org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
 */
/**
 * {@link  MBeanInfoAssembler}接口的实现，该接口从源级别元数据中读取管理接口信息。 
 *  <p>使用{@link  JmxAttributeSource}策略界面，以便可以使用任何受支持的实现读取元数据。 
 *  Spring提供了一个现成的基于注释的实现：{@code  AnnotationJmxAttributeSource}。 
 *  @author  Rob Harrop @author  Juergen Hoeller @author  Jennifer Hickey @始于1.2 
 * @see  #setAttributeSource 
 * @see  org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
 */
public class MetadataMBeanInfoAssembler extends AbstractReflectiveMBeanInfoAssembler
		implements AutodetectCapableMBeanInfoAssembler, InitializingBean {

	@Nullable
	private JmxAttributeSource attributeSource;


	/**
	 * Create a new {@code MetadataMBeanInfoAssembler} which needs to be
	 * configured through the {@link #setAttributeSource} method.
	 */
	/**
	 * 创建一个新的{@code  MetadataMBeanInfoAssembler}，需要通过{@link  #setAttributeSource}方法进行配置。 
	 * 
	 */
	public MetadataMBeanInfoAssembler() {
	}

	/**
	 * Create a new {@code MetadataMBeanInfoAssembler} for the given
	 * {@code JmxAttributeSource}.
	 * @param attributeSource the JmxAttributeSource to use
	 */
	/**
	 * 为给定的{@code  JmxAttributeSource}创建一个新的{@code  MetadataMBeanInfoAssembler}。 
	 *  
	 * @param  attributeSource要使用的JmxAttributeSource
	 */
	public MetadataMBeanInfoAssembler(JmxAttributeSource attributeSource) {
		Assert.notNull(attributeSource, "JmxAttributeSource must not be null");
		this.attributeSource = attributeSource;
	}


	/**
	 * Set the {@code JmxAttributeSource} implementation to use for
	 * reading the metadata from the bean class.
	 * @see org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
	 */
	/**
	 * 设置{@code  JmxAttributeSource}实现，以用于从Bean类读取元数据。 
	 *  
	 * @see  org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
	 */
	public void setAttributeSource(JmxAttributeSource attributeSource) {
		Assert.notNull(attributeSource, "JmxAttributeSource must not be null");
		this.attributeSource = attributeSource;
	}

	@Override
	public void afterPropertiesSet() {
		if (this.attributeSource == null) {
			throw new IllegalArgumentException("Property 'attributeSource' is required");
		}
	}

	private JmxAttributeSource obtainAttributeSource() {
		Assert.state(this.attributeSource != null, "No JmxAttributeSource set");
		return this.attributeSource;
	}


	/**
	 * Throws an IllegalArgumentException if it encounters a JDK dynamic proxy.
	 * Metadata can only be read from target classes and CGLIB proxies!
	 */
	/**
	 * 如果遇到JDK动态代理，则抛出IllegalArgumentException。 
	 * 元数据只能从目标类和CGLIB代理中读取！ 
	 * 
	 */
	@Override
	protected void checkManagedBean(Object managedBean) throws IllegalArgumentException {
		if (AopUtils.isJdkDynamicProxy(managedBean)) {
			throw new IllegalArgumentException(
					"MetadataMBeanInfoAssembler does not support JDK dynamic proxies - " +
					"export the target beans directly or use CGLIB proxies instead");
		}
	}

	/**
	 * Used for autodetection of beans. Checks to see if the bean's class has a
	 * {@code ManagedResource} attribute. If so it will add it list of included beans.
	 * @param beanClass the class of the bean
	 * @param beanName the name of the bean in the bean factory
	 */
	/**
	 * 用于自动检测豆类。 
	 * 检查bean的类是否具有{@code  ManagedResource}属性。 
	 * 如果是这样，它将添加它包含的豆列表。 
	 *  
	 * @param  beanClass Bean的类
	 * @param  beanName Bean工厂中Bean的名称
	 */
	@Override
	public boolean includeBean(Class<?> beanClass, String beanName) {
		return (obtainAttributeSource().getManagedResource(getClassToExpose(beanClass)) != null);
	}

	/**
	 * Vote on the inclusion of an attribute accessor.
	 * @param method the accessor method
	 * @param beanKey the key associated with the MBean in the beans map
	 * @return whether the method has the appropriate metadata
	 */
	/**
	 * 表决是否包含属性访问器。 
	 *  
	 * @param 方法访问器方法
	 * @param  beanKey与bean映射中的MBean关联的键
	 * @return 该方法是否具有适当的元数据
	 */
	@Override
	protected boolean includeReadAttribute(Method method, String beanKey) {
		return hasManagedAttribute(method) || hasManagedMetric(method);
	}

	/**
	 * Votes on the inclusion of an attribute mutator.
	 * @param method the mutator method
	 * @param beanKey the key associated with the MBean in the beans map
	 * @return whether the method has the appropriate metadata
	 */
	/**
	 * 表决是否包含属性突变器。 
	 *  
	 * @param 方法mutator方法
	 * @param  beanKey与bean映射中的MBean关联的键
	 * @return 该方法是否具有适当的元数据
	 */
	@Override
	protected boolean includeWriteAttribute(Method method, String beanKey) {
		return hasManagedAttribute(method);
	}

	/**
	 * Votes on the inclusion of an operation.
	 * @param method the operation method
	 * @param beanKey the key associated with the MBean in the beans map
	 * @return whether the method has the appropriate metadata
	 */
	/**
	 * 表决是否包括一项行动。 
	 *  
	 * @param 方法操作方法
	 * @param  beanKey与bean映射中的MBean关联的键
	 * @return 该方法是否具有适当的元数据
	 */
	@Override
	protected boolean includeOperation(Method method, String beanKey) {
		PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
		return (pd != null && hasManagedAttribute(method)) || hasManagedOperation(method);
	}

	/**
	 * Checks to see if the given Method has the {@code ManagedAttribute} attribute.
	 */
	/**
	 * 检查给定的方法是否具有{@code  ManagedAttribute}属性。 
	 * 
	 */
	private boolean hasManagedAttribute(Method method) {
		return (obtainAttributeSource().getManagedAttribute(method) != null);
	}

	/**
	 * Checks to see if the given Method has the {@code ManagedMetric} attribute.
	 */
	/**
	 * 检查给定的方法是否具有{@code  ManagedMetric}属性。 
	 * 
	 */
	private boolean hasManagedMetric(Method method) {
		return (obtainAttributeSource().getManagedMetric(method) != null);
	}

	/**
	 * Checks to see if the given Method has the {@code ManagedOperation} attribute.
	 * @param method the method to check
	 */
	/**
	 * 检查给定的方法是否具有{@code  ManagedOperation}属性。 
	 *  
	 * @param 方法检查的方法
	 */
	private boolean hasManagedOperation(Method method) {
		return (obtainAttributeSource().getManagedOperation(method) != null);
	}


	/**
	 * Reads managed resource description from the source level metadata.
	 * Returns an empty {@code String} if no description can be found.
	 */
	/**
	 * 从源级别元数据读取托管资源描述。 
	 * 如果找不到描述，则返回一个空的{@code  String}。 
	 * 
	 */
	@Override
	protected String getDescription(Object managedBean, String beanKey) {
		ManagedResource mr = obtainAttributeSource().getManagedResource(getClassToExpose(managedBean));
		return (mr != null ? mr.getDescription() : "");
	}

	/**
	 * Creates a description for the attribute corresponding to this property
	 * descriptor. Attempts to create the description using metadata from either
	 * the getter or setter attributes, otherwise uses the property name.
	 */
	/**
	 * 为与此属性描述符对应的属性创建描述。 
	 * 尝试使用来自getter或setter属性的元数据创建描述，否则使用属性名称。 
	 * 
	 */
	@Override
	protected String getAttributeDescription(PropertyDescriptor propertyDescriptor, String beanKey) {
		Method readMethod = propertyDescriptor.getReadMethod();
		Method writeMethod = propertyDescriptor.getWriteMethod();

		ManagedAttribute getter =
				(readMethod != null ? obtainAttributeSource().getManagedAttribute(readMethod) : null);
		ManagedAttribute setter =
				(writeMethod != null ? obtainAttributeSource().getManagedAttribute(writeMethod) : null);

		if (getter != null && StringUtils.hasText(getter.getDescription())) {
			return getter.getDescription();
		}
		else if (setter != null && StringUtils.hasText(setter.getDescription())) {
			return setter.getDescription();
		}

		ManagedMetric metric = (readMethod != null ? obtainAttributeSource().getManagedMetric(readMethod) : null);
		if (metric != null && StringUtils.hasText(metric.getDescription())) {
			return metric.getDescription();
		}

		return propertyDescriptor.getDisplayName();
	}

	/**
	 * Retrieves the description for the supplied {@code Method} from the
	 * metadata. Uses the method name is no description is present in the metadata.
	 */
	/**
	 * 从元数据中检索提供的{@code 方法}的描述。 
	 * 使用方法名称是因为元数据中没有描述。 
	 * 
	 */
	@Override
	protected String getOperationDescription(Method method, String beanKey) {
		PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
		if (pd != null) {
			ManagedAttribute ma = obtainAttributeSource().getManagedAttribute(method);
			if (ma != null && StringUtils.hasText(ma.getDescription())) {
				return ma.getDescription();
			}
			ManagedMetric metric = obtainAttributeSource().getManagedMetric(method);
			if (metric != null && StringUtils.hasText(metric.getDescription())) {
				return metric.getDescription();
			}
			return method.getName();
		}
		else {
			ManagedOperation mo = obtainAttributeSource().getManagedOperation(method);
			if (mo != null && StringUtils.hasText(mo.getDescription())) {
				return mo.getDescription();
			}
			return method.getName();
		}
	}

	/**
	 * Reads {@code MBeanParameterInfo} from the {@code ManagedOperationParameter}
	 * attributes attached to a method. Returns an empty array of {@code MBeanParameterInfo}
	 * if no attributes are found.
	 */
	/**
	 * 从附加到方法的{@code  ManagedOperationParameter}属性中读取{@code  MBeanParameterInfo}。 
	 * 如果未找到任何属性，则返回{@code  MBeanParameterInfo}的空数组。 
	 * 
	 */
	@Override
	protected MBeanParameterInfo[] getOperationParameters(Method method, String beanKey) {
		ManagedOperationParameter[] params = obtainAttributeSource().getManagedOperationParameters(method);
		if (ObjectUtils.isEmpty(params)) {
			return super.getOperationParameters(method, beanKey);
		}

		MBeanParameterInfo[] parameterInfo = new MBeanParameterInfo[params.length];
		Class<?>[] methodParameters = method.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			ManagedOperationParameter param = params[i];
			parameterInfo[i] =
					new MBeanParameterInfo(param.getName(), methodParameters[i].getName(), param.getDescription());
		}
		return parameterInfo;
	}

	/**
	 * Reads the {@link ManagedNotification} metadata from the {@code Class} of the managed resource
	 * and generates and returns the corresponding {@link ModelMBeanNotificationInfo} metadata.
	 */
	/**
	 * 从托管资源的{@code 类}中读取{@link  ManagedNotification}元数据，并生成并返回相应的{@link  ModelMBeanNotificationInfo}元数据。 
	 * 
	 */
	@Override
	protected ModelMBeanNotificationInfo[] getNotificationInfo(Object managedBean, String beanKey) {
		ManagedNotification[] notificationAttributes =
				obtainAttributeSource().getManagedNotifications(getClassToExpose(managedBean));
		ModelMBeanNotificationInfo[] notificationInfos =
				new ModelMBeanNotificationInfo[notificationAttributes.length];

		for (int i = 0; i < notificationAttributes.length; i++) {
			ManagedNotification attribute = notificationAttributes[i];
			notificationInfos[i] = JmxMetadataUtils.convertToModelMBeanNotificationInfo(attribute);
		}

		return notificationInfos;
	}

	/**
	 * Adds descriptor fields from the {@code ManagedResource} attribute
	 * to the MBean descriptor. Specifically, adds the {@code currencyTimeLimit},
	 * {@code persistPolicy}, {@code persistPeriod}, {@code persistLocation}
	 * and {@code persistName} descriptor fields if they are present in the metadata.
	 */
	/**
	 * 将{@code  ManagedResource}属性中的描述符字段添加到MBean描述符。 
	 * 具体来说，添加{@code  currencyTimeLimit}，{<@code> persistPolicy}，{<@code> persistPeriod}，{<@code> persistLocation}和{@code  persistName}描述符字段（如果存在）元数据。 
	 * 
	 */
	@Override
	protected void populateMBeanDescriptor(Descriptor desc, Object managedBean, String beanKey) {
		ManagedResource mr = obtainAttributeSource().getManagedResource(getClassToExpose(managedBean));
		if (mr == null) {
			throw new InvalidMetadataException(
					"No ManagedResource attribute found for class: " + getClassToExpose(managedBean));
		}

		applyCurrencyTimeLimit(desc, mr.getCurrencyTimeLimit());

		if (mr.isLog()) {
			desc.setField(FIELD_LOG, "true");
		}
		if (StringUtils.hasLength(mr.getLogFile())) {
			desc.setField(FIELD_LOG_FILE, mr.getLogFile());
		}

		if (StringUtils.hasLength(mr.getPersistPolicy())) {
			desc.setField(FIELD_PERSIST_POLICY, mr.getPersistPolicy());
		}
		if (mr.getPersistPeriod() >= 0) {
			desc.setField(FIELD_PERSIST_PERIOD, Integer.toString(mr.getPersistPeriod()));
		}
		if (StringUtils.hasLength(mr.getPersistName())) {
			desc.setField(FIELD_PERSIST_NAME, mr.getPersistName());
		}
		if (StringUtils.hasLength(mr.getPersistLocation())) {
			desc.setField(FIELD_PERSIST_LOCATION, mr.getPersistLocation());
		}
	}

	/**
	 * Adds descriptor fields from the {@code ManagedAttribute} attribute or the {@code ManagedMetric} attribute
	 * to the attribute descriptor.
	 */
	/**
	 * 将{@code  ManagedAttribute}属性或{@code  ManagedMetric}属性的描述符字段添加到属性描述符。 
	 * 
	 */
	@Override
	protected void populateAttributeDescriptor(
			Descriptor desc, @Nullable Method getter, @Nullable Method setter, String beanKey) {

		if (getter != null) {
			ManagedMetric metric = obtainAttributeSource().getManagedMetric(getter);
			if (metric != null) {
				populateMetricDescriptor(desc, metric);
				return;
			}
		}

		ManagedAttribute gma = (getter != null ? obtainAttributeSource().getManagedAttribute(getter) : null);
		ManagedAttribute sma = (setter != null ? obtainAttributeSource().getManagedAttribute(setter) : null);
		populateAttributeDescriptor(desc,
				(gma != null ? gma : ManagedAttribute.EMPTY),
				(sma != null ? sma : ManagedAttribute.EMPTY));
	}

	private void populateAttributeDescriptor(Descriptor desc, ManagedAttribute gma, ManagedAttribute sma) {
		applyCurrencyTimeLimit(desc, resolveIntDescriptor(gma.getCurrencyTimeLimit(), sma.getCurrencyTimeLimit()));

		Object defaultValue = resolveObjectDescriptor(gma.getDefaultValue(), sma.getDefaultValue());
		desc.setField(FIELD_DEFAULT, defaultValue);

		String persistPolicy = resolveStringDescriptor(gma.getPersistPolicy(), sma.getPersistPolicy());
		if (StringUtils.hasLength(persistPolicy)) {
			desc.setField(FIELD_PERSIST_POLICY, persistPolicy);
		}
		int persistPeriod = resolveIntDescriptor(gma.getPersistPeriod(), sma.getPersistPeriod());
		if (persistPeriod >= 0) {
			desc.setField(FIELD_PERSIST_PERIOD, Integer.toString(persistPeriod));
		}
	}

	private void populateMetricDescriptor(Descriptor desc, ManagedMetric metric) {
		applyCurrencyTimeLimit(desc, metric.getCurrencyTimeLimit());

		if (StringUtils.hasLength(metric.getPersistPolicy())) {
			desc.setField(FIELD_PERSIST_POLICY, metric.getPersistPolicy());
		}
		if (metric.getPersistPeriod() >= 0) {
			desc.setField(FIELD_PERSIST_PERIOD, Integer.toString(metric.getPersistPeriod()));
		}

		if (StringUtils.hasLength(metric.getDisplayName())) {
			desc.setField(FIELD_DISPLAY_NAME, metric.getDisplayName());
		}

		if (StringUtils.hasLength(metric.getUnit())) {
			desc.setField(FIELD_UNITS, metric.getUnit());
		}

		if (StringUtils.hasLength(metric.getCategory())) {
			desc.setField(FIELD_METRIC_CATEGORY, metric.getCategory());
		}

		desc.setField(FIELD_METRIC_TYPE, metric.getMetricType().toString());
	}

	/**
	 * Adds descriptor fields from the {@code ManagedAttribute} attribute
	 * to the attribute descriptor. Specifically, adds the {@code currencyTimeLimit}
	 * descriptor field if it is present in the metadata.
	 */
	/**
	 * 将{@code  ManagedAttribute}属性中的描述符字段添加到属性描述符。 
	 * 具体来说，如果元数据中存在{@code  currencyTimeLimit}描述符字段，则添加该字段。 
	 * 
	 */
	@Override
	protected void populateOperationDescriptor(Descriptor desc, Method method, String beanKey) {
		ManagedOperation mo = obtainAttributeSource().getManagedOperation(method);
		if (mo != null) {
			applyCurrencyTimeLimit(desc, mo.getCurrencyTimeLimit());
		}
	}

	/**
	 * Determines which of two {@code int} values should be used as the value
	 * for an attribute descriptor. In general, only the getter or the setter will
	 * be have a non-negative value so we use that value. In the event that both values
	 * are non-negative, we use the greater of the two. This method can be used to
	 * resolve any {@code int} valued descriptor where there are two possible values.
	 * @param getter the int value associated with the getter for this attribute
	 * @param setter the int associated with the setter for this attribute
	 */
	/**
	 * 确定应将两个{@code  int}值中的哪一个用作属性描述符的值。 
	 * 通常，只有getter或setter会具有非负值，因此我们使用该值。 
	 * 如果两个值均为非负值，则使用两者中的较大者。 
	 * 此方法可用于解析存在两个可能值的任何{@code  int}值的描述符。 
	 *  
	 * @param  getter与该属性的getter关联的int值
	 * @param  setter与该属性的setter关联的int
	 */
	private int resolveIntDescriptor(int getter, int setter) {
		return (getter >= setter ? getter : setter);
	}

	/**
	 * Locates the value of a descriptor based on values attached
	 * to both the getter and setter methods. If both have values
	 * supplied then the value attached to the getter is preferred.
	 * @param getter the Object value associated with the get method
	 * @param setter the Object value associated with the set method
	 * @return the appropriate Object to use as the value for the descriptor
	 */
	/**
	 * 根据附加到getter和setter方法上的值找到描述符的值。 
	 * 如果两个都提供了值，则首选附加到吸气剂的值。 
	 *  
	 * @param  getter与get方法关联的Object值
	 * @param  setter与set方法关联的Object值
	 * @return 适当的Object用作描述符的值
	 */
	@Nullable
	private Object resolveObjectDescriptor(@Nullable Object getter, @Nullable Object setter) {
		return (getter != null ? getter : setter);
	}

	/**
	 * Locates the value of a descriptor based on values attached
	 * to both the getter and setter methods. If both have values
	 * supplied then the value attached to the getter is preferred.
	 * The supplied default value is used to check to see if the value
	 * associated with the getter has changed from the default.
	 * @param getter the String value associated with the get method
	 * @param setter the String value associated with the set method
	 * @return the appropriate String to use as the value for the descriptor
	 */
	/**
	 * 根据附加到getter和setter方法上的值找到描述符的值。 
	 * 如果两个都提供了值，则首选附加到吸气剂的值。 
	 * 提供的默认值用于检查与getter关联的值是否已更改为默认值。 
	 *  
	 * @param  getter与get方法关联的String值
	 * @param  setter与set方法关联的String值
	 * @return 用作描述符值的适当String
	 */
	@Nullable
	private String resolveStringDescriptor(@Nullable String getter, @Nullable String setter) {
		return (StringUtils.hasLength(getter) ? getter : setter);
	}

}
