/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * the class represents a feed collection
 */
public interface Feed extends Linkable {
    /**
     * @return feed id
     */
    String getId();
    
    /**
     * @return feed title
     */
    String getTitle();
    
    /**
     * @return feed updated time
     */
    String getUpdated();
    
    /**
     * @return feed authors
     */
    List<Author> getAuthors();
    
    /**
     * @return feed summary
     */
    String getSummary();

    /**
     * @return Entry list of the feed
     */
    List<Entry> getEntries();
    
    /**
     * @return total count of the request feed, including all page of feed
     */
    Integer getTotal();
    
    /**
     * @return current page
     */
    Integer getPage();
    
    /**
     * @return items per page
     */
    Integer getItemsPerPage();
}
