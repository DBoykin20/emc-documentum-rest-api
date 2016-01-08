/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="feed", namespace=XMLNamespace.ATOM_NAMESPACE)
public class JaxbFeed extends LinkableBase implements Feed {
	private String id;
	private String title;
	private String updated;
	private String summary;
	private Integer page;
	private Integer itemsPerPage;
	private Integer total;
	private List<Author> authors;
	private List<Link> links;
	private List<Entry> entries;
	
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	@XmlElement(name="link", type=JaxbLink.class, namespace=XMLNamespace.ATOM_NAMESPACE)
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	@XmlElement(name="author", type=JaxbAuthor.class)
	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	@XmlElement(name="entry", type=JaxbEntry.class)
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	@XmlElement(namespace=XMLNamespace.DM_NAMESPACE)
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	@XmlElement(name="items-per-page", namespace=XMLNamespace.DM_NAMESPACE)
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	@Override
	@XmlElement(namespace=XMLNamespace.DM_NAMESPACE)
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public boolean equals(Object obj) {
		JaxbFeed that = (JaxbFeed)obj;
		return Equals.equal(id, that.id) 
			&& Equals.equal(title, that.title)
			&& Equals.equal(updated, that.updated)
			&& Equals.equal(summary, that.summary)
			&& Equals.equal(links, that.links)
			&& Equals.equal(page, that.page)
			&& Equals.equal(itemsPerPage, that.itemsPerPage)
			&& Equals.equal(total, that.total)
			&& Equals.equal(entries, that.entries)
			&& Equals.equal(authors, that.authors);
	}
}
