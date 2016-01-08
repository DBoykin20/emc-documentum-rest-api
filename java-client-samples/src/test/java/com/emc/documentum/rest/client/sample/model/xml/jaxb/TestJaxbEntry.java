/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;


public class TestJaxbEntry extends TestJaxb {
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
		JaxbEntry entry = new JaxbEntry();
		List<Author> authors = new ArrayList<Author>();
		JaxbAuthor author = new JaxbAuthor();
		author.setEmail("email1@emc.com");
		author.setName("user name1");
		author.setUri("http://uri1");
		authors.add(author);
		author = new JaxbAuthor();
		author.setEmail("email2@emc.com");
		author.setName("user name2");
		author.setUri("http://uri2");
		authors.add(author);
		entry.setAuthors(authors);
		entry.setId("http://id");
		List<Link> links = new ArrayList<Link>();
		JaxbLink link = new JaxbLink();
		link.setHref("http://link1");
		link.setRel(LinkRelation.SELF.rel());
		links.add(link);
		link = new JaxbLink();
		link.setHref("http://link2");
		link.setRel(LinkRelation.ALTERNATE.rel());
		links.add(link);
		entry.setLinks(links);
		entry.setSummary("entry summary");
		entry.setTitle("entry title");
		entry.setUpdated("entry updated");
		JaxbEntry.Content content = new JaxbEntry.Content();
		content.setSrc("http://src");
		content.setType("contenttype");
		entry.setContent(content);
		return (T)entry;
	}
	
	@Test
	public void testUnmarshal() throws Exception {
		JaxbEntry entry = testUnmarshal(xml);
		assertNotNull(entry.getContent());
		assertNotNull(entry.getContentSrc());
		assertNotNull(entry.getContentType());
		assertNotNull(entry.getId());
		assertNotNull(entry.getTitle());
		assertNotNull(entry.getSummary());
		assertNotNull(entry.getAuthors());
		assertEquals(entry.getAuthors().size(), 1);
		assertNotNull(entry.getHref(LinkRelation.EDIT));
		assertNotNull(entry.getLinks());
	}
	
	private static final String xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
			"    <id>http://localhost:8080/dctm-rest/repositories/acme</id>" +
			"    <title>acme</title>" +
			"    <summary>my repository</summary>" +
	        "    <author>" +
            "        <name>cs</name>" +
            "        <uri>http://localhost:8080/dctm-rest/repositories/acme/users/cs</uri>" +
            "    </author>" +
			"    <content" +
			"        src=\"http://localhost:8080/dctm-rest/repositories/acme.xml\" type=\"application/xml\"/>" +
			"    <link" +
			"        href=\"http://localhost:8080/dctm-rest/repositories/acme.xml\" rel=\"edit\"/>" +
			"</entry>";
}
