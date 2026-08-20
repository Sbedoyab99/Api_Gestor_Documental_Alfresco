package com.sbedoyab99.alfresco.gestor.demo.project.domain;

public record Project(
		String id,
		String title,
		String description,
		ProjectVisibility visibility) {
}
