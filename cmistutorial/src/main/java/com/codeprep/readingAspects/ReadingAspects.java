package com.codeprep.readingAspects;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.SecondaryType;

public class ReadingAspects {

	public static void readAspectsForExistingDocument(Document document) {
		// Make sure we got a document, then list aspects
		if (document != null) {
			List<SecondaryType> aspects = document.getSecondaryTypes();
			System.out.println("Aspects for: " + getDocumentPath(document));
			for (SecondaryType aspect : aspects) {
				System.out.println(" " + aspect.getDisplayName() + " (" + aspect.getId() + ")");
			}
		} else {
			System.out.println("Document is null, cannot list aspects for it!");
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
