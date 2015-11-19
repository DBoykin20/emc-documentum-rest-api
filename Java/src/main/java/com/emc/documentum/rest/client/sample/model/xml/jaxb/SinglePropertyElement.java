/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SinglePropertyElement extends AbstractElement {
	private Element text;
	private NodeList nodelist;
	private Element nextSibling;
	
	public SinglePropertyElement(String name, String value) {
		this(name, value, null);
	}
	
	public SinglePropertyElement(String name, String value, Element nextSibling) {
		super(name);
		this.text = new TextElement(value);
		this.nextSibling = nextSibling;
		this.nodelist = new NodeList() {
			@Override
			public Node item(int index) {
				return text;
			}
			@Override
			public int getLength() {
				return 1;
			}
		};
	}

	@Override
	public Node getNextSibling() {
		return nextSibling;
	}
	
	public void setNextSibling(Element nextSibling) {
		this.nextSibling = nextSibling;
	}

	@Override
	public NodeList getChildNodes() {
		return nodelist;
	}
}
