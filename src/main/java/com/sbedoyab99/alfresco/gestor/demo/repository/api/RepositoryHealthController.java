package com.sbedoyab99.alfresco.gestor.demo.repository.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbedoyab99.alfresco.gestor.demo.repository.application.RepositoryHealthService;
import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/repository")
@Tag(name = "Repositorio", description = "Estado y operaciones del repositorio Alfresco")
public class RepositoryHealthController {

	private final RepositoryHealthService healthService;

	public RepositoryHealthController(RepositoryHealthService healthService) {
		this.healthService = healthService;
	}

	@GetMapping("/health")
	@Operation(
			summary = "Consultar disponibilidad de Alfresco",
			description = "Comprueba la conexión y las credenciales configuradas para el repositorio.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "El repositorio está disponible"),
		@ApiResponse(responseCode = "503", description = "El repositorio no está disponible")
	})
	public ResponseEntity<RepositoryHealthResponse> health() {
		RepositoryStatus status = this.healthService.getStatus();
		HttpStatus httpStatus = status == RepositoryStatus.UP
				? HttpStatus.OK
				: HttpStatus.SERVICE_UNAVAILABLE;

		return ResponseEntity.status(httpStatus).body(new RepositoryHealthResponse(status));
	}
}
