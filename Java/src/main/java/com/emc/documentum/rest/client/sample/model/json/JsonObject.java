/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class JsonObject extends LinkableBase implements RestObject {
	private String name;
	private String type;
	private String definition;
	private Map<String, Object> properties;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonLink.class)
	private List<Link> links;
	
	public JsonObject() {
	}
	
	public JsonObject(RestObject object) {
		this.name = object.getName();
		this.type = object.getType();
		this.definition = object.getDefinition();
		this.properties = object.getProperties();
		this.links = object.getLinks();
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	@Override
	public String getPropertiesType() {
		return null;
	}

	@Override
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public boolean equals(Object obj) {
		JsonObject that = (JsonObject)obj;
		return Equals.equal(name, that.name) 
			&& Equals.equal(type, that.type)
			&& Equals.equal(definition, that.definition)
			&& Equals.equal(properties, that.properties);
	}
}
