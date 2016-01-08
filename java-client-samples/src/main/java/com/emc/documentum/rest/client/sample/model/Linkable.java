/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * the object which is linkable
 */
public interface Linkable {
    /**
     * @return all the links provided by the resource
     */
    List<Link> getLinks();
    
    /**
     * @param rel
     * @return specified link by relation
     */
    String getHref(LinkRelation rel);
    
    /**
     * @param rel
     * @param title
     * @return specified link by relation and title
     */
    String getHref(LinkRelation rel, String title);
}
