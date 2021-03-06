/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2007 the original author or authors.
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
 * 版权所有2002-2007的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jmx.export;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;

/**
 * Extension of the {@link RequiredModelMBean} class that ensures the
 * {@link Thread#getContextClassLoader() thread context ClassLoader} is switched
 * for the managed resource's {@link ClassLoader} before any invocations occur.
 *
 * @author Rob Harrop
 * @since 2.0
 * @see RequiredModelMBean
 */
/**
 * {@link  RequiredModelMBean}类的扩展，以确保在进行任何调用之前，为受管资源的{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）线程上下文ClassLoader}。 
 *  @author  Rob Harrop @始于2.0 
 * @see  RequiredModelMBean
 */
public class SpringModelMBean extends RequiredModelMBean {

	/**
	 * Stores the {@link ClassLoader} to use for invocations. Defaults
	 * to the current thread {@link ClassLoader}.
	 */
	/**
	 * 存储{@link  ClassLoader}以用于调用。 
	 * 默认为当前线程{@link  ClassLoader}。 
	 * 
	 */
	private ClassLoader managedResourceClassLoader = Thread.currentThread().getContextClassLoader();


	/**
	 * Construct a new SpringModelMBean instance with an empty {@link ModelMBeanInfo}.
	 * @see javax.management.modelmbean.RequiredModelMBean#RequiredModelMBean()
	 */
	/**
	 * 使用空的{@link  ModelMBeanInfo}构造一个新的SpringModelMBean实例。 
	 *  
	 * @see  javax.management.modelmbean.RequiredModelMBean＃RequiredModelMBean（）
	 */
	public SpringModelMBean() throws MBeanException, RuntimeOperationsException {
		super();
	}

	/**
	 * Construct a new SpringModelMBean instance with the given {@link ModelMBeanInfo}.
	 * @see javax.management.modelmbean.RequiredModelMBean#RequiredModelMBean(ModelMBeanInfo)
	 */
	/**
	 * 使用给定的{@link  ModelMBeanInfo}构造一个新的SpringModelMBean实例。 
	 *  
	 * @see  javax.management.modelmbean.RequiredModelMBean＃RequiredModelMBean（ModelMBeanInfo）
	 */
	public SpringModelMBean(ModelMBeanInfo mbi) throws MBeanException, RuntimeOperationsException {
		super(mbi);
	}


	/**
	 * Sets managed resource to expose and stores its {@link ClassLoader}.
	 */
	/**
	 * 设置托管资源以公开并存储其{@link  ClassLoader}。 
	 * 
	 */
	@Override
	public void setManagedResource(Object managedResource, String managedResourceType)
			throws MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException {

		this.managedResourceClassLoader = managedResource.getClass().getClassLoader();
		super.setManagedResource(managedResource, managedResourceType);
	}


	/**
	 * Switches the {@link Thread#getContextClassLoader() context ClassLoader} for the
	 * managed resources {@link ClassLoader} before allowing the invocation to occur.
	 * @see javax.management.modelmbean.ModelMBean#invoke
	 */
	/**
	 * 在允许调用发生之前，为托管资源{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）上下文ClassLoader}。 
	 *  
	 * @see  javax.management.modelmbean.ModelMBean＃invoke
	 */
	@Override
	public Object invoke(String opName, Object[] opArgs, String[] sig)
			throws MBeanException, ReflectionException {

		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
			return super.invoke(opName, opArgs, sig);
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * Switches the {@link Thread#getContextClassLoader() context ClassLoader} for the
	 * managed resources {@link ClassLoader} before allowing the invocation to occur.
	 * @see javax.management.modelmbean.ModelMBean#getAttribute
	 */
	/**
	 * 在允许调用发生之前，为托管资源{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）上下文ClassLoader}。 
	 *  
	 * @see  javax.management.modelmbean.ModelMBean＃getAttribute
	 */
	@Override
	public Object getAttribute(String attrName)
			throws AttributeNotFoundException, MBeanException, ReflectionException {

		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
			return super.getAttribute(attrName);
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * Switches the {@link Thread#getContextClassLoader() context ClassLoader} for the
	 * managed resources {@link ClassLoader} before allowing the invocation to occur.
	 * @see javax.management.modelmbean.ModelMBean#getAttributes
	 */
	/**
	 * 在允许调用发生之前，为托管资源{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）上下文ClassLoader}。 
	 *  
	 * @see  javax.management.modelmbean.ModelMBean＃getAttributes
	 */
	@Override
	public AttributeList getAttributes(String[] attrNames) {
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
			return super.getAttributes(attrNames);
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * Switches the {@link Thread#getContextClassLoader() context ClassLoader} for the
	 * managed resources {@link ClassLoader} before allowing the invocation to occur.
	 * @see javax.management.modelmbean.ModelMBean#setAttribute
	 */
	/**
	 * 在允许调用发生之前，为托管资源{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）上下文ClassLoader}。 
	 *  
	 * @see  javax.management.modelmbean.ModelMBean＃setAttribute
	 */
	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
			super.setAttribute(attribute);
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

	/**
	 * Switches the {@link Thread#getContextClassLoader() context ClassLoader} for the
	 * managed resources {@link ClassLoader} before allowing the invocation to occur.
	 * @see javax.management.modelmbean.ModelMBean#setAttributes
	 */
	/**
	 * 在允许调用发生之前，为托管资源{@link  ClassLoader}切换{@link  Thread＃getContextClassLoader（）上下文ClassLoader}。 
	 *  
	 * @see  javax.management.modelmbean.ModelMBean＃setAttributes
	 */
	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
			return super.setAttributes(attributes);
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

}
