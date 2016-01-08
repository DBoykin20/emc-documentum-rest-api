/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "repository", namespace = XMLNamespace.DM_NAMESPACE)
public class JaxbRepository extends LinkableBase implements Repository {
	private int id;
	private String name;
	private String description;
	private List<Server> servers;
	private List<Link> links;

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@XmlElement(name="server", type=JaxbRepository.JaxbServer.class)
	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	@Override
	@XmlElementWrapper(name="links")
	@XmlElement(name="link", type=JaxbLink.class, namespace=XMLNamespace.DM_NAMESPACE)
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@XmlRootElement(name = "server")
	public static class JaxbServer implements Repository.Server {
	    private String name;
	    private String host;
	    private String version;
	    private String docbroker;
		@Override
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		@Override
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		@Override
		public String getDocbroker() {
			return docbroker;
		}
		public void setDocbroker(String docbroker) {
			this.docbroker = docbroker;
		}

		@Override
		public boolean equals(Object obj) {
			JaxbServer that = (JaxbServer) obj;
			return Equals.equal(name, that.name)
					&& Equals.equal(host, that.host)
					&& Equals.equal(version, that.version)
					&& Equals.equal(docbroker, that.docbroker);
		}
	}

	@Override
	public boolean equals(Object obj) {
		JaxbRepository that = (JaxbRepository) obj;
		return Equals.equal(id, that.id)
				&& Equals.equal(name, that.name)
				&& Equals.equal(description, that.description)
				&& Equals.equal(servers, that.servers)
				&& Equals.equal(links, that.links);
	}
}