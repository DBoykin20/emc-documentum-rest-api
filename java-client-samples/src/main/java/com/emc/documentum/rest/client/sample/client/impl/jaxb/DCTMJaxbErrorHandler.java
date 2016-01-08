/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;

import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.model.RestError;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbRestError;

/**
 * the error response handler to process xml error response by JAXB
 */
public class DCTMJaxbErrorHandler implements ResponseErrorHandler {
	private final List<HttpMessageConverter<?>> converters;
	
	public DCTMJaxbErrorHandler(List<HttpMessageConverter<?>> converters) {
		this.converters = converters;
	}
	
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void handleError(ClientHttpResponse response) throws IOException {
        MediaType mediaType = response.getHeaders().getContentType();
        RestError error = null;
        for(HttpMessageConverter converter : converters) {
        	if(converter.canRead(JaxbRestError.class, mediaType)) {
        		error = (RestError)converter.read(JaxbRestError.class, response);
        		break;
        	}
        }
        throw new DCTMRestErrorException(response.getHeaders(), response.getStatusCode(), error);
    }
}
