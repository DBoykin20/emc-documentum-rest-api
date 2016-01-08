/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RepeatingPropertyElement extends AbstractElement {
	private ItemNodeList nodeList;
	
	public RepeatingPropertyElement(String name, List<String> values) {
		super(name);
		this.nodeList = new ItemNodeList(values);
	}
	
	@Override
	public NodeList getChildNodes() {
		return nodeList;
	}

	private static class ItemNodeList implements NodeList {
		private List<Element> values;
		
		ItemNodeList(List<String> list) {
			if(list != null) {
				this.values = new ArrayList<Element>(list.size());
				for(String s : list) {
					values.add(new SinglePropertyElement("item", s));
				}
				for(int i=0;i<values.size()-1;++i) {
					((SinglePropertyElement)values.get(i)).setNextSibling(values.get(i+1));
				}
			}
		}
		
		@Override
		public Node item(int index) {
			return values.get(index);
		}

		@Override
		public int getLength() {
			return values==null?0:values.size();
		}
	}
}
