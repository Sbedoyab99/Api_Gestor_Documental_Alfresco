package com.sbedoyab99.alfresco.gestor.demo.project.application;

import org.springframework.stereotype.Service;

import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.infrastructure.ProjectClient;

@Service
public class CreateProjectService {

	private final ProjectClient projectClient;

	public CreateProjectService(ProjectClient projectClient) {
		this.projectClient = projectClient;
	}

	public Project create(Project project) {
		return this.projectClient.create(project);
	}
}
