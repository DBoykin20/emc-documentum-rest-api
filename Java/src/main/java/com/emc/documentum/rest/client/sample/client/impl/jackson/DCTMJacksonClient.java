/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jackson;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient;
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.json.JsonFeed;
import com.emc.documentum.rest.client.sample.model.json.JsonHomeDocument;
import com.emc.documentum.rest.client.sample.model.json.JsonObject;
import com.emc.documentum.rest.client.sample.model.json.JsonRepository;

/**
 * the DCTMRestClient implementation by Jackson json support
 */
@NotThreadSafe
public class DCTMJacksonClient extends AbstractRestTemplateClient implements DCTMRestClient {
	public DCTMJacksonClient(String contextRoot, String repositoryName,
			String username, String password, boolean useFormatExtension) {
		super(contextRoot, repositoryName, username, password, useFormatExtension);
	}
	
	@Override
	public HomeDocument getHomeDocument() {
		if(homeDocument == null) {
			homeDocument = get(getHomeDocumentUri(), Headers.ACCEPT_JSON_HOME_DOCUMENT, JsonHomeDocument.class);
		}
		return homeDocument;
	}
	
	@Override
	public Feed getRepositories() {
		if(repositories == null) {
			String repositoriesUri = getHomeDocument().getHref(LinkRelation.REPOSITORIES);
			repositories = get(repositoriesUri, true, JsonFeed.class);
		}
		return repositories;
	}
	
	@Override
	public Repository getRepository() {
		if(repository == null) {
			boolean resetEnableStreaming = enableStreaming;
			Feed repositories = getRepositories();
			for(Entry e : repositories.getEntries()) {
				if(repositoryName.equals(e.getTitle())) {
					repository = get(e.getContentSrc(), false, JsonRepository.class);
				}
			}
			if(resetEnableStreaming) {
				enableStreaming = resetEnableStreaming;
			}
		}
		return repository;
	}
	
	@Override
	public Feed dql(String dql, String... params) {
		return get(getRepository().getHref(LinkRelation.SELF), true, JsonFeed.class, UriHelper.append(params, "dql", dql));
	}
	
	@Override
	public Feed getCabinets(String... params) {
		Repository repository = getRepository();
		String cabinetsUri = repository.getHref(LinkRelation.CABINETS);
		return get(cabinetsUri, true, JsonFeed.class, params);
	}
	
	@Override
	public RestObject getCabinet(String cabinet, String... params) {
		RestObject obj = null; 
		Feed feed = getCabinets("filter", "starts-with(object_name,'" + cabinet + "')");
		List<Entry> entries = feed.getEntries();
		if(entries != null) {
			for(Entry e : entries) {
				if(cabinet.equals(e.getTitle())) {
					obj = get(e.getContentSrc(), false, JsonObject.class);
					break;
				}
			}
		}
		return obj;
	}
	
	@Override
	public RestObject get(RestObject object, String... params) {
		return get(object.getHref(LinkRelation.SELF), false, object.getClass(), params);
	}
	
	@Override
	public Feed getFolders(RestObject parent, String... params) {
		return get(parent.getHref(LinkRelation.FOLDERS), true, JsonFeed.class, params);
	}
	
	@Override
	public Feed getObjects(RestObject parent, String... params) {
		return get(parent.getHref(LinkRelation.OBJECTS), true, JsonFeed.class, params);
	}
	
	@Override
	public Feed getDocuments(RestObject parent, String... params) {
		return get(parent.getHref(LinkRelation.DOCUMENTS), true, JsonFeed.class, params);
	}
	
	@Override
	public RestObject createFolder(RestObject parent, RestObject newFolder, String... params) {
		return post(parent.getHref(LinkRelation.FOLDERS), new JsonObject(newFolder), JsonObject.class, params);
	}
	
	@Override
	public RestObject getFolder(String folderUri, String... params) {
		return get(folderUri, false, JsonObject.class, params);
	}
	
	@Override
	public RestObject createObject(RestObject parent, RestObject objectToCreate, Object content, String... params) {
		return post(parent.getHref(LinkRelation.OBJECTS), new JsonObject(objectToCreate), content, JsonObject.class, params);
	}

	@Override
	public RestObject getObject(String objectUri, String... params) {
		return get(objectUri, false, JsonObject.class, params);
	}
	
