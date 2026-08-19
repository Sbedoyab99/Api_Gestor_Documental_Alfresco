package com.sbedoyab99.alfresco.gestor.demo.repository.api;

import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual de la integración con Alfresco")
public record RepositoryHealthResponse(
		@Schema(description = "Disponibilidad del repositorio", example = "UP")
		RepositoryStatus status) {
}
