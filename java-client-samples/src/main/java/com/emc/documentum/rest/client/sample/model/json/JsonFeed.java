/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Link;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class JsonFeed extends LinkableBase implements Feed {
	private String id;
	private String title;
	private String updated;
	private String summary;
	private Integer page;
	@JsonProperty("items-per-page")
	private Integer itemsPerPage;
	private Integer total;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonAuthor.class)
	private List<Author> author;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonLink.class)
	private List<Link> links;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonEntry.class)
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
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	@JsonGetter("author")
	public List<Author> getAuthors() {
		return author;
	}

	@JsonSetter("author")
	public void setAuthors(List<Author> authors) {
		this.author = authors;

	}

	@Override
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	@Override
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public boolean equals(Object obj) {
		JsonFeed that = (JsonFeed)obj;
		return Equals.equal(id, that.id) 
			&& Equals.equal(title, that.title)
			&& Equals.equal(updated, that.updated)
			&& Equals.equal(summary, that.summary)
			&& Equals.equal(links, that.links)
			&& Equals.equal(page, that.page)
			&& Equals.equal(itemsPerPage, that.itemsPerPage)
			&& Equals.equal(total, that.total)
			&& Equals.equal(entries, that.entries)
			&& Equals.equal(author, that.author);
	}
}
