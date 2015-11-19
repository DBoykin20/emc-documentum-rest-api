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
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;


public class TestJaxbFeed extends TestJaxb {
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
		JaxbFeed feed = new JaxbFeed();
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
		feed.setAuthors(authors);
		feed.setId("http://id");
		List<Link> links = new ArrayList<Link>();
		JaxbLink link = new JaxbLink();
		link.setHref("http://link1");
		link.setRel(LinkRelation.SELF.rel());
		links.add(link);
		link = new JaxbLink();
		link.setHref("http://link2");
		link.setRel(LinkRelation.ALTERNATE.rel());
		links.add(link);
		feed.setLinks(links);
		feed.setSummary("feed summary");
		feed.setTitle("feed title");
		feed.setUpdated("feed updated");
		List<Entry> entries = new ArrayList<Entry>();
		entries.add(createEntry(1));
		entries.add(createEntry(2));
		entries.add(createEntry(3));
		feed.setEntries(entries);
		feed.setPage(10);
		feed.setItemsPerPage(100);
		feed.setTotal(1000);
		return (T)feed;
	}
	
	private static Entry createEntry(int id) {
		JaxbEntry entry = new JaxbEntry();
		List<Author> authors = new ArrayList<Author>();
		JaxbAuthor author = new JaxbAuthor();
		author.setEmail(id + "email1@emc.com");
		author.setName(id + "user name1");
		author.setUri(id + "http://uri1");
		authors.add(author);
		author = new JaxbAuthor();
		author.setEmail(id + "email2@emc.com");
		author.setName(id + "user name2");
		author.setUri(id + "http://uri2");
		authors.add(author);
		entry.setAuthors(authors);
		entry.setId(id + "http://id");
		List<Link> links = new ArrayList<Link>();
		JaxbLink link = new JaxbLink();
		link.setHref(id + "http://link1");
		link.setRel(LinkRelation.SELF.rel());
		links.add(link);
		link = new JaxbLink();
		link.setHref(id + "http://link2");
		link.setRel(LinkRelation.ALTERNATE.rel());
		links.add(link);
		entry.setLinks(links);
		entry.setSummary(id + "entry summary");
		entry.setTitle(id + "entry title");
		entry.setUpdated(id + "entry updated");
		JaxbEntry.Content content = new JaxbEntry.Content();
		content.setSrc("id + http://src");
		content.setType(id + "contenttype");
		entry.setContent(content);
		return entry;
	}
	
	@Test
	public void testUnmarshal() throws Exception {
		JaxbFeed feed = testUnmarshal(xml);
		assertNotNull(feed.getPage());
		assertNotNull(feed.getItemsPerPage());
		assertNotNull(feed.getTotal());
		assertNotNull(feed.getId());
		assertNotNull(feed.getTitle());
		assertNotNull(feed.getSummary());
		assertNotNull(feed.getLinks());
		assertNotNull(feed.getHref(LinkRelation.SELF));
		assertNotNull(feed.getHref(LinkRelation.PAGING_FIRST));
		assertNotNull(feed.getHref(LinkRelation.PAGING_LAST));
		assertNotNull(feed.getEntries());
		Entry entry = feed.getEntries().get(0);
		assertNotNull(entry);
		assertNotNull(entry.getContentSrc());
		assertNotNull(entry.getContentType());
		assertNotNull(entry.getId());
		assertNotNull(entry.getTitle());
		assertNotNull(entry.getSummary());
		assertNotNull(entry.getLinks());
		assertNotNull(entry.getAuthors());
		assertEquals(entry.getAuthors().size(), 1);
		assertNotNull(entry.getHref(LinkRelation.EDIT));
	}
	
	private static final String xml = 
			"<feed xmlns=\"http://www.w3.org/2005/Atom\"" +
			"    xmlns:dm=\"http://identifiers.emc.com/vocab/documentum\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
			"    <id>http://localhost:8080/dctm-rest/repositories/acme/folders/0b00000180003903/objects</id>" +
			"    <title>Objects under folder 0b00000180003903</title>" +
			"    <updated>2014-02-18T16:41:25.516+08:00</updated>" +
			"    <author>" +
			"        <name>EMC Documentum</name>" +
			"    </author>" +
			"    <summary>my feed</summary>" +
			"    <dm:total>5</dm:total>" +
			"    <dm:page>1</dm:page>" +
			"    <dm:items-per-page>1</dm:items-per-page>" +
			"    <link" +
			"        href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0b00000180003903/objects.xml?include-total=true&amp;items-per-page=1\" rel=\"self\"/>" +
			"    <link" +
			"        href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0b00000180003903/objects.xml?include-total=true&amp;items-per-page=1&amp;page=2\" rel=\"next\"/>" +
			"    <link" +
			"        href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0b00000180003903/objects.xml?include-total=true&amp;items-per-page=1&amp;page=1\" rel=\"first\"/>" +
			"    <link" +
			"        href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0b00000180003903/objects.xml?include-total=true&amp;items-per-page=1&amp;page=5\" rel=\"last\"/>" +
			"    <entry>" +
			"        <id>http://localhost:8080/dctm-rest/repositories/acme/objects/090000018000a91d</id>" +
			"        <title>newjsonobj</title>" +
			"        <updated>2014-01-27T15:27:51.000+08:00</updated>" +
			"        <summary>dm_document 090000018000a91d</summary>" +
			"        <author>" +
			"            <name>cs</name>" +
			"            <uri>http://localhost:8080/dctm-rest/repositories/acme/users/cs</uri>" +
			"        </author>" +
			"        <content" +
			"            src=\"http://localhost:8080/dctm-rest/repositories/acme/objects/090000018000a91d.xml\" type=\"application/xml\"/>" +
			"        <link" +
			"            href=\"http://localhost:8080/dctm-rest/repositories/acme/objects/090000018000a91d.xml\" rel=\"edit\"/>" +
			"    </entry>" +
			"</feed>";
}
