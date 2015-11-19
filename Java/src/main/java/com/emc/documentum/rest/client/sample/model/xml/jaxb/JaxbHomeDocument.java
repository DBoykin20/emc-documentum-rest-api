package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="resources", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbHomeDocument extends LinkableBase implements HomeDocument {
	private List<ServiceResource> resources;
	
	@XmlElement(name="resource")
	public List<ServiceResource> getResources() {
		return resources;
	}

	public void setResources(List<ServiceResource> resources) {
		this.resources = resources;
	}

	public static class ServiceResource {
		private String rel;
		private ResourceLink link;
		
		@XmlAttribute
		public String getRel() {
			return rel;
		}
		public void setRel(String rel) {
			this.rel = rel;
		}
		@XmlElement(name="link")
		public ResourceLink getLink() {
			return link;
		}
		public void setLink(ResourceLink link) {
			this.link = link;
		}
		
		@Override
		public boolean equals(Object obj) {
			ServiceResource that = (ServiceResource)obj;
			return Equals.equal(rel, that.rel) 
				&& Equals.equal(link, that.link);
		}
	}
	
	public static class ResourceLink {
		private String href;

		@XmlAttribute
		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}
		
		@Override
		public boolean equals(Object obj) {
			ResourceLink that = (ResourceLink)obj;
			return Equals.equal(href, that.href);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		JaxbHomeDocument that = (JaxbHomeDocument)obj;
		return Equals.equal(resources, that.resources);
	}

	@Override
	public List<Link> getLinks() {
		List<Link> links = null;
		if(resources != null) {
			links = new ArrayList<Link>(resources.size());
			for(ServiceResource r : resources) {
				JaxbLink l = new JaxbLink();
				l.setHref(r.getLink().getHref());
				l.setRel(r.getRel());
				links.add(l);
			}
		}
		return links;
	}
}
