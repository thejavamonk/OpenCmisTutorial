package com.codeprep.updateFolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

public class UpdateFolder {

	public static Folder updateFolder(Folder folder) {
		String newFolderName = "OpenCMISTest_Updated";
		Folder updatedFolder = null;
		// If we got a folder update the name of it
		if (folder != null) {
			// Make sure the user is allowed to update folder properties
			if (folder.getAllowableActions().getAllowableActions().contains(Action.CAN_UPDATE_PROPERTIES) == false) {
				throw new CmisUnauthorizedException("Current user does not have permission to update "
						+ "folder properties for " + folder.getPath());
			}
			// Update the folder with a new name
			String oldName = folder.getName();
			Map<String, Object> newFolderProps = new HashMap<String, Object>();
			newFolderProps.put(PropertyIds.NAME, newFolderName);
			updatedFolder = (Folder) folder.updateProperties(newFolderProps);
			System.out.println("Updated " + oldName + " with new name: " + updatedFolder.getPath() + " [creator="
					+ updatedFolder.getCreatedBy() + "][created="
					+ date2String(updatedFolder.getCreationDate().getTime()) + "][modifier="
					+ updatedFolder.getLastModifiedBy() + "][modified="
					+ date2String(updatedFolder.getLastModificationDate().getTime()) + "]");
		} else {
			System.out.println("Folder to update is null!");
		}
		return updatedFolder;
	}
	
	private static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
	}

}
