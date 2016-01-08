/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */

package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.RestObject;

public class TestJaxbCabinet extends TestJaxb {
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testUnmarshal() throws Exception {
		RestObject r = testUnmarshal(xml);
		assertEquals(r.getDefinition(), "http://localhost:8080/dctm-rest/repositories/acme/types/dm_cabinet");
		assertEquals(r.getName(), "cabinet");
		assertEquals(r.getPropertiesType(), "dm_cabinet-properties");
		assertNotNull(r.getLinks());
		assertTrue(r.getLinks().size() > 0);
		assertNotNull(r.getHref(LinkRelation.SELF));
		assertNotNull(r.getHref(LinkRelation.FOLDERS));
		assertNotNull(r.getHref(LinkRelation.OBJECTS));
		assertNotNull(r.getProperties());
		assertEquals(r.getProperties().get("r_object_id"), "0c00000180000107");
		assertEquals(((List)r.getProperties().get("r_version_label")).size(), 2);
		assertEquals(((List)r.getProperties().get("r_version_label")).get(0), "1.0");
		assertEquals(((List)r.getProperties().get("r_version_label")).get(1), "CURRENT");
	}
	
	private static final String xml = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					"<cabinet" +
					"    definition=\"http://localhost:8080/dctm-rest/repositories/acme/types/dm_cabinet\"" +
					"    xmlns=\"http://identifiers.emc.com/vocab/documentum\"" +
					"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"dm_cabinet\">" +
					"    <properties xsi:type=\"dm_cabinet-properties\">" +
					"        <r_object_id>0c00000180000107</r_object_id>" +
					"        <object_name>Temp</object_name>" +
					"        <r_object_type>dm_cabinet</r_object_type>" +
					"        <title>Temporary Object Cabinet</title>" +
					"        <subject>Cabinet containing all temporary objects.</subject>" +
					"        <authors xsi:nil=\"true\"/>" +
					"        <keywords xsi:nil=\"true\"/>" +
					"        <a_application_type/>" +
					"        <a_status/>" +
					"        <r_creation_date>2013-09-12T01:34:51.000+08:00</r_creation_date>" +
					"        <r_modify_date>2013-09-12T01:34:51.000+08:00</r_modify_date>" +
					"        <r_modifier>acme</r_modifier>" +
					"        <r_access_date xsi:nil=\"true\"/>" +
					"        <a_is_hidden>false</a_is_hidden>" +
					"        <i_is_deleted>false</i_is_deleted>" +
					"        <a_retention_date xsi:nil=\"true\"/>" +
					"        <a_archive>false</a_archive>" +
					"        <a_compound_architecture/>" +
					"        <a_link_resolved>false</a_link_resolved>" +
					"        <i_reference_cnt>1</i_reference_cnt>" +
					"        <i_has_folder>true</i_has_folder>" +
					"        <i_folder_id xsi:nil=\"true\"/>" +
					"        <r_composite_id xsi:nil=\"true\"/>" +
					"        <r_composite_label xsi:nil=\"true\"/>" +
					"        <r_component_label xsi:nil=\"true\"/>" +
					"        <r_order_no xsi:nil=\"true\"/>" +
					"        <r_link_cnt>7</r_link_cnt>" +
					"        <r_link_high_cnt>0</r_link_high_cnt>" +
					"        <r_assembled_from_id>0000000000000000</r_assembled_from_id>" +
					"        <r_frzn_assembly_cnt>0</r_frzn_assembly_cnt>" +
					"        <r_has_frzn_assembly>false</r_has_frzn_assembly>" +
					"        <resolution_label/>" +
					"        <r_is_virtual_doc>0</r_is_virtual_doc>" +
					"        <i_contents_id>0000000000000000</i_contents_id>" +
					"        <a_content_type/>" +
					"        <r_page_cnt>0</r_page_cnt>" +
					"        <r_content_size>0</r_content_size>" +
					"        <a_full_text>true</a_full_text>" +
					"        <a_storage_type/>" +
					"        <i_cabinet_id>0c00000180000107</i_cabinet_id>" +
					"        <owner_name>acme</owner_name>" +
					"        <owner_permit>7</owner_permit>" +
					"        <group_name>docu</group_name>" +
					"        <group_permit>5</group_permit>" +
					"        <world_permit>6</world_permit>" +
					"        <i_antecedent_id>0000000000000000</i_antecedent_id>" +
					"        <i_chronicle_id>0c00000180000107</i_chronicle_id>" +
					"        <i_latest_flag>true</i_latest_flag>" +
					"        <r_lock_owner/>" +
					"        <r_lock_date xsi:nil=\"true\"/>" +
					"        <r_lock_machine/>" +
					"        <log_entry/>" +
					"        <r_version_label>" +
					"            <item>1.0</item>" +
					"            <item>CURRENT</item>" +
					"        </r_version_label>" +
					"        <i_branch_cnt>0</i_branch_cnt>" +
					"        <i_direct_dsc>false</i_direct_dsc>" +
					"        <r_immutable_flag>false</r_immutable_flag>" +
					"        <r_frozen_flag>false</r_frozen_flag>" +
					"        <r_has_events>false</r_has_events>" +
					"        <acl_domain>acme</acl_domain>" +
					"        <acl_name>dm_4500000180000103</acl_name>" +
					"        <a_special_app/>" +
					"        <i_is_reference>false</i_is_reference>" +
					"        <r_creator_name>acme</r_creator_name>" +
					"        <r_is_public>true</r_is_public>" +
					"        <r_policy_id>0000000000000000</r_policy_id>" +
					"        <r_resume_state>0</r_resume_state>" +
					"        <r_current_state>0</r_current_state>" +
					"        <r_alias_set_id>0000000000000000</r_alias_set_id>" +
					"        <a_effective_date xsi:nil=\"true\"/>" +
					"        <a_expiration_date xsi:nil=\"true\"/>" +
					"        <a_publish_formats xsi:nil=\"true\"/>" +
					"        <a_effective_label xsi:nil=\"true\"/>" +
					"        <a_effective_flag xsi:nil=\"true\"/>" +
					"        <a_category/>" +
					"        <language_code/>" +
					"        <a_is_template>false</a_is_template>" +
					"        <a_controlling_app/>" +
					"        <r_full_content_size>0.0</r_full_content_size>" +
					"        <a_extended_properties xsi:nil=\"true\"/>" +
					"        <a_is_signed>false</a_is_signed>" +
					"        <a_last_review_date xsi:nil=\"true\"/>" +
					"        <i_retain_until xsi:nil=\"true\"/>" +
					"        <r_aspect_name xsi:nil=\"true\"/>" +
					"        <i_retainer_id xsi:nil=\"true\"/>" +
					"        <i_partition>0</i_partition>" +
					"        <i_is_replica>false</i_is_replica>" +
					"        <i_vstamp>0</i_vstamp>" +
					"        <r_folder_path>" +
					"            <item>/Temp</item>" +
					"        </r_folder_path>" +
					"        <i_ancestor_id>" +
					"            <item>0c00000180000107</item>" +
					"        </i_ancestor_id>" +
					"        <is_private>false</is_private>" +
					"    </properties>" +
					"    <links>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/cabinets/0c00000180000107.xml\" rel=\"self\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/cabinets/0c00000180000107.xml\" rel=\"edit\"/>" +
					"        <link" +
					"            hreftemplate=\"http://localhost:8080/dctm-rest/repositories/acme/objects/0c00000180000107/aspects/{aspectName}.xml\" rel=\"http://identifiers.emc.com/linkrel/attach-aspect\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/cabinets/0c00000180000107.xml\" rel=\"http://identifiers.emc.com/linkrel/delete\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0c00000180000107/folders.xml\" rel=\"http://identifiers.emc.com/linkrel/folders\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0c00000180000107/documents.xml\" rel=\"http://identifiers.emc.com/linkrel/documents\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0c00000180000107/objects.xml\" rel=\"http://identifiers.emc.com/linkrel/objects\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/folders/0c00000180000107/child-links.xml\" rel=\"http://identifiers.emc.com/linkrel/child-links\"/>" +
					"        <link" +
					"            href=\"http://localhost:8080/dctm-rest/repositories/acme/relations.xml?related-object-id=0c00000180000107&amp;related-object-role=any\" rel=\"http://identifiers.emc.com/linkrel/relations\"/>" +
					"    </links>" +
					"</cabinet>";

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModel() {
    	JaxbCabinet object = new JaxbCabinet();
    	object.setDefinition("mydefinition");
    	object.setType("dm_xxx");
    	
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
    	map.put("p1", "v1");
    	map.put("p2", "v2");
    	map.put("p3", Arrays.asList("v3.1", "v3.2", "v3.3"));
    	map.put("p4", "v4");
    	map.put("p5", Arrays.asList("v5.1", "v5.2", "v5.3"));
    	map.put("p6", Arrays.asList("v6.1"));
    	object.setProperties(map);
    	
		List<Link> links = new ArrayList<Link>();
		JaxbLink link = new JaxbLink();
		link.setHref("http://link1");
		link.setRel(LinkRelation.SELF.rel());
		links.add(link);
		link = new JaxbLink();
		link.setHref("http://link2");
		link.setRel(LinkRelation.ALTERNATE.rel());
		links.add(link);
		object.setLinks(links);
    	return (T)object;
	}
}
