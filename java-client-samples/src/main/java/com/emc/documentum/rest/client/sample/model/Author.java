/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * the class represents the Author of Feed and Entry
 */
public interface Author {
	
	/**
	 * @return author name
	 */
    public String getName();
    
    /**
     * @return the uri of the author
     */
    public String getUri();
    
    /**
     * @return the email of the author
     */
    public String getEmail();
}
