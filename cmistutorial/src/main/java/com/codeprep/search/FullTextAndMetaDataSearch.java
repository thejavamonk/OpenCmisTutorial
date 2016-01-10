package com.codeprep.search;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;

public class FullTextAndMetaDataSearch {

	public static void searchMetadataAndFTS(Session session) {
		// Check if the repo supports Metadata search and
		// Full Text Search (FTS)
		RepositoryInfo repoInfo = session.getRepositoryInfo();
		if (repoInfo.getCapabilities().getQueryCapability().equals(CapabilityQuery.METADATAONLY)) {
			System.out.println("Repository does not support FTS [repoName=" + repoInfo.getProductName() + "][repoVersion="
					+ repoInfo.getProductVersion() + "]");
		} else {
			String query = "SELECT * FROM cmis:document WHERE " + "cmis:name LIKE 'test%'";
			ItemIterable<QueryResult> searchResult = session.query(query, false);
			logSearchResult(query, searchResult);
			query = "SELECT * FROM cmis:document WHERE " + "cmis:name LIKE 'test%' AND CONTAINS('testing')";
			searchResult = session.query(query, false);
			logSearchResult(query, searchResult);
		}
	}

	private static void logSearchResult(String query, ItemIterable<QueryResult> searchResult) {
		System.out.println("Results from query " + query);
		int i = 1;
		for (QueryResult resultRow : searchResult) {
			System.out.println("--------------------------------------------\n" + i + " , "
					+ resultRow.getPropertyByQueryName("cmis:objectId").getFirstValue() + " , "
					+ resultRow.getPropertyByQueryName("cmis:objectTypeId").getFirstValue() + " , "
					+ resultRow.getPropertyByQueryName("cmis:name").getFirstValue());
			i++;
		}
		System.out.println("");
	}

}
