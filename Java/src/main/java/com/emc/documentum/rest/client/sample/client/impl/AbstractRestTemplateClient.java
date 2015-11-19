/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.client.util.Debug;
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.SupportedMediaTypes;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestError;
import com.emc.documentum.rest.client.sample.model.RestObject;

/**
 * the basic implementation with Spring RestTemplate
 */
@NotThreadSafe
public abstract class AbstractRestTemplateClient implements DCTMRestClient {
	protected final RestTemplate restTemplate = new RestTemplate();
	
	protected final String contextRoot;
	protected final boolean useFormatExtension;
	protected final String repositoryName;
	protected final String username;
	protected final String password;
	protected HomeDocument homeDocument;
	protected Feed repositories;
	protected Repository repository;
	protected boolean enableStreaming = false;
	protected boolean debug;
	
	protected HttpHeaders headers;
	protected HttpStatus status;
	
	
	public AbstractRestTemplateClient(String contextRoot, String repositoryName, String username, String password, boolean useFormatExtension) {
		this.contextRoot = contextRoot;
		this.repositoryName = repositoryName;
		this.username = username;
		this.password = password;
		this.useFormatExtension = useFormatExtension;
		initRestTemplate(restTemplate);
	}
	
	/**
	 * HomeDocument resource uri is the only fixed uri
	 * it must be: "Rest context path"/services
	 * e.g.
	 * http://localhost:8080/dctm-rest/services 
	 * @return the HomeDocument resource uri
	 */
	protected String getHomeDocumentUri() {
		String servicesUri = contextRoot + "/services";
		if(useFormatExtension) {
			switch(getClientType()) {
				case XML:
					servicesUri += ".xml";
					break;
				case JSON:
					servicesUri += ".json";
					break;
			}
		}
		return servicesUri;
	}
	
	@Override
	public HttpHeaders getHeaders() {
		return headers;
	}
	
	@Override
	public HttpStatus getStatus() {
		return status;
	}
	
	public AbstractRestTemplateClient debug(boolean debug) {
		this.debug = debug;
		return this;
	}
	
	protected void initRestTemplate(RestTemplate restTemplate) {
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}
	
	protected abstract ClientType getClientType();
	
    private void setupHttp(ResponseEntity<?> entity) {
        if(entity == null) {
            headers = null;
            status = null;
        } else {
            headers = entity.getHeaders();
            status = entity.getStatusCode();
        	if(debug) {
        		Debug.debug("Response status: " + getStatus());
        		Debug.debug("Response headers: " + getHeaders());
        	}
        }        
    }
    
    private void setupHttp(DCTMRestErrorException exception) {
        if(exception == null) {
            headers = null;
            status = null;
        } else {
            headers = exception.getHeaders();
            status = exception.getStatus();
        	if(debug) {
        		Debug.debug("Response status: " + getStatus());
        		Debug.debug("Response headers: " + getHeaders());
        	}
        }        
    }
    	
    /**
     * send the request and process the response by Spring RestTemplate
     * @param uri the resource uri
     * @param httpMethod the HTTP method, e.g. GET, POST, DELETE...
     * @param headers the request http headers
     * @param requestBody the request http body
     * @param responseBodyClass the class to represent response
     * @param params request parameters
     * @return
     */
	protected <T> T sendRequest(String uri,
                             HttpMethod httpMethod,
                             HttpHeaders headers,
                             Object requestBody,
                             Class<T> responseBodyClass,
                             String... params) {
		if(debug) {
			Debug.debug("Resource URI: " + uri);
			Debug.debug("HTTP method: " + httpMethod);
			Debug.debug("Request headers: " + headers);
			Debug.debugObject(requestBody);
			if(responseBodyClass != null) {
				Debug.debug("Expected response body class: " + responseBodyClass.getSimpleName());
			}
			if(params != null) {
				Debug.debug("Request parameters: " + UriHelper.queryString(params));
			}
		}
		
		if(uri == null || uri.length() == 0) {
			throw new IllegalStateException("The resource URI is empty, or you do not have priviledge");
		}
		uri = UriHelper.decode(uri);
		if(username != null && password != null) {
			HttpHeaders clonedHeader = new HttpHeaders();
			clonedHeader.putAll(headers);
	        String usernameAndPassword = username + ":" + password;
	        clonedHeader.add("Authorization", "Basic " + new String(Base64.encodeBase64(usernameAndPassword.getBytes())));
			headers = clonedHeader;
		}
        HttpEntity<Object> requestEntity = requestBody == null ? 
                new HttpEntity<Object>(headers) :
                new HttpEntity<Object>(requestBody, headers);
        String requestUri = UriHelper.appendQueryString(uri, params);
        if(debug) {
        	Debug.debug("Sending " + httpMethod + " request to " + uri);
        }
        ResponseEntity<T> entity = null;
        if(enableStreaming) {
        	ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
        	if(factory instanceof SimpleClientHttpRequestFactory) {
        		((SimpleClientHttpRequestFactory)factory).setBufferRequestBody(false);
        		if(debug) {
        			Debug.debug("Enable streaming mode for request body transfer");
        		}
        	} else if(factory instanceof HttpComponentsClientHttpRequestFactory) {
        		((HttpComponentsClientHttpRequestFactory)factory).setBufferRequestBody(false);
        		if(debug) {
        			Debug.debug("Enable streaming mode for request body transfer");
        		}
        	}
        }
        try {
        	entity = restTemplate.exchange(requestUri, httpMethod, requestEntity, responseBodyClass);
        	setupHttp(entity);
        } catch(DCTMRestErrorException e) {
            setupHttp(e);
            RestError error = e.getError();
            if(error != null) {
            	Debug.error("status [" + error.getStatus() + "]");
            	Debug.error("code [" + error.getCode() + "]");
            	Debug.error("message [" + error.getMessage() + "]");
            	Debug.error("detail [" + error.getDetails() + "]");
            }
            throw e;
        } finally {
        	if(enableStreaming) {
        		enableStreaming = false;
            	ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
            	if(factory instanceof SimpleClientHttpRequestFactory) {
            		((SimpleClientHttpRequestFactory)factory).setBufferRequestBody(true);
            		if(debug) {
            			Debug.debug("Disable streaming mode for request body transfer");
            		}
            	} else if(factory instanceof HttpComponentsClientHttpRequestFactory) {
            		((HttpComponentsClientHttpRequestFactory)factory).setBufferRequestBody(true);
            		if(debug) {
            			Debug.debug("Disable streaming mode for request body transfer");
            		}
            	}
        	}
        }
        return entity == null ? null : entity.getBody();
    }
	
