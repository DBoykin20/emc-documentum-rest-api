/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;

public abstract class LinkableBase implements Linkable {
	@XmlTransient
	private MultiValueMap<String, Link> linksMap;
	
	@Override
	public String getHref(LinkRelation rel) {
		return getValue(rel, null);
	}
	
	@Override
	public String getHref(LinkRelation rel, String title) {
		return getValue(rel, title);
	}
	

	private String getValue(LinkRelation rel, String title) {
		if(linksMap == null && getLinks() != null) {
			linksMap = new LinkedMultiValueMap<String, Link>();
			for(Link l : getLinks()) {
				linksMap.add(l.getRel(), l);
			}
		}
		String href = null;
		if(linksMap != null) {
			List<Link> list = linksMap.get(rel.rel());
			if(list != null) {
				Link link = null;
				if(title == null) {
					link = list.get(0);
				} else {
					for(Link lk : list) {
						if(title.equals(lk.getTitle())) {
							link = lk;
							break;
						}
					}
				}
				if(link != null) {
					href = link.isTemplate()?link.getHreftemplate():link.getHref();
				}
			}
		}
		return href;
	}
}
