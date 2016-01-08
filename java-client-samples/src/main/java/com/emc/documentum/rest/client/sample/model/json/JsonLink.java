/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonLink implements Link {
	@JsonProperty
	private String rel;
	@JsonProperty
	private String hreftemplate;
	@JsonProperty
	private String href;
	@JsonProperty
	private String title;
	@JsonProperty
	private String type;

	@Override
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
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
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		JsonLink that = (JsonLink)obj;
		return Equals.equal(rel, that.rel) 
			&& Equals.equal(hreftemplate, that.hreftemplate)
			&& Equals.equal(href, that.href)
			&& Equals.equal(title, that.title)
			&& Equals.equal(type, that.type);
	}
}
