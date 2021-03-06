/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the plain RestObject implementation which has properties only
 * normally used when create/update the RestObject
 */
public class PlainRestObject implements RestObject {
	private final String type;
	private final Map<String, Object> properties;

	public PlainRestObject(String type, Map<String, Object> properties) {
		this.properties = properties;
		this.type = type;
	}
	
	public PlainRestObject(Map<String, Object> properties) {
		this(null, properties);
	}
	
	public PlainRestObject(String...properties) {
		if(properties == null || properties.length %2 != 0) {
			throw new IllegalArgumentException("the properties must be key value pair");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<properties.length;i+=2) {
			map.put(properties[i], properties[i+1]);
		}
		this.type = null;
		this.properties = map;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	@Override
	public List<Link> getLinks() {
		return null;
	}

	@Override
	public String getHref(LinkRelation rel) {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDefinition() {
		return null;
	}

	@Override
	public String getPropertiesType() {
		return null;
	}

	@Override
	public String getHref(LinkRelation rel, String title) {
		return null;
	}
}
