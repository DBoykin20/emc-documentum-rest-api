/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class JsonEntry extends LinkableBase implements Entry {
	@JsonProperty
	private String id;
	@JsonProperty
	private String title;
	@JsonProperty
	private String updated;
	@JsonProperty
	private String summary;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonAuthor.class)
	private List<Author> author;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonLink.class)
	private List<Link> links;
	@JsonProperty
	private Content content;
	
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

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content= content;
	}

	@Override
	public List<Author> getAuthors() {
		return author;
	}

	public void setAuthors(List<Author> authors) {
		this.author = authors;
	}

	@Override
	public String getContentSrc() {
		return content==null?null:content.getSrc();
	}

	@Override
	public String getContentType() {
		return content==null?null:content.getType();
	}

	@Override
	public RestObject getContentObject() {
		return content;
	}

	public static class Content extends JsonObject {
	    private String src;
		@JsonProperty("content-type")
	    private String contentType;
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		
		@Override
		public boolean equals(Object obj) {
			Content that = (Content)obj;
			return super.equals(obj)
				&& Equals.equal(src, that.src) 
				&& Equals.equal(contentType, that.contentType);
		}
	}

	@Override
	public boolean equals(Object obj) {
		JsonEntry that = (JsonEntry)obj;
		return Equals.equal(id, that.id) 
			&& Equals.equal(title, that.title)
			&& Equals.equal(updated, that.updated)
			&& Equals.equal(summary, that.summary)
			&& Equals.equal(content, that.content)
			&& Equals.equal(links, that.links)
			&& Equals.equal(author, that.author);
	}
}