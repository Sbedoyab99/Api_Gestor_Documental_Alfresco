package com.sbedoyab99.alfresco.gestor.demo.project.application;

public class DuplicateProjectException extends RuntimeException {

	public DuplicateProjectException(String projectId) {
		super("A project with id '%s' already exists".formatted(projectId));
	}
}
