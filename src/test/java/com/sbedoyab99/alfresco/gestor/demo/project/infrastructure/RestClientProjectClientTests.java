package com.sbedoyab99.alfresco.gestor.demo.project.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.sbedoyab99.alfresco.gestor.demo.project.application.AlfrescoProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.application.DuplicateProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;

class RestClientProjectClientTests {

	private MockRestServiceServer server;
	private RestClientProjectClient client;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = RestClient.builder().baseUrl("http://alfresco.test");
		this.server = MockRestServiceServer.bindTo(builder).build();
		this.client = new RestClientProjectClient(builder.build());
	}

	@Test
	void createsSiteAndMapsEntry() {
		this.server.expect(once(), requestTo("http://alfresco.test" + RestClientProjectClient.SITES_ENDPOINT))
				.andExpect(method(POST))
				.andExpect(content().json("""
						{
						  "id": "migracion-erp",
						  "title": "Migración ERP",
						  "description": "Documentación del proyecto",
						  "visibility": "PRIVATE"
						}
						"""))
				.andRespond(withSuccess("""
						{
						  "entry": {
						    "id": "migracion-erp",
						    "title": "Migración ERP",
						    "description": "Documentación del proyecto",
						    "visibility": "PRIVATE"
						  }
						}
						""", MediaType.APPLICATION_JSON));

		Project result = this.client.create(project());

		assertThat(result).isEqualTo(project());
		this.server.verify();
	}

	@Test
	void mapsConflictToDuplicateProject() {
		this.server.expect(requestTo("http://alfresco.test" + RestClientProjectClient.SITES_ENDPOINT))
				.andRespond(withStatus(HttpStatus.CONFLICT));

		assertThatThrownBy(() -> this.client.create(project()))
				.isInstanceOf(DuplicateProjectException.class)
				.hasMessageContaining("migracion-erp");
		this.server.verify();
	}

	@Test
	void mapsOtherRemoteErrorsToAlfrescoProjectException() {
		this.server.expect(requestTo("http://alfresco.test" + RestClientProjectClient.SITES_ENDPOINT))
				.andRespond(withResourceNotFound());

		assertThatThrownBy(() -> this.client.create(project()))
				.isInstanceOf(AlfrescoProjectException.class);
		this.server.verify();
	}

	private static Project project() {
		return new Project(
				"migracion-erp", "Migración ERP", "Documentación del proyecto", ProjectVisibility.PRIVATE);
	}
}
