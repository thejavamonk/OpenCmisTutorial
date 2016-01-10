package com.codeprep.addingAspectToExistingObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;

public class AddingAspectToExistingObject {

	public static void addAspectToExistingDocument(Document document) {
		String aspectName = "P:cm:effectivity";
		// Make sure we got a document, and then add the aspect to it
		if (document != null) {
			// Check that document don't already got the aspect applied
			List<Object> aspects = document.getProperty("cmis:secondaryObjectTypeIds").getValues();
			if (!aspects.contains(aspectName)) {
				aspects.add(aspectName);
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put("cmis:secondaryObjectTypeIds", aspects);
				properties.put("cm:from", new Date());
				Calendar toDate = Calendar.getInstance();
				toDate.add(Calendar.MONTH, 2);
				properties.put("cm:to", toDate.getTime());
				Document updatedDocument = (Document) document.updateProperties(properties);
				System.out.println("Added aspect " + aspectName + " to " + getDocumentPath(updatedDocument));
			} else {
				System.out.println("Aspect " + aspectName + " is already applied to " + getDocumentPath(document));
			}
		} else {
			System.out.println("Document is null, cannot add aspect to it!");
		}
	}

	private static String getDocumentPath(Document document) {
		String path2Doc = getParentFolderPath(document);
		if (!path2Doc.endsWith("/")) {
			path2Doc += "/";
		}
		path2Doc += document.getName();
		return path2Doc;
	}

	private static String getParentFolderPath(Document document) {
		Folder parentFolder = getDocumentParentFolder(document);
		return parentFolder == null ? "Un-filed" : parentFolder.getPath();
	}

	private static Folder getDocumentParentFolder(Document document) {
		// Get all the parent folders (could be more than one
		// if multi-filed)
		List<Folder> parentFolders = document.getParents();
		// Grab the first parent folder
		if (parentFolders.size() > 0) {
			if (parentFolders.size() > 1) {
				System.out.println("The " + document.getName() + " has more than one parent folder, it is multi-filed");
			}
			return parentFolders.get(0);
		} else {
			System.out.println("Document " + document.getName() + " is un-filed and does not have a parent folder");
			return null;
		}
	}
}
