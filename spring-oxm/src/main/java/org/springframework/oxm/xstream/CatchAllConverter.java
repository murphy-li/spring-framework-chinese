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

package org.springframework.oxm.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream {@link Converter} that supports all classes, but throws exceptions for
 * (un)marshalling.
 *
 * <p>The main purpose of this class is to
 * {@linkplain com.thoughtworks.xstream.XStream#registerConverter(com.thoughtworks.xstream.converters.Converter, int) register}
 * this converter as a catch-all last converter with a
 * {@linkplain com.thoughtworks.xstream.XStream#PRIORITY_NORMAL normal}
 * or higher priority, in addition to converters that explicitly handle the domain
 * classes that should be supported. As a result, default XStream converters with
 * lower priorities and possible security vulnerabilities do not get invoked.
 *
 * <p>For instance:
 * <pre class="code">
 * XStreamMarshaller unmarshaller = new XStreamMarshaller();
 * unmarshaller.getXStream().registerConverter(new MyDomainClassConverter(), XStream.PRIORITY_VERY_HIGH);
 * unmarshaller.getXStream().registerConverter(new CatchAllConverter(), XStream.PRIORITY_NORMAL);
 * MyDomainClass myObject = unmarshaller.unmarshal(source);
 * </pre>
 *
 * @author Arjen Poutsma
 * @since 3.2.5
 */
/**
 * XStream {@link  Converter}支持所有类，但会引发（un）编组异常。 
 *  <p>此类的主要目的是{@link 简单地将com.thoughtworks.xstream.XStream＃registerConverter（com.thoughtworks.xstream.converters.Converter，int）注册}将此转换器作为通用的最后一个转换器具有{@link  plain com.thoughtworks.xstream.XStream＃PRIORITY_NORMAL normal}或更高优先级的转换器，以及明确处理应支持的域类的转换器。 
 * 结果，不会调用具有较低优先级和可能的安全漏洞的默认XStream转换器。 
 *  <p>例如：<pre class ="code"> XStreamMarshaller unmarshaller = new XStreamMarshaller（）; unmarshaller.getXStream（）。 
 * registerConverter（new MyDomainClassConverter（），XStream.PRIORITY_VERY_HIGH）; unmarshaller.getXStream（）。 
 * registerConverter（new CatchAllConverter（），XStream.PRIORITY_NORMAL）; MyDomainClass myObject = unmarshaller.unmarshal（源）; </ pre> @author  Arjen Poutsma @since 3.2.5
 */
public class CatchAllConverter implements Converter {

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return true;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		throw new UnsupportedOperationException("Marshalling not supported");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		throw new UnsupportedOperationException("Unmarshalling not supported");
	}

}
