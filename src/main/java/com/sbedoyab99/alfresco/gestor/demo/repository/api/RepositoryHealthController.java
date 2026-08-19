package com.sbedoyab99.alfresco.gestor.demo.repository.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbedoyab99.alfresco.gestor.demo.repository.application.RepositoryHealthService;
import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;

@RestController
@RequestMapping("/api/repository")
public class RepositoryHealthController {

	private final RepositoryHealthService healthService;

	public RepositoryHealthController(RepositoryHealthService healthService) {
		this.healthService = healthService;
	}

	@GetMapping("/health")
	public ResponseEntity<RepositoryHealthResponse> health() {
		RepositoryStatus status = this.healthService.getStatus();
		HttpStatus httpStatus = status == RepositoryStatus.UP
				? HttpStatus.OK
				: HttpStatus.SERVICE_UNAVAILABLE;

		return ResponseEntity.status(httpStatus).body(new RepositoryHealthResponse(status));
	}
}
