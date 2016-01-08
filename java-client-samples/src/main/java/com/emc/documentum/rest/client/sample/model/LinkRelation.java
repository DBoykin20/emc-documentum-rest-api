/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * the link relations used by REST services
 * the resource should be navigated by link relations except Home Document resource
 * only a few link relations are listed
 */
public enum LinkRelation {
    SELF("self"),
    EDIT("edit"),
    DELETE("delete", true),
    UP("up"),
    DOWN("down"),
    CONTENTS("contents"),
    CONTENT("content"),
    EDIT_MEDIA("edit-media"),
    VERSIONS("version-history"),
    PARENT("parent"),
    ALTERNATE("alternate"),
    PAGING_NEXT("next"),
    PAGING_PREV("previous"),
    PAGING_FIRST("first"),
    PAGING_LAST("last"),
    ENCLOSURE("enclosure"),
    AUTHOR("authour"),
    CANONICAL("canonical"),
    PREDECESSOR_VERSION("predecessor-version"),
    TYPE("type"),
    TYPES("types", true),

    // Documentum specific link relations
    FOLDERS("folders", true),
    DOCUMENTS("documents", true),
    OBJECTS("objects", true),
    ANCESTOR_FOLDER("ancestor-folder", true),
    CABINET("cabinet", true),
    CONTENT_MEDIA("content-media", true),
    PRIMARY_CONTENT("primary-content", true),
    CHECKED_OUT_OBJECTS("checked-out-objects", true),
    DEFAULT_FOLDER("default-folder", true),
 
    //Home Document link relations
    REPOSITORIES("repositories", true),
    ABOUT("about", false),
    RELATIONS("relations", true),
    
    //User/Group link relation
    USERS("users", true),
    USER("user", true),
    GROUPS("groups", true),
    CURRENT_USER("current-user", true),
    
    //for versioning
    CHECKIN_NEXT_MAJOR("checkin-next-major", true),
    CHECKIN_NEXT_MINOR("checkin-next-minor", true),
    CHECKIN_BRANCH_VERSION("checkin-branch", true),
    CHECKOUT("checkout", true),
    CANCEL_CHECKOUT("cancel-checkout", true),
    
    DQL("dql", true),
    CABINETS("cabinets", true),
    FORMATS("formats", true),
    NETWORK_LOCATIONS("network-locations", true),
    PARENT_LINKS("parent-links", true),
    CHILD_LINKS("child-links", true);
    
    private static final String PREFIX = "http://identifiers.emc.com/linkrel/";
    private final String rel;
    
    private LinkRelation(String rel) {
        this.rel = rel;
    }
    
    private LinkRelation(String rel, boolean isDocumentum) {
        this.rel = isDocumentum ? PREFIX + rel : rel;
    }
    
    public String rel() {
        return rel;
    }
}
