/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.w3c.dom.Node;

import com.emc.documentum.rest.client.sample.model.RestObject;

/**
 * the class to unmarshal the xml
 */
public final class DCTMJaxbContext {
	private final static JAXBContext context;
	
	static {
		JAXBContext c = null;
		try {
			c = JAXBContext.newInstance("com.emc.documentum.rest.client.sample.model.xml.jaxb");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		context = c;
	}
	
	public static RestObject unmarshal(Node node) {
		RestObject obj = null;
		try {
			obj = (RestObject)context.createUnmarshaller().unmarshal(node);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
