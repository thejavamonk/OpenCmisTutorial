package com.codeprep.customDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;

public class CustomDocumentType {

	public static Document createDocumentFromFileWithCustomType(Session session) {
		String documentName = "OpenCMISTest2.pdf";
		File file = new File("Some.pdf");
		Folder parentFolder = session.getRootFolder();
		// Check if document already exist, if not create it
		Document newDocument = (Document) getObject(session, parentFolder, documentName);
		if (newDocument == null) {
			// Setup document metadata
			Map<String, Object> newDocumentProps = new HashMap<String, Object>();
			newDocumentProps.put(PropertyIds.OBJECT_TYPE_ID, "D:myc:itDoc");
			newDocumentProps.put(PropertyIds.NAME, documentName);
			InputStream is = null;
			try {
				// Setup document content
				is = new FileInputStream(file);
				String mimetype = "application/pdf";
				ContentStream contentStream = session.getObjectFactory().createContentStream(documentName,
						file.length(), mimetype, is);
				// Create versioned document object
				newDocument = parentFolder.createDocument(newDocumentProps, contentStream, VersioningState.MAJOR);
				System.out.println("Created new document: " + getDocumentPath(newDocument) + " [version="
						+ newDocument.getVersionLabel() + "][creator=" + newDocument.getCreatedBy() + "][created="
						+ date2String(newDocument.getCreationDate().getTime()) + "]");
				// Close the stream to handle any IO Exception
				is.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				IOUtils.closeQuietly(is);
			}
		} else {
			System.out.println("Document already exist: " + getDocumentPath(newDocument));
		}
		return newDocument;
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
