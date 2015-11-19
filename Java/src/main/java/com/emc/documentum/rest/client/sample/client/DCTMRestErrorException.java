/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.emc.documentum.rest.client.sample.model.RestError;

/**
 * the exception class, which wraps the error response from REST server
 */
public class DCTMRestErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final RestError error;
    private final HttpHeaders headers;
    private final HttpStatus status;
    
    public DCTMRestErrorException(HttpHeaders headers, HttpStatus status, RestError error) {
        this.headers = headers;
        this.status = status;
        this.error = error;
    }

    public RestError getError() {
        return error;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
