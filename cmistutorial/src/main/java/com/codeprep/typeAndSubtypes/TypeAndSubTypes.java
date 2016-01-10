package com.codeprep.typeAndSubtypes;

import java.util.List;

import org.apache.chemistry.opencmis.client.api.DocumentType;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.Tree;

public class TypeAndSubTypes {

	public static void listTypesAndSubtypes(Session session) {
		boolean includePropertyDefinitions = false;
		List<Tree<ObjectType>> typeTrees = session.getTypeDescendants(null, -1, includePropertyDefinitions);
		for (Tree<ObjectType> typeTree : typeTrees) {
			logTypes(typeTree, "");
		}
	}

	private static void logTypes(Tree<ObjectType> typeTree, String tab) {
		ObjectType objType = typeTree.getItem();
		String docInfo = "";
		if (objType instanceof DocumentType) {
		DocumentType docType = (DocumentType)objType;
		docInfo = "[versionable=" + docType.isVersionable() +
		"][content="+docType.getContentStreamAllowed()+"]";
		}
		System.out.println(tab + objType.getDisplayName() + " [id=" +
		objType.getId() + "][fileable=" + objType.isFileable() +
		"][queryable=" + objType.isQueryable() + "]" + docInfo);
		for (Tree<ObjectType> subTypeTree : typeTree.getChildren()) {
		logTypes(subTypeTree, tab + " ");
		}
	}

}
