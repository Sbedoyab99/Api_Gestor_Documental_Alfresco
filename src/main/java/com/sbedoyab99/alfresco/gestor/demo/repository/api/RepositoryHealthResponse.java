package com.sbedoyab99.alfresco.gestor.demo.repository.api;

import com.sbedoyab99.alfresco.gestor.demo.repository.domain.RepositoryStatus;

public record RepositoryHealthResponse(RepositoryStatus status) {
}