	/**
	 * construct a new RestObject based on oldObject's class with new properties
	 * @param oldObject
	 * @param newObject
	 * @return
	 */
	protected RestObject newRestObject(RestObject oldObject, RestObject newObject) {
		try {
			return oldObject.getClass().getConstructor(RestObject.class).newInstance(newObject);
		} catch (Exception e) {
			throw new IllegalArgumentException(oldObject.getClass().getName());
		}
	}
	
	protected <T> T get(String uri, HttpHeaders headers, Class<? extends T> responseBodyClass, String... params) {
    	return sendRequest(uri, HttpMethod.GET, headers, null, responseBodyClass, params);
    }
    
    protected <T> T get(String uri, boolean isCollection, Class<? extends T> responseBodyClass, String... params) {
    	return get(uri, isXml()?(isCollection?Headers.ACCEPT_ATOM_HEADERS:Headers.ACCEPT_XML_HEADERS):Headers.ACCEPT_JSON_HEADERS, responseBodyClass, params);
    }

    protected void delete(String uri, String... params) {
    	sendRequest(uri, HttpMethod.DELETE, isXml()?Headers.ACCEPT_XML_HEADERS:Headers.ACCEPT_JSON_HEADERS, null, null, params);
    }
    
    protected <T> T post(String uri, T body, Class<? extends T> responseBodyClass, String... params) {
    	return sendRequest(uri, HttpMethod.POST, isXml()?Headers.ACCEPT_XML_HEADERS_WITH_CONTENT:Headers.ACCEPT_JSON_HEADERS_WITH_CONTENT, body, responseBodyClass, params);
    }
    
    protected <T> T post(String uri, Object content, String mediaType, Class<? extends T> responseBodyClass, String... params) {
    	return sendRequest(uri, HttpMethod.POST, new Headers().accept(isXml()?SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE:SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE).contentType(mediaType).toHttpHeaders(),
    			content, responseBodyClass, params);
    }
    
    protected <T> T put(String uri, Class<? extends T> responseBodyClass, String... params) {
    	return sendRequest(uri, HttpMethod.PUT, isXml()?Headers.ACCEPT_XML_HEADERS:Headers.ACCEPT_JSON_HEADERS, null, responseBodyClass, params);
    }
    
    protected <T> T post(String uri, T object, Object content, Class<? extends T> responseBodyClass, String... params) {
    	T t = null;
    	if(content == null) {
    		t = post(uri, object, responseBodyClass, params);
    	} else if(object != null && content != null) {
    		Map<String, String> partHeaders = new HashMap<String, String>();
    		partHeaders.put("Content-Type", isXml()?SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE:SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_VALUE);
    		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
    		parts.add("metadata", new HttpEntity<Object>(object, isXml()?Headers.XML_CONTENT:Headers.JSON_CONTENT));
    		parts.add("binary", content);
    		t = sendRequest(uri, HttpMethod.POST, isXml()?Headers.ACCEPT_XML_HEADERS_WITH_MULTIPART_CONTENT:Headers.ACCEPT_JSON_HEADERS_WITH_MULTIPART_CONTENT, parts, responseBodyClass, params);
    	}
    	return t;
    }
    
    @Override
	public void enableStreamingForNextRequest() {
		this.enableStreaming = true;
	}

	protected boolean isXml() {
    	return ClientType.XML == getClientType();
    }
    
    protected boolean isJson() {
    	return ClientType.JSON == getClientType();
    }

    protected static enum ClientType {
		XML, JSON
	}
}
