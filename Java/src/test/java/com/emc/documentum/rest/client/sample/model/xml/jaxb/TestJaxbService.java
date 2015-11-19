/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */

package com.emc.documentum.rest.client.sample.model.xml.jaxb;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbHomeDocument.ResourceLink;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbHomeDocument.ServiceResource;

public class TestJaxbService extends TestJaxb {
	
	@Test
	public void testUnmarshal() throws Exception {
		JaxbHomeDocument s = testUnmarshal(xml);
		assertNotNull(s.getLinks());
		assertNotNull(s.getHref(LinkRelation.ABOUT));
		assertNotNull(s.getHref(LinkRelation.REPOSITORIES));
	}
	
	private static final String xml = 
			"	<resources xmlns=\"http://identifiers.emc.com/vocab/documentum\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
			"	    <resource rel=\"http://identifiers.emc.com/linkrel/repositories\">" +
			"	        <link href=\"http://localhost:8080/dctm-rest/repositories.xml\"/>" +
			"	        <hints>" +
			"	            <allow>" +
			"	                <i>GET</i>" +
			"	            </allow>" +
			"	            <representations>" +
			"	                <i>application/xml</i>" +
			"	                <i>application/json</i>" +
			"	                <i>application/atom+xml</i>" +
			"	                <i>application/vnd.emc.documentum+json</i>" +
			"	            </representations>" +
			"	        </hints>" +
			"	    </resource>" +
			"	    <resource rel=\"about\">" +
			"	        <link href=\"http://localhost:8080/dctm-rest/product-info.xml\"/>" +
			"	        <hints>" +
			"	            <allow>" +
			"	                <i>GET</i>" +
			"	            </allow>" +
			"	            <representations>" +
			"	                <i>application/xml</i>" +
			"	                <i>application/json</i>" +
			"	                <i>application/vnd.emc.documentum+xml</i>" +
			"	                <i>application/vnd.emc.documentum+json</i>" +
			"	            </representations>" +
			"	        </hints>" +
			"	    </resource>" +
			"	</resources>";

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
    	JaxbHomeDocument s = new JaxbHomeDocument();
    	List<ServiceResource> resources = new ArrayList<ServiceResource>();
    	ResourceLink l = new ResourceLink();
    	l.setHref("href 1");
    	ServiceResource r = new ServiceResource();
    	r.setLink(l);
    	r.setRel("rel 1");
    	resources.add(r);
    	l = new ResourceLink();
    	l.setHref("href 2");
    	r = new ServiceResource();
    	r.setLink(l);
    	r.setRel("rel 2");
    	resources.add(r);
    	s.setResources(resources);
    	return (T)s;
	}
}
