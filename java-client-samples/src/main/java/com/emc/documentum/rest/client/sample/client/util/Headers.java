/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * the utility class has some predefined http headers
 */
public final class Headers {
	private HttpHeaders headers = new HttpHeaders();
	public Headers accept(String mediaType) {
		headers.set("Accept", mediaType);
		return this;
	}
	
	public Headers contentType(String mediaType) {
		headers.set("Content-Type", mediaType);
		return this;
	}
	
	public Headers header(String headerName, String headerValue) {
		headers.set(headerName, headerValue);
		return this;
	}
	
	public HttpHeaders toHttpHeaders() {
		return headers;
	}
	
	// some common useful headers
	public static final HttpHeaders ACCEPT_XML_HEADERS = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_ATOM_HEADERS = new Headers().accept(MediaType.APPLICATION_ATOM_XML_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_XML_HEADERS_WITH_CONTENT = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_XML_HEADERS_WITH_MULTIPART_CONTENT = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).contentType(MediaType.MULTIPART_FORM_DATA_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_XML_HOME_DOCUMENT = new Headers().accept(SupportedMediaTypes.APPLICATION_XML_HOME.toString()).toHttpHeaders();
	public static final HttpHeaders XML_CONTENT = new Headers().contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).toHttpHeaders();
	
	public static final HttpHeaders ACCEPT_JSON_HEADERS = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_JSON_HOME_DOCUMENT = new Headers().accept(SupportedMediaTypes.APPLICATION_JSON_HOME.toString()).toHttpHeaders();
	public static final HttpHeaders ACCEPT_JSON_HEADERS_WITH_CONTENT = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).toHttpHeaders();
	public static final HttpHeaders ACCEPT_JSON_HEADERS_WITH_MULTIPART_CONTENT = new Headers().accept(SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).contentType(MediaType.MULTIPART_FORM_DATA_VALUE).toHttpHeaders();
	public static final HttpHeaders JSON_CONTENT = new Headers().contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).toHttpHeaders();
}
