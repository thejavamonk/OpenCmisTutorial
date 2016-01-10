package com.codeprep.traverseroot;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TraverseRoot {

	private static Log logger = LogFactory.getLog(TraverseRoot.class);

	public static void listTopFolder(Session session) {

		Folder root = session.getRootFolder();
		ItemIterable<CmisObject> contentItems = root.getChildren();
		for (CmisObject contentItem : contentItems) {
			if (contentItem instanceof Document) {
				Document docMetadata = (Document) contentItem;
				ContentStream docContent = docMetadata.getContentStream();
				logger.info(docMetadata.getName() + " [size=" + docContent.getLength() + "][Mimetype="
						+ docContent.getMimeType() + "][type=" + docMetadata.getType().getDisplayName() + "]");
			} else {
				logger.info(contentItem.getName() + "[type=" + contentItem.getType().getDisplayName() + "]");
			}
		}
	}
}
