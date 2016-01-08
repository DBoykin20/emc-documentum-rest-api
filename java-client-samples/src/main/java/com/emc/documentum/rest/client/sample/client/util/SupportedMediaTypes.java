/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import org.springframework.http.MediaType;

/**
 * the class defines supported media type by REST services
 */
public class SupportedMediaTypes {
    public static final MediaType APPLICATION_VND_DCTM_XML = MediaType.valueOf("application/vnd.emc.documentum+xml");
    public static final MediaType APPLICATION_VND_DCTM_JSON = MediaType.valueOf("application/vnd.emc.documentum+json");
    public static final MediaType APPLICATION_JSON_HOME = MediaType.valueOf("application/home+json");
    public static final MediaType APPLICATION_XML_HOME = MediaType.valueOf("application/home+xml");
    
    public static final String APPLICATION_VND_DCTM_XML_VALUE = APPLICATION_VND_DCTM_XML.toString();
    public static final String APPLICATION_VND_DCTM_JSON_VALUE = APPLICATION_VND_DCTM_JSON.toString();
    public static final String APPLICATION_XML_HOME_VALUE = APPLICATION_XML_HOME.toString();
    public static final String APPLICATION_JSON_HOME_VALUE = APPLICATION_JSON_HOME.toString();
}
