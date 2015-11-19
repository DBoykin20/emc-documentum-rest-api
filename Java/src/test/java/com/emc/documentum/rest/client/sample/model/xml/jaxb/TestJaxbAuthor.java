/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;


public class TestJaxbAuthor extends TestJaxb {
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
		JaxbAuthor author = new JaxbAuthor();
		author.setEmail("email@emc.com");
		author.setName("user name");
		author.setUri("http://uri");
		return (T)author;
	}
}
