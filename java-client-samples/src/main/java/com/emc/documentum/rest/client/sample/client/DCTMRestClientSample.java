/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.PlainRestObject;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;


/**
 * sample application uses DCTMRestClient
 */
public class DCTMRestClientSample {
	private static DCTMRestClient client;
	private static final String NEWLINE = System.getProperty("line.separator");
	
	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Please input the client representation type: ");
		for(DCTMRestClientBinding b : DCTMRestClientBinding.values()) {
			sb.append(b.name() + " ");
		}
		String bindingStr = read(sb.toString(), "XML");
		DCTMRestClientBinding binding = DCTMRestClientBinding.valueOf(bindingStr.toUpperCase());
		String contextRoot = read("Please input the REST context path:", "http://localhost:8080/dctm-rest");
		String repository = read("Please input the repository name:");
		String username = read("Please input the username:");
		String password = read("Please input the password:");
		String useFormatExtension = read("Please input the whether add format extension .xml or .json for URI:", "false");
		String debug = read("Please input whether print debug information:", "false");
		
		DCTMRestClientBuilder builder = new DCTMRestClientBuilder().
				bind(binding).
				contextRoot(contextRoot).
				credentials(username, password).
				repository(repository).
				useFormatExtension("true".equalsIgnoreCase(useFormatExtension)).
				debug("true".equalsIgnoreCase(debug));
		
		client = builder.build();
		
		sb = new StringBuilder();
		sb.append("Please input the number of the sample operation which need be executed:").append(NEWLINE)
		  .append("0 Exit").append(NEWLINE)
		  .append("1 Navigation").append(NEWLINE)
		  .append("2 Folder Create/Update/Delete").append(NEWLINE)
		  .append("3 Sysobject Create/Update/Delete").append(NEWLINE)
		  .append("4 Document Create/Update/Delete").append(NEWLINE)
		  .append("5 Content Management").append(NEWLINE)
		  .append("6 Version Management").append(NEWLINE)
		  .append("7 DQL Query");
		
