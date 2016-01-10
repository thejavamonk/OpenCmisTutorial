package com.codeprep.customTypeFolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

public class CustomTypeFolder {

	public static void createFolderWithCustomType(Session session) {
		String folderName = "OpenCMISTest2";
		Folder parentFolder = session.getRootFolder();
		// Check if folder already exist, if not create it
		Folder newFolder = (Folder) getObject(session, parentFolder, folderName);
		if (newFolder == null) {
			Map<String, Object> newFolderProps = new HashMap<String, Object>();
			newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "F:myc:project");
			newFolderProps.put(PropertyIds.NAME, folderName);
			newFolderProps.put("myc:projectCode", "PROJ001");
			newFolder = parentFolder.createFolder(newFolderProps);
			System.out.println("Created new folder: " + newFolder.getPath() + " [creator=" + newFolder.getCreatedBy()
					+ "][created=" + date2String(newFolder.getCreationDate().getTime()) + "]");
		} else {
			System.out.println("Folder already exist: " + newFolder.getPath());
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

	private static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
	}
}
