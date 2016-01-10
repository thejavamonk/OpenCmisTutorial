package com.codeprep.cmisClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CmisClient {

	private static Log logger = LogFactory.getLog(CmisClient.class);
	private static Map<String, Session> connections = new ConcurrentHashMap<String, Session>();

	public CmisClient() {

	}

	public Session getSession(String connectionName, String username, String password) {

		Session session = connections.get(connectionName);

		if (session == null) {
			logger.info("Not connected, creating new connection to Alfresco with connection id " + connectionName);

			SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(SessionParameter.USER, username);
			parameters.put(SessionParameter.PASSWORD, password);
			parameters.put(SessionParameter.ATOMPUB_URL,
					"http://localhost:8090/alfresco/api/-default-/cmis/versions/1.1/atom");
			parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			parameters.put(SessionParameter.COMPRESSION, "true");
			parameters.put(SessionParameter.CACHE_TTL_OBJECTS, "0");
			parameters.put(SessionParameter.REPOSITORY_ID, "-default-");

			List<Repository> repositories = sessionFactory.getRepositories(parameters);
			Repository alfrescoRepository = null;
			if (repositories != null && repositories.size() > 0) {
				logger.info("Found " + repositories.size() + " alfresco repositories");
				alfrescoRepository = repositories.get(0);
				logger.info("repository with id = " + alfrescoRepository.getId());
			} else {

				throw new CmisConnectionException(
						"Could not connect to the Alfresco Server " + " no repositories found");
			}
			session = alfrescoRepository.createSession();
			connections.put(connectionName, session);
		} else {
			logger.info("Already connected to Alfresco with the " + "connection id (" + connectionName + ")");
		}
		return session;
	}
}