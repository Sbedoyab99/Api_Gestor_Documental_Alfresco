package com.sbedoyab99.alfresco.gestor.demo.repository.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class RestClientAlfrescoClient implements AlfrescoClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientAlfrescoClient.class);
	private static final String ROOT_NODE_ENDPOINT =
			"/alfresco/api/-default-/public/alfresco/versions/1/nodes/-root-?fields=id";

	private final RestClient restClient;

	public RestClientAlfrescoClient(RestClient alfrescoRestClient) {
		this.restClient = alfrescoRestClient;
	}

	@Override
	public boolean isAvailable() {
		try {
			this.restClient.get()
					.uri(ROOT_NODE_ENDPOINT)
					.retrieve()
					.toBodilessEntity();
			return true;
		}
		catch (RestClientException exception) {
			LOGGER.error("Unable to communicate with the Alfresco repository", exception);
			return false;
		}
	}
}
