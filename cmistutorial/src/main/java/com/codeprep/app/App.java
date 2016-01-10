package com.codeprep.app;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;

import com.codeprep.cmisClient.CmisClient;
import com.codeprep.readingAspects.ReadingAspects;
import com.codeprep.search.FullTextAndMetaDataSearch;

public class App {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		CmisClient cmisClient = new CmisClient();
		Session session = cmisClient.getSession("testapp", "admin", "natgeo123");
		
		/**
		 * method to get repository info
		 */
//		GetRepoInfo.getRepoInfo(session.getRepositoryInfo());
		
		/**
		 * method to list all folders in the root directory
		 */
//		TraverseRoot.listTopFolder(session);
		
		/**
		 * method to demonstrate pagination
		 */
//		Pagination.listTopFolderWithPagingAndPropFilter(session);
		
		/**
		 * method to demonstrate types and subtypes in a CMS server
		 */
//		TypeAndSubTypes.listTypesAndSubtypes(session);
		
		/**
		 * method to create a folder using openCMIS
		 */
//		CreateFolder.createFolder(session);
		
		/**
		 * method to create a folder using a custom type
		 */
//		CustomTypeFolder.createFolderWithCustomType(session);
		
		/**
		 * method to upload a file or create a document
		 */
//		String folderPath = session.getRootFolder().getPath()+"CMISDemoFolder/";
//		Folder parentFolder = (Folder) session.getObjectByPath(folderPath);
//		try {
//			CreateDocument.createDocument(session, parentFolder);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		/**
		 * method to update folder
		 */
//		String folderPath = session.getRootFolder().getPath()+"CMISDemoFolder/";
//		Folder folder = (Folder) session.getObjectByPath(folderPath);
//		UpdateFolder.updateFolder(folder);
		
		/**
		 * method to update content of document
		 */
//		String folderPath = session.getRootFolder().getPath()+"OpenCMISTest_Updated/OpenCMISTest.txt";
//		Document document = (Document) session.getObjectByPath(folderPath);
//		try {
//			UpdateDocument.updateDocument(session, document);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		/**
		 * method to delete a document
		 */
//		String folderPath = session.getRootFolder().getPath()+"OpenCMISTest_Updated/OpenCMISTest.txt";
//		Document document = (Document) session.getObjectByPath(folderPath);
//		DeleteDocument.deleteDocument(document);
		
		/**
		 * method to delete a folder (Throws an exception if there are other files inside the folder)
		 */
//		String folderPath = session.getRootFolder().getPath()+"folderToDelete/";
//		Folder folder = (Folder) session.getObjectByPath(folderPath);
//		DeleteFolder.deleteFolder(folder);
		
		/**
		 * method to delete a folder tree (Can delete non empty folders also)
		 */
//		DeleteFolderTree.deleteFolderTree(session);
		
		/**
		 * method to add aspects at the time of creation
		 */
//		AspectOnCreation.createFolderWithTitledAspect(session);
		
		/**
		 * method to add aspects on an already existing object
		 */
//		String folderPath = session.getRootFolder().getPath()+"OpenCMISTest_Updated/testDocument.txt";
//		Document document = (Document) session.getObjectByPath(folderPath);
//		AddingAspectToExistingObject.addAspectToExistingDocument(document);
		
		/**
		 * method to read applied aspects 
		 */
//		String folderPath = session.getRootFolder().getPath()+"OpenCMISTest_Updated/testDocument.txt";
//		Document document = (Document) session.getObjectByPath(folderPath);
//		ReadingAspects.readAspectsForExistingDocument(document);
		
		/**
		 * method to perform search FTS or metadata search 
		 */
		FullTextAndMetaDataSearch.searchMetadataAndFTS(session);
	}
}
