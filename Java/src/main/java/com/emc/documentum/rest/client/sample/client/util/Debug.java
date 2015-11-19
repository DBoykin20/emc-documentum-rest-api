/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.util;

import java.util.List;
import java.util.logging.LogManager;

import javax.xml.transform.Source;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import com.emc.documentum.rest.client.sample.model.RestObject;

public class Debug {
	static LogManager lm;
	
    public static void debug(String msg) {
    	System.out.println("  [debug] " + msg);
    }
    
    public static void error(String msg) {
    	System.err.println("  [error] " + msg);
    }
    
    @SuppressWarnings("unchecked")
	public static void debugObject(Object object) {
		if(object instanceof RestObject) {
			debugRestObject((RestObject)object);
		} else if(object instanceof MultiValueMap) {
			for(List<Object> list : ((MultiValueMap<String, Object>)object).values()) {
				for(Object o : list) {
					if(o instanceof RestObject) {
						debugRestObject((RestObject)o);
					} else if(o instanceof HttpEntity){
						Object oo = ((HttpEntity<?>)o).getBody();
						if(oo instanceof RestObject) {
							debugRestObject((RestObject)oo);
						} else {
							debugContent(oo);
						}
					} else {
						debugContent(o);
					}
				}
			}
		} else {
			debugContent(object);
		}
    }
    
    public static void debugRestObject(RestObject object) {
		if(object != null) {
			debug("RestObject: type=" + object.getType() + ", properties=" + object.getProperties());
		}
	}

    public static void debugContent(Object content) {
		if(content != null) {
			if(content instanceof String) {
				debug("Binary Content: " + content);
			} else if(content instanceof byte[]) {
				byte[] bytes = (byte[])content;
				StringBuilder sb = new StringBuilder();
				for(byte b : bytes) {
					sb.append(Integer.toHexString(b));
				}
				debug("Binary Content: size=" + bytes.length + ", content=" + sb);
			} else if(content instanceof Source) {
				debug("Binary Content: javax.xml.transform.Source");
			} else if(content instanceof Resource) {
				debug("Binary Content: org.springframework.core.io.Resource");
			} else{
				debug("Binary Content: " + content.getClass().getName());
			}
		}
	}
}
