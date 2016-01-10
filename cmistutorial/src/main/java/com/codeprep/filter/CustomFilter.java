package com.codeprep.filter;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codeprep.cmisClient.CmisClient;

public class CustomFilter {

	private static Log logger = LogFactory.getLog(CmisClient.class);

	public static void listTopFolderWithPropFilter(Session session) {
		Folder root = session.getRootFolder();
		OperationContext operationContext = new OperationContextImpl();
		int maxItemsPerPage = 5;
		operationContext.setMaxItemsPerPage(maxItemsPerPage);
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
