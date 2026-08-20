package com.sbedoyab99.alfresco.gestor.demo.project.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.sbedoyab99.alfresco.gestor.demo.project.application.AlfrescoProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.application.DuplicateProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;

@Component
public class RestClientProjectClient implements ProjectClient {

	static final String SITES_ENDPOINT = "/alfresco/api/-default-/public/alfresco/versions/1/sites";

	private final RestClient restClient;

	public RestClientProjectClient(RestClient alfrescoRestClient) {
		this.restClient = alfrescoRestClient;
	}

	@Override
	public Project create(Project project) {
		try {
			SiteEntryResponse response = this.restClient.post()
					.uri(SITES_ENDPOINT)
					.body(SiteBodyCreate.from(project))
					.retrieve()
					.onStatus(status -> status == HttpStatus.CONFLICT, (request, remoteResponse) -> {
						throw new DuplicateProjectException(project.id());
					})
					.onStatus(HttpStatusCode::isError, (request, remoteResponse) -> {
						throw new AlfrescoProjectException(
								"Alfresco rejected the site creation with status " + remoteResponse.getStatusCode());
					})
					.body(SiteEntryResponse.class);

			if (response == null || response.entry() == null) {
				throw new AlfrescoProjectException("Alfresco returned an empty response when creating the site");
			}
			return response.entry().toProject();
		}
		catch (DuplicateProjectException | AlfrescoProjectException exception) {
			throw exception;
		}
		catch (RestClientException exception) {
			throw new AlfrescoProjectException("Unable to create the site in Alfresco", exception);
		}
	}

	record SiteBodyCreate(String id, String title, String description, ProjectVisibility visibility) {

		static SiteBodyCreate from(Project project) {
			return new SiteBodyCreate(project.id(), project.title(), project.description(), project.visibility());
		}
	}

	record SiteEntryResponse(SiteEntry entry) {
	}

	record SiteEntry(String id, String title, String description, ProjectVisibility visibility) {

		Project toProject() {
			return new Project(this.id, this.title, this.description, this.visibility);
		}
	}
}
