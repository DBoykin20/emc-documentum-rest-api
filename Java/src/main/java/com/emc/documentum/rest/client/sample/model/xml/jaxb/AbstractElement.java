/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public abstract class AbstractElement implements Element {
	private String nodeName;
	
	public AbstractElement(String name) {
		this.nodeName = name;
	}
	
	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
	}

	@Override
	public short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	@Override
	public Node getParentNode() {
		return null;
	}

	@Override
	public Node getFirstChild() {
		return (getChildNodes()==null||getChildNodes().getLength()==0)?null:getChildNodes().item(0);
	}

	@Override
	public Node getLastChild() {
		return (getChildNodes()==null||getChildNodes().getLength()==0)?null:getChildNodes().item(getChildNodes().getLength()-1);
	}

	@Override
	public boolean hasChildNodes() {
		return getChildNodes()!=null && getChildNodes().getLength()>0;
	}


	@Override
	public Node getPreviousSibling() {
		return null;
	}

	@Override
	public Node getNextSibling() {
		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return new NamedNodeMap() {
			@Override
			public Node getNamedItem(String name) {
				return null;
			}
			@Override
			public Node setNamedItem(Node arg) throws DOMException {
				return null;
			}
			@Override
			public Node removeNamedItem(String name) throws DOMException {
				return null;
			}
			@Override
			public Node item(int index) {
				return null;
			}
			@Override
			public int getLength() {
				return 0;
			}
			@Override
			public Node getNamedItemNS(String namespaceURI, String localName)
					throws DOMException {
				return null;
			}
			@Override
			public Node setNamedItemNS(Node arg) throws DOMException {
				return null;
			}
			@Override
			public Node removeNamedItemNS(String namespaceURI, String localName)
					throws DOMException {
				return null;
			}
		};
	}

	@Override
	public Document getOwnerDocument() {
		return null;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return null;
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return null;
	}

	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		return null;
	}

	@Override
	public Node appendChild(Node newChild) throws DOMException {
		return null;
	}

	@Override
	public Node cloneNode(boolean deep) {
		return null;
	}

	@Override
	public void normalize() {
	}

	@Override
	public boolean isSupported(String feature, String version) {
		return false;
	}

	@Override
	public String getNamespaceURI() {
		return null;
	}

	@Override
	public String getPrefix() {
		return null;
	}

	@Override
	public void setPrefix(String prefix) throws DOMException {
	}

	@Override
	public String getLocalName() {
		return nodeName;
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String getBaseURI() {
		return null;
	}

	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		return 0;
	}

	@Override
	public void setTextContent(String textContent) throws DOMException {
	}

	@Override
	public String getTextContent() throws DOMException {
		return null;
	}

	@Override
	public boolean isSameNode(Node other) {
		return false;
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		return null;
	}

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	@Override
	public boolean isEqualNode(Node arg) {
		return false;
	}

	@Override
	public Object getFeature(String feature, String version) {
		return null;
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return null;
	}

	@Override
	public Object getUserData(String key) {
		return null;
	}

	@Override
	public String getTagName() {
		return null;
	}

	@Override
	public String getAttribute(String name) {
		return null;
	}

	@Override
	public void setAttribute(String name, String value) throws DOMException {
	}

	@Override
	public void removeAttribute(String name) throws DOMException {
	}

	@Override
	public Attr getAttributeNode(String name) {
		return null;
	}

	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		return null;
	}

	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		return null;
	}

	@Override
	public NodeList getElementsByTagName(String name) {
		return null;
	}

	@Override
	public String getAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		return null;
	}

	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException {
	}

	@Override
	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException {
	}

	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException {
		return null;
	}

	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		return null;
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
			throws DOMException {
		return null;
	}

	@Override
	public boolean hasAttribute(String name) {
		return false;
	}

	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		return false;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		return null;
	}

	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
	}

	@Override
	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
	}

	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
	}
}
