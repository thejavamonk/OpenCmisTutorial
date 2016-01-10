package com.codeprep.getDocumentContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;

public class GetDocumentContent {

	public void getContentForDocumentAndStoreInFile(Session session) {
		// This is one of the out-of-the-box email templates in Alfresco
		String documentPath = "/Data Dictionary/Email Templates/invite/invite-email.html.ftl";
		// Get the document object by path so we can
		// get to the content stream
		Document templateDocument = (Document) session.getObjectByPath(documentPath);
		if (templateDocument != null) {
			// Make sure the user is allowed to get the
			// content stream (bytes) for the document
			if (templateDocument.getAllowableActions().getAllowableActions()
					.contains(Action.CAN_GET_CONTENT_STREAM) == false) {
				throw new CmisUnauthorizedException(
						"Current user does not have permission to get the" + " content stream for " + documentPath);
			}
			File file = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				// Create the file on the local drive without any content
				file = new File(templateDocument.getName());
				if (!file.exists()) {
					file.createNewFile();
				}
				// Get the object content stream and write to
				// the new local file
				input = templateDocument.getContentStream().getStream();
				output = new FileOutputStream(file);
				org.apache.commons.io.IOUtils.copy(input, output);
				// Close streams and handle exceptions
				input.close();
				output.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				IOUtils.closeQuietly(output);
				IOUtils.closeQuietly(input);
			}
			System.out.println(
					"Created a new file " + file.getAbsolutePath() + " with content from document: " + documentPath);
		} else {
			System.out.println("Template document could not be found: " + documentPath);
		}
	}

}
