/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

public class JsonRepository extends LinkableBase implements Repository {
	private int id;
	private String name;
	private String description;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JasonServer.class)
	private List<Server> servers;
	@JsonProperty
	@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonLink.class)
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
	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	@Override
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public static class JasonServer implements Repository.Server {
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
			JasonServer that = (JasonServer) obj;
			return Equals.equal(name, that.name)
					&& Equals.equal(host, that.host)
					&& Equals.equal(version, that.version)
					&& Equals.equal(docbroker, that.docbroker);
		}
	}

	@Override
	public boolean equals(Object obj) {
		JsonRepository that = (JsonRepository) obj;
		return Equals.equal(id, that.id)
				&& Equals.equal(name, that.name)
				&& Equals.equal(description, that.description)
				&& Equals.equal(servers, that.servers)
				&& Equals.equal(links, that.links);
	}
}