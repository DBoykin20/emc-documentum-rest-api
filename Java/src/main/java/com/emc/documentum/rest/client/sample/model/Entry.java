/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * the class represents the entry of a feed
 */
public interface Entry extends Linkable {
    
    /**
     * @return entry id
     */
    String getId();
    
    /**
     * @return entry title
     */
    String getTitle();
    
    /**
     * @return entry updated time
     */
    String getUpdated();
    
    /**
     * @return entry summary
     */
    String getSummary();
    
    /**
     * @return entry authors
     */
    List<Author> getAuthors();
    
    /**
     * for entry without inline content
     * @return the content source uri
     */
    String getContentSrc();
    
    /**
     * for entry without inline content
     * @return the content type of the source uri
     */
    String getContentType();
    
    /**
     * for entry with inline content
     * @return the RestObject embedded in the entry
     */
    RestObject getContentObject();
}
