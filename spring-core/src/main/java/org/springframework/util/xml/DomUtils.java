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

package org.springframework.util.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Convenience methods for working with the DOM API,
 * in particular for working with DOM Nodes and DOM Elements.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Costin Leau
 * @author Arjen Poutsma
 * @author Luke Taylor
 * @since 1.2
 * @see org.w3c.dom.Node
 * @see org.w3c.dom.Element
 */
/**
 * 用于DOM API的便捷方法，特别是用于DOM节点和DOM元素的便捷方法。 
 *  @author  Juergen Hoeller @author  Rob Harrop @author  Costin Leau @author  Arjen Poutsma @author  Luke Taylor @始于1.2 
 * @see  org.w3c.dom.Node 
 * @see  org。 
 *  w3c.dom.Element
 */
public abstract class DomUtils {

	/**
	 * Retrieves all child elements of the given DOM element that match any of the given element names.
	 * Only looks at the direct child level of the given element; do not go into further depth
	 * (in contrast to the DOM API's {@code getElementsByTagName} method).
	 * @param ele the DOM element to analyze
	 * @param childEleNames the child element names to look for
	 * @return a List of child {@code org.w3c.dom.Element} instances
	 * @see org.w3c.dom.Element
	 * @see org.w3c.dom.Element#getElementsByTagName
	 */
	/**
	 * 检索与任何给定元素名称匹配的给定DOM元素的所有子元素。 
	 * 仅查看给定元素的直接子级； 
	 * 不要进一步深入（与DOM API的{@code  getElementsByTagName}方法相反）。 
	 *  
	 * @param 选择DOM元素以分析
	 * @param  childEleNames子元素名称以查找
	 * @return 子{{@code> org.w3c.dom.Element}实例的列表
	 * @see  org。 
	 *  w3c.dom.Element 
	 * @see  org.w3c.dom.Element＃getElementsByTagName
	 */
	public static List<Element> getChildElementsByTagName(Element ele, String... childEleNames) {
		Assert.notNull(ele, "Element must not be null");
		Assert.notNull(childEleNames, "Element names collection must not be null");
		List<String> childEleNameList = Arrays.asList(childEleNames);
		NodeList nl = ele.getChildNodes();
		List<Element> childEles = new ArrayList<>();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameMatch(node, childEleNameList)) {
				childEles.add((Element) node);
			}
		}
		return childEles;
	}

	/**
	 * Retrieves all child elements of the given DOM element that match the given element name.
	 * Only look at the direct child level of the given element; do not go into further depth
	 * (in contrast to the DOM API's {@code getElementsByTagName} method).
	 * @param ele the DOM element to analyze
	 * @param childEleName the child element name to look for
	 * @return a List of child {@code org.w3c.dom.Element} instances
	 * @see org.w3c.dom.Element
	 * @see org.w3c.dom.Element#getElementsByTagName
	 */
	/**
	 * 检索与给定元素名称匹配的给定DOM元素的所有子元素。 
	 * 只看给定元素的直接子级； 
	 * 不要进一步深入（与DOM API的{@code  getElementsByTagName}方法相反）。 
	 *  
	 * @param 选择DOM元素以分析
	 * @param  childEleName子元素名称以查找
	 * @return 子{{@code> org.w3c.dom.Element}实例的列表
	 * @see  org。 
	 *  w3c.dom.Element 
	 * @see  org.w3c.dom.Element＃getElementsByTagName
	 */
	public static List<Element> getChildElementsByTagName(Element ele, String childEleName) {
		return getChildElementsByTagName(ele, new String[] {childEleName});
	}

	/**
	 * Utility method that returns the first child element identified by its name.
	 * @param ele the DOM element to analyze
	 * @param childEleName the child element name to look for
	 * @return the {@code org.w3c.dom.Element} instance, or {@code null} if none found
	 */
	/**
	 * 返回由其名称标识的第一个子元素的实用程序方法。 
	 *  
	 * @param 选择DOM元素以分析
	 * @param  childEleName子元素名称以查找
	 * @return  {@code  org.w3c.dom.Element}实例，或{@code  null}如果找不到
	 */
	@Nullable
	public static Element getChildElementByTagName(Element ele, String childEleName) {
		Assert.notNull(ele, "Element must not be null");
		Assert.notNull(childEleName, "Element name must not be null");
		NodeList nl = ele.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameMatch(node, childEleName)) {
				return (Element) node;
			}
		}
		return null;
	}

	/**
	 * Utility method that returns the first child element value identified by its name.
	 * @param ele the DOM element to analyze
	 * @param childEleName the child element name to look for
	 * @return the extracted text value, or {@code null} if no child element found
	 */
	/**
	 * 返回由其名称标识的第一个子元素值的实用程序方法。 
	 *  
	 * @param 选择DOM元素以分析
	 * @param  childEleName子元素名称以查找
	 * @return 提取的文本值； 
	 * 如果未找到子元素，则为{@code  null}
	 */
	@Nullable
	public static String getChildElementValueByTagName(Element ele, String childEleName) {
		Element child = getChildElementByTagName(ele, childEleName);
		return (child != null ? getTextValue(child) : null);
	}

	/**
	 * Retrieves all child elements of the given DOM element.
	 * @param ele the DOM element to analyze
	 * @return a List of child {@code org.w3c.dom.Element} instances
	 */
	/**
	 * 检索给定DOM元素的所有子元素。 
	 *  
	 * @param 选择DOM元素以分析
	 * @return 子{{@code> org.w3c.dom.Element}实例的列表
	 */
	public static List<Element> getChildElements(Element ele) {
		Assert.notNull(ele, "Element must not be null");
		NodeList nl = ele.getChildNodes();
		List<Element> childEles = new ArrayList<>();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				childEles.add((Element) node);
			}
		}
		return childEles;
	}

	/**
	 * Extracts the text value from the given DOM element, ignoring XML comments.
	 * <p>Appends all CharacterData nodes and EntityReference nodes into a single
	 * String value, excluding Comment nodes. Only exposes actual user-specified
	 * text, no default values of any kind.
	 * @see CharacterData
	 * @see EntityReference
	 * @see Comment
	 */
	/**
	 * 从给定的DOM元素中提取文本值，而忽略XML注释。 
	 *  <p>将所有CharacterData节点和EntityReference节点追加到单个String值中，不包括Comment节点。 
	 * 仅公开实际的用户指定的文本，没有任何默认值。 
	 *  
	 * @see  CharacterData 
	 * @see  EntityReference 
	 * @see 注释
	 */
	public static String getTextValue(Element valueEle) {
		Assert.notNull(valueEle, "Element must not be null");
		StringBuilder sb = new StringBuilder();
		NodeList nl = valueEle.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
				sb.append(item.getNodeValue());
			}
		}
		return sb.toString();
	}

	/**
	 * Namespace-aware equals comparison. Returns {@code true} if either
	 * {@link Node#getLocalName} or {@link Node#getNodeName} equals
	 * {@code desiredName}, otherwise returns {@code false}.
	 */
	/**
	 * 知道命名空间等于比较。 
	 * 如果{@link  Node＃getLocalName}或{@link  Node＃getNodeName}等于{@code  requiredName}，则返回{@code  true}，否则返回{@code  false}。 
	 * 
	 */
	public static boolean nodeNameEquals(Node node, String desiredName) {
		Assert.notNull(node, "Node must not be null");
		Assert.notNull(desiredName, "Desired name must not be null");
		return nodeNameMatch(node, desiredName);
	}

	/**
	 * Returns a SAX {@code ContentHandler} that transforms callback calls to DOM {@code Node}s.
	 * @param node the node to publish events to
	 * @return the content handler
	 */
	/**
	 * 返回一个SAX {@code  ContentHandler}，该回调将回调调用转换为DOM {@code  Node}。 
	 *  
	 * @param 节点是将事件发布到
	 * @return 内容处理程序的节点
	 */
	public static ContentHandler createContentHandler(Node node) {
		return new DomContentHandler(node);
	}

	/**
	 * Matches the given node's name and local name against the given desired name.
	 */
	/**
	 * 将给定节点的名称和本地名称与给定所需名称进行匹配。 
	 * 
	 */
	private static boolean nodeNameMatch(Node node, String desiredName) {
		return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
	}

	/**
	 * Matches the given node's name and local name against the given desired names.
	 */
	/**
	 * 将给定节点的名称和本地名称与给定所需名称进行匹配。 
	 * 
	 */
	private static boolean nodeNameMatch(Node node, Collection<?> desiredNames) {
		return (desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName()));
	}

}
