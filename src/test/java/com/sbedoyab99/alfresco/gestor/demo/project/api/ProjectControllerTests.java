package com.sbedoyab99.alfresco.gestor.demo.project.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sbedoyab99.alfresco.gestor.demo.project.application.CreateProjectService;
import com.sbedoyab99.alfresco.gestor.demo.project.application.DuplicateProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;

class ProjectControllerTests {

	private final CreateProjectService service = mock(CreateProjectService.class);
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(this.service))
				.setControllerAdvice(new ProjectExceptionHandler())
				.build();
	}

	@Test
	void respondsCreatedWithProjectAndLocation() throws Exception {
		when(this.service.create(any())).thenReturn(project());

		this.mockMvc.perform(post("/api/projects")
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequest()))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/api/projects/migracion-erp"))
				.andExpect(jsonPath("$.id").value("migracion-erp"))
				.andExpect(jsonPath("$.title").value("Migración ERP"))
				.andExpect(jsonPath("$.visibility").value("PRIVATE"));
	}

	@Test
	void rejectsInvalidProjectId() throws Exception {
		this.mockMvc.perform(post("/api/projects")
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequest().replace("migracion-erp", "Migracion ERP")))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.id").exists());
	}

	@Test
	void rejectsUnknownVisibility() throws Exception {
		this.mockMvc.perform(post("/api/projects")
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequest().replace("PRIVATE", "INTERNAL")))
				.andExpect(status().isBadRequest());
	}

	@Test
	void respondsConflictWhenProjectAlreadyExists() throws Exception {
		when(this.service.create(any())).thenThrow(new DuplicateProjectException("migracion-erp"));

		this.mockMvc.perform(post("/api/projects")
					.contentType(MediaType.APPLICATION_JSON)
					.content(validRequest()))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.type").value("urn:problem:duplicate-project"));
	}

	private static Project project() {
		return new Project(
				"migracion-erp", "Migración ERP", "Documentación del proyecto", ProjectVisibility.PRIVATE);
	}

	private static String validRequest() {
		return """
				{
				  "id": "migracion-erp",
				  "title": "Migración ERP",
				  "description": "Documentación del proyecto",
				  "visibility": "PRIVATE"
				}
				""";
	}
}
