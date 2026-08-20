package com.sbedoyab99.alfresco.gestor.demo.project.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sbedoyab99.alfresco.gestor.demo.project.application.CreateProjectService;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Proyectos", description = "Gestión de proyectos representados como Sites de Alfresco")
public class ProjectController {

	private final CreateProjectService createProjectService;

	public ProjectController(CreateProjectService createProjectService) {
		this.createProjectService = createProjectService;
	}

	@PostMapping
	@Operation(summary = "Crear proyecto", description = "Crea un proyecto como Site de Alfresco.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Proyecto creado"),
		@ApiResponse(responseCode = "400", description = "Petición inválida"),
		@ApiResponse(responseCode = "409", description = "El identificador ya existe"),
		@ApiResponse(responseCode = "502", description = "Alfresco no pudo completar la operación")
	})
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody CreateProjectRequest request) {
		Project createdProject = this.createProjectService.create(request.toProject());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{projectId}")
				.buildAndExpand(createdProject.id())
				.toUri();

		return ResponseEntity.created(location).body(ProjectResponse.from(createdProject));
	}
}
