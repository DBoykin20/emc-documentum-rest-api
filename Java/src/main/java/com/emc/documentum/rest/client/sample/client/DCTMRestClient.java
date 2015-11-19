/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;

/**
 * The sample REST client library 
 */
@NotThreadSafe
public interface DCTMRestClient {

	/**
	 * @return the http headers of the previous operation 
	 */
	public HttpHeaders getHeaders();
	
	/**
	 * @return the http status of the previous operation
	 */
	public HttpStatus getStatus();
	
	/**
	 * enable streaming for the binary content transfer.
	 * especially useful when upload big files.
	 * only valid for the next operations.
	 * will be automatically disabled after the operation.
	 */
	public void enableStreamingForNextRequest();
	
	/**
	 * @return the cached HomeDocument object
	 */
	public HomeDocument getHomeDocument();
	
	/**
	 * @return the cached Repositories feed
	 */
	public Feed getRepositories();
	
	/**
	 * @return the cached Repository object
	 */
	public Repository getRepository();
	
	/**
	 * execute a readonly dql
	 * @param dql the dql to be executed
	 * @param params the query parameters
	 * @return the query feed response
	 */
	public Feed dql(String dql, String... params);
	
	/**
	 * @param params
	 * @return the cabinets feed based on query parameters
	 */
	public Feed getCabinets(String... params);
	
	/**
	 * @param cabinet the cabinet name
	 * @param params
	 * @return the cabinet by its name
	 */
	public RestObject getCabinet(String cabinet, String... params);
	
	/**
	 * @param parent the parent object, e.g. cabinet, folder
	 * @param params
	 * @return the folders feed under the specified object
	 */
	public Feed getFolders(RestObject parent, String... params);
	
	/**
	 * @param parent the parent object, e.g. cabinet, folder
	 * @param params
	 * @return the documents (dm_document or its subtype) feed under the specified object
	 */
	public Feed getDocuments(RestObject parent, String... params);
	
	/**
	 * @param parent the parent object, e.g. cabinet, folder
	 * @param params
	 * @return the sysobjects (dm_sysobject or its subtype) feed under the specified object
	 */
	public Feed getObjects(RestObject parent, String... params);
	
	/**
	 * @param object
	 * @param params
	 * @return get the object by its self link
	 */
	public RestObject get(RestObject object, String... params);
	
	/**
	 * create a folder under specified folder/cabinet
	 * @param parent the folder/cabinet where the new folder will be created under
	 * @param newFolder the new folder with its properties
	 * @param params
	 * @return the created folder
	 */
	public RestObject createFolder(RestObject parent, RestObject newFolder, String... params);
	
	/**
	 * @param folderUri
	 * @param params
	 * @return the folder of the specified uri
	 */
	public RestObject getFolder(String folderUri, String... params);
	
	/**
	 * create a sysobject (or its subtype) under specified folder/cabinet
	 * @param parent the folder/cabinet where the new sysobject will be created under
	 * @param objectToCreate the new object with its properties
	 * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
	 * @param params
	 * @return the created sysobject
	 */
	public RestObject createObject(RestObject parent, RestObject objectToCreate, Object content, String... params);
	
	/**
	 * @param objectUri
	 * @param params
	 * @return the sysobject of the specified uri
	 */
	public RestObject getObject(String objectUri, String... params);
	
	/**
	 * create a document (or its subtype) under specified folder/cabinet
	 * @param parent the folder/cabinet where the new document will be created under
	 * @param objectToCreate the new document with its properties
	 * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
	 * @param params
	 * @return the created document
	 */
	public RestObject createDocument(RestObject parent, RestObject objectToCreate, Object content, String... params);

	/**
	 * @param documentUri
	 * @param params
	 * @return the document of the specified uri
	 */
	public RestObject getDocument(String documentUri, String... params);
	
	/**
	 * update the RestObject with new properties
	 * @param oldObject the previously fetched RestObject
	 * @param newObject the new RestObject with new properties to be updated
	 * @param params
	 * @return the updated RestObject
	 */
	public RestObject update(RestObject oldObject, RestObject newObject, String... params);
	
	/**
	 * delete the RestObject
	 * @param object the previously fetched RestObject
	 * @param params
	 */
	public void delete(RestObject object, String... params);
	
	/**
	 * create a content (primary content or rendition) to a RestObject
	 * @param object the previously fetched RestObject
	 * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
	 * @param mediaType
	 * @param params
	 * @return
	 */
	public RestObject createContent(RestObject object, Object content, String mediaType, String... params);
	
	/**
	 * @param object
	 * @param params
	 * @return the metadata of primary content of the specified RestObject
	 */
	public RestObject getPrimaryContent(RestObject object, String... params);
	
	/**
	 * @param contentUri
	 * @param params
	 * @return the metadata of the content specified by the uri
	 */
	public RestObject getContent(String contentUri, String... params);
	
	/**
	 * @param object
	 * @param params
	 * @return the content metadata collection
	 */
	public Feed getContents(RestObject object, String... params);
	
	/**
	 * @param feed the current page
	 * @return the next page of the feed collection if it has
	 */
	public Feed nextPage(Feed feed);
	
	/**
	 * @param feed the current page
	 * @return the previous page of the feed collection if it has
	 */
	public Feed previousPage(Feed feed);

	/**
	 * @param feed the current page
	 * @return the first page of the feed collection if it has
	 */
	public Feed firstPage(Feed feed);

	/**
	 * @param feed the current page
	 * @return the last page of the feed collection if it has
	 */
	public Feed lastPage(Feed feed);
	
	/**
	 * @param object
	 * @param params
	 * @return the version histories of the given object
	 */
	public Feed getVersions(RestObject object, String... params);
	
	/**
	 * check out the given RestObject (sysobject, document and their subtype)
	 * @param object
	 * @param params
	 * @return checked out RestObject
	 */
	public RestObject checkout(RestObject object, String... params);
	
	/**
	 * cancel a checked out object
	 * @param object
	 */
	public void cancelCheckout(RestObject object);
	
	/**
	 * check in the object with next major version
	 * can check in both new object metadata and binary content , or new metadata or binary content only
	 * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
	 * @param oldObject the checked out RestObject
	 * @param newObject the new metadata to be checked in
	 * @param content the binary content
	 * @param params
	 * @return checked in object
	 */
	public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String... params);
	
	/**
	 * check in the object with next minor version
	 * can check in both new object metadata and binary content , or new metadata or binary content only
	 * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
	 * @param oldObject the checked out RestObject
	 * @param newObject the new metadata to be checked in
	 * @param content the binary content
	 * @param params
	 * @return checked in object
	 */
	public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String... params);
	
	/**
	 * check in the object with branch version
	 * can check in both new object metadata and binary content , or new metadata or binary content only
	 * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
	 * @param oldObject the checked out RestObject
	 * @param newObject the new metadata to be checked in
	 * @param content the binary content
	 * @param params
	 * @return checked in object
	 */
	public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String... params);
}
