package com.sbedoyab99.alfresco.gestor.demo.project.api;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sbedoyab99.alfresco.gestor.demo.project.application.AlfrescoProjectException;
import com.sbedoyab99.alfresco.gestor.demo.project.application.DuplicateProjectException;

@RestControllerAdvice
public class ProjectExceptionHandler {

	@ExceptionHandler(DuplicateProjectException.class)
	ProblemDetail handleDuplicateProject(DuplicateProjectException exception) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
		problem.setTitle("Project already exists");
		problem.setType(URI.create("urn:problem:duplicate-project"));
		return problem;
	}

	@ExceptionHandler(AlfrescoProjectException.class)
	ProblemDetail handleAlfrescoProject(AlfrescoProjectException exception) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.BAD_GATEWAY, "Alfresco could not complete the project operation");
		problem.setTitle("Alfresco operation failed");
		problem.setType(URI.create("urn:problem:alfresco-operation-failed"));
		return problem;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
		Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(
						fieldError -> fieldError.getField(),
						fieldError -> fieldError.getDefaultMessage() == null
								? "invalid value"
								: fieldError.getDefaultMessage(),
						(existing, ignored) -> existing));

		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
		problem.setTitle("Invalid request");
		problem.setType(URI.create("urn:problem:validation"));
		problem.setProperty("errors", errors);
		return problem;
	}
}
