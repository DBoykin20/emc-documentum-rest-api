/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * represents the Repository object
 */
public interface Repository extends Linkable {
    /**
     * @return repository id
     */
    int getId();
    
    /**
     * @return repository name
     */
    String getName();
    
    /**
     * @return repository description
     */
    String getDescription();
    
    /**
     * @return list of servers used by repository
     */
    List<Server> getServers();
    
    /**
     * Repository server information
     */
    public interface Server {
        /**
         * @return server name
         */
        String getName();
        
        /**
         * @return server host
         */
        String getHost();
        
        /**
         * @return server version
         */
        String getVersion();
        
        /**
         * @return docbroker
         */
        String getDocbroker();
    }
}