		while(true) {
			String sample = read(sb.toString());
			int op = 0;
			try {
				op = Integer.parseInt(sample);
				switch(op) {
					case 0:
						System.out.println("Thanks, bye.");
						System.exit(0);
						break;
					case 1:
						navigation();
						break;
					case 2:
						crudFolder();
						break;
					case 3:
						crudSysobject();
						break;
					case 4:
						crudDocument();
						break;
					case 5:
						contentManagement();
						break;
					case 6:
						versionManagement();
						break;
					case 7:
						dqlQuery();
						break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String read(String prompt) throws Exception {
		byte[] bytes = new byte[100];
		String value = null;
		while(value == null || value.length() == 0) {
			System.out.println(prompt);
			int readed = System.in.read(bytes);
			while(readed <= 0) {
				System.out.println(prompt);
				readed = System.in.read(bytes);
			}
			value = new String(bytes, 0, readed).trim();
		}
		return value;
	}
	
	private static String read(String prompt, String defaultValue) throws Exception {
		System.out.println(prompt + " [default " + defaultValue + "]"); 
		String value = defaultValue;
		byte[] bytes = new byte[100];
		int readed = System.in.read(bytes);
		if(readed > 0) {
			String tmp = new String(bytes, 0, readed).trim();
			if(tmp.length() > 0) {
				value = tmp;
			}
		}
		return value;
	}

	/**
	 * samples to navigate the Repository
	 * including get HomeDocument, Repositories Feed, Repository, Cabinets Feed, Cabinet, Folder, Document, Sysobject...
	 * also has samples about Feed paging and embedded content into the Feed 
	 */
	private static void navigation() {
		System.out.println("start Navigation sample");
		
		System.out.println("-------------get home document resource");
		HomeDocument home = client.getHomeDocument();
		List<Link> list = home.getLinks();
		for(Link l : list) {
			System.out.println(l.getRel() + " -> " + l.getHref());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get repositories collection");
		Feed repositories = client.getRepositories();
		System.out.println("There are " + repositories.getTotal() + " repositories in total.");
		List<Entry> repositoryEntry = repositories.getEntries();
		for(Entry e : repositoryEntry) {
			System.out.print(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);

		System.out.println("-------------get the repository");
		Repository repository = client.getRepository();
		System.out.println(repository.getName());
		for(Repository.Server s : repository.getServers()) {
			System.out.println("Name: " + s.getName());
			System.out.println("Host: " + s.getHost());
			System.out.println("Docbroker: " + s.getDocbroker());
			System.out.println("Version: " + s.getVersion());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get cabinets");
		Feed cabinets = client.getCabinets();
		for(Entry e : cabinets.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);

		System.out.println("-------------get cabinets by page with 2 cabinets per page");
		cabinets = client.getCabinets("items-per-page", "2", "include-total", "true");
		System.out.println("There are " + cabinets.getTotal() + " cabinets in total.");
		for(Entry e : cabinets.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);

		System.out.println("-------------get cabinets of the next page");
		cabinets = client.nextPage(cabinets);
		for(Entry e : cabinets.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get cabinets of the last page");
		cabinets = client.lastPage(cabinets);
		for(Entry e : cabinets.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);

		System.out.println("-------------get cabinets of the previous page");
		cabinets = client.previousPage(cabinets);
		for(Entry e : cabinets.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get the Temp cabinet");
		RestObject tempCabinet = client.getCabinet("Temp");
		System.out.println(tempCabinet.getProperties().get("r_object_id"));
		System.out.println(NEWLINE);

		System.out.println("-------------get folders under the Temp cabinet");
		Feed folders = client.getFolders(tempCabinet);
		for(Entry e : folders.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get documents under the Temp cabinet");
		Feed documents = client.getDocuments(tempCabinet);
		for(Entry e : documents.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get sysobjects under the Temp cabinet");
		Feed objects = client.getDocuments(tempCabinet);
		for(Entry e : objects.getEntries()) {
			System.out.println(e.getTitle() + " -> " + e.getContentSrc());
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get cabinets with the content embedded in the feed entry");
		cabinets = client.getCabinets("inline", "true");
		for(Entry e : cabinets.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("r_object_id=" + o.getProperties().get("r_object_id") + " object_name=" + o.getProperties().get("object_name"));
			System.out.println(LinkRelation.SELF.rel() + " -> " + o.getHref(LinkRelation.SELF));
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------get folders under the Temp cabinet with the content embedded in the feed entry");
		folders = client.getFolders(tempCabinet, "inline", "true");
		for(Entry e : folders.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("r_object_id=" + o.getProperties().get("r_object_id") + " object_name=" + o.getProperties().get("object_name"));
			System.out.println(LinkRelation.SELF.rel() + " -> " + o.getHref(LinkRelation.SELF));
		}
		System.out.println(NEWLINE);
		
		System.out.println("finish Navigation sample");
		System.out.println(NEWLINE);
	}
	
	/**
	 * samples to create/delete/update folder
	 */
	private static void crudFolder() {
		System.out.println("start Folder Create/Update/Delete sample");

		RestObject tempCabinet = client.getCabinet("Temp");
		
		System.out.println("-------------create a folder under the Temp cabinet");
		RestObject newFolder = new PlainRestObject("object_name", "my_new_folder");
		RestObject createdFolder = client.createFolder(tempCabinet, newFolder);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdFolder.getProperties().get("r_object_id") + " object_name=" + createdFolder.getProperties().get("object_name"));
		System.out.println(NEWLINE);

		System.out.println("-------------update the folder name");
		RestObject updateFolder = new PlainRestObject("object_name", "my_new_folder_updated");
		RestObject updatedFolder = client.update(createdFolder, updateFolder);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + updatedFolder.getProperties().get("r_object_id") + " object_name=" + updatedFolder.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created folder");
		client.delete(createdFolder);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("-------------create a folder under the Temp cabinet");
		RestObject newFolder1 = new PlainRestObject("object_name", "my_new_folder1");
		RestObject createdFolder1 = client.createFolder(tempCabinet, newFolder1);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdFolder1.getProperties().get("r_object_id") + " object_name=" + createdFolder1.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------create a folder under the folder just created");
		RestObject newFolder2 = new PlainRestObject("object_name", "my_new_folder2");
		RestObject createdFolder2 = client.createFolder(createdFolder1, newFolder2);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdFolder2.getProperties().get("r_object_id") + " object_name=" + createdFolder2.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created folder with a folder in it");
		client.delete(createdFolder1, "del-non-empty", "true");
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("finish Folder Create/Update/Delete sample");
		System.out.println(NEWLINE);
	}
	
	/**
	 * samples to create/delete/update Sysobject, including create object with binary content
	 */
	private static void crudSysobject() {
		System.out.println("start Sysobject Create/Update/Delete sample");
		
		RestObject tempCabinet = client.getCabinet("Temp");
		
		System.out.println("-------------create an object without binary content under the Temp cabinet");
		RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
		RestObject createdObjectWithoutContent = client.createObject(tempCabinet, newObjectWithoutContent, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithoutContent.getProperties().get("r_object_id") + " object_name=" + createdObjectWithoutContent.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------update the object with a new name");
		RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
		RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + updatedObjectWithObjectName.getProperties().get("r_object_id") + " object_name=" + updatedObjectWithObjectName.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created object");
		client.delete(updatedObjectWithObjectName);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("-------------create an object with repeating properties and specify dm_docment type under the Temp cabinet");
		Map<String, Object> newPropertiesMap = new HashMap<String, Object>();
		newPropertiesMap.put("r_object_type", "dm_document");
		newPropertiesMap.put("object_name", "obj_with_repeating_properties");
		newPropertiesMap.put("keywords", Arrays.asList("objects", "repeating", "properties"));
		RestObject newObjectWithRepeatingProperties = new PlainRestObject(newPropertiesMap);
		RestObject createdObjectWithRepeatingProperties = client.createObject(tempCabinet, newObjectWithRepeatingProperties, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithRepeatingProperties.getProperties().get("r_object_id") +
				" object_name=" + createdObjectWithRepeatingProperties.getProperties().get("object_name") +
				" r_object_type=" + createdObjectWithRepeatingProperties.getProperties().get("r_object_type"));
		System.out.println("keywords=" + createdObjectWithRepeatingProperties.getProperties().get("keywords"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created object");
		client.delete(createdObjectWithRepeatingProperties);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);

		System.out.println("-------------create an object with specify object type in another place to override the r_object_type");
		RestObject newObjectWithOverwriteType = new PlainRestObject("dm_sysobject", newPropertiesMap);
		RestObject createdObjectWithOverwriteType = client.createObject(tempCabinet, newObjectWithOverwriteType, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithOverwriteType.getProperties().get("r_object_id") +
				" object_name=" + createdObjectWithOverwriteType.getProperties().get("object_name") +
				" r_object_type=" + createdObjectWithOverwriteType.getProperties().get("r_object_type"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created object");
		client.delete(createdObjectWithOverwriteType);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);

		System.out.println("-------------create an object with binary content under the Temp cabinet");
		RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
		RestObject createdObjectWithContent = client.createObject(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithContent.getProperties().get("r_object_id") + " object_name=" + createdObjectWithContent.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created object");
		client.delete(createdObjectWithContent);
		System.out.println("http status: " + client.getStatus());		
		System.out.println(NEWLINE);
		
		System.out.println("finish Sysobject Create/Update/Delete sample");
		System.out.println(NEWLINE);
	}
	
	/**
	 * samples to create/delete/update document, including create document with binary content
	 */
	private static void crudDocument() {
		System.out.println("start Document Create/Update/Delete sample");
		
		RestObject tempCabinet = client.getCabinet("Temp");
		
		System.out.println("-------------create a document without binary content under the Temp cabinet");
		RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
		RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithoutContent.getProperties().get("r_object_id") +
				" object_name=" + createdObjectWithoutContent.getProperties().get("object_name") +
				" r_object_type=" + createdObjectWithoutContent.getProperties().get("r_object_type"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------update the document with a new name");
		RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
		RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + updatedObjectWithObjectName.getProperties().get("r_object_id") + " object_name=" + updatedObjectWithObjectName.getProperties().get("object_name"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created document");
		client.delete(updatedObjectWithObjectName);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);

		System.out.println("-------------create a document with binary content under the Temp cabinet");
		RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
		RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithContent.getProperties().get("r_object_id") +
				" object_name=" + createdObjectWithContent.getProperties().get("object_name") +
				" r_object_type=" + createdObjectWithContent.getProperties().get("r_object_type"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created object");
		client.delete(createdObjectWithContent);
		System.out.println("http status: " + client.getStatus());		
		System.out.println(NEWLINE);
		
		System.out.println("finish Document Create/Update/Delete sample");
		System.out.println(NEWLINE);
	}
	
	/**
	 * samples to manage the content
	 * including create object with content, create a rendition, get primary content, and get all contents' feed 
	 */
	private static void contentManagement() {
		System.out.println("start Content Management sample");
		
		RestObject tempCabinet = client.getCabinet("Temp");
		
		System.out.println("-------------create a document with binary content under the Temp cabinet");
		RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
		RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithContent.getProperties().get("r_object_id") +
				" object_name=" + createdObjectWithContent.getProperties().get("object_name") +
				" r_object_type=" + createdObjectWithContent.getProperties().get("r_object_type"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------get primary content of the document");
		RestObject primaryContent = client.getPrimaryContent(createdObjectWithContent, "media-url-policy", "all");
		System.out.println("the ACS link: " + primaryContent.getHref(LinkRelation.ENCLOSURE, "ACS"));
		System.out.println("the local link: " + primaryContent.getHref(LinkRelation.ENCLOSURE, "LOCAL"));
		System.out.println(NEWLINE);

		System.out.println("-------------create a new rendition to the document");
		client.enableStreamingForNextRequest();
		client.createContent(createdObjectWithContent, "I'm the rendition content", "text/html", "primary", "false");
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("-------------get primary content and rendition collection of the document");
		Feed renditionList = client.getContents(createdObjectWithContent);
    	for(Entry renditionEntry : renditionList.getEntries()) {
    		System.out.println(renditionEntry.getTitle());
    		RestObject rendition = client.getContent(renditionEntry.getContentSrc());
    		System.out.println("the content link: " + rendition.getHref(LinkRelation.ENCLOSURE));
    	}
    	System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created document");
		client.delete(createdObjectWithContent);
		System.out.println("http status: " + client.getStatus());		
		System.out.println(NEWLINE);
		
		System.out.println("finish Content Management sample");
		System.out.println(NEWLINE);
	}
	
	/**
	 * samples to manage versioning
	 * including check out, cancel check out, and check in with next major/minor or branch version
	 */
	private static void versionManagement() {
		System.out.println("start Version Management sample");
		
		RestObject tempCabinet = client.getCabinet("Temp");
		
		System.out.println("-------------create a document without binary content under the Temp cabinet");
		RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
		RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + createdObjectWithoutContent.getProperties().get("r_object_id") + " r_lock_owner=" + createdObjectWithoutContent.getProperties().get("r_lock_owner"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check out the created document");
		RestObject checkedOutObject = client.checkout(createdObjectWithoutContent);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedOutObject.getProperties().get("r_object_id") + " r_lock_owner=" + checkedOutObject.getProperties().get("r_lock_owner"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------cancel check out the created document");
		client.cancelCheckout(checkedOutObject);
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("-------------check out the created document");
		createdObjectWithoutContent = client.get(createdObjectWithoutContent);
		checkedOutObject = client.checkout(createdObjectWithoutContent);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedOutObject.getProperties().get("r_object_id") + " r_lock_owner=" + checkedOutObject.getProperties().get("r_lock_owner"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check in the created document next minor version with new name");
		RestObject newObjectWithName = new PlainRestObject("object_name", "obj_without_content_checked_in");
		RestObject checkedInObject = client.checkinNextMinor(checkedOutObject, newObjectWithName, null);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedInObject.getProperties().get("r_object_id") +
				" r_lock_owner=" + checkedInObject.getProperties().get("r_lock_owner") +
				" r_version_label=" + checkedInObject.getProperties().get("r_version_label"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check out the created document");
		checkedOutObject = client.checkout(createdObjectWithoutContent);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedOutObject.getProperties().get("r_object_id") + " r_lock_owner=" + checkedOutObject.getProperties().get("r_lock_owner"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check in the created document branch version with new binary content");
		checkedInObject = client.checkinBranch(checkedOutObject, null, "new content");
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedInObject.getProperties().get("r_object_id") +
				" r_lock_owner=" + checkedInObject.getProperties().get("r_lock_owner") +
				" r_version_label=" + checkedInObject.getProperties().get("r_version_label"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check out the checked in document");
		checkedOutObject = client.checkout(checkedInObject);
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedOutObject.getProperties().get("r_object_id") + " r_lock_owner=" + checkedOutObject.getProperties().get("r_lock_owner"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------check in the created document next major version with both new anme and new binary content");
		newObjectWithName = new PlainRestObject("object_name", "new object name");
		checkedInObject = client.checkinNextMajor(checkedOutObject, newObjectWithName, "new content");
		System.out.println("http status: " + client.getStatus());
		System.out.println("r_object_id=" + checkedInObject.getProperties().get("r_object_id") +
				" r_lock_owner=" + checkedInObject.getProperties().get("r_lock_owner") +
				" r_version_label=" + checkedInObject.getProperties().get("r_version_label"));
		System.out.println(NEWLINE);
		
		System.out.println("-------------delete the created document with all versions");
		client.delete(checkedInObject, "del-version", "all");
		System.out.println("http status: " + client.getStatus());
		System.out.println(NEWLINE);
		
		System.out.println("finish Version Management sample");
		System.out.println(NEWLINE);
	}

	/**
	 * samples to execute dql query against the repository
	 */
	private static void dqlQuery() {
		System.out.println("start DQL Query sample");
		
		System.out.println("-------------execute dql 'select * from dm_cabinet' with 5 items per page");
		Feed queryResult = client.dql("select * from dm_user", "items-per-page", "5");
		for(Entry e : queryResult.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("r_object_id=" + o.getProperties().get("r_object_id") + " user_name=" + o.getProperties().get("user_name"));
		}
		System.out.println(queryResult.getHref(LinkRelation.PAGING_NEXT));
		System.out.println(NEWLINE);
		
		System.out.println("-------------get the next page of dql 'select * from dm_cabinet' with 5 items per page");
		queryResult = client.nextPage(queryResult);
		for(Entry e : queryResult.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("r_object_id=" + o.getProperties().get("r_object_id") + " user_name=" + o.getProperties().get("user_name"));
		}
		System.out.println(NEWLINE);
		
		System.out.println("-------------execute dql 'select * from dm_format' with 5 items per page");
		queryResult = client.dql("select name,description from dm_format", "items-per-page", "5");
		for(Entry e : queryResult.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("name=" + o.getProperties().get("name") + " description=" + o.getProperties().get("description"));
		}
		System.out.println(NEWLINE);

		System.out.println("-------------get the next page of dql 'select * from dm_format' with 5 items per page");
		queryResult = client.nextPage(queryResult);
		for(Entry e : queryResult.getEntries()) {
			RestObject o = e.getContentObject();
			System.out.println("name=" + o.getProperties().get("name") + " description=" + o.getProperties().get("description"));
		}
		System.out.println(NEWLINE);

		System.out.println("finish DQL Query sample");
		System.out.println(NEWLINE);
	}
}
