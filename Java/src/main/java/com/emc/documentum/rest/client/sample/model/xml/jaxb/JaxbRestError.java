/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestError;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="error", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbRestError implements RestError {
	private int status;
	private String code;
	private String message;
	private String details;

	@Override
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public boolean equals(Object obj) {
		JaxbRestError that = (JaxbRestError)obj;
		return Equals.equal(status, that.status) 
			&& Equals.equal(code, that.code)
			&& Equals.equal(message, that.message)
			&& Equals.equal(details, that.details);
	}
}
