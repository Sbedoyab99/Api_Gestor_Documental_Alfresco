package com.sbedoyab99.alfresco.gestor.demo.project.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;
import com.sbedoyab99.alfresco.gestor.demo.project.infrastructure.ProjectClient;

class CreateProjectServiceTests {

	private final ProjectClient projectClient = mock(ProjectClient.class);
	private final CreateProjectService service = new CreateProjectService(this.projectClient);

	@Test
	void createsProjectThroughClient() {
		Project requested = new Project(
				"migracion-erp", "Migración ERP", "Documentación del proyecto", ProjectVisibility.PRIVATE);
		Project created = new Project(
				"migracion-erp", "Migración ERP", "Documentación del proyecto", ProjectVisibility.PRIVATE);
		when(this.projectClient.create(requested)).thenReturn(created);

		Project result = this.service.create(requested);

		assertThat(result).isEqualTo(created);
		verify(this.projectClient).create(requested);
	}
}
