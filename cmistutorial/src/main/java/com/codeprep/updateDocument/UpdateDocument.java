package com.codeprep.updateDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.CapabilityContentStreamUpdates;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

public class UpdateDocument {

	public static Document updateDocument(Session session, Document document) throws IOException {
		RepositoryInfo repoInfo = session.getRepositoryInfo();
		if (!repoInfo.getCapabilities().getContentStreamUpdatesCapability()
				.equals(CapabilityContentStreamUpdates.ANYTIME)) {
			System.out.println(
					"Updating content stream without a checkout is" + " not supported by this repository [repoName="
							+ repoInfo.getProductName() + "][repoVersion=" + repoInfo.getProductVersion() + "]");
			return document;
		}
		// Make sure we got a document, then update it
		Document updatedDocument = null;
		if (document != null) {
			// Make sure the user is allowed to update the content
			// for this document
			if (document.getAllowableActions().getAllowableActions().contains(Action.CAN_SET_CONTENT_STREAM) == false) {
				throw new CmisUnauthorizedException("Current user does not"
						+ " have permission to set/update content stream for " + getDocumentPath(document));
			}
			// Setup new document content
			String newDocumentText = "This is a test document that has " + "been updated with new content!";
			String mimetype = "text/plain; charset=UTF-8";
			byte[] bytes = newDocumentText.getBytes("UTF-8");
			ByteArrayInputStream input = new ByteArrayInputStream(bytes);
			ContentStream contentStream = session.getObjectFactory().createContentStream(document.getName(),
					bytes.length, mimetype, input);
			boolean overwriteContent = true;
			updatedDocument = document.setContentStream(contentStream, overwriteContent);
			if (updatedDocument == null) {
				System.out.println("No new version was created when " + "content stream was updated for "
						+ getDocumentPath(document));
				updatedDocument = document;
			}
			System.out.println("Updated content for document: " + getDocumentPath(updatedDocument) + " [version="
					+ updatedDocument.getVersionLabel() + "][modifier=" + updatedDocument.getLastModifiedBy()
					+ "][modified=" + date2String(updatedDocument.getLastModificationDate().getTime()) + "]");
		} else {
			System.out.println("Document is null, cannot update it!");
		}
		return updatedDocument;
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

	private static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
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
