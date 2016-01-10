package com.codeprep.deleteDocument;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

public class DeleteDocument {

	public static void deleteDocument(Document document) {
		// If we got a document try and delete it
		if (document != null) {
			// Make sure the user is allowed to delete the document
			if (document.getAllowableActions().getAllowableActions().contains(Action.CAN_DELETE_OBJECT) == false) {
				throw new CmisUnauthorizedException("Current user does " + "not have permission to delete document "
						+ document.getName() + " with Object ID " + document.getId());
			}
			String docPath = document.getPaths().get(0);
			boolean deleteAllVersions = true;
			document.delete(deleteAllVersions);
			System.out.println("Deleted document: " + docPath);
		} else {
			System.out.println("Cannot delete document as it is null!");
		}
	}

}
