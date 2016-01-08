/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public abstract class TestJaxb {
	protected final static JAXBContext context;
	protected final static Marshaller marshaller;
	protected final static Unmarshaller unmarshaller;
	static {
		JAXBContext c = null;
		Marshaller m = null;
		Unmarshaller u = null;
		try {
			c = JAXBContext.newInstance("com.emc.documentum.rest.client.sample.model.xml.jaxb");
			m = c.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			u = c.createUnmarshaller();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		context = c;
		marshaller = m;
		unmarshaller = u;
	}
	
	protected abstract <T> T createModel();

	protected static String marshal(Object model) throws Exception {
		StringWriter sw = new StringWriter();
		marshaller.marshal(model, sw);
		String str = sw.getBuffer().toString();
		System.out.println(str);
		return str;
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T unmarshal(String xml) throws Exception {
		return (T)unmarshaller.unmarshal(new StringReader(xml));
	}
	
	protected <T> T marshalUnmarshal(T model) throws Exception {
		return unmarshal(marshal(model));
	}
	
	@Test
	public <T> void testMarshalUnmarshal() throws Exception {
		T t1 = createModel();
		T t2 = marshalUnmarshal(t1);
		assertEquals(t1, t2);
	}
	
	public <T> T testUnmarshal(String xml) throws Exception {
		T model = unmarshal(xml);
		assertNotNull(model);
		return model;
	}
}
