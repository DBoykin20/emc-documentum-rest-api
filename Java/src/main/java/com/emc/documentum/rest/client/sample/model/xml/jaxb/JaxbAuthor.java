/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlType(propOrder= {"name", "uri", "email"})
@XmlRootElement(name="author", namespace=XMLNamespace.ATOM_NAMESPACE)
public class JaxbAuthor implements Author {
    private String name;
    private String uri;
    private String email;
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		JaxbAuthor that = (JaxbAuthor) obj;
		return Equals.equal(email, that.email) && Equals.equal(uri, that.uri)
				&& Equals.equal(email, that.email);
	}
}
