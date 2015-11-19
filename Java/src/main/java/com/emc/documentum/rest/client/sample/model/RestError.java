/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * represents the error response from REST services
 */
public interface RestError {
	/**
	 * @return the http status code
	 */
	public int getStatus();
	
	/**
	 * @return the REST error code
	 */
	public String getCode();
	
	/**
	 * @return the error message
	 */
    public String getMessage();
    
    /**
     * @return the detailed error message
     */
    public String getDetails();
}
