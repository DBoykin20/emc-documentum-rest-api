/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext;
import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "entry", namespace = XMLNamespace.ATOM_NAMESPACE)
public class JaxbEntry extends LinkableBase implements Entry {
	private String id;
	private String title;
	private String updated;
	private String summary;
	private List<Author> authors;
	private List<Link> links;
	private Content content;
	private RestObject contentObject;
	
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

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content= content;
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
	public String getContentSrc() {
		return content==null?null:content.getSrc();
	}

	@Override
	public String getContentType() {
		return content==null?null:content.getType();
	}
	
	@Override
	public RestObject getContentObject() {
		if(contentObject == null && content != null && content.getElement() != null) {
			contentObject = DCTMJaxbContext.unmarshal(content.getElement());
		}
		return contentObject;
	}
	
	@XmlRootElement(name="content")
	public static class Content {
	    private String src;
	    private String type;
		private Element element;
		
		@XmlAnyElement
		public Element getElement() {
			return element;
		}
		public void setElement(Element element) {
			this.element = element;
		}
	    @XmlAttribute
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		@XmlAttribute
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		@Override
		public boolean equals(Object obj) {
			Content that = (Content)obj;
			return Equals.equal(src, that.src) 
				&& Equals.equal(type, that.type);
		}
	}

	@Override
	public boolean equals(Object obj) {
		JaxbEntry that = (JaxbEntry)obj;
		return Equals.equal(id, that.id) 
			&& Equals.equal(title, that.title)
			&& Equals.equal(updated, that.updated)
			&& Equals.equal(summary, that.summary)
			&& Equals.equal(content, that.content)
			&& Equals.equal(links, that.links)
			&& Equals.equal(authors, that.authors);
	}
}