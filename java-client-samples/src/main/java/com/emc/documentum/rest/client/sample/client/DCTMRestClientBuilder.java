/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import com.emc.documentum.rest.client.sample.client.impl.jackson.DCTMJacksonClient;
import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbClient;
import com.emc.documentum.rest.client.sample.client.util.Debug;

/**
 * build a DCTMRestClient with parameters
 */
public final class DCTMRestClientBuilder {
	private String contextRoot;
	private String repository;
	private String username;
    private String password;
    private DCTMRestClientBinding binding;
    private boolean useFormatExtension;
    private boolean debug;
    
    /**
     * the context root the REST service
     * e.g.
     * http://localhost:8080/dctm-rest
     * @param contextRoot
     * @return
     */
	public DCTMRestClientBuilder contextRoot(String contextRoot) {
		this.contextRoot = contextRoot.endsWith("/")?contextRoot.substring(0, contextRoot.length()-1):contextRoot;
		return this;
	}
	
	/**
	 * @param repository the repository name
	 * @return
	 */
	public DCTMRestClientBuilder repository(String repository) {
		this.repository = repository;
		return this;
	}
	
	/**
	 * @param binding binding type
	 * @return
	 */
	public DCTMRestClientBuilder bind(DCTMRestClientBinding binding) {
	    this.binding = binding;
	    return this;
	}
	
	/**
	 * whether append .xml or .json extension to the uri
	 * e.g.
	 * http://localhost:8080/dctm-rest/services.xml
	 * or
	 * http://localhost:8080/dctm-rest/services.json
	 * @param useFormatExtension
	 * @return
	 */
	public DCTMRestClientBuilder useFormatExtension(boolean useFormatExtension) {
	    this.useFormatExtension = useFormatExtension;
	    return this;
	}
	
	/**
	 * whether print debug information
	 * @param debug
	 * @return
	 */
	public DCTMRestClientBuilder debug(boolean debug) {
	    this.debug = debug;
	    return this;
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public DCTMRestClientBuilder credentials(String username, String password) {
	    this.username = username;
        this.password = password;
		return this;
	}
	
	public DCTMRestClient build() {
		DCTMRestClient client = null;
		switch(binding) {
			case XML:
				client = new DCTMJaxbClient(contextRoot, repository, username, password, useFormatExtension).debug(debug);
				if(debug) {
					Debug.debug("Build DCTMRestClient with JAXB implementation");
				}
				break;
			case JSON:
				client = new DCTMJacksonClient(contextRoot, repository, username, password, useFormatExtension).debug(debug);
				if(debug) {
					Debug.debug("Build DCTMRestClient with Jackson implementation");
				}
				break;
			default:
				throw new IllegalArgumentException(binding + " binding is not supported yet");	
		}
		if(debug) {
			Debug.debug("Context Root=" + contextRoot);
			Debug.debug("Repository=" + repository);
			Debug.debug("User Name=" + username);
			Debug.debug("Password=" + password);
			Debug.debug("Use Format Extension=" + useFormatExtension);
		}
		return client;
	}
}
