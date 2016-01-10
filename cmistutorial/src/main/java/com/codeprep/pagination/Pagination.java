package com.codeprep.pagination;

import java.util.HashSet;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codeprep.cmisClient.CmisClient;

public class Pagination {

	private static Log logger = LogFactory.getLog(CmisClient.class);

	public static void listTopFolderWithPagingAndPropFilter(Session session) {
		Folder root = session.getRootFolder();
		
		Set<String> propertyFilter = new HashSet<String>();
		propertyFilter.add(PropertyIds.CREATED_BY);
		propertyFilter.add(PropertyIds.NAME);
		
		OperationContext operationContext = new OperationContextImpl();
		int maxItemsPerPage = 5;
		operationContext.setMaxItemsPerPage(maxItemsPerPage);
		operationContext.setFilter(propertyFilter);
		ItemIterable<CmisObject> contentItems = root.getChildren(operationContext);
		long numerOfPages = Math.abs(contentItems.getTotalNumItems() / maxItemsPerPage);
		int pageNumber = 1;
		boolean finishedPaging = false;
		int count = 0;
		while (!finishedPaging) {
			logger.info("Page " + pageNumber + " (" + numerOfPages + ")");
			ItemIterable<CmisObject> currentPage = contentItems.skipTo(count).getPage();
			for (CmisObject contentItem : currentPage) {
				logger.info(contentItem.getName() + " [type=" + contentItem.getType().getDisplayName() + "]");
				count++;
			}
			pageNumber++;
			if (!currentPage.getHasMoreItems()) {
				finishedPaging = true;
			}
		}
	}

}
