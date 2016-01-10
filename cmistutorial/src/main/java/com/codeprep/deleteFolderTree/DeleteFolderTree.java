package com.codeprep.deleteFolderTree;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

public class DeleteFolderTree {

	public static void deleteFolderTree(Session session) {
		
		UnfileObject unfileMode = UnfileObject.UNFILE;
		RepositoryInfo repoInfo = session.getRepositoryInfo();
		if (!repoInfo.getCapabilities().isUnfilingSupported()) {
			System.out.println("The repository does not support unfiling" + " a document from a folder, documents will "
					+ "be deleted completely from all associated folders " + "[repoName=" + repoInfo.getProductName()
					+ "][repoVersion=" + repoInfo.getProductVersion() + "]");
			unfileMode = UnfileObject.DELETE;
		}
		String folderName = "OpenCMISTestWithContent";
		Folder parentFolder = session.getRootFolder();
		// Check if folder exist, if not don't try and delete it
		Folder someFolder = (Folder) getObject(session, parentFolder, folderName);
		if (someFolder != null) {
			// Make sure the user is allowed to delete the folder
			if (someFolder.getAllowableActions().getAllowableActions().contains(Action.CAN_DELETE_TREE) == false) {
				throw new CmisUnauthorizedException(
						"Current user does" + " not have permission to delete folder tree" + parentFolder.getPath());
			}
			boolean deleteAllVersions = true;
			boolean continueOnFailure = true;
			List<String> failedObjectIds = someFolder.deleteTree(deleteAllVersions, unfileMode, continueOnFailure);
			System.out.println("Deleted folder and all its content: " + someFolder.getName());
			if (failedObjectIds != null && failedObjectIds.size() > 1) {
				for (String failedObjectId : failedObjectIds) {
					System.out.println("Could not delete Alfresco node with " + "Node Ref: " + failedObjectId);
				}
			}
		} else {
			System.out.println("Did not delete folder as it does not exist: " + parentFolder.getPath() + folderName);
		}
	}

	private static CmisObject getObject(Session session, Folder parentFolder, String objectName) {
		CmisObject object = null;
		try {
			String path2Object = parentFolder.getPath();
			if (!path2Object.endsWith("/")) {
				path2Object += "/";
			}
			path2Object += objectName;

			// This line of code is responsible for creation of the folder
			object = session.getObjectByPath(path2Object);
		} catch (CmisObjectNotFoundException nfe0) {
			// Nothing to do, object does not exist
		}
		return object;
	}
}
