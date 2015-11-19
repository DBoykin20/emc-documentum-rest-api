/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jackson;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;

import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.model.RestError;
import com.emc.documentum.rest.client.sample.model.json.JsonRestError;

/**
 * the error response handler to process json error response by Jackson
 */
public class DCTMJacksonErrorHandler implements ResponseErrorHandler {
	private final List<HttpMessageConverter<?>> converters;
	
	public DCTMJacksonErrorHandler(List<HttpMessageConverter<?>> converters) {
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
        	if(converter.canRead(JsonRestError.class, mediaType)) {
        		error = (RestError)converter.read(JsonRestError.class, response);
        		break;
        	}
        }
        throw new DCTMRestErrorException(response.getHeaders(), response.getStatusCode(), error);
    }
}
