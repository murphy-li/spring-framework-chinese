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
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

/**
 * SAX {@code ContentHandler} that transforms callback calls to DOM {@code Node}s.
 *
 * @author Arjen Poutsma
 * @since 3.0
 * @see org.w3c.dom.Node
 */
/**
 * SAX {@code  ContentHandler}将回调调用转换为DOM {@code  Node}。 
 *  @author  Arjen Poutsma @从3.0开始
 * @see  org.w3c.dom.Node
 */
class DomContentHandler implements ContentHandler {

	private final Document document;

	private final List<Element> elements = new ArrayList<>();

	private final Node node;


	/**
	 * Create a new instance of the {@code DomContentHandler} with the given node.
	 * @param node the node to publish events to
	 */
	/**
	 * 使用给定的节点创建{@code  DomContentHandler}的新实例。 
	 *  
	 * @param 节点将事件发布到的节点
	 */
	DomContentHandler(Node node) {
		this.node = node;
		if (node instanceof Document) {
			this.document = (Document) node;
		}
		else {
			this.document = node.getOwnerDocument();
		}
	}


	private Node getParent() {
		if (!this.elements.isEmpty()) {
			return this.elements.get(this.elements.size() - 1);
		}
		else {
			return this.node;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		Node parent = getParent();
		Element element = this.document.createElementNS(uri, qName);
		for (int i = 0; i < attributes.getLength(); i++) {
			String attrUri = attributes.getURI(i);
			String attrQname = attributes.getQName(i);
			String value = attributes.getValue(i);
			if (!attrQname.startsWith("xmlns")) {
				element.setAttributeNS(attrUri, attrQname, value);
			}
		}
		element = (Element) parent.appendChild(element);
		this.elements.add(element);
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		this.elements.remove(this.elements.size() - 1);
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		String data = new String(ch, start, length);
		Node parent = getParent();
		Node lastChild = parent.getLastChild();
		if (lastChild != null && lastChild.getNodeType() == Node.TEXT_NODE) {
			((Text) lastChild).appendData(data);
		}
		else {
			Text text = this.document.createTextNode(data);
			parent.appendChild(text);
		}
	}

	@Override
	public void processingInstruction(String target, String data) {
		Node parent = getParent();
		ProcessingInstruction pi = this.document.createProcessingInstruction(target, data);
		parent.appendChild(pi);
	}


	// Unsupported

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void startDocument() {
	}

	@Override
	public void endDocument() {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) {
	}

	@Override
	public void endPrefixMapping(String prefix) {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) {
	}

	@Override
	public void skippedEntity(String name) {
	}

}