	@Override
	public RestObject createDocument(RestObject parent, RestObject objectToCreate, Object content, String... params) {
		return post(parent.getHref(LinkRelation.DOCUMENTS), new JsonObject(objectToCreate), content, JsonObject.class, params);
	}
	
	@Override
	public RestObject getDocument(String documentUri, String... params) {
		return get(documentUri, false, JsonObject.class, params);
	}
	
	@Override
	public RestObject update(RestObject oldObject, RestObject newObject, String... params) {
		try {
			RestObject newRestObject = newRestObject(oldObject, newObject);
			return post(oldObject.getHref(LinkRelation.EDIT), newRestObject, newRestObject.getClass(), params);
		} catch (Exception e) {
			throw new IllegalArgumentException(oldObject.getClass().getName());
		}
	}
	
	@Override
	public void delete(RestObject object, String... params) {
		delete(object.getHref(LinkRelation.DELETE), params);
	}
	
	@Override
	public RestObject createContent(RestObject object, Object content, String mediaType, String... params) {
		return post(object.getHref(LinkRelation.CONTENTS), content, mediaType, JsonObject.class, params);
	}
	
	@Override
	public RestObject getPrimaryContent(RestObject object, String... params) {
		return getContent(object.getHref(LinkRelation.PRIMARY_CONTENT), params);
	}
	
	@Override
	public RestObject getContent(String contentUri, String... params) {
		return get(contentUri, false, JsonObject.class, params);
	}
	
	@Override
	public Feed getContents(RestObject object, String... params) {
		return get(object.getHref(LinkRelation.CONTENTS), true, JsonFeed.class, params);
	}
	
	@Override
	public RestObject checkout(RestObject object, String... params) {
		return put(object.getHref(LinkRelation.CHECKOUT), JsonObject.class, params);
	}
	
	@Override
	public void cancelCheckout(RestObject object) {
		delete(object.getHref(LinkRelation.CANCEL_CHECKOUT));
	}
	
	@Override
	public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String... params) {
		return checkin(oldObject, LinkRelation.CHECKIN_NEXT_MAJOR, newObject, content, params);
	}
	
	@Override
	public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String... params) {
		return checkin(oldObject, LinkRelation.CHECKIN_NEXT_MINOR, newObject, content, params);
	}
	
	@Override
	public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String... params) {
		return checkin(oldObject, LinkRelation.CHECKIN_BRANCH_VERSION, newObject, content, params);
	}
	
	@Override
	public Feed getVersions(RestObject object, String... params) {
		return get(object.getHref(LinkRelation.VERSIONS), true, JsonFeed.class, params);
	}
	
	@Override
	public Feed nextPage(Feed feed) {
		return page(feed.getHref(LinkRelation.PAGING_NEXT));
	}
	
	@Override
	public Feed previousPage(Feed feed) {
		return page(feed.getHref(LinkRelation.PAGING_PREV));
	}

	@Override
	public Feed firstPage(Feed feed) {
		return page(feed.getHref(LinkRelation.PAGING_FIRST));
	}

	@Override
	public Feed lastPage(Feed feed) {
		return page(feed.getHref(LinkRelation.PAGING_LAST));
	}
	
	private Feed page(String uri) {
		Feed feed = null;
		if(uri != null) {
			feed = get(uri, true, JsonFeed.class);
		}
		return feed;
	}
	
	private RestObject checkin(RestObject oldObject, LinkRelation rel, RestObject newObject, Object content, String... params) {
		RestObject resp = null;
		if(newObject != null && content != null) {
			resp = post(oldObject.getHref(rel), new JsonObject(newObject), content, JsonObject.class, params);
		} else if(newObject == null) {
			resp = post(oldObject.getHref(rel), content, MediaType.APPLICATION_OCTET_STREAM_VALUE, oldObject.getClass(), params);
		} else if(content == null) {
			resp = post(oldObject.getHref(rel), new JsonObject(newObject), JsonObject.class, params);
		}
		return resp;
	}
    
	@Override
	protected void initRestTemplate(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new DCTMJacksonErrorHandler(restTemplate.getMessageConverters()));
	}

	@Override
	protected ClientType getClientType() {
		return ClientType.JSON;
	}
}
