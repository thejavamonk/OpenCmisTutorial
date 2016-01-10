package com.codeprep.getRepoInfo;

import org.apache.chemistry.opencmis.commons.data.RepositoryCapabilities;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetRepoInfo {

	private static Log logger = LogFactory.getLog(GetRepoInfo.class);

	public static void getRepoInfo(RepositoryInfo repositoryInfo) {
		RepositoryCapabilities repoCapabilities = repositoryInfo.getCapabilities();
		logger.info("aclCapability = " + repoCapabilities.getAclCapability().name());
		logger.info("changesCapability = " + repoCapabilities.getChangesCapability().name());
		logger.info("contentStreamUpdatable = " + repoCapabilities.getContentStreamUpdatesCapability().name());
		logger.info("joinCapability = " + repoCapabilities.getJoinCapability().name());
		logger.info("queryCapability = " + repoCapabilities.getQueryCapability().name());
		logger.info("renditionCapability = " + repoCapabilities.getRenditionsCapability().name());
		logger.info("allVersionsSearchable? = " + repoCapabilities.isAllVersionsSearchableSupported());
		logger.info("getDescendantSupported? = " + repoCapabilities.isGetDescendantsSupported());
		logger.info("getFolderTreeSupported? = " + repoCapabilities.isGetFolderTreeSupported());
		logger.info("multiFilingSupported? = " + repoCapabilities.isMultifilingSupported());
		logger.info("privateWorkingCopySearchable? = " + repoCapabilities.isPwcSearchableSupported());
		logger.info("pwcUpdateable? = " + repoCapabilities.isPwcUpdatableSupported());
		logger.info("unfilingSupported? = " + repoCapabilities.isUnfilingSupported());
		logger.info("versionSpecificFilingSupported? = " + repoCapabilities.isVersionSpecificFilingSupported());
	}
}
