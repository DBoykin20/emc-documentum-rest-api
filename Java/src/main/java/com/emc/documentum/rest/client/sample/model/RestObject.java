/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.Map;

/**
 * the class represents the object of the REST services
 */
public interface RestObject extends Linkable {
    /**
     * @return item type, eg. dm_document
     */
	String getType();
	
	/**
	 * @return object root name
	 */
	String getName();
	
	/**
	 * @return object definition type
	 */
	String getDefinition();
	
	/**
	 * @return all attributes of item
	 */
	Map<String, Object> getProperties();
	
	/**
	 * @return the xsi:type properties, valid for xml only
	 */
	String getPropertiesType();
}
