package com.codeprep.deleteFolder;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

public class DeleteFolder {

	public static void deleteFolder(Folder folder) {
		// If we got a folder then delete
		if (folder != null) {
			// Make sure the user is allowed to delete the folder
			if (folder.getAllowableActions().getAllowableActions().contains(Action.CAN_DELETE_OBJECT) == false) {
				throw new CmisUnauthorizedException(
						"Current user does " + "not have permission to delete folder " + folder.getPath());
			}
			String folderPath = folder.getPath();
			folder.delete();
			System.out.println("Deleted folder: " + folderPath);
		} else {
			System.out.println("Cannot delete folder that is null");
		}
	}
}
