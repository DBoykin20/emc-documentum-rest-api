/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;


public class TestJaxbLink extends TestJaxb {
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
		JaxbLink link = new JaxbLink();
		link.setHref("href");
		link.setHreftemplate("hreftemplate");
		link.setRel("rel");
		link.setTitle("title");
		link.setType("type");
		return (T)link;
	}
}
