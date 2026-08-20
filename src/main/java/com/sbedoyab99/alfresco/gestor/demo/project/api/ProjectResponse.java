package com.sbedoyab99.alfresco.gestor.demo.project.api;

import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;

public record ProjectResponse(
		String id,
		String title,
		String description,
		ProjectVisibility visibility) {

	static ProjectResponse from(Project project) {
		return new ProjectResponse(project.id(), project.title(), project.description(), project.visibility());
	}
}
