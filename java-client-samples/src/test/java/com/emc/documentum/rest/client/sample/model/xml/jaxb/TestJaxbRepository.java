/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */

package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Repository;

public class TestJaxbRepository extends TestJaxb {
	
	@Test
	public void testUnmarshal() throws Exception {
		Repository r = testUnmarshal(xml);
		assertEquals(r.getDescription(), "my repo");
		assertEquals(r.getName(), "acme");
		assertEquals(r.getId(), 1);
		assertNotNull(r.getLinks());
		assertTrue(r.getLinks().size() > 0);
		assertNotNull(r.getHref(LinkRelation.SELF));
		assertNotNull(r.getHref(LinkRelation.CABINETS));
		assertNotNull(r.getHref(LinkRelation.TYPES));
		assertNotNull(r.getHref(LinkRelation.DQL));
	}
	
	private static final String xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<repository xmlns=\"http://identifiers.emc.com/vocab/documentum\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
			"    <id>1</id>" +
			"    <description>my repo</description>" +
			"    <name>acme</name>" +
			"    <server>" +
			"        <name>acme</name>" +
			"        <host>mycs</host>" +
			"        <version xml:space=\"preserve\">6.7.0004.0217  Win64.SQLServer</version>" +
			"        <docbroker>mycs</docbroker>" +
			"    </server>" +
			"    <links>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme.xml\" rel=\"self\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/users.xml\" rel=\"http://identifiers.emc.com/linkrel/users\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/currentuser.xml\" rel=\"http://identifiers.emc.com/linkrel/current-user\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/groups.xml\" rel=\"http://identifiers.emc.com/linkrel/groups\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/cabinets.xml\" rel=\"http://identifiers.emc.com/linkrel/cabinets\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/formats.xml\" rel=\"http://identifiers.emc.com/linkrel/formats\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/network-locations.xml\" rel=\"http://identifiers.emc.com/linkrel/network-locations\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/relations.xml\" rel=\"http://identifiers.emc.com/linkrel/relations\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/relation-types.xml\" rel=\"http://identifiers.emc.com/linkrel/relation-types\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/checked-out-objects.xml\" rel=\"http://identifiers.emc.com/linkrel/checked-out-objects\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/types.xml\" rel=\"http://identifiers.emc.com/linkrel/types\"/>" +
			"        <link" +
			"            hreftemplate=\"http://localhost:8080/dctm-rest/repositories/acme.xml{?dql,page,items-per-page}\" rel=\"http://identifiers.emc.com/linkrel/dql\"/>" +
			"    </links>" +
			"</repository>";

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
    	JaxbRepository r = new JaxbRepository();
    	List<Repository.Server> servers = new ArrayList<Repository.Server>();
    	JaxbRepository.JaxbServer s = new JaxbRepository.JaxbServer();
    	s.setDocbroker("docbroker1");
    	s.setHost("host1");
    	s.setName("name1");
    	s.setVersion("version1");
    	servers.add(s);
    	s = new JaxbRepository.JaxbServer();
    	s.setDocbroker("docbroker2");
    	s.setHost("host2");
    	s.setName("name2");
    	s.setVersion("version2");
    	servers.add(s);
    	r.setServers(servers);
    	r.setDescription("description");
    	r.setId(1);
    	r.setName("name");
		List<Link> links = new ArrayList<Link>();
		JaxbLink link = new JaxbLink();
		link.setHref("http://link1");
		link.setRel(LinkRelation.SELF.rel());
		links.add(link);
		link = new JaxbLink();
		link.setHref("http://link2");
		link.setRel(LinkRelation.ALTERNATE.rel());
		links.add(link);
		r.setLinks(links);
    	return (T)r;
	}
}
