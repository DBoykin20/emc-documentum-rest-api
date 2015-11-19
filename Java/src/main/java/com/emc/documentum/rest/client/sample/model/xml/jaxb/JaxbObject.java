/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

public abstract class JaxbObject extends LinkableBase implements RestObject {
	protected String type;
	protected String definition;
	protected Map<String, Object> properties;
	protected ObjectProperties objectProperties;

	protected List<Link> links;
	
	public JaxbObject() {
	}

	public JaxbObject(RestObject object) {
		setType(object.getType());
		setDefinition(object.getDefinition());
		setProperties(object.getProperties());
	}
	
	@Override
	@XmlAttribute(namespace = XMLNamespace.SCHEMA_INSTANCE_NAMESPACE)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	@XmlAttribute
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	@XmlTransient
	public Map<String, Object> getProperties() {
		if(properties == null) {
			properties = new HashMap<String, Object>();
			if(objectProperties != null && objectProperties.getElements() != null) {
				for(Element e : objectProperties.getElements()) {
					NodeList nodeList = e.getElementsByTagName("item");
					int n = nodeList.getLength();
					if(n > 0) {
						List<String> list = new ArrayList<String>(n);
						for(int i=0;i<n;++i) {
							list.add(nodeList.item(i).getTextContent());
						}
						properties.put(e.getLocalName(), list);
					} else {
						properties.put(e.getLocalName(), e.getTextContent());
					}
				}
			}
		}
		return properties;
	}

	@SuppressWarnings("unchecked")
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
		if(properties != null) {
			objectProperties = new ObjectProperties();
			List<Element> list = new ArrayList<Element>(properties.size());
			for(Map.Entry<String, Object> entry : properties.entrySet()) {
				if(entry.getValue() instanceof List) {
					list.add(new RepeatingPropertyElement(entry.getKey(), (List<String>)entry.getValue()));
				} else {
					list.add(new SinglePropertyElement(entry.getKey(), (String)entry.getValue()));
				}
			}
			objectProperties.setElements(list);
		}
	}

	@XmlElement(name="properties")
	protected ObjectProperties getObjectProperties() {
		return objectProperties;
	}

	protected void setObjectProperties(ObjectProperties objectProperties) {
		this.objectProperties = objectProperties;
	}
	
	@Override
	public String getPropertiesType() {
		return objectProperties==null?null:objectProperties.getPropertiesType();
	}

	@Override
	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link", type = JaxbLink.class, namespace = XMLNamespace.DM_NAMESPACE)
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@XmlRootElement(name = "properties")
	static class ObjectProperties {
		private List<Element> elements;
		private String propertiesType;
		
		@XmlAnyElement
		public List<Element> getElements() {
			return elements;
		}

		public void setElements(List<Element> elements) {
			this.elements = elements;
		}
		
		@XmlAttribute(name="type", namespace=XMLNamespace.SCHEMA_INSTANCE_NAMESPACE)
		public String getPropertiesType() {
			return propertiesType;
		}

		public void setPropertiesType(String propertiesType) {
			this.propertiesType = propertiesType;
		}
		
		@Override
		public boolean equals(Object obj) {
			ObjectProperties that = (ObjectProperties) obj;
			return Equals.equal(propertiesType, that.propertiesType)
					&& Equals.equal(elements, that.elements);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		JaxbObject that = (JaxbObject) obj;
		return Equals.equal(type, that.type)
				&& Equals.equal(definition, that.definition)
				&& Equals.equal(getProperties(), that.getProperties());
	}
}
