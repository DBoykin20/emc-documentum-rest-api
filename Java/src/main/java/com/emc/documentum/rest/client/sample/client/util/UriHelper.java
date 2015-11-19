/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * the utility class to process uri
 */
public final class UriHelper {

	/**
	 * append query parameters to uri
	 * @param uri
	 * @param params
	 * @return
	 */
	public static String appendQueryString(String uri, String... params) {
	    String requestUri = uri;
	    if(params!=null && params.length > 0) {
	        requestUri = uri + (uri.contains("?")?'&':'?') + queryString(params);
	    }
	    return requestUri;
	}

	/**
	 * create query string based on the query parameters
	 * @param params
	 * @return
	 */
    public static String queryString(String... params) {
    	if(params.length%2 != 0) {
    		throw new IllegalArgumentException("query parameter must be composed by key value");
    	}
        StringBuilder sb = new StringBuilder();
        if(params != null) {
	        for(int i=0;i<params.length;i+=2) { 
	        	if(i != 0) {
	        		sb.append('&');
	        	}
	        	sb.append(params[i]).append('=').append(params[i+1]);
	        }
        }
        return sb.toString();
    }
    
    /**
     * append more parameters to existed parameters
     * @param params
     * @param moreParams
     * @return
     */
    public static String[] append(String[] params, String... moreParams) {
    	if(params == null) {
    		return moreParams;
    	}
    	if(moreParams == null) {
    		return params;
    	}
    	String[] ps = new String[params.length + moreParams.length];
    	System.arraycopy(params, 0, ps, 0, params.length);
    	System.arraycopy(moreParams, 0, ps, params.length, moreParams.length);
    	return ps;
    }
    
    /**
     * decode uri by URLDecoder
     * @param uri
     * @return
     */
    public static String decode(String uri) {
    	String newUri = uri;
    	try {
			newUri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
    	return newUri;
    }
}
