/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TextElement extends AbstractElement {
	private String nodeValue;
	private static final NodeList NODELIST = new NodeList() {
		@Override
		public Node item(int index) {
			return null;
		}
		@Override
		public int getLength() {
			return 0;
		}
	};
	
	public TextElement(String value) {
		super(null);
		this.nodeValue = value==null?"":value;
	}
	
	@Override
	public short getNodeType() {
		return Node.TEXT_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return nodeValue;
	}
	
	@Override
	public NodeList getChildNodes() {
		return NODELIST;
	}

	@Override
	public String getTextContent() throws DOMException {
		return nodeValue;
	}
}
