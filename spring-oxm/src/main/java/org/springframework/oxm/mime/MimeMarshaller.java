/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2009 the original author or authors.
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
 * 版权所有2002-2009的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.oxm.mime;

import java.io.IOException;

import javax.xml.transform.Result;

import org.springframework.lang.Nullable;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;

/**
 * Subinterface of {@link Marshaller} that can use MIME attachments to optimize
 * storage of binary data. Attachments can be added as MTOM, XOP, or SwA.
 *
 * @author Arjen Poutsma
 * @since 3.0
 * @see <a href="https://www.w3.org/TR/2004/WD-soap12-mtom-20040608/">SOAP Message Transmission Optimization Mechanism</a>
 * @see <a href="https://www.w3.org/TR/2005/REC-xop10-20050125/">XML-binary Optimized Packaging</a>
 */
/**
 * {@link  Marshaller}的子接口，可以使用MIME附件来优化二进制数据的存储。 
 * 附件可以添加为MTOM，XOP或SwA。 
 *  @author  Arjen Poutsma @since 3.0 
 * @see  <a href="https://www.w3.org/TR/2004/WD-soap12-mtom-20040608/"> SOAP消息传输优化机制</ a > 
 * @see  <a href="https://www.w3.org/TR/2005/REC-xop10-20050125/"> XML二进制优化包装</a>
 */
public interface MimeMarshaller extends Marshaller {

	/**
	 * Marshals the object graph with the given root into the provided {@link Result},
	 * writing binary data to a {@link MimeContainer}.
	 * @param graph the root of the object graph to marshal
	 * @param result the result to marshal to
	 * @param mimeContainer the MIME container to write extracted binary content to
	 * @throws XmlMappingException if the given object cannot be marshalled to the result
	 * @throws IOException if an I/O exception occurs
	 */
	/**
	 * 将具有给定根的对象图编组到提供的{@link  Result}中，将二进制数据写入{@link  MimeContainer}。 
	 *  
	 * @param 绘制对象图的根以进行编组
	 * @param 结果将结果编组为
	 * @param  mimeContainer MIME容器，如果无法将给定对象编组为
	 * @throws  XmlMappingException如果发生I / O异常，则结果为
	 * @throws  IOException
	 */
	void marshal(Object graph, Result result, @Nullable MimeContainer mimeContainer) throws XmlMappingException, IOException;

}
