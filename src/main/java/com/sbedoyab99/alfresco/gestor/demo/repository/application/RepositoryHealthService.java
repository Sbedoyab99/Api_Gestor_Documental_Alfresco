package com.sbedoyab99.alfresco.gestor.demo.repository.application;

import org.springframework.stereotype.Service;

import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;
import com.sbedoyab99.alfresco.gestor.demo.repository.infrastructure.AlfrescoClient;

@Service
public class RepositoryHealthService {

	private final AlfrescoClient alfrescoClient;

	public RepositoryHealthService(AlfrescoClient alfrescoClient) {
		this.alfrescoClient = alfrescoClient;
	}

	public RepositoryStatus getStatus() {
		return this.alfrescoClient.isAvailable() ? RepositoryStatus.UP : RepositoryStatus.DOWN;
	}
}
