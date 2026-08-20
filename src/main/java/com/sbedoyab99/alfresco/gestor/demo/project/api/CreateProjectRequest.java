package com.sbedoyab99.alfresco.gestor.demo.project.api;

import com.sbedoyab99.alfresco.gestor.demo.project.domain.Project;
import com.sbedoyab99.alfresco.gestor.demo.project.domain.ProjectVisibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
		@NotBlank
		@Size(min = 3, max = 64)
		@Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
				message = "must contain only lowercase letters, numbers and single hyphens")
		String id,

		@NotBlank
		@Size(max = 255)
		String title,

		@Size(max = 1000)
		String description,

		@NotNull
		ProjectVisibility visibility) {

	Project toProject() {
		return new Project(this.id, this.title, this.description, this.visibility);
	}
}
