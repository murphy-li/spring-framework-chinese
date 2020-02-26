/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2012 the original author or authors.
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
 * 版权所有2002-2012的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.jmx.export.naming;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * Interface that allows infrastructure components to provide their own
 * {@code ObjectName}s to the {@code MBeanExporter}.
 *
 * <p><b>Note:</b> This interface is mainly intended for internal usage.
 *
 * @author Rob Harrop
 * @since 1.2.2
 * @see org.springframework.jmx.export.MBeanExporter
 */
/**
 * 该接口允许基础结构组件向{@code  MBeanExporter}提供它们自己的{@code  ObjectName}。 
 *  <p> <b>注意：</ b>此接口主要供内部使用。 
 *  @author  Rob Harrop @1.2.2起
 * @see  org.springframework.jmx.export.MBeanExporter
 */
public interface SelfNaming {

	/**
	 * Return the {@code ObjectName} for the implementing object.
	 * @throws MalformedObjectNameException if thrown by the ObjectName constructor
	 * @see javax.management.ObjectName#ObjectName(String)
	 * @see javax.management.ObjectName#getInstance(String)
	 * @see org.springframework.jmx.support.ObjectNameManager#getInstance(String)
	 */
	/**
	 * 返回实现对象的{@code  ObjectName}。 
	 *  
	 * @throws  MalformedObjectNameException，如果由ObjectName构造函数抛出
	 * @see  javax.management.ObjectName＃ObjectName（String）
	 * @see  javax.management.ObjectName＃getInstance（String）
	 * @see  org.springframework.jmx.support .ObjectNameManager＃getInstance（String）
	 */
	ObjectName getObjectName() throws MalformedObjectNameException;

}
