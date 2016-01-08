/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Link;

public class JsonHomeDocument extends LinkableBase implements HomeDocument {
	private Map<String, Map<String, Object>> resources;
	
	public Map<String, Map<String, Object>> getResources() {
		return resources;
	}

	public void setResources(Map<String, Map<String, Object>> resources) {
		this.resources = resources;
	}
	
	@Override
	public boolean equals(Object obj) {
		JsonHomeDocument that = (JsonHomeDocument)obj;
		return Equals.equal(resources, that.resources);
	}

	@Override
	public List<Link> getLinks() {
		List<Link> links = null;
		if(resources != null) {
			links = new ArrayList<Link>(resources.size());
			for(Map.Entry<String, Map<String, Object>> entry : resources.entrySet()) {
				JsonLink l = new JsonLink();
				l.setHref((String)entry.getValue().get("href"));
				l.setRel(entry.getKey());
				links.add(l);
			}
		}
		return links;
	}
}
