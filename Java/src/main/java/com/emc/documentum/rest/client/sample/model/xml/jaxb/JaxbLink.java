/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="link", namespace=XMLNamespace.ATOM_NAMESPACE)
public class JaxbLink implements Link {
	private String rel;
	private String hreftemplate;
	private String href;
	private String title;
	private String type;

	@Override
	@XmlAttribute
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
	@XmlAttribute
	public String getHreftemplate() {
		return hreftemplate;
	}

	public void setHreftemplate(String hreftemplate) {
		this.hreftemplate = hreftemplate;
	}

	@Override
	public boolean isTemplate() {
		return hreftemplate != null;
	}

	@Override
	@XmlAttribute
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		JaxbLink that = (JaxbLink)obj;
		return Equals.equal(rel, that.rel) 
			&& Equals.equal(hreftemplate, that.hreftemplate)
			&& Equals.equal(href, that.href)
			&& Equals.equal(title, that.title)
			&& Equals.equal(type, that.type);
	}
}
